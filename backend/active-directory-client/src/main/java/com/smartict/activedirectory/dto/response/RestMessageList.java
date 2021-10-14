/* SmartICT Bilisim A.S. (C) 2021 */
package com.smartict.activedirectory.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestMessageList {
    List<RestDetailedMessage> exceptionList = new ArrayList<>();
}
