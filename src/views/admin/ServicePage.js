import React from "react";
import { CCol, CRow, CButton} from "@coreui/react";
//import ServiceHistoryPage from "../widgets/ServiceHistoryPage";
import DescriptionIcon from "@material-ui/icons/Description";

const ServicePage = () => {
    return (
        <>
            <CRow>
                <CCol xs="12">

                    <DescriptionIcon /> Household Member Services

                    <CButton color={"primary"} className={"float-right mr-1 mb-1"}> Provide Service</CButton> {" "}
           <hr />
                </CCol>
            </CRow>
            <CRow>
                    {/* <CCol xs="12" >
                        <ServiceHistoryPage isHistory={false} memberId={12} />
                    </CCol> */}
            </CRow>
            </>
    );
}


export default ServicePage;