<?php
	$id_usuario = $_GET['id_usuario'];
	$preco = $_GET['preco'];
	$horaInicio = $_GET['horaInicio'];
	$horaFim = $_GET['horaFim'];
	$diasDisponiveis = $_GET['diasDisponiveis'];
	
	$preco = str_replace(',', '.', $preco);
	$sql = "INSERT INTO tbl_babas(id_usuario, preco, horaInicio, horaFim, diasDisponiveis) VALUES('".$id_usuario."', '".$preco."', '".$horaInicio."', '".$horaFim."', '".$diasDisponiveis."');";
	
	$conexao = mysql_connect('localhost', 'root', 'bcd127');
	mysql_select_db('db_nannygo');
	
	mysql_query($sql);
?>
