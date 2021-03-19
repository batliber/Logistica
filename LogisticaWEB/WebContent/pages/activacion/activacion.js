var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_ACTIVACION = 9;
var __ROL_ACTIVADOR = 10;

var mode = __ROL_ACTIVADOR;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonAsignar").hide();
	$("#divButtonExportarAExcel").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if ((data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_ACTIVACION)
				|| (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR)) {
				$("#divButtonAsignar").show();
				$("#divButtonExportarAExcel").show();
				
				grid = new Grid(
					document.getElementById("divTableContratos"),
					{
						tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
						tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" } },
						tdContratoFinContrato: { campo: "fechaFinContrato", descripcion: "Fin de contrato", abreviacion: "Fin", tipo: __TIPO_CAMPO_FECHA },
						tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
						tdContratoNuevoPlan: { campo: "nuevoPlan.descripcion", clave: "nuevoPlan.id", descripcion: "Nuevo plan", abreviacion: "Nuevo plan", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPlanes, clave: "id", valor: "descripcion" }, ancho: 80 },
						tdContratoEquipo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 90 },
						tdContratoCostoEnvio: { campo: "costoEnvio", descripcion: "Costo de envío", abreviacion: "C. env.", tipo: __TIPO_CAMPO_DECIMAL },
						tdContratoNumeroSerie: { campo: "numeroSerie", descripcion: "Número de serie", abreviacion: "Serie", tipo: __TIPO_CAMPO_STRING },
						tdFechaActivarEn: { campo: "fechaActivarEn", descripcion: "Activar en", abreviacion: "Act. en", tipo: __TIPO_CAMPO_FECHA },
						tdFechaEnvioAntel: { campo: "fechaEnvioAntel", descripcion: "Fecha de envío a ANTEL", abreviacion: "E. ANTEL", tipo: __TIPO_CAMPO_FECHA },
						tdFechaDevolucionDistribuidor: { campo: "fechaDevolucionDistribuidor", descripcion: "Devuelto por distribuidor", abreviacion: "Distribuído", tipo: __TIPO_CAMPO_FECHA },
						tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING },
						tdUsuario: { campo: "usuario.nombre", clave: "usuario.id", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listUsuarios, clave: "id", valor: "nombre" }, ancho: 90 },
						tdActivador: { campo: "activador.nombre", clave: "activador.id", descripcion: "Activador", abreviacion: "Activador", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listActivadores, clave: "id", valor: "nombre" }, ancho: 90 },
						tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 90 },
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
							campo: "estado.nombre",
							operador: "keq",
							valores: ["10"]
						},
						{
							campo: "usuario.nombre",
							operador: "nl",
							valores: []
						}
					], 
					false
				).then(function (data) {
					grid.filtroDinamico.preventReload = false
					
					reloadData();
				});
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleTripleSize");
				
				mode = __ROL_SUPERVISOR_ACTIVACION;
				break;
			}
		}
		
		if (grid == null) {
			grid = new Grid(
				document.getElementById("divTableContratos"),
				{
					tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
					tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
					tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" } },
					tdContratoFinContrato: { campo: "fechaFinContrato", descripcion: "Fin de contrato", abreviacion: "Fin", tipo: __TIPO_CAMPO_FECHA },
					tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
					tdContratoLocalidad: { campo: "localidad", descripcion: "Localidad", abreviacion: "Localidad", tipo: __TIPO_CAMPO_STRING },
					tdContratoNuevoPlan: { campo: "nuevoPlan.descripcion", clave: "nuevoPlan.id", descripcion: "Nuevo plan", abreviacion: "Nuevo plan", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPlanes, clave: "id", valor: "descripcion" }, ancho: 80 },
					tdContratoEquipo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 90 },
					tdContratoCostoEnvio: { campo: "costoEnvio", descripcion: "Costo de envío", abreviacion: "C. env.", tipo: __TIPO_CAMPO_DECIMAL },
					tdContratoTelefonoContacto: { campo: "telefonoContacto", descripcion: "Teléfono contacto", abreviacion: "Teléfono", tipo: __TIPO_CAMPO_STRING },
					tdContratoNumeroSerie: { campo: "numeroSerie", descripcion: "Número de serie", abreviacion: "Serie", tipo: __TIPO_CAMPO_STRING },
					tdFechaDevolucionDistribuidor: { campo: "fechaDevolucionDistribuidor", descripcion: "Devuelto por distribuidor", abreviacion: "Distribuído", tipo: __TIPO_CAMPO_FECHA },
					tdFechaActivarEn: { campo: "fechaActivarEn", descripcion: "Activar en", abreviacion: "Act. en", tipo: __TIPO_CAMPO_FECHA },
					tdFechaEnvioAntel: { campo: "fechaEnvioAntel", descripcion: "Fecha de envío a ANTEL", abreviacion: "E. ANTEL", tipo: __TIPO_CAMPO_FECHA },
					tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING },
					tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 90 },
				}, 
				true,
				reloadData,
				trContratoOnClick
			);
			
			grid.rebuild();
			
			mode = __ROL_ACTIVADOR;
		}
		
		$("#divIFrameContrato").draggable();
		$("#divIFrameSeleccionActivador").draggable();
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
	var estadoId = $(target).children("[campo='tdEstado']").attr("clave");
	
	var formMode = __FORM_MODE_READ;
	if (estadoId == __ESTADO_ACTIVAR
		|| estadoId == __ESTADO_CONTROL_ANTEL
		|| estadoId == __ESTADO_ACT_DOC_VENTA
		|| estadoId == __ESTADO_FACTURA_IMPAGA) {
		if (mode == __ROL_SUPERVISOR_ACTIVACION) {
			formMode = __FORM_MODE_SUPERVISOR_ACTIVACION;
		} else {
			formMode = __FORM_MODE_ACTIVACION;
		}
	}
	
	document.getElementById("iFrameContrato").src = 
		"/LogisticaWEB/pages/contrato/contrato.jsp?m=" + formMode + "&cid=" + $(target).attr("id");
	
	showPopUp(document.getElementById("divIFrameContrato"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#selectActivador").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameContrato"));
}

function inputAsignarOnClick() {
	metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
		metadataConsulta.tamanoSubconjunto = grid.getCount();
	}
	
	if (metadataConsulta.tamanoSubconjunto <= __MAXIMA_CANTIDAD_REGISTROS_ASIGNACION) {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ContratoREST/chequearAsignacion",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	    }).then(function(data) {
			if (data != null
				&& (data.ok 
					|| confirm("Atención: se modificarán registros que ya se encuentran asignados.")
				)
			) {
				listActivadores()
					.then(function(data) {
						fillSelect(
							"selectActivador",
							data,
							"id",
							"nombre"
						);
						
						showPopUp(document.getElementById("divIFrameSeleccionActivador"));
					});
			}
		});
	} else {
		alert("No se puede completar la operación. La asignación modificará más de 300 registros.");
	}
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameSeleccionActivador"));
	
	$("#selectActivador").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputAceptarOnClick(event, element) {
	if ($("#selectActivador").val() == "0") {
		alert("Debe seleccionar un activador.");
		
		return;
	}
	
	var activador = {
		id: $("#selectActivador").val()
	};
	
	metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
		metadataConsulta.tamanoSubconjunto = grid.getCount();
	}
	
	if (confirm("Se asignarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ContratoREST/asignarActivador",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify({
	        	"usuario": activador,
	        	"metadataConsulta": grid.filtroDinamico.calcularMetadataConsulta()
	        })
	    }).then(function(data) {
			alert("Operación exitosa.");
			
			reloadData();
		});
	}
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