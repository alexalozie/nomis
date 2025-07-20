import axios from "axios";
import { url as baseUrl } from "../../../api";
import React, { useEffect, useState } from "react";
import MaterialTable from "material-table";
import { connect } from "react-redux";
import { fetchUsers, updateUserRole } from "../../../actions/user";
import "./UserList.css";
import { Menu, MenuList, MenuButton, MenuItem } from "@reach/menu-button";
import "@reach/menu-button/styles.css";
import { ToastContainer, toast } from "react-toastify";
import SaveIcon from "@material-ui/icons/Save";
import CancelIcon from "@material-ui/icons/Cancel";
import MatButton from "@material-ui/core/Button";
import {
  Button,
  Form,
  FormGroup,
  Label,
  Modal,
  ModalBody,
  ModalFooter,
  ModalHeader,
} from "reactstrap";
import { makeStyles } from "@material-ui/core/styles";
import { MdModeEdit } from "react-icons/md";
import {FaTrashAlt } from "react-icons/fa";
import useForm from "../../Functions/UseForm";
import DualListBox from "react-dual-listbox";
import "react-dual-listbox/lib/react-dual-listbox.css";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";
import AssignUserToProjectModal from "./AssignUserToProject";
import { forwardRef } from 'react';
import { Link } from 'react-router-dom';

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

const useStyles = makeStyles((theme) => ({
  button: {
    margin: theme.spacing(1),
  },
}));

let userId = 0;
let currentUser = {};

