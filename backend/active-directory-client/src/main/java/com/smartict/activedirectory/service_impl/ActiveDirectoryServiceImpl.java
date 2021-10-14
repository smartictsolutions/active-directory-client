/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.service_impl;

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
import com.smartict.activedirectory.dto.login.ActiveDirectoryLoginRequest;
import com.smartict.activedirectory.dto.user.ActiveDirectoryUser;
import com.smartict.activedirectory.service.ActiveDirectoryService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author ege.yilmaz
 */

@Service
public class ActiveDirectoryServiceImpl implements ActiveDirectoryService {

    private String principal;

    private String domainControllers;

    private String organizationUnit;

    private String securityAuthentication;

    private String filterQuery;

    private String providerUrl;

    private String usernameKey;

    private String nameKey;

    private String surnameKey;

    private String mailKey;

    private String mobileKey;

    private String addressKey;

    private String titleKey;

    private String pidKey;

    private SearchControls searchControls;

    public ActiveDirectoryServiceImpl(
        @Value("${spring.active-directory.principal}") String principal,
        @Value("${spring.active-directory.domain-controllers}") String domainControllers,
        @Value("${spring.active-directory.organization-unit}") String organizationUnit,
        @Value("${spring.active-directory.security-authentication}") String securityAuthentication,
        @Value("${spring.active-directory.filter-query}") String filterQuery,
        @Value("${spring.active-directory.provider-url}") String providerUrl,
        @Value("${spring.active-directory.username-key}") String usernameKey,
        @Value("${spring.active-directory.name-key}") String nameKey,
        @Value("${spring.active-directory.surname-key}") String surnameKey,
        @Value("${spring.active-directory.mail-key}") String mailKey,
        @Value("${spring.active-directory.mobile-key}") String mobileKey,
        @Value("${spring.active-directory.address-key}") String addressKey,
        @Value("${spring.active-directory.title-key}") String titleKey,
        @Value("${spring.active-directory.pid-key}") String pidKey
    ) {
        this.principal = Objects.nonNull(System.getenv("PRINCIPAL")) ? System.getenv("PRINCIPAL") : principal;
        this.domainControllers = Objects.nonNull(System.getenv("DOMAIN_CONTROLLERS")) ? System.getenv("DOMAIN_CONTROLLERS") : domainControllers;
        this.organizationUnit = Objects.nonNull(System.getenv("ORGANIZAION_UNIT")) ? System.getenv("ORGANIZAION_UNIT") : organizationUnit;
        this.securityAuthentication = Objects.nonNull(System.getenv("SECURITY_AUTHENTICATION")) ? System.getenv("SECURITY_AUTHENTICATION")
                : securityAuthentication;
        this.filterQuery = Objects.nonNull(System.getenv("FILTER_QUERY")) ? System.getenv("FILTER_QUERY") : filterQuery;
        this.providerUrl = Objects.nonNull(System.getenv("PROVIDER_URL")) ? System.getenv("PROVIDER_URL") : providerUrl;
        this.usernameKey = Objects.nonNull(System.getenv("USERNAME_KEY")) ? System.getenv("USERNAME_KEY") : usernameKey;
        this.nameKey = Objects.nonNull(System.getenv("NAME_KEY")) ? System.getenv("NAME_KEY") : nameKey;
        this.surnameKey = Objects.nonNull(System.getenv("SURNAME_KEY")) ? System.getenv("SURNAME_KEY") : surnameKey;
        this.mailKey = Objects.nonNull(System.getenv("MAIL_KEY")) ? System.getenv("MAIL_KEY") : mailKey;
        this.mobileKey = Objects.nonNull(System.getenv("MOBILE_KEY")) ? System.getenv("MOBILE_KEY") : mobileKey;
        this.addressKey = Objects.nonNull(System.getenv("ADDRESS_KEY")) ? System.getenv("ADDRESS_KEY") : addressKey;
        this.titleKey = Objects.nonNull(System.getenv("TITLE_KEY")) ? System.getenv("TITLE_KEY") : titleKey;
        this.pidKey = Objects.nonNull(System.getenv("PID_KEY")) ? System.getenv("PID_KEY") : pidKey;
        this.searchControls = new SearchControls();
        this.searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        String[] returningAttr = {
            this.usernameKey,
            this.nameKey,
            this.surnameKey,
            this.mailKey,
            this.mobileKey,
            this.addressKey,
            this.titleKey,
            this.pidKey
        };
        this.searchControls.setReturningAttributes(returningAttr);
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
        return getActiveDirectoryUserData(getLdapContext(activeDirectoryLoginRequest), activeDirectoryLoginRequest);
    }

