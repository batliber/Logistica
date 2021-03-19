var links = {
	'divCardRecarga': '/LogisticaWEB/pages/mobile/mrecargas/mrecarga/mrecarga.jsp',
	'divCardSaldo': '/LogisticaWEB/pages/mobile/mrecargas/msaldo/msaldo.jsp',
	'divCardHistorico': '/LogisticaWEB/pages/mobile/mrecargas/mhistorial_recargas/mhistorial_recargas.jsp',
	'divCardChips': '/LogisticaWEB/pages/mobile/mrecargas/mchips/mchips.jsp',
	'divCardMisDatos': '/LogisticaWEB/pages/mobile/mrecargas/mmis_datos/mmis_datos.jsp',
	'divCardContacto': '/LogisticaWEB/pages/mobile/mrecargas/mcontacto/mcontacto.jsp'
};

$(document).ready(function() {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaPuntoVentaUsuarioREST/getPuntoVentaByContext"
	}).then(function(data) {
		if (data != null) {
			$("#divPuntoVenta").text(data.id + " - " + data.nombre);
		}
	});
	
	$(".divCard").click(
		function divCardOnClick(eventObject) {
			window.location = links[$(eventObject.target).attr("id")];
		}
	);
});