var __ROL_ADMINISTRADOR = 1;
var __ROL_DEMO = 21;

var grid = null;
		
$(document).ready(init);

function init() {
	$("#divButtonAsignar").hide();
	$("#divButtonExportarAExcel").hide();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
    }).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
				$("#divButtonExportarAExcel").show();
				
				grid = new Grid(
					document.getElementById("divTableAcreditaciones"),
					{
						tdRecargaEstadoAcreditacionSaldo: { campo: "recargaEstadoAcreditacionSaldo.nombre", clave: "recargaEstadoAcreditacionSaldo.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listRecargaEstadoAcreditacionSaldos, clave: "id", valor: "nombre" } },
						tdRecargaEmpresa: { campo: "recargaEmpresa.nombre", clave: "recargaEmpresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listRecargaEmpresas, clave: "id", valor: "nombre" } },
						tdPuntoVenta: { campo: "puntoVenta.nombre", clave: "puntoVenta.id", descripcion: "Punto de venta", abreviacion: "P. V.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPuntoVentas, clave: "id", valor: "nombre" } },
						tdFechaSolicitud: { campo: "fechaSolicitud", descripcion: "Fecha de solicitud", abreviacion: "Solicitado", tipo: __TIPO_CAMPO_FECHA },
						tdFechaAcreditacion: { campo: "fechaAcreditacion", descripcion: "Fecha de acreditación", abreviacion: "Acreditado", tipo: __TIPO_CAMPO_FECHA },
						tdMonto: { campo: "monto", descripcion: "Monto", abreviacion: "Monto", tipo: __TIPO_CAMPO_DECIMAL },
						tdAdjunto: { campo: "adjunto", descripcion: "Adjunto", abreviacion: "Adjunto", tipo: __TIPO_CAMPO_STRING },
						tdObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING },
						tdDistribuidor: { campo: "distribuidor", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_STRING },
						tdRecargaFormaPago: { campo: "recargaFormaPago.descripcion", clave: "recargaFormaPago.id", descripcion: "Forma de pago", abreviacion: "F. pago", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listRecargaFormaPagos, clave: "id", valor: "descripcion" } },
						tdRecargaBanco: { campo: "recargaBanco.nombre", clave: "recargaBanco.id", descripcion: "Banco destino", abreviacion: "Banco. dest.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listRecargaBancos, clave: "id", valor: "nombre" } },
						tdCajero: { campo: "cajero", descripcion: "Cajero", abreviacion: "Cajero", tipo: __TIPO_CAMPO_STRING },
						tdCuentas: { campo: "empresaRecargaBancoCuenta.numero", descripcion: "Cuentas", abreviacion: "Cuentas", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresaRecargaBancoCuentas, clave: "id", valor: "numero" } },
						tdFechaPreautorizacion: { campo: "fechaPreaprobacion", descripcion: "Fecha de pre-autorización", abreviacion: "Pre-autoriz.", tipo: __TIPO_CAMPO_FECHA },
						tdFechaCredito: { campo: "fechaCredito", descripcion: "Fecha de crédito", abreviacion: "Crédito", tipo: __TIPO_CAMPO_FECHA },
						tdFechaDenegacion: { campo: "fechaDenegacion", descripcion: "Fecha de denegación", abreviacion: "Denegado", tipo: __TIPO_CAMPO_FECHA },
						tdFechaEliminacion: { campo: "fechaEliminacion", descripcion: "Fecha de eliminación", abreviacion: "Eliminado", tipo: __TIPO_CAMPO_FECHA }
					},
					true,
					reloadData,
					trAcreditacionOnClick
				);
				
				grid.rebuild();
				
				grid.filtroDinamico.preventReload = true;
				grid.filtroDinamico.agregarOrdenManual(
					{
						campo: "tdFechaSolicitud",
						ascendente: false
					}
				).then(function(data) {
					grid.filtroDinamico.preventReload = false
						
					reloadData();
				});
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				break;
			}
		}
		
		if (grid == null) {
			for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
				if (data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
//					
					grid = new Grid(
						document.getElementById("divTableAcreditaciones"),
						{
							tdRecargaEstadoAcreditacionSaldo: { campo: "recargaEstadoAcreditacionSaldo.nombre", clave: "recargaEstadoAcreditacionSaldo.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listRecargaEstadoAcreditacionSaldos, clave: "id", valor: "nombre" } }
						}, 
						true,
						reloadData,
						trAcreditacionOnClick
					);
					
					grid.rebuild();
					
					$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
					break;
				}
			}
		}
		
		reloadData();

		$("#divIFrameAcreditacion").draggable();
    });
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/RecargaSolicitudAcreditacionSaldoREST/listContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/RecargaSolicitudAcreditacionSaldoREST/countContextAware",
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

function trAcreditacionOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var estadoId = $(target).children("[campo='tdEstado']").attr("clave");
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameAcreditacion").src = 
		"/LogisticaWEB/pages/recargas/acreditaciones/acreditacion_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	
	showPopUp(document.getElementById("divIFrameAcreditacion"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameAcreditacion"));
}

function inputExportarAExcelOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/RecargaSolicitudAcreditacionSaldoREST/exportarAExcel",
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