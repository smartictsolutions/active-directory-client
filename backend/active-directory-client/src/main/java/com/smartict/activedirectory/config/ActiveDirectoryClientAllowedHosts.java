/* SmartICT Bilisim A.S. (C) 2020 */
package com.smartict.activedirectory.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "active-directory.security.cors")
public class ActiveDirectoryClientAllowedHosts {

    private List<String> allowedHosts;

    public List<String> getAllowedHosts() {
        return allowedHosts;
    }

    public void setAllowedHosts(List<String> allowedHosts) {
        this.allowedHosts = allowedHosts;
    }
}
