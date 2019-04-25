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
	$("#inputExportarAExcel").prop("disabled", true);
	$("#inputExportarSubconjunto").prop("disabled", true);
	$("#inputAsignar").prop("disabled", true);
	$("#inputAsignarSubconjunto").prop("disabled", true);
	$("#inputDeshacerAsignacion").prop("disabled", true);
	$("#inputReprocesar").prop("disabled", true);
	$("#inputReprocesarSubconjunto").prop("disabled", true);
	$("#inputListaNegra").prop("disabled", true);
	$("#inputControlarRiesgoCrediticio").prop("disabled", true);
	
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
			$("#inputExportarSubconjunto").prop("disabled", true);
			$("#inputAsignar").prop("disabled", true);
			$("#inputAsignarSubconjunto").prop("disabled", true);
			$("#inputDeshacerAsignacion").prop("disabled", true);
			$("#inputReprocesar").prop("disabled", true);
			$("#inputReprocesarSubconjunto").prop("disabled", true);
			$("#inputListaNegra").prop("disabled", true);
			$("#inputControlarRiesgoCrediticio").prop("disabled", true);
			$("#inputControlarRiesgoCrediticioSubconjunto").prop("disabled", true);
			
			$("#inputHabilitarAcciones").val("Habilitar acciones");
		} else {
			$("#inputExportarAExcel").prop("disabled", false);
			$("#inputExportarSubconjunto").prop("disabled", false);
			$("#inputAsignar").prop("disabled", false);
			$("#inputAsignarSubconjunto").prop("disabled", false);
			$("#inputDeshacerAsignacion").prop("disabled", false);
			$("#inputReprocesar").prop("disabled", false);
			$("#inputReprocesarSubconjunto").prop("disabled", false);
			$("#inputListaNegra").prop("disabled", false);
			$("#inputControlarRiesgoCrediticio").prop("disabled", false);
			$("#inputControlarRiesgoCrediticioSubconjunto").prop("disabled", false);
			
			$("#inputHabilitarAcciones").val("Deshabilitar acciones");
		}
		
		accionesHabilitadas = !accionesHabilitadas;
	} else {
		accionesHabilitadas = habilitar;
		
		$("#inputExportarAExcel").prop("disabled", !habilitar);
		$("#inputExportarSubconjunto").prop("disabled", !habilitar);
		$("#inputAsignar").prop("disabled", !habilitar);
		$("#inputAsignarSubconjunto").prop("disabled", !habilitar);
		$("#inputDeshacerAsignacion").prop("disabled", !habilitar);
		$("#inputReprocesar").prop("disabled", !habilitar);
		$("#inputReprocesarSubconjunto").prop("disabled", !habilitar);
		$("#inputListaNegra").prop("disabled", !habilitar);
		$("#inputControlarRiesgoCrediticio").prop("disabled", !habilitar);
		$("#inputControlarRiesgoCrediticioSubconjunto").prop("disabled", !habilitar);
		
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
				tdContratoEstadoContrato: { campo: "estadoContrato", descripcion: "Estado del contrato", abreviacion: "Estado", tipo: __TIPO_CAMPO_STRING },
				tdContratoNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING },
				tdContratoDireccion: { campo: "direccion", descripcion: "Dirección", abreviacion: "Dirección", tipo: __TIPO_CAMPO_STRING },
				tdContratoCodigoPostal: { campo: "codigoPostal", descripcion: "Código postal", abreviacion: "C.P.", tipo: __TIPO_CAMPO_STRING, ancho: 60 },
				tdContratoLocalidad: { campo: "localidad", descripcion: "Localidad", abreviacion: "Localidad", tipo: __TIPO_CAMPO_STRING },
				tdContratoEquipo: { campo: "equipo", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_STRING },
				tdContratoAgente: { campo: "agente", descripcion: "Agente", abreviacion: "Agente", tipo: __TIPO_CAMPO_STRING },
				tdContratoFechaExportacion: { campo: "fechaExportacion", descripcion: "Asignado", abreviacion: "Asignado", tipo: __TIPO_CAMPO_FECHA },
				tdContratoFact: { campo: "fact", descripcion: "Obtenido", abreviacion: "Obtenido", tipo: __TIPO_CAMPO_FECHA },
				tdContratoPersonaDocumento: { campo: "acmInterfacePersona.documento", descripcion: "Documento (Persona)", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING, ancho: 90, oculto: true },
				tdContratoPersonaNombre: { campo: "acmInterfacePersona.nombre", descripcion: "Nombre (Persona)", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, oculto: true },
				tdContratoPersonaApellido: { campo: "acmInterfacePersona.apellido", descripcion: "Apellido (Persona)", abreviacion: "Apellido", tipo: __TIPO_CAMPO_STRING, oculto: true },
				tdContratoPersonaCalificacionRiesgoCrediticioAntel: { campo: "acmInterfacePersona.riesgoCrediticio.calificacionRiesgoCrediticioAntel.descripcion", clave: "acmInterfacePersona.riesgoCrediticio.calificacionRiesgoCrediticioAntel.id", descripcion: "Calificación ANTEL", abreviacion: "Calif. ANTEL", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listCalificacionRiesgoCrediticioAntel, clave: "id", valor: "descripcion" }, ancho: 100 },
				tdContratoPersonaCalificacionRiesgoCrediticioBCU: { campo: "acmInterfacePersona.riesgoCrediticio.calificacionRiesgoCrediticioBCU.descripcion", clave: "acmInterfacePersona.riesgoCrediticio.calificacionRiesgoCrediticioBCU.id", descripcion: "Calificación BCU", abreviacion: "Calif. BCU", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listCalificacionRiesgoCrediticioBCU, clave: "id", valor: "descripcion" }, ancho: 100 },
				tdContratoPersonaFechaControlRiesgo: { campo: "acmInterfacePersona.riesgoCrediticio.fact", descripcion: "Fecha control riesgo", abreviacion: "Controlado", tipo: __TIPO_CAMPO_FECHA },
				tdContratoPersonaSexo: { campo: "acmInterfacePersona.sexo", descripcion: "Sexo (Persona)", abreviacion: "Sexo", tipo: __TIPO_CAMPO_STRING, ancho: 60, oculto: true },
				tdContratoPersonaFechaNacimiento: { campo: "acmInterfacePersona.fechaNacimiento", descripcion: "Fecha de nacimiento (Persona)", abreviacion: "F. Nac.", tipo: __TIPO_CAMPO_FECHA, ancho: 70, oculto: true },
				tdContratoPersonaDireccion: { campo: "acmInterfacePersona.direccion", descripcion: "Direccion (Persona)", abreviacion: "Direccion", tipo: __TIPO_CAMPO_STRING, oculto: true },
				tdContratoPersonaLocalidad: { campo: "acmInterfacePersona.localidad", descripcion: "Localidad (Persona)", abreviacion: "Localidad", tipo: __TIPO_CAMPO_STRING, oculto: true }
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
				tdPrepagoAgente: { campo: "agente", descripcion: "Agente", abreviacion: "Agente", tipo: __TIPO_CAMPO_STRING },
				tdPrepagoFact: { campo: "fact", descripcion: "Obtenido", abreviacion: "Obtenido", tipo: __TIPO_CAMPO_FECHA },
				tdPrepagoPersonaDocumento: { campo: "acmInterfacePersona.documento", descripcion: "Documento (Persona)", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING, ancho: 90, oculto: true },
				tdPrepagoPersonaNombre: { campo: "acmInterfacePersona.nombre", descripcion: "Nombre (Persona)", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, oculto: true },
				tdPrepagoPersonaApellido: { campo: "acmInterfacePersona.apellido", descripcion: "Apellido (Persona)", abreviacion: "Apellido", tipo: __TIPO_CAMPO_STRING, oculto: true },
				tdPrepagoPersonaCalificacionRiesgoCrediticioAntel: { campo: "acmInterfacePersona.riesgoCrediticio.calificacionRiesgoCrediticioAntel.descripcion", clave: "acmInterfacePersona.riesgoCrediticio.calificacionRiesgoCrediticioAntel.id", descripcion: "Calificación ANTEL", abreviacion: "Calif. ANTEL", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listCalificacionRiesgoCrediticioAntel, clave: "id", valor: "descripcion" }, ancho: 100 },
				tdPrepagoPersonaCalificacionRiesgoCrediticioBCU: { campo: "acmInterfacePersona.riesgoCrediticio.calificacionRiesgoCrediticioBCU.descripcion", clave: "acmInterfacePersona.riesgoCrediticio.calificacionRiesgoCrediticioBCU.id", descripcion: "Calificación BCU", abreviacion: "Calif. BCU", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listCalificacionRiesgoCrediticioBCU, clave: "id", valor: "descripcion" }, ancho: 100 },
				tdPrepagoPersonaFechaControlRiesgo: { campo: "acmInterfacePersona.riesgoCrediticio.fact", descripcion: "Fecha control riesgo", abreviacion: "Controlado", tipo: __TIPO_CAMPO_FECHA },
				tdPrepagoPersonaSexo: { campo: "acmInterfacePersona.sexo", descripcion: "Sexo (Persona)", abreviacion: "Sexo", tipo: __TIPO_CAMPO_STRING, ancho: 60, oculto: true },
				tdPrepagoPersonaFechaNacimiento: { campo: "acmInterfacePersona.fechaNacimiento", descripcion: "Fecha de nacimiento (Persona)", abreviacion: "F. Nac.", tipo: __TIPO_CAMPO_FECHA, ancho: 70, oculto: true },
				tdPrepagoPersonaDireccion: { campo: "acmInterfacePersona.direccion", descripcion: "Direccion (Persona)", abreviacion: "Direccion", tipo: __TIPO_CAMPO_STRING, oculto: true },
				tdPrepagoPersonaLocalidad: { campo: "acmInterfacePersona.localidad", descripcion: "Localidad (Persona)", abreviacion: "Localidad", tipo: __TIPO_CAMPO_STRING, oculto: true }
			}, 
			true,
			reloadData,
			trPrepagosOnClick
		);
	} else if ($("#selectTipoRegistro").val() == "listaNegra") {
		grid = new Grid(
			document.getElementById("divTabla"),
			{
				tdListaNegraMID: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
				tdListaNegraObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 700 },
				tdListaNegraFact: { campo: "fact", descripcion: "Obtenido", abreviacion: "Obtenido", tipo: __TIPO_CAMPO_FECHA }
			}, 
			true,
			reloadData,
			trListaNegraOnClick
		);
	} else if ($("#selectTipoRegistro").val() == "numeroContrato") {
		grid = new Grid(
			document.getElementById("divTabla"),
			{
				tdNumeroContratoNumero: { campo: "numeroContrato", descripcion: "Número de contrato", abreviacion: "Nro. contrato", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
				tdNumeroContratoEstado: { campo: "estado.descripcion", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "descripcion" }, ancho: 150 },
				tdNumeroContratoFcre: { campo: "fcre", descripcion: "Creado", abreviacion: "Creado", tipo: __TIPO_CAMPO_FECHA_HORA, ancho: 100 },
				tdNumeroContratoFact: { campo: "fact", descripcion: "Obtenido", abreviacion: "Obtenido", tipo: __TIPO_CAMPO_FECHA_HORA, ancho: 100 }
			}, 
			true,
			reloadData,
			trNumeroContratoOnClick
		);
	} else {
		grid = new Grid(
			document.getElementById("divTabla"),
			{
				tdSinDatosMID: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
				tdSinDatosEstado: { campo: "estado.descripcion", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "descripcion" }, ancho: 150 },
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
	
	ACMInterfaceContratoDWR.listTipoContratos(
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

function listCalificacionRiesgoCrediticioAntel() {
	var result = [];
	
	CalificacionRiesgoCrediticioAntelDWR.list(
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

function listCalificacionRiesgoCrediticioBCU() {
	var result = [];
	
	CalificacionRiesgoCrediticioBCUDWR.list(
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

function trListaNegraOnClick() {
	
}

function trSinDatosOnClick() {
	
}

function trNumeroContratoOnClick() {
	
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		ACMInterfaceContratoDWR.listContextAware(
			grid.filtroDinamico.calcularMetadataConsulta(),
			{
				callback: function(data) {
					grid.reload(data);
				}
			}
		);
		
		ACMInterfaceContratoDWR.countContextAware(
			grid.filtroDinamico.calcularMetadataConsulta(),
			{
				callback: function(data) {
					grid.setCount(data);
					
					$("#inputHabilitarAcciones").prop("disabled", false);
				}
			}
		);
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		ACMInterfacePrepagoDWR.listContextAware(
			grid.filtroDinamico.calcularMetadataConsulta(),
			{
				callback: function(data) {
					grid.reload(data);
				}
			}
		);
		
		ACMInterfacePrepagoDWR.countContextAware(
			grid.filtroDinamico.calcularMetadataConsulta(),
			{
				callback: function(data) {
					grid.setCount(data);
					
					$("#inputHabilitarAcciones").prop("disabled", false);
				}
			}
		);
	} else if ($("#selectTipoRegistro").val() == "listaNegra") {
		ACMInterfaceListaNegraDWR.list(
			grid.filtroDinamico.calcularMetadataConsulta(),
			{
				callback: function(data) {
					grid.reload(data);
					
					$("#inputHabilitarAcciones").prop("disabled", false);
				}, async: false
			}
		);
	} else if ($("#selectTipoRegistro").val() == "numeroContrato") {
		ACMInterfaceNumeroContratoDWR.list(
			grid.filtroDinamico.calcularMetadataConsulta(),
			{
				callback: function(data) {
					grid.reload(data);
					
					$("#inputHabilitarAcciones").prop("disabled", false);
				}, async: false
			}
		);
		
		ACMInterfaceNumeroContratoDWR.count(
			grid.filtroDinamico.calcularMetadataConsulta(),
			{
				callback: function(data) {
					grid.setCount(data);
				}
			}
		);
	} else {
		ACMInterfaceMidDWR.listSinDatos(
			grid.filtroDinamico.calcularMetadataConsulta(),
			{
				callback: function(data) {
					grid.reload(data);
					
					$("#inputHabilitarAcciones").prop("disabled", false);
				}, async: false
			}
		);
		
		ACMInterfaceMidDWR.countSinDatos(
			grid.filtroDinamico.calcularMetadataConsulta(),
			{
				callback: function(data) {
					grid.setCount(data);
				}
			}
		);
	}
}

function inputExportarAExcelOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	metadataConsulta.tamanoSubconjunto = grid.getCount();
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		if (confirm("Se exportarán " + grid.getCount() + " registros.")) {
			ACMInterfaceContratoDWR.exportarAExcel(
				metadataConsulta,
				{
					callback: function(data) {
						document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
						document.getElementById("formExportarAExcel").submit();
					}, async: false
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		if (confirm("Se exportarán " + grid.getCount() + " registros.")) {
			ACMInterfacePrepagoDWR.exportarAExcel(
				metadataConsulta,
				{
					callback: function(data) {
						document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
						document.getElementById("formExportarAExcel").submit();
					}, async: false
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "listaNegra") {
		if (confirm("Se exportarán " + grid.getCount() + " registros.")) {
			ACMInterfaceListaNegraDWR.exportarAExcel(
				metadataConsulta,
				{
					callback: function(data) {
						document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
						document.getElementById("formExportarAExcel").submit();
					}, async: false
				}
			);
		}
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
}

function inputExportarSubconjuntoOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			ACMInterfaceContratoDWR.exportarAExcel(
				metadataConsulta,
				{
					callback: function(data) {
						alert("Archivo generado: " + data);
						
						reloadData();
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			ACMInterfacePrepagoDWR.exportarAExcel(
				metadataConsulta,
				{
					callback: function(data) {
						alert("Archivo generado: " + data);
						
						reloadData();
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "listaNegra") {
		if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			ACMInterfaceListaNegraDWR.exportarAExcel(
				metadataConsulta,
				{
					callback: function(data) {
						alert("Archivo generado: " + data);
						
						reloadData();
					}
				}
			);
		}
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
			
			ACMInterfaceContratoDWR.preprocesarExportacionByEmpresa(
				metadataConsulta,
				empresa,
				{
					callback: function(data) {
						if (confirm(data.replace(new RegExp("\\|", "g"), "\n"))) {
							ACMInterfaceContratoDWR.exportarAExcelByEmpresa(
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
			
			ACMInterfacePrepagoDWR.preprocesarExportacionByEmpresa(
				metadataConsulta,
				empresa,
				{
					callback: function(data) {
						if (confirm(data.replace(new RegExp("\\|", "g"), "\n"))) {
							ACMInterfacePrepagoDWR.exportarAExcelByEmpresa(
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
			ACMInterfaceContratoDWR.deshacerAsignacion(
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
			ACMInterfacePrepagoDWR.deshacerAsignacion(
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

function inputReprocesarOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	metadataConsulta.tamanoSubconjunto = grid.getCount();
	
	var observaciones = null;
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		$("#selectTipoProcesamiento").val(0);
		$("#textareaSeleccionTipoProcesamientoObservaciones").val("");
		
		showPopUp(document.getElementById("divIFrameSeleccionTipoProcesamiento"));
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		observaciones = prompt("Se reprocesarán " + grid.getCount() + " registros.");
		
		if (observaciones != null) {
			ACMInterfacePrepagoDWR.reprocesar(
				metadataConsulta,
				observaciones,
				{
					callback: function(data) {
						reloadData();
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "numeroContrato") {
		observaciones = prompt("Se reprocesarán " + grid.getCount() + " registros.");
		
		if (observaciones != null) {
			ACMInterfaceNumeroContratoDWR.reprocesar(
				metadataConsulta,
				observaciones,
				{
					callback: function(data) {
						reloadData();
					}, async: false
				}
			);
		}
//		$("#inputRangoNumeroContratoDesde").val(0);
//		$("#inputRangoNumeroContratoHasta").val(0);
//		$("#textareaNumeroContratoObservaciones").val("");
//		
//		showPopUp(document.getElementById("divIFrameSeleccionRangoNumerosContratos"));
	} else if ($("#selectTipoRegistro").val() == "sinDatos") {
		observaciones = prompt("Se reprocesarán " + grid.getCount() + " registros.");
		
		if (observaciones != null) {
			ACMInterfaceMidDWR.reprocesarSinDatos(
				metadataConsulta,
				observaciones,
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

function inputReprocesarSubconjuntoOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	var observaciones = null;
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		$("#selectTipoProcesamiento").val(0);
		$("#textareaSeleccionTipoProcesamientoObservaciones").val("");
		
		showPopUp(document.getElementById("divIFrameSeleccionTipoProcesamiento"));
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		observaciones = prompt("Se reprocesarán " + metadataConsulta.tamanoSubconjunto + " registros.");
		
		if (observaciones != null) {
			ACMInterfacePrepagoDWR.reprocesar(
				metadataConsulta,
				observaciones,
				{
					callback: function(data) {
						alert("Operación exitosa.")
						
						reloadData();
					}, async: false
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "sinDatos") {
		observaciones = prompt("Se reprocesarán " + metadataConsulta.tamanoSubconjunto + " registros.");
		
		if (observaciones != null) {
			ACMInterfaceMidDWR.reprocesarSinDatos(
				metadataConsulta,
				observaciones,
				{
					callback: function(data) {
						alert("Operación exitosa.")
						
						reloadData();
					}, async: false
				}
			);
		}
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
}

function inputCancelarRangoNumerosContratosOnClick(event) {
	closePopUp(event, document.getElementById("divIFrameSeleccionRangoNumerosContratos"));
	
	$("#inputRangoNumeroContratoDesde").val(0);
	$("#inputRangoNumeroContratoHasta").val(0);
	$("#textareaNumeroContratoObservaciones").val("");
	
	reloadData();
}

function inputAceptarRangoNumerosContratosOnClick(event) {
	var numeroContratoDesde = $("#inputRangoNumeroContratoDesde").val();
	var numeroContratoHasta = $("#inputRangoNumeroContratoHasta").val();
	var observaciones = $("#textareaNumeroContratoObservaciones").val();
	
	if (numeroContratoDesde == null || numeroContratoDesde == "") {
		alert("Debe ingresar un rango válido.")
	}
	
	if (numeroContratoHasta == null || numeroContratoHasta == "") {
		alert("Debe ingresar un rango válido.")
	}
	
	var metadataConsulta = {
		metadataOrdenaciones: [],
		metadataCondiciones: [
			{
				campo: "numeroContrato",
				condicion: "ge",
				valores: [ numeroContratoDesde ]
			},
			{
				campo: "numeroContrato",
				condicion: "le",
				valores: [ numeroContratoHasta ]
			}
		]
	};
	
	ACMInterfaceNumeroContratoDWR.reprocesar(
		metadataConsulta,
		observaciones,
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				inputCancelarRangoNumerosContratosOnClick();
				
				reloadData();
			}, async: false
		}
	);
}

function inputCancelarSeleccionTipoProcesamientoOnClick(event) {
	closePopUp(event, document.getElementById("divIFrameSeleccionTipoProcesamiento"));
	
	$("#selectTipoProcesamiento").val(0);
	$("#textareaSeleccionTipoProcesamientoObservaciones").val("");
	
	reloadData();
}

function inputAceptarSeleccionTipoProcesamientoOnClick(event) {
	var tipoProcesamiento = $("#selectTipoProcesamiento").val();
	var observaciones = $("#textareaSeleccionTipoProcesamientoObservaciones").val();
	
	if (tipoProcesamiento == 0) {
		alert("Debe seleccionar un tipo de procesamiento.")
	} else if (tipoProcesamiento == 1) {
		var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
		
		ACMInterfaceContratoDWR.reprocesarPorMID(
			metadataConsulta,
			observaciones,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					inputCancelarSeleccionTipoProcesamientoOnClick();
					
					reloadData();
				}, async: false
			}
		);
	} else {
		var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
		
		ACMInterfaceContratoDWR.reprocesarPorNumeroContrato(
			metadataConsulta,
			observaciones,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					inputCancelarSeleccionTipoProcesamientoOnClick();
					
					reloadData();
				}, async: false
			}
		);
	}
}

function inputControlarRiesgoCrediticioOnClick(event) {
	$("#selectTipoControlRiesgoCrediticioEmpresa > option").remove();
	$("#selectTipoControlRiesgoCrediticio > option").remove();
	
	$("#selectTipoControlRiesgoCrediticioEmpresa").append("<option value='0'>Seleccione...</option>");
	$("#selectTipoControlRiesgoCrediticio").append("<option value='0'>Seleccione...</option>");
	
	UsuarioRolEmpresaDWR.listEmpresasByContext(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectTipoControlRiesgoCrediticioEmpresa").append(html);
			}, async: false
		}
	);
	
	TipoControlRiesgoCrediticioDWR.list(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectTipoControlRiesgoCrediticio").append(html);
			}, async: false
		}
	);
	
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	$("#inputTipoControlRiesgoCrediticioTamanoSubconjunto").val(grid.getCount());
	
	showPopUp(document.getElementById("divIFrameSeleccionTipoControlRiesgoCrediticio"));
}

function inputCancelarSeleccionTipoControlRiesgoCrediticioOnClick(event) {
	closePopUp(event, document.getElementById("divIFrameSeleccionTipoControlRiesgoCrediticio"));
	
	$("#selectTipoControlRiesgoCrediticioEmpresa").val("0");
	$("#selectTipoControlRiesgoCrediticio").val("0");
	$("#inputTipoControlRiesgoCrediticioTamanoSubconjunto").val("0");
}
	
function inputAceptarSeleccionTipoControlRiesgoCrediticioOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if ($("#selectTipoControlRiesgoCrediticioEmpresa").val() == 0) {
		alert("Debe seleccionar una Empresa.");
		return false;
	}
	
	if ($("#selectTipoControlRiesgoCrediticio").val() == 0) {
		alert("Debe seleccionar un tipo de control de riesgo.");
		return false;
	}
	
	var cantidadRegistros = $("#inputTipoControlRiesgoCrediticioTamanoSubconjunto").val();
	metadataConsulta.tamanoSubconjunto = cantidadRegistros;
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		if (confirm("Se controlará el riesgo crediticio para " + cantidadRegistros + " registros.")) {
			ACMInterfaceContratoDWR.controlarRiesgoCrediticio(
				metadataConsulta,
				$("#selectTipoControlRiesgoCrediticioEmpresa").val(),
				$("#selectTipoControlRiesgoCrediticio").val(),
				{
					callback: function(data) {
						alert("Operación exitosa.");
						
						inputCancelarSeleccionTipoControlRiesgoCrediticioOnClick();
						
						reloadData();
					}, async: false
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		if (confirm("Se controlará el riesgo crediticio para " + cantidadRegistros + " registros.")) {
			ACMInterfacePrepagoDWR.controlarRiesgoCrediticio(
				metadataConsulta,
				$("#selectTipoControlRiesgoCrediticioEmpresa").val(),
				$("#selectTipoControlRiesgoCrediticio").val(),
				{
					callback: function(data) {
						alert("Operación exitosa.");
						
						inputCancelarSeleccionTipoControlRiesgoCrediticioOnClick();
						
						reloadData();
					}, async: false
				}
			);
		}
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
}

function inputControlarRiesgoCrediticioSubconjuntoOnClick(event) {
	$("#selectTipoControlRiesgoCrediticioEmpresa > option").remove();
	$("#selectTipoControlRiesgoCrediticio > option").remove();
	
	$("#selectTipoControlRiesgoCrediticioEmpresa").append("<option value='0'>Seleccione...</option>");
	$("#selectTipoControlRiesgoCrediticio").append("<option value='0'>Seleccione...</option>");
	
	UsuarioRolEmpresaDWR.listEmpresasByContext(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectTipoControlRiesgoCrediticioEmpresa").append(html);
			}, async: false
		}
	);
	
	TipoControlRiesgoCrediticioDWR.list(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectTipoControlRiesgoCrediticio").append(html);
			}, async: false
		}
	);
	
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	$("#inputTipoControlRiesgoCrediticioTamanoSubconjunto").val(metadataConsulta.tamanoSubconjunto);
	
	showPopUp(document.getElementById("divIFrameSeleccionTipoControlRiesgoCrediticio"));
}

function inputListaNegraOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		if (confirm("Se agregarán " + grid.getCount() + " registros a la lista negra.")) {
			ACMInterfaceContratoDWR.agregarAListaNegra(
				metadataConsulta,
				{
					callback: function(data) {
						reloadData();
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		if (confirm("Se agregarán " + grid.getCount() + " registros a la lista negra.")) {
			ACMInterfacePrepagoDWR.agregarAListaNegra(
				metadataConsulta,
				{
					callback: function(data) {
						reloadData();
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "sinDatos") {
		if (confirm("Se agregarán " + grid.getCount() + " registros a la lista negra.")) {
			ACMInterfaceMidDWR.agregarAListaNegraSinDatos(
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