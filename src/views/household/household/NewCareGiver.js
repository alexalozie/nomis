import React, { useState } from 'react';
import { Modal, ModalHeader, ModalBody } from 'reactstrap';
import { CModal, CModalBody, CModalHeader} from "@coreui/react";
import {CARE_GIVER_ENROLMENT_FORM} from './../../../api/codes'
import FormRenderer from './../../formBuilder/FormRenderer'
import {toast} from "react-toastify";
import axios from "axios";
import {url} from "../../../api";


const NewOvc = (props) => {

  const currentForm = {
    code: CARE_GIVER_ENROLMENT_FORM ,
    formName: "Care Giver Enrolment Form",
    options:{
      hideHeader: true
    },
  };

  const createMember = (body) => {
    const onSuccess = () => {
      toast.success('Household caregiver saved!');
      props.reload();
      props.toggle();

    }

    const onError = () => {
      toast.error('An error occurred, household caregiver not saved!');
    }
    axios
        .post(`${url}household-members`, body)
        .then(response => {
          if(onSuccess){
            onSuccess();
          }
        })
        .catch(error => {
              if(onError){
                onError();
              }
            }

        );
  }

    const updateMember = (body) => {
        const onSuccess = () => {
            toast.success('Household caregiver updated!');
            props.reload();
            props.toggle();

        }

        const onError = () => {
            toast.error('An error occurred, household caregiver not saved!');
        }
        axios
            .put(`${url}household-members/${props.householdMember.id}`, body)
            .then(response => {
                if(onSuccess){
                    onSuccess();
                }
            })
            .catch(error => {
                    if(onError){
                        onError();
                    }
                }

            );
    }

    const save = (e) => {
    //alert('Save Successfully');

    const data = e.data;
      delete data.totalMembers;
    const member = {details: data,
        uniqueId: data.uniqueId,
        householdMemberType: 1, householdId: props.householdId};

    if(props.householdMember && props.householdMember.id){
        updateMember(member)
    }else{
        createMember(member)
    }


  };


  return (
      <div>
          {props.modal &&
          <CModal show={props.modal} onClose={props.toggle} backdrop={true} size='xl' closeOnBackdrop={false}>
              <CModalHeader  closeButton >{props.householdMember && props.householdMember.id ? "Update" : "New" } Care Giver</CModalHeader>
              <CModalBody>
                  {props.householdMember && props.householdMember.id ?
                      <FormRenderer
                          householdId={props.householdId}
                          formCode={currentForm.code}
                          submission={{data: {...props.householdMember.details, totalMembers: props.totalMembers}}}
                          onSubmit={save}
                      />
                      :
                      <FormRenderer
                          householdId={props.householdId}
                          formCode={currentForm.code}
                          submission={{data: {totalMembers: props.totalMembers}}}
                          onSubmit={save}/>


                  }
              </CModalBody>
          </CModal>

          }
       </div>
  );

}

export default NewOvc;