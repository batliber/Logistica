$(document).ready(init);

function init() {
	refinarForm();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
    }).then(function(data) {
		fillSelect(
			"selectEmpresa", 
			data,
			"id", 
			"nombre"
		);
	}).then(function(data) {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/TipoProductoREST/list"
	    }).then(function(data) { 
			fillSelect(
				"selectTipoProducto", 
				data,
				"id", 
				"descripcion"
			);
		});
	}).then(function(data) {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/MarcaREST/list"
	    }).then(function(data) { 
			fillSelect(
				"selectMarca", 
				data,
				"id",
				"nombre"
			);
		});
	}).then(function(data) {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/MonedaREST/list"
	    }).then(function(data) { 
			fillSelect(
				"selectMoneda", 
				data,
				"id",
				"nombre"
			);
		});
	}).then(function(data) {
		if (id != null) {
			$("#selectEmpresa").prop("disabled", true);
			$("#selectTipoProducto").prop("disabled", true);
			$("#selectMarca").prop("disabled", true);
			$("#selectModelo").prop("disabled", true);
			$("#selectMoneda").prop("disabled", true);
			$("#inputCuotas").prop("disabled", true);
			
			$.ajax({
		        url: "/LogisticaWEB/RESTFacade/PrecioREST/getById/" + id
		    }).then(function(data) {
				$("#selectModelo").append(
					"<option id='" + data.modelo.id + "' value='" + data.modelo.id + "'>" 
						+ data.modelo.descripcion 
					+ "</option>"
				);
				
				$("#selectEmpresa").val(data.empresa.id);
				$("#selectTipoProducto").val(data.tipoProducto.id);
				$("#selectMarca").val(data.marca.id);
				$("#selectModelo").val(data.modelo.id);
				$("#selectMoneda").val(data.moneda.id);
				$("#inputCuotas").val(data.cuotas);
				$("#inputPrecio").val(data.precio);
			});
		}
	});
}

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function selectTipoProductoOnChange() {
	return false;
}

function selectMarcaOnChange() {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ModeloREST/listMinimalByMarcaId/" + $("#selectMarca").val()
    }).then(function(data) { 
		fillSelect(
			"selectModelo",
			data,
			"id",
			"descripcion"
		);		
    });
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
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/PrecioREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(precio)
	    }).then(function(data) {
			alert("Operación exitosa");
		});
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/PrecioREST/add",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(precio)
	    }).then(function(data) {
	    	if (data != null) {
				alert("Operación exitosa");
			} else {
	    		alert("Error en la operación");
	    	}
	    });
	}
}