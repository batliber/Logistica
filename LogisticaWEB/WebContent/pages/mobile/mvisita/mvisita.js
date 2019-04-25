var map = null;
var infoWindow = null;
var markers = [];

$(document).ready(init);

function init() {
	$("#divTitle").append("Visita");
	
	EstadoVisitaPuntoVentaDistribuidorDWR.list(
		{
			callback: function(data) {
				$("#selectEstadoVisitaPuntoVentaDistribuidor > option").remove();
				
				html =
					"<option value=0>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html +=
						"<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectEstadoVisitaPuntoVentaDistribuidor").append(html);
			}, async: false
		}
	);

	initMap();
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
			zoom: 15
    	}
	);
	
	if (id != null) {
		VisitaPuntoVentaDistribuidorDWR.getById(
			id,
			{
				callback: function(data) {
					if (data != null) {
						$("#divDistribuidor").text(data.distribuidor.nombre);
						$("#divDistribuidor").attr("did", data.distribuidor.id);
						$("#divDepartamento").text(data.puntoVenta.departamento.nombre);
						$("#divDepartamento").attr("did", data.puntoVenta.departamento.id);
						$("#divPuntoVenta").text(data.puntoVenta.nombre);
						$("#divPuntoVenta").attr("pvid", data.puntoVenta.id);
						$("#divPuntoVentaBarrio").html(
							data.puntoVenta.barrio != null ? data.puntoVenta.barrio.nombre : "&nbsp;"
						);
						$("#divPuntoVentaDireccion").html(
							data.puntoVenta.direccion != null ? data.puntoVenta.direccion : "&nbsp;"
						);
						$("#divPuntoVentaTelefono").html(
							data.puntoVenta.telefono != null ? data.puntoVenta.telefono : "&nbsp;"
						);
						$("#divPuntoVentaContacto").html(
							data.puntoVenta.contacto != null ? data.puntoVenta.contacto : "&nbsp;"
						);
						$("#divFechaAsignacion").text(formatLongDate(data.fechaAsignacion));
						$("#divFechaAsignacion").attr("fa", formatRawDate(data.fechaAsignacion));
						
						if (data.estadoVisitaPuntoVentaDistribuidor != null) {
							$("#selectEstadoVisitaPuntoVentaDistribuidor").val(data.estadoVisitaPuntoVentaDistribuidor.id);
						}
						if (data.observaciones != null) {
							$("#textareaObservaciones").text(data.observaciones);
						}
						
						if (data.puntoVenta != null) {
							fillMap([data.puntoVenta]);
						}
					}
				}, async: false
			}
		);
	} else {
		navigator.geolocation.getCurrentPosition(
			center, 
			positionError
		);
	}
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

function inputLimpiarOnClick(event, element) {
	$("#divDistribuidor").html("&nbsp;");
	$("#divDistribuidor").attr("did", "");
	$("#divDepartamento").html("&nbsp;");
	$("#divDepartamento").attr("did", "");
	$("#divPuntoVenta").html("&nbsp;");
	$("#divPuntoVentaBarrio").html("&nbsp;");
	$("#divPuntoVentaDireccion").html("&nbsp;");
	$("#divPuntoVentaTelefono").html("&nbsp;");
	$("#divPuntoVentaContacto").html("&nbsp;");
	$("#divPuntoVenta").attr("pvid", "");
	$("#divFechaAsignacion").html("&nbsp;");
	$("#divFechaAsignacion").attr("fa", "");
	$("#textareaObservaciones").val("");
	
	$("#selectEstadoVisitaPuntoVentaDistribuidor").val(0);
	
	navigator.geolocation.getCurrentPosition(
		center, 
		positionError
	);
}

function inputSubmitOnClick(event, element) {
	var visitaPuntoVentaDistribuidor = {
		fechaAsignacion: new Date(parseInt($("#divFechaAsignacion").attr("fa"))),
		fechaVisita: new Date(),
		observaciones: $("#textareaObservaciones").val() != "" ? $("#textareaObservaciones").val() : null,
		distribuidor: {
			id: $("#divDistribuidor").attr("did")
		},
		estadoVisitaPuntoVentaDistribuidor: {
			id: $("#selectEstadoVisitaPuntoVentaDistribuidor").val()
		},
		puntoVenta: {
			id: $("#divPuntoVenta").attr("pvid")
		}
	};
	
	if (id != null) {
		visitaPuntoVentaDistribuidor.id = id;
		
		VisitaPuntoVentaDistribuidorDWR.update(
			visitaPuntoVentaDistribuidor,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}