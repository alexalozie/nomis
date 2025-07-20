import React from 'react';
import { Modal, ModalHeader, ModalBody, } from 'reactstrap';
import * as CODES from './../../../api/codes'
import FormRenderer from './../../formBuilder/FormRenderer'
import {toast, ToastContainer} from "react-toastify";


const NewHouseHoldEconomicsAssessment = (props) => {
    const {
        className
    } = props;


    const currentForm = {
        code: CODES.ECONOMICS_STRENGTHENING,
        formName: "Hosehold Economics",
        options:{
            hideHeader: true
        },
    };

    const onSuccess = () => {
        props.reloadSearch();
        toast.success("Form saved successfully!", { appearance: "success" });
        props.toggle();
    }


    return (
        <div>
            <ToastContainer />
            <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true} size='xl'>
                <ModalHeader toggle={props.toggle}>New HouseHold Economics Strengthening Assessment</ModalHeader>
                <ModalBody>
                    <FormRenderer
                        formCode={currentForm.code}
                        householdId={props.householdId}
                        // submission={{data: {...props.household.details, assessmentDate: props.assessmentDate}}}
                        onSuccess={onSuccess}
                    />
                </ModalBody>
            </Modal>

        </div>
    );

}

export default NewHouseHoldEconomicsAssessment;