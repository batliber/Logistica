var map = null;
var marker = null;

$(document).ready(init);

function init() {
	$("#divTitle").append("Punto de venta");
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/DepartamentoREST/list"
	}).then(function(data) {
		fillSelect(
			"selectDepartamento",
			data,
			"id", 
			"nombre"
		);
	});
	
	fillSelect(
		"selectBarrio",
		[],
		"id",
		"nombre"
	);
	
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
	
	marker = new google.maps.Marker({
		position: initialCenter,
		map: map
	});
	
	map.addListener('center_changed', function() {
		var coords = map.getCenter();
		
		marker.setPosition(coords);
	});
	
	map.addListener('zoom_changed', function() {
		var coords = map.getCenter();
		
		marker.setPosition(coords);
	});
	
	var input = document.getElementById("inputBusqueda");
	var searchBox = new google.maps.places.SearchBox(input);
	
	map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
	
	map.addListener('bounds_changed', function() {
		searchBox.setBounds(map.getBounds());
	});
	
	searchBox.addListener('places_changed', function() {
		var places = searchBox.getPlaces();
		
		if (places.length == 0) {
			return;
		}
		
		var bounds = new google.maps.LatLngBounds();
		
		places.forEach(function(place) {
			var latitud = place.geometry.location.lat();
			var longitud = place.geometry.location.lng();
			
			marker.setPosition(new google.maps.LatLng(latitud, longitud));
		
			if (place.geometry.viewport) {
				bounds.union(place.geometry.viewport);
			} else {
				bounds.extend(place.geometry.location);
			}
		});
		
		map.fitBounds(bounds);
	});
	
	navigator.geolocation.getCurrentPosition(
		center, 
		positionError
	);
}

function center(data) {
	var crd = data.coords;
	
	$("#divPosicion").text(crd.latitude + ", " + crd.longitude + " - " + crd.accuracy);
	$("#inputLatitud").val(crd.latitude);
	$("#inputLongitud").val(crd.longitude);
	$("#inputPrecision").val(crd.accuracy);
	
	var latlng = new google.maps.LatLng(crd.latitude, crd.longitude);
	
	marker.setPosition(latlng);
	
	map.setCenter(latlng);
}

function positionError() {
	
}

function selectDepartamentoOnChange(event, element) {
	var departamentoId = $("#selectDepartamento").val();
	
	fillSelect(
		"selectBarrio",
		[],
		"id",
		"nombre"
	);
	
	if (departamentoId != 0) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/BarrioREST/listMinimalByDepartamentoId/" + departamentoId
		}).then(function(data) {
			fillSelect(
				"selectBarrio",
				data,
				"id",
				"nombre"
			);		
		});
	}
}

function selectBarrioOnChange(event, element) {
	
}

function inputLimpiarOnClick(event, element) {
	$("#inputNombre").val(null);
	$("#inputTelefono").val(null);
	$("#inputDocumento").val(null);
	$("#inputContacto").val(null);
	$("#selectDepartamento").val(0);
	$("#inputDireccion").val(null);
}

function inputSubmitOnClick(event) {
	var puntoVenta = { 
		latitud: marker.getPosition().lat(),
		longitud: marker.getPosition().lng(),
		precision: map.getZoom(),
	};
	
	var nombre = $("#inputNombre").val();
	if (nombre != null && nombre != "") {
		puntoVenta.nombre = nombre;
	} else {
		alert("Debe ingresar el nombre del Punto de venta.");
		return;
	}
	
	var telefono = $("#inputTelefono").val();
	if (telefono != null && telefono != "") {
		puntoVenta.telefono = telefono;
	} else {
		alert("Debe ingresar el teléfono del Punto de venta.");
		return;
	}
	
	var documento = $("#inputDocumento").val();
	if (documento != null && documento != "") {
		puntoVenta.documento = documento;
	} else {
		alert("Debe ingresar el documento del contacto del Punto de venta.");
		return;
	}
	
	var contacto = $("#inputContacto").val();
	if (contacto != null && contacto != "") {
		puntoVenta.contacto = contacto;
	} else {
		alert("Debe ingresar el contacto del Punto de venta.");
		return;
	}
	
	var departamentoId = $("#selectDepartamento").val();
	if (departamentoId > 0) {
		puntoVenta.departamento = {
			id: departamentoId
		};
	}
	else {
		alert("Debe seleccionar un Departamento.");
		return;
	}
	
	var barrioId = $("#selectBarrio").val();
	if (barrioId > 0) {
		puntoVenta.barrio = {
			id: barrioId
		};
	}
	else {
		alert("Debe seleccionar un Barrio.");
		return;
	}
	
	var direccion = $("#inputDireccion").val();
	if (direccion != null && direccion != "") {
		puntoVenta.direccion = direccion;
	} else {
		alert("Debe ingresar la dirección del Punto de venta.");
		return;
	}
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/addMobile",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(puntoVenta)
	}).then(function(data) {
		alert("Operación exitosa.");
		
		inputLimpiarOnClick();
	});
}