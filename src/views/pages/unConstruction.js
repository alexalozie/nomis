import React from 'react'
import {

  CCol,
  CContainer,

  CRow
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import ConstructionIcon from '@mui/icons-material/Construction';

const Page500 = () => {
  return (
    <div className=" c-default-layout flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md="6">
            <span className="clearfix">
              <h1 className="float-left display-3 mr-4">503</h1>
              <h4 className="pt-3">Page under construction!</h4>
              <p className="text-muted float-left">Please check back later.</p>
            </span>
            <CCol md="12" >
                <ConstructionIcon  size="large" sx={{ fontSize: 100 }} />
            </CCol>
          </CCol>
          
        </CRow>
      </CContainer>
    </div>
  )
}

export default Page500
