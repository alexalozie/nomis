package org.nomisng.service.chart;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HouseholdChartService {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;
    private static final int CAREGIVER_TYPE = 1;
    private static final int VC_TYPE = 2;

    public Long totalHousehold(Long cboProjectId) {
        String sql = "SELECT COUNT(*) FROM household where cbo_project_id = " + cboProjectId + " AND archived = 0";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public Long totalVulnerableChildren(Long cboProjectId) {
        String sql = "SELECT COUNT(*) FROM household_member WHERE cbo_project_id = " + cboProjectId + " AND archived = 0 AND household_member_type = " + VC_TYPE;
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public Long totalCaregiver(Long cboProjectId) {
        String sql = "SELECT COUNT(*) FROM household_member WHERE  cbo_project_id = " + cboProjectId + " AND archived = 0 AND household_member_type = " + CAREGIVER_TYPE;
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public Long totalPositiveVc(Long cboProjectId) {
        String sql = "SELECT count(distinct hm.unique_id) FROM household_member hm " +
                "left join encounter e on hm.id=  e.household_member_id  " +
                "join form_data fd on e.id = fd.encounter_id  " +
                "where (hm.details->'hiv_test_result'->>'code' = 'e528faaf-7735-4cbc-a1b4-d110247b7227' " +
                "OR (hm.details->'enrolmentStreams'->>'enrolment_stream_number')::integer = 1 " +
                "OR lower(fd.data ->'new_hiv_test_result' ->> 'display') = 'positive') AND  hm.cbo_project_id = " + cboProjectId +
                " AND hm.archived = 0 AND household_member_type = " + VC_TYPE;
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public Long totalPositiveCaregiver(Long cboProjectId) {
        String sql = "SELECT count(distinct hm.unique_id) FROM household_member hm " +
                "left join encounter e on hm.id=  e.household_member_id  " +
                "join form_data fd on e.id = fd.encounter_id  " +
                "where (hm.details->'hiv_test_result'->>'code' = 'e528faaf-7735-4cbc-a1b4-d110247b7227' " +
                "OR (hm.details->'enrolmentStreams'->>'enrolment_stream_number')::integer = 1 " +
                "OR lower(fd.data ->'new_hiv_test_result' ->> 'display') = 'positive') AND  hm.cbo_project_id = " + cboProjectId +
                " AND hm.archived = 0 AND household_member_type = " + CAREGIVER_TYPE;
        return jdbcTemplate.queryForObject(sql, Long.class);
    }


}
