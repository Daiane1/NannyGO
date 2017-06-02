<?php
	require_once('conexao.php');
	$id_usuario = $_GET['id_usuario'];
	
	$sql = "SELECT t.id_transacao, t.id_usuario, t.id_baba, t.data_transacao, t.status_aprovado, t.metodo_pagamento, t.valor, t.data_servico, u.nome, t.hora_inicio, t.qntd_horas FROM tbl_transacoes AS t INNER JOIN tbl_babas AS b ON b.id_baba=t.id_baba INNER JOIN tbl_usuarios AS u ON u.id_usuario=b.id_usuario WHERE t.id_usuario = ".$id_usuario." ORDER BY t.data_transacao DESC ;";
	$select = mysqli_query($conexao, $sql);
	
	$array = array();
	while($resultado=mysqli_fetch_array($select))
	{	
		$transacao = array(
			"idTransacao"=>$resultado['id_transacao'],
			"idUsuario"=>$resultado['id_usuario'],
			"idBaba"=>$resultado['id_baba'],
			"dataTransacao"=>$resultado['data_transacao'],
			"dataServico"=>$resultado['data_servico'],
			"statusAprovado"=>$resultado['status_aprovado'],
			"metodoPagamento"=>$resultado['metodo_pagamento'],
			"nome"=>$resultado['nome'],
			"valor"=>$resultado['valor'],
			"horaInicio"=>$resultado['hora_inicio'],
			"qntdHoras"=>$resultado['qntd_horas']);	
		$array[] = $transacao;
	}
	
	$arrayJSON = json_encode($array);
	
	echo($arrayJSON);
	
	mysqli_close($conexao);
?>