__EMPRESA_ELARED_ID = 1;

$(document).ready(init);

function init() {
	refinarForm();
	
	$("#divEliminarZona").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/DepartamentoREST/list"
	}).then(function(data) {
		fillSelect(
			"selectDepartamento", 
			data,
			"id", 
			"nombre"
		);
	}).then(function(data) {
		$(".inputCantidad").prop("disabled", true);
		$("input:checkbox").change(function(eventObject) {
			var target = eventObject.target;
			
			if ($(target).prop("checked")) {
				$("#" + $(target).attr("id") + "Cantidad").val(10);
				$("#" + $(target).attr("id") + "Cantidad").prop("disabled", false);
			} else {
				$("#" + $(target).attr("id") + "Cantidad").val("");
				$("#" + $(target).attr("id") + "Cantidad").prop("disabled", true);
			}
		});
		$("input:checkbox").prop("checked", false);
		
		if (id != null) {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/ZonaREST/getById/" + id
			}).then(function(data) {
				$("#inputZonaNombre").val(data.nombre);
				$("#inputZonaDetalle").val(data.detalle);
				
				if ($("#selectDepartamento").length > 0) { 
					$("#selectDepartamento").val(data.departamento.id);
				} else {
					$("#divDepartamento").attr("did", data.departamento.id);
					$("#divDepartamento").html(data.departamento.nombre);
				}
				
				if (mode == __FORM_MODE_ADMIN) {
					$("#divEliminarZona").show();
					$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				}
			}).then(function(data) {
				$.ajax({
					url: "/LogisticaWEB/RESTFacade/DisponibilidadEntregaEmpresaZonaTurnoREST/listByEmpresaZona"
						+ "?eid=" + __EMPRESA_ELARED_ID + "&zid=" + id
				}).then(function(data) { 
					for (var i=0; i<data.length; i++) {
						var turno = data[i].turno;
						var dia = data[i].dia;
						
						var inputs = $(".inputCantidad[tid='" + turno.id +  "']");
						for (var j=0; j<inputs.length; j++) {
							var input = $(inputs[j]);
							
							if (input.attr("did") == dia) {
								input.val(data[i].cantidad);
								input.prop("disabled", false);
								
								$("#" + input.attr("id").replace("Cantidad", "")).prop("checked", true);
								break;
							}
						}
					}
				});
			});
		}
	});
}

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function inputGuardarOnClick(event) {
	var zona = {
		nombre: $("#inputZonaNombre").val(),
		detalle: $("#inputZonaDetalle").val()
	};
	
	if ($("#selectDepartamento").length > 0 && $("#selectDepartamento").val() != 0) {
		zona.departamento = {
			id: $("#selectDepartamento").length > 0 ? 
				$("#selectDepartamento").val() : 
				$("#divDepartamento").attr("did")
		};
	} else {
		alert("Debe seleccionar un departamento.");
		
		return;
	}
	
	var empresa = {
		id: __EMPRESA_ELARED_ID
	};
	
	var inputsCantidad = $(".inputCantidad");
	
	var disponibilidades = [];
	for (var i=0; i < inputsCantidad.length; i++) {
		if (!$(inputsCantidad[i]).prop("disabled")) {
			disponibilidades[disponibilidades.length] = {
				empresa: empresa,
				zona: zona,
				turno: {
					id: $(inputsCantidad[i]).attr("tid")
				},
				dia: $(inputsCantidad[i]).attr("did"),
				cantidad: $(inputsCantidad[i]).val()
			};
		}
	}
	
	if (id != null) {
		zona.id = id;
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ZonaREST/update",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(zona)
		}).then(function(data) {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/DisponibilidadEntregaEmpresaZonaTurnoREST/updateDisponibilidadByZona/" + zona.id,
				method: "POST",
				contentType: 'application/json',
				data: JSON.stringify(disponibilidades)
			}).then(function(data) {
				alert("Operación exitosa");
			});
		});
	} else {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ZonaREST/add",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(zona)
		}).then(function(data) {
			if (data != null) {
				$.ajax({
					url: "/LogisticaWEB/RESTFacade/DisponibilidadEntregaEmpresaZonaTurnoREST/updateDisponibilidadByZona/" + data.id,
					method: "POST",
					contentType: 'application/json',
					data: JSON.stringify(disponibilidades)
				}).then(function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarZona").prop("disabled", false);
				});
			} else {
				alert("Error en la operación");
			}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará la Zona")) {
		var zona = {
			id: id
		};
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ZonaREST/remove",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(zona)
		}).then(function(data) {
			alert("Operación exitosa");
		});
	}
}