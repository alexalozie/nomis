import React, {useState, useEffect} from 'react';
import axios from "axios";
import {  Row,Col,FormGroup,Label,Input,Card,CardBody, Table,} from 'reactstrap';
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
import { url  } from "../../../api";
import { CModal, CModalHeader, CModalBody,CForm} from '@coreui/react'
import { useSelector, useDispatch } from 'react-redux';
import { createCboProject } from '../../../actions/cboProject'
import { useHistory } from "react-router-dom";
import DeleteIcon from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';
import List from "@material-ui/core/List";
import Autocomplete, { createFilterOptions } from '@material-ui/lab/Autocomplete';
import {createOrgUnitLevel} from './../../../actions/organizationalUnit'


const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    },
    error: {
        color: "#f85032",
        fontSize: "12.8px",
    },
}))
const filterOptions = createFilterOptions({
    matchFrom: 'start',
    stringify: (option) => option.title,
  });


const NewCboProject = (props) => {
    let history = useHistory();
    const [loading, setLoading] = useState(false)
    const dispatch = useDispatch();
    const defaultDetailValues = { name: "", description:""}
    const classes = useStyles()
    const [errors, setErrors] = useState({});
    const [otherDetailFields, setOtherDetailFields] = useState(false);
    const [locationList, setLocationList] = useState({ stateName:"", lga:""})
    const [otherDetails, setOtherDetails] = useState(defaultValues);
    const [details, setDetails] = useState(defaultDetailValues);
    const [relativesLocation, setRelativesLocation] = useState([]);
    const [locationListArray2, setLocationListArray2] = useState([])

    const orgUnitIDParam = props.orgUnitID ? props.orgUnitID :{};
    const [otherfields, setOtherFields] = useState({state:"", name:"", description:"", lga:""});
    const defaultValues = {name:"",id:"" }
    const [formData, setFormData] = useState(defaultValues)
    const [pcrOptions, setOptionPcr] = useState([]);
    const [orgUnitLevel, setOrgUnitLevel] = useState([]);
    const [states, setStates] = useState([]);
    const [provinces, setProvinces] = useState([]);

    const handleOtherFieldInputChange = e => {
        setOtherFields ({ ...otherfields, [e.target.name]: e.target.value });
  }


    useEffect(() => {
        setStateByCountryId()
        setOtherDetails(props.formData ? props.formData : defaultValues);
        
    }, [props.formData]); //componentDidMount

    const validate = () => {
        let temp = { ...errors }
        temp.name = details.name ? "" : "This field is required"
        temp.description = details.description ? "" : "This field is required"
        setErrors({
            ...temp
            })    
        return Object.values(temp).every(x => x == "")
  }
  
//Get States from selected country
const  setStateByCountryId=() =>{
    async function getStateByCountryId() {
        const response = await axios.get(url + 'organisation-units/hierarchy/1/2')
        const stateList = response.data;
       // console.log(stateList)
        setStates(stateList);
    }
    getStateByCountryId();
}

const handleInputChange2 = e => {
    const stateId = e.target.value ;
    async function getCharacters() {
        const response = await axios.get(`${url}organisation-units/hierarchy/`+stateId+"/3");
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

  
  const createOrgUnit = (e) => {
      e.preventDefault();
      setOtherFields({ ...otherfields, organisationUnitLevelId: props.orgUnitID });
      otherfields['organisationUnitLevelId'] = props.orgUnitID.id
      otherfields['parentOrganisationUnitId'] = props.orgUnitID.id ===1 ? 0 : otherfields.parentOrganisationUnitId
     //check if the Org Unit Level ID is 1 which is country
   
      const onSuccess = () => {
          setLoading(false);
          props.togglestatus();
      };
      const onError = () => {
          setLoading(false);
          props.togglestatus();
      };
  
      props.createOrgUnitLevel(otherfields,onSuccess, onError);
  }

    const handleInputChange = e => {
        setDetails ({...details,  [e.target.name]: e.target.value});
    }
   
    const addLocations2 = e => { 
        if(validate()){
            const allRelativesLocation = [...relativesLocation, details];
            setRelativesLocation(allRelativesLocation);
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
    const handleCheckedBox = e => {
      
        var isChecked = e.target.checked;
        console.log(isChecked)
        setOtherDetailFields(isChecked)
    }
    const closeModal = ()=>{
        resetForm()
        props.toggleModal()
        setDetails({name: "", description:""}) 
        setErrors({}) 
    }

    return (

        <div >
        <ToastContainer />
            <CModal show={props.showModal}  size="lg" zIndex={"9999"} backdrop={false} >
                <CForm >
                    <CModalHeader toggle={props.toggleModal}>Create School</CModalHeader>
                    <CModalBody>
                        <Card >
                            <CardBody>
                                <Row>
                                <Col md={6}>
                                <FormGroup>
                                    <Label for="">State</Label>
                                            <Input
                                            type="select"
                                            name="state"
                                            id="state"
                                            value={otherfields.state}
                                            onChange={handleInputChange2}
                                            required>
                                                <option ></option>
                                                {states.map((row) => (
                                                    <option key={row.id} value={row.id}>
                                                        {row.name}
                                                    </option>
                                                ))}
                                        </Input>
                                    </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                            <FormGroup>
                                              <Label for="">LGA </Label>
                                              <Select
                                                options={provinces}
                                                isMulti="true"
                                                noOptionsMessage="true"/>
                                          </FormGroup>
                                    </Col>
                                    <br/>
                                    <Col md={12}>
                                        <hr/>
                                        <h5>School Information</h5>
                                        <hr/>
                                    </Col>
                                    
                                    <Col md={5}>
                                        <FormGroup>
                                            <Label >School Name</Label>
                                            <Input
                                                  type="text"
                                                  name="name"
                                                  id="name"
                                                  value={otherfields.name}
                                                  onChange={handleOtherFieldInputChange}/>
                                        </FormGroup>
                                    </Col>
                                    <Col md={5}>
                                <FormGroup>
                                    <Label for="">School Type</Label>
                                            <Input
                                            type="select"
                                            name="state"
                                            id="state"
                                            value={otherfields.state} required>
                                                <option >Select School Type</option>
                                                <option value="">Formal</option>
                                                <option value="">Informal </option>
                                        </Input>
                                    </FormGroup>
                                    </Col>
                                    <Col md={10}>
                                                <CFormGroup>
                                                    <Label >School Address </Label>
                                                    <Input
                                                        type="textarea"
                                                        name="description"
                                                        id="description"
                                                        value={otherfields.description}
                                                        onChange={handleOtherFieldInputChange}/>
                                                </CFormGroup>
                                    </Col>
                                    
                                    <Col md={2}>
                                    <Button variant="contained"
                                        color="primary"
                                        //startIcon={<FaPlus />} 
                                        style={{ marginTop:25}}
                                        size="small"
                                        onClick={addLocations2}
                                        >
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
                                                                    <th>Name</th>
                                                                    <th>Description</th>
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
                                    
                                    Save  {loading ? <Spinner /> : ""}
                                </MatButton>
                                <MatButton
                                    variant='contained'
                                    color='default'
                                    onClick={closeModal}
                                    startIcon={<CancelIcon />}>
                                    Cancel
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
                <th>{relative.name}</th>
                <th>{relative.description}</th>
                
                <th >
                    <IconButton aria-label="delete" size="small" color="error" onClick={() =>removeRelativeLocation(index)}>
                        <DeleteIcon fontSize="inherit" />
                    </IconButton>
                    
                </th>
            </tr> 
    );
  }


export default connect(null, {createIp, updateIp})(NewCboProject);

