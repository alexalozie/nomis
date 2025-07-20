import React, {useState} from 'react';
import {CButton, CCol, CRow, CCard} from "@coreui/react";
import MaterialTable from 'material-table';
import NewEconomicsAssessment from './NewEconomicsAssessment';
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
            toast.error('Error: Could not fetch  Household Economics Strengthening !');
        }
        axios
            .get(`${url}households/${props.householdId}/${CODES.ECONOMICS_STRENGTHENING}/formData`)
            .then(response => {
                setCarePlanList(response.data)
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
        setCurrentForm({ ...row, type: "VIEW", formCode: CODES.ECONOMICS_STRENGTHENING   });
        setShowFormModal(true);
    }

    const editForm = (row) => {
        setCurrentForm({ ...row, type: "EDIT", formCode: CODES.ECONOMICS_STRENGTHENING  });
        setShowFormModal(true);
    }

    function fetchLastAssessment() {
        if(loadingAssessment){
            return;
        }
        if(props.householdId) {
            setLoadingAssessment(true);
            axios
                .get(`${url}households/${props.householdId}/${CODES.CARE_PLAN}/formData`)
                .then(response => {
                    setLoadingAssessment(false);

                    if (response.data && response.data.length == 0) {
                        toast.info('These household does not have a careplan.');
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
                    <CButton color={"primary"} onClick={showNewCarePlan}> New Household Economics Strengthening</CButton>
                </CCol>
            </CRow>

            <CRow className={"pb-3"}>
                <CCol xs={"12"}>
                    <MaterialTable
                        title=" Household Economics Strengthening History"
                        isLoading={loading}
                        columns={[
                            { title: 'Date Created', field: 'data.date_assessment' },
                            { title: 'Total Score', field: 'data.score' },
                            { title: 'Duration on this project in Month', field: 'data.duration_months' },
                        ]}
                        data={carePlanList}
                        actions={[
                            {
                                icon: 'edit',
                                tooltip: 'edit Form',
                                onClick: (event, rowData) => editForm(rowData.data)
                            },
                            {
                                icon: 'visibility',
                                tooltip: 'View Form',
                                onClick: (event, rowData) => viewForm(rowData.data)
                            }
                        ]}
                        options={{
                            actionsColumnIndex: -1,
                            padding: 'dense',
                        }}
                    />
                </CCol>
            </CRow>
            <FormRendererModal
                showModal={showFormModal}
                setShowModal={setShowFormModal}
                currentForm={currentForm}
                onSuccess={onSuccess}
                //onError={onError}
                options={{modalSize:"xl"}}/>
            <NewEconomicsAssessment  modal={modal} toggle={toggle} reloadSearch={fetchCarePlan} householdId={props.householdId} lastAssessment={lastAssessment}/>
        </CCard>
    )
}

export default CarePlan;