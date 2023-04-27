/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.dto.user;

import javax.naming.ldap.LdapContext;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {
    private String username;
    private Long date;
    private LdapContext ldapContext;

}
