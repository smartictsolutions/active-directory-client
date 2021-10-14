/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveDirectoryUser {
    private String username;
    // @JsonSetter(#(@Value("${spring.active-directory.name-key}")))
    private String name;
    private String surname;
    private String eposta;
    private String mobilePhone;
    private String address;
    private String title;
    private String employeeNo;

}
