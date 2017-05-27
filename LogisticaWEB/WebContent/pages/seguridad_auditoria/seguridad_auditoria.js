var __ROL_ADMINISTRADOR = 1;

var grid = null;

$(document).ready(function() {
	$("#divButtonNew").hide();
	
	grid = new Grid(
		document.getElementById("divTableSeguridadAuditoria"),
		{
			tdFecha: { campo: "fecha", descripcion: "Fecha", abreviacion: "Fecha", tipo: __TIPO_CAMPO_FECHA_HORA },
			tdUsuario: { campo: "usuario.nombre", clave: "usuario.id", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listUsuarios, clave: "id", valor: "nombre" }, ancho: 200 },
			tdSeguridadTipoEvento: { campo: "seguridadTipoEvento.descripcion", clave: "seguridadTipoEvento.id", descripcion: "Fecha", abreviacion: "Fecha", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listSeguridadTipoEventos, clave: "id", valor: "descripcion" } }
		}, 
		false,
		reloadData,
		trSeguridadAuditoriaOnClick
	);
	
	grid.rebuild();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR) {
						mode = __FORM_MODE_ADMIN;
						
						break;
					}
				}
			}, async: false
		}
	);
	
	var hoy = new Date();
	var manana = new Date();
	manana.setDate(hoy.getDate() + 1);
	
	$("#inputFechaDesde").val(formatShortDate(hoy));
	$("#inputFechaHasta").val(formatShortDate(manana));
	
	reloadData();
});

function listUsuarios() {
	var result = [];
	
	UsuarioDWR.list(
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

function listSeguridadTipoEventos() {
	var result = [];
	
	SeguridadTipoEventoDWR.list(
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

function inputFechaDesdeOnChange() {
	if ($("#inputFechaDesde").val() != null && $("#inputFechaDesde").val() != "") {
		reloadData();
	}
}

function inputFechaHastaOnChange() {
	if ($("#inputFechaHasta").val() != null && $("#inputFechaHasta").val() != "") {
		reloadData();
	}
}

function reloadData() {
	SeguridadAuditoriaDWR.list(
		parseShortDate($("#inputFechaDesde").val()),
		parseShortDate($("#inputFechaHasta").val()),
		{
			callback: function(data) {
				var registros = {
					cantidadRegistros: data.length,
					registrosMuestra: []
				};
				
				var ordered = data;
				
				var ordenaciones = grid.filtroDinamico.calcularOrdenaciones();
				if (ordenaciones.length > 0 && data != null) {
					ordered = data.sort(function(a, b) {
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
				
				for (var i=0; i<ordered.length; i++) {
					registros.registrosMuestra[registros.registrosMuestra.length] = {
						id: ordered[i].id,
						fecha: ordered[i].fecha,
						seguridadTipoEvento: ordered[i].seguridadTipoEvento,
						usuario: ordered[i].usuario
					};
				}
				
				grid.reload(registros);
			}, async: false
		}
	);
}

function trSeguridadAuditoriaOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_NEW;
}

function inputActualizarOnClick() {
	reloadData();
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}