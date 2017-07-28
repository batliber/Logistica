$(document).ready(init);

function init() {
	refinarForm();
	
	$("#divEliminarUsuario").hide();
	$("#inputUsuarioContrasena").prop("disabled", true);
	$("#inputCambiarContrasena").prop("checked", false);
	
	EmpresaDWR.list(
		{
			callback: function(data) {
				$("#tableEmpresas > tbody:last > tr").remove();
				
				html = "";
				
				for (var i=0; i<data.length; i++) {
					html += 
						"<tr>"
							+ "<td><div class='divEmpresaNombre'>" + data[i].nombre + "</div></td>"
							+ "<td><div><input type='checkbox' id='" + data[i].id + "'/></div></td>"
						+ "</tr>";
				}
				
				$("#tableEmpresas > tbody:last").append(html);
				
				$("#tableEmpresas > tbody:last > tr:odd").css("background-color", "#F8F8F8");
				$("#tableEmpresas > tbody:last > tr:odd").hover(
					function() {
						$(this).css("background-color", "orange");
					},
					function() {
						$(this).css("background-color", "#F8F8F8");
					}
				);
				$("#tableEmpresas > tbody:last > tr:even").css("background-color", "#FFFFFF");
				$("#tableEmpresas > tbody:last > tr:even").hover(
					function() {
						$(this).css("background-color", "orange");
					},
					function() {
						$(this).css("background-color", "#FFFFFF");
					}
				);
			}, async: false
		}
	);
	
	RolDWR.list(
		{
			callback: function(data) {
				$("#tableRoles > tbody:last > tr").remove();
				
				html = "";
				
				for (var i=0; i<data.length; i++) {
					html += 
						"<tr>"
							+ "<td><div class='divRolNombre'>" + data[i].nombre + "</div></td>"
							+ "<td><div><input type='checkbox' id='" + data[i].id + "'/></div></td>"
						+ "</tr>";
				}
				
				$("#tableRoles > tbody:last").append(html);
				
				$("#tableRoles > tbody:last > tr:odd").css("background-color", "#F8F8F8");
				$("#tableRoles > tbody:last > tr:odd").hover(
					function() {
						$(this).css("background-color", "orange");
					},
					function() {
						$(this).css("background-color", "#F8F8F8");
					}
				);
				$("#tableRoles > tbody:last > tr:even").css("background-color", "#FFFFFF");
				$("#tableRoles > tbody:last > tr:even").hover(
					function() {
						$(this).css("background-color", "orange");
					},
					function() {
						$(this).css("background-color", "#FFFFFF");
					}
				);
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
					$("#inputUsuarioDocumento").val(data.documento);
					
					for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
						$("#tableEmpresas > tbody > tr > td > div > input[id=" + data.usuarioRolEmpresas[i].empresa.id + "]").prop("checked", true);
						$("#tableRoles > tbody > tr > td > div > input[id=" + data.usuarioRolEmpresas[i].rol.id + "]").prop("checked", true);
					}
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#tableEmpresas > tbody > tr > td > div > input").prop("disabled", false);
						$("#tableRoles > tbody > tr > td > div > input").prop("disabled", false);
						
						$("#divEliminarUsuario").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
					} else {
						$("#tableEmpresas > tbody > tr > td > div > input").prop("disabled", true);
						$("#tableRoles > tbody > tr > td > div > input").prop("disabled", true);
					}
				}, async: false
			}
		);
	} else {
		$("#inputUsuarioContrasena").prop("disabled", false);
		$("#inputCambiarContrasena").prop("checked", true);
	}
}

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
		documento: $("#inputUsuarioDocumento").val(),
		login: $("#inputUsuarioLogin").val(),
		nombre: $("#inputUsuarioNombre").val(),
		usuarioRolEmpresas: []
	};
	
	var checksEmpresas = $("#tableEmpresas > tbody > tr > td > div > input");
	for (var i=0; i<checksEmpresas.length; i++) {
		if ($(checksEmpresas[i]).prop("checked")) {
			var checksRoles = $("#tableRoles > tbody > tr > td > div > input");
			for (var j=0; j<checksRoles.length; j++) {
				if ($(checksRoles[j]).prop("checked")) {
					usuario.usuarioRolEmpresas[usuario.usuarioRolEmpresas.length] = {
						usuario: { id: id },
						rol: { 
							id: $(checksRoles[j]).attr("id")
						},
						empresa: { 
							id: $(checksEmpresas[i]).attr("id")
						}
					};
				}
			}
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
		
		UsuarioDWR.getByLogin(
			usuario.login,
			{
				callback: function(data) {
					if (data == null) {
						UsuarioDWR.add(
							usuario,
							{
								callback: function(data) {
									alert("Operación exitosa");
									
									$("#inputEliminarUsuario").prop("disabled", false);
								}, async: false
							}
						);
					} else {
						alert("Ya existe un usuario con ese login.");
					}
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