/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActiveDirectoryLoginRequest {
    private UUID customerId;
    private String username;
    private String password;

    public ActiveDirectoryLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
