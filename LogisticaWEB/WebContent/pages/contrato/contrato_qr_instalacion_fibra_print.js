$(document).ready(init);

function init() {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/getById/" + id
	}).then(function(data) {
//		$(".divHeader").html(
//			//data.empresa != null ? data.empresa.nombre : "&nbsp;"
//			"&nbsp;"
//		);
		$(".divHeaderContrato").html(
			data.telefonoContacto != null ? data.telefonoContacto : "&nbsp;"
		);
		$(".divHeaderFecha").html(
			formatShortDate(new Date())
		);
		
		$("#divNumeroTramite").html(
			data.numeroTramite != null ? data.numeroTramite : "&nbsp;"
		);
		$("#divAntelNroTrn").html(
			data.antelNroTrn != null ? data.antelNroTrn : "&nbsp;"
		);
		$("#divNumeroSerie").html(
			data.numeroSerie != null ? data.numeroSerie : "&nbsp;"
		);
		$("#imgNumeroTramiteBarCode").on('load', function() {
			window.print();
		});
		$("#imgNumeroTramiteBarCode").attr(
			"src", 
			"/LogisticaWEB/Barcode?type=QR&code="
				// + "https://wse-prod.antel.com.uy/apti/instaladores/autogestion/autoSustitucion.xhtml?"
				// + "https://www.antel.com.uy/personas-y-hogares/novedades/guia-para-el-cambio-de-router-qr?" 
				+ "https://www.antel.com.uy/personas-y-hogares/novedades/plan-recambio-de-routers?"
				+ "token=" + data.antelNroTrn);
		
		$(".divSquare").html(
			data.antelNroTrn != null ? data.antelNroTrn : "&nbsp;"
		);
	});
}