const UserList = (props) => {

  const classes = useStyles();
  const [loading, setLoading] = useState(true);
  const [modal, setModal] = useState(false);
  const [deleteModal, setDeleteModal] = useState(false);
  const [assignFacilityModal, setAssignFacilityModal] = useState(false);
  const [roles, setRoles] = useState([]);
  const [selectedRoles, setselectedRoles] = useState([]);
  const [saving, setSaving] = useState(false);
  
  let { values, setValues, handleInputChange, resetForm } = useForm({});

  useEffect(() => {
    fetchUsers()
  }, []);

  //Load User from the server 
  async function fetchUsers() {
    const onSuccess = () => {
      setLoading(false);
    };
    const onError = () => {
      setLoading(false);
    };
    props.fetchAllUsers(onSuccess, onError);
  }
  /* Get list of Roles from the server */
  useEffect(() => {
    async function getCharacters() {
      axios
        .get(`${baseUrl}roles`)
        .then((response) => {
         
          setRoles(
            Object.entries(response.data).map(([key, value]) => ({
              label: value.name,
              value: value.id,
            }))
          );
        })
        .catch((error) => {
          console.log(error);
        });
    }
    getCharacters();
  }, []);

  const onRoleSelect = (selectedValues) => {
    setselectedRoles(selectedValues);
  };

  const toggleAssignModal = (user) => {
    currentUser = user;
    setAssignFacilityModal(!assignFacilityModal);
    console.log(assignFacilityModal);
  
  }

  const toggleEditRoles = (id) => {
    userId = id;
    setModal(!modal);
    if (!modal) {
      axios
        .get(`${baseUrl}users/${id}/roles`)
        .then((response) => {
          
          const y = response && response.data
            ? response.data.map((x) => (x.id)) : [];
            console.log(y)

          setselectedRoles(y)
         
          console.log(selectedRoles)
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };
  const toggleDeleteUser = (id) => {
    userId = id;
    setDeleteModal(!deleteModal);
  };
  const deleteUser = (id) => {
      axios
        .delete(`${baseUrl}users/${id}/`)
        .then((response) => {
          const y = response
          toast.success("User Deleted Successfully");
          fetchUsers()
          toggleDeleteUser()
        })
        .catch((error) => {
          toast.error("Something went wrong");
        });
  }
  const handleEdit = (e) => {
    e.preventDefault();
    let roles = [];
    selectedRoles.map((p) => {
      const role = { id: null };
      role.id = p;
      roles.push(role);
    });
    values = roles;
    setSaving(true);
    const onSuccess = () => {
      setSaving(false);
      toast.success("User roles Updated Successfully");
      resetForm();
      fetchUsers()
    };
    const onError = () => {
      setSaving(false);
      toast.error("Something went wrong");
    };
    props.updateUserRole(userId, values, onSuccess, onError);
  };


  return (
    <div>
      <MaterialTable
      icons={tableIcons}
        title="User List"
        columns={[
          { title: "Name", field: "name" },
          { title: "Username", field: "userName", filtering: false },
          { title: "Assigned Project ", field: "assignProject", filtering: false },
          { title: "Roles", field: "roles", filtering: false },
            { title: "Phone Number", field: "phoneNumber", filtering: false },
            { title: "", field: "actions", filtering: false },
        ]}
        isLoading={loading}
      data={query =>
          new Promise((resolve, reject) =>
              axios.get(`${baseUrl}users/?size=${query.pageSize}&page=${query.page}&search=${query.search}`)
                  .then(response => response)
                  .then(result => { resolve({
                      data: result.data.map((row) => ({
              name: row.firstName + " " + row.lastName,
              userName: row.userName,
              assignProject: row.applicationUserCboProjects.length >0 ? row.applicationUserCboProjects.length : 0,
              roles: row.roles.toString(),
                phoneNumber: row.phoneNumber,
          actions:
              <Menu>
                <MenuButton
                  style={{
                    backgroundColor: "#3F51B5",
                    color: "#fff",
                    border: "2px solid #3F51B5",
                    borderRadius: "4px",
                  }}>
                  Actions <span aria-hidden>▾</span>
                </MenuButton>
                <MenuList style={{ color: "#000 !important" }}>
                  <MenuItem style={{ color: "#000 !important" }}  onClick={() => toggleEditRoles(row.id)}>
                      <MdModeEdit size="15" />{" "}
                      <span style={{ color: "#000" }}>Edit Roles </span>
                  </MenuItem>
                    <MenuItem style={{ color:"#000 !important"}}>
                        <Link
                            to={{pathname: "/user-update", row: row}}>
                            <MdModeEdit size="15" color="blue" />{" "}<span style={{color: '#000'}}>Edit User</span>
                        </Link>
                    </MenuItem>
                    <MenuItem style={{ color: "#000 !important" }}  onClick={() => toggleAssignModal(row)}>
                      <MdModeEdit size="15" />{" "}
                      <span style={{ color: "#000" }}>Assign Project </span>
                  </MenuItem>
                  <MenuItem style={{ color: "#000 !important" }} onClick={() => toggleDeleteUser(row.id)}>
                      <FaTrashAlt size="15" />{" "}
                      <span style={{ color: "#000" }}>Delete User </span>
                  </MenuItem>
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
          filtering: false,
          exportButton: false,
          searchFieldAlignment: 'left',
          pageSizeOptions:[10,20,100],
          pageSize:10,
          debounceInterval: 400
      }}
      />
     <AssignUserToProjectModal
          showModal={assignFacilityModal} toggleModal={() => setAssignFacilityModal(!assignFacilityModal)}  user={currentUser} loadUsers={fetchUsers} />

        <Modal isOpen={modal}  size="lg">
                <Form onSubmit={handleEdit}>
                  <ModalHeader>Edit Roles</ModalHeader>
                  <ModalBody>
                    <FormGroup>
                      <Label for="roles">Roles</Label>
                      <DualListBox
                          canFilter
                        options={roles}
                        onChange={onRoleSelect}
                        selected={selectedRoles}
                      />
                    </FormGroup>

                    <MatButton
                      type="submit"
                      variant="contained"
                      color="primary"
                      
                      className=" float-left mr-1"
                      startIcon={<SaveIcon />}
                      disabled={saving}
                      onClick={() => toggleEditRoles(userId)}
                    >
                      {!saving ? (
                        <span style={{ textTransform: "capitalize" }}>
                          Save
                        </span>
                      ) : (
                        <span style={{ textTransform: "capitalize" }}>
                          Saving...
                        </span>
                      )}
                    </MatButton>
                    {"    "}
                    <MatButton
                      variant="contained"
                      className=" float-left mr-1"
                      startIcon={<CancelIcon />}
                      onClick={() => toggleEditRoles(userId)}>
                      <span style={{ textTransform: "capitalize" }}>
                        Cancel
                      </span>
                    </MatButton>
                  <br/><br/>
                  </ModalBody>


                </Form>
              </Modal>
              {/** Delete User Modal  */}
              <Modal isOpen={deleteModal}  size="lg">
                <Form >
                  <ModalHeader>Delete User</ModalHeader>
                  <ModalBody>
                   <p>Are you sure you want to delete User</p> 
                    <MatButton
                      type="submit"
                      variant="contained"
                      color="primary"

                      className=" float-left mr-1"
                      startIcon={<SaveIcon />}
                      disabled={saving}
                      onClick={()=>deleteUser(userId)}
                    >
                      {!saving ? (
                        <span style={{ textTransform: "capitalize" }}>
                          Delete
                        </span>
                      ) : (
                        <span style={{ textTransform: "capitalize" }}>
                          Deleting...
                        </span>
                      )}
                    </MatButton>
                    {"    "}
                    <MatButton
                      variant="contained"
                      className=" float-left mr-1"
                      startIcon={<CancelIcon />}
                      onClick={() => toggleDeleteUser(userId)}
                    >
                      <span style={{ textTransform: "capitalize" }}>
                        Cancel
                      </span>
                    </MatButton>
                  <br/><br/>
                  </ModalBody>
                
                   
                </Form>
              </Modal>
              {/**End of Deleting User Modal  */}
    </div>
  );
};

const mapStateToProps = (state) => {
  return {
      list: state.users.list
    // usersList: state.users.list,
  };
};

const mapActionToProps = {
  fetchAllUsers: fetchUsers,
  updateUserRole: updateUserRole,
};

export default connect(mapStateToProps, mapActionToProps)(UserList);
