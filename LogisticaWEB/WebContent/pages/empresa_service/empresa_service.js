var __ROL_ADMINISTRADOR = 1;

var grid = null;

$(document).ready(function() {
	$("#divButtonNew").hide();
	
	grid = new Grid(
		document.getElementById("divTableEmpresaServices"),
		{
			tdEmpresaServiceNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 200 } 
		}, 
		false,
		reloadData,
		trEmpresaServiceOnClick
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
	
	$("#divIFrameEmpresaService").draggable();
});

function reloadData() {
	EmpresaServiceDWR.list(
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
						nombre: ordered[i].nombre,
						direccion: ordered[i].direccion,
						telefono: ordered[i].telefono,
						fechaBaja: ordered[i].fechaBaja,
						uact: ordered[i].uact,
						fact: ordered[i].fact,
						term: ordered[i].term
					};
				}
				
				grid.reload(registros);
			}, async: false
		}
	);
}

function trEmpresaServiceOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_NEW;
	
	document.getElementById("iFrameEmpresaService").src = "./empresa_service_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameEmpresaService"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameEmpresaService").src = "./empresa_service_edit.jsp";
	showPopUp(document.getElementById("divIFrameEmpresaService"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}