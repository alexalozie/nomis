import React, { useState } from "react";
import {
    CCol, CRow, CWidgetIcon, CCard,
    CCardBody,
    CCardHeader, CButton,
} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import { Icon, Label } from 'semantic-ui-react'
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder';
import ChildCareIcon from '@material-ui/icons/ChildCare';
import AccessibilityNewIcon from '@material-ui/icons/AccessibilityNew';
import ServiceHistoryPage from '../widgets/ServiceHistoryPage';
import LinearProgress from "@material-ui/core/LinearProgress";
import { fetchHouseHoldMemberById } from "../../../actions/houseHoldMember";
import { connect } from "react-redux";
import { calculateAge } from "./../../../utils/calculateAge";
import axios from "axios";
import { url } from "../../../api";
import { Link } from 'react-router-dom';
import * as CODES from "../../../api/codes";
import ReAssignCaregiver from './ReAssignCaregiver';
import ReassignCaregiver from "../widgets/ReassignCaregiver";


const Dashboard = (props) => {

    return (
        <>
            <TopDashboardStats household={props.household} member={props.member} />
            <MidDashboardStats member={props.member} household={props.household} fetchingHousehold={props.fetchingHousehold} reload={props.reload} />
            <CRow>
                <CCol xs="12" >
                    <RecentServiceOffered memberId={props.member.id} />
                </CCol>
                {props.member.householdMemberType === 2 &&
                    <CCol xs="12">
                        <RecentPreventiveServiceOffered memberId={props.member.id} />
                    </CCol>
                }
                <CCol xs="12" >
                    <ServiceHistoryPage memberId={props.member.id} />
                </CCol>
            </CRow>

        </>

    )
}

const TopDashboardStats = (props) => {
    const [nutritionAssessment, setNutritionAssessment] = useState();
    const [statusUpdate, setStatusUpdate] = useState();
    const [hivStatus, setHivStatus] = useState();
    const [bmi, setBMI] = useState(" ");
    const [bmiStatus, setBMIStatus] = useState(" ");
    const [color, setColor] = useState("info");

    React.useEffect(() => {
        getColor();
        fetchNutritionAssessments();
        fetchLastStatusUpdate();
    }, [props.member]);

    const getColor = () => {
        if (props.member.details && props.member.details.sex && props.member.details.sex.display.toLowerCase() == "female") {
            setColor("dribbble");
        } else {
            setColor("github");
        }
    }
    const fetchNutritionAssessments = () => {
        axios
            .get(`${url}household-members/${props.member.id}/${CODES.NUTRITIONAL_ASSESSMENT_FORM}/encounters?page=0&size=1`)
            .then(response => {
                if (response.data && response.data.length > 0) {
                    const nutritionAssessment = response.data[0].formData[0].data;
                    setNutritionAssessment(nutritionAssessment);
                    calculateBMI(nutritionAssessment.weight, nutritionAssessment.height)
                }

            })
            .catch(error => {

            }
            );
    }

    const fetchLastStatusUpdate = () => {
        axios
            .get(`${url}household-members/${props.member.id}/${CODES.STATUS_UPDATE_FORM}/encounters?page=0&size=1`)
            .then(response => {
                let hivStats = "";
                if (response.data && response.data.length > 0) {
                    const update = response.data[0].formData[0].data;
                    setStatusUpdate(update);
                    hivStats = update.hivStatus && update.hivStatus.display ? update.hivStatus.display : "-"
                    setHivStatus(hivStats);
                }

                if (!hivStats) {
                    hivStats = props.member.details && props.member.details.hivStatus && props.member.details.hivStatus.display ? props.member.details.hivStatus.display : "-";
                    setHivStatus(hivStats);
                }
            })
            .catch(error => {

            }
            );
    }

    const calculateBMI = (weight, height) => {
        if (weight && height) {
            const bmi = (weight / (height * height)) * 10000;
            if (bmi <= 18.5) {
                setBMIStatus('Underweight');
            }
            else if (bmi > 18.5 && bmi <= 24.9) {
                setBMIStatus('Healthy Weight');
            }
            else if (bmi > 25.0 && bmi <= 29.9) {
                setBMIStatus('Overweight');
            } else {
                setBMIStatus('Obese');
            }
            setBMI(Number(bmi).toFixed(1));
        }
    }
    const isCareGiver = false;
    return (
        <CRow>
            <CCol xs="12" sm="6" lg="4">
                <CWidgetIcon text="HIV Status"
                    header={hivStatus}
                    color={color} iconPadding={false}>
                    <FavoriteBorderIcon />
                </CWidgetIcon>
            </CCol>
            {isCareGiver ?
                <CCol xs="12" sm="6" lg="4">
                    <CWidgetIcon text="Viral Load" header="1000 copies/ml" color={color} iconPadding={false}>
                        <CIcon width={24} name="cil-graph" />
                    </CWidgetIcon>
                </CCol> :
                <CCol xs="12" sm="6" lg="4">
                    <CWidgetIcon text="Weight" header={nutritionAssessment && nutritionAssessment.weight ? (nutritionAssessment.weight + " kg") : "-"} color={color} iconPadding={false}>
                        <CIcon width={24} name="cil-graph" />
                    </CWidgetIcon>
                </CCol>
            }
            {isCareGiver ?
                <CCol xs="12" sm="6" lg="4">
                    <CWidgetIcon text="Children" header="2" color={color} iconPadding={false}>
                        <ChildCareIcon />
                    </CWidgetIcon>
                </CCol>
                :

                <CCol xs="12" sm="6" lg="4">
                    <CWidgetIcon text="BMI" header={bmi + " - " + bmiStatus} color={color} iconPadding={false}>
                        <AccessibilityNewIcon />
                    </CWidgetIcon>
                </CCol>
            }
        </CRow>
    );
}

