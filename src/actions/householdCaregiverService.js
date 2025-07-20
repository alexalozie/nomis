import axios from "axios";
import { url } from "./../api";
import * as ACTION_TYPES from "./types";



export const createProvideService = (formData, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}encounters`, formData)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.HOUSE_HOLD_MEMBER_CREATE_SERVICE,
                payload: response.data
            });
            if(onSuccess){
                onSuccess();
            }
        })
        .catch(error => {
            dispatch({
                type: ACTION_TYPES.ERROR_HOUSE_HOLD_MEMBER_CREATE_SERVICE,
                payload: error
            });
                if(onError){
                    onError();
                }
            }

        );
};


export const deleteProvidedService = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}encounters/${id}`)
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
