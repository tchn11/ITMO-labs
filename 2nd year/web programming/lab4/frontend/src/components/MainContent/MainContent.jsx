import React from "react";
import CSSModules from 'react-css-modules';
import styles from './MainContent.module.css';
import sharedStyles from './main.module.css';
import GraphSection from './GraphSection/GraphSection';
import TableSectionContainer from './TableSection/TableSectionContainer';
import FormSection from "./FormSection/FormSection";

const MainContent = (props) => {
    return (
        <main styleName="main-container">
            <h1 className="visually-hidden">Основная страница</h1>
            <div styleName="main-container__item main-container__item_left-column column-container">
                <GraphSection />
                <FormSection />
            </div>
            <TableSectionContainer />
        </main>
    );
}

export default CSSModules(MainContent, { ...styles, ...sharedStyles }, { allowMultiple: true, handleNotFoundStyleName: 'ignore' });