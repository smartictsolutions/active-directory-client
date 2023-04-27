/* SmartICT Bilisim A.S. (C) 2020 */
package com.smartict.activedirectory.dto.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Hata mesajları için gerekli verilerin tutulduğu sınıftır.
 */
@Getter
@Setter
public class ExceptionHandlerDto {
    private String message;
    private Boolean warning = false;
    private String localizedMessage;
}
