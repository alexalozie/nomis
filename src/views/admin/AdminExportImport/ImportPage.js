import React, {useState} from "react";
import { connect } from 'react-redux';
import {ToastContainer} from "react-toastify";
import {Card, CardContent} from '@material-ui/core';
import Breadcrumbs from '@material-ui/core/Breadcrumbs';
import {Link} from 'react-router-dom';
import Typography from '@material-ui/core/Typography';
import MatButton from '@material-ui/core/Button';
import { TiArrowBack } from "react-icons/ti";
import {uploadFile} from "../../../actions/importExportManager";
import {Spinner} from 'reactstrap';



const FileUpload = (props) => {
    const [file, setFile] = useState()
    const [loading, setLoading] = useState(false);

    function handleChange(event) {
        setFile(event.target.files[0])
    }


    function handleSubmit(event) {
        event.preventDefault()
        setLoading(true);
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
            props.history.push(`/general-export`)

        }
        const onError = () => {
            setLoading(false);

        }
        props.uploadFile(formData, config, onSuccess, onError);
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
                        <Typography color="textPrimary">General Import</Typography>
                    </Breadcrumbs>
                    <Link to ={{
                        pathname: "/general-export",
                        state: 'import-pages'}}>
                        <MatButton type="submit" variant="contained" color="primary" className=" float-right mr-1">
                            <TiArrowBack /> &nbsp; back
                        </MatButton>
                    </Link>
                    <h4>Choose a File to Import</h4>
                    <hr />
                    <form onSubmit={handleSubmit}>
                        <input type="file" onChange={handleChange}/>
                        <MatButton type="submit" variant="contained" color="primary" className=" float-center mr-1" disabled={loading}> Upload  {loading ? <Spinner /> : ""} </MatButton>
                    </form>
                </CardContent>
            </Card>
        </div>
    )
}
export default connect(null, { uploadFile})(FileUpload);





