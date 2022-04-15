const xButtons = document.querySelectorAll(".button-x");

const yLimitationLabel = document.querySelector("#y-limitation-label");
const rLimitationLabel = document.querySelector("#r-limitation-label");

const yInput = document.querySelector("#CoordinateY");
const rInput = document.querySelector("#CoordinateR");

const targetDot = document.querySelector("#target-dot");
const graph = document.querySelector(".graph");

const rightPartOfTheContent = document.querySelector(".right-part-of-the-content");




/*
graph
 */
graph.onclick = function(event){
    if (!isRValueCorrect()){
        playRHintAnimation();
        return;
    }

    const target = this.getBoundingClientRect();
    let xCoordinate = event.clientX - target.left;
    const yCoordinate = event.clientY - target.top;
    let width = Number(graph.getAttribute("width"));
    let height = Number(graph.getAttribute("height"));
    const pixelsInOneRadiusX = (width + 3) / 2.5;
    const pixelsInOneRadiusY = (height + 3) / 2.5;
    const numberOfRadiusInX = (xCoordinate - width/2) / pixelsInOneRadiusX;
    const numberOfRadiusInY = - (yCoordinate - height/2) / pixelsInOneRadiusY;

    yInput.value = (numberOfRadiusInY * getR()).toFixed(4);
    const xValue = Number((numberOfRadiusInX*getR()).toFixed(4));

    xCoordinate = ( width / 2 )+ pixelsInOneRadiusX * (xValue / getR());
    moveTargetDotByRealCoordinates(xCoordinate, yCoordinate);
    sendHTTPAimRequest(xValue, yInput.value, getR());

}


function moveTargetDotByRealCoordinates(realX, realY){
    if (realX === undefined || realX.length === 0 || isNaN(realX) || realY === undefined || getChosenX === undefined) {
        targetDot.setAttribute('r', '0');
        return;
    }
    targetDot.setAttribute('r', '4');
    targetDot.setAttribute('cx', realX.toString());
    targetDot.setAttribute('cy', realY.toString());
}

function moveTargetByCoordinates(x, y, r) {
    let width = Number(graph.getAttribute("width"));
    let height = Number(graph.getAttribute("height"));
    const pixelsInOneRadiusX = (width + 3) / 2.5;
    const pixelsInOneRadiusY = (height + 3) / 2.5;
    let xCoordinate = width / 2 + pixelsInOneRadiusX * (x / r);
    let yCoordinate = height / 2 - pixelsInOneRadiusY * (y / r);

    moveTargetDotByRealCoordinates(xCoordinate, yCoordinate)
}

function moveTargetDot() {
    let chosenX = getChosenX();
    let chosenY = getY();
    let chosenR = getR();
    moveTargetByCoordinates(chosenX, chosenY, chosenR);
}

/*
Y
 */
function yHadBeenEntered() {
    if (!isYValueCorrect()){
        playYHintAnimation();
    }
    moveTargetDot();
}


function isYValueCorrect() {
    const number = (Number(yInput.value))
    if (yInput.value.length === 0) {
        return false;
    } else if (isNaN(number)) {
        return false;
    }  else return !(number >= 5 || number <= -5);
}

function isYTooLong() {
    let value = yInput.value;
    return value.length > 18;
}

function getY() {
    return yInput.value;
}

function playYHintAnimation() {
    yLimitationLabel.title = "not_transparent";
    setTimeout(() => yLimitationLabel.title = "transparent", 6000);
}

function playYTooLong() {
    let number =  yInput.value;
    yInput.value = number.substring(0, 17);
    sendButtonPress();
}

/*
R
 */
function rHadBeenEntered() {
    if (!isRValueCorrect()){
        playRHintAnimation();
    }
    moveTargetDot();
}


function isRValueCorrect() {
    const number = (Number(rInput.value))
    if (rInput.value.length === 0) {
        return false;
    } else if (isNaN(number)) {
        return false;
    }  else return !(number >= 4 || number <= 1);
}

function isRTooLong() {
    return rInput.value.length > 18;
}

function getR() {
    return rInput.value;
}

function playRHintAnimation() {
    rLimitationLabel.title = "not_transparent";
    setTimeout(() => rLimitationLabel.title = "transparent", 6000);
}

function playRTooLong() {
    let number =  rInput.value;
    rInput.value = number.substring(0, 17);
    sendButtonPress();
}


/*
X
 */
function isXSelected() {

    return !(getChosenX() === undefined);
}

function XButtonIsClicked(number) {
    deleteClassButtonActive();
    for (let button of xButtons) {
        if (Number(button.value) === number) {
            button.classList.add("button-y-active");
        }
    }
    moveTargetDot();
}


function getChosenX() {
    for (let button of xButtons) {
        if (button.classList.contains("button-y-active")) {
            return button.value;
        }
    }
}

function playXSectionAnimation() {
    for (let button of xButtons) {
        button.classList.add("button-y-highlighted");
        setTimeout(() => button.classList.remove("button-y-highlighted"), 2500);
    }
}

function deleteClassButtonActive() {
    for (let button of xButtons) {
        button.classList.remove("button-y-active")
    }
}



/*
buttons
*/
function sendButtonPress() {
    if (isYTooLong()){
        playYTooLong();
        return;
    }
    if (isRTooLong()){
        playRTooLong();
        return;
    }
    if (isYValueCorrect() && isRValueCorrect() && isXSelected()) {
       sendHTTPAimRequest(getChosenX(), getY(), getR());
    } else {
        if (xValue === null) playXSectionAnimation();
        if (!isYValueCorrect()) playYHintAnimation();
        if (!isRValueCorrect()) playRHintAnimation();
    }
}

function clearButtonPress() {
    clearResultTable();
    deleteClassButtonActive();
    yInput.value = "";
    rInput.value = "";
}

/*
result table
 */
function clearResultTable() {
    sendHTTPCleanRequest();
}

/*
http request
 */

function sendHTTPAimRequest(x, y, r){
    const requestURL = "/WEB-LAB2-1.0-SNAPSHOT/controller";
    const httpRequest = new XMLHttpRequest();

    httpRequest.open("POST", requestURL);

    httpRequest.setRequestHeader("Content-Type", "application/json" );
    httpRequest.responseType = "text";

     httpRequest.onload = () => {
         rightPartOfTheContent.innerHTML = httpRequest.responseText;
     };

    const body = {
        requestType: "aim request",
        xValue: x,
        yValue: y,
        rValue: r
    };

    httpRequest.send(JSON.stringify(body));
}

function sendHTTPCleanRequest(){
    const requestURL = "/WEB-LAB2-1.0-SNAPSHOT/controller";
    const httpRequest = new XMLHttpRequest();

    httpRequest.open("POST", requestURL);

    httpRequest.setRequestHeader("Content-Type", "application/json" );
    httpRequest.setRequestHeader("charset", "UTF-8" );

    httpRequest.onload = () => {
        rightPartOfTheContent.innerHTML = httpRequest.responseText
    }

    const body = {
        requestType: "clear table"
    };

    httpRequest.send(JSON.stringify(body));
}