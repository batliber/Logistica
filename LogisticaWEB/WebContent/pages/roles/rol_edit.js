var gridMenus = null;
var gridSubordinados = null;

var menus = {
	cantidadRegistros: 0,
	registrosMuestra: []
};

var subordinados = {
	cantidadRegistros: 0,
	registrosMuestra: []
};

$(document).ready(init);

function init() {
	gridMenus = new Grid(
		document.getElementById("divTableMenus"),
		{
			tdTitulo: { campo: "titulo", descripcion: "Menu", abreviacion: "Menu", tipo: __TIPO_CAMPO_STRING, ancho: 200 } 
		}, 
		false,
		reloadMenus,
		trMenusOnClick,
		null,
		18
	);
	
	gridMenus.rebuild();
	
	gridSubordinados = new Grid(
		document.getElementById("divTableSubordinados"),
		{
			tdNombre: { campo: "nombre", descripcion: "Subordinado", abreviacion: "Subordinado", tipo: __TIPO_CAMPO_STRING, ancho: 200 } 
		}, 
		false,
		reloadMenus,
		trSubordinadosOnClick,
		null,
		18
	);
			
	gridSubordinados.rebuild();
	
	refinarForm();
	
	$("#divEliminarRol").hide();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/MenuREST/list"
    }).then(function(data) {
		fillSelect(
			"selectMenu", 
			data,
			"id", 
			"titulo"
		);
    }).then(function(data) {
    	$.ajax({
            url: "/LogisticaWEB/RESTFacade/RolREST/listMinimal"
        }).then(function(data) { 
    		fillSelect(
    			"selectRol", 
    			data,
    			"id", 
    			"nombre"
    		);
        }).then(function(data) {
			if (id != null) {
				$.ajax({
			        url: "/LogisticaWEB/RESTFacade/RolREST/getById/" + id
			    }).then(function(data) { 
					$("#inputRolNombre").val(data.nombre);
					
					menus.cantidadRegistros = data.menus.length,
					menus.registrosMuestra = data.menus;
					
					reloadMenus();
					
					subordinados.cantidadRegistros = data.subordinados.length,
					subordinados.registrosMuestra = data.subordinados;
					
					reloadSubordinados();
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#selectMenu").prop("disabled", false);
						
						$("#divEliminarRol").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
					} else {
						$("#selectMenu").prop("disabled", true);
					}
				});
			}
        });
    });
}

function reloadMenus() {
	gridMenus.reload(menus);
}

function reloadSubordinados() {
	gridSubordinados.reload(subordinados);
}

function trMenusOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var id = $(target).attr("id");
	
	var i=0;
	for (i=0; i<menus.registrosMuestra.length; i++) {
		if (menus.registrosMuestra[i].id == id) {
			break;
		}
	}
	
	menus.cantidadRegistros = menus.cantidadRegistros - 1;
	menus.registrosMuestra.splice(i, 1);
	
	reloadMenus();
}

function trSubordinadosOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var id = $(target).attr("id");
	
	var i=0;
	for (i=0; i<subordinados.registrosMuestra.length; i++) {
		if (subordinados.registrosMuestra[i].id == id) {
			break;
		}
	}
	
	subordinados.cantidadRegistros = subordinados.cantidadRegistros - 1;
	subordinados.registrosMuestra.splice(i, 1);
	
	reloadSubordinados();
}

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function inputAgregarMenuOnClick(event, element) {
	var menuId = $("#selectMenu").val();
	if (menuId != 0) {
		var found = false;
		for (i=0; i<menus.registrosMuestra.length; i++) {
			if (menus.registrosMuestra[i].id == menuId) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			$.ajax({
		        url: "/LogisticaWEB/RESTFacade/MenuREST/getById/" + menuId
		    }).then(function(data) {
				menus.cantidadRegistros = menus.cantidadRegistros + 1;
				menus.registrosMuestra[menus.registrosMuestra.length] = data;
				
				reloadMenus();
			});
		}
	} else {
		alert("Debe seleccionar un menú.");
	}
}

function inputAgregarRolOnClick(event, element) {
	var rolId = $("#selectRol").val();
	if (rolId != 0) {
		var found = false;
		for (i=0; i<subordinados.registrosMuestra.length; i++) {
			if (subordinados.registrosMuestra[i].id == rolId) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			$.ajax({
		        url: "/LogisticaWEB/RESTFacade/RolREST/getById/" + rolId
		    }).then(function(data) {
				subordinados.cantidadRegistros = subordinados.cantidadRegistros + 1;
				subordinados.registrosMuestra[subordinados.registrosMuestra.length] = data;
			
				reloadSubordinados();
			});
		}
	} else {
		alert("Debe seleccionar un rol.");
	}
}

function inputGuardarOnClick(event) {
	var rol = {
		nombre: $("#inputRolNombre").val(),
		menus: menus.registrosMuestra,
		subordinados: subordinados.registrosMuestra
	};
	
	if (id != null) {
		rol.id = id;
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/RolREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(rol)
	    }).then(function(data) {
	    	alert("Operación exitosa");
	    });
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/RolREST/getByNombre/" + rol.nombre
	    }).then(function(data) {
			if (data == null) {
				$.ajax({
			        url: "/LogisticaWEB/RESTFacade/RolREST/add",
			        method: "POST",
			        contentType: 'application/json',
			        data: JSON.stringify(rol)
			    }).then(function(data) {
			    	if (data != null) {
						alert("Operación exitosa");
							
						$("#inputEliminarRol").prop("disabled", false);
			    	} else {
			    		alert("Error en la operación");
			    	}
			    });
			} else {
				alert("Ya existe un rol con ese nombre.");
			}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Rol")) {
		var rol = {
			id: id
		};
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/RolREST/remove",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(rol)
	    }).then(function(data) { 
	    	alert("Operación exitosa");
	    });
	}
}