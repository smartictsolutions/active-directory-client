/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.service;

import java.util.List;
import java.util.UUID;

import com.smartict.activedirectory.dto.active_directory.ActiveDirectorySettingDto;
import com.smartict.activedirectory.dto.login.ActiveDirectoryLoginRequest;
import com.smartict.activedirectory.dto.search.SearchKeyValueDto;
import com.smartict.activedirectory.dto.user.ActiveDirectoryUser;

public interface ActiveDirectoryService {
    ActiveDirectoryUser getAuthenticatedActiveDirectoryUser(ActiveDirectoryLoginRequest activeDirectoryLoginRequest);

    List<ActiveDirectoryUser> getAllUsers(UUID customerId, String ldapQueryKey);

    List<ActiveDirectoryUser> searchUsersContainsWithKeyValue(SearchKeyValueDto searchKeyValueDto);

    ActiveDirectorySettingDto readByCustomerId(UUID customerId);

    ActiveDirectorySettingDto setActiveDirectorySetting(ActiveDirectorySettingDto activeDirectorySettingDto);

    ActiveDirectorySettingDto deleteByCustomerId(UUID customerId);
}
