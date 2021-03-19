$(document).ready(init)

function init() {
	refinarForm();
	
	$("#divEliminarModelo").hide();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/EmpresaServiceREST/list"
    }).then(function(data) {
		fillSelect(
			"selectModeloEmpresaService", 
			data,
			"id", 
			"nombre"
		);
    }).then(function(data) {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/MarcaREST/list"
	    }).then(function(data) { 
			fillSelect(
				"selectModeloMarca", 
				data,
				"id",
				"nombre"
			);
		});
    }).then(function(data) {
		if (id != null) {
			$.ajax({
		        url: "/LogisticaWEB/RESTFacade/ModeloREST/getById/" + id
		    }).then(function(data) {
				$("#inputModeloDescripcion").val(data.descripcion);
				
				if (data.empresaService != null) {
					$("#selectModeloEmpresaService").val(data.empresaService.id);
				}
				
				if (data.marca != null) {
					$("#selectModeloMarca").val(data.marca.id);
				}
				
				if (mode == __FORM_MODE_ADMIN) {
					$("#divEliminarModelo").show();
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

function inputGuardarOnClick(event) {
	var modelo = {
		descripcion: $("#inputModeloDescripcion").val(),
		marca: {
			id: $("#selectModeloMarca").val()
		},
		empresaService: {
			id: $("#selectModeloEmpresaService").val()
		}
	};
	
	if (modelo.descripcion == "") {
		alert("Debe ingresar una descripción.");
		
		return;
	}
	
	if (modelo.marca.id == "") {
		alert("Debe seleccionar una marca.");
		
		return;
	}
	
	if (modelo.empresaService.id == "0") {
		alert("Debe seleccionar un service");
		
		return;
	}
	
	if (id != null) {
		modelo.id = id;
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ModeloREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(modelo)
	    }).then(function(data) {
			alert("Operación exitosa");
		});
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ModeloREST/add",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(modelo)
	    }).then(function(data) {
	    	if (data != null) {
				alert("Operación exitosa");
				
				$("#inputEliminarModelo").prop("disabled", false);
	    	} else {
	    		alert("Error en la operación");
	    	}
	    });
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Modelo")) {
		var modelo = {
			id: id
		};
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ModeloREST/remove",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(modelo)
	    }).then(function(data) {
			alert("Operación exitosa");
		});
	}
}