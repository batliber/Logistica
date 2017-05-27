$(document).ready(function() {
	refinarForm();
	
	EmpresaDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectEmpresa").append(html);
			}, async: false
		}
	);
	
	MarcaDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectMarca").append(html);
			}, async: false
		}
	);
	
	MonedaDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectMoneda").append(html);
			}, async: false
		}
	);
	
	$("#selectModelo").append("<option id='0' value='0'>Seleccione...</option>");
	
	if (id != null) {
		$("#selectEmpresa").prop("disabled", true);
		$("#selectMarca").prop("disabled", true);
		$("#selectModelo").prop("disabled", true);
		$("#selectMoneda").prop("disabled", true);
		
		PrecioDWR.getById(
			id,
			{
				callback: function(data) {
					$("#selectEmpresa").val(data.empresa.id);
					$("#selectMarca").val(data.marca.id);
					
					$("#selectModelo").append(
						"<option id='" + data.modelo.id + "' value='" + data.modelo.id + "'>" + data.modelo.descripcion + "</option>"
					);
					
					$("#selectModelo").val(data.modelo.id);
					$("#selectMoneda").val(data.moneda.id);
					$("#inputPrecio").val(data.precio);
					
					if (mode == __FORM_MODE_ADMIN) {
						
					} else {
						
					}
				}, async: false
			}
		);
	}
});

function refinarForm() {
	
}

function selectMarcaOnChange() {
	$("#selectModelo > option").remove();
	
	ModeloDWR.listByMarcaId(
		$("#selectMarca").val(), 
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectModelo").append(html);
			}, async: false
		}
	);
}

function inputGuardarOnClick(event) {
	var empresa = {};
	if ($("#selectEmpresa").length > 0 && $("#selectEmpresa").val() != 0) {
		empresa.id = $("#selectEmpresa").length > 0 ? $("#selectEmpresa").val() : $("#divEmpresa").attr("eid");
	} else {
		alert("Debe seleccionar una empresa.");
		
		return;
	}
	
	var marca = {};
	if ($("#selectMarca").length > 0 && $("#selectMarca").val() != 0) {
		marca.id = $("#selectMarca").length > 0 ? $("#selectMarca").val() : $("#divMarca").attr("mid");
	} else {
		alert("Debe seleccionar una marca.");
		
		return;
	}
	
	var modelo = {};
	if ($("#selectModelo").length > 0 && $("#selectModelo").val() != 0) {
		modelo.id = $("#selectModelo").length > 0 ? $("#selectModelo").val() : $("#divModelo").attr("mid");
	} else {
		alert("Debe seleccionar un modelo.");
		
		return;
	}
	
	var moneda = {};
	if ($("#selectMoneda").length > 0 && $("#selectMoneda").val() != 0) {
		moneda.id = $("#selectMoneda").length > 0 ? $("#selectMoneda").val() : $("#divMoneda").attr("mid");
	} else {
		alert("Debe seleccionar una moneda.");
		
		return;
	}
	
	var precio = {
		empresa: empresa,
		marca: marca,
		modelo: modelo,
		moneda: moneda,
		precio: $("#inputPrecio").val()
	};
	
	if (id != null) {
		precio.id = id;
		
		PrecioDWR.update(
			precio,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		PrecioDWR.add(	
			precio,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}