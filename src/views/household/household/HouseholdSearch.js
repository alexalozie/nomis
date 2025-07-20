import React, {useState, useEffect} from 'react'
import {
    CCard,
    CCardBody,
    CCardHeader,
    CCol,
    CButton,
    CRow
} from '@coreui/react'
import {connect, useDispatch} from "react-redux";
import MaterialTable from 'material-table';
import {Menu,MenuList,MenuButton,MenuItem,} from "@reach/menu-button";
import "@reach/menu-button/styles.css";
import { Link } from 'react-router-dom';
import { ToastContainer, toast } from "react-toastify";
import NewHouseHold from './NewHouseHold';
import { fetchAllHouseHold, deleteHousehold } from "./../../../actions/houseHold";

import { forwardRef } from 'react';
import axios from "axios";
import {url as baseUrl} from "./../../../api";
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
import makeStyles from '@material-ui/core/styles/makeStyles';

const {useRef} = require('react');


const useStyles = makeStyles({
    table: {
        width: "90%"
    }
});

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

const HouseHoldList = (props) => {
    const [modal, setModal] = useState(false);
    const nodeRef = useRef(null);
    const classes = useStyles();
    const dispatch = useDispatch();
    React.useEffect(() => {
        //show side-menu when this page loads
        dispatch({type: 'MENU_MINIMIZE', payload: false });
    },[]);
    const toggle = () => setModal(!modal);
    const toggleNew = () => {
        setCurrentHousehold(null);
        setModal(!modal);
    };
    const toggleUpdate = (hh) => {
        setCurrentHousehold(hh);
        setModal(!modal);
    };
    const [loading, setLoading] = useState(true);
    const [currentHousehold, setCurrentHousehold] = useState(null);

    useEffect(() => {
        performSearch();
    }, []); //componentDidMount

    const performSearch = () => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)
        }
        props.fetchAllHouseHold(onSuccess, onError);
    }

    const onDelete = row => {
        performSearch()
        const onSuccess = () =>{
            toast.success("Household deleted successfully");
        }
        if (window.confirm(`Are sure you want to delete this record? ${row.uniqueId}`))
            props.deleteHousehold(row.id, onSuccess, () => toast.error("An error occurred, household not deleted successfully"));
    }

    return (
        <>
            <ToastContainer />
            <CRow>
                <CCol>
                    <CCard>
                        <CCardHeader>
                            Household Vulnerability Assessment
                            <CButton color="primary" className="float-right" onClick={toggleNew}>New Household Vulnerability Assessment </CButton>
                        </CCardHeader>
                        <CCardBody>
                            <MaterialTable
                                icons={tableIcons}
                                title="Household List"
                                nodeRef={nodeRef}
                                columns={[
                                    { title: 'Unique ID', field: 'id',
                                        headerStyle: {
                                        backgroundColor: "#039be5"},
                                        searchable: true,
                                        filtering: true
                                    },
                                    { title: 'Date Assessed', field: 'date',
                                        headerStyle: {
                                            backgroundColor: "#01579b"},
                                        searchable: true,
                                        filtering: true
                                    },
                                    { title: 'Total VC', field: 'ovc',
                                        headerStyle: {
                                            backgroundColor: "#039be5"},
                                        searchable: true,
                                        filtering: true
                                    },
                                    {title: 'Address', field: 'address',
                                        headerStyle: {
                                            backgroundColor: "#01579b"},
                                        searchable: true,
                                        filtering: true,
                                    },
                                    {title: 'Action',field: 'action',
                                        headerStyle: {
                                            backgroundColor: "#039be5"},
                                        searchable: true,
                                        filtering: true
                                    },
                                ]}
                                data={query =>
                                    new Promise((resolve, reject) =>
                                        axios.get(`${baseUrl}households/?size=${query.pageSize}&page=${query.page}&search=${query.search}`)
                                            .then(response => response)
                                            .then(result => { resolve({
                                                data:
                                                    result.data.map((row) => ({
                                                    id:<span> <Link to={{pathname: "/household/home", state: row.id }}>{row.uniqueId} </Link></span>,
                                                    date: row.details && row.details.assessmentDate ? row.details.assessmentDate : "",
                                                    ovc: row.details &&  row.details.noOfChildren != null ?  row.details.noOfChildren : 0,
                                                    address: row.details && row.details.street ? row.details.street : null,
                                                        action:
                                                        <Menu>
                                                            <MenuButton style={{ backgroundColor:"#3F51B5", color:"#fff", border:"2px solid #3F51B5", borderRadius:"4px"}}>
                                                                Action <span aria-hidden>▾</span>
                                                            </MenuButton>
                                                            <MenuList style={{hover:"#eee"}}>
                                                                <MenuItem > <Link to={{pathname: "/household/home", state: row.id }}> View Dashboard</Link> </MenuItem>
                                                                {/*<MenuItem onClick={() => toggleUpdate(row)}>{" "}Edit</MenuItem>*/}
                                                                <MenuItem onClick={() => onDelete(row)} >Delete Household</MenuItem>
                                                               {/* <MenuItem style={{ color: "#000 !important" }} onClick={() => toggleDeleteUser(row.id)}>
                                                                    <FaTrashAlt size="15" />{" "}
                                                                    <span style={{ color: "#000" }}>Delete Household</span></MenuItem>*/}
                                                            </MenuList>
                                                        </Menu>

                                                })),
                                                page: query.page,
                                                totalCount: result.headers['x-total-count'],
                                            })
                                            })
                                    )}
                                options={{

                                    searchFieldStyle: {
                                        width : '200%',
                                        margingLeft: '250px',
                                    },
                                    filtering: true,
                                    exportButton: false,
                                    searchFieldAlignment: 'left',
                                    pageSizeOptions:[10,20,100],
                                    pageSize:10,
                                    debounceInterval: 400
                                }}
                            />
                        </CCardBody>
                    </CCard>
                </CCol>
            </CRow>
            {modal ?
                <NewHouseHold  modal={modal} toggle={toggle} reloadSearch={performSearch} household={currentHousehold} />
                :
                ""
            }

        </>

    )

}


const mapStateToProps = state => {
    return {
        houseHoldList: state.houseHold.houseHoldList
    };
};
const mapActionToProps = {
    fetchAllHouseHold: fetchAllHouseHold,
    deleteHousehold: deleteHousehold
};

export default connect(mapStateToProps, mapActionToProps)(HouseHoldList);

