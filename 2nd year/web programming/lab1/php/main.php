<?php


function validate($val){
	return isset($val);
}

// Проверка на попадание в область

function check_first_area($x, $y, $r){
		return ($x>=0 && $y>=0 && sqrt($x*$x+$y*$y)<=$r/2);
}

function check_second_area($x, $y, $r){
		return ($x<=0 && $y>=0 && $y<=2*$x+$r);
}

function check_third_area($x, $y, $r){
		return ($x>=0 && $y<=0 && $x<=$r/2 && $y>= -$r);
}

// Основная логика программы
$Xval = $_POST["x_val"];
$Yval = $_POST["y_val"];
$Rval = $_POST["r_val"];

$timezone = $_POST["timezone"];

if (validate($Xval) && validate($Yval) && validate($Rval) && validate($timezone)){
	
	$INSIDE = check_first_area($Xval, $Yval, $Rval) || check_second_area($Xval, $Yval, $Rval) || check_third_area($Xval, $Yval, $Rval);
	$CONVERTED_INSIDE = $INSIDE ? "Внутри": "Снаружи";
	$current_time = date("j M o G:i:s", time()-$timezone*60);
	$executionTime = round(microtime(true) - $_SERVER['REQUEST_TIME_FLOAT'], 7);
	
	$ANSWER = "<tr>";
	$ANSWER .= "<td>" . $Xval . "</td>";
	$ANSWER .= "<td>" . $Yval . "</td>";
	$ANSWER .= "<td>" . $Rval . "</td>";
	$ANSWER .= "<td>" . $current_time . "</td>";
	$ANSWER .= "<td>" . $executionTime . "</td>";
	$ANSWER .= "<td>" . $CONVERTED_INSIDE . "</td>";
	$ANSWER .= "</tr>";
	
	echo $ANSWER;
}
?>