import CSSModules from 'react-css-modules';
import styles from './TableSection.module.css';
import sharedStyles from '../main.module.css';
import Table from './Table/Table';

const TableSection = (props) => {
    return (
        <section styleName="main-container__item main-container__item_table section" className="content-section">
            <h2 className="theme section-header">
        <span className="section-header__text">
          Таблица
        </span>
            </h2>
            <Table entries={props.entries} />
        </section>
    );
}

export default CSSModules(TableSection, { ...styles, ...sharedStyles }, { allowMultiple: true, handleNotFoundStyleName: 'ignore' });
