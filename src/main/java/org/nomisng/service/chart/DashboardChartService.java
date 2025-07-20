package org.nomisng.service.chart;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.eclipse.birt.chart.extension.render.Pie;
import org.nomisng.domain.dto.chart.*;
import org.nomisng.repository.FormDataRepository;
import org.nomisng.repository.HouseholdMemberRepository;
import org.nomisng.repository.HouseholdRepository;
import org.nomisng.service.UserService;
import org.nomisng.util.ChartUtil;
import org.nomisng.util.ConstantsUtils;
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
public class DashboardChartService {
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

    public Object getNewlyEnrolledAndPositiveChildren() {
        clearChartList();
        Long cboProjectId = userService.getUserWithRoles().get().getCboProjectByCurrentCboProjectId().getId();

        List<Object> positive = new ArrayList<Object>();
        List<Object> newlyEnrolled = new ArrayList<Object>();
        List<Object> linked = new ArrayList<Object>();

        String type = "column";
        String chartTitle = "Total New VC Enrolment for the Last 6 months";
        LocalDate currentMonth = YearMonth.now().atEndOfMonth();


        newlyEnrolled.add(householdMemberRepository.getNewlyEnrolledByDateRange(currentMonth.minusMonths(6), currentMonth.minusMonths(5),cboProjectId));
        newlyEnrolled.add(householdMemberRepository.getNewlyEnrolledByDateRange(currentMonth.minusMonths(5), currentMonth.minusMonths(4),cboProjectId));
        newlyEnrolled.add(householdMemberRepository.getNewlyEnrolledByDateRange(currentMonth.minusMonths(4), currentMonth.minusMonths(3),cboProjectId));
        newlyEnrolled.add(householdMemberRepository.getNewlyEnrolledByDateRange(currentMonth.minusMonths(3), currentMonth.minusMonths(2),cboProjectId));
        newlyEnrolled.add(householdMemberRepository.getNewlyEnrolledByDateRange(currentMonth.minusMonths(2), currentMonth.minusMonths(1),cboProjectId));
        newlyEnrolled.add(householdMemberRepository.getNewlyEnrolledByDateRange(currentMonth.minusMonths(1), currentMonth, cboProjectId));
        columnSeries.add(chartUtil.getMainMap(newlyEnrolled, "Total New VC", null, type, null));

        positive.add(householdMemberRepository.getPositiveByDateRange(currentMonth.minusMonths(6), currentMonth.minusMonths(5),cboProjectId));
        positive.add(householdMemberRepository.getPositiveByDateRange(currentMonth.minusMonths(5), currentMonth.minusMonths(4),cboProjectId));
        positive.add(householdMemberRepository.getPositiveByDateRange(currentMonth.minusMonths(4), currentMonth.minusMonths(3),cboProjectId));
        positive.add(householdMemberRepository.getPositiveByDateRange(currentMonth.minusMonths(3), currentMonth.minusMonths(2),cboProjectId));
        positive.add(householdMemberRepository.getPositiveByDateRange(currentMonth.minusMonths(2), currentMonth.minusMonths(1),cboProjectId));
        positive.add(householdMemberRepository.getPositiveByDateRange(currentMonth.minusMonths(1), currentMonth, cboProjectId));
        columnSeries.add(chartUtil.getMainMap(positive, "Total New HIV Positive", null, type, null));

        chartUtil.setXAxis(chartUtil.setMonthCategories());
        return chartUtil.buildMainMap(type, chartTitle, "", chartUtil.getXAxis(),
                chartUtil.getYAxis(), columnSeries, null);
    }

