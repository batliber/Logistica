var __ROL_ADMINISTRADOR = 1;
var __ROL_ANTEL = 14;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonExportarAExcel").hide();

	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
	}).then(function(data) {
		for (var i = 0; i < data.usuarioRolEmpresas.length; i++) {
			if ((data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR)
				|| (data.usuarioRolEmpresas[i].rol.id == __ROL_ANTEL)) {
				$("#divButtonExportarAExcel").show();

				grid = new Grid(
					document.getElementById("divTableContratos"), 
					{
						tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
						tdContratoAntelNroTrn: { campo : "antelNroTrn", descripcion : "Nro Trn", abreviacion : "Nro Trn", tipo : __TIPO_CAMPO_STRING, ancho : 90 },
						tdContratoNumeroVale: { campo: "numeroVale", descripcion: "Número de vale", abreviacion: "Vale", tipo: __TIPO_CAMPO_NUMERICO },
						tdContratoEmpresa: { campo : "empresa.nombre", clave : "empresa.id", descripcion : "Empresa", abreviacion : "Empresa", tipo : __TIPO_CAMPO_RELACION, dataSource: { funcion : listEmpresas, clave : "id", valor : "nombre" }, ancho : 90 },
						tdContratoEquipo: { campo : "modelo.descripcion", clave : "modelo.id", descripcion : "Equipo", abreviacion : "Equipo", tipo : __TIPO_CAMPO_RELACION, dataSource: { funcion : listModelos, clave : "id", valor : "descripcion" }, ancho : 90 },
						tdContratoDocumento : { campo : "documento", descripcion : "Documento", abreviacion : "Documento", tipo : __TIPO_CAMPO_STRING, ancho : 90 },
						tdFechaVenta: { campo : "fechaVenta", descripcion : "Fecha de venta", abreviacion : "Vendido", tipo : __TIPO_CAMPO_FECHA },
						tdNumeroFactura: { campo : "numeroFactura", descripcion : "Doc Número", abreviacion : "Doc Número", tipo : __TIPO_CAMPO_STRING, ancho : 90 },
						// tdContratoFormaPago: { campo: "formaPago.descripcion", clave: "formaPago.id", descripcion: "Forma de pago", abreviacion: "Forma pago", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listFormaPagos, clave: "id", valor: "descripcion" }, ancho: 90 },
						tdContratoAntelFormaPago: { campo : "antelFormaPago", descripcion : "Forma de pago", abreviacion : "Forma de pago", tipo : __TIPO_CAMPO_STRING, ancho : 90 },
						tdContratoAntelNroServicioCuenta: { campo : "antelNroServicioCuenta", descripcion : "Nro servicio cuenta", abreviacion : "Nro serv. cuenta", tipo : __TIPO_CAMPO_STRING, ancho : 90 },
						tdContratoCuotas: { campo : "cuotas", descripcion : "Cuotas", abreviacion : "Cuotas", tipo : __TIPO_CAMPO_NUMERICO },
//						tdContratoImporteTotal: { campo : "precio", descripcion : "Importe", abreviacion : "Importe", tipo : __TIPO_CAMPO_DECIMAL },
						tdContratoAntelImporte: { campo : "antelImporte", descripcion : "Importe", abreviacion : "Importe", tipo : __TIPO_CAMPO_DECIMAL },
						tdContratoObservaciones : { campo : "observaciones", descripcion : "Observaciones", abreviacion : "Observaciones", tipo : __TIPO_CAMPO_STRING, ancho : 90 },
						tdFechaEnvioANucleo: { campo : "fechaEnvioANucleo", descripcion : "Fecha de envío a Núcleo", abreviacion : "F. env. Núcleo", tipo : __TIPO_CAMPO_FECHA, ancho: 90 },
						tdEstado: { campo : "estado.nombre", clave : "estado.id", descripcion : "Estado", abreviacion : "Estado", tipo : __TIPO_CAMPO_RELACION, dataSource : { funcion : listEstados, clave : "id", valor : "nombre" }, ancho : 90 },
					}, 
					true, 
					reloadData, 
					trContratoOnClick
				);

				grid.rebuild();
				
				grid.filtroDinamico.preventReload = true;
				grid.filtroDinamico.agregarFiltrosManuales(
					[
						{
							campo: "antelNroTrn",
							operador: "nnl",
							valores: []
						}
					], 
					true
				).then(function (data) {
					grid.filtroDinamico.preventReload = false
					
					reloadData();
				});
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				break;
			}
		}

		if (grid == null) {
			grid = new Grid(
				document.getElementById("divTableContratos"), 
				{
					tdContratoNumeroTramite : { campo : "numeroTramite", descripcion : "Número de trámite", abreviacion : "Trámite", tipo : __TIPO_CAMPO_NUMERICO },
					tdEstado : { campo : "estado.nombre", clave : "estado.id", descripcion : "Estado", abreviacion : "Estado", tipo : __TIPO_CAMPO_RELACION, dataSource : { funcion : listEstados, clave : "id", valor : "nombre" }, ancho : 90 },
				}, 
				true, 
				reloadData, 
				trContratoOnClick
			);

			grid.rebuild();
		}

		reloadData();
		
		$("#divIFrameContrato").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ContratoREST/listContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ContratoREST/countContextAware",
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
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ANTEL;
		
	document.getElementById("iFrameContrato").src = 
		"/LogisticaWEB/pages/contrato/contrato.jsp?m=" + formMode + "&cid=" + $(target).attr("id");
	
	showPopUp(document.getElementById("divIFrameContrato"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameContrato"));
}

function inputExportarAExcelOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/exportarAExcelVentasCuentaAjena",
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