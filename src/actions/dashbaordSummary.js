import axios from 'axios';
import {url} from '../api';
import * as ACTION_TYPES from './types';
import axios from "axios";
import { url } from "../api";
import * as ACTION_TYPES from "./types";


export const fetchSummary = (id, onSuccess , onError) => dispatch => {
    axios
        .get(`${url} dashboard/dashboard-summary${id}`)
        .then(response => {
            console.log(response.data)
            dispatch({
                type: ACTION_TYPES.FETCH_DASHBOARD,
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