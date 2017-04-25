<?php
	$nome = $_GET['nome'];
	$login = $_GET['login'];
	$senha = $_GET['senha'];
	$sexo = $_GET['sexo'];
	$telefone = $_GET['telefone'];
	$email = $_GET['email'];
	$data_nascimento = $_GET['data_nascimento'];
	$imagem = "";
	
	$nome = str_replace('_',' ',$nome);
	if ($sexo == 'M')
	{
		$imagem = 'R.drawable.babyM';
	}
	else
	{
		$imagem = 'R.drawable.babyF';
	}
	
	$sql = "INSERT INTO tbl_usuarios(nome, login, senha, sexo, telefone, email, data_nascimento, idCidade, imagem) VALUES('".$nome."','".$login."','".$senha."','".$sexo."','".$telefone."','".$email."','".$data_nascimento."', 1, '".$imagem."');";
	$conexao = mysql_connect('localhost', 'root', 'bcd127');
	mysql_select_db('db_nannygo');
	echo($sql);
	
	mysql_query($sql);
?>