<?php
header('Content-Type: text/html; charset=utf-8');
error_reporting(0);

require_once __DIR__ . '/db_connect.php';

$db = new DB_CONNECT();



$response= array();
$response["usuario"]= array();
if (isset($_POST["ausuario"]) && isset($_POST["acontrasena"])) {
	$usuario=$_POST["ausuario"];
	$contrasena=$_POST["acontrasena"];
	
	$sql = sprintf("select * from usuario where username = '%s' and password = '%s'",$usuario, $contrasena);
	$result = mysql_query($sql) or die(mysql_error());
	$result = mysql_fetch_array($result);

	$data_user = array();
	
	$data_user["id"]= $result["id"];
	$data_user["user"]= $result["username"];
	$data_user["pass"]= $result["password"];
	$data_user["email"]= $result["email"];
	/*$data_user["id"]="1";
	$data_user["user"]=$_POST["ausuario"];
	$data_user["pass"]="1234";
	$data_user["numTel"]="14525454123";*/
	array_push($response["usuario"], $data_user);
	$response["success"] = 1;
	echo json_encode($response);
}else{
	echo "No envio Android";
}