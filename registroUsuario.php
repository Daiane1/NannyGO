<?php
	require_once('conexao.php');

	$nome = $_GET['nome'];
	$login = $_GET['login'];
	$senha = $_GET['senha'];
	$sexo = $_GET['sexo'];
	$telefone = $_GET['telefone'];
	$email = $_GET['email'];
	$data_nascimento = $_GET['data_nascimento'];
	$cidade = $_GET['cidade'];
	$logradouro = $_GET['logradouro'];
	
	$nome = str_replace('_',' ',$nome);
	$logradouro = str_replace('_', ' ', $logradouro);
	
	if ($sexo == 'M')
	{
		$imagem = 'R.drawable.babyM';
	}
	else
	{
		$imagem = 'R.drawable.babyF';
	}
	
	$sql = "INSERT INTO tbl_usuarios(nome, login, senha, sexo, telefone, email, data_nascimento, idCidade, logradouro) VALUES('".$nome."','".$login."','".$senha."','".$sexo."','".$telefone."','".$email."','".$data_nascimento."', '".$cidade."', '".$logradouro."');";
	
	echo $sql;
	
	mysqli_query($conexao, $sql);
	
	mysqli_close($conexao);
?>