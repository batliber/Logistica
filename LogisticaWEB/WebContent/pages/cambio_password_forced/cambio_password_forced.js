$(document).ready(init);

function init() {
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				if (data != null) {
					$("#divUsuario").html(data.login);
					
					$("#inputActual").focus();
				}
			}, async: false
		}
	);
}

function inputConfirmaContrasenaOnChange(event, element) {
	inputGuardarOnClick();
}

function inputGuardarOnClick(event, element) {
	var contrasenaActual = $("#inputActual").val();
	var contrasenaNueva = $("#inputNueva").val();
	var contrasenaConfirma = $("#inputConfirma").val();
	
	if (contrasenaActual == null || contrasenaActual == "") {
		alert("Debe ingresar su contraseña actual.");
		
		$("#inputActual").focus();
		
		return;
	}
	
	if (contrasenaNueva == null || contrasenaNueva == "") {
		alert("Debe ingresar una nueva contraseña.");
		
		$("#inputNueva").focus();
		
		return;
	}
	
	if (contrasenaConfirma == null || contrasenaConfirma == "") {
		alert("Debe confirmar la nueva contraseña.");
		
		$("#inputConfirma").focus();
		
		return;
	}
	
	if (contrasenaNueva == contrasenaActual) {
		alert("La nueva contraseña coincide con la actual.");
		
		$("#inputConfirma").focus();
		
		return;
	}
	
	if (contrasenaNueva != contrasenaConfirma) {
		alert("Las contraseñas no coinciden");
		
		$("#inputConfirma").focus();
		
		return;
	}
	
	if (!contrasenaNueva.match(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9]{8,})$/)) {
		alert("La nueva contraseña debe ser de 8 o más caracteres y debe contener mayúsculas, minúsculas y números.");
		
		return;
	}
	
	$("#inputActual").val(null);
	$("#inputNueva").val(null);
	$("#inputConfirma").val(null);
	
	UsuarioDWR.cambiarContrasena(
		contrasenaActual,
		contrasenaNueva,
		contrasenaConfirma,
		{
			callback: function(data) {
				alert(data);
				
				location.href = '/LogisticaWEB/'
			}, async: false
		}
	);
}