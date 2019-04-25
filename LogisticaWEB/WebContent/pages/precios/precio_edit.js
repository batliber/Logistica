$(document).ready(init);

function init() {
	refinarForm();
	
	UsuarioRolEmpresaDWR.listEmpresasByContext(
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
	
	TipoProductoDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectTipoProducto").append(html);
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
		$("#selectTipoProducto").prop("disabled", true);
		$("#selectMarca").prop("disabled", true);
		$("#selectModelo").prop("disabled", true);
		$("#selectMoneda").prop("disabled", true);
		$("#inputCuotas").prop("disabled", true);
		
		PrecioDWR.getById(
			id,
			{
				callback: function(data) {
					$("#selectModelo").append(
						"<option id='" + data.modelo.id + "' value='" + data.modelo.id + "'>" + data.modelo.descripcion + "</option>"
					);
					
					$("#selectEmpresa").val(data.empresa.id);
					$("#selectTipoProducto").val(data.tipoProducto.id);
					$("#selectMarca").val(data.marca.id);
					$("#selectModelo").val(data.modelo.id);
					$("#selectMoneda").val(data.moneda.id);
					$("#inputCuotas").val(data.cuotas);
					$("#inputPrecio").val(data.precio);
					
					if (mode == __FORM_MODE_ADMIN) {
						
					} else {
						
					}
				}, async: false
			}
		);
	}
}

function refinarForm() {
	
}

function selectTipoProductoOnChange() {
	return false;
}

function selectMarcaOnChange() {
	$("#selectModelo > option:gt(0)").remove();
	
	ModeloDWR.listByMarcaId(
		$("#selectMarca").val(), 
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectModelo").html(html);
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
	
	var tipoProducto = {};
	if ($("#selectTipoProducto").length > 0 && $("#selectTipoProducto").val() != 0) {
		tipoProducto.id = $("#selectTipoProducto").length > 0 ? $("#selectTipoProducto").val() : $("#divTipoProducto").attr("tpid");
	} else {
		alert("Debe seleccionar un tipo.");
		
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
	
	var valorCuotas = $("#inputCuotas").val();
	if (valorCuotas == null || valorCuotas == "") {
		alert("Debe ingresar una cantidad de cuotas válida.");
		
		return;
	}
	
	var valorPrecio = $("#inputPrecio").val();
	if (valorPrecio == null || valorPrecio == "") {
		alert("Debe ingresar un precio válido.");
		
		return;
	}
	
	var precio = {
		empresa: empresa,
		tipoProducto: tipoProducto,
		marca: marca,
		modelo: modelo,
		moneda: moneda,
		cuotas: valorCuotas,
		precio: valorPrecio
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