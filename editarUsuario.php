<?php
	require_once('conexao.php');

	$id_usuario = $_GET['id_usuario'];
	$nome = $_GET['nome'];
	$sexo = $_GET['sexo'];
	$telefone = $_GET['telefone'];
	$email = $_GET['email'];
	$data_nascimento = $_GET['data_nascimento'];
	$cidade = $_GET['cidade'];
	$logradouro = $_GET['logradouro'];
	
	$nome = str_replace('_', ' ', $nome);
	$logradouro = str_replace('_', ' ', $logradouro);
	
	$sql = "UPDATE tbl_usuarios SET nome='".$nome."', sexo='".$sexo."', telefone='".$telefone."', email='".$email."', data_nascimento='".$data_nascimento."', logradouro='".$logradouro."', idCidade='".$cidade."' WHERE id_usuario=".$id_usuario.";"; 
	
	echo $sql;
	mysqli_query($conexao, $sql);
	
	mysqli_close($conexao);
?>
