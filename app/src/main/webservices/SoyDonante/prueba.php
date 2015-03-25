<?php


require_once __DIR__ . '/db_connect.php';

$db = new DB_CONNECT();


	$idUser='1';
	$sql = sprintf("SELECT a.idUser, a.idAmigo, b.foto, b.nombres, b.apellidos,d.foto, d.nombres, d.apellidos FROM contacto a left join usuario c on a.idUser=c.id left join perfil b on c.id=b.idUser left join usuario u on a.idAmigo=u.id left join perfil d on u.id=d.idUser WHERE ((a.idUser='%s' or a.idAmigo='%s') and a.solicitud='aceptada')",$idUser,$idUser);
	$result = mysql_query($sql) or die(mysql_error());
	

	while($row = mysql_fetch_array($result)){
		echo '<pre>';
		print_r($row[7]);
		echo '</pre>';
	}
	
	$idUser="";
	$username="diego";

	$sql = sprintf("INSERT ignore into usuario (username, password, email) values ('diego','54321','ar@ap.com')"); 
	$result = mysql_query($sql) or die(mysql_error());

	$sql = sprintf("SELECT id from usuario where username='%s'",$username);
	$result = mysql_query($sql) or die(mysql_error());

	while($row = mysql_fetch_array($result)){
		$idUser= $row[0];
	}
	if($idUser!=""){
		$sql = sprintf("INSERT into perfil (idUser,nombres,email) values ('%s','%s','%s')",$idUser,$nombres, $email); 
		$result = mysql_query($sql) or die(mysql_error());
	}else{

	}
	

?>