<?php
	require_once('conexao.php');

	$id_usuario = $_GET['id_usuario'];
	
	$sql = "SELECT * FROM tbl_babas WHERE id_usuario='".$id_usuario."';";
	
	$select = mysqli_query($conexao, $sql);
	
	if ($rs=mysqli_fetch_array($select))
	{
		$baba = array(
			"idBaba"=>$rs['id_baba'],
			"idUsuario"=>$rs['id_usuario'],
			"idCidade"=>$rs['idCidade'],
			"preco"=>$rs['preco'],
			"horaInicio"=>$rs['horaInicio'],
			"horaFim"=>$rs['horaFim'],
			"diasDisponiveis"=>$rs['diasDisponiveis']
			);
			
		$usuarioJSON = json_encode($baba);
		
		echo($usuarioJSON);
	}
	
	mysqli_close($conexao);
	
?>
