$(document).ready(function() {
	refinarForm();
	
	$("#divEliminarZona").hide();
	
	DepartamentoDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectDepartamento").append(html);
			}, async: false
		}
	);
	
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
		ZonaDWR.getById(
			id,
			{
				callback: function(data) {
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
				}, async: false
			}
		);
		
		DisponibilidadEntregaEmpresaZonaTurnoDWR.listByEmpresaZona(
			{
				id: 1
			},
			{
				id: id,
				departamento: {
					id: $("#selectDepartamento").val()
				}
			},
			{
				callback: function(data) {
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

function inputGuardarOnClick(event) {
	var zona = {
		nombre: $("#inputZonaNombre").val(),
		detalle: $("#inputZonaDetalle").val()
	};
	
	if ($("#selectDepartamento").length > 0 && $("#selectDepartamento").val() != 0) {
		zona.departamento = {
			id: $("#selectDepartamento").length > 0 ? $("#selectDepartamento").val() : $("#divDepartamento").attr("did")
		};
	} else {
		alert("Debe seleccionar un departamento.");
		
		return;
	}
	
	if (id != null) {
		zona.id = id;
		
		ZonaDWR.update(
			zona,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		ZonaDWR.add(
			zona,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarZona").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará la Zona")) {
		var zona = {
			id: id
		};
		
		ZonaDWR.remove(
			zona,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}