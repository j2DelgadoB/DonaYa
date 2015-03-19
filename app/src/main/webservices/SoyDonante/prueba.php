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
	
	

?>