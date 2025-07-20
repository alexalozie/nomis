import React, {useEffect} from 'react';
import MaterialTable from 'material-table';
import {
    Card,
    CardBody, Modal, ModalBody, ModalHeader, Spinner, ModalFooter
} from 'reactstrap';
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";
import { Link } from 'react-router-dom';
import Button from "@material-ui/core/Button";
import { FaPlus } from "react-icons/fa";
import "@reach/menu-button/styles.css";
import { connect } from "react-redux";
import { fetchAll, deleteIp } from "../../../actions/ip";
import NewCboProject from "./NewCboProject";
import EditCboProject from "./EditCboProject";
import ViewCboProject from './ViewCboProject'
import EditIcon from "@material-ui/icons/Edit";
import {Menu,MenuList,MenuButton,MenuItem,} from "@reach/menu-button";
import "@reach/menu-button/styles.css";
import { toast } from "react-toastify";
import SaveIcon from "@material-ui/icons/Delete";
import CancelIcon from "@material-ui/icons/Cancel";
import {makeStyles} from "@material-ui/core/styles";
import { useSelector, useDispatch } from 'react-redux';
import {  fetchAllCboProject } from '../../../actions/cboProject'

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

const IpListManager = (props) => {
    const cboProjectList = useSelector(state => state.cboProjects.cboProjectList);
    const dispatch = useDispatch();
    const [loading, setLoading] = React.useState(true);
    const [deleting, setDeleting] = React.useState(false);
    const [showModal, setShowModal] = React.useState(false);
    const toggleModal = () => setShowModal(!showModal)
    const [showEditModal, setShowEditModal] = React.useState(false);
    const toggleEditModal = () => setShowEditModal(!showEditModal)
    const [currentIp, setcurrentIp] = React.useState(null);
    const [currentProject, setcurrentProject] = React.useState(null);
    const [showDeleteModal, setShowDeleteModal] = React.useState(false);
    const toggleDeleteModal = () => setShowDeleteModal(!showDeleteModal)
    const [showCboProjectModal, setShowCboProjectModalModal] = React.useState(false);
    const toggleCBoProjecteModal = () => setShowCboProjectModalModal(!showCboProjectModal)
    const [currentCboProject, setCurrentCboProject] = React.useState(null);
    const classes = useStyles()
    useEffect(() => {
        loadCboProjectList()
    }, []); //componentDidMount

 const loadCboProjectList = () => {
  
    setLoading('true');
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)     
        }
        dispatch(fetchAllCboProject(onSuccess, onError));
    }; //componentDidMount

    const openNewDomainModal = (row) => {
        setcurrentProject(row);
        toggleModal();
    }

    const openCboProjectDetailModal = (row) => {
        setCurrentCboProject(row);
        toggleCBoProjecteModal();
    }

    const openEditCboProjectDetailModal = (row) => {
        setcurrentProject(row);
        toggleEditModal();
    }

    const processDelete = (id) => {
        setDeleting(true);
       const onSuccess = () => {
           setDeleting(false);
           toggleDeleteModal();
           loadCboProjectList();
       };
       const onError = () => {
           setDeleting(false);
           toggleDeleteModal();
       };
       props.deleteIp(id, onSuccess, onError);
       }
       const openDonor = (row) => {
            setcurrentIp(row);
           toggleModal();
       }
   
    //    const deleteIpDetail = (row) => {
    //        setcurrentIp(row);
    //        toggleDeleteModal();
    //    }

    return (
        <Card>
            <CardBody>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" to={{pathname: "/admin"}} >
                        Admin
                    </Link>
                    <Typography color="textPrimary">CBO Project Setup</Typography>
                </Breadcrumbs>
                <br/>
                <div className={"d-flex justify-content-end pb-2"}>
                    <Button variant="contained"
                            color="primary"
                            startIcon={<FaPlus />}
                            onClick={() => openNewDomainModal(null)}
                            >
                        <span style={{textTransform: 'capitalize'}}> New CBO Project</span>
                    </Button>

                </div>
                <MaterialTable
                    icons={tableIcons}
                    title="Find Cbo Project"
                    columns={[
                    {
                        title: "ID",
                        field: "id",
                    },
                    {
                        title: "CBO Name",
                        field: "cboName",
                    },
                    {
                        title: "Donor Name",
                        field: "donorName",
                    },
                    {
                        title: "Implementer Name",
                        field: "IpName",
                    },
                    { title: "Project", field: "description" },
                    { title: "Action", field: "action" },
                ]}
                isLoading={loading}
                data={cboProjectList.map((row) => ({
                    id:row.id,
                    cboName: row.cboName,
                    description: row.description,
                   // cboName: row.description,
                    donorName: row.donorName,
                    IpName: row.implementerName,
                    action:( <div>
                            <Menu>
                                <MenuButton
                                style={{
                                    backgroundColor: "#3F51B5",
                                    color: "#fff",
                                    border: "2px solid #3F51B5",
                                    borderRadius: "4px",
                                }}
                                >
                                Actions <span aria-hidden>▾</span>
                                </MenuButton>
                                <MenuList style={{ color: "#000 !important" }}>
                                
                                <MenuItem style={{ color: "#000 !important" }} onClick={() =>openCboProjectDetailModal(row)}>
                                   
                                    <span style={{ color: "#000" }} >View  Detail</span>
                                    
                                </MenuItem>
                                
                                <MenuItem style={{ color: "#000 !important" }}  onClick={() =>openEditCboProjectDetailModal(row)}>
                                  
                                    <span style={{ color: "#000" }}>Edit  </span>
                                   
                                </MenuItem>
                                
                                </MenuList>
                            </Menu>
                            </div>
                            )

                }))}
                
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
            </CardBody>
            <EditCboProject toggleModal={toggleEditModal} showModal={showEditModal} loadIpList={props.ipList} formData={currentProject} loadIps={loadCboProjectList}/>
            <NewCboProject toggleModal={toggleModal} showModal={showModal} loadIpList={props.ipList} formData={currentProject} loadIps={loadCboProjectList}/>
            <ViewCboProject toggleModal={toggleCBoProjecteModal} showModal={showCboProjectModal} currentCboProject={currentCboProject}  loadIps={loadCboProjectList}/>
            {/*Delete Modal for Application Codeset */}
            <Modal isOpen={showDeleteModal} toggle={toggleDeleteModal} >
                    <ModalHeader toggle={props.toggleDeleteModal}> Delete Ip - {currentIp && currentIp.name ? currentIp.name : ""} </ModalHeader>
                    <ModalBody>
                        <p>Are you sure you want to proceed ?</p>
                    </ModalBody>
                <ModalFooter>
                    <Button
                        type='button'
                        variant='contained'
                        color='primary'
                        className={classes.button}
                        startIcon={<SaveIcon />}
                        disabled={deleting}
                        onClick={() => processDelete(currentIp.id)}
                    >
                        Delete  {deleting ? <Spinner /> : ""}
                    </Button>
                    <Button
                        variant='contained'
                        color='default'
                        onClick={toggleDeleteModal}
                        startIcon={<CancelIcon />}
                    >
                        Cancel
                    </Button>
                </ModalFooter>
        </Modal>
        </Card>
        
    );
   

}


const mapStateToProps = state => {
    return {
        ipList: state.ipReducer.ipList
    };
  };
  const mapActionToProps = {
    fetchAllDonors: fetchAll,
    deleteIp: deleteIp
  };
  
  export default connect(mapStateToProps, mapActionToProps)(IpListManager);