const MidDashboardStats = (props) => {
    
    const [fetchingMember, setFetchingMember] = useState(true);
    const [caregiver, setCaregiver] = useState(props.member && props.member.details && props.member.details.caregiver ? props.member.details.caregiver : {});
    const [showModal, setShowModal] = React.useState(false);
    const toggleModal = () => setShowModal(!showModal)
    const caregiverId = props.member && props.member.details && props.member.details.caregiver ? props.member.details.caregiver.id : null;
    React.useEffect(() => {
        fetchCaregiver();
    }, [caregiverId]);

    const fetchCaregiver = () => {
        axios
            .get(`${url}household-members/${caregiverId}`)
            .then(response => {
                setFetchingMember(false);
                setCaregiver(response.data);
            })
            .catch(error => {
                setFetchingMember(false);
                setCaregiver(null);
            }

            );
    }

    function checkFlag(flags) {
        const careGiverFlag = flags && flags.flags !== null ? flags.flags : []
        if (careGiverFlag !== null && careGiverFlag !== undefined && careGiverFlag.length > 0) {

            const flagObj = careGiverFlag
            for (var i = 0; i < flagObj.length; i++) {
                if (flagObj[i] !== null && flagObj[i].fieldName === 'beneficiary_status' && flagObj[i].fieldValue === 'Transferred') {
                    return <span style={{ color: "red" }}>Transferred</span>
                } else if (flagObj[i] !== null && flagObj[i].fieldName === 'beneficiary_status' && flagObj[i].fieldValue === 'Lost to follow-up') {
                    return <span style={{ color: "red" }}>Lost to follow-up</span>
                } else if (flagObj[i] !== null && flagObj[i].fieldName === 'beneficiary_status' && flagObj[i].fieldValue === 'Migrated') {
                    return <span style={{ color: "red" }}>Migrated</span>
                } else if (flagObj[i] !== null && flagObj[i].fieldName === 'beneficiary_status' && flagObj[i].fieldValue === 'Known death') {
                    return <span style={{ color: "red" }}>Known death</span>
                } else {
                    return "Active"
                }

            }
        } else {
            return "Active"
        }
    }


    return (
        <>
            {props.member.householdMemberType && props.member.householdMemberType === 1 &&
                <CRow>
                    <CCol xs="12" sm="12" lg="12">
                        <CCard style={{ backgroundColor: "rgb(235 243 232)" }}>
                            <CCardHeader> <Icon name='home' /> Household Information
                                <Link title="Go to household dashboard" to={{ pathname: "/household/home", state: props.household.id }} className="float-right"> <CButton
                                    color="info"
                                    className="float-right">Go to Current Household </CButton></Link>
                            </CCardHeader>
                            <CCardBody>
                                {props.fetchingHousehold &&
                                    <LinearProgress color="primary" thickness={5} className={"mb-2"} />
                                }
                                <span>Household ID: <small>{props.household ? props.household.uniqueId : 'Nil'} </small></span><br />
                                <span>Address: <small> {props.household && props.household.details ? props.household.details.street : 'Nil'}</small></span><br />
                                <span>Date Of Assessment: <small>{props.household && props.household.details ? props.household.details.assessmentDate : 'Nil'}</small> </span><br />
                                <span>Primary Caregiver Name: <small>{props.household && props.household.details && props.household.details.primaryCareGiver ? props.household.details.primaryCareGiver.lastName + ' ' + props.household.details.primaryCareGiver.firstName : 'Nil'}  </small></span><br />
                            </CCardBody>
                        </CCard>
                    </CCol>
                </CRow>

            }
            {props.member.householdMemberType && props.member.householdMemberType !== 1 &&
                <CRow>
                    <CCol xs="12" sm="6" lg="6">
                        <CCard style={{ backgroundColor: "rgb(235 243 232)" }}>
                            <CCardHeader> <Icon name='home' /> Household Information
                                <Link title="Go to household dashboard" to={{ pathname: "/household/home", state: props.household.id }} className="float-right"> <CButton
                                    color="info" variant="outline"
                                    className="float-right">Go to Current Household </CButton></Link>
                            </CCardHeader>
                            <CCardBody>
                                {props.fetchingHousehold &&
                                    <LinearProgress color="primary" thickness={5} className={"mb-2"} />
                                }
                                <span>Household ID: <small>{props.household && props.household.details ? props.household.details.uniqueId : 'Nil'} </small></span><br />
                                <span>Address: <small> {props.household && props.household.details ? props.household.details.street : 'Nil'}</small></span><br />
                                <span>Date Of Assessment: <small>{props.household && props.household.details ? props.household.details.assessmentDate : 'Nil'}</small> </span><br />
                                <span>Primary Caregiver Name: <small>{props.household && props.household.details && props.household.details.primaryCareGiver ? props.household.details.primaryCareGiver.lastName + ' ' + props.household.details.primaryCareGiver.firstName : 'Nil'}  </small></span><br />
                            </CCardBody>
                        </CCard>
                    </CCol>
                    <CCol xs="12" sm="6" lg="6">
                        <CCard style={{ backgroundColor: "rgb(235 243 232)" }}>
                            <CCardHeader> <Icon name='user' /> Caregiver Information
                            {checkFlag(caregiver) !== 'Active' ?
                                    <ReassignCaregiver householdId={props.household.id} memberId={props.member.id} color="info" variant="" className="float-right" reload={props.reload} />
                                    : ""

                                }
                            </CCardHeader>
                            <CCardBody>
                                {fetchingMember &&
                                    <LinearProgress color="primary" thickness={5} className={"mb-2"} />
                                }
                                <span>Caregiver Name: <small>{caregiver && caregiver.details ? caregiver.details.firstName + ' ' + caregiver.details.lastName : 'Nil'}</small></span><br />
                                <span>Age: <small>{caregiver && caregiver.details && caregiver.details.dob ? (caregiver.details.dateOfBirthType === 'estimated' ? '~' : '') + calculateAge(caregiver.details.dob) : 'Nil'}</small></span><br />
                                <span>Sex: <small>{caregiver && caregiver.details && caregiver.details.sex ? caregiver.details.sex.display : 'Nil'}</small> </span><br />
                                <span>Phone Number: <small>{caregiver && caregiver.details ? caregiver.details.mobilePhoneNumber : 'Nil'} </small></span><br />
                                <span>Status: <small>{checkFlag(caregiver)}</small></span><br /> 
                                {/* <span>Caregiver Name: <small>{caregiver && caregiver ? caregiver.firstName + ' ' + caregiver.lastName : 'Nil'}</small></span><br />
                                <span>Age: <small>{caregiver && caregiver && caregiver.dob ? (caregiver.dateOfBirthType === 'estimated' ? '~' : '') + calculateAge(caregiver.dob) : 'Nil'}</small></span><br />
                                <span>Sex: <small>{caregiver && caregiver && caregiver.sex ? caregiver.sex.display : 'Nil'}</small> </span><br />
                                <span>Phone Number: <small>{caregiver && caregiver ? caregiver.mobilePhoneNumber : 'Nil'} </small></span><br />
                                <span>Status: <small>{checkFlag(caregiver)}</small></span><br /> */}
                            </CCardBody>
                        </CCard>
                    </CCol>
                </CRow>
            }
            <ReAssignCaregiver toggleModal={toggleModal} showModal={showModal} />
        </>
    )
}

