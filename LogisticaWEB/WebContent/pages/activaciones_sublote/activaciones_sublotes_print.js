var usuario = null;

$(document).ready(function() {
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
				$("#divEmpresa").text(data.empresa.nombre);
				$("#divNumeroLote").text(data.numero);
				$("#divFechaVencimiento").text(formatShortDate(data.activaciones[0].fechaVencimiento));
				
				$(".divNumeroLoteBarCode").css("background-image", "url(/LogisticaWEB/Barcode?code=" + data.numero + ")");
				
				$("body").css("background-color", "white");
				$(".divTagSheet").css("border", "none");
				
				window.print();
			}, async: false
		}
	);
});