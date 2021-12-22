import React, { useRef, useEffect } from 'react';
import CSSModules from 'react-css-modules';
import styles from './Graph.module.css';
import GraphSvg from './GraphSvg';
import Canvas from './Canvas/Canvas';
import {wrapMapToPropsConstant} from "react-redux/lib/connect/wrapMapToProps";

const Graph = (props) => {
    const resize = 68;

    const pointsCanvasRef = useRef(null);
    const currentCanvasRef = useRef(null);

    function isIn(x, y, r) {
        return (x >= 0 && y >= 0 && x <= -y*2 + parseFloat(r)) ||
            (x <= 0 && y <= 0 && x >= -r/2 && y >= -r) ||
            (x >= 0 && y <= 0 && x * x + y * y <= r * r);
    }

    const loadPrevPoints = (canvas, canvasCtx) => {
        if (props.rCurrent < props.rMin || props.rCurrent > props.rMax)
            return;
        for (let entry of props.entries) {
            canvasCtx.fillStyle = isIn(entry.x, entry.y, props.rCurrent) ? 'green' : 'red';
            canvasCtx.beginPath();
            canvasCtx.arc(
                entry.x / props.rCurrent * resize + canvas.width / 2,
                -entry.y / props.rCurrent * resize + canvas.height / 2,
                2, 0, 2 * Math.PI);
            canvasCtx.fill();
        }
    }

    const clearCanvas = (canvas, canvasCtx) => {
        canvasCtx.clearRect(0, 0, canvas.width, canvas.height);
    }

    let isNumeric = num => {
        return !isNaN(parseFloat(num)) && isFinite(num);
    }

    const drawCurrent = (canvas, canvasCtx) => {
        clearCanvas(canvas, canvasCtx);

        if (props.rCurrent < props.rMin || props.rCurrent > props.rMax || !isNumeric(props.rCurrent))
            return;

        const x = props.xCurrent * resize / props.rCurrent + canvas.width / 2;
        const y = -(props.yCurrent / props.rCurrent * resize - canvas.height / 2);

        if (x > canvas.width || x < 0 ||
            y > canvas.height || y < 0) {
            return;
        }

        canvasCtx.setLineDash([2, 2]);
        canvasCtx.fillStyle = isIn(props.xCurrent, props.yCurrent, props.rCurrent) ? 'green' : 'red';
        canvasCtx.beginPath();
        canvasCtx.moveTo(x, canvas.width / 2);
        canvasCtx.lineTo(x, y);
        canvasCtx.moveTo(canvas.height / 2, y);
        canvasCtx.lineTo(x, y);
        canvasCtx.stroke();
        canvasCtx.arc(x, y, 2, 0, 2 * Math.PI);
        canvasCtx.fill();
    }
    const handleClick = (canvasRef, event) => {
        const canvas = canvasRef.current;

        if (props.rCurrent < props.rMin || props.rCurrent > props.rMax)
            return;

        let canvasX = (event.nativeEvent.offsetX - canvas.width / 2) / resize * props.rCurrent;

        if (canvasX < props.xMin) {
            canvasX = props.xMin;
        } else if (canvasX > props.xMax) {
            canvasX = props.xMax;
        }

        let canvasY = (-event.nativeEvent.offsetY + canvas.height / 2) / resize * props.rCurrent;

        if (canvasY < props.yMin) {
            canvasY = props.yMin;
        } else if (canvasY > props.yMax) {
            canvasY = props.yMax;
        }

        props.changeX(canvasX.toString().substring(0, 7));
        props.changeY(canvasY.toString().substring(0, 7));

        if (canvasX > props.xMax || canvasX < props.xMin || !isNumeric(canvasX))
            return;
        if (canvasY > props.yMax || canvasY < props.yMin || !isNumeric(canvasY))
            return;
        if (props.rCurrent > props.rMax || props.rCurrent < props.rMin || !isNumeric(props.rCurrent))
            return;

        props.checkEntry();
    }

    useEffect(() => {
        const pointsCanvas = pointsCanvasRef.current;
        const pointsCanvasCtx = pointsCanvas.getContext('2d');
        clearCanvas(pointsCanvas, pointsCanvasCtx);

        const currentCanvas = currentCanvasRef.current;
        const currentCanvasCtx = currentCanvas.getContext('2d');
        clearCanvas(currentCanvas, currentCanvasCtx);

        loadPrevPoints(pointsCanvas, pointsCanvasCtx);
        drawCurrent(currentCanvas, currentCanvasCtx);
    });

    return(
        <div styleName="graph-container">
            <GraphSvg rValue={props.rCurrent} />
            <Canvas canvasRef={pointsCanvasRef} alt="Интерактивная область графика (предыдущие точки)" />
            <Canvas canvasRef={currentCanvasRef} alt="Интерактивная область графика (текущая точка)" handleClick={handleClick} />
        </div>
    );
}

export default CSSModules(Graph, styles, { allowMultiple: true, handleNotFoundStyleName: 'ignore' });
