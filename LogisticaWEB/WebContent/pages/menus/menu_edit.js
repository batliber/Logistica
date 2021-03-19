$(document).ready(init);

function init() {
	refinarForm();
	
	$("#divEliminarMenu").hide();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/MenuREST/list"
    }).then(function(data) {
		fillSelect(
			"selectMenuPadre", 
			data,
			"id", 
			"titulo"
		);
    }).then(function(data) {
		if (id != null) {
			$.ajax({
		        url: "/LogisticaWEB/RESTFacade/MenuREST/getById/" + id
		    }).then(function(data) { 
				$("#inputMenuTitulo").val(data.titulo);
				$("#inputMenuURL").val(data.url);
				$("#inputMenuOrden").val(data.orden);
				
				if (data.padre != null) {
					$("#selectMenuPadre").val(data.padre);
				}
				
				if (mode == __FORM_MODE_ADMIN) {
					$("#divEliminarMenu").show();
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
	var menu = {
		titulo: $("#inputMenuTitulo").val(),
		url: $("#inputMenuURL").val(),
		orden: $("#inputMenuOrden").val(),
		padre: $("#selectMenuPadre").val()
	};
	
	if (menu.titulo == "") {
		alert("Debe ingresar un título.");
		
		return;
	}
	
	if (menu.url == "") {
		alert("Debe ingresar una URL.");
		
		return;
	}
	
	if (menu.orden == "") {
		alert("Debe ingresar un orden.");
		
		return;
	}
	
	if (menu.padre == 0) {
		menu.padre = null;
	}
	
	if (id != null) {
		menu.id = id;
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/MenuREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(menu)
	    }).then(function(data) {
			alert("Operación exitosa");
		});
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/MenuREST/add",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(menu)
	    }).then(function(data) {
	    	if (data != null) {
				alert("Operación exitosa");
			
				$("#inputEliminarMenu").prop("disabled", false);
	    	} else {
	    		alert("Error en la operación");
	    	}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Menu")) {
		var menu = {
			id: id
		};
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/MenuREST/remove",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(menu)
	    }).then(function(data) {
			alert("Operación exitosa");
		});
	}
}