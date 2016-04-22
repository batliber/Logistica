var __ROL_ADMINISTRADOR = 1;

var mode = __FORM_MODE_USER;

var grid = null;

$(document).ready(function() {
	$("#divButtonNew").hide();
	
	grid = new Grid(
		document.getElementById("divTableUsuarios"),
		{
			tdUsuarioLogin: { campo: "login", descripcion: "Login", abreviacion: "Login", tipo: __TIPO_CAMPO_STRING },
			tdUsuarioNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 200 },
			tdUsuarioRol: { campo: "rolNombre", descripcion: "Rol", abreviacion: "Rol", tipo: __TIPO_CAMPO_STRING, ancho: 300 }, 
			tdUsuarioEmpresa: { campo: "empresaNombre", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_STRING, ancho: 300 }
		}, 
		reloadData,
		trUsuarioOnClick
	);
	
	grid.rebuild();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR) {
						mode = __FORM_MODE_ADMIN;
						
						$("#divButtonNew").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						
						break;
					}
				}
			}, async: false
		}
	);
	
	reloadData();
	
	$("#divIFrameUsuario").draggable();
});

function reloadData() {
	UsuarioDWR.list(
		{
			callback: function(data) {
				var registros = {
					cantidadRegistros: data.length,
					registrosMuestra: []
				};
				
				for (var i=0; i<data.length; i++) {
					var todas = false;
					var empresaId = 0;
					var empresaNombre = "";
					var rolNombre = "";
					for (var j=0; j<data[i].usuarioRolEmpresas.length; j++) {
						if (empresaId == 0) {
							empresaId = data[i].usuarioRolEmpresas[j].empresa.id;
							empresaNombre += data[i].usuarioRolEmpresas[j].empresa.nombre;
							rolNombre += data[i].usuarioRolEmpresas[j].rol.nombre;
						} else {
							if (empresaNombre.indexOf(data[i].usuarioRolEmpresas[j].empresa.nombre) < 0) {
								empresaNombre += ", " + data[i].usuarioRolEmpresas[j].empresa.nombre;
							}
							if (rolNombre.indexOf(data[i].usuarioRolEmpresas[j].rol.nombre) < 0) {
								rolNombre += ", " + data[i].usuarioRolEmpresas[j].rol.nombre;
							}
						}
						
//						if (data[i].usuarioRolEmpresas[j].empresa.id != empresaId) {
//							todas = true;
//							break;
//						}
					}
					
					registros.registrosMuestra[registros.registrosMuestra.length] = {
						id: data[i].id,
						login: data[i].login,
						contrasena: data[i].contrasena,
						nombre: data[i].nombre,
						fechaBaja: data[i].fechaBaja,
						rolNombre: rolNombre,
						empresaNombre: (todas ? "Todas" : empresaNombre),
						uact: data[i].uact,
						fact: data[i].fact,
						term: data[i].term
					};
				}
				
				var ordered = registros.registrosMuestra;
				
				var ordenaciones = grid.filtroDinamico.calcularOrdenaciones();
				if (ordenaciones.length > 0 && data != null) {
					ordered = registros.registrosMuestra.sort(function(a, b) {
						var result = 0;
						
						for (var i=0; i<ordenaciones.length; i++) {
							var aValue = null;
							try {
								aValue = eval("a." + ordenaciones[i].campo);
						    } catch(e) {
						        aValue = null;
						    }
						    
						    var bValue = null;
						    try {
								bValue = eval("b." + ordenaciones[i].campo);
						    } catch(e) {
						        bValue = null;
						    }
							
							if (aValue < bValue) {
								result = -1 * (ordenaciones[i].ascendente ? 1 : -1);
								
								break;
							} else if (aValue > bValue) {
								result = 1 * (ordenaciones[i].ascendente ? 1 : -1);
								
								break;
							}
						}
						
						return result;
					});
				}
				
				registros.registrosMuestra = ordered;
				
				grid.reload(registros);
			}, async: false
		}
	);
}

function trUsuarioOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	document.getElementById("iFrameUsuario").src = "./usuario_edit.jsp?m=" + mode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameUsuario"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameUsuario").src = "./usuario_edit.jsp";
	showPopUp(document.getElementById("divIFrameUsuario"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}