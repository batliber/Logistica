$(document).ready(init);

function init() {
	refinarForm();
	
	$("#divEliminarEmpresaService").hide();
	
	if (id != null) {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/EmpresaServiceREST/getById/" + id
	    }).then(function(data) { 
			$("#inputEmpresaServiceNombre").val(data.nombre);
			$("#inputEmpresaServiceDireccion").val(data.direccion);
			$("#inputEmpresaServiceTelefono").val(data.telefono);
			
			if (mode == __FORM_MODE_ADMIN) {
				$("#divEliminarEmpresaService").show();
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
	var empresaService = {
		nombre: $("#inputEmpresaServiceNombre").val(),
		direccion: $("#inputEmpresaServiceDireccion").val(),
		telefono: $("#inputEmpresaServiceTelefono").val(),
	};
	
	if (id != null) {
		empresaService.id = id;
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/EmpresaServiceREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(empresaService)
	    }).then(function(data) {
	    	alert("Operación exitosa");
	    });
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/EmpresaServiceREST/add",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(empresaService)
	    }).then(function(data) {
	    	if (data != null) {
				alert("Operación exitosa");
	    	
				$("#inputEliminarEmpresaService").prop("disabled", false);
	    	} else {
	    		alert("Error en la operación");
	    	}
	    });
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Service")) {
		var empresaService = {
			id: id
		};
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/EmpresaServiceREST/remove",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(empresaService)
	    }).then(function(data) {
	    	alert("Operación exitosa");
	    });
	}
}