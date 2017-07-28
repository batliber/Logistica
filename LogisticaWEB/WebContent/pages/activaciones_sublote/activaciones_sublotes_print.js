var usuario = null;

$(document).ready(init);

function init() {
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				usuario = data;
			}, async: false
		}
	);
	
	ActivacionSubloteDWR.getById(
		id,
		{
			callback: function(data) {
				$("#divEmpresa").html(data.empresa.nombre);
				$("#divNumeroLote").html(data.numero);
				$("#divFechaVencimiento").html(
					(data.activaciones != null && data.activaciones.length > 0 && data.activaciones[0].fechaVencimiento != null) ?
						formatShortDate(data.activaciones[0].fechaVencimiento) :
						"&nbsp;"
				);
				
				$(".divNumeroLoteBarCode").css(
					"background-image", "url(/LogisticaWEB/Barcode?code=" + data.numero + ")"
				);
				
				$("body").css("background-color", "white");
				$(".divTagSheet").css("border", "none");
				
				window.print();
			}, async: false
		}
	);
}