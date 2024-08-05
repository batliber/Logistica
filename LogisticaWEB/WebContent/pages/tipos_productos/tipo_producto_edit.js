$(document).ready(init)

function init() {
	refinarForm();
	
	$("#divEliminarTipoProducto").hide();
	
	if (id != null) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/TipoProductoREST/getById/" + id
		}).then(function(data) { 
			$("#inputTipoProductoDescripcion").val(data.descripcion);
			
			if (mode == __FORM_MODE_ADMIN) {
				$("#divEliminarTipoProducto").show();
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
			}
		});
	}
}

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function inputGuardarOnClick(event) {
	var tipoProducto = {
		descripcion: $("#inputTipoProductoDescripcion").val()
	};
	
	if (tipoProducto.descripcion == "") {
		alert("Debe ingresar una descripción.");
		
		return;
	}
	
	if (id != null) {
		tipoProducto.id = id;
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/TipoProductoREST/update",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(tipoProducto)
		}).then(function(data) {
			alert("Operación exitosa");
		});
	} else {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/TipoProductoREST/add",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(tipoProducto)
		}).then(function(data) {
			if (data != null) {
				alert("Operación exitosa");
			
				$("#inputEliminarTipoProducto").prop("disabled", false);
			} else {
				alert("Error en la operación");
			}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Tipo de producto")) {
		var tipoProducto = {
			id: id
		};
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/TipoProductoREST/remove",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(tipoProducto)
		}).then(function(data) { 
			alert("Operación exitosa");
		});
	}
}