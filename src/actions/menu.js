import axios from "axios";
import { url } from "api";
import * as ACTION_TYPES from "./types";


export const fetchAll = (onSuccess , onError) => dispatch => {
    axios
      .get(`${url}menu`)
      .then(response => {
        dispatch({
          type: ACTION_TYPES.MENU_FETCH_ALL,
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
