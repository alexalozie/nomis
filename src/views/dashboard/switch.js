import React, {useState, useEffect} from 'react';
import {  Modal, ModalHeader, ModalBody,Form,Row,Col,FormGroup,Label,Input,Card,CardBody} from 'reactstrap';
import { makeStyles } from '@material-ui/core/styles'
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import Select from "react-select";
import {  toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import axios from "axios";
import {url as baseUrl} from "./../../api";
import { authentication } from "./../../_services/authentication";
import store from "./../../store";
import * as ACTION_TYPES from "./../../actions/types";

const { dispatch } = store;

const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const SwitchProject = (props) => {
    const [user, setUser] = useState(null);
    const classes = useStyles()
    const [modal, setModal] = useState(true); 
    const toggleAssignFacilityModal = () => {
        setModal(!modal);
      };
    async function fetchMe() {
        if( authentication.currentUserValue != null ) {
          axios
              .get(`${baseUrl}account`)
              .then((response) => {
                setUser(response.data);
                localStorage.removeItem('currentUserCboProjectName');
                // set user permissions in local storage for easy retrieval, when user logs out it will be removed from the local storage
                localStorage.setItem('currentUser_Permission', JSON.stringify(response.data.permissions));
                dispatch({
                  type: ACTION_TYPES.FETCH_PERMISSIONS,
                  payload: response.data.permissions,
                });
                
                localStorage.setItem('currentUserCboProjectName', JSON.stringify(response.data));
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
              toast.success('Facility switched successfully!');
              fetchMe();         
              toggleAssignFacilityModal();
              window.location.reload();
              
            }) .catch((error) => {
             toast.error('An error occurred, could not switch facilty.');
            });
    
      }
    
    
      useEffect(() => {
        fetchMe();
      }, []);


    return (

        <div >
            <ToastContainer />
            <Modal isOpen={modal} backdrop={true}  zIndex={"9999"}>
            <ModalHeader toggle={() => setModal(!modal)}> Switch Facility </ModalHeader>
                    <ModalBody>
                        <Card >
                            <CardBody>
                            <Row >
                            <Col md={12}><h4>Please select Project</h4></Col>
                                <Col md={12}>
                                <FormGroup>
                                    <Label>Select Facility</Label>
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
                            </CardBody>
                        </Card>
                    </ModalBody>

            </Modal>
        </div>
    );
}


export default SwitchProject;

