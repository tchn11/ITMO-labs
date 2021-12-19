import React from 'react';
import CSSModules from 'react-css-modules';
import styles from './AuthSection.module.css';
import AuthFormContainer from './AuthForm/AuthFormContainer';

const AuthSection = (props) => {
    return (
        <section styleName="section" className="content-section">
            <h2 className="theme section-header">
        <span className="section-header__text">
          Авторизация
        </span>
            </h2>
            <AuthFormContainer />
        </section>
    );
}

export default CSSModules(AuthSection, styles, { allowMultiple: true, handleNotFoundStyleName: 'ignore' });
