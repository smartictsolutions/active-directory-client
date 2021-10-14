/* SmartICT Bilisim A.S. (C) 2020 */
package com.smartict.activedirectory.constant.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum EnumSuccessMessages {
    ACTIVE_DIRECTORY_AUTH_SUCCESS("EnumSuccessMessages.ACTIVE_DIRECTORY_AUTH_SUCCESS", "Kimlik doğrulama işlemi başarılı");

    private final String languageValue;
    private final String languageKey;

    EnumSuccessMessages(String languageValue, String languageKey) {
        this.languageValue = languageValue;
        this.languageKey = languageKey;
    }

    @JsonCreator
    public static EnumSuccessMessages valueOfLanguageValue(String languageValue) {
        for (EnumSuccessMessages deger : values()) {
            if (deger.languageValue.equalsIgnoreCase(languageValue)) {
                return deger;
            }
        }
        return null;
    }

    @JsonCreator
    public static EnumSuccessMessages valueOfLanguageKey(String languageKey) {
        for (EnumSuccessMessages deger : values()) {
            if (deger.languageKey.equalsIgnoreCase(languageKey)) {
                return deger;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "EnumSuccessMessages {languageValue=" + languageValue + ", languageKey=" + languageKey + "}";
    }

}
