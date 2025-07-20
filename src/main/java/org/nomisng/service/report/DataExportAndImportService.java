package org.nomisng.service.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class DataExportAndImportService {
    private final JdbcTemplate jdbcTemplate;

    public List<String> getAdminTableName() {
        return null;
    }

    public void importData(FileInputStream fileInputStream) {
       //TODO: 1. import data, 2. read the excel sheets, 3. persist data in the database
    }


    public void exportData(List<String> dataSource) {
        /*
        TODO: 1. loop through the selected choices and map the name to the correct table name
        TODO: 2. Pull the record from the database
        TODO: 3. Write data to excel for export. NB: use table name as sheet name.
         */
        for (String tableName : dataSource) {

        }
    }


    private String getOrgUnitQuery() {
        String query = "SELECT id, name, description, organisation_unit_level_id, parent_organisation_unit_id, " +
                "archived, details, date_created, created_by, date_modified, modified_by, datim_code " +
                "FROM public.organisation_unit;";

        return query;
    }

    private String getOrgUnitLevelQuery() {
        String query = "SELECT id, name, description, archived, status, date_created, created_by, date_modified, " +
                "modified_by, parent_organisation_unit_level_id\n" +
                "FROM public.organisation_unit_level;";

        return query;
    }

    private String getOrgUnitHierarchyQuery() {
        String query = "SELECT id, organisation_unit_id, parent_organisation_unit_id, organisation_unit_level_id " +
                "FROM public.organisation_unit_hierarchy;";

        return query;
    }

    private String getFormQuery() {
        String query = "SELECT id, name, code, resource_object, form_type_id, resource_path, created_by, " +
                "date_created, date_modified, modified_by, archived, uuid, version, support_services, form_type " +
                "FROM public.form;";

        return query;
    }

    private String getFlagQuery() {
        String query = "SELECT id, name, field_value, datatype, operator, date_created, created_by, date_modified, " +
                "modified_by, archived, field_name, continuous, type FROM public.flag;";

        return query;
    }

    private String getFormFlagQuery() {
        String query = "SELECT id, flag_id, status, created_by, date_created, modified_by, date_modified, " +
                "archived, form_code, applied_to FROM public.form_flag;";

        return query;
    }

    //TODO
    private String getCboQuery() {
        String query = "SELECT id, name, description, code, created_by, date_created, modified_by," +
                "date_modified, archived FROM public.cbo;";

        return query;
    }

    private String getCboProjectQuery() {
        String query = "SELECT id, cbo_id, donor_id, implementer_id, archived, description " +
                "FROM public.cbo_project;";

        return query;
    }

    private String getCboProjectLocationQuery() {
        String query = "SELECT id, cbo_project_id, organisation_unit_id, archived " +
                "FROM public.cbo_project_location;";

        return query;
    }

    private String getDomainQuery() {
        String query = "SELECT id, name, code, created_by, date_created, date_modified, modified_by, archived " +
                "FROM public.domain;";

        return query;
    }

    private String getDonorQuery() {
        String query = "SELECT id, name, description, code, created_by, date_created, " +
                "modified_by, date_modified, archived FROM public.donor;";

        return query;
    }
    private String getApplicationCodesetQuery() {
        String query = "SELECT id, codeset_group, display, language, version, code, date_created, " +
                "created_by, date_modified, modified_by, archived " +
                "FROM public.application_codeset;";

        return query;
    }

    private String getApplicationUserQuery() {
        String query = "SELECT id, user_name, password, first_name, last_name, other_names, email, " +
                "phone_number, current_cbo_project_id, date_created, activation_key, date_reset, " +
                "reset_key, time_uploaded, archived, created_by, date_modified, modified_by, gender " +
                "FROM public.application_user;";

        return query;
    }

    private String getApplicationUserCboProjectQuery() {
        String query = "SELECT id, created_by, date_created, date_modified, modified_by, application_user_id, " +
                "archived, cbo_project_id FROM public.application_user_cbo_project;";

        return query;
    }

    private String getApplicationUserRoleQuery() {
        String query = "SELECT user_id, role_id FROM public.application_user_role;";

        return query;
    }

    private String getRoleQuery() {
        String query = "SELECT id, date_created, date_modified, name, created_by, modified_by, code " +
                "FROM public.role;";

        return query;
    }

    private String getRolePermissionQuery() {
        String query = "SELECT role_id, permission_id FROM public.role_permission;";

        return query;
    }

    private String getSchoolQuery() {
        String query = "SELECT id, created_by, date_created, date_modified, modified_by, address, " +
                "archived, lga_id, name, state_id, type FROM public.school;";

        return query;
    }

    private String getPermissionQuery() {
        String query = "SELECT id, description, name, created_by, date_created, date_modified, modified_by, archived " +
                "FROM public.permission;";

        return query;
    }

    private String getOvcServiceQuery() {
        String query = "SELECT id, name, domain_id, code, created_by, date_created, date_modified, " +
                "modified_by, archived, service_type FROM public.ovc_service;";

        return query;
    }

}
