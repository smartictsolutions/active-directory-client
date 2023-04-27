/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.constant.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum EnumExceptionMessages {

    UPLOAD_REQUEST_BODY_INVALID("ExceptionMessages.UPLOAD_REQUEST_BODY_INVALID", "Gönderilen veriler geçersizdir!"),

    UNEXPECTED_ERROR_OCCURRED("ExceptionMessages.UNEXPECTED_ERROR_OCCURRED", "Beklenmeyen hata oluştu!"),

    ACTIVE_DIRECTORY_CONTEXT_EXIT_FAILED("EnumExceptionMessages.ACTIVE_DIRECTORY_CONTEXT_EXIT_FAILED", "Close işleminde hata gerçekleşti!"),

    ACTIVE_DIRECTORY_AUTH_FAILED("ExceptionMessages.ACTIVE_DIRECTORY_AUTH_FAILED", "Kimlik doğrulama aşamasında hata oluştu! Kullanıcı adı veya şifre yanlış!"),
    ACTIVE_DIRECTORY_AUTH_CONNECTION_FAILED("ExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONNECTION_FAILED", "Sunucuya bağlanılırken hata oluştu!"),
    ACTIVE_DIRECTORY_AUTH_CONFIGURATION_ERROR("ExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONFIGURATION_ERROR", "Konfigürasyonlar hatalı!"),
    ACTIVE_DIRECTORY_AUTH_CONNECTION_TIME_OUT(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_CONNECTION_TIME_OUT", "Sunucuyla olan bağlantı zaman aşımına uğradı!"
    ),
    ACTIVE_DIRECTORY_USER_INFO_NOT_RECEIVED(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_USER_INFO_NOT_RECEIVED",
            "Aktif Directory kullanıcı bilgileleri alınamadı! \n Organizational uniti ve Kullanıcı yetkilerini kontrol ediniz !"
    ),

    ACTIVE_DIRECTORY_ERROR_WHILE_SEARCHING("EnumExceptionMessages.ACTIVE_DIRECTORY_ERROR_WHILE_SEARCHING", "Search işlemi sırasında hata meydana geldi!"),
    ACTIVE_DIRECTORY_ERROR_WHILE_SEARCH_PAGE_SET(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_ERROR_WHILE_SEARCH_PAGE_SET", "Search işlem için yapılan sayfalandırmada hata meydana geldi!"
    ),

    ACTIVE_DIRECTORY_AUTH_EMPTY_INPUT("EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_EMPTY_INPUT", "Kullanıcı adı veya Şifre girdileri boş olarak gönderilemez!"),

    ACTIVE_DIRECTORY_AUTH_NULL("EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_NULL", "Kullanıcı adı ve şifre bilgisi alınamadı "),
    ACTIVE_DIRECTORY_AUTH_EMPTY_USERNAME("EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_EMPTY_USERNAME", "Kullanıcı adı boş olamaz!"),

    ACTIVE_DIRECTORY_AUTH_INVALID_ATTRIBUTE_ID_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_AUTH_INVALID_ATTRIBUTE_ID_ERROR", "Kullanıcıda olmayan bir Attribute Id ile işlem yapılamaz!"
    ),

    ACTIVE_DIRECTORY_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_SETTING_NOT_FOUND_ERROR", "Kuruma ait aktif directory ayarları veri tabanı bulunamadı!"
    ),

    ACTIVE_DIRECTORY_SUPER_USER_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_SUPER_USER_SETTING_NOT_FOUND_ERROR",
            "Kuruma ait aktif directory ayarlarında SUPER USER ayarları bulunamadı!"
    ),
    ACTIVE_DIRECTORY_ORGANIZATION_UNIT_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_ORGANIZATION_UNIT_SETTING_NOT_FOUND_ERROR",
            "Kuruma ait aktif directory ayarlarında ORGANIZATION UNIT ayarları bulunamadı!"
    ),
    ACTIVE_DIRECTORY_DOMAIN_CONTROLLERS_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_DOMAIN_CONTROLLERS_SETTING_NOT_FOUND_ERROR",
            "Kuruma ait aktif directory ayarlarında DOMAIN CONTROLLERS ayarları bulunamadı!"
    ),
    ACTIVE_DIRECTORY_USERNAME_KEY_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_USERNAME_KEY_SETTING_NOT_FOUND_ERROR",
            "Kuruma ait aktif directory ayarlarında USER NAME KEY ayarları bulunamadı!"
    ),
    ACTIVE_DIRECTORY_NAME_KEY_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_NAME_KEY_SETTING_NOT_FOUND_ERROR", "Kuruma ait aktif directory ayarlarında NAME KEY ayarları bulunamadı!"
    ),
    ACTIVE_DIRECTORY_SURNAME_KEY_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_SURNAME_KEY_SETTING_NOT_FOUND_ERROR",
            "Kuruma ait aktif directory ayarlarında SURNAME KEY ayarları bulunamadı!"
    ),
    ACTIVE_DIRECTORY_MAIL_KEY_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_MAIL_KEY_SETTING_NOT_FOUND_ERROR", "Kuruma ait aktif directory ayarlarında MAIL KEY ayarları bulunamadı!"
    ),
    ACTIVE_DIRECTORY_MOBILE_KEY_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_MOBILE_KEY_SETTING_NOT_FOUND_ERROR",
            "Kuruma ait aktif directory ayarlarında MOBILE KEY ayarları bulunamadı!"
    ),
    ACTIVE_DIRECTORY_ADDRESS_KEY_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_ADDRESS_KEY_SETTING_NOT_FOUND_ERROR",
            "Kuruma ait aktif directory ayarlarında ADDRESS KEY ayarları bulunamadı!"
    ),
    ACTIVE_DIRECTORY_TITLE_KEY_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_TITLE_KEY_SETTING_NOT_FOUND_ERROR", "Kuruma ait aktif directory ayarlarında TITLE KEY ayarları bulunamadı!"
    ),
    ACTIVE_DIRECTORY_PID_KEY_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_PID_KEY_SETTING_NOT_FOUND_ERROR", "Kuruma ait aktif directory ayarlarında PID KEY ayarları bulunamadı!"
    ),
    ACTIVE_DIRECTORY_PROVIDER_URL_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_PROVIDER_URL_SETTING_NOT_FOUND_ERROR",
            "Kuruma ait aktif directory ayarlarında PROVIDER URL ayarları bulunamadı!"
    ),
    ACTIVE_DIRECTORY_SECURITY_AUTHENTICATION_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_SECURITY_AUTHENTICATION_SETTING_NOT_FOUND_ERROR",
            "Kuruma ait aktif directory ayarlarında SECURITY_AUTHENTICATION ayarları bulunamadı!"
    ),
    ACTIVE_DIRECTORY_PRINCIPAL_SETTING_NOT_FOUND_ERROR(
            "EnumExceptionMessages.ACTIVE_DIRECTORY_PRINCIPAL_SETTING_NOT_FOUND_ERROR", "Kuruma ait aktif directory ayarlarında PRINCIPAL ayarları bulunamadı!"
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
