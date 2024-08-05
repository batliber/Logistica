var __ROL_ADMINISTRADOR = 1;
var __ROL_GERENCIA = 41733595;

var mode = __FORM_MODE_USER;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonNew").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_GERENCIA) {
				mode = __FORM_MODE_ADMIN;
				
				$("#divButtonNew").show();
				
				grid = new Grid(
					document.getElementById("divTableEmpresas"),
					{
						tdEmpresaNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 200 },
						tdEmpresaOmitirControlVendidos: { campo: "omitirControlVendidos", descripcion: "Omitir control vendidos", abreviacion: "Omit. Ctrl. Vend.", tipo: __TIPO_CAMPO_BOOLEAN, ancho: 100 }
					}, 
					false,
					reloadData,
					trEmpresaOnClick
				);
				
				grid.rebuild();
					
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				break;
			}
		}
		
		reloadData();
				
		$("#divIFrameEmpresa").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
	}).then(function(data) {
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
			registros.registrosMuestra[registros.registrosMuestra.length] = ordered[i];
		}
		
		grid.reload(registros);
	});
}

function trEmpresaOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	document.getElementById("iFrameEmpresa").src = "./empresa_edit.jsp?m=" + mode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameEmpresa"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameEmpresa").src = "./empresa_edit.jsp";
	showPopUp(document.getElementById("divIFrameEmpresa"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}