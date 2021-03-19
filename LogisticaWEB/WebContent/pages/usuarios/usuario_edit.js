var gridEmpresas = null;
var gridRoles = null;

var empresas = {
	cantidadRegistros: 0,
	registrosMuestra: []
};

var roles = {
	cantidadRegistros: 0,
	registrosMuestra: []
};

$(document).ready(init);

function init() {
	refinarForm();
	
	$("#divEliminarUsuario").hide();
	$("#inputUsuarioContrasena").prop("disabled", true);
	$("#inputCambiarContrasena").prop("checked", false);
	
	initGridEmpresas();
	initGridRoles();
	
	initPuntosVenta()
		.then(initEmpresas)
		.then(initRoles)
		.then(function(odata) {
			if (id != null) {
				$.ajax({
			        url: "/LogisticaWEB/RESTFacade/RecargaPuntoVentaUsuarioREST/getPuntoVentaByUsuarioId/" + id
			   	}).then(function(data) {
					if (data != null) {
						$("#selectUsuarioPuntoVenta").val(data.id);
					}
				}).then(function(data) {
					$.ajax({
				        url: "/LogisticaWEB/RESTFacade/UsuarioREST/getById/" + id
				    }).then(function(data) {
						$("#inputUsuarioLogin").val(data.login);
						$("#inputUsuarioNombre").val(data.nombre);
						$("#inputUsuarioDocumento").val(data.documento);
						$("#inputUsuarioBloqueado").prop("checked", data.bloqueado);
						
						for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
							var usuarioRolEmpresa = data.usuarioRolEmpresas[i];
							
							var found = false;
							for (var j=0; j<empresas.registrosMuestra.length; j++) {
								if (empresas.registrosMuestra[j].id == usuarioRolEmpresa.empresa.id) {
									found = true;
								}
							}
							
							if (!found) {
								empresas.registrosMuestra[empresas.registrosMuestra.length] = usuarioRolEmpresa.empresa;
								empresas.cantidadRegistros++;
							}
							
							found = false;
							for (var j=0; j<roles.registrosMuestra.length; j++) {
								if (roles.registrosMuestra[j].id == usuarioRolEmpresa.rol.id) {
									found = true;
								}
							}
							
							if (!found) {
								roles.registrosMuestra[roles.registrosMuestra.length] = usuarioRolEmpresa.rol;
								roles.cantidadRegistros++;
							}
						}
						
						reloadEmpresas();
						reloadRoles();
					
						if (mode == __FORM_MODE_ADMIN) {
							$("#divEliminarUsuario").show();
							$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						} else {
							
						}
					});
				});
			} else {
				$("#inputUsuarioContrasena").prop("disabled", false);
				$("#inputCambiarContrasena").prop("checked", true);
			}
		});
}

function initGridEmpresas() {
	gridEmpresas = new Grid(
		document.getElementById("divTableEmpresas"),
		{
			tdNombre: { campo: "nombre", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_STRING, ancho: 166 } 
		}, 
		false,
		reloadEmpresas,
		trEmpresasOnClick,
		null,
		17
	);
	
	gridEmpresas.rebuild();
}

function initGridRoles() {
	gridRoles = new Grid(
		document.getElementById("divTableRoles"),
		{
			tdNombre: { campo: "nombre", descripcion: "Rol", abreviacion: "Rol", tipo: __TIPO_CAMPO_STRING, ancho: 169 } 
		}, 
		false,
		reloadRoles,
		trRolesOnClick,
		null,
		17
	);
	
	gridRoles.rebuild();
}

function initPuntosVenta() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/listMinimal"
	}).then(function(data) {
		fillSelect(
	    	"selectUsuarioPuntoVenta", 
	    	data,
	   		"id", 
	   		"nombre"
	   	);
	}); 
}

function initEmpresas() {
    return $.ajax({
        url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
    }).then(function(data) {
	    fillSelect(
	    	"selectEmpresa", 
	    	data,
	   		"id", 
	   		"nombre"
	   	);
	});
}

function initRoles() {
	return $.ajax({
        url: "/LogisticaWEB/RESTFacade/RolREST/listMinimal"
	}).then(function(data) {
	  	fillSelect(
	       	"selectRol", 
	       	data,
	       	"id", 
	       	"nombre"
	       );
	});
}

function reloadEmpresas() {
	gridEmpresas.reload(empresas);
}

function reloadRoles() {
	gridRoles.reload(roles);
}

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		$("#divEmpresa").html("&nbsp;");
		$("#divRol").html("&nbsp;");
	}
}

function inputAgregarEmpresaOnClick(event, element) {
	var empresaId = $("#selectEmpresa").val();
	if (empresaId != 0) {
		var found = false;
		for (i=0; i<empresas.registrosMuestra.length; i++) {
			if (empresas.registrosMuestra[i].id == empresaId) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			$.ajax({
		        url: "/LogisticaWEB/RESTFacade/EmpresaREST/getById/" + empresaId
		    }).then(function(data) {
				empresas.cantidadRegistros = empresas.cantidadRegistros + 1;
				empresas.registrosMuestra[empresas.registrosMuestra.length] = data;
				
				reloadEmpresas();
			});
		}
	} else {
		alert("Debe seleccionar una empresa.");
	}
}

