function update_function_generator(label, command)
{
    return function(){
        $.ajax({
            url: "php/lobby.php",
            method: "GET",
            data: 'command=' + command,
            dataType: "html",
            success: function(data){
                console.log(data);

                $(label).html(data);

                get_gold(window.localStorage.getItem('username'));
                get_diamonds(window.localStorage.getItem('username'));
            },
            error: function(error){
                console.log(error);
            }
        });
    };
}

function rerool(){
    $.ajax({
        url: "php/lobby.php",
        method: "GET",
        data: 'command=rerool',
        dataType: "html",
        success: function(data){
            console.log(data);
        },
        error: function(error){
            console.log(error);
        }
    });
}

function get_gold(username)
{
    let gold = 0;
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

function add_on_click_buy(name)
{
    $("#" + name + "_button").click(update_function_generator('#' + name + '_label', name + '_buy'));
}

function update(name)
{
    $.ajax({
        url: "php/lobby.php",
        method: "GET",
        data: 'command=update_' + name,
        dataType: "html",
        success: function(data){
            console.log(data);

            $("#" + name + "_label").html(data);
        },
        error: function(error){
            console.log(error);
        }
    });
}

$(document).ready(function(){
    if (!window.localStorage.getItem('new_game'))
    {
        $.ajax({
            url: "php/lobby.php",
            method: "GET",
            data: 'command=start_new_game&username=' + window.localStorage.getItem('username'),
            dataType: "html",
            success: function(data){
                console.log(data);
            },
            error: function(error){
                console.log(error);
            }
        });
        window.localStorage.setItem('new_game', 'true');
    }

    if (!window.localStorage.getItem('username'))
    {
        window.location.replace('http://localhost');
        return;
    }

    if (window.localStorage.getItem('floor') != 0)
    {
        window.location.replace('http://localhost/main.html');
        return;
    }

    $("#username").text(window.localStorage.getItem('username'));
    get_gold(window.localStorage.getItem('username'));
    get_diamonds(window.localStorage.getItem('username'));

    rerool();

    setTimeout(function (){
        add_on_click_buy('gf1');
        add_on_click_buy('gf2');
        add_on_click_buy('gf3');

        add_on_click_buy('mr1');
        add_on_click_buy('mr2');
        add_on_click_buy('mr3');

        add_on_click_buy('dd1');
        add_on_click_buy('dd2');
        add_on_click_buy('dd3');

        add_on_click_buy('dm1');
        add_on_click_buy('dm2');
        add_on_click_buy('dm3');

        update('gf1');
        update('gf2');
        update('gf3');

        update('mr1');
        update('mr2');
        update('mr3');

        update('dd1');
        update('dd2');
        update('dd3');

        update('dm1');
        update('dm2');
        update('dm3');
    }, 200);

    $('#continue_button').click(function () {
        window.localStorage.setItem('floor', 1);
        window.location.replace('http://localhost/main.html');
    });
})
