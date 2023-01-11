function update_inventory()
{
    get_gold(window.localStorage.getItem('username'));
    get_diamonds(window.localStorage.getItem('username'));
    get_health(window.localStorage.getItem('username'));
    //update_mood();
    $.ajax({
        url: "php/main.php",
        method: "GET",
        data: 'data=inventory&username=' + window.localStorage.getItem('username'),
        dataType: "html",
        success: function(data){
            console.log(data);

            $("#inventory").html(data);
        },
        error: function(error){
            console.log(error);
        }
    })
}

/*function update_mood()
{
    $.ajax({
        url: "php/main.php",
        method: "GET",
        data: 'data=mood',
        dataType: "html",
        success: function(data){
            console.log(data);

            $("#singer_mood").text("Настроение: " + data);
        },
        error: function(error){
            console.log(error);
        }
    });
}*/

function get_gold(username)
{
    $.ajax({
        url: "php/lobby.php",
        method: "GET",
        data: 'command=get_gold&user=' + username,
        dataType: "html",
        success: function(data){
            console.log(data);

            $("#gold").text("Золото: " + data);
        },
        error: function(error){
            console.log(error);
        }
    });
}

function get_diamonds(username)
{
    $.ajax({
        url: "php/lobby.php",
        method: "GET",
        data: 'command=get_diamonds&user=' + username,
        dataType: "html",
        success: function(data){
            console.log(data);

            $("#diamonds").text("Алмазы: " + data);
        },
        error: function(error){
            console.log(error);
        }
    });
}

function get_health(username)
{
    $.ajax({
        url: "php/lobby.php",
        method: "GET",
        data: 'command=get_health&user=' + username,
        dataType: "html",
        success: function(data){
            console.log(data);

            $("#health").text("Жизни: " + data);
        },
        error: function(error){
            console.log(error);
        }
    });
}

function update_singer(floor, reroll, num)
{
    $.ajax({
        url: "php/main.php",
        method: "GET",
        data: 'data=singer' + num + '&floor=' + floor + '&reroll=' + reroll,
        dataType: "html",
        success: function(data){
            console.log(data);

            $("#it" + num + "_text").html(data);
        },
        error: function(error){
            console.log(error);
        }
    })
}

function send_function_factory(num)
{
    return function () {
        let value = $("#it" + num + "_textfield").val();
        $.ajax({
            url: "php/main.php",
            method: "GET",
            data: 'data=buy' + num + '&floor=' + window.localStorage.getItem('floor') + '&money=' + value,
            dataType: "html",
            success: function(data){
                console.log(data);

                update_inventory();
                if (data == 'dead')
                {
                    window.localStorage.removeItem("was_floor_rolled");
                    window.localStorage.removeItem('new_game');
                    alert('Торговец разозлился слишком сильно');
                    window.location.replace('http://localhost/lobby.html');
                }
                $("#it" + num + "_text").html(data);
            },
            error: function(error){
                console.log(error);
            }
        });
    }
}

$(document).ready(function(){
    if (!window.localStorage.getItem('username'))
    {
        window.location.replace('http://localhost');
        return;
    }
    update_inventory();

    $("#next_floor").click(function (){
        window.localStorage.setItem('floor', parseInt(window.localStorage.getItem('floor')) + 1);
        if (window.localStorage.getItem('floor') > 4)
        {
            window.localStorage.removeItem("was_floor_rolled");
            window.localStorage.removeItem('new_game');
            alert('Ура победа');
            window.location.replace('http://localhost/lobby.html');
        }
        update_singer(window.localStorage.getItem('floor'), 'true', 1);
        setTimeout(function () {
            update_singer(window.localStorage.getItem('floor'), 'false', 2);
            update_singer(window.localStorage.getItem('floor'), 'false', 3);
            $.ajax({
                url: "php/main.php",
                method: "GET",
                data: 'data=next_floor',
                dataType: "html",
                success: function (data) {
                    console.log(data);
                    if (data == 'dead')
                    {
                        window.localStorage.removeItem("was_floor_rolled");
                        window.localStorage.removeItem('new_game');
                        alert('Вы умерли');
                        window.location.replace('http://localhost/lobby.html');
                    }
                    else
                    {
                        alert(data);
                    }
                    update_inventory();
                },
                error: function (error) {
                    console.log(error);
                }
            });
        }, 300);
    });

    if (!window.localStorage.getItem("was_floor_rolled")) {
        window.localStorage.setItem('was_floor_rolled', 'false');

        update_singer(window.localStorage.getItem('floor'), 'true', 1);
        setTimeout(function () {
            update_singer(window.localStorage.getItem('floor'), 'false', 2);
            update_singer(window.localStorage.getItem('floor'), 'false', 3);

            $("#username").text(window.localStorage.getItem('username'));

            $("#it1_button").click(send_function_factory('1'));
            $("#it2_button").click(send_function_factory('2'));
            $("#it3_button").click(send_function_factory('3'));
        }, 200);
    }
    else
    {
        update_singer(window.localStorage.getItem('floor'), 'false', 1);
        update_singer(window.localStorage.getItem('floor'), 'false', 2);
        update_singer(window.localStorage.getItem('floor'), 'false', 3);

        $("#username").text(window.localStorage.getItem('username'));

        $("#it1_button").click(send_function_factory('1'));
        $("#it2_button").click(send_function_factory('2'));
        $("#it3_button").click(send_function_factory('3'));
    }
})