/* SmartICT Bilisim A.S. (C) 2023 */
package com.smartict.activedirectory.dto.active_directory;

import java.util.UUID;

public class ActiveDirectorySettingDto {

    private UUID id;

    private UUID customerId;

    private String principal;

    private String domainControllers;

    private String organizationUnit;

    private String securityAuthentication;

    private String providerUrl;

    private String usernameKey;

    private String nameKey;

    private String surnameKey;

    private String mailKey;

    private String mobileKey;

    private String addressKey;

    private String titleKey;

    private String pidKey;

    private String superUserUsername;

    private String superUserPassword;

    private Integer sessionTimeout;

    public ActiveDirectorySettingDto() {

    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setDomainControllers(String domainControllers) {
        this.domainControllers = domainControllers;
    }

    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public void setSecurityAuthentication(String securityAuthentication) {
        this.securityAuthentication = securityAuthentication;
    }

    public void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    public void setUsernameKey(String usernameKey) {
        this.usernameKey = usernameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public void setSurnameKey(String surnameKey) {
        this.surnameKey = surnameKey;
    }

    public void setMailKey(String mailKey) {
        this.mailKey = mailKey;
    }

    public void setMobileKey(String mobileKey) {
        this.mobileKey = mobileKey;
    }

    public void setAddressKey(String addressKey) {
        this.addressKey = addressKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public void setPidKey(String pidKey) {
        this.pidKey = pidKey;
    }

    public void setSuperUserUsername(String superUserUsername) {
        this.superUserUsername = superUserUsername;
    }

    public void setSuperUserPassword(String superUserPassword) {
        this.superUserPassword = superUserPassword;
    }

    public void setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getDomainControllers() {
        return domainControllers;
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public String getSecurityAuthentication() {
        return securityAuthentication;
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public String getUsernameKey() {
        return usernameKey;
    }

    public String getNameKey() {
        return nameKey;
    }

    public String getSurnameKey() {
        return surnameKey;
    }

    public String getMailKey() {
        return mailKey;
    }

    public String getMobileKey() {
        return mobileKey;
    }

    public String getAddressKey() {
        return addressKey;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public String getPidKey() {
        return pidKey;
    }

    public String getSuperUserUsername() {
        return superUserUsername;
    }

    public String getSuperUserPassword() {
        return superUserPassword;
    }

    public Integer getSessionTimeout() {
        return sessionTimeout;
    }
}
