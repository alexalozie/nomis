import axios from "axios";
import { url } from "../api";
import {toast} from 'react-toastify';



export const requestPassword = (data, onSuccess , onError) => dispatch => {
    console.log(data.emailAddress)
    axios
        .post(`${url}users/forgot-password?email=`+data.emailAddress)
        .then(response => {
            if (onSuccess) {
                onSuccess();
            }
            toast.success(response.data)

        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};

export const resetPassword = (data, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}users/reset-password?password=`+data.password+`&token=`+data.token)
        .then(response => {
            if (onSuccess) {
                onSuccess();
            }
                toast.success(response.data)
        })
        .catch(error => {
                if(onError){
                    onError();
                }
            }

        );
};

