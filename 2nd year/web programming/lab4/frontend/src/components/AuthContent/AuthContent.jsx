import React from 'react';
import CSSModules from 'react-css-modules';
import styles from './AuthContent.module.css';
import AuthSection from './AuthSection/AuthSection';

const AuthContent = (props) => {
    return (
        <main styleName="main-container">
            <h1 className="visually-hidden">Стартовая страница</h1>
            <AuthSection />
        </main>
    );
}

export default CSSModules(AuthContent, styles, { allowMultiple: true, handleNotFoundStyleName: 'ignore' });
