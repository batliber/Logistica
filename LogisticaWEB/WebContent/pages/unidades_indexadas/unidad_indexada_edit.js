$(document).ready(init);

function init() {
	refinarForm();
	
	$("#inputUnidadIndexadaVigenciaHasta").prop("disabled", true);
	
	$("#divEliminarUnidadIndexada").hide();
	
	if (id != null) {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/UnidadIndexadaREST/getById/" + id
	    }).then(function(data) { 
			$("#inputUnidadIndexadaVigenciaHasta").val(formatLongDate(data.fechaVigenciaHasta));
			$("#inputUnidadIndexadaValor").val(formatDecimal(data.valor, 4));
			
			$("#inputUnidadIndexadaValor").prop("disabled", true);
			
			if (mode == __FORM_MODE_ADMIN) {
				if (data.fechaVigenciaHasta == null) {
					$("#divEliminarUnidadIndexada").show();
					$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				} else {
					$("#inputGuardar").prop("disabled", true);
				}
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
	var unidadIndexada = {
		valor: $("#inputUnidadIndexadaValor").val()
	};
	
	if (unidadIndexada.valor == "") {
		alert("Debe ingresar un valor.");
		
		return;
	}
	
	if (id != null) {
		unidadIndexada.id = id;
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/UnidadIndexadaREST/add",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(unidadIndexada)
	    }).then(function(data) {
	    	if (data != null) {
				alert("Operación exitosa");
			
				$("#inputEliminarUnidadIndexada").prop("disabled", false);
	    	} else {
	    		alert("Error en la operación");
	    	}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará la cotización")) {
		var unidadIndexada = {
			id: id
		};
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/UnidadIndexadaREST/remove",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(unidadIndexada)
	    }).then(function(data) { 
			alert("Operación exitosa");
		});
	}
}