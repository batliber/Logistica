$(document).ready(init)

function init() {
	refinarForm();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/EstadoActivacionREST/list"
    }).then(function(data) {
		fillSelect(
			"selectEstadoActivacion", 
			data,
			"id", 
			"nombre"
		);
    }).then(function(data) {
		if (id != null) {
			$.ajax({
		        url: "/LogisticaWEB/RESTFacade/ActivacionREST/getById/" + id
		    }).then(function(data) {
				$("#divEmpresa").text(data.empresa.nombre);
				$("#divEmpresa").attr("eid", data.empresa.id);
				
				$("#divMid").html(data.mid);
				if (data.fechaImportacion != null) {
					$("#divFechaImportacion").html(formatLongDate(data.fechaImportacion));
					$("#divFechaImportacion").attr("f", data.fechaImportacion);
				}
				
				if (data.activacionLote != null) {
					$("#divActivacionLote").html(data.activacionLote.numero);
					$("#divActivacionLote").attr("alid", data.activacionLote.id);
				}
				
				if (data.fechaActivacion != null) {
					$("#inputFechaActivacion").val(formatLongDate(data.fechaActivacion));
				}
				
				if (data.fechaVencimiento != null) {
					$("#divFechaVencimiento").html(
						data.fechaVencimiento != null ? 
							formatLongDate(data.fechaVencimiento) 
							: "&nbsp;"
					);
					$("#divFechaVencimiento").attr("f", data.fechaVencimiento);
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
			});
		}
    });
}

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
		activacion.fechaImportacion = parseInt(fechaImportacion);
	}
	
	var fechaActivacion = $("#inputFechaActivacion").val();
	if (fechaActivacion != null && fechaActivacion != "") {
		activacion.fechaActivacion = parseLongDate(fechaActivacion);
	}
	
	var fechaVencimiento = $("#divFechaVencimiento").attr("f");
	if (fechaVencimiento != null) {
		activacion.fechaVencimiento = parseInt(fechaVencimiento);
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
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ActivacionREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(activacion)
	    }).then(function(data) {
			alert("Operación exitosa");
		});
	}
}