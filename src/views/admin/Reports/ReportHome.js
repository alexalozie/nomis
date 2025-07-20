import React from "react";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";

import { Card, CardContent } from "@material-ui/core";
import AddIcon from '@material-ui/icons/Add';
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Title from "../components/Title/CardTitle";
import ReportSearch from "./ReportSearch";

const useStyles = makeStyles(theme => ({
    card: {
        margin: theme.spacing(20),
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    }
}));

const GeneralReportSearch = props => {
    const classes = useStyles();

    return (
        <div>
            <Card className={classes.cardBottom}>
                <CardContent>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" to={{pathname: "/admin"}} >
                        Admin
                    </Link>
                    <Typography color="textPrimary">Reports</Typography>
                </Breadcrumbs>
                    <Title>
                        <Link to="/build-report">
                            <Button
                                variant="contained"
                                color="primary"
                                className=" float-right mr-1"
                                startIcon={<AddIcon />}>
                                <span style={{textTransform: 'capitalize'}}>New</span>
                                &nbsp;&nbsp;
                                <span style={{textTransform: 'capitalize'}}>Report</span>
                            </Button>
                        </Link>
                        <br/>
                    </Title>
                    <br />
                    <ReportSearch/>
                </CardContent>
            </Card>
        </div>
    );
};

export default GeneralReportSearch;
