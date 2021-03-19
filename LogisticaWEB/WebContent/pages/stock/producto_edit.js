$(document).ready(init);

function init() {
	refinarForm();
	
	$("#divEliminarProducto").hide();
	
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
		        url: "/LogisticaWEB/RESTFacade/ProductoREST/getById/" + id
		    }).then(function(data) { 
				$("#inputProductoDescripcion").val(data.descripcion);
				
				if (data.empresaService != null) {
					$("#selectProductoEmpresaService").val(data.empresaService.id);
				}
				
				if (data.marca != null) {
					$("#selectProductoMarca").val(data.marca.id);
				}
				
				if (mode == __FORM_MODE_ADMIN) {
					$("#divEliminarProducto").show();
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
	var producto = {
		descripcion: $("#inputProductoDescripcion").val(),
		marca: {
			id: $("#selectProductoMarca").val()
		},
		empresaService: {
			id: $("#selectProductoEmpresaService").val()
		}
	};
	
	if (producto.descripcion == "") {
		alert("Debe ingresar una descripción.");
		
		return;
	}
	
	if (producto.marca.id == "") {
		alert("Debe seleccionar una marca.");
		
		return;
	}
	
	if (producto.empresaService.id == "0") {
		alert("Debe seleccionar un service");
		
		return;
	}
	
	if (id != null) {
		producto.id = id;
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ProductoREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(producto)
	    }).then(function(data) {
	    	alert("Operación exitosa");
	    });
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ProductoREST/add",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(producto)
	    }).then(function(data) {
	    	if (data != null) {
				alert("Operación exitosa");
	    	
				$("#inputEliminarProducto").prop("disabled", false);
	    	} else {
	    		alert("Error en la operación");
	    	}
	    });
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Equipo")) {
		var producto = {
			id: id
		};
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ProductoREST/remove",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(producto)
	    }).then(function(data) { 
	    	alert("Operación exitosa");
	    });
	}
}