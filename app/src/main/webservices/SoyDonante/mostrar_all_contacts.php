<?php
header('Content-Type: text/html; charset=utf-8');
error_reporting(0);
require_once __DIR__ . '/db_connect.php';

$db = new DB_CONNECT();
$response= array();
$response["ContactList"]= array();

if (isset($_POST["idUser"])) {
	$idUser=$_POST["idUser"];
	/////////////////////////0////////1
	$sql = sprintf("SELECT a.idUser, a.idAmigo, b.foto, b.nombres, b.apellidos,d.foto, d.nombres, d.apellidos FROM contacto a left join usuario c on a.idUser=c.id left join perfil b on c.id=b.idUser left join usuario u on a.idAmigo=u.id left join perfil d on u.id=d.idUser WHERE ((a.idUser='%s' or a.idAmigo='%s') and a.solicitud='aceptada')",$idUser,$idUser);
	$result = mysql_query($sql) or die(mysql_error());

	while ($row = mysql_fetch_array($result)) {
		$data_post_rpta = array();
		if (strcmp($row["idUser"],$_POST["idUser"]) == 0) {
			# entonces post idUser fue el receptor de solicitud y la acepto
			$data_post_rpta["idAmigo"]= $row["idAmigo"];
			//$data_post_rpta["fotoAmigo"]= $row[5];
			$data_post_rpta["nomAmigo"]= $row[6];
			$data_post_rpta["apeAmigo"]= $row[7];
		}else{
			#nuestro caso ejemplo
			# entonces post idUser fue el emisor de enviar la solicitud
			$data_post_rpta["idAmigo"]= $row["idUser"];
			//$data_post_rpta["fotoAmigo"]= $row[2];
			$data_post_rpta["nomAmigo"]= $row[3];
			$data_post_rpta["apeAmigo"]= $row[4];
		}
		array_push($response["ContactList"], $data_post_rpta);
	}
	$response["success"] = 1;
	echo json_encode($response);
}else{
	echo "No se creo la tabla o no se insertaron los valores correctamente";
}
