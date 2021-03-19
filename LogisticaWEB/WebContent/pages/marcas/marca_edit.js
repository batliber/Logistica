$(document).ready(init)

function init() {
	refinarForm();
	
	$("#divEliminarMarca").hide();
	
	if (id != null) {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/MarcaREST/getById/" + id
	    }).then(function(data) { 
	    	$("#inputMarcaNombre").val(data.nombre);
			
			if (mode == __FORM_MODE_ADMIN) {
				$("#divEliminarMarca").show();
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
	var marca = {
		nombre: $("#inputMarcaNombre").val()
	};
	
	if (marca.nombre == "") {
		alert("Debe ingresar un nombre.");
		
		return;
	}
	
	if (id != null) {
		marca.id = id;
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/MarcaREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(marca)
	    }).then(function(data) {
	    	alert("Operación exitosa");
	    });
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/MarcaREST/add",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(marca)
	    }).then(function(data) {
	    	if (data != null) {
				alert("Operación exitosa");
	    	
				$("#inputEliminarMarca").prop("disabled", false);
	    	} else {
	    		alert("Error en la operación");
	    	}
	    });
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará la Marca")) {
		var marca = {
			id: id
		};
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/MarcaREST/remove",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(marca)
	    }).then(function(data) { 
	    	alert("Operación exitosa");
	    });
	}
}