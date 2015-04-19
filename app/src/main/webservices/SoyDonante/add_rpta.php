<?php
header('Content-Type: text/html; charset=utf-8');
error_reporting(0);

require_once __DIR__ . '/db_connect.php';


$db = new DB_CONNECT();
$response= array();

if (isset($_POST["idPost"]) && isset($_POST["newRpta"])) {
	$idPost = $_POST["idPost"];
	$newRpta = $_POST["newRpta"];
	//$idPost = parseInt($idPost);	

	$sql = sprintf("INSERT INTO respuesta (idPost, msjRespuesta) values ('%s','%s')",$idPost,$newRpta); 
	$result = mysql_query($sql) or die(mysql_error());


	
	$response["success"] = 1;

	echo json_encode($response);
}else{
	echo "No se creo la tabla o no se insertaron los valores de la tabla respuesta correctamente";
}
