<?php
    function get_opened_database()
    {
        $PG_STRING = 'host=localhost port=5432 dbname=pg user=postgres password=postgres';
        return pg_connect($PG_STRING);
    }

    function close_database($db)
    {
        pg_close($db);
    }