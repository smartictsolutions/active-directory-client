/* SmartICT Bilisim A.S. (C) 2023 */
package com.smartict.activedirectory.db.dao;

import java.util.UUID;

import com.smartict.activedirectory.db.ActiveDirectorySetting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveDirectorySettingRepository extends JpaRepository<ActiveDirectorySetting, UUID> {
    ActiveDirectorySetting readByCustomerId(UUID customerId);
}
