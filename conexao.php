<?php
	$conexao = new mysqli('localhost', 'root', 'bcd127', 'db_nannygo');
	if (!$conexao) 
	{
		die("Conexão falhou: " . mysqli_connect_error());
	}

?>