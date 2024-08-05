var map = null;

$(document).ready(init);

function init() {
	$("#divTitle").append("Distribuci&oacute;n");
	
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
					if (data.resultadoEntregaDistribucion != null) {
						$("#selectResultadoEntregaDistribucion").val(data.resultadoEntregaDistribucion.id);
					}
					if (data.resultadoEntregaDistribucionObservaciones) {
						$("#textareaObservaciones").val(data.resultadoEntregaDistribucionObservaciones);
					}
				}
				
				$("#inputNumeroTramite").focus();
			});
		}
		
		initMap();
		
		navigator.geolocation.getCurrentPosition(
			center, 
			positionError
		);
	});
}

function center(data) {
	var crd = data.coords;
	
	$("#divPosicion").text(crd.latitude + ", " + crd.longitude + " - " + crd.accuracy);
	$("#inputLatitud").val(crd.latitude);
	$("#inputLongitud").val(crd.longitude);
	$("#inputPrecision").val(crd.accuracy);
	
	new google.maps.InfoWindow({
		map: map,
		position: new google.maps.LatLng(crd.latitude, crd.longitude),
		content: "Ubicación actual"
	});
}

function positionError(data) {
	$("#divPosicion").text("No se puede determinar la posición.");
}

function initMap() {
	var divMap = document.getElementById("divMap");
	map = new google.maps.Map(
		divMap, {
			center: {lat: 0, lng: 0},
			zoom: 8
		}
	);
}

function inputNumeroTramiteOnChange(event, element) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/getByNumeroTramite/" + $("#inputNumeroTramite").val()
	}).then(function(data) { 
		if (data != null) {
			$("#aMID").attr("href", "tel:0" + data.mid);
			$("#aMID").text(data.mid);
		} else {
			alert("No se encuentra el trámite solicitado.");
			inputLimpiarOnClick();
		}
	});
}

function inputAgregarArchivoOnClick(event, element) {
	var divArchivos = $(".divArchivos");
	var inputCount = $('input[type="file"]').length;
	
	divArchivos.append(
		"<div class='divFormLabel'>Nombre:</div><div class='divFormValue'>"
			+ "<form method='POST' action='/LogisticaWEB/Upload' id='formArchivo" + inputCount + "'>"
				+ "<input type='file' id='inputArchivo" + inputCount + "' name='inputArchivo" + inputCount + "'/>"
			+ "</form>"
		+ "</div>"
	);
}

function inputLimpiarOnClick(event, element) {
	$(".divArchivos").html("&nbsp");
	
	$("#aMID").html("&nbsp;");
	$("#aMID").prop("href", "#");
	
	$("#textareaObservaciones").val("");
	
	$("#selectResultadoEntregaDistribucion").val(0);
	
	$("#inputNumeroTramite").val(null);
	$("#inputNumeroTramite").focus();
	
	navigator.geolocation.getCurrentPosition(
		center, 
		positionError
	);
}

function inputSubmitOnClick(event, element) {
	var numeroTramite = $("#inputNumeroTramite").val();
	var resultadoEntregaDistribucion = $("#selectResultadoEntregaDistribucion").val();
	
	if (resultadoEntregaDistribucion == 0) {
		alert("Seleccione un resultado de entrega.");
		return false;
	}
	
	var xmlHTTPRequest = new XMLHttpRequest();
	xmlHTTPRequest.open(
		"POST",
		"/LogisticaWEB/Upload",
		false
	);
	
	// POST con la información estructurada.
	var formData = new FormData();
	formData.append("caller", "mobile");
	formData.append("inputNumeroTramite", numeroTramite);
	formData.append("selectResultadoEntregaDistribucion", resultadoEntregaDistribucion);
	formData.append("textareaObservaciones", $("#textareaObservaciones").val());
	formData.append("inputLatitud", $("#inputLatitud").val());
	formData.append("inputLongitud", $("#inputLongitud").val());
	formData.append("inputPrecision", $("#inputPrecision").val());

	xmlHTTPRequest.send(formData);
	
	if (xmlHTTPRequest.status != 200) {
		alert(xmlHTTPRequest.responseText);
		
		return;
	}
	
	// POST con los archivos.
	
	var inputs = $('input[type="file"]');
	for (var i=0; i<inputs.length; i++) {
		xmlHTTPRequest = new XMLHttpRequest();
		xmlHTTPRequest.open(
			"POST",
			"/LogisticaWEB/Upload",
			false
		);
		
		formData = new FormData(document.getElementById("formArchivo" + i));
		formData.append("caller", "mobile");
		formData.append("inputNumeroTramite", numeroTramite);
		formData.append("selectResultadoEntregaDistribucion", resultadoEntregaDistribucion);
		formData.append("textareaObservaciones", $("#textareaObservaciones").val());
		formData.append("inputLatitud", $("#inputLatitud").val());
		formData.append("inputLongitud", $("#inputLongitud").val());
		formData.append("inputPrecision", $("#inputPrecision").val());

		xmlHTTPRequest.send(formData);
		
		if (xmlHTTPRequest.status != 200) {
			alert(xmlHTTPRequest.responseText);
			return;
		}
	}
	
	if (xmlHTTPRequest.status == 200) {
		alert(JSON.parse(xmlHTTPRequest.responseText).message);
		
		inputLimpiarOnClick(event, element);
	} else {
		alert(xmlHTTPRequest.responseText);
	}
}