$(document).ready(function() {
	refinarForm();
	
	$("#divEliminarEmpresa").hide();
	
	if (id != null) {
		EmpresaDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputEmpresaNombre").val(data.nombre);
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarEmpresa").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
					}
				}, async: false
			}
		);
	}
});

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function inputGuardarOnClick(event) {
	var empresa = {
		nombre: $("#inputEmpresaNombre").val()
	};
	
	if (id != null) {
		empresa.id = id;
		
		EmpresaDWR.update(
			empresa,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		EmpresaDWR.add(
			empresa,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarEmpresa").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará la Empresa")) {
		var empresa = {
			id: id
		};
		
		EmpresaDWR.remove(
			empresa,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}