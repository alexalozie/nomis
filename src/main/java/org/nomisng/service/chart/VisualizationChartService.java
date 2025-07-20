package org.nomisng.service.chart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.chart.PieChart;
import org.nomisng.repository.FormDataRepository;
import org.nomisng.repository.HouseholdMemberRepository;
import org.nomisng.repository.HouseholdRepository;
import org.nomisng.service.UserService;
import org.nomisng.util.ChartUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisualizationChartService {
    private final UserService userService;
    private final FormDataRepository formDataRepository;
    private final HouseholdMemberRepository householdMemberRepository;
    private final HouseholdRepository householdRepository;
    private final JdbcTemplate jdbcTemplate;
    private final List<Map<String, Object>> series;
    private final ChartUtil chartUtil;
    private Map<String, List> xAxis = new HashMap();
    private Map<String, Object> yAxis = new HashMap();
    private Map<String, Object> yAxisTitle = new HashMap();
    private List<Object> columnSeries = new ArrayList<>();
    private final List<Object> data = new ArrayList<Object>();

    public List<PieChart> getServByEnrolmentStreamChart() {
        Long cboProjectId = userService.getUserWithRoles().get().getCboProjectByCurrentCboProjectId().getId();
        List<PieChart> pieChartList = new ArrayList<>();
        String sql = "SELECT COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question1' as boolean) = true then 1 else 0 end), 0) stream1, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question2' as boolean) = true then 1 else 0 end), 0) stream2, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question3' as boolean) = true then 1 else 0 end), 0) stream3, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question4' as boolean) = true then 1 else 0 end), 0) stream4, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question5' as boolean) = true then 1 else 0 end), 0) stream5, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question6' as boolean) = true then 1 else 0 end), 0) stream6, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question7' as boolean) = true then 1 else 0 end), 0) stream7, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question8' as boolean) = true then 1 else 0 end), 0) stream8, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question9' as boolean) = true then 1 else 0 end), 0) stream9, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question10' as boolean) = true then 1 else 0 end), 0) stream10, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question11' as boolean) = true then 1 else 0 end), 0) stream11, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question12' as boolean) = true then 1 else 0 end), 0) stream12, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question13' as boolean) = true then 1 else 0 end), 0) stream13, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question14' as boolean) = true then 1 else 0 end), 0) stream14, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question15' as boolean) = true then 1 else 0 end), 0) stream15, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question16' as boolean) = true then 1 else 0 end), 0) stream16, " +
                "COALESCE(sum(case when cast(hm.details->'enrolmentStreams'->>'question17' as boolean) = true then 1 else 0 end), 0) stream17 " +
                "FROM household_member hm  WHERE hm.archived = 0 AND hm.cbo_project_id = ? ";
        jdbcTemplate.query(sql, rs -> {
            PieChart stream1 = new PieChart();
            stream1.setName("Child living with HIV (CLHIV)");
            stream1.setY(rs.getInt("stream1"));
            stream1.setSelected(true);
            stream1.setSliced(false);
            pieChartList.add(stream1);
            PieChart stream2 = new PieChart();
            stream2.setName("HIV Exposed Infants (HEI)");
            stream2.setY(rs.getInt("stream2"));
            stream2.setSelected(true);
            stream2.setSliced(false);
            pieChartList.add(stream2);
            PieChart stream3 = new PieChart();
            stream3.setName("Child living with an HIV Positive Adult");
            stream3.setY(rs.getInt("stream3"));
            stream3.setSelected(true);
            stream3.setSliced(false);
            pieChartList.add(stream3);
            PieChart stream4 = new PieChart();
            stream4.setName("Child at risk or have experienced sexual violence or any other form of violence");
            stream4.setY(rs.getInt("stream4"));
            stream4.setSelected(true);
            stream4.setSliced(false);
            pieChartList.add(stream4);
            PieChart stream5 = new PieChart();
            stream5.setName("Teenage mother");
            stream5.setY(rs.getInt("stream5"));
            stream5.setSelected(true);
            stream5.setSliced(false);
            pieChartList.add(stream5);
            PieChart stream6 = new PieChart();
            stream6.setName("Children in need of alternative family care");
            stream6.setY(rs.getInt("stream6"));
            stream6.setSelected(true);
            stream6.setSliced(false);
            pieChartList.add(stream6);
            PieChart stream7 = new PieChart();
            stream7.setName("Children living on the street (exploited almajiri, nomadic, militants)");
            stream7.setY(rs.getInt("stream7"));
            stream7.setSelected(true);
            stream7.setSliced(false);
            pieChartList.add(stream7);
            PieChart stream8 = new PieChart();
            stream8.setName("Children in conflict with law");
            stream8.setY(rs.getInt("stream8"));
            stream8.setSelected(true);
            stream8.setSliced(false);
            pieChartList.add(stream8);
            PieChart stream9 = new PieChart();
            stream9.setName("Children of KP");
            stream9.setY(rs.getInt("stream9"));
            stream9.setSelected(true);
            stream9.setSliced(false);
            pieChartList.add(stream9);
            PieChart stream10 = new PieChart();
            stream10.setName("Child orphaned by AIDS");
            stream10.setY(rs.getInt("stream10"));
            stream10.setSelected(true);
            stream10.setSliced(false);
            pieChartList.add(stream10);
            PieChart stream11 = new PieChart();
            stream11.setName("Child living in a child Headed Household");
            stream11.setY(rs.getInt("stream11"));
            stream11.setSelected(true);
            stream11.setSliced(false);
            pieChartList.add(stream11);
            PieChart stream12 = new PieChart();
            stream12.setName("Child especially adolescent females at risk of transactional sex");
            stream12.setY(rs.getInt("stream12"));
            stream12.setSelected(true);
            stream12.setSliced(false);
            pieChartList.add(stream12);
            PieChart stream13 = new PieChart();
            stream13.setName("Socially excluded children");
            stream13.setY(rs.getInt("stream13"));
            stream13.setSelected(true);
            stream13.setSliced(false);
            pieChartList.add(stream13);
            PieChart stream14 = new PieChart();
            stream14.setName("Socially excluded children");
            stream14.setY(rs.getInt("stream14"));
            stream14.setSelected(true);
            stream14.setSliced(false);
            pieChartList.add(stream14);
            PieChart stream15 = new PieChart();
            stream15.setName("Socially excluded children");
            stream15.setY(rs.getInt("stream15"));
            stream15.setSelected(true);
            stream15.setSliced(false);
            pieChartList.add(stream15);
            PieChart stream16 = new PieChart();
            stream16.setName("Socially excluded children");
            stream16.setY(rs.getInt("stream16"));
            stream16.setSelected(true);
            stream16.setSliced(false);
            pieChartList.add(stream16);
            PieChart stream17 = new PieChart();
            stream17.setName("Socially excluded children");
            stream17.setY(rs.getInt("stream17"));
            stream17.setSelected(true);
            stream17.setSliced(false);
            pieChartList.add(stream17);

        }, cboProjectId);

        return pieChartList;
    }

    public Object getServComprehensiveAndCaregiverChart() {
        clearChartList();
        Long cboProjectId = userService.getUserWithRoles().get().getCboProjectByCurrentCboProjectId().getId();

        List<Object> comprehensive = new ArrayList<>();
        List<Object> caregiver = new ArrayList<>();

        String type = "stack";
        String chartTitle = "Total number of OVC_SERV Comprehensive and Caregiver";
        LocalDate currentMonth = YearMonth.now().atEndOfMonth();

        String sql = "SELECT COALESCE(sum (case when hm.details->'sex'->>'code'='976cac0a-5bee-4421-9cbc-0bd8a487f077' " +
                "then 1 else 0 end), 0) male_total, COALESCE(sum(case when hm.details->'sex'->>'code'='3ef366ba-9b2c-48c7-a611-c26e01117763' " +
                "then 1 else 0 end), 0) female_total from household_member hm where id not in (SELECT household_member_id from encounter " +
                "where form_code != '6fb50a2e-e2d3-4534-a641-1abdb75c6fda' and household_member_id is not null) " +
                "and cbo_project_id = ? and archived = 0";

        jdbcTemplate.query(sql, rs -> {
             comprehensive.add(rs.getLong("male_total"));
            comprehensive.add(rs.getInt("female_total"));
            columnSeries.add(chartUtil.getMainMap(comprehensive, "Active", "stack", type, null));
        }, cboProjectId);

        sql = "SELECT COALESCE(sum (case when hm.details->'sex'->>'code'='976cac0a-5bee-4421-9cbc-0bd8a487f077' " +
            "then 1 else 0 end), 0) male_total, COALESCE(sum(case when hm.details->'sex'->>'code'='3ef366ba-9b2c-48c7-a611-c26e01117763' " +
            "then 1 else 0 end), 0) female_total " +
            "from household_member hm where household_member_type = 1 AND cbo_project_id = ? and archived = 0 AND hm.id not in " +
            "(SELECT household_member_id from encounter e " +
            "where e.form_code != '6fb50a2e-e2d3-4534-a641-1abdb75c6fda' and e.household_member_id is not null) ";
        jdbcTemplate.query(sql, rs-> {
            caregiver.add(rs.getInt("male_total"));
            caregiver.add(rs.getInt("female_total"));
            columnSeries.add(chartUtil.getMainMap(caregiver, "Caregiver", "stack", type, null));
        },cboProjectId);

        chartUtil.setXAxis(chartUtil.setVCComprehensiveAndCaregiverCategories());
        return chartUtil.buildMainMap(type, chartTitle, "", chartUtil.getXAxis(),
                chartUtil.getYAxis(), columnSeries, null);
    }

    public Object getVcHivStatusChart() {
        clearChartList();
        Long cboProjectId = userService.getUserWithRoles().get().getCboProjectByCurrentCboProjectId().getId();

        List<Object> positive = new ArrayList<Object>();
        List<Object> negative = new ArrayList<Object>();
        List<Object> unknown = new ArrayList<Object>();
        List<Object> onArt = new ArrayList<Object>();
        List<Object> testNotRequired = new ArrayList<Object>();
        List<Object> servActive = new ArrayList<Object>();

        String type = "column";
        String chartTitle = "HIVSTAT";

        jdbcTemplate.query("SELECT count(distinct unique_id) total FROM household_member h LEFT JOIN encounter e " +
                "ON e.household_member_id = h.id LEFT JOIN form_data f ON f.encounter_id = e.id " +
                "WHERE h.household_member_type = 2 " +
                "AND (h.details->'hiv_test_result'->>'code' = 'e528faaf-7735-4cbc-a1b4-d110247b7227' " +
                "OR (cast(h.details->'enrolmentStreams'->>'enrolment_stream_number' as integer)  = 1) " +
                "OR f.data->'new_hiv_test_result'->>'code' = 'efb47f13-7760-4483-99f4-5f525ccd1fcd') " +
                "and h.cbo_project_id = ? AND e.archived = 0 ", rs -> {
            positive.add(rs.getLong("total"));
            columnSeries.add(chartUtil.getMainMap(positive, "Positive", null, type, null));
        }, cboProjectId);
        jdbcTemplate.query("SELECT count(distinct unique_id) total FROM household_member h LEFT JOIN encounter e  " +
                "ON e.household_member_id = h.id LEFT JOIN form_data f ON f.encounter_id = e.id  " +
                "WHERE h.household_member_type = 2 " +
                "AND EXTRACT(YEAR from AGE(NOW(), (h.details ->>'dob')::date))  < 18 " +
                "AND (h.details->'hiv_test_result'->>'code' = 'b0ea49c0-8b98-4e50-a9e6-ad3546baeedc'  " +
                "OR (cast(h.details->'enrolmentStreams'->>'enrolment_stream_number' as integer)  = 1)  " +
                "OR f.data->'new_hiv_test_result'->>'code' = '98b58c3d-4102-4c9b-9278-f2c16b3cf786')  " +
                "and h.cbo_project_id = ? AND e.archived = 0 ", rs -> {
            negative.add(rs.getLong("total"));
            columnSeries.add(chartUtil.getMainMap(negative, "Negative", null, type, null));
        }, cboProjectId);

        jdbcTemplate.query("SELECT count(distinct unique_id) total FROM household_member h LEFT JOIN encounter e  " +
                "ON e.household_member_id = h.id LEFT JOIN form_data f ON f.encounter_id = e.id  " +
                "WHERE h.household_member_type = 2 " +
                "AND EXTRACT(YEAR from AGE(NOW(), (h.details ->>'dob')::date))  < 18 " +
                "AND h.details->'hiv_test_result'->>'code' = '7fc0361d-cb3a-424c-80ec-b5c23f53fd3a'  " +
                "OR f.data->'new_hiv_test_result'->>'code' = 'cf9c68ed-84fe-46e8-afa7-7e784a651a81'  " +
                "and h.cbo_project_id = ? AND e.archived = 0  ", rs -> {
            unknown.add(rs.getLong("total"));
            columnSeries.add(chartUtil.getMainMap(unknown, "Unknown", null, type, null));
        }, cboProjectId);
        jdbcTemplate.query("SELECT count(distinct unique_id) total FROM household_member h LEFT JOIN encounter e  " +
                "ON e.household_member_id = h.id LEFT JOIN form_data f ON f.encounter_id = e.id  " +
                "WHERE h.household_member_type = 2 " +
                "AND EXTRACT(YEAR from AGE(NOW(), (h.details ->>'dob')::date))  < 18 " +
                "AND f.data->'new_hiv_test_result'->>'code' = '494411c8-f84e-4f43-b49a-f18c32c8ca0f'  " +
                "and h.cbo_project_id = ? AND e.archived = 0 ", rs -> {
            testNotRequired.add(rs.getLong("total"));
            columnSeries.add(chartUtil.getMainMap(testNotRequired, "Test not required", null, type, null));
        }, cboProjectId);
        jdbcTemplate.query("select COUNT(distinct unique_id) total from " +
                "household_member h left join encounter e on e.household_member_id = h.id " +
                "left join form_data f on f.encounter_id = e.id " +
                "where h.details->'enrolledOnART'->>'display' = 'YES' OR f.data->>'currentlyOnART' = 'yes' and " +
                "EXTRACT(YEAR from AGE(NOW(), (h.details ->>'dob')::date))  < 18 " +
                "and h.cbo_project_id = ? AND e.archived = 0 AND h.household_member_type = 2 ", rs -> {
            onArt.add(rs.getLong("total"));
            columnSeries.add(chartUtil.getMainMap(onArt, "On Art", null, type, null));
        }, cboProjectId);
        jdbcTemplate.query("SELECT COUNT(distinct unique_id) total from household_member hm where id not in (SELECT household_member_id from encounter " +
                "where form_code != '6fb50a2e-e2d3-4534-a641-1abdb75c6fda' and household_member_id is not null) " +
                "AND EXTRACT(YEAR from AGE(NOW(), (hm.details ->>'dob')::date))  < 18 " +
                "and hm.cbo_project_id = ? and hm.archived = 0 AND hm.household_member_type = 2 ", rs -> {
            servActive.add(rs.getLong("total"));
            columnSeries.add(chartUtil.getMainMap(servActive, "Serv Comprehensive", null, type, null));
        }, cboProjectId);

        chartUtil.setXAxis(chartUtil.setHivStatusCategories());
        return chartUtil.buildMainMap(type, chartTitle, "", chartUtil.getXAxis(),
                chartUtil.getYAxis(), columnSeries, null);
    }

    public Object getVcViralLoadCascadeChart() {
        clearChartList();
        Long cboProjectId = userService.getUserWithRoles().get().getCboProjectByCurrentCboProjectId().getId();

        List<Object> eligible = new ArrayList<Object>();
        List<Object> onArt = new ArrayList<Object>();
        List<Object> vlResult = new ArrayList<Object>();
        List<Object> vlSuppressed = new ArrayList<Object>();
        String type = "column";
        String chartTitle = "Viral Load Cascade";


        jdbcTemplate.query("select COUNT(distinct unique_id) total from " +
                "household_member h left join encounter e on e.household_member_id = h.id " +
                "left join form_data f on f.encounter_id = e.id " +
                "where h.details->'enrolledOnART'->>'display' = 'YES' OR f.data->>'currentlyOnART' = 'yes' and " +
                "EXTRACT(YEAR from AGE(NOW(), (h.details ->>'dob')::date))  < 18 " +
                "and h.cbo_project_id = ? AND e.archived = 0 AND h.household_member_type = 2 ", rs -> {
            onArt.add(rs.getLong("total"));
            columnSeries.add(chartUtil.getMainMap(onArt, "On Art", null, type, null));
        }, cboProjectId);
        jdbcTemplate.query("select COUNT(distinct unique_id) total from  " +
                "household_member h left join encounter e on e.household_member_id = h.id " +
                "left join form_data fd on fd.encounter_id = e.id " +
                "where h.details->'enrolledOnART'->>'display' = 'YES' OR fd.data->>'currentlyOnART' = 'yes' and " +
                "EXTRACT(YEAR from AGE(NOW(), (h.details ->>'dob')::date))  < 18 " +
                "and fd.data->'vlTestLastOneYear'->>'code' = '2db39bbb-feb3-4616-b803-96339ee6782e' " +
                "and fd.data->'enrolledOnART'->>'code' = '51dc4de7-8c88-4963-b255-331bf930b7c0'" +
                "and h.cbo_project_id = ? AND e.archived = 0 AND h.household_member_type = 2 ", rs -> {
            eligible.add(rs.getLong("total"));
            columnSeries.add(chartUtil.getMainMap(eligible, "Eligible VL", null, type, null));
        }, cboProjectId);
        jdbcTemplate.query("select COUNT(distinct unique_id) total from  " +
                "household_member h join encounter e on e.household_member_id = h.id " +
                "join form_data fd on fd.encounter_id = e.id " +
                "where h.details->'enrolledOnART'->>'display' = 'YES' OR fd.data->>'currentlyOnART' = 'yes' and " +
                "EXTRACT(YEAR from AGE(NOW(), (h.details ->>'dob')::date))  < 18 " +
                "and fd.data->'vlTestLastOneYear'->>'code' = '2db39bbb-feb3-4616-b803-96339ee6782e' " +
                "and COALESCE(nullif(fd.data->>'whatWasTheResult','')::float, 0) < 1000 and fd.data->>'knowVLTestResult' = 'yes'  " +
                "and h.cbo_project_id = ? AND e.archived = 0 AND h.household_member_type = 2 ", rs -> {
            vlResult.add(rs.getLong("total"));
            columnSeries.add(chartUtil.getMainMap(vlResult, "VL Result", null, type, null));
        }, cboProjectId);
        jdbcTemplate.query("select COUNT(distinct unique_id) total from  " +
                "household_member h join encounter e on e.household_member_id = h.id " +
                "join form_data fd on fd.encounter_id = e.id " +
                "where h.details->'enrolledOnART'->>'display' = 'YES' OR fd.data->>'currentlyOnART' = 'yes' and " +
                "EXTRACT(YEAR from AGE(NOW(), (h.details ->>'dob')::date))  < 18 " +
                "and fd.data->'vlTestLastOneYear'->>'code' = '2db39bbb-feb3-4616-b803-96339ee6782e' " +
                "and COALESCE(nullif(fd.data->>'whatWasTheResult','')::float, 0) >= 0  " +
                "and fd.data->>'knowVLTestResult' = 'yes' " +
                "and h.cbo_project_id = ? AND e.archived = 0 AND h.household_member_type = 2 ", rs -> {
            vlResult.add(rs.getLong("total"));
            columnSeries.add(chartUtil.getMainMap(vlResult, "VL Result", null, type, null));
        }, cboProjectId);

        chartUtil.setXAxis(chartUtil.setVLCategories());
        return chartUtil.buildMainMap(type, chartTitle, "", chartUtil.getXAxis(),
                chartUtil.getYAxis(), columnSeries, null);
    }

    public Map<String, Integer> getMapData() {
        Map<String, Integer> data = new HashMap<>();
        String sql = "";
        jdbcTemplate.query(sql, rs->{
            data.put("ng-ab",  rs.getInt("abia"));
            data.put("ng-ab",  rs.getInt("abia"));
            data.put("ng-ab",  rs.getInt("abia"));
            data.put("ng-ab",  rs.getInt("abia"));
            data.put("ng-ab",  rs.getInt("abia"));
            data.put("ng-ab",  rs.getInt("abia"));
            data.put("ng-ab",  rs.getInt("abia"));
            data.put("ng-ab",  rs.getInt("abia"));
            data.put("ng-ab",  rs.getInt("abia"));
            data.put("ng-ab",  rs.getInt("abia"));
            data.put("ng-ab",  rs.getInt("abia"));
            data.put("ng-ab",  rs.getInt("abia"));
            data.put("ng-ab",  rs.getInt("abia"));
            data.put("ng-kw",  rs.getInt("kwara"));
            data.put("ng-ta",  rs.getInt("taraba"));
            data.put("ng-cr",  rs.getInt("cross_river"));
            data.put("ng-be",  rs.getInt("benue"));
            data.put("ng-by",  rs.getInt("bayelsa"));
            data.put("ng-im",  rs.getInt("imo"));
            data.put("ng-ak",  rs.getInt("akwa_ibom"));
            data.put("ng-bo",  rs.getInt("borno"));
            data.put("ng-ad",  rs.getInt("adamawa"));
            data.put("ng-ke",  rs.getInt("kebbit"));
            data.put("ng-yo",  rs.getInt("yobe"));
            data.put("ng-za",  rs.getInt("zamfara"));
            data.put("ng-so",  rs.getInt("sokoto"));
            data.put("ng-kt",  rs.getInt("katsina"));
            data.put("ng-ri",  rs.getInt("rivers"));

        });

        return data;
    }

    private void clearChartList() {
        series.clear();
        data.clear();
        data.clear();
        xAxis.clear();
        yAxis.clear();
        yAxisTitle.clear();
        columnSeries.clear();
    }
}
