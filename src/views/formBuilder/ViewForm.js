import React, {useRef, useEffect, useState} from 'react';
import {  Errors, Form, FormBuilder, Formio } from 'react-formio';
import {Card,CardContent,} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import {fetchById, updateForm, fetchService} from '../../actions/formBuilder'
import MatButton from '@material-ui/core/Button';
import { authHeader } from '../../_helpers/auth-header';
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import {
    FormGroup,
    Input,
    Label,
    Col,
    Row, CardBody
} from 'reactstrap';
import {Link} from 'react-router-dom';
import CheckBoxOutlineBlankIcon from "@material-ui/icons/CheckBoxOutlineBlank";
import CheckBoxIcon from "@material-ui/icons/CheckBox";
import axios from 'axios';
import {url} from '../../api';
import CancelIcon from "@material-ui/icons/Cancel";
import DownloadLink  from "react-download-link";
import { Alert } from '@material-ui/lab';
import LinearProgress from "@material-ui/core/LinearProgress";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";
import 'formiojs/dist/formio.builder.min.css';
import CustomSelect from "./customComponents/CustomSelect";
import {CModal, CModalBody, CModalHeader, CModalFooter} from "@coreui/react";
import {fetchHouseHoldMemberById} from "../../actions/houseHoldMember";
import {fetchHouseHoldById} from "../../actions/houseHold";

Formio.use(CustomSelect);

const useStyles = makeStyles(theme => ({
    root2: {
        width: '100%',
        height: 100,
        overflow: 'auto',
    }
}));

