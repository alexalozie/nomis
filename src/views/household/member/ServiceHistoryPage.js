import React, {useState, useEffect} from 'react';
import MaterialTable from 'material-table';
import { connect } from "react-redux";
import moment from "moment";
import {fetchAllHouseHoldMemberServiceHistory} from "../../../actions/houseHoldMember";
import FormRendererModal from "../../formBuilder/FormRendererModal";
import { toast, ToastContainer } from "react-toastify";
import ProvideService from "../household/ProvideService";
import {CButton, CCardHeader, CCol, CRow} from "@coreui/react";
import{HOUSEHOLD_MEMBER_SERVICE_PROVISION} from "../../../api/codes";

const ServiceHistoryPage = (props) => {
    const [showFormModal, setShowFormModal] = useState(false);
    const [showServiceModal, setShowServiceModal] = useState(false);
    const [currentForm, setCurrentForm] = useState(false);
    const [loading, setLoading] = useState(false);
    const [provideServiceData, setProvideServiceData] = useState({serviceList:[], serviceDate: null, formDataId: 0, encounterId:0, type:"UPDATE"});
    const memberId = props.member.id;

    const toggleServiceModal = () => setShowServiceModal(!showServiceModal);

    useEffect(() => {
        fetchHouseholdServiceHistory();
    }, [memberId]); //componentDidMount

    const fetchHouseholdServiceHistory = () => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }
        const onError = () => {
            setLoading(false);
        }
        props.fetchAllHouseHoldMemberServiceHistory(memberId, onSuccess, onError);
    }

    const onSuccess = () => {
        fetchHouseholdServiceHistory();
        toast.success("Form saved successfully!");
        setShowFormModal(false);
    }
    const viewForm = (row) => {
        //check if it is service provision page (static html) else use formio view
        if(row.formCode == HOUSEHOLD_MEMBER_SERVICE_PROVISION){
            const formData = row.formData[0];
            const selectedService = {serviceList : formData.data.serviceOffered, serviceDate: formData.data.serviceDate, type:"VIEW", formDataId: formData.id};
            console.log(selectedService);
            setProvideServiceData(selectedService);
            toggleServiceModal();
            return;
        }
        setCurrentForm({ ...row, type: "VIEW", encounterId: row.id });
        setShowFormModal(true);
    }

    const editForm = (row) => {
        //check if it is service provision page (static html) else use formio update
        if(row.formCode == HOUSEHOLD_MEMBER_SERVICE_PROVISION){
            const formData = row.formData[0];
            const selectedService = {serviceList : formData.data.serviceOffered, serviceDate: formData.data.serviceDate, type:"UPDATE", formDataId: formData.id, encounterId: formData.encounterId};
            console.log(selectedService);
            setProvideServiceData(selectedService);
            toggleServiceModal();
            return;
        }
        setCurrentForm({ ...row, type: "EDIT", encounterId: row.id });
        setShowFormModal(true);
    }
   
    return (
        <>
            <ToastContainer />
            <CRow>
            {/*    <CCol md={12}>*/}
            {/*<CButton color={"primary"} className={"float-right mr-1 mb-1"} onClick={toggleServiceModal}>Provide Service For Member</CButton>*/}
            {/*    </CCol>*/}
                <CCol md={12}>
                <MaterialTable
                    title="Services Form History"
                    columns={[
                        {title: 'Form Name', field: 'formName'},
                        {title: 'Date', field: 'date'},
                        // { title: 'Name', field: 'memberName' },
                    ]}
                    isLoading={loading}
                    data={props.memberServiceHistory.map(service => ({...service,
                        formName: service.formName,
                        date: service.dateEncounter ? moment(service.dateEncounter).format('LL') : '',
                        memberName: service.householdMemberId
                    }))}
                    actions={[
                        {
                            icon: 'edit',
                            tooltip: 'Edit Form',
                            onClick: (event, rowData) => editForm(rowData)
                        },
                        rowData => ({
                            icon: 'visibility',
                            tooltip: 'View Form',
                            onClick: (event, rowData) => viewForm(rowData)

                        })
                    ]}
                    options={{
                        actionsColumnIndex: -1,
                        padding: 'dense',
                        header: true
                    }}
                />
                </CCol>
            </CRow>
            <ProvideService  modal={showServiceModal} toggle={toggleServiceModal} memberId={props.member.id} householdId={props.householdId} reloadSearch={fetchHouseholdServiceHistory}
                             serviceList={provideServiceData.serviceList} serviceDate={provideServiceData.serviceDate}
                                formDataId={provideServiceData.formDataId} encounterId={provideServiceData.encounterId} type={provideServiceData.type}/>
               <FormRendererModal
                   householdMemberId={props.member.id}
                   showModal={showFormModal}
                   setShowModal={setShowFormModal}
                   currentForm={currentForm}
                   onSuccess={onSuccess}
                   //onError={onError}
                   options={{modalSize:"xl"}}
               />
        </>
    );

}

const mapStateToProps = state => {
    return {
        memberServiceHistory: state.houseHoldMember.serviceHistory
    };
  };
  const mapActionToProps = {
      fetchAllHouseHoldMemberServiceHistory: fetchAllHouseHoldMemberServiceHistory
  };
  
  export default connect(mapStateToProps, mapActionToProps)(ServiceHistoryPage);
