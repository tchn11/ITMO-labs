$(document).ready(function(){
		function isNum(n){
			return !isNaN(parseFloat(n));
		}
	
	/* проверка на валидность введённых значений у*/
	
		function validate_Y(){
			var y = $("#y_textinput").val().replace(",",".");
			if (isNum(y) && y >= -3 && y <= 5){
				$("#y_textinput").removeClass("text_error");
				return true;
			}
			else {
				$("#y_textinput").addClass("text_error");
				return false;
			}
		}
	
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
		
		function validate_R(){
			if ($(".r_checkbox").is(":checked")){
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
		
		function send_Form (event){
			console.log("send");
			event.preventDefault();
			if (!validated_Form()){
				return;
			}
			// сделать асинхронный http запрос
			console.log($(this).serialize());
			$.ajax({
				url: "php/main.php",
				method: "POST",
				data: $(this).serialize() + "&timezone=" + new Date().getTimezoneOffset(),
				dataType: "html",
				beforeSend: function (){
					$(".button").attr("disabled", "disabled");
				},
				success: function(data){
					console.log(data);
					$(".button").attr("disabled", false);	
					$("#result_table").append(data);
				},
				error: function(error){
					console.log(error);
					$(".button").attr("disabled", false);	
				}
			
			})
		}
		
		$("#inpform").on("submit", send_Form);
})
	