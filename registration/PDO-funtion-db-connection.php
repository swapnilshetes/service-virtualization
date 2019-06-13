<?php

require "config.php";
//$data = $_POST;
$data['postuser'] = $_REQUEST['username'] ; // 'swapnils';
$data['postpass'] = $_REQUEST['password'] ; //'Feb@2018';
$data['postemail'] =$_REQUEST['email'] ; //"swapnil.shete@bitwiseglobal.com";
login($data);

function login($data) {
/* 	$username= "swapnils";
	$password = "Feb@2018";
	$email = "swapnil.shete@bitwiseglobal.com"; */
	$data_postuser = 'swapnils';
$data_postpass = 'Feb@2018';
$data_postemail = "swapnil.shete@bitwiseglobal.com";
	try{
			//$sql = "select user_id , email, username from user where username=:username1";
			//$sql = "select user_id , email, username from user";
			//$stmt->bindParam(":username1", $data_postuser , PDO::PARAM_STR);
			//print_r( $stmt );
			//$stmt->bindParam("email", $data_postpass, PDO::PARAM_STR);
			//echo $password = $data['postpass']; //hash("sha256", $data);
			//$stmt->bindParam("password", $data_postemail , PDO::PARAM_STR);
			//print_r($stmt->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION));
			//echo "<p>". $db->errorCode() ."</p>";
			//echo "<pre>";
			//
			//echo print_r($db->errorInfo() ,true);

			//echo "</pre>";
			//print_r($stmt->debugDumpParams());
			
			$db = getDB();
			$userData = '';
			//$sql = 'select user_id , email, username from user where ( username= "$data['postuser']" or email= "$data['postemail']" ) and password="$data['postpass']"';
			
			//$sql = "select user_id , email, username from user";
			
			$sql = "select user_id , email, username from user where ( username= '".$data['postuser']."' or email= '" .$data['postemail']."' ) and password= '".$data['postpass']."'";
			
			 $stmt = $db->prepare($sql , array(PDO::MYSQL_ATTR_USE_BUFFERED_QUERY => false));
			//$stmt->setAttribute(PDO::MYSQL_ATTR_USE_BUFFERED_QUERY, false);
			//$stmt->bindParam(":username", $data_postuser, PDO::PARAM_STR);
			//$stmt->bindParam(":email", $data_postemail, PDO::PARAM_STR);
			//$stmt->bindParam("password", $data_postpass, PDO::PARAM_STR);
			
			
			$stmt->execute();
			//$maincout  = 	 $stmt->rowCount();
			
			$userData =  $stmt->fetch(PDO::FETCH_OBJ);
			
			if(!empty($userData)) {
				$user_id = $userData->user_id;
				//$userData->token = apiKey($user_id);
			}
			
			$db = null ;
			
			echo '{"userData":' . json_encode($userData) .'}';
	} catch(PDOException $e) {
			echo '{"error":{"text":' . json_encode($e->getMessage()) .'}}';
	}
	
}
?>