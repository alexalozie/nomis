import axios from "axios";
import { url } from "./../api";
import * as ACTION_TYPES from "./types";
import { toast } from "react-toastify";

export const fetchAll = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}implementers`)
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
                if(onError){
                    onError();
                }
            }

        );
};

export const createIp = (data, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}implementers`, data)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("Save Successful!");
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                    toast.error("Something went wrong.");
                }
            }

        );
};

export const updateIp = (id, data, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}implementers/${id}`, data)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("Update Successful!");
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                    toast.error("Some thing went wrong!");
                }
            }

        );
};

export const deleteIp = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}implementers/${id}`)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("Deleted Successful!");
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                    toast.error("Some thing went wrong!");
                }
            }

        );
};

