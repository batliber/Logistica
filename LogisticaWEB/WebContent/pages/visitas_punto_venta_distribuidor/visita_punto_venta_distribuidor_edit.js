$(document).ready(init);

function init() {
	UsuarioRolEmpresaDWR.listDistribuidoresChipsByContext(
		{
			callback: fillSelectDistribuidorChip, async: false
		}
	);
	
	EstadoVisitaPuntoVentaDistribuidorDWR.list(
		{
			callback: fillSelectEstadoVisitaPuntoVentaDistribuidor, async: false
		}
	);
	
	PuntoVentaDWR.listMinimal(
		{
			callback: fillSelectPuntoVenta, async: false
		}
	);
	
	refinarForm();
	
	$("#divEliminarVisitaPuntoVentaDistribuidor").hide();
	
	if (id != null) {
		VisitaPuntoVentaDistribuidorDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputFechaAsignacion").val(formatShortDate(data.fechaAsignacion));
					$("#inputFechaVisita").val(formatShortDate(data.fechaVisita));
					$("#textareaObservaciones").val(data.observaciones);
					
					if (data.puntoVenta != null) {
						$("#selectPuntoVenta").val(data.puntoVenta.id);
					}
					
					if (data.estadoVisitaPuntoVentaDistribuidor != null) {
						$("#selectEstadoVisitaPuntoVentaDistribuidor").val(data.estadoVisitaPuntoVentaDistribuidor.id);
					}
					
					if (data.distribuidor != null) {
						$("#selectDistribuidor").val(data.distribuidor.id);
					}
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarVisitaPuntoVentaDistribuidor").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
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

function fillSelectDistribuidorChip(data) {
	$("#selectDistribuidor > option").remove();
	
	var html =
		"<option id='0' value='0'>Seleccione...</option>";
	
	for (var i=0; i<data.length; i++) {
		html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
	}
	
	$("#selectDistribuidor").append(html);
}

function fillSelectEstadoVisitaPuntoVentaDistribuidor(data) {
	$("#selectEstadoVisitaPuntoVentaDistribuidor > option").remove();
	
	html =
		"<option value='0'>Seleccione...</option>";
	
	for (var i=0; i<data.length; i++) {
		html +=
			"<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
	}
	
	$("#selectEstadoVisitaPuntoVentaDistribuidor").append(html);
}

function fillSelectPuntoVenta(data) {
	$("#selectPuntoVenta > option").remove();
	
	html =
		"<option value=0>Seleccione...</option>";
	
	for (var i=0; i<data.length; i++) {
		html +=
			"<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
	}
	
	$("#selectPuntoVenta").append(html);
}

function inputGuardarOnClick(event) {
	var visitaPuntoVentaDistribuidor = {
		fechaAsignacion: parseShortDate($("#inputFechaAsignacion").val()),
		fechaVisita: parseShortDate($("#inputFechaVisita").val()),
		observaciones: $("#textareaObservaciones").val(),
		distribuidor: {
			id: $("#selectDistribuidor").val()
		},
		estadoVisitaPuntoVentaDistribuidor: {
			id: $("#selectEstadoVisitaPuntoVentaDistribuidor").val()
		},
		puntoVenta: {
			id: $("#selectPuntoVenta").val()
		}
	};
	
	if (visitaPuntoVentaDistribuidor.distribuidor.id == "0") {
		alert("Debe seleccionar un distribuidor.");
		
		return;
	}
	
	if (visitaPuntoVentaDistribuidor.estadoVisitaPuntoVentaDistribuidor.id == "0") {
		alert("Debe seleccionar un estado.")
		
		return;
	}
	
	if (visitaPuntoVentaDistribuidor.puntoVenta.id == "0") {
		alert("Debe seleccionar un punto de venta.");
		
		return;
	}
	
	if (id != null) {
		visitaPuntoVentaDistribuidor.id = id;
		
		VisitaPuntoVentaDistribuidorDWR.update(
			visitaPuntoVentaDistribuidor,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		VisitaPuntoVentaDistribuidorDWR.add(
			visitaPuntoVentaDistribuidor,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarVisitaPuntoVentaDistribuidor").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el registro.")) {
		var visitaPuntoVentaDistribuidor = {
			id: id
		};
		
		VisitaPuntoVentaDistribuidorDWR.remove(
			visitaPuntoVentaDistribuidor,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}