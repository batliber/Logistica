var map = null;
var marker = null;

$(document).ready(init);

function init() {
	EstadoPuntoVentaDWR.list(
		{
			callback: fillSelectPuntoVentaEstado, async: false
		}
	);
	
	DepartamentoDWR.list(
		{
			callback: fillSelectDepartamento, async: false
		}
	);
	
	fillSelectBarrio([]);
	
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
					$("#divPuntoVentaFechaAsignacionDistribuidor").text(formatRawDate(data.fechaAsignacionDistribuidor));
					$("#divPuntoVentaFechaVisitaDistribuidor").text(formatRawDate(data.fechaVisitaDistribuidor));
					$("#divPuntoVentaFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor").text(formatRawDate(data.fechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor));
					
					if (data.departamento != null) {
						$("#selectPuntoVentaDepartamento").val(data.departamento.id);
						
						selectDepartamentoOnChange();
					}
					
					if (data.barrio != null) {
						$("#selectPuntoVentaBarrio").val(data.barrio.id);
					}
					
					if (data.estadoPuntoVenta != null) {
						$("#selectPuntoVentaEstado").val(data.estadoPuntoVenta.id);
					}
					
					if (data.estadoVisitaPuntoVentaDistribuidor != null) {
						$("#divPuntoVentaEstadoVisitaPuntoVentaDistribuidor").attr("eid", data.estadoVisitaPuntoVentaDistribuidor.id);
					}
					
					if (data.distribuidor != null) {
						$("#divPuntoVentaDistribuidor").attr("did", data.distribuidor.id);
					}
					
					if (data.latitud != null && data.longitud != null) {
						var latlng = {
							lat: data.latitud, lng: data.longitud
						};
						
						marker.setPosition(latlng);
						
						map.setCenter(latlng);
						if (data.precision != null) {
							map.setZoom(data.precision);
						}
					}
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarPuntoVenta").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
					}
				}, async: false
			}
		);
	}
}

function refinarForm() {
	$("#divLabelPorcentajeActivacion").hide();
	$("#divPuntoVentaPorcentajeActivacion").hide();
	$("#divLabelFechaCalculoPorcentajeActivacion").hide();
	$("#divPuntoVentaFechaCalculoPorcentajeActivacion").hide();
	$("#divLabelFechaAsignacionDistribuidor").hide();
	$("#divPuntoVentaFechaAsignacionDistribuidor").hide();
	$("#divLabelDistribuidor").hide();
	$("#divPuntoVentaDistribuidor").hide();
	$("#divLabelFechaVisitaDistribuidor").hide();
	$("#divPuntoVentaFechaVisitaDistribuidor").hide();
	$("#divLabelFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor").hide();
	$("#divPuntoVentaFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor").hide();
	$("#divLabelEstadoVisitaPuntoVentaDistribuidor").hide();
	$("#divPuntoVentaEstadoVisitaPuntoVentaDistribuidor").hide();
	
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function initMap() {
	var divMap = document.getElementById("divMapa");
	
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
}

function fillSelectPuntoVentaEstado(data) {
	$("#selectDepartamento > option").remove();
	
	var html =
		"<option id='0' value='0'>Seleccione...</option>";
	
	for (var i=0; i<data.length; i++) {
		html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
	}
	
	$("#selectPuntoVentaEstado").append(html);
}

function fillSelectDepartamento(data) {
	$("#selectPuntoVentaDepartamento > option").remove();
	
	html =
		"<option value='0'>Seleccione...</option>";
	
	for (var i=0; i<data.length; i++) {
		html +=
			"<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
	}
	
	$("#selectPuntoVentaDepartamento").append(html);
}

function fillSelectBarrio(data) {
	$("#selectPuntoVentaBarrio > option").remove();
	
	html =
		"<option value=0>Seleccione...</option>";
	
	for (var i=0; i<data.length; i++) {
		html +=
			"<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
	}
	
	$("#selectPuntoVentaBarrio").append(html);
}

function selectDepartamentoOnChange(event, element) {
	var departamentoId = $("#selectPuntoVentaDepartamento").val();
	
	fillSelectBarrio([]);
	
	if (departamentoId != 0) {
		BarrioDWR.listByDepartamentoId(
			$("#selectPuntoVentaDepartamento").val(),
			{
				callback: fillSelectBarrio, async: false
			}
		);
	}
}

function inputGuardarOnClick(event) {
	var puntoVenta = {
		nombre: $("#inputPuntoVentaNombre").val(),
		direccion: $("#inputPuntoVentaDireccion").val(),
		telefono: $("#inputPuntoVentaTelefono").val(),
		contacto: $("#inputPuntoVentaContacto").val(),
		documento: $("#inputPuntoVentaDocumento").val(),
		fechaAsignacionDistribuidor: 
			($("#divPuntoVentaFechaAsignacionDistribuidor").text().trim() != "" ?
				new Date(parseInt($("#divPuntoVentaFechaAsignacionDistribuidor").text()))
				: null),
		fechaVisitaDistribuidor: 
			($("#divPuntoVentaFechaVisitaDistribuidor").text().trim() != "" ?
				new Date(parseInt($("#divPuntoVentaFechaVisitaDistribuidor").text()))
				: null),
		fechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor: 
			($("#divPuntoVentaFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor").text().trim() != "" ?
				new Date(parseInt($("#divPuntoVentaFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor").text()))
				: null),
		latitud: marker.getPosition().lat(),
		longitud: marker.getPosition().lng(),
		precision: map.getZoom(),
		departamento: {
			id: $("#selectPuntoVentaDepartamento").val()
		},
		barrio: {
			id: $("#selectPuntoVentaBarrio").val()
		},
		estadoPuntoVenta: {
			id: $("#selectPuntoVentaEstado").val()
		}
	};
	
	if ($("#divPuntoVentaDistribuidor").attr("did") != null 
		&& $("#divPuntoVentaDistribuidor").attr("did") != "") {
		puntoVenta.distribuidor = {
			id: $("#divPuntoVentaDistribuidor").attr("did")
		};
	}
	
	if ($("#divPuntoVentaEstadoVisitaPuntoVentaDistribuidor").attr("eid") != null 
		&& $("#divPuntoVentaEstadoVisitaPuntoVentaDistribuidor").attr("eid") != "") {
		puntoVenta.estadoVisitaPuntoVentaDistribuidor = {
			id: $("#divPuntoVentaEstadoVisitaPuntoVentaDistribuidor").attr("eid")
		};
	}
	
	if (puntoVenta.nombre == "") {
		alert("Debe ingresar un nombre.");
		
		return;
	}
	
	if (puntoVenta.estadoPuntoVenta.id == 0) {
		alert("Debe ingresar un estado.")
		
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