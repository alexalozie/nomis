import React from 'react'
import {
  CBadge,
  CDropdown,
  CDropdownItem,
  CDropdownMenu,
  CDropdownToggle,
  CProgress
} from '@coreui/react'
import CIcon from '@coreui/icons-react'

const TheHeaderDropdownNotif = () => {
  const itemsCount = 5
  return (
    <CDropdown
      inNav
      className="c-header-nav-item mx-2">
      <CDropdownToggle className="c-header-nav-link" caret={false}>
        <CIcon name="cil-bell"/>
        <CBadge shape="pill" color="danger">{itemsCount}</CBadge>
      </CDropdownToggle>
      <CDropdownMenu  placement="bottom-end" className="pt-0">
        <CDropdownItem
          header
          tag="div"
          className="text-center"
          color="light">
          <strong>You have {itemsCount} notifications</strong>
        </CDropdownItem>
        <CDropdownItem><CIcon name="cil-user-follow" className="mr-2 text-success" /> USAID HIV Report Tools Form</CDropdownItem>
        <CDropdownItem><CIcon name="cil-user-unfollow" className="mr-2 text-danger" />New OVC Update Form is available</CDropdownItem>

        <CDropdownItem
          header
          tag="div"
          color="light"
        >
          <strong>View All</strong>
        </CDropdownItem>
      
      </CDropdownMenu>
    </CDropdown>
  )
}

export default TheHeaderDropdownNotif