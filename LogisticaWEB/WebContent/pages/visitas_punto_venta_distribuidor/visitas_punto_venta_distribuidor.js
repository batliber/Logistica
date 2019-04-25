var __ROL_ADMINISTRADOR = 1;
var __ROL_DEMO = 21;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonNew").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
						mode = __FORM_MODE_ADMIN;
						
						grid = new Grid(
							document.getElementById("divTableVisitasPuntoVentaDistribuidor"),
							{
								tdPuntoVenta: { campo: "puntoVenta.nombre", clave: "puntoVenta.id", descripcion: "Punto de venta", abreviacion: "Pto. Venta", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPuntoVentas, clave: "id", valor: "nombre"}, ancho: 300 },
								tdPuntoVentaBarrio: { campo: "puntoVenta.barrio.nombre", clave: "puntoVenta.barrio.id", descripcion: "Barrio", abreviacion: "Barrio", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBarrios, clave: "id", valor: "nombre"}, ancho: 175 },
								tdDistribuidor: { campo: "distribuidor.nombre", clave: "distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidoresChips, clave: "id", valor: "nombre"}, ancho: 200 },
								tdFechaAsignacion: { campo: "fechaAsignacion", descripcion: "Asignado", abreviacion: "Asignado", tipo: __TIPO_CAMPO_FECHA_HORA },
								tdEstado: { campo: "estadoVisitaPuntoVentaDistribuidor.nombre", clave: "estadoVisitaPuntoVentaDistribuidor.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoVisitaPuntoVentaDistribuidores, clave: "id", valor: "nombre"} },
								tdFechaVisita: { campo: "fechaVisita", descripcion: "Visitado", abreviacion: "Visitado", tipo: __TIPO_CAMPO_FECHA_HORA },
								tdObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 300 }
							},
							true,
							reloadData,
							trVisitaPuntoVentaDistribuidorOnClick
						);
						
						grid.rebuild();
						
						$("#divButtonNew").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleTripleSize");
						
						break;
					}
				}
			}, async: false
		}
	);
	
	$("#divIFrameVisitaPuntoVentaDistribuidor").draggable();
	
	reloadData();
}

function listPuntoVentas() {
	var result = [];
	
	PuntoVentaDWR.list(
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

function listBarrios() {
	var result = [];
	
	BarrioDWR.list(
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

function listDistribuidoresChips() {
	var result = [];
	
	UsuarioRolEmpresaDWR.listDistribuidoresChipsByContext(
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

function listEstadoVisitaPuntoVentaDistribuidores() {
	var result = [];
	
	EstadoVisitaPuntoVentaDistribuidorDWR.list(
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
	
	VisitaPuntoVentaDistribuidorDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	VisitaPuntoVentaDistribuidorDWR.countContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
}

function trVisitaPuntoVentaDistribuidorOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameVisitaPuntoVentaDistribuidor").src = "./visita_punto_venta_distribuidor_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameVisitaPuntoVentaDistribuidor"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameVisitaPuntoVentaDistribuidor").src = "./visita_punto_venta_distribuidor_edit.jsp";
	showPopUp(document.getElementById("divIFrameVisitaPuntoVentaDistribuidor"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}

function inputExportarAExcelOnClick(event, element) {
	VisitaPuntoVentaDistribuidorDWR.exportarAExcel(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
				document.getElementById("formExportarAExcel").submit();
			}, async: false
		}
	);
}