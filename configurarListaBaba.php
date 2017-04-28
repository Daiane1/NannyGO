<?php
	$sql = "SELECT * FROM tbl_usuarios as a, tbl_babas as b WHERE a.id_usuario = b.id_usuario;";
	
	$conexao = mysql_connect('localhost', 'root', 'bcd127');
	mysql_select_db('db_nannygo');
	
	$select = mysql_query($sql);
	
	$array = array();
	while($resultado=mysql_fetch_array($select))
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




?>