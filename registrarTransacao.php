<?php
	require_once('conexao.php');

	$id_usuario = $_GET['id_usuario'];
	$id_baba = $_GET['id_baba'];
	$metodo_pagamento = $_GET['metodo_pagamento'];
	$valor = $_GET['valor'];
	$data_servico = $_GET['data_servico'];
	$hora_inicio = $_GET['hora_inicio'];
	$qntd_horas = $_GET['qntd_horas'];
	
	date_default_timezone_set('America/Sao_Paulo');
	$data = date('Y-m-d');
	$valor = str_replace(',', '.', $valor);
	
	
	$sql = "INSERT INTO tbl_transacoes(id_usuario, id_baba, data_transacao, metodo_pagamento, valor, data_servico, hora_inicio, qntd_horas) VALUES('".$id_usuario."', '".$id_baba."', '".$data."', '".$metodo_pagamento."', '".$valor."', '".$data_servico."', '".$hora_inicio."', '".$qntd_horas."');";
	mysqli_query($conexao, $sql);
	
	echo $sql;
	
	mysqli_close($conexao);
?>
