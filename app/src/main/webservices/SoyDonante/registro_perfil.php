<?php
header('Content-Type: text/html; charset=utf-8');
error_reporting(0);

require_once __DIR__ . '/db_connect.php';


$db = new DB_CONNECT();
$response= array();
//datos cita para la donacion
if (isset($_GET["username"]) && isset($_GET["password"]) && isset($_GET["email"]) )  {
	
	$username = $_GET["username"];
	$password = $_GET["password"];
	$email = $_GET["email"];
	$idUser="";

	$sql = sprintf("INSERT ignore into usuario (username, password, email) values ('%s','%s','%s')",$username, $password, $email); 
	$result = mysql_query($sql) or die(mysql_error());

	$sql = sprintf("SELECT id from usuario where username='%s'",$username);
	$result = mysql_query($sql) or die(mysql_error());

	while($row = mysql_fetch_array($result)){
		$idUser= $row[0];
	}
	if($idUser!=""){
		$sql = sprintf("INSERT into perfil (idUser,nombres,email) values ('%s','%s','%s')",$idUser,$username, $email); 
		$result = mysql_query($sql) or die(mysql_error());
	}else{

	}


	$response["success"] = 1;

	echo json_encode($response);
}else{
	echo "Se modificaron los valores correctamente";
}
