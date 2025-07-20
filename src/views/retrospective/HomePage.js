import React, {useEffect, useState} from 'react';
import { CCol, CRow, CButton, CCard, CCardBody, CCardHeader,} from "@coreui/react";
import {FormGroup,  Label} from "reactstrap";
import Select from "react-select";
import {connect, useDispatch} from "react-redux";
import {fetchAllHouseHoldMembersByHouseholdId, fetchAllHouseHold} from "../../actions/houseHold";
import axios from "axios";
import {url} from "../../api";
import * as CODES from "../../api/codes";
import {toast, ToastContainer} from "react-toastify";
import {calculateAge} from "../../utils/calculateAge";
import { Link } from 'react-router-dom';
import FormRenderer from "../formBuilder/FormRenderer";
import ProvideService from "../household/household/ProvideService";
import NewOvc from "../household/household/NewOvc";
import NewCareGiver from "../household/household/NewCareGiver";
import NewHouseHold from "../household/household/NewHouseHold";
import { DatePicker } from "react-widgets";
import "react-widgets/dist/css/react-widgets.css";
import Moment from "moment";
import momentLocalizer from "react-widgets-moment";
import NewCarePlan from "../household/household/NewCarePlan";
//Dtate Picker package
Moment.locale("en");
momentLocalizer();

