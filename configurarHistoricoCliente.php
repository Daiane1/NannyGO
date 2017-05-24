<?php
	require_once('conexao.php');
	$idorigem = $_GET['origem'];
	
	$sql = "SELECT t.valor,
					u.nome
					FROM 
					tbl_transacao as t
					inner join
					tbl_usuario as u
					on t.idUsuario = t.idUsuario
					;";
	
	$selectHistoricoCliente = mysqli_query($conexao, $sql);
	
	$array = array();
	
	while($resultado=mysqli_fetch_array($selectHistoricoCliente)){
		$HistoricoCliente = array(
			"idTransacao"=>$resultado['id_transacao'],
			"idUsuario"=>$resultado['id_usuario'],
			"idBaba"=>$resultado['id_baba'],
			"nome"=>$resultado['nome'],
			"valor"=>$resultado['valor'],
		$array[] = $HistoricoCliente;
	}
	
	usort($array, function($a, $b){
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