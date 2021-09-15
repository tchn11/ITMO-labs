$(function(){
	let canv = $('.graph_canvas');

	const GRAPH_COFF = 67;
	const GRAPH_MOVE = 110;
	const X_VALUES = ["-4.0", "-3.0", "-2.0", "-1.0", "0.0", "1.0", "2.0", "3.0", "4.0"];

	let r_val = window.localStorage.getItem("r_val");

	if (r_val !== null){
		redrawFromInput(window.localStorage.getItem("x_val"), window.localStorage.getItem("y_val"));
	}

	function isNum(n){
		return !isNaN(parseFloat(n)) && isFinite(n);
	}

	/* проверка на валидность введённых значений у*/

	function validate_Y(){
		var y = $("#y_textinput").val().replace(",",".");
		if (isNum(y) && y >= -5 && y <= 3){
			$("#y_textinput").removeClass("text_error");
			return true;
		}
		else {
			$("#y_textinput").addClass("text_error");
			return false;
		}
	}
	/* проверка на валидность введённых значений х*/
	
	function validate_X(){
		if ($(".x_radio").is(":checked")){
			$(".x_box_label").removeClass("box_error");
			return true;
		}
		else{
			$(".x_box_label").addClass("box_error");
			return false;
		}
	}
		
	/* проверка на валидность введённых значений r*/

	function validate_R(){
		if ($(".r_checkbox").is(":checked")){
			if ($(".r_checkbox:checked").length > 1){
				$(".r_box_label").addClass("box_error");
				return false;
			}
			$(".r_box_label").removeClass("box_error");
			return true;
		}
		else{
			$(".r_box_label").addClass("box_error");
			return false;
		}
	}

	function validated_Form(){
		return validate_X() & validate_Y() & validate_R();
	}

	function clearCanvals(){
		canv[0].getContext('2d').clearRect( 0, 0, canv.width(), canv.height());
	}
	function drawPoint(x, y){
		clearCanvals();
		if(x > canv.width() || x < -canv.width() || y > canv.height() || y < -canv.height())
			return;

		let Axes = canv[0].getContext('2d');
		Axes.setLineDash([2, 2]);
		Axes.beginPath();
		Axes.moveTo(x, 110);
		Axes.lineTo(x, y);
		Axes.moveTo(110, y);
		Axes.lineTo(x, y);
		Axes.stroke();
		Axes.fillStyle = 'red';
		Axes.arc(x, y, 2, 0, 2*Math.PI);
		Axes.fill();
	}

	function redrawFromInput(x, y){
		drawPoint(x * GRAPH_COFF / r_val + GRAPH_MOVE,
				-(y / r_val * GRAPH_COFF - GRAPH_MOVE));
	}

	function canv_click(event){
		console.log("Click!")
		if(!validate_R())
			return;

		let x = (event.offsetX - GRAPH_MOVE) / GRAPH_COFF * r_val;

		let minDiff = Infinity;
		let nearestXValue;

		for (let i = 0; i < X_VALUES.length; i++){
			if (Math.abs(x - X_VALUES[i]) < minDiff){
				minDiff = Math.abs(x - X_VALUES[i]);
				nearestXValue = X_VALUES[i];
			}
		}

		let y = (-event.offsetY + GRAPH_MOVE) / GRAPH_COFF * r_val;

		if (y < -5) y = -5;
		if (y > 3) y = 3;

		drawPoint(nearestXValue * GRAPH_COFF/r_val + GRAPH_MOVE, -(y/r_val * GRAPH_COFF - GRAPH_MOVE));

		let xSelected = $('input[name="x_val"][value="'+ nearestXValue.trim() +'"]');

		xSelected.trigger("click");

		$("#y_textinput").val(y.toString().substring(0, 5));
	}

	canv.on("click", canv_click)

	function send_Form (event){
		console.log("send");
		event.preventDefault();
		if (!validated_Form()){
			return;
		}
		// сделать асинхронный http запрос
		console.log($(this).serialize());
		var x = $('input[name=x_val]:checked').val()
		var y = $("#y_textinput").val();
		$.ajax({
			url: "/web-lab2",
			method: "POST",
			data: $(this).serialize() + "&timezone=" + new Date().getTimezoneOffset(),
			dataType: "html",
			beforeSend: function (){
				$(".button").attr("disabled", "disabled");
			},
			success: function (data){
				$(".button").attr("disabled", false);

				document.open();
				document.write(data);
				document.close();
			},
			error: function(error){
				console.log(error);
				$(".button").attr("disabled", false);
			}

		});

		window.localStorage.setItem("r_val", r_val);
		window.localStorage.setItem("x_val", x);
		window.localStorage.setItem("y_val", y);
	}

	$("#inpform").on("submit", send_Form);

	function reset(event){
		$.ajax({
			url: "/web-lab2",
			method: "POST",
			data: "clear=true",
			dataType: "html",
			beforeSend: function (){
				$(".button").attr("disabled", "disabled");
			},
			success: function (data){
				$(".button").attr("disabled", false);

				document.open();
				document.write(data);
				document.close();
			},
			error: function(error){
				console.log(error);
				$(".button").attr("disabled", false);
			}
		});

		window.localStorage.removeItem("r_val");
		window.localStorage.removeItem("x_val");
		window.localStorage.removeItem("y_val");

		return true;
	}

	$("#inpform").on("reset", reset);

	function newR(event){
		if (!validate_R())
			return;
		r_val = $('.r_checkbox:checked').val();
		console.log(r_val);
	}

	$('.r_checkbox').on("click", newR);
})
	