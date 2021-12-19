import React from "react";

const Entry = (props) => {
    return(
        <tr>
            <td>{props.x}</td>
            <td>{props.y}</td>
            <td>{props.r}</td>
            <td>{props.result ? "Попал" : "Не попал"}</td>
        </tr>
    );
}

export default Entry;
