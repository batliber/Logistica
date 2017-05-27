var map = null;

var latitud = null;
var longitud = null;

$(document).ready(function() {
	DepartamentoDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectPuntoVentaDepartamento").append(html);
			}, async: false
		}
	);
	
	$("#selectPuntoVentaBarrio").append("<option id='0' value='0'>Seleccione...</option>");
	
	refinarForm();
	
	$("#divEliminarPuntoVenta").hide();
	
	initMap();
	
	if (id != null) {
		PuntoVentaDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputPuntoVentaNombre").val(data.nombre);
					$("#inputPuntoVentaDireccion").val(data.direccion);
					$("#inputPuntoVentaTelefono").val(data.telefono);
					$("#inputPuntoVentaContacto").val(data.contacto);
					$("#inputPuntoVentaDocumento").val(data.documento);
					
					if (data.departamento != null) {
						$("#selectPuntoVentaDepartamento").val(data.departamento.id);
						
						selectDepartamentoOnChange();
					}
					
					if (data.barrio != null) {
						$("#selectPuntoVentaBarrio").val(data.barrio.id);
					}
					
					latitud = data.latitud;
					longitud = data.longitud;
					
					var latlng = {
						lat: data.latitud, lng: data.longitud
					};
					
					var marker = new google.maps.Marker({
						position: latlng,
						map: map,
						title: data.nombre
					});
					
					map.setCenter(latlng);
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarPuntoVenta").show();
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

function initMap() {
	var divMap = document.getElementById("divMapa");
	map = new google.maps.Map(
		divMap, {
			center: {lat: 0, lng: 0},
			zoom: 8
    	}
	);
	
	var input = document.getElementById("inputBusqueda");
	var searchBox = new google.maps.places.SearchBox(input);
	
	map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
	
	// Bias the SearchBox results towards current map's viewport.
	map.addListener('bounds_changed', function() {
		searchBox.setBounds(map.getBounds());
	});
	
	var markers = [];
	
	// Listen for the event fired when the user selects a prediction and retrieve
	// more details for that place.
	searchBox.addListener('places_changed', function() {
		var places = searchBox.getPlaces();
		
		if (places.length == 0) {
			return;
		}
		
		// Clear out the old markers.
		markers.forEach(function(marker) {
			marker.setMap(null);
		});
		
		markers = [];
		
		var bounds = new google.maps.LatLngBounds();
		
		places.forEach(function(place) {
			latitud = place.geometry.location.lat();
			longitud = place.geometry.location.lng();
			
			markers.push(new google.maps.Marker({
				map: map,
				title: place.name,
				position: place.geometry.location
			}));
		
			if (place.geometry.viewport) {
				// Only geocodes have viewport.
				bounds.union(place.geometry.viewport);
			} else {
				bounds.extend(place.geometry.location);
			}
		});
		
		map.fitBounds(bounds);
	});
}

function selectDepartamentoOnChange(event, element) {
	$("#selectPuntoVentaBarrio > option:gt(0)").remove();
	
	if ($("#selectPuntoVentaDepartamento").val() != 0) {
		BarrioDWR.listByDepartamentoId(
			$("#selectPuntoVentaDepartamento").val(),
			{
				callback: function(data) {
					var html = "";
					
					for (var i=0; i<data.length; i++) {
						html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
					}
					
					$("#selectPuntoVentaBarrio").append(html);
				}, async: false
			}
		);
	} else {
		$("#selectPuntoVentaBarrio").val(0);
	}
}

function inputGuardarOnClick(event) {
	var puntoVenta = {
		nombre: $("#inputPuntoVentaNombre").val(),
		direccion: $("#inputPuntoVentaDireccion").val(),
		telefono: $("#inputPuntoVentaTelefono").val(),
		contacto: $("#inputPuntoVentaContacto").val(),
		documento: $("#inputPuntoVentaDocumento").val(),
		latitud: latitud,
		longitud: longitud,
		departamento: {
			id: $("#selectPuntoVentaDepartamento").val()
		},
		barrio: {
			id: $("#selectPuntoVentaBarrio").val()
		}
	};
	
	if (puntoVenta.nombre == "") {
		alert("Debe ingresar un nombre.");
		
		return;
	}
	
	if (puntoVenta.departamento.id == 0) {
		alert("Debe seleccionar un departamento.");
		
		return;
	}
	
	if (puntoVenta.barrio.id == 0) {
		alert("Debe seleccionar un barrio.");
		
		return;
	}
	
	if (id != null) {
		puntoVenta.id = id;
		
		PuntoVentaDWR.update(
			puntoVenta,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		PuntoVentaDWR.add(
			puntoVenta,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarPuntoVenta").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el punto de venta")) {
		var puntoVenta = {
			id: id
		};
		
		PuntoVentaDWR.remove(
			puntoVenta,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}