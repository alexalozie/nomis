import React, {useState, useEffect} from 'react';
import {  Modal, ModalHeader, ModalBody, FormFeedback,Form,Row,Col,FormGroup,Label,Input,Card,CardBody} from 'reactstrap';
import { connect } from 'react-redux';
import MatButton from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import SaveIcon from '@material-ui/icons/Save'
import CancelIcon from '@material-ui/icons/Cancel'
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import { createDomain, updateDomain } from "../../../actions/domainsServices";
import { Spinner } from 'reactstrap';



const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    },
    error: {
        color: "#f85032",
        fontSize: "12.8px",
    },
}))

const NewDomain = (props) => {
    const [loading, setLoading] = useState(false)
    const defaultValues = {name:""}
    const [formData, setFormData] = useState(defaultValues)
    const classes = useStyles()
    const [errors, setErrors] = useState({});
    /*****  Validation */
    const validate = () => {
        let temp = { ...errors };
        temp.name = formData.name
            ? ""
            : "Name is required";
            
        setErrors({
            ...temp,
        });
        
        return Object.values(temp).every((x) => x == "");
    };

const closeModal = ()=>{
    resetForm()
    props.toggleModal()
}

    useEffect(() => {
        //for Domain Area edit, load form data
        if(props.currentDomain){
            setFormData({...formData, name:props.currentDomain.name });
        }
        //props.currenDomain=null
    },  [props.currentDomain,  props.showModal]);

    const handleNameInputChange = e => {

        setFormData ({ ...formData, [e.target.name]: e.target.value  });
    }

    const resetForm = () => {
        setFormData(defaultValues)
    }

    const createGlobalDomain = e => {
        e.preventDefault()
        if (validate()) {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
            props.loadDomains();
            props.toggleModal()
            resetForm()
        }
        const onError = () => {
            setLoading(false);
           
        }
        if(props.currentDomain){
            props.updateDomain(props.currentDomain.id, formData, onSuccess, onError)
            resetForm()
            return
        }
        props.createDomain(formData, onSuccess,onError)
    }
    }


    return (

        <div >
         
            <Modal isOpen={props.showModal} toggle={props.toggleModal} size="lg" zIndex={"9999"} backdrop={false} backdrop="static">

                <Form onSubmit={createGlobalDomain}>
                    <ModalHeader >New Domain </ModalHeader>
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
                                                onChange={handleNameInputChange}
                                                
                                            />
                                             {errors.name !=="" ? (
                                                <span className={classes.error}>{errors.name}</span>
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

                                    <span style={{textTransform: 'capitalize'}}>Save </span> 
                                    {loading ? <Spinner />
                                     : ""}
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
                    </ModalBody>

                </Form>
            </Modal>
        </div>
    );
}


export default connect(null, { createDomain, updateDomain })(NewDomain);


