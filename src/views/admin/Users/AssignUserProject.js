import axios from "axios";
import React, { useState, useEffect } from "react";
import MatButton from "@material-ui/core/Button";
import {
  Col,
  Form,
  FormGroup,
  Label,
  Row,

} from "reactstrap";
import { makeStyles } from "@material-ui/core/styles";
import { Card, CardContent } from "@material-ui/core";
import SaveIcon from "@material-ui/icons/Save";
import CancelIcon from "@material-ui/icons/Cancel";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import { connect } from "react-redux";
import Title from "../components/Title/CardTitle";
import { assignProjectToUser } from "../../../actions/cboProject";
import { url as baseUrl } from "../../../api";
import { Spinner } from "reactstrap";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";
import {TiArrowBack } from "react-icons/ti";
import Moment from "moment";
import momentLocalizer from "react-widgets-moment";
import DualListBox from "react-dual-listbox";
import "react-dual-listbox/lib/react-dual-listbox.css";

Moment.locale("en");
momentLocalizer();

const useStyles = makeStyles((theme) => ({
  card: {
    margin: theme.spacing(20),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  form: {
    width: "100%", // Fix IE 11 issue.
    marginTop: theme.spacing(3),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
  cardBottom: {
    marginBottom: 20,
  },
  Select: {
    height: 45,
    width: 300,
  },
  button: {
    margin: theme.spacing(1),
  },
  root: {
    flexGrow: 1,
    maxWidth: 752,
  },
  demo: {
    backgroundColor: theme.palette.background.default,
  },
  inline: {
    display: "inline",
  },
}));

const AssignProjectUser = (props) => {
    const classes = useStyles();
    const userDetail = props.location && props.location.state ? props.location.state : null
    if(userDetail ==null ){
        props.history.push(`/users`)
    }
    const currentUserName= userDetail!== "" && userDetail.userName? userDetail.userName : "" ;
    const userId = userDetail !==null &&  userDetail.id ? userDetail.id : ""
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
                    setProjects(response.data);
                })
                .catch((error) => {
                    console.log(error);
                });
        }

        getCharacters();
        setSelectedProjects(userDetail.applicationUserCboProjects.map((x) => (x.cboProjectId)))
        
    }, []);
   

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

                    console.log(response.data)
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    
        getCharacters();
        
    }, []);
   
    const onProjectSelect = (selectedValues) => {
        setSelectedProjects(selectedValues);
          console.log(selectedValues)  
    };

  const handleSubmit = (e) => {
    e.preventDefault();
    assignProject['applicationUserId']= userId
    assignProject['cboProjectIds']=selectedProjects
    console.log(selectedProjects)
    if(selectedProjects ==""){
      toast.error("Peoject can not be empty")
      return;
    }
    setSaving(true);
    const onSuccess = () => {
      setSaving(false);
      props.history.push(`/users`)
    };
    const onError = () => {
      setSaving(false);
    };
    props.assignProjectToUser(assignProject, onSuccess, onError);
  };

  return (
    <div>
      <Title>
        <Link to="/users">
          <Button
            variant="contained"
            color="primary"
            className=" float-right mr-1"
            startIcon={<TiArrowBack />}
          >
            <span style={{ textTransform: "capitalize" }}>Back </span>
          </Button>
        </Link>
        <br />
      </Title>
      <br />
      <ToastContainer autoClose={3000} hideProgressBar />
      

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

             
            </CardContent>
          </Card>
        </Col>
      </Form>
    </div>
  );
};


export default connect(null, { assignProjectToUser })(AssignProjectUser);
