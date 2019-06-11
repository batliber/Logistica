var __ROL_ADMINISTRADOR = 1;
var __ROL_DEMO = 21;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonAsignarVisitas").hide();
	$("#divButtonVisitasPermanentes").hide();
	$("#divButtonRecalcularPorcentajes").hide();
	$("#divButtonExportarAExcel").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
						$("#divButtonAsignarVisitas").show();
						$("#divButtonVisitasPermanentes").show();
						$("#divButtonRecalcularPorcentajes").show();
						$("#divButtonExportarAExcel").show();
						
						grid = new Grid(
							document.getElementById("divTablePuntosVenta"),
							{
								tdNombre: { campo: "puntoVenta.nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 200 },
								tdDepartamento: { campo: "puntoVenta.departamento.nombre", clave: "puntoVenta.departamento.id", descripcion: "Departamento", abreviacion: "Depto.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre"} },
								tdBarrio: { campo: "puntoVenta.barrio.nombre", clave: "puntoVenta.barrio.id", descripcion: "Barrio", abreviacion: "Barrio", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBarrios, clave: "id", valor: "nombre"} },
								tdEstado: { campo: "puntoVenta.estadoPuntoVenta.nombre", clave: "puntoVenta.estadoPuntoVenta.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoPuntoVentas, clave: "id", valor: "nombre"}, ancho: 70 },
								tdPorcentajeActivacion: { campo: "porcentajeActivacion", descripcion: "Porcentaje activación", abreviacion: "% act.", tipo: __TIPO_CAMPO_PORCENTAJE, decimales: 1 },
								tdFechaCalculoPorcentajeActivacion: { campo: "fechaCalculo", descripcion: "Fecha cálculo % activación", abreviacion: "F. calc. % act.", tipo: __TIPO_CAMPO_FECHA_HORA },
								tdFechaLiquidacion: { campo: "fechaLiquidacion", descripcion: "Fecha de liquidación", abreviacion: "F. liq.", tipo: __TIPO_CAMPO_FECHA_HORA },
								tdDistribuidor: { campo: "puntoVenta.distribuidor.nombre", clave: "puntoVenta.distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidoresChips, clave: "id", valor: "nombre" }, ancho: 90 },
								tdFechaAsignacion: { campo: "puntoVenta.fechaAsignacionDistribuidor", descripcion: "Asignado distribuidor", abreviacion: "F. asign. distr.", tipo: __TIPO_CAMPO_FECHA_HORA },
								tdFechaVisita: { campo: "puntoVenta.fechaVisitaDistribuidor", descripcion: "Visitado distribuidor", abreviacion: "F. visit. distr.", tipo: __TIPO_CAMPO_FECHA_HORA },
								tdEstadoVisita: { campo: "puntoVenta.estadoVisitaPuntoVentaDistribuidor.nombre", clave: "puntoVenta.estadoVisitaPuntoVentaDistribuidor.id", descripcion: "Estado última visita", abreviacion: "Estado últ. visita", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoVisitaPuntoVentaDistribuidores, clave: "id", valor: "nombre"}, ancho: 100 },
								tdFechaUltimoCambioEstadoVisita: { campo: "puntoVenta.fechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor", descripcion: "Fecha último cambio estado visita", abreviacion: "F. últ. camb. est. visit.", tipo: __TIPO_CAMPO_FECHA_HORA, ancho: 120 }
							}, 
							true,
							reloadData,
							trPuntoVentaOnClick
						);
						
						grid.rebuild();
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleQuintupleSize");
						break;
					}
				}
				
				if (grid == null) {
					grid = new Grid(
						document.getElementById("divTablePuntosVenta"),
						{
							tdNombre: { campo: "puntoVenta.nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 200 }
						}, 
						true,
						reloadData,
						trPuntoVentaOnClick
					);
					
					grid.rebuild();
				}
			}, async: false
		}
	);
	
	reloadData();
	
	$("#divIFramePuntoVenta").draggable();
	$("#divIFrameSeleccionDistribuidor").draggable();
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

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	CalculoPorcentajeActivacionPuntoVentaDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {				
				grid.reload(data);
			}
		}
	);
	
	CalculoPorcentajeActivacionPuntoVentaDWR.countContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
}

function trPuntoVentaOnClick() {
	
}

function inputActualizarOnClick() {
	reloadData();
}

function inputAsignarVisitasOnClick() {
	metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
		metadataConsulta.tamanoSubconjunto = grid.getCount();
	}
	
	$("#inputVisitasPermanentes").attr("checked", false);
	
	$("#selectDistribuidor > option").remove();
	
	$("#selectDistribuidor").append("<option value='0'>Seleccione...</option>");
	
	UsuarioRolEmpresaDWR.listDistribuidoresChipsByContext(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectDistribuidor").append(html);
			}, async: false
		}
	);

	showPopUp(document.getElementById("divIFrameSeleccionDistribuidor"));
}

function inputVisitasPermanentesOnClick() {
	metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
		metadataConsulta.tamanoSubconjunto = grid.getCount();
	}
	
	$("#inputVisitasPermanentes").attr("checked", true);
	
	$("#selectDistribuidor > option").remove();
	
	$("#selectDistribuidor").append("<option value='0'>Seleccione...</option>");
	
	UsuarioRolEmpresaDWR.listDistribuidoresChipsByContext(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectDistribuidor").append(html);
			}, async: false
		}
	);

	showPopUp(document.getElementById("divIFrameSeleccionDistribuidor"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#inputVisitasPermanentes").attr("checked", false);
	$("#selectDistribuidor").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameSeleccionDistribuidor"));
	
	$("#inputVisitasPermanentes").attr("checked", false);
	$("#selectDistribuidor").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputAceptarOnClick(event, element) {
	if ($("#selectDistribuidor").val() != "0") {
		var distribuidor = {
			id: $("#selectDistribuidor").val()
		};
		
		var observaciones = $("#textareaObservaciones").val();
		
		metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
		if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
			metadataConsulta.tamanoSubconjunto = grid.getCount();
		}
		
		if (confirm("Se asignarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			if ($("#inputVisitasPermanentes").attr("checked")) {
				VisitaPuntoVentaDistribuidorDWR.crearVisitasPermanentes(
					distribuidor,
					observaciones,
					metadataConsulta,
					{
						callback: function(data) {
							alert("Operación exitosa.");
							
							reloadData();
						}, async: false
					}
				);
			} else {
				VisitaPuntoVentaDistribuidorDWR.crearVisitas(
					distribuidor,
					observaciones,
					metadataConsulta,
					{
						callback: function(data) {
							alert("Operación exitosa.");
							
							reloadData();
						}, async: false
					}
				);
			}
		}
	} else {
		alert("Debe seleccionar un distribuidor.");
	}
}

function inputRecalcularPorcentajesOnClick() {
	LiquidacionDWR.calcularPorcentajeActivacionPuntoVentas(
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				reloadData();
			}, async: false
		}
	);
}

function inputExportarAExcelOnClick(event, element) {
	CalculoPorcentajeActivacionPuntoVentaDWR.exportarAExcel(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
				document.getElementById("formExportarAExcel").submit();
			}, async: false
		}
	);
}