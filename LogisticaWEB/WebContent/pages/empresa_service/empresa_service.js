var __ROL_ADMINISTRADOR = 1;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonNew").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR) {
						mode = __FORM_MODE_ADMIN;
						
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
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	EmpresaServiceDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}, async: false
		}
	);
	
	EmpresaServiceDWR.countContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
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