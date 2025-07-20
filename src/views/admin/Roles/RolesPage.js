import React from "react";
import { Link } from "react-router-dom";
import Button from "@material-ui/core/Button";
import { Card, CardContent } from "@material-ui/core";
import { FaPlus } from "react-icons/fa";
import {TiArrowBack } from "react-icons/ti";
import { makeStyles } from "@material-ui/core/styles";
import Title from "../components/Title/CardTitle";
import RoleList from "./RolesList";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles((theme) => ({
  card: {
    margin: theme.spacing(20),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
}));

const RolePage = (props) => {
  const classes = useStyles();

  return (
    <div>
      <Card className={classes.cardBottom}>
        <CardContent>
        <Breadcrumbs aria-label="breadcrumb">
                <Link color="inherit" to={{pathname: "/admin"}} >
                    Admin
                </Link>
                <Typography color="textPrimary">Role List </Typography>
            </Breadcrumbs>
            <br/>
          <Title>
          <Link to ={{
            pathname: "/users",
            state: 'users'
          }}>
          <Button
            variant="contained"
            color="primary"
            className=" float-right mr-1"
            startIcon={<TiArrowBack />}>
            <span style={{ textTransform: "capitalize" }}>Back</span>
          </Button>
        </Link>
            <Link to="/add-role">
              <Button
                variant="contained"
                color="primary"
                className=" float-right mr-1"
                startIcon={<FaPlus />}>
                <span style={{ textTransform: "capitalize" }}>Add </span>
                &nbsp;&nbsp;
                <span style={{ textTransform: "lowercase" }}>Role </span>
              </Button>
            </Link>
            <br />
          </Title>
          <br />
          <RoleList />
        </CardContent>
      </Card>
    </div>
  );
};

export default RolePage;
