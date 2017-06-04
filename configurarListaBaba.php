<?php
	require_once('conexao.php');
	$idorigem = $_GET['origem'];
	$id_usuario = $_GET['id_usuario'];
	
	$sql = "SELECT * FROM vw_lista_babas WHERE id_usuario!=".$id_usuario.";";
	
	$selectBaba = mysqli_query($conexao, $sql);
	
	$array = array();
	while($resultado=mysqli_fetch_array($selectBaba))
	{
		$sql = "SELECT nome FROM tbl_cidade WHERE id_cidade=".$idorigem.";";
		$select = mysqli_query($conexao, $sql);
		
		if($rs=mysqli_fetch_array($select))
		{
			$origem = $rs['nome'];
		}
		$origem = str_replace(" ", "%20", $origem);
		
		$iddestino = $resultado['idCidade'];
		
		$sql = "SELECT nome FROM tbl_cidade WHERE id_cidade=".$iddestino.";";
		$select = mysqli_query($conexao, $sql);
		
		
		if($rs=mysqli_fetch_array($select))
		{
			$destino = $rs['nome'];
		}
		$destino = str_replace(" ", "%20", $destino);
		
		$url = "http://maps.googleapis.com/maps/api/distancematrix/xml?origins=".$origem."&destinations=".$destino."&mode=driving&language=pt-BR&sensor=false";

		$xml = simplexml_load_file($url);
		
		$distancia = explode('"',$xml->row[0]->element->distance->value);
		
		$baba = array(
			"distancia"=>$distancia[0],
			"idBaba"=>$resultado['id_baba'],
			"idUsuario"=>$resultado['id_usuario'],
			"nome"=>$resultado['nome'],
			"preco"=>$resultado['preco'],
			"horaInicio"=>$resultado['horaInicio'],
			"horaFim"=>$resultado['horaFim'],
			"diasDisponiveis"=>$resultado['diasDisponiveis'],
			"idCidade"=>$resultado['idCidade'],
			"login"=>$resultado['login'],
			"sexo"=>$resultado['sexo']);	
		$array[] = $baba;
	}
	
	usort($array, function($a, $b)
	{
		if ($a == $b)
		{
			return 0;
		}
		else if ($a > $b)
		{
			return 1;
		}
		else {              
			return -1;
		}
	});
	
	$arrayJSON = json_encode($array);
	
	echo($arrayJSON);
	
	mysqli_close($conexao);
?>