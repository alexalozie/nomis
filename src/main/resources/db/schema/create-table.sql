CREATE OR REPLACE FUNCTION datediff(date_from DATE, date_to DATE) RETURNS INT AS $$
DECLARE age INTERVAL;
BEGIN
CASE lower(type)
        WHEN 'year' THEN
            RETURN date_part('year', date_to) - date_part('year', date_from);
WHEN 'month' THEN
            age := age(date_to, date_from);
RETURN date_part('year', age) * 12 + date_part('month', age);
ELSE
            RETURN (date_to - date_from)::int;
END CASE;
END;
$$
LANGUAGE PLPGSQL;


CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE OR REPLACE FUNCTION uuid_generate_v4(
    )
    RETURNS uuid
    LANGUAGE 'c'
    COST 1
    VOLATILE STRICT PARALLEL SAFE
AS '$libdir/uuid-ossp', 'uuid_generate_v4';

CREATE OR REPLACE FUNCTION uuid_generate_v5(
    namespace uuid,
    name text)
    RETURNS uuid
    LANGUAGE 'c'
    COST 1
    IMMUTABLE STRICT PARALLEL SAFE
AS '$libdir/uuid-ossp', 'uuid_generate_v5';

CREATE OR REPLACE FUNCTION isnumeric(
    text)
    RETURNS boolean
    LANGUAGE 'plpgsql'
    COST 100
    IMMUTABLE STRICT PARALLEL UNSAFE
AS $BODY$
   DECLARE x NUMERIC;   BEGIN       x = $1::NUMERIC;       RETURN TRUE;       EXCEPTION WHEN others THEN           RETURN FALSE;   END;
$BODY$;


