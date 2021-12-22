import entryAPI from "../../api/entryAPI";
import { logout } from "./auth";
import {setEntries, addEntry} from "./table";

const CHANGE_R = 'web-lab4/values/SELECT_R';
const CHANGE_X = 'web-lab4/values/SELECT_X';
const CHANGE_Y = 'web-lab4/values/SELECT_Y';
const CLEAR_CURRENT = 'web-lab4/values/CLEAR_CURRENT';

const initialState = {
    rMin: -3,
    rMax: 5,
    rCurrent: undefined,
    xMin: -3,
    xMax: 5,
    xCurrent: undefined,
    yMin: -5,
    yMax: 3,
    yCurrent: undefined
};

export default function valueReducer(state = initialState, action = {}) {
    switch (action.type){
        case CHANGE_R:
            return Object.assign(
                {},
                state,
                {
                    rCurrent: action.value
                }
            );
        case CHANGE_X:
            return Object.assign(
                {},
                state,
                {
                    xCurrent: action.value
                }
            );
        case CHANGE_Y:
            return Object.assign(
                {},
                state,
                {
                    yCurrent: action.value
                }
            );
        case CLEAR_CURRENT:
            return Object.assign(
                {},
                state,
                {
                    rCurrent: undefined,
                    xCurrent: undefined,
                    yCurrent: undefined
                }
            );
        default:
            return state;
    }
}

export function changeR(value){
    return {type: CHANGE_R, value};
}

export function changeX(value){
    return {type: CHANGE_X, value};
}

export function changeY(value) {
    return { type: CHANGE_Y, value };
}

export function clearCurrent() {
    return { type: CLEAR_CURRENT };
}

export const checkEntry = () => (dispatch, getState) => {
    entryAPI.checkEntry(
        getState().values.xCurrent,
        getState().values.yCurrent,
        getState().values.rCurrent,
        JSON.parse(localStorage.getItem('userWl4')).jwt)
        .then(response => {
            if (response.status === 200) {
                dispatch(addEntry(response.data));
            } else {
                alert(`Непредвиденный ответ ${response.status} от сервера!`);
            }
        })
        .catch(error => {
            if (error.response.status === 401) {
                dispatch(logout());
            } else {
                alert(`Непредвиденный ответ ${error.response.status} от сервера!`);
            }
        });
}

export const clearEntries = () => (dispatch) => {
    entryAPI.clearEntries(JSON.parse(localStorage.getItem('userWl4')).jwt)
        .then(response => {
            if (response.status === 200) {
                dispatch(setEntries([]));
            } else {
                alert(`Непредвиденный ответ ${response.status} от сервера!`);
            }
        })
        .catch(error => {
            if (error.response.status === 401) {
                dispatch(logout());
            } else {
                alert(`Непредвиденный ответ ${error.response.status} от сервера!`);
            }
        });
}
