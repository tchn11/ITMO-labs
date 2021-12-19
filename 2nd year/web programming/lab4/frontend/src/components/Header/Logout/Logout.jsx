import React from 'react';
import CSSModules from 'react-css-modules';
import styles from './Logout.module.css';
import logoutIcon from '../../../assets/logout.png';

const Logout = (props) => {
    return (
        <a styleName="logout-link" href="/" onClick={(e) => props.action(e)}>
            <img src={logoutIcon} alt="Выйти из аккаунта" height="30" />
        </a>
    );
}

export default CSSModules(Logout, styles, { allowMultiple: true, handleNotFoundStyleName: 'ignore' });
