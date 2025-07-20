import React, {useState, useEffect} from 'react';
import axios from "axios";
import {  Row,Col,FormGroup,Label,Card,CardBody, Form,} from 'reactstrap';
import { connect } from 'react-redux'
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import { Spinner } from 'reactstrap';
import { url as baseUrl } from "../../../api";
import { CModal, CModalHeader, CModalBody,CButton} from '@coreui/react'
import { assignProjectToUser } from '../../../actions/cboProject'
import { useHistory } from "react-router-dom";
import DualListBox from "react-dual-listbox";
import "react-dual-listbox/lib/react-dual-listbox.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Title from "../components/Title/CardTitle";
import {  CardContent } from "@material-ui/core";


const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))



const AssignProjectUser = (props) => {
    let history = useHistory();
    const classes = useStyles();
    const user = props.user
    const currentUserName= user!== "" && user.userName? user.userName : "" ;
    const userId = user !==null &&  user.id ? user.id : ""
    const [saving, setSaving] = useState(false);
    const [selectedProjects, setSelectedProjects] = useState([]);

    const [projects, setProjects] = useState([]);
    const [userProjects, setUserProjects] = useState([]);
    const [assignProject, setAssignProject] = useState({applicationUserId: "", cboProjectIds : ""});
     /* Get list of Project from the server */ 
    useEffect(() => {
        async function getCharacters() {
            axios
                .get(`${baseUrl}cbo-projects/all`)
                .then((response) => {
                    setUserProjects(response.data);
                    setProjects(Object.entries(response.data).map(([key, value]) => ({
                      label: value.description,
                      value: value.id,
                    })))

                })
                .catch((error) => {
                    
                });
        }
        const y = props.user && props.user.applicationUserCboProjects
            ? props.user.applicationUserCboProjects.map((x) => (x.cboProjectId)) : [];
        setSelectedProjects(y)
        getCharacters();
        
    }, [props.user]);
   
    const onProjectSelect = (selectedValues) => {
        setSelectedProjects(selectedValues); 
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        assignProject['applicationUserId']= userId
        assignProject['cboProjectIds']=selectedProjects
        if(selectedProjects ==""){
        toast.error("Peoject can not be empty")
        return;
        }
        setSaving(true);
        const onSuccess = () => {
        setSaving(false);
        props.toggleModal()
        props.loadUsers();

        };
        const onError = () => {
        setSaving(false);
        };
        props.assignProjectToUser(assignProject, onSuccess, onError);
    };


    return (

        <div >
            
            <CModal show={props.showModal} onClose={props.toggleModal} size="lg">

                    <CModalHeader toggle={props.toggleModal}>Assign User to Project </CModalHeader>
                    <CModalBody>
                        
                                <Form onSubmit={handleSubmit}>
                                    <Col xl={12} lg={12} md={12}>
                                    
                                    <Card className={classes.cardBottom}>
                                        <CardContent>
                                        <Title>Assign User To Project </Title>
                                        <Row form>
                                            <Col md={12}>              
                                                <h6>User Name : <span>{ currentUserName  }</span></h6>
                                            </Col>
                                            <br/>
                                            <br/>
                                            <Col md={12}>
                                            <FormGroup>
                                                <Label > <h6>CBO Project</h6></Label>
                                                <DualListBox
                                                //options={unAssignProject}
                                                options={projects}
                                                onChange={onProjectSelect}
                                                selected={selectedProjects}
                                                />
                                            </FormGroup>
                                            </Col>
                                        </Row>
                                        {saving ? <Spinner /> : ""}
                                        <br />
                                        <MatButton
                                            type="submit"
                                            variant="contained"
                                            color="primary"
                                            className={classes.button}
                                            startIcon={<SaveIcon />}
                                            disabled={saving}
                                        >
                                            {!saving ? (
                                            <span style={{ textTransform: "capitalize" }}>Save</span>
                                            ) : (
                                            <span style={{ textTransform: "capitalize" }}>Saving...</span>
                                            )}
                                        </MatButton>
                                        <MatButton
                                            variant='contained'
                                            color='default'
                                            onClick={props.toggleModal}
                                            startIcon={<CancelIcon />}>
                                            Cancel
                                        </MatButton>
                                        </CardContent>
                                    </Card>
                                    </Col>
                                </Form>
                              
                    </CModalBody>
            </CModal>
        </div>
    );
}



export default connect(null, {assignProjectToUser})(AssignProjectUser);

