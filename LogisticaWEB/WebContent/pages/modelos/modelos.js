var __ROL_ADMINISTRADOR = 1;
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
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_MAESTROS_RIVERGREEN) {
						mode = __FORM_MODE_ADMIN;
						
						$("#divButtonNew").show();
						
						grid = new Grid(
							document.getElementById("divTableModelos"),
							{
								tdModeloMarca: { campo: "marca.nombre", clave: "marca.id", descripcion: "Marca", abreviacion: "Marca", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMarcas, clave: "id", valor: "nombre" }, ancho: 150 },
								tdModeloDescripcion: { campo: "descripcion", descripcion: "Descripción", abreviacion: "Descripción", tipo: __TIPO_CAMPO_STRING, ancho: 250 },
								tdModeloEmpresaService: { campo: "empresaService.nombre", clave: "empresaService.id", descripcion: "Service", abreviacion: "Service", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresaServices, clave: "id", valor: "nombre" }, ancho: 200 },
								tdModeloFechaBaja: { campo: "fechaBaja", descripcion: "Eliminado", abreviacion: "Eliminado", tipo: __TIPO_CAMPO_FECHA_HORA }
							}, 
							true,
							reloadData,
							trModeloOnClick
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
	
	$("#divIFrameModelo").draggable();
}

function listMarcas() {
	var result = [];
	
	MarcaDWR.list(
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

function listEmpresaServices() {
	var result = [];
	
	EmpresaServiceDWR.list(
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
	
	ModeloDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}, async: false
		}
	);
	
	ModeloDWR.countContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
}

function trModeloOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameModelo").src = "./modelo_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameModelo"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameModelo").src = "./modelo_edit.jsp";
	showPopUp(document.getElementById("divIFrameModelo"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}