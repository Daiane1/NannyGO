<?php 	
	header('Content-type: bitmap; charset=utf-8');
	
	if(isset($_POST["enconded_string"])){
		
		$encoded_string = $_POST["encoded_string"];
		$nome_imagem = $_POST["nome_imagem"];
		
		$decoded_string = base64_decode($encoded_string);
		
		$path = 'imagens/'.$nome_imagem;
		$file = fopen($path, 'wb');
		
		$is_written = fwrite($file, $decoded_string);
		fclose($file);
		
		if($is_written > 0){
			$conexao= mysql_connect('localhost','root', 'bcd127');
			$sql = "insert into tbl_imagem(imagem, pasta) values('$nome_imagem','$pasta');";
			
			$resultado = mysql_query($conexao, $sql);
			
			if($resultado){
				echo("Sucesso");
			}else{
				echo("Falhou")
			}
			
			mysql_close($conexao);
		}
	}
	
	
?>