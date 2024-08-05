$(document).ready(init)

function init() {
	refinarForm();
	
	$("#divEliminarModalidadVenta").hide();
	
	if (id != null) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ModalidadVentaREST/getById/" + id
		}).then(function(data) { 
			$("#inputModalidadVentaDescripcion").val(data.descripcion);
			
			if (mode == __FORM_MODE_ADMIN) {
				$("#divEliminarModalidadVenta").show();
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
	var modalidadVenta = {
		descripcion: $("#inputModalidadVentaDescripcion").val()
	};
	
	if (modalidadVenta.descripcion == "") {
		alert("Debe ingresar una descripción.");
		
		return;
	}
	
	if (id != null) {
		modalidadVenta.id = id;
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ModalidadVentaREST/update",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(modalidadVenta)
		}).then(function(data) {
			alert("Operación exitosa");
		});
	} else {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ModalidadVentaREST/add",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(modalidadVenta)
		}).then(function(data) {
			if (data != null) {
				alert("Operación exitosa");
			
				$("#inputEliminarModalidadVenta").prop("disabled", false);
			} else {
				alert("Error en la operación");
			}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará la Modalidad de Venta")) {
		var modalidadVenta = {
			id: id
		};
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ModalidadVentaREST/remove",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(modalidadVenta)
		}).then(function(data) { 
			alert("Operación exitosa");
		});
	}
}