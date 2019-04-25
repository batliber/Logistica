$(document).ready(init);

function init() {
	TipoTasaInteresEfectivaAnualDWR.list(
		{
			callback: function(data) {
				var html = "<option value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html +=
						"<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectTasaInteresEfectivaAnualTipo").html(html);
			}, async: false
		}
	);
	
	refinarForm();
	
	$("#inputTasaInteresEfectivaAnualVigenciaHasta").prop("disabled", true);
	
	$("#divEliminarTasaInteresEfectivaAnual").hide();
	
	if (id != null) {
		TasaInteresEfectivaAnualDWR.getById(
			id,
			{
				callback: function(data) {
					if (data.tipoTasaInteresEfectivaAnual != null) {
						$("#selectTasaInteresEfectivaAnualTipo").val(data.tipoTasaInteresEfectivaAnual.id);
					}
					
					$("#inputTasaInteresEfectivaAnualCuotasDesde").val(data.cuotasDesde);
					if (data.cuotasHasta != null) {
						$("#inputTasaInteresEfectivaAnualCuotasHasta").val(data.cuotasHasta);
					} else {
						$("#inputTasaInteresEfectivaAnualCuotasHastaMax").prop("checked", true);
						$("#inputTasaInteresEfectivaAnualCuotasHasta").prop("disabled", true);
					}
					$("#inputTasaInteresEfectivaAnualMontoDesde").val(data.montoDesde);
					if (data.montoHasta != null) {
						$("#inputTasaInteresEfectivaAnualMontoHasta").val(data.montoHasta);
					} else {
						$("#inputTasaInteresEfectivaAnualMontoHastaMax").prop("checked", true);
						$("#inputTasaInteresEfectivaAnualMontoHasta").prop("disabled", true);
					}
					$("#inputTasaInteresEfectivaAnualValor").val(formatDecimal(data.valor, 2));
					$("#inputTasaInteresEfectivaAnualVigenciaHasta").val(formatLongDate(data.fechaVigenciaHasta));
					
					$("#selectTasaInteresEfectivaAnualTipo").prop("disabled", true);
					$("#inputTasaInteresEfectivaAnualCuotasDesde").prop("disabled", true);
					$("#inputTasaInteresEfectivaAnualCuotasHasta").prop("disabled", true);
					$("#inputTasaInteresEfectivaAnualCuotasHastaMax").prop("disabled", true);
					$("#inputTasaInteresEfectivaAnualMontoDesde").prop("disabled", true);
					$("#inputTasaInteresEfectivaAnualMontoHasta").prop("disabled", true);
					$("#inputTasaInteresEfectivaAnualMontoHastaMax").prop("disabled", true);
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

function inputTasaInteresEfectivaAnualCuotasHastaMaxOnChange(event, element) {
	if ($("#inputTasaInteresEfectivaAnualCuotasHastaMax").prop("checked")) {
		$("#inputTasaInteresEfectivaAnualCuotasHasta").val(null);
		$("#inputTasaInteresEfectivaAnualCuotasHasta").prop("disabled", true);
	} else {
		$("#inputTasaInteresEfectivaAnualCuotasHasta").prop("disabled", false);
		$("#inputTasaInteresEfectivaAnualCuotasHasta").focus();
	}
}

function inputTasaInteresEfectivaAnualMontoHastaMaxOnChange(event, element) {
	if ($("#inputTasaInteresEfectivaAnualMontoHastaMax").prop("checked")) {
		$("#inputTasaInteresEfectivaAnualMontoHasta").val(null);
		$("#inputTasaInteresEfectivaAnualMontoHasta").prop("disabled", true);
	} else {
		$("#inputTasaInteresEfectivaAnualMontoHasta").prop("disabled", false);
		$("#inputTasaInteresEfectivaAnualMontoHasta").focus();
	}
}

function inputGuardarOnClick(event) {
	var tasaInteresEfectivaAnual = {
		cuotasDesde: $("#inputTasaInteresEfectivaAnualCuotasDesde").val(),
		cuotasHasta: $("#inputTasaInteresEfectivaAnualCuotasHasta").val(),
		montoDesde: $("#inputTasaInteresEfectivaAnualMontoDesde").val(),
		montoHasta: $("#inputTasaInteresEfectivaAnualMontoHasta").val(),
		valor: $("#inputTasaInteresEfectivaAnualValor").val(),
		tipoTasaInteresEfectivaAnual: {
			id: $("#selectTasaInteresEfectivaAnualTipo").val()
		}
	};
	
	if (tasaInteresEfectivaAnual.tipoTasaInteresEfectivaAnual.id == 0) {
		alert("Debe seleccionar un tipo de tasa.");
		
		return;
	}
	
	if (tasaInteresEfectivaAnual.cuotasDesde == "") {
		alert("Debe ingresar cuotas desde.");
		
		return;
	}
	
	if (!$("#inputTasaInteresEfectivaAnualCuotasHastaMax").prop("checked")
		&& tasaInteresEfectivaAnual.cuotasHasta == "") {
		alert("Debe ingresar cuotas hasta.");
		
		return;
	}
	
	if (tasaInteresEfectivaAnual.montoDesde == "") {
		alert("Debe ingresar monto desde.");
		
		return;
	}
	
	if (!$("#inputTasaInteresEfectivaAnualMontoHastaMax").prop("checked")
		&& tasaInteresEfectivaAnual.montoHasta == "") {
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