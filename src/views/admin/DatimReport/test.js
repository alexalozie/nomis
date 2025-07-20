import React, {useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import { authHeader } from '../../../_helpers/auth-header'
import {Card, CardBody, Col, FormGroup, Input, Label, Row, Spinner} from 'reactstrap';
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import { Link } from 'react-router-dom';
import {toast, ToastContainer} from "react-toastify";
import axios from 'axios';
import {url} from '../../../api';
import {CForm} from '@coreui/react';
import MatButton from '@material-ui/core/Button';
import SaveIcon from '@material-ui/icons/Save';
import {downloadDatim} from '../../../actions/datimPage';




const useStyles = makeStyles(theme => ({
    root2: {
        width: '100%',
        height: 100,
        overflow: 'auto',
    }
}));

const GenerateReport = props => {
    const [submission, setSubmission] = React.useState({ data: { authHeader: authHeader().Authorization, baseUrl: url }});
    const defaultValues = { quarter: "", year: ""};
    const [formData, setFormData] = useState( defaultValues);
    const options = {noAlerts: true,};
    const [loading, setLoading] = useState(false)
    const [formCode, setformCode] = React.useState();
    const [downloading, setDownLoading] = React.useState(false);
    const [form2, setform2] = React.useState();
    const [reportId, setreportId] = React.useState();
    const classes = useStyles();
    const [year, setYear] = useState(0);
    const [quarter, setQuarter] = useState('');

    let myform;



    function submitForm (e) {
        e.preventDefault()
        axios({
            url: `${url}data-reports/datim/${year}/${quarter}`,
            method: "GET",
            responseType: 'blob',
        }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'datim.csv');
            document.body.appendChild(link);
            link.click();
            toast.success('Databa Downloaded successfully');
            return;
        }).catch(error => {
                toast.error('Error: Could not download flat file!');
            }
        )
    }

    return (
        <div >
            <ToastContainer />
            <Card>
                <ToastContainer/>
                <CardBody>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" to={{pathname: "/datim-report"}} >
                            Generate DATIM Reports
                        </Link>
                    </Breadcrumbs>
                    <br/>
                    <Card>
                        <CForm onSubmit={submitForm} >
                            <Card >
                                <CardBody>
                                    <Row>
                                        <Col md={6}>
                                            <FormGroup>
                                                <Label for="year">
                                                    Year
                                                </Label>
                                                <Input id="year" name="number" placeholder="Enter Reporting year" type="number"
                                                       value={year}
                                                       onChange={(e) => setYear(e.target.value)}
                                                />
                                            </FormGroup>
                                        </Col>
                                        <Col md={6}>
                                            <FormGroup>
                                                <Label for="quarter">Report Quarter</Label>
                                                <Input id="quarter" name="select" type="select"
                                                       onChange={(e) => setQuarter(e.target.value)}>
                                                    <option></option>
                                                    <option value={"q1"}>Q1</option>
                                                    <option value={"q2"}>Q2</option>
                                                    <option value={"q3"}>Q3</option>
                                                    <option value={"q4"}>Q4</option>
                                                </Input>
                                            </FormGroup>
                                        </Col>
                                    </Row>
                                    <MatButton
                                        type='submit'
                                        variant='contained'
                                        color='primary'
                                        className={classes.button}
                                        startIcon={<SaveIcon />}
                                        disabled={loading}>
                                        <span style={{textTransform: 'capitalize'}}>Generate DATIM Flat File</span>  {loading ? <Spinner /> : ""}
                                    </MatButton>
                                </CardBody>
                            </Card>
                        </CForm >
                    </Card>
                </CardBody>
            </Card>
        </div>

    );
}
export default connect(null, {downloadDatim})(GenerateReport);


