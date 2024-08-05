var __ESTADO_VISITA_PENDIENTE_ID = 1;
var __ESTADO_VISITA_VISITADO_ID = 2;
var __ESTADO_VISITA_PERMANENTE_ID = 6;
var __ESTADO_VISITA_AUTOR_ID = 7;

var map = null;
var infoWindow = null;
var markers = [];

$(document).ready(init);

function init() {
	$("#divTitle").append("Entregar sub-lote");
	
	$("#selectDepartamento").change(reloadPuntosVenta);
	$("#selectBarrio").change(reloadPuntosVenta);
	$("#selectPuntoVenta").change(reloadPuntosVenta);
	
	fillSelect("selectDepartamento", [], "id", "nombre");
	fillSelect("selectBarrio", [], "id", "nombre");
	fillSelect("selectPuntoVenta", [], "id", "nombre");
	
	initMap();
	
	reloadPuntosVenta();
	
	if (numeroSublote != null) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ActivacionSubloteREST/getByNumero/" + numeroSublote
		}).then(showSubloteData);
	} else if (id != null) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ActivacionSubloteREST/getById/" + id
		}).then(showSubloteData);
	}
}

function initMap() {
	var divMap = document.getElementById("divMap");
	
	map = new google.maps.Map(
		divMap, {
			center: {lat: 0, lng: 0},
			zoom: 15
		}
	);
	
	navigator.geolocation.getCurrentPosition(
		center, 
		positionError
	);
}

function showSubloteData(data) {
	if (data != null) {
		id = data.id;
		
		if (data.empresa != null) {
			$("#divEmpresa").attr("eid", data.empresa.id);
			$("#divEmpresa").html(data.empresa.nombre);
		} else {
			$("#divEmpresa").attr("eid", null);
			$("#divEmpresa").html("&nbsp;");
		}
		
		if (data.distribuidor != null) {
			$("#divDistribuidor").attr("did", data.distribuidor.id);
			$("#divDistribuidor").html(data.distribuidor.nombre);
			$("#divFechaAsignacionDistribuidor").attr("d", data.fechaAsignacionDistribuidor);
			$("#divFechaAsignacionDistribuidor").html(formatLongDate(data.fechaAsignacionDistribuidor));
		} else {
			$("#divDistribuidor").attr("did", null);
			$("#divDistribuidor").html("&nbsp;");
			$("#divFechaAsignacionDistribuidor").attr("d", null);
			$("#divFechaAsignacionDistribuidor").html("&nbsp");
		}
		
		$("#inputNumeroSublote").val(data.numero);
		if (data.puntoVenta != null) {
			fillSelect(
				"selectDepartamento",
				[data.puntoVenta.departamento],
				"id",
				"nombre"
			);
			fillSelect(
				"selectBarrio",
				[data.puntoVenta.barrio],
				"id",
				"nombre"
			);
			fillSelect(
				"selectPuntoVenta",
				[data.puntoVenta],
				"id",
				"nombre"
			);
			
			$("#selectDepartamento").val(data.puntoVenta.departamento.id);
			$("#selectBarrio").val(data.puntoVenta.barrio.id);
			$("#selectPuntoVenta").val(data.puntoVenta.id);
			
			fillMap([data.puntoVenta]);
		} else {
			$("#selectDepartamento").val(0);
			
			fillSelect(
				"selectBarrio",
				[],
				"id",
				"nombre"
			);
			fillSelect(
				"selectPuntoVenta",
				[],
				"id",
				"nombre"
			);
			$("#selectBarrio").val(0);
			$("#selectPuntoVenta").val(0);
					
			fillMap([]);
		}
	} else {
		alert("No se encuentra el sub-lote solicitado.");
		inputLimpiarOnClick();
	}
}

function center(data) {
	var crd = data.coords;
	
	$("#divPosicion").text(crd.latitude + ", " + crd.longitude + " - " + crd.accuracy);
	$("#inputLatitud").val(crd.latitude);
	$("#inputLongitud").val(crd.longitude);
	$("#inputPrecision").val(crd.accuracy);
	
	var latlng = new google.maps.LatLng(crd.latitude, crd.longitude);
	
	if (infoWindow == null) {
		infoWindow = new google.maps.InfoWindow({
			map: map,
			position: latlng,
			content: "Ubicación actual"
		});
	} else {
		infoWindow.position = latlng;
	}
	
	map.setCenter(latlng);
}

function positionError() {
	$("#divPosicion").text("No se puede determinar la posición.");
}

function fillMap(data) {
	clearMarkers();
	
	var markerBounds = new google.maps.LatLngBounds();
	
	for (var i=0; i<data.length; i++) {
		var puntoVenta = data[i];
		if (puntoVenta.latitud != null && puntoVenta.longitud != null) {
			var latlng = {
				lat: puntoVenta.latitud, lng: puntoVenta.longitud
			};
			
			var icon = {
				url: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png"
			};
			if (puntoVenta.estadoVisitaPuntoVentaDistribuidor != null) {
				if (puntoVenta.estadoVisitaPuntoVentaDistribuidor.id == __ESTADO_VISITA_PENDIENTE_ID) {
					icon.url = "https://maps.google.com/mapfiles/ms/icons/red-dot.png"
				} else if (puntoVenta.estadoVisitaPuntoVentaDistribuidor.id == __ESTADO_VISITA_VISITADO_ID) {
					icon.url = "https://maps.google.com/mapfiles/ms/icons/green-dot.png";
				} else if (puntoVenta.estadoVisitaPuntoVentaDistribuidor.id == __ESTADO_VISITA_PERMANENTE_ID) {
					icon.url = "https://maps.google.com/mapfiles/ms/icons/yellow-dot.png";
				}
			}
			
			var marker = new google.maps.Marker({
				position: latlng,
				map: map,
				title: puntoVenta.nombre,
				icon: icon
			});
			
			markers.push(marker);
			
			markerBounds.extend(latlng);
		}
	}
	
	if (data.length > 1) {
		map.fitBounds(markerBounds);
	} else if (data.length == 1 && data[0].precision != null) {
		map.setCenter({ lat: data[0].latitud, lng: data[0].longitud });
		map.setZoom(data[0].precision);
	}
}

