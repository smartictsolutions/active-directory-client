/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestDetailedMessage {
    private String messageLanguageKey;
    private String messageLanguageValue;
    private String additionalValue;
    private String lineSeparator = ",";

    public RestDetailedMessage(String messageLanguageKey, String messageLanguageValue) {
        this.messageLanguageKey = messageLanguageKey;
        this.messageLanguageValue = messageLanguageValue;
        this.additionalValue = "";
    }
}
