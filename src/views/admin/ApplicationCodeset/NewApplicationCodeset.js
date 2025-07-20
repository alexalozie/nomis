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
import { createDataElement , updateDataElement } from "../../../actions/datimPage";
import { Spinner } from 'reactstrap';
import {toast} from "react-toastify";


const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const NewApplicationCodeset = (props) => {
    const [loading, setLoading] = useState(false)
    const [state, setState] = useState(true);
    const [showNewCodesetGroup, setShowNewCodesetGroup] = useState(false)
    const defaultValues = {dataCode:"", dataId:"", dataName:"", dataDescription:"", age:"", ageId:"", sex:"", sexId:"",  status:"", statusId:"",};
    const [formData, setFormData] = useState( defaultValues)
    //const [errors, setErrors] = useState({});
    const classes = useStyles()

    useEffect(() => {
        setFormData(props.formData ? props.formData : defaultValues);
        setShowNewCodesetGroup(false);
    },  [props.formData,  props.showModal]);

    const handleInputChange = e => {
        setFormData ({ ...formData, [e.target.name]: e.target.value});
    }

    const handleCodesetGroupChange = (newValue) => {
        setFormData ({ ...formData, codesetGroup: newValue.value});
    };


    const createGlobalVariable = e => {
            e.preventDefault()
        setLoading(true);

        const onSuccess = () => {
            setLoading(false);
            props.loadCodeset();
            props.toggleModal()
        }
        const onError = () => {
            setLoading(false);
            toast.error("An error occured, could not save new Data Element");
            //  props.toggleModal()
        }
        if(formData.id){
            props.updateDataElement(formData.id, formData, onSuccess, onError)
            return
        }
        props.createDataElement(formData, onSuccess,onError)

    }
    return (

        <div >
            <ToastContainer />
            <Modal isOpen={props.showModal} toggle={props.toggleModal} size="lg">
                <Form onSubmit={createGlobalVariable}>
                    <ModalHeader toggle={props.toggleModal}>Create New Data Element</ModalHeader>
                    <ModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label>Data Code</Label>
                                            <Input
                                                type='text'
                                                name='dataCode'
                                                id='dataCode'
                                                placeholder='Enter data element code'
                                                value={formData.dataCode}
                                                onChange={handleInputChange}
                                                required/>
                                        </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label>Data Id</Label>
                                            <Input
                                                type='text'
                                                name='dataId'
                                                id='dataId'
                                                placeholder='data element Id'
                                                value={formData.dataId}
                                                onChange={handleInputChange}
                                                required/>
                                        </FormGroup>
                                    </Col>
                                </Row>
                                <Row >
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label>Data Name</Label>
                                            <Input
                                                type='text'
                                                name='dataName'
                                                id='dataName'
                                                placeholder='Enter data element name'
                                                value={formData.dataName}
                                                onChange={handleInputChange}
                                                required/>
                                        </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label>Data Description</Label>
                                            <Input
                                                type='text'
                                                name='dataDescription'
                                                id='dataDescription'
                                                placeholder='data element description'
                                                value={formData.dataDescription}
                                                onChange={handleInputChange}
                                                required/>
                                        </FormGroup>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label>Age</Label>
                                            <Input
                                                type='number'
                                                name='age'
                                                id='age'
                                                placeholder=' '
                                                value={formData.age}
                                                onChange={handleInputChange}
                                                required/>
                                        </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label>Age Id</Label>
                                            <Input
                                                type='number'
                                                name='ageId'
                                                id='ageId'
                                                placeholder=' '
                                                value={formData.ageId}
                                                onChange={handleInputChange}
                                                required/>
                                        </FormGroup>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label for="exampleSelect">Sex</Label>
                                            <Input id="sex" name="select" type="select">
                                                <option></option>
                                                <option value={"Male"}>Male</option>
                                                <option value={"Female"}>Female</option>
                                            </Input>
                                        </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label>Sex Id</Label>
                                            <Input
                                                type='text'
                                                name='sexId'
                                                id='dataDescription'
                                                placeholder='Enter sex ID'
                                                value={formData.sexId}
                                                onChange={handleInputChange}
                                                required/>
                                        </FormGroup>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label for="exampleSelect">Status</Label>
                                            <Input id="status" name="select" type="select">
                                                <option></option>
                                                <option value={"Active"}>Active</option>
                                                <option value={"Graduated"}>Graduated</option>
                                            </Input>
                                        </FormGroup>
                                    </Col>
                                    <Col md={6}>
                                        <FormGroup>
                                            <Label>Status Id</Label>
                                            <Input
                                                type='text'
                                                name='statusId'
                                                id='statusId'
                                                placeholder='Enter status ID'
                                                value={formData.statusId}
                                                onChange={handleInputChange}
                                                required/>
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


export default connect(null, { createDataElement , updateDataElement})(NewApplicationCodeset);

