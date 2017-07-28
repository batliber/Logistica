var __ROL_ADMINISTRADOR= 1;

var mode = __FORM_MODE_USER;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonNew").hide();
	
	grid = new Grid(
		document.getElementById("divTableUsuarios"),
		{
			tdUsuarioLogin: { campo: "login", descripcion: "Login", abreviacion: "Login", tipo: __TIPO_CAMPO_STRING },
			tdUsuarioNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 200 },
			tdUsuarioRol: { campo: "rolNombre", descripcion: "Rol", abreviacion: "Rol", tipo: __TIPO_CAMPO_STRING, ancho: 300 }, 
//			tdUsuarioEmpresa: { campo: "empresaNombre", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_STRING, ancho: 300 }
//			tdUsuarioEmpresa: { campo: "empresa.nombre", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_MULTIPLE, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 300 },
			tdUsuarioEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 300 },
		}, 
		true,
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
}

function listEmpresas() {
	var result = [];
	
	EmpresaDWR.list(
		{
			callback: function(data) {
				if (data != null) {
					result = data;
				}
			}, async: false
		}
	);
	
	return result;
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	UsuarioDWR.listVigentesContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				var registros = {
					registrosMuestra: []
				};
				
				for (var i=0; i<data.registrosMuestra.length; i++) {
					var todas = false;
					var empresaId = 0;
					var empresaNombre = "";
					var rolNombre = "";
					for (var j=0; j<data.registrosMuestra[i].usuarioRolEmpresas.length; j++) {
						if (empresaId == 0) {
							empresaId = data.registrosMuestra[i].usuarioRolEmpresas[j].empresa.id;
							empresaNombre += data.registrosMuestra[i].usuarioRolEmpresas[j].empresa.nombre;
							rolNombre += data.registrosMuestra[i].usuarioRolEmpresas[j].rol.nombre;
						} else {
							if (empresaNombre.indexOf(data.registrosMuestra[i].usuarioRolEmpresas[j].empresa.nombre) < 0) {
								empresaNombre += ", " + data.registrosMuestra[i].usuarioRolEmpresas[j].empresa.nombre;
							}
							if (rolNombre.indexOf(data.registrosMuestra[i].usuarioRolEmpresas[j].rol.nombre) < 0) {
								rolNombre += ", " + data.registrosMuestra[i].usuarioRolEmpresas[j].rol.nombre;
							}
						}
						
//						if (data.registrosMuestra[i].usuarioRolEmpresas[j].empresa.id != empresaId) {
//							todas = true;
//							break;
//						}
					}
					
					registros.registrosMuestra[registros.registrosMuestra.length] = {
						id: data.registrosMuestra[i].id,
						login: data.registrosMuestra[i].login,
						contrasena: data.registrosMuestra[i].contrasena,
						nombre: data.registrosMuestra[i].nombre,
						fechaBaja: data.registrosMuestra[i].fechaBaja,
						rolNombre: rolNombre,
						empresa: { id: 1, nombre: (todas ? "Todas" : empresaNombre) },
						uact: data.registrosMuestra[i].uact,
						fact: data.registrosMuestra[i].fact,
						term: data.registrosMuestra[i].term
					};
				}
				
				grid.reload(registros);
			}
		}
	);
	
	UsuarioDWR.countVigentesContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
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