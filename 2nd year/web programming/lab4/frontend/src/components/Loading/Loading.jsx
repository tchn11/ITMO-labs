import React from "react";
import CSSModules from "react-css-modules";
import styles from "./Loading.module.css";
import loading from "../../assets/loading.svg";

const Loading = (props) => {
    return(
        <div styleName="loading-container">
            <img src={loading} alt="Загрузка"/>
        </div>
    );
}

export default CSSModules(Loading, styles, { allowMultiple: true, handleNotFoundStyleName: 'ignore' });