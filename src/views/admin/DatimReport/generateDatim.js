import React, {useState, useEffect} from 'react';
import axios from "axios";
import {  Row,Col,FormGroup,Label,Input,Card,CardBody} from 'reactstrap';
import { connect } from 'react-redux'
import {  CFormGroup } from '@coreui/react';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import Select from "react-select";
import { Spinner } from 'reactstrap';
import { url as baseUrl } from "../../../api";
import {url} from '../../../api';
import {CForm} from '@coreui/react'
import { useSelector, useDispatch } from 'react-redux';
import {downloadDatim} from '../../../actions/datimPage';
import { useHistory } from "react-router-dom";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import { Link } from 'react-router-dom';

const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    },
    error: {
        color: "#f85032",
        fontSize: "12.8px",
    },
}))



const NewCboProject = (props) => {
    let history = useHistory();
    const [loading, setLoading] = useState(false)
    const cboProjectList = useSelector(state => state.cboProjects.cboProjectList);
    const dispatch = useDispatch();
    const defaultValues = { stateId: "", lgaId: "", reportStartDate: "", reportEndDate: "", organisationUnitIds:""}
    const defaultLocationValues = { state: "", lga:""}
    const [formData, setFormData] = useState( defaultValues)
    //const [errors, setErrors] = useState({});
    const [donorList, setdonorList] = useState([]);
    const [ipList, setipList] = useState([]);
    const [cboList, setcboList] = useState([]);
    const classes = useStyles()
    const [errors, setErrors] = useState({});
    const [selectedOption, setSelectedOption] = useState(null);
    const [otherDetails, setOtherDetails] = useState(defaultValues);
    const [locationDetails, setLocationDetails] = useState(defaultLocationValues);
    const [relativesLocation, setRelativesLocation] = useState([]);
    const [provinces, setProvinces] = useState([]);
    const [states, setStates] = useState([]);
    const [reportStartDate, setReportStartDate] = useState('');
    const [reportEndDate, setReportEndDate] = useState('');
    const [name, setName] = useState('');
    const [lga, setLga] = useState('');


    useEffect(() => {
        //loadCboProjectList()
        setFormData(props.formData ? props.formData : defaultValues);

    }, [props.formData]); //componentDidMount

    useEffect(() => {

        async function getCharacters() {
            axios
                .get(`${baseUrl}donors`)
                .then((response) => {
                    //console.log(Object.entries(response.data));
                    setdonorList(
                        Object.entries(response.data).map(([key, value]) => ({
                            label: value.name,
                            value: value.id,
                        }))
                    );
                })
                .catch((error) => {

                });
        }
        getCharacters();
        setStateByCountryId();
    },  [  props.showModal]);
    /*  endpoint */

    useEffect(() => {
        async function getCharacters() {
            axios
                .get(`${baseUrl}implementers`)
                .then((response) => {
                    //console.log(Object.entries(response.data));
                    setipList(
                        Object.entries(response.data).map(([key, value]) => ({
                            label: value.name,
                            value: value.id,
                        }))
                    );
                })
                .catch((error) => {

                });
        }
        getCharacters();
    },  []);
    /*  endpoint */


    useEffect(() => {
        async function getCharacters() {
            axios
                .get(`${baseUrl}cbos`)
                .then((response) => {
                    //console.log(Object.entries(response.data));
                    setcboList(
                        Object.entries(response.data).map(([key, value]) => ({
                            label: value.name,
                            value: value.id,
                        }))
                    );
                })
                .catch((error) => {

                });
        }
        getCharacters();
    },  []);
    /*  endpoint */


//Get States from selected country
    const  setStateByCountryId=() =>{
        async function getStateByCountryId() {
            const response = await axios.get(baseUrl + 'organisation-units/hierarchy/1/2')
            const stateList = response.data;
            // console.log(stateList)
            setStates(stateList);
        }
        getStateByCountryId();
    }

    const handleInputChange = e => {
        setOtherDetails ({...otherDetails,  [e.target.name]: e.target.value});
    }
    const handleInputChange2 = e => {
        setLocationDetails ({...locationDetails,  [e.target.name]: e.target.value});
        const stateId = e.target.value ;
        async function getCharacters() {
            const response = await axios.get(`${baseUrl}organisation-units/hierarchy/`+stateId+"/3");
            setProvinces(
                Object.entries(response.data).map(([key, value]) => ({
                    label: value.name,
                    value: value.id,
                }))
                // response.data

            );
        }
        getCharacters();

    }


    const addLocations2 = e => {
        console.log(selectedOption)
        if(locationDetails.state !="" && selectedOption && selectedOption.length > 0){
            const newStates = states.filter(state => state.id == locationDetails.state)
            locationDetails['state']= newStates[0].name
            locationDetails['lga']= selectedOption
            const allRelativesLocation = [...relativesLocation, locationDetails];
            setRelativesLocation(allRelativesLocation);
            setSelectedOption(null)
        }else{
            toast.error("Location can not be empty")
        }

    }


    function submitForm (e) {
        e.preventDefault()
        setLoading(true);
        axios({
            url: `${url}data-reports/datim-flat-file`,
            method: "GET",
            responseType: 'blob',
        }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'datim.csv');
            document.body.appendChild(link);
            link.click();
            setLoading(false);
            props.history.push(`/datim-report`)
            toast.success('Report Downloaded successfully');
            return;
        }).catch(error => {
                toast.error('Error: Could not download flat file!');
            }
        )
    }

    return (

        <div >
            <ToastContainer />
            <Card>
                <ToastContainer/>
                <CardBody>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" to={{pathname: "/datim-report"}} >
                            Generate DATIM Reports
                        </Link>
                    </Breadcrumbs>
                    <br/>
                    <Card>
                        <CForm onSubmit={submitForm} >
                            <Card >
                                <CardBody>
                                    <Row>
                                        <Col md={6}>
                                            <FormGroup>
                                                <Label >State*</Label>
                                                <Input
                                                    type="select"
                                                    name="state"
                                                    id="state"
                                                    value={locationDetails.state}
                                                    onChange={handleInputChange2}
                                                    required>
                                                    <option >Please Select State</option>
                                                    {states.map((row) => (
                                                        <option key={row.id} value={row.id}>
                                                            {/*onChange={(e) => setName(e.target.value)}>*/}
                                                            {row.name}
                                                        </option>
                                                    ))}
                                                </Input>
                                                {errors.state !=="" ? (
                                                    <span className={classes.error}>{errors.state}</span>
                                                ) : "" }
                                            </FormGroup>
                                        </Col>
                                        <Col md={6}>
                                            <CFormGroup>
                                                <Label >LGA*</Label>
                                                <Select
                                                    onChange={setSelectedOption}
                                                    value={selectedOption}
                                                    options={provinces}
                                                    isMulti="true"
                                                    noOptionsMessage="true"/>
                                                {/*onChange={(e) => setLga(e.target.value)}>*/}
                                                {errors.lga !=="" ? (
                                                    <span className={classes.error}>{errors.lga}</span>
                                                ) : "" }
                                            </CFormGroup>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col md={6}>
                                            <FormGroup>
                                                <Label for="reportStartDate">
                                                    Report Start Date
                                                </Label>
                                                <Input
                                                    id="reportStartDate"
                                                    name="reportStartDate"
                                                    placeholder="Start Date"
                                                    type="date"
                                                    value={reportStartDate}
                                                    onChange={(e) => setReportStartDate(e.target.value)}
                                                />
                                            </FormGroup>
                                        </Col>
                                        <Col md={6}>
                                            <FormGroup>
                                                <Label for="Date">
                                                    Report End Date
                                                </Label>
                                                <Input
                                                    id="reportEndDate"
                                                    name="reportEndDate"
                                                    placeholder="reportEndDate"
                                                    type="date"
                                                    value={reportEndDate}
                                                    onChange={(e) => setReportEndDate(e.target.value)}
                                                />
                                            </FormGroup>
                                        </Col>
                                    </Row>
                                    <MatButton
                                        type='submit'
                                        variant='contained'
                                        color='primary'
                                        className={classes.button}
                                        startIcon={<SaveIcon />}
                                        disabled={loading}>
                                        <span style={{textTransform: 'capitalize'}}>Generate DATIM Flat File</span>   {loading ? <Spinner /> : ""}
                                    </MatButton>
                                </CardBody>
                            </Card>
                        </CForm>
                    </Card>
                </CardBody>
            </Card>
        </div>

    );
}
export default connect(null, {downloadDatim})(NewCboProject);

