import React, { useState } from 'react';
import { Modal, ModalHeader, ModalBody } from 'reactstrap';
import * as CODES from './../../../api/codes';
import FormRenderer from './../../formBuilder/FormRenderer';

const ProvideService = (props) => {
  const {
    buttonLabel,
    className
  } = props;
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);

  const currentForm = {
    code: CODES.HOUSEHOLD_MEMBER_SERVICE_PROVISION,
    formName: "Hosehold Assesment",
    options:{
        hideHeader: true
    },
  };

  const saveAssessment = (e) => {
    alert('Save Successfully');
    props.togglestatus();


};


  return (
    <div>
      
      <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true} size='lg'>
        <ModalHeader toggle={props.toggle}>Provide Service</ModalHeader>
        <ModalBody>
          <FormRenderer
          formCode={currentForm.code}
          programCode=""
          onSubmit={saveAssessment}
          />
        </ModalBody>
    
      </Modal>
    </div>
  );
  
}

export default ProvideService;