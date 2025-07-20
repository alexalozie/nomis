import React from "react";
// import Page from "components/Page";
import { Form } from "react-formio";
import * as actions from "../../actions/formManager";
import { connect } from "react-redux";
import Moment from "moment";
import momentLocalizer from "react-widgets-moment";
import { toast, ToastContainer } from "react-toastify";
import {Card, Alert, CardBody, Spinner, ModalBody} from "reactstrap";
// import { fetchLastEncounter } from '../../_services/form-renderer';
import { url } from "../../api/index";
import axios from "axios";
import { formRendererService } from "../../_services/form-renderer";
import { authHeader } from '../../_helpers/auth-header';
import _ from 'lodash';
import {fetchHouseHoldById} from "../../actions/houseHold";
import {fetchHouseHoldMemberById} from "../../actions/houseHoldMember";
import LinearProgress from "@material-ui/core/LinearProgress";

Moment.locale("en");
momentLocalizer();

const FormRenderer = (props) => {
    const [form, setForm] = React.useState({});
    const [errorMsg, setErrorMsg] = React.useState("");
    const [showErrorMsg, setShowErrorMsg] = React.useState(false);
    const [showLoading, setShowLoading] = React.useState(false);
    const [showLoadingForm, setShowLoadingForm] = React.useState(true);
    const [showLoadingEncounter, setShowLoadingEncounter] = React.useState(false)
    const [formId, setFormId] = React.useState();
    const [encounterId, setEncounterId] = React.useState(props.encounterId);
    const [household, setHousehold] = React.useState({});
    const [householdMember, setHouseholdMember] = React.useState({});
    const [submission, setSubmission] = React.useState(_.merge(props.submission, { data: { household: props.household, baseUrl: url, authHeader: authHeader().Authorization }}));
    const onDismiss = () => setShowErrorMsg(false);
    const [last, setLast] = React.useState();
    const options = {
        noAlerts: true,
    };

    //fetch form by form code
    React.useEffect(() => {
        formRendererService
            .fetchFormByFormCode(props.formCode).then((response) => {
            setShowLoadingForm(false);
            if (!response.data.resourceObject) {
                setErrorMsg("Form resource not found, please contact admin.");
                setShowErrorMsg(true);
                return;
            }
            //fetch last encounter and add to submission if is not an update
            console.log("fetching encounter")
            if(!props.encounterId) {
                //fetchEncounter();
            }

            //if encounterDate is passed, check to see if a record has been persisted for that date and add to the submission object
            if(props.encounterDate){
                //don't fetch if encounter Id was passed. 
                //a fetch by encounterId would be made instead
                if(!props.encounterId){
                fetchEncounter(props.encounterDate);
                }
            }
            setForm(response.data);
        }) .catch((error) => {
            setErrorMsg("Error loading form, something went wrong");
            setShowErrorMsg(true);
            setShowLoadingForm(false);
        });
    }, []);

    React.useEffect(() => {
        // if encounterId is passed fetch and extract form data
        if(props.encounterId) {
            formRendererService
                .fetchEncounterById(props.encounterId)
                .then((response) => {
                    setShowLoadingEncounter(false);
                    const extractedData = extractFormData(response.data.formData);
                    if (!extractedData) {
                        setErrorMsg("Could not load encounter information");
                        setShowErrorMsg(true);
                    }
                    setSubmission(_.merge(submission, {data: extractedData.data}));
                    //setSubmission({data: extractedData.data});
                    setFormId(extractedData.id);
                })
                .catch((error) => {
                    setErrorMsg("Could not load encounter information");
                    setShowErrorMsg(true);
                    setShowLoadingEncounter(false);
                });
        }
    }, []);

    //extract the formData as an obj (if form data length is one) or an array
    const extractFormData = (formData) => {
        if (!formData) {
            return null;
        }
        if (formData.length === 1) {
            return formData[0];
        }
        return formData.map((item) => {
            return item;
        });
    };

    async function fetchEncounter(date){
        //return if household id or member id was not passed. This could be household enrollment form
        if(!props.householdId || !props.householdMemberId){
            return ;
        }
        setShowLoadingEncounter(true);
        let encounterDate = Moment(date).format('YYYY-MM-DD');
        let url_slugs = "";

        if(props.householdId){
            url_slugs = `${url}households/${props.householdId}/${props.formCode}/encounters?page=0&size=1&dateFrom=${encounterDate}&dateTo=${encounterDate}`;
        }
        if(props.householdMemberId){
            url_slugs = `${url}household-members/${props.householdMemberId}/${props.formCode}/encounters?page=0&size=1&dateFrom=${encounterDate}&dateTo=${encounterDate}`;
        }

        await axios.get(url_slugs, {})
            .then(response => {
                //get encounter form data and store it in submission object
                if(response.data && response.data.length > 0){
                    const extractedData = response.data[0].formData[0];
                    setSubmission(_.merge(submission, {data: extractedData.data}));
                    setEncounterId(extractedData.encounterId);
                    setFormId(extractedData.id);
                }
                setShowLoadingEncounter(false);
            }) .catch((error) => {
                setErrorMsg("Error loading encounter, something went wrong");
                setShowErrorMsg(true);
                setShowLoadingEncounter(false);
            });

        ;

    }

    async function fetchLastEncounter(){
        //return if household id or member id was not passed. This could be household enrollment form
        if(!props.householdId || !props.householdMemberId){
            return ;
        }
        setShowLoadingEncounter(true);
        let url_slugs = "";

        if(props.householdId){
            url_slugs = `${url}households/${props.householdId}/${props.formCode}/encounters?page=0&size=1`;
        }
        if(props.householdMemberId){
            url_slugs = `${url}household-members/${props.householdMemberId}/${props.formCode}/encounters?page=0&size=1`;
        }

        await axios.get(url_slugs, {})
            .then(response => {
                //get encounter form data and store it in submission object
                if(response.data && response.data.length > 0){
                    const update = response.data[0].formData[0].data;
                    setLast(update);
                    setSubmission(_.merge(submission, { data: { last: update}}));
                }
                setShowLoadingEncounter(false);
            }) .catch((error) => {
                setErrorMsg("Error loading encounter, something went wrong");
                setShowErrorMsg(true);
                setShowLoadingEncounter(false);
            });

        ;

    }
    //fetch household and household member
    React.useEffect(() => {
        //fetch household by householdId if householdId is in the props object
        if(props.householdId) {
            props.fetchHouseHoldById(props.householdId);
        }
        if(props.householdMemberId) {
            props.fetchHouseHoldMemberById(props.householdMemberId);
        }

    }, []);


    //Add household data to submission
    React.useEffect(() => {
        setSubmission(_.merge(submission, { data: { household: props.household}}));
    }, [props.household]);

    //Add householdMember data to submission
    React.useEffect(() => {
        setSubmission(_.merge(submission, { data: { householdMember: props.householdMember}}));
    }, [props.householdMember]);

    // Submit form to server
    const submitForm = (submission) => {
        const onSuccess = () => {
            setShowLoading(false);
            toast.success("Form saved successfully!", { appearance: "success" });
        };
        const onError = (errstatus) => {
            setErrorMsg(
                "Something went wrong, request failed! Please contact admin."
            );
            setShowErrorMsg(true);
            setShowLoading(false);
        };

        if(formId){
            updateForm(submission, onSuccess, onError);
        }else{
            saveForm(submission, onSuccess, onError);
        }
    };

    const saveForm = (submission, onSuccess, onError) => {

        const encounterDate = submission.data["encounterDate"]
            ? submission.data["encounterDate"]
            : new Date();
        const formatedDate = new Date(encounterDate);

        let data = {
            formData: [{data:submission.data}],
            householdId: props.householdId,
            householdMemberId: props.householdMemberId,
            formCode: props.formCode,
            dateEncounter: formatedDate,
        };

        props.saveEncounter(
            data,
            props.onSuccess ? props.onSuccess : onSuccess,
            props.onError ? props.onError : onError
        );
    }

    const updateForm = (submission, onSuccess, onError) => {
        const data = {
            data: submission.data,
            encounterId: encounterId,
            id: formId
        }

        formRendererService.updateFormData(formId, data)
            .then((response) => {
                props.onSuccess ? props.onSuccess() : onSuccess();
            })
            .catch((error) => {
                props.onError ? props.onError() : onError()
            });
    }
    if(showLoadingForm){
        return (<span className="text-center">
    <Spinner style={{ width: "3rem", height: "3rem" }} type="grow" />{" "}
            Loading form...
  </span>);
    }

    if(showLoadingEncounter){
        return (<span className="text-center">
    <Spinner style={{ width: "3rem", height: "3rem" }} type="grow" />{" "}
            Loading encounter information...
  </span>);
    }

    return (
        <React.Fragment>
            <ToastContainer />
            <Card>
                <CardBody>
                    { props.showHeader &&
                    <>
                        <h3 class="text-capitalize">
                            {/*{"New: "}*/}
                            {props.title || (form && form.name ? form.name : '')}
                        </h3>
                        <hr />
                    </>
                    }
                    {/* <Errors errors={props.errors} /> */}
                    <Alert color="danger" isOpen={showErrorMsg} toggle={onDismiss}>
                        {errorMsg}
                    </Alert>

                    <Form
                        form={form.resourceObject}
                        submission={submission}
                        hideComponents={props.hideComponents}
                        options={options}
                        onSubmit={(submission) => {
                            delete submission.data.householdMember;
                            delete submission.data.household;
                            delete submission.data.authHeader;
                            delete submission.data.submit;
                            delete submission.data.baseUrl;
                            delete submission.data.last;
                            delete submission.data.checklist;

                            if (props.onSubmit) {
                                return props.onSubmit(submission);
                            }
                            return submitForm(submission);
                        }}
                    />
                    {showLoading &&
                    <LinearProgress color="primary" thickness={5} className={"mb-2"}/>
                    }
                </CardBody>
            </Card>
        </React.Fragment>
    );
};

const mapStateToProps = (state = { form: {} }) => {
    return {
        household: state.houseHold.household,
        householdMember: state.houseHoldMember.member,
        form: state.programManager.form,
        formEncounter: state.programManager.formEncounter,
        errors: state.programManager.errors,
    };
};

const mapActionToProps = {
    fetchForm: actions.fetchById,
    saveEncounter: actions.saveEncounter,
    fetchHouseHoldById: fetchHouseHoldById,
    fetchHouseHoldMemberById: fetchHouseHoldMemberById
};

export default connect(mapStateToProps, mapActionToProps)(FormRenderer);
