<?php

	$cpf_cnpj_pagador="";
	$datavenc="";
	$valor="";
	$pagador="";
	$dataDoc="";
	$Agência="";
    $NossoNumero="";
    $Cedente='';	
	$Sacado="";
	$Rua="";
	$Numero="";
	$Bairro="";
	$Cidade="";
	$CEP="";
	$Uf="";
	$DataDoProces="";

	
	
	$cpf_cnpj_pagador=$_GET['cpf_cnpj_pagador'];
	$valor=$_GET['valor'];
	$datavenc=$_GET['vencimento'];
	$Sacado=$_GET['Sacado'];
	$Agência=$_GET['Agência'];
	$dataDoc=$_GET['dataDoc'];
    $NossoNumero=$_GET['NossoNumero'];
    $Cedente=$_GET['Cedente'];
	$Rua=$_GET['Rua'];
	$Numero=$_GET['Numero'];
	$Bairro=$_GET['Bairro'];
	$Cidade=$_GET['Cidade'];
	$CEP=$_GET['CEP'];
	$Uf=$_GET['Uf'];
	$DataDoProces=$_GET['DataDoProces'];


?>
<html>
<HEAD>
<TITLE></TITLE>
<STYLE>
td.BoletoCodigoBanco {font-size: 6mm; font-family: arial, verdana; font-weight : bold; 
					  FONT-STYLE: italic; text-align: center; vertical-align: bottom;  
					  border-bottom: 0.15mm solid #000000; border-right: 0.15mm solid #000000;
					  padding-bottom : 1mm}
td.BoletoLogo { border-bottom: 0.15mm solid #000000;  border-right: 0.15mm solid #000000;
				text-align: center; height: 10mm}	
