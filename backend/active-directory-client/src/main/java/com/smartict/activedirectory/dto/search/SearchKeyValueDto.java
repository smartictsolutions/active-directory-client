/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchKeyValueDto {
    UUID customerId;
    String key;
    String value;
}
