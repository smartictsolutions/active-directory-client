/* SmartICT Bilisim A.S. (C) 2020 */
package com.smartict.activedirectory.constant.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum EnumValidationMessages {

    PLEASE_FILL_ALL_FIELDS("EnumValidationMessages.PLEASE_FILL_ALL_FIELDS", "Lütfen tüm zorunlu alanları doldurun."),
    LOGIN_PASSWORD_NOT_NULL("EnumValidationMessages.LOGIN_PASSWORD_NOT_NULL", "Şifre alanı boş olamaz"),
    LOGIN_USER_NAME_NOT_NULL("EnumValidationMessages.LOGIN_USER_NAME_NOT_NULL", "Kullanıcı Adı alanı boş olamaz"),
    LOGIN_USER_CARD_NO_NOT_NULL("EnumValidationMessages.LOGIN_USER_CARD_NO_NOT_NULL", "Kullanıcı kart no boş olamaz"),
    LOGIN_PASSWORD_WRONG("EnumValidationMessages.LOGIN_PASSWORD_WRONG", "Kullanıcı Adı alanı boş olamaz"),

    LIST_NOT_NULL("EnumValidationMessages.LIST_NOT_NULL", "Liste yok"),
    USER_NOT_NULL("EnumValidationMessages.USER_NOT_NULL", "Kullanıcı yok"),
    USER_ID_NOT_EMPTY("EnumValidationMessages.USER_ID_NOT_EMPTY", "Kullanıcı idsi boş olamaz."),
    USER_ACTIVE_STATUS_EMPTY("EnumValidationMessages.USER_ACTIVE_STATUS_EMPTY", "Aktiflik durumu boş olamaz."),

    AUTHORITY_AUTHORIZE_CODE_NOT_NULL("EnumValidationMessages.AUTHORITY_AUTHORIZE_CODE_NOT_NULL", "Yetki kodu boş olamaz."),
    AUTHORITY_IS_MENU_NOT_NULL("EnumValidationMessages.AUTHORITY_IS_MENU_NOT_NULL", "Yetki menü bilgisi verilmelidir."),
    AUTHORITY_IS_VISIBLE_NOT_NULL("EnumValidationMessages.AUTHORITY_IS_VISIBLE_NOT_NULL", "Yetki görünüm bilgisi verilmelidir."),
    AUTHORITY_NOT_NULL("EnumValidationMessages.AUTHORITY_NOT_NULL", "Yetki güncelleme isteği boş olamaz"),
    AUTHORITY_ID_LIST_NOT_NULL("EnumValidationMessages.AUTHORITY_ID_LIST_NOT_NULL", "Yetki id listesi boş olamaz"),

    USER_ID_NOT_NULL("EnumValidationMessages.USER_ID_NOT_NULL", "Kullanıcı bilgisi boş olamaz."),
    USERNAME_OR_EMAIL_ALREADY_EXIST("EnumValidationMessages.USERNAME_OR_EMAIL_ALREADY_EXIST", "Bu kullanıcı adı veya e-posta ile zaten bir kullanıcı mevcut."),
    USERNAME_OR_EMAIL_NOT_NULL("EnumValidationMessages.USERNAME_OR_EMAIL_NOT_NULL", "Kullanıcı adı ve e-posta alanları boş olamaz."),
    USER_PASSWORD_NOT_NULL("EnumValidationMessages.USER_PASSWORD_NOT_NULL", "Kullanıcı şifresi boş olamaz."),
    USER_SETTINGS_NOT_NULL("EnumValidationMessages.USER_SETTINGS_NOT_NULL", "Kullanıcı ayarları boş olamaz."),
    USER_SUSPENSION_START_DATE_NOT_NULL("EnumValidationMessages.USER_SUSPENSION_START_DATE_NOT_NULL", "Kullanıcı askıya alma başlangıç tarihi boş olamaz."),
    USER_SUSPENSION_DUE_DATE_NOT_NULL("EnumValidationMessages.USER_SUSPENSION_DUE_DATE_NOT_NULL", "Kullanıcı askıya alma bitiş tarihi boş olamaz.");

    private final String languageKey;
    private final String languageValue;

    EnumValidationMessages(String languageKey, String languageValue) {
        this.languageKey = languageKey;
        this.languageValue = languageValue;
    }

    @JsonCreator
    public static EnumValidationMessages valueOfLanguageValue(String languageValue) {
        for (EnumValidationMessages deger : values()) {
            if (deger.languageValue.equalsIgnoreCase(languageValue)) {
                return deger;
            }
        }
        return null;
    }

    @JsonCreator
    public static EnumValidationMessages valueOfLanguageKey(String languageKey) {
        for (EnumValidationMessages deger : values()) {
            if (deger.languageKey.equalsIgnoreCase(languageKey)) {
                return deger;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "EnumValidationMessages {languageValue=" + languageValue + ", languageKey=" + languageKey + "}";
    }
}
