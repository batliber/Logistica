$(document).ready(init);

function init() {
	if (id != null) {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ContratoREST/getById/" + id
	    }).then(function(data) {
			$(".divCabezalDatosClienteNombre").html(data.nombre);
			$(".divCabezalDatosClienteDireccion").html(data.direccionEntregaCalle);
			$(".divCabezalDatosFacturacionFecha").html(formatShortDate(data.fechaVenta));
			
			$(".divLineasCantidad").append(
				"<div class='divLineaCantidad'>" + 1 + "</div>"
			);
			$(".divLineasDescripcion").append(
				"<div class='divLineaDescripcion'>"
					+ "SMART TV MARCA " + data.marca.nombre 
					+ " MODELO " + data.modelo.descripcion 
					+ " - SERIE NUMERO " + (data.numeroSerie != null ? data.numeroSerie : "")
				+ "</div>" 
				+ "<br/>" 
				+ "<div class='divLineaDescripcion'>"
					+ "Remito de entrega seg&uacute;n factura: e-Ticket VCA " + data.numeroFactura
				+ "</div>"
				+ "<div class='divLineaDescripcion'>"
					+ "Monto total = " + data.antelImporte + " y forma de pago (" + data.cuotas + " cuotas)"
				+ "</div>"
			);
			$(".divLineasPrecioUnitario").append(
				"<div class='divLineaPrecioUnitario'>" + data.precio + "</div>"
			);
			$(".divLineasImporte").append(
				"<div class='divLineaImporte'>" + data.precio + "</div>"
			);
			
			$(".divRemito:odd").css("margin-top", "7mm");
		});
	}
}

function inputImprimirOnClick() {
//	$("input[type='text']").css("background-color", "white");
//	$("textarea").css("background-color", "white");
	$(".divA4Sheet").css("border", "none");
	$(".divPrintingButtonBar").hide();
	
	window.print();
}

function inputCancelarOnClick() {
	window.close();
}