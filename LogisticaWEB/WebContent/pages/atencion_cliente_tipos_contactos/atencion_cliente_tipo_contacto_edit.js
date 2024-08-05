$(document).ready(init)

function init() {
	refinarForm();
	
	$("#divEliminarAtencionClienteTipoContacto").hide();
	
	if (id != null) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/AtencionClienteTipoContactoREST/getById/" + id
		}).then(function(data) { 
			$("#inputAtencionClienteTipoContactoDescripcion").val(data.descripcion);
			
			if (mode == __FORM_MODE_ADMIN) {
				$("#divEliminarAtencionClienteTipoContacto").show();
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
	var atencionClienteTipoContacto = {
		descripcion: $("#inputAtencionClienteTipoContactoDescripcion").val()
	};
	
	if (atencionClienteTipoContacto.descripcion == "") {
		alert("Debe ingresar una descripción.");
		
		return;
	}
	
	if (id != null) {
		atencionClienteTipoContacto.id = id;
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/AtencionClienteTipoContactoREST/update",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(atencionClienteTipoContacto)
		}).then(function(data) {
			alert("Operación exitosa");
		});
	} else {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/AtencionClienteTipoContactoREST/add",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(atencionClienteTipoContacto)
		}).then(function(data) {
			if (data != null) {
				alert("Operación exitosa");
			
				$("#inputEliminarAtencionClienteTipoContacto").prop("disabled", false);
			} else {
				alert("Error en la operación");
			}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Tipo de Contacto")) {
		var atencionClienteTipoContacto = {
			id: id
		};
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/AtencionClienteTipoContactoREST/remove",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(atencionClienteTipoContacto)
		}).then(function(data) { 
			alert("Operación exitosa");
		});
	}
}