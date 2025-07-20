import React,{useState, useEffect} from 'react'
import axios from "axios";
import {url as baseUrl} from "./../api";
import {
  CBadge,
  CDropdown,
  CDropdownItem,
  CDropdownMenu,
  CDropdownToggle,
  CImg
} from '@coreui/react'
import CIcon from '@coreui/icons-react';
import {  toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { CgProfile } from 'react-icons/cg';

import * as ACTION_TYPES from "./../actions/types";
import store from "./../store";
import { useHistory } from "react-router-dom";
import { authentication } from "./../_services/authentication";
//import AssignFacilityModal from './AssignFacilityModal'

import {
   Modal, ModalHeader, Card, CardBody, Row, Col, ModalBody, Label, FormGroup, Alert
} from "reactstrap";
import Select from "react-select";
import MatButton from "@material-ui/core/Button";
import CancelIcon from '@material-ui/icons/Cancel'
import { useLocation } from 'react-router-dom';

const { dispatch } = store;

const TheHeaderDropdown = (props) => {
  let history = useHistory();
  
  const [isOpenNotificationPopover, setIsOpenNotificationPopover] = useState(false);
  const [isNotificationConfirmed, setIsNotificationConfirmed] = useState(false);
  const [isOpenUserCardPopover, setIsOpenUserCardPopover] = useState(false);
  const [user, setUser] = useState(null);
  const [showLogOut, setShowLogOut] = useState(false);
  const [modal, setModal] = useState(false); 
  const [modalSwitch, setModalSwitch] = useState(false);
  const [assignFacilityModal, setAssignFacilityModal] = useState(false);
  const currentUrl =useLocation();
  
  const toggleNotificationPopover = () => {
    setIsOpenNotificationPopover(!isOpenNotificationPopover);

    if (!isNotificationConfirmed) {
      setIsNotificationConfirmed(true);
    }
  };

  const toggleUserCardPopover = () => {
    setIsOpenUserCardPopover(!isOpenUserCardPopover);
  };

  const toggleAssignFacilityModal = () => {
    setModal(!modal);
  };
  const toggleSwitchProjectAtLoginModal = () => {
    setModalSwitch(!modalSwitch);
  };
  const handleSidebarControlButton = (event) => {
    event.preventDefault();
    event.stopPropagation();

    document.querySelector(".cr-sidebar").classList.toggle("cr-sidebar--open");
  };

  const logout = () => {
    history.push("/");
    authentication.logout();
  }

  async function fetchMe() {
    if( authentication.currentUserValue != null ) {
      axios
          .get(`${baseUrl}account`)
          .then((response) => {
            setUser(response.data);
            setShowLogOut(true)
            localStorage.removeItem('currentUserCboProjectName');
            // set user permissions in local storage for easy retrieval, when user logs out it will be removed from the local storage
            localStorage.setItem('currentUser_Permission', JSON.stringify(response.data.permissions));
            dispatch({
              type: ACTION_TYPES.FETCH_PERMISSIONS,
              payload: response.data.permissions,
            });
            
            if(response.data.currentCboProjectDescription==null){
              toggleSwitchProjectAtLoginModal()
            }
            
            props.projectName(response.data.currentCboProjectDescription)
            
          })
          .catch((error) => {
            authentication.logout();
           // console.log(error);
          });
    }
  }

  async function switchFacility (facility) {

    await axios.post(`${baseUrl}cbo-projects/${facility.value.cboProjectId}`, {})
        .then(response => {

          fetchMe();         
          toggleAssignFacilityModal();
          toast.success('Project switched successfully!');
          window.location.reload();
        }) .catch((error) => {
          toast.error('An error occurred, could not switch facilty.');
        });

  }
  async function switchProject (facility) {

    await axios.post(`${baseUrl}cbo-projects/${facility.value.cboProjectId}`, {})
        .then(response => {
          toast.success('Project switched successfully!');
          fetchMe();         
          toggleSwitchProjectAtLoginModal(); 
          window.location.reload();
        }) .catch((error) => {
         toast.error('An error occurred, could not switch facilty.');
        });

  }

  useEffect(() => {
    fetchMe();
  }, []);

  return (
    <>
    <ToastContainer />
    <CDropdown
      inNav
      className="c-header-nav-items mx-2"
      direction="down"
    >
      <CDropdownToggle className="c-header-nav-link" caret={false}>
        <div className="c-avatar">
          <CgProfile className="c-avatar-img"/>
        </div>
        
      </CDropdownToggle>
      <CDropdownMenu className="pt-0" placement="bottom-end">
        
        <CDropdownItem
          header
          tag="div"
          color="light"
          className="text-center"
        >
          
        </CDropdownItem>
        <CDropdownItem >
          <CIcon name="cil-user" className="mfe-2" />{user && user!==null ? user.userName : ""}
        </CDropdownItem>

        <CDropdownItem divider />
        <CDropdownItem style={{  fontSize:'bold'}} onClick={toggleAssignFacilityModal}>
          <CIcon name="cil-lock-locked" className="mfe-2" />
          Switch Project
        </CDropdownItem>
        <CDropdownItem style={{ color:'red', fontSize:'bold'}} onClick={logout}>
          <CIcon name="cil-lock-locked" className="mfe-2" />
          Log out
        </CDropdownItem>
      </CDropdownMenu>
    </CDropdown>
    <Modal isOpen={modal} backdrop={true}  zIndex={"9999"}>
            <ModalHeader toggle={() => setModal(!modal)}> Switch Project </ModalHeader>
            <ModalBody>
              <Card >
                <CardBody>
                  <Row >
                    <Col md={12}>
                      <FormGroup>
                        <Label>Select Project</Label>
                        <Select
                            required
                            isMulti={false}
                            onChange={switchFacility}
                            options={user && user.applicationUserCboProjects ? user.applicationUserCboProjects.map((x) => ({
                              label: x.cboProjectDescription,
                              value: x,
                            })) : []}
                        />
                      </FormGroup>
                    </Col>
                  </Row>
                  <MatButton
                      variant='contained'
                      color='default'
                      onClick={toggleAssignFacilityModal}
                      startIcon={<CancelIcon />}
                  >
                    Cancel
                  </MatButton>
                </CardBody>
              </Card>
            </ModalBody>
          </Modal>

          {/* Modal to check if user selected the right Projectat Login  */}
          <Modal isOpen={modalSwitch}  zIndex={"9999"} backdrop={false} backdrop="static">
            <ModalHeader > Switch Project </ModalHeader>
            <ModalBody>
              <Card >
                <CardBody>
                  <Row >
                  <Col md={12}>
                    <Alert color="primary">
                          You don't have access to the selected Project
                    </Alert>
                    </Col>
                  <Col md={12}><h4>Please switch project</h4></Col>
                    <Col md={12}>
                      <FormGroup>
                        <Label>Select Project</Label>
                        <Select
                            required
                            isMulti={false}
                            onChange={switchProject}
                            options={user && user.applicationUserCboProjects ? user.applicationUserCboProjects.map((x) => ({
                              label: x.cboProjectDescription,
                              value: x,
                            })) : []}
                        />
                      </FormGroup>
                    </Col>
                  </Row>
                  {user && user.applicationUserCboProjects.length < 0 ?
                    (
                        <MatButton
                            variant='contained'
                            color='secondary'
                            onClick={logout}
                            //startIcon={<CancelIcon />}
                        >
                          Logout
                        </MatButton>
                    )
                    : ""
                  }
                </CardBody>
              </Card>
            </ModalBody>
          </Modal>
   
    </>
  )
}

export default TheHeaderDropdown
