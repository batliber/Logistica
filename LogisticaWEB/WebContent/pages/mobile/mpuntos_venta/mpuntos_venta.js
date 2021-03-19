var __ESTADO_VISITA_PENDIENTE_ID = 1;
var __ESTADO_VISITA_VISITADO_ID = 2;
var __ESTADO_VISITA_PERMANENTE_ID = 6;
var __ESTADO_VISITA_AUTOR_ID = 7;

var map = null;
var marker = null;
var markers = [];

$(document).ready(init);

function init() {
	$("#divTitle").append("Mapa");
	
	$("#selectDepartamento").change(reloadData);
	$("#selectBarrio").change(reloadData);
	$("#selectEstado").change(reloadData);
	
	fillSelect("selectBarrio", [], "id", "nombre");
	fillSelect("selectEstado", [], "id", "nombre");
	
	listDepartamentos()
		.then(function(data) { 
			fillSelect("selectDepartamento", data, "id", "nombre");
			
			initMap();
		});
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
	
	navigator.geolocation.getCurrentPosition(
		center, 
		positionError
	);
}

function center(data) {
	var crd = data.coords;
	
	var latlng = new google.maps.LatLng(crd.latitude, crd.longitude);
	
	marker.setPosition(latlng);
	
	reloadData();
}

function positionError() {
	reloadData();
}

function clearMarkers() {
	for (var j=0; j<markers.length; j++) {
		markers[j].setMap(null);
	}
	markers = [];
}

function fillMap(visitas) {
	clearMarkers();
	
	var markerBounds = new google.maps.LatLngBounds();
	
	for (var i=0; i<visitas.length; i++) {
		var visita = visitas[i];
		var puntoVenta = visita.puntoVenta;
		if (puntoVenta.latitud != null && puntoVenta.longitud != null) {
			var latlng = {
				lat: puntoVenta.latitud, lng: puntoVenta.longitud
			};
			
			var icon = {
				url: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png"
			}
			if (puntoVenta.estadoVisitaPuntoVentaDistribuidor != null) {
				if (puntoVenta.estadoVisitaPuntoVentaDistribuidor.id == __ESTADO_VISITA_PENDIENTE_ID) {
					icon.url = "https://maps.google.com/mapfiles/ms/icons/red-dot.png"
				} else if (puntoVenta.estadoVisitaPuntoVentaDistribuidor.id == __ESTADO_VISITA_VISITADO_ID) {
					icon.url = "https://maps.google.com/mapfiles/ms/icons/green-dot.png";
				} else if (puntoVenta.estadoVisitaPuntoVentaDistribuidor.id == __ESTADO_VISITA_PERMANENTE_ID) {
					icon.url = "https://maps.google.com/mapfiles/ms/icons/yellow-dot.png";
				} else if (puntoVenta.estadoVisitaPuntoVentaDistribuidor.id == __ESTADO_VISITA_AUTOR_ID) {
					icon.url = "https://maps.google.com/mapfiles/ms/icons/blue-dot.png";
				}
			}
			
			var marker = new google.maps.Marker({
				position: latlng,
				map: map,
				title: puntoVenta.nombre,
				icon: icon,
				customInfo: visita.id
			});
			
			marker.addListener('click', function(data){
				$.ajax({
					url: "/LogisticaWEB/RESTFacade/VisitaPuntoVentaDistribuidorREST/getById/" + this.customInfo
				}).then(function(visitaPuntoVenta) {
					if (visitaPuntoVenta != null) {
						/*
						if (confirm(
								"Punto de Venta: " + visitaPuntoVenta.puntoVenta.nombre + ".\n"
								+ "Direccion: " + visitaPuntoVenta.puntoVenta.direccion + ".\n"
								+ "Desea visitar el punto de venta?"
							)
						) {
							window.location.href = 
								"/LogisticaWEB/pages/mobile/mvisita/mvisita.jsp?vid=" + visitaPuntoVenta.id;
						}
						*/
						if (confirm(
								"Punto de Venta: " + visitaPuntoVenta.puntoVenta.nombre + ".\n"
								+ "Direccion: " + visitaPuntoVenta.puntoVenta.direccion + ".\n"
								+ "Desea navegar al punto de venta?"
							)
						) {
							window.location.href = 
								"https://www.google.com/maps/search/?api=1&dir_action=navigate&query=" + 
									visitaPuntoVenta.puntoVenta.latitud
									+ ", " + visitaPuntoVenta.puntoVenta.longitud
						}
					}
				});
			});

			
			markers.push(marker);
			
			markerBounds.extend(latlng);
		}
	}
	
	if (visitas.length > 1) {
		map.fitBounds(markerBounds);
	} else if (visitas.length == 1 && visitas[0].puntoVenta.precision != null) {
		map.setCenter({ lat: visitas[0].puntoVenta.latitud, lng: visitas[0].puntoVenta.longitud });
		map.setZoom(visitas[0].puntoVenta.precision);
	}
}

function reloadData(eventObject) {
	var metadataConsulta = {
		tamanoMuestra: 1000,
		metadataCondiciones: [],
		metadataOrdenaciones: []
	};
	
	if ($("#selectDepartamento").val() != "0") {
		metadataConsulta.metadataCondiciones[metadataConsulta.metadataCondiciones.length] = {
			campo: "puntoVenta.departamento.id",
			operador: "eq",
			valores: [$("#selectDepartamento").val()]
		}
	}
	
	if ($("#selectBarrio").val() != "0") {
		metadataConsulta.metadataCondiciones[metadataConsulta.metadataCondiciones.length] = {
			campo: "puntoVenta.barrio.id",
			operador: "eq",
			valores: [$("#selectBarrio").val()]
		}
	}
	
	if ($("#selectEstado").val() != "0") {
		metadataConsulta.metadataCondiciones[metadataConsulta.metadataCondiciones.length] = {
			campo: "estadoVisitaPuntoVentaDistribuidor.id",
			operador: "eq",
			valores: [$("#selectEstado").val()]
		}
	}
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/VisitaPuntoVentaDistribuidorREST/listMisVisitasContextAndLocationAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
			"metadataConsulta": metadataConsulta,
			"latitud": -34.9017,
			"longitud": -56.1634
		})
	}).then(function(data) {
		var departamentos = [];
		var barrios = [];
		var estados = [];
		
		var visitas = data.registrosMuestra;
		for (var i=0; i<visitas.length; i++) {
			departamentos[departamentos.length] = {
				id: visitas[i].puntoVenta.departamento.id,
				nombre: visitas[i].puntoVenta.departamento.nombre
			};
			
			barrios[barrios.length] = {
				id: visitas[i].puntoVenta.barrio.id,
				nombre: visitas[i].puntoVenta.barrio.nombre
			};
			
			estados[estados.length] = {
				id: visitas[i].estadoVisitaPuntoVentaDistribuidor.id,
				nombre: visitas[i].estadoVisitaPuntoVentaDistribuidor.nombre
			};
		}
		
		if ($("#selectBarrio").val() == "0") {
			fillSelect("selectBarrio", barrios, "id", "nombre");
		}
		
		if ($("#selectEstado").val() == "0") {
			fillSelect("selectEstado", estados, "id", "nombre");
		}
		
		fillMap(visitas);
	});
}