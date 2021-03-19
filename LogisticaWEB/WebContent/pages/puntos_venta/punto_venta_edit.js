var map = null;
var marker = null;

$(document).ready(init);

function init() {
	initTabbedPanel();

	refinarForm();
	
	$("#divEliminarPuntoVenta").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/EstadoPuntoVentaREST/list"
	}).then(function(data) {
		fillSelect(
			"selectPuntoVentaEstado", 
			data,
			"id", 
			"nombre"
		);
	}).then(function(data) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/DepartamentoREST/list"
		}).then(function(data) {
			fillSelect(
				"selectPuntoVentaDepartamento",
				data,
				"id", 
				"nombre"
			);
		});
	}).then(function(data) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/BarrioREST/listMinimal"
		}).then(function(data) {
			fillSelect(
				"selectPuntoVentaBarrio",
				data,
				"id", 
				"nombre"
			);
		}).then(function(data) {
			initMap();
			
			if (id != null) {
				$.ajax({
					url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/getById/" + id
				}).then(function(data) {
					$("#inputPuntoVentaNombre").val(data.nombre);
					$("#inputPuntoVentaDireccion").val(data.direccion);
					$("#inputPuntoVentaTelefono").val(data.telefono);
					$("#inputPuntoVentaContacto").val(data.contacto);
					$("#inputPuntoVentaDocumento").val(data.documento);
					$("#divPuntoVentaFechaAsignacionDistribuidor").text(
						formatRawDate(data.fechaAsignacionDistribuidor)
					);
					$("#divPuntoVentaFechaVisitaDistribuidor").text(
						formatRawDate(data.fechaVisitaDistribuidor)
					);
					$("#divPuntoVentaFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor").text(
						formatRawDate(data.fechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor)
					);
					$("#divPuntoVentaFechaVencimientoChipMasViejo").text(
						formatRawDate(data.fechaVencimientoChipMasViejo)
					);
					
					if (data.departamento != null) {
						$("#selectPuntoVentaDepartamento").val(data.departamento.id);
					}
					
					if (data.barrio != null) {
						$("#selectPuntoVentaBarrio").val(data.barrio.id);
					}
					
					if (data.estadoPuntoVenta != null) {
						$("#selectPuntoVentaEstado").val(data.estadoPuntoVenta.id);
					}
					
					if (data.estadoVisitaPuntoVentaDistribuidor != null) {
						$("#divPuntoVentaEstadoVisitaPuntoVentaDistribuidor").attr(
							"eid", data.estadoVisitaPuntoVentaDistribuidor.id
						);
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
					
					if (data.recargaPuntoVentaCota != null) {
						$("#inputCotaTopeAlarmaSaldo").val(data.recargaPuntoVentaCota.topeAlarmaSaldo);
						$("#inputCotaRecargaTotalDia").val(data.recargaPuntoVentaCota.topeTotalPorDia);
						$("#inputCotaRecargaTotalMes").val(data.recargaPuntoVentaCota.topeTotalPorMes);
						$("#inputCotaRecargaTopeMid").val(data.recargaPuntoVentaCota.topePorMid);
						$("#inputCotaRecargaTopeDescuento").val(data.recargaPuntoVentaCota.topePorcentajeDescuento);
					} else {
						$("#inputCotaTopeAlarmaSaldo").val(null);
						$("#inputCotaRecargaTotalDia").val(null);
						$("#inputCotaRecargaTotalMes").val(null);
						$("#inputCotaRecargaTopeMid").val(null);
						$("#inputCotaRecargaTopeDescuento").val(null);
					}
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarPuntoVenta").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
					}
				});
			}
		});
	});
}

function initTabbedPanel() {
	$("#divTab2").hide();
	
	$(".divTabHeader > div").click(function(eventObject) {
		var element = $(eventObject.currentTarget);
		var index = element.attr("id").substring(element.attr("id").length - 1);
		
		if (element.attr("class") != "divTabTitleFiller") {
			var tabs = $(".divTabbedPanel > .divTab");
			for (var i=0; i<tabs.length; i++) {
				if ((i + 1) == index) {
					$("#divTab" + (i + 1)).show();
					$("#divTabTitle" + (i + 1)).attr("class", "divTabTitleSelected");
				} else {
					$("#divTab" + (i + 1)).hide();
					$("#divTabTitle" + (i + 1)).attr("class", "divTabTitle");
				}
			}
		}
	});
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
	$("#divLabelFechaVencimientoChipMasViejo").hide();
	$("#divPuntoVentaFechaVencimientoChipMasViejo").hide();
	
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

function selectDepartamentoOnChange(event, element) {
	fillSelect(
		"selectPuntoVentaBarrio",
		[],
		"id",
		"nombre"
	);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/BarrioREST/listMinimalByDepartamentoId/" + $("#selectPuntoVentaDepartamento").val()
    }).then(function(data) { 
		fillSelect(
			"selectPuntoVentaBarrio",
			data,
			"id",
			"nombre"
		);		
    });
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
				parseInt($("#divPuntoVentaFechaAsignacionDistribuidor").text())
				: null),
		fechaVisitaDistribuidor: 
			($("#divPuntoVentaFechaVisitaDistribuidor").text().trim() != "" ?
				parseInt($("#divPuntoVentaFechaVisitaDistribuidor").text())
				: null),
		fechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor: 
			($("#divPuntoVentaFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor").text().trim() != "" ?
				parseInt($("#divPuntoVentaFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor").text())
				: null),
		fechaVencimientoChipMasViejo: 
			($("#divPuntoVentaFechaVencimientoChipMasViejo").text().trim() != "" ?
				parseInt($("#divPuntoVentaFechaVencimientoChipMasViejo").text())
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
	
	var topeAlarmaSaldo = $("#inputCotaTopeAlarmaSaldo").val();
	var topeTotalPorDia = $("#inputCotaRecargaTotalDia").val();
	var topePorcentajeDescuento = $("#inputCotaRecargaTotalMes").val();
	var topePorMid = $("#inputCotaRecargaTopeMid").val();
	var topeTotalPorMes = $("#inputCotaRecargaTopeDescuento").val();
	
	if (
		(topeAlarmaSaldo != null && topeAlarmaSaldo != "")
		|| (topeTotalPorDia != null && topeTotalPorDia != "")
		|| (topePorcentajeDescuento != null && topePorcentajeDescuento != "")
		|| (topePorMid != null && topePorMid != "")
		|| (topeTotalPorMes != null && topeTotalPorMes != "") 
	) {
		puntoVenta.recargaPuntoVentaCota = {
			topeAlarmaSaldo: topeAlarmaSaldo,
			topeTotalPorDia: topeTotalPorDia,
			topePorcentajeDescuento: topePorcentajeDescuento,
			topePorMid: topePorMid,
			topeTotalPorMes: topeTotalPorMes,
			pùntoVenta: {
				id: id
			}
		};
	}
	
	if (id != null) {
		puntoVenta.id = id;
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(puntoVenta)
	    }).then(function(data) {
			alert("Operación exitosa");
		});
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/add",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(puntoVenta)
	    }).then(function(data) {
	    	if (data != null) {
				alert("Operación exitosa");
				
				$("#inputEliminarPuntoVenta").prop("disabled", false);
	    	} else {
	    		alert("Error en la operación");
	    	}
	    });
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el punto de venta")) {
		var puntoVenta = {
			id: id
		};
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/remove",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(puntoVenta)
	    }).then(function(data) {
			alert("Operación exitosa");
		});
	}
}