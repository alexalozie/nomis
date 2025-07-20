package org.nomisng.service;

import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;


public interface HouseholdMemberService {

    String getUniqueId(Household household);
    Page<HouseholdMember> getAllHouseholdMembersPage(String search, Integer memberType, Pageable pageable);

    List<HouseholdMemberDTO> getAllHouseholdMembersFromPage(Page<HouseholdMember> householdMembersPage);

    HouseholdMember addMemberFlag(HouseholdMember householdMember);

    HouseholdMember saveHouseholdMember(HouseholdMemberDTO householdMemberDTO);

    HouseholdMemberDTO getHouseholdMemberById(Long id);

    HouseholdDTO getHouseholdByHouseholdMemberId(Long id);

    List<EncounterDTO> getEncountersByHouseholdMemberId(Long id);

    HouseholdMember updateHouseholdMember(Long id, HouseholdMemberDTO householdMemberDTO);

    void deleteHouseholdMember(Long id);
    List<FormDTO> getFormsByHouseholdMemberById(Long id);

    List<HouseholdMember> getAllMembersAboutToGraduate(Long householdId);
    Boolean getMemberAboutToGraduate(Long id);

    List<HouseholdMember> getHouseholdMemberCaregivers(Long householdId);

    Optional<HouseholdMember> reAssignCaregiver(Long ovcId, Long caregiverId);

    List<Map<String, Long>> getEnrolmentSummary(Long cboProjectId);

    Long getTotalVc(Long cboProjectId);

    Long getTotalPositive(Long cboProjectId);

    Long getTotalLinked(Long cboProjectId);

}
