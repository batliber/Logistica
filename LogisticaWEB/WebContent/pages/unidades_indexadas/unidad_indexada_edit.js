$(document).ready(init);

function init() {
	refinarForm();
	
	$("#inputUnidadIndexadaVigenciaHasta").prop("disabled", true);
	
	$("#divEliminarUnidadIndexada").hide();
	
	if (id != null) {
		UnidadIndexadaDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputUnidadIndexadaVigenciaHasta").val(formatLongDate(data.fechaVigenciaHasta));
					$("#inputUnidadIndexadaValor").val(formatDecimal(data.valor, 4));
					
					$("#inputUnidadIndexadaValor").prop("disabled", true);
					
					if (mode == __FORM_MODE_ADMIN) {
						if (data.fechaVigenciaHasta == null) {
							$("#divEliminarUnidadIndexada").show();
							$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						} else {
							$("#inputGuardar").prop("disabled", true);
						}
					}
				}, async: false
			}
		);
	}
}

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function inputGuardarOnClick(event) {
	var unidadIndexada = {
		valor: $("#inputUnidadIndexadaValor").val()
	};
	
	if (unidadIndexada.valor == "") {
		alert("Debe ingresar un valor.");
		
		return;
	}
	
	if (id != null) {
		unidadIndexada.id = id;
	} else {
		UnidadIndexadaDWR.add(
			unidadIndexada,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarUnidadIndexada").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará la cotización")) {
		var unidadIndexada = {
			id: id
		};
		
		UnidadIndexadaDWR.remove(
			unidadIndexada,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}