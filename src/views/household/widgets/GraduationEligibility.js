import React from 'react';
import {  List, Button } from 'semantic-ui-react'
import {
    CCol, CRow, CCard,
    CCardBody,
    CCardHeader, CAlert
} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import axios from "axios";
import { url } from "../../../api";
import { calculateAge } from "../../../utils/calculateAge";
import { Link } from "react-router-dom";


const GraduationEligibility = (props) => {

    const householdId = props.householdId;
    //  const memberId = props.memberId ? props.memberId : null;
    const [eligible, setEligible] = React.useState(false);
    const [houseMemberList, setHouseMemberList] = React.useState([]);

    React.useEffect(() => {
        if(props.householdId){
        getGraduationEligibilityStatus();
        getVCEligibleToGraduateInHH();
        }
    }, [props.householdId]);

    const getGraduationEligibilityStatus = () => {
        let urlStub = `${url}households/${householdId}/aboutToGraduate`;

        axios
            .get(urlStub)
            .then(response => {
                if (response.data && response.data.length > 0) {
                    setEligible(true);
                }
            })
            .catch(error => {
                setEligible(false);
            }

            );
    }
    const getVCEligibleToGraduateInHH = () => {
        return;
        let urlStub = `${url}household-members/${householdId}/membersAboutToGraduate`;

        axios
            .get(urlStub)
            .then(response => {
                if (response.data && response.data.length > 0) {
                    console.log('setting eligibiliy')
                    setHouseMemberList(response.data);
                }

            })
            .catch(error => {
                setHouseMemberList([]);
            }

            );
    }

    return (
        <>
            {eligible &&
                <CAlert color={"info"}>
                    <b><CIcon width={15} name="cil-home"/> </b>This household is eligible for graduation
                </CAlert>}
                {houseMemberList.length > 0 &&
                <CRow>
                    <CCol xs="12" >
                        <CCard accentColor='danger'>
                            <CCardHeader>VCs Eligible For Graduation
                            </CCardHeader>

                            <CCardBody>
                                <List divided verticalAlign='middle'>
                                    {houseMemberList.map((member) => (
                                        <List.Item>
                                            <List.Content floated='right'>
                                                <Link color="inherit" to={{
                                                    pathname: "/household-member/home", state: member.id, householdId: householdId
                                                }}> <Button> View</Button> </Link>
                                            </List.Content>
                                            <List.Content>{member.details.firstName + " " + member.details.lastName} - {member.details.sex && member.details.sex.display ? member.details.sex.display : "Nil"} </List.Content>
                                            <List.Description>{calculateAge(member.details.dob)} </List.Description>
                                        </List.Item>
                                    ))}
                                </List>
                            </CCardBody>
                        </CCard>
                    </CCol>
                </CRow>
            }
        </>
    )
}

export default GraduationEligibility;