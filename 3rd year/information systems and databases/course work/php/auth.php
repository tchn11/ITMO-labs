<?php
    include 'db_functions.php';
    $db = get_opened_database();
    $result = pg_query($db, 'SELECT * FROM player WHERE name = \'' . $_GET["usr"] . '\';');

    $num_of_rows = count(pg_fetch_all($result));

    if (isset($_GET["reg"]))
    {
        if ($num_of_rows != 0)
        {
            echo "Error: username exists";
        }
        else
        {
            $result = pg_query($db, 'INSERT INTO player 
                    (PASSWORD, NAME, HEALTH_POINTS, GOLD, DIAMONDS, SUCCESS_CHANCE) VALUES 
                    (\''. $_GET["passwd"] . '\', \'' . $_GET["usr"] . '\', 3, 100, 5, 0)');

            $_SESSION['password'] = $_GET["passwd"];
            $_SESSION['username'] = $_GET["usr"];

            echo 'OK';
        }
    }
    else
    {
        if ($num_of_rows == 0)
        {
            echo "Error: user do not exists";
        }
        else
        {
            $row = pg_fetch_row($result);
            if ($_GET["passwd"] == $row[1])
            {
                $_SESSION['password'] = $_GET["passwd"];
                $_SESSION['username'] = $_GET["usr"];
                echo "OK";
            }
            else
            {
                echo "Error: wrong password";
            }
        }
    }

    close_database($db);