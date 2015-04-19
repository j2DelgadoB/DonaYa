<?php
header('Content-Type: text/html; charset=utf-8');
error_reporting(0);

require_once __DIR__ . '/db_connect.php';


$db = new DB_CONNECT();
$response= array();
if (isset($_GET["idUser"]) && isset($_GET["idAmigo"]) && isset($_GET["solicitud"]) ) {
	$idUser=$_GET["idUser"];
	$idAmigo=$_GET["idAmigo"];
	$solicitud=$_GET["solicitud"];
	$sql = sprintf("INSERT INTO contacto (idUser,idAmigo,solicitud) values('%s','%s','%s')",$idUser,$idAmigo,$solicitud);
	$result = mysql_query($sql) or die(mysql_error());

	$response["success"] = 1;
	echo json_encode($response);

}else{
	echo "No se insertaron los valores correctamente";
}