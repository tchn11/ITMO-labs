import entryAPI from "../../api/entryAPI";
import {setLoading, logout} from './auth';

const SET_ENTRIES = 'web-lab4/table/SET_ENTRIES';
const CLEAR_ENTRIES = 'web-lab4/table/CLEAR_ENTRIES';
const ADD_ENTRY = 'web-lab4/table/ADD_ENTRY';

const initialState = {
    entries: []
};

export default function tableReducer(state = initialState, action = {}) {
    switch (action.type) {
        case SET_ENTRIES:
            return Object.assign(
                {},
                state,
                {
                    entries: action.value
                }
            );
        case CLEAR_ENTRIES:
            return Object.assign(
                {},
                state,
                {
                    entries: []
                }
            );
        case ADD_ENTRY:
            return Object.assign(
                {},
                state,
                {
                    entries: [...state.entries, action.value]
                }
            );
        default:
            return state;
    }
}

export function setEntries(value){
    return {type: SET_ENTRIES, value};
}

export function clearEntries(){
    return {type: CLEAR_ENTRIES};
}

export function addEntry(value){
    return {type: ADD_ENTRY, value};
}
export const getEntries = () => (dispatch) => {
    dispatch(setLoading(true));
    entryAPI.getEntries(JSON.parse(localStorage.getItem('userWl4')).jwt)
        .then(response => {
            if (response.status === 200) {
                dispatch(setEntries(response.data));
            } else {
                alert(`Непредвиденный ответ ${response.status} от сервера!`);
            }
            dispatch(setLoading(false));
        })
        .catch(error => {
            if (error.status === 401) {
                dispatch(logout());
            } else {
                alert(`Непредвиденный ответ ${error.response.status} от сервера!`);
            }
            dispatch(setLoading(false));
        });
}