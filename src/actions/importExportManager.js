import axios from "axios";
import { url } from "../api";
import * as ACTION_TYPES from "./types";
import {toast} from 'react-toastify';



export const fetchAllImport = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}data/available-files`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FILE_LIST,
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

export const ExportData = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}data/export`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.EXPORT_DATA,
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

export const dataBaseRestore = (onSuccess, onError) => dispatch => {
    axios
        .get(`${url}backup/restore`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.RESTORE_DATABASE,
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

export const downloadFile = (data, onSuccess, onError) => dispatch => {

    axios({
        url: `${url}data/download/{file}`,
        method: "GET",
        responseType: 'blob',
    }).then((response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'file.sql');
        document.body.appendChild(link);
        link.click();
    })
}


export const uploadFile = (formData, config,  onSuccess, onError) => dispatch => {
    axios
        .post(`${url}data/import`, formData, config)
        .then(response => {
            console.log(response);
            dispatch({
                type: ACTION_TYPES.IMPORT_FILE,
                payload: response.data
            });

            toast.success("Data imported  and record updated successfully!");
            if(onSuccess){
                onSuccess();
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                    toast.error("File was not imported successfully!");
                }
            }

        );
};
