import React, {useState} from 'react';
import {CButton, CCol, CRow, CCard} from "@coreui/react";
import MaterialTable from 'material-table';
import NewHouseHoldAssessment from './NewHouseHoldAssessment';
import {toast} from "react-toastify";
import axios from "axios";
import {url} from "../../../api";
import * as CODES from './../../../api/codes'
import FormRendererModal from "../../formBuilder/FormRendererModal";

const Assessment = (props) => {
    const [modal, setModal] = useState(false);
    const [loading, setLoading] = useState(false);
    const [assessmentList, setAssessmentList] = useState([]);
    const toggle = () => setModal(!modal);
    const [showFormModal, setShowFormModal] = useState(false);
    const [currentForm, setCurrentForm] = useState(false);

    const onSuccess = () => {
        fetchAssessments();
        toast.success("Form saved successfully!");
        setShowFormModal(false);
    }
    const viewForm = (row) => {
        console.log(row);
        setCurrentForm({ ...row, type: "VIEW", formCode: CODES.HOUSEHOLD_ASSESSMENT   });
        setShowFormModal(true);
    }

    const editForm = (row) => {
        console.log(row);
        setCurrentForm({ ...row, type: "EDIT", formCode: CODES.HOUSEHOLD_ASSESSMENT  });
        setShowFormModal(true);
    }

    React.useEffect(() => {
       fetchAssessments();
    }, [props.householdId]);

    const fetchAssessments = () => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }

        const onError = () => {
            setLoading(false);
            toast.error('Error: Could not fetch  Careplan Achievement Checklist !');
        }
        axios
            .get(`${url}households/${props.householdId}/${CODES.HOUSEHOLD_ASSESSMENT}/formData`)
            .then(response => {
                setAssessmentList(response.data)
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

    return (
        <CCard>
            <CRow >
                <CCol xs="12" className={"text-right p-4"}>
                    <CButton color={"primary"}  onClick={toggle} > New  Careplan Achievement Checklist</CButton>
                </CCol>
            </CRow>

            <CRow>
                <CCol xs={"12"}>
                    <MaterialTable
                        title=" Careplan Achievement Checklist History"
                        isLoading={loading}
                        columns={[
                            { title: 'Date Created', field: 'data.assessmentDate' },
                            { title: 'Total Yes', field: 'data.totalYes' },
                            { title: 'Total No', field: 'data.totalNo' },
                            { title: 'Total N/A', field: 'data.totalNa' },
                        ]}
                        data={assessmentList}
                        actions={[
                            {
                                icon: 'edit',
                                tooltip: 'edit Form',
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
                options={{modalSize:"xl"}}
            />
            <NewHouseHoldAssessment  modal={modal} toggle={toggle} householdId={props.householdId} reloadSearch={fetchAssessments}/>
        </CCard>
    )
}

export default Assessment;