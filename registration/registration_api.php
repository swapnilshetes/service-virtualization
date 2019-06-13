<?php 
//body -> Row-> Json action {"apiAction" : "userRegistration" , "full_name" : "swapnil shete" , "email" : "swapnil.shete@bitwiseglobal.com" ,"company" : "Bitwise" ,"message" : "Test Data"} 


if (isset($_SERVER['HTTP_ORIGIN'])) {
    header("Access-Control-Allow-Origin: {$_SERVER['HTTP_ORIGIN']}");
    header('Access-Control-Allow-Credentials: true');
    header('Access-Control-Max-Age: 86400');    // cache for 1 day
}

// Access-Control headers are received during OPTIONS requests
if ($_SERVER['REQUEST_METHOD'] == 'OPTIONS') {

    if (isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_METHOD']))
        header("Access-Control-Allow-Methods: GET, POST, OPTIONS");         

    if (isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS']))
        header("Access-Control-Allow-Headers:  {$_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS']}");

    exit(0);
}

/*echo "<pre>";
print_r($_SERVER);
print_r( getallheaders() );
*/

class rest {
	
	public $dbh;
	
	public function __construct() {
		$dsn = 'mysql:host=localhost;dbname=ionic_db_api;';
		$user = 'root';
		$password = '';
		try {
		$this->dbh = new PDO($dsn, $user, $password);
		return; 
		} catch(PDOException $e ) {
			print "Error!: " . $e->getMessage() . "<br/>";
			die();
		}
		 
	}
	public function addUse( $set_name_param ) {
		
		$stmt = $this->dbh->prepare("insert into app_user (NAME) values ('$set_name_param')");
		
		$stmt->execute();
		
		$stmt = $this->dbh->prepare("SELECT * FROM app_user order by ID desc limit 1");
		
		$stmt->execute();
		
		return  $stmt->fetchAll();
	}
	
	
	
	public function setRegistration( $set_params ) {
		
		$full_name = $set_params["full_name"];
		$company = $set_params["company"];
		$job_title = $set_params["job_title"];
		$email = $set_params["email"];
		$message = $set_params["message"];
		
		//echo "insert into registration (	full_name,company,job_title,email,	message ) values ('$full_name' , '$company' , '$job_title' , '$email', '$message')" ;
		
		$stmt = $this->dbh->prepare("insert into registration (	full_name,company,job_title,email,	message ) values ('$full_name' , '$company' , '$job_title' , '$email', '$message')");
		
		$stmt->execute();
		
		$stmt = $this->dbh->prepare("SELECT * FROM registration order by id desc limit 1");
		
		$stmt->execute();
		
		return '{"registrationData":' . json_encode($stmt->fetchAll(PDO::FETCH_OBJ)) .'}';
		
	}
	
	
	public function getRegistration( $set_params ) {
		
		$id = $set_params["id"];
		
		$stmt = $this->dbh->prepare("SELECT * FROM registration where id= $id order by id desc limit 1");
		
		$stmt->execute();
		
		return '{"registration_info":' . json_encode($stmt->fetchAll(PDO::FETCH_OBJ)) .'}';
		
	}
	
	public function authUser( $validaterUser ) {
		/*
			$validaterUser['postuser'] = $_REQUEST['username'] ; // 'swapnils';
			$validaterUser['postpass'] = $_REQUEST['password'] ; //'Feb@2018';
			$validaterUser['postemail'] =$_REQUEST['email'] ; //"swapnil.shete@bitwiseglobal.com";
			
			$validaterUser['postuser'] =  'swapnils';
			$validaterUser['postpass'] = 'Feb@2018';
			$validaterUser['postemail'] ="swapnil.shete@bitwiseglobal.com";
		*/
		
		$sql = "select user_id , email, username from user where ( username= '".$validaterUser['username']."' or email= '" .$validaterUser['email']."' ) and password= '".$validaterUser['password']."'";
		
		$stmt = $this->dbh->prepare( $sql );
		$stmt->execute();
		$userData = $stmt->fetch(PDO::FETCH_OBJ);
		$this->dbh = null ;
		if(!empty($userData )) {
			return '{"userData":' . json_encode($userData) .'}';
		} else {
			return '{"error":{"text" : "bad Request"}}';
		}	
			
		
	}
	
	public function getJsonObject( $data) {
		return  json_encode ($data);
	}

	
}
$obj = new rest();

/**
*
* Parameter Send by json to back end
*
*/
$entityBody = file_get_contents('php://input');
//echo "<pre>>". $entityBody;
if(isset($_GET['apiAction']) && isset($_GET['id'])) {
	$post_ary = $_GET;
} else {
$post_ary = json_decode($entityBody, true);	
}

//echo "<pre>aaaa"; print_r($post_ary); exit;

/**
*
* Parameter Send by Normal Post to back end
*
*/

//echo "-----";
//print_r($_REQUEST);
//print_r($post_ary);
//echo $set_name = isset($post_ary['NAME']) ? $post_ary['NAME'] : isset($_REQUEST['NAME']) ? $_REQUEST['NAME'] : '';
//exit; 


/* $set_name = isset($post_ary['NAME']) ? $post_ary['NAME'] : '' ; 
//$set_name = isset($post_ary['NAME']) ? $post_ary['NAME'] : isset($_REQUEST['NAME']) ? $_REQUEST['NAME'] : '' ; 
$action = isset($post_ary['action']) ? $post_ary['action'] :  '' ; 
//$action = isset($post_ary['action']) ? $post_ary['action'] : isset($_REQUEST['action']) ? $_REQUEST['action'] : '' ; 

	if( $action =='add') {
		$result = $obj->addUse( $set_name );
	} else {
		$result = $obj->getAll();
	}
	 */

	 
	 
if(isset($post_ary ['apiAction']) && $post_ary ['apiAction']=="userRegistration"){
	$resultJson = $obj->setRegistration( $post_ary );
}elseif(isset($post_ary ['apiAction']) && $post_ary ['apiAction']=="getUserInfo") {
	$resultJson = $obj->getRegistration( $post_ary );
} else{
	 $resultJson = $obj->getAllIdeas( $post_ary );
}	 
	
	
		
echo $resultJson;
?>
