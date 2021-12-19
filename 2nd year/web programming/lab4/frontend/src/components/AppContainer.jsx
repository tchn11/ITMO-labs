import { connect } from 'react-redux';
import App from './App'
import {initializeAuth} from '../redux/modules/auth';

const mapStateToProps = (state) => ({
    loggedUser: state.auth.loggedUser,
    isLoading: state.auth.isLoading
});

function mapDispatchToProps(dispatch) {
    return {
        initializeAuth: () => dispatch(initializeAuth())
    };
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(App);
