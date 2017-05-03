<?php
  $imagem = $_POST["imagem"];
  
	$decodedImagem = base64_decode("$imagem");
	file_put_contents("arquivos/", $decodedImagem);
?>