const HomePage = (props) => {
    const [hhLoading, setHHLoading] = useState(true);
    const [hmLoading, setHMLoading] = useState(true);
    const [submission, setSubmission] = useState();
    const [loadingEncounter, setShowLoadingEncounter] = useState(false);
    const [selectedHH, setHH] = useState();
    const [selectedHM, setHM] = useState();
    const [formList, setFormList] = React.useState([]);
    const [selectedForm, setSelectedForm] = React.useState();
    const [showFormPage, setShowForm] = React.useState(false);
    const [showServiceModal, setShowServiceModal] = useState(false);
    const [newOvcModal, setShowOvcModal] = React.useState(false);
    const [newCaregiverModal, setShowCaregiverModal] = React.useState(false);
    const [newHouseholdModal, setShowHHModal] = React.useState(false);
    const [serviceDate, setServiceDate] = React.useState();
    const [encounter, setEncounter] = React.useState();
    const [newCarePlanModal, setShowCarePlanModal] = React.useState(false);
    const toggleHousehold = () => setShowHHModal(!newHouseholdModal);
    const toggleOvc = () => setShowOvcModal(!newOvcModal);
    const toggleCaregiver = () => setShowCaregiverModal(!newCaregiverModal);
    const toggleServiceModal = () => setShowServiceModal(!showServiceModal);
    const toggleCarePlan = () => setShowCarePlanModal(!newCarePlanModal);
    // const dispatch = useDispatch();
    // React.useEffect(() => {
    //     //show side-menu when this page loads
    //     dispatch({type: 'MENU_MINIMIZE', payload: true });
    // },[]);
    useEffect(() => {
        fetchHousehold();
        fetchForms();
    }, []); //componentDidMount

    const fetchHousehold = () => {
        setHHLoading(true);
        const onSuccess = () => {
            setHHLoading(false)
        }
        const onError = () => {
            setHHLoading(false)
        }
        props.fetchAllHouseHold(onSuccess, onError);
    }

    const onSelectHousehold = (x) => {
        if(x){
            setHH(x.value);
            fetchMembers(x.value.id);
        } else {
            setHH(null);
            setHM(null);
        }
    }

    const fetchMembers = (householdId) => {
        setHMLoading(true);
        const onSuccess = () => {
            setHMLoading(false)
        }
        const onError = () => {
            setHMLoading(false)
        }
        props.fetchAllHouseHoldMembersByHouseholdId(householdId,onSuccess, onError)
    }

    const fetchForms = () => {
        //setLoading(true);
        const onSuccess = () => {
            //setLoading(false);
        }

        const onError = () => {
            //setLoading(false);
            toast.error('Error: Could not fetch available forms!');
        }
        //&& x.code !== CODES.CARE_PLAN && x.code !== CODES.HOUSEHOLD_ASSESSMENT
        axios
            .get(`${url}forms`)
            .then(response => {
                setFormList(response.data)
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

    const openForm = () => {
        
        if(selectedForm.code === CODES.NEW_HOUSEHOLD){
            toggleHousehold();
            return;
        }

        if(!selectedHH){
            toast.error("Select a household to proceed")
            return;
        }
        if(!selectedForm){
            toast.error("Select a form to proceed")
            return;
        }

        if(selectedForm.code === CODES.VULNERABLE_CHILDREN_ENROLMENT_FORM){
            toggleOvc();
            return;
        }

        if(selectedForm.code === CODES.CARE_GIVER_ENROLMENT_FORM){
            toggleCaregiver();
            return;
        }

        if(selectedForm.code === CODES.CARE_PLAN){
            toggleCarePlan();
            return;
        }
        //check if it is a household member form
        if((selectedForm.code !== CODES.CARE_GIVER_ENROLMENT_FORM
            && selectedForm.code !== CODES.VULNERABLE_CHILDREN_ENROLMENT_FORM
            && selectedForm.code !== CODES.NEW_HOUSEHOLD
            && selectedForm.code !== CODES.HOUSEHOLD_ASSESSMENT
            && selectedForm.code !== CODES.CARE_PLAN)
            && !selectedHM){
            toast.error("Select a household member to proceed")
            return;
        }

        if(selectedForm.code === CODES.HOUSEHOLD_MEMBER_SERVICE_PROVISION){
            if(!serviceDate){
                toast.error("Pick a service date to proceed")
                return;
            }
            fetchEncounterByEncounterDate(serviceDate).then(x=>{
                toggleServiceModal();
                }
            );

            return;
        }
        fetchEncounterByEncounterDate(serviceDate).then(x=>{
            setShowForm(true);
            }
        );


    }

    const onSuccess = () => {
        setShowForm(false);
        toast.success("Form saved successfully!", { appearance: "success" });
    }

    const setDate = (e) => {
        setServiceDate(e );
    }

    async function fetchEncounterByEncounterDate(date){
        //return if household id or member id was not passed. This could be household enrollment form
        if(!selectedHH || !selectedHM){
            return ;
        }
        const encounterDate = Moment(date).format('YYYY-MM-DD');
        setShowLoadingEncounter(true);
        let url_slugs = "";

        if(selectedHH){
            url_slugs = `${url}households/${selectedHH.id}/${selectedForm.code}/encounters?page=0&size=1&dateFrom=${encounterDate}&dateTo=${encounterDate}`;
        }
        if(selectedForm.code !== CODES.HOUSEHOLD_ASSESSMENT && selectedForm.code !== CODES.CARE_PLAN && selectedHM){
            url_slugs = `${url}household-members/${selectedHM.id}/${selectedForm.code}/encounters?page=0&size=1&dateFrom=${encounterDate}&dateTo=${encounterDate}`;
        }

       await  axios.get(url_slugs, {})
            .then(response => {
                //get encounter form data and store it in submission object
                if(response.data && response.data.length > 0){
                    const fd = response.data[0].formData[0];
                    setEncounter(fd);
                    setSubmission( { data: fd.data});
                } else {
                    setEncounter(null);
                    setSubmission(null);
                }
                setShowLoadingEncounter(false);
            }) .catch((error) => {
                toast.error("Error loading encounter, something went wrong");
                setShowLoadingEncounter(false);
            });

        ;

    }

    return (
        <>
            <ToastContainer />
            {/*Restropective form*/}
            {!showFormPage &&
            <CCard>
                <CCardBody>
                    <CRow>
                        <CCol md={6}>
                            <FormGroup>
                                <Label for="household">Search Household*</Label>
                                <Select
                                    required
                                    isClearable
                                    isLoading={hhLoading}
                                    isMulti={false}
                                    value={!selectedHH ? {} : {label: selectedHH.uniqueId, value: selectedHH}}
                                    onChange={onSelectHousehold}
                                    options={props.houseHoldList.map((x) => ({
                                        label: x.uniqueId,
                                        value: x,
                                    }))}
                                />
                            </FormGroup>
                        </CCol>

                        <CCol md={6}>
                            <FormGroup>
                                <Label for="householdM">Search Household Member</Label>
                                <Select
                                    required
                                    isClearable
                                    isLoading={hmLoading}
                                    value={!selectedHM ? {} : {label: selectedHM.details.firstName + " " + selectedHM.details.lastName + " - " + selectedHM.uniqueId, value: selectedHM}}
                                    onChange={(x) => {
                                        if(x) {
                                            setHM(x.value)
                                        }else{
                                            setHM(null)
                                        }
                                    }}
                                    options={!selectedHH ? [] : props.householdMembers.map((x) => ({
                                        label: x.details.firstName + " " + x.details.lastName + " - " + x.uniqueId,
                                        value: x,

                                    }))}
                                />
                            </FormGroup>
                        </CCol>

                        <CCol md={6}>
                            <FormGroup>
                                <Label >Service Date</Label>
                                <DatePicker
                                    name="serviceDate"
                                    id="serviceDate"
                                    defaultValue={serviceDate}
                                    max={new Date()}
                                    required
                                    onChange={setDate}/>
                            </FormGroup>
                        </CCol>
                        <CCol md={6}>
                            <FormGroup>
                                <Label for="form ">Select Form</Label>
                                <Select
                                    required
                                    isClearable
                                    isMulti={false}
                                    value={!selectedForm ? {} : {label:selectedForm.name, value:selectedForm}}
                                    onChange={(x) => {
                                        if(x){
                                            setSelectedForm(x.value)
                                        }else{
                                            setSelectedForm(null);
                                        }

                                    }}
                                    options={formList.map((x) => ({
                                        label: x.name,
                                        value: x,
                                    }))}
                                />
                            </FormGroup>
                        </CCol>
                        <CCol md={2}>
                            <CButton color={"primary"} className={" mt-4"} onClick={openForm}>Open Form </CButton>
                        </CCol>
                    </CRow>
                </CCardBody>
            </CCard>

            }
            {/*Restropective form*/}

            {/*Display household and household member information*/}
            {selectedHH && selectedHH.details &&
                <>
            <CCard style={{backgroundColor: "rgb(235 243 232)"}}>
                <CCardBody>
                    {/*<h4>Household Information</h4>*/}
                    <CRow>
                        <CCol md={4}>
                            <Link title="Go to dashboard" to={{pathname: "/household/home", state: selectedHH.id }}> <b>Household ID: </b> {selectedHH ? selectedHH.uniqueId : 'Nil'}</Link> <br/>
                            <b>Address: </b>{selectedHH.details ? selectedHH.details.street : 'Nil'} <br/>
                            <b>Date Of Assessment: </b>{selectedHH.details.assessmentDate ? Moment(selectedHH.details.assessmentDate).format('DD-MM-YYYY') : 'Nil'}<br/>
                        </CCol>
                        <CCol md={4}>
                            <b>Primary Caregiver
                                Name: </b>{selectedHH.details.primaryCareGiver ? selectedHH.details.primaryCareGiver.lastName + ' ' + selectedHH.details.primaryCareGiver.firstName : 'Nil'}
                            <br/>
                            <b>Phone: </b>{selectedHH.details.primaryCareGiver ? selectedHH.details.primaryCareGiver.mobilePhoneNumber : 'Nil'}<br/>
                            <b>HIV Status: </b>{selectedHH.details.primaryCareGiver && selectedHH.details.primaryCareGiver.LAST_HIV_STATUS && selectedHH.details.primaryCareGiver.LAST_HIV_STATUS.display ? selectedHH.details.primaryCareGiver.LAST_HIV_STATUS.display : (selectedHH.details.primaryCareGiver.LAST_HIV_STATUS === 2 ? "HIV Positive" : "HIV Negative")}<br/>
                            <b>Sex: </b>{selectedHH.details.primaryCareGiver && selectedHH.details.primaryCareGiver.sex && selectedHH.details.primaryCareGiver.sex.display ? selectedHH.details.primaryCareGiver.sex.display : (selectedHH.details.primaryCareGiver.sex === 2 ? "Male" : "Female")}<br/>
                        </CCol>
                        <CCol md={4}>
                            {selectedHH.details.primaryCareGiver && selectedHH.details.primaryCareGiver.dob ?
                                <span>  <b>Age: </b>{calculateAge(selectedHH.details.primaryCareGiver.dob)} | {Moment(selectedHH.details.primaryCareGiver.dob).format('DD-MM-YYYY')} </span> :
                                <span>  <b>Age: </b>Nil</span>
                            }<br/>
                            <b>Marital
                                Status: </b>{selectedHH.details.primaryCareGiver && selectedHH.details.primaryCareGiver.maritalStatus ? selectedHH.details.primaryCareGiver.maritalStatus.display : 'Nil'}<br/>
                            <b>Occupation: </b>{selectedHH.details.primaryCareGiver && selectedHH.details.primaryCareGiver.occupation ? selectedHH.details.primaryCareGiver.occupation : 'Nil'}<br/>
                        </CCol>
                    </CRow>
                </CCardBody>
            </CCard>
                    {selectedHM && selectedHM.details &&
                    //
                     <CCard style={{ backgroundColor: selectedHM.details.sex && selectedHM.details.sex.display.toLowerCase() == "male" ? "#4183c416 ": "#ea4c8912"}}>
                        <CCardBody>
                    {/*< h5 >{selectedHM.householdMemberType === 1?"Caregiver ": "OVC " } Information</h5>*/}
                    <CRow>
                        <CCol md={4}>
                            <Link title={"Go to dashboard"}
                                to={{pathname: "/household-member/home", state: selectedHM.id, householdId:selectedHM.householdId }}> <b>{selectedHM.householdMemberType === 1?"Caregiver ": "OVC " }</b>
                           <b>Unique ID: </b>{selectedHM.uniqueId}</Link> <br/>
                            <b>Name: </b>{selectedHM.details.firstName + ' ' + selectedHM.details.lastName } <br/>
                            {/*<b>Phone: </b>{selectedHM.details.mobilePhoneNumber || 'Nil'}<br/>*/}
                        </CCol>
                        <CCol md={4}>
                            <b>Sex: </b>{selectedHM.details.sex && selectedHM.details.sex.display ? selectedHM.details.sex.display : ""}<br/>
                            {selectedHM.details.dob ?
                                <span> <b>Age:  </b>{calculateAge(  selectedHM.details.dob)} | {Moment(selectedHM.details.dob).format('DD-MM-YYYY')}</span> :
                                <span><b>Age: </b>Nil</span>
                            }<br/>
                            {/*<b>Date Of Assessment: </b> <span>{selectedHM.details.dateOfEnrolment ? Moment(selectedHM.details.dateOfEnrolment).format('DD-MM-YYYY') : 'Nil'}</span>*/}
                        </CCol>
                        {/*<CCol md={4}>*/}
                        {/*    <CButton color={"info"} variant="outline"  onClick={()=>{}}>Provide Service </CButton>*/}
                        {/*</CCol>*/}
                    </CRow>
                        </CCardBody>
                    </CCard>
                    }

            </>
            }
            {/*Display household and household member information*/}

            {/*Display form*/}
            {showFormPage &&
            <CCard>
                <CCardHeader><h3 style={{display:"inline"}}className="text-capitalize">Fill Form - {selectedForm.name}</h3>
                    <CButton color={"primary"} className={"float-right mr-1 mb-1"} onClick={() => setShowForm(false)}>Go back </CButton>
                </CCardHeader>
                <CCardBody>
                    <FormRenderer
                        formCode={selectedForm.code}
                        encounterDate={serviceDate}
                        householdId={selectedHH.id}
                        submission={submission}
                        encounterId={encounter ? encounter.encounterId : ""}
                        householdMemberId = {(selectedForm.code !== CODES.HOUSEHOLD_ASSESSMENT && selectedForm.code !== CODES.CARE_PLAN) && selectedHM && selectedHM.id ? selectedHM.id : null}
                        onSuccess={onSuccess}
                    />
                </CCardBody>
            </CCard>
            }
            {/*Display form*/}

            <ProvideService  modal={showServiceModal} toggle={toggleServiceModal} memberId={selectedHM ? selectedHM.id : ""} memberType={selectedHM ? selectedHM.householdMemberType : ""} householdId={selectedHH ? selectedHH.id : ""}
                             serviceList={submission ? submission.data.serviceOffered : []} serviceDate={submission ? submission.data.serviceDate : Moment(serviceDate).format('YYYY-MM-DD')}
                             formDataId={encounter ? encounter.id : ""} encounterId={encounter ? encounter.encounterId : ""}
            />
            <NewCarePlan  modal={newCarePlanModal} toggle={toggleCarePlan} householdId={selectedHH ? selectedHH.id : ""} />
            {newOvcModal ?
                <NewOvc  modal={newOvcModal} toggle={toggleOvc} householdId={selectedHH ? selectedHH.id : ""} reload={() => fetchMembers(selectedHH.id)} totalMembers={props.householdMembers.filter(x=>x.householdMemberType===2).length} householdMember={selectedHM && selectedHM.householdMemberType == 2 ? selectedHM : null}/>
                : ""
            }

            {newCaregiverModal ?
                <NewCareGiver  modal={newCaregiverModal} toggle={toggleCaregiver} householdId={selectedHH ? selectedHH.id : ""} reload={() => fetchMembers(selectedHH.id)} totalMembers={props.householdMembers.filter(x=>x.householdMemberType===1).length} householdMember={selectedHM && selectedHM.householdMemberType == 1 ? selectedHM : null}/>
                : ""
            }

            {newHouseholdModal ?
            <NewHouseHold  modal={newHouseholdModal} toggle={toggleHousehold} reloadSearch={fetchHousehold} household={selectedHH} />
               : ""}
        </>
    )
}
const mapStateToProps = state => {
    return {
        houseHoldList: state.houseHold.houseHoldList,
        householdMembers: state.houseHold.householdMembers
    };
};
const mapActionToProps = {
    fetchAllHouseHold: fetchAllHouseHold,
    fetchAllHouseHoldMembersByHouseholdId: fetchAllHouseHoldMembersByHouseholdId
}

export default connect(mapStateToProps, mapActionToProps) (HomePage);