const RecentServiceOffered = (props) => {
    const [loading, setLoading] = useState(false);
    const [lastService, setLast] = useState([]);
    const [serviceDate, setServiceDate] = useState();

    React.useEffect(() => {
        fetchServices();
    }, [props.memberId]);

    const fetchServices = () => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }

        const onError = () => {
            setLoading(false);
            // toast.error('Error: Could not fetch recent service!');
        }
        axios
            .get(`${url}household-members/${props.memberId}/${CODES.HOUSEHOLD_MEMBER_SERVICE_PROVISION}/encounters`)
            .then(response => {
                if (response.data.length > 0) {
                    const formData = response.data[0].formData;
                    const services = formData[0].data;
                    setServiceDate(services.serviceDate);
                    setLast(services.serviceOffered);
                } else {
                    setLast([]);
                }
                if (onSuccess) {
                    onSuccess();
                }
            })
            .catch(error => {
                if (onError) {
                    onError();
                }
            }

            );
    }
    return (
        <>
            <CCard>
                <CCardHeader>Recent Service Offered
                    <div className="card-header-actions">
                        <b>  {serviceDate || ''}</b>
                    </div>
                </CCardHeader>
                <CCardBody>
                    {loading ?
                        <LinearProgress color="primary" thickness={5} className={"mb-2"} />
                        :
                        <Label.Group color='blue'>
                            {lastService && lastService.length > 0 ? lastService.map(x =>
                                <Label key={x.id}>{x.name}</Label>
                            ) :
                                <Label>No Service has been offered</Label>
                            }

                        </Label.Group>
                    }
                </CCardBody>
            </CCard>
        </>
    );
}

