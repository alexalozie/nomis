import React, {useState} from 'react';
import {Header, Menu, Icon} from 'semantic-ui-react'
import 'semantic-ui-css/semantic.min.css'
import {CCol, CRow, CTabContent,
    CTabPane} from "@coreui/react";
import DashboardIcon from '@material-ui/icons/Dashboard';
import GroupIcon from '@material-ui/icons/Group';
import FolderIcon from '@material-ui/icons/Folder';
import ListIcon from '@material-ui/icons/List';
import HouseholdDashboard from './HouseholdDashboard'
import HouseholdMember from "./HouseholdMember";
import HouseholdService from "./HouseholdService";
import {calculateAge} from "./../../../utils/calculateAge";
import { makeStyles } from "@material-ui/core/styles";
import AssessmentCarePlanHome from "./AssessmentCarePlanHome";
import HouseHoldEconomicsStrenthening from "./HouseHoldEconomicsStrenthening"
import {fetchHouseHoldById} from "./../../../actions/houseHold";
import {connect, useDispatch} from "react-redux";
import LinearProgress from "@material-ui/core/LinearProgress";
import ReassignCaregiver from "./../widgets/ReassignCaregiver";


const useStyles = makeStyles({
    root: {
        position: "sticky",
        top: "1rem",
        minWidth: "275"
    }
});
const HouseholdHomePage = (props) => {
    //Getting the house Hold details from the props
    console.log(props.location)
    const dispatch = useDispatch();
    const houseHoldId = props.location.state;
    const [fetchingHousehold, setFetchingHousehold] = useState(true);
    const classes = useStyles();
    const [activeItem, setActiveItem] = React.useState('dashboard');
    const handleItemClick = (e, { name }) => {
        setActiveItem(name);
    }


    React.useEffect(() => {
       fetchHousehold();
        //minimize side-menu when this page loads
        dispatch({type: 'MENU_MINIMIZE', payload: true });
    }, [houseHoldId]);

    const fetchHousehold = () => {
        setFetchingHousehold(true);
        const onSuccess = () => {
            setFetchingHousehold(false);
        };
        const onError = () => {
            setFetchingHousehold(false);
        };
        props.fetchHouseHoldById(houseHoldId, onSuccess, onError);
    }

    return (
        <>
            {fetchingHousehold &&
            <LinearProgress color="primary" thickness={5} className={"mb-2"}/>
            }
            <CRow>

                <CCol sm="3" lg="3">
            <Menu className={classes.root} vertical fluid inverted style={{backgroundColor:'#096150'}}>
                <Menu.Item header className={'p-4'}>
                    <HouseHoldInfo household={props.hh} reload={fetchHousehold}/>
                </Menu.Item>
                <Menu.Item
                    name='dashboard'
                    active={activeItem === 'dashboard'}
                    onClick={handleItemClick}
                    className={'text-center'}>
                    <DashboardIcon fontSize="large" className={'text-center'}/>
                    <p>Dashboard</p>
                </Menu.Item>

                <Menu.Item
                    name='members'
                    active={activeItem === 'members'}
                    onClick={handleItemClick}
                    className={'text-center'}>
                    <GroupIcon fontSize="large" className={'text-center'}/>
                    <p>Members</p>
                </Menu.Item>
                <Menu.Item
                    name='careplan'
                    active={activeItem === 'careplan'}
                    onClick={handleItemClick}
                    className={'text-center'}>
                    <ListIcon fontSize="large" className={'text-center'}/>
                    <p> Care Plan Form & Care Plan Achievement Checklist</p>
                </Menu.Item>
                {/*<Menu.Item*/}
                {/*    name='econTool'*/}
                {/*    active={activeItem === 'econTool'}*/}
                {/*    onClick={handleItemClick}*/}
                {/*    className={'text-center'}>*/}
                {/*    <ListIcon fontSize="large" className={'text-center'}/>*/}
                {/*    <p>HouseHold Economics Strengthening Assessment</p>*/}
                {/*</Menu.Item>*/}
                <Menu.Item
                    name='services'
                    active={activeItem === 'services'}
                    onClick={handleItemClick}
                    className={'text-center'}>
                    <FolderIcon fontSize="large" className={'text-center'}/>
                       <p>Service Form History </p>
                </Menu.Item>
            </Menu>
                </CCol>
                <CCol sm="9" lg="9">
                    <CTabContent>
                        <CTabPane active={activeItem === 'dashboard'} >
                            {activeItem === "dashboard" &&
                            <HouseholdDashboard household={props.hh} houseHoldId={houseHoldId}/>
                            }
                        </CTabPane>
                        <CTabPane active={activeItem === 'members'} >
                            {activeItem === "members" &&
                            <HouseholdMember houseHoldId={houseHoldId}/>
                            }
                        </CTabPane>
                        <CTabPane active={activeItem === 'careplan'} >
                            {activeItem === "careplan" &&
                            <AssessmentCarePlanHome householdId={houseHoldId}/>
                            }
                        </CTabPane>
                        {/*<CTabPane active={activeItem === 'econTool'} >*/}
                        {/*    {activeItem === "econTool" &&*/}
                        {/*    <HouseHoldEconomicsStrenthening householdId={houseHoldId}/>*/}
                        {/*    }*/}
                        {/*</CTabPane>*/}
                        <CTabPane active={activeItem === 'services'} >
                            {activeItem === "services" &&
                            <HouseholdService householdId={houseHoldId} activeItem={activeItem}/>
                            }
                        </CTabPane>
                    </CTabContent>

                </CCol>
                </CRow>
            </>
    )
}

