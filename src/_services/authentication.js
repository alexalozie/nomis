import { BehaviorSubject } from 'rxjs';
import {url as baseUrl, url} from "../api";
import { handleResponse } from '../_helpers';
import store from '../store';
import * as ACTION_TYPES from "./../actions/types";
import jwt_decode from "jwt-decode";
import _ from 'lodash';
import axios from "axios";

 //import SwitchProject from './switchmodal'

const { dispatch } = store;
const currentUserSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser')));

//const currentUserPermissions = localStorage.getItem('currentUser_Permission') ? new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser_Permission'))) : null;
let  cboProjects = {}

export const authentication = {
    login,
    logout,
    currentUser: currentUserSubject.asObservable(),
    get currentUserValue () { return currentUserSubject.value },
    getCurrentUserRole,
    getCurrentUser,
    userHasRole,
    fetchMe,
    currentCboProject
};

function login(username, password, remember, cboProjectId) {

    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password, remember, cboProjectId })
    };

    return fetch(`${url}authenticate`, requestOptions)
        .then(handleResponse)
        .then(user => {
            dispatch({
                type: ACTION_TYPES.AUTHENTICATION,
                payload: "Authenticated"
            });
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            localStorage.setItem('currentUser', JSON.stringify(user));
            localStorage.setItem('selectedProjectAtLogin', JSON.stringify(cboProjectId));
            currentUserSubject.next(user);
            fetchMe();
            return user;
        });
}

function logout(props) {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('currentUser_Permissions');
    localStorage.removeItem('currentUserCboProjectName');
    localStorage.removeItem('selectedProjectAtLogin');
    currentUserSubject.next(null);
    // axios.post(`${url}users/logOut`)
    //
    //     .then(response => {
    //
    //         console.log(response)
    //
    //
    //     })
    //     .catch((error) => {
    //
    //     });;
             // remove user from local storage to log user out
           
      
   
}

function getCurrentUserRole() {

    const currentUserPermissions = localStorage.getItem('currentUser_Permission') != null ? JSON.parse(localStorage.getItem('currentUser_Permission')) : null;
    if(!currentUserPermissions){
        return [];
    }
    // fetch all the permissions of the logged in user
    const permissions = currentUserPermissions;
    if(!permissions || permissions.length < 1){
        return [];
    }
    return permissions;
}

function userHasRole(role){
    const userRoles = getCurrentUserRole();
    if(role && role.length > 0 && _.intersection(role, userRoles).length === 0){
        return false;
    }
    return true;
}

function userHasSuperAdmin(){
    const userRoles = getCurrentUserRole();
    if(_.intersection("System Administrator", userRoles).length !== 0){
        return false;
    }
    return true;
}

function currentCboProject(){

}

function getCurrentUser(){
    const user = currentUserSubject.value;
    if(!user || !user.id_token){
        return [];
    }

    const token = user.id_token;
    const decoded = jwt_decode(token);
    console.log(decoded);
    console.log(currentUserSubject)
    return decoded;
}

async function fetchMe(){

    axios
        .get(`${baseUrl}account`)
        .then((response) => {
            localStorage.removeItem('currentUserCboProjectName');
            localStorage.setItem('currentUser_Permission', JSON.stringify(response.data.permissions));
            localStorage.setItem('currentUserCboProjectName', JSON.stringify(response.data));
           
            dispatch({
                type: ACTION_TYPES.FETCH_ME,
                payload: response.data,
            });
           
            return response.data.permissions;
        })
        .catch((error) => {
            dispatch({
                type: ACTION_TYPES.FETCH_ME,
                payload: null,
            });
            return null;
        });
}