import axios from "axios";
import React, { useState, useEffect } from "react";
import MatButton from "@material-ui/core/Button";
import {
  Col,
  Form,
  FormGroup,
  Input,
  Label,
  Row,
  Alert,
  FormFeedback,
} from "reactstrap";
import { makeStyles } from "@material-ui/core/styles";
import { Card, CardContent } from "@material-ui/core";
import SaveIcon from "@material-ui/icons/Save";
import CancelIcon from "@material-ui/icons/Cancel";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import { connect } from "react-redux";
// React Notification
import Title from "../components/Title/CardTitle";
import { register } from "../../../actions/user";
import { url as baseUrl } from "../../../api";
import { initialfieldState_userRegistration } from "../../../_helpers/initialFieldState_UserRegistration";
import useForm from "../../Functions/UseForm";
import { Spinner } from "reactstrap";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";
import { TiArrowBack } from "react-icons/ti";
import Moment from "moment";
import momentLocalizer from "react-widgets-moment";
import moment from "moment";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";

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
  error: {
    color: "#f85032",
    fontSize: "12.8px",
},
}));

const UserRegistration = (props) => {
  const classes = useStyles();
  const { values, setValues, handleInputChange, resetForm } = useForm(
    initialfieldState_userRegistration
  );
  const [gender, setGender] = useState([]);
  const [role, setRole] = useState(['']);
  const [confirm, setConfirm] = useState("");
  const [matchingPassword, setMatchingPassword] = useState(false);
  const [validPassword, setValidPassword] = useState(false);
  const [matchingPasswordClass, setMatchingPasswordClass] = useState("");
  const [validPasswordClass, setValidPasswordClass] = useState("");
  const [saving, setSaving] = useState(false);

  /* Get list of gender parameter from the endpoint */
  useEffect(() => {
    async function getCharacters() {
      axios
        .get(`${baseUrl}application-codesets/codesetGroup/GENDER`)
        .then((response) => {
          setGender(
            Object.entries(response.data).map(([key, value]) => ({
              label: value.display,
              value: value.display,
            }))
          );
        })
        .catch((error) => {
          
        });
    }
    getCharacters();
  }, []);

  /* Get list of Role parameter from the endpoint */
  useEffect(() => {
    async function getCharacters() {
      axios
        .get(`${baseUrl}roles`)
        .then((response) => {
          setRole(
            Object.entries(response.data).map(([key, value]) => ({
              label: value.name,
              value: value.name,
            }))
          );
        })
        .catch((error) => {});
    }
    getCharacters();
  }, []);

  // check if password and confirm password match
  const handleConfirmPassword = (e, setConfirmPassword = true) => {
    if (setConfirmPassword) setConfirm(e.target.value);
    if (e.target.value === values.password || e.target.value === confirm) {
      setMatchingPassword(true);
      setMatchingPasswordClass("is-valid");
    } else {
      setMatchingPassword(false);
      setMatchingPasswordClass("is-invalid");
    }
  };

  const handlePassword = (e) => {
    handleInputChange(e);
    // validate password
    if (e.target.value.length > 5) {
      setValidPassword(true);
      setValidPasswordClass("is-valid");
    } else {
      setValidPassword(false);
      setValidPasswordClass("is-invalid");
    }
    // check if password and confirm password match
    handleConfirmPassword(e, false);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setSaving(true);
    const dateOfBirth = moment(values.dateOfBirth).format("YYYY-MM-DD");
    values["dateOfBirth"] = dateOfBirth;
    values["roles"] = [values["role"]];
    delete values["dateOfBirth"];
    delete values["phone"];
    delete values["adminRegistration"];
    delete values["username"];
    delete values["role"];
    
    const onSuccess = () => {
      setSaving(false); 
        props.history.push(`/users`)    
      resetForm();
      toast.success("Registration Successful");
    };
    const onError = () => {
      setSaving(false);
      props.history.push(`/users`)
      toast.error("Something went wrong");    
    };
    props.register(values, onSuccess, onError);
  };

  return (
    <div>
   
    <Card className={classes.cardBottom}>
        <CardContent>
        <Breadcrumbs aria-label="breadcrumb">
                <Link color="inherit" to={{pathname: "/admin"}} >
                    Admin
                </Link>
                <Link color="inherit" to={{pathname: "/users"}} >
                    User List
                </Link>
                <Typography color="textPrimary">User Registration </Typography>
            </Breadcrumbs>
            <br/>
      <Title>
        <Link
              to ={{
                pathname: "/users",
                state: 'users'
              }}
        >
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
      

      <Form onSubmit={handleSubmit}>
        <Col xl={12} lg={12} md={12}>
        
        <Alert color="primary">
          All Information with Asterisks(*) are compulsory
        </Alert>
          <Card className={classes.cardBottom}>
            <CardContent>
              <Title>User Information</Title>
              <br />
              <Row form>
                <Col md={6}>
                  <FormGroup>
                    <Label for="firstName">First Name *</Label>
                    <Input
                      type="text"
                      name="firstName"
                      id="firstName"
                      value={values.firstName}
                      onChange={handleInputChange}
                      required
                    />
                  </FormGroup>
                 
                  <FormGroup>
                    <Label for="userName">Username *</Label>
                    <Input
                      type="text"
                      name="userName"
                      id="userName"
                      onChange={handleInputChange}
                      value={values.userName}
                      required
                    />
                  </FormGroup>
                  <FormGroup>
                    <Label for="email">Email </Label>
                    <Input
                      type="email"
                      name="email"
                      id="email"
                      onChange={handleInputChange}
                      value={values.email}
                      
                    />
                  </FormGroup>
                  <FormGroup>
                    <Label for="password">Password *</Label>
                    <Input
                      type="password"
                      name="password"
                      id="password"
                      onChange={handlePassword}
                      value={values.password}
                      required
                      className={validPasswordClass}
                    />
                    <FormFeedback>
                      Password must be atleast 6 characters
                    </FormFeedback>
                  </FormGroup>
                 
                 
                  
                </Col>
                <Col md={6}>
                 
                  <FormGroup>
                    <Label for="lastName">Last Name * </Label>
                    <Input
                      type="text"
                      name="lastName"
                      id="lastName"
                      onChange={handleInputChange}
                      value={values.lastName}
                      required
                    />
                  </FormGroup>
                   <FormGroup>
                    <Label for="role">Role *</Label>
                    <Input
                      type="select"
                      name="role"
                      id="role"
                      value={values.role}
                      onChange={handleInputChange}
                      required
                    >
                      <option value=""> </option>
                      {role.map(({ label, value }) => (
                        <option key={value} value={value}>
                          {label}
                        </option>
                      ))}
                    </Input>
                  </FormGroup>
                  
                  <FormGroup>
                    <Label for="phoneNumber">Phone Number </Label>
                    <Input
                      type="number"
                      name="phoneNumber"
                      id="phoneNumber"
                      onChange={handleInputChange}
                      value={values.phoneNumber}
                    />
                  </FormGroup>
                 
                  <FormGroup>
                    <Label for="confirm">Confirm Password *</Label>
                    <Input
                      type="password"
                      name="confirm"
                      id="confirm"
                      onChange={handleConfirmPassword}
                      value={confirm}
                      required
                      className={matchingPasswordClass}
                    />
                    <FormFeedback>Passwords do not match</FormFeedback>
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
                disabled={saving || !(validPassword && matchingPassword)}>
                {!saving ? (
                  <span style={{ textTransform: "capitalize" }}>Save</span>
                ) : (
                  <span style={{ textTransform: "capitalize" }}>Saving...</span>
                )}
              </MatButton>

              <MatButton
                variant="contained"
                className={classes.button}
                startIcon={<CancelIcon />}
                onClick={resetForm}>
                <span style={{ textTransform: "capitalize" }}>Cancel</span>
              </MatButton>
            </CardContent>
          </Card>
        </Col>
      </Form>
      </CardContent>
      </Card>
    </div>
  );
};

const mapStateToProps = (state) => ({
  status: state.users.status,
});

export default connect(mapStateToProps, { register })(UserRegistration);
