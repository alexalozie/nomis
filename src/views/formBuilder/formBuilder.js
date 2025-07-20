import React, {useRef, useEffect, useState}  from 'react';
import { connect } from 'react-redux';
import {  Errors, FormBuilder } from 'react-formio';
import {Card,CardContent,} from '@material-ui/core';
import { Link } from 'react-router-dom';
import {FormGroup, Input, Label, Col, Row, Form} from 'reactstrap';
import MatButton from '@material-ui/core/Button';
import { TiArrowBack } from "react-icons/ti";
import CheckBoxOutlineBlankIcon from '@material-ui/icons/CheckBoxOutlineBlank';
import CheckBoxIcon from '@material-ui/icons/CheckBox';
import {ToastContainer} from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import { Alert } from '@material-ui/lab';
import axios from 'axios';
import { url } from "./../../api";
import Select from 'react-select';
import {createForm, fetchDomain, fetchDomainServices} from './../../actions/formBuilder'
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";


const Create = props => {
    const [res, setRes] = React.useState("");
    const [formData, setFormData] = React.useState({
        resourceObject: null,
        display: ""
    });

    const textAreaRef = useRef(null);
    const [form, setform] = useState([{title: 'Loading', value: ''}]);
    const [formType, setformType] = React.useState("");
    const [domainCode, setdomainCode] = React.useState("");
    const [disabledCheckBox, setdisabledCheckBox] = useState(true)
    const [showFileImport, setShowFileImport] = useState(true);
    const toggleShowFileImport = () => setShowFileImport(!showFileImport);
    const [isSupportingForm, setIsSupportingForm] = React.useState(false);
    const icon = <CheckBoxOutlineBlankIcon fontSize="small"/>;
    const checkedIcon = <CheckBoxIcon fontSize="small"/>;
    let fileReader;

    const handleFileRead = (e) => {
        const content = fileReader.result;
        setFormData(JSON.parse(content));
        setRes(content.resourceObject);

    }

    const handleFileChosen = (file) => {
        fileReader = new FileReader();
        fileReader.onloadend = handleFileRead;
        fileReader.readAsText(file);
    };

    useEffect(() => {
        async function getCharacters() {
            try {

                const response = await axios(
                    url + "forms"
                );
                const body = response.data;

                setform(
                    body.map(({ name, code }) => ({ title: name, value: code }))
                );
                //body !==null ? setdisabledCheckBox(false) : setdisabledCheckBox(true)

            } catch (error) {
            }
        }
        getCharacters();
    }, []);


    useEffect (() => {
      props.fetchDomain()
    }, [])

    const fetchServices = (domainCode, formType) => {
        if(domainCode && formType) {
            props.fetchService(domainCode, formType)
        }
    }


    const handleSubmit = e => {
        formData['resourceObject']=res;
        formData['id'] = "";
        e.preventDefault();
        props.createForm(formData);
    }
    return (
        <div >
            <ToastContainer autoClose={3000} hideProgressBar />
            <Card >
                <CardContent>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" to={{pathname: "/admin"}} >
                        Admin
                    </Link>
                    <Typography color="textPrimary">Form Builder</Typography>
                </Breadcrumbs>
                    <Link to ={{
                        pathname: "/form-home",
                        // pathname: "/form-home",
                        state: 'form-builder'
                    }}>
                        <MatButton
                            type="submit"
                            variant="contained"
                            color="primary"
                            className=" float-right mr-1">
                            <TiArrowBack /> &nbsp; back
                        </MatButton>
                    </Link>
                    <h4>Create Form</h4>
                    <hr />
                    <Errors errors={props.errors} />
                    {/*<Alert color="info" icon={false} className={"mb-3"}>*/}
                    {/*<h4>To create a form </h4>*/}
                    {/*    <p onClick={toggleShowFileImport} style={{cursor:"pointer"}}>- Click to <b>import</b> form from a <b>.json</b> file OR </p>*/}
                    {/*    <p>- <b>Fill</b> the form below</p>*/}
                    {/*</Alert>*/}
                    {showFileImport && <>
                        <Alert onClose={toggleShowFileImport} icon={false} className={"mb-3"}>
                            <h4>Import Form from a <b>(.json)</b> file</h4>
                            <input type="file" id="file" className="input-file mb-4" accept='.json'
                                   onChange={e => handleFileChosen(e.target.files[0])}/>

                        </Alert>
                    </>
                    }

                    <Form onSubmit={handleSubmit} >
                        <Row>
                            <Col md={4}> <FormGroup>
                                <Label class="sr-only">Display Type</Label>
                                <Input type="select"  id="display" value={formData.display} onChange={e => setFormData({...formData, display:e.target.value})}>
                                    <option value="form">Form</option>
                                    <option value="wizard">Wizard</option></Input>
                            </FormGroup></Col>

                            <Col md={4}> <FormGroup>
                                <Label class="sr-only">Form Name</Label>
                                <Input type="text" class="form-control" id="name" name="name" value={formData.name}   onChange={e => setFormData({...formData, name:e.target.value})} required/>
                            </FormGroup> </Col>


                            <Col md={4}> <FormGroup>
                                <Label class="sr-only">Version</Label>
                                <Input type="text" class="form-control" id="version" name="version" value={formData.version}   onChange={e => setFormData({...formData, version:e.target.value})} required/>
                            </FormGroup> </Col>
                            <Col md={4}> <FormGroup>
                                <Label class="sr-only">Form Type</Label>
                                <Input type="select"  id="formType"  name="formType" value={formData.formType} onChange={e => setFormData({...formData, formType:e.target.value})}>
                                    <option></option>
                                    
                                    <option value="1">VC</option>
                                    <option value="2">Care Giver</option>
                                    <option value="3"> Both</option>
                                </Input>
                            </FormGroup></Col>
                            <Col md={12} className={"pb-2"}>
                            <FormGroup check>
                                <Label check>
                                    <Input type="checkbox" onChange={e=>setIsSupportingForm(e.target.checked)} />{' '}
                                    Is this a supporting form? If yes, check the box and select the supporting services.
                                </Label>
                            </FormGroup>
                            </Col>
                            {isSupportingForm ?
                                <Col md={12}>
                                    <Row>
                            <Col md={4}> <FormGroup>
                                <Label class="sr-only">Domain Name</Label>
                                {props.domains && props.domains.length && props.domains.length > 0 ?
                                    <Input type="select" class="form-control" id="domainCode" required value={domainCode}  onChange={e => {
                                        setdomainCode(e.target.value);
                                        fetchServices(e.target.value, formType);
                                    } }>
                                        <option >Select a domain</option>
                                        {props.domains.map(domain => (<option key={domain.code} value={domain.id} >{domain.name}</option>))}
                                    </Input>:  <Input type="select" class="form-control" id="domainCode" required value={domainCode} onChange={e => setdomainCode(e.target.value)}>
                                        <option>No Domain found</option>
                                    </Input>}
                            </FormGroup></Col>
                            
                            <Col md={4}> <FormGroup>
                                <Label class="sr-only">Service</Label>
                                {props.services && props.services.length && props.services.length > 0 ?
                                    <Select isMulti
                                            name="colors"
                                            options={props.services.map((service) => ({label:service.name, value:service.code}))}
                                            onChange={(newValue) => {
                                                setFormData({...formData, supportingServices: newValue.map(({value}) => value).toString()})
                                            }
                                            }
                                    />
                                    :  <Input type="select" class="form-control" id="ovcServiceCode" required >
                                        <option>No Services Found</option>
                                    </Input>}
                            </FormGroup></Col>
                                    </Row>
                                </Col>
: <></>}
                            <Col md={2}> <FormGroup>
                                <label class="sr-only"></label>
                                <button type="submit"  class="form-control btn btn-primary mt-4" >Save Form</button>
                            </FormGroup></Col>
                        </Row>
                    </Form>
                    {/*display the resource object if the form is imported else display the default formData object*/}
                    <FormBuilder form={formData.resourceObject || formData}
                                 submission={{data :{baseUrl:url}}}
                                 saveText={'Create Form'} onChange={(schema) => {
                        setRes(JSON.stringify(schema));
                    }} />
                    <br></br>
                    <div>
                        <h4>Json Form</h4>
                        <textarea cols="50" ref={textAreaRef} value={res}/>
                    </div>
                </CardContent>
            </Card>
        </div>
    );

}
const mapStateToProps =  (state = { form:{}}) => {
    return {
        services: state.formReducers.services,
        domains: state.formReducers.domains,
    }}

const mapActionsToProps = ({
    fetchService: fetchDomainServices,
    fetchDomain: fetchDomain,
    createForm: createForm,
})

export default connect(mapStateToProps, mapActionsToProps)(Create)
