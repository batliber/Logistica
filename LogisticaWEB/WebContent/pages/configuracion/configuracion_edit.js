$(document).ready(init)

function init() {
	refinarForm();
	
	$("#divEliminarConfiguracion").hide();
	
	if (id != null) {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ConfigurationREST/getById/" + id
	    }).then(function(data) { 
	    	$("#inputConfiguracionClave").val(data.clave);
	    	$("#inputConfiguracionValor").val(data.valor);
			
			if (mode == __FORM_MODE_ADMIN) {
				$("#divEliminarConfiguracion").show();
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
	var configuracion = {
		clave: $("#inputConfiguracionClave").val(),
		valor: $("#inputConfiguracionValor").val()
	};
	
	if (configuracion.clave == "") {
		alert("Debe ingresar una clave.");
		
		return;
	}
	
	if (configuracion.valor == "") {
		alert("Debe ingresar un valor.");
		
		return;
	}
	
	if (id != null) {
		configuracion.id = id;
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ConfigurationREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(configuracion)
	    }).then(function(data) {
	    	alert("Operación exitosa");
	    });
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ConfigurationREST/add",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(configuracion)
	    }).then(function(data) {
	    	if (data != null) {
				alert("Operación exitosa");
	    	
				$("#inputEliminarConfiguracion").prop("disabled", false);
	    	} else {
	    		alert("Error en la operación");
	    	}
	    });
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará la Configuracion")) {
		var configuracion = {
			id: id
		};
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ConfigurationREST/remove",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(configuracion)
	    }).then(function(data) { 
	    	alert("Operación exitosa");
	    });
	}
}