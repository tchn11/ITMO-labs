<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Web programming lab #1</title>
    <style>
        /*селектор элемента*/
        html {
            background: #ebebeb;
            position: relative;
            color: #333;
            font-family: Courier, monospace;
            font-weight: normal;
        }


        h1, h2 {
            font-family: Courier, monospace;
            color: white;
            size: auto;
            margin: 0;
            padding: 3px;
        }

        .header {
            text-align: center;
            background-color: #464646;
            fill: rgb(240,195,55);
            color: white;
            margin-bottom: 20px;
        }


        .content {
            position: relative;

        }


        .left-part-of-the-content {
            float: left;
            width: auto;
            margin-right: 15px;
            margin-left: 5px;
        }

        .central-part-of-the-content {
            float: left;
            width: 210px;
            margin-left: 15px;
            margin-right: 15px;
            border: 2px solid black;
            border-radius: 10px;
            padding: 10px 10px 10px 10px;

        }

        .right-part-of-the-content {
            float: left;
            margin-top: 10px;
            width: auto;
            margin-left: 20px;
            border: 2px solid black;
            border-radius: 10px;
        }

        .axis {
            stroke: black;
        }

        .axis-pointer {
            stroke: black;
        }

        .text {
            font-size: 19px;
        }

        .figure {
            fill-opacity: 0.3;
        }

        #triangle {
            fill: rgb(240,195,55);
            stroke: rgb(240,195,55);
        }

        #triangle:hover {
            fill: rgba(240, 195, 55, 0.5);
        }

        #rectangle {
            fill: rgb(150,0,250);
            stroke: rgb(150,0,250);
        }

        #rectangle:hover {
            fill: rgba(150,0,250,0.5);
        }

        #quarterCircle {
            fill: black;
            stroke: black;
        }

        #quarterCircle:hover {
            fill: rgba(255, 255, 255, 0.5);
        }

        #target-dot {
            fill: rgba(245,86,0,0.53);
            fill: black;

            transition: 1s;
        }


        .section-header {
            text-align: center;
            display: block;
            font-size: 20px;
            margin-right: auto;
            margin-left: auto;
            font-weight: bold;
        }

        .input {
            font: inherit;
            display: block;
            margin-right: auto;
            margin-left: auto;
            font-size: 15px;
            margin-bottom: 5px;
            margin-top: 5px;
            width: 180px;

        }

        .limitation-label {
            display: block;
            text-align: center;
            margin-right: auto;
            margin-left: auto;
            transition: 1s;
            font-size: 15px;
            font-weight: bold;

        }

        /*селектор атрибутов*/
        .limitation-label[title = "transparent"] {
            color: rgba(225, 10, 20, 0);
        }

        .limitation-label[title = "not_transparent"] {
            color: rgb(225, 10, 20);
        }

        .y-too-long-label  {
            display: block;
            text-align: center;
            margin-right: auto;
            margin-left: auto;
            transition: 1s;
            font-size: 15px;
            font-weight: bold;
        }

        /*селектор атрибутов*/
        .y-too-long-label[title = "transparent"] {
            color: rgba(225, 10, 20, 0);
        }

        .y-too-long-label[title = "not_transparent"] {
            color: rgb(225, 10, 20);
        }


        .r-table {
            font-weight: bold;
            font-size: 20px;
            position: relative;
        }

        .r-value-section {
            margin: auto;
        }

        /*селектор дочерних элементов*/
        td > div {
            float: left;
            height: 40px;
            position: relative;
            right: 0px;
        }

        .r-box-label {
            position: absolute;
            top: 22px;
            left: 13px;
            font-size: 18px;
        }

        .r-box-label-highlighted {
            color: rgb(225, 10, 20);
            font-weight: bold;
        }

        .r-radio {
            height: 19px;
            width: 19px;
            margin-left: 10px;
            margin-right: 10px;
            border: 2px;
        }


        .x-table {
            border-collapse: separate;
            border-spacing: 0 10px;

        }

        div{

        }
        .x-table-header {
            position: relative;
            right: 6px;
            alignment: center;
            font-size: 20px;
            font-weight: bold;
        }

        .x-table-data {
            float: left;
            margin-left: 9px;
            margin-right: 9px;

        }

        .button-x {
            width: 50px;
            height: 45px;
            border-width: 7px;
            border-radius: 15px;
            font-size: 14px;
            transition: 0.45s;
            outline: 0;
        }

        .button-x:hover, .button-y-highlighted {
            border-color: rgb(225, 10, 20);
        }

        .button-y-active {
            background-color: rgb(225, 10, 20);
        }


        .button {
            font-family: inherit;
            font-weight: bold;
            text-align: center;
            margin-top: 5px;
            border-radius: 5px;
            width: auto;
            height: 35px;
            font-size: 15px;
            transition: 0.45s;
        }

        /*каскадирование*/
        #send_button {
            background-color: rgb(40, 150, 90);
            border: 2px solid rgb(20, 130, 70);
            margin-left: 8px;
        }

        #send_button:hover {
            background-color: rgba(40, 150, 90, 0.6);

        }

        #clear_button {
            background-color: rgb(225, 10, 20);
            border: 2px solid rgb(170, 10, 20);
        }

        #clear_button:hover {
            background-color: rgba(255, 0, 4, 0.6);
        }


        .result-table {
            font-size: 20px;
            border-collapse: separate;
            border-spacing: 0 0;

        }

        #result_table_result_sell {
            border-right: 0;
        }

        /*селектор потомков*/
        .result-table-header th {
            padding: 10px 15px 10px 15px;
            border-right: 2px solid black;
        }

        .result-table-td {
            text-align: center;
            border-right: 2px solid black;
            border-top: 2px solid black;
        }



        .last-element-of-row {
            border-right: 0;
        }

        footer {
            position: fixed;
            left: 0;
            bottom: 0;
            width: 100%;
            background-color: #464646;
            color: white;
        }

        .bottom-link-with-image {
            text-align: center;
        }

        .bear-image {
            padding-top: 5px;
            padding-bottom: 5px;
            width: 65px;
        }

        .copy-right {
            position: absolute;
            bottom: 20px;
            right: 20px;
            margin: auto;
        }
    </style>
