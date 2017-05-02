<?php
	require_once('conexao.php');

	$id_usuario = $_GET['id_usuario'];
	$preco = $_GET['preco'];
	$horaInicio = $_GET['horaInicio'];
	$horaFim = $_GET['horaFim'];
	$diasDisponiveis = $_GET['diasDisponiveis'];
	
	$preco = str_replace(',', '.', $preco);
	
	$sql = "INSERT INTO tbl_babas(id_usuario, preco, horaInicio, horaFim, diasDisponiveis) VALUES('".$id_usuario."', '".$preco."', '".$horaInicio."', '".$horaFim."', '".$diasDisponiveis."');";
	mysqli_query($conexao, $sql);
	
	$sql = "UPDATE tbl_usuarios SET statusBaba=1 WHERE id_usuario=".$id_usuario.";";
	mysqli_query($conexao, $sql);
	
	mysqli_close($conexao);
?>
