import React, {useState} from "react";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";
import { Card, CardContent } from "@material-ui/core";
import UploadFileIcon from '@mui/icons-material/UploadFile';
import { makeStyles } from "@material-ui/core/styles";
import Title from "../../../views/Title/CardTitle";
import Schools from "../SchoolManager/Schools";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";
import {dataBaseRestore, BackUpDataBase} from "../../../actions/dataBaseManager"
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
    return (
        <div>
            <Card>
                <CardContent>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" to={{pathname: "/admin"}} >
                            Admin
                        </Link>
                        <Typography color="textPrimary">School Manager</Typography>
                    </Breadcrumbs>
                    <Title>
                        <Link color="inherit" to ={{pathname: "import-school",}}  >
                            <Button variant="contained"
                                    color="primary"
                                    className=" float-right mr-1"
                                    startIcon={<UploadFileIcon/>}>
                                <span style={{textTransform: 'capitalize'}}></span>
                                &nbsp;&nbsp;
                                <span style={{textTransform: 'capitalize'}}>Import School</span>
                            </Button>
                        </Link>
                        <br />
                    </Title>
                    <br />
                    <Schools/>
                </CardContent>
            </Card>
        </div>
    );
};
export default connect(null, { dataBaseRestore, BackUpDataBase})(GeneralFormSearch);