td.BoletoLinhaDigitavel {font-size: 4 mm; font-family: arial, verdana; font-weight : bold; 
					     text-align: center; vertical-align: bottom; 
					      border-bottom: 0.15mm solid #000000; padding-bottom : 1mm; }
td.BoletoTituloEsquerdo{font-size: 0.2cm; font-family: arial, verdana; padding-left : 0.15mm;
						border-right: 0.15mm solid #000000; text-align: left}
td.BoletoTituloDireito{font-size: 2mm; font-family: arial, verdana; padding-left : 0.15mm;
						text-align: left}
td.BoletoValorEsquerdo{font-size: 3mm; font-family: arial, verdana; text-align: center;
						border-right: 0.15mm solid #000000; font-weight: bold;
						border-bottom: 0.15mm solid #000000; padding-top: 0.5mm}
td.BoletoValorDireito{font-size: 3mm; font-family: arial, verdana; text-align:right; 
					  padding-right: 3mm; padding-top: 0.8mm; border-bottom: 0.15mm solid #000000;
					   font-weight: bold;}
td.BoletoTituloSacado{font-size: 2mm; font-family: arial, verdana; padding-left : 0.15mm;
						vertical-align: top; padding-top : 0.15mm; text-align: left}
td.BoletoValorSacado{font-size: 3mm; font-family: arial, verdana;  font-weight: bold; 
					text-align : left}
td.BoletoTituloSacador{font-size: 2mm; font-family: arial, verdana; padding-left : 0.15mm;
						vertical-align: bottom; padding-bottom : 0.8mm;
						border-bottom: 0.15mm solid #000000}
td.BoletoValorSacador{font-size: 3mm; font-family: arial, verdana; vertical-align: bottom; 
					padding-bottom : 0.15mm; border-bottom: 0.15mm solid #000000;
					 font-weight: bold; text-align: left}	
td.BoletoPontilhado{border-top: 0.3mm dashed #000000; font-size: 1mm}
ul.BoletoInstrucoes{font-size : 3mm; font-family : verdana, arial}	  
</STYLE>
</HEAD>
<BODY>

<P align=center>
<TABLE cellSpacing=0 cellPadding=0 border=0 class=Boleto>
  <TR>
    <TD style='width: 0.9cm'></TD>
    <TD style='width: 1cm'></TD>
    <TD style='width: 1.9cm'></TD>
    
    <TD style='width: 0.5cm'></TD>
    <TD style='width: 1.3cm'></TD>
    <TD style='width: 0.8cm'></TD>
    <TD style='width: 1cm'></TD>
    
    <TD style='width: 1.9cm'></TD>
    <TD style='width: 1.9cm'></TD>
    
    <TD style='width: 3.8cm'></TD>
    
    <TD style='width: 3.8cm'></TD>
  <tr><td colspan=11>
  <ul class=BoletoInstrucoes>
  <li>Imprima em papel A4 ou Carta</li>
  <li>Utilize margens mínimas a direita e a esquerda</li>
  <li>Recorte na linha pontilhada</li>
  <li>Não rasure o código de barras</li>
  </ul>
  </td></tr>
  </TR>
  <tr><td colspan=11 class=BoletoPontilhado>&nbsp;</td></tr>
  <TR>
    <TD colspan=4 class=BoletoLogo><img src='imagens/104.jpg'></TD>
    <TD colspan=2 class=BoletoCodigoBanco>104-0</TD>
    <TD colspan=6 class=BoletoLinhaDigitavel>10491.2346.600000.200042 00000.1234147 7 62000000</TD>
  </TR>
  <TR>
	<TD colspan=10 class=BoletoTituloEsquerdo>Cedente</TD>
    <TD class=BoletoTituloDireito>Agência/Código do Cedente</TD>
  </TR>
  <TR>
    <TD colspan=10 class=BoletoValorEsquerdo style='text-align: left; padding-left : 0.1cm'><?php echo($Cedente); ?></TD>
    <TD class=BoletoValorDireito><?php echo($Agência); ?></TD>
  </TR>   
  <TR>
    <TD colspan=3 class=BoletoTituloEsquerdo>Data do Documento</TD>
    <TD colspan=4 class=BoletoTituloEsquerdo>Número do Documento</TD>
    <TD class=BoletoTituloEsquerdo>Espécie</TD>
    <TD class=BoletoTituloEsquerdo>Aceite</TD>
    <TD class=BoletoTituloEsquerdo>Data do Processamento</TD>
    <TD class=BoletoTituloDireito>Nosso Numero</TD>
  </TR>
  <TR>
    <TD colspan=3 class=BoletoValorEsquerdo><?php echo($dataDoc)?></TD>
    <TD colspan=4 class=BoletoValorEsquerdo><?php echo($NossoNumero);?></TD>
    <TD class=BoletoValorEsquerdo>Real</TD>
    <TD class=BoletoValorEsquerdo>S</TD>
    <TD class=BoletoValorEsquerdo><?php echo($DataDoProces); ?></TD>
    <TD class=BoletoValorDireito>1223</TD>
  </TR>  
  <TR>
    <TD colspan=3 class=BoletoTituloEsquerdo>Uso do Banco</TD>
    <TD colspan=2 class=BoletoTituloEsquerdo>Carteira</TD>
    <TD colspan=2 class=BoletoTituloEsquerdo>Moeda</TD>
    <TD colspan=2 class=BoletoTituloEsquerdo>Quantidade</TD>
    <TD class=BoletoTituloEsquerdo>(x) Valor</TD>
    <TD class=BoletoTituloDireito>(=) Valor do Documento</TD>
  </TR>
  <TR>
    <TD colspan=3 class=BoletoValorEsquerdo>&nbsp;</TD>
    <TD colspan=2 class=BoletoValorEsquerdo>SR</TD>
    <TD colspan=2 class=BoletoValorEsquerdo>R$</TD>
    <TD colspan=2 class=BoletoValorEsquerdo>&nbsp;</TD>
    <TD class=BoletoValorEsquerdo>R$ <?php echo($valor); ?></TD>
    <TD class=BoletoValorDireito>R$ <?php echo($valor); ?></TD>
  </TR>  
  <TR>
    <TD colspan=10 class=BoletoTituloEsquerdo>Instruções</TD>
    <TD class=BoletoTituloDireito>(-) Desconto</TD>
  </TR>
  <TR>
    <TD colspan=10 rowspan=9 class=BoletoValorEsquerdo style='text-align: left; vertical-align:top; padding-left : 0.1cm'>Instruções</TD>
    <TD class=BoletoValorDireito>&nbsp;</TD>
  </TR>  
  <TR>
    <TD class=BoletoTituloDireito>(-) Outras Deduções/Abatimento</TD>
  </TR>  
  <TR>
    <TD class=BoletoValorDireito>&nbsp;</TD>
  </TR>  
  <TR>
    <TD class=BoletoTituloDireito>(+) Mora/Multa/Juros</TD>
  </TR>  
  <TR>
    <TD class=BoletoValorDireito>&nbsp;</TD>
  </TR>  
  <TR>
    <TD class=BoletoTituloDireito>(+) Outros Acréscimos</TD>
  </TR>  
  <TR>
    <TD class=BoletoValorDireito>&nbsp;</TD>
  </TR>  
  <TR>
    <TD class=BoletoTituloDireito>(=) Valor Cobrado</TD>
  </TR>  
  <TR>
    <TD class=BoletoValorDireito>&nbsp;</TD>
  </TR>                
  <TR>
    <TD rowspan=3 Class=BoletoTituloSacado>Sacado:</TD>
    <TD colspan=8 Class=BoletoValorSacado><?php echo($Sacado)?></TD>
	<TD colspan=10 Class=BoletoValorSacado><?php echo($cpf_cnpj_pagador)?></TD>
  </TR> 
  <TR>
    <TD colspan=10 Class=BoletoValorSacado><?php echo($Rua);?> <?php echo($Numero)?> <?php echo($Bairro)?></TD>
  </TR>
  <TR>
    <TD colspan=10 Class=BoletoValorSacado><?php echo($Cidade)?> <?php echo($Uf)?> <?php echo($CEP)?></TD>
  </TR>  
  <TR>
    <TD colspan=2 Class=BoletoTituloSacador>Sacador / Avalista:</TD>
    <TD colspan=9 Class=BoletoValorSacador><?php echo($Sacado)?></TD>
  </TR>
  <TR>
    <TD colspan=11 class=BoletoTituloDireito style='text-align: right; padding-right: 0.1cm'>Recibo do Sacado - Autenticação Mecânica</TD>
  </TR>
  <TR>
    <TD colspan=11 height=60 valign=top>&nbsp;</TD>
  </TR>
  <tr><td colspan=11 class=BoletoPontilhado>&nbsp;</td></tr>  
  <TR>
    <TD colspan=4 class=BoletoLogo><img src='imagens/Caixa.jpg'></TD>
    <TD colspan=2 class=BoletoCodigoBanco>104-0</TD>
    <TD colspan=6 class=BoletoLinhaDigitavel>LinhaDigitavel</TD>
  </TR>
  <TR>
    <TD colspan=10 class=BoletoTituloEsquerdo>Local de Pagamento</TD>
    <TD class=BoletoTituloDireito>Vencimento</TD>
  </TR>
  <TR>
    <TD colspan=10 class=BoletoValorEsquerdo style='text-align: left; padding-left : 0.1cm'>Pagável em qualquer banco até o vencimento.</TD>
    <TD class=BoletoValorDireito><?php echo($datavenc); ?></TD>
  </TR>  
  <TR>
    <TD colspan=10 class=BoletoTituloEsquerdo>Cedente</TD>
    <TD class=BoletoTituloDireito>Agência/Código do Cedente</TD>
  </TR>
  <TR>
    <TD colspan=10 class=BoletoValorEsquerdo style='text-align: left; padding-left : 0.1cm'><?php echo($Cedente); ?> </TD>
    <TD class=BoletoValorDireito><?php echo($Agência); ?></TD>
  </TR>   
  <TR>
    <TD colspan=3 class=BoletoTituloEsquerdo>Data do Documento</TD>
    <TD colspan=4 class=BoletoTituloEsquerdo>Número do Documento</TD>
    <TD class=BoletoTituloEsquerdo>Espécie</TD>
    <TD class=BoletoTituloEsquerdo>Aceite</TD>
    <TD class=BoletoTituloEsquerdo>Data do Processamento</TD>
    <TD class=BoletoTituloDireito>Nosso Numero</TD>
  </TR>
  <TR>
    <TD colspan=3 class=BoletoValorEsquerdo> <?php echo($dataDoc)?></TD>
    <TD colspan=4 class=BoletoValorEsquerdo><?php echo($NossoNumero);?></TD>
    <TD class=BoletoValorEsquerdo>RC</TD>
    <TD class=BoletoValorEsquerdo>N</TD>
    <TD class=BoletoValorEsquerdo><?php echo($DataDoProces); ?></TD>
    <TD class=BoletoValorDireito><?php echo($NossoNumero);?></TD>
  </TR>  
  <TR>
    <TD colspan=3 class=BoletoTituloEsquerdo>Uso do Banco</TD>
    <TD colspan=2 class=BoletoTituloEsquerdo>Carteira</TD>
    <TD colspan=2 class=BoletoTituloEsquerdo>Especie</TD>
    <TD colspan=2 class=BoletoTituloEsquerdo>Quantidade</TD>
    <TD class=BoletoTituloEsquerdo>(x) Valor</TD>
    <TD class=BoletoTituloDireito>(=) Valor do Documento</TD>
  </TR>
  <TR>
    <TD colspan=3 class=BoletoValorEsquerdo>&nbsp;</TD>
    <TD colspan=2 class=BoletoValorEsquerdo>SR</TD>
    <TD colspan=2 class=BoletoValorEsquerdo>R$</TD>
    <TD colspan=2 class=BoletoValorEsquerdo>&nbsp;</TD>
    <TD class=BoletoValorEsquerdo>&nbsp;</TD>
    <TD class=BoletoValorDireito>R$<?php echo($valor); ?></TD>
  </TR>  
  <TR>
    <TD colspan=10 class=BoletoTituloEsquerdo>Instruções</TD>
    <TD class=BoletoTituloDireito>(-) Desconto</TD>
  </TR>
  <TR>
    <TD colspan=10 rowspan=9 class=BoletoValorEsquerdo style='text-align: left; vertical-align:top; padding-left : 0.1cm'>Ins</TD>
    <TD class=BoletoValorDireito>&nbsp;</TD>
  </TR>  
  <TR>
    <TD class=BoletoTituloDireito>(-) Outras Deduções/Abatimento</TD>
  </TR>  
  <TR>
    <TD class=BoletoValorDireito>&nbsp;</TD>
  </TR>  
  <TR>
    <TD class=BoletoTituloDireito>(+) Mora/Multa/Juros</TD>
  </TR>  
  <TR>
    <TD class=BoletoValorDireito>&nbsp;</TD>
  </TR>  
  <TR>
    <TD class=BoletoTituloDireito>(+) Outros Acréscimos</TD>
  </TR>  
  <TR>
    <TD class=BoletoValorDireito>&nbsp;</TD>
  </TR>  
  <TR>
    <TD class=BoletoTituloDireito>(=) Valor Cobrado</TD>
  </TR>  
  <TR>
    <TD class=BoletoValorDireito>&nbsp;</TD>
  </TR>                
  <TR>
    <TD rowspan=3 Class=BoletoTituloSacado>Sacado:</TD>
    <TD colspan=8 Class=BoletoValorSacado><?php echo($Sacado)?></TD>
    <TD colspan=2 Class=BoletoValorSacado></TD>
  </TR> 
  <TR>
    <TD colspan=10 Class=BoletoValorSacado><?php echo($Rua)?> <?php echo($Numero)?> <?php echo($Bairro)?></TD>
  </TR>
  <TR>
    <TD colspan=10 Class=BoletoValorSacado><?php echo($Cidade)?> <?php echo($Uf)?> <?php echo($CEP)?></TD>
  </TR>  
  <TR>
    <TD colspan=2 Class=BoletoTituloSacador>Sacador / Avalista:</TD>
    <TD colspan=9 Class=BoletoValorSacador><?php echo($Sacado)?></TD>
  </TR>
  <TR>
    <TD colspan=11 class=BoletoTituloDireito style='text-align: right; padding-right: 0.1cm'>Ficha de Compensação - Autenticação Mecânica</TD>
  </TR>
  <TR>
    <TD colspan=11 height=60 valign=top><img src='imagens/codbar.jpg'></TD>
  </TR>
  <tr><td colspan=11 class=BoletoPontilhado>&nbsp;</td></tr>  
  </TABLE></P>

</BODY>
</HTML>

  
	

  
	