import axios from "axios";
import { url as baseUrl } from "../api";
import * as ACTION_TYPES from "./types";
import { toast } from "react-toastify";

/**
 * @Actions
 *  Role Operations
 * returns API response from server => payload: response || error
 * =================================
 * @method POST => register() -> register a new User

 */
export const addRole = (data, onSuccess, onError) => (dispatch) => {
  axios
    .post(`${baseUrl}roles/`, data)
    .then((response) => {
      try {
        dispatch({
          type: ACTION_TYPES.ADD_ROLE,
          payload: response.data,
        });
        onSuccess && onSuccess();
      } catch (err) {
        onError();
        console.log(err);
      }
    })
    .catch((error) => {
      dispatch({
        type: ACTION_TYPES.ROLE_ERROR,
        payload: "Something went wrong, please try again",
      });
      console.log(error);
    });
};

export const updateRole = (id, data, onSuccess, onError) => (dispatch) => {
  axios
    .post(`${baseUrl}roles/${id}`, data)
    .then((response) => {
      try {
        dispatch({
          type: ACTION_TYPES.ADD_ROLE,
          payload: response.data,
        });
        onSuccess && onSuccess();
      } catch (err) {
        onError();
        console.log(err);
      }
    })
    .catch((error) => {
      dispatch({
        type: ACTION_TYPES.ROLE_ERROR,
        payload: "Something went wrong, please try again",
      });
      console.log(error);
    });
};

export const fetchRoles = (onSuccess, onError) => (dispatch) => {
  axios
    .get(`${baseUrl}roles/`)
    .then((response) => {
      if (onSuccess) {
        onSuccess();
      }
      dispatch({
        type: ACTION_TYPES.FETCH_ROLES,
        payload: !response.data  ? {}: response.data,
      });
      onSuccess();
    })
    .catch((error) => {
      if (onError) {
        onError();
      }
      dispatch({
        type: ACTION_TYPES.ROLE_ERROR,
        payload: "Something went wrong, please try again",
      });
      onError();
    });
};

export const fetchPermissions = (onSuccess, onError) => (dispatch) => {
  axios
    .get(`${baseUrl}permissions/`)
    .then((response) => {
      if (onSuccess) {
        onSuccess();
      }
      dispatch({
        type: ACTION_TYPES.FETCH_PERMISSIONS,
        payload: !response.data  ? {}: response.data,
      });
      onSuccess();
    })
    .catch((error) => {
      if (onError) {
        onError();
      }
      dispatch({
        type: ACTION_TYPES.PERMISSION_ERROR,
        payload: "Something went wrong, please try again",
      });
      onError();
    });
};

export const deleteRole = (id) => (dispatch) => {
  axios
    .delete(`${baseUrl}roles/${id}`)
    .then((response) => {
      dispatch({
        type: ACTION_TYPES.ROLE_DELETE,
        payload: id,
      });
      toast.success("Role deleted successfully!");
    })
    .catch((error) => {
      dispatch({
        type: ACTION_TYPES.ROLE_ERROR,
        payload: error.response.data,
      });
      toast.error("Something went wrong");
    });
};
