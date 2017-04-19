<?php
	$nome = $_GET['nome'];
	$login = $_GET['login'];
	$senha = $_GET['senha'];
	$sexo = $_GET['sexo'];
	$telefone = $_GET['telefone'];
	$email = $_GET['email'];
	$data_nascimento = $_GET['data_nascimento'];
	
	$nome = str_replace('_',' ',$nome);
	
	$sql = "INSERT INTO tbl_usuarios(nome, login, senha, sexo, telefone, email, data_nascimento) VALUES('".$nome."','".$login."','".$senha."','".$sexo."','".$telefone."','".$email."','".$data_nascimento."');";
	$conexao = mysql_connect('localhost', 'root', 'bcd127');
	mysql_select_db('db_nannygo');
	
	mysql_query($sql);
?>