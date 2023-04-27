/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.controller;

import java.util.List;
import java.util.UUID;

import com.smartict.activedirectory.constant.exception.ServiceException;
import com.smartict.activedirectory.constant.message.EnumCrudMessages;
import com.smartict.activedirectory.constant.message.EnumSuccessMessages;
import com.smartict.activedirectory.dto.active_directory.ActiveDirectorySettingDto;
import com.smartict.activedirectory.dto.login.ActiveDirectoryLoginRequest;
import com.smartict.activedirectory.dto.response.ResponseTypeEnum;
import com.smartict.activedirectory.dto.response.RestResponse;
import com.smartict.activedirectory.dto.search.SearchKeyValueDto;
import com.smartict.activedirectory.dto.user.ActiveDirectoryUser;
import com.smartict.activedirectory.service.ActiveDirectoryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("activedirectory")
public class ActiveDirectoryController {

    private final ActiveDirectoryService activeDirectoryService;

    public ActiveDirectoryController(ActiveDirectoryService activeDirectoryService) {
        this.activeDirectoryService = activeDirectoryService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<RestResponse<ActiveDirectoryUser>> activeDirectoryAuthentication(
        @RequestBody ActiveDirectoryLoginRequest activeDirectoryLoginRequest
    ) {
        try {
            return new ResponseEntity<>(
                new RestResponse<>(
                    activeDirectoryService.getAuthenticatedActiveDirectoryUser(activeDirectoryLoginRequest),
                    EnumCrudMessages.ACTIVE_DIRECTORY_AUTH_TITLE.getLanguageKey(),
                    EnumCrudMessages.ACTIVE_DIRECTORY_AUTH_TITLE.getLanguageValue(),
                    EnumSuccessMessages.ACTIVE_DIRECTORY_AUTH_SUCCESS.getLanguageKey(),
                    EnumSuccessMessages.ACTIVE_DIRECTORY_AUTH_SUCCESS.getLanguageValue(),
                    ResponseTypeEnum.Success
                ),
                HttpStatus.OK
            );
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                new RestResponse<>(
                    EnumCrudMessages.ACTIVE_DIRECTORY_AUTH_TITLE.getLanguageKey(),
                    EnumCrudMessages.ACTIVE_DIRECTORY_AUTH_TITLE.getLanguageValue(),
                    e.getMessageLanguageKey(),
                    e.getMessage(),
                    ResponseTypeEnum.Error
                ),
                HttpStatus.NOT_ACCEPTABLE
            );
        }

    }

