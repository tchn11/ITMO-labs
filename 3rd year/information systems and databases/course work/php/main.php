<?php
    include "db_functions.php";

    $db = get_opened_database();
    session_start();

    if ($_GET['data'] == 'mood')
    {
        $query = pg_query($db, 'SELECT mood FROM Singer WHERE floor_id=' . $_SESSION['floor'] . ';');
        $mood = pg_fetch_row($query)[0];
        echo $mood;
    }

    if ($_GET['data'] == 'inventory')
    {
        $id_query = pg_query($db, "SELECT PLAYER_ID FROM Player WHERE NAME = '" . $_GET['username'] . "';");
        $_SESSION['user_id'] = pg_fetch_row($id_query)[0];

        $answer = pg_query($db, 'SELECT * FROM player_storage WHERE player_id = \'' . $_SESSION['user_id'] . '\';');

        $rows = pg_fetch_all($answer);

        if (count($rows) > 0)
        {
            echo "<div>";

            for ($i = 0; $i < count($rows); $i++)
            {
                $function_answer = pg_query($db, "SELECT GetItemName(" . $rows[$i]['item_id'] . ', \'' . $rows[$i]['item_type'] . '\');');
                $function_row = pg_fetch_all($function_answer);
                echo $function_row[0]['getitemname'] . ' ';
            }

            echo "</div>";
        }
    }
    if ($_GET['data'] == 'singer1')
    {
        $floor = $_GET['floor'];
        $reroll = $_GET['reroll'];

        if ($reroll == 'true')
        {
            $tmp_res = pg_query($db, "SELECT RerollSinger(" . $floor . ");");
        }

        $singer_answer = pg_query($db, "SELECT * FROM Singer WHERE FLOOR_ID=" . $floor . ";");
        $singer_row = pg_fetch_all($singer_answer);

        $item_id = $singer_row[0]['first_item_id'];
        $item_type = $singer_row[0]['first_item_type'];
        $mood = $singer_row[0]['mood'];

        $query = pg_query($db, "SELECT GetItemName(" . $item_id . ", '" . $item_type . "');");
        $name = pg_fetch_row($query)[0];
        $query = pg_query($db, "SELECT GetItemGoldCost(" . $item_id . ", '" . $item_type . "');");
        $gold = pg_fetch_row($query)[0];
        echo $name . "<br />" . "Стоимость: " . $gold . " золота.";
    }
    if ($_GET['data'] == 'singer2')
    {
        $floor = $_GET['floor'];
        $reroll = $_GET['reroll'];

        if ($reroll == 'true')
        {
            $tmp_res = pg_query($db, "SELECT RerollSinger(" . $floor . ");");
        }

        $singer_answer = pg_query($db, "SELECT * FROM Singer WHERE FLOOR_ID=" . $floor . ";");
        $singer_row = pg_fetch_all($singer_answer);

        $item_id = $singer_row[0]['second_item_id'];
        $item_type = $singer_row[0]['second_item_type'];
        $mood = $singer_row[0]['mood'];

        $query = pg_query($db, "SELECT GetItemName(" . $item_id . ", '" . $item_type . "');");
        $name = pg_fetch_row($query)[0];
        $query = pg_query($db, "SELECT GetItemGoldCost(" . $item_id . ", '" . $item_type . "');");
        $gold = pg_fetch_row($query)[0];
        echo $name . "<br />" . "Стоимость: " . $gold . " золота.";
    }
    if ($_GET['data'] == 'singer3')
    {
        $floor = $_GET['floor'];
        $reroll = $_GET['reroll'];

        if ($reroll == 'true')
        {
            $tmp_res = pg_query($db, "SELECT RerollSinger(" . $floor . ");");
        }

        $singer_answer = pg_query($db, "SELECT * FROM Singer WHERE FLOOR_ID=" . $floor . ";");
        $singer_row = pg_fetch_all($singer_answer);

        $item_id = $singer_row[0]['third_item_id'];
        $item_type = $singer_row[0]['third_item_type'];
        $mood = $singer_row[0]['mood'];

        $query = pg_query($db, "SELECT GetItemName(" . $item_id . ", '" . $item_type . "');");
        $name = pg_fetch_row($query)[0];
        $query = pg_query($db, "SELECT GetItemGoldCost(" . $item_id . ", '" . $item_type . "');");
        $gold = pg_fetch_row($query)[0];
        echo $name . "<br />" . "Стоимость: " . $gold . " золота.";
    }
    if ($_GET['data'] == 'buy1')
    {
        $floor = $_GET['floor'];
        $money = $_GET['money'];

        $singer_answer = pg_query($db, "SELECT * FROM Singer WHERE FLOOR_ID=" . $floor . ";");
        $singer_row = pg_fetch_all($singer_answer);

        $item_id = $singer_row[0]['first_item_id'];
        $item_type = $singer_row[0]['first_item_type'];
        $mood = $singer_row[0]['mood'];
        if ($item_id != '') {
            $query = pg_query($db, "SELECT GetItemName(" . $item_id . ", '" . $item_type . "');");
            $name = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT GetItemGoldCost(" . $item_id . ", '" . $item_type . "');");
            $gold = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT BuyFromSinger(" . $_SESSION['user_id'] . ", cast(1 as smallint), " . $floor . ", " . $money . ");");
            $code = pg_fetch_row($query)[0];
            $query = pg_query($db, 'SELECT mood FROM Singer WHERE floor_id=' . $_SESSION['floor'] . ';');
            $mood = pg_fetch_row($query)[0];
            if ($mood > 1.4)
            {
                echo 'dead';
            }
            elseif ($code == 0) {
                echo "Предмет куплен";
            }
            elseif ($code == 1) {
                echo "Предмет уже куплен";
            }
            elseif ($code == 2) {
                echo $name . "<br />" . "Стоимость: " . $gold . " золота.<br /> Недостаточно золота.";
            }
            elseif ($code == 3) {
                echo $name . "<br />" . "Стоимость: " . $gold . " золота.<br /> Цена не угадана, тороговец разозлился.";
            }
        }
        else
        {
            echo "Предмет уже куплен";
        }
    }
    if ($_GET['data'] == 'buy2')
    {
        $floor = $_GET['floor'];
        $money = $_GET['money'];

        $singer_answer = pg_query($db, "SELECT * FROM Singer WHERE FLOOR_ID=" . $floor . ";");
        $singer_row = pg_fetch_all($singer_answer);

        $item_id = $singer_row[0]['second_item_id'];
        $item_type = $singer_row[0]['second_item_type'];
        $mood = $singer_row[0]['mood'];
        if ($item_id != '') {
            $query = pg_query($db, "SELECT GetItemName(" . $item_id . ", '" . $item_type . "');");
            $name = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT GetItemGoldCost(" . $item_id . ", '" . $item_type . "');");
            $gold = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT BuyFromSinger(" . $_SESSION['user_id'] . ", cast(2 as smallint), " . $floor . ", " . $money . ");");
            $code = pg_fetch_row($query)[0];
            $query = pg_query($db, 'SELECT mood FROM Singer WHERE floor_id=' . $_SESSION['floor'] . ';');
            $mood = pg_fetch_row($query)[0];
            if ($mood > 1.4)
            {
                echo 'dead';
            }
            elseif ($code == 0) {
                echo "Предмет куплен";
            }
            elseif ($code == 1) {
                echo "Предмет уже куплен";
            }
            elseif ($code == 2) {
                echo $name . "<br />" . "Стоимость: " . $gold . " золота.<br /> Недостаточно золота.";
            }
            elseif ($code == 3) {
                echo $name . "<br />" . "Стоимость: " . $gold . " золота.<br /> Цена не угадана, тороговец разозлился.";
            }
        }
        else
        {
            echo "Предмет уже куплен";
        }
    }
    if ($_GET['data'] == 'buy3')
    {
        $floor = $_GET['floor'];
        $money = $_GET['money'];

        $singer_answer = pg_query($db, "SELECT * FROM Singer WHERE FLOOR_ID=" . $floor . ";");
        $singer_row = pg_fetch_all($singer_answer);

        $item_id = $singer_row[0]['third_item_id'];
        $item_type = $singer_row[0]['third_item_type'];
        $mood = $singer_row[0]['mood'];
        if ($item_id != '')
        {
            $query = pg_query($db, "SELECT GetItemName(" . $item_id . ", '" . $item_type . "');");
            $name = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT GetItemGoldCost(" . $item_id . ", '" . $item_type . "');");
            $gold = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT BuyFromSinger(" . $_SESSION['user_id'] . ", cast(3 as smallint), " . $floor . ", " . $money . ");");
            $code = pg_fetch_row($query)[0];
            $query = pg_query($db, 'SELECT mood FROM Singer WHERE floor_id=' . $_SESSION['floor'] . ';');
            $mood = pg_fetch_row($query)[0];
            if ($mood > 1.4)
            {
                echo 'dead';
            }
            elseif ($code == 0) {
                echo "Предмет куплен";
            }
            elseif ($code == 1) {
                echo "Предмет уже куплен";
            }
            elseif ($code == 2) {
                echo $name . "<br />" . "Стоимость: " . $gold . " золота.<br /> Недостаточно золота.";
            }
            elseif ($code == 3) {
                echo $name . "<br />" . "Стоимость: " . $gold . " золота.<br /> Цена не угадана, тороговец разозлился.";
            }
        }
        else
        {
            echo "Предмет уже куплен";
        }
    }

    if ($_GET['data'] == 'next_floor')
    {
       $events = array(
            ['Повезло, повезло. Ваш танец понравился монстрам.', 0],
            ['Ваш нелепый вальс очаровал даже драконов.', 0],
            ['Вы наполняетесь решимостью.', 0],
            ['Скелеты в шкафу! Чёрный скелет нападает из-за угла.', 1],
            ['Мимики не дремлют. Обычные ящики оказались не совсем обычными.', 1],
            ['Осторожно, злая собака! Адская гончая напрыгивает сбоку.', 1],
            ['Назвался груздем – полезай в кузов. Не все грибы одинаково безобидны.', 1],
            ['Призраки боятся себя больше вас.', 2],
            ['Не зная броду, не суйся за Фродо. Монстры танцуют, даже если вы застреваете в трясине.', 2],
            ['Friendly fire. Дракон правда не хотел вас задеть...', 3],
            ['Шах и мат! Блюз бессмертен.', 3],
            ['Удар в спину. Уважайте зелёных слаймов.', 6],
            ['Go back, I want to be monkey. Не каждую обезъяну человек способен одолеть.', 3]
        );

        $query=pg_query($db, "SELECT SUCCESS_CHANCE FROM Player WHERE PLAYER_ID = " . $_SESSION['user_id'] . ';');
        $chance = pg_fetch_row($query)[0];
        $random_event = $events[rand(0, 12)];
        $random_float = rand(0, 1000000) / 1000000;
        if ($random_float <= $chance)
        {
            $query = pg_query($db, "UPDATE Player SET GOLD = GOLD + (SUCCESS_CHANCE * 1000 + 100) WHERE PLAYER_ID = " . $_SESSION['user_id'] . ';');
            $query = pg_query($db, "UPDATE Player SET DIAMONDS = DIAMONDS + 1 WHERE PLAYER_ID = " . $_SESSION['user_id'] . ';');
            echo "Снаряжение защитило";
        }
        else {
            $query = pg_query($db, "UPDATE Player SET HEALTH_POINTS = HEALTH_POINTS - " . $random_event[1] . " WHERE PLAYER_ID = " . $_SESSION['user_id'] . ';');
            $query = pg_query($db, "SELECT HEALTH_POINTS FROM Player WHERE PLAYER_ID = " . $_SESSION['user_id'] . ';');
            $health = pg_fetch_row($query)[0];
            if ($health <= 0) {
                echo 'dead';
            } else {
                $query = pg_query($db, "UPDATE Player SET GOLD = GOLD + (SUCCESS_CHANCE * 1000 + 50) WHERE PLAYER_ID = " . $_SESSION['user_id'] . ';');
                $query = pg_query($db, "UPDATE Player SET DIAMONDS = DIAMONDS + 1 WHERE PLAYER_ID = " . $_SESSION['user_id'] . ';');
                echo $random_event[0];
            }
        }
    }

    close_database($db);
