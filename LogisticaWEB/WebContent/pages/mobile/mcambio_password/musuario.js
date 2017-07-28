$(document).ready(function() {
	$("#divTitle").append("Contrase&ntilde;a");
	
	$("#inputActual").focus();
});

function inputSubmitOnClick(event, element) {
	UsuarioDWR.cambiarContrasena(
		$("#inputActual").val(),
		$("#inputNueva").val(),
		$("#inputConfirma").val(),
		{
			callback: function(data) {
				alert(data);
			}, async: false
		}
	);
}