    /**
     * Active Directory'de yer alan kullanıcıların listesini döner.
     *
     * @return : {@link List<Attributes>}
     */
    @Override
    public List<ActiveDirectoryUser> getAllUsersWithAdminContext(ActiveDirectoryLoginRequest activeDirectoryLoginRequest) {
        checkActiveDirectoryLoginRequest(activeDirectoryLoginRequest);
        try {
            NamingEnumeration<SearchResult> answers = getLdapContext(activeDirectoryLoginRequest).search(
                this.organizationUnit + this.domainControllers,
                this.filterQuery,
                this.searchControls
            );

            List<Attributes> listOfUsers = new ArrayList<>();
            while (answers.hasMoreElements()) {
                listOfUsers.add(answers.next().getAttributes());
            }

            List<ActiveDirectoryUser> listOfActiveDirectoryUserData = new ArrayList<>();
            for (Attributes attributes : listOfUsers) {
                if (attributes.size() > 0) {
                    ActiveDirectoryUser activeDirectoryUser = new ActiveDirectoryUser();

                    setActiveDirectoryUser(attributes, activeDirectoryUser);

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

    /**
     * {@link ActiveDirectoryLoginRequest} ile gelen verilerin boş olup olmadığını kontrol eder.
     *
     * @param activeDirectoryLoginRequest : {@link ActiveDirectoryLoginRequest}
     */
    private void checkActiveDirectoryLoginRequest(ActiveDirectoryLoginRequest activeDirectoryLoginRequest) {
        // Boş kullanıcı ismi ve şifre ile kimlik doğrulaması yapılabildiğinden, engellenmesi amacıyla kontrol edilmektedir.
        if (activeDirectoryLoginRequest.getUsername().equals("") || activeDirectoryLoginRequest.getPassword().equals("")) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_EMPTY_INPUT.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_EMPTY_INPUT.getLanguageValue()
            );
        }
    }

    /**
     * Konfigüre edilebilir attributeları kullanarak {@link SearchControls} döner.
     *
     * @param  attributeArray : {@code attributes}
     * @return                : {@link SearchControls}
     */
    private SearchControls setSearchControls(String[] attributeArray) {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setReturningAttributes(attributeArray);
        return searchControls;
    }

    /**
     * Kullanıcı'ya ait içerik ve şifre bilgisine göre {@link LdapContext} objesi döner.
     *
     * @param  activeDirectoryLoginRequest : {@link ActiveDirectoryLoginRequest}
     * @return                             : {@link LdapContext}
     */
    private LdapContext getLdapContext(ActiveDirectoryLoginRequest activeDirectoryLoginRequest) {
        Hashtable<String, String> authEnv = new Hashtable<>();
        authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        authEnv.put(Context.PROVIDER_URL, this.providerUrl);
        authEnv.put(Context.SECURITY_AUTHENTICATION, this.securityAuthentication);
        authEnv.put(Context.SECURITY_PRINCIPAL, activeDirectoryLoginRequest.getUsername() + this.principal);
        authEnv.put(Context.SECURITY_CREDENTIALS, activeDirectoryLoginRequest.getPassword());

        try {
            return new InitialLdapContext(authEnv, null);
        } catch (AuthenticationException authenticationException) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_FAILED.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_FAILED.getLanguageValue()
            );
        } catch (NamingException namingException) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONFIGURATION_ERROR.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONFIGURATION_ERROR.getLanguageValue()
            );
        } catch (IllegalStateException illegalStateException) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONNECTION_TIME_OUT.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONNECTION_TIME_OUT.getLanguageValue()
            );
        }
    }

    /**
     * Kullanıcının Active Directory'de yer alan verisini döner.
     *
     * @param  ldapContext                 : {@link LdapContext}
     * @param  activeDirectoryLoginRequest : {@link ActiveDirectoryLoginRequest}
     * @return                             : {@code activeDirectoryUserData}
     */
    private ActiveDirectoryUser getActiveDirectoryUserData(LdapContext ldapContext, ActiveDirectoryLoginRequest activeDirectoryLoginRequest) {
        try {
            NamingEnumeration<SearchResult> searchResultNamingEnumeration = ldapContext.search(
                this.domainControllers,
                this.usernameKey + "=" + activeDirectoryLoginRequest.getUsername(),
                this.searchControls
            );

            // JSONObject activeDirectoryUserData = new JSONObject();
            ActiveDirectoryUser activeDirectoryUser = new ActiveDirectoryUser();
            if (searchResultNamingEnumeration.hasMore()) {
                Attributes attributes = searchResultNamingEnumeration.next().getAttributes();
                setActiveDirectoryUser(attributes, activeDirectoryUser);
            }
            return activeDirectoryUser;
            // return activeDirectoryUserData.toMap();
        } catch (NamingException exception) {
            throw new ServiceException(
                EnumExceptionMessages.ACTIVE_DIRECTORY_ERROR_WHILE_SEARCHING.getLanguageKey(),
                EnumExceptionMessages.ACTIVE_DIRECTORY_ERROR_WHILE_SEARCHING.getLanguageValue()
            );
        }
    }

    private void setActiveDirectoryUser(Attributes attributes, ActiveDirectoryUser activeDirectoryUser) {

        activeDirectoryUser.setUsername(parser(String.valueOf(attributes.get(usernameKey))));
        activeDirectoryUser.setName(parser(String.valueOf(attributes.get(nameKey))));
        activeDirectoryUser.setSurname(parser(String.valueOf(attributes.get(surnameKey))));
        activeDirectoryUser.setEposta(parser(String.valueOf(attributes.get(mailKey))));
        activeDirectoryUser.setMobilePhone(parser(String.valueOf(attributes.get(mobileKey))));
        activeDirectoryUser.setAddress(parser(String.valueOf(attributes.get(addressKey))));
        activeDirectoryUser.setTitle(parser(String.valueOf(attributes.get(titleKey))));
        activeDirectoryUser.setEmployeeNo(parser(String.valueOf(attributes.get(pidKey))));

    }

    private String parser(String str) {

        if (Objects.nonNull(str) && str != "null") {
            return String.join("", (str.split(": ")[1]));
        }
        return null;
    }

}
