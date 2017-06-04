<?php
	require_once('conexao.php');

	$id_usuario = $_GET['id_usuario'];
	
	$sql = "SELECT * FROM vw_usuario WHERE id_usuario='".$id_usuario."';";
	
	$select = mysqli_query($conexao, $sql);
	
	if ($rs=mysqli_fetch_array($select))
	{
		$usuario = array(
			"idUsuario"=>$rs['id_usuario'],
			"nome"=>$rs['nome'],
			"sexo"=>$rs['sexo'],
			"telefone"=>$rs['telefone'],
			"email"=>$rs['email'],
			"login"=>$rs['login'],
			"senha"=>$rs['senha'],
			"dataNascimento"=>$rs['data_nascimento'],
			"statusBaba"=>$rs['statusBaba'],
			"cidade"=>$rs['cidade'],
			"estado"=>$rs['estado'],
			"uf"=>$rs['uf'],
			"logradouro"=>$rs['logradouro']
			);
			
		$usuarioJSON = json_encode($usuario);
		
		echo($usuarioJSON);
	}
	
	mysqli_close($conexao);
	
?>
