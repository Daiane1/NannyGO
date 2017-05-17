<?php
	require_once('conexao.php');

	$id_usuario = $_GET['id_usuario'];
	$preco = $_GET['preco'];
	$horaInicio = $_GET['horaInicio'];
	$horaFim = $_GET['horaFim'];
	$diasDisponiveis = $_GET['diasDisponiveis'];
	$id_cidade = $_GET['id_cidade'];
	
	$preco = str_replace(',', '.', $preco);
	$diasDisponiveis = str_replace('_', ' ', $diasDisponiveis);
	
	$sql = "INSERT INTO tbl_babas(id_usuario, preco, horaInicio, horaFim, diasDisponiveis, idCidade) VALUES('".$id_usuario."', '".$preco."', '".$horaInicio."', '".$horaFim."', '".$diasDisponiveis."', '".$id_cidade."');";
	mysqli_query($conexao, $sql);
	
	$sql = "UPDATE tbl_usuarios SET statusBaba=1 WHERE id_usuario=".$id_usuario.";";
	mysqli_query($conexao, $sql);
	
	mysqli_close($conexao);
?>
