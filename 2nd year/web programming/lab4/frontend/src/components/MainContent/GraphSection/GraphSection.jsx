import React from 'react';
import CSSModules from 'react-css-modules';
import styles from './GraphSection.module.css';
import sharedStyles from '../main.module.css';
import GraphContainer from './Graph/GraphContainer';

const GraphSection = (props) => {
    return (
        <section styleName="column-container__item section" className="content-section">
            <h2 className="theme section-header">
        <span className="section-header__text">
          График
        </span>
            </h2>
            <GraphContainer />
        </section>
    );
}

export default CSSModules(GraphSection, { ...styles, ...sharedStyles }, { allowMultiple: true, handleNotFoundStyleName: 'ignore' });
