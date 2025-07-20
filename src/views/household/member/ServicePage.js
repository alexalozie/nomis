import React, { useState } from "react";
import { CCol, CRow, CButton, CAlert, CLink} from "@coreui/react";
import ServiceHistoryPage from "../widgets/ServiceHistoryPage";
import MaterialTable from 'material-table';
import { Tab } from 'semantic-ui-react'
import ProvideService from './ProvideService';

const ServiceHomePage = () => {
    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);
    const [index, setIndex] = React.useState(0);
    const handleTabChange = (e, { activeIndex }) => setIndex(activeIndex);
    const panes = [
        {
            menuItem: 'Household Member Services',
            render: () => <ServicePage setIndex={setIndex}/>,
        },
        {
            menuItem: 'Pending Forms (All unfilled supporting forms)',
            render: () => <PendingForms/>,
        }
    ]

    return (
        <Tab menu={{ secondary: true, pointing: true }} panes={panes} activeIndex={index}  onTabChange={handleTabChange}/>
    );
}

const ServicePage = (props) => {
    const [open, setOpen] = React.useState(false);
    const handleButtonClick = () => setOpen(!open)
    const handleConfirm = () => setOpen(false)
    const handleCancel = () => setOpen(false)
    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);

    return (
        <>
            <CRow>
                <CCol xs="12">
                <CAlert
                    color="success"
                    closeButton
                    show={open}
                >
                    Service Saved!  <CLink className="alert-link" onClick={() => props.setIndex(1)}>Click here to fill all supporting forms!</CLink>.
                </CAlert>
                </CCol>
                <CCol xs="12">
                    <CButton color={"primary"} className={"float-right mr-1 mb-1"} onClick={toggle}> Provide Service</CButton> {" "}
                    {/* <CButton color={"primary"} className={"float-right mr-1 mb-1"} onClick={handleButtonClick}> onSubmit</CButton> {" "} */}
                </CCol>
                <hr />
            </CRow>
            <CRow className={"pt-3"}>
                    <CCol xs="12" >
                        <ServiceHistoryPage isHistory={false} memberId={12} />
                    </CCol>
            </CRow>
            {modal ?
                <ProvideService  modal={modal} toggle={toggle}/>
                :
                ""
            }
            </>
    );
}

const PendingForms = () => {
    return (
        <>
            <CRow className={"pt-3"}>
                <CCol xs="12" >
                    <MaterialTable
                        title="Pending Forms"
                        columns={[
                            { title: 'Form Name', field: 'name' },
                            { title: 'Date', field: 'date' },
                            { title: 'Service Name', field: 'service' },
                        ]}
                        data={[
                            { name: 'Child Educational Performance Assessment Tool', service: 'School performance assessment', date: '12/11/2020 08:35 AM', birthCity: 63 },
                            { name: 'Nutritional Assessment Form', service: 'Nutrition assessment, counselling, and support (NACS)', date: '12/11/2020 08:35 AM', birthCity: 34 },
                        ]}
                        actions={[
                            {
                                icon: 'edit',
                                tooltip: 'Fill Form',
                                onClick: (event, rowData) => alert("You saved " + rowData.name)
                            }
                        ]}
                        options={{
                            actionsColumnIndex: -1,
                            padding: 'dense',
                        }}
                    />
                </CCol>
            </CRow>
        
        </>
    );
}

export default ServiceHomePage;