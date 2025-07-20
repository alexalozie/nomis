package org.nomisng.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.FlagDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.entity.Flag;
import org.nomisng.domain.entity.Form;
import org.nomisng.domain.entity.FormFlag;
import org.nomisng.domain.entity.MemberFlag;
import org.nomisng.domain.mapper.FlagMapper;
import org.nomisng.repository.FlagRepository;
import org.nomisng.repository.FormFlagRepository;
import org.nomisng.repository.MemberFlagRepository;
import org.nomisng.service.FlagService;
import org.nomisng.util.FlagOperatorType;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.apierror.RecordExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FlagServiceImpl implements FlagService {
    private static final int STATUS = 1;
    private static final int FORM_LEVEL_FLAG = 2;
    private static final int FORM_DATA_LEVEL_FLAG = 3;
    private static final int SYSTEM_LEVEL_FLAG = 1;
    private static final int STRING_FLAG_DATA_TYPE = 0;
    private static final int APPLICATION_CODESET_FLAG_DATA_TYPE = 1;
    private static final int INTEGER_FLAG_DATA_TYPE = 2;
    private final FlagRepository flagRepository;
    private final FlagMapper flagMapper;
    private final MemberFlagRepository memberFlagRepository;
    private final FormFlagRepository formFlagRepository;
    private final ObjectMapper mapper;

    @Override
    public Flag saveFlag(FlagDTO flagDTO) {
        Optional<Flag> optionalFlag = flagRepository.findByNameAndFieldNameAndFieldValueAndArchived(flagDTO.getName(), flagDTO.getFieldName(), flagDTO.getFieldValue(), UN_ARCHIVED);
        if (optionalFlag.isPresent()) throw new RecordExistException(Flag.class, "Name", flagDTO.getName() + " with " + flagDTO.getFieldValue());
        return flagRepository.save(flagMapper.toFlag(flagDTO));
    }

    @Override
    public Flag updateFlag(Long id, FlagDTO flagDTO) {
        Flag flag = flagRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Flag.class, "Id", id + ""));
        flagDTO.setId(flag.getId());
        return flagRepository.save(flagMapper.toFlag(flagDTO));
    }
    @Override
    public void checkForAndSaveMemberFlag(Long householdId, Long householdMemberId, Object object, List<FormFlag> formFlags) {
        if (formFlags != null) {
            formFlags.forEach(formFlag -> {
                Optional<Integer> flagDataType = Optional.ofNullable(formFlag.getFlag().getDatatype());// 0 - string, 1 - application codeset, 2 - integer, 3 - form level flag
                Optional<Integer> flagType = Optional.ofNullable(formFlag.getFlag().getType()); //1 is system level flag, 2 - form level flag, 3 is form data level flag
                flagType.ifPresent(data -> flagDataType.ifPresent(data1 -> {
                    String fieldName = formFlag.getFlag().getFieldName().trim();
                    String operator = formFlag.getFlag().getOperator().trim();
                    Boolean continuous = formFlag.getFlag().getContinuous();
                    String formFlagFieldValue = formFlag.getFlag().getFieldValue().trim();
                    JsonNode tree;
                    String field;
                    int fieldIntegerValue = 0;
                    int formFlagFieldIntegerValue = 0;
                    FlagOperatorType flagOperatorType = FlagOperatorType.from(operator);
                    try {
                        String regex = "^\"+|\"+$";
                        switch (data) {
                        //if system level flag
                        case SYSTEM_LEVEL_FLAG: 
                            this.saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                            break;
                        //if form level flag
                        case FORM_LEVEL_FLAG: 
                            this.saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                            break;
                        //form data level flag
                        default: 
                            //if not application code set but is string
                            switch (data1) {
                            case STRING_FLAG_DATA_TYPE: 
                                tree = mapper.readTree(object.toString()).get(fieldName);
                                field = String.valueOf(tree).replaceAll(regex, "");
                                if (formFlagFieldValue.equalsIgnoreCase(field)) {
                                    this.saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                }
                                break;
                            //if application code set
                            case APPLICATION_CODESET_FLAG_DATA_TYPE: 
                                tree = mapper.readTree(object.toString()).get(fieldName);
                                JsonNode jsonNode = tree.get("display");
                                field = String.valueOf(jsonNode).replaceAll(regex, "");
                                if (formFlagFieldValue.equalsIgnoreCase(field)) {
                                    this.saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                //<editor-fold defaultstate="collapsed" desc="delombok">
                                }
                                break;
                            // If integer
                            case INTEGER_FLAG_DATA_TYPE: 
                                tree = mapper.readTree(object.toString()).get(fieldName);
                                //removing extra quotes
                                field = String.valueOf(tree).replaceAll(regex, "");
                                fieldIntegerValue = Integer.valueOf(field);
                                formFlagFieldIntegerValue = Integer.valueOf(formFlagFieldValue);
                                switch (flagOperatorType) {
                                case EQUAL_T0: 
                                    if (formFlagFieldValue.equalsIgnoreCase(field)) {
                                        saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                    } else if (Boolean.TRUE.equals(continuous)) {
                                        memberFlagRepository.findByHouseholdIdAndHouseholdMemberIdAndFlagId(householdId, householdMemberId, formFlag.getFlagId()).ifPresent(memberFlagRepository::delete);
                                    }
                                //</editor-fold>
                                    break;
                                case GREATER_THAN: 
                                    if (fieldIntegerValue > formFlagFieldIntegerValue) {
                                        saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                    } else if (Boolean.TRUE.equals(continuous)) {
                                        memberFlagRepository.findByHouseholdIdAndHouseholdMemberIdAndFlagId(householdId, householdMemberId, formFlag.getFlagId()).ifPresent(memberFlagRepository::delete);
                                    }
                                    //<editor-fold defaultstate="collapsed" desc="delombok">
                                    break;
                                case LESS_THAN: 
                                    if (fieldIntegerValue < formFlagFieldIntegerValue) {
                                        saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                    } else if (Boolean.TRUE.equals(continuous)) {
                                        findByHouseholdIdAndHouseMemberIdAndFlagIdAndDelete(householdId, householdMemberId, formFlag.getFlagId());
                                    }
                                    break;
                                case GREATER_THAN_OR_EQUAL_TO: 
                                    if (fieldIntegerValue >= formFlagFieldIntegerValue) {
                                        saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                    } else if (Boolean.TRUE.equals(continuous)) {
                                        findByHouseholdIdAndHouseMemberIdAndFlagIdAndDelete(householdId, householdMemberId, formFlag.getFlagId());
                                    }
                                    break;
                                case FORM_LEVEL: 
                                case INVALID: 
                                    break;
                                case LESS_THAN_OR_EQUAL_TO: 
                                    if (fieldIntegerValue <= formFlagFieldIntegerValue) {
                                        saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                    } else if (Boolean.TRUE.equals(continuous)) {
                                        findByHouseholdIdAndHouseMemberIdAndFlagIdAndDelete(householdId, householdMemberId, formFlag.getFlagId());
                                    }
                                    break;
                                }
                            default: 
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));
            });
        }
    }

    @Override
    public List<Form> applyingFormsToBeneficiariesFlags(HouseholdMemberDTO householdMemberDTO, Form form, List<Form> forms) {
        //Get forms flags are applied to
        List<FormFlag> formFlags = formFlagRepository.findByFormCodeAndStatusAndArchived(form.getCode(), STATUS, UN_ARCHIVED);
        List<Flag> memberFlags = new ArrayList<>();
        //check if formFlag is empty
        if (formFlags.isEmpty()) {
            forms.add(form);
            return forms;
        } else {
            //check for member flag
            formFlags.forEach(formFlag -> householdMemberDTO.getFlags().forEach(flag -> {
                if (Objects.equals(formFlag.getFlagId(), flag.getId())) {
                    memberFlags.add(flag);
                }
            }));
            if (memberFlags.size() == formFlags.size()) {
                forms.add(form);
                return forms;
            }
        }
        return forms;
    }

    private void saveMemberFlag(Long householdId, Long householdMemberId, Long flagId) {
        MemberFlag memberFlag = new MemberFlag();
        //set memberFlag attributes
        memberFlag.setFlagId(flagId);
        memberFlag.setHouseholdId(householdId);
        memberFlag.setHouseholdMemberId(householdMemberId);
        List<MemberFlag> memberFlags = memberFlagRepository.findAllByHouseholdIdAndHouseholdMemberId(householdMemberId, householdId);
        Flag flag = flagRepository.findByIdAndArchived(flagId, UN_ARCHIVED).get();
        //Check for opposites or similarities in flag field name & delete
        memberFlags.forEach(memberFlag1 -> {
            if (memberFlag1.getFlag().getFieldName().equalsIgnoreCase(flag.getFieldName()) && !memberFlag1.getFlag().getFieldValue().equalsIgnoreCase(flag.getFieldValue())) {
                memberFlagRepository.delete(memberFlag1);
            }
        });
        if (!memberFlagRepository.findByHouseholdIdAndHouseholdMemberIdAndFlagId(householdId, householdMemberId, flagId).isPresent()) {
            memberFlagRepository.save(memberFlag);
        }
    }

    public Integer getAge(Object object) {
        try {
            final JsonNode tree = mapper.readTree(object.toString()).get("dob");
            String dob = String.valueOf(tree).replaceAll("^\"+|\"+$", "");
            if (dob != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                //convert String to LocalDate
                LocalDate localDate = LocalDate.parse(dob, formatter);
                Period period = Period.between(localDate, LocalDate.now());
                return period.getYears();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void findByHouseholdIdAndHouseMemberIdAndFlagIdAndDelete(Long householdId, Long householdMemberId, Long flagId) {
        memberFlagRepository.findByHouseholdIdAndHouseholdMemberIdAndFlagId(householdId, householdMemberId, flagId).ifPresent(memberFlag -> {
            memberFlagRepository.delete(memberFlag);
        });
    }

}
