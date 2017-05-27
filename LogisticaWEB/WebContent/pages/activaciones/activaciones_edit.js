$(document).ready(function() {
	refinarForm();
	
	EstadoActivacionDWR.list(
		{
			callback: function(data) {
				$("#selectEstadoActivacion option").remove();
				
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectEstadoActivacion").append(html);
			}, async: false
		}
	);
	
	if (id != null) {
		ActivacionDWR.getById(
			id,
			{
				callback: function(data) {
					$("#divEmpresa").text(data.empresa.nombre);
					$("#divEmpresa").attr("eid", data.empresa.id);
					
					$("#divMid").html(data.mid);
					if (data.fechaImportacion != null) {
						$("#divFechaImportacion").html(formatLongDate(data.fechaImportacion));
						$("#divFechaImportacion").attr("f", formatRawDate(data.fechaImportacion));
					}
					
					if (data.activacionLote != null) {
						$("#divActivacionLote").html(data.activacionLote.numero);
						$("#divActivacionLote").attr("alid", data.activacionLote.id);
					}
					
					if (data.fechaActivacion != null) {
						$("#inputFechaActivacion").val(formatLongDate(data.fechaActivacion));
					}
					
					if (data.fechaVencimiento != null) {
						$("#divFechaVencimiento").html(data.fechaVencimiento != null ? formatLongDate(data.fechaVencimiento) : "&nbsp;");
						$("#divFechaVencimiento").attr("f", formatRawDate(data.fechaVencimiento));
					}
					
					$("#divChip").html(data.chip);
					
					if (data.tipoActivacion != null) {
						$("#divTipoActivacion").html(data.tipoActivacion.descripcion);
						$("#divTipoActivacion").attr("taid", data.tipoActivacion.id);
					}
					
					if (data.estadoActivacion != null) {
						$("#selectEstadoActivacion").val(data.estadoActivacion.id);
					}
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarModelo").show();
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
	var activacion = {
		mid: $("#divMid").html(),
		chip: $("#divChip").html(),
		empresa: {
			id: $("#divEmpresa").attr("eid")
		},
		estadoActivacion: {
			id: $("#selectEstadoActivacion").val()
		}
	};
	
	var fechaImportacion = $("#divFechaImportacion").attr("f");
	if (fechaImportacion != null) {
		activacion.fechaImportacion = new Date(parseInt(fechaImportacion));
	}
	
	var fechaActivacion = $("#inputFechaActivacion").val();
	if (fechaActivacion != null && fechaActivacion != "") {
		activacion.fechaActivacion = parseLongDate(fechaActivacion);
	}
	
	var fechaVencimiento = $("#divFechaVencimiento").attr("f");
	if (fechaVencimiento != null) {
		activacion.fechaVencimiento = new Date(parseInt(fechaVencimiento));
	}
	
	var tipoActivacionId = $("#divTipoActivacion").attr("taid");
	if (tipoActivacionId != null && tipoActivacionId != "") {
		activacion.tipoActivacion = {
			id: tipoActivacionId
		};
	}
	
	var activacionLoteId = $("#divActivacionLote").attr("alid");
	if (activacionLoteId != null && activacionLoteId != "") {
		activacion.activacionLote = {
			id: activacionLoteId
		}
	}
	
	if (id != null) {
		activacion.id = id;
		
		ActivacionDWR.update(
			activacion,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}