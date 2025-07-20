import React, {useState, useEffect } from 'react'
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
import {Alert} from 'reactstrap';
import CIcon from '@coreui/icons-react';
// import { authentication } from "./../../../_services/authentication";







const Login = () => {
    let history = useHistory();
    const [token, setToken] = useState("");
    const [password, setPassword] = useState("");
    const [remember, setRemember] = useState("");
    const [submitText, setSubmittext] = useState("Reset Password");
    const [isButtonDisabled, setIsButtonDisabled] = useState(true);
    const [helperText, setHelperText] = useState("");
    const [error, setError] = useState(false);
    const [errorStyle, setErrorStyle] = useState("")
    const [visible, setVisible] = useState(true)

    const onDismiss = () => setVisible(false);

    useEffect(() => {
        if (token.trim() && password.trim()) {
            setIsButtonDisabled(false);
        } else {
            setIsButtonDisabled(true);
        }
    }, [token, password]);
    /* Get list of Role parameter from the endpoint */

    const handleLogin = () => {

        // setSubmittext("Login Please wait...")
        // setIsButtonDisabled(false)
        // authentication.login(token, password).then(
        //     (user) => {
        //         setError(false);
        //         setHelperText("Login Successfully");
        //         history.push({pathname:'/Login'  })
        //     },
        //     (error) => {
        //         setIsButtonDisabled(true)
        //         setSubmittext("Reset Password")
        //         setError(true);
        //         setErrorStyle({border: "2px solid red"})
        //         setHelperText("Incorrect username or password");
        //
        //     }
        // );
    };

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
                                        <h1 style={{color:'#0b795f'}}>Password Reset</h1>
                                        <p className="text-muted">Enter new Password and Token to reset account</p>
                                        <CInputGroup className="mb-3">
                                            <CInputGroupPrepend>
                                                <CInputGroupText>
                                                    <CIcon name="cil-user" />
                                                </CInputGroupText>
                                            </CInputGroupPrepend>
                                            <CInput type="text" placeholder="token" autoComplete="token"
                                                    name="token"
                                                    onChange={(e) => setToken(e.target.value)}
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
                                        <CRow>
                                            <CCol xs="12">
                                                <CButton style={{ backgroundColor:'#0b795f', color:'#fff' }} className="px-4" onClick={handleLogin} disabled={isButtonDisabled}>{submitText}</CButton>
                                            </CCol>
                                        </CRow>
                                    </CForm>
                                </CCardBody>
                            </CCard>
                        </CCardGroup>
                    </CCol>
                </CRow>
            </CContainer>
        </div>

    )
}

export default Login
