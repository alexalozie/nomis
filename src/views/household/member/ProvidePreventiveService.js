import React, { useState } from 'react';
import { Modal, ModalHeader, ModalBody } from 'reactstrap';
import { CModal, CModalBody, CModalHeader} from "@coreui/react";
import {PREVENTIVE_SERVICE_FORM} from './../../../api/codes'
import FormRenderer from './../../formBuilder/FormRenderer'
import {toast} from "react-toastify";
import axios from "axios";
import {url} from "../../../api";


const ProvidePreventiveService = (props) => {

  const currentForm = {
    code: PREVENTIVE_SERVICE_FORM ,
    formName: "Provide Preventive Service Form",
    options:{
      hideHeader: true
    },
  };
    const onSuccess = () => {
        toast.success('Service saved!');
        props.reload();
        props.toggle();
    }

    const onError = () => {
        toast.error('An error occurred, service not saved!');
    }

  return (
      <div>
          {props.modal &&
          <CModal show={props.modal} onClose={props.toggle} backdrop={true} size='xl' closeOnBackdrop={false}>
              <CModalHeader  closeButton >Provide Preventive Service</CModalHeader>
              <CModalBody>
                  <FormRenderer
                      householdId={props.householdId}
                      householdMemberId={props.memberId}
                      formCode={currentForm.code}
                      onSuccess={onSuccess}
                      onError={onError}
                      />
              </CModalBody>
          </CModal>

          }
       </div>
  );

}

export default ProvidePreventiveService;