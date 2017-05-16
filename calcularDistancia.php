<?php
	require_once('conexao.php');
	$origem = $_GET['origem'];
	$destino = $_GET['destino'];
	
	$sql = "SELECT nome FROM tbl_cidade WHERE id_cidade=".$origem.";";
	$select = mysqli_query($conexao, $sql);
	
	if($rs=mysqli_fetch_array($select))
	{
		$origem = $rs['nome'];
	}
	
	$sql = "SELECT nome FROM tbl_cidade WHERE id_cidade=".$destino.";";
	$select = mysqli_query($conexao, $sql);
	
	if($rs=mysqli_fetch_array($select))
	{
		$destino = $rs['nome'];
	}
	
	$url = "http://maps.googleapis.com/maps/api/distancematrix/xml?origins=".$origem."&destinations=".$destino."&mode=driving&language=pt-BR&sensor=false";

	$xml = simplexml_load_file($url);
	
	$distancia = $xml->row[0]->element->distance->value;
	
	echo($distancia);
?>