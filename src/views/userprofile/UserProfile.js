
import React, { useState } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Input, Label, Form, FormGroup } from 'reactstrap';

const UserProfilePage = (props) => {
  const {
    buttonLabel,
    className
  } = props;
  const [modal, setModal] = useState(false);
  const [backdrop, setBackdrop] = useState(true);


  const toggle = () => setModal(!modal);


  return (
    <div>

      <Modal isOpen={props.modal} toggle={props.toggle} className={className} backdrop={true}>
        <ModalHeader toggle={props.toggle}>User Profile</ModalHeader>
        <ModalBody>
          <p>User Profile Details here </p>
        </ModalBody>
        
      </Modal>
    </div>
  );
}

export default UserProfilePage;