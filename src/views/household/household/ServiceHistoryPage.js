import React, {useState, useEffect} from 'react';
import MaterialTable from 'material-table';
import { connect } from "react-redux";
import {deleteHousehold, fetchAllHouseHoldServiceHistory} from "./../../../actions/houseHold";
import moment from "moment";
import FormRendererModal from "../../formBuilder/FormRendererModal";
import {toast} from "react-toastify";
import{HOUSEHOLD_MEMBER_SERVICE_PROVISION} from "../../../api/codes";
import { deleteProvidedService } from "./../../../actions/householdCaregiverService";
import ProvideService from "./ProvideService";

const ServiceHistoryPage = (props) => {

    const [loading, setLoading] = useState(false);
    const householdId = props.householdId;
    const [showFormModal, setShowFormModal] = useState(false);
    const [currentForm, setCurrentForm] = useState(false);
    const [showServiceModal, setShowServiceModal] = useState(false);
    const [provideServiceData, setProvideServiceData] = useState({serviceList:[], serviceDate: null, formDataId: 0, encounterId:0, type:"UPDATE"});
    const toggleServiceModal = () => setShowServiceModal(!showServiceModal);

    useEffect(() => {
        fetchHouseholdServiceHistory(householdId);
    }, [householdId]);

    const fetchHouseholdServiceHistory = (householdId) => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }
        const onError = () => {
            setLoading(false);
        }
        props.fetchAllHouseHoldServiceHistory(householdId, onSuccess, onError);
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
            const selectedService = {serviceList : formData.data.serviceOffered, serviceDate: formData.data.serviceDate, type:"VIEW", formDataId: formData.id, memberId: row.householdMemberId};
            console.log(selectedService);
            setProvideServiceData(selectedService);
            toggleServiceModal();
            return;
        }
        setCurrentForm({ ...row, type: "VIEW", encounterId: row.id });
        setShowFormModal(true);
    }

    const editForm = (row) => {
        // console.log("THis ID"+row.id)
        //check if it is service provision page (static html) else use formio update
        if(row.formCode == HOUSEHOLD_MEMBER_SERVICE_PROVISION){
            const formData = row.formData[0];
            const selectedService = {serviceList : formData.data.serviceOffered, serviceDate: formData.data.serviceDate, type:"UPDATE", formDataId: formData.id, encounterId: formData.encounterId, memberId: row.householdMemberId};
            console.log(selectedService);
            setProvideServiceData(selectedService);
            toggleServiceModal();
            return;
        }
        setCurrentForm({ ...row, type: "EDIT", encounterId: row.id });
        setShowFormModal(true);
    }

    // const deleteForm = row => {
    //     fetchHouseholdServiceHistory();
    //     const onSuccess = () =>{
    //         toast.success("Service deleted successfully");
    //     }
    //     if (window.confirm(`Are sure you want to delete this record? ${row.id}`))
    //         props.deleteProvidedService(row.id, onSuccess, () => toast.error("An error occurred, service did not deleted successfully"));
    // }

    return (
<>
            <MaterialTable
                title="Services Form History"
                columns={[
                    { title: 'Form Name', field: 'formName' },
                    { title: 'Date', field: 'date' },
                      { title: 'Name', field: 'memberName' },
                ]}
                isLoading={loading}
                data={props.householdServiceHistory.map(service => ({...service,
                    formName: service.formName,
                    date: service.dateEncounter ? moment(service.dateEncounter).format('LL') : '',
                    memberName: service.firstName ? (service.firstName + " " + service.lastName) : ''
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

                    }),
                    // rowData => ({
                    //     icon: 'delete',
                    //     tooltip: 'delete Form',
                    //     onClick: (event, rowData) => deleteForm(rowData)
                    //
                    // })
                ]}
                options={{
                    actionsColumnIndex: -1,
                    padding: 'dense',
                    header: true
                }}
            />
    <ProvideService  modal={showServiceModal} toggle={toggleServiceModal} memberId={provideServiceData.memberId} householdId={props.householdId} reloadSearch={fetchHouseholdServiceHistory}
                     serviceList={provideServiceData.serviceList} serviceDate={provideServiceData.serviceDate}
                     formDataId={provideServiceData.formDataId} encounterId={provideServiceData.encounterId} type={provideServiceData.type}/>

    <FormRendererModal
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
        householdServiceHistory: state.houseHold.householdServiceHistory
    };
  };
  const mapActionToProps = {
    fetchAllHouseHoldServiceHistory: fetchAllHouseHoldServiceHistory,
      deleteProvidedService: deleteProvidedService
  };
  
  export default connect(mapStateToProps, mapActionToProps)(ServiceHistoryPage);
