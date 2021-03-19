var __ROL_ADMINISTRADOR = 1;
var __ROL_GERENTE_PHONEHOUSE = 69464609;

var accionesHabilitadas = false;

var grid = null;

var estados = [
	"No procesado", 
	"En proceso", 
	"Procesado", 
	"Lista vacia", 
	"Para procesar", 
	"Prioritario", 
	"Lista negra"
];

$(document).ready(init);

function init() {
	/*
	$("#divButtonAsignar").hide();
	$("#divButtonAsignarSubconjunto").hide();
	$("#divButtonDeshacerAsignacion").hide();
	*/
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (
				data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_GERENTE_PHONEHOUSE
			) {
				$("#inputExportarAExcel").prop("disabled", true);
				$("#inputAsignar").prop("disabled", true);
				$("#inputAsignarSubconjunto").prop("disabled", true);
				$("#inputDeshacerAsignacion").prop("disabled", true);
			
				selectTipoRegistroOnChange();
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				
				break;
			}
		}
    });
}

function inputActualizarOnClick(event, element) {
	reloadData();
}

function inputHabilitarAccionesOnClick(event, element) {
	habilitarAcciones(accionesHabilitadas, false);
}

function habilitarAcciones(habilitar, force) {
	if (!force) {
		if (accionesHabilitadas) {
			$("#inputExportarAExcel").prop("disabled", true);
			$("#inputAsignar").prop("disabled", true);
			$("#inputAsignarSubconjunto").prop("disabled", true);
			$("#inputDeshacerAsignacion").prop("disabled", true);
			
			$("#inputHabilitarAcciones").val("Habilitar acciones");
		} else {
			$("#inputExportarAExcel").prop("disabled", false);
			$("#inputAsignar").prop("disabled", false);
			$("#inputAsignarSubconjunto").prop("disabled", false);
			$("#inputDeshacerAsignacion").prop("disabled", false);
			
			$("#inputHabilitarAcciones").val("Deshabilitar acciones");
		}
		
		accionesHabilitadas = !accionesHabilitadas;
	} else {
		accionesHabilitadas = habilitar;
		
		$("#inputAsignar").prop("disabled", !habilitar);
		$("#inputAsignarSubconjunto").prop("disabled", !habilitar);
		$("#inputDeshacerAsignacion").prop("disabled", !habilitar);
		
		$("#inputHabilitarAcciones").val(habilitar ? "Deshabilitar acciones" : "Habilitar acciones" );
	}
}

