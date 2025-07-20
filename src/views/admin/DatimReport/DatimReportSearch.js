import React, {useState} from "react";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";
import { Card, CardContent } from "@material-ui/core";
import AssessmentIcon from '@mui/icons-material/Assessment';
import { makeStyles } from "@material-ui/core/styles";
import Title from "../../../views/Title/CardTitle";
import DatimSearchPage from "./DatimSearchPage";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";
import {ExportData} from "../../../actions/importExportManager"
import {connect} from 'react-redux';
import DownloadIcon from '@mui/icons-material/Download';





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
    return (
        <div>
            <Card>
                <CardContent>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" to={{pathname: "/admin"}} >
                            Admin
                        </Link>
                        <Typography color="textPrimary">DATIM Report Page</Typography>
                    </Breadcrumbs>
                    <Title>
                        <Link color="inherit" to ={{pathname: "generate-report",}}  >
                            <Button variant="contained"
                                    color="primary"
                                    className=" float-right mr-1"
                                    startIcon={<AssessmentIcon/>}>
                                <span style={{textTransform: 'capitalize'}}></span>
                                &nbsp;&nbsp;
                                <span style={{textTransform: 'capitalize'}}>Generate DATIM Report</span>
                            </Button>
                        </Link>
                        <Link color="inherit" to ={{pathname: "datim-flat-file",}}  >
                            <Button variant="contained"
                                    color="primary"
                                    className=" float-right mr-1"
                                    startIcon={<DownloadIcon/>}>
                                <span style={{textTransform: 'capitalize'}}></span>
                                &nbsp;&nbsp;
                                <span style={{textTransform: 'capitalize'}}>Download DATIM Flat File</span>
                            </Button>
                        </Link>
                        <br />
                    </Title>
                    <br />
                    <DatimSearchPage/>
                </CardContent>
            </Card>
        </div>
    );
};
export default connect(null, { ExportData})(GeneralFormSearch);


