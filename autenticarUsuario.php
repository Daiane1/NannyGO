<?php
	$login = $_GET['login'];
	$senha = $_GET['senha'];
	
	$sql = "SELECT * FROM tbl_usuarios WHERE login='".$login."' and senha='".$senha."';";
	
	$conexao = mysql_connect('localhost', 'root', 'bcd127');
	mysql_select_db('db_nannygo');
	
	$select = mysql_query($sql);
	
	if ($rs=mysql_fetch_array($select))
	{
		$usuario = array(
			"nome"=>$rs['nome'],
			"sexo"=>$rs['sexo'],
			"telefone"=>$rs['telefone'],
			"email"=>$rs['email'],
			"login"=>$rs['login'],
			"senha"=>$rs['senha'],
			"dataNascimento"=>$rs['data_nascimento']);
			
		$usuarioJSON = json_encode($usuario);
		
		echo($usuarioJSON);
	}
	
?>
