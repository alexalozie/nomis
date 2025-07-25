import React, { useState } from "react";
// import useForm from "../Functions/UseForm";
import FormRenderer from "./FormRenderer";
import {
    Col,
    FormGroup,
    Input,
    Label,
    Row,
    Modal,
    ModalHeader,
    ModalBody,
    ModalFooter,
    Alert,
} from "reactstrap";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { connect } from "react-redux";
import ViewForm from './FormRendererView';

const FormRendererModal = (props) => {
    const [errorMsg, setErrorMsg] = React.useState("");
    const [showErrorMsg, setShowErrorMsg] = useState(false);
    const [loading, setLoading] = React.useState(false);
    const onDismiss = () => setShowErrorMsg(false);

    const toggle = () => {
        return props.setShowModal(!props.showModal);
    };

    return (
        <Modal
            isOpen={props.showModal}
            toggle={toggle}
            zIndex={"9999"}
            size={"xl"}
            className={
                props.options && props.options.modalSize ? props.options.modalSize : ""
            }
        >
            <ToastContainer />
            <ModalHeader toggle={toggle}>{props.title || props.currentForm.formName || ""}</ModalHeader>
            <ModalBody>
                <Alert color="danger" isOpen={showErrorMsg} toggle={onDismiss}>
                    {errorMsg}
                </Alert>

                
                { props.currentForm && (!props.currentForm.type || props.currentForm.type === 'NEW') &&
                <FormRenderer
                householdId={props.householdId}
                householdMemberId={props.householdMemberId}
                formCode={props.currentForm.code}
                onSuccess={props.onSuccess}
                onSubmit={props.currentForm.onSubmit}
            />
                }

                { props.currentForm && props.currentForm.type === 'VIEW' &&
                <ViewForm
                    formCode={props.currentForm.formCode}
                    encounterId={props.currentForm.encounterId}
                    householdId={props.currentForm.householdId}
                    householdMemberId={props.currentForm.householdMemberId}
                    onSuccess={props.onSuccess}
                    onSubmit={props.currentForm.onSubmit}
                />
                }

                { props.currentForm && props.currentForm.type === 'EDIT' &&
                    <>
                        <FormRenderer
                            householdId={props.currentForm.householdId}
                            householdMemberId={props.currentForm.householdMemberId}
                            encounterId={props.currentForm.encounterId}
                            formCode={props.currentForm.formCode}
                            onSuccess={props.onSuccess}
                           // onSubmit={props.currentForm.onSubmit}
                        />
                {/*// <UpdateForm*/}
                {/*//     patientId={props.patientId}*/}
                {/*//     formCode={props.currentForm.formCode}*/}
                {/*//     programCode={props.currentForm.programCode}*/}
                {/*//     visitId={props.visitId || props.patient.visitId}*/}
                {/*//     onSuccess={props.onSuccess}*/}
                {/*//     onSubmit={props.currentForm.onSubmit}*/}
                {/*//     typePatient={props.currentForm.typePatient}*/}
                {/*//     encounterId={props.currentForm.encounterId}/>*/}
                </>
                         }
            </ModalBody>
        </Modal>
    );
};

const mapStateToProps = (state) => {
    return {
     //   patient: state.patients,
    };
};

const mapActionToProps = {};

export default connect(mapStateToProps, mapActionToProps)(FormRendererModal);
