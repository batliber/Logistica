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
	
	MenuDWR.list(
		{
			callback: function(data) {
				var html = 
					"<option value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html +=
						"<option value='" + data[i].id + "'>" + data[i].titulo + "</option>";
				}
				
				$("#selectMenu").html(html);
			}, async: false
		}
	);
	
	RolDWR.list(
		{
			callback: function(data) {
				var html = 
					"<option value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html +=
						"<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectRol").html(html);
			}, async: false
		}
	);
	
	if (id != null) {
		RolDWR.getById(
			id,
			{
				callback: function(data) {
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
				}, async: false
			}
		);
	}
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
			MenuDWR.getById(
				menuId,
				{
					callback: function(data) {
						menus.cantidadRegistros = menus.cantidadRegistros + 1;
						menus.registrosMuestra[menus.registrosMuestra.length] = data;
						
						reloadMenus();
					}, async: false
				}
			);
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
			RolDWR.getById(
				rolId,
				{
					callback: function(data) {
						subordinados.cantidadRegistros = subordinados.cantidadRegistros + 1;
						subordinados.registrosMuestra[subordinados.registrosMuestra.length] = data;
						
						reloadSubordinados();
					}, async: false
				}
			);
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
		
		RolDWR.update(
			rol,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		RolDWR.getByNombre(
			rol.nombre,
			{
				callback: function(data) {
					if (data == null) {
						RolDWR.add(
							rol,
							{
								callback: function(data) {
									alert("Operación exitosa");
									
									$("#inputEliminarRol").prop("disabled", false);
								}, async: false
							}
						);
					} else {
						alert("Ya existe un rol con ese nombre.");
					}
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Rol")) {
		var rol = {
			id: id
		};
		
		RolDWR.remove(
			rol,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}