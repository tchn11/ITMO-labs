import React from 'react';
import CSSModules from 'react-css-modules';
import styles from './InfoMessage.module.css';

const InfoMessage = (props) => {
    return (
        <p styleName="values-form__info">
            {props.message}
        </p>
    );
}

export default CSSModules(InfoMessage, styles, { allowMultiple: true, handleNotFoundStyleName: 'ignore' });