    @GetMapping("/getAllUsers/{customerId}/{ldapQueryKey}")
    public ResponseEntity<RestResponse<List<ActiveDirectoryUser>>> getAllUsers(@PathVariable String customerId, @PathVariable String ldapQueryKey) {
        try {
            return new ResponseEntity<>(
                new RestResponse<>(
                    activeDirectoryService.getAllUsers(UUID.fromString(customerId), ldapQueryKey),
                    EnumCrudMessages.READ_TITLE.getLanguageKey(),
                    EnumCrudMessages.READ_TITLE.getLanguageValue(),
                    EnumCrudMessages.READ_SUCCESS_MESSAGE.getLanguageKey(),
                    EnumCrudMessages.READ_SUCCESS_MESSAGE.getLanguageValue(),
                    ResponseTypeEnum.Success
                ),
                HttpStatus.OK
            );

        } catch (ServiceException e) {
            return new ResponseEntity<>(
                new RestResponse<>(
                    EnumCrudMessages.READ_TITLE.getLanguageKey(),
                    EnumCrudMessages.READ_TITLE.getLanguageValue(),
                    e.getMessageLanguageKey(),
                    e.getMessage(),
                    ResponseTypeEnum.Error
                ),
                HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

    @PostMapping("/searchUsersContainsWithKeyValue")
    public ResponseEntity<RestResponse<List<ActiveDirectoryUser>>> searchUsersContainsWithKeyValue(@RequestBody SearchKeyValueDto searchKeyValueDto) {
        try {
            return new ResponseEntity<>(
                new RestResponse<>(
                    activeDirectoryService.searchUsersContainsWithKeyValue(searchKeyValueDto),
                    EnumCrudMessages.READ_TITLE.getLanguageKey(),
                    EnumCrudMessages.READ_TITLE.getLanguageValue(),
                    EnumCrudMessages.READ_SUCCESS_MESSAGE.getLanguageKey(),
                    EnumCrudMessages.READ_SUCCESS_MESSAGE.getLanguageValue(),
                    ResponseTypeEnum.Success
                ),
                HttpStatus.OK
            );

        } catch (ServiceException e) {
            return new ResponseEntity<>(
                new RestResponse<>(
                    EnumCrudMessages.READ_TITLE.getLanguageKey(),
                    EnumCrudMessages.READ_TITLE.getLanguageValue(),
                    e.getMessageLanguageKey(),
                    e.getMessage(),
                    ResponseTypeEnum.Error
                ),
                HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

    @PostMapping("setActiveDirectorySetting")
    public ResponseEntity<RestResponse<ActiveDirectorySettingDto>> setActiveDirectorySetting(@RequestBody ActiveDirectorySettingDto activeDirectorySettingDto) {
        try {
            return new ResponseEntity<>(
                new RestResponse<>(
                    activeDirectoryService.setActiveDirectorySetting(activeDirectorySettingDto),
                    EnumCrudMessages.CREATE_TITLE.getLanguageKey(),
                    EnumCrudMessages.CREATE_TITLE.getLanguageValue(),
                    EnumCrudMessages.CREATE_SUCCESS_MESSAGE.getLanguageKey(),
                    EnumCrudMessages.CREATE_SUCCESS_MESSAGE.getLanguageValue(),
                    ResponseTypeEnum.Success
                ),
                HttpStatus.OK
            );

        } catch (ServiceException e) {
            return new ResponseEntity<>(
                new RestResponse<>(
                    EnumCrudMessages.CREATE_TITLE.getLanguageKey(),
                    EnumCrudMessages.CREATE_TITLE.getLanguageValue(),
                    e.getMessageLanguageKey(),
                    e.getMessage(),
                    ResponseTypeEnum.Error
                ),
                HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

    @GetMapping("readActiveDirectorySettingByCustomerId/{customerId}")
    public ResponseEntity<RestResponse<ActiveDirectorySettingDto>> readActiveDirectorySettingByCustomerId(@PathVariable String customerId) {
        try {
            return new ResponseEntity<>(
                new RestResponse<>(
                    activeDirectoryService.readByCustomerId(UUID.fromString(customerId)),
                    EnumCrudMessages.READ_TITLE.getLanguageKey(),
                    EnumCrudMessages.READ_TITLE.getLanguageValue(),
                    EnumCrudMessages.READ_SUCCESS_MESSAGE.getLanguageKey(),
                    EnumCrudMessages.READ_SUCCESS_MESSAGE.getLanguageValue(),
                    ResponseTypeEnum.Success
                ),
                HttpStatus.OK
            );

        } catch (ServiceException e) {
            return new ResponseEntity<>(
                new RestResponse<>(
                    EnumCrudMessages.READ_TITLE.getLanguageKey(),
                    EnumCrudMessages.READ_TITLE.getLanguageValue(),
                    e.getMessageLanguageKey(),
                    e.getMessage(),
                    ResponseTypeEnum.Error
                ),
                HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

    @DeleteMapping("deleteActiveDirectorySettingByCustomerId/{customerId}")
    public ResponseEntity<RestResponse<ActiveDirectorySettingDto>> deleteActiveDirectorySettingByCustomerId(@PathVariable String customerId) {
        try {
            return new ResponseEntity<>(
                new RestResponse<>(
                    activeDirectoryService.deleteByCustomerId(UUID.fromString(customerId)),
                    EnumCrudMessages.DELETE_TITLE.getLanguageKey(),
                    EnumCrudMessages.DELETE_TITLE.getLanguageValue(),
                    EnumCrudMessages.DELETE_SUCCESS_MESSAGE.getLanguageKey(),
                    EnumCrudMessages.DELETE_SUCCESS_MESSAGE.getLanguageValue(),
                    ResponseTypeEnum.Success
                ),
                HttpStatus.OK
            );

        } catch (ServiceException e) {
            return new ResponseEntity<>(
                new RestResponse<>(
                    EnumCrudMessages.DELETE_TITLE.getLanguageKey(),
                    EnumCrudMessages.DELETE_TITLE.getLanguageValue(),
                    e.getMessageLanguageKey(),
                    e.getMessage(),
                    ResponseTypeEnum.Error
                ),
                HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

}
