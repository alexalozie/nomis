import React, {createRef, useState} from 'react';
import { Menu,Dropdown,} from 'semantic-ui-react'
import 'semantic-ui-css/semantic.min.css'
import {CCol, CRow,} from "@coreui/react";
import { Link } from 'react-router-dom'
import DashboardIcon from '@material-ui/icons/Dashboard';
import ListIcon from '@material-ui/icons/List';
import DnsIcon from '@material-ui/icons/Dns';
import People from '@material-ui/icons/People';
import CropFreeIcon from '@material-ui/icons/CropFree';
import DomainIcon from '@material-ui/icons/Domain';
import SettingsIcon from '@material-ui/icons/Settings';

const HomePage = (props) => {
    let contextRef = createRef()
    const [activeItem, setActiveItem] = React.useState('dashboard');
    const handleItemClick = (e, { name }) => {
        setActiveItem(name);
    }
    const subMenu = (path) => {
        if(path=='organisation-unit-home'){
            props.history.push(`/organisation-unit-home`);
        }else if(path=='ip'){
            props.history.push(`/ip`);
        }else if(path=='cbo'){
            props.history.push(`/cbo`);
        }
        else if(path=='donor'){
            props.history.push(`/donor`);
        }
        else if(path=='schoolHomePage'){
            props.history.push(`/schoolHomePage`);
        }
        
    }

    return (
        <>
            <CRow>
                <CCol sm="3" lg="3">
                    {/*className={'bg-success'}*/}
             <Menu vertical fluid inverted style={{backgroundColor:'#096150'}}>

        <Menu.Item
          name='messages'
          active={activeItem === 'dashboard'}
          onClick={handleItemClick}>

       <DashboardIcon className={'text-left'}/>
                  <span className={'pl-3'}>  Dashboard </span>
        </Menu.Item>
        <Menu.Item
          name='messages'
          active={activeItem === 'usersetup'}>
         <Link color="inherit" to ={{ pathname: "users", }}  >
            <People fontSize="small" className={'text-left'}/>
            <span className={'pl-3'}>  User Setup </span>
        </Link>
        </Menu.Item>
        <Menu.Item>
         <Link color="inherit" to ={{ pathname: "program-setup-home", }}  >
            <DomainIcon fontSize="small" className={'text-left'}/>
            <span className={'pl-3'}>  Domain Setup </span>
        </Link>

        </Menu.Item>
        <Menu.Item>
            <SettingsIcon fontSize="small" className={'text-left'}/>
            <span className={'pl-3'}>Organisation Unit</span>
        <Dropdown   className={'float-right'} >
        <Dropdown.Menu >
            {/*<Dropdown.Item icon='settings' text='Organisation Unit Setup' onClick={() =>subMenu("organisation-unit-home")}/>*/}
            <Dropdown.Item icon='settings' text='Donor Setup' onClick={() =>subMenu("donor")}/>
            <Dropdown.Item icon='settings' text='Implementing Partner Setup' onClick={() =>subMenu("ip")}/>
            <Dropdown.Item icon='settings' text='CBO Setup' onClick={() =>subMenu("cbo")}/>
            <Dropdown.Item icon='settings' text='School Setup' onClick={() =>subMenu("schoolHomePage")}/>
          </Dropdown.Menu>
        </Dropdown>
        </Menu.Item>
        <Menu.Item>
        <Link color="inherit" to ={{ pathname: "cbo-donor-ip", }}  >
            <SettingsIcon fontSize="small" className={'text-left'}/>
            <span className={'pl-3'}>  CBO Project  Setup  </span>
        </Link>
        </Menu.Item>
        <Menu.Item
          name='messages'
          active={activeItem === 'usersetup'}>
        <Link color="inherit" to ={{ pathname: "application-codeset-home", }}  >
            <CropFreeIcon fontSize="small" className={'text-left'}/>
            <span className={'pl-3'}> Application Codeset </span>
            </Link>
        </Menu.Item>
        <Menu.Item
          name='messages'
          active={activeItem === 'form-home'}
          onClick={()=>{}}>
            <Link color="inherit" to ={{pathname: "form-home",}}  >
                <DnsIcon fontSize="small" className={'text-left'}/>
                <span className={'pl-3'}> Form Builder </span>
            </Link>
        </Menu.Item>
        <Menu.Item>
        <Link color="inherit" to ={{ pathname: "report-builder", }}  >
            <ListIcon fontSize="small" className={'text-left'}/>
            <span className={'pl-2'}>  Report Builder  </span>
        </Link>
        </Menu.Item>
                 <Menu.Item>
                     <Link color="inherit" to ={{ pathname: "datim-pages", }}  >
                         <ListIcon fontSize="small" className={'text-left'}/>
                         <span className={'pl-2'}> DATIM Report Element Setup </span>
                     </Link>
                 </Menu.Item>
                 <Menu.Item
                     name='messages'
                     active={activeItem === 'datim-report'}
                     onClick={()=>{}}>
                     <Link color="inherit" to ={{pathname: "datim-report",}}  >
                         <DnsIcon fontSize="small" className={'text-left'}/>
                         <span className={'pl-2'}>Generate DATIM Flat Files</span>
                     </Link>
                 </Menu.Item>
                 <Menu.Item
                 name='messages'
                 active={activeItem === 'flags'}
                 onClick={()=>{}}>
                 <Link color="inherit" to ={{pathname: "flags",}}  >
                     <DnsIcon fontSize="small" className={'text-left'}/>
                     <span className={'pl-2'}> Flag Manager </span>
                 </Link>
             </Menu.Item>
                 <Menu.Item
                 name='messages'
                 active={activeItem === 'data-home-page'}
                 onClick={()=>{}}>
                 <Link color="inherit" to ={{pathname: "data-home-page",}}  >
                     <DnsIcon fontSize="small" className={'text-left'}/>
                     <span className={'pl-2'}> Database Manager </span>
                 </Link>
             </Menu.Item>
                 <Menu.Item
                     name='messages'
                     active={activeItem === 'general-export'}
                     onClick={()=>{}}>
                     <Link color="inherit" to ={{pathname: "general-export",}}  >
                         <DnsIcon fontSize="small" className={'text-left'}/>
                         <span className={'pl-2'}>General Import/Export Manager</span>
                     </Link>
                 </Menu.Item>
            </Menu>
                </CCol>
                {/*<CCol sm="9" lg="9">*/}
                {/*    <CTabContent>*/}
                {/*        <CTabPane active={activeItem === 'dashboard'} >*/}
                {/*            <Dashboard />*/}
                {/*        </CTabPane>*/}
                {/*        <CTabPane active={activeItem === 'services'} >*/}
                {/*            <ServicePage />*/}
                {/*        </CTabPane>*/}
                {/*    </CTabContent>*/}
                {/*</CCol>*/}
                </CRow>
            </>
    )
}

const InfoSection = () => {
    return (
        <>
            <CRow>
                <CCol sm="12" lg="12">
                    {/* <Header as='h3' inverted dividing>
                        <Icon name='user' />  Member
                    </Header> */}
                </CCol>
                {/* <CCol sm="12" lg="12" className={'text-left pt-3'}>
                    <span>Unique ID: <small>12/11/2020 </small></span><br/>
                    <span>Name: <small>Moses Lambo </small></span><br/>
                    <span>Phone: <small>07057787654</small></span><br/>
                    <span>Sex: <small>Male</small></span> {'  '}
                    <span>Age: <small>52</small></span><br/>
                    <span>Date Of Assessment: <small>12/11/2020</small> </span><br/><br/>
                    <span>State: <small>Abia</small></span><br/>
                    <span>LGA: <small>Okelewo</small></span><br/>
                    <span>CBO: <small>MSM</small></span><br/>
                </CCol> */}
            </CRow>
        </>
    )
}

export default HomePage;