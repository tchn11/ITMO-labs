<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="lib"%>
<jsp:useBean id="table" class="Lab2.beans.RawBean" scope="session" />

<!DOCTYPE html>

<html>
	<head>
		<title> Лабораторная №2 </title>
		<link rel="shortcut icon" href="img/duck.png" type="image/png">
		<link href="https://fonts.googleapis.com/css2?family=PT+Serif&display=swap" rel="stylesheet">
		<link rel="stylesheet" href="css/style.css">
		<meta name="viewpoint" content="width=device-width, initial-scale=1.0">
	</head>

	<body>
		<div id="top_div" class="main_div">
			<div id="header_left">
				<h2>Конаныхина Антонина Александровна,
				группа Р3215</h2>
			</div>
			<img src="img/duck512.gif" width=55px>
			<div id="header_right">
				<h2 id = "right_h2">Вариант 15111</h2>
			</div>
		</div>
		<div class="main_div">
			<div id="graph">
			<div class="div_header">
			График
			</div>
				<div id="graph_div" class="div_content">
					<object class="result-graph" type="image/svg+xml", data="img/graph.svg">
						<img src="img/graph.jpg" alt="График не найден" width="220" height="220">
					</object>
					<canvas class="graph_canvas" width="220" height="220">Интерктивная область графика</canvas>
				</div>
			</div>

			<div id="input_form">
			<div class="div_header">
			Входные данные
			</div>
			<div class="div_content">
				<form id="inpform" action="/web-lab2" method="POST">

					<table id="input_grid">
						<!-- значение Х -->
						<tr>
						  <td class="input_grid_label">
							<label>X:</label>
						  </td>

						  <td class="input_grid_value x_radio_group">
							<div class="center_labeled">
							  <label class="x_box_label" for="x_radio_1">-4</label>
							  <input class="x_radio" id="x_radio_1" type="radio" name="x_val" value="-4.0">
							</div>

							<div class="center_labeled">
							  <label class="x_box_label" for="x_radio_2">-3</label>
							  <input class="x_radio" id="x_radio_2" type="radio" name="x_val" value="-3.0">
							</div>

							<div class="center_labeled">
							  <label class="x_box_label" for="x_radio_3">-2</label>
							  <input class="x_radio" id="x_radio_3" type="radio" name="x_val" value="-2.0">
							</div>

							<div class="center_labeled">
							  <label class="x_box_label" for="x_radio_4">-1</label>
							  <input class="x_radio" id="x_radio_4" type="radio" name="x_val" value="-1.0">
							</div>

							<div class="center_labeled">
							  <label class="add_labeled x_box_label" for="x_radio_5">0</label>
							  <input class="x_radio" id="x_radio_5" type="radio" name="x_val" value="0.0">
							</div>

							<div class="center_labeled">
							  <label class="add_labeled x_box_label" for="x_radio_6">1</label>
							  <input class="x_radio" id="x_radio_6" type="radio" name="x_val" value="1.0">
							</div>

							<div class="center_labeled">
							  <label class="add_labeled x_box_label" for="x_radio_7">2</label>
							  <input class="x_radio" id="x_radio_7" type="radio" name="x_val" value="2.0">
							</div>

							<div class="center_labeled">
							  <label class="add_labeled x_box_label" for="x_radio_8">3</label>
							  <input class="x_radio" id="x_radio_8" type="radio" name="x_val" value="3.0">
							</div>

							<div class="center_labeled">
							  <label class="add_labeled x_box_label" for="x_radio_9">4</label>
							  <input class="x_radio" id="x_radio_9" type="radio" name="x_val" value="4.0">
							</div>
						  </td>
						</tr>

						<!-- значение Y -->
						<tr>
						  <td class="input_grid_label">
							<label for="y_textinput"> Y:</label>
						  </td>

						  <td class="input_grid_value">
							<input id="y_textinput" type="text" name="y_val" maxlength="5" autocomplete="off" placeholder="Ведите число от -5 до 3...">
						  </td>
						</tr>

						<!-- значение R -->
						<tr>
						  <td class="input_grid_label">
							<label> R:</label>
						  </td>

						  <td class="input_grid_value">
							<div class="center_labeled">
							  <label class="r_box_label" for="r_checkbox_1">1</label>
							  <input class="r_checkbox" id="r_checkbox_1" type="checkbox" name="r_val" value="1.0">
							</div>

							<div class="center_labeled">
							  <label class="r_box_label" for="r_checkbox_2">2</label>
							  <input class="r_checkbox" id="r_checkbox_2" type="checkbox" name="r_val" value="2.0">
							</div>

							<div class="center_labeled">
							  <label class="r_box_label" for="r_checkbox_3">3</label>
							  <input class="r_checkbox" id="r_checkbox_3" type="checkbox" name="r_val" value="3.0">
							</div>

							<div class="center_labeled">
							  <label class="r_box_label" for="r_checkbox_4">4</label>
							  <input class="r_checkbox" id="r_checkbox_4" type="checkbox" name="r_val" value="4.0">
							</div>
							<div class="center_labeled">
							  <label class="r_box_label" for="r_checkbox_5">5</label>
							  <input class="r_checkbox" id="r_checkbox_5" type="checkbox" name="r_val" value="5.0">
							</div>
						  </td>
						</tr>

						<!-- кнопки -->

						<tr>
						  <td colspan="2">
							<div class="buttons">
							  <input id="submit_button" class="button" type="submit" value="Отправить">
							  <input id="clear_button" class="button" type="reset" value="Стереть">
							</div>
						  </td>
						</tr>
					  </table>
					</form>
				</div>
			</div>
		</div>
		<!-- таблица -->
		<div id="scroll">
			<table id="result_table" cols=6>
				<tr id="table_header">
				  <th class="coords_column">X </th>
				  <th class="coords_column">Y </th>
				  <th class="coords_column">R </th>
				  <th class="time_column">Текущее время</th>
				  <th class="time_column">Время выполнения</th>
				  <th class="result_column">Результат</th>
				</tr>
				<lib:forEach var="raw" items="${table.raws}">
					<tr>
						<th>${raw.x_val}</th>
						<th>${raw.y_val}</th>
						<th>${raw.r_val}</th>
						<th>${raw.current_time}</th>
						<th>${raw.execution_time}</th>
						<th>${raw.result ? "внутри" : "снаружи"}</th>
					</tr>
				</lib:forEach>
			</table>
		</div>
		<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
		<script src="js/main.js"></script>
	</body>

</html>