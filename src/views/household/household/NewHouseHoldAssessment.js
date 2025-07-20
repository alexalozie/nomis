
import React from 'react';
import { Modal, ModalHeader, ModalBody, } from 'reactstrap';
import * as CODES from './../../../api/codes'
import FormRenderer from './../../formBuilder/FormRenderer'
import {toast, ToastContainer} from "react-toastify";


const NewHouseHoldAssessment = (props) => {
  const {
    className
  } = props;


  const currentForm = {
    code: CODES.HOUSEHOLD_ASSESSMENT,
    //programCode: CODES.GENERAL_SERVICE,
    formName: "Hosehold Assesment",
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
        <ModalHeader toggle={props.toggle} >New HouseHold Assessment</ModalHeader>
        <ModalBody>
          <FormRenderer
          formCode={currentForm.code}
          householdId={props.householdId}
          onSuccess={onSuccess}
          />
        </ModalBody>
        {/* <ModalFooter>
          <Button color="primary" onClick={props.toggle}>Save</Button>{' '}
          <Button color="secondary" onClick={props.toggle}>Cancel</Button>
        </ModalFooter> */}
      </Modal>
      
    </div>
  );
  
}

export default NewHouseHoldAssessment;