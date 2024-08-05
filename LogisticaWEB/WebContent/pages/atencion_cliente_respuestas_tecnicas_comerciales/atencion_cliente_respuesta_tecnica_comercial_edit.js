$(document).ready(init)

function init() {
	refinarForm();
	
	$("#divEliminarAtencionClienteRespuestaTecnicaComercial").hide();
	
	if (id != null) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/AtencionClienteRespuestaTecnicaComercialREST/getById/" + id
		}).then(function(data) { 
			$("#inputAtencionClienteRespuestaTecnicaComercialDescripcion").val(data.descripcion);
			
			if (mode == __FORM_MODE_ADMIN) {
				$("#divEliminarAtencionClienteRespuestaTecnicaComercial").show();
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
	var atencionClienteRespuestaTecnicaComercial = {
		descripcion: $("#inputAtencionClienteRespuestaTecnicaComercialDescripcion").val()
	};
	
	if (atencionClienteRespuestaTecnicaComercial.descripcion == "") {
		alert("Debe ingresar una descripción.");
		
		return;
	}
	
	if (id != null) {
		atencionClienteRespuestaTecnicaComercial.id = id;
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/AtencionClienteRespuestaTecnicaComercialREST/update",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(atencionClienteRespuestaTecnicaComercial)
		}).then(function(data) {
			alert("Operación exitosa");
		});
	} else {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/AtencionClienteRespuestaTecnicaComercialREST/add",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(atencionClienteRespuestaTecnicaComercial)
		}).then(function(data) {
			if (data != null) {
				alert("Operación exitosa");
			
				$("#inputEliminarAtencionClienteRespuestaTecnicaComercial").prop("disabled", false);
			} else {
				alert("Error en la operación");
			}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará la Respuesta técnica/comercial")) {
		var atencionClienteRespuestaTecnicaComercial = {
			id: id
		};
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/AtencionClienteRespuestaTecnicaComercialREST/remove",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(atencionClienteRespuestaTecnicaComercial)
		}).then(function(data) { 
			alert("Operación exitosa");
		});
	}
}