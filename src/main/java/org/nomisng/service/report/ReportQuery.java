package org.nomisng.service.report;

public class ReportQuery {

    public final static String APPLICATION_CODESET_FETCH_QUERY = "SELECT id, codeset_group, display, language, version, code, " +
            "date_created, created_by, date_modified, modified_by, archived, uid " +
            "FROM public.application_codeset WHERE date_modified >= ?";
    public final static String FORM_FETCH_QUERY = "SELECT id, name, code, resource_object, form_type_id, resource_path, created_by, " +
            "date_created, date_modified, modified_by, archived, uuid, version, support_services, form_type, uid " +
            "FROM public.form WHERE date_modified >= ?";
    public final static String ORGANISATION_UNIT_FETCH_QUERY = "";
    public final static String ORGANISATION_UNIT_LEVEL_FETCH_QUERY = "";
    public final static String IMPLEMENTER_FETCH_QUERY = "";
    public final static String CBO_FETCH_QUERY = "";
    public final static String CBO_PROJECT_FETCH_QUERY = "";
    public final static String DONOR_FETCH_QUERY = "";
    public final static String DOMAIN_FETCH_QUERY = "";
    public final static String OVC_FETCH_QUERY = "";
    public final static String FLAG_FETCH_QUERY = "";
    public final static String SCHOOL_FETCH_QUERY = "";
    public final static String DRUG_FETCH_QUERY = "";
    public final static String APPLICATION_USER_CBO_PROJECT_FETCH_QUERY = "";
    public final static String USER_FETCH_QUERY = "";
    public final static String ROLE_FETCH_QUERY = "";
    public final static String PERMISSION_FETCH_QUERY = "";
    public final static String ROLE_PERMISSION_FETCH_QUERY = "";


    public final static String APPLICATION_CODESET_INSERT_QUERY = "";
    public final static String FORM_INSERT_QUERY = "";
    public final static String ORGANISATION_UNIT_INSERT_QUERY = "";
    public final static String ORGANISATION_UNIT_LEVEL_INSERT_QUERY = "";
    public final static String IMPLEMENTER_INSERT_QUERY = "";
    public final static String CBO_INSERT_QUERY = "";
    public final static String CBO_PROJECT_INSERT_QUERY = "";
    public final static String DONOR_INSERT_QUERY = "";
    public final static String DOMAIN_INSERT_QUERY = "";
    public final static String OVC_INSERT_QUERY = "";
    public final static String FLAG_INSERT_QUERY = "";
    public final static String SCHOOL_INSERT_QUERY = "";
    public final static String DRUG_INSERT_QUERY = "";
    public final static String APPLICATION_USER_CBO_PROJECT_INSERT_QUERY = "";
    public final static String USER_INSERT_QUERY = "";
    public final static String ROLE_INSERT_QUERY = "";
    public final static String PERMISSION_INSERT_QUERY = "";
    public final static String ROLE_PERMISSION_INSERT_QUERY = "";
}
