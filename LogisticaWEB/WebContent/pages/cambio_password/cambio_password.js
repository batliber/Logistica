$(document).ready(init);

function init() {
	$("#inputUsuarioLogin").prop("disabled", true);
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				if (data != null) {
					$("#inputUsuarioLogin").val(data.login);
					
					$("#inputContrasenaActual").focus();
				}
			}, async: false
		}
	);
}

function inputConfirmaContrasenaOnChange(event, element) {
	inputGuardarOnClick();
}

function inputGuardarOnClick(event, element) {
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
	
	$("#inputContrasenaActual").val(null);
	$("#inputNuevaContrasena").val(null);
	$("#inputConfirmaContrasena").val(null);
	
	UsuarioDWR.cambiarContrasena(
		contrasenaActual,
		contrasenaNueva,
		contrasenaConfirma,
		{
			callback: function(data) {
				alert(data);
				
				$("#inputContrasenaActual").focus();
			}, async: false
		}
	);
}