<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Remito</title>
	<script type="text/javascript">
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
	</script>
	<script type="text/javascript" src="./contrato_preimpreso_remito_print.js"></script>
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