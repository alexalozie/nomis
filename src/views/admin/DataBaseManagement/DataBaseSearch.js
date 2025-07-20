import React, { useState, useEffect } from "react";
import MaterialTable from "material-table";
import { connect } from "react-redux";
import {fetchAllBackUp, dataBaseRestore, downloadDataBase, BackUpDataBase} from "../../../actions/dataBaseManager";
import Moment from "moment";
import momentLocalizer from "react-widgets-moment";
import "react-widgets/dist/css/react-widgets.css";
import { ToastContainer, toast } from "react-toastify";
import {Menu, MenuButton, MenuItem, MenuList} from '@reach/menu-button';
import "@reach/menu-button/styles.css";
import AddBox from '@material-ui/icons/AddBox';
import ArrowUpward from '@material-ui/icons/ArrowUpward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';
import { forwardRef } from 'react';
import axios from 'axios';
import {url} from '../../../api';
import {Card, CardContent} from '@material-ui/core';
import Breadcrumbs from '@material-ui/core/Breadcrumbs';
import {Link} from 'react-router-dom';
import Typography from '@material-ui/core/Typography';
import Title from '../../Title/CardTitle';
import Button from '@material-ui/core/Button';
import UploadFileIcon from '@mui/icons-material/UploadFile';
import BackupIcon from '@mui/icons-material/Backup';
import DownloadForOfflineIcon from '@mui/icons-material/DownloadForOffline';



const tableIcons = {
    Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
    Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
    Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
    Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
    DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
    Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
    Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
    Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
    FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
    LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
    NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
    PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref} />),
    ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
    Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
    SortArrow: forwardRef((props, ref) => <ArrowUpward {...props} ref={ref} />),
    ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
    ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />)
};

//Dtate Picker package
Moment.locale("en");
momentLocalizer();

function DatabaseSearch(props) {
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        DatabaseSearch()
    }, []); //componentDidMount


    const DatabaseSearch = () => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        };
        const onError = () => {
            setLoading(false);
        };
        props.fetchAllBackUp(onSuccess, onError);
    };

    function onRestore () {
        axios
            .get(`${url}backup/restore`)
            .then(response => {
                console.log(response.data)
                if (response.data) {
                    toast.success('Database Restore successfully');
                    return;
                }
            })
            .catch(error => {
                    toast.error('Error: Could not restore up database!');
                }
            )
    }


    function onDownload (row) {
        axios({
            url: `${url}backup/download`,
            method: "GET",
            responseType: 'blob',
        }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', row.name);
            document.body.appendChild(link);
            link.click();
            toast.success('Database Downloaded successfully');
            return;
        }).catch(error => {
                toast.error('Error: Could not restore up database!');
            }
        )
    }


    function DataBaseBackUp () {
        axios
            .get(`${url}backup/backup`)
            .then(response => {
                if (response.data) {
                    setLoading(false);
                    toast.success('Database Backed up successfully');
                    DatabaseSearch()
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
                                    DatabaseSearch={props.dataBaseList}
                                    startIcon={<BackupIcon />}>
                                <span style={{textTransform: 'capitalize'}}></span>
                                &nbsp;&nbsp;
                                <span style={{textTransform: 'capitalize'}}>Backup Database</span>
                            </Button>
                            <br />
                        </Title>
                        &nbsp;&nbsp;
                        <MaterialTable
                            icons={tableIcons}
                            title="Find By Database Name"
                            columns={[
                                { title: "Database Name", field: "name" },
                                {title: "Action", field: "actions", filtering: false,},
                            ]}
                            isLoading={loading}
                            data={props.dataBaseList.map((row) => ({
                                name: row
                            }))}
                            actions= {[
                                {
                                    icon: DownloadForOfflineIcon,
                                    iconProps: {color: 'primary'},
                                    tooltip: 'Download Database',
                                    onClick: (event, row) => onDownload(row)
                                },
                                {
                                    icon: DownloadForOfflineIcon,
                                    iconProps: {color: 'primary'},
                                    tooltip: 'Restore Database',
                                    onClick: (event, row) => onRestore(row)
                                },
                            ]}
                            //overriding action menu with props.actions
                            components={props.actions}
                            options={{
                                headerStyle: {
                                    backgroundColor: "#9F9FA5",
                                    color: "#000",
                                },
                                searchFieldStyle: {
                                    width : '200%',
                                    margingLeft: '250px',
                                },
                                filtering: true,
                                exportButton: false,
                                searchFieldAlignment: 'left',
                                actionsColumnIndex: -1
                            }}
                        />
                    </CardContent>
                </Card>
            </div>


    );
}
const mapStateToProps =  (state = { dataBaseList:[]}) => {
    return {
        dataBaseList: state.dataBaseReducer.dataBaseList !==null ? state.dataBaseReducer.dataBaseList : [],
    }}

const mapActionToProps = {
    fetchAllBackUp: fetchAllBackUp,
    dataBaseRestore: dataBaseRestore,
    downloadDataBase: downloadDataBase


};

export default connect(mapStateToProps, mapActionToProps)(DatabaseSearch);
