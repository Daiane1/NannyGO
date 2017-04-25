<?php
	$sql = "SELECT * FROM tbl_babas;";
	
	$conexao = mysql_connect('localhost', 'root', 'bcd127');
	mysql_select_db('db_nannygo');
	
	$select = mysql_query($sql);
	
	$array = array();
	while($resultado=mysql_fetch_array($select))
	{
		$baba = array(
			"idBaba"=>$resultado['id_baba'],
			"idUsuario"=>$resultado['id_usuario'],
			"preco"=>$resultado['preco'],
			"horaInicio"=>$resultado['horaInicio'],
			"horaFim"=>$resultado['horaFim'],
			"diasDisponiveis"=>$resultado['diasDisponiveis']);
		$array[] = $baba;
	}
	
	$arrayJSON = json_encode($array);
	
	echo($arrayJSON);




?>