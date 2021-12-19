import React from 'react';
import CSSModules from 'react-css-modules';
import styles from './FromSection.module.css';
import sharedStyles from '../main.module.css';
import FormContainer from './Form/FormContainer';

const FormSection = (props) => {
    return (
        <section styleName="column-container__item section" className="content-section">
            <h2 className="theme section-header">
                <span className="section-header__text">
                  Данные
                </span>
            </h2>
            <FormContainer />
        </section>
    );
}

export default CSSModules(FormSection, { ...styles, ...sharedStyles }, { allowMultiple: true, handleNotFoundStyleName: 'ignore' });
