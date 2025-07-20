import React, {useState} from "react";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";
import { Card, CardContent } from "@material-ui/core";
import BackupIcon from '@mui/icons-material/Backup';
import UploadFileIcon from '@mui/icons-material/UploadFile';
import { makeStyles } from "@material-ui/core/styles";
import Title from "../../../views/Title/CardTitle";
import DataBaseSearch from "../DataBaseManagement/DataBaseSearch";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";
import {dataBaseRestore, BackUpDataBase} from "../../../actions/dataBaseManager"
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



function DataBaseBackUp () {
    console.log('hi1')
    axios
        .get(`${url}backup/backup`)
        .then(response => {
            console.log('hi2')
            if (response.data) {
                setLoading(false);
                toast.success('Database Backed up successfully');
                return;
            }
        })
        .catch(error => {
                toast.error('Error: Could not back up database!');
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
                            <Typography color="textPrimary">Database Manager</Typography>
                        </Breadcrumbs>
                    <Title>
                        <Link color="inherit" to ={{pathname: "import-page",}}  >
                            <Button variant="contained"
                                    color="primary"
                                    className=" float-right mr-1"
                                    startIcon={<UploadFileIcon/>}>
                                <span style={{textTransform: 'capitalize'}}></span>
                                &nbsp;&nbsp;
                                <span style={{textTransform: 'capitalize'}}>Upload Database</span>
                            </Button>
                        </Link>
                            <Button onClick={DataBaseBackUp} variant="contained"
                                    color="primary"
                                    className=" float-right mr-1"
                                    startIcon={<BackupIcon />}>
                                <span style={{textTransform: 'capitalize'}}></span>
                                &nbsp;&nbsp;
                                <span style={{textTransform: 'capitalize'}}>Backup Database</span>
                            </Button>

                        <br />
                    </Title>
                    <br />
                    <DataBaseSearch/>
                </CardContent>
            </Card>
        </div>
    );
};
export default connect(null, { dataBaseRestore, BackUpDataBase})(GeneralFormSearch);


