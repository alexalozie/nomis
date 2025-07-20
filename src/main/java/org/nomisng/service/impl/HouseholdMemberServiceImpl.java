package org.nomisng.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.nomisng.domain.dto.*;
import org.nomisng.service.EncounterService;
import org.nomisng.service.FormService;
import org.nomisng.service.HouseholdMemberService;
import org.nomisng.service.UserService;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.apierror.RecordExistException;
import org.nomisng.domain.entity.*;
import org.nomisng.domain.mapper.EncounterMapper;
import org.nomisng.domain.mapper.FormMapper;
import org.nomisng.domain.mapper.HouseholdMapper;
import org.nomisng.domain.mapper.HouseholdMemberMapper;
import org.nomisng.repository.FormRepository;
import org.nomisng.repository.HouseholdMemberRepository;
import org.nomisng.repository.HouseholdRepository;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.nomisng.util.Constants.ArchiveStatus.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HouseholdMemberServiceImpl implements HouseholdMemberService {
    public static final int APPLIED_TO_FORM = 1;
    private final NamedParameterJdbcTemplate nameParameterJdbcTemplate;
    private final HouseholdMemberRepository householdMemberRepository;
    private final FormRepository formRepository;
    private final HouseholdRepository householdRepository;
    private final HouseholdMemberMapper householdMemberMapper;
    private final HouseholdMapper householdMapper;
    private final EncounterMapper encounterMapper;
    private final FormMapper formMapper;
    private final EncounterService encounterService;
    private final FormService formService;
    private final UserService userService;
    private ObjectMapper mapper = new ObjectMapper();
    private String firstName = "firstName";
    private String lastName = "lastName";
    private static final String CARE_SUPPORT_KEY = "care_support";
    private static final String HOUSEHOLD_ID = "householdId";
    private static final String HOUSEHOLD_MEMBER_ID = "householdMemberId";
    private static final String TYPE_ID = "typeId";
    private static final int CAREGIVER_TYPE = 1;
    private static final int OVC_TYPE = 2;
    private static final int OTHER_TYPE = 3;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public String getUniqueId(Household household) {
        Long serialNumber = householdMemberRepository.findHouseholdMemberCountOfHousehold(household.getId()) + 1;
        String uniqueId = household.getUniqueId() + "/" + serialNumber;

        return uniqueId;
    }

    public Page<HouseholdMember> getAllHouseholdMembersPage(String search, Integer memberType, Pageable pageable) {
        Long currentCboProjectId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        if (search == null || search.equalsIgnoreCase("*")) {

            return householdMemberRepository.findAllByCboProjectIdAndArchivedOrderByIdDesc(currentCboProjectId, UN_ARCHIVED, pageable);
        }
        search = "%" + search + "%";
        return householdMemberRepository.findAllByCboProjectIdAndArchivedAndSearchParameterOrderByIdDesc(search, currentCboProjectId, UN_ARCHIVED, pageable);
    }

    public List<HouseholdMemberDTO> getAllHouseholdMembersFromPage(Page<HouseholdMember> householdMembersPage) {
        List<HouseholdMember> householdMembers = householdMembersPage.getContent().stream().map(householdMember -> addMemberFlag(householdMember)).collect(Collectors.toList());
        return householdMemberMapper.toHouseholdDTOS(householdMembers);
    }

    public HouseholdMember addMemberFlag(HouseholdMember householdMember) {
        List<Flag> flags = new ArrayList<>();
        householdMember.getMemberFlagsById().forEach(memberFlag -> {
            flags.add(memberFlag.getFlag());
        });
        householdMember.setFlags(flags);
        return householdMember;
    }

    public HouseholdMember saveHouseholdMember(HouseholdMemberDTO householdMemberDTO) {
        try {
            String details = mapper.writeValueAsString(householdMemberDTO.getDetails());
            firstName = this.getValueFromJsonField(details, "firstName");
            lastName = this.getValueFromJsonField(details, "lastName");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Optional<HouseholdMember> optionalHouseholdMember = householdMemberRepository.findByHouseholdIdAndFirstNameAndLastNameAndArchived(householdMemberDTO.getHouseholdId(), firstName, lastName, UN_ARCHIVED);
        optionalHouseholdMember.ifPresent(householdMember -> {
            throw new RecordExistException(HouseholdMember.class, firstName + " " + lastName, "already exist in household");
        });
        Household household = householdRepository.findById(householdMemberDTO.getHouseholdId()).orElseThrow(() -> new EntityNotFoundException(Household.class, "id", "" + householdMemberDTO.getHouseholdId()));
        String uniqueId = getUniqueId(household);
        householdMemberDTO.setUniqueId(uniqueId);

        HouseholdMember householdMember = householdMemberMapper.toHouseholdMember(householdMemberDTO);
        if (householdMember.getUniqueId() == null) {
            householdMember.setUniqueId(uniqueId);
        }
        Long currentCboProjectId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        householdMember.setCboProjectId(currentCboProjectId);
        return householdMemberRepository.save(householdMember);
    }

    public HouseholdMemberDTO getHouseholdMemberById(Long id) {
        HouseholdMember householdMember = this.addMemberFlag(householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id + "")));
        return householdMemberMapper.toHouseholdMemberDTO(householdMember);
    }

    public HouseholdDTO getHouseholdByHouseholdMemberId(Long id) {
        HouseholdMember householdMember = householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id + ""));
        return householdMapper.toHouseholdDTO(householdMember.getHouseholdByHouseholdId());
    }

    public List<EncounterDTO> getEncountersByHouseholdMemberId(Long id) {
        HouseholdMember householdMember = householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id + ""));
        List<Encounter> encounters = // get all unarchived
                // by id reversed/descending order
                householdMember.getEncounterByHouseholdMemberId().stream().map(encounter -> encounterService.addFirstNameAndLastNameAndFormNameToEncounter(encounter)).filter(encounter -> encounter.getArchived() != null && encounter.getArchived() == UN_ARCHIVED).sorted(Comparator.comparing(Encounter::getId).reversed()).collect(Collectors.toList());
        return encounterMapper.toEncounterDTOS(encounters);
    }

    public HouseholdMember updateHouseholdMember(Long id, HouseholdMemberDTO householdMemberDTO) {
        householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id + ""));
        HouseholdMember householdMember = householdMemberMapper.toHouseholdMember(householdMemberDTO);
        householdMember.setArchived(UN_ARCHIVED);
        householdMember.setId(id);
        return householdMemberRepository.save(householdMember);
    }

    public void deleteHouseholdMember(Long id) {
        HouseholdMember householdMember = householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id + ""));
        householdMember.setArchived(ARCHIVED);
        householdMemberRepository.save(householdMember);
    }

    private String getValueFromJsonField(String details, String field) {
        try {
            JsonNode tree = mapper.readTree(details).get(field);
            if (!tree.isNull()) {
                //remove trailing quotes and return
                return String.valueOf(tree).replaceAll("^\"+|\"+$", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<FormDTO> getFormsByHouseholdMemberById(Long id) {
        HouseholdMember householdMember = householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id + ""));
        //get for by member or form type
        List<String> formCodes = formService.getAllForms(householdMember.getHouseholdMemberType()).stream().map(FormDTO::getCode).collect(Collectors.toList());
        //check for member flag
        householdMember.getMemberFlagsById().forEach(memberFlag -> {
            Optional<String> formCode = memberFlag.getFlag().getFormsByIdFlag().stream().filter(formFlag -> formFlag.getStatus() == APPLIED_TO_FORM).map(FormFlag::getFormCode).findFirst();
            //Compare formCodes
            formCode.ifPresent(code -> {
                if (!formCodes.contains(formCode)) {
                    formCodes.remove(formCode);
                }
            });
        });
        return formMapper.toFormDTOS(formRepository.findByCodeInList(UN_ARCHIVED, formCodes));
    }

    public List<HouseholdMember> getAllMembersAboutToGraduate(Long householdId) {
        List<HouseholdMember> householdMemberList = householdMemberRepository.findByHouseholdIdAndArchived(householdId, UN_ARCHIVED);
        List<HouseholdMember> graduatedList = new ArrayList<>();
        householdMemberList.forEach(member -> {
            boolean status = getMemberAboutToGraduate(member.getId());
            if (status) {
                graduatedList.add(member);
            }

        });

        return graduatedList;
    }

    private String getServiceProvided(Long householdId, Long householdMemberId) {
        String service = null;
        try {
            String sqlStatement = "select service from ( select household_id, coalesce(count(e.household_id), 0) total, " +
                    "string_agg(c->>\'name\', \', \') service " +
                    "from encounter e join form_data f on e.id = f.encounter_id, " +
                    "jsonb_array_elements(data->\'serviceOffered\') with ordinality a(c) " +
                    "where f.data ?? \'serviceDate\' AND e.archived = 0 AND encounter.household_member_id = :householdMemberId " +
                    "and f.data->>\'serviceOffered\'  is not null group by household_id) as tbl " +
                    "where household_id = :householdId";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
            params.addValue(HOUSEHOLD_ID, householdId);
            service = namedParameterJdbcTemplate.queryForObject(sqlStatement, params, String.class);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return service;
    }

    private Integer getServiceProvided(Long householdId) {
        Integer total = 0;
        try {
            String sqlStatement = "select total from ( select household_id, coalesce(count(e.household_id), 0) total, string_agg(c->>\'name\', \', \') service " +
                    "from encounter e join form_data f on e.id = f.encounter_id, " +
                    "jsonb_array_elements(data->\'serviceOffered\') with ordinality a(c) " +
                    "where f.data ?? \'serviceDate\' AND e.archived = 0 " +
                    "and f.data->>\'serviceOffered\' is not null group by household_id) as tbl " +
                    "where household_id = :householdId";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue(HOUSEHOLD_ID, householdId);
            total = namedParameterJdbcTemplate.queryForObject(sqlStatement, params, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return total;
    }

    public Boolean getMemberAboutToGraduate(Long id) {
        Optional<HouseholdMember> optMember = householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED);
        boolean status = false;
        if (optMember.isPresent()) {
            HouseholdMember member = optMember.get();
            int age = getCurrentAge(member.getHouseholdId(), member.getId());
            boolean education = getEducationalStatus(member.getHouseholdId(), member.getId());
            boolean hivPositive = getHivPositive(member.getHouseholdId(), member.getId());
            String offered = getServiceOffered(member.getHouseholdId(), member.getId());
            String provided = getServiceProvided(member.getHouseholdId(), member.getId());
            if (provided.equalsIgnoreCase(offered)) {
                if (education == false && age > 17 && hivPositive == false) {
                    status = true;
                } else if (education == false && age > 21 && hivPositive) {
                    status = true;
                }
            } else {
                if (age > 17) {
                    if (age >= 21 && (education || hivPositive)) {
                        status = true;
                    } else {
                        status = !education && !hivPositive;
                    }
                }
            }
        }
        return status;
    }

    private Integer getCurrentAge(Long householdId, Long householdMemberId) {
        String sqlStatement = "SELECT EXTRACT(\'YEAR\' from AGE(TO_DATE(details->>\'dob\', \'YYYY-m-d\'))) current_age " + "FROM household_member WHERE id = :householdMemberId " + "AND household_id =:householdId AND archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
        params.addValue(HOUSEHOLD_ID, householdId);
        List<Integer> strLst = namedParameterJdbcTemplate.query(sqlStatement, params, (rs, rowNum) -> rs.getInt(1));
        if (strLst.isEmpty()) {
            return 0;
        } else {
            return strLst.get(0);
        }
    }

    private String getServiceOffered(Long householdId, Long householdMemberId) {
        String sqlStatement = "SELECT encounter.form_code, d->>\'services\' as service " +
                "FROM encounter join form_data  on encounter.id = form_data.encounter_id, " +
                " jsonb_array_elements(data->\'carePlan\') with ordinality a(c), " +
                " jsonb_array_elements(c->\'identifiedIssues\') with ordinality b(d) " +
                "where encounter.form_code = \'c4666b04-9357-4229-8683-de5efed78ab7\' " +
                "AND encounter.household_member_id = :householdMemberId AND " +
                "encounter.household_id = :householdId AND encounter.archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
        params.addValue(HOUSEHOLD_ID, householdId);
        List<String> strLst = namedParameterJdbcTemplate.query(sqlStatement, params, (rs, rowNum) -> rs.getString(1));
        if (strLst.isEmpty()) {
            return null;
        } else {
            return strLst.get(0);
        }
    }

    private Integer getServiceOffered(Long householdId) {
        Integer total = 0;
        try {
            String sqlStatement = "select coalesce(count(household_id),0) total " + "    FROM encounter join form_data on encounter.id = form_data.encounter_id,  " + "    jsonb_array_elements(data->\'carePlan\') with ordinality a(c),  " + "    jsonb_array_elements(c->\'identifiedIssues\') with ordinality b(d)  " + "    where encounter.form_code = \'c4666b04-9357-4229-8683-de5efed78ab7\' " + "    and  encounter.household_id = :householdId";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue(HOUSEHOLD_ID, householdId);
            total = namedParameterJdbcTemplate.queryForObject(sqlStatement, params, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return total;
    }

    private Boolean getEducationalStatus(Long householdId, Long householdMemberId) {
        //check if child is in school, true:yes, false:no
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sqlStatement = "SELECT case when f.data->\'childInSchool\'->>\'code\' " + "= \'51dc4de7-8c88-4963-b255-331bf930b7c0\' THEN true " + "ELSE false END status FROM encounter e join form_data f on e.id = f.encounter_id " + "WHERE e.form_code = \'f4f553f3-46eb-4968-b895-3a2397a80cb3\'  " + "AND e.household_id = :householdId AND e.archived = 0";
        if (householdMemberId == -1L) {
            sqlStatement = "SELECT case when f.data->\'childInSchool\'->>\'code\' " + "= \'51dc4de7-8c88-4963-b255-331bf930b7c0\' THEN true " + "ELSE false END status FROM encounter e join form_data f on e.id = f.encounter_id " + "WHERE e.form_code = \'f4f553f3-46eb-4968-b895-3a2397a80cb3\'  " + "AND e.household_member_id = :householdMemberId AND e.household_id =:householdId " + "AND e.archived";
            params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
        }
        params.addValue(HOUSEHOLD_ID, householdId);
        List<Boolean> strLst = namedParameterJdbcTemplate.query(sqlStatement, params, (rs, rowNum) -> rs.getBoolean(1));
        return !strLst.isEmpty();
    }

    private Boolean getCaregiverHivPositive(Long householdId, Long householdMemberId) {
        String sqlStatement = "SELECT case when details->\'caregiver\'->\'details\'->\'hivStatus\'->>\'code\' " + "= \'e528faaf-7735-4cbc-a1b4-d110247b7227\' THEN true " + "ELSE false END status FROM household_member " + "WHERE id = :householdMemberId AND household_id =:householdId AND archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
        params.addValue(HOUSEHOLD_ID, householdId);
        List<Boolean> strLst = namedParameterJdbcTemplate.query(sqlStatement, params, (rs, rowNum) -> rs.getBoolean(1));
        return !strLst.isEmpty();
    }

    private Boolean getHivPositive(Long householdId, Long householdMemberId) {
        String sqlStatement = "SELECT CASE  WHEN details->\'hivStatus\'->>\'code\' = \'e528faaf-7735-4cbc-a1b4-d110247b7227\' " + "THEN true ELSE false END AS status FROM household_member " + "WHERE id = :householdMemberId AND household_id =:householdId AND archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
        params.addValue(HOUSEHOLD_ID, householdId);
        List<Boolean> strLst = namedParameterJdbcTemplate.query(sqlStatement, params, (rs, rowNum) -> rs.getBoolean(1));
        return !strLst.isEmpty();
    }

    private List<Map<String, Object>> getHouseholdMembersAbove17(Long householdMemberId) {
        String sqlStatement = "SELECT id, unique_id, TO_DATE(details->>\'dob\', \'YYYY-m-d\') dob, " + "(DATE_PART(\'YEAR\', CURRENT_DATE) - " + "DATE_PART(\'YEAR\',TO_DATE(details->>\'dob\', \'YYYY-m-d\'))) current_age " + "FROM household_member WHERE (DATE_PART(\'YEAR\', CURRENT_DATE) - " + "DATE_PART(\'YEAR\',TO_DATE(details->>\'dob\', \'YYYY-m-d\')))  > 18 " + "AND id = :householdMemberId AND archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        namedParameterJdbcTemplate.query(sqlStatement, params, rs -> {
            map.put("unique_id", StringUtils.trimToEmpty("unique_id"));
            map.put("dob", StringUtils.trimToEmpty("dob"));
            map.put("current_age", StringUtils.trimToEmpty("current_age"));
            mapList.add(map);
        });
        return mapList;
    }

    public List<HouseholdMember> getHouseholdMemberCaregivers(Long householdId) {
        return householdMemberRepository.findByHouseholdIdAndHouseholdMemberTypeAndArchived(householdId, CAREGIVER_TYPE, UN_ARCHIVED);
    }

    public Optional<HouseholdMember> reAssignCaregiver(Long ovcId, Long caregiverId) {
        AtomicLong memberId = new AtomicLong();
        String sqlStatement = "SELECT details " + "FROM household_member WHERE household_member_type = :typeId AND " + "id = :caregiverId AND archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue(TYPE_ID, CAREGIVER_TYPE).addValue("caregiverId", caregiverId);
        namedParameterJdbcTemplate.query(sqlStatement, params, rs -> {
            if (rs.getString("details") != null) {
                try {
                    JSONObject jsonObject = new JSONObject(rs.getString("details"));
                    String query = "UPDATE household_member set details = jsonb_set(details::jsonb, \'{caregiver}\', to_jsonb(:caregiver::json), false) " + "where id = :ovcId and household_member_type = :typeId AND archived = 0";
                    MapSqlParameterSource param = new MapSqlParameterSource().addValue("caregiver", jsonObject.toString()).addValue(TYPE_ID, OVC_TYPE).addValue("ovcId", ovcId);
                    memberId.set(namedParameterJdbcTemplate.update(query, param));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        if (memberId.get() > 0) {
            return householdMemberRepository.findByIdAndArchived(ovcId, UN_ARCHIVED);
        }
        return Optional.empty();
    }

    public List<Map<String, Long>> getEnrolmentSummary(Long cboProjectId) {
        String sql = "SELECT EXTRACT(YEAR FROM date_created) mYear, EXTRACT(MONTH FROM date_created) mKey, " + "COUNT(EXTRACT(MONTH FROM date_created))  mValue " + "FROM household_member WHERE date_created >= (CURRENT_DATE + INTERVAL \'-6 MONTHS\') " + "AND cbo_project_id = :cboProjectId GROUP BY mYear, mKey order by mYear, mKey;";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("cboProjectId", cboProjectId);
        List<Map<String, Long>> summaryList = new ArrayList<>();
        namedParameterJdbcTemplate.query(sql, params, rs -> {
            while (rs.next()) {                Map<String, Long> mapDTO = new HashMap<>();
                mapDTO.put("month", rs.getLong("mKey"));
                mapDTO.put("value", rs.getLong("mValue"));
                summaryList.add(mapDTO);
            }
        });
        return summaryList;
    }

    public Long getTotalVc(Long cboProjectId) {
        return householdMemberRepository.getTotalVc(cboProjectId);
    }

    public Long getTotalPositive(Long cboProjectId) {
        return householdMemberRepository.getTotalPositive(cboProjectId);
    }

    public Long getTotalLinked(Long cboProjectId) {
        return householdMemberRepository.getTotalLinked(cboProjectId);
    }

}
