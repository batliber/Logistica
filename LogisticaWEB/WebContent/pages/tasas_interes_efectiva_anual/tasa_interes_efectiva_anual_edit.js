$(document).ready(init);

function init() {
	refinarForm();
	
	$("#inputTasaInteresEfectivaAnualVigenciaHasta").prop("disabled", true);
	
	$("#divEliminarTasaInteresEfectivaAnual").hide();
	
	if (id != null) {
		TasaInteresEfectivaAnualDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputTasaInteresEfectivaAnualCuotasDesde").val(data.cuotasDesde);
					$("#inputTasaInteresEfectivaAnualCuotasHasta").val(data.cuotasHasta);
					$("#inputTasaInteresEfectivaAnualMontoDesde").val(data.montoDesde);
					$("#inputTasaInteresEfectivaAnualMontoHasta").val(data.montoHasta);
					$("#inputTasaInteresEfectivaAnualValor").val(formatDecimal(data.valor, 2));
					$("#inputTasaInteresEfectivaAnualVigenciaHasta").val(formatLongDate(data.fechaVigenciaHasta));
					
					$("#inputTasaInteresEfectivaAnualCuotasDesde").prop("disabled", true);
					$("#inputTasaInteresEfectivaAnualCuotasHasta").prop("disabled", true);
					$("#inputTasaInteresEfectivaAnualMontoDesde").prop("disabled", true);
					$("#inputTasaInteresEfectivaAnualMontoHasta").prop("disabled", true);
					$("#inputTasaInteresEfectivaAnualValor").prop("disabled", true);
					
					if (mode == __FORM_MODE_ADMIN) {
						if (data.fechaVigenciaHasta == null) {
							$("#divEliminarTasaInteresEfectivaAnual").show();
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
	var tasaInteresEfectivaAnual = {
		cuotasDesde: $("#inputTasaInteresEfectivaAnualCuotasDesde").val(),
		cuotasHasta: $("#inputTasaInteresEfectivaAnualCuotasHasta").val(),
		montoDesde: $("#inputTasaInteresEfectivaAnualMontoDesde").val(),
		montoHasta: $("#inputTasaInteresEfectivaAnualMontoHasta").val(),
		valor: $("#inputTasaInteresEfectivaAnualValor").val()
	};
	
	if (tasaInteresEfectivaAnual.cuotasDesde == "") {
		alert("Debe ingresar cuotas desde.");
		
		return;
	}
	
	if (tasaInteresEfectivaAnual.cuotasHasta == "") {
		alert("Debe ingresar cuotas hasta.");
		
		return;
	}
	
	if (tasaInteresEfectivaAnual.montoDesde == "") {
		alert("Debe ingresar monto desde.");
		
		return;
	}
	
	if (tasaInteresEfectivaAnual.montoHasta == "") {
		alert("Debe ingresar monto hasta.");
		
		return;
	}
	
	if (tasaInteresEfectivaAnual.valor == "") {
		alert("Debe ingresar un valor.");
		
		return;
	}
	
	if (id != null) {
		tasaInteresEfectivaAnual.id = id;
	} else {
		TasaInteresEfectivaAnualDWR.add(
			tasaInteresEfectivaAnual,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarTasaInteresEfectivaAnual").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el rango")) {
		var tasaInteresEfectivaAnual = {
			id: id
		};
		
		TasaInteresEfectivaAnualDWR.remove(
			tasaInteresEfectivaAnual,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}