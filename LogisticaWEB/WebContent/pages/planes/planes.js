var __ROL_ADMINISTRADOR = 1;
var __ROL_PLANES = 22;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonNew").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_PLANES) {
						mode = __FORM_MODE_ADMIN;
						
						$("#divButtonNew").show();
						
						grid = new Grid(
							document.getElementById("divTablePlanes"),
							{
								tdPlanTipoPlan: { campo: "tipoPlan.descripcion", clave: "tipoPlan.id", descripcion: "Tipo de plan", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoPlanes, clave: "id", valor: "descripcion" }, ancho: 250 },
								tdPlanDescripcion: { campo: "descripcion", descripcion: "Descripción", abreviacion: "Descripción", tipo: __TIPO_CAMPO_STRING, ancho: 350 },
								tdPlanFechaBaja: { campo: "fechaBaja", descripcion: "Eliminado", abreviacion: "Eliminado", tipo: __TIPO_CAMPO_FECHA_HORA },
							}, 
							true,
							reloadData,
							trPlanOnClick
						);
						
						grid.rebuild();
						
						grid.filtroDinamico.agregarFiltroManual(
							{
								campo: "fechaBaja",
								operador: "nl",
								valores: []
							}
						);
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						break;
					}
				}
			}, async: false
		}
	);
	
	reloadData();
	
	$("#divIFramePlan").draggable();
}

function listTipoPlanes() {
	var result = [];
	
	TipoPlanDWR.list(
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
	
	PlanDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}, async: false
		}
	);
	
	PlanDWR.countContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
}

function inputMostrarFechaBajaOnClick(event, element) {
	reloadData();
}

function trPlanOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFramePlan").src = "./plan_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFramePlan"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFramePlan").src = "./plan_edit.jsp";
	showPopUp(document.getElementById("divIFramePlan"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}