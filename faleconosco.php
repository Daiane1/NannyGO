<?php 
	require_once('conexao.php');
	
	$comentario = $_GET['comentario'];
	
	$comentario = str_replace('_', ' ', $comentario);
	
	$sql = "INSERT INTO tbl_faleconosco(comentario) values('".$comentario."')";
	
	mysqli_query($conexao, $sql);
	
	mysqli_close($conexao);
?>