import React from 'react';
import { FormGroup, Label } from "reactstrap";
import Select from "react-select";
import axios from "axios";
import { url } from "../../../api";
import { Modal, ModalBody, ModalHeader, Spinner, ModalFooter, Button } from 'reactstrap';
import { CButton } from "@coreui/react";
import SaveIcon from "@material-ui/icons/Delete";
import CancelIcon from "@material-ui/icons/Cancel";
import { toast, ToastContainer } from "react-toastify";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

const ReassignCaregiver = (props) => {

    const householdId = props.householdId;
    const memberId = props.memberId ? props.memberId : null;
    const [caregivers, setCaregivers] = React.useState([]);
    const [cLoading, setCLoading] = React.useState(false);
    const [saving, setSaving] = React.useState(false);
    const [selectedCaregiver, setSelectedCaregiver] = React.useState();
    const [showModal, setShowModal] = React.useState(false);
    const toggleModal = () => setShowModal(!showModal);
    const classes = useStyles();

    React.useEffect(() => {
        fetchCaregivers(props.householdId);
    }, [props.householdId, showModal]);

    const fetchCaregivers = (householdId) => {
        setCLoading(true);
        const onSuccess = () => {
            setCLoading(false);
        }

        const onError = () => {
            setCLoading(false);
            if(showModal == true){
                toast.error('Error: Could not fetch available caregivers!');
            }
            
        }
        axios
            .get(`${url}households/${householdId}/getHouseholdCaregivers`)
            .then(response => {
                setCaregivers(response.data)
                if (onSuccess) {
                    onSuccess();
                }
            })
            .catch(error => {
                if (onError) {
                    onError();
                }
            }

            );
    }

    const save = () => {
        
        if (!selectedCaregiver) {
            toast.error('Select a caregiver');
            return;
        }
        setSaving(true);
        const onSuccess = () => {
            setSaving(false);
            toast.success('Caregiver reassigned successfully!');
            if(props.reload){
                props.reload();
            }
            toggleModal();
        }

        const onError = () => {
            setSaving(false);
            toast.error('Error: Could not reassign caregivers!');
        }
        let urlStub = memberId ? `${url}household-members/${memberId}/${selectedCaregiver}/reAssignCaregiver`:`${url}households/${householdId}/${selectedCaregiver}/reAssignCaregiver`;
        axios
            .get(urlStub)
            .then(response => {
                if (onSuccess) {
                    onSuccess();
                }
            })
            .catch(error => {
                if (onError) {
                    onError();
                }
            }

            );
    }

    const onSelectCaregiver = (x) => {
        setSelectedCaregiver(x.value);
    }

    const openModal = () => {
        toggleModal();
    }

    return (
        <>
            <CButton className={props.className ? props.className: "pt-2 mt-2 "} variant={props.variant ? props.variant: "outline"} size="xs" color={props.color ? props.color: "warning"} onClick={() => openModal()}>Reassign {memberId ? '' : 'Primary'} Caregiver </CButton>
<ToastContainer />
            <Modal isOpen={showModal} toggle={toggleModal} backdrop="static">
                <ModalHeader toggle={toggleModal}> Reassign Caregiver </ModalHeader>
                <ModalBody>
                    <FormGroup>
                        <Label for="household">Select New Caregiver</Label>
                        <Select
                            required
                            isClearable
                            isLoading={cLoading}
                            isMulti={false}
                            onChange={onSelectCaregiver}
                            options={caregivers.map((x) => ({
                                label: x.details ? (x.details.firstName + " " + x.details.lastName) : '',
                                value: x.id,
                            }))}
                        />
                    </FormGroup>

                </ModalBody>
                <ModalFooter>
                    <Button
                        type='button'
                        variant='contained'
                        color='primary'
                        className={classes.button}
                        startIcon={<SaveIcon />}
                        disabled={saving}
                        onClick={save}
                    >
                        Save  {saving ? <Spinner /> : ""}
                    </Button>
                    <Button
                        variant='contained'
                        color='default'
                        onClick={toggleModal}
                        startIcon={<CancelIcon />}
                    >
                        Cancel
                    </Button>
                </ModalFooter>
            </Modal>
        </>
    )
}

export default ReassignCaregiver;