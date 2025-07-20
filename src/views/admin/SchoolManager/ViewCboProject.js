import React, {useState, useEffect} from 'react';
import axios from "axios";
import {  Row,Col,FormGroup,Label,Input,Card,CardBody, Table} from 'reactstrap';
import { connect } from 'react-redux'
import {  CFormGroup } from '@coreui/react';
import MatButton from '@material-ui/core/Button'
import Button from "@material-ui/core/Button";
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import { Spinner } from 'reactstrap';
import { CModal, CModalHeader, CModalBody,CForm, CModalFooter, CButton} from '@coreui/react'
import { useSelector, useDispatch } from 'react-redux';
import { updateCboProjectLocation } from '../../../actions/cboProject'
import { useHistory } from "react-router-dom";
import DeleteIcon from '@mui/icons-material/Delete';



const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))



const ViewCboProject = (props) => {
    let history = useHistory();
    const [loading, setLoading] = useState(false)
    const currentCboProject = props.currentCboProject && props.currentCboProject!==null ? props.currentCboProject : [];
    const CboProjectOrgUnit = currentCboProject.organisationUnits
    const [cboProjectLocation, setCboProjectLocation] = useState({ id: "", organisationUnitId: "", cboProjectId: "", archived:""});
    const dispatch = useDispatch();
    const classes = useStyles()
    const [orgUnits, setOrgUnits] = useState( props.currentCboProject && props.currentCboProject.organisationUnits? props.currentCboProject.organisationUnits : []);
 
    const  RemoveItem = (e) => {
        //Name
        const lgaName=e.Name
        delete e.Name
        setOrgUnits(e)
        cboProjectLocation['archived']= 2
        cboProjectLocation['cboProjectId']= currentCboProject.id
        cboProjectLocation['organisationUnitId']=e.id
        cboProjectLocation['id']=e.cboProjectLocationId
        
        setLoading(true);
        const onSuccess = () => {
            setLoading(false)
            history.push('/cbo-donor-ip')
            props.toggleModal() 
            props.loadIps()          
        }
        const onError = () => {
            setLoading(false)
            orgUnits['Name']  = lgaName
            props.toggleModal() 
            history.push('/cbo-donor-ip')
        }   
        console.log(cboProjectLocation)    
        dispatch(updateCboProjectLocation([cboProjectLocation],onSuccess, onError));
        
    }
    
    const lgaStatus = e =>{
            if(e.Name !==null){
                return  <Button variant="contained"  size="small" >
                {e.Name}
                </Button>
            }else{
                
            }
    }


    return (
        <div >
            <CModal show={props.showModal} onClose={props.toggleModal} size="lg">
                <CForm >
                    <CModalHeader toggle={props.toggleModal}>CBO Project Detail </CModalHeader>
                    <CModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                <Col md={6}>
                                        <FormGroup>
                                            <h6 >Project Name : {currentCboProject.description}</h6>
                                           
                                           
                                        </FormGroup>
                                    </Col>
                                <Col md={6}>
                                        <FormGroup>
                                            <h6 >Donors: {currentCboProject.donorName} </h6>
                                            
                                        </FormGroup>
                                    </Col>
                                    
                                    <Col md={6}>
                                        <FormGroup>
                                            <h6 >Implementing Partners: {currentCboProject.implementerName} </h6>
                                           
                                        </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                        <FormGroup>
                                            <h6>CBO: {currentCboProject.cboName} </h6>
                                            
                                        </FormGroup>
                                    </Col>
                                    <Col md={12}>
                                        <hr/>
                                        <h4>Location</h4>
                                    </Col>
                                    <Col md={12}>
                                    <div className={classes.demo}>
                                            {CboProjectOrgUnit 
                                                            ?
                                                            <Table  striped responsive>
                                                                <thead >
                                                                    <tr>
                                                                        <th>State</th>
                                                                        <th>LGA</th>
                                                                        {/* <th ></th> */}
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                {CboProjectOrgUnit.map((items, index) => (
                                                                    <tr>
                                                                        <th>{items.State}</th>
                                                                        <th>{lgaStatus(items)}
                                                                            
                                                                           
                                                                        </th>
                                                                        
                                                                       
                                                                    </tr>
                                                                    ) )}
                                                                </tbody>
                                                                </Table>
                                                            
                                                          
                                                          
                                                          : ""
                                                          }
                                                          
                                                     
                                                  </div>
                                    </Col>
                                </Row>

                                
                            </CardBody>
                        </Card>
                    </CModalBody>
                    <CModalFooter>                  
                        <CButton
                            color="secondary"
                            onClick={props.toggleModal}
                        >Cancel
                        </CButton>
                    </CModalFooter>
                </CForm>
            </CModal>
        </div>
    );
}



export default connect(null, {updateCboProjectLocation})(ViewCboProject);

