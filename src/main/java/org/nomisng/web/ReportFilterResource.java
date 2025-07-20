package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.service.ReportFilterService;
import org.nomisng.service.vm.ReportFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/report-filters")
@RequiredArgsConstructor
@Slf4j
public class ReportFilterResource {
    private final ReportFilterService reportFilterService;

    @GetMapping("/states")
    public ResponseEntity<List<ReportFilter>> getAllState() {
        log.debug("REST request to get all school");
        List<ReportFilter> reportFilterList = reportFilterService.getStateList();
        return ResponseEntity.ok().body(reportFilterList);
    }

    @GetMapping("/lgaByStateName/{parentOrgUnitName}/{parentUnitLevelId}/{orgUnitLevelId}")
    public ResponseEntity<List<ReportFilter>> getAllLgaOrWardListByStateName(@PathVariable String parentOrgUnitName, @PathVariable Long parentUnitLevelId, @PathVariable Long orgUnitLevelId) {
        List<ReportFilter> reportFilterList = reportFilterService.getLgaOrWardListByStateName(parentOrgUnitName, parentUnitLevelId, orgUnitLevelId);
        return ResponseEntity.ok().body(reportFilterList);
    }

    @GetMapping("/lgaByStateId/{parentOrgUnitId}/{orgUnitLevelId}")
    public ResponseEntity<List<ReportFilter>> getAllLgaOrWardListByStateId(@PathVariable Long parentOrgUnitId, @PathVariable Long orgUnitLevelId) {
        List<ReportFilter> reportFilterList = reportFilterService.getLgaOrWardListByStateId(parentOrgUnitId, orgUnitLevelId);
        return ResponseEntity.ok().body(reportFilterList);
    }

    @GetMapping("/cbo-lists")
    public ResponseEntity<List<ReportFilter>> getAllCbo() {
        List<ReportFilter> reportFilterList = reportFilterService.getCboList();
        return ResponseEntity.ok().body(reportFilterList);
    }

    @GetMapping("/current-user-state")
    public ResponseEntity<List<ReportFilter>> getStateByCurrentUser() {
        List<ReportFilter> reportFilterList = reportFilterService.getStateByCurrentUser();
        return ResponseEntity.ok().body(reportFilterList);
    }

    @GetMapping("/current-user-lga/{stateId}")
    public ResponseEntity<List<ReportFilter>> getStateByCurrentUser(@PathVariable Long stateId) {
        List<ReportFilter> reportFilterList = reportFilterService.getLgaByCurrentUserAndStateId(stateId);
        return ResponseEntity.ok().body(reportFilterList);
    }

    @GetMapping("/current-user-org-unit")
    public ResponseEntity<List<ReportFilter>> getOrganisationUnitByCboProjectId() {
        List<ReportFilter> reportFilterList = reportFilterService.getOrganisationUnitByCboProjectId();
        return ResponseEntity.ok().body(reportFilterList);
    }

    @GetMapping("/current-user-cbo")
    public ResponseEntity<List<ReportFilter>> getCurrentUserCboByCboProject() {
        List<ReportFilter> reportFilterList = reportFilterService.getCurrentUserCbo();
        return ResponseEntity.ok().body(reportFilterList);
    }

    @GetMapping("/current-user-cbo-project")
    public ResponseEntity<List<ReportFilter>> getCurrentUserCboProject() {
        List<ReportFilter> reportFilterList = reportFilterService.getCurrentUserCboProject();
        return ResponseEntity.ok().body(reportFilterList);
    }

    @GetMapping("/cbo-project/{cboId}")
    public ResponseEntity<List<ReportFilter>> getCboProjectsByCboId(@PathVariable Long cboId) {
        List<ReportFilter> reportFilterList = reportFilterService.getCboProject(cboId);
        return ResponseEntity.ok().body(reportFilterList);
    }

}
