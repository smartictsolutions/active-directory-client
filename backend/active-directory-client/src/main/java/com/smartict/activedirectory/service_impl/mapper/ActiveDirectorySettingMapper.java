/* SmartICT Bilisim A.S. (C) 2023 */
package com.smartict.activedirectory.service_impl.mapper;

import com.smartict.activedirectory.db.ActiveDirectorySetting;
import com.smartict.activedirectory.dto.active_directory.ActiveDirectorySettingDto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ActiveDirectorySettingMapper extends BaseMapper<ActiveDirectorySetting, ActiveDirectorySettingDto> {
    ActiveDirectorySettingMapper INSTANCE = Mappers.getMapper(ActiveDirectorySettingMapper.class);
}
