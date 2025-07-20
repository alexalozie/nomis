import React, {lazy } from 'react'
import {CCard, CCol, CRow} from '@coreui/react'
import VCByAge from '../visualization/VCByAge';
import ViralLoadEligibility from '../visualization/ViralLoadEligibility';



const WidgetsDropdown = lazy(() => import('../widgets/WidgetsDropdown.js'))
const WidgetsBrand = lazy(() => import('../widgets/WidgetsBrand.js'))


const Dashboard = (props) => {



    return (
        <>
            <CRow>
                <CCol sm="12" className="p-2">
                    <WidgetsDropdown />
                </CCol>
            </CRow>
            <CRow>
                <CCol sm="6" className="p-2">
                    <ViralLoadEligibility />
                </CCol>
                <CCol sm="6" className="p-2">
                    <VCByAge/>
                </CCol>
            </CRow>
            <CCard>

            </CCard>

        </>
    )
}

export default Dashboard

