var __ROL_ADMINISTRADOR = 1;
var __ROL_ENCARGADO_ACTIVACIONES = 12;

var grid = null;
		
$(document).ready(init)

function init() {
	$("#divButtonNuevo").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_ENCARGADO_ACTIVACIONES) {
						$("#divButtonNuevo").show();
						
						grid = new Grid(
							document.getElementById("divTableActivacionesSublotes"),
							{
								tdNumero: { campo: "numero", descripcion: "Número", abreviacion: "Número", tipo: __TIPO_CAMPO_NUMERICO },
								tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 200 },
								tdDescripcion: { campo: "descripcion", abreviacion: "Descripción", descripcion: "Descripción", tipo: __TIPO_CAMPO_STRING, ancho: 250 },
								tdDistribuidor: { campo: "distribuidor.nombre", clave: "distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidores, clave: "id", valor: "nombre" }, ancho: 200 },
								tdFechaAsignacionDistribuidor: { campo: "fechaAsignacionDistribuidor", abreviacion: "F. Asign. Distr.", descripcion: "Fecha de asign. Distribuidor", tipo: __TIPO_CAMPO_FECHA_HORA },
								tdPuntoVenta: { campo: "puntoVenta.nombre", clave: "puntoVenta.id", descripcion: "Punto de venta", abreviacion: "Pto. venta", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPuntoVentas, clave: "id", valor: "nombre" }, ancho: 200 },
								tdFechaAsignacionPuntoVenta: { campo: "fechaAsignacionPuntoVenta", abreviacion: "F. Asign. P.V.", descripcion: "Fecha de asign. Pto. venta", tipo: __TIPO_CAMPO_FECHA_HORA },
							}, 
							true,
							reloadData,
							trActivacionSubloteOnClick
						);
						
						grid.rebuild();
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						break;
					}
				}
				
				if (grid == null) {
					grid = new Grid(
						document.getElementById("divTableActivacionesSublotes"),
						{
							tdMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
						}, 
						true,
						reloadData,
						trActivacionSubloteOnClick
					);
					
					grid.rebuild();
				}
			}, async: false
		}
	);
	
	reloadData();

	$("#divIFrameActivacionSublote").draggable();
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

function listDistribuidores() {
	var result = [];
	
	UsuarioRolEmpresaDWR.listDistribuidoresByContext(
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

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	ActivacionSubloteDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	ActivacionSubloteDWR.countContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
}

function inputActualizarOnClick(event, element) {
	reloadData();
}

function trActivacionSubloteOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameActivacionSublote").src = "/LogisticaWEB/pages/activaciones_sublote/activaciones_sublotes_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameActivacionSublote"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameActivacionSublote"));
}

function inputNuevoOnClick() {
	document.getElementById("iFrameActivacionSublote").src = "./activaciones_sublotes_edit.jsp";
	showPopUp(document.getElementById("divIFrameActivacionSublote"));
}