import axios from "axios";
import { url } from "./../api";
import * as ACTION_TYPES from "./types";


export const fetchAllHouseHold = (onSuccess , onError) => dispatch => {
    axios
        .get(`${url}households`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_HOUSE_HOLD,
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

export const fetchAllHouseHoldServiceHistory = (id, onSuccess , onError) => dispatch => {
    axios
        .get(`${url}households/${id}/encounters`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_HOUSEHOLD_SERVICE_HISTORY,
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

export const fetchHouseHoldById = (id, onSuccess , onError) => dispatch => {
    axios
        .get(`${url}households/${id}`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_HOUSE_HOLD_BY_ID,
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

export const saveHouseHold = (body, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}households`, body)
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

export const deleteHousehold = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}households/${id}`)
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

export const fetchAllHouseHoldMembersByHouseholdId = (id, onSuccess , onError) => dispatch => {
    axios
        .get(`${url}households/${id}/householdMembers`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.FETCH_HOUSEHOLD_MEMBERS_BY_HOUSEHOLD_ID,
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

export const getServiceDueDate = (id, onSuccess , onError) => dispatch => {
    axios
        .get(`${url}households/${id}/getServiceDueDate`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.GET_HOUSEHOLD_SERVICE_DUE_DATE,
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

export const HouseHoldDueForGraduation = (householdId, onSuccess , onError) => dispatch => {
    axios
        .get(`${url}households/${householdId}/aboutToGraduate`)
        .then(response => {
            dispatch({
                type: ACTION_TYPES.GET_HOUSEHOLD_DUE_FOR_GRADUATION,
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