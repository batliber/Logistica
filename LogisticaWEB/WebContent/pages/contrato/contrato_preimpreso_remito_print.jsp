<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Remito</title>
	<script type="text/javascript">
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ContratoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./contrato_preimpreso_remito_print.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./contrato_preimpreso_remito_print.css"/>
</head>
<body>
	<div class="divPrintingButtonBar">
		<div class="divButtonBar">
			<div class="divButton"><input type="submit" value="Imprimir" onclick="javascript:inputImprimirOnClick(event, this)"></div>
			<div class="divButton"><input type="submit" value="Cancelar" onclick="javascript:inputCancelarOnClick(event, this)"></div>
		</div>
	</div>
	<div class="divA4Sheet">
		<div class="divA4SheetContent">
			<div class="divPageHeading">&nbsp;</div>
			<div class="divPageContent">
<%
	int vias = 2;
	for (int i=0; i<vias; i++) {
%>
				<div class="divRemito">
					<div class="divCabezal">
						<div class="divCabezalDatosCliente">
							<div class="divCabezalDatosClienteFiller">&nbsp;</div>
							<div class="divCabezalDatosClienteDatos">
								<div class="divCabezalDatosClienteNombre">&nbsp;</div>
								<div class="divCabezalDatosClienteDireccion">&nbsp;</div>
							</div>
						</div>
						<div class="divCabezalDatosFacturacion">
							<div class="divCabezalDatosFacturacionTipoDoc">REMITO</div>
							<div class="divCabezalDatosFacturacionFecha">&nbsp;</div>
							<div class="divCabezalDatosFacturacionRUTComprador">&nbsp;</div>
							<div class="divCabezalDatosFacturacionConsFinal">&nbsp;</div>
						</div>
					</div>
					<div class="divLineas">
						<div class="divLineasCantidad">&nbsp;</div>
						<div class="divLineasDescripcion">&nbsp;</div>
						<div class="divLineasPrecioUnitario">&nbsp;</div>
						<div class="divLineasImporte">&nbsp;</div>
					</div>
					<div class="divPie">
						<div class="divPieFiller">&nbsp;</div>
						<div class="divPieTotales">
							<div class="divPieSubtotal">&nbsp;</div>
							<div class="divPieIVA">&nbsp;</div>
							<div class="divPieTotal">&nbsp;</div>
						</div>
					</div>
				</div>
<%
	}
%>
				
			</div>
		</div>
	</div>
</body>