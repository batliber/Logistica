$(document).ready(function() {
	$("#selectEmpresa").append("<option value='0'>Seleccione...</option>");
	
	EmpresaDWR.list(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectEmpresa").append(html);
			}, async: false
		}
	);
});

function inputAceptarOnClick() {
	
}

function inputCancelarOnClick() {
	
}