$(document).ready(init);

function init() {
	refinarForm();
	
	$("#divEliminarVisitaPuntoVentaDistribuidor").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/listMinimal"
	}).then(function(data) {
		fillSelect(
			"selectPuntoVenta", 
			data,
			"id", 
			"nombre"
		);
	}).then(function(data) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listDistribuidoresChipsByContextMinimal"
		}).then(function(data) {
			fillSelect(
				"selectDistribuidor", 
				data,
				"id", 
				"nombre"
			);
		}).then(function(data) {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/EstadoVisitaPuntoVentaDistribuidorREST/list"
			}).then(function(data) {
				fillSelect(
					"selectEstadoVisitaPuntoVentaDistribuidor", 
					data,
					"id", 
					"nombre"
				);
			}).then(function(data) {
				if (id != null) {
					$.ajax({
						url: "/LogisticaWEB/RESTFacade/VisitaPuntoVentaDistribuidorREST/getById/" + id
					}).then(function(data) {
						$("#inputFechaAsignacion").val(formatShortDate(data.fechaAsignacion));
						$("#inputFechaVisita").val(formatShortDate(data.fechaVisita));
						$("#textareaObservaciones").val(data.observaciones);
						
						if (data.puntoVenta != null) {
							$("#selectPuntoVenta").val(data.puntoVenta.id);
						}
						
						if (data.estadoVisitaPuntoVentaDistribuidor != null) {
							$("#selectEstadoVisitaPuntoVentaDistribuidor").val(
								data.estadoVisitaPuntoVentaDistribuidor.id
							);
						}
						
						if (data.distribuidor != null) {
							$("#selectDistribuidor").val(data.distribuidor.id);
						}
						
						if (mode == __FORM_MODE_ADMIN) {
							$("#divEliminarVisitaPuntoVentaDistribuidor").show();
							$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						}
					});
				}
			});
		});
	});
}

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function inputGuardarOnClick(event) {
	var visitaPuntoVentaDistribuidor = {
		fechaAsignacion: parseShortDate($("#inputFechaAsignacion").val()),
		fechaVisita: parseShortDate($("#inputFechaVisita").val()),
		observaciones: $("#textareaObservaciones").val(),
		distribuidor: {
			id: $("#selectDistribuidor").val()
		},
		estadoVisitaPuntoVentaDistribuidor: {
			id: $("#selectEstadoVisitaPuntoVentaDistribuidor").val()
		},
		puntoVenta: {
			id: $("#selectPuntoVenta").val()
		}
	};
	
	if (visitaPuntoVentaDistribuidor.distribuidor.id == "0") {
		alert("Debe seleccionar un distribuidor.");
		
		return;
	}
	
	if (visitaPuntoVentaDistribuidor.estadoVisitaPuntoVentaDistribuidor.id == "0") {
		alert("Debe seleccionar un estado.")
		
		return;
	}
	
	if (visitaPuntoVentaDistribuidor.puntoVenta.id == "0") {
		alert("Debe seleccionar un punto de venta.");
		
		return;
	}
	
	if (id != null) {
		visitaPuntoVentaDistribuidor.id = id;
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/VisitaPuntoVentaDistribuidorREST/update",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(visitaPuntoVentaDistribuidor)
		}).then(function(data) {
			alert("Operación exitosa");
		});
	} else {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/VisitaPuntoVentaDistribuidorREST/add",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(visitaPuntoVentaDistribuidor)
		}).then(function(data) {
			if (data != null) {
				alert("Operación exitosa");
				
				$("#inputEliminarVisitaPuntoVentaDistribuidor").prop("disabled", false);
			} else {
				alert("Error en la operación");
			}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el registro.")) {
		var visitaPuntoVentaDistribuidor = {
			id: id
		};
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/VisitaPuntoVentaDistribuidorREST/remove",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(visitaPuntoVentaDistribuidor)
		}).then(function(data) { 
			alert("Operación exitosa");
		});
	}
}