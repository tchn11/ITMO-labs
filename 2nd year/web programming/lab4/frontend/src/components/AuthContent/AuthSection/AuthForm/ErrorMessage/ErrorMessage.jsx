import React from 'react';
import CSSModules from 'react-css-modules';
import styles from './ErrorMessage.module.css';

const ErrorMessage = (props) => {
    return (
        props.message &&
        <p styleName="error-message">
            {props.message}
        </p>
    );
}

export default CSSModules(ErrorMessage, styles, { allowMultiple: true, handleNotFoundStyleName: 'ignore' });
