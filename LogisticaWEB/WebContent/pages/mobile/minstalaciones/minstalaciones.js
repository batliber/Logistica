var __RESULTADO_ENTREGA_DISTRIBUCION_FIRMA = 1;
var __RESULTADO_ENTREGA_DISTRIBUCION_EXTRAVIADO_DANADO = 8;

var grid = null;

$(document).ready(init);

function init() {
	$("#divTitle").append("Fibra &oacute;ptica");
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ResultadoEntregaDistribucionREST/list"
	}).then(function(data) {
		fillSelect(
			"selectResultadoEntregaDistribucion", 
			data,
			"id", 
			"descripcion"
		);
	}).then(function(data) {
		if (numeroTramite != null) {
			$("#inputNumeroTramite").val(numeroTramite);
			
			inputNumeroTramiteOnChange();
		} else if (id != null) {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/ContratoREST/getById/" + id
			}).then(function(data) {
				if (data != null) {
					$("#inputNumeroTramite").val(data.numeroTramite);
					$("#aMID").attr("href", "tel:0" + data.mid);
					$("#aMID").text(data.mid);
					$("#inputDocumento").val(data.documento);
					$("#inputNombre").val(data.nombre);
					if (data.resultadoEntregaDistribucion != null) {
						$("#selectResultadoEntregaDistribucion").val(data.resultadoEntregaDistribucion.id);
					}
					if (data.resultadoEntregaDistribucionObservaciones != null) {
						$("#textareaObservaciones").val(data.resultadoEntregaDistribucionObservaciones);
					}
				} else {
					alert("No se encuentra el tr\u00e1mite solicitado.");
					inputLimpiarOnClick();
				}
					
				$("#inputNumeroTramite").focus();
			});
		}
	});
}

function inputNumeroTramiteOnChange(event, element) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/getByNumeroTramite/" + $("#inputNumeroTramite").val()
	}).then(function(data) { 
		if (data != null) {
			if (data.fechaPickUp != null) {
				alert("Ya se realiz\u00f3 Pick-up para el tr\u00e1mite ingresado.");
				inputLimpiarOnClick();
			} else {
				id = data.id;
				$("#aMID").attr("href", "tel:0" + data.mid);
				$("#aMID").text(data.mid);
				
				$("#inputDocumento").val(data.documento);
				$("#inputNombre").val(data.nombre);
				if (data.resultadoEntregaDistribucion != null) {
					$("#selectResultadoEntregaDistribucion").val(data.resultadoEntregaDistribucion.id);
				}
				if (data.resultadoEntregaDistribucionObservaciones != null) {
					$("#textareaObservaciones").val(data.resultadoEntregaDistribucionObservaciones);
				}
			}
		} else {
			alert("No se encuentra el tr\u00e1mite solicitado.");
			inputLimpiarOnClick();
		}
	});
}

function inputLimpiarOnClick(event, element) {
	$("#aMID").html("&nbsp;");
	$("#aMID").prop("href", "#");
	
	$("#inputDocumento").val(null);
	$("#inputNombre").val(null);
	$("#textareaObservaciones").val("");
	
	$("#selectResultadoEntregaDistribucion").val(0);
	
	$("#inputNumeroTramite").val(null);
	$("#inputNumeroTramite").focus();
}

function inputSubmitOnClick(event, element) {
	var numeroTramite = $("#inputNumeroTramite").val();
	var resultadoEntregaDistribucion = $("#selectResultadoEntregaDistribucion").val();
	var documento = $("#inputDocumento").val();
	var nombre = $("#inputNombre").val();
	var observaciones = $("#textareaObservaciones").val();
	
	if (numeroTramite.trim() == "") {
		alert("Debe ingresar un número de trámite.");
		return false;
	}
	
	if (resultadoEntregaDistribucion == 0) {
		alert("Seleccione un resultado de entrega.");
		return false;
	}
	
	if (documento.trim() == "") {
		alert("Debe ingresar un documento.");
		return false;
	}
	
	if (nombre.trim() == "") {
		alert("Debe ingresar un nombre.");
		return false;
	}
	
	if ((resultadoEntregaDistribucion == __RESULTADO_ENTREGA_DISTRIBUCION_FIRMA)
		&& !confirm("Se confirmar\u00e1 la entrega del tr\u00e1mite: " + numeroTramite.trim())) {
		return false;
	} else if ((resultadoEntregaDistribucion == __RESULTADO_ENTREGA_DISTRIBUCION_EXTRAVIADO_DANADO)
		&& !confirm("Se marcar\u00e1 el tr\u00e1mite como EXTRAVIADO/DA\u00d1ADO. \u00bfEst\u00e1 seguro?")) {
		return false;
	}
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/instalar",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
			contratoId: id,
			resultadoEntregaDistribucionId: resultadoEntregaDistribucion,
			documento: documento.trim(),
			nombre: nombre.trim(),
			observaciones: observaciones.trim()
		})
	}).then(function(data) {
		alert("Operación exitosa.");
		
		inputLimpiarOnClick();
	});
}