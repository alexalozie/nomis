import {useState } from 'react';
import axios from "axios";
import { url } from "./../api";
import * as ACTION_TYPES from "./types";
import { toast } from "react-toastify";


export const fetchAllCboProject = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}cbo-projects`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_CBO_PROJECT,
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

export const createCboProject = (data, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}cbo-projects`, data)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.CREATE_CBO_PROJECT,
                payload: response.data
            });
            if(onSuccess){
                onSuccess();
                toast.success("CBO Project Setup Successful!");
            }
        })
        .catch(error => {
            
            if (error.response) {
                let errorMessage = error.response.data.apierror.message;
                dispatch({
                    type: ACTION_TYPES.ERROR_CREATE_CBO_PROJECT,
                    payload: errorMessage
                });
                if(onError){
                    onError();
                    toast.error(errorMessage);
                }
                // client received an error response (5xx, 4xx)
              } else if (error.request) {
                let errorMessage ='something went wrong. no response from the server';
                dispatch({
                    type: ACTION_TYPES.ERROR_CREATE_CBO_PROJECT,
                    payload: errorMessage
                });
                if(onError){
                    onError();
                    toast.error(errorMessage);
                }
                // client never received a response, or request never left
              } else {
                let errorMessage = 'something went wrong';
                // anything else
                dispatch({
                    type: ACTION_TYPES.ERROR_CREATE_CBO_PROJECT,
                    payload: errorMessage
                });
                if(onError){
                    onError();
                    toast.error(errorMessage);
                }
              }
           
            }

        );
};

export const updateCboProject = (id, data, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}cbo-projects/${id}`, data)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.UPDATE_CBO_PROJECT,
                payload: response.data
            });
            if(onSuccess){
                onSuccess();
                toast.success("CBO Project Updated Successful!");
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

export const deleteCboProject = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}ips/${id}`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.DELETE_CBO_PROJECT,
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

export const assignProjectToUser = (data, onSuccess, onError  ) => dispatch => {
    axios
        .post(`${url}application-user-cbo-project`, data)
        .then(response => {
            
                toast.success("CBO Project Assign Successful!");
                dispatch({
                    type: ACTION_TYPES.ASSIGN_CBO_PROJECT,
                    payload: response.data
                });
               
                onSuccess()    
                    
                
            
        })
        .catch(error => {
            toast.error("Something went wrong");
            onError()
        }
        );
};

export const updateCboProjectLocation = (data, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}cbo-project-location`, data)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.UPDATE_CREATE_CBO_PROJECT_LOCATION,
                payload: response.data
            });
            if(onSuccess){
                onSuccess();
                toast.success("CBO Project Location Updated Successful!");
            }
        })
        .catch(error => {
            
            if (error.response) {
                let errorMessage = error.response.data.apierror.message;
                dispatch({
                    type: ACTION_TYPES.ERROR_UPDATE_CREATE_CBO_PROJECT_LOCATION,
                    payload: errorMessage
                });
                if(onError){
                    onError();
                    toast.error(errorMessage);
                }
                // client received an error response (5xx, 4xx)
              } else if (error.request) {
                let errorMessage ='something went wrong. no response from the server';
                dispatch({
                    type: ACTION_TYPES.ERROR_CREATE_CBO_PROJECT,
                    payload: errorMessage
                });
                if(onError){
                    onError();
                    toast.error(errorMessage);
                }
                // client never received a response, or request never left
              } else {
                let errorMessage = 'something went wrong';
                // anything else
                dispatch({
                    type: ACTION_TYPES.ERROR_CREATE_CBO_PROJECT,
                    payload: errorMessage
                });
                if(onError){
                    onError();
                    toast.error(errorMessage);
                }
              }
           
            }

        );
};