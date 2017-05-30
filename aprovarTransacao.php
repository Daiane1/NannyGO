<?php
	require_once('conexao.php');
	$id_transacao = $_GET['id_transacao'];
	
	$sql = "UPDATE tbl_transacoes SET status_aprovado=1 WHERE id_transacao=".$id_transacao."";
	mysqli_query($conexao, $sql);
	
	mysqli_close($conexao);
?>