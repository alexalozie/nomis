import axios from "axios";
import { url } from "./../api";
import * as ACTION_TYPES from "./types";
import { toast } from "react-toastify";

export const fetchAll = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}ips`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_IP,
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

export const createIp = (data, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}ips`, data)
        .then(response => {
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

export const updateIp = (id, data, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}ips/${id}`, data)
        .then(response => {
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

export const deleteIp = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}ips/${id}`)
        .then(response => {
            if(onSuccess){
                onSuccess();
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};

