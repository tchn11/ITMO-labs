import authAPI from "../../api/authAPI";
import { getEntries, clearEntries } from './table'
import {clearCurrent} from "./values";

const SET_LOADING = 'web-lab4/auth/SET_LOADING';
const SET_SERVER_ERROR_MESSAGE = 'web-lab4/auth/SET_SERVER_ERROR_MESSAGE';
const SET_LOGGED_USER = 'web-lab4/auth/SET_LOGGED_USER';

const initialState = {
    isLoading: false,
    loggedUser: '',
    serverErrorMessage: ''
};

export default function authReducer(state = initialState, action = {}){
    switch (action.type){
        case SET_LOADING:
            return Object.assign(
                {},
                state,
                {
                    isLoading: action.value
                }
            );
        case SET_SERVER_ERROR_MESSAGE:
            return Object.assign(
                {},
                state,
                {
                    serverErrorMessage: action.value
                }
            );
        case SET_LOGGED_USER:
            return Object.assign(
                {},
                state,
                {
                    loggedUser: action.value
                }
            );
        default:
            return state;
    }
}

export function setLoading(value) {
    return {type: SET_LOADING, value};
}

export function setServerErrorMessage(value) {
    return {type: SET_SERVER_ERROR_MESSAGE, value};
}

export function setLoggedUser(value) {
    return {type: SET_LOGGED_USER, value};
}

export const initializeAuth = () => (dispatch) => {
    let currentUser = JSON.parse(localStorage.getItem('userWl4'));
    if (currentUser !== null){
        dispatch(setLoggedUser(currentUser.username));
        dispatch(getEntries());
    }
}

export const login = (username, password) => (dispatch) =>{
    dispatch(setLoading(true));
    authAPI.login(username, password)
        .then(response => {
            if (response.status === 200) {
                localStorage.setItem('userWl4', JSON.stringify(response.data));
                dispatch(setLoggedUser(response.data.username));
                dispatch(getEntries());
            } else {
                alert(`Непредвиденный ответ ${response.status} от сервера!`);
            }
            dispatch(setLoading(false));
        })
        .catch(error => {
           if (error.response.status === 400) {
               dispatch(setServerErrorMessage(error.response.data));
           } else {
               alert(`Непредвиденный ответ ${error.response.status} от сервера`);
           }
           dispatch(setLoading(false));
        });
}

export const register = (username, password) => (dispatch) => {
    dispatch(setLoading(true));
    authAPI.register(username, password)
        .then(response => {
            if (response.status === 200){
                dispatch(login(username, password));
            } else {
                alert(`Непредвиденный ответ ${response.status} от сервера`);
            }
            dispatch(setLoading(false));
        })
        .catch(error => {
           if (error.response.status === 400) {
               dispatch(setServerErrorMessage(error.response.data))
           } else {
               alert(`Непредвиденный ответ ${error.status} от сервера`);
           }
           dispatch(setLoading(false));
        });
}

export  const logout = () => (dispatch) =>{
    localStorage.removeItem("userWl4");
    dispatch(setLoggedUser(''));
    dispatch(clearCurrent());
    dispatch(clearEntries());
}
