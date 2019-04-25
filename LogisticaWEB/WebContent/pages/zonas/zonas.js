var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_DISTRIBUCION = 7;
var __ROL_MAESTROS_RIVERGREEN = 20;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonNew").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_DISTRIBUCION
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_MAESTROS_RIVERGREEN) {
						mode = __FORM_MODE_ADMIN;
						$("#divButtonNew").show();
						
						grid = new Grid(
							document.getElementById("divTableZonas"),
							{
								tdZonaNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 200 },
								tdZonaDetalle: { campo: "detalle", descripcion: "Detalle", abreviacion: "Detalle", tipo: __TIPO_CAMPO_STRING, ancho: 200 },
								tdZonaDepartamento: { campo: "departamento.nombre", clave: "departamento.id", descripcion: "Departamento", abreviacion: "Departamento", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre" } } 
							}, 
							true,
							reloadData,
							trZonaOnClick
						);
						
						grid.rebuild();
							
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						
						break;
					}
				}
			}, async: false
		}
	);
	
	reloadData();
	
	$("#divIFrameZona").draggable();
}

function listDepartamentos() {
	var result = [];
	
	DepartamentoDWR.list(
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
	
	ZonaDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}, async: false
		}
	);
	
	ZonaDWR.countContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
}

function trZonaOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_NEW;
	
	document.getElementById("iFrameZona").src = "./zona_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameZona"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameZona").src = "./zona_edit.jsp";
	showPopUp(document.getElementById("divIFrameZona"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}