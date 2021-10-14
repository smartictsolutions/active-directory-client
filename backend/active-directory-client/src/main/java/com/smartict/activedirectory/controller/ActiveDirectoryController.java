/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.controller;

import java.util.List;

import com.smartict.activedirectory.constant.exception.ServiceException;
import com.smartict.activedirectory.constant.message.EnumCrudMessages;
import com.smartict.activedirectory.constant.message.EnumSuccessMessages;
import com.smartict.activedirectory.dto.login.ActiveDirectoryLoginRequest;
import com.smartict.activedirectory.dto.response.ResponseTypeEnum;
import com.smartict.activedirectory.dto.response.RestResponse;
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

    @PostMapping("/authentication")
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

    @PostMapping("/getAllUsers")
    public ResponseEntity<RestResponse<List<ActiveDirectoryUser>>> getAllUsers(@RequestBody ActiveDirectoryLoginRequest activeDirectoryLoginRequest) {
        try {
            return new ResponseEntity<>(
                new RestResponse<>(
                    activeDirectoryService.getAllUsersWithAdminContext(activeDirectoryLoginRequest),
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
}
