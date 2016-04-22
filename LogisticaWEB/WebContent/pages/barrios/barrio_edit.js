$(document).ready(function() {
	refinarForm();
	
	$("#divEliminarBarrio").hide();
	
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
	
	ZonaDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectZona").append(html);
			}, async: false
		}
	);
	
	if (id != null) {
		BarrioDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputBarrioNombre").val(data.nombre);
					
					if ($("#selectDepartamento").length > 0) { 
						$("#selectDepartamento").val(data.departamento.id);
					} else {
						$("#divDepartamento").attr("did", data.departamento.id);
						$("#divDepartamento").html(data.departamento.nombre);
					}
					
					if ($("#selectZona").length > 0) { 
						$("#selectZona").val(data.zona.id);
					} else {
						$("#divZona").attr("zid", data.zona.id);
						$("#divZona").html(data.zona.nombre);
					}
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarBarrio").show();
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

function selectDepartamentoOnChange() {
	$("#selectZona > option:gt(0)").remove();

	ZonaDWR.listByDepartamentoId(
		$("#selectDepartamento").val(),
		{
			callback: function(data) {
				$("#selectZona > option:gt(0)").remove();
				
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectZona").append(html);
			}, async: false
		}
	);
}

function inputGuardarOnClick(event) {
	var barrio = {
		nombre: $("#inputBarrioNombre").val()
	};
	
	if ($("#selectDepartamento").length > 0 && $("#selectDepartamento").val() != 0) {
		barrio.departamento = {
			id: $("#selectDepartamento").length > 0 ? $("#selectDepartamento").val() : $("#divDepartamento").attr("did")
		};
	} else {
		alert("Debe seleccionar un departamento.");
		
		return;
	}
	
	if ($("#selectZona").length > 0 && $("#selectZona").val() != 0) {
		barrio.zona = {
			id: $("#selectZona").length > 0 ? $("#selectZona").val() : $("#divZona").attr("zid"),
			departamento: {
				id: $("#selectDepartamento").length > 0 ? $("#selectDepartamento").val() : $("#divDepartamento").attr("did")
			}
		};
	} else {
		alert("Debe seleccionar una zona.");
		
		return;
	}
	
	if (id != null) {
		barrio.id = id;
		
		BarrioDWR.update(
			barrio,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		BarrioDWR.add(
			barrio,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarBarrio").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Barrio")) {
		var barrio = {
			id: id
		};
		
		BarrioDWR.remove(
			barrio,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}