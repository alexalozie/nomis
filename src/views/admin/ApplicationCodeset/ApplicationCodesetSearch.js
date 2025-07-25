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
import { fetchAllCodeset, deleteApplicationCodeset } from "../../../actions/codeSet";
import NewApplicationCodeset from "./NewApplicationCodeset";
import EditIcon from "@material-ui/icons/Edit";
import DeleteIcon from "@material-ui/icons/Delete";
import { toast } from "react-toastify";
import SaveIcon from "@material-ui/icons/Delete";
import CancelIcon from "@material-ui/icons/Cancel";
import {makeStyles} from "@material-ui/core/styles";
import { forwardRef } from 'react';
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


const ApplicationCodesetList = (props) => {
    const [loading, setLoading] = React.useState(true);
    const [deleting, setDeleting] = React.useState(false);
    const [showModal, setShowModal] = React.useState(false);
    const toggleModal = () => setShowModal(!showModal)
    const [currentCodeset, setCurrentCodeset] = React.useState(null);
    const [showDeleteModal, setShowDeleteModal] = React.useState(false);
    const toggleDeleteModal = () => setShowDeleteModal(!showDeleteModal)
    const classes = useStyles()
    useEffect(() => {
        loadApplicationCodeset()
    }, []); //componentDidMount

 const loadApplicationCodeset = () => {
  
    setLoading('true');
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)     
        }
            props.fetchAllCApplicationCodeset(onSuccess, onError);
    }; //componentDidMount

    const openNewDomainModal = (row) => {
        setCurrentCodeset(row);
        toggleModal();
    }

    const processDelete = (id) => {
        setDeleting(true);
       const onSuccess = () => {
           setDeleting(false);
           toggleDeleteModal();
           loadApplicationCodeset();
       };
       const onError = () => {
           setDeleting(false);
           toast.error("Something went wrong, please contact administration");
       };
       props.delete(id, onSuccess, onError);
       }
       const openApplicationCodeset = (row) => {
           setCurrentCodeset(row);
           toggleModal();
       }
   
       const deleteApplicationCodeset = (row) => {
           setCurrentCodeset(row);
           toggleDeleteModal();
       }

    return (
        <Card>
            <CardBody>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" to={{pathname: "/admin"}} >
                        Admin
                    </Link>
                    <Typography color="textPrimary">Application Codeset </Typography>
                </Breadcrumbs>
                <br/>
                {/*<div className={"d-flex justify-content-end pb-2"}>*/}
                {/*    <Button variant="contained"*/}
                {/*            color="primary"*/}
                {/*            startIcon={<FaPlus />}*/}
                {/*            onClick={() => openNewDomainModal(null)}>*/}
                {/*        <span style={{textTransform: 'capitalize'}}> New Application Codeset </span>*/}
                {/*    </Button>*/}
                {/*</div>*/}
                <MaterialTable
                 icons={tableIcons}
                    title="Find Application Codeset"
                    columns={[
                    {
                        title: "Codeset Group",
                        field: "codesetGroup",
                    },
                    { title: "Value", field: "display" },
                    { title: "Version", field: "version" },
                    { title: "Language", field: "language" },
                    
                ]}
                isLoading={loading}
                data={props.applicationCodesetList.map((row) => ({
                    codesetGroup: row.codesetGroup,
                    id: row.id,
                    display: row.display,
                    language: row.language,
                    version: row.version
                }))}
                actions= {[
                    // {
                    //     icon: EditIcon,
                    //     iconProps: {color: 'primary'},
                    //     tooltip: 'Edit Codeset',
                    //     onClick: (event, row) => openApplicationCodeset(row)
                    // },
                    // {
                    //     icon: DeleteIcon,
                    //     iconProps: {color: 'primary'},
                    //     tooltip: 'Delete Codeset',
                    //     onClick: (event, row) => deleteApplicationCodeset(row)
                    // }
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
            </CardBody>
            <NewApplicationCodeset toggleModal={toggleModal} showModal={showModal} loadApplicationCodeset={props.applicationCodesetList} formData={currentCodeset} loadCodeset={loadApplicationCodeset}/>
            {/*Delete Modal for Application Codeset */}
            <Modal isOpen={showDeleteModal} toggle={toggleDeleteModal} >
                    <ModalHeader toggle={props.toggleDeleteModal}> Delete Domain Area - {currentCodeset && currentCodeset.display ? currentCodeset.display : ""} </ModalHeader>
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
                        onClick={() => processDelete(currentCodeset.id)}>
                        Delete  {deleting ? <Spinner /> : ""}
                    </Button>
                    <Button
                        variant='contained'
                        color='default'
                        onClick={toggleDeleteModal}
                        startIcon={<CancelIcon />}>
                        Cancel
                    </Button>
                </ModalFooter>
        </Modal>
        </Card>
        
    );
   

}


const mapStateToProps = state => {
    return {
        applicationCodesetList: state.codesetsReducer.codesetList
    };
  };
  const mapActionToProps = {
    fetchAllCApplicationCodeset: fetchAllCodeset,
    delete: deleteApplicationCodeset
  };
  
  export default connect(mapStateToProps, mapActionToProps)(ApplicationCodesetList);
