
import axios from "axios";
import { url } from "../api";
import * as ACTION_TYPES from "./types";
import { toast } from "react-toastify";


export const fetchAllCodeset = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}application-codesets`)
        .then(response => {
            console.log(response.data)
            dispatch({
                type: ACTION_TYPES.FETCH_ALL_CODESET,
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
export const createApplicationCodeset = (data, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}application-codesets`, data)
        .then(response => {
            
            if(onSuccess){
                onSuccess();
                toast.success("Application codeset saved successfully!")
                }
            
        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};

export const updateApplicationCodeset = (id, data, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}application-codesets/${id}`, data)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("Application codeset updated successfully!")
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};

export const deleteApplicationCodeset = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}application-codesets/${id}`)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("Application codeset deleted successfully!")
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};

export const fetchAllWards = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}wards`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.WARD_LIST,
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
