import React, {useState} from 'react';
import { CCard,
    CCardBody,
    CCardHeader,} from "@coreui/react";
import {Button, List} from 'semantic-ui-react'
import {fetchAllHouseHoldServiceHistory} from "../../../actions/houseHold";
import {fetchAllHouseHoldMemberServiceHistory} from "../../../actions/houseHoldMember";
import {connect} from "react-redux";
import moment from "moment";
import LinearProgress from "@material-ui/core/LinearProgress";
import FormRendererModal from "../../formBuilder/FormRendererModal";
import ProvideService from "../household/ProvideService";
import {HOUSEHOLD_MEMBER_SERVICE_PROVISION} from "../../../api/codes";

const ServiceHistoryPage = (props) => {
    //should be able to fetch by either houseHoldId or memberId
    const [loading, setLoading] = useState(false);
    const houseHoldId = props.householdId;
    const memberId = props.memberId;
    const isBoolean = (variable) => typeof variable === "boolean";
    const isHistory = isBoolean(props.isHistory) ? props.isHistory : true;
    const [showFormModal, setShowFormModal] = useState(false);
    const [currentForm, setCurrentForm] = useState(false);
    const [showServiceModal, setShowServiceModal] = useState(false);
    const [provideServiceData, setProvideServiceData] = useState({serviceList:[], serviceDate: null, formDataId: 0, encounterId:0, type:"VIEW"});
    const toggleServiceModal = () => setShowServiceModal(!showServiceModal);

    React.useEffect(() => {
        if(!memberId) {
            fetchHouseholdServiceHistory(houseHoldId);
        } else {
            fetchAllHouseHoldMemberServiceHistory(memberId);
        }

    }, [houseHoldId, memberId]);

    const fetchHouseholdServiceHistory = (houseHoldId) => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }
        const onError = () => {
            setLoading(false);
        }
        props.fetchAllHouseHoldServiceHistory(houseHoldId, onSuccess, onError);
    }

    const fetchAllHouseHoldMemberServiceHistory = (memberId) => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }
        const onError = () => {
            setLoading(false);
        }
        props.fetchAllHouseHoldMemberServiceHistory(memberId, onSuccess, onError);
    }

    const viewForm = (row) => {
        //check if it is service provision page (static html) else use formio view
        if(row.formCode == HOUSEHOLD_MEMBER_SERVICE_PROVISION){
            const formData = row.formData[0];
            const selectedService = {serviceList : formData.data.serviceOffered, serviceDate: formData.data.serviceDate, type:"VIEW", formDataId: formData.id, memberId: row.householdMemberId};
            console.log(selectedService);
            setProvideServiceData(selectedService);
            toggleServiceModal();
            return;
        }

        setCurrentForm({ ...row, type: "VIEW", encounterId: row.id });
        setShowFormModal(true);
    }


    if(isHistory) {

    return (
        <>
     <CCard>
                    <CCardHeader>Recent Service Forms

                    </CCardHeader>
                    <CCardBody>
                        {memberId ?
                            <List divided verticalAlign='middle'>
                                {!loading && props.memberServiceHistory.length <= 0 &&
                                <List.Item>
                                    <List.Content>There are no services for this household member</List.Content>
                                </List.Item>
                                }

                                {loading &&
                                <LinearProgress color="primary" thickness={5} className={"mb-2"}/>
                                }
                                {props.memberServiceHistory.map(service =>
                                    <List.Item>
                                        <List.Content floated='right'>
                                            <Button onClick={() => viewForm(service)}>View</Button>
                                        </List.Content>
                                        <List.Content>{service.formName} {memberId ? '' : service.firstName !== null ? (' - '+service.firstName+' '+service.lastName) : ''}</List.Content>
                                        <List.Description>{service.dateEncounter ? moment(service.dateEncounter).format('LLL') : ''} </List.Description>
                                    </List.Item>
                                )
                                }
                            </List>
                            :
                            <List divided verticalAlign='middle'>
                                {!loading && props.householdServiceHistory.length <= 0 &&
                                <List.Item>
                                    <List.Content>There are no services in this household</List.Content>
                                </List.Item>
                                }

                                {loading &&
                                <LinearProgress color="primary" thickness={5} className={"mb-2"}/>
                                }
                                {props.householdServiceHistory.map(service =>
                                    <List.Item>
                                        <List.Content floated='right'>
                                            <Button onClick={() => viewForm(service)}>View</Button>
                                        </List.Content>
                                        <List.Content>{service.formName} {memberId ? '' : service.firstName !== null ? (' - ' + service.firstName + ' ' + service.lastName) : ''}</List.Content>
                                        <List.Description>{service.dateEncounter ? moment(service.dateEncounter).format('LL') : ''} </List.Description>
                                    </List.Item>
                                )
                                }
                            </List>
                        }
                    </CCardBody>
                </CCard>
            <FormRendererModal
                showModal={showFormModal}
                setShowModal={setShowFormModal}
                currentForm={currentForm}
                //onSuccess={onSuccess}
                //onError={onError}
                options={{modalSize:"xl"}}
            />

            <ProvideService  modal={showServiceModal} toggle={toggleServiceModal} memberId={provideServiceData.memberId}  householdId={props.householdId} reloadSearch={fetchHouseholdServiceHistory}
                             serviceList={provideServiceData.serviceList} serviceDate={provideServiceData.serviceDate}
                             formDataId={provideServiceData.formDataId} encounterId={provideServiceData.encounterId} type={provideServiceData.type}/>

        </>
    );
    }
}

const mapStateToProps = state => {
    return {
        householdServiceHistory: state.houseHold.householdServiceHistory,
        memberServiceHistory: state.houseHoldMember.serviceHistory
    };
};
const mapActionToProps = {
    fetchAllHouseHoldServiceHistory: fetchAllHouseHoldServiceHistory,
    fetchAllHouseHoldMemberServiceHistory: fetchAllHouseHoldMemberServiceHistory
};

export default connect(mapStateToProps, mapActionToProps)(ServiceHistoryPage);