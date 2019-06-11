var __ROL_ADMINISTRADOR = 1;
var __ROL_ENCARGADO_ACTIVACIONES = 12;
var __ROL_DEMO = 21;

var grid = null;
var map = null;

$(document).ready(init);

function init() {
	$("#divButtonNew").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_ENCARGADO_ACTIVACIONES
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
						mode = __FORM_MODE_ADMIN;
						$("#divButtonNew").show();
						
						grid = new Grid(
							document.getElementById("divTablePuntosVenta"),
							{
								tdNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 200 },
								tdTelefono: { campo: "telefono", descripcion: "Teléfono", abreviacion: "Tel.", tipo: __TIPO_CAMPO_STRING, ancho: 100, oculto: true },
								tdContacto: { campo: "contacto", descripcion: "Contacto", abreviacion: "Contacto", tipo: __TIPO_CAMPO_STRING, ancho: 150, oculto: true },
								tdDepartamento: { campo: "departamento.nombre", clave: "departamento.id", descripcion: "Departamento", abreviacion: "Depto.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre"} },
								tdBarrio: { campo: "barrio.nombre", descripcion: "Barrio", abreviacion: "Barrio", tipo: __TIPO_CAMPO_STRING, ancho: 150 },
								tdEstado: { campo: "estadoPuntoVenta.nombre", clave: "estadoPuntoVenta.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoPuntoVentas, clave: "id", valor: "nombre"}, ancho: 100 },
								tdFechaBaja: { campo: "fechaBaja", descripcion: "Eliminado", abreviacion: "Eliminado", tipo: __TIPO_CAMPO_FECHA_HORA },
								tdFechaAsignacion: { campo: "fechaAsignacionDistribuidor", descripcion: "Asignado distribuidor", abreviacion: "F. asign. distr.", tipo: __TIPO_CAMPO_FECHA_HORA },
								tdFechaVisita: { campo: "fechaVisitaDistribuidor", descripcion: "Visitado distribuidor", abreviacion: "F. visit. distr.", tipo: __TIPO_CAMPO_FECHA_HORA },
								tdEstadoVisita: { campo: "estadoVisitaPuntoVentaDistribuidor.nombre", clave: "puntoVenta.estadoVisitaPuntoVentaDistribuidor.id", descripcion: "Estado última visita", abreviacion: "Estado últ. visita", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoVisitaPuntoVentaDistribuidores, clave: "id", valor: "nombre"}, ancho: 100 },
								tdFechaUltimoCambioEstadoVisita: { campo: "fechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor", descripcion: "Fecha último cambio estado visita", abreviacion: "F. últ. camb. est. visit.", tipo: __TIPO_CAMPO_FECHA_HORA, ancho: 120 },
								tdUcre: { campo: "ucre", descripcion: "Creado por", abreviacion: "Creado por", tipo: __TIPO_CAMPO_STRING, oculto: true },
								tdFcre: { campo: "fcre", descripcion: "Creado", abreviacion: "Creado", tipo: __TIPO_CAMPO_FECHA_HORA }
							},
							true,
							reloadData,
							trPuntoVentaOnClick
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
	
	$("#divIFramePuntoVenta").draggable();
	
	reloadData();
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

function listEstadoPuntoVentas() {
	var result = [];
	
	EstadoPuntoVentaDWR.list(
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

function initMap() {
	var divMap = document.getElementById("divMapaPuntosVenta");
	map = new google.maps.Map(
		divMap, {
			center: {lat: 0, lng: 0},
			zoom: 8
    	}
	);
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	PuntoVentaDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				initMap();
				
				var markerBounds = new google.maps.LatLngBounds();
				
				for (var i=0; i<data.registrosMuestra.length; i++) {
					if (data.registrosMuestra[i].latitud != null && data.registrosMuestra[i].longitud != null) {
						var latlng = {
							lat: data.registrosMuestra[i].latitud, lng: data.registrosMuestra[i].longitud
						};
						
						var marker = new google.maps.Marker({
							position: latlng,
							map: map,
							title: data.registrosMuestra[i].nombre
						});
						
						markerBounds.extend(latlng);
					}
				}
				
				map.fitBounds(markerBounds);
				
				grid.reload(data);
			}
		}
	);
	
	PuntoVentaDWR.countContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
}

function trPuntoVentaOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFramePuntoVenta").src = "./punto_venta_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFramePuntoVenta"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFramePuntoVenta").src = "./punto_venta_edit.jsp";
	showPopUp(document.getElementById("divIFramePuntoVenta"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}