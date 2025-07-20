package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.nomisng.domain.entity.Cbo;
import org.nomisng.domain.entity.CboProject;
import org.nomisng.domain.entity.Implementer;
import org.nomisng.domain.entity.OrganisationUnit;
import org.nomisng.repository.CboProjectRepository;
import org.nomisng.repository.CboRepository;
import org.nomisng.repository.ImplementerRepository;
import org.nomisng.repository.OrganisationUnitRepository;
import org.nomisng.service.vm.DataHelper;
import org.nomisng.service.vm.ReportFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportFilterService {
    private final ImplementerRepository implementerRepository;
    private final OrganisationUnitRepository organisationUnitRepository;
    private final CboRepository cboRepository;
    private final CboProjectRepository cboProjectRepository;
    private static final int UNARCHIVED = 0;
    private static final Long STATE_LEVEL_ID = 2L;
    private List<ReportFilter> reportFilterList = new ArrayList<>();

    private final DataHelper dataHelper;

    public List<ReportFilter> getImplementerList() {
        List<Implementer> implementerList = implementerRepository.findAll();
        reportFilterList = new ArrayList<>();
        implementerList.forEach(orgUnit -> {
            ReportFilter reportFilter = new ReportFilter();
            reportFilter.setLabel(orgUnit.getName());
            reportFilter.setValue(orgUnit.getId());
            reportFilterList.add(reportFilter);
        });

        return reportFilterList;
    }

    public List<ReportFilter> getStateList() {
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAllByOrganisationUnitLevelIdOrderByNameAsc(STATE_LEVEL_ID);
        return getOrgUnitReportFilters(organisationUnitList);
    }

    public List<ReportFilter> getLgaOrWardListByStateId(Long parentOrgUnitId, Long orgUnitLevelId) {
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAllByParentOrganisationUnitIdAndOrganisationUnitLevelId(parentOrgUnitId, orgUnitLevelId);
        return getOrgUnitReportFilters(organisationUnitList);
    }

    public List<ReportFilter> getLgaOrWardListByStateName(String parentOrgUnitName, Long parentUnitLevelId, Long orgUnitLevelId) {
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.getOrganisationUnitByStateNameAndOrganisationUnitLevelId(parentOrgUnitName, parentUnitLevelId, orgUnitLevelId);
        return getOrgUnitReportFilters(organisationUnitList);
    }

    @NotNull
    private List<ReportFilter> getOrgUnitReportFilters(List<OrganisationUnit> organisationUnitList) {
        reportFilterList = new ArrayList<>();
        organisationUnitList.forEach(orgUnit -> {
            ReportFilter reportFilter = new ReportFilter();
            reportFilter.setLabel(orgUnit.getName());
            reportFilter.setValue(orgUnit.getName());
            reportFilterList.add(reportFilter);
        });
        return reportFilterList;
    }

    @NotNull
    private List<ReportFilter> getCboReportFilters(@NotNull List<Cbo> cboList) {
        reportFilterList = new ArrayList<>();
        cboList.forEach(cbo -> {
            ReportFilter reportFilter = new ReportFilter();
            reportFilter.setLabel(cbo.getName());
            reportFilter.setValue(cbo.getName());
            reportFilterList.add(reportFilter);
        });
        return reportFilterList;
    }

    public List<ReportFilter> getCboList() {
        return getCboReportFilters(cboRepository.findAll());
    }

    public List<ReportFilter> getCurrentUserCbo() {
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        Optional<Cbo> cbo = cboRepository.getCboByCboProjectId(cboProjectId, UNARCHIVED);
        reportFilterList = new ArrayList<>();
        ReportFilter reportFilter = new ReportFilter();
        if (cbo.isPresent()) {
            reportFilter.setValue(cbo.get().getName());
            reportFilter.setLabel(cbo.get().getName());
        }
        reportFilterList.add(reportFilter);
        return reportFilterList;
    }

    public List<ReportFilter> getCboProject(Long cboId) {
        List<CboProject> cboProjectList = cboProjectRepository.findAllByCboIdAndArchived(cboId, UNARCHIVED);
        reportFilterList = new ArrayList<>();
        cboProjectList.forEach(cboProject -> {
            ReportFilter reportFilter = new ReportFilter();
            reportFilter.setLabel(cboProject.getDescription());
            reportFilter.setValue(cboProject.getDescription());
            reportFilterList.add(reportFilter);
        });
        return reportFilterList;
    }

    public List<ReportFilter> getCurrentUserCboProject() {
        Long currentCboProjectId = dataHelper.getCurrentCboProjectId();
        Optional<CboProject> cboProject = cboProjectRepository.findById(currentCboProjectId);
        reportFilterList = new ArrayList<>();
        ReportFilter reportFilter = new ReportFilter();
        if (cboProject.isPresent()) {
            reportFilter.setValue(cboProject.get().getDescription());
            reportFilter.setLabel(cboProject.get().getDescription());
        }
        reportFilterList.add(reportFilter);
        return reportFilterList;
    }

    public List<ReportFilter> getOrganisationUnitByCboProjectId() {
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findAllByCboProjectId(cboProjectId);
        return getOrgUnitReportFilters(organisationUnitList);
    }

    public List<ReportFilter> getStateByCurrentUser() {
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findStateByCboProjectId(cboProjectId);
        return getOrgUnitReportFilters(organisationUnitList);
    }

    public List<ReportFilter> getLgaByCurrentUserAndStateId(Long stateId) {
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        List<OrganisationUnit> organisationUnitList = organisationUnitRepository.findLgaByStateIdAndCboProjectId(stateId, cboProjectId);
        return getOrgUnitReportFilters(organisationUnitList);
    }

}
