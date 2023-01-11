<?php
    session_start();
    $_SESSION['password'] = '';
    $_SESSION['username'] = '';
    $_SESSION['floor'] = 1;
    readfile('auth.html');