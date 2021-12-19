import { connect } from 'react-redux';
import AuthForm from './AuthForm'
import {
    login,
    register
} from '../../../../redux/modules/auth';

function mapStateToProps(state) {
    return {
        serverErrorMessage: state.auth.serverErrorMessage
    };
}

function mapDispatchToProps(dispatch) {
    return {
        login: (username, password) => dispatch(login(username, password)),
        register: (username, password) => dispatch(register(username, password))
    };
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(AuthForm);