CREATE TABLE IF NOT EXISTS implementer
(
    id bigint NOT NULL   DEFAULT nextval('implementer_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    description character varying COLLATE pg_catalog."default",
    code character varying COLLATE pg_catalog."default",
    created_by character varying COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    modified_by character varying COLLATE pg_catalog."default",
    date_modified timestamp without time zone,
    archived integer,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT ip_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS standard_codeset_source
(
    id bigint NOT NULL   DEFAULT nextval('standard_codeset_source_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    description character varying COLLATE pg_catalog."default",
    archived integer,
    created_by character varying(255) COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT standard_codeset_source_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS standard_codeset
(
    id bigint NOT NULL  DEFAULT nextval('standard_codeset_id_seq'::regclass),
    code character varying COLLATE pg_catalog."default",
    description character varying COLLATE pg_catalog."default",
    standard_codeset_source_id bigint,
    archived integer,
    created_by character varying(255) COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT standard_codeset_pkey PRIMARY KEY (id),
    CONSTRAINT standard_codeset_source_id_fk FOREIGN KEY (standard_codeset_source_id)
    REFERENCES standard_codeset_source (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    );


CREATE TABLE IF NOT EXISTS organisation_unit_level
(
    id bigint NOT NULL  DEFAULT nextval('organisation_unit_id_seq'::regclass),
    name character varying(100) COLLATE pg_catalog."default",
    description character varying(300) COLLATE pg_catalog."default",
    archived integer,
    status integer,
    date_created timestamp without time zone,
    created_by character varying COLLATE pg_catalog."default",
    date_modified timestamp without time zone,
    modified_by character varying COLLATE pg_catalog."default",
    parent_organisation_unit_level_id bigint,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT organisational_unit_level_pkey PRIMARY KEY (id),
    CONSTRAINT organisation_unit_level_id_fk FOREIGN KEY (parent_organisation_unit_level_id)
    REFERENCES organisation_unit_level (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID
    );

CREATE TABLE IF NOT EXISTS organisation_unit
(
    id bigint NOT NULL  DEFAULT nextval('organisation_unit_id_seq'::regclass),
    name character varying(1000) COLLATE pg_catalog."default",
    description character varying(1000) COLLATE pg_catalog."default",
    organisation_unit_level_id bigint,
    parent_organisation_unit_id bigint,
    archived integer,
    details varchar(5000),
    date_created timestamp without time zone,
    created_by character varying COLLATE pg_catalog."default",
    date_modified timestamp without time zone,
    modified_by character varying COLLATE pg_catalog."default",
    datim_code character varying(50) COLLATE pg_catalog."default",
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT organisation_unit_pkey PRIMARY KEY (id),
    CONSTRAINT organisation_unit_level_id_fk FOREIGN KEY (organisation_unit_level_id)
    REFERENCES organisation_unit_level (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID
    );

CREATE TABLE IF NOT EXISTS organisation_unit_hierarchy
(
    id bigint NOT NULL  DEFAULT nextval('organisation_unit_hierarchy_id_seq'::regclass),
    organisation_unit_id bigint,
    parent_organisation_unit_id bigint,
    organisation_unit_level_id bigint,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT organisation_unit_hierarchy_pkey PRIMARY KEY (id),
    CONSTRAINT organisation_unit_level_id_fk FOREIGN KEY (organisation_unit_id)
    REFERENCES organisation_unit (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT parent_organisation_unit_level_id_fk FOREIGN KEY (parent_organisation_unit_id)
    REFERENCES organisation_unit (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID
    );

CREATE TABLE IF NOT EXISTS role
(
    id bigint NOT NULL   DEFAULT nextval('role_id_seq'::regclass),
    date_created timestamp without time zone,
    date_modified timestamp without time zone,
    name character varying COLLATE pg_catalog."default",
    created_by character varying COLLATE pg_catalog."default",
    modified_by character varying COLLATE pg_catalog."default",
    code character varying COLLATE pg_catalog."default",
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT role_pkey PRIMARY KEY (id)
    );


CREATE TABLE IF NOT EXISTS permission
(
    id bigint NOT NULL   DEFAULT nextval('permission_id_seq'::regclass),
    description character varying COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    created_by character varying(255) COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    archived integer,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT priviledge_pkey PRIMARY KEY (id),
    CONSTRAINT unique_name UNIQUE (name)
    );

CREATE TABLE IF NOT EXISTS role_permission
(
    role_id bigint NOT NULL,
    permission_id bigint NOT NULL,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT role_permissions_pkey PRIMARY KEY (role_id, permission_id),
    CONSTRAINT permission_id_fk FOREIGN KEY (permission_id)
    REFERENCES permission (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT role_id_fk FOREIGN KEY (role_id)
    REFERENCES role (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );

CREATE TABLE IF NOT EXISTS donor
(
    id bigint NOT NULL  DEFAULT nextval('donor_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    description character varying COLLATE pg_catalog."default",
    code character varying COLLATE pg_catalog."default",
    created_by character varying COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    modified_by character varying COLLATE pg_catalog."default",
    date_modified timestamp without time zone,
    archived integer,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT donor_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS cbo
(
    id bigint NOT NULL   DEFAULT nextval('cbo_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    description character varying COLLATE pg_catalog."default",
    code character varying COLLATE pg_catalog."default",
    created_by character varying COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    modified_by character varying COLLATE pg_catalog."default",
    date_modified timestamp without time zone,
    archived integer,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT cbo_pkey PRIMARY KEY (id)
    );


CREATE TABLE IF NOT EXISTS cbo_project
(
    id bigint NOT NULL DEFAULT nextval('cbo_project_id_seq'::regclass),
    cbo_id bigint,
    donor_id bigint,
    implementer_id bigint,
    archived bigint,
    description character varying COLLATE pg_catalog."default",
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT cbo_donor_organisation_unit_pkey PRIMARY KEY (id),
    CONSTRAINT cbo_id_fk FOREIGN KEY (cbo_id)
    REFERENCES cbo (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT donor_id_fk FOREIGN KEY (donor_id)
    REFERENCES donor (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT ip_id_fk FOREIGN KEY (implementer_id)
    REFERENCES implementer (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );

CREATE TABLE IF NOT EXISTS cbo_project_location
(
    id bigint NOT NULL DEFAULT nextval('cbo_project_location_id_seq'::regclass),
    cbo_project_id bigint,
    organisation_unit_id bigint,
    archived integer,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT cbo_project_id_fk FOREIGN KEY (cbo_project_id)
    REFERENCES cbo_project (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT organisation_unit_id_fk FOREIGN KEY (organisation_unit_id)
    REFERENCES organisation_unit (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );


CREATE TABLE IF NOT EXISTS application_codeset
(
    id bigint NOT NULL DEFAULT nextval('application_codeset_id_seq'::regclass),
    codeset_group character varying COLLATE pg_catalog."default",
    display character varying COLLATE pg_catalog."default",
    language character varying COLLATE pg_catalog."default",
    version character varying COLLATE pg_catalog."default",
    code character varying COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    created_by character varying COLLATE pg_catalog."default",
    date_modified timestamp without time zone,
    modified_by character varying COLLATE pg_catalog."default",
    archived integer,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT application_codeset2_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS application_codeset_standard_codeset
(
    id bigint NOT NULL DEFAULT nextval('application_codeset_standard_codeset_id_seq'::regclass),
    application_codeset_id bigint,
    standard_codeset_id bigint,
    created_by character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_created timestamp without time zone NOT NULL,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT application_codeset_standard_codeset_pkey PRIMARY KEY (id),
    CONSTRAINT application_codeset_id_fk FOREIGN KEY (application_codeset_id)
    REFERENCES application_codeset (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION,
    CONSTRAINT standard_codeset_fk_id FOREIGN KEY (standard_codeset_id)
    REFERENCES standard_codeset (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    );

CREATE TABLE IF NOT EXISTS application_user
(
    id bigint NOT NULL DEFAULT nextval('application_user_id_seq'::regclass),
    user_name character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    other_names character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    phone_number character varying(255) COLLATE pg_catalog."default",
    current_cbo_project_id bigint,
    date_created timestamp without time zone,
    activation_key character varying COLLATE pg_catalog."default",
    date_reset date,
    reset_key character varying COLLATE pg_catalog."default",
    time_uploaded time with time zone,
                               archived integer,
                               created_by character varying(255) COLLATE pg_catalog."default",
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    gender character varying(255) COLLATE pg_catalog."default",
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT user_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS application_user_cbo_project
(
    id bigint NOT NULL DEFAULT nextval('application_user_cbo_project_id_seq'::regclass),
    created_by character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_created timestamp without time zone NOT NULL,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    application_user_id bigint,
    archived integer,
    cbo_project_id bigint,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT application_user_organisation_unit_pkey PRIMARY KEY (id),
    CONSTRAINT application_user_id_fk FOREIGN KEY (application_user_id)
    REFERENCES application_user (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION,
    CONSTRAINT cbo_project_id_fk FOREIGN KEY (cbo_project_id)
    REFERENCES cbo_project (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID
    );

CREATE TABLE IF NOT EXISTS application_user_role
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT application_user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT role_id_fk FOREIGN KEY (role_id)
    REFERENCES role (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT user_id_fk FOREIGN KEY (user_id)
    REFERENCES application_user (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );

CREATE TABLE IF NOT EXISTS domain
(
    id bigint NOT NULL DEFAULT nextval('domain_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    code character varying COLLATE pg_catalog."default" NOT NULL,
    created_by character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_created timestamp without time zone NOT NULL,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    archived integer,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT domain_pkey PRIMARY KEY (id),
    CONSTRAINT unique_uuid UNIQUE (code)
    );

CREATE TABLE IF NOT EXISTS item
(
    item_id bigint NOT NULL DEFAULT nextval('item_id_seq'::regclass),
    description character varying(100) COLLATE pg_catalog."default" NOT NULL,
    unit_measure character varying(45) COLLATE pg_catalog."default",
    max_level integer,
    min_level integer,
    date_last_received date,
    date_last_issued date,
    date_last_audited date,
    balance_type character varying(1) COLLATE pg_catalog."default",
    unit_cost double precision,
    balance integer,
    time_stamp timestamp without time zone,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT item_pkey PRIMARY KEY (item_id)
    );



CREATE TABLE IF NOT EXISTS regimen_type
(
    id bigint NOT NULL DEFAULT nextval('regimen_type_id_seq'::regclass),
    description character varying(100) COLLATE pg_catalog."default" NOT NULL,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT regimentype_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS drug
(
    id bigint NOT NULL DEFAULT nextval('drug_id_seq'::regclass),
    abbrev character varying(45) COLLATE pg_catalog."default" NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    strength character varying(45) COLLATE pg_catalog."default" NOT NULL,
    pack_size integer NOT NULL,
    doseform character varying(45) COLLATE pg_catalog."default",
    morning integer,
    afternoon integer,
    evening integer,
    item_id bigint NOT NULL,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT drug_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS household
(
    id bigint NOT NULL  DEFAULT nextval('household_id_seq'::regclass),
    unique_id character varying COLLATE pg_catalog."default" NOT NULL,
    status character varying COLLATE pg_catalog."default",
    cbo_id bigint,
    created_by character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_created timestamp without time zone NOT NULL,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    archived integer,
    details jsonb,
    cbo_project_id bigint,
    ward_id bigint,
    serial_number bigint,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT household_pkey PRIMARY KEY (id),
    CONSTRAINT cbo_donor_ip_organisation_unit_id_fk FOREIGN KEY (cbo_project_id)
    REFERENCES cbo_project (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT organisation_unit_fk FOREIGN KEY (ward_id)
    REFERENCES organisation_unit (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID
    );

CREATE TABLE IF NOT EXISTS household_member
(
    id bigint NOT NULL   DEFAULT nextval('household_member_id_seq'::regclass),
    household_id bigint,
    created_by character varying(255) COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    household_member_type integer,
    archived integer,
    details jsonb,
    cbo_project_id bigint,
    unique_id character varying COLLATE pg_catalog."default",
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT household_member_pkey PRIMARY KEY (id),
    CONSTRAINT cbo_donor_ip_organisation_unit_id_fk FOREIGN KEY (cbo_project_id)
    REFERENCES cbo_project (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT household_id_fk FOREIGN KEY (household_id)
    REFERENCES household (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID
    );

CREATE TABLE IF NOT EXISTS household_migration
(
    id bigint NOT NULL   DEFAULT nextval('household_migration_id_seq'::regclass),
    zip_code character varying COLLATE pg_catalog."default",
    city character varying COLLATE pg_catalog."default",
    street character varying COLLATE pg_catalog."default",
    landmark character varying COLLATE pg_catalog."default",
    organisation_unit_id bigint,
    household_id bigint,
    created_by character varying(255) COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    active integer,
    cbo_project_id bigint,
    migration_type integer,
    country_id bigint,
    state_id bigint,
    province_id bigint,
    ward_id bigint,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT household_contact_pkey PRIMARY KEY (id),
    CONSTRAINT cbo_donor_ip_organisation_unit_id_fk FOREIGN KEY (cbo_project_id)
    REFERENCES cbo_project (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT fk_household_contact_household FOREIGN KEY (household_id)
    REFERENCES household (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION,
    CONSTRAINT organisation_unit_id_fk FOREIGN KEY (organisation_unit_id)
    REFERENCES organisation_unit (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID
    );

CREATE TABLE IF NOT EXISTS household_unique_id_cbo_project_history
(
    id bigint NOT NULL   DEFAULT nextval('household_unique_id_cbo_project_history_id_seq'::regclass),
    household_id bigint NOT NULL,
    cbo_project_id bigint NOT NULL,
    date_created timestamp without time zone NOT NULL,
    created_by character varying COLLATE pg_catalog."default" NOT NULL,
    date_modified timestamp without time zone NOT NULL,
    modified_by character varying COLLATE pg_catalog."default" NOT NULL,
    archived integer,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT household_unique_id_cbo_project_history_pkey PRIMARY KEY (id),
    CONSTRAINT cbo_project_id_fk FOREIGN KEY (cbo_project_id)
    REFERENCES cbo_project (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION,
    CONSTRAINT household_id_fk FOREIGN KEY (household_id)
    REFERENCES household (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    );


CREATE TABLE IF NOT EXISTS encounter
(
    id bigint NOT NULL   DEFAULT nextval('encounter_id_seq'::regclass),
    date_encounter date,
    household_member_id bigint,
    form_code character varying COLLATE pg_catalog."default",
    created_by character varying(255) COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    archived integer,
    household_id bigint,
    cbo_project_id bigint,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT encounter_pkey PRIMARY KEY (id),
    CONSTRAINT cbo_donor_ip_organisation_unit_id_fk FOREIGN KEY (cbo_project_id)
    REFERENCES cbo_project (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT household_id_fk FOREIGN KEY (household_id)
    REFERENCES household (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT household_member_id_fk FOREIGN KEY (household_member_id)
    REFERENCES household_member (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID
    );

CREATE TABLE IF NOT EXISTS flag
(
    id bigint NOT NULL DEFAULT nextval('flag_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    field_value character varying COLLATE pg_catalog."default",
    datatype integer,
    operator character varying COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    created_by character varying COLLATE pg_catalog."default",
    date_modified timestamp without time zone,
    modified_by character varying COLLATE pg_catalog."default",
    archived integer,
    field_name character varying COLLATE pg_catalog."default",
    continuous boolean,
    type integer,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT flag_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS form
(
    id bigint NOT NULL   DEFAULT nextval('form_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    code character varying COLLATE pg_catalog."default",
    resource_object json,
    form_type_id bigint,
    resource_path character varying COLLATE pg_catalog."default",
    created_by character varying(255) COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    archived integer,
    uuid character varying(255) COLLATE pg_catalog."default",
    version character varying COLLATE pg_catalog."default",
    support_services character varying(255) COLLATE pg_catalog."default",
    form_type integer,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT form_pkey PRIMARY KEY (id),
    CONSTRAINT unique_form_code UNIQUE (code)
    );

CREATE TABLE IF NOT EXISTS form_data
(
    id bigint NOT NULL   DEFAULT nextval('form_data_id_seq'::regclass),
    encounter_id bigint NOT NULL,
    data jsonb,
    created_by character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_created timestamp without time zone NOT NULL,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    archived integer,
    cbo_project_id bigint,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT form_data_pkey PRIMARY KEY (id),
    CONSTRAINT cbo_project_fk FOREIGN KEY (cbo_project_id)
    REFERENCES cbo_project (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT encounter_id_fk FOREIGN KEY (encounter_id)
    REFERENCES encounter (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID
    );

CREATE TABLE IF NOT EXISTS form_flag
(
    id bigint NOT NULL DEFAULT nextval('form_flag_id_seq'::regclass),
    flag_id bigint,
    status integer,
    created_by character varying COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    modified_by character varying COLLATE pg_catalog."default",
    date_modified timestamp without time zone,
    archived integer,
    form_code character varying COLLATE pg_catalog."default",
    applied_to character varying(100) COLLATE pg_catalog."default",
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT flag_form_pkey PRIMARY KEY (id),
    CONSTRAINT form_code_fk FOREIGN KEY (form_code)
    REFERENCES form (code) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    );

CREATE TABLE IF NOT EXISTS member_flag
(
    id bigint NOT NULL   DEFAULT nextval('member_flag_id_seq'::regclass),
    household_member_id bigint,
    household_id bigint,
    flag_id bigint,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT flag_id_fk FOREIGN KEY (flag_id)
    REFERENCES flag (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT household_id_fk FOREIGN KEY (household_id)
    REFERENCES household (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID,
    CONSTRAINT member_id_fk FOREIGN KEY (household_member_id)
    REFERENCES household_member (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );

CREATE TABLE IF NOT EXISTS module
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    active boolean NOT NULL,
    archived integer,
    artifact character varying(255) COLLATE pg_catalog."default",
    base_package character varying(255) COLLATE pg_catalog."default" NOT NULL,
    build_time timestamp without time zone,
    code character varying(255) COLLATE pg_catalog."default",
    data bytea,
    date_installed timestamp without time zone,
    description character varying(255) COLLATE pg_catalog."default",
    installed_by character varying(255) COLLATE pg_catalog."default",
    module_type integer,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    process_config boolean,
    started boolean,
    status integer NOT NULL,
    uninstall boolean,
    version character varying(255) COLLATE pg_catalog."default",
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT module_pkey PRIMARY KEY (id),
    CONSTRAINT uk_f73dsvaor0f4cycvldyt2idf1 UNIQUE (name),
    CONSTRAINT uk_rxqkmhg2y507iqe53my30p6sv UNIQUE (base_package)
    );

CREATE TABLE IF NOT EXISTS acrossmodules
(
    module_id character varying(120) COLLATE pg_catalog."default" NOT NULL,
    installer_id character varying(120) COLLATE pg_catalog."default" NOT NULL,
    module character varying(255) COLLATE pg_catalog."default" NOT NULL,
    installer character varying(255) COLLATE pg_catalog."default" NOT NULL,
    version integer NOT NULL,
    description character varying(500) COLLATE pg_catalog."default",
    created timestamp without time zone NOT NULL,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT pk_across_modules PRIMARY KEY (module_id, installer_id)
    );

CREATE TABLE IF NOT EXISTS menu
(
    id bigint NOT NULL   DEFAULT nextval('menu_id_seq'::regclass),
    archived integer,
    module_id character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    url character varying(255) COLLATE pg_catalog."default",
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT menu_pkey PRIMARY KEY (id),
    CONSTRAINT fk1obs6w04f4923yr9ejg0054cd FOREIGN KEY (module_id)
    REFERENCES module (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );


CREATE TABLE IF NOT EXISTS ovc_service
(
    id bigint NOT NULL   DEFAULT nextval('ovc_service_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    domain_id bigint NOT NULL,
    code character varying COLLATE pg_catalog."default",
    created_by character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_created timestamp without time zone NOT NULL,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    archived integer,
    service_type integer,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT service_pkey PRIMARY KEY (id),
    CONSTRAINT uk_jxqx9q5tehvt65cvnfydu83uw UNIQUE (code),
    CONSTRAINT uk_p678i09huw94k0w1fgkvb9r36 UNIQUE (code),
    CONSTRAINT unique_service_code UNIQUE (code),
    CONSTRAINT domain_id_fk FOREIGN KEY (domain_id)
    REFERENCES domain (id) MATCH SIMPLE
                           ON UPDATE NO ACTION
                           ON DELETE NO ACTION
    NOT VALID
    );

CREATE TABLE IF NOT EXISTS regimen
(
    id bigint NOT NULL  DEFAULT nextval('regimen_id_seq'::regclass),
    description character varying(100) COLLATE pg_catalog."default" NOT NULL,
    composition character varying(100) COLLATE pg_catalog."default",
    regimen_type_id bigint NOT NULL,
    active boolean NOT NULL DEFAULT true,
    priority integer DEFAULT 1,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT regimen_pkey PRIMARY KEY (id),
    CONSTRAINT fk_regimen_regimentype1 FOREIGN KEY (regimen_type_id)
    REFERENCES regimen_type (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );


CREATE TABLE IF NOT EXISTS regimen_drug
(
    id bigint NOT NULL   DEFAULT nextval('regimen_drug_id_seq'::regclass),
    regimen_id bigint NOT NULL,
    drug_id bigint NOT NULL,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT regimendrug_pkey PRIMARY KEY (id),
    CONSTRAINT fk_regimen_has_drug_drug1 FOREIGN KEY (drug_id)
    REFERENCES drug (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk_regimen_has_drug_regimen1 FOREIGN KEY (regimen_id)
    REFERENCES regimen (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );


CREATE TABLE IF NOT EXISTS report_info
(
    id bigint NOT NULL   DEFAULT nextval('report_info_id_seq'::regclass),
    archived integer,
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    template character varying COLLATE pg_catalog."default",
    resource_object jsonb,
    date_created timestamp without time zone,
    created_by character varying COLLATE pg_catalog."default",
    date_modified timestamp without time zone,
    modified_by character varying COLLATE pg_catalog."default",
    code character varying COLLATE pg_catalog."default",
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT report_info_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS visit
(
    id bigint NOT NULL   DEFAULT nextval('visit_id_seq'::regclass),
    start_time time with time zone,
    end_time time with time zone,
                      created_by character varying(255) COLLATE pg_catalog."default",
    date_created timestamp without time zone,
    date_modified timestamp without time zone,
    modified_by character varying(255) COLLATE pg_catalog."default",
    household_id bigint,
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT visit_pkey PRIMARY KEY (id),
    CONSTRAINT household_id_fk FOREIGN KEY (id)
    REFERENCES household (id) MATCH SIMPLE
                  ON UPDATE NO ACTION
                  ON DELETE NO ACTION
    NOT VALID
    );

CREATE TABLE IF NOT EXISTS school
(
    id bigint NOT NULL  DEFAULT nextval('school_id_seq'::regclass),
    created_by character varying(255) COLLATE pg_catalog."default" NOT NULL,
    date_created timestamp without time zone NOT NULL,
    date_modified timestamp without time zone NOT NULL,
    modified_by character varying(255) COLLATE pg_catalog."default" NOT NULL,
    address character varying(255) COLLATE pg_catalog."default",
    archived integer NOT NULL default 0,
    lga_id bigint NOT NULL,
    ward_id bigint NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    state_id bigint NOT NULL,
    type character varying(20),
    uid uuid DEFAULT uuid_generate_v4(),
    CONSTRAINT school_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public.etl_report
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    source_table character varying(80) COLLATE pg_catalog."default",
    source_total_record integer,
    destination_table character varying(80) COLLATE pg_catalog."default",
    destination_total_record integer,
    duration double precision,
    created_date date,
    duplicate text COLLATE pg_catalog."default",
    CONSTRAINT etl_report_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public.refresh_token
(
    id uuid DEFAULT uuid_generate_v4(),
    expiry_date timestamp without time zone NOT NULL,
    token character varying(255) COLLATE pg_catalog."default" NOT NULL,
    user_id bigint,
    CONSTRAINT refresh_token_pkey PRIMARY KEY (id),
    CONSTRAINT uk_r4k4edos30bx9neoq81mdvwph UNIQUE (token),
    CONSTRAINT fkbugkce55bollxfuui14q7i2jj FOREIGN KEY (user_id)
    REFERENCES public.application_user (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );


