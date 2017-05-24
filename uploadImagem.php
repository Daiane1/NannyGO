<?php 	
	/*header('Content-type: bitmap; charset=utf-8');
	
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
			$sql = "insert into tbl_imagem(imagem, pasta) values('$nome_imagem','$path');";
			
			$resultado = mysql_query($conexao, $sql);
			
			if($resultado){
				echo("Sucesso");
			}else{
				echo("Falhou")
			}
			
			mysql_close($conexao);
		}
	} */
	
	$nome = gen_uuid() ;
	$imagem = $_POST["imagem"];
	
	$decode_imagem = base64_decode("$imagem");
	file_put_contents("fotos/" . $nome . "JPG", $decode_imagem);
	
	
	echo $nome;
	
	
	function gen_uuid() {
		return sprintf( '%04x%04x-%04x-%04x-%04x-%04x%04x%04x',
			// 32 bits for "time_low"
			mt_rand( 0, 0xffff ), mt_rand( 0, 0xffff ),

			// 16 bits for "time_mid"
			mt_rand( 0, 0xffff ),

			// 16 bits for "time_hi_and_version",
			// four most significant bits holds version number 4
			mt_rand( 0, 0x0fff ) | 0x4000,

			// 16 bits, 8 bits for "clk_seq_hi_res",
			// 8 bits for "clk_seq_low",
			// two most significant bits holds zero and one for variant DCE1.1
			mt_rand( 0, 0x3fff ) | 0x8000,

			// 48 bits for "node"
			mt_rand( 0, 0xffff ), mt_rand( 0, 0xffff ), mt_rand( 0, 0xffff )
		);
	}
	
?>