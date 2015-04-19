<?php
header('Content-Type: text/html; charset=utf-8');
error_reporting(0);

require_once __DIR__ . '/db_connect.php';


$db = new DB_CONNECT();
$response= array();
$response["dataPerfil"]= array();
if (isset($_POST["idUser"])) {
	$idUser=$_POST["idUser"];
	$sql = sprintf("SELECT p.nombres, p.apellidos, p.tipoSangre, p.email, p.telefono, p.cuentaFace, p.cuentaTwitt, p.cuentaGoogle, p.fondo, p.foto from perfil p left join usuario u on p.idUser= u.id where p.idUser=%s",$idUser);
	$result = mysql_query($sql) or die(mysql_error());
	while ($row = mysql_fetch_array($result)) {
	$data_perfil = array();
	$data_perfil["nombres"]= $row["nombres"];
	$data_perfil["apellidos"]= $row["apellidos"];
	$data_perfil["tipoSangre"]= $row["tipoSangre"];
	$data_perfil["email"]= $row["email"];
	$data_perfil["telefono"]= $row["telefono"];
	$data_perfil["cuentaFace"]= $row["cuentaFace"];
	$data_perfil["cuentaTwitt"]= $row["cuentaTwitt"];
	$data_perfil["cuentaGoogle"]= $row["cuentaGoogle"];
	$data_perfil["fondo"]= $row["fondo"];
	$data_perfil["foto"]= $row["foto"];
	
	array_push($response["dataPerfil"], $data_perfil);
	}

	$response["success"] = 1;
	echo json_encode($response);
}else{
	echo "No se creo la tabla o no se insertaron los valores correctamente";
}
