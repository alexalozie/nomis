package org.nomisng.service;

import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.dto.FormDataDTO;
import org.nomisng.domain.entity.Encounter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface EncounterService {

    List<EncounterDTO> getAllEncounters();
    EncounterDTO getEncounterById(Long id);

    Encounter update(Long id, EncounterDTO encounterDTO);

    Encounter save(EncounterDTO encounterDTO);

    void delete(Long id);

    List<FormDataDTO> getFormDataByEncounterId(Long encounterId);

    Page<Encounter> getEncountersByHouseholdMemberIdAndFormCode(Long householdMemberId, String formCode, Pageable pageable);

    Page<Encounter> getEncountersByHouseholdIdAndFormCode(Long householdId, String formCode, Pageable pageable);

    List<EncounterDTO> getEncounterDTOFromPage(Page<Encounter> encounterPage);

    List<FormDataDTO> getFormDataDTOFromPage(Page<Encounter> encounterPage);

    Page<Encounter> getEncounterByHouseholdIdAndFormCodeAndDateEncounter(Long householdId, String formCode,
                                                                                String dateFrom, String dateTo, Pageable pageable);

    Page<Encounter> getEncountersByHouseholdMemberIdAndFormCodeAndDateEncounter(Long householdMemberId, String formCode,
                                                                                       String dateFrom, String dateTo, Pageable pageable);

    Encounter addFirstNameAndLastNameAndFormNameToEncounter(Encounter encounter);
}
