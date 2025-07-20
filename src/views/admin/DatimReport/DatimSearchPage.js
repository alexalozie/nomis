import React, { useState, useEffect } from "react";
import MaterialTable from "material-table";
import { connect } from "react-redux";
import {fetchAvailableReport} from "../../../actions/datimPage"
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

function GeneralImportSearch(props) {
    const [loading, setLoading] = useState(false);
    console.log(props.availableList)
    useEffect(() => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        };
        const onError = () => {
            setLoading(false);
        };
        props.fetchAvailableReport(onSuccess, onError);
    }, []);

    function onDownload (row) {
        const  file = row.name;
        const  fileFormat = 'xlsx';
        axios({
            url: `${url}data-reports/download/${file}/${fileFormat}`,
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




    return (
        <React.Fragment>
            <div>
                <MaterialTable
                    icons={tableIcons}
                    title="Find File by Name"
                    columns={[
                        { title: "File Name", field: "name" },
                    ]}
                    isLoading={loading}
                    data={props.availableList.map((row) => ({
                        name: row
                    }))}
                    actions= {[
                        {
                            icon: DownloadForOfflineIcon,
                            iconProps: {color: 'primary'},
                            tooltip: 'Download as FILE',
                            onClick: (event, row) => onDownload(row)
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
            </div>

            <ToastContainer />
        </React.Fragment>
    );
}
const mapStateToProps =  (state = { availableList:[]}) => {
    return {
        availableList: state.datimReducer.availableList !==null ? state.datimReducer.availableList : [],
    }}

const mapActionToProps = {
    fetchAvailableReport: fetchAvailableReport,


};

export default connect(mapStateToProps, mapActionToProps)(GeneralImportSearch);
