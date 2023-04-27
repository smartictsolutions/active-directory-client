/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.dto.response;

public class RestResponse<T> {
    private T data;
    private String titleLanguageKey;
    private String title;
    private String messageLanguageKey;
    private String message;
    private ResponseTypeEnum type;

    public RestResponse() {}

    public RestResponse(T data, ResponseTypeEnum type) {
        this.data = data;
        this.type = type;
    }

    public RestResponse(String messageLanguageKey, String message, ResponseTypeEnum type) {
        this.title = "";
        this.titleLanguageKey = "";
        this.message = message;
        this.messageLanguageKey = messageLanguageKey;
        this.type = type;
    }

    public RestResponse(String titleLanguageKey, String title, String messageLanguageKey, String message, ResponseTypeEnum type) {
        this.title = title;
        this.titleLanguageKey = titleLanguageKey;
        this.message = message;
        this.messageLanguageKey = messageLanguageKey;
        this.type = type;
    }

    public RestResponse(T data, String titleLanguageKey, String title, String messageLanguageKey, String message, ResponseTypeEnum type) {
        this.data = data;
        this.title = title;
        this.titleLanguageKey = titleLanguageKey;
        this.message = message;
        this.messageLanguageKey = messageLanguageKey;
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleLanguageKey() {
        return titleLanguageKey;
    }

    public void setTitleLanguageKey(String titleLanguageKey) {
        this.titleLanguageKey = titleLanguageKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageLanguageKey() {
        return messageLanguageKey;
    }

    public void setMessageLanguageKey(String messageLanguageKey) {
        this.messageLanguageKey = messageLanguageKey;
    }

    public ResponseTypeEnum getType() {
        return type;
    }

    public void setType(ResponseTypeEnum type) {
        this.type = type;
    }
}
