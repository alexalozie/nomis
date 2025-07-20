import React, { useEffect, useState } from "react";
import {
    CCol, CRow, CWidgetIcon, CCard,
    CCardBody,
    CCardHeader, CAlert
} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import { Button, List } from 'semantic-ui-react'
import ServiceHistoryPage from "../widgets/ServiceHistoryPage";
import { connect } from "react-redux";
import { calculateAge } from "../../../utils/calculateAge";
import { Link } from "react-router-dom";
import { fetchAllHouseHoldMembersByHouseholdId, getServiceDueDate, HouseHoldDueForGraduation} from "../../../actions/houseHold";
import LinearProgress from "@material-ui/core/LinearProgress";
import GraduationEligibility from "../widgets/GraduationEligibility";


const HouseholdDashboard = (props) => {
    const [loading, setLoading] = useState(true);
    const [dueServicesLoading, setDueServicesLoading] = useState(true);


    useEffect(() => {
        fetchMembers();
        fetchDueServices();
    }, []);

    const fetchMembers = () => {
        setLoading('true');
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)
        }
        props.fetchAllMember(props.houseHoldId, onSuccess, onError);
    }

    const fetchDueServices = () => {
        setDueServicesLoading('true');
        const loadingDone = () => {
            setDueServicesLoading(false)
        }

        props.getServiceDueDate(props.houseHoldId, loadingDone, loadingDone);
    }

    // const fetchDueGraduation = () => {
    //     setDueGraduationLoading('true');
    //     const loadingDone = () => {
    //         setDueGraduationLoading(false)
    //     }
    //
    //     props.HouseHoldDueForGraduation(props.houseHoldId, loadingDone, loadingDone);
    // }

    return (
        <>
            <CRow>
                <CCol xs="12" sm="6" lg="4">
                    <CWidgetIcon text="Total Beneficiaries" header={props.houseMemberList ? props.houseMemberList. length : ''} color="success" iconPadding={false}>
                        <CIcon width={24} name="cil-people" />
                    </CWidgetIcon>
                </CCol>
                {/*<CCol xs="12" sm="6" lg="3">*/}
                {/*    <CWidgetIcon text="Total Service Provided" header={props.householdServiceHistory.length} color="success"  iconPadding={false}>*/}
                {/*        <CIcon width={24} name="cil-notes"/>*/}
                {/*    </CWidgetIcon>*/}
                {/*</CCol>*/}
                <CCol xs="12" sm="6" lg="4">
                    <CWidgetIcon text="Total HIV Positive" header={props.houseMemberList.filter(x => x.details.hiv_test_result && x.details.hiv_test_result.display === "HIV Positive").length} color="danger" iconPadding={false}>
                        <CIcon width={24} name="cil-user" />
                    </CWidgetIcon>
                </CCol>
                <CCol xs="12" sm="6" lg="4">
                    <CWidgetIcon text="Total Virally Suppressed" header={props.houseMemberList.filter(x => x.details.vlLessThan1000 && x.details.vlLessThan1000.Value === "yes").length} color="warning" iconPadding={false}>
                        <CIcon width={24} name="cil-user" />
                    </CWidgetIcon>
                </CCol>
            </CRow>
            <CRow>
           
                {/*<CCol xs="12">*/}
                {/*    {dueServicesLoading ?*/}
                {/*        <LinearProgress color="primary" thickness={5} className={"mb-2"} />*/}
                {/*        :*/}
                {/*        <>*/}
                {/*            {props.dueServices.length > 0 &&*/}
                {/*                <CAlert color={"warning"}>*/}
                {/*                    <b><CIcon width={15} name="cil-warning" /> </b>There are services past due date in this Household*/}
                {/*                </CAlert>}*/}
                {/*        </>*/}
                {/*    }*/}
                {/*</CCol>*/}

                {/*<CCol xs="12">*/}
                {/*    {dueGraduationLoading ?*/}
                {/*        <LinearProgress color="primary" thickness={5} className={"mb-2"} />*/}
                {/*        :*/}
                {/*        <>*/}
                {/*            {props.dueGraduation.length > 0 &&*/}
                {/*            <CAlert color={"warning"}>*/}
                {/*                <b><CIcon width={15} name="cil-warning" /> </b>This household is eligible for graduation*/}
                {/*            </CAlert>}*/}
                {/*        </>*/}
                {/*    }*/}
                {/*</CCol>*/}

            {/*    <CCol xs="12" >*/}
            {/*<GraduationEligibility householdId={props.household.id}/>*/}
            {/*</CCol>*/}

                <CCol xs="12" >
                    <ServiceHistoryPage householdId={props.household.id} />
                </CCol>

                <CCol xs="12" >
                    <CCard>
                        <CCardHeader>VCs
                        </CCardHeader>

                        <CCardBody>
                            {loading ?
                                <LinearProgress color="primary" thickness={5} className={"mb-2"} />
                                :
                                <List divided verticalAlign='middle'>
                                    {props.houseMemberList.filter(x => x.householdMemberType === 2).length > 0 ? props.houseMemberList.filter(x => x.householdMemberType === 2).map((member) => (
                                        <List.Item>
                                            <List.Content floated='right'>
                                                <Link color="inherit" to={{
                                                    pathname: "/household-member/home", state: member.id, householdId: props.household.id
                                                }}> <Button> View</Button> </Link>
                                            </List.Content>
                                            <List.Content>{member.details.firstName + " " + member.details.lastName} - {member.details.sex && member.details.sex.display ? member.details.sex.display : "Nil"} </List.Content>
                                            <List.Description>{calculateAge(member.details.dob)} </List.Description>
                                        </List.Item>
                                    ))
                                        :
                                        <List.Item>
                                            <List.Content>There are no VCs in this household</List.Content>
                                        </List.Item>
                                    }
                                </List>
                            }
                        </CCardBody>
                    </CCard>
                </CCol>
            </CRow>

        </>

    )
}

const mapStateToProps = (state) => {
    return {
        houseMemberList: state.houseHold.householdMembers,
        householdServiceHistory: state.houseHold.householdServiceHistory,
        dueServices: state.houseHold.dueServices,
        dueGraduation: state.houseHold.dueGraduation
    };
};

const mapActionToProps = {
    fetchAllMember: fetchAllHouseHoldMembersByHouseholdId,
    getServiceDueDate: getServiceDueDate,
    HouseHoldDueForGraduation: HouseHoldDueForGraduation
}
export default connect(mapStateToProps, mapActionToProps)(HouseholdDashboard);