<?php 
	
	require_once('conexao.php');
	
	$comentario = $_GET['comentario'];
	$id_usuario = $_GET['id_usuario'];
	
	$sql = "insert into tbl_faleconosco(comentario, id_usuario) values('".$comentario."','".$id_usuario."')"
	
	echo $sql;
	
	mysqli_query($conexao, $sql);
	
	mysqli_close($conexao);
?>