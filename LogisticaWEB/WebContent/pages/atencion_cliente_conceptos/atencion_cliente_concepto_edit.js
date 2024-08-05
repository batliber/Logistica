$(document).ready(init)

function init() {
	refinarForm();
	
	$("#divEliminarAtencionClienteConcepto").hide();
	
	if (id != null) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/AtencionClienteConceptoREST/getById/" + id
		}).then(function(data) { 
			$("#inputAtencionClienteConceptoDescripcion").val(data.descripcion);
			
			if (mode == __FORM_MODE_ADMIN) {
				$("#divEliminarAtencionClienteConcepto").show();
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
	var atencionClienteConcepto = {
		descripcion: $("#inputAtencionClienteConceptoDescripcion").val()
	};
	
	if (atencionClienteConcepto.descripcion == "") {
		alert("Debe ingresar una descripción.");
		
		return;
	}
	
	if (id != null) {
		atencionClienteConcepto.id = id;
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/AtencionClienteConceptoREST/update",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(atencionClienteConcepto)
		}).then(function(data) {
			alert("Operación exitosa");
		});
	} else {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/AtencionClienteConceptoREST/add",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(atencionClienteConcepto)
		}).then(function(data) {
			if (data != null) {
				alert("Operación exitosa");
			
				$("#inputEliminarAtencionClienteConcepto").prop("disabled", false);
			} else {
				alert("Error en la operación");
			}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Concepto")) {
		var atencionClienteConcepto = {
			id: id
		};
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/AtencionClienteConceptoREST/remove",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(atencionClienteConcepto)
		}).then(function(data) { 
			alert("Operación exitosa");
		});
	}
}