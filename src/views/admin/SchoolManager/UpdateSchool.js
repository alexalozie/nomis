import React, {useState} from "react";
import { connect } from 'react-redux';
import {ToastContainer} from "react-toastify";
import {Card, CardContent} from '@material-ui/core';
import Breadcrumbs from '@material-ui/core/Breadcrumbs';
import {Link} from 'react-router-dom';
import Typography from '@material-ui/core/Typography';
import MatButton from '@material-ui/core/Button';
import { TiArrowBack } from "react-icons/ti";
import {uploadSchool} from "../../../actions/school";
import {CAlert, CCol, CRow} from '@coreui/react';
import CIcon from '@coreui/icons-react';





const FileUpload = (props) => {
    const [file, setFile] = useState()
    const [loading, setLoading] = useState(false);

    function handleChange(event) {
        setFile(event.target.files[0])
    }


    function handleSubmit(event) {
        event.preventDefault()
        const formData = new FormData();
        formData.append('file', file);
        formData.append('fileName', file.name);
        const config = {
            headers: {
                'content-type': 'multipart/form-data',
            },
        };
        const onSuccess = () => {
            setLoading(false);

        }
        const onError = () => {
            setLoading(false);

        }
        props.uploadSchool(formData, config, onSuccess, onError);
    }

    return(
        <div>
            <ToastContainer autoClose={3000} hideProgressBar />
            <Card >
                <CardContent>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" to={{pathname: "/admin"}} >
                            Admin
                        </Link>
                        <Typography color="textPrimary">School Import</Typography>
                    </Breadcrumbs>
                    <Link to ={{
                        pathname: "/schoolHomePage",
                        state: 'import-school'}}>
                        <MatButton type="submit" variant="contained" color="primary" className=" float-right mr-1">
                            <TiArrowBack /> &nbsp; back
                        </MatButton>
                    </Link>
                    <CCol xs="6">
                        <CAlert color={"warning"}>
                            <b><CIcon width={15} name="cil-warning" /> </b>Choose an excel file to import (use the approved format)
                        </CAlert>
                    </CCol>
                    <hr />
                    <form onSubmit={handleSubmit}>
                        <input type="file" onChange={handleChange}/>
                        <MatButton type="submit" variant="contained" color="primary" className=" float-center mr-1"> Import School </MatButton>
                    </form>
                </CardContent>
            </Card>
        </div>
    )
}
export default connect(null, { uploadSchool})(FileUpload);





