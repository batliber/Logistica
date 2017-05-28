$(document).ready(function() {
	refinarForm();
	
	$("#divEliminarEmpresaService").hide();
	
	if (id != null) {
		EmpresaServiceDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputEmpresaServiceNombre").val(data.nombre);
					$("#inputEmpresaServiceDireccion").val(data.direccion);
					$("#inputEmpresaServiceTelefono").val(data.telefono);
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarEmpresaService").show();
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
	var empresaService = {
		nombre: $("#inputEmpresaServiceNombre").val(),
		direccion: $("#inputEmpresaServiceDireccion").val(),
		telefono: $("#inputEmpresaServiceTelefono").val(),
	};
	
	if (id != null) {
		empresaService.id = id;
		
		EmpresaServiceDWR.update(
			empresaService,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		EmpresaServiceDWR.add(
			empresaService,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarEmpresaService").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Service")) {
		var empresaService = {
			id: id
		};
		
		EmpresaServiceDWR.remove(
			empresaService,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}