const HouseHoldInfo = (props) => {

    return (
        <>
            <CRow>
                <CCol sm="12" lg="12">
                    <Header as='h3' inverted dividing>
                        <Icon name='home' />  Household
                    </Header>
                </CCol>
                <CCol sm="12" lg="12" className={'text-left pt-3'}>
                    {props.household && props.household.details ?
                        <>
                    <span>Household ID: <small> {props.household ? props.household.uniqueId : 'Nil'} </small></span><br/>
                            <span>Address: <small>{props.household.details ? props.household.details.street : 'Nil'} </small> <br />
                    State: <small>{props.household.details.state ? props.household.details.state.name : "" }</small> {' '}
                                LGA: <small>{props.household.details.lga ? props.household.details.lga.name : "" }</small> <br />
                                Ward: <small>{props.household.details.ward ? props.household.details.ward.name : "" }</small> {' '}
                                Community: <small>{props.household.details.community || "" }</small>
                               

                    </span><br/>
                    <span>Date Of Assessment: <small>{props.household.details.assessmentDate || 'Nil'}</small> </span> <br />
                    <br/><br/>
                    <span>Primary Caregiver Name: <small>{props.household.details.primaryCareGiver ? props.household.details.primaryCareGiver.lastName + ' ' + props.household.details.primaryCareGiver.firstName: 'Nil' } </small></span><br/>
                    <span>Phone: <small>{props.household.details.primaryCareGiver ? props.household.details.primaryCareGiver.mobilePhoneNumber : 'Nil' }</small></span><br/>
                    <span>Sex: <small>{props.household.details.primaryCareGiver && props.household.details.primaryCareGiver.sex && props.household.details.primaryCareGiver.sex.display ? props.household.details.primaryCareGiver.sex.display : (props.household.details.primaryCareGiver.sex === 2 ? "Male" : "Female") }</small></span><br/>
                            {props.household.details.primaryCareGiver && props.household.details.primaryCareGiver.dob ?
                    <span>Age: <small>{props.household.details.primaryCareGiver.dateOfBirthType === 'estimated' ? '~' : ''} {calculateAge(  props.household.details.primaryCareGiver.dob)}  | {props.household.details.primaryCareGiver.dob}</small></span> :
                                <span>Age: <small>Nil</small></span>}<br/>
                    <span>Marital Status: <small>{props.household.details.primaryCareGiver && props.household.details.primaryCareGiver.maritalStatus ? props.household.details.primaryCareGiver.maritalStatus.display : 'Nil' }</small></span><br/>
                    <span>Occupation: <small>{props.household.details.primaryCareGiver && props.household.details.primaryCareGiver.occupation ? props.household.details.primaryCareGiver.occupation : 'Nil'}</small></span><br/>
                 <ReassignCaregiver householdId={props.household.id} reload={props.reload} />
                    </> : <></>}
                </CCol>
            </CRow>
        </>
    )
}


const mapStateToProps = (state) => {
    return {
        hh: state.houseHold.household
    };
};

const mapActionToProps = {
    fetchHouseHoldById: fetchHouseHoldById,
};

export default connect(mapStateToProps, mapActionToProps)(HouseholdHomePage);
