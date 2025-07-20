import React from 'react';
import {
    Card,
    CardBody, Modal, ModalBody, ModalHeader, Spinner, ModalFooter
} from 'reactstrap';
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import Typography from "@material-ui/core/Typography";
import { Link } from 'react-router-dom';
import MaterialTable from 'material-table';

import { connect } from "react-redux";
import { fetchAllFlag, deleteFlag } from "../../../actions/flag";
import Button from "@material-ui/core/Button";
import { FaPlus } from "react-icons/fa";
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
import NewFlag from './NewFlag';
import {Alert} from "@material-ui/lab";


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
const FlagSearch = (props) => {
    const [loading, setLoading] = React.useState(true);
    const [deleting, setDeleting] = React.useState(false);
    const [showModal, setShowModal] = React.useState(false);
    const [showDeleteModal, setShowDeleteModal] = React.useState(false);
    const [currentFlag, setCurrentFlag] = React.useState(null);
    const [formData, setFormData] = React.useState(null);
    const toggleModal = () => setShowModal(!showModal)
    const toggleDeleteModal = () => setShowDeleteModal(!showDeleteModal)
    const classes = useStyles()

     React.useEffect(() => {
       loadFlag()
    }, []); 

    const loadFlag = () => {
        const onSuccess = () => {
            setLoading(false);
        };
        const onError = () => {
            setLoading(false);
        };
        props.fetchAll(onSuccess, onError);
    }

      const openNewFlag = (row) => {
        setFormData(row);
        toggleModal();
    }

    const deleteFlag = (row) => {
            setCurrentFlag(row);
            toggleDeleteModal();
        }

        const processDelete = (id) => {
                setDeleting(true);
                const onSuccess = () => {
                    setDeleting(false);
                    toggleDeleteModal();
                    toast.success("Flag deleted successfully!");
                    loadFlag();
                };
                const onError = () => {
                    setDeleting(false);
                    toast.error("Something went wrong, please contact administration");
                };
                props.delete(id, onSuccess, onError);
            }
    return (
        <>
            <Card>
                <CardBody>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" to={{ pathname: "/admin" }} >
                            Admin
                        </Link>
                        <Typography color="textPrimary">Flag Search </Typography>
                    </Breadcrumbs>
                    <br />
                    <div className={"d-flex justify-content-end pb-2"}>
                        <Button
                            variant="contained"
                            color="primary"
                            startIcon={<FaPlus />}
                        onClick={() => openNewFlag(null)}>
                            <span style={{ textTransform: 'capitalize' }}>Add New Flag</span>
                        </Button>
                    </div>

                    <MaterialTable
                    title="Find Flags"
                    columns={[
                        {
                            title: "Flag Name",
                            field: "flag.name",
                        },
                        { title: "Form Field", field: "flag.fieldName" },
                        { title: "Operator", field: "flag.operator" },
                        { title: "Value", field: "flag.fieldValue" },
                    ]}
                    isLoading={loading}
                    data={props.list}

                    actions={[
                        // {
                        //     icon: EditIcon,
                        //     iconProps: { color: 'primary' },
                        //     tooltip: 'Edit Flag',
                        //     onClick: (event, rowData) => openNewFlag(rowData)
                        // },
                        // {
                        //     icon: DeleteIcon,
                        //     iconProps: { color: 'primary' },
                        //     tooltip: 'Delete Flag',
                        //     onClick: (event, rowData) => deleteFlag(rowData.flag)
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
                            width: '300%',
                            margingLeft: '250px',
                        },
                        filtering: true,
                        exportButton: false,
                        searchFieldAlignment: 'left',
                        actionsColumnIndex: -1
                    }}
                />

                </CardBody>
                <NewFlag toggleModal={toggleModal} showModal={showModal} loadFlag={loadFlag} formData={formData} />
            <Modal isOpen={showDeleteModal} toggle={toggleDeleteModal} >
                <ModalHeader toggle={toggleDeleteModal}> Delete Flag - {currentFlag && currentFlag.name ? currentFlag.name : ""} </ModalHeader>
                <ModalBody>
                    <Alert className={"mb-3"} color={"danger"}>
                        This delete can affect records on the system!<br></br>
                        <b>Are you sure you want to proceed ?</b>
                    </Alert>

                </ModalBody>
                <ModalFooter>
                    <Button
                        type='button'
                        variant='contained'
                        color='primary'
                        className={classes.button}
                        startIcon={<SaveIcon />}
                        disabled={deleting}
                        onClick={() => processDelete(currentFlag.id)}
                    >
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
        </>
    );
}

const mapStateToProps = state => {
    return {
        list: state.flags.flagList
    };
};

const mapActionToProps = {
    fetchAll: fetchAllFlag,
    delete: deleteFlag
};

export default connect(mapStateToProps, mapActionToProps)(FlagSearch);
