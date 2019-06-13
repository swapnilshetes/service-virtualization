<?php
session_start();

define('DB_SERVER', 'localhost');
define('DB_USERNAME', 'root');
define('DB_PASSWORD', '');
define('DB_DATABASE', 'ionic_db_api');
define('BASE_URL', 'http://localhost/ionic-api/');
define('SITE_KEY', 'localhost');

function getDB() {

	$dbhost = DB_SERVER ;
	$dbuser = DB_USERNAME ;
	$dbpass = DB_PASSWORD ;
	$dbname = DB_DATABASE ;
	try{
		$dbconnection = new PDO("mysql:dbname=ionic_db_api;host=localhost",'root','');
		$dbconnection->exec("set name utf8");
		$dbconnection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	} catch( PDOException  $e ) {
		echo 'Connection failed: ' . $e->getMessage();
	}
	return $dbconnection;
}


function apiKey($session_uid) {
	$key = md5(SITE_KEY, $session_uid);
	return hash('sha256', $key);
}
?>

