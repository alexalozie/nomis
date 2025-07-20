import axios from "axios";
import { url } from "./../api";
import * as ACTION_TYPES from "./types";
import { toast } from "react-toastify";


export const fetchAllCbos = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}cbos`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_CBO,
                payload: response.data
            });
          
            onSuccess();
            
  
        })
        .catch(error => {
                if(onError){
                    onError();
                    
                }
            }

        );
};
export const createCbosSetup = (data, onSuccess , onError) => dispatch => {

    axios
        .post(`${url}cbos`, data)
        .then(response => {
            
            if(onSuccess){
                onSuccess();
                toast.success("CBO Setup saved successfully!")
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

export const updateCbo = (id, data, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}cbos/${id}`, data)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("CBO Updated successfully!")
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
export const deleteCbo = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}cbos/${id}`)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("CBO Deleted successfully!")
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