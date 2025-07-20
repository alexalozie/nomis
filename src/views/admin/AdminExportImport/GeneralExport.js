import React, {useEffect, useState} from "react";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";
import { Card, CardContent } from "@material-ui/core";
import BackupIcon from '@mui/icons-material/Backup';
import UploadFileIcon from '@mui/icons-material/UploadFile';
import { makeStyles } from "@material-ui/core/styles";
import Title from "../../../views/Title/CardTitle";
import GeneralImportPage from "../AdminExportImport/GeneralImportPage";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";
import {ExportData} from "../../../actions/importExportManager"
import axios from 'axios';
import {url} from '../../../api';
import {toast} from 'react-toastify';
import {connect} from 'react-redux';






const useStyles = makeStyles(theme => ({
    card: {
        margin: theme.spacing(20),
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    }
}));

const GeneralFormSearch = e => {
    const [loading, setLoading] = useState(false);
    // useEffect(() => {
    //     GeneralImportPage()
    // }, []); //componentDidMount

    function ExportData () {
        console.log('hi1')
        axios
            .get(`${url}data/export`)
            .then(response => {
                console.log('hi2')
                if (response.data) {
                    setLoading(false);
                    toast.success('Files Exported successfully');
                    return;
                }
            })
            .catch(error => {
                    toast.error('Error: Could not export Table, contact the Admin!');
                    setLoading(false);
                }
            )
    }

    return (
        <div>
            <Card>
                <CardContent>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" to={{pathname: "/admin"}} >
                            Admin
                        </Link>
                        <Typography color="textPrimary">General Export/Import Manager</Typography>
                    </Breadcrumbs>
                    <Title>
                        <Link color="inherit" to ={{pathname: "import-pages",}}  >
                            <Button variant="contained"
                                    color="primary"
                                    className=" float-right mr-1"
                                    startIcon={<UploadFileIcon/>}>
                                <span style={{textTransform: 'capitalize'}}></span>
                                &nbsp;&nbsp;
                                <span style={{textTransform: 'capitalize'}}>Upload File</span>
                            </Button>
                        </Link>
                        <Button onClick={ExportData} variant="contained"
                                color="primary"
                                className=" float-right mr-1"
                                startIcon={<BackupIcon />}>
                            <span style={{textTransform: 'capitalize'}}></span>
                            &nbsp;&nbsp;
                            <span style={{textTransform: 'capitalize'}}>Export Files</span>
                        </Button>
                        <br />
                    </Title>
                    <br />
                    <GeneralImportPage/>
                </CardContent>
            </Card>
        </div>
    );
};
export default connect(null, { ExportData})(GeneralFormSearch);


