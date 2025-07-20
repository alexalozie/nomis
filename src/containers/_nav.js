import React from 'react'
import CIcon from '@coreui/icons-react'

const _nav =  [
  {
    _tag: 'CSidebarNavItem',
    name: 'Dashboard',
    to: '/dashboard',
    icon: <CIcon name="cilGraph" customClasses="c-sidebar-nav-icon"/>,
  },
  
  {
    _tag: 'CSidebarNavItem',
    icon:  <CIcon name="cil-home" customClasses="c-sidebar-nav-icon" size="8xl"/>,
    to: '/households',
    name: 'Household',
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Household Members',
    to: '/household-members',
    icon: 'cilPeople',
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Retrospective',
    to: '/retrospective',
    icon: 'cilList',
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Reports',
    to: '/report',
    icon: 'cilList',
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Visualization',
    to: '/visualization-home',
    icon: 'cilGraph',
  },
  {
    _tag: 'CSidebarNavItem',
    name: 'Administration',
    to: '/admin',
    icon: 'cilJustifyCenter',
   }, 


]

export default _nav
