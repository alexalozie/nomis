import React from 'react';

const Dashboard = React.lazy(() => import('./views/dashboard/Dashboard'));
const houseHoldAssessment = React.lazy(() => import('./views/household/household/HouseholdSearch'));
const HouseholdHomePage = React.lazy(() => import('./views/household/household/HouseholdHomePage'));
const HouseholdMemberHomePage = React.lazy(() => import('./views/household/member/HomePage'));
const MemberSearch = React.lazy(() => import('./views/household/member/MemberSearch'));
const FormBuilder = React.lazy(() => import('./views/formBuilder/formBuilder'));
const ViewForm = React.lazy(() => import('./views/formBuilder/ViewForm'));
const FormPage = React.lazy(() => import('./views/formBuilder/FormPage'));
const DataVisualisation = React.lazy(() => import('./views/pages/unConstruction'));
const RetrospectivePage = React.lazy(() => import('./views/retrospective/HomePage'));
const HomePage = React.lazy(() => import('./views/visualization/HomePage'));

/* Admin */
const AdminHomePage = React.lazy(() => import('./views/admin/HomePage'));
const UserSetupHomePage = React.lazy(() => import('./views/admin/Users/UserPage'));
const ApplicationCodeSetupHomePage = React.lazy(() => import('./views/admin/ApplicationCodeset/ApplicationCodesetSearch'));
const OrganisationUnitHomepage = React.lazy(() => import('./views/admin/OrganizationUnit/Index'));
const ParentOrganizationUnit = React.lazy(() => import("./views/admin/OrganizationUnit/ParentOrganizationalUnit"));
const ParentOrganizationUnitLevel = React.lazy(() => import("./views/admin/OrganizationUnit/ParentOrganizationalUnitLevel"));
const ProgramSetupHomePage = React.lazy(() => import('./views/admin/DomainManager/DomainManager'));
const DomainServices = React.lazy(() => import('./views/admin/DomainManager/DomainServices'));
const UpdateUser = React.lazy(() => import('./views/admin/Users/UpdateUser'));
const UserRegistration = React.lazy(() => import('./views/admin/Users/UserRegistration'));
const Roles = React.lazy(() => import('./views/admin/Roles/RolesPage'));
const addRole = React.lazy(() => import("./views/admin/Roles/AddRole"))
/*Reporting components*/
const ReportBuilderPage = React.lazy(() => import('./views/admin/Reports/ReportHome'));
const ReportTemplate = React.lazy(() => import("./views/admin/Reports/ReportTemplate"));
const ReportPage = React.lazy(() => import("./views/admin//Reports/ReportingPage"));
const JasperTemplateUpdate = React.lazy(() => import("./views/admin/Reports/JasperTemplateUpdate"));
const ReportView = React.lazy(() => import("./views/admin/Reports/ReportView"));
const CboManager = React.lazy(() => import("./views/admin/CboManager/CboManager"));
const DonorManager = React.lazy(() => import("./views/admin/DonorManager/DonorManager"));
const IpManager = React.lazy(() => import("./views/admin/IpManager/IpManager"));
const CboDonorIpManager = React.lazy(() => import("./views/admin/CboProject/CboProject"));
const SchoolManager = React.lazy(() => import("./views/admin/SchoolManager/Schools"));
const LoginPage = React.lazy(() => import("./views/pages/login/Login"));
const Register = React.lazy(() => import("./views/pages/login/Register"));
const ResetPassword = React.lazy(() => import("./views/pages/login/ResetPassword"));
const FlagHomePage = React.lazy(() => import('./views/admin/Flag/FlagSearchPage'));
const DataBaseSearch = React.lazy(() => import('./views/admin/DataBaseManagement/DataBaseSearch'));
/*const DataBaseSearch = React.lazy(() => import('./views/admin/DataBaseManagement/DataBaseHomePage'));*/
const ImportBase = React.lazy(() => import('./views/admin/DataBaseManagement/ImportBase'));
const importSchool = React.lazy(() => import('./views/admin/SchoolManager/importSchool'));
const schoolHomePage = React.lazy(() => import("./views/admin/SchoolManager/schoolHomePage.js"));
const GeneralImportPage = React.lazy(() => import("./views/admin/AdminExportImport/GeneralImportPage"));
const ImportPage = React.lazy(() => import("./views/admin/AdminExportImport/ImportPage"));
const DatimPage = React.lazy(() => import("./views/admin/DatimReport/DatimPage"));
const DatimImportPage = React.lazy(() => import("./views/admin/DatimReport/DatimImportPage"));
const DatimReportSearch = React.lazy(() => import("./views/admin/DatimReport/DatimReportSearch"));
const GenerateReport = React.lazy(() => import("./views/admin/DatimReport/GenerateReport"));
const generateDatim = React.lazy(() => import("./views/admin/DatimReport/generateDatim"));




