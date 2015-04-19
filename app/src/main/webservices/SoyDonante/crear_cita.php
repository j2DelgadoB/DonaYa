<?php
header('Content-Type: text/html; charset=utf-8');
error_reporting(0);

require_once __DIR__ . '/db_connect.php';


$db = new DB_CONNECT();
$response= array();
//datos cita para la donacion
if (isset($_GET["nombres"]) && isset($_GET["apellidos"]) && isset($_GET["tipoSangre"]) && isset($_GET["idUser"]) && isset($_GET["telefono"]) && isset($_GET["email"]) && isset($_GET["distrito"]) && isset($_GET["fecha"]) && isset($_GET["hora"]) ) {
	
	$idUser = $_GET["idUser"];

	$distrito = $_GET["distrito"];
	$fecha = $_GET["fecha"];
	$hora = $_GET["hora"];

	$nombres = $_GET["nombres"];
	$apellidos = $_GET["apellidos"];
	$tipoSangre = $_GET["tipoSangre"];
	$email = $_GET["email"];
	$telefono = $_GET["telefono"];
	

	/*$sql = sprintf("create table if not exists cita(id int(11) not null AUTO_INCREMENT, idUser int(11) not null,distrito varchar(350) not null,fechaCita DATE not null,horaCita TIME not null, nomCitaDonante varchar(250) not null, apeCitaDonante varchar(250) not null, tipoSangre varchar(75) not null, email varchar(200) not null, telefono varchar(100) not null, PRIMARY KEY (id),FOREIGN KEY (idUser) REFERENCES usuario(id))");
	$result = mysql_query($sql) or die(mysql_error());*/
	//$sql = sprintf("alter table post add foreign key(idUser) references usuario(id) ")

	$sql = sprintf("INSERT INTO cita (idUser,distrito,fechaCita,horaCita,nomCitaDonante,apeCitaDonante,tipoSangre,email,telefono) values ('%s','%s','%s','%s','%s','%s','%s','%s','%s')",$idUser,$distrito,$fecha,$hora,$nombres,$apellidos,$tipoSangre,$email,$telefono); 
	$result = mysql_query($sql) or die(mysql_error());


	
	$response["success"] = 1;

	echo json_encode($response);
}else{
	echo "No se creo la tabla o no se insertaron los valores correctamente";
}




