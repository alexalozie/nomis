import React, {useEffect, useState} from 'react'
import {CCard, CCardBody, CCardHeader, CCol, CRow} from '@coreui/react'
import MaterialTable from 'material-table';
import {Menu,MenuList,MenuButton,MenuItem,} from "@reach/menu-button";
import "@reach/menu-button/styles.css";
import { Link } from 'react-router-dom';
import { toast } from "react-toastify";
import {connect, useDispatch} from "react-redux";
import { fetchAllHouseHoldMember, deleteHouseholdMember } from "./../../../actions/houseHoldMember";
import {calculateAge} from "./../../../utils/calculateAge";
import NewOvc from "../household/NewOvc";
import NewCareGiver from "../household/NewCareGiver";


const HouseholdMember = (props) => {
    const [loading, setLoading] = useState('');
    const [currentHm, setCurrentHm] = useState('');
    const [newOvcModal, setShowOvcModal] = React.useState(false);
    const [newCaregiverModal, setShowCaregiverModal] = React.useState(false);
    const [modal, setModal] = useState(false);

    const dispatch = useDispatch();
    React.useEffect(() => {
        //show side-menu when this page loads
        dispatch({type: 'MENU_MINIMIZE', payload: false });
    },[]);

    const fetchMembers = () => {
        setLoading('true');
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)
        }
        props.fetchAllMember(onSuccess, onError);
    }
    useEffect(() => {
        fetchMembers();
    }, []); //componentDidMount

    const toggleOvc = () => setShowOvcModal(!newOvcModal);
    const toggleCaregiver = () => setShowCaregiverModal(!newCaregiverModal);

    const toggleUpdate = (row) => {
        setCurrentHm(row);
        if(row.householdMemberType === 1){
            toggleCaregiver();
        }

        if(row.householdMemberType === 2){
            toggleOvc();
        }
    };

    const onDelete = row => {
        const onSuccess = () =>{
            toast.success("Household Member deleted successfully");
            props.fetchAllMember();
        }
        if (window.confirm(`Are you sure to delete this record? ${row.uniqueId}`))
            props.deleteHouseholdMember(row.id, onSuccess, () => toast.error("An error occurred, household member not deleted successfully"));
    }


    return (
        <>

            <CRow>
                <CCol>
                    <CCard>
                        <CCardHeader>
                            Household Members
                        </CCardHeader>
                        <CCardBody>
                            <MaterialTable
                                title="Household Member List"
                                columns={[
                                    { title: 'Unique ID', field: 'id' },
                                    { title: 'Member Type', field: 'type' },
                                    { title: 'Date Assessed', field: 'date' },
                                    { title: 'Name', field: 'name' },
                                    { title: 'Age', field: 'age'},
                                    { title: 'Action', field: 'action'},
                                ]}
                                isLoading={loading}
                                data={props.houseMemberList.map((row) => ({
                                    id: <span> <Link
                                        to={{pathname: "/household-member/home", state: row.id, householdId:row.householdId }}>{row.uniqueId}</Link></span>,
                                    date: row.householdMemberType === 1 ? row.details.date_of_enrolment : row.details.date_of_enrolment,
                                    type: row.householdMemberType === 1 ? "Caregiver" : "VC",
                                    name: row.details.firstName + " " + row.details.lastName,
                                    age: (row.details.dateOfBirthType === 'estimated' ? '~' : '') + calculateAge(row.details.dob),
                                    action:
                                        <Menu>
                                            <MenuButton style={{ backgroundColor:"#3F51B5", color:"#fff", border:"2px solid #3F51B5", borderRadius:"4px"}}>
                                                Action <span aria-hidden>▾</span>
                                            </MenuButton>
                                            <MenuList style={{hover:"#eee"}}>
                                                <MenuItem >
                                                    <Link
                                                        to={{pathname: "/household-member/home" , state:row.id, householdId:row.householdId}}>
                                                        View Dashboard
                                                    </Link>
                                                </MenuItem>
                                                {/*<MenuItem onClick={() => toggleUpdate(row)}>{" "}Edit</MenuItem>*/}
                                                <MenuItem onClick={() => onDelete(row)} >{" "}Delete HH Member</MenuItem>
                                            </MenuList>
                                        </Menu>

                                }))}

                                options={{
                                    headerStyle: {
                                        backgroundColor: "#9F9FA5",
                                        color: "#000",
                                    },
                                    searchFieldStyle: {
                                        width : '300%',
                                        margingLeft: '250px',
                                    },
                                    filtering: true,
                                    exportButton: false,
                                    searchFieldAlignment: 'left',

                                }}
                            />
                        </CCardBody>
                    </CCard>
                </CCol>
            </CRow>
            {newOvcModal ?
                <NewOvc  modal={newOvcModal} toggle={toggleOvc} householdId={currentHm ? currentHm.householdId : ""} reload={fetchMembers}  householdMember={currentHm && currentHm.householdMemberType == 2 ? currentHm : null}/>
                : ""
            }

            {newCaregiverModal ?
                <NewCareGiver  modal={newCaregiverModal} toggle={toggleCaregiver} householdId={currentHm ? currentHm.householdId : ""} reload={fetchMembers} householdMember={currentHm && currentHm.householdMemberType  == 1 ? currentHm : null}/>
                : ""
            }   </>
    )
}

const mapStateToProps = state => {
    return {
        houseMemberList: state.houseHoldMember.members
    };
};
const mapActionToProps = {
    fetchAllMember: fetchAllHouseHoldMember,
    deleteHouseholdMember: deleteHouseholdMember
};

export default connect(mapStateToProps, mapActionToProps)(HouseholdMember);
