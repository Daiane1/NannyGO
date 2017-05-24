<?php
	require_once('conexao.php');

	$id_usuario = $_GET['id_usuario'];
	$id_baba = $_GET['id_baba'];
	$metodo_pagamento = $_GET['metodo_pagamento'];
	$valor = $_GET['valor'];
	$data_servico = $_GET['data_servico'];
	
	date_default_timezone_set('America/Sao_Paulo');
	$data = date('Y-m-d');
	
	
	$sql = "INSERT INTO tbl_transacoes(id_usuario, id_baba, data_transacao, metodo_pagamento, valor, data_servico) VALUES('".$id_usuario."', '".$id_baba."', '".$data."', '".$metodo_pagamento."', '".$valor."', '".$data_servico."');";
	mysqli_query($conexao, $sql);
	
	mysqli_close($conexao);
?>
