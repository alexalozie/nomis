import {useState } from 'react';
import axios from "axios";
import { url } from "./../api";
import * as ACTION_TYPES from "./types";
import { toast } from "react-toastify";

export const fetchAllSchool = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}schools`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_SCHOOLS,
                payload: response.data
            });
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

export const createSchool = (data, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}schools`, data)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.CREATE_SCHOOL,
                payload: response.data
            });
            if(onSuccess){
                onSuccess();
                toast.success("School saved Successful!");
            }
        })
        .catch(error => {

                if (error.response) {
                    let errorMessage = error.response.data.apierror.message;
                    dispatch({
                        type: ACTION_TYPES.ERROR_CREATE_SCHOOL,
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
                        type: ACTION_TYPES.ERROR_CREATE_SCHOOL,
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
                        type: ACTION_TYPES.ERROR_CREATE_SCHOOL,
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

export const updateSchool = (id, data, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}schools/${id}`, data)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.UPDATE_SCHOOL,
                payload: response.data
            });
            if(onSuccess){
                onSuccess();
                toast.success("School Updated Successful!");
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

export const uploadSchool = (formData, config,  onSuccess, onError) => dispatch => {
    axios
        .post(`${url}schools/import`, formData, config)
        .then(response => {
            console.log(response);
            dispatch({
                type: ACTION_TYPES.UPLOAD_SCHOOL,
                payload: response.data
            });

            toast.success("School was imported successfully!");
            if(onSuccess){
                onSuccess();
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                    toast.error("School not was uploaded successfully!");
                }
            }

        );
};
