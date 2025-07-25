import React, {useEffect, useState} from 'react'
import { useSelector, useDispatch } from 'react-redux'
import {
  CHeader,
  CToggler,
  CHeaderBrand,
  CHeaderNav,
  CHeaderNavItem,
  CHeaderNavLink,
  CSubheader,
  CBreadcrumbRouter

} from '@coreui/react'
// routes config
import routes from '../routes'
import { 
  TheHeaderDropdown,
  TheHeaderDropdownNotif,
}  from './index'
import { toast } from 'react-toastify';
import axios from "axios";
import { url as baseUrl } from "./../api";
import { authentication } from "./../_services/authentication";

const TheHeader = () => {
  const currentUserCboProjectName = localStorage.getItem('currentUserCboProjectName') != null ? JSON.parse(localStorage.getItem('currentUserCboProjectName')) : null;
  const [cboUserProject, setCboUserProject] = useState([])
  const [projectName, setProjectName] =useState(null) 
  const dispatch = useDispatch()
  const sidebarShow = useSelector(state => state.sidebarShow)

  const toggleSidebar = () => {
    const val = [true, 'responsive'].includes(sidebarShow) ? false : 'responsive'
    dispatch({type: 'set', sidebarShow: val})
  }

  const toggleSidebarMobile = () => {
    const val = [false, 'responsive'].includes(sidebarShow) ? true : 'responsive'
    dispatch({type: 'set', sidebarShow: val})
  }
const  getProjectName = (name) =>{ 
  setProjectName(name)
  console.log(projectName)
}
    useEffect(() => {
    async function getCharacters() {
        axios
          .get(`${baseUrl}account`)
          .then((response) => {

            setCboUserProject(response.data);
            setProjectName(response.data.currentCboProjectDescription)
            console.log(projectName)
          })
          .catch((error) => {
            
          });
      }
      getCharacters();
},  []);
 /*  endpoint */
 

  return (
    <CHeader withSubheader>
      <CToggler
        inHeader
        className="ml-md-3 d-lg-none"
        onClick={toggleSidebarMobile}
      />
      <CToggler
        inHeader
        className="ml-3 d-md-down-none"
        onClick={toggleSidebar}
      />
      <CHeaderBrand className="mx-auto d-lg-none" to="/">
        {/* <CIcon name="logo" height="48" alt="Logo"/> */}
      </CHeaderBrand>

      <CHeaderNav className="d-md-down-none mr-auto">
        <CHeaderNavItem className="px-3" >
          <CHeaderNavLink to="/dashboard">
            
            <h3 style={{color:"black"}}>{projectName && projectName !=null ? projectName : ""}</h3>
          
          </CHeaderNavLink>
        </CHeaderNavItem>
        
      </CHeaderNav>

      <CHeaderNav className="px-3">
        {/* <TheHeaderDropdownNotif  /> */}
        <TheHeaderDropdown projectName={getProjectName}/>
      </CHeaderNav>

      <CSubheader className="px-3 justify-content-between">
        <CBreadcrumbRouter 
          className="border-0 c-subheader-nav m-0 px-0 px-md-3" 
          routes={routes} 
        />
          <div className="d-md-down-none mfe-2 c-subheader-nav">
           
          </div>
      </CSubheader>
    </CHeader>
  )
}

export default TheHeader
