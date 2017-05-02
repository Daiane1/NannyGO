<?php
	require_once('conexao.php');
	$sql = "SELECT * FROM tbl_usuarios as a, tbl_babas as b WHERE a.id_usuario = b.id_usuario;";
	
	$select = mysqli_query($conexao, $sql);
	
	$array = array();
	while($resultado=mysqli_fetch_array($select))
	{
		$baba = array(
			"idBaba"=>$resultado['id_baba'],
			"idUsuario"=>$resultado['id_usuario'],
			"nome"=>$resultado['nome'],
			"preco"=>$resultado['preco'],
			"horaInicio"=>$resultado['horaInicio'],
			"horaFim"=>$resultado['horaFim'],
			"diasDisponiveis"=>$resultado['diasDisponiveis'],
			"imagem"=>$resultado['imagem']);
		$array[] = $baba;
	}
	
	$arrayJSON = json_encode($array);
	
	echo($arrayJSON);
	
	mysqli_close($conexao);




?>