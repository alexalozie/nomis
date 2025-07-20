package org.nomisng.service;


import org.nomisng.domain.dto.FlagDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.entity.Flag;
import org.nomisng.domain.entity.Form;
import org.nomisng.domain.entity.FormFlag;

import java.util.List;


public interface FlagService {

    Flag saveFlag(FlagDTO flagDTO) ;

    Flag updateFlag(Long id, FlagDTO flagDTO) ;

    void checkForAndSaveMemberFlag(Long householdId, Long householdMemberId, Object object, List<FormFlag> formFlags);

    //Flag operation
    List<Form> applyingFormsToBeneficiariesFlags(HouseholdMemberDTO householdMemberDTO, Form form, List<Form> forms) ;
    Integer getAge(Object object);

}
