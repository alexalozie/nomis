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
import Select from "react-select/creatable";
import { createApplicationCodeset , updateApplicationCodeset } from "../../../actions/codeSet";
import { Spinner } from 'reactstrap';
import {toast} from "react-toastify";


const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const NewApplicationCodeset = (props) => {
    const [loading, setLoading] = useState(false)
    const [showNewCodesetGroup, setShowNewCodesetGroup] = useState(false)
    const defaultValues = {display:"", language:"", version:"", codesetGroup:""};
    const [formData, setFormData] = useState( defaultValues)
    //const [errors, setErrors] = useState({});
    const classes = useStyles()

    useEffect(() => {
        //for application codeset edit, load form data

        //props.loadCodeset();
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
            toast.error("An error occured, could not save application codeset");
            //  props.toggleModal()
        }
        if(formData.id){
            props.updateApplicationCodeset(formData.id, formData, onSuccess, onError)
            return
        }
        props.createApplicationCodeset(formData, onSuccess,onError)

    }
    return (

        <div >
            <ToastContainer />
            <Modal isOpen={props.showModal} toggle={props.toggleModal} size="lg">

                <Form onSubmit={createGlobalVariable}>
                    <ModalHeader toggle={props.toggleModal}>New Application Codeset </ModalHeader>
                    <ModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                    <Col md={12}>
                                        {!showNewCodesetGroup ?
                                            <FormGroup>
                                                <Label>Codeset Group <span style={{cursor: "pointer", color: "blue"}}
                                                                           onClick={() => setShowNewCodesetGroup(true)}> ( or Click to create new codeset group)</span></Label>
                                                <Select
                                                    required
                                                    name="cg"
                                                    id="cg"
                                                    isOptionDisabled={option => formData.id ? option.value !== formData.codesetGroup : false}
                                                    isMulti={false}
                                                    onChange={handleCodesetGroupChange}
                                                    options={props.loadApplicationCodeset ? Array.from(new Set(props.loadApplicationCodeset.map(x => x.codesetGroup))).sort().map(codesetGroup => ({
                                                        value: codesetGroup,
                                                        label: codesetGroup
                                                    })) : []}
                                                    value={formData.codesetGroup ? {
                                                        value: formData.codesetGroup,
                                                        label: formData.codesetGroup
                                                    } : ""}
                                                    isLoading={false}
                                                />
                                            </FormGroup> :
                                            <FormGroup>
                                                <Label>Codeset Group <span style={{cursor: "pointer", color: "blue"}}
                                                                           onClick={() => setShowNewCodesetGroup(false)}> ( or Click to pick from existing codeset group)</span></Label>
                                                <Input
                                                    type='text'
                                                    name='codesetGroup'
                                                    id='codesetGroup'
                                                    placeholder='Enter new codeset group'
                                                    value={formData.codesetGroup}
                                                    onChange={handleInputChange}
                                                    required
                                                />
                                            </FormGroup>

                                        }
                                    </Col>

                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>Name</Label>
                                            <Input
                                                type='text'
                                                name='display'
                                                id='display'
                                                placeholder=' '
                                                value={formData.display}
                                                onChange={handleInputChange}
                                                required
                                            />
                                        </FormGroup>
                                    </Col>

                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>Language</Label>
                                            <Input
                                                type='text'
                                                name='language'
                                                id='language'
                                                placeholder=' '
                                                value={formData.language}
                                                onChange={handleInputChange}
                                                required
                                            />
                                        </FormGroup>
                                    </Col>

                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>Version</Label>
                                            <Input
                                                type='text'
                                                name='version'
                                                id='version'
                                                placeholder=' '
                                                value={formData.version}
                                                onChange={handleInputChange}
                                                required
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


export default connect(null, { createApplicationCodeset , updateApplicationCodeset})(NewApplicationCodeset);
