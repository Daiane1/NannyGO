<?php	
	require_once('conexao.php');
	$sql = "SELECT * from vw_cidade_estado;";
	
	$select = mysqli_query($conexao, $sql);
	
	$array = array();
	while($resultado=mysqli_fetch_array($select))
	{
		$cidade = array(
			"idCidade"=>$resultado['id_cidade'],
			"cidade"=>$resultado['cidade'],
			"estado"=>$resultado['estado'],
			"uf"=>$resultado['uf']
		);
		
		$array[] = $cidade;
	}
	$json = json_encode($array);
	echo($json);
	
?>