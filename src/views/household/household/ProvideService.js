import React from 'react';
import {Modal, ModalHeader, ModalBody, ModalFooter, Row, Col, Label, FormGroup, Input} from 'reactstrap';
import {CButton} from "@coreui/react";
import * as CODES from './../../../api/codes';
import { connect } from "react-redux";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import { createProvideService } from "./../../../actions/householdCaregiverService";
import axios from "axios";
import {url} from "../../../api";
import { ToastContainer, toast } from "react-toastify";
import DualListBox from "react-dual-listbox";
import "react-dual-listbox/lib/react-dual-listbox.css";
import LinearProgress from "@material-ui/core/LinearProgress";
import moment from "moment";
import {formRendererService} from "../../../_services/form-renderer";
import { DatePicker } from "react-widgets";
import "react-widgets/dist/css/react-widgets.css";
import Moment from "moment";
import momentLocalizer from "react-widgets-moment";
//Dtate Picker package
Moment.locale("en");
momentLocalizer();
const ProvideService = (props) => {
    const formData = {};
    const [fetchingServices, setFetchingServices] = React.useState(false);
    const [saving, setSaving] = React.useState(false);
    const [serviceList, setServiceList] = React.useState([]);
    const [selectedServices, setSelectedService] = React.useState([]);
    const [serviceDate, setServiceDate] = React.useState(new Date());

    React.useEffect(() => {

        if(props.modal) {
            fetchServices();
            setSelectedService(props.serviceList ? props.serviceList : []);
            setServiceDate(props.serviceDate ? new Date(props.serviceDate) : new Date());
            setFetchingServices(false);
            setSaving(false);
        }
    },[props.modal]);


    const fetchServices = () => {
        setFetchingServices(true);
        axios
            .get(`${url}ovc-services/0`)
            .then(response => {
                let services = response.data;
                console.log(response.data)
                let domains = services.map(x => x.domainName);
                let distinctDomain = new Set(domains);
                let serviceArray = [];
                distinctDomain.forEach((domainName) => {
                    const domainServices = services.filter(service => service.domainName === domainName).map(x => ({...x, value :x.id, label:x.name}));
                    serviceArray.push({label: domainName, options: domainServices});
                });

                console.log(serviceArray);
                setServiceList(serviceArray);
                setFetchingServices(false);
            })
            .catch(error => {
                    setFetchingServices(false);
                    toast.error("Could not fetch list of services");
                }

            );
    }

    const onChange = (values) => {
        setSelectedService(values);
    }

    const setDate = (e) => {
        setServiceDate(e ? Moment(e).format('YYYY-MM-DD') : null);
    }

    const save = () => {
        if(selectedServices.length <= 0){
            toast.error("Select a service");
            return;
        }

        if(!serviceDate){
            toast.error("Select a service date");
            return;
        }

        setSaving(true);
        const services = selectedServices.map(x => x.options);
        let merged = [].concat.apply([], services);
        const data = {
            serviceDate: Moment(serviceDate).format('YYYY-MM-DD'),
            serviceOffered: merged
        }
        formData['dateEncounter'] = new Date(serviceDate);
        formData['formCode'] = CODES.HOUSEHOLD_MEMBER_SERVICE_PROVISION;
        formData['formData'] = [{data: data}] ;
        formData['archived'] = 0 ;
        formData['householdMemberId'] = props.memberId;
        formData['householdId'] = props.householdId;

        const onSuccess = () => {
            setSaving(false);
            toast.success('Service(s) saved successfully');
            props.toggle();
            if(props.reloadSearch){
                props.reloadSearch();
            }
        };
        const onError = () => {
            setSaving(false);
            toast.error('An error occurred, service(s) not saved successfully');
        };
        if(props.formDataId){
            const body = {
                data: data,
                encounterId: props.encounterId,
                id: props.formDataId
            }
            updateService(body, onSuccess, onError);
            return;
        }
        props.createProvideService(formData, onSuccess, onError);
    }

    const updateService = (data, onSuccess, onError) => {
        formRendererService.updateFormData(props.formDataId, data)
            .then((response) => {
                onSuccess();
            })
            .catch((error) => {
                onError()
            });
    }
    const lang =  {
        availableFilterHeader: 'Filter available',
        availableHeader: 'Available Services',
        moveAllLeft: 'Move all left',
        moveAllRight: 'Move all right',
        moveLeft: 'Move left',
        moveRight: 'Move right',
        moveBottom: 'Move to bottom',
        moveDown: 'Move down',
        moveUp: 'Move up',
        moveTop: 'Move to top',
        noAvailableOptions: 'No available options',
        noSelectedOptions: 'No selected options',
        selectedFilterHeader: 'Filter selected',
        selectedHeader: 'Selected Services',
    };


    return (
        <div>
            <ToastContainer />
            <Modal isOpen={props.modal} toggle={props.toggle} backdrop={"static"} size='xl'>
                <ModalHeader toggle={props.toggle}> Service Form</ModalHeader>
                <ModalBody>

                    {(saving || fetchingServices) &&
                    <LinearProgress color="primary" thickness={5} className={"mb-2"}/>
                    }

                    <Row>
                        <Col md={6}>
                            <FormGroup>
                                <Label >Service Date</Label>
                                <DatePicker
                                    name="serviceDate"
                                    id="serviceDate"
                                    defaultValue={serviceDate}
                                    max={new Date()}
                                    required
                                    disabled={props.type && props.type === "VIEW" ? true : false}
                                    onChange={setDate}
                                />
                                {/*<Input type={"date"} onChange={setDate} value={serviceDate} max={new Date()}/>*/}
                            </FormGroup>
                        </Col>
                        <Col md={12}>
                            <DualListBox options={serviceList} onChange={onChange}
                                         selected={selectedServices} canFilter
                                         lang={lang}
                                         disabled={props.type && props.type === "VIEW" ? true : false}
                                         simpleValue={false} showHeaderLabels={true}/>

                        </Col>
                    </Row>

                    {/*<FormRenderer*/}
                    {/*formCode={currentForm.code}*/}
                    {/*programCode=""*/}
                    {/*onSubmit={saveAssessment}*/}
                    {/*/>*/}
                </ModalBody>
                <ModalFooter>
                    {props.type && props.type === "VIEW" ? "" :
                        <CButton color="primary" onClick={save}>Provide Services</CButton>
                    }
                    <CButton color="danger" onClick={props.toggle}>Cancel</CButton>
                </ModalFooter>
            </Modal>
        </div>
    );

}

const mapStateToProps = state => {
    return {

    };
};
const mapActionToProps = {
    createProvideService: createProvideService
};


export default connect(mapStateToProps, mapActionToProps )(
    ProvideService
);