</head>


<body>
<div class="header">
    <h1>Девяткин Арсений P3215</h1>
    <h2>Вариант 15505</h2>
</div>


<div class="content">
    <div class="left-part-of-the-content">
        <svg class="graph" width="424" height="424">
            <!-- poligons --->
            <polygon class="figure" id = "rectangle"  points="41,212 212,212 212,41 41,41"></polygon>
            <polygon class="figure" id = "triangle"  points="41,212 212,383 212,212"></polygon>
            <path class="figure" id="quarterCircle" d="M 298 212 Q 298 126 212 126 L 212 212 Z" ></path>
            <!-- axis--->
            <line class="axis" id="x-axis" x1="0" x2="424" y1="212" y2="212"></line>
            <line class="axis" id="y-axis" x1="212" x2="212" y1="0" y2="424"></line>
            <polygon id="y-axis-pointer" points="212,0 218,15 206,15" stroke="black"></polygon>
            <polygon id="x-axis-pointer" points="424,212 409,218 409,206" stroke="black"></polygon>
            <!-- x-pointers-->
            <line class="axis-pointer" id="x-axis-r-pointer" x1="383" x2="383" y1="202" y2="222"></line>
            <line class="axis-pointer" id="x-axis-r/2-pointer" x1="298" x2="298" y1="202" y2="222"></line>
            <line class="axis-pointer" id="x-axis--r/2-pointer" x1="127" x2="127" y1="202" y2="222"></line>
            <line class="axis-pointer" id="x-axis--r-pointer" x1="41" x2="41" y1="202" y2="222"></line>
            <!-- y-pointers -->
            <line class="axis-pointer" id="y-axis--r-pointer" x1="202" x2="222" y1="383" y2="383"></line>
            <line class="axis-pointer" id="y-axis--r/2-pointer" x1="202" x2="222" y1="298" y2="298"></line>
            <line class="axis-pointer" id="y-axis-r/2-pointer" x1="202" x2="222" y1="127" y2="127"></line>
            <line class="axis-pointer" id="y-axis-r-pointer" x1="202" x2="222" y1="41" y2="41"></line>
            <!-- caption--->
            <text class="text" x="378" y="190">R</text>
            <text class="text" x="290" y="190">R/2</text>
            <text class="text" x="133" y="190">-R/2</text>
            <text class="text" x="30" y="190">-R</text>
            <text class="text" x="233" y="387">-R</text>
            <text class="text" x="238" y="300">-R/2</text>
            <text class="text" x="238" y="150">R/2</text>
            <text class="text" x="233" y="45">R</text>

            <!-- dot --->
            <circle id="target-dot" r="0" cx="212" cy="212"></circle>

        </svg>
    </div>


    <div class="central-part-of-the-content">
        <div class="y-value-section">
            <label class="section-header">Координата Y</label>
            <label class="limitation-label" id="y-limitation-label" title="transparent">y должен ∈ (-5,5)</label>
            <input class="input" oninput="yHadBeenEntered()" type="text" id="CoordinateY" placeholder="Значение y">
        </div>

        <div class="y-value-section">
            <label class="section-header">Значение R</label>
            <label class="limitation-label" id="r-limitation-label" title="transparent">r должен ∈ (1,4)</label>
            <input class="input" oninput="rHadBeenEntered()" type="text" id="CoordinateR" placeholder="Значение r">
        </div>



        <table class="x-table">
            <thead>
            <tr>
                <th class="x-table-header">Значение X</th>
            </tr>
            </thead>
            <tbody class="x-table-body">
            <tr class="x-table-row">
                <td class="x-table-data"><input class="button-x" type="button" onclick="XButtonIsClicked(-4)"
                                                id="x_button_-4" value="-4"></td>
                <td class="x-table-data"><input class="button-x" type="button" onclick="XButtonIsClicked(-3)"
                                                id="x_button_-3" value="-3"></td>
                <td class="x-table-data"><input class="button-x" type="button" onclick="XButtonIsClicked(-2)"
                                                id="x_button_-2" value="-2"></td>
            </tr>
            <tr class="x-table-row">
                <td class="x-table-data"><input class="button-x" type="button" onclick="XButtonIsClicked(-1)"
                                                id="x_button_-1" value="-1"></td>
                <td class="x-table-data"><input class="button-x" type="button" onclick="XButtonIsClicked(0)"
                                                id="x_button_0" value="0"></td>
                <td class="x-table-data"><input class="button-x" type="button" onclick="XButtonIsClicked(1)"
                                                id="x_button_1" value="1"></td>
            </tr>
            <tr class="x-table-row">
                <td class="x-table-data"><input class="button-x" type="button" onclick="XButtonIsClicked(2)"
                                                id="y_button_2" value="2"></td>
                <td class="x-table-data"><input class="button-x" type="button" onclick="XButtonIsClicked(3)"
                                                id="y_button_3" value="3"></td>
                <td class="x-table-data"><input class="button-x" type="button" onclick="XButtonIsClicked(4)"
                                                id="y_button_4" value="4"></td>
            </tr>
            </tbody>
        </table>

        <button class="button" onclick="sendButtonPress()" id="send_button" type="button">Отправить</button>
        <button class="button" onclick="clearButtonPress()" id="clear_button" type="button">Очистить</button>
    </div>


    <div class="right-part-of-the-content">

        <table class="result-table" id = "result_table">
            <thead>
            <tr class="result-table-header">
                <th class="result-table-th">Значение Х</th>
                <th class="result-table-th">Значение Y</th>
                <th class="result-table-th">R</th>
                <th class="result-table-th">Время запроса</th>
                <th class="result-table-th" id="result_table_result_sell">Результат</th>
            </tr>
            </thead>
            <tbody class="result-table-body">
                <%
                    ServletContext context = request.getServletContext();
                    ArrayList<String> resultList = (ArrayList<String>) context.getAttribute("resultList");
                    if (resultList != null)
                        for (String responseInfo : resultList)
                            out.println(responseInfo);
                %>
            </tbody>
        </table>

    </div>
</div>




<script src="SixDegrees.js"></script>
</body>
</html>
