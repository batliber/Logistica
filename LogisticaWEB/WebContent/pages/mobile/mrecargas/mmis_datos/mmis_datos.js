var map = null;
var marker = null;
var markers = [];

$(document).ready(init);

function init() {
	$("#divTitle").append("Mis datos");
	
	$("input").attr("disabled", "true");
	
	initMap();
}

function initMap() {
	var divMap = document.getElementById("divMap");
	
	var initialCenter = {lat: -34.9017, lng: -56.1634};
	
	map = new google.maps.Map(
		divMap, {
			center: initialCenter,
			zoom: 15
		}
	);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaPuntoVentaUsuarioREST/getPuntoVentaByContext"
	}).then(function(data) {
		if (data != null) {
			$("#inputId").val(data.id);
			$("#inputNombre").val(data.nombre);
			$("#inputTelefono").val(data.telefono);
			$("#inputDocumento").val(data.documento);
			$("#inputContacto").val(data.contacto);
			$("#inputDepartamento").val(
				data.departamento != null ? data.departamento.nombre : null
			);
			$("#inputBarrio").val(
				data.barrio != null ? data.barrio.nombre : null
			);
			$("#inputDireccion").val(data.direccion);
			
			fillMap([data]);
		}
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

function fillMap(data) {
	clearMarkers();
	
	var markerBounds = new google.maps.LatLngBounds();
	
	for (var i=0; i<data.length; i++) {
		if (data[i].latitud != null && data[i].longitud != null) {
			var latlng = {
				lat: data[i].latitud, lng: data[i].longitud
			};
			
			var marker = new google.maps.Marker({
				position: latlng,
				map: map,
				title: data[i].nombre
			});
			
			markers.push(marker);
			
			markerBounds.extend(latlng);
		}
	}
	
	if (data.length > 1) {
		map.fitBounds(markerBounds);
	} else if (data.length == 1 && data[0].precision != null) {
		map.setCenter(markerBounds.getCenter());
		map.setZoom(14);
	} else if (data.length == 1) {
		map.setCenter(markerBounds.getCenter());
		map.setZoom(14);
	}
}

function clearMarkers() {
	for (var j=0; j<markers.length; j++) {
		markers[j].setMap(null);
	}
	markers = [];
}