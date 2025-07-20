SELECT setval('public.implementer_id_seq',(SELECT max(id) FROM public.implementer));
ALTER TABLE public.implementer ALTER COLUMN id SET DEFAULT nextval('public.implementer_id_seq');

SELECT setval('public.standard_codeset_source_id_seq',(SELECT max(id) FROM public.standard_codeset_source));
ALTER TABLE public.standard_codeset_source ALTER COLUMN id SET DEFAULT nextval('public.standard_codeset_source_id_seq');

SELECT setval('public.standard_codeset_id_seq',(SELECT max(id) FROM public.standard_codeset));
ALTER TABLE public.standard_codeset ALTER COLUMN id SET DEFAULT nextval('public.standard_codeset_id_seq');

SELECT setval('public.organisation_unit_level_id_seq',(SELECT max(id) FROM public.organisation_unit_level));
ALTER TABLE public.organisation_unit_level ALTER COLUMN id SET DEFAULT nextval('public.organisation_unit_level_id_seq');

SELECT setval('public.organisation_unit_id_seq',(SELECT max(id) FROM public.organisation_unit));
ALTER TABLE public.organisation_unit ALTER COLUMN id SET DEFAULT nextval('public.organisation_unit_id_seq');

SELECT setval('public.organisation_unit_hierarchy_id_seq',(SELECT max(id) FROM public.organisation_unit_hierarchy));
ALTER TABLE public.organisation_unit_hierarchy ALTER COLUMN id SET DEFAULT nextval('public.organisation_unit_hierarchy_id_seq');

SELECT setval('public.role_id_seq',(SELECT max(id) FROM public.role));
ALTER TABLE public.role ALTER COLUMN id SET DEFAULT nextval('public.role_id_seq');

SELECT setval('public.permission_id_seq',(SELECT max(id) FROM public.permission));
ALTER TABLE public.permission ALTER COLUMN id SET DEFAULT nextval('public.permission_id_seq');

SELECT setval('public.cbo_id_seq',(SELECT max(id) FROM public.cbo));
ALTER TABLE public.cbo ALTER COLUMN id SET DEFAULT nextval('public.cbo_id_seq');

SELECT setval('public.cbo_project_location_id_seq',(SELECT max(id) FROM public.cbo_project_location));
ALTER TABLE public.cbo_project_location ALTER COLUMN id SET DEFAULT nextval('public.cbo_project_location_id_seq');

SELECT setval('public.cbo_project_id_seq',(SELECT max(id) FROM public.cbo_project));
ALTER TABLE public.cbo_project ALTER COLUMN id SET DEFAULT nextval('public.cbo_project_id_seq');

SELECT setval('public.application_user_id_seq',(SELECT max(id) FROM public.application_user));
ALTER TABLE public.application_user ALTER COLUMN id SET DEFAULT nextval('public.application_user_id_seq');

SELECT setval('public.cbo_project_location_id_seq',(SELECT max(id) FROM public.cbo_project_location));
ALTER TABLE public.cbo_project_location ALTER COLUMN id SET DEFAULT nextval('public.cbo_project_location_id_seq');

SELECT setval('public.application_codeset_id_seq',(SELECT max(id) FROM public.application_codeset));
ALTER TABLE public.application_codeset ALTER COLUMN id SET DEFAULT nextval('public.application_codeset_id_seq');

SELECT setval('public.application_codeset_standard_codeset_id_seq',(SELECT max(id) FROM public.application_codeset_standard_codeset));
ALTER TABLE public.application_codeset_standard_codeset ALTER COLUMN id SET DEFAULT nextval('public.application_codeset_standard_codeset_id_seq');

SELECT setval('public.application_user_cbo_project_id_seq',(SELECT max(id) FROM public.application_user_cbo_project));
ALTER TABLE public.application_user_cbo_project ALTER COLUMN id SET DEFAULT nextval('public.application_user_cbo_project_id_seq');

SELECT setval('public.domain_id_seq',(SELECT max(id) FROM public.domain));
ALTER TABLE public.domain ALTER COLUMN id SET DEFAULT nextval('public.domain_id_seq');

SELECT setval('public.donor_id_seq',(SELECT max(id) FROM public.donor));
ALTER TABLE public.donor ALTER COLUMN id SET DEFAULT nextval('public.donor_id_seq');

SELECT setval('public.item_id_seq',(SELECT max(item_id) FROM public.item));
ALTER TABLE public.item ALTER COLUMN item_id SET DEFAULT nextval('public.item_id_seq');

SELECT setval('public.regimen_type_id_seq',(SELECT max(id) FROM public.regimen_type));
ALTER TABLE public.regimen_type ALTER COLUMN id SET DEFAULT nextval('public.regimen_type_id_seq');

SELECT setval('public.drug_id_seq',(SELECT max(id) FROM public.drug));
ALTER TABLE public.drug ALTER COLUMN id SET DEFAULT nextval('public.drug_id_seq');

SELECT setval('public.household_id_seq',(SELECT max(id) FROM public.household));
ALTER TABLE public.household ALTER COLUMN id SET DEFAULT nextval('public.household_id_seq');

SELECT setval('public.household_member_id_seq',(SELECT max(id) FROM public.household_member));
ALTER TABLE public.household_member ALTER COLUMN id SET DEFAULT nextval('public.household_member_id_seq');

SELECT setval('public.household_migration_id_seq',(SELECT max(id) FROM public.household_migration));
ALTER TABLE public.household_migration ALTER COLUMN id SET DEFAULT nextval('public.household_migration_id_seq');

SELECT setval('public.household_unique_id_cbo_project_history_id_seq',(SELECT max(id) FROM public.household_unique_id_cbo_project_history));
ALTER TABLE public.household_unique_id_cbo_project_history ALTER COLUMN id SET DEFAULT nextval('public.household_unique_id_cbo_project_history_id_seq');

SELECT setval('public.encounter_id_seq',(SELECT max(id) FROM public.encounter));
ALTER TABLE public.encounter ALTER COLUMN id SET DEFAULT nextval('public.encounter_id_seq');

SELECT setval('public.flag_id_seq',(SELECT max(id) FROM public.flag));
ALTER TABLE public.flag ALTER COLUMN id SET DEFAULT nextval('public.flag_id_seq');

SELECT setval('public.form_id_seq',(SELECT max(id) FROM public.form));
ALTER TABLE public.form ALTER COLUMN id SET DEFAULT nextval('public.form_id_seq');

SELECT setval('public.form_data_id_seq',(SELECT max(id) FROM public.form_data));
ALTER TABLE public.form_data ALTER COLUMN id SET DEFAULT nextval('public.form_data_id_seq');

SELECT setval('public.form_flag_id_seq',(SELECT max(id) FROM public.form_flag));
ALTER TABLE public.form_flag ALTER COLUMN id SET DEFAULT nextval('public.form_flag_id_seq');

SELECT setval('public.member_flag_id_seq',(SELECT max(id) FROM public.member_flag));
ALTER TABLE public.member_flag ALTER COLUMN id SET DEFAULT nextval('public.member_flag_id_seq');

SELECT setval('public.menu_id_seq',(SELECT max(id) FROM public.menu));
ALTER TABLE public.menu ALTER COLUMN id SET DEFAULT nextval('public.menu_id_seq');

SELECT setval('public.ovc_service_id_seq',(SELECT max(id) FROM public.ovc_service));
ALTER TABLE public.ovc_service ALTER COLUMN id SET DEFAULT nextval('public.ovc_service_id_seq');

SELECT setval('public.regimen_id_seq',(SELECT max(id) FROM public.regimen));
ALTER TABLE public.regimen ALTER COLUMN id SET DEFAULT nextval('public.regimen_id_seq');

SELECT setval('public.regimen_drug_id_seq',(SELECT max(id) FROM public.regimen_drug));
ALTER TABLE public.regimen_drug ALTER COLUMN id SET DEFAULT nextval('public.regimen_drug_id_seq');

SELECT setval('public.report_info_id_seq',(SELECT max(id) FROM public.report_info));
ALTER TABLE public.report_info ALTER COLUMN id SET DEFAULT nextval('public.report_info_id_seq');

SELECT setval('public.visit_id_seq',(SELECT max(id) FROM public.visit));
ALTER TABLE public.visit ALTER COLUMN id SET DEFAULT nextval('public.visit_id_seq');

SELECT setval('public.school_id_seq',(SELECT max(id) FROM public.school));
ALTER TABLE public.school ALTER COLUMN id SET DEFAULT nextval('public.school_id_seq');

SELECT setval('public.donor_id_seq',(SELECT max(id) FROM public.donor));
ALTER TABLE public.donor ALTER COLUMN id SET DEFAULT nextval('public.donor_id_seq');