function selectTipoRegistroOnChange() {
	habilitarAcciones(false, true);
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		grid = new Grid(
			document.getElementById("divTabla"),
			{
				tdContratoMID: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
				tdContratoFinContrato: { campo: "fechaFinContrato", descripcion: "Fecha de fin de contrato", abreviacion: "Fin", tipo: __TIPO_CAMPO_FECHA },
				tdContratoTipoContratoDescripcion: { campo: "tipoContratoDescripcion", descripcion: "Tipo de contrato", abreviacion: "Tipo", tipo: __TIPO_CAMPO_MULTIPLE, dataSource: { funcion: listTipoContratos, clave: "tipoContratoDescripcion", valor: "tipoContratoDescripcion" }, ancho: 90 },
				tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING, ancho: 90 },
				tdContratoNumeroCliente: { campo: "numeroCliente", descripcion: "Número de cliente", abreviacion: "Cliente", tipo: __TIPO_CAMPO_NUMERICO },
				tdContratoNumeroContrato: { campo: "numeroContrato", descripcion: "Número de contrato", abreviacion: "Contrato", tipo: __TIPO_CAMPO_NUMERICO },
				tdContratoNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING },
				tdContratoDireccion: { campo: "direccion", descripcion: "Dirección", abreviacion: "Dirección", tipo: __TIPO_CAMPO_STRING },
				tdContratoCodigoPostal: { campo: "codigoPostal", descripcion: "Código postal", abreviacion: "C.P.", tipo: __TIPO_CAMPO_STRING, ancho: 60 },
				tdContratoLocalidad: { campo: "localidad", descripcion: "Localidad", abreviacion: "Localidad", tipo: __TIPO_CAMPO_STRING },
				tdContratoEquipo: { campo: "equipo", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_STRING },
				tdContratoAgente: { campo: "agente", descripcion: "Agente", abreviacion: "Agente", tipo: __TIPO_CAMPO_STRING },
				tdContratoFechaExportacion: { campo: "fechaExportacion", descripcion: "Asignado", abreviacion: "Asignado", tipo: __TIPO_CAMPO_FECHA },
				tdContratoFact: { campo: "fact", descripcion: "Obtenido", abreviacion: "Obtenido", tipo: __TIPO_CAMPO_FECHA }
			}, 
			true,
			reloadData,
			noOp
		);
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		grid = new Grid(
			document.getElementById("divTabla"),
			{
				tdPrepagoMID: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
				tdPrepagoMesAno: { campo: "mesAno", descripcion: "Mes/Año", abreviacion: "Mes/Año", tipo: __TIPO_CAMPO_FECHA_MES_ANO },
				tdPrepagoMontoMesActual: { campo: "montoMesActual", descripcion: "Monto actual", abreviacion: "Monto actual", tipo: __TIPO_CAMPO_DECIMAL, ancho: 80 },
				tdPrepagoMontoMesAnterior1: { campo: "montoMesAnterior1", descripcion: "Monto mes anterior 1", abreviacion: "Monto ant. 1", tipo: __TIPO_CAMPO_DECIMAL, ancho: 100 },
				tdPrepagoMontoMesAnterior2: { campo: "montoMesAnterior2", descripcion: "Monto mes anterior 2", abreviacion: "Monto ant. 2", tipo: __TIPO_CAMPO_DECIMAL, ancho: 100 },
				tdPrepagoMontoPromedio: { campo: "montoPromedio", descripcion: "Monto promedio", abreviacion: "Monto prom.", tipo: __TIPO_CAMPO_DECIMAL, ancho: 100 },
				tdPrepagoFechaExportacion: { campo: "fechaExportacion", descripcion: "Asignado", abreviacion: "Asignado", tipo: __TIPO_CAMPO_FECHA },
				tdPrepagoFechaActivacionKit: { campo: "fechaActivacionKit", descripcion: "Fecha de activación del kit", abreviacion: "Activado", tipo: __TIPO_CAMPO_FECHA },
				tdPrepagoFact: { campo: "fact", descripcion: "Obtenido", abreviacion: "Obtenido", tipo: __TIPO_CAMPO_FECHA }
			}, 
			true,
			reloadData,
			noOp
		);
	} else {
		grid = new Grid(
			document.getElementById("divTabla"),
			{
				tdSinDatosMID: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
				tdEstado: { campo: "estado.descripcion", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "descripcion" }, ancho: 90 },
				tdSinDatosFact: { campo: "fact", descripcion: "Obtenido", abreviacion: "Obtenido", tipo: __TIPO_CAMPO_FECHA }
			}, 
			true,
			reloadData,
			noOp
		);
	}
	
	grid.rebuild();
	
	reloadData();
}

function listTipoContratos() {
	return $.ajax({
        url: "/LogisticaWEB/RESTFacade/ACMInterfaceContratoPHREST/listTipoContratosContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    });
}

function inputTamanoMuestraOnChange(event) {
	reloadData();
}

function noOp() {
	
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	var listURL = "/LogisticaWEB/RESTFacade/ACMInterfaceMidPHREST/listSinDatosContextAware";
	var countURL = "/LogisticaWEB/RESTFacade/ACMInterfaceMidPHREST/countSinDatosContextAware";
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		listURL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoPHREST/listContextAware";
	    countURL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoPHREST/countContextAware";
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		listURL = "/LogisticaWEB/RESTFacade/ACMInterfacePrepagoPHREST/listContextAware";
	    countURL = "/LogisticaWEB/RESTFacade/ACMInterfacePrepagoPHREST/countContextAware";
	}
	
	$.ajax({
        url: listURL,
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: countURL,
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.setCount(data);
    	
    	$("#inputHabilitarAcciones").prop("disabled", false);
    });
}

function inputExportarAExcelOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	metadataConsulta.tamanoSubconjunto = grid.getCount();
	
	exportarAExcel(metadataConsulta);
}

