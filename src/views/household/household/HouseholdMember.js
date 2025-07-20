import React, {useState, useEffect} from "react";
import { CCol, CRow, CButton, CCard, CCardBody,CCardFooter} from "@coreui/react";
import { Icon} from 'semantic-ui-react'
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import { MdAdd } from 'react-icons/md';
import { Link } from 'react-router-dom';
import NewOvc from './NewOvc';
import NewCareGiver from './NewCareGiver';
import ProvideService from './ProvideService';
import { connect } from "react-redux";
import { fetchAllHouseHoldMembersByHouseholdId } from "./../../../actions/houseHold";
import { Alert } from 'reactstrap';
import {calculateAge} from "./../../../utils/calculateAge"
import {Menu,MenuList,MenuButton,MenuItem,} from "@reach/menu-button";
import "@reach/menu-button/styles.css";

const HouseholdMember = (props) => {
    //Getting the household Id from the props

    const [houseHoldId, sethouseHoldId] = useState(props.houseHoldId);
    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);
    const [modal2, setModal2] = useState(false);
    const toggle2 = () => setModal2(!modal2);
    const [loading, setLoading] = useState('')

    useEffect(() => {
        fetchMembers();
    }, []); //componentDidMount

    const fetchMembers = () => {
        setLoading('true');
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)
        }
        props.fetchAllMember(houseHoldId, onSuccess, onError);
    }

    return (
        <>
            <CRow>
                <CCol xs="12">

                    <Icon name='users' />  Household Members

                    <CButton color={"primary"} className={"float-right mr-1 mb-1"} onClick={toggle2}> New Caregiver</CButton> {" "}
                    <CButton color={"primary"} className={"float-right mr-1 mb-1"} onClick={toggle}> New VC</CButton>{" "}
                    <hr />
                </CCol>
            </CRow>
            <CRow>
                {!loading && props.houseMemberList !==null ? props.houseMemberList.map((member) => (
                        (
                            <CCol xs="12" sm="6" lg="4" key={member.id}>
                                <MemberCard  member={member} householdId={houseHoldId} />
                            </CCol>
                        )
                    )
                    )
                    :
                    "Loading please wait.."
                }

            </CRow>
            {props.houseMemberList.length<=0 ?
                <Alert color="primary">
                    No Household member please click the button above to add a new member to this household
                </Alert>
                :
                ""
            }
            <NewOvc  modal={modal} toggle={toggle} householdId={props.houseHoldId} reload={fetchMembers} totalMembers={props.houseMemberList.filter(x=>x.householdMemberType===2).length} />
            <NewCareGiver  modal={modal2} toggle={toggle2} householdId={props.houseHoldId} reload={fetchMembers} totalMembers={props.houseMemberList.filter(x=>x.householdMemberType===1).length}/>

        </>
    );
}

const MemberCard = (props) => {
    const [modal3, setModal3] = useState(false);
    const [memberId, setMemberId] = useState(null);
    const toggle3 = () => setModal3(!modal3);
    //Get the Member flag array and check for the HIV status
    const flags=  props.member.flags

    function checkHivStatus (flags) {
        for(var i=0; i<flags.length; i++){
            if (flags[i]!==null && flags[i].fieldName==='hiv_test_result' && flags[i].fieldValue==='HIV Positive'){
                return true
            }


        }
    }
    //Provide Service To Household Member Action Button to Load the Modal Form
    const provideServiceModal = (memberId) =>{
        setMemberId(memberId)
        setModal3(!modal3)
    }

    function checkFlag (flags) {
        const careGiverFlag =flags && flags.flags!==null ? flags.flags : []
        if(careGiverFlag!==null && careGiverFlag!==undefined && careGiverFlag.length > 0 ){

            const flagObj = careGiverFlag
            for(var i=0; i<flagObj.length; i++){
                if (flagObj[i]!==null && flagObj[i].fieldName==='beneficiary_status' && flagObj[i].fieldValue==='Transferred'){
                    return true
                }else if(flagObj[i]!==null && flagObj[i].fieldName==='beneficiary_status' && flagObj[i].fieldValue==='Lost to follow-up'){
                    return true
                }else if(flagObj[i]!==null && flagObj[i].fieldName==='beneficiary_status' && flagObj[i].fieldValue==='Migrated'){
                    return true
                }else if(flagObj[i]!==null && flagObj[i].fieldName==='beneficiary_status' && flagObj[i].fieldValue==='Known death'){
                    return true
                }else{
                    return false
                }

            }
        }else{
            return false
        }
    }

    return (
        <>
            <CCard>
                <CCardBody className={'text-center'}>
                    <p className={'text-left'}><b>{props.member.householdMemberType===1?"Caregiver": "VC" || ''}</b>
                        {checkHivStatus(props.member.flags)===true?<MdAdd size="15" style={{color:'red'}}/> : ""}

                    </p>
                    <AccountCircleIcon fontSize="large" style={{padding:'5px', color:props.member.householdMemberType===1?'green':'blue'}} /><br/>
                    <Link color="inherit" to ={{
                        pathname: "/household-member/home", state: props.member.id, householdId:props.householdId }}  ><span>{props.member.details.firstName + " " + props.member.details.lastName } <br /> {props.member.uniqueId}</span></Link><br/>
                    <span>{props.member.details.sex && props.member.details.sex.display ? props.member.details.sex.display  : '' } | {props.member.details.dateOfBirthType === 'estimated' ? '~' : ''} {calculateAge(props.member.details.dob)} </span>

                </CCardBody>
                <CCardFooter className={'text-center'}>

                    <center>
                        <Menu>
                            <MenuButton style={{ backgroundColor:"#3F51B5", color:"#fff", border:"2px solid #3F51B5", borderRadius:"4px" }}>
                                Provide Services <span aria-hidden>▾</span>
                            </MenuButton>
                            <MenuList style={{hover:"#eee"}}>

                                <MenuItem className="blue-highlight" onClick={() =>provideServiceModal(props.member.id)} disabled={checkFlag(props.member)===true ? true :false}>{" "}Provide Service</MenuItem>
                                <Link
                                    to={{pathname: "/household-member/home", state: props.member.id, householdId: props.householdId, activeItem:'forms' }}>
                                    <MenuItem  className="blue-highlight" >{" "}Other Service Form</MenuItem>
                                </Link>
                            </MenuList>
                        </Menu>
                    </center>
                </CCardFooter>
            </CCard>
            <ProvideService  modal={modal3} toggle={toggle3} memberId={memberId} memberType={props.member.householdMemberType} householdId={props.householdId}/>
        </>
    );
}

const mapStateToProps = state => {
    return {
        houseMemberList: state.houseHold.householdMembers
    };
};
const mapActionToProps = {
    fetchAllMember: fetchAllHouseHoldMembersByHouseholdId
};

export default connect(mapStateToProps, mapActionToProps)(HouseholdMember);
