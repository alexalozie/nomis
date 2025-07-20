import axios from "axios";
import { url } from "../api";
import * as ACTION_TYPES from "./types";
import {toast} from 'react-toastify';



export const fetchDatimReport = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}data-elements`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.DATIM_LIST,
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

export const fetchAvailableReport = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}data-reports/available-files`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.DATIM_REPORT,
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

export const uploadDatimFile = (formData, config,  onSuccess, onError) => dispatch => {
    axios
        .post(`${url}data-elements/import/data-element`, formData, config)
        .then(response => {
            console.log(response);
            dispatch({
                type: ACTION_TYPES.IMPORT_FILE,
                payload: response.data
            });

            toast.success("Data imported successfully!");
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


export const createDataElement = (data, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}data-elements`, data)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("data element created successfully!")
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};

export const updateDataElement = (id, data, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}data-elements/${id}`, data)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("data element updated successfully!")
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};

export const deleteDataElemet = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}data-elements/${id}`)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("data element deleted successfully!")
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};

export const generateDatimReport = (data, onSuccess , onError) => dispatch => {
    axios
        .get(`${url}data-reports/generate`, data)
        .then(response => {
            if(onSuccess){
                onSuccess();
                toast.success("DATIM Report Generated successfully!")
            }
        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};

// export const downloadDatim = (data, onSuccess , onError) => dispatch => {
//     axios
//         .get(`${url}data-reports`, data)
//         .then(response => {
//             if(onSuccess){
//                 onSuccess();
//                 toast.success("Flat downloaded successfully!")
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

export const downloadDatim = (onSuccess, onError) => dispatch => {
    axios
        .get(`${url}data-reports`, {responseType: 'arraybuffer'})
        .then(response => {
            if(onSuccess){
                onSuccess();
            }
            //Create a Blob from the PDF Stream
            const file = new Blob(
                [response.data],
                {type: "csv"});
            //Build a URL from the file
            const fileURL = URL.createObjectURL(file);
            //Open the URL on new Window
            window.open(fileURL);
        })
        .catch(error => {
            if(onError){
                onError();
            }
        });
}
