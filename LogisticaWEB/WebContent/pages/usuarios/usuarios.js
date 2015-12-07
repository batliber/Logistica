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
			tdUsuarioRol: { campo: "rolNombre", descripcion: "Rol", abreviacion: "Rol", tipo: __TIPO_CAMPO_STRING, ancho: 200 }, 
			tdUsuarioEmpresa: { campo: "empresaNombre", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_STRING, ancho: 200 }
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
					registros.registrosMuestra[registros.registrosMuestra.length] = {
						id: data[i].id,
						login: data[i].login,
						contrasena: data[i].contrasena,
						nombre: data[i].nombre,
						fechaBaja: data[i].fechaBaja,
						rolNombre: data[i].usuarioRolEmpresas[0].rol.nombre,
						empresaNombre: (data[i].usuarioRolEmpresas.length == 1 ? data[i].usuarioRolEmpresas[0].empresa.nombre : "Todas"),
						uact: data[i].uact,
						fact: data[i].fact,
						term: data[i].term
					};
				}
				
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