function clearMarkers() {
	for (var j=0; j<markers.length; j++) {
		markers[j].setMap(null);
	}
	markers = [];
}

function inputNumeroSubloteOnChange(event, element) {
	var numeroSublote = $("#inputNumeroSublote").val();
	
	if (numeroSublote != null && numeroSublote != "") {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ActivacionSubloteREST/getByNumeroContextAware/" + numeroSublote
		}).then(showSubloteData);
	} else {
		inputLimpiarOnClick();
	}
}

function inputLimpiarOnClick(event, element) {
	$("#divEmpresa").attr("eid", null);
	$("#divEmpresa").html("&nbsp;");
	$("#divDistribuidor").attr("did", null);
	$("#divDistribuidor").html("&nbsp;");
	$("#divFechaAsignacionDistribuidor").attr("d", null);
	$("#divFechaAsignacionDistribuidor").html("&nbsp;");
	$("#inputNumeroSublote").val(null);
	$("#selectDepartamento").val(0);
	$("#selectBarrio > option:gt(0)").remove();
	$("#selectBarrio").val(0);
	$("#selectPuntoVenta > option:gt(0)").remove();
	$("#selectPuntoVenta").val(0);
	
	$("#inputNumeroSublote").focus();
}

function inputSubmitOnClick(event) {
	var empresaId = $("#divEmpresa").attr("eid");
	
	var activacionSublote = {
		empresa: {
			id: empresaId
		}
	};
	
	var distribuidorId = $("#divDistribuidor").attr("did");
	activacionSublote.distribuidor = {
		id: distribuidorId
	};
	
	var puntoVentaId = $("#selectPuntoVenta").val();
	if (puntoVentaId > 0) {
		activacionSublote.puntoVenta = {
			id: puntoVentaId
		};
	}
	else {
		alert("Debe seleccionar un Punto de venta.");
		return;
	}
	
	if (id != null) {
		activacionSublote.id = id;
		activacionSublote.numero = $("#inputNumeroSublote").val();
		
		var fechaAsignacionDistribuidor = $("#divFechaAsignacionDistribuidor").attr("d");
		if (fechaAsignacionDistribuidor != null && fechaAsignacionDistribuidor != "") {
			activacionSublote.fechaAsignacionDistribuidor = fechaAsignacionDistribuidor;
		}
		
		activacionSublote.fechaAsignacionPuntoVenta = new Date().getTime();
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ActivacionSubloteREST/asignarAPuntoVenta",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(activacionSublote)
		}).then(function(data) {
			alert("Operación exitosa.");
			
			inputLimpiarOnClick();
		});
	} else {
		alert("Debe seleccionar un sub-lote.")
	}
}

function reloadPuntosVenta(eventObject) {
	var targetId = eventObject != null ? $(eventObject.currentTarget).attr("id") : "";
	
	var listTO = {
		"latitud": ($("#inputLatitud").val() != null && $("#inputLatitud").val() != "") ? $("#inputLatitud").val() : -34.9017,
		"longitud": ($("#inputLongitud").val() != null && $("#inputLongitud").val() != "") ? $("#inputLongitud").val() : -56.1634
	};
	
	if (targetId.includes("Departamento")) {
		$("#selectBarrio").val(0);
		$("#selectPuntoVenta").val(0);
	} else if (targetId.includes("Barrio")) {
		$("#selectPuntoVenta").val(0);
	}
	
	if ($("#selectDepartamento").val() != "0") {
		listTO.departamentoId = $("#selectDepartamento").val();
	} else {
		/*fillSelect("selectBarrio", [], "id", "nombre");
		fillSelect("selectPuntoVenta", [], "id", "nombre");
		fillMap([]);*/
	}
	
	if ($("#selectBarrio").val() != "0") {
		listTO.barrioId = $("#selectBarrio").val();
	}
	
	if ($("#selectPuntoVenta").val() != "0") {
		listTO.puntoVentaId = $("#selectPuntoVenta").val();
	}
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/listMinimalCreatedORAssignedContextAndLocationAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(listTO)
	}).then(function(data) {
		var departamentos = [];
		var barrios = [];
		var puntosVenta = [];
		
		for (var i=0; i<data.length; i++) {
			if (data[i].departamento != null) {
				departamentos[departamentos.length] = {
					id: data[i].departamento.id,
					nombre: data[i].departamento.nombre
				};
			}
			
			if (data[i].barrio != null) {
				barrios[barrios.length] = {
					id: data[i].barrio.id,
					nombre: data[i].barrio.nombre
				};
			}
			
			puntosVenta[puntosVenta.length] = {
				id: data[i].id,
				nombre: data[i].nombre,
				latitud: data[i].latitud,
				longitud: data[i].longitud
			};
		}
		
		if ($("#selectDepartamento").val() == "0") {
			fillSelect("selectDepartamento", departamentos, "id", "nombre");
		}
		
		if ($("#selectBarrio").val() == "0") {
			fillSelect("selectBarrio", barrios, "id", "nombre");
		}
		
		if ($("#selectPuntoVenta").val() == "0") {
			fillSelect("selectPuntoVenta", puntosVenta, "id", "nombre");
		}
		
		fillMap(data);
	});
}