$(document).ready(init);

function init() {
	
}

function inputControlarDocumentoOnClick(event, element) {
	$.get(
		"https://tosteaun.no-ip.biz:8443/LogisticaWEB/REST/RiesgoCrediticioREST/getInformacionRiesgoCrediticio/"
			+ $("#inputDocumento").val(), 
		function(data) {
			alert(JSON.stringify(data));
		}
	);
}