const Update = props => {
    const [res, setRes] = React.useState("");
    const [loading, setLoading] = React.useState(true);
    const [disabledCheckBox, setdisabledCheckBox] = useState(true)
    const [row, setRow] = useState(props.location.state && props.location.state.row ? props.location.state.row : "");
    const icon = <CheckBoxOutlineBlankIcon fontSize="small" />;
    const checkedIcon = <CheckBoxIcon fontSize="small" />;
    const [displayType, setDisplayType] = React.useState("");
    const [formCode, setformCode] = React.useState();
    const [previewHHMemberId, setPreviewHHMemberId] = React.useState("7898");
    const [previewHHId, setPreviewHHId] = React.useState("FCT-IO-PO-989");
    const [fetchingMember, setFetchingMember] = useState(true);
    const [fetchingHousehold, setFetchingHousehold] = useState(true);
    const [form2, setform2] = React.useState();
    const classes = useStyles();
    let myform;
    const submission = props.patient;
    const textAreaRef = useRef(null);
    const [showModal, setShowModal] = React.useState(false);
    const toggleModal = () => setShowModal(!showModal)

    let fileReader;
    const [showFileImport, setShowFileImport] = useState(true);
    const toggleShowFileImport = () => setShowFileImport(!showFileImport);

    useEffect(() => {
        async function fetchForms() {
            try {
                const response = await axios(url + "forms");
                const body = response.data;
                const data = body.map(({ name, code }) => ({ title: name, value: code }));
               // setFormPrecedenceList(data);
                body !== null ? setdisabledCheckBox(false) : setdisabledCheckBox(true)
            } catch (error) {
            }
        }
        fetchForms();
    }, []);

    useEffect(() => {
        async function fetchFormByCode() {
            axios
                .get(`${url}forms/formCode?formCode=${row.code}`)
                .then(response => {
                    setform2(response.data);
                    setLoading(false);
                })
                .catch(error => {
                    toast.error('Could not load form resource, please contact admin.')
                    setLoading(false);
                })
        }
        fetchFormByCode();
    }, []);

    useEffect(() => {
        setFetchingMember(true);
        const onSuccess = () => {
            setFetchingMember(false);
        };
        const onError = () => {
            setFetchingMember(false);
        };
        props.fetchHouseHoldMemberById(previewHHMemberId, onSuccess, onError);
    }, [previewHHMemberId]);

    useEffect(() => {
        setFetchingHousehold(true);
        const onSuccess = () => {
            setFetchingHousehold(false);
        };
        const onError = () => {
            setFetchingHousehold(false);
        };
        props.fetchHouseHoldById(previewHHId, onSuccess, onError);
    }, [previewHHId]);

    const handleFileRead = (e) => {
        const content = fileReader.result;
        setRow( {...JSON.parse(content), ...{id: row.id, code: row.code, name: row.name} });
        setform2({...JSON.parse(content), ...{id: row.id, code: row.code, name: row.name} });
        setRes(content.resourceObject);
    }

    const handleFileChosen = (file) => {
        fileReader = new FileReader();
        fileReader.onloadend = handleFileRead;
        fileReader.readAsText(file);
    };

    useEffect (() => {
        props.fetchService()
    }, [])

    const handleSubmit = e =>  {
        form2['resourceObject'] = JSON.parse(res);
        props.updateForm(form2.id, form2, setLoading);
    }


    return (
        <Card>
            <ToastContainer />
            <CardBody>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" to={{  pathname: "/form-home",
                        state: 'form-builder'}} >
                        Form Manager
                    </Link>
                    <Typography color="textPrimary">Edit Form -  {row ? row.name : ""}</Typography>

                </Breadcrumbs>
                <br/>

                <Row>
                    <Col md={12}>
                        {showFileImport && <>
                            <Alert onClose={toggleShowFileImport} icon={false} className={"mb-3"}>
                                <h4>Import Form from a <b>(.json)</b> file</h4>
                                <input type="file" id="file" className="input-file mb-4" accept='.json'
                                       onChange={e => handleFileChosen(e.target.files[0])}/>
                            </Alert>
                        </>
                        }
                        {loading &&
                        <LinearProgress color="primary" thickness={5}/>
                        }
                        <Card >
                            <CardContent>

                                <Row>
                                    <Col md={4}> <FormGroup>
                                        <Label class="sr-only">Display Type</Label>
                                        <Input type="select"  id="displayType" value={displayType} onChange={e => setDisplayType(e.target.value)}>
                                            <option value="form">Form</option>
                                            <option value="wizard">Wizard</option></Input>
                                    </FormGroup></Col>

                                    <Col md={2}> <FormGroup>
                                        <label class="sr-only" ></label>
                                        <button type="button"  class="form-control btn btn-primary mt-4" onClick={() => handleSubmit()}>Update Form</button>
                                    </FormGroup></Col>
                                    <Col md={2}>
                                        <div onClick={toggleModal}  className="mt-5" style={{cursor:"pointer", color:"blue"}}>Preview Form</div>
                                    </Col>
                                </Row>
                                {/*only render form when loading is false and form2 has a value*/}
                                { !loading && form2 ?
                                    <FormBuilder form={form2.resourceObject || {}} {...props}
                                                 submission={{data :{baseUrl:url, householdMember: props.member, household:props.hh}}}
                                                 onChange={(schema) => {
                                                     // console.log(JSON.stringify(schema));
                                                     setRes(JSON.stringify(schema));
                                                 }} />
                                    : ""
                                }
                                <br></br>
                            </CardContent>
                        </Card>
                    </Col>
                </Row>
                <hr></hr>
                <Card >
                    <CardContent>
                        <h4>Json Form</h4>
                        <DownloadLink
                            label="Export as a json file"
                            filename={form2 ? form2.name+".json" : "lamisplus-form.json"}
                            exportFile={() => JSON.stringify(form2)}
                        /> Or Copy the json object below. <br/>

                        <div >
                    <textarea cols="100"
                              ref={textAreaRef}
                              value={res}/>
                        </div>
                    </CardContent>
                </Card>


                {/*preview modal start*/}
                {showModal &&
                <CModal show={showModal} onClose={toggleModal} backdrop={true} size='xl'>
                    <CModalHeader closeButton>View Form</CModalHeader>
                    <CModalBody>
                        <Card>
                            <CardContent>

                                <hr/>
                                <Errors errors={props.errors}/>
                                {!res ? "" :
                                    <Form
                                        form={JSON.parse(res)}
                                        ref={form => myform = form}
                                        submission={{
                                            data: {
                                                authHeader: authHeader().Authorization,
                                                baseUrl: url,
                                                household: props.household
                                            }
                                        }}
                                        //src={url}
                                        hideComponents={props.hideComponents}
                                        //onSubmit={props.onSubmit}
                                        onSubmit={(submission) => {

                                            delete submission.data.householdMember;
                                            delete submission.data.household;
                                            delete submission.data.authHeader;
                                            delete submission.data.submit;
                                            delete submission.data.baseUrl;
                                            console.log(submission);
                                            return alert(JSON.stringify(submission))
                                        }}
                                    />
                                }
                                <br></br>
                            </CardContent>
                        </Card>
                    </CModalBody>
                    <CModalFooter>
                        <MatButton
                            variant='contained'
                            color='default'
                            onClick={toggleModal}
                            startIcon={<CancelIcon/>}
                        >
                            Cancel
                        </MatButton>
                    </CModalFooter>
                </CModal>
                }
                <hr></hr>

            </CardBody>
        </Card>
    );
}

const mapStateToProps =  (state = { form:{}}) => {
    return {
        household: state.houseHold.household,
        services: state.formReducers.services,
        formList: state.formReducers.form,
        member: state.houseHoldMember.member,
        hh: state.houseHold.household
    }}

const mapActionsToProps = ({
    fetchById: fetchById,
    updateForm: updateForm,
    fetchService: fetchService,
    fetchHouseHoldMemberById: fetchHouseHoldMemberById,
    fetchHouseHoldById: fetchHouseHoldById,
})

export default connect(mapStateToProps, mapActionsToProps)(Update)