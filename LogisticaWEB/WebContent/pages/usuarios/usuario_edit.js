$(document).ready(function() {
	refinarForm();
	
	$("#divEliminarUsuario").hide();
	$("#inputUsuarioContrasena").prop("disabled", true);
	$("#inputCambiarContrasena").prop("checked", false);
	
	EmpresaDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>"
					+ " <option value='*'>Todas</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectEmpresa").append(html);
			}, async: false
		}
	);
	
	RolDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectRol").append(html);
			}, async: false
		}
	);
	
	if (id != null) {
		UsuarioDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputUsuarioLogin").val(data.login);
					$("#inputUsuarioNombre").val(data.nombre);
					
					if (data.usuarioRolEmpresas.length == 0) {
						if ($("#selectEmpresa").length > 0) {
							$("#selectEmpresa").val(0); 
						}
					} else if (data.usuarioRolEmpresas.length == 1) {
						if ($("#selectEmpresa").length > 0) { 
							$("#selectEmpresa").val(data.usuarioRolEmpresas[0].empresa.id); 
						} else {
							$("#divEmpresa").attr("eid", data.usuarioRolEmpresas[0].empresa.id);
							$("#divEmpresa").html(data.usuarioRolEmpresas[0].empresa.nombre);
						}
						
						if ($("#selectRol").length > 0) { 
							$("#selectRol").val(data.usuarioRolEmpresas[0].rol.id);
						} else {
							$("#divRol").attr("rid", data.usuarioRolEmpresas[0].rol.id);
							$("#divRol").html(data.usuarioRolEmpresas[0].rol.nombre);
						}
					} else if (data.usuarioRolEmpresas.length > 1) {
						if ($("#selectEmpresa").length > 0) { 
							$("#selectEmpresa").val("*");
						} else {
							$("#divEmpresa").append("<div id='t' style='float: left;'>Todas</div>");
							
							for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
								$("#divEmpresa").append("<div style='display: none;' eid='" + data.usuarioRolEmpresas[i].empresa.id + "'></div>");
							}
						}
						
						if ($("#selectRol").length > 0) { 
							$("#selectRol").val(data.usuarioRolEmpresas[0].rol.id);
						} else {
							$("#divRol").attr("rid", data.usuarioRolEmpresas[0].rol.id);
							$("#divRol").html(data.usuarioRolEmpresas[0].rol.nombre);
						}
					}
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarUsuario").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
					}
				}, async: false
			}
		);
	} else {
		$("#inputUsuarioContrasena").prop("disabled", false);
		$("#inputCambiarContrasena").prop("checked", true);
	}
});

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		$("#divEmpresa").html("&nbsp;");
		$("#divRol").html("&nbsp;");
	}
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
		login: $("#inputUsuarioLogin").val(),
		nombre: $("#inputUsuarioNombre").val(),
		usuarioRolEmpresas: []
	};
	
	if ($("#selectEmpresa").length > 0 && $("#selectEmpresa").val() != 0 && $("#selectEmpresa").val() != "*") {
		usuario.usuarioRolEmpresas = [{
			usuario: { id: id },
			rol: { 
				id: $("#selectRol").length > 0 ? $("#selectRol").val() : $("#divRol").attr("rid")
			},
			empresa: { 
				id: $("#selectEmpresa").length > 0 ? $("#selectEmpresa").val() : $("#divEmpresa").attr("eid")
			}
		}];
	} else if ($("#selectEmpresa").length > 0 && $("#selectEmpresa").val() == "*") {
		var options = $("#selectEmpresa > option");
		
		for (var i=2; i<options.length; i++) {
			usuario.usuarioRolEmpresas[usuario.usuarioRolEmpresas.length] = {
				usuario: { id: id },
				rol: {
					id: $("#selectRol").length > 0 ? $("#selectRol").val() : $("#divRol").attr("rid")
				},
				empresa: {
					id: $(options[i]).val()
				}
			};
		}
	} else if ($("#selectEmpresa").length == 0) {
		var empresas = $("#divEmpresa > div");
		
		for (var i=1; i<empresas.length; i++) {
			usuario.usuarioRolEmpresas[usuario.usuarioRolEmpresas.length] = {
				usuario: { id: id },
				rol: {
					id: $("#selectRol").length > 0 ? $("#selectRol").val() : $("#divRol").attr("rid")
				},
				empresa: {
					id: $(empresas[i]).attr("eid")
				}
			};
		}
	}
	
	if (id != null) {
		usuario.id = id;
		
		if ($("#inputCambiarContrasena").prop("checked")) {
			usuario.contrasena = $("#inputUsuarioContrasena").val();
		}
		
		UsuarioDWR.update(
			usuario,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		usuario.contrasena = $("#inputUsuarioContrasena").val();
		
		UsuarioDWR.add(
			usuario,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarUsuario").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Usuario")) {
		var usuario = {
			id: id
		};
		
		UsuarioDWR.remove(
			usuario,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}