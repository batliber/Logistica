$(document).ready(init);

function init() {
	$("#divTitle").append("Contrase&ntilde;a");
	
	$("#inputActual").focus();
}

function inputSubmitOnClick(event, element) {
	var contrasenaActual = $("#inputContrasenaActual").val();
	var contrasenaNueva = $("#inputNuevaContrasena").val();
	var contrasenaConfirma = $("#inputConfirmaContrasena").val();
	
	if (contrasenaActual == null || contrasenaActual == "") {
		alert("Debe ingresar su contraseña actual.");
		
		$("#inputContrasenaActual").focus();
		
		return;
	}
	
	if (contrasenaNueva == null || contrasenaNueva == "") {
		alert("Debe ingresar una nueva contraseña.");
		
		$("#inputNuevaContrasena").focus();
		
		return;
	}
	
	if (contrasenaConfirma == null || contrasenaConfirma == "") {
		alert("Debe confirmar la nueva contraseña.");
		
		$("#inputConfirmaContrasena").focus();
		
		return;
	}
	
	if (contrasenaNueva != contrasenaConfirma) {
		alert("Las contraseñas no coinciden");
		
		$("#inputConfirmaContrasena").focus();
		
		return;
	}
	
	if (!contrasenaNueva.match(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9]{8,})$/)) {
		alert("La nueva contraseña debe ser de 8 o más caracteres y debe contener mayúsculas, minúsculas y números.");
		
		return;
	}
	
	$("#inputContrasenaActual").val(null);
	$("#inputNuevaContrasena").val(null);
	$("#inputConfirmaContrasena").val(null);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/UsuarioREST/cambiarContrasena",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify({
        	contrasenaActual: contrasenaActual,
        	contrasenaNueva: contrasenaNueva,
        	contrasenaConfirma: contrasenaConfirma
        })
    }).then(
    	function(data) {
	    	if (data != null) {
	    		alert(data.responseText);
	    	} else {
	    		alert("Operación exitosa.");
	    	}
	    	
	    	$("#inputContrasenaActual").focus();
    	}, function(data) {
    		if (data != null) {
	    		alert(data.responseText);
	    	}
    	}
    );
}