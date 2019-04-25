var map = null;
var infoWindow = null;
var markers = [];

$(document).ready(init);

function init() {
	$("#divTitle").append("Entregar sub-lote");
	
	DepartamentoDWR.list(
		{
			callback: fillSelectDepartamento, async: false
		}
	);
	
	fillSelectBarrio([]);
	fillSelectPuntoVenta([]);
	
	initMap();
	
	/*
	PuntoVentaDWR.listMinimalContextAndLocationAware(
		{
			callback: function(data) {
				fillSelectPuntoVenta(data);
				fillMap(data);
			}
			, async: false
		}
	);
	*/
}

function initMap() {
	var divMap = document.getElementById("divMap");
	
	map = new google.maps.Map(
		divMap, {
			center: {lat: 0, lng: 0},
			zoom: 15
    	}
	);
	
	if (numeroSublote != null) {
		ActivacionSubloteDWR.getByNumero(
			numeroSublote,
			{
				callback: showSubloteData, async: false
			}
		);
	} else if (id != null) {
		ActivacionSubloteDWR.getById(
			id,
			{
				callback: showSubloteData, async: false
			}
		);
	} else {
		navigator.geolocation.getCurrentPosition(
			center, 
			positionError
		);
	}
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
			$("#divFechaAsignacionDistribuidor").attr("d", data.fechaAsignacionDistribuidor.getTime());
			$("#divFechaAsignacionDistribuidor").html(formatLongDate(data.fechaAsignacionDistribuidor));
		} else {
			$("#divDistribuidor").attr("did", null);
			$("#divDistribuidor").html("&nbsp;");
			$("#divFechaAsignacionDistribuidor").attr("d", null);
			$("#divFechaAsignacionDistribuidor").html("&nbsp");
		}
		
		$("#inputNumeroSublote").val(data.numero);
		if (data.puntoVenta != null) {
			$("#selectDepartamento").val(data.puntoVenta.departamento.id);
			
			fillSelectBarrio([data.puntoVenta.barrio]);
			$("#selectBarrio").val(data.puntoVenta.barrio.id);
			
			fillSelectPuntoVenta([data.puntoVenta]);
			$("#selectPuntoVenta").val(data.puntoVenta.id);
			
			fillMap([data.puntoVenta]);
		} else {
			$("#selectDepartamento").val(0);
			
			fillSelectBarrio([]);
			$("#selectBarrio").val(0);
			
			fillSelectPuntoVenta([]);
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

function fillSelectDepartamento(data) {
	$("#selectDepartamento > option").remove();
	
	html =
		"<option value='0'>Seleccione...</option>";
	
	for (var i=0; i<data.length; i++) {
		html +=
			"<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
	}
	
	$("#selectDepartamento").append(html);
}

function fillSelectBarrio(data) {
	$("#selectBarrio > option").remove();
	
	html =
		"<option value=0>Seleccione...</option>";
	
	for (var i=0; i<data.length; i++) {
		html +=
			"<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
	}
	
	$("#selectBarrio").append(html);
}

function fillSelectPuntoVenta(data) {
	$("#selectPuntoVenta > option").remove();
	
	html =
		"<option value=0>Seleccione...</option>";
	
	for (var i=0; i<data.length; i++) {
		html +=
			"<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
	}
	
	$("#selectPuntoVenta").append(html);
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
		ActivacionSubloteDWR.getByNumeroContextAware(
			numeroSublote,
			{
				callback: showSubloteData, async: false
			}
		);
	} else {
		inputLimpiarOnClick();
	}
}

function selectDepartamentoOnChange(event, element) {
	var departamentoId = $("#selectDepartamento").val();
	
	fillSelectBarrio([]);
	fillSelectPuntoVenta([]);
	fillMap([]);
	
	if (departamentoId != 0) {
		BarrioDWR.listByDepartamentoId(
			departamentoId,
			{
				callback: function(data) {
					fillSelectBarrio(data);
				}, async: false
			}
		);
		
		PuntoVentaDWR.listByDepartamentoIdContextAndLocationAware(
			departamentoId,
			$("#inputLatitud").val(),
			$("#inputLongitud").val(),
			{
				callback: function(data) {
					fillSelectPuntoVenta(data);
					fillMap(data);
				}, async: false
			}
		);
	}
}

function selectBarrioOnChange(event, element) {
	var barrioId = $("#selectBarrio").val();
	
	fillSelectPuntoVenta([]);
	fillMap([]);
	
	if (barrioId != 0) {
		PuntoVentaDWR.listByBarrioIdContextAndLocationAware(
			barrioId,
			$("#inputLatitud").val(),
			$("#inputLongitud").val(),
			{
				callback: function(data) {
					fillSelectPuntoVenta(data);
					fillMap(data);
				}, async: false
			}
		);
	}
}

function selectPuntoVentaOnChange(event, element) {
	var puntoVentaId = $("#selectPuntoVenta").val();
	
	if (puntoVentaId != 0) {
		PuntoVentaDWR.getById(
			puntoVentaId,
			{
				callback: function(data) {
					if (data != null) {
						fillMap([data]);
					}
				}, async: false
			}
		);
	} else {
		fillMap([]);
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
		
		activacionSublote.fechaAsignacionPuntoVenta = new Date();
		
		ActivacionSubloteDWR.asignarAPuntoVentaContextAware(
			activacionSublote,
			activacionSublote.puntoVenta,
			{
				callback: function(data) {
					alert("Operación exitosa.");
					
					inputLimpiarOnClick();
				}, async: false
			}
		);
	} else {
		alert("Debe seleccionar un sub-lote.")
	}
}