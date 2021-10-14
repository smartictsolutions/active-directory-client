/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.constant.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum EnumExceptionMessages {

    UPLOAD_REQUEST_BODY_INVALID("ExceptionMessages.UPLOAD_REQUEST_BODY_INVALID", "Gönderilen veriler geçersizdir!"),

    UNEXPECTED_ERROR_OCCURRED("ExceptionMessages.UNEXPECTED_ERROR_OCCURRED", "Beklenmeyen hata oluştu!"),

    ACTIVE_DIRECTORY_CONTEXT_EXIT_FAILED("EnumExceptionMessages.ACTIVE_DIRECTORY_CONTEXT_EXIT_FAILED", "Close işleminde hata gerçekleşti!"),

    ACTIVE_DIRECTORY_AUTH_FAILED("ExceptionMessages.ACTIVE_DIRECTORY_AUTH_FAILED", "Kimlik doğrulama aşamasında hata oluştu!"),
    ACTIVE_DIRECTORY_AUTH_CONNECTION_FAILED("ExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONNECTION_FAILED", "Sunucuya bağlanılırken hata oluştu!"),
    ACTIVE_DIRECTORY_AUTH_CONFIGURATION_ERROR("ExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONFIGURATION_ERROR", "Konfigürasyonlar hatalı!"),
    ACTIVE_DIRECTORY_AUTH_CONNECTION_TIME_OUT(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONNECTION_TIME_OUT", "Sunucuyla olan bağlantı zaman aşımına uğradı!"
    ),

    ACTIVE_DIRECTORY_ERROR_WHILE_SEARCHING("EnumExceptionMessages.ACTIVE_DIRECTORY_ERROR_WHILE_SEARCHING", "Search işlemi sırasında hata meydana geldi!"),
    ACTIVE_DIRECTORY_ERROR_WHILE_SEARCH_PAGE_SET(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_ERROR_WHILE_SEARCH_PAGE_SET", "Search işlem için yapılan sayfalandırmada hata meydana geldi!"
    ),

    ACTIVE_DIRECTORY_AUTH_EMPTY_INPUT("EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_EMPTY_INPUT", "Kullanıcı adı veya Şifre girdileri boş olarak gönderilemez!"),

    ACTIVE_DIRECTORY_AUTH_EMPTY_USERNAME("EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_EMPTY_USERNAME", "Kullanıcı adı boş olamaz!"),

    ACTIVE_DIRECTORY_AUTH_INVALID_ATTRIBUTE_ID_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_INVALID_ATTRIBUTE_ID_ERROR", "Kullanıcıda olmayan bir Attribute Id ile işlem yapılamaz!"
    );

    private final String languageValue;
    private final String languageKey;

    EnumExceptionMessages(String languageKey, String languageValue) {
        this.languageKey = languageKey;
        this.languageValue = languageValue;
    }

    @JsonCreator
    public static EnumExceptionMessages valueOfLanguageValue(String languageValue) {
        for (EnumExceptionMessages deger : values()) {
            if (deger.languageValue.equalsIgnoreCase(languageValue)) {
                return deger;
            }
        }
        return null;
    }

    @JsonCreator
    public static EnumExceptionMessages valueOfLanguageKey(String languageKey) {
        for (EnumExceptionMessages deger : values()) {
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
