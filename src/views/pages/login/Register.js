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
import { requestPassword , resetPassword } from "../../../actions/resetPassword";
import { Spinner } from 'reactstrap';
import {toast} from "react-toastify";
import {useHistory} from 'react-router-dom';




const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const PasswordReset = (props) => {
    let history = useHistory();
    const [loading, setLoading] = useState(false)
    const [showNewResetModal, setshowNewResetModal] = useState(false)
    const [showNewModal, setshowNewModal] = useState(false)
    const defaultValues = {emailAddress:"",};
    const [formData, setFormData] = useState( defaultValues)
    const toggleModal = () => setShowModal(!showModal)
    const [showModal, setShowModal] = React.useState(false);

    const classes = useStyles()

    useEffect(() => {
        setFormData(props.formData ? props.formData : defaultValues);
        setshowNewModal(false);
    },  [props.formData,  props.showNewModal]);

    const handleInputChange = e => {
        setFormData ({ ...formData, [e.target.name]: e.target.value});
    }

    // const openResetNewModal = (row) => {
    //     showNewResetModal(!showNewResetModal);
    //     toggleModal();
    //     // console.log(assignFacilityModal);
    //
    // }
    const requestPassword = e => {
        e.preventDefault()
        setLoading(true);

        const onSuccess = () => {
            setLoading(false);
            props.toggleModal()
        }
        const onError = () => {
            setLoading(false);
            toast.error("An error occured, could not varify email address");
             props.toggleModal()
        }
        props.requestPassword(formData, onSuccess,onError)

    }
    return (

        <div >
            <ToastContainer />
            <Modal isOpen={props.showModal} toggle={props.toggleModal} size="lg">
                <Form onSubmit={requestPassword}>
                    <ModalHeader toggle={props.toggleModal}>Enter Valid Email Address</ModalHeader>
                    <ModalBody>
                        <Card >
                            <CardBody>
                                <Row >
                                    <Col md={12}>
                                        <FormGroup>
                                            <Label>Enter Email Address</Label>
                                            <Input
                                                type='text'
                                                name='emailAddress'
                                                id='emailAddress'
                                                placeholder='Enter valid email address'
                                                value={formData.emailAddress}
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
                                    <span style={{textTransform: 'capitalize'}}>Request Password Reset</span>  {loading ? <Spinner /> : ""}
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


export default connect(null, { requestPassword , resetPassword})(PasswordReset);