    public List<PieChart> getEduSummaryPieChart() {
        Long cboProjectId = userService.getUserWithRoles().get().getCboProjectByCurrentCboProjectId().getId();
        List<PieChart> pieChartList = new ArrayList<>();
        String sql = "SELECT COALESCE(sum (case when hm.details->'sex'->>'code'='976cac0a-5bee-4421-9cbc-0bd8a487f077' " +
                "then 1 else 0 end), 0) male_total, COALESCE(sum(case when hm.details->'sex'->>'code'='3ef366ba-9b2c-48c7-a611-c26e01117763' " +
                "then 1 else 0 end), 0) female_total FROM household_member hm left join encounter e on hm.id = e.household_member_id " +
                "left join form_data fd on e.id = fd.encounter_id " +
                "WHERE hm.details->>'child_in_school' = 'yes' or fd.data->'childInSchool'->>'code'='51dc4de7-8c88-4963-b255-331bf930b7c0' " +
                "AND hm.archived = 0 AND hm.cbo_project_id = ? ";
        jdbcTemplate.query(sql, rs -> {
            PieChart maleData = new PieChart();
            maleData.setName("Male");
            maleData.setY(rs.getInt("male_total"));
            maleData.setSelected(true);
            maleData.setSliced(false);
            pieChartList.add(maleData);

            PieChart femaleData = new PieChart();
            femaleData.setName("Female");
            femaleData.setY(rs.getInt("female_total"));
            femaleData.setSelected(false);
            femaleData.setSliced(false);
            pieChartList.add(femaleData);
        }, cboProjectId);

        return pieChartList;
    }

    public Object getVcByAgeAndSexDisAggregation() {
        clearChartList();
        Long cboProjectId = userService.getUserWithRoles().get().getCboProjectByCurrentCboProjectId().getId();

        List<Object> objMale = new ArrayList<Object>();
        List<Object> objFemale = new ArrayList<Object>();

        String type = "column";
        String chartTitle = "Total number of VC by age and sex categories";

        String sql = "SELECT COALESCE(sum(case when EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) <1 AND details->'sex'->>'display' = 'Female' then 1 else 0 end),0) female1, " +
                "COALESCE(sum(case when EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) >=1 AND EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) <=4 and details->'sex'->>'display' = 'Female' then 1 else 0 end), 0) female2, " +
                "COALESCE(sum(case when EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) >=5 AND EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) <=9 and details->'sex'->>'display' = 'Female' then 1 else 0 end), 0) female3, " +
                "COALESCE(sum(case when EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) >=10 AND EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) <=14 and details->'sex'->>'display' = 'Female' then 1 else 0 end), 0) female4, " +
                "COALESCE(sum(case when EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) >=15 AND EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) <=17 and details->'sex'->>'display' = 'Female' then 1 else 0 end), 0) female5, " +
                "COALESCE(sum(case when EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) <1 AND details->'sex'->>'display' = 'Male' then 1 else 0 end),0) male1, " +
                "COALESCE(sum(case when EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) >=1 AND EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) <=4 and details->'sex'->>'display' = 'Male' then 1 else 0 end), 0) male2, " +
                "COALESCE(sum(case when EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) >=5 AND EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) <=9 and details->'sex'->>'display' = 'Male' then 1 else 0 end), 0) male3, " +
                "COALESCE(sum(case when EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) >=10 AND EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) <=14 and details->'sex'->>'display' = 'Male' then 1 else 0 end), 0) male4, " +
                "COALESCE(sum(case when EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) >=15 AND EXTRACT(YEAR from AGE(NOW(), (details ->>'dob')::date)) <=17 and details->'sex'->>'display' = 'Male' then 1 else 0 end), 0) male5 " +
                "FROM household_member WHERE household_member_type = 2 AND archived = 0 AND cbo_project_id = ? ";

        jdbcTemplate.query(sql, rs -> {
            objMale.add(rs.getInt("male1"));
            objMale.add(rs.getInt("male2"));
            objMale.add(rs.getInt("male3"));
            objMale.add(rs.getInt("male4"));
            objMale.add(rs.getInt("male5"));
            columnSeries.add(chartUtil.getMainMap(objMale, "Male", null, type, null));

            objFemale.add(rs.getInt("female1"));
            objFemale.add(rs.getInt("female2"));
            objFemale.add(rs.getInt("female3"));
            objFemale.add(rs.getInt("female4"));
            objFemale.add(rs.getInt("female5"));
            columnSeries.add(chartUtil.getMainMap(objFemale, "Female", null, type, null));
        }, cboProjectId);

        chartUtil.setXAxis(chartUtil.setAgeCategories());
        return chartUtil.buildMainMap(type, chartTitle, "", chartUtil.getXAxis(),
                chartUtil.getYAxis(), columnSeries, null);
    }

    private String month(int mont) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"};
        if (mont > 12 || mont < 1) {
            return months[1];
        }
        return months[mont];
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
