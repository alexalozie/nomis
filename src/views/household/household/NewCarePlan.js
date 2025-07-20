
import React, {useState} from 'react';
import FormRenderer from "../../formBuilder/FormRenderer";
import {toast, ToastContainer} from "react-toastify";
import axios from "axios";
import * as CODES from "../../../api/codes";
import {CModal, CModalBody, CModalHeader} from "@coreui/react";
import {url} from "../../../api";

const NewCarePlan = (props) => {
  const {
    className
  } = props;
    const [checklist, setChecklist] = useState(null);
    const [loading, setLoading] = useState(false);

    React.useEffect( () => {
        if(!props.lastAssessment && props.modal == true) {
           fetchLastAssessment();
        }
    },[props.householdId, props.modal]);
  //  fetchLastAssessment();
  const onSuccess = () => {
    props.reloadSearch();
    toast.success("Form saved successfully!", { appearance: "success" });
    props.toggle();
  }

    function fetchLastAssessment() {
      if(props.householdId) {
          setLoading(true);

          axios
              .get(`${url}households/${props.householdId}/${CODES.HOUSEHOLD_ASSESSMENT}/formData`)
              .then(response => {
                  if (response.data && response.data && response.data.length == 0) {
                      toast.info('There have been no assessment for this household! Go to the assessment tab to fill an assessment before you can fill a care plan.');
                      setChecklist(null);
                      return;
                  }
                  setChecklist(response.data[0].data);
                  setLoading(false);
              })
              .catch(error => {
                      setLoading(false);
                      toast.error('Error: Could not fetch the last household assessment!');
                  }
              );
      }
    }

  return (
      <div>
        <ToastContainer />

          <CModal show={props.modal} onClose={props.toggle} className={className} backdrop={true} size={"xl"} closeOnBackdrop={false}>
              <CModalHeader closeButton>New Care Plan</CModalHeader>
              <CModalBody>
                  {!loading && props.lastAssessment &&
                  <FormRenderer
                      formCode={CODES.CARE_PLAN}
                      householdId={props.householdId}
                      onSuccess={onSuccess}
                      submission={{data: {checklist: props.lastAssessment}}}
                  />
                  }
                  {!loading && checklist &&
                  <FormRenderer
                      formCode={CODES.CARE_PLAN}
                      householdId={props.householdId}
                      onSuccess={onSuccess}
                      submission={{data: {checklist: checklist}}}/>
                  }
              </CModalBody>

          </CModal>
      </div>
  );

}

export default NewCarePlan;