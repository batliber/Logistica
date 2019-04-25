var __ROL_ADMINISTRADOR = 1;

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
	$("#divButtonAsignar").hide();
	$("#divButtonAsignarSubconjunto").hide();
	$("#divButtonDeshacerAsignacion").hide();
	
	$("#inputExportarAExcel").prop("disabled", true);
	$("#inputAsignar").prop("disabled", true);
	$("#inputAsignarSubconjunto").prop("disabled", true);
	$("#inputDeshacerAsignacion").prop("disabled", true);

	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR) {
						$("#divButtonAsignar").show();
						$("#divButtonAsignarSubconjunto").show();
						$("#divButtonDeshacerAsignacion").show();
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleFourfoldSize");
						break;
					}
				}
			}, async: false
		}
	);
			
	selectTipoRegistroOnChange();
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
//			$("#inputExportarSubconjunto").prop("disabled", true);
			$("#inputAsignar").prop("disabled", true);
			$("#inputAsignarSubconjunto").prop("disabled", true);
			$("#inputDeshacerAsignacion").prop("disabled", true);
//			$("#inputReprocesar").prop("disabled", true);
//			$("#inputReprocesarSubconjunto").prop("disabled", true);
//			$("#inputListaNegra").prop("disabled", true);
			
			$("#inputHabilitarAcciones").val("Habilitar acciones");
		} else {
			$("#inputExportarAExcel").prop("disabled", false);
//			$("#inputExportarSubconjunto").prop("disabled", false);
			$("#inputAsignar").prop("disabled", false);
			$("#inputAsignarSubconjunto").prop("disabled", false);
			$("#inputDeshacerAsignacion").prop("disabled", false);
//			$("#inputReprocesar").prop("disabled", false);
//			$("#inputReprocesarSubconjunto").prop("disabled", false);
//			$("#inputListaNegra").prop("disabled", false);
			
			$("#inputHabilitarAcciones").val("Deshabilitar acciones");
		}
		
		accionesHabilitadas = !accionesHabilitadas;
	} else {
		accionesHabilitadas = habilitar;
		
//		$("#inputExportarAExcel").prop("disabled", !habilitar);
//		$("#inputExportarSubconjunto").prop("disabled", !habilitar);
		$("#inputAsignar").prop("disabled", !habilitar);
		$("#inputAsignarSubconjunto").prop("disabled", !habilitar);
		$("#inputDeshacerAsignacion").prop("disabled", !habilitar);
//		$("#inputReprocesar").prop("disabled", !habilitar);
//		$("#inputReprocesarSubconjunto").prop("disabled", !habilitar);
//		$("#inputListaNegra").prop("disabled", !habilitar);
		
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
			trContratoOnClick
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
			trPrepagosOnClick
		);
//	} else if ($("#selectTipoRegistro").val() == "listaNegra") {
//		grid = new Grid(
//			document.getElementById("divTabla"),
//			{
//				tdListaNegraMID: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
//				tdListaNegraObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 700 },
//				tdListaNegraFact: { campo: "fact", descripcion: "Obtenido", abreviacion: "Obtenido", tipo: __TIPO_CAMPO_FECHA }
//			}, 
//			reloadData,
//			trListaNegraOnClick
//		);
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
			trSinDatosOnClick
		);
	}
	
	grid.rebuild();
	
	reloadData();
}

function listTipoContratos() {
	var result = [];
	
	ACMInterfaceContratoPHDWR.listTipoContratos(
		grid.filtroDinamico.calcularMetadataConsulta(),
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

function listEstados() {
	var result = [];
	
	ACMInterfaceEstadoDWR.list(
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

function inputTamanoMuestraOnChange(event) {
	reloadData();
}

function trContratoOnClick() {
	
}

function trPrepagosOnClick() {
	
}

function trSinDatosOnClick() {
	
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		ACMInterfaceContratoPHDWR.list(
			grid.filtroDinamico.calcularMetadataConsulta(),
			{
				callback: function(data) {
					grid.reload(data);
					
					$("#inputHabilitarAcciones").prop("disabled", false);
				}, async: false
			}
		);
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		ACMInterfacePrepagoPHDWR.list(
			grid.filtroDinamico.calcularMetadataConsulta(),
			{
				callback: function(data) {
					grid.reload(data);
					
					$("#inputHabilitarAcciones").prop("disabled", false);
				}, async: false
			}
		);
//	} else if ($("#selectTipoRegistro").val() == "listaNegra") {
//		ACMInterfaceListaNegraPHDWR.list(
//			grid.filtroDinamico.calcularMetadataConsulta(),
//			{
//				callback: function(data) {
//					grid.reload(data);
//					
//					$("#inputHabilitarAcciones").prop("disabled", false);
//				}, async: false
//			}
//		);
	} else {
		ACMInterfaceMidPHDWR.listSinDatos(
			grid.filtroDinamico.calcularMetadataConsulta(),
			{
				callback: function(data) {
					grid.reload(data);
					
					$("#inputHabilitarAcciones").prop("disabled", false);
				}, async: false
			}
		);
		
		ACMInterfaceMidPHDWR.countSinDatos(
			grid.filtroDinamico.calcularMetadataConsulta(),
			{
				callback: function(data) {
					grid.setCount(data);
				}
			}
		);
	}
}

function inputExportarAExcelOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	var empresa = {
		id: $("#selectEmpresa").val()
	};
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		if ($("#inputTamanoSubconjuntoAsignacion").val() != 0) {
			metadataConsulta.tamanoSubconjunto = 
				Math.min(
					$("#inputTamanoSubconjuntoAsignacion").val(),
					grid.getCount()
				);
		} else {
			metadataConsulta.tamanoSubconjunto = grid.getCount();
		}
		
		ACMInterfaceContratoPHDWR.exportarAExcel(
			metadataConsulta,
			{
				callback: function(data) {
					document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
					document.getElementById("formExportarAExcel").submit();	
				}, async: false
			}
		);
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		if ($("#inputTamanoSubconjuntoAsignacion").val() != 0) {
			metadataConsulta.tamanoSubconjunto = 
				Math.min(
					$("#inputTamanoSubconjuntoAsignacion").val(),
					grid.getCount()
				);
		} else {
			metadataConsulta.tamanoSubconjunto = grid.getCount();
		}
		
		ACMInterfacePrepagoPHDWR.exportarAExcel(
			metadataConsulta,
			{
				callback: function(data) {
					document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
					document.getElementById("formExportarAExcel").submit();
				}, async: false
			}
		);
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
}

function inputAsignarOnClick(event) {
	$("#selectEmpresa > option").remove();
	
	$("#selectEmpresa").append("<option value='0'>Seleccione...</option>");
	
	UsuarioRolEmpresaDWR.listEmpresasByContext(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectEmpresa").append(html);
			}, async: false
		}
	);
	
	showPopUp(document.getElementById("divIFrameSeleccionEmpresa"));
}

function inputAsignarSubconjuntoOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	$("#inputTamanoSubconjuntoAsignacion").val(metadataConsulta.tamanoSubconjunto);
	
	$("#selectEmpresa > option").remove();
	
	$("#selectEmpresa").append("<option value='0'>Seleccione...</option>");
	
	UsuarioRolEmpresaDWR.listEmpresasByContext(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectEmpresa").append(html);
			}, async: false
		}
	);
	
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
	
	if ($("#selectEmpresa").val() != "0") {
		var empresa = {
			id: $("#selectEmpresa").val()
		};
		
		if ($("#selectTipoRegistro").val() == "contrato") {
			if ($("#inputTamanoSubconjuntoAsignacion").val() != 0) {
				metadataConsulta.tamanoSubconjunto = 
					Math.min(
						$("#inputTamanoSubconjuntoAsignacion").val(),
						grid.getCount()
					);
			} else {
				metadataConsulta.tamanoSubconjunto = grid.getCount();
			}
			
			ACMInterfaceContratoPHDWR.preprocesarExportacionByEmpresa(
				metadataConsulta,
				empresa,
				{
					callback: function(data) {
						if (confirm(data.replace(new RegExp("\\|", "g"), "\n"))) {
							ACMInterfaceContratoPHDWR.exportarAExcelByEmpresa(
								metadataConsulta,
								empresa,
								$("#textareaObservaciones").val(),
								{
									callback: function(data) {
										alert("Archivo generado: " + data);
										
										closePopUp(event, document.getElementById("divIFrameSeleccionEmpresa"));
										
										$("#inputTamanoSubconjuntoAsignacion").val(0);
										$("#selectEmpresa").val("0");
										$("#textareaObservaciones").val("");
										
										reloadData();
									}, async: false
								}
							);
						}
					}, async: false
				}
			);
		} else if ($("#selectTipoRegistro").val() == "prepago") {
			if ($("#inputTamanoSubconjuntoAsignacion").val() != 0) {
				metadataConsulta.tamanoSubconjunto = 
					Math.min(
						$("#inputTamanoSubconjuntoAsignacion").val(),
						grid.getCount()
					);
			} else {
				metadataConsulta.tamanoSubconjunto = grid.getCount();
			}
			
			ACMInterfacePrepagoPHDWR.preprocesarExportacionByEmpresa(
				metadataConsulta,
				empresa,
				{
					callback: function(data) {
						if (confirm(data.replace(new RegExp("\\|", "g"), "\n"))) {
							ACMInterfacePrepagoPHDWR.exportarAExcelByEmpresa(
								metadataConsulta,
								empresa,
								$("#textareaObservaciones").val(),
								{
									callback: function(data) {
										alert("Archivo generado: " + data);
										
										closePopUp(event, document.getElementById("divIFrameSeleccionEmpresa"));
										
										$("#inputTamanoSubconjuntoAsignacion").val(0);
										$("#selectEmpresa").val("0");
										$("#textareaObservaciones").val("");
										
										reloadData();
									}, async: false
								}
							);
						}
					}, async: false
				}
			);
		} else {
			alert("Funcionalidad no habilitada para el tipo de registro.");
		}
	} else {
		alert("Debe seleccionar una empresa.");
	}
}

function inputDeshacerAsignacionOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		if (confirm("Se anulará la última asignación.")) {
			ACMInterfaceContratoPHDWR.deshacerAsignacion(
				metadataConsulta,
				{
					callback: function(data) {
						reloadData();
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		if (confirm("Se anulará la última asignación.")) {
			ACMInterfacePrepagoPHDWR.deshacerAsignacion(
				metadataConsulta,
				{
					callback: function(data) {
						reloadData();
					}
				}
			);
		}
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
}