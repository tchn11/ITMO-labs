function form_sender(event){
    event.preventDefault();
    // сделать асинхронный http запрос
    $.ajax({
        url: "php/auth.php",
        method: "GET",
        data: $(this).serialize(),
        dataType: "html",
        beforeSend: function (){
            $(".button").attr("disabled", "disabled");
        },
        success: function(data){
            console.log(data);

            $(".button").attr("disabled", false);

            $("#error_msg").text(data);
            if (data == 'OK')
            {
                window.localStorage.setItem('username', $("#username-textinput").val());
                window.localStorage.setItem('password', $("#password-textinput").val());
                window.localStorage.setItem('floor', 0);
                window.location.replace('http://localhost/lobby.html');
            }
        },
        error: function(error){
            console.log(error);
            $(".button").attr("disabled", false);
        }
    })
}

$(document).ready(function(){
    $("#input-form").on("submit", form_sender);
    window.localStorage.clear();
})