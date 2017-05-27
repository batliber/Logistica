$(document).ready(function() {
	refinarForm();
	
	$("#divEliminarMarca").hide();
	
	if (id != null) {
		MarcaDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputMarcaNombre").val(data.nombre);
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarMarca").show();
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
	var marca = {
		nombre: $("#inputMarcaNombre").val()
	};
	
	if (marca.nombre == "") {
		alert("Debe ingresar un nombre.");
		
		return;
	}
	
	if (id != null) {
		marca.id = id;
		
		MarcaDWR.update(
			marca,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		MarcaDWR.add(
			marca,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarMarca").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará la Marca")) {
		var marca = {
			id: id
		};
		
		MarcaDWR.remove(
			marca,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}