/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.service_impl;

import java.time.Instant;
import java.util.*;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.smartict.activedirectory.constant.exception.EnumExceptionMessages;
import com.smartict.activedirectory.constant.exception.ServiceException;
import com.smartict.activedirectory.db.ActiveDirectorySetting;
import com.smartict.activedirectory.db.dao.ActiveDirectorySettingRepository;
import com.smartict.activedirectory.dto.active_directory.ActiveDirectorySettingDto;
import com.smartict.activedirectory.dto.login.ActiveDirectoryLoginRequest;
import com.smartict.activedirectory.dto.search.SearchKeyValueDto;
import com.smartict.activedirectory.dto.user.ActiveDirectoryUser;
import com.smartict.activedirectory.dto.user.UserSession;
import com.smartict.activedirectory.service.ActiveDirectoryService;
import com.smartict.activedirectory.service_impl.mapper.ActiveDirectorySettingMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author ege.yilmaz
 */

@Service
public class ActiveDirectoryServiceImpl implements ActiveDirectoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveDirectoryServiceImpl.class);

    private final ActiveDirectorySettingRepository activeDirectorySettingRepository;

    public ActiveDirectoryServiceImpl(ActiveDirectorySettingRepository activeDirectorySettingRepository) {
        this.activeDirectorySettingRepository = activeDirectorySettingRepository;
    }

    /**
     * kullanıcı kimlik doğrulama işlemi yaparak, kullanıcı bilgilerini döner.
     *
     * @param  activeDirectoryLoginRequest : {@link ActiveDirectoryLoginRequest}
     * @return                             : {@code activeDirectoryUserData}
     */
    @Override
    public ActiveDirectoryUser getAuthenticatedActiveDirectoryUser(ActiveDirectoryLoginRequest activeDirectoryLoginRequest) {
        checkActiveDirectoryLoginRequest(activeDirectoryLoginRequest);

        ActiveDirectorySetting activeDirectorySetting = activeDirectorySettingRepository.readByCustomerId(activeDirectoryLoginRequest.getCustomerId());

        if (activeDirectorySetting == null) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        List<ActiveDirectoryUser> ldapSearchResult = ldapSearch(
            getLdapContext(activeDirectorySetting, activeDirectoryLoginRequest.getUsername(), activeDirectoryLoginRequest.getPassword()),
            activeDirectorySetting,
            activeDirectorySetting.getUsernameKey(),
            activeDirectoryLoginRequest.getUsername()
        );
        if (Objects.nonNull(ldapSearchResult) && !ldapSearchResult.isEmpty()) {
            return ldapSearchResult.get(0);
        } else {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_USER_INFO_NOT_RECEIVED.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_USER_INFO_NOT_RECEIVED.getLanguageValue()
            );
        }
    }

    /**
     * Active Directory'de yer alan kullanıcıların listesini döner.
     *
     * @return : {@link List<ActiveDirectoryUser>}
     */
    @Override
    public List<ActiveDirectoryUser> getAllUsers(UUID customerId, String ldapQueryKey) {

        ActiveDirectorySetting activeDirectorySetting = activeDirectorySettingRepository.readByCustomerId(customerId);

        if (activeDirectorySetting == null) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        return ldapSearch(getSuperUserContext(activeDirectorySetting), activeDirectorySetting, ldapQueryKey, null);
    }

    /**
     * Active Directory'de yer alan kullanıcılara contains sorgusu yapar.
     * <p>
     * Search işemli {@link SearchKeyValueDto} içerisinde gönderilir.
     * </p>
     * <p>
     * {@link SearchKeyValueDto#key} -> içeriside sorgu yapılacak kolon adı verilir . ör. sAMAccountName kullanıcı adı kolonudur ve bunun üzerinden sorgu yapar
     * </p>
     * <p>
     * {@link SearchKeyValueDto#value} -> içerisine sorgu atılacak kolonun değeri verilir . Kolonda bu değeri <b>içeren</b> tüm kullanıcıları döndürür
     * </p>
     *
     * @return : {@link List<ActiveDirectoryUser>}
     */
    @Override
    public List<ActiveDirectoryUser> searchUsersContainsWithKeyValue(SearchKeyValueDto searchKeyValueDto) {

        ActiveDirectorySetting activeDirectorySetting = activeDirectorySettingRepository.readByCustomerId(searchKeyValueDto.getCustomerId());

        if (activeDirectorySetting == null) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (activeDirectorySetting == null || activeDirectorySetting.getSuperUserUsername() == null || activeDirectorySetting.getSuperUserUsername()
            .length() == 0 || activeDirectorySetting.getSuperUserPassword() == null || activeDirectorySetting.getSuperUserPassword().length() == 0) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_SUPER_USER_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_SUPER_USER_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        return ldapSearch(getSuperUserContext(activeDirectorySetting), activeDirectorySetting, searchKeyValueDto.getKey(), searchKeyValueDto.getValue());
    }

    @Override
    public ActiveDirectorySettingDto readByCustomerId(UUID customerId) {
        return ActiveDirectorySettingMapper.INSTANCE.entityToDto(activeDirectorySettingRepository.readByCustomerId(customerId));
    }

    @Override
    public ActiveDirectorySettingDto setActiveDirectorySetting(ActiveDirectorySettingDto activeDirectorySettingDto) {
        this.deleteByCustomerId(activeDirectorySettingDto.getCustomerId());
        ActiveDirectorySetting newActiveDirectorySetting = ActiveDirectorySettingMapper.INSTANCE.dtoToEntity(activeDirectorySettingDto);
        return ActiveDirectorySettingMapper.INSTANCE.entityToDto(activeDirectorySettingRepository.save(newActiveDirectorySetting));
    }

    @Override
    public ActiveDirectorySettingDto deleteByCustomerId(UUID customerId) {
        ActiveDirectorySetting activeDirectorySetting = activeDirectorySettingRepository.readByCustomerId(customerId);
        if (Objects.nonNull(activeDirectorySetting)) {
            activeDirectorySettingRepository.delete(activeDirectorySetting);
            return ActiveDirectorySettingMapper.INSTANCE.entityToDto(activeDirectorySetting);
        }
        return null;
    }

    private List<ActiveDirectoryUser> ldapSearch(LdapContext ldapContext, ActiveDirectorySetting activeDirectorySetting, String ldapQueryKey, String value) {
        if (Objects.isNull(ldapQueryKey) || ldapQueryKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_SUPER_USER_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_SUPER_USER_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(activeDirectorySetting.getOrganizationUnit()) || activeDirectorySetting.getOrganizationUnit().isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_ORGANIZATION_UNIT_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_ORGANIZATION_UNIT_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(activeDirectorySetting.getDomainControllers()) || activeDirectorySetting.getDomainControllers().isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_DOMAIN_CONTROLLERS_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_DOMAIN_CONTROLLERS_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(value) || value.isEmpty()) {
            value = "*";
        } else {
            value = "*" + value + "*";
        }

        try {
            NamingEnumeration<SearchResult> answers = ldapContext.search(
                activeDirectorySetting.getOrganizationUnit() + activeDirectorySetting.getDomainControllers(),
                ldapQueryKey + "=" + value,
                makeSearchControls(activeDirectorySetting)
            );

            List<Attributes> listOfUsers = new ArrayList<>();
            while (answers.hasMoreElements()) {
                listOfUsers.add(answers.next().getAttributes());
            }

            List<ActiveDirectoryUser> listOfActiveDirectoryUserData = new ArrayList<>();
            for (Attributes attributes : listOfUsers) {
                if (attributes.size() > 0) {

                    ActiveDirectoryUser activeDirectoryUser = makeActiveDirectoryUser(activeDirectorySetting, attributes);

                    listOfActiveDirectoryUserData.add(activeDirectoryUser);
                }
            }
            return listOfActiveDirectoryUserData;
        } catch (NamingException namingException) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_ERROR_WHILE_SEARCHING.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_ERROR_WHILE_SEARCHING.getLanguageValue()
            );
        }
    }

    private LdapContext getSuperUserContext(ActiveDirectorySetting activeDirectorySetting) {
        String superUserUsername = activeDirectorySetting.getSuperUserUsername();
        String superUserPassword = activeDirectorySetting.getSuperUserPassword();

        if (Objects.isNull(superUserUsername) || superUserUsername.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_SUPER_USER_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_SUPER_USER_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(superUserPassword) || superUserPassword.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_SUPER_USER_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_SUPER_USER_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        ActiveDirectoryLoginRequest activeDirectoryLoginRequest = new ActiveDirectoryLoginRequest(superUserUsername, superUserPassword);

        checkActiveDirectoryLoginRequest(activeDirectoryLoginRequest);

        LdapContext ldapContext = getLdapContext(activeDirectorySetting, superUserUsername, superUserPassword);

        UserSession superUserSession = new UserSession();
        superUserSession.setDate(Instant.now().toEpochMilli());
        superUserSession.setUsername(superUserUsername);
        superUserSession.setLdapContext(ldapContext);
        return ldapContext;
    }

    private SearchControls makeSearchControls(ActiveDirectorySetting activeDirectorySetting) {

        String usernameKey = activeDirectorySetting.getUsernameKey();
        String nameKey = activeDirectorySetting.getNameKey();
        String surnameKey = activeDirectorySetting.getSurnameKey();
        String mailKey = activeDirectorySetting.getMailKey();
        String mobileKey = activeDirectorySetting.getMobileKey();
        String addressKey = activeDirectorySetting.getAddressKey();
        String titleKey = activeDirectorySetting.getTitleKey();
        String pidKey = activeDirectorySetting.getPidKey();

        if (Objects.isNull(usernameKey) || usernameKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_USERNAME_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_USERNAME_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(nameKey) || nameKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_NAME_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_NAME_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(surnameKey) || surnameKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_SURNAME_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_SURNAME_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(mailKey) || mailKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_MAIL_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_MAIL_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(mobileKey) || mobileKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_MOBILE_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_MOBILE_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(addressKey) || addressKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_ADDRESS_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_ADDRESS_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(titleKey) || titleKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_TITLE_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_TITLE_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(pidKey) || pidKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_PID_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_PID_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] returningAttr = {
            usernameKey,
            nameKey,
            surnameKey,
            mailKey,
            mobileKey,
            addressKey,
            titleKey,
            pidKey
        };
        searchControls.setReturningAttributes(returningAttr);

        return searchControls;
    }

    /**
     * Kullanıcı'ya ait içerik ve şifre bilgisine göre {@link LdapContext} objesi döner.
     *
     * @param  activeDirectorySetting : {@link ActiveDirectorySetting}
     * @param  username               : {@link String}
     * @param  password               : {@link String}
     * @return                        : {@link LdapContext}
     */
    private LdapContext getLdapContext(ActiveDirectorySetting activeDirectorySetting, String username, String password) {
        String providerUrl = activeDirectorySetting.getProviderUrl();
        String securityAuthentication = activeDirectorySetting.getSecurityAuthentication();
        String principal = activeDirectorySetting.getPrincipal();

        if (Objects.isNull(providerUrl) || providerUrl.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_PROVIDER_URL_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_PROVIDER_URL_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(securityAuthentication) || securityAuthentication.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_SECURITY_AUTHENTICATION_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_SECURITY_AUTHENTICATION_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(principal) || principal.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_PRINCIPAL_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_PRINCIPAL_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        Hashtable<String, String> authEnv = new Hashtable<>();
        authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        authEnv.put(Context.PROVIDER_URL, providerUrl);
        authEnv.put(Context.SECURITY_AUTHENTICATION, securityAuthentication);
        authEnv.put(Context.SECURITY_PRINCIPAL, username + principal);
        authEnv.put(Context.SECURITY_CREDENTIALS, password);
        LOGGER.info("Context istenen sunucu: " + providerUrl + "\n" + "Context kullanıcı adı: " + username + "\n");
        try {
            InitialLdapContext initialLdapContext = new InitialLdapContext(authEnv, null);
            LOGGER.info("Context başarıyla oluşturuldu: " + "\n" + "Bağlanılan sunucu: " + providerUrl + "\n" + "Bağlanan hesabın kullanıcı adı: " + username);
            return initialLdapContext;
        } catch (AuthenticationException authenticationException) {
            LOGGER.warn(
                "Context oluştuma işlemi başarısız: " +
                        "\n" +
                        "Bağlantı istenen sunucu: " +
                        providerUrl +
                        "\n" +
                        "Bağlantı istenen kullanıcı adı: " +
                        username +
                        "\n" +
                        "Sebep: " +
                        "Kullanıcı adı veya şifre yanlış.!" +
                        "\n"
            );
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_FAILED.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_FAILED.getLanguageValue()
            );
        } catch (NamingException namingException) {
            LOGGER.warn(
                "Context oluştuma işlemi başarısız: " +
                        "\n" +
                        "Bağlantı istenen sunucu: " +
                        providerUrl +
                        "\n" +
                        "Bağlantı istenen kullanıcı adı: " +
                        username +
                        "\n" +
                        "Sebep: " +
                        "Konfigürasyonlar yanlış veya sunucuya bağlantı oluşturulamıyor.!" +
                        "\n"
            );
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONFIGURATION_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONFIGURATION_ERROR.getLanguageValue()
            );
        } catch (IllegalStateException illegalStateException) {
            LOGGER.warn(
                "Context oluştuma işlemi başarısız: " +
                        "\n" +
                        "Bağlantı istenen sunucu: " +
                        providerUrl +
                        "\n" +
                        "Bağlantı istenen kullanıcı adı: " +
                        username +
                        "\n" +
                        "Sebep: " +
                        "Sunucuya yapılan bağlantı zaman aşımına uğradı !" +
                        "\n"
            );
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONNECTION_TIME_OUT.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONNECTION_TIME_OUT.getLanguageValue()
            );
        }

    }

    /**
     * {@link ActiveDirectoryLoginRequest} ile gelen verilerin boş olup olmadığını kontrol eder.
     *
     * @param activeDirectoryLoginRequest : {@link ActiveDirectoryLoginRequest}
     */
    private void checkActiveDirectoryLoginRequest(ActiveDirectoryLoginRequest activeDirectoryLoginRequest) {
        // Boş kullanıcı ismi ve şifre ile kimlik doğrulaması yapılabildiğinden, engellenmesi amacıyla kontrol edilmektedir.
        if (Objects.isNull(activeDirectoryLoginRequest) || Objects.isNull(activeDirectoryLoginRequest.getUsername()) || Objects.isNull(
            activeDirectoryLoginRequest.getPassword()
        )) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_NULL.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_NULL.getLanguageValue()
            );
        }
        if (activeDirectoryLoginRequest.getUsername().equals("") || activeDirectoryLoginRequest.getPassword().equals("")) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_EMPTY_INPUT.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_EMPTY_INPUT.getLanguageValue()
            );
        }
    }

    private ActiveDirectoryUser makeActiveDirectoryUser(ActiveDirectorySetting activeDirectorySetting, Attributes attributes) {

        String usernameKey = activeDirectorySetting.getUsernameKey();
        String nameKey = activeDirectorySetting.getNameKey();
        String surnameKey = activeDirectorySetting.getSurnameKey();
        String mailKey = activeDirectorySetting.getMailKey();
        String mobileKey = activeDirectorySetting.getMobileKey();
        String addressKey = activeDirectorySetting.getAddressKey();
        String titleKey = activeDirectorySetting.getTitleKey();
        String pidKey = activeDirectorySetting.getPidKey();

        if (Objects.isNull(usernameKey) || usernameKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_USERNAME_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_USERNAME_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(nameKey) || nameKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_NAME_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_NAME_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(surnameKey) || surnameKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_SURNAME_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_SURNAME_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(mailKey) || mailKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_MAIL_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_MAIL_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(mobileKey) || mobileKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_MOBILE_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_MOBILE_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(addressKey) || addressKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_ADDRESS_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_ADDRESS_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(titleKey) || titleKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_TITLE_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_TITLE_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        if (Objects.isNull(pidKey) || pidKey.isEmpty()) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_PID_KEY_SETTING_NOT_FOUND_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_PID_KEY_SETTING_NOT_FOUND_ERROR.getLanguageValue()
            );
        }

        ActiveDirectoryUser activeDirectoryUser = new ActiveDirectoryUser();
        activeDirectoryUser.setUsername(parser(String.valueOf(attributes.get(usernameKey))));
        activeDirectoryUser.setName(parser(String.valueOf(attributes.get(nameKey))));
        activeDirectoryUser.setSurname(parser(String.valueOf(attributes.get(surnameKey))));
        activeDirectoryUser.setEposta(parser(String.valueOf(attributes.get(mailKey))));
        activeDirectoryUser.setMobilePhone(parser(String.valueOf(attributes.get(mobileKey))));
        activeDirectoryUser.setAddress(parser(String.valueOf(attributes.get(addressKey))));
        activeDirectoryUser.setTitle(parser(String.valueOf(attributes.get(titleKey))));
        activeDirectoryUser.setEmployeeNo(parser(String.valueOf(attributes.get(pidKey))));

        return activeDirectoryUser;
    }

    private String parser(String str) {

        if (Objects.nonNull(str) && !str.equals("null")) {
            return String.join("", (str.split(": ")[1]));
        }
        return null;
    }
}