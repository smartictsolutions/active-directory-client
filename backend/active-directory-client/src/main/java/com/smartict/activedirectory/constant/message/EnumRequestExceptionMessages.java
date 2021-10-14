/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.constant.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

/**
 * Gelen istekleri işlerken oluşan hataları frontend'e dönerken mesajın hata dil kodunu göndermek için kullanılır. Frontend gelen dil koduna göre dil
 * dosyasından dil karşılığını alarak ekranda gösterir.
 * 
 * @author sedat.durmus
 */
@Getter
public enum EnumRequestExceptionMessages {
    USER_ID_NOT_FOUND("EnumRequestExceptionMessages.USER_ID_NOT_FOUND", "Belirtilen kullanıcı bulunamadı");

    private final String languageKey;
    private final String languageValue;

    EnumRequestExceptionMessages(String languageKey, String languageValue) {
        this.languageKey = languageKey;
        this.languageValue = languageValue;
    }

    @JsonCreator
    public static EnumRequestExceptionMessages valueOfLanguageValue(String languageValue) {
        for (EnumRequestExceptionMessages deger : values()) {
            if (deger.languageValue.equalsIgnoreCase(languageValue)) {
                return deger;
            }
        }
        return null;
    }

    @JsonCreator
    public static EnumRequestExceptionMessages valueOfLanguageKey(String languageKey) {
        for (EnumRequestExceptionMessages deger : values()) {
            if (deger.languageKey.equalsIgnoreCase(languageKey)) {
                return deger;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "EnumRequestExceptionMessages {languageValue=" + languageValue + ", languageKey=" + languageKey + "}";
    }
}
