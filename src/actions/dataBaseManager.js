import axios from "axios";
import { url } from "../api";
import * as ACTION_TYPES from "./types";
import {toast} from 'react-toastify';



export const fetchAllBackUp = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}backup/backup-available`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.BACKUP_LIST,
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

export const BackUpDataBase = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}backup/backup`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.BACKUP_DATABASE,
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

export const downloadDataBase = (data, onSuccess, onError) => dispatch => {

    axios({
        url: `${url}backup/download`,
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

    /*    let downloadFormat = 'application/sql';
        axios
            .post(`${url}backup/download`, data, {responseType: 'arraybuffer'})
            .then(response => {
                if(onSuccess){
                    onSuccess();
                }
                //Create a Blob from the PDF Stream
                const file = new Blob(
                    [response.data],
                    {type: downloadFormat});
                //Build a URL from the file
                // const fileURL = URL.createObjectURL(file);
                //Open the URL on new Window
                window.URL.createObjectURL(file);
                // window.open(fileURL);
            })
            .catch(error => {
                if(onError){
                    onError();
                }
            });*/
}

// export const downloadDataBase = (onSuccess , onError) => dispatch => {
//     axios
//         .get(`${url}backup/download`)
//         .then(response => {
//             dispatch({
//                 type: ACTION_TYPES.DOWNLOAD_DATABASE,
//                 payload: response.data
//             });
//             if(onSuccess){
//                 onSuccess();
//             }
//         })
//         .catch(error => {
//                 if(onError){
//                     onError();
//                 }
//             }
//
//         );
// };

export const uploadDataBase = (formData, config,  onSuccess, onError) => dispatch => {
    axios
        .post(`${url}backup/upload`, formData, config)
        .then(response => {
            console.log(response);
            dispatch({
                type: ACTION_TYPES.UPLOAD_DATA_BASE,
                payload: response.data
            });

            toast.success("Database was uploaded successfully!");
            if(onSuccess){
                onSuccess();
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                    toast.error("Database not was uploaded successfully!");
                }
            }

        );
};
