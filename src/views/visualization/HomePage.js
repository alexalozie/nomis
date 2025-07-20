import React, { lazy } from 'react';
import {
    CCol,
    CRow,
} from '@coreui/react';

import EnrollmentStream from './EnrollmentStream';
import OVCTreatmentStatus from './OVCTreatmentStatus';
import ViralLoadEligibility from './ViralLoadEligibility';
import OVCServeCaregiver from './OVCServeCaregiver';
import EnrolmentChart from '../dashboard/EnrolmentChart'

// const WidgetsDropdown = lazy(() => import('../widgets/WidgetsDropdown.js'))
// const WidgetsBrand = lazy(() => import('../widgets/WidgetsBrand.js'))


const Dashboard = () => {
  return (
    <>
          <CRow>
            <CCol sm="6" className="p-2">
                <OVCServeCaregiver/>
            </CCol>
            <CCol sm="6" className="p-2">
                <EnrollmentStream />
            </CCol>
          </CRow>
          <CRow>
            <CCol sm="6" className="p-2">
                <OVCTreatmentStatus />
            </CCol>
            <CCol sm="6" className="p-2">
                <EnrolmentChart />
            </CCol>
          </CRow>
    </>
  )
}

export default Dashboard
