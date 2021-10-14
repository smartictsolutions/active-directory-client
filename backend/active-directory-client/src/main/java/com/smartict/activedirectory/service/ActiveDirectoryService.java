/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.service;

import java.util.List;

import com.smartict.activedirectory.dto.login.ActiveDirectoryLoginRequest;
import com.smartict.activedirectory.dto.user.ActiveDirectoryUser;

public interface ActiveDirectoryService {
    ActiveDirectoryUser getAuthenticatedActiveDirectoryUser(ActiveDirectoryLoginRequest activeDirectoryLoginRequest);

    List<ActiveDirectoryUser> getAllUsersWithAdminContext(ActiveDirectoryLoginRequest activeDirectoryLoginRequest);
}
