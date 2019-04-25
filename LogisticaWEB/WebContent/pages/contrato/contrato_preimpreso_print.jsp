<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="uy.com.amensg.logistica.dwr.*" %>
<%@ page import="uy.com.amensg.logistica.util.*" %>
<%@ page import="uy.com.amensg.logistica.entities.ContratoTO" %>
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
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ContratoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/EmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./contrato_preimpreso_print.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./contrato_preimpreso_print.css"/>
</head>
<%
	ContratoTO contratoTO = new ContratoDWR().getById(new Long(request.getParameter("cid")));
%>
<body>
	<div class="divPrintingButtonBar">
		<div class="divButtonBar">
			<div class="divButton"><input type="submit" value="Imprimir" onclick="javascript:inputImprimirOnClick(event, this)"></div>
			<div class="divButton"><input type="submit" value="Cancelar" onclick="javascript:inputCancelarOnClick(event, this)"></div>
		</div>
	</div>
<%
	int vias = 3; 
	for (int i = 0; i<vias; i++) {
%>
	<div class="divA4Sheet">
		<div class="divA4SheetContent">
			<div class="divPageHeading">&nbsp;</div>
			<div class="divPageContent">
				<div class="divTexto" style="height: 160px;">
					En <input type="text" class="inputDepartamento"/> a los <input type="text" class="inputDia"/> d&iacute;as del mes de <input type="text" class="inputMes"/> de <input type="text" class="inputAno"/>, se celebra este contrato, por un plazo de <input type="text" class="inputDuracionContrato"/> <!-- de acuerdo con las condiciones precedentemente descriptas y particulares del Plan que se se&ntilde;ala m&aacute;s adelante,  -->entre la Administraci&oacute;n Nacional de Telecomunicaciones (ANTEL) y <input type="text" class="inputNombre"/> <!-- quedando perfeccionado en la fecha se habilite el servicio al -->(denominado Cliente).
					<br/>
					Forman parte del presente contrato, el Reglamento de Servicios de ANTEL publicado en el Diario Oficial Nº26.953 de 9 de marzo de 2006 P&aacute;gs. 1917, 1918, 1919; las Condiciones de Contrataci&oacute;n Particulares de cada servicio y los Anexos con las especificaciones de los productos y servicios solicitados.
					<br/>
					El Reglamento de Servicios de ANTEL y las Condiciones de Contrataci&oacute;n Particulares de cada servicio pueden consultarse en la p&aacute;gina web de ANTEL www.antel.com.uy, o solicitarlos en los Centros Comerciales y/o Tiendas de ANTEL.
					<br/>
					Los Anexos con las especificaciones de los productos y servicios contratados se entregan en oportunidad de realizar cada contrataci&oacute;n.
					<br/>
					El presente contrato se perfecciona en la fecha que se habilite el servicio al Cliente.
				</div>
				<div class="divTitulo">INFORMACION CORRESPONDIENTE AL CLIENTE</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaHalf" style="width: 300px;">Nombres: <input type="text" class="inputClienteNombre"/></div>
						<div class="divColumnaHalfFiller" style="width: 275px;">Apellido: <input type="text" class="inputClienteApellido"></div>
					</div>
					<div class="divFila">
						<div class="divColumnaFull">Nombre de la Empresa: <input type="text" class="inputClienteNombreEmpresa"/></div>
					</div>
					<div class="divFila">
						<div class="divColumnaHalf" style="text-align: center;width: 300px;">Tipo de documento</div>
						<div class="divColumnaHalfFiller" style="text-align: center;width: 275px;">Numero de documento</div>
					</div>
					<div class="divFila" style="height: 28px;">
						<div class="divColumnaMin" style="width: 29px;height: 26px;">RUT</div>
						<div class="divColumnaMin divTipoDocumento2" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaMin" style="width: 22px;height: 26px;">C.I.</div>
						<div class="divColumnaMin divTipoDocumento1" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaMin" style="width: 25px;height: 26px;">PAS</div>
						<div class="divColumnaMin divTipoDocumento3" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaMin" style="width: 43px;height: 26px;">INCISO</div>
						<div class="divColumnaMin divTipoDocumento4" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaMin" style="width: 24px;height: 26px;">BPS</div>
						<div class="divColumnaMin divTipoDocumento5" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaMin" style="width: 36px;height: 26px;">OTRO</div>
						<div class="divColumnaMinFiller divTipoDocumento6" style="height: 26px;border-right: solid 1px black;">&nbsp;</div>
						<div class="divColumnaDigit divColumnaDigit1" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit divColumnaDigit2" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit divColumnaDigit3" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit divColumnaDigit4" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit divColumnaDigit5" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit divColumnaDigit6" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit divColumnaDigit7" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit divColumnaDigit8" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit divColumnaDigit9" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit divColumnaDigit10" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit divColumnaDigit11" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigit divColumnaDigit12" style="height: 26px;">&nbsp;</div>
						<div class="divColumnaDigitFiller divColumnaDigit13" style="height: 26px;">&nbsp;</div>
					</div>
					<div class="divFila">
						<div class="divColumnaFifth" style="width: 90px;">Sexo:<input type="text" class="inputClienteSexo"/></div>
						<div class="divColumnaFifth" style="width: 134px;">FechaNacim:<input type="text" class="inputClienteFechaNacimiento"/></div>
						<div class="divColumnaFifth" style="width: 93px;">Tipo Cliente:<input type="text" class="inputClienteTipoCliente"/></div>
						<div class="divColumnaFifth" style="width: 89px;">Actividad:<input type="text" class="inputClienteActividad"/></div>
						<div class="divColumnaFifthFiller" style="width: 160px;">Tel. Contacto:<input type="text" class="inputClienteTelefonoContacto"/></div>
					</div>
				</div>
				<div class="divTitulo">INFORMACION CORRESPONDIENTE AL SERVICIO Y TERMINAL:</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaHalf">N de m&oacute;vil: <input type="text" class="inputMID"/></div>
						<div class="divColumnaHalfFiller">CHIP: <input type="text" class="inputNumeroChip"/></div>
					</div>
					<div class="divFila">
						<div class="divColumnaHalf">Marca: <input type="text" class="inputMarca"/> Numero de serie electr&oacute;nico:</div>
						<div class="divColumnaHalfFiller" style="padding-top: 0;">
							<!-- <input type="text" class="inputNumeroSerie"/> -->
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit1">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit2">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit3">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit4">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit5">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit6">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit7">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit8">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit9">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit10">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit11">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit12">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit13">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigit divColumnaNumeroSerieDigit14">&nbsp;</div>
							<div class="divColumnaNumeroSerieDigitFiller divColumnaNumeroSerieDigit15">&nbsp;</div>
						</div>
					</div>
					<div class="divFila">
						<div class="divColumnaHalf">Modelo: <input type="text" class="inputModelo"/></div>
						<div class="divColumnaHalfFiller">C&oacute;digo de bloqueo: <input type="text" class="inputCodigoBloqueo"/></div>
					</div>
				</div>
				<div class="divTitulo">DOMICILIO ESPECIAL: El cliente constituye domicilio a todos los efectos del presente contrato en: </div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaFull">CALLE: <input type="text" class="inputDireccionCalle"/></div>
					</div>
					<div class="divFila">
						<div class="divColumnaSixth">Numero:<input type="text" class="inputDireccionNumero"/></div>
						<div class="divColumnaSixth">Bis:<input type="text" class="inputDireccionBis"/></div>
						<div class="divColumnaSixth">Apto:<input type="text" class="inputDireccionApto"/></div>
						<div class="divColumnaSixth">Block:<input type="text" class="inputDireccionBlock"/></div>
						<div class="divColumnaSixth">Manz:<input type="text" class="inputDireccionManzana"/></div>
						<div class="divColumnaSixthFiller">Solar:<input type="text" class="inputDireccionSolar"/></div>
					</div>
					<div class="divFila">
						<div class="divColumnaThird">Localidad:<input type="text" class="inputDireccionLocalidad"/></div>
						<div class="divColumnaThird">Departamento:<input type="text" class="inputDireccionDepartamento"/></div>
						<div class="divColumnaThirdFiller">C.P.:<input type="text" class="inputDireccionCodigoPostal"/></div>
					</div>
					<div class="divFila">
						<div class="divColumnaFull">Observaciones: <input type="text" class="inputDireccionObservaciones"/></div>
					</div>
				</div>
				<div class="divTitulo">DOMICILIO DE ENVIO DE FACTURAS (en caso de ser distinta al domicilio especial)</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaFull">CALLE: <input type="text" class="inputDireccionCalle"/></div>
					</div>
					<div class="divFila">
						<div class="divColumnaSixth">Numero:<input type="text" class="inputDireccionNumero"/></div>
						<div class="divColumnaSixth">Bis:<input type="text" class="inputDireccionBis"/></div>
						<div class="divColumnaSixth">Apto:<input type="text" class="inputDireccionApto"/></div>
						<div class="divColumnaSixth">Block:<input type="text" class="inputDireccionBlock"/></div>
						<div class="divColumnaSixth">Manz:<input type="text" class="inputDireccionManzana"/></div>
						<div class="divColumnaSixthFiller">Solar:<input type="text" class="inputDireccionSolar"/></div>
					</div>
					<div class="divFila">
						<div class="divColumnaThird">Localidad:<input type="text" class="inputDireccionLocalidad"/></div>
						<div class="divColumnaThird">Departamento:<input type="text" class="inputDireccionDepartamento"/></div>
						<div class="divColumnaThirdFiller">C.P.:<input type="text" class="inputDireccionCodigoPostal"/></div>
					</div>
					<div class="divFila">
						<div class="divColumnaFull">Observaciones: <input type="text" class="inputDireccionObservaciones"/></div>
					</div>
				</div>
				<div class="divTitulo">EL CLIENTE SENALA QUE QUIEN TIENE LA CALIDAD DE USUARIO DEL SERVICIO ES:</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaHalf">Nombres: <input type="text" class="inputUsuarioNombre"/></div>
						<div class="divColumnaHalfFiller">Apellidos: <input type="text" class="inputUsuarioApellido"/></div>
					</div>
					<div class="divFila">
						<div class="divColumnaHalf">Documento: <input type="text" class="inputUsuarioDocumento"/></div>
						<div class="divColumnaHalfFiller">Direcci&oacute;n de e-mail: <input type="text" class="inputUsuarioEmail"/></div>
					</div>
				</div>
				<div class="divTitulo">DATOS COMERCIALES</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaThird">Plan: <input type="text" class="inputPlan"/></div>
						<div class="divColumnaThird">Promotor: <input type="text" class="inputPromotor"/></div>
						<div class="divColumnaThirdFiller">Ayuda Econ&oacute;mica: <input type="text" class="inputAyudaEconomica"/></div>
					</div>
					<div class="divFila">
						<div class="divColumnaThird">Cantidad de minutos: <input type="text" class="inputCantidadMinutos"/></div>
						<div class="divColumnaThird">Desde: <input type="text" class="inputDesde"/></div>
						<div class="divColumnaThirdFiller">Hasta: <input type="text" class="inputHasta"/></div>
					</div>
				</div>
				<div class="divTitulo">AUTORIZACI&Oacute;N:</div>
				<div class="divTexto" style="height: 50px;">
					El cliente autoriza a que, en caso de falta de pago, los importes se facturen en la cuenta de ANTEL Nº <input type="text" class="inputCuentaANTELFacturacionFaltaPago"/> de la cual es titular.
					<br/>
					Administraci&oacute;n Nacional de Telecomunicaciones. Guatemala 1075 (C.P. 11827) Montevideo R.U.T. 211003420017
				</div>
<%
		if (contratoTO.getMotivoCambioPlan() != null) {
%>
				<div class="" style="float: left;width: 100%;"><input type="text" class="inputMotivosCambioPlan"/></div>
<%		
		}
%>
			</div>
			<div class="divPageFooter">&nbsp;</div>
		</div>
	</div>
<% 
	}
	
	vias = 3;
	for (int i=0; i<vias; i++) {
%>
	<div class="divA4Sheet" style="border-top: none;">
		<div class="divA4SheetContent">
			<div class="divPageHeading" style="height: 30mm;">&nbsp;</div>
			<div class="divPageContent">
				<div class="divTitulo">GARANTIA</div>
				<div class="divTextarea">
					<ul style="padding-left: 5px;">
						<li style="list-style-type: none;">
							A) Fiador solidario- El Sr <input type="text" class="inputFiadorSolidarioNombre"/> se constituye en fiador solidario de las obligaciones asumidas por el cliente.
							<br/>
							En virtud de ello, la presente fianza <!-- subsidiar&aacute; -->subsistir&aacute; por todo el tiempo durante el cual el cliente mantenga relaciones comerciales con ANTEL, y solo cesar&aacute; si media alguna de las siguientes circunstancias:</li>
						<li style="list-style-type: none;">
							<ol>
								<li>Que se hayan cancelado definitivamente todas las deudas y obligaciones de cargo del Cliente derivadas del presente contrato.</li>
								<li>Que ANTEL libere expresamente y por escrito al fiador solidario, siempre que el Cliente ofrezca fiador solidario o garant&iacute;a sustitutivos a satisfacci&oacute;n de ANTEL.</li>
							</ol>
						<li style="list-style-type: none;">&nbsp;</li>
						<li style="list-style-type: none;">A su vez el fiador solidario autoriza a que en caso de falta de pago por parte del Cliente, los cargos generados, sean facturados en la cuenta de ANTEL Nº <input type="text" class="inputFiadorSolidarioNumeroCuenta"/> de la que es titular- Documento de identidad: <input type="text" class="inputFiadorSolidarioDocumento"/> Tel&eacute;fono de contacto: <input type="text" class="inputFiadorSolidarioTelefonoContacto"/>.</li>
						<li style="list-style-type: none;">&nbsp;</li>
						<li style="list-style-type: none;">El fiador solidario constituye domicilio especial a todos los efectos del presente contrato en: <input type="text" class="inputFiadorSolidarioDireccion"/></li>
						<li style="list-style-type: none;">&nbsp;</li>
						<li style="list-style-type: none;">B) Dep&oacute;sito en efectivo - El Cliente deposita en car&aacute;cter de garant&iacute;a la suma de: <input type="text" class="inputFiadorSolidarioDepositoGarantia"/></li>
					</ul>
				</div>
				<div class="divTitulo">OBSERVACIONES:</div>
				<div class="divTextarea" style="font-size: 7pt;height: 102px;">
					Tuve al cliente ante m&iacute; y corrobor&eacute; su identidad. El n&uacute;mero de individualizaci&oacute;n del presente contrato, lucir&aacute; en cada factura a emitirse.
					<br/>
					Se informa que la fecha de conexión será a partir de <input type="text" class="inputGarantiaFechaConexion"/> para poder proceder a su conexión debe estar al día en el pago de sus facturas.
					<br/>
					Cliente se comunica con *611 por números gratis.
					<br/>
					<textarea class="textareaObservaciones"></textarea>
				</div>
				<div class="divTexto" style="float: left; width: 100%;height: 20px;">
					El n&uacute;mero de individualizaci&oacute;n del presente contrato, lucir&aacute; en cada factura a emitirse.
				</div>
				<div class="divTitulo" style="height: 40px;">FIRMAS: Para constancia y como prueba de conformidad, se suscriben tres ejemplares de un mismo tenor en el lugar y fecha antes se&ntilde;alados</div>
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
						<div class="divColumnaThird">Nombre: <input type="text" class="inputAgenteVentaNombre"/></div>
						<div class="divColumnaThird">C&oacute;digo: <input type="text" class="inputAgenteVentaCodigo"/></div>
						<div class="divColumnaThirdFiller">Sucursal: <input type="text" class="inputAgenteVentaSucursal"/></div>
					</div>
				</div>
				<div class="divTitulo">VENDEDOR</div>
				<div class="divTabla">
					<div class="divFila">
						<div class="divColumnaThird">Firma: </div>
						<div class="divColumnaThird">Aclaraci&oacute;n: <input type="text" class="inputVendedorNombre"/></div>
						<div class="divColumnaThirdFiller">Documento:<input type="text" class="inputVendedorDocumento"/></div>
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
						<div class="divColumnaNineth"><input type="text" style="font-size: 5pt;" class="inputControlCuentaBackoffice"/></div>
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
						<div class="divColumnaNineth"><input type="text" class="inputControlCuentaCliente"/></div>
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
<%
	}
	
	boolean conLimite = 
		contratoTO.getNuevoPlan().getTipoPlan() != null 
		&& contratoTO.getNuevoPlan().getTipoPlan().getId().equals(
			new Long(Configuration.getInstance().getProperty("tipoPlan.ConLimite"))
		);
	
	vias = 2;
	for (int i=0; i<vias; i++) {
%>
	<div class="divA4Sheet" style="border-top: none;">
		<div class="divA4SheetContent divA4SheetContentEspecificaciones">
			<div class="divPageHeading" style="height: 15mm;">&nbsp;</div>
			<div class="divPageContent" style="width: 88%;">
				<div class="divEspecificacionesTitulo" style="width: 60%;">Especificaciones del Servicio de Telefon&iacute;a M&oacute;vil</div>
				<div style="float: left;width: 20%;text-align: right;">
					Fecha:
				</div>
				<div style="float: left;width: 20%;">
					<div style="float: left;width: 30%;border-bottom: dotted 1px black;">&nbsp;</div>
					<div style="float: left;width: 1%;">/</div>
					<div style="float: left;width: 30%;border-bottom: dotted 1px black;">&nbsp;</div>
					<div style="float: left;width: 1%;">/</div>
					<div style="float: left;width: 30%;border-bottom: dotted 1px black;">&nbsp;</div>
				</div>
				<div style="width: 100%;">&nbsp;</div>
				<div style="float: left;width: 100%;">Las especificaciones que se detallan a continuaci&oacute;n corresponden al contrato No. <input type="text" class="inputEspecificacionesNumeroContrato"/> del servicio No. <input type="text" class="inputEspecificacionesMid"/>, suscrito por <input type="text" class="inputEspecificacionesTitular"/>, titular del documento <input type="text" class="inputEspecificacionesTipoDocumento"/> No. <input type="text" class="inputEspecificacionesDocumento"/></div>
				<div style="width: 100%;">&nbsp;</div>
				<div class="divEspecificacionesTituloInformacion">Los precios que se detallan tienen el IVA incluido:</div>
				<br/>
				<div class="divTabla">
					<div class="divFila" style="height: 20px;">
						<div class="divColumnaHalf" style="width: 108px;">Nombre del plan</div>
						<div class="divColumnaHalfFiller" style="width: 542px;"><input type="text" class="inputEspecificacionesPlanComercial"/></div>
					</div>
					<div class="divFila" style="height: 20px;">
						<div class="divColumnaHalf" style="width: 108px;">Consumo m&iacute;nimo</div>
						<div class="divColumnaHalfFiller" style="width: 542px;">$<input type="text" class="inputEspecificacionesConsumoMinimo"/></div>
					</div>
<%
		if (contratoTO.getNuevoPlan() != null && 
			contratoTO.getNuevoPlan().getBeneficioIncluidoEnLlamadas()) {
%>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaQuarter" style="width: 108px;height: 18px;">Llamadas</div>
						<div class="divColumnaQuarter" style="width: 175px;height: 18px;">&nbsp;</div>
						<div class="divColumnaQuarter" style="width: 129px;height: 18px;border-bottom: solid 1px black;">Destino Antel (por minuto)</div>
						<div class="divColumnaQuarterFiller" style="width: 232px;height: 18px;border-bottom: solid 1px black;">Destino celular (otras compa&ntilde;&iacute;as) (por minuto)</div>
					</div>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaQuarter" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaQuarter" style="width: 175px;height: 18px;border-bottom: solid 1px black;">&nbsp;</div>
						<div class="divColumnaQuarter" style="width: 129px;height: 18px;border-bottom: solid 1px black;">$<input type="text" class="inputEspecificacionesPrecioMinutoHorarioNormal"/></div>
						<div class="divColumnaQuarterFiller" style="width: 232px;height: 18px;border-bottom: solid 1px black;">$<input type="text" class="inputEspecificacionesPrecioMinutoOtrasOperadoras"/></div>
					</div>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaThird" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaThird" style="width: 175px;height: 18px;">Inclu&iacute;do</div>
						<div class="divColumnaThirdFiller" style="width: 364px;height: 18px;border-bottom: solid 1px black;">Minutos gratis destinos m&oacute;viles y fijos de antel <sup>(4)</sup></div>
					</div>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaThird" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaThird" style="width: 175px;height: 18px;border-bottom: solid 1px black;">&nbsp;</div>
						<div class="divColumnaThirdFiller" style="width: 364px;height: 18px;border-bottom: solid 1px black;"><input type="text" class="inputEspecificacionesMovilesGratisDestinosMovilesYFijosAntel"/></div>
					</div>
<%
		} else {
%>
					<div class="divFila" style="height: 30px;border-bottom: none;">
						<div class="divColumnaFifth" style="width: 108px;height: 28px;">Llamadas</div>
						<div class="divColumnaFifth" style="width: 175px;height: 28px;">&nbsp;</div>
						<div class="divColumnaFifth" style="width: 75px;height: 28px;border-bottom: solid 1px black;">Destino Antel (por minuto)</div>
						<div class="divColumnaFifth" style="width: 120px;height: 28px;border-bottom: solid 1px black;">Destino celular (otras compa&ntilde;&iacute;as) (por minuto)</div>
						<div class="divColumnaFifthFiller" style="width: 163px;height: 28px;border-bottom: solid 1px black;">Destinos a m&oacute;viles amigos (por minuto)<sup>(2)</sup></div>
					</div>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaFifth" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaFifth" style="width: 175px;height: 18px;border-bottom: solid 1px black;">&nbsp;</div>
						<div class="divColumnaFifth" style="width: 75px;height: 18px;border-bottom: solid 1px black;">$<input type="text" class="inputEspecificacionesPrecioMinutoHorarioNormal"/></div>
						<div class="divColumnaFifth" style="width: 120px;height: 18px;border-bottom: solid 1px black;">$<input type="text" class="inputEspecificacionesPrecioMinutoOtrasOperadoras"/></div>
						<div class="divColumnaFifthFiller" style="width: 163px;height: 18px;border-bottom: solid 1px black;">$<input type="text" class="inputEspecificacionesPrecioMinutoNumerosAmigos"/></div>
					</div>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaQuarter" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaQuarter" style="width: 175px;height: 18px;">Inclu&iacute;do</div>
						<div class="divColumnaQuarter" style="width: 198px;height: 18px;border-bottom: solid 1px black;">M&oacute;viles gratis destinos Antel<sup>(3)</sup></div>
						<div class="divColumnaQuarterFiller" style="width: 163px;height: 18px;border-bottom: solid 1px black;">Fijo gratis de ANTEL<sup>(4)</sup></div>
					</div>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaQuarter" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaQuarter" style="width: 175px;height: 18px;border-bottom: solid 1px black;">&nbsp;</div>
						<div class="divColumnaQuarter" style="width: 198px;height: 18px;border-bottom: solid 1px black;"><input type="text" class="inputEspecificacionesMinutosGratisMovil"/></div>
						<div class="divColumnaQuarterFiller" style="width: 163px;height: 18px;border-bottom: solid 1px black;"><input type="text" class="inputEspecificacionesMinutosGratisFijo"/></div>
					</div>
<%
		}
%>
<!-- 
					<div class="divFila" style="height: 20px;border-bottom: none;">
<%
		if (contratoTO.getNuevoPlan() != null && 
			(contratoTO.getNuevoPlan().getDescripcion().startsWith("1155")
			|| contratoTO.getNuevoPlan().getDescripcion().startsWith("2385"))
		) {
%>
						<div class="divColumnaThird" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaThird" style="width: 175px;height: 18px;">Inclu&iacute;do</div>
						<div class="divColumnaThirdFiller" style="width: 364px;height: 18px;border-bottom: solid 1px black;">M&oacute;viles gratis destinos m&oacute;viles y fijos de Antel (****)</div>
					</div>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaThird" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaThird" style="width: 175px;height: 18px;border-bottom: solid 1px black;">&nbsp;</div>
						<div class="divColumnaThirdFiller" style="width: 364px;height: 18px;border-bottom: solid 1px black;"><input type="text" class="inputEspecificacionesMovilesGratisDestinosMovilesYFijosAntel"/></div>

<%
		} else {
%>
						<div class="divColumnaQuarter" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaQuarter" style="width: 175px;height: 18px;">Inclu&iacute;do</div>
						<div class="divColumnaQuarter" style="width: 198px;height: 18px;border-bottom: solid 1px black;">M&oacute;viles gratis destinos Antel<sup>(3)</sup></div>
						<div class="divColumnaQuarterFiller" style="width: 163px;height: 18px;border-bottom: solid 1px black;">Fijo gratis de ANTEL<sup>(4)</sup></div>
					</div>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaQuarter" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaQuarter" style="width: 175px;height: 18px;border-bottom: solid 1px black;">&nbsp;</div>
						<div class="divColumnaQuarter" style="width: 198px;height: 18px;border-bottom: solid 1px black;"><input type="text" class="inputEspecificacionesMinutosGratisMovil"/></div>
						<div class="divColumnaQuarterFiller" style="width: 163px;height: 18px;border-bottom: solid 1px black;"><input type="text" class="inputEspecificacionesMinutosGratisFijo"/></div>
					
<% 
		}
%>
					</div>
-->
					<div class="divFila" style="height: 20px;">
						<div class="divColumnaThird" style="width: 108px;">&nbsp;</div>
						<div class="divColumnaThird" style="width: 175px;">M&aacute;ximo rendimiento en minutos<sup>(1)</sup></div>
						<div class="divColumnaThirdFiller" style="width: 350px;"><input type="text" class="inputEspecificacionesRendimientoMinutosAntelHorarioNormal"/> minutos destino Antel &oacute; <input type="text" class="inputEspecificacionesRendimientoMinutosOtrasOperadoras"/> minutos otros destinos</div>
					</div>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaThird" style="width: 108px;height: 18px;">Mensajer&iacute;a</div>
						<div class="divColumnaThird" style="width: 175px;height: 18px;">&nbsp;</div>
						<div class="divColumnaThirdFiller" style="width: 364px;height: 18px;border-bottom: solid 1px black;">Precio SMS (por sms)</div>
					</div>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaThird" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaThird" style="width: 175px;height: 18px;border-bottom: solid 1px black;">&nbsp;</div>
						<div class="divColumnaThirdFiller" style="width: 364px;height: 18px;border-bottom: solid 1px black;">$<input type="text" class="inputEspecificacionesPrecioSMS"/></div>
					</div>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaThird" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaThird" style="width: 175px;height: 18px;">Inclu&iacute;do</div>
						<div class="divColumnaThirdFiller" style="width: 364px;height: 18px;border-bottom: solid 1px black;">SMS gratis destinos Antel<sup>(5)</sup></div>
					</div>
					<div class="divFila" style="height: 20px;">
						<div class="divColumnaThird" style="width: 108px;">&nbsp;</div>
						<div class="divColumnaThird" style="width: 175px;">&nbsp;</div>
						<div class="divColumnaThirdFiller" style="width: 364px;"><input type="text" class="inputEspecificacionesSMSGratisMovil"/> sms</div>
					</div>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaThird" style="width: 108px;height: 18px;">Navegaci&oacute;n</div>
						<div class="divColumnaThird" style="width: 175px;height: 18px;">&nbsp;</div>
						<div class="divColumnaThirdFiller" style="width: 364px;height: 18px;border-bottom: solid 1px black;">Precio del MB (por MB)<sup><%= (!conLimite) ? "(6)" : "" %></sup></div>
					</div>
					<div class="divFila" style="height: 20px;border-bottom: none;">
						<div class="divColumnaThird" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaThird" style="width: 175px;height: 18px;border-bottom: solid 1px black;">&nbsp;</div>
						<div class="divColumnaThirdFiller" style="width: 364px;height: 18px;border-bottom: solid 1px black;">$<input type="text" class="inputEspecificacionesConsumoFueraBono"/></div>
					</div>
					<div class="divFila" style="height: 20px;">
						<div class="divColumnaThird" style="width: 108px;height: 18px;">&nbsp;</div>
						<div class="divColumnaThird" style="width: 175px;height: 18px;">M&aacute;ximo rendimiento para datos<sup>(1)</sup></div>
						<div class="divColumnaThirdFiller" style="height: 18px;"><input type="text" class="inputEspecificacionesIncluyeParaNavegacionCelular"/> GB</div>
					</div>
					<div class="divFila" style="height: 20px;">
						<div class="divColumnaHalf" style="width: 108px;height: 18px;">Duraci&oacute;n contractual<sup>(7)</sup></div>
						<div class="divColumnaHalfFiller" style="width: 542px;height: 18px;"><input type="text" class="inputEspecificacionesDuraccionContractual"/></div>
					</div>
				</div>
<%
		if (conLimite) {
%>
				<div class="">
					Este plan permite realizar recargas de saldo y promocionales, servicios de valor agregado y contratar paquetes de roaming a pagar en factura. Verificar los topes del plan en www.antel.com.uy.
				</div>
<%
			if (contratoTO.getNuevoPlan() != null && contratoTO.getNuevoPlan().getBeneficioIncluidoEnLlamadas()) {
%>
				<div class=""><span style="font-weight: bold">Rendimiento:</span><sup>(1)</sup></div>
				<div class="">- El rendimiento detallado es el m&aacute;ximo, utilizando la totalidad del importe mensual en dicho tipo de tr&aacute;fico.</div>
				<div class="">- El m&aacute;ximo rendimiento en minutos a destinos Antel, aplica una vez superado los minutos gratis a destinos m&oacute;viles y fijos Antel.</div>
<%
			} else {
%>				
				<div class=""><span style="font-weight: bold">Rendimiento:</span><sup>(1)</sup> El rendimiento detallado es el m&aacute;ximo, utilizando la totalidad del importe mensual en dicho tipo de tr&aacute;fico.</div>
<%
			}
		} else {
%>
				<div class=""><span style="font-weight: bold;">Rendimiento:</span><sup>(1)</sup> El rendimiento detallado es el m&aacute;ximo, utilizando la totalidad del importe mensual en dicho tipo de tr&aacute;fico. Superado el l&iacute;mite antes referido se facturar&aacute;n a la tarifa correspondiente al Plan aqu&iacute; detallado.</div>
<%
		}
%>			
				<div class="" style="font-weight: bold;">Llamadas:</div>
<!-- 
<%
		if (contratoTO.getNuevoPlan() != null && 
			(contratoTO.getNuevoPlan().getDescripcion().startsWith("1155")
			|| contratoTO.getNuevoPlan().getDescripcion().startsWith("2385"))
		) {
%>
				<div class="">Beneficio destinos gratis:</div>
				<div class="">Antel le regala todos los meses minutos de comunicaci&oacute;n y mensajes de texto, no acumulables mes a mes.</div>
				<div class="">(****) Los minutos incluidos por mes corresponden a la m&aacute;xima cantidad de minutos de comunicaci&oacute;n a cualquier n&uacute;mero destino Antel. Los minutos que pudieren superar el l&iacute;mite antes referido se facturar&aacute;n a la tarifa del minuto de comunicaci&oacute;n correspondiente al Plan aqu&iacute; detallado.</div>
				<div class="">(*****) Los sms inclu&iacute;dos son para enviar a <input type="text" class="inputEspecificacionesSMSGratisCantidadCelulares"/> n&uacute;mero M&oacute;vil de Antel. Los sms que pudieran superar el l&iacute;mite antes referido se facturar&aacute;n a la tarifa correspondiente al Plan aqu&iacute; detallado.</div>
				<div class="">Dichos beneficios operan exclusivamente dentro del territorio nacional. Por m&aacute;s informaci&oacute;n sobre los beneficios del plan contratado, consulte la Web de Antel: www.antel.com.uy. El Reglamento de Servicios de ANTEL y las Condiciones de Contrataci&oacute;n puede consultarse en la Web de ANTEL www.antel.com.uy, o en los Centros Comerciales y/o Tiendas a trav&eacute;s de los Terminales de Autogesti&oacute;n.</div>
<%
		} else {
%>
				<div class="" style="font-weight: bold;">Beneficio n&uacute;meros amigos:</div>
				<div class="">-<sup>(2)</sup> Puede elegir 5 amigos que tengan celulares de Antel, para comunicarse a una tarifa preferencial. Los n&uacute;meros amigos se pueden modificar cada 30 d&iacute;as.</div>
				<div class="" style="font-weight: bold;">Beneficio destinos gratis:</div>
				<div class="">Antel le regala todos los meses minutos de comunicaci&oacute;n, no acumulables mes a mes. Por este plan Ud. tiene los siguientes destinos gratis:</div>
				<div class="">-<sup>(3)</sup> 5 n&uacute;mero/s M&oacute;viles de Antel.</div>
				<div class="">-<sup>(4)</sup> 5 n&uacute;mero/s de tel&eacute;fono fijo.</div>
				<div class="">Una vez superados los minutos gratis, se facturar&aacute; a la tarifa corresondiente al plan aqu&iacute; detallado. Los clientes con documento RUT no pueden acceder a este benficio, excepto las empresas unipersonales. Los destinos gratis se pueden modificar cada 3 meses.</div>
				<!-- 
				<div class="">(*****) Los sms inclu&iacute;dos son para enviar a <input type="text" class="inputEspecificacionesSMSGratisCantidadCelulares"/> n&uacute;mero M&oacute;vil de Antel. Los sms que pudieran superar el l&iacute;mite antes referido se facturar&aacute;n a la tarifa correspondiente al Plan aqu&iacute; detallado.</div>
				<div class="">Los mismos los puede seleccionar v&iacute;a Web en www.antel.com.uy. Cada destino seleccionado puede modificarse cada tres meses. Los clientes con documento RUT no pueden acceder a los Nros Gratis, excepto las empresas unipersonales. Dichos beneficios operan exclusivamente dentro del territorio nacional. Por m&aacute;s informaci&oacute;n sobre los beneficios del plan contratado, consulte la Web de Antel: www.antel.com.uy. El Reglamento de Servicios de ANTEL y las Condiciones de Contrataci&oacute;n puede consultarse en la Web de ANTEL www.antel.com.uy, o en los Centros Comerciales y/o Tiendas a trav&eacute;s de los Terminales de Autogesti&oacute;n.</div>
				 -->
<% 
		}
%>	
-->

<%
		if (contratoTO.getNuevoPlan() != null && 
			contratoTO.getNuevoPlan().getBeneficioIncluidoEnLlamadas()) {
%>
				<div class="">Antel le regala todos los meses minutos de comunicaci&oacute;n. Superado el l&iacute;mite referido se facturar&aacute;n a la tarifa correspondiente al plan aqu&iacute; detallado.</div>
				<div class="">-<sup>(4)</sup> Llamadas incluidas para comunicarse a cualquier n&uacute;mero m&oacute;vil o fijo de Antel, no acumulables mes a mes.</div>
<%
		} else {
%>
				<div class="" style="font-weight: bold;">Beneficio n&uacute;meros amigos:</div>
				<div class="">-<sup>(2)</sup> Puede elegir 5 amigos que tengan celulares de Antel, para comunicarse a una tarifa preferencial. Los n&uacute;meros amigos se pueden modificar cada 30 d&iacute;as.</div>
				<div class="" style="font-weight: bold;">Beneficio destinos gratis:</div>
				<div class="">Antel le regala todos los meses minutos de comunicaci&oacute;n, no acumulables mes a mes. Por este plan Ud. tiene los siguientes destinos gratis:</div>
				<div class="">-<sup>(3)</sup> 5 n&uacute;mero/s M&oacute;viles de Antel.</div>
				<div class="">-<sup>(4)</sup> 5 n&uacute;mero/s de tel&eacute;fono fijo.</div>
				<div class="">Una vez superados los minutos gratis, se facturar&aacute; a la tarifa corresondiente al plan aqu&iacute; detallado. Los clientes con documento RUT no pueden acceder a este benficio, excepto las empresas unipersonales. Los destinos gratis se pueden modificar cada 3 meses.</div>
				<!-- 
				<div class="">(*****) Los sms inclu&iacute;dos son para enviar a <input type="text" class="inputEspecificacionesSMSGratisCantidadCelulares"/> n&uacute;mero M&oacute;vil de Antel. Los sms que pudieran superar el l&iacute;mite antes referido se facturar&aacute;n a la tarifa correspondiente al Plan aqu&iacute; detallado.</div>
				<div class="">Los mismos los puede seleccionar v&iacute;a Web en www.antel.com.uy. Cada destino seleccionado puede modificarse cada tres meses. Los clientes con documento RUT no pueden acceder a los Nros Gratis, excepto las empresas unipersonales. Dichos beneficios operan exclusivamente dentro del territorio nacional. Por m&aacute;s informaci&oacute;n sobre los beneficios del plan contratado, consulte la Web de Antel: www.antel.com.uy. El Reglamento de Servicios de ANTEL y las Condiciones de Contrataci&oacute;n puede consultarse en la Web de ANTEL www.antel.com.uy, o en los Centros Comerciales y/o Tiendas a trav&eacute;s de los Terminales de Autogesti&oacute;n.</div>
				 -->
<% 
		}
%>

				<div class="" style="font-weight: bold;">Mensajer&iacute;a:</div>
				<div class=""><sup>(5)</sup> Antel le regala todos los meses mensajes de texto, no acumulables mes a mes. Los mismos son para enviar a cualquier n&uacute;mero M&oacute;vil de Antel.</div>
				<div class="" style="font-weight: bold;">Navegaci&oacute;n:</div>
				<div class="">- Beneficio WhatsApp gratis: consulte las condiciones en http://antel.com.uy/whatsapp</div>			
<%
		if (!conLimite) {
%>
				<div class="">- Consumido los datos cubiertos por el consumo m&iacute;nimo del plan, la velocidad de bajada ser&aacute; de hasta 1 Mbps. La velocidad se restablece con el nuevo per&iacute;odo de facturaci&oacute;n mensual.</div>
				<!-- 
				<div class="" style="font-weight: bold;">- Se aplica un tope de facturaci&oacute;n mensual de $<input type="text" class="inputEspecificacionesTopeFacturacionMensual"/>, impuestos incluidos para el tr&aacute;fico de datos.</div>
				 -->
<%
		}
%>
				<div class="">- Las velocidades de conexi&oacute;n est&aacute;n sujetas a los recursos disponibles y cobertura de la red LTE de Antel.</div>
				<div class="">- En caso de no existir cobertura LTE en la zona, se traficar&aacute; por la red 3.7G. Puede consultar la cobertura en www.antel.com.uy.</div>
				
<%
		if (!conLimite) {
%>
				<div class=""><sup>(6)</sup> Precio del MB aplica tanto para el consumo dentro del plan como para lo que se supere del consumo m&iacute;nimo.</div>
<%
		}
%>
				<div class=""><span style="font-weight: bold;">Roaming:</span> Tu plan cuenta con el beneficio Roaming como en casa. Consult&aacute; los pa&iacute;ses habilitados y detalles de este beneficio en antel.com.uy/casa</div>
				<div class="">Los beneficios se eligen en www.antel.com.uy, por la App o *611# y rigen dentro del territorio nacional, por m&aacute;s informaci&oacute;n en www.antel.com.uy</div>
				<div class="" style="font-weight: bold;">Duraci&oacute;n contractual: <sup>(7)</sup></div>
				<div class="">- Plazo contractual culminado, si realiza un cambio de plan sin comprar un equipo m&oacute;vil, el nuevo plan ser&aacute; de libre rescisi&oacute;n.</div>
				<div class="">- Plazo contractual no culminado, si realiza un cambio de plan a uno de igual o mayor consumo m&iacute;nimo, sin comprar un equipo m&oacute;vil, se mantendr&aacute; la duraci&oacute;n del contrato original.</div>
				<div class="">En las contrataciones no presenciales el consumidor podr&aacute; ejercer el derecho consagrado en el art&iacute; 16 de la Ley 17.250, dentro de los cinco d&iacute;as h&aacute;biles siguientes a la contrataci&oacute;n del servicio o entrega del producto, cumpliendo en todo, con las condiciones establecidas en dicha norma.</div>
				<div class="">El Reglamento de Servicios de ANTEL y las Condiciones de Contrataci&oacute;n forman parte de este documento y puden consultarse en la Web de ANTEL www.antel.com.uy, en los Locales Comerciales y/o Tiendas.</div>
				<div class="">Los cargos mensuales establecidos en el contrato ser&aacute;n modificados autom&aacute;ticamente de conformidad con lo dispuesto en el Art. 12 del Decreto de Ley Nº 14.235 de 25 de julio de 1974 en la redacci&oacute;n dada por el Art. 13 de la Ley Nº 16.211 del 1 de octubre de 1991.</div>
				<div class="">El documento original es entregado al cliente en el mismo acto de su suscripci&oacute;n. Se guarda copia fiel del original.</div>
				<br/>
				<div class="">
					Observaciones:
				</div>
				<div style="float: left;width: 30%;">
					<div style="width: 75%;border-bottom: dotted 1px black;height: 20px;">&nbsp;</div>
				</div>
				<div style="float: left;width: 30%;">
					<div style="width: 75%;border-bottom: dotted 1px black;height: 20px;">&nbsp;</div>
				</div>
				<div style="float: left;width: 30%;">
					<div style="width: 75%;border-bottom: dotted 1px black;height: 20px;">&nbsp;</div>
				</div>
				<div style="float: left;width: 30%;height: 20px;">Firma del cliente</div>
				<div style="float: left;width: 30%;height: 20px;">Aclaraci&oacute;n de firma</div>
				<div style="float: left;width: 30%;height: 20px;">Documento</div>
				<div style="float: left;width: 30%;">
					<div style="width: 75%;border-bottom: dotted 1px black;height: 20px;">&nbsp;</div>
				</div>
				<div style="float: left;width: 30%;">
					<div style="width: 75%;border-bottom: dotted 1px black;height: 20px;">&nbsp;</div>
				</div>
				<div style="float: left;width: 30%;">
					<div style="width: 75%;border-bottom: dotted 1px black;height: 20px;">&nbsp;</div>
				</div>
				<div style="float: left;width: 30%;height: 20px;">Firma por Antel</div>
				<div style="float: left;width: 30%;height: 20px;">Aclaraci&oacute;n de firma</div>
				<div style="float: left;width: 30%;height: 20px;">Usuario/Documento</div>
			</div>
		</div>
	</div>
<%
	}

	if (!contratoTO.getModelo().getId().equals(
		new Long(Configuration.getInstance().getProperty("modelo.SinEquipo")))
	) {
		
		vias = 2;
		for (int i=0; i<vias; i++) {
%>
	<div class="divA4Sheet" style="border-top: none;">
		<div class="divA4SheetContent">
			<div class="divPageHeading" style="height: 25mm;">
				<div style="margin: auto;width: 15mm;height: 100%;"><img class="imgGarantiaLogo" class="imgGarantiaLogo" src="/LogisticaWEB/images/logo-vos.png"/></div>
			</div>
			<div class="divPageContent" style="width: 88%;">
				<div class="divGarantiaTitulo">GARANT&Iacute;A LIMITADA</div>
				<br/>
				<div class=""><input type="text" class="inputGarantiaEmpresa"/> le garantiza que, durante el periodo de garant&iacute;a, <input type="text" class="inputGarantiaService"/> o el servicio Oficial autorizado por la comp&ntilde;&iacute;a subsanar&aacute; de forma gratuita y dentro de un plazo comercialmente razonable no acotado los defectos del equipo mediante la reparaci&oacute;n o, en el caso de que el Servicio Oficial lo considerase necesario a su discreci&oacute;n, la sustituci&oacute;n del Producto de conformidad con la presente Garant&iacute;a Limitada.</div>
				<br/>
				<div class=""><span class="spanGarantiaTitulo">Periodo de Garant&iacute;a:</span> El Periodo de garant&iacute;a dar&aacute; comienzo en el momento de la compra original del Producto por parte del usuario. El Producto puede estar formado por varias piezas diferentes y las distintas piezas pueden estar cubiertas por un periodo de garant&iacute;a distinto (en lo sucesivo, el "Periodo de Garant&iacute;a"). Los distintos Periodos de Garant&iacute; son: a) Doce (12) meses para el dispositivo m&oacute;vil, b) Seis (6) meses para los siguientes accesorios y piezas consumibles, bater&iacute;as, cargadores, etc., c) treinta (30) d&iacute;as para tarjetas de memoria y dem&aacute;s accesorios.</div>
				<br/>
				<div class=""><span class="spanGarantiaTitulo">C&oacute;mo obtener el servicio garantizado:</span> Cualquier reclamaci&oacute;n realizada en virtud de la presente Garant&iacute;a Limitada estar&aacute; sujeta a la notificaci&oacute;n por su parte del presunto defecto a <input type="text" class="inputGarantiaService"/> o a una compa&ntilde;&iacute;a de servicio oficial autorizada dentro de un plazo razonable a partir de su descubrimiento y, en cualquier caso, nunca posterior a la fecha de vencimiento del Per&iacute;odo de Garant&iacute;a. <span class="spanGarantiaNegrita">Al realizar una reclamaci&oacute;n en virtud de la presente Garant&iacute;a Limitada deber&aacute; proporcionar: a) el producto (o la pieza afectada) y b) el comprobante de compra original, que indique con claridad el nombre y direcci&oacute;n del vendedor, la fecha y el lugar de la compra as&iacute; como la presentaci&oacute;n del original del presente Certificado de Garant&iacute;a debidamente sellado y firmado por dicho distribuidor, el tipo de producto y el IMEI u otro n&uacute;mero de serie.</span></div>
				<br/>
				<div class=""><span class="spanGarantiaTitulo">¿Qu&eacute; es lo que no cubre?</span></div>
				<br/>
				<div class="">
					<ol style="margin: 0;padding: 0;">
						<li>La presente Garant&iacute;a Limitada no cubre manuales de usuario ni software, configuraci&oacute;n, contenidos, datos o enlaces de terceros, incluidos o descargados en el Producto por usted. <input type="text" class="inputGarantiaService"/> no garantiza que el software del producto se ajuste a sus necesidades, funcion en combinaci&oacute;n con cualquier hardware o software provisto por un proveedor independiente, ni que el funcionamiento de cualquier software est&eacute; libre de sufrir interrupciones o errores, ni que cualquier defecto presente en el software pueda ser objeto de rectificaci&oacute;n o ser rectificado.</li>
						<li>La presente Garant&iacute;a Limitada no cubre a) el desgaste normal (incluido, sin car&aacute;cter limitativo, el desgaste de lentes de c&aacute;maras, bater&iacute;as o pantallas), b) los defectos ocasionados por una mala manipulaci&oacute;n (incluidos, sin car&aacute;cter limitativo, los defectos ocasionados por elementos afilados, doblado, compresi&oacute;n o ca&iacute;das, etc.), ni c) los defectos o da&ntilde;os ocasionados por una mala utilizaci&oacute;n del Producto, incluido el uso contrario a las instrucciones provistas por la marca del Producto (por ejemplo, seg&uacute;n lo dispuesto en la gu&iacute;a del usuario del Producto) y/o e) otros actos m&aacute;s all&aacute; del control razonable de <input type="text" class="inputGarantiaService"/></li>
						<li>La presente Garant&iacute;a Limitada no cubre los defectos o presuntos defectos ocasionados por el hecho de que el Producto fuera utilizado con, o en relaci&oacute;n con, cualquier producto, equipamiento, software y/o servicio no fabricado o suministrado por la marca o fuera utilizado de otra manera que no fuera para su uso previsto. Los defectos pueden ser ocasionados por virus debidos al acceso no autorizado por parte de usted o un tercero a servicios, otras cuentas, redes o sistemas inform&aacute;ticos. Dicho acceso no autorizado puede tener lugar mediante ataques de hackers, obtenci&oacute;n il&iacute;cita de contrase&ntilde;as u otros medios similares.</li>
						<li>La presente Garant&iacute;a Limitada no cubre los defectos ocasionados por el hecho de que la bater&iacute;a haya sido sometida a cortocircuito o por el hecho de que los sellos del cierre de la bater&iacute;a o las c&eacute;lulas se hayan roto o muestren evidencias de manipulaci&oacute;n, o por el hecho de que la bater&iacute;a haya sido utilizada en equipos para los que no hubiera sido especificada.</li>
						<li>La presente Garant&iacute;a Limitada no ser&aacute; v&aacute;lida si el Producto ha sido abierto, modificado o reparado por otros que no sean un centro de servicio autorizado, si ha sido reparado utilizando piezas de repuesto no autorizadas o si el n&uacute;mero de serie del Producto, el c&oacute;digo de fecha de equipamiento m&oacute;vil o el n&uacute;mero IMEI han sido eliminados, borrados, desfigurados, alterados o aparecen ilegibles en forma alguna, algo que ser&aacute; determinado exclusivamente a discreci&oacute;n de <input type="text" class="inputGarantiaService"/> o por el servicio Oficial Autorizado.</li>
						<li>La presente Garant&iacute;a Limitada no ser&aacute; v&aacute;lida si el Producto ha sido expuesto a la humedad o a condiciones atmosf&eacute;ricas o t&eacute;rmicas extremas o a cambios r&aacute;pidos de las mismas, a la corrosi&oacute;n, la oxidaci&oacute;n, el vertido de alimentos, l&iacute;quidos, arena o a la influencia de productos qu&iacute;micos o cualquier elemento extra&ntilde;os.</li>
					</ol>
				</div>
				<br/>
				<div class="" style="width: 100%;text-align: center;text-decoration: underline;">CERTIFICADO DE GARANTIA</div>
				<br/>
				<div class="">
					<div class="">Marca: <input type="text" class="inputGarantiaProductoMarca"/></div>
					<div class="">Modelo: <input type="text" class="inputGarantiaProductoModelo"/></div>
					<div class="">Nro. Serie: <input type="text" class="inputGarantiaNumeroSerie"/></div>
					<div class="">Nombre, Apellido: <input type="text" class="inputGarantiaNombreApellido"/></div>
					<div class="">M&oacute;vil: <input type="text" class="inputGarantiaMID"/></div>
				</div>
				<br/><br/>
				<div class="" style="float: left;width: 50%;height: 20px;">FIRMA CLIENTE</div>
				<div class="" style="float: left;width: 45%;height: 20px;">POR LA EMPRESA</div>
				<br/><br/>
				<div class=""><input type="text" class="inputGarantiaDatosService"/></div>
			</div>
		</div>
	</div>
