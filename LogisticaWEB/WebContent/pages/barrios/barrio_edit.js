$(document).ready(init);

function init() {
	refinarForm();
	
	$("#divEliminarBarrio").hide();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/DepartamentoREST/list"
    }).then(function(data) { 
		fillSelect(
			"selectDepartamento", 
			data,
			"id", 
			"nombre"
		);
    }).then(function(data) {
    	$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ZonaREST/listMinimal"
	    }).then(function(data) { 
			fillSelect(
				"selectZona", 
				data,
				"id", 
				"nombre"
			);
		});
    }).then(function(data) {
		if (id != null) {
			$.ajax({
		        url: "/LogisticaWEB/RESTFacade/BarrioREST/getById/" + id
		    }).then(function(data) { 
				$("#inputBarrioNombre").val(data.nombre);
				
				if ($("#selectDepartamento").length > 0) { 
					$("#selectDepartamento").val(data.departamento.id);
				} else {
					$("#divDepartamento").attr("did", data.departamento.id);
					$("#divDepartamento").html(data.departamento.nombre);
				}
				
				if ($("#selectZona").length > 0) { 
					$("#selectZona").val(data.zona.id);
				} else {
					$("#divZona").attr("zid", data.zona.id);
					$("#divZona").html(data.zona.nombre);
				}
				
				if (mode == __FORM_MODE_ADMIN) {
					$("#divEliminarBarrio").show();
					$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				}
			});
		}
    });
}

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function selectDepartamentoOnChange() {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ZonaREST/listMinimalByDepartamentoId/" + $("#selectDepartamento").val()
    }).then(function(data) { 
		fillSelect(
			"selectZona",
			data,
			"id",
			"nombre"
		);		
    });
}

function inputGuardarOnClick(event) {
	var barrio = {
		nombre: $("#inputBarrioNombre").val()
	};
	
	if ($("#selectDepartamento").length > 0 && $("#selectDepartamento").val() != 0) {
		barrio.departamento = {
			id: $("#selectDepartamento").length > 0 ? $("#selectDepartamento").val() : $("#divDepartamento").attr("did")
		};
	} else {
		alert("Debe seleccionar un departamento.");
		
		return;
	}
	
	if ($("#selectZona").length > 0 && $("#selectZona").val() != 0) {
		barrio.zona = {
			id: $("#selectZona").length > 0 ? $("#selectZona").val() : $("#divZona").attr("zid"),
			departamento: {
				id: $("#selectDepartamento").length > 0 ? $("#selectDepartamento").val() : $("#divDepartamento").attr("did")
			}
		};
	} else {
		alert("Debe seleccionar una zona.");
		
		return;
	}
	
	if (id != null) {
		barrio.id = id;
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/BarrioREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(barrio)
	    }).then(function(data) {
			alert("Operación exitosa");
		});
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/BarrioREST/add",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(barrio)
	    }).then(function(data) {
	    	if (data != null) {
				alert("Operación exitosa");
				
				$("#inputEliminarBarrio").prop("disabled", false);
	    	} else {
	    		alert("Error en la operación");
	    	}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Barrio")) {
		var barrio = {
			id: id
		};
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/BarrioREST/remove",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(barrio)
	    }).then(function(data) {
			alert("Operación exitosa");
		});
	}
}