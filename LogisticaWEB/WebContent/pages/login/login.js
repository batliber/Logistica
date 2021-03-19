$(document).ready(init);

function init() {
	if (window.parent != window) {
		window.top.location = window.location;
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ConfigurationREST/getByClave/datosSistema.version"
	    }).then(function(data) {
	    	$(".divVersionBarText").html("Versión " + data.valor);
	    });
		
		$("#inputUsuario").focus();
	}
}

function inputAccederOnClick(event, element) {
	$("#divError").text("");
	
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
	    		if (data != null && data.menus != null && data.menus.length > 0) {
					var i = 0;
					for (i=0; i<data.menus.length; i++) {
						if (data.menus[i].padre != null) {
							break;
						}
					}
					window.location = data.menus[i].url;
				} else {
					window.location = "/LogisticaWEB";
				}
    		}
		}, function(data) {
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