<%
		}
	}

	if (contratoTO.getMotivoCambioPlan() != null) {
	
		vias = 2;
		for (int i=0; i<vias; i++) {
%>
	<div class="divA4Sheet divA4SheetCambioPlan" style="border-top: none;">
		<div class="divA4SheetContent">
			<div class="divPageHeading" style="height: 43mm;">
				<div style="margin: auto;width: 15mm;height: 100%;">&nbsp;</div>
			</div>
			<div class="divPageContent" style="width: 88%;">
				<div class="divTitulo" style="height: 18px;">Fecha: <input type="text" class="inputCambioPlanFecha"/></div>
				<div class="divTitulo" style="height: 17px;">Datos del cliente:</div>
				<div class="divTabla">
					<div class="divFila" style="height: 28px;">
						<div class="divColumnaHalf" style="width: 324px;padding-top: 8px;">
							Nombre: <input type="text" class="inputCambioPlanClienteNombre"/>
						</div>
						<div class="divColumnaHalfFiller" style="width: 312px;margin-left: 6px;padding-top: 8px;">
							Doumento: <input type="text" class="inputCambioPlanClienteDocumento"/>
						</div>
					</div>
					<div class="divFila" style="height: 28px;">
						<div class="divColumnaHalf" style="width: 324px;padding-top: 8px;">
							Nº. Contrato: <input type="text" class="inputCambioPlanNumeroContrato"/>
						</div>
						<div class="divColumnaHalfFiller" style="width: 312px;margin-left: 6px;padding-top: 8px;">
							Nº. MID: <input type="text" class="inputCambioPlanMid"/>
						</div>
					</div>
					<div class="divFila" style="height: 28px;">
						<div class="divColumnaHalf" style="width: 324px;padding-top: 8px;">
							Plan Vigente: <input type="text" class="inputCambioPlanPlan"/>
						</div>
						<div class="divColumnaHalfFiller" style="width: 312px;margin-left: 6px;padding-top: 8px;">
							Plan Solicitado: <input type="text" class="inputCambioPlanNuevoPlan"/>
						</div>
					</div>
				</div>
				<div class="divTitulo" style="height: 40px;">Motivos del Cambio de Plan:</div>
				<div class="divTabla">
					<div class="divFila" style="height: 159px;margin-top: 5px;">
						<div class="divColumnaFull">
							<input type="text" class="inputCambioPlanMotivosCambioPlan"/>
						</div>
					</div>
				</div>
				<div class="divTitulo" style="height: 25px;">Fecha a partir de la cual adquiere vigencia el Cambio de Plan:</div>
				<div class="divTabla">
					<div class="divFila" style="height: 28px;margin-top: 5px;">
						<div class="divColumnaFull">
							<input type="text" class="inputCambioPlanFechaVigencia"/>
						</div>
					</div>
				</div>
				<div class="divTitulo" style="height: 20px;margin-top: 15px;">Costos asociados al Cambio de Plan (si corresponde)</div>
				<div class="divCambioPlanCostosAsociados" style="font-size: 7pt;">
					ANTEL hace saber al cliente y &eacute;ste acepta, que el precio del CHIP de Telefon&iacute;a M&oacute;vil asciende a&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$<input type="text" class="inputCambioPlanPrecioChip"/> (pesos uruguayos).
					<br/>
					El importe correspondiente al CHIP de Telefon&iacute;a M&oacute;vil ser&aacute; facturado al cliente en la primera factura correspondiente al plan arriba se&ntilde;alado.
					<br/>
					En caso de hurto o extrav&iacute;o el cliente se har&aacute; cargo de la reposici&oacute;n del CHIP de Telefon&iacute;a M&oacute;vil. En estos casos ser&aacute; responsable de cualquier uso que se haga del Servicio Telef&oacute;nico Celular hasta tanto no comunique el hecho a ANTEL.
				</div>
				<div class="divTitulo" style="height: 26px;">Consideraciones generales:</div>
				<div class="divCambioPlanConsideracionesGenerales" style="font-size: 7pt;">
					<ol style="padding-left: 12px;margin-top: 0;">
						<li>Respecto al Roaming en (Clientes itinerantes): ANTEL hace saber al Cliente y este acepta que el alcance del roaming queda sujeto a la implementaci&oacute;n de los acuerdos suscritos o a suscribirse entre ANTEL y las operadoras del extranjero.</li>
						<li style="padding-top: 15px;">Para el caso de Clientes que habiendo adherido a alguno de los planes durante el per&iacute;odo de la promoci&oacute;n opten por adherir a otro plan durante dicho per&iacute;odo o con posterioridaad al mismo, ANTEL y el Cliente acuerdan incorporar la siguiente previsi&oacute;n al apartado 1) Obligaciones de ANTEL: "Si el cliente contrat&oacute; un plan con terminal incluido, y cambia a un plan que implica menores erogaciones, deber&aacute; abonar el importe correspondiente al terminal, que le restare al momento de realizar el Cambio de Plan".</li>
						<li style="padding-top: 17px;">Para el caso que se trate de Clientes contractuales, las partes acuerdan que el Cliente mantiene la obligaci&oacute;n de abonar el sald odel precio del terminal que pudiere estar pendiente, de acuerdo con lo previsto en el apartado XI) REINTEGRO POR CONCEPTO DEL TERMINAL, del contrato respectivo.</li>
					</ol>
				</div>
				<div class="divTabla">
					<div class="divFila" style="height: 30px;">
						<div class="divColumnaHalf" style="width: 324px;padding-top: 8px;">
							Firma del titular:
						</div>
						<div class="divColumnaHalfFiller" style="width: 312px;margin-left: 6px;padding-top: 8px;">
							Aclaraci&oacute;n de firma:
						</div>
					</div>
				</div>
				<div class="divTitulo" style="height: 25px;">&nbsp;</div>
				<div class="divTabla">
					<div class="divFila" style="height: 28px;">
						<div class="divColumnaHalf" style="width: 324px;padding-top: 8px;">
							Firma del representante:
						</div>
						<div class="divColumnaHalfFiller" style="width: 312px;margin-left: 6px;padding-top: 8px;">
							Aclaraci&oacute;n de firma: <input type="text" class="inputCambioPlanBackofficeNombre"/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<%
		}
	}
%>
</body>
</html>