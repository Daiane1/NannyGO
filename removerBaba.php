<?php
	require_once('conexao.php');
	
	$id_usuario = $_GET['id_usuario'];
	
	$sql = "DELETE FROM tbl_babas WHERE id_usuario=".$id_usuario.";";
	mysqli_query($conexao, $sql);
	
	$sql = "UPDATE tbl_usuarios SET statusBaba=0 WHERE id_usuario=".$id_usuario.";";
	mysqli_query($conexao, $sql);
	
	mysqli_close($conexao);


?>