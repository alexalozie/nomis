import axios from "axios";
import { url } from "api";
import * as ACTION_TYPES from "./types";


export const fetchAll = (onSuccess , onError) => dispatch => {
    axios
      .get(`${url}global-variables`)
      .then(response => {
        dispatch({
          type: ACTION_TYPES.GLOBAL_VARIABLE_FETCH_ALL,
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

export const newGlobalVariable = (formData, onSuccess , onError) => dispatch => {
    axios
        .post(`${url}global-variables`, formData)
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

export const updateGlobalVariable = (id, formData, onSuccess , onError) => dispatch => {
    axios
        .put(`${url}global-variables/${id}`, formData)
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

export const deleteGlobalVariable = (id, onSuccess , onError) => dispatch => {
    axios
        .delete(`${url}global-variables/${id}`)
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