const RecentPreventiveServiceOffered = (props) => {
    const [loading, setLoading] = useState(false);
    const [lastService, setLast] = useState([]);
    const [serviceDate, setServiceDate] = useState();

    React.useEffect(() => {
        fetchServices();
    }, [props.memberId]);

    const fetchServices = () => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }

        const onError = () => {
            setLoading(false);
            // toast.error('Error: Could not fetch recent service!');
        }
        axios
            .get(`${url}household-members/${props.memberId}/${CODES.PREVENTIVE_SERVICE_FORM}/encounters`)
            .then(response => {
                if (response.data.length > 0) {
                    const formData = response.data[0].formData;
                    const services = formData[0].data;
                    setServiceDate(services.encounterDate + " (" + services.cohortName + ")");
                    setLast(services.serviceOffered);
                } else {
                    setLast([]);
                }
                if (onSuccess) {
                    onSuccess();
                }
            })
            .catch(error => {
                if (onError) {
                    onError();
                }
            }

            );
    }
    return (
        <>
            <CCard>
                <CCardHeader>Recent Preventive Service Offered
                    <div className="card-header-actions">
                        <b> {serviceDate || ''}</b>
                    </div>
                </CCardHeader>
                <CCardBody>
                    {loading ?
                        <LinearProgress color="primary" thickness={5} className={"mb-2"} />
                        :
                        <Label.Group color='blue'>
                            {lastService && lastService.length > 0 ? lastService.map(x =>
                                <Label key={x.id}>{x.display}</Label>
                            ) :
                                <Label>No Preventive Service has been offered</Label>
                            }

                        </Label.Group>
                    }
                </CCardBody>
            </CCard>
        </>
    );
}


const mapStateToProps = (state) => {
    return {
        caregiver: state.houseHoldMember.member,
    };
};

const mapActionToProps = {
    fetchHouseHoldMemberById: fetchHouseHoldMemberById
};

export default connect(mapStateToProps, mapActionToProps)(Dashboard);