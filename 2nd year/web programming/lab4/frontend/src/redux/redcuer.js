import { combineReducers } from "redux";
import valuesReducer from './modules/values';
import tableReducer from './modules/table';
import authReducer from './modules/auth';

const reducer = combineReducers({
    values: valuesReducer,
    table: tableReducer,
    auth: authReducer
});

export default reducer;
