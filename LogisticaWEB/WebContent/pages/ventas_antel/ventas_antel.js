var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_CALL_CENTER = 3;
var __ROL_SUPERVISOR_VENTAS_ANTEL = 63639424;

var __ROL_COORDINADOR_DISTRIBUCION = 11;

var __EMPRESA_ID_ANTEL_POLO_1 = 63371826;
var __EMPRESA_ID_ANTEL_POLO_2 = 63562256;

var grid = null;
		
$(document).ready(init);
		
function init() {
	$("#divButtonAgregarMid").hide();
	$("#divButtonExportarAExcel").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",
	}).then(function(data) {
		var showExportarAExcel = false;
		
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].empresa.id == __EMPRESA_ID_ANTEL_POLO_1
				|| data.usuarioRolEmpresas[i].empresa.id == __EMPRESA_ID_ANTEL_POLO_2) {
				showExportarAExcel = true;
				break;
			}
		}
		
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if ((data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_CALL_CENTER)
				|| (data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_VENTAS_ANTEL)
				|| (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR)) {
				$("#divButtonAgregarMid").show();
				$("#divButtonExportarAExcel").show();
				
				grid = new Grid(
					document.getElementById("divTableContratos"),
					{
						tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 80 },
						tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
						tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING, ancho: 90 },
						tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 90 },
						tdDistribuidor: { campo: "distribuidor.nombre", clave: "distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidores, clave: "id", valor: "nombre" }, ancho: 90 },
						tdResultadoEntregaDistribucionFecha: { campo: "resultadoEntregaDistribucionFecha", descripcion: "Fecha de entregado", abreviacion: "Entregado", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdResultadoEntregaDistribucion: { campo: "resultadoEntregaDistribucion.descripcion", clave: "resultadoEntregaDistribucion.id", descripcion: "Resultado entrega", abreviacion: "Entrega", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listResultadoEntregaDistribuciones, clave: "id", valor: "descripcion" } },
						tdResultadoEntregaDistribucionObservaciones: { campo: "resultadoEntregaDistribucionObservaciones", descripcion: "Observaciones entrega", abreviacion: "Obs. entrega.", tipo: __TIPO_CAMPO_STRING },
						tdFechaDevolucionDistribuidor: { campo: "fechaDevolucionDistribuidor", descripcion: "Devuelto", abreviacion: "Devuelto", tipo: __TIPO_CAMPO_FECHA },
						tdFechaCoordinacion: { campo: "fechaCoordinacion", descripcion: "Fecha de coordinación", abreviacion: "Coordinado", tipo: __TIPO_CAMPO_FECHA },
						tdUsuario: { campo: "usuario.nombre", clave: "usuario.id", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listUsuarios, clave: "id", valor: "nombre" }, ancho: 90 },
						tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoANTELs, clave: "id", valor: "nombre" }, ancho: 150 },								
						tdContratoCostoEnvio: { campo: "costoEnvio", descripcion: "Costo de envío", abreviacion: "C. env.", tipo: __TIPO_CAMPO_DECIMAL, oculto: true },
						tdFechaRechazo: { campo: "fechaRechazo", descripcion: "Fecha de rechazo", abreviacion: "Rechazado", tipo: __TIPO_CAMPO_FECHA, oculto: true },
					}, 
					true,
					reloadData,
					trContratoOnClick
				);
				
				grid.rebuild();
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleTripleSize");
				break;
			}
		}
		
		if (grid == null) {
			grid = new Grid(
				document.getElementById("divTableContratos"),
				{
					tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
					tdContratoAntelNroTrn: { campo : "antelNroTrn", descripcion : "Número de orden", abreviacion : "Nro. orden", tipo : __TIPO_CAMPO_STRING, ancho : 90 },
					tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
					tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING, ancho: 90 },
					tdContratoNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING },
					tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 90 },
					tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoANTELs, clave: "id", valor: "nombre" }, ancho: 200 },								
					tdContratoDepartamento: { campo: "zona.departamento.nombre", clave: "zona.departamento.id", descripcion: "Departamento", abreviacion: "Depto.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre"}, ancho: 100 },
					tdContratoBarrio: { campo: "barrio.nombre", clave: "barrio.id", descripcion: "Barrio", abreviacion: "Barrio", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBarrios, clave: "id", valor: "nombre" }, ancho: 175 },
					tdContratoZona: { campo: "zona.nombre", clave: "zona.id", descripcion: "Zona", abreviacion: "Zona", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listZonas, clave: "id", valor: "nombre"} }
				}, 
				true,
				reloadData,
				trContratoOnClick
			);
			
			grid.rebuild();
			
			if (showExportarAExcel) {
				$("#divButtonAgregarMid").show();
//				$("#divButtonExportarAExcel").show();
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
			}
		}
		
		reloadData();
		
		$("#divIFrameContrato").draggable();
		$("#divIFrameHistoricoContrato").draggable();
	});
}

function listTipoContratos() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/listTipoContratosContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoANTELREST/listContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoANTELREST/countContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.setCount(data);
	});
}

function inputActualizarOnClick(event, element) {
	reloadData();
}

function trContratoOnClick(eventObject) {
//	var target = eventObject.currentTarget;
//	var estadoId = $(target).children("[campo='tdEstado']").attr("clave");
//	
//	var formMode = __FORM_MODE_NEW_ANTEL;
//	document.getElementById("iFrameContrato").src = 
//		"/LogisticaWEB/pages/contrato/contrato.jsp?m=" + formMode + "&cid=" + $(target).attr("id");
//	
//	showPopUp(document.getElementById("divIFrameContrato"));
	
	var target = eventObject.currentTarget;
	
	document.getElementById("iFrameHistorico").src = "/LogisticaWEB/pages/ventas_antel/historico.jsp?cid=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameHistoricoContrato"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameContrato"));
}

function inputAgregarMidOnClick(event, element) {
	document.getElementById("iFrameContrato").src = 
		"/LogisticaWEB/pages/contrato/contrato.jsp?m=" + __FORM_MODE_NEW_ANTEL;
	
	showPopUp(document.getElementById("divIFrameContrato"));
}

function inputExportarAExcelOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/exportarAExcel",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(metadataConsulta)
		}).then(function(data) {
			if (data != null) {
				document.getElementById("formExportarAExcel").action = 
					"/LogisticaWEB/Download?fn=" + data.nombreArchivo;
				
				document.getElementById("formExportarAExcel").submit();
			}
		});
	}
}