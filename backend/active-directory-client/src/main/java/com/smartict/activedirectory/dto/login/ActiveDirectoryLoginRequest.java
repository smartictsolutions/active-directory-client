/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.dto.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveDirectoryLoginRequest {
    private String username;
    private String password;
}
