package org.nomisng.web.chart;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.MapDTO;
import org.nomisng.domain.dto.chart.PieChart;
import org.nomisng.repository.CboProjectRepository;
import org.nomisng.repository.HouseholdMemberRepository;
import org.nomisng.repository.HouseholdRepository;
import org.nomisng.service.HouseholdMemberService;
import org.nomisng.service.HouseholdService;
import org.nomisng.service.UserService;
import org.nomisng.service.chart.DashboardChartService;
import org.nomisng.service.chart.HouseholdChartService;
import org.nomisng.web.apierror.BadRequestAlertException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardSummaryResource {
    private String ENTITY_NAME = "Dashboard";
    private final HouseholdService householdService;
    private final HouseholdMemberService householdMemberService;

    private final HouseholdChartService householdChartService;
    private final DashboardChartService dashboardChartService;
    private final UserService userService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> getMemberSummary() {
        Long cboProjectId = userService.getUserWithRoles().get().getCboProjectByCurrentCboProjectId().getId();
        Long totalHousehold = householdService.getTotalHousehold(cboProjectId);
        Long totalGraduated = householdService.getTotalGraduated(cboProjectId);
        Long totalVc = householdMemberService.getTotalVc(cboProjectId);
        Long totalPositive = householdMemberService.getTotalPositive(cboProjectId);
        Long totalLinked = householdMemberService.getTotalLinked(cboProjectId);
        Map<String, Long> summary = new HashMap<>();
        summary.put("totalHousehold", totalHousehold);
        summary.put("totalVc", totalVc);
        summary.put("totalGraduated", totalGraduated);
        summary.put("totalPositive", totalPositive);
        summary.put("totalLinked", totalLinked);
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

    @GetMapping("/edu-summary")
    public ResponseEntity<List<PieChart>> getEduSummary() {
        List<PieChart> pieChartList = dashboardChartService.getEduSummaryPieChart();

        return new ResponseEntity<>(pieChartList, HttpStatus.OK);
    }

//    @GetMapping("/enrolment-summary")
//    public ResponseEntity<List<Map<String, Long>>> getEnrolmentSummary() {
////        if (cboProjectId == null) {
////            throw new BadRequestAlertException("Cbo project id is mandatory.", ENTITY_NAME, "idexists");
////        }
//        Long cboProjectId = userService.getUserWithRoles().get().getCboProjectByCurrentCboProjectId().getId();
//        List<Map<String, Long>> mapDTOList = householdMemberService.getEnrolmentSummary(cboProjectId);
//        return new ResponseEntity<>(mapDTOList, HttpStatus.OK);
//    }

    @GetMapping("/enrolment-chart")
    public ResponseEntity<Object> getNewlyEnrolledAndPositiveChildrenChart() {
        return ResponseEntity.ok(dashboardChartService.getNewlyEnrolledAndPositiveChildren());
    }

    @GetMapping("/vc-by-age-chart")
    public ResponseEntity<Object> getVcByAgeAndSexDisAggregationChart() {
        return ResponseEntity.ok(dashboardChartService.getVcByAgeAndSexDisAggregation());
    }

    @GetMapping("/dashboard-summary")
    public ResponseEntity<Map<String, Long>> getDashboardSummary() {
//        if (cboProjectId == null) {
//            throw new BadRequestAlertException("Cbo project id is mandatory.", ENTITY_NAME, "idexists");
//        }
        Long cboProjectId = userService.getUserWithRoles().get().getCboProjectByCurrentCboProjectId().getId();
        Long totalHousehold = householdChartService.totalHousehold(cboProjectId);
        Long totalCaregiver = householdChartService.totalCaregiver(cboProjectId);
        Long totalVc = householdChartService.totalVulnerableChildren(cboProjectId);
        Long totalPositiveVc = householdChartService.totalPositiveVc(cboProjectId);
        Long totalPositiveCaregiver = householdChartService.totalPositiveCaregiver(cboProjectId);
        Map<String, Long> summary = new HashMap<>();
        summary.put("totalHousehold", totalHousehold);
        summary.put("totalVc", totalVc);
        summary.put("totalCaregiver", totalCaregiver);
        summary.put("totalPositiveVc", totalPositiveVc);
        summary.put("totalPositiveCaregiver", totalPositiveCaregiver);

        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

}