function exportarAExcel(metadataConsulta) {
	var URL = null;

	if ($("#selectTipoRegistro").val() == "contrato") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoPHREST/exportarAExcel";
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfacePrepagoPHREST/exportarAExcel";
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
	
	if (URL != null) {
		if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			$.ajax({
		        url: URL,
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
}

function inputAsignarOnClick(event) {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
    }).then(function(data) {
    	fillSelect(
    		"selectEmpresa", 
    		data,
    		"id", 
    		"nombre"
    	);
    });
	
	showPopUp(document.getElementById("divIFrameSeleccionEmpresa"));
}

function inputAsignarSubconjuntoOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	$("#inputTamanoSubconjuntoAsignacion").val(metadataConsulta.tamanoSubconjunto);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
    }).then(function(data) {
    	fillSelect(
    		"selectEmpresa", 
    		data,
    		"id", 
    		"nombre"
    	);
    });
	
	showPopUp(document.getElementById("divIFrameSeleccionEmpresa"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#inputTamanoSubconjuntoAsignacion").val(0);
	$("#selectEmpresa").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameSeleccionEmpresa"));
	
	$("#inputTamanoSubconjuntoAsignacion").val(0);
	$("#selectEmpresa").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputAceptarOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	var preprocesarURL = null;
	var procesarURL = null;
	
	if ($("#selectEmpresa").val() != "0") {
		var empresa = {
			id: $("#selectEmpresa").val()
		};
		
		if ($("#inputTamanoSubconjuntoAsignacion").val() != 0) {
			metadataConsulta.tamanoSubconjunto = 
				Math.min(
					$("#inputTamanoSubconjuntoAsignacion").val(),
					grid.getCount()
				);
		} else {
			metadataConsulta.tamanoSubconjunto = grid.getCount();
		}
		
		if ($("#selectTipoRegistro").val() == "contrato") {
			preprocesarURL = 
				"/LogisticaWEB/RESTFacade/ACMInterfaceContratoPHREST/preprocesarAsignacionByEmpresa";
			procesarURL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoPHREST/asignarByEmpresa";
		} else if ($("#selectTipoRegistro").val() == "prepago") {
			preprocesarURL = 
				"/LogisticaWEB/RESTFacade/ACMInterfacePrepagoPHREST/preprocesarAsignacionByEmpresa";
			procesarURL = "/LogisticaWEB/RESTFacade/ACMInterfacePrepagoPHREST/asignarByEmpresa";
		} else {
			alert("Funcionalidad no habilitada para el tipo de registro.");
		}
	} else {
		alert("Debe seleccionar una empresa.");
	}
	
	if (preprocesarURL != null && procesarURL != null) {
		$.ajax({
	        url: preprocesarURL,
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify({
	        	"metadataConsulta": metadataConsulta,
	        	"empresa": empresa
	        })
	    }).then(function(data) {
	    	if (data != null && confirm(data.datos.replace(new RegExp("\\|", "g"), "\n"))) {
		    	$.ajax({
			        url: procesarURL,
			        method: "POST",
			        contentType: 'application/json',
			        data: JSON.stringify({
			        	"metadataConsulta": metadataConsulta,
			        	"empresa": empresa,
			        	"observaciones": $("#textareaObservaciones").val()
			        })
			    }).then(function(data) {
			    	if (data != null) {
				    	alert("Archivo generado: " + data.nombreArchivo);
						
						closePopUp(event, document.getElementById("divIFrameSeleccionEmpresa"));
						
						$("#inputTamanoSubconjuntoAsignacion").val(0);
						$("#selectEmpresa").val("0");
						$("#textareaObservaciones").val("");
						
						reloadData();
			    	}
			    });
	    	}
	    });
	}
}

function inputDeshacerAsignacionOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	var URL = null;
	if ($("#selectTipoRegistro").val() == "contrato") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoPHREST/deshacerAsignacion";
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfacePrepagoPHREST/deshacerAsignacion";
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
	
	if (URL != null) {
		if (confirm("Se anulará la última asignación.")) {
			$.ajax({
		        url: URL,
		        method: "POST",
		        contentType: 'application/json',
		        data: JSON.stringify(metadataConsulta)
		    }).then(function(data) {
		    	alert("Operación exitosa.");
		    	
				reloadData();
		    });
		}	
	}
}