const routes = [
  { path: '/', exact: true, name: '', component: LoginPage , isPrivate: true},
  { path: '/', exact: true, name: '', component: Register , isPrivate: true},
  { path: '/resetPassword', name: 'Reset Password', component: ResetPassword },
  { path: '/dashboard', name: 'Dashboard', component: Dashboard, isPrivate: true },
  { path: '/households', name: 'Households', component: houseHoldAssessment , isPrivate: true},
  { path: '/household/home', name: 'Household Home', component: HouseholdHomePage , isPrivate: true},
  { path: '/household-member/home', name: 'Household Member', component: HouseholdMemberHomePage , isPrivate: true},
  { path: '/admin', name: 'Admin Dashboard', component: AdminHomePage , isPrivate: true},
  { path: '/household-members', name: 'Household Members', component: MemberSearch, isPrivate: true },
  { path: '/form-builder', name: 'Form Builder', component: FormBuilder, isPrivate: true },
  { path: '/edit-form', name: 'Edit Form', component: ViewForm, isPrivate: true },
  { path: '/retrospective', name: 'Retrospective', component: RetrospectivePage, isPrivate: true},
  /* Administrative Link */
  { path: "/form-home", name: 'Form Builder', component: FormPage, isPrivate: true},
  { path: "/users", name: 'User Management', component: UserSetupHomePage, isPrivate: true},
  { path: "/application-codeset-home", name: 'Application Codeset', component: ApplicationCodeSetupHomePage, isPrivate: true},
  { path: "/program-setup-home", name: 'Domain Setup', component: ProgramSetupHomePage, isPrivate: true},
  { path: "/organisation-unit-home", name: 'Organisation Unit', component: OrganisationUnitHomepage, isPrivate: true},
  { path: "/admin-parent-organization-unit", name: 'Organisation Unit', component: ParentOrganizationUnit, isPrivate: true},
  { path: "/admin-parent-organization-unit-level", name: 'Organisation Unit', component: ParentOrganizationUnitLevel, isPrivate: true},
  { path: "/domain-service", name: 'Domain Services', component: DomainServices, isPrivate: true},
  { path: "/user-registration", name: 'User Registration', component: UserRegistration, isPrivate: true},
  { path: "/user-update", name: 'User Update', component: UpdateUser, isPrivate: true},
  { path: "/roles", name: 'Role', component: Roles, isPrivate: true},
  { path: "/add-role", name: 'Role', component: addRole, isPrivate: true},
  { path: "/report-builder", name: 'Report ', component: ReportBuilderPage, isPrivate: true},
  { path: "/build-report", name: 'Build Report', component: ReportTemplate, isPrivate: true},
  { path: "/template-update", name: 'Build Report', component: JasperTemplateUpdate, isPrivate: true},
  { path: "/report", name: 'Report', component: ReportPage, isPrivate: true},
  { path: "/report-view", name: 'Report View', component: ReportView, isPrivate: true},
  { path: "/cbo", name: 'CBO ', component: CboManager, isPrivate: true} ,
  { path: "/donor", name: 'Donor ', component: DonorManager, isPrivate: true} ,
  { path: "/ip", name: 'Implementing Partner ', component: IpManager, isPrivate: true} ,
  //{ path: "/donor-ip", name: 'Donor-Implementing Partner ', component: DonorIpManager} ,
  { path: "/cbo-donor-ip", name: 'CBO Project ', component: CboDonorIpManager, isPrivate: true} ,
  { path: "/schoolHomePage", name: 'Schools ', component: schoolHomePage, isPrivate: true} ,
  { path: "/visualisation", name: 'Data Visualisation', component: DataVisualisation, isPrivate: true} ,
  { path: "/flags", name: 'Flag Search', component: FlagHomePage, isPrivate: true},
  { path: "/data-base-home", name: 'Data Base Home', component: DataBaseSearch, isPrivate: true},
  { path: "/data-home-page", name: 'Data Base Home', component: DataBaseSearch, isPrivate: true},
  { path: "/import-page", name: 'Data Base Home', component: ImportBase, isPrivate: true},
  { path: "/import-school", name: 'Schools', component: importSchool, isPrivate: true},
  { path: "/visualization-home", name: 'Data Visualisation', component: HomePage, isPrivate: true},
  { path: "/general-export", name: 'General Import', component: GeneralImportPage, isPrivate: true},
  { path: "/import-pages", name: 'Import page', component: ImportPage, isPrivate: true},
  { path: "/datim-pages", name: 'Datim page', component: DatimPage, isPrivate: true},
  { path: "/datim-import", name: 'Datim page', component: DatimImportPage, isPrivate: true},
  { path: "/datim-report", name: 'Datim Report', component: DatimReportSearch, isPrivate: true},
  { path: "/generate-report", name: 'Report', component: GenerateReport, isPrivate: true},
  { path: "/datim-flat-file", name: 'DATIM Flat File', component: generateDatim, isPrivate: true},
];
export default routes;
