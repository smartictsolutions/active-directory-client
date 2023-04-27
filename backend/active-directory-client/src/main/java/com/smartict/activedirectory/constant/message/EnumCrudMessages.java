/* SmartICT Bilisim A.S. (C) 2020 */
package com.smartict.activedirectory.constant.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

/**
 * Crud mesajlarını ifade eder.
 *
 * @author sedat.durmus
 */
@Getter
public enum EnumCrudMessages {
    OBJECT_NOT_FOUND("EnumCrudMessages.OBJECT_NOT_FOUND", "Kayıt yapmak istediğiniz nesne boş olamaz."),
    DB_OBJECT_NOT_FOUND("EnumCrudMessages.DB_OBJECT_NOT_FOUND", "Veritabanında kayıt bulunamadı veya veritabanından id boş geldi!"),
    ID_MUST_NOT_BE_NULL("EnumCrudMessages.ID_MUST_NOT_BE_NULL", "İşlem yapmak istediğiniz ID boş olamaz."),

    ACTIVE_DIRECTORY_AUTH_TITLE("EnumCrudMessages.ACTIVE_DIRECTORY_AUTH_TITLE", "Kimlik doğrulama işlemi"),

    CREATE_TITLE("EnumCrudMessages.CREATE_TITLE", "Kayıt oluşturma işlemi"),
    READ_TITLE("EnumCrudMessages.READ_TITLE", "Kayıt getirme işlemi"),
    UPDATE_TITLE("EnumCrudMessages.UPDATE_TITLE", "Kayıt güncelleme işlemi"),
    DELETE_TITLE("EnumCrudMessages.DELETE_TITLE", "Kayıt silme işlemi"),
    LIST_READ_TITLE("EnumCrudMessages.LIST_READ_TITLE", "Kayıt listesi getirme işlemi"),

    CREATE_SUCCESS_MESSAGE("EnumCrudMessages.CREATE_SUCCESS_MESSAGE", "Kayıt oluşturma işlemi başarılı bir şekilde gerçekleşti."),
    READ_SUCCESS_MESSAGE("EnumCrudMessages.READ_SUCCESS_MESSAGE", "Kayıt getirme işlemi başarılı bir şekilde gerçekleşti."),
    LIST_READ_SUCCESS_MESSAGE("EnumCrudMessages.LIST_READ_SUCCESS_MESSAGE", "Kayıt listesi getirme işlemi başarılı bir şekilde gerçekleşti."),
    UPDATE_SUCCESS_MESSAGE("EnumCrudMessages.UPDATE_SUCCESS_MESSAGE", "Kayıt güncelleme işlemi başarılı bir şekilde gerçekleşti."),
    DELETE_SUCCESS_MESSAGE("EnumCrudMessages.DELETE_SUCCESS_MESSAGE", "Kayıt silme işlemi başarılı bir şekilde gerçekleşti.");

    private final String languageKey;
    private final String languageValue;

    EnumCrudMessages(String languageKey, String languageValue) {
        this.languageKey = languageKey;
        this.languageValue = languageValue;
    }

    @JsonCreator
    public static EnumCrudMessages valueOfLanguageValue(String languageValue) {
        for (EnumCrudMessages deger : values()) {
            if (deger.languageValue.equalsIgnoreCase(languageValue)) {
                return deger;
            }
        }
        return null;
    }

    @JsonCreator
    public static EnumCrudMessages valueOfLanguageKey(String languageKey) {
        for (EnumCrudMessages deger : values()) {
            if (deger.languageKey.equalsIgnoreCase(languageKey)) {
                return deger;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "EnumExceptionMessages {languageValue=" + languageValue + ", languageKey=" + languageKey + "}";
    }
}
