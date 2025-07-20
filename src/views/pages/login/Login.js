import React, {useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import axios from "axios";
import { useHistory  } from "react-router-dom";
import {
  CButton,
  CCard,
  CCardBody,
  CCardGroup,
  CCol,
  CContainer,
  CForm,
  CInput,
  CInputGroup,
  CInputGroupPrepend,
  CInputGroupText,
  CRow,
} from '@coreui/react';
import {Alert, Card} from 'reactstrap';
import CIcon from '@coreui/icons-react';
import { authentication } from "./../../../_services/authentication";
import logo from './../../../assets/images/arms.jpg';
import {Input} from "reactstrap";
import { url as baseUrl } from "../../../api";
import Register from "../../pages/login/Register";
import {resetPassword} from '../../../actions/resetPassword';





const Login = () => {
  let history = useHistory();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [remember, setRemember] = useState("");
  const [submitText, setSubmittext] = useState("Sign In");
  const [isButtonDisabled, setIsButtonDisabled] = useState(true);
  const [helperText, setHelperText] = useState("");
  const [error, setError] = useState(false);
  const [showNewModal, setshowNewModal] = useState(false)
  const [showNewResetModal, setshowNewResetModal] = useState(false)
  const [errorStyle, setErrorStyle] = useState("")
  const [visible, setVisible] = useState(true);
  const [project, setProject] = useState([]);
  const [cboProjectId, setCboProjectId] = useState("");
  const toggleModal = () => setShowModal(!showModal)
  const [showModal, setShowModal] = React.useState(false);


  const onDismiss = () => setVisible(false);

  useEffect(() => {
    if (username.trim() && password.trim()) {
      setIsButtonDisabled(false);
    } else {
      setIsButtonDisabled(true);
    }
  }, [username, password]);
/* Get list of Role parameter from the endpoint */

  const openNewModal = (row) => {
    setshowNewModal(!showNewModal);
    toggleModal();
  }

  useEffect(() => {
  async function getCharacters() {
    axios
      .get(`${baseUrl}cbo-projects/all`)
      .then((response) => {
         setProject(
          Object.entries(response.data).map(([key, value]) => ({
            label: value.description,
            value: value.id,
          }))
          
        );
        
      })
      .catch((error) => {});
  }
  getCharacters();
}, []);

  const handleLogin = () => {
    
    setSubmittext("Login Please wait...")
    setIsButtonDisabled(false)
    authentication.login(username, password, remember, cboProjectId).then(
      (user) => {
        setError(false);
        setHelperText("Login Successfully");
        //history.push("/dashboard")
        history.push({pathname:'/dashboard', state:  cboProjectId  })
      },
      (error) => {
        setIsButtonDisabled(true)
        setSubmittext("Sign In")
        setError(true);
        setErrorStyle({border: "2px solid red"})
        setHelperText("Incorrect username or password");

      }
    );
  };
  const handleInputChange = e => {
    setCboProjectId(e.target.value)
  }

  const handleKeyPress = (e) => {
    if (e.keyCode === 13 || e.which === 13) {
      isButtonDisabled || handleLogin();
    }
  };



  return (
    <div className="c-app c-default-layout flex-row align-items-center">

      <CContainer >
      
        <CRow className="justify-content-center">
        
          <CCol md="8" >
                {error ?
                <>
                  <Alert color="danger" isOpen={visible} toggle={onDismiss} fade={false}>
                   Incorrect Username or Password. Please Try again...
                  </Alert>
                  </>
                  : ""
                }
            <CCardGroup>
            
              <CCard className="p-19" >
                <CCardBody>
                  <CForm onSubmit={handleLogin}>
                    <h1 style={{color:'#0b795f'}}>Login</h1>
                    <p className="text-muted">Sign In to your account</p>
                    <CInputGroup className="mb-3">
                      <CInputGroupPrepend>
                        <CInputGroupText>
                          <CIcon name="cil-user" />
                        </CInputGroupText>
                      </CInputGroupPrepend>
                      <CInput type="text" placeholder="Username" autoComplete="username" 
                        name="email"
                        onChange={(e) => setUsername(e.target.value)}
                        onKeyPress={(e) => handleKeyPress(e)} 
                        required/>
                    </CInputGroup>
                   
                    <CInputGroup className="mb-4">
                      <CInputGroupPrepend>
                        <CInputGroupText>
                          <CIcon name="cil-lock-locked" />
                        </CInputGroupText>
                      </CInputGroupPrepend>
                      <CInput type="password" placeholder="Password" autoComplete="current-password" 
                        placeholder="Password"
                        onChange={(e) => setPassword(e.target.value)}
                        onKeyPress={(e) => handleKeyPress(e)}/>
                      
                    </CInputGroup>
                    <CInputGroup className="mb-3">
                      <CInputGroupPrepend>
                        <CInputGroupText>
                          <CIcon name="cil-user" />
                        </CInputGroupText>
                      </CInputGroupPrepend>

                    <Input
                      type="select"
                      name="cboProjectId"
                      id="cboProjectId"
                      //value={values.role}
                      onChange={handleInputChange}
                      required>
                      <option >Select CBO Project</option>
                      {project.map(({ label, value }) => (
                        <option key={value} value={value}>
                          {label}
                        </option>
                      ))}
                    </Input>
                    </CInputGroup>
                    <CRow>
                      <CCol xs="12">
                        <CButton style={{ backgroundColor:'#0b795f', color:'#fff' }} className="px-4" onClick={handleLogin} disabled={isButtonDisabled}>{submitText}</CButton>
                      </CCol>
                      {/*<CCol xs="6" className="text-right">*/}
                      {/*   <CButton color="link" className="px-0">Forgot password?</CButton>*/}
                      {/*</CCol>*/}
                    </CRow>

                    <CRow>
                      <CCol xs="12">
                        {/*<Link color="inherit" to={{pathname: "/register"}} >*/}
                        {/*<Link to="/register">*/}
                        {/*  <CButton color="link" className="px-0" className="float-right" onClick={() => openNewModal(null)}>*/}
                        {/*    Forgot password?</CButton>*/}
                        {/*</Link>*/}
                      </CCol>
                      <CCol xs="6" className="text-right">
                        {/* <CButton color="link" className="px-0">Forgot password?</CButton> */}
                      </CCol>
                    </CRow>
                  </CForm>
               
                </CCardBody>
                <Register toggleModal={toggleModal} showModal={showModal} formData={showNewModal} />
                <resetPassword toggleModal={toggleModal} showModal={showModal} formData={showNewResetModal} />
              </CCard>
              <CCard  style={{ backgroundColor:'#0b795f' }} className="text-white py-5 d-md-down-none p-19">
                <CCardBody className="text-center" style={{ backgroundColor:'#0b795f' }}>
                  <div>
                  <span style={{marginRight:10, margingTop:150,}}>
                    <img src={logo} alt="Nigeria Coat of Arms" style={{width: 50, height: 50, borderRadius: 400/ 2}}  className=" "/>
                </span>
                    <h1 style={{color:'#fff'}}>NOMIS</h1>
                    <p> National OVC Management Information System  </p>

                    {/*<Link to="/register">*/}
                    {/*   <CButton color="primary" className="mt-3" active tabIndex={-1}>Forgot Password!</CButton>*/}
                    {/*</Link>*/}
                  </div>
                </CCardBody>
                {/*{<span style={{marginRight:10, margingTop:150, margingBottom:-650}}>*/}
                {/*    <img src={logo} alt="Nigeria Coat of Arms" style={{width: 50, height: 50, borderRadius: 400/ 2}}  className="float-right "/>*/}
                {/*</span> }*/}
              </CCard>
            </CCardGroup>
          </CCol>
        </CRow>
      </CContainer>
    </div>


  )
}

export default Login