function inputAgregarRolOnClick(event, element) {
	var rolId = $("#selectRol").val();
	if (rolId != 0) {
		var found = false;
		for (i=0; i<roles.registrosMuestra.length; i++) {
			if (roles.registrosMuestra[i].id == rolId) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			$.ajax({
		        url: "/LogisticaWEB/RESTFacade/RolREST/getById/" + rolId
		    }).then(function(data) {
				roles.cantidadRegistros = roles.cantidadRegistros + 1;
				roles.registrosMuestra[roles.registrosMuestra.length] = data;
				
				reloadRoles();
			});
		}
	} else {
		alert("Debe seleccionar un rol.");
	}
}

function trEmpresasOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var id = $(target).attr("id");
	
	var i=0;
	for (i=0; i<empresas.registrosMuestra.length; i++) {
		if (empresas.registrosMuestra[i].id == id) {
			break;
		}
	}
	
	empresas.cantidadRegistros = empresas.cantidadRegistros - 1;
	empresas.registrosMuestra.splice(i, 1);
	
	reloadEmpresas();
}

function trRolesOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var id = $(target).attr("id");
	
	var i=0;
	for (i=0; i<roles.registrosMuestra.length; i++) {
		if (roles.registrosMuestra[i].id == id) {
			break;
		}
	}
	
	roles.cantidadRegistros = roles.cantidadRegistros - 1;
	roles.registrosMuestra.splice(i, 1);
	
	reloadRoles();
}

function inputCambiarContrasenaOnChange(event, element) {
	if ($("#inputCambiarContrasena").prop("checked")) {
		$("#inputUsuarioContrasena").prop("disabled", false);
	} else {
		$("#inputUsuarioContrasena").prop("disabled", true);
	}
}

function inputGuardarOnClick(event) {
	var usuario = {
		documento: $("#inputUsuarioDocumento").val(),
		login: $("#inputUsuarioLogin").val(),
		nombre: $("#inputUsuarioNombre").val(),
		bloqueado: $("#inputUsuarioBloqueado").prop("checked"),
		
		usuarioRolEmpresas: []
	};
	
	if (usuario.login == null || usuario.login == "") {
		alert("El login no puede estar vacío.");
		return false;
	}
	
	if (usuario.nombre == null || usuario.nombre == "") {
		alert("El nombre no puede estar vacío.");
		return false;
	}
	
	if (usuario.documento == null || usuario.documento == "") {
		alert("El documento no puede estar vacío.");
		return false;
	}
	
	if (empresas.registrosMuestra.length == 0) {
		alert("Debe seleccionar al menos una empresa");
		return false;
	}
	
	if (roles.registrosMuestra.length == 0) {
		alert("Debe seleccionar al menos un rol.");
		return false;
	}
	
	for (var i=0; i<empresas.registrosMuestra.length; i++) {
		for (var j=0; j<roles.registrosMuestra.length; j++) {
			usuario.usuarioRolEmpresas[usuario.usuarioRolEmpresas.length] = {
				usuario: { id: id },
				rol: roles.registrosMuestra[j],
				empresa: empresas.registrosMuestra[i]
			}
		}
	}

	if (id != null) {
		usuario.id = id;
		
		if ($("#inputCambiarContrasena").prop("checked")) {
			usuario.contrasena = $("#inputUsuarioContrasena").val();
			usuario.cambioContrasenaProximoLogin = true;
		}
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/UsuarioREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(usuario)
	    }).then(function(data) {
			if (data != null) {
				var puntoVentaId = $("#selectUsuarioPuntoVenta").val();
						
				var recargaPuntoVentaUsuario = {
					usuario: {
						id: id
					}
				};
				if (puntoVentaId > 0) {
					recargaPuntoVentaUsuario.puntoVenta = {
						id: puntoVentaId
					}
				}
				
				$.ajax({
					url: "/LogisticaWEB/RESTFacade/RecargaPuntoVentaUsuarioREST/update",
				    method: "POST",
				    contentType: 'application/json',
				    data: JSON.stringify(recargaPuntoVentaUsuario)
				}).then(function(data) {
					alert("Operación exitosa");
				});
			} else {
				alert("No se pudo completar la operación.")
			}
		});
	} else {
		usuario.contrasena = $("#inputUsuarioContrasena").val();
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/UsuarioREST/getByLogin/" + usuario.login
	    }).then(function(data) {
			if (data == null) {
				$.ajax({
			        url: "/LogisticaWEB/RESTFacade/UsuarioREST/add",
			        method: "POST",
			        contentType: 'application/json',
			        data: JSON.stringify(usuario)
			    }).then(function(data) {
			    	if (data != null) {
						id = data.id;
						
						var puntoVentaId = $("#selectUsuarioPuntoVenta").val();
						
						var recargaPuntoVentaUsuario = {
							usuario: {
								id: id
							}
						};
						if (puntoVentaId > 0) {
							recargaPuntoVentaUsuario.puntoVenta = {
								id: puntoVentaId
							}
						}
						
						$.ajax({
					        url: "/LogisticaWEB/RESTFacade/RecargaPuntoVentaUsuarioREST/update",
					        method: "POST",
					        contentType: 'application/json',
					        data: JSON.stringify(recargaPuntoVentaUsuario)
					    }).then(function(data) {
							alert("Operación exitosa");
					
			    			$("#inputEliminarUsuario").prop("disabled", false);
						});
			    	} else {
			    		alert("Error en la operación");
			    	}
				});
			} else {
				alert("Ya existe un usuario con ese login.");
			}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Usuario")) {
		var usuario = {
			id: id
		};
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/UsuarioREST/remove",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(usuario)
	    }).then(function(data) {
			alert("Operación exitosa");
		});
	}
}