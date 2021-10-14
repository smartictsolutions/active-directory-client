/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.constant.enums;

import lombok.Getter;

@Getter
public enum EnumSearchType {
    Self_Search("EnumSearchType.Self_Search", 0),
    Admin_Search("EnumSearchType.Admin_Search", 1);

    private String key;

    private int sType;

    EnumSearchType(String key, int sType) {
        this.key = key;
        this.sType = sType;
    }

}
