import React, { useState, useEffect } from "react";
import MaterialTable from "material-table";
import { connect } from "react-redux";
import {fetchAllImport} from "../../../actions/importExportManager"
import Moment from "moment";
import momentLocalizer from "react-widgets-moment";
import "react-widgets/dist/css/react-widgets.css";
import { ToastContainer, toast } from "react-toastify";
import DownloadForOfflineIcon from '@mui/icons-material/DownloadForOffline';
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
import {CardBody} from 'reactstrap';
import Breadcrumbs from '@material-ui/core/Breadcrumbs';
import {Link} from 'react-router-dom';
import Typography from '@material-ui/core/Typography';
import Title from '../../Title/CardTitle';
import {ExportData} from "../../../actions/importExportManager"
import Button from '@material-ui/core/Button';
import UploadFileIcon from '@mui/icons-material/UploadFile';
import BackupIcon from '@mui/icons-material/Backup';
import {Card, CardContent} from '@material-ui/core';
import {makeStyles} from '@material-ui/core/styles';



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

const useStyles = makeStyles(theme => ({
    button: {
        margin: theme.spacing(1)
    }
}))

//Dtate Picker package
Moment.locale("en");
momentLocalizer();

function GeneralImportSearch(props) {
    const [loading, setLoading] = useState(false);
    const classes = useStyles();

    useEffect(() => {
        GeneralImportSearch()
    }, []); //componentDidMount

    const GeneralImportSearch = () => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        };
        const onError = () => {
            setLoading(false);
        };
        props.fetchAllImport(onSuccess, onError);
    }; //componentDidMount


    function onDownload (row) {
        const  file = row.name;
        axios({
            url: `${url}data/download/${file}`,
            method: "GET",
            responseType: 'blob',
        }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', row.name);
            document.body.appendChild(link);
            link.click();
            toast.success('File Downloaded successfully');
            return;
        }).catch(error => {
                toast.error('Error: Could not download file!');
            }
        )
    }


    function onDownloadJson (row) {
        const  file = row.name;
        axios({
            url: `${url}data/download/json/${file}`,
            method: "GET",
            responseType: 'blob',
        }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', row.name);
            document.body.appendChild(link);
            link.click();
            toast.success('File Downloaded successfully');
            return;
        }).catch(error => {
                toast.error('Error: Could not download file!');
            }
        )
    }

    function ExportData () {
        axios
            .get(`${url}data/export`)
            .then(response => {
                if (response.data) {
                    setLoading(false);
                    toast.success('Files Exported successfully')
                    GeneralImportSearch()
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
        <React.Fragment>
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
                    <Button onClick={ExportData} variant="contained" color="primary" className=" float-right mr-1" GeneralImportSearch={props.fileList} startIcon={<BackupIcon />}>
                        <span style={{textTransform: 'capitalize'}}></span>
                        &nbsp;&nbsp;
                        <span style={{textTransform: 'capitalize'}}>Export Files</span>
                    </Button>
                    <br />
                </Title>
                <br />
                <MaterialTable
                    icons={tableIcons}
                    title="Find File by Name"
                    columns={[
                        { title: "File Name", field: "name" },
                    ]}
                    isLoading={loading}
                    data={props.fileList.map((row) => ({
                        name: row
                    }))}
                    actions= {[
                        {
                            icon: DownloadForOfflineIcon,
                            iconProps: {color: 'primary'},
                            tooltip: 'Download as FILE',
                            onClick: (event, row) => onDownload(row)
                        },
                        {
                            icon: DownloadForOfflineIcon,
                            iconProps: {color: 'primary'},
                            tooltip: 'Download as JSON',
                            onClick: (event, row) => onDownloadJson(row)
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

            <ToastContainer />
        </React.Fragment>
    );
}
const mapStateToProps =  (state = { fileList:[]}) => {
    return {
        fileList: state.generalImportReducer.fileList !==null ? state.generalImportReducer.fileList : [],
    }}

const mapActionToProps = {
    fetchAllImport: fetchAllImport,


};

export default connect(mapStateToProps, mapActionToProps)(GeneralImportSearch);
