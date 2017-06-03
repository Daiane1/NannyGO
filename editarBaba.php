<?php
	require_once('conexao.php');

	$id_usuario = $_GET['id_usuario'];
	$preco = $_GET['preco'];
	$horaInicio = $_GET['horaInicio'];
	$horaFim = $_GET['horaFim'];
	$diasDisponiveis = $_GET['diasDisponiveis'];
	
	$preco = str_replace(',', '.', $preco);
	$diasDisponiveis = str_replace('_', ' ', $diasDisponiveis);
	
	$sql = "UPDATE tbl_babas SET preco='".$preco."', horaInicio='".$horaInicio."', horaFim='".$horaFim."', diasDisponiveis='".$diasDisponiveis."' WHERE id_usuario=".$id_usuario.";"; 
	mysqli_query($conexao, $sql);
	
	mysqli_close($conexao);
?>
