<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Contrato</title>
	<script type="text/javascript">
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ContratoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./contrato_preimpreso_print.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./contrato_preimpreso_print.css"/>
</head>
<body>
	<div class="divA4Sheet">
		<div class="divA4SheetContent">
			<div class="divPageHeading">&nbsp;</div>
			<div class="divPageContent">
				<div class="divTexto">
					En <span id="spanDepartamento">&nbsp;</span> a los <span id="spanDia">&nbsp;</span> d&iacute;as del mes de <span id="spanMes">&nbsp;</span> de <span id="spanAno">&nbsp;</span>, se celebra este contrato, por un plazo de <span id="spanDuracionContrato">&nbsp;</span> de acuerdo con las condiciones precedentemente descriptas y particulares del Plan que se se&ntilde;ala m&aacute;s adelante, entre la Administraci&oacute;n Nacional de Telecomunicaciones (ANTEL) y <span id="spanNombre">&nbsp;</span> quedando perfeccionado en la fecha se habilite el servicio al Cliente.
				</div>
				<div class="divTitulo">INFORMACION CORRESPONDIENTE AL CLIENTE</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaHalf">Nombres:<span id="spanClienteNombre">&nbsp;</span></div>
						<div class="divColumnaHalfFiller">Apellido:<span id="spanClienteApellido">&nbsp;</span></div>
					</div>
					<div class="divFila">
						<div class="divColumnaFull">Nombre de la Empresa:<span id="spanClienteNombreEmpresa">&nbsp;</span></div>
					</div>
					<div class="divFila">
						<div class="divColumnaHalf" style="text-align: center;">Tipo de documento</div>
						<div class="divColumnaHalfFiller" style="text-align: center;">Numero de documento</div>
					</div>
					<div class="divFila" style="height: 28px;">
						<div class="divColumnaMin" style="width: 29px;height: 26px;">RUT</div>
						<div class="divColumnaMin" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaMin" style="width: 22px;height: 26px;">C.I.</div>
						<div class="divColumnaMin" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaMin" style="width: 25px;height: 26px;">PAS</div>
						<div class="divColumnaMin" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaMin" style="width: 43px;height: 26px;">INCISO</div>
						<div class="divColumnaMin" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaMin" style="width: 24px;height: 26px;">BPS</div>
						<div class="divColumnaMin" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaMin" style="width: 36px;height: 26px;">OTRO</div>
						<div class="divColumnaMinFiller" style="height: 26px;border-right: solid 1px black;">&nbsp;</div>
						<div class="divColumnaDigit" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigitFiller" style="height: 26px;">&nbsp;</div>
					</div>
					<div class="divFila">
						<div class="divColumnaFifth">Sexo:</div>
						<div class="divColumnaFifth">FechaNacim:</div>
						<div class="divColumnaFifth">Tipo Cliente:</div>
						<div class="divColumnaFifth">Actividad:</div>
						<div class="divColumnaFifthFiller">Tel. Contacto:</div>
					</div>
				</div>
				<div class="divTitulo">INFORMACION CORRESPONDIENTE AL SERVICIO Y TERMINAL:</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaHalf">N de m&oacute;vil:<span id="spanMID">&nbsp;</span></div>
						<div class="divColumnaHalfFiller">CHIP:<span id="spanNumeroChip">&nbsp;</span></div>
					</div>
					<div class="divFila">
						<div class="divColumnaHalf">Marca:<span id="spanMarca">&nbsp;</span></div>
						<div class="divColumnaHalfFiller">Numero de serie electr&oacute;nico:<span id="spanNumeroSerie">&nbsp;</span></div>
					</div>
					<div class="divFila">
						<div class="divColumnaHalf">Modelo:<span id="spanModelo">&nbsp;</span></div>
						<div class="divColumnaHalfFiller">C&oacute;digo de bloqueo:<span id="spanCodigoBloqueo">&nbsp;</span></div>
					</div>
				</div>
				<div class="divTitulo">DOMICILIO ESPECIAL: El cliente constituye domicilio a todos los efectos del presente contrato en:</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaFull">CALLE:<span id="spanDireccionCalle">&nbsp;</span></div>
					</div>
					<div class="divFila">
						<div class="divColumnaSixth">Numero:<span id="spanDireccionNumero">&nbsp;</span></div>
						<div class="divColumnaSixth">Bis<span id="spanDireccionBis">&nbsp;</span></div>
						<div class="divColumnaSixth">Apto:<span id="spanDireccionApto">&nbsp;</span></div>
						<div class="divColumnaSixth">Block:<span id="spanDireccionBlock">&nbsp;</span></div>
						<div class="divColumnaSixth">Manz:<span id="spanDireccionManzana">&nbsp;</span></div>
						<div class="divColumnaSixthFiller">Solar:<span id="spanDireccionSolar">&nbsp;</span></div>
					</div>
					<div class="divFila">
						<div class="divColumnaThird">Localidad:<span id="spanDireccionLocalidad">&nbsp;</span></div>
						<div class="divColumnaThird">Departamento:<span id="spanDireccionDepartamento">&nbsp;</span></div>
						<div class="divColumnaThirdFiller">C.P.:<span id="spanDireccionCodigoPostal">&nbsp;</span></div>
					</div>
					<div class="divFila">
						<div class="divColumnaFull">Observaciones:<span id="spanDireccionObservaciones">&nbsp;</span></div>
					</div>
				</div>
				<div class="divTitulo">DOMICILIO DE ENVIO DE FACTURAS (en caso de ser distinta al domicilio especial)</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaFull">CALLE:<span id="spanDireccionEntregaCalle">&nbsp;</span></div>
					</div>
					<div class="divFila">
						<div class="divColumnaSixth">Numero:<span id="spanDireccionFacturaNumero">&nbsp;</span></div>
						<div class="divColumnaSixth">Bis<span id="spanDireccionFacturaBis">&nbsp;</span></div>
						<div class="divColumnaSixth">Apto:<span id="spanDireccionFacturaApto">&nbsp;</span></div>
						<div class="divColumnaSixth">Block:<span id="spanDireccionFacturaBlock">&nbsp;</span></div>
						<div class="divColumnaSixth">Manz:<span id="spanDireccionFacturaManzana">&nbsp;</span></div>
						<div class="divColumnaSixthFiller">Solar:<span id="spanDireccionFacturaSolar">&nbsp;</span></div>
					</div>
					<div class="divFila">
						<div class="divColumnaThird">Localidad:<span id="spanDireccionFacturaLocalidad">&nbsp;</span></div>
						<div class="divColumnaThird">Departamento:<span id="spanDireccionFacturaDepartamento">&nbsp;</span></div>
						<div class="divColumnaThirdFiller">C.P.:<span id="spanDireccionFacturaCodigoPostal">&nbsp;</span></div>
					</div>
					<div class="divFila">
						<div class="divColumnaFull">Observaciones:<span id="spanDireccionFacturaObservaciones">&nbsp;</span></div>
					</div>
				</div>
				<div class="divTitulo">EL CLIENTE SENALA QUE QUIEN TIENE LA CALIDAD DE USUARIO DEL SERVICIO ES:</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaHalf">Nombres:<span id="spanUsuarioNombre">&nbsp;</span></div>
						<div class="divColumnaHalfFiller">Apellidos:<span id="spanUsuarioApellido">&nbsp;</span></div>
					</div>
					<div class="divFila">
						<div class="divColumnaHalf">Documento:<span id="spanUsuarioDocumento">&nbsp;</span></div>
						<div class="divColumnaHalfFiller">Direcci&oacute;n de e-mail:<span id="spanUsuarioEmail">&nbsp;</span></div>
					</div>
				</div>
				<div class="divTitulo">DATOS COMERCIALES</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaThird">Plan:<span id="spanPlan">&nbsp;</span></div>
						<div class="divColumnaThird">Promotor:<span id="spanPromotor">&nbsp;</span></div>
						<div class="divColumnaThirdFiller">Ayuda Econ&oacute;mica:<span id="spanAyudaEconomica">&nbsp;</span></div>
					</div>
					<div class="divFila">
						<div class="divColumnaThird">Cantidad de minutos<span id="spanCantidadMinutos">&nbsp;</span></div>
						<div class="divColumnaThird">Desde<span id="spanDesde">&nbsp;</span></div>
						<div class="divColumnaThirdFiller">Hasta<span id="spanHasta">&nbsp;</span></div>
					</div>
				</div>
			</div>
			<div class="divPageFooter">&nbsp;</div>
		</div>
	</div>
	<div class="divA4Sheet">
		<div class="divA4SheetContent">
			<div class="divPageHeading">&nbsp;</div>
			<div class="divPageContent">
				<div class="divTitulo">GARANTIA</div>
				<div class="divTextarea">
					<ul style="padding-left: 5px;">
						<li style="list-style-type: none;">
							A) Fiador solidario- El Sr <span id="spanFiadorSolidarioNombre">&nbsp;</span> se constituye en fiador solidario de las obligaciones asumidas por el cliente.
							<br/>
							En virtud de ello, la presente fianza subsidiar&aacute; por todo el tiempo durante el cual el cliente mantenga relaciones comerciales con ANTEL, y solo cesar&aacute; si media alguna de las siguientes circunstancias:</li>
						<li style="list-style-type: none;">
							<ol>
								<li>Que se hayan cancelado definitivamente todas las deudas y obligaciones de cargo del Cliente derivadas del presente contrato.</li>
								<li>Que ANTEL libere expresamente y por escrito al fiador solidario, siempre que el Cliente ofrezca fiador solidario o garant&iacute;a sustitutivos a satisfacci&oacute;n de ANTEL.</li>
							</ol>
						<li style="list-style-type: none;">&nbsp;</li>
						<li style="list-style-type: none;">A su vez el fiador solidario autoriza a que en caso de falta de pago por parte del Cliente, los cargos generados, sean facturados en la cuenta de ANTEL Nº <span id="spanFiadorSolidarioNumeroCuenta">&nbsp;</span> de la que es titular- Documento de identidad: <span id="spanFiadorSolidarioDocumento">&nbsp;</span> Tel&eacute;fono de contacto: <span id="spanFiadorSolidarioTelefonoContacto">&nbsp;</span>.</li>
						<li style="list-style-type: none;">&nbsp;</li>
						<li style="list-style-type: none;">El fiador solidario constituye domicilio especial a todos los efectos del presente contrato en: <span id="spanFiadorSolidarioDireccion">&nbsp;</span></li>
						<li style="list-style-type: none;">&nbsp;</li>
						<li style="list-style-type: none;">B) Dep&oacute;sito en efectivo - El Cliente deposita en car&aacute;cter de garant&iacute;a la suma de: <span id="spanFiadorSolidarioDepositoGarantia">&nbsp;</span></li>
					</ul>
				</div>
				<div class="divTitulo">OBSERVACIONES:</div>
				<div class="divTextarea" style="font-size: 7pt;height: 66px;">
					Tuve al cliente ante m&iacute; y corrobor&eacute; su identidad. El n&uacute;mero de individualizaci&oacute;n del presente contrato, lucir&aacute; en cada factura a emitirse.
					<br/><br/>
					<span id="spanObservaciones">&nbsp;</span>
				</div>
				<div class="divTitulo">FIRMAS: Para constancia y como prueba de conformidad, se suscriben tres ejemplares de un mismo tenor en el lugar y fecha antes se&ntilde;alados</div>
				<div class="divTabla">
					<div class="divFila" style="height: 44px;">
						<div class="divColumnaHalf" style="height: 42px;">Cliente:</div>
						<div class="divColumnaHalfFiller" style="height: 42px;">Aclaraci&oacute;n:</div>
					</div>
					<div class="divFila" style="height: 44px;">
						<div class="divColumnaHalf" style="height: 42px;">Fiador Solidario:</div>
						<div class="divColumnaHalfFiller" style="height: 42px;">Aclaraci&oacute;n:</div>
					</div>
				</div>
				<div class="divTitulo">Para USO INTERNO</div>
				<div class="divTitulo">AGENTE DE VENTA:</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaThird">Nombre:<span id="spanAgenteVentaNombre">&nbsp;</span></div>
						<div class="divColumnaThird">C&oacute;digo:<span id="spanAgenteVentaCodigo">&nbsp;</span></div>
						<div class="divColumnaThirdFiller">Sucursal:<span id="spanAgenteVentaSucursal">&nbsp;</span></div>
					</div>
				</div>
				<div class="divTitulo">VENDEDOR</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaThird">Firma:</div>
						<div class="divColumnaThird">Aclaraci&oacute;n:<span id="spanVendedorNombre">&nbsp;</span></div>
						<div class="divColumnaThirdFiller">Documento:<span id="spanVendedorDocumento">&nbsp;</span></div>
					</div>
				</div>
				<div class="divTitulo">&nbsp;</div>
				<div class="divTabla">
					<div class="divFila" style="height: 38px;text-align: center;">
						<div class="divColumnaNineth" style="height: 36px;font-weight: bold;">CONTROLES</div>
						<div class="divColumnaNineth" style="height: 36px;">Clearing<br/>cliente</div>
						<div class="divColumnaNineth" style="height: 36px;">Clearing<br/>garante</div>
						<div class="divColumnaNineth" style="height: 36px;">Control<br/>Cta. Tel&eacute;f.</div>
						<div class="divColumnaNineth" style="height: 36px;">Control<br/>documentos</div>
						<div class="divColumnaNineth" style="height: 36px;">Ingreso<br/>ACM</div>
						<div class="divColumnaNineth" style="height: 36px;">Alta<br/>T&eacute;cnica</div>
						<div class="divColumnaNineth" style="height: 36px;">Control<br/>Facturaci&oacute;n</div>
						<div class="divColumnaNinethFiller" style="height: 38px;">Archivo</div>
					</div>
					<div class="divFila">
						<div class="divColumnaNineth">Firma</div>
						<div class="divColumnaNineth">&nbsp;</div>
						<div class="divColumnaNineth">&nbsp;</div>
						<div class="divColumnaNineth"><span id="spanControlCuentaCliente">&nbsp;</span></div>
						<div class="divColumnaNineth">&nbsp;</div>
						<div class="divColumnaNineth">&nbsp;</div>
						<div class="divColumnaNineth">&nbsp;</div>
						<div class="divColumnaNineth">&nbsp;</div>
						<div class="divColumnaNinethFiller">&nbsp;</div>
					</div>
					<div class="divFila">
						<div class="divColumnaNineth" >Fecha:</div>
						<div class="divColumnaNineth">&nbsp;</div>
						<div class="divColumnaNineth">&nbsp;</div>
						<div class="divColumnaNineth">&nbsp;</div>
						<div class="divColumnaNineth">&nbsp;</div>
						<div class="divColumnaNineth">&nbsp;</div>
						<div class="divColumnaNineth">&nbsp;</div>
						<div class="divColumnaNineth">&nbsp;</div>
						<div class="divColumnaNinethFiller">&nbsp;</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>