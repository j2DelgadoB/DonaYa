<?php
header('Content-Type: text/html; charset=utf-8');
error_reporting(0);

require_once __DIR__ . '/db_connect.php';


$db = new DB_CONNECT();
$response= array();
//datos cita para la donacion
if (isset($_GET["nombres"]) && isset($_GET["apellidos"]) && isset($_GET["tipoSangre"]) && isset($_GET["idUser"]) && isset($_GET["telefono"]) && isset($_GET["email"]) ) {
	
	$idUser = $_GET["idUser"];

	$nombres = $_GET["nombres"];
	$apellidos = $_GET["apellidos"];
	$tipoSangre = $_GET["tipoSangre"];
	$email = $_GET["email"];
	$telefono = $_GET["telefono"];


	$sql = sprintf("UPDATE perfil set nombres='%s', apellidos='%s', tipoSangre='%s', email='%s', telefono='%s' where idUser='%s'",$nombres,$apellidos,$tipoSangre,$email,$telefono,$idUser); 
	$result = mysql_query($sql) or die(mysql_error());

	$response["success"] = 1;

	echo json_encode($response);
}else{
	echo "Se modificaron los valores correctamente";
}
