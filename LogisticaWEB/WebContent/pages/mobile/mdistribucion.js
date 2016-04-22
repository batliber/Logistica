var map = null;

$(document).ready(function() {
	$("#divTitle").append("Distribuci&oacute;n");
	
	ResultadoEntregaDistribucionDWR.list(
		{
			callback: function(data) {
				$("#selectResultadoEntregaDistribucion > option").remove();
				
				html =
					"<option value=0>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html +=
						"<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectResultadoEntregaDistribucion").append(html);
			}, async: false
		}
	);
	
	if (numeroTramite != null) {
		$("#inputNumeroTramite").val(numeroTramite);
		
		inputNumeroTramiteOnChange();
	} else if (id != null) {
		ContratoDWR.getById(
			id,
			{
				callback: function(data) {
					if (data != null) {
						$("#inputNumeroTramite").val(data.numeroTramite);
						$("#aMID").attr("href", "tel:0" + data.mid);
						$("#aMID").text(data.mid);
						if (data.resultadoEntregaDistribucion != null) {
							$("#selectResultadoEntregaDistribucion").val(data.resultadoEntregaDistribucion.id);
						}
						if (data.resultadoEntregaDistribucionObservaciones) {
							$("#textareaObservaciones").text(data.resultadoEntregaDistribucionObservaciones);
						}
					}
				}, async: false
			}
		);
		
		$("#inputNumeroTramite").focus();
	}
	
	navigator.geolocation.getCurrentPosition(
		center, 
		positionError
	);
});

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
	ContratoDWR.getByNumeroTramite(
		$("#inputNumeroTramite").val(),
		{
			callback: function(data) {
				if (data != null) {
					$("#aMID").attr("href", "tel:0" + data.mid);
					$("#aMID").text(data.mid);
				}
			}, async: false
		}
	);
}

function inputLimpiarOnClick(event, element) {
	$("#formResultadoEntregaDistribucion")[0].reset();
	
	$("#aMID").html("&nbsp;");
	$("#aMID").prop("href", "#");
	
	$("#textareaObservaciones").text("");
	
	$("#inputNumeroTramite").focus();
	
	center();
}

function inputSubmitOnClick(event, element) {
	var xmlHTTPRequest = new XMLHttpRequest();
	xmlHTTPRequest.open(
		"POST",
		"/LogisticaWEB/Upload",
		false
	);
	
	var formData = new FormData(document.getElementById("formResultadoEntregaDistribucion"));

	xmlHTTPRequest.send(formData);
	
	if (xmlHTTPRequest.status == 200) {
		alert(JSON.parse(xmlHTTPRequest.responseText).message);
		
		inputLimpiarOnClick(event, element);
	} else {
		alert(xmlHTTPRequest.responseText);
	}
}