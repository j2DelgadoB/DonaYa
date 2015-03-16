<?php
header('Content-Type: text/html; charset=utf-8');
error_reporting(0);

require_once __DIR__ . '/db_connect.php';


$db = new DB_CONNECT();
$response= array();
$response["msjsPost"]= array();
if (isset($_POST["mostrar_todo"])) {
	$sql = sprintf("SELECT a.id, a.idUser, a.msjSolicitud, b.msjRespuesta FROM post a left join respuesta b on a.id = b.idPost left join usuario u on a.idUser = u.id ORDER BY a.id DESC");
	$result = mysql_query($sql) or die(mysql_error());
	while ($row = mysql_fetch_array($result)) {
	$data_post_rpta = array();
	$data_post_rpta["idPost"]= $row["id"];
	$data_post_rpta["msjSolicitud"]= $row["msjSolicitud"];
	$data_post_rpta["msjRespuesta"]= $row["msjRespuesta"];
	array_push($response["msjsPost"], $data_post_rpta);
	}

	$response["success"] = 1;
	echo json_encode($response);
}else{
	echo "No se creo la tabla o no se insertaron los valores correctamente";
}
