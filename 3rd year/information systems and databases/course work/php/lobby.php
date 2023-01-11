<?php
    include 'db_functions.php';

    session_start();

    $db = get_opened_database();

    $command = $_GET['command'];

    switch ($command){
        case "get_gold":
        {
            $username = $_GET['user'];
            $_SESSION['username'] = $username;
            $id_query = pg_query($db, "SELECT PLAYER_ID FROM Player WHERE NAME = '" . $username . "';");
            $_SESSION['user_id'] = pg_fetch_row($id_query)[0];
            $answer = pg_query($db, "SELECT GOLD FROM Player WHERE NAME = '" . $username . "';");
            $gold = pg_fetch_row($answer)[0];
            echo $gold;
            break;
        }
        case "get_diamonds":
        {
            $username = $_GET['user'];
            $_SESSION['username'] = $username;
            $id_query = pg_query($db, "SELECT PLAYER_ID FROM Player WHERE NAME = '" . $username . "';");
            $_SESSION['user_id'] = pg_fetch_row($id_query)[0];
            $answer = pg_query($db, "SELECT DIAMONDS FROM Player WHERE NAME = '" . $username . "';");
            $diamonds = pg_fetch_row($answer)[0];
            echo $diamonds;
            break;
        }
        case "get_health":
        {
            $username = $_GET['user'];
            $_SESSION['username'] = $username;
            $health_query = pg_query($db, "SELECT HEALTH_POINTS FROM Player WHERE NAME = '" . $username . "';");
            echo pg_fetch_row($health_query)[0];
            break;
        }
        case "rerool":
        {
            pg_query($db, "SELECT RerollMerlin();");
            $mr_query = pg_query($db, "SELECT * FROM Merlin;");
            $mr_rows = pg_fetch_all($mr_query);

            $_SESSION['mr1_id'] = $mr_rows[0]['item_id'];
            $_SESSION['mr1_type'] = $mr_rows[0]['item_type'];
            $_SESSION['mr2_id'] = $mr_rows[1]['item_id'];
            $_SESSION['mr2_type'] = $mr_rows[1]['item_type'];
            $_SESSION['mr3_id'] = $mr_rows[2]['item_id'];
            $_SESSION['mr3_type'] = $mr_rows[2]['item_type'];

            pg_query($db, "SELECT RerollHephaestus();");
            $mr_query = pg_query($db, "SELECT * FROM Hephaestus;");
            $mr_rows = pg_fetch_all($mr_query);
            $_SESSION['gf1_id'] = $mr_rows[0]['item_id'];
            $_SESSION['gf1_type'] = $mr_rows[0]['item_type'];
            $_SESSION['gf2_id'] = $mr_rows[1]['item_id'];
            $_SESSION['gf2_type'] = $mr_rows[1]['item_type'];
            $_SESSION['gf3_id'] = $mr_rows[2]['item_id'];
            $_SESSION['gf3_type'] = $mr_rows[2]['item_type'];

            pg_query($db, "SELECT RerollDiamondDealler();");
            $mr_query = pg_query($db, "SELECT * FROM Diamond_dealer;");
            $mr_rows = pg_fetch_all($mr_query);
            $_SESSION['dd1_id'] = $mr_rows[0]['item_id'];
            $_SESSION['dd1_type'] = $mr_rows[0]['item_type'];
            $_SESSION['dd2_id'] = $mr_rows[1]['item_id'];
            $_SESSION['dd2_type'] = $mr_rows[1]['item_type'];
            $_SESSION['dd3_id'] = $mr_rows[2]['item_id'];
            $_SESSION['dd3_type'] = $mr_rows[2]['item_type'];

            $random_row = pg_query($db, "SELECT UPDATE_ID, NAME, PRICE_DIAMOND FROM Dungeon_master ORDER BY RANDOM() LIMIT 1;");
            $random_query = pg_fetch_row($random_row);
            $_SESSION['dm1_id'] = $random_query[0];
            $_SESSION['dm1_name'] = $random_query[1];
            $_SESSION['dm1_price'] = $random_query[2];
            $random_row = pg_query($db, "SELECT UPDATE_ID, NAME, PRICE_DIAMOND FROM Dungeon_master ORDER BY RANDOM() LIMIT 1;");
            $random_query = pg_fetch_row($random_row);
            $_SESSION['dm2_id'] = $random_query[0];
            $_SESSION['dm2_name'] = $random_query[1];
            $_SESSION['dm2_price'] = $random_query[2];
            $random_row = pg_query($db, "SELECT UPDATE_ID, NAME, PRICE_DIAMOND FROM Dungeon_master ORDER BY RANDOM() LIMIT 1;");
            $random_query = pg_fetch_row($random_row);
            $_SESSION['dm3_id'] = $random_query[0];
            $_SESSION['dm3_name'] = $random_query[1];
            $_SESSION['dm3_price'] = $random_query[2];
            break;
        }
        case "update_gf1":
        {
            $query = pg_query($db, "SELECT GetItemName(" . $_SESSION['gf1_id'] . ", '" . $_SESSION['gf1_type'] . "');");
            $name = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT GetItemDiamondCost(" . $_SESSION['gf1_id'] . ", '" . $_SESSION['gf1_type'] . "');");
            $diamonds = pg_fetch_row($query)[0];
            echo $name . "<br />" . "Стоимость: " . $diamonds . " алмазов.";
            break;
        }
        case "gf1_buy":
        {
            $query = pg_query($db, "SELECT BuyHephaestus(" .  $_SESSION['user_id'] . ", " . $_SESSION['gf1_id'] . ", '" . $_SESSION['gf1_type'] . "');");
            if (pg_fetch_all($query)[0]['buyhephaestus'] == 't')
            {
                echo 'Куплено';
            }
            else
            {
                echo 'Ошибка покупки';
            }
            break;
        }
        case "update_gf2":
        {
            $query = pg_query($db, "SELECT GetItemName(" . $_SESSION['gf2_id'] . ", '" . $_SESSION['gf2_type'] . "');");
            $name = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT GetItemDiamondCost(" . $_SESSION['gf2_id'] . ", '" . $_SESSION['gf2_type'] . "');");
            $diamonds = pg_fetch_row($query)[0];
            echo $name . "<br />" . "Стоимость: " . $diamonds . " алмазов.";
            break;
        }
        case "gf2_buy":
        {
            $query = pg_query($db, "SELECT BuyHephaestus(" .  $_SESSION['user_id'] . ", " . $_SESSION['gf2_id'] . ", '" . $_SESSION['gf2_type'] . "');");
            if (pg_fetch_all($query)[0]['buyhephaestus'] == 't')
            {
                echo 'Куплено';
            }
            else
            {
                echo 'Ошибка покупки';
            }
            break;
        }
        case "update_gf3":
        {
            $query = pg_query($db, "SELECT GetItemName(" . $_SESSION['gf3_id'] . ", '" . $_SESSION['gf3_type'] . "');");
            $name = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT GetItemDiamondCost(" . $_SESSION['gf3_id'] . ", '" . $_SESSION['gf3_type'] . "');");
            $diamonds = pg_fetch_row($query)[0];
            echo $name . "<br />" . "Стоимость: " . $diamonds . " алмазов.";
            break;
        }
        case "gf3_buy":
        {
            $query = pg_query($db, "SELECT BuyHephaestus(" .  $_SESSION['user_id'] . ", " . $_SESSION['gf3_id'] . ", '" . $_SESSION['gf3_type'] . "');");
            if (pg_fetch_all($query)[0]['buyhephaestus'] == 't')
            {
                echo 'Куплено';
            }
            else
            {
                echo 'Ошибка покупки';
            }
            break;
        }
        case "update_mr1":
        {
            $query = pg_query($db, "SELECT GetItemName(" . $_SESSION['mr1_id'] . ", '" . $_SESSION['mr1_type'] . "');");
            $name = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT GetItemDiamondCost(" . $_SESSION['mr1_id'] . ", '" . $_SESSION['mr1_type'] . "');");
            $diamonds = pg_fetch_row($query)[0];
            echo $name . "<br />" . "Стоимость: " . $diamonds . " алмазов.";
            break;
        }
        case "mr1_buy":
        {
            $query = pg_query($db, "SELECT BuyFromMerlin(" .  $_SESSION['user_id'] . ", " . $_SESSION['mr1_id'] . ");");
            if (pg_fetch_all($query)[0]['buyfrommerlin'] == 't')
            {
                echo 'Куплено';
            }
            else
            {
                echo 'Ошибка покупки';
            }
            break;
        }
        case "update_mr2":
        {
            $query = pg_query($db, "SELECT GetItemName(" . $_SESSION['mr2_id'] . ", '" . $_SESSION['mr2_type'] . "');");
            $name = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT GetItemDiamondCost(" . $_SESSION['mr2_id'] . ", '" . $_SESSION['mr2_type'] . "');");
            $diamonds = pg_fetch_row($query)[0];
            echo $name . "<br />" . "Стоимость: " . $diamonds . " алмазов.";
            break;
        }
        case "mr2_buy":
        {
            $query = pg_query($db, "SELECT BuyFromMerlin(" .  $_SESSION['user_id'] . ", " . $_SESSION['mr2_id'] . ");");
            if (pg_fetch_all($query)[0]['buyfrommerlin'] == 't')
            {
                echo 'Куплено';
            }
            else
            {
                echo 'Ошибка покупки';
            }
            break;
        }
        case "update_mr3":
        {
            $query = pg_query($db, "SELECT GetItemName(" . $_SESSION['mr3_id'] . ", '" . $_SESSION['mr3_type'] . "');");
            $name = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT GetItemDiamondCost(" . $_SESSION['mr3_id'] . ", '" . $_SESSION['mr3_type'] . "');");
            $diamonds = pg_fetch_row($query)[0];
            echo $name . "<br />" . "Стоимость: " . $diamonds . " алмазов.";
            break;
        }
        case "mr3_buy":
        {
            $query = pg_query($db, "SELECT BuyFromMerlin(" .  $_SESSION['user_id'] . ", " . $_SESSION['mr3_id'] . ");");
            if (pg_fetch_all($query)[0]['buyfrommerlin'] == 't')
            {
                echo 'Куплено';
            }
            else
            {
                echo 'Ошибка покупки';
            }
            break;
        }

        case "update_dd1":
        {
            $query = pg_query($db, "SELECT GetItemName(" . $_SESSION['dd1_id'] . ", '" . $_SESSION['dd1_type'] . "');");
            $name = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT GetItemDiamondCost(" . $_SESSION['dd1_id'] . ", '" . $_SESSION['dd1_type'] . "');");
            $diamonds = pg_fetch_row($query)[0];
            echo $name . "<br />" . "Стоимость: " . $diamonds . " алмазов.";
            break;
        }
        case "dd1_buy":
        {
            $query = pg_query($db, "SELECT BuyDiamondDealer(" .  $_SESSION['user_id'] . ", " . $_SESSION['dd1_id'] . ", '" . $_SESSION['dd1_type'] . "');");
            if (pg_fetch_all($query)[0]['buydiamonddealer'] == 't')
            {
                echo 'Куплено';
            }
            else
            {
                echo 'Ошибка покупки';
            }
            break;
        }
        case "update_dd2":
        {
            $query = pg_query($db, "SELECT GetItemName(" . $_SESSION['dd2_id'] . ", '" . $_SESSION['dd2_type'] . "');");
            $name = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT GetItemDiamondCost(" . $_SESSION['dd2_id'] . ", '" . $_SESSION['dd2_type'] . "');");
            $diamonds = pg_fetch_row($query)[0];
            echo $name . "<br />" . "Стоимость: " . $diamonds . " алмазов.";
            break;
        }
        case "dd2_buy":
        {
            $query = pg_query($db, "SELECT BuyDiamondDealer(" .  $_SESSION['user_id'] . ", " . $_SESSION['dd2_id'] . ", '" . $_SESSION['dd2_type'] . "');");
            if (pg_fetch_all($query)[0]['buydiamonddealer'] == 't')
            {
                echo 'Куплено';
            }
            else
            {
                echo 'Ошибка покупки';
            }
            break;
        }
        case "update_dd3":
        {
            $query = pg_query($db, "SELECT GetItemName(" . $_SESSION['dd3_id'] . ", '" . $_SESSION['dd3_type'] . "');");
            $name = pg_fetch_row($query)[0];
            $query = pg_query($db, "SELECT GetItemDiamondCost(" . $_SESSION['dd3_id'] . ", '" . $_SESSION['dd3_type'] . "');");
            $diamonds = pg_fetch_row($query)[0];
            echo $name . "<br />" . "Стоимость: " . $diamonds . " алмазов.";
            break;
        }
        case "dd3_buy":
        {
            $query = pg_query($db, "SELECT BuyDiamondDealer(" .  $_SESSION['user_id'] . ", " . $_SESSION['dd3_id'] . ", '" . $_SESSION['dd3_type'] . "');");
            if (pg_fetch_all($query)[0]['buydiamonddealer'] == 't')
            {
                echo 'Куплено';
            }
            else
            {
                echo 'Ошибка покупки';
            }
            break;
        }
        case "update_dm1":
        {
            $query = pg_query($db, "SELECT GetItemName(" . $_SESSION['dm1_id'] . ", 'DUNGEON MASTER');");
            $name = pg_fetch_row($query)[0];
            echo $name . "<br />" . "Стоимость: " . $_SESSION['dm1_price'] . " алмазов.";
            break;
        }
        case "dm1_buy":
        {
            $query = pg_query($db, "SELECT BuyDungeonMaster(" .  $_SESSION['user_id'] . ", " . $_SESSION['dm1_id'] . ");");
            if (pg_fetch_all($query)[0]['buydungeonmaster'] == 't')
            {
                echo 'Куплено';
            }
            else
            {
                echo 'Ошибка покупки';
            }
            break;
        }
        case "update_dm2":
        {
            $query = pg_query($db, "SELECT GetItemName(" . $_SESSION['dm2_id'] . ", 'DUNGEON MASTER');");
            $name = pg_fetch_row($query)[0];
            echo $name . "<br />" . "Стоимость: " . $_SESSION['dm2_price'] . " алмазов.";
            break;
        }
        case "dm2_buy":
        {
            $query = pg_query($db, "SELECT BuyDungeonMaster(" .  $_SESSION['user_id'] . ", " . $_SESSION['dm2_id'] . ");");
            if (pg_fetch_all($query)[0]['buydungeonmaster'] == 't')
            {
                echo 'Куплено';
            }
            else
            {
                echo 'Ошибка покупки';
            }
            break;
        }
        case "update_dm3":
        {
            $query = pg_query($db, "SELECT GetItemName(" . $_SESSION['dm3_id'] . ", 'DUNGEON MASTER');");
            $name = pg_fetch_row($query)[0];
            echo $name . "<br />" . "Стоимость: " . $_SESSION['dm3_price'] . " алмазов.";
            break;
        }
        case "dm3_buy":
        {
            $query = pg_query($db, "SELECT BuyDungeonMaster(" .  $_SESSION['user_id'] . ", " . $_SESSION['dm3_id'] . ");");
            if (pg_fetch_all($query)[0]['buydungeonmaster'] == 't')
            {
                echo 'Куплено';
            }
            else
            {
                echo 'Ошибка покупки';
            }
            break;
        }
        case "start_new_game":
        {
            $query = pg_query($db, "DELETE FROM Player_storage WHERE Player_storage.PLAYER_ID=(SELECT Player.PLAYER_ID FROM Player WHERE NAME='" . $_GET['username'] . "');");
            $query = pg_query($db, "UPDATE Player SET HEALTH_POINTS=3 WHERE NAME='" . $_GET['username'] . "';");
            $query = pg_query($db, "UPDATE Player SET GOLD=100 WHERE NAME='" . $_GET['username'] . "';");
            echo "cleaned";
            break;
        }
        default:
            echo "Error";
    }

    close_database($db);
