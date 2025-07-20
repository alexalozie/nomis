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
import Select from "react-select";
import { createIp, updateIp  } from "../../../actions/ip";
import { Spinner } from 'reactstrap';
import { url as baseUrl } from "../../../api";
import { CModal, CModalHeader, CModalBody,CForm} from '@coreui/react'
import { useSelector, useDispatch } from 'react-redux';
import { createCboProject } from '../../../actions/cboProject'
import { useHistory } from "react-router-dom";
import DeleteIcon from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';
import List from "@material-ui/core/List";

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
    const defaultValues = { stateId: "", lgaId: "", description: "", cboId:"", implementerId: "", donorId: "", organisationUnitIds:""}
    const defaultLocationValues = { state: "", lga:""}
    //const [errors, setErrors] = useState({});
    const [donorList, setdonorList] = useState([]);
    const [ipList, setipList] = useState([]);
    const [cboList, setcboList] = useState([]);
    const classes = useStyles()
    const [errors, setErrors] = useState({});
    const [selectedOption, setSelectedOption] = useState(null);
    const [locationList, setLocationList] = useState({ stateName:"", lga:""})
    const [otherDetails, setOtherDetails] = useState(defaultValues);
    const [locationDetails, setLocationDetails] = useState(defaultLocationValues);
    const [relativesLocation, setRelativesLocation] = useState([]);
    const [provinces, setProvinces] = useState([]);
    const [stateList, setStateList] = useState();
    const [lgaDetail, setLgaDetail] = useState();
    const [stateDetail, setStateDetail] = useState();
    const [states, setStates] = useState([]);
   const [locationListArray2, setLocationListArray2] = useState([])


    useEffect(() => {
        //loadCboProjectList()
        setOtherDetails(props.formData ? props.formData : defaultValues);
        
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

    /* Remove Relative Location function **/
    const removeRelativeLocation = index => {       
        relativesLocation.splice(index, 1);
        setRelativesLocation([...relativesLocation]);
       
    };
    const resetForm = () => {
        setOtherDetails(defaultValues)
        setRelativesLocation({state: "", lga:""})  
    }

    /*****  Validation */
    const validate = () => {
        let temp = { ...errors };
            temp.donorId = otherDetails.donorId
            ? ""
            : "Donor is required";
            temp.description = otherDetails.description
            ? ""
            : "Project Name is required";
            temp.cboId = otherDetails.cboId
            ? ""
            : "CBO is required";
            temp.implementerId = otherDetails.implementerId
            ? ""
            : "Implementing Partner is required";
        setErrors({
            ...temp,
        });
        return Object.values(temp).every((x) => x == "");
    };
    const organisationUnitIds = []
    const createCboAccountSetup = e => {
        e.preventDefault()

        if(relativesLocation.length >0 && validate()){
        const orgunitlga= relativesLocation.map(item => { 
            delete item['state'];
            
            item['lga'].map(itemLga => {
                console.log(itemLga['value'])
            organisationUnitIds.push(itemLga['value'])

            return itemLga;
            })
        })
        otherDetails['organisationUnitIds'] = organisationUnitIds
        delete otherDetails['lgaId'];
        delete otherDetails['stateId'];       
        setLoading(true);
        const onSuccess = () => {
            setLoading(false)
            setOtherDetails(defaultValues)  
            setLocationListArray2([]) 
            history.push('/cbo-donor-ip')
            props.toggleModal() 
            props.loadIps() 
            resetForm()
                  
        }
        const onError = () => {
            setLoading(false)  
            setOtherDetails(defaultValues)  
            setLocationListArray2([])
            history.push('/cbo-donor-ip') 
            props.toggleModal() 
        }       
        dispatch(createCboProject(otherDetails,onSuccess, onError));       
        return

    }else if(!validate()){
        return
    }else{
        toast.error("Location can't be empty")
    }
 
    }
    
    const closeModal = ()=>{
        resetForm()
        props.toggleModal()
        setLocationList({stateName: "", lga:""}) 
        setErrors({}) 
    }

    return (

        <div >
        <ToastContainer />
            <CModal show={props.showModal} onClose={props.toggleModal} size="lg" zIndex={"9999"} backdrop={false} backdrop="static">
                <CForm >
                    <CModalHeader toggle={props.toggleModal}>NEW CBO PROJECT SETUP </CModalHeader>
                    <CModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                <Col md={6}>
                                        <FormGroup>
                                            <Label >Project Name*</Label>
                                            <Input
                                            type="text"
                                            name="description"
                                            id="description"
                                            value={otherDetails.description}
                                            onChange={handleInputChange}
                                            required
                                            />
                                           {errors.description !=="" ? (
                                                <span className={classes.error}>{errors.description}</span>
                                            ) : "" }
                                        </FormGroup>
                                    </Col>
                                <Col md={6}>
                                        <FormGroup>
                                            <Label >Donors *</Label>
                                            <Input
                                            type="select"
                                            name="donorId"
                                            id="donorId"
                                            value={otherDetails.donorId}
                                            onChange={handleInputChange}
                                            required
                                            >
                                            <option value=""> </option>
                                            {donorList.map(({ label, value }) => (
                                                <option key={value} value={value}>
                                                {label}
                                                </option>
                                            ))}
                                            </Input>
                                            {errors.donorId !=="" ? (
                                                <span className={classes.error}>{errors.donorId}</span>
                                            ) : "" }
                                        </FormGroup>
                                    </Col>
                                    
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label >Implementing Partners *</Label>
                                            <Input
                                            type="select"
                                            name="implementerId"
                                            id="implementerId"
                                            value={otherDetails.implementerId}
                                            onChange={handleInputChange}
                                            required>
                                            <option value=""> </option>
                                            {ipList.map(({ label, value }) => (
                                                <option key={value} value={value}>
                                                {label}
                                                </option>
                                            ))}
                                            </Input>
                                            {errors.implementerId !=="" ? (
                                                <span className={classes.error}>{errors.implementerId}</span>
                                            ) : "" }
                                        </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label >CBO *</Label>
                                            <Input
                                            type="select"
                                            name="cboId"
                                            id="cboId"
                                            value={otherDetails.cboId}
                                            onChange={handleInputChange}
                                            required>
                                            <option value=""> </option>
                                            {cboList.map(({ label, value }) => (
                                                <option key={value} value={value}>
                                                {label}
                                                </option>
                                            ))}
                                            </Input>
                                            {errors.cboId !=="" ? (
                                                <span className={classes.error}>{errors.cboId}</span>
                                            ) : "" }
                                        </FormGroup>
                                    </Col>
                                    <Col md={12}>
                                        <hr/>
                                        <h4>Location</h4>
                                    </Col>
                                    
                                    <Col md={5}>
                                        <FormGroup>
                                            <Label >State *</Label>
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
                                                            {row.name}
                                                        </option>
                                                    ))}
                                            </Input>
                                            {errors.state !=="" ? (
                                                <span className={classes.error}>{errors.state}</span>
                                            ) : "" }
                                        </FormGroup>
                                    </Col>
                                    
                                    <Col md={5}>
                                                <CFormGroup>
                                                    <Label >LGA* </Label>
                                                    <Select
                                                        onChange={setSelectedOption}
                                                        value={selectedOption}
                                                        options={provinces}
                                                        isMulti="true"
                                                        noOptionsMessage="true"/>
                                                    {errors.lga !=="" ? (
                                                    <span className={classes.error}>{errors.lga}</span>
                                                ) : "" }
                                                </CFormGroup>

                                    </Col>
                                    
                                    <Col md={2}>
                                    <Button variant="contained"
                                        color="primary"
                                        //startIcon={<FaPlus />} 
                                        style={{ marginTop:25}}
                                        size="small"
                                        onClick={addLocations2}>
                                        <span style={{textTransform: 'capitalize'}}> Add </span>
                                    </Button>
                                    </Col>
                                    <Col md={12}>
                                                  <div className={classes.demo}>
                                                  {relativesLocation.length >0 
                                                    ?
                                                      <List>
                                                      <Table  striped responsive>
                                                            <thead >
                                                                <tr>
                                                                    <th>State</th>
                                                                    <th>LGA</th>
                                                                    <th ></th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                            {relativesLocation.map((relative, index) => (

                                                                <RelativeList
                                                                    key={index}
                                                                    index={index}
                                                                    relative={relative}
                                                                    removeRelativeLocation={removeRelativeLocation}
                                                                />
                                                          ))}
                                                          </tbody>
                                                        </Table>
                                                      </List>
                                                     :
                                                     ""
                                                  }               
                                                  </div>
                                            </Col>
                                </Row>

                                <MatButton
                                    type='submit'
                                    variant='contained'
                                    color='primary'
                                    className={classes.button}
                                    startIcon={<SaveIcon />}
                                    disabled={loading}
                                    onClick={createCboAccountSetup}>
                                    <span style={{textTransform: 'capitalize'}}>Save</span>  {loading ? <Spinner /> : ""}
                                </MatButton>
                                <MatButton
                                    variant='contained'
                                    color='default'
                                    onClick={closeModal}
                                    startIcon={<CancelIcon />}>
                                    <span style={{textTransform: 'capitalize'}}>Cancel</span>
                                </MatButton>
                            </CardBody>
                        </Card>
                    </CModalBody>

                </CForm>
            </CModal>
        </div>
    );
}

function RelativeList({
    relative,
    index,
    removeRelativeLocation,
  }) {
    function LgaList (selectedOption){
        const  maxVal = []
        if (selectedOption != null && selectedOption.length > 0) {
          for(var i=0; i<selectedOption.length; i++){
             
                  if ( selectedOption[i].label!==null && selectedOption[i].label)
                        //console.log(selectedOption[i])
                            maxVal.push(selectedOption[i].label)
              
          }
        return maxVal.toString();
        }
    }
    return (
            <tr>
                <th>{relative.state}</th>
                <th>{LgaList(relative.lga)}</th>
                
                <th >
                    <IconButton aria-label="delete" size="small" color="error" onClick={() =>removeRelativeLocation(index)}>
                        <DeleteIcon fontSize="inherit" />
                    </IconButton>
                    
                </th>
            </tr> 
    );
  }


export default connect(null, {createIp, updateIp})(NewCboProject);

