package org.nomisng.service;

import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.dto.HouseholdMigrationDTO;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdMember;
import org.nomisng.domain.entity.HouseholdMigration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface HouseholdService {
    Boolean isUniqueIdAvailable(String uniqueId);

    Page<Household> getAllHouseholdsByPage(String search, Pageable pageable);

    List<HouseholdDTO> getAllHouseholdsFromPage(Page<Household> householdPage);

    Optional<Household> findByUniqueId(String uniqueId);

    Household saveHousehold(HouseholdDTO householdDTO);
    HouseholdDTO getHouseholdById(Long id);

    List<EncounterDTO> getEncounterByHouseholdId(Long householdId);

    Household updateHousehold(Long id, HouseholdDTO householdDTO);

    void deleteHousehold(Long id);
    List<HouseholdMemberDTO> getHouseholdMembersByHouseholdId(Long id, Integer memberType);

    List<HouseholdMigrationDTO> getHouseholdAddressesByHouseholdId(Long id);
    List<HouseholdMigrationDTO> saveHouseholdMigration(Long id, HouseholdMigration householdMigration);

    Long getMaxHouseholdIdByWardId(Long wardId);

    List<Household> getAllHouseholdAboutToGraduate();
    List<Household> getHouseholdAboutToGraduate(Long householdId);

    List<HouseholdMember> getHouseholdMembersAboutToGraduate(Long householdId);
    Optional<HouseholdMigration> getMigratedStatus(Long householdId);

    void migrateHouseholdMembers(Long householdId, HouseholdMigration householdMigration);

    List<Map<String, Object>> getServiceDueDate(Long householdId);

    HouseholdMember convertOvcToCaregiver(Long householdId, Long householdMemberId);

    Optional<Household> reAssignPrimaryCaregiver(Long householdId, Long caregiverId);

    List<HouseholdMember> getHouseholdCaregivers(Long householdId);
    Map<String, Object> getCareSupportChecklistByHouseholdId(Long householdId);


    boolean checkCareplanService(String service, String domain,
                                        Long householdId, Long householdMemberId);
    Long getTotalHousehold(Long cboProjectId) ;
    Long getTotalGraduated(Long cboProjectId);


}
