import React, {useState} from "react";
import { CCol, CRow} from "@coreui/react";
import ServiceHistoryPage from './ServiceHistoryPage';
import DescriptionIcon from '@material-ui/icons/Description';


const HouseholdService = (props) => {
    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);

    return (
        <>
            <CRow>
                <CCol xs="12">
                    <DescriptionIcon /> Household Services
           <hr />
                </CCol>
            </CRow>
            <CRow>
                    <CCol xs="12" >
                        <ServiceHistoryPage householdId={props.householdId} />
                    </CCol>
            </CRow>
           
            </>
    );
}


export default HouseholdService;