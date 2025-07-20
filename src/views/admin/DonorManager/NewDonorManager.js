import React, {useState, useEffect} from 'react';
import {  Modal, ModalHeader, ModalBody,Form,Row,Col,FormGroup,Label,Input,Card,CardBody} from 'reactstrap';
import { connect } from 'react-redux';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
//import Select from "react-select/creatable";
import { createDonor, updateDonor  } from "./../../../actions/donors";
import { Spinner } from 'reactstrap';



const useStyles = makeStyles(theme => ({
    error: {
        color: "#f85032",
        fontSize: "12.8px",
    },
    button: {
        margin: theme.spacing(1)
    }
}))

const NewDonor = (props) => {
    const [loading, setLoading] = useState(false)
    //const [showNewCbo, setShowNewCbo] = useState(false)
    const defaultValues = { id:"",  name:"", description:"", code:""};
    const [formData, setFormData] = useState( defaultValues)
    //const [errors, setErrors] = useState({});
    const classes = useStyles()
    const [errors, setErrors] = useState({});
     /*****  Validation */
     const validate = () => {
        let temp = { ...errors };
        temp.name = formData.name
            ? ""
            : "Name is required";
            temp.description = formData.description
            ? ""
            : "Description  is required";    
        setErrors({
            ...temp,
        });
        
        return Object.values(temp).every((x) => x == "");
    };
    useEffect(() => {
        //for application CBO  edit, load form data
        //props.loadCbo();
        setFormData(props.formData ? props.formData : defaultValues);
        //setShowNewCbo(false);
    },  [props.formData,  props.showModal]);

    const handleInputChange = e => {
        setFormData ({ ...formData, [e.target.name]: e.target.value});
    }
    const resetForm = () => {
        setFormData(defaultValues)
    }

    const createDonorSetup = e => {
        
        e.preventDefault()
        if (validate()) {
            setLoading(true);
            const onSuccess = () => {
                setLoading(false);
                props.loadDonors();
                props.toggleModal()
                resetForm()
            }
            const onError = () => {
                setLoading(false);
                props.toggleModal()
            }
            if(formData.id){
                props.updateDonor(formData.id, formData, onSuccess, onError)
                return
            }
            props.createDonor(formData, onSuccess,onError)
        }
    }


    return (

        <div >
           
            <Modal isOpen={props.showModal} toggle={props.toggleModal} size="lg" zIndex={"9999"} backdrop={false} backdrop="static">

                <Form onSubmit={createDonorSetup}>
                    <ModalHeader toggle={props.toggleModal}>New Donor Setup </ModalHeader>
                    <ModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                    
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>Name</Label>
                                            <Input
                                                type='text'
                                                name='name'
                                                id='name'
                                                placeholder=' '
                                                value={formData.name}
                                                onChange={handleInputChange}
                                                style={{textTransform: "upperCase" }} 
                                            />
                                            {errors.name !=="" ? (
                                                <span className={classes.error}>{errors.name}</span>
                                            ) : "" }
                                        </FormGroup>
                                    </Col>

                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>Description  (Address/Phone Number/Email)</Label>
                                            <Input
                                                type='textarea'
                                                name='description'
                                                id='description'
                                                placeholder=' '
                                                value={formData.description}
                                                onChange={handleInputChange}
                                                
                                            />
                                            {errors.description !=="" ? (
                                                <span className={classes.error}>{errors.description}</span>
                                            ) : "" }
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
                                    <span style={{textTransform: 'capitalize'}}>Save</span>  {loading ? <Spinner /> : ""}
                                </MatButton>
                                <MatButton
                                    variant='contained'
                                    color='default'
                                    onClick={props.toggleModal}
                                    startIcon={<CancelIcon />}>
                                    <span style={{textTransform: 'capitalize'}}>Cancel</span>
                                </MatButton>
                            </CardBody>
                        </Card>
                    </ModalBody>

                </Form>
            </Modal>
        </div>
    );
}


export default connect(null, {createDonor, updateDonor})(NewDonor);

