import axios from "axios";
import { url } from "./../api";
import * as ACTION_TYPES from "./types";
import { toast } from "react-toastify";

export const fetchAll = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}donors`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_DONOR,
                payload: response.data
            });
            if(onSuccess){
                onSuccess();
            }
        })
        .catch(error => {
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

export const createDonor = (data, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}donors`, data)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("Donor Save successfully!");
            }
        })
        .catch(error => {
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

export const updateDonor = (id, data, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}donors/${id}`, data)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("Donor updated successfully!")
            }
        })
        .catch(error => {
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

export const deleteDonor = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}donors/${id}`)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("Donor deleted successfully!")
            }
        })
        .catch(error => {
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

