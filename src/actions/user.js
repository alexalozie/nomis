import axios from "axios";
import { url as baseUrl } from "../api";
import * as ACTION_TYPES from "./types";
import { toast } from "react-toastify";
/**
 * @Actions
 *  User Operations
 * returns API response from server => payload: response || error
 * =================================
 * @method POST => register() -> register a new User

 */
export const register = (data, onSuccess, onError) => (dispatch) => {
  axios
    .post(`${baseUrl}register/`, data)
    .then((response) => {
      try {
        dispatch({
          type: ACTION_TYPES.REGISTER_SUCCESS,
          payload: response.data,
        });
    
        onSuccess();
      } catch (err) {
        onError();
        
      }
    })
    .catch((error) => {
      if (error.response) {
        let errorMessage = error.response.data.apierror.message;              
        if(onError){
            onError();
            toast.error(errorMessage);
        }
        // client received an error response (5xx, 4xx)
      } else if (error.request) {
        let errorMessage ='something went wrong. no response from the server';                
        if(onError){
            onError();
            toast.error(errorMessage);
        }
        // client never received a response, or request never left
      } else {
        let errorMessage = 'something went wrong';
        // anything else               
        if(onError){
            onError();
            toast.error(errorMessage);
        }
      }
    }
      );
};

export const fetchUsers = (onSuccess, onError) => (dispatch) => {
  axios
    .get(`${baseUrl}users/`)
    .then((response) => {
      if (onSuccess) {
        onSuccess();
      }
      dispatch({
        type: ACTION_TYPES.FETCH_USERS,
        payload: response.data,
      });
      onSuccess();
    })
    .catch((error) => {
                     
        if(onError){
            onError();
            
        }
      }
      );
};


export const updateUserRole = (id, data, onSuccess, onError) => (dispatch) => {
  axios
    .put(`${baseUrl}users/${id}/roles`, data)
    .then((response) => {
      try {
        dispatch({
          type: ACTION_TYPES.USER_ROLE_UPDATE,
          payload: response.data,
        });
        onSuccess && onSuccess();
      } catch (err) {
        onError();
        console.log(err);
      }
    })
    .catch((error) => {
     
      if (error.response) {               
        if(onError){
            onError();
            
        }
      }
    }
     
      );
};

export const updateUser = (id, data, onSuccess, onError) => (dispatch) => {
    axios
        .put(`${baseUrl}users/${id}`, data)
        .then((response) => {
            try {
                dispatch({
                    type: ACTION_TYPES.USER_UPDATE,
                    payload: response.data,
                });
                onSuccess && onSuccess();
            } catch (err) {
                onError();
                console.log(err);
            }
        })
        .catch((error) => {

                if (error.response) {
                    if(onError){
                        onError();

                    }
                }
            }

        );
};

export const fetchMe = (onSuccess, onError) => (dispatch) => {
    axios
        .get(`${baseUrl}account/`)
        .then((response) => {
            if (onSuccess) {
                onSuccess();
            }
            dispatch({
                type: ACTION_TYPES.FETCH_ME,
                payload: response.data,
            });
        })
        .catch((error) => {
          if (error.response) {               
            if(onError){
                onError();
                toast.error('Something went wrong! Please try again ');
            }
          }
        }

        );
};
