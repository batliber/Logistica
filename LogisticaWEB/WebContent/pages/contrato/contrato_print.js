$(document).ready(init);

function init() {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/getById/" + id
	}).then(function(data) {
		$(".divHeader").html(
			data.empresa != null ? data.empresa.nombre : "&nbsp;"
		);
		$("#divNumeroTramite").html(
			data.numeroTramite != null ? data.numeroTramite : "&nbsp;"
		);
		//$("#divNumeroTramiteBarCode").css("background-image", "url(/LogisticaWEB/Barcode?code=" + data.numeroTramite + ")");
		
		$("#divMid").html(
			data.mid != null ? data.mid : "&nbsp;"
		);
		//$("#divMidBarCode").css("background-image", "url(/LogisticaWEB/Barcode?code=" + data.mid + ")");
		$("#divEstado").html(
			data.estado != null ? data.estado.nombre : "&nbsp;"
		);
		$("#divFechaVenta").html(
			data.fechaVenta != null ? formatShortDate(data.fechaVenta) : "&nbsp;"
		);
//		$("#divLocalidad").html(
//			data.localidad != null ? data.localidad : "&nbsp;"
//		);
//		$("#divCodigoPostal").html(
//			data.codigoPostal != null ? data.codigoPostal : "&nbsp;"
//		);
		$("#divFechaVencimiento").html(
			data.fechaFinContrato != null ? formatShortDate(data.fechaFinContrato) : "&nbsp;"
		);
		$("#divNumeroContrato").html(
			data.numeroContrato != null ? data.numeroContrato : "&nbsp;"
		);
		$("#divNumeroFacturaRiverGreen").html(
			data.numeroFacturaRiverGreen != null ? data.numeroFacturaRiverGreen : "&nbsp;"
		);
		$("#divPlan").html(
			data.tipoContratoDescripcion != null ? data.tipoContratoDescripcion : "&nbsp;"
		); 
		$("#divNuevoPlan").html(
			data.nuevoPlan != null ? data.nuevoPlan.descripcion : "&nbsp;"
		);
		$("#divEquipo").html(
			data.producto != null ? data.producto.modelo.descripcion 
				: (data.modelo != null ?
					data.modelo.descripcion 
					: "&nbsp;")
		);
		$("#divNumeroSerie").html(
			data.numeroSerie != null ? data.numeroSerie : "&nbsp;"
		);
		$("#divDocumento").html(
			data.documento != null ? data.documento : "&nbsp;"
		);
		
		var nombre = "";
		if (data.nombre != null) {
			nombre += data.nombre;
		}
		if (data.apellido != null) {
			nombre += " " + data.apellido;
		}
		$("#divNombre").html(nombre != "" ? nombre : "&nbsp;");
		$("#divNumeroFactura").html(
			data.numeroFactura != null ? data.numeroFactura : "&nbsp;"
		); 
//		$("#divDireccionFactura").html(
//			data.direccionFactura != null && data.direccionFactura != "" ? data.direccionFactura : "&nbsp;"
//		);
//		$("#divDireccionEntrega").html(
//			data.direccionEntrega != null && data.direccionEntrega != "" ? data.direccionEntrega : "&nbsp;"
//		);
		
		var direccionFactura = "";
		if (data.direccionFacturaCalle != null && data.direccionFacturaCalle != "") {
			direccionFactura += data.direccionFacturaCalle;
		}
		if (data.direccionFacturaNumero != null && data.direccionFacturaNumero != "") {
			direccionFactura += " " + data.direccionFacturaNumero;
		}
		if (data.direccionFacturaBis != null && data.direccionFacturaBis) {
			direccionFactura += " BIS";
		}
		if (data.direccionFacturaBlock != null && data.direccionFacturaBlock != "") {
			direccionFactura += " Block " + data.direccionFacturaBlock;
		}
		if (data.direccionFacturaApto != null && data.direccionFacturaApto != "") {
			direccionFactura += " Apto. " + data.direccionFacturaApto;
		}
		if (data.direccionFacturaSolar != null && data.direccionFacturaSolar != "") {
			direccionFactura += " Solar " + data.direccionFacturaSolar;
		}
		if (data.direccionFacturaManzana != null) {
			direccionFactura += " Manzana " + data.direccionFacturaManzana;
		}
		
		$("#divDireccionFactura").html(direccionFactura != "" ? direccionFactura : "&nbsp;");
		
		var direccionEntrega = "";
		if (data.direccionEntregaCalle != null && data.direccionEntregaCalle != "") {
			direccionEntrega += data.direccionEntregaCalle;
		}
		if (data.direccionEntregaNumero != null && data.direccionEntregaNumero != "") {
			direccionEntrega += " " + data.direccionEntregaNumero;
		}
		if (data.direccionEntregaBis != null && data.direccionEntregaBis) {
			direccionEntrega += " BIS";
		}
		if (data.direccionEntregaBlock != null && data.direccionEntregaBlock != "") {
			direccionEntrega += " Block " + data.direccionEntregaBlock;
		}
		if (data.direccionEntregaApto != null && data.direccionEntregaApto != "") {
			direccionEntrega += " Apto. " + data.direccionEntregaApto;
		}
		if (data.direccionEntregaSolar != null && data.direccionEntregaSolar != "") {
			direccionEntrega += " Solar " + data.direccionEntregaSolar;
		}
		if (data.direccionEntregaManzana != null) {
			direccionEntrega += " Manzana " + data.direccionEntregaManzana;
		}
		
		$("#divDireccionEntrega").html(direccionEntrega != "" ? direccionEntrega : "&nbsp;");
		$("#divDireccionEntregaNumero").html(data.direccionEntregaNumero != null && data.direccionEntregaNumero != "" ? data.direccionEntregaNumero : "&nbsp;");
		$("#divDireccionEntregaApto").html(data.direccionEntregaApto != null && data.direccionEntregaApto != "" ? data.direccionEntregaApto : "&nbsp;");
		$("#divDireccionEntregaManzana").html(data.direccionEntregaManzana != null ? data.direccionEntregaManzana : "&nbsp;");
		$("#divDireccionEntregaSolar").html(data.direccionEntregaSolar != null && data.direccionEntregaSolar != "" ? data.direccionEntregaSolar : "&nbsp;");
		$("#divDireccionEntregaCodigoPostal").html(data.direccionEntregaCodigoPostal != null ? data.direccionEntregaCodigoPostal : "&nbsp;");
		$("#divDireccionEntregaObservaciones").html(data.direccionEntregaObservaciones != null && data.direccionEntregaObservaciones != "" ? data.direccionEntregaObservaciones : "&nbsp;");
		
		$("#divTelefonoContacto").html(
			data.telefonoContacto != null ? data.telefonoContacto : "&nbsp;"
		);
		$("#divCostoEnvio").html(
			data.costoEnvio != null ? data.costoEnvio : "&nbsp;"
		);
		$("#divEmail").html(
			data.email != null ? data.email : "&nbsp;"
		);
		$("#divPrecio").html(
			data.precio != null ? data.precio : "&nbsp;"
		);
		$("#divFormaPago").html(
			data.formaPago != null ? data.formaPago.descripcion : "&nbsp;"
		);
		$(".divDepartamento").html(
			data.zona != null ? data.zona.departamento.nombre : "&nbsp;"
		);
		$(".divBarrio").html(
			data.barrio != null ? data.barrio.nombre : "&nbsp"
		);
		$(".divZona").html(
			data.zona != null ? data.zona.nombre : "&nbsp"
		);
		$("#divTurno").html(
			data.turno != null ? data.turno.nombre : "&nbsp;"
		);
		$("#divFechaNacimiento").html(
			data.fechaNacimiento != null ? formatShortDate(data.fechaNacimiento) : "&nbsp;"
		);
		$("#divFechaEntrega").html(
			data.fechaEntrega != null ? formatShortDate(data.fechaEntrega) : "&nbsp;"
		);
		$("#divFechaActivarEn").html(
			data.fechaActivarEn != null ? formatShortDate(data.fechaActivarEn) : "&nbsp;"
		);
		$("#divObservaciones").html(
			data.observaciones != null ? data.observaciones : "&nbsp;"
		);
		
		$("#divIncluirChip").html(
			(data.incluirChip != null && data.incluirChip) ? "Si" : "&nbsp;"
		);
		
		/*
		$("#divVendedor").html(
			data.vendedor != null ? data.vendedor.nombre : "&nbsp;"
		);
		
		$("#divEmpresa").html(
			data.empresa != null ? data.empresa.nombre : "&nbsp;"
		);
		
		$("#divRol").html(
			data.rol != null ? data.rol.nombre : "&nbsp;"
		);
		
		$("#divUsuario").html(
			data.usuario != null ? data.usuario.nombre : "&nbsp;"
		);
		*/
		
		$("#imgNumeroTramiteBarCode").on('load', function() {
			$("#imgMidBarCode").on('load', function() {
				window.print();
			});
			$("#imgMidBarCode").attr("src", "/LogisticaWEB/Barcode?code=" + data.mid);
		});
		$("#imgNumeroTramiteBarCode").attr("src", "/LogisticaWEB/Barcode?code=" + data.numeroTramite);
	});
}