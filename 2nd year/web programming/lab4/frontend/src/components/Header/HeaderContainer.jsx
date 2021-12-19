import { connect } from 'react-redux';
import Header from './Header';
import { logout } from '../../redux/modules/auth';

function mapStateToProps(state){
    return {
        loggedUser: state.auth.loggedUser
    };
}

function mapDispatchToProps(dispatch){
    return{
        logout: () => dispatch(logout())
    };
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Header);