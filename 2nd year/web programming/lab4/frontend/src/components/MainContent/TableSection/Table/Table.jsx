import React from 'react';
import CSSModules from 'react-css-modules';
import styles from './Table.module.css';
import Entry from './Entry';

const Table = (props) => {
    return (
        <div styleName="table-container">
            <table styleName="result-table">
                <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Результат</th>
                </tr>
                {props.entries.map(entry => <Entry x={entry.x} y={entry.y} r={entry.r} result={entry.result} />)}
            </table>
        </div>
    );
}

export default CSSModules(Table, styles, { allowMultiple: true, handleNotFoundStyleName: 'ignore' });
