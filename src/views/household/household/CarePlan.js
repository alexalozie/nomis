import React, {useState} from 'react';
import {CButton, CCol, CRow, CCard, CCardBody} from "@coreui/react";
import {CChartBar} from "@coreui/react-chartjs";
import MaterialTable from 'material-table';
import NewCarePlan from './NewCarePlan';
import {toast} from "react-toastify";
import axios from "axios";
import {url} from "../../../api";
import * as CODES from "../../../api/codes";
import FormRendererModal from "../../formBuilder/FormRendererModal";

const CarePlan = (props) => {
    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);
    const showNewCarePlan = () => {
        fetchLastAssessment();
    }
    const [loading, setLoading] = useState(false);
    const [loadingAssessment, setLoadingAssessment]= useState(false);
    const [carePlanList, setCarePlanList] = useState([]);
    const [lastAssessment, setLastAssessment] = useState();
    const [currentForm, setCurrentForm] = useState(false);
    const [showFormModal, setShowFormModal] = useState(false);

    const onSuccess = () => {
        fetchCarePlan();
        toast.success("Form saved successfully!");
        setShowFormModal(false);
    }

    React.useEffect(() => {
        fetchCarePlan();
    }, [props.householdId]);

    const fetchCarePlan = () => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }

        const onError = () => {
            setLoading(false);
            toast.error('Error: Could not fetch household careplans!');
        }
        axios
            .get(`${url}households/${props.householdId}/${CODES.CARE_PLAN}/formData`)
            .then(response => {

                setCarePlanList(response.data.map((x)=>({
                    data: x,
                    dateCreated: x.data && x.data.dateEncounter ? x.data.dateEncounter : null,
                    inProgress:  x.data && x.data.carePlan ? [].concat.apply([], x.data.carePlan.map(c=>c.identifiedIssues)).filter(x => x.followupStatus === 'inProgress').length  : 0,
                    pending:  x.data && x.data.carePlan ? [].concat.apply([], x.data.carePlan.map(c=>c.identifiedIssues)).filter(x => x.followupStatus === 'pending').length : 0,
                    completed: x.data && x.data.carePlan ? [].concat.apply([], x.data.carePlan.map(c=>c.identifiedIssues)).filter(x => x.followupStatus === 'completed').length : 0
                })));
                if(onSuccess){
                    onSuccess();
                }
            })
            .catch(error => {
                    if(onError){
                        onError();
                    }
                }

            );
    }

    const viewForm = (row) => {
        setCurrentForm({ ...row, type: "VIEW", formCode: CODES.CARE_PLAN   });
        setShowFormModal(true);
    }

    const editForm = (row) => {
        setCurrentForm({ ...row, type: "EDIT", formCode: CODES.CARE_PLAN  });
        setShowFormModal(true);
    }

     function fetchLastAssessment() {
        if(loadingAssessment){
            return;
        }
        if(props.householdId) {
            setLoadingAssessment(true);
             axios
                .get(`${url}households/${props.householdId}/${CODES.HOUSEHOLD_ASSESSMENT}/formData`)
                .then(response => {
                    setLoadingAssessment(false);

                    if (response.data && response.data.length == 0) {
                        toast.info('There have been no assessment for this household! Go to the assessment tab to fill an assessment before you can fill a care plan.');
                        setLastAssessment(null);
                        return;
                    }
                    setLastAssessment(response.data[0].data);
                    toggle();
                })
                .catch(error => {
                    setLoadingAssessment(false);
                        toast.error('Error: Could not fetch the last household assessment!');
                    }
                );
        }
    }

    return (
        <CCard>
            <CRow>
                <CCol xs="12" className={"text-right p-4"}>
                    <CButton color={"primary"} onClick={showNewCarePlan}> New Care Plan</CButton>
                </CCol>
            </CRow>

            <CRow className={"pb-3"}>
                <CCol xs={"12"}>
                    <MaterialTable
                        title="Care Plan History"
                        columns={[
                            { title: 'Date Created', field: 'dateCreated' },
                            { title: 'Pending', field: 'pending' },
                            { title: 'In Progress', field: 'inProgress' },
                            { title: 'Completed', field: 'completed' },
                        ]}
                        // data={carePlanList.map((x)=>{
                        //    console.log(x.data)})}
                        data={carePlanList}
                        actions={[
                            {
                                icon: 'edit',
                                tooltip: 'edit Form',
                                onClick: (event, rowData) => editForm(rowData.data)
                            },
                            rowData => ({
                                icon: 'visibility',
                                tooltip: 'View Form',
                                onClick: (event, rowData) => viewForm(rowData.data)
                            })
                        ]}
                        options={{
                            actionsColumnIndex: -1,
                            padding: 'dense',
                        }}
                    />
                </CCol>
            </CRow>
            {carePlanList.length > 0 &&
            <CRow>
                <CCol xs="12">
                    <CCard>
                        <CCardBody>
                            <CChartBar
                                datasets={[
                                    {
                                        label: 'Pending',
                                        backgroundColor: '#f87979',
                                        data: carePlanList.map(x => x.pending)
                                    },
                                    {
                                        label: 'In Progress',
                                        backgroundColor: '#f8a121',
                                        data: carePlanList.map(x => x.inProgress)
                                    },
                                    {
                                        label: 'Completed',
                                        backgroundColor: '#01f83a',
                                        data: carePlanList.map(x => x.completed)
                                    }
                                ]}
                                labels="services"
                                options={{
                                    tooltips: {
                                        enabled: true
                                    },
                                    height: 10
                                }}
                                height={10}
                            />
                        </CCardBody>
                    </CCard>
                </CCol>
            </CRow>
            }
            <FormRendererModal
                showModal={showFormModal}
                setShowModal={setShowFormModal}
                currentForm={currentForm}
                onSuccess={onSuccess}
                //onError={onError}
                options={{modalSize:"xl"}}/>
            <NewCarePlan  modal={modal} toggle={toggle} reloadSearch={fetchCarePlan} householdId={props.householdId} lastAssessment={lastAssessment}/>
        </CCard>
    )
}

export default CarePlan;