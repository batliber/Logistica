$(document).ready(init);

function init() {
	$("#inputUsuario").focus();
}

function inputAccederOnClick(event, element) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/login",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
        	"l": $("#inputUsuario").val(),
        	"p": $("#inputContrasena").val()
        })
	}).then(
		function(data) {
			if (requestedPage != null && requestedPage != "") {
				window.location = requestedPage;
			} else {
				window.location = "/LogisticaWEB/pages/mobile/mmain.jsp";
			}
		}, 
		function(data) {
			$("#divError").text(data.responseText);
		}
	);
}

function inputUsuarioOnKeyDown(event, element) {
	if (event.keyCode == 13) {
		inputAccederOnClick(event, element);
	}
}

function inputContrasenaOnKeyDown(event, element) {
	if (event.keyCode == 13) {
		inputAccederOnClick(event, element);
	}
}