/* SmartICT Bilisim A.S. (C) 2023 */
package com.smartict.activedirectory.db;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "active_directory_setting")
public class ActiveDirectorySetting {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "principal")
    private String principal;

    @Column(name = "domain_controllers")
    private String domainControllers;

    @Column(name = "organization_unit")
    private String organizationUnit;

    @Column(name = "security_authentication")
    private String securityAuthentication;

    @Column(name = "provider_url")
    private String providerUrl;

    @Column(name = "user_name_key")
    private String usernameKey;

    @Column(name = "name_key")
    private String nameKey;

    @Column(name = "surname_key")
    private String surnameKey;

    @Column(name = "mail_key")
    private String mailKey;

    @Column(name = "mobile_key")
    private String mobileKey;

    @Column(name = "address_key")
    private String addressKey;

    @Column(name = "title_key")
    private String titleKey;

    @Column(name = "pid_key")
    private String pidKey;

    @Column(name = "super_user_username")
    private String superUserUsername;

    @Column(name = "super_user_password")
    private String superUserPassword;

    @Column(name = "session_timeout")
    private Integer sessionTimeout;

    public ActiveDirectorySetting() {}

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
