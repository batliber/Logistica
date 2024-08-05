var accionesHabilitadas = false;

var grid = null;

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
	$("#inputSubirArchivo").prop("disabled", true);
	
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
			$("#inputSubirArchivo").prop("disabled", true);
			
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
			$("#inputSubirArchivo").prop("disabled", false);
			
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
		$("#inputSubirArchivo").prop("disabled", !habilitar);
		
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
			noOp
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
			noOp
		);
	} else if ($("#selectTipoRegistro").val() == "numeroContrato") {
		grid = new Grid(
			document.getElementById("divTabla"),
			{
				tdNumeroContratoNumero: { campo: "numeroContrato", descripcion: "Número de contrato", abreviacion: "Nro. contrato", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
				tdNumeroContratoEstado: { campo: "estado.descripcion", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listACMInterfaceEstados, clave: "id", valor: "descripcion" }, ancho: 150 },
				tdNumeroContratoFcre: { campo: "fcre", descripcion: "Creado", abreviacion: "Creado", tipo: __TIPO_CAMPO_FECHA_HORA },
				tdNumeroContratoFact: { campo: "fact", descripcion: "Obtenido", abreviacion: "Obtenido", tipo: __TIPO_CAMPO_FECHA_HORA }
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
				tdSinDatosEstado: { campo: "estado.descripcion", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listACMInterfaceEstados, clave: "id", valor: "descripcion" }, ancho: 150 },
				tdSinDatosFact: { campo: "fact", descripcion: "Obtenido", abreviacion: "Obtenido", tipo: __TIPO_CAMPO_FECHA_HORA }
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
		url: "/LogisticaWEB/RESTFacade/ACMInterfaceContratoREST/listTipoContratosContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	});
}

function noOp() {
	
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	var listURL = "/LogisticaWEB/RESTFacade/ACMInterfaceMidREST/listSinDatosContextAware";
	var countURL = "/LogisticaWEB/RESTFacade/ACMInterfaceMidREST/countSinDatosContextAware";
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		listURL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoREST/listContextAware";
		countURL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoREST/countContextAware";
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		listURL = "/LogisticaWEB/RESTFacade/ACMInterfacePrepagoREST/listContextAware";
		countURL = "/LogisticaWEB/RESTFacade/ACMInterfacePrepagoREST/countContextAware";
	} else if ($("#selectTipoRegistro").val() == "listaNegra") {
		listURL = "/LogisticaWEB/RESTFacade/ACMInterfaceListaNegraREST/listContextAware";
		countURL = "/LogisticaWEB/RESTFacade/ACMInterfaceListaNegraREST/countContextAware";
	} else if ($("#selectTipoRegistro").val() == "numeroContrato") {
		listURL = "/LogisticaWEB/RESTFacade/ACMInterfaceNumeroContratoREST/listContextAware";
		countURL = "/LogisticaWEB/RESTFacade/ACMInterfaceNumeroContratoREST/countContextAware";
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

function inputExportarAExcelOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	metadataConsulta.tamanoSubconjunto = grid.getCount();
	
	exportarAExcel(metadataConsulta);
}

function inputExportarSubconjuntoOnClick(event) {
	exportarAExcel(grid.filtroDinamico.calcularMetadataConsulta());
}

function exportarAExcel(metadataConsulta) {
	var URL = null;

	if ($("#selectTipoRegistro").val() == "contrato") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoREST/exportarAExcel";
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfacePrepagoREST/exportarAExcel";
	} else if ($("#selectTipoRegistro").val() == "listaNegra") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfaceListaNegraREST/exportarAExcel";
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
		fillSelect("selectEmpresa", data, "id", "nombre");
	});
	
	showPopUp(document.getElementById("divIFrameSeleccionEmpresa"));
}

function inputAsignarSubconjuntoOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	$("#inputTamanoSubconjuntoAsignacion").val(metadataConsulta.tamanoSubconjunto);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
	}).then(function(data) {
		fillSelect("selectEmpresa", data, "id", "nombre");
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
		
		if (metadataConsulta.tamanoSubconjunto > 1000) {
			alert("Se asignarán más de 1000 registros. Seleccione un subconjunto menor.");
			
			return;
		}
		
		if ($("#selectTipoRegistro").val() == "contrato") {
			preprocesarURL = 
				"/LogisticaWEB/RESTFacade/ACMInterfaceContratoREST/preprocesarAsignacionByEmpresa";
			procesarURL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoREST/asignarByEmpresa";
		} else if ($("#selectTipoRegistro").val() == "prepago") {
			preprocesarURL = 
				"/LogisticaWEB/RESTFacade/ACMInterfacePrepagoREST/preprocesarAsignacionByEmpresa";
			procesarURL = "/LogisticaWEB/RESTFacade/ACMInterfacePrepagoREST/asignarByEmpresa";
		} else {
			alert("Funcionalidad no habilitada para el tipo de registro.");
		}
	} else {
		alert("Debe seleccionar una empresa.");
		
		return;
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
		URL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoREST/deshacerAsignacion";
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfacePrepagoREST/deshacerAsignacion";
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

function inputReprocesarOnClick(event) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	metadataConsulta.tamanoSubconjunto = grid.getCount();

	reprocesar(metadataConsulta);
}

function inputReprocesarSubconjuntoOnClick(event) {
	reprocesar(grid.filtroDinamico.calcularMetadataConsulta());
}

function reprocesar(metadataConsulta) {
	var URL = null;
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		$("#selectTipoProcesamiento").val(0);
		$("#textareaSeleccionTipoProcesamientoObservaciones").val("");
		
		showPopUp(document.getElementById("divIFrameSeleccionTipoProcesamiento"));
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfacePrepagoREST/reprocesar";
	} else if ($("#selectTipoRegistro").val() == "numeroContrato") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfaceNumeroContratoREST/reprocesar";
	} else if ($("#selectTipoRegistro").val() == "sinDatos") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfaceMidREST/reprocesarSinDatos";
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
	
	if (URL != null) {
		var observaciones = prompt("Se reprocesarán " + metadataConsulta.tamanoSubconjunto + " registros.");
		
		if (observaciones != null) {
			$.ajax({
				url: URL,
				method: "POST",
				contentType: 'application/json',
				data: JSON.stringify({
					"metadataConsulta": metadataConsulta,
					"observaciones": observaciones
				})
			}).then(function(data) {
				alert("Operación exitosa.");
				
				reloadData();
			});
		}
	}
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
	
	var URL = null;
	
	if (tipoProcesamiento == 0) {
		alert("Debe seleccionar un tipo de procesamiento.");
	} else if (tipoProcesamiento == 1) {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoREST/reprocesarPorMID";
	} else {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoREST/reprocesarPorNumeroContrato";
	}
	
	if (URL != null) {
		$.ajax({
			url: URL,
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify({
				"metadataConsulta": grid.filtroDinamico.calcularMetadataConsulta(),
				"observaciones": observaciones
			})
		}).then(function(data) {
			alert("Operación exitosa.");
			
			inputCancelarSeleccionTipoProcesamientoOnClick();
			
			reloadData();
		});
	}
}

function inputControlarRiesgoCrediticioOnClick(event) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
	}).then(function(data) {
		fillSelect("selectTipoControlRiesgoCrediticioEmpresa", data, "id", "nombre");
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/TipoControlRiesgoCrediticioREST/list"
	}).then(function(data) {
		fillSelect("selectTipoControlRiesgoCrediticio", data, "id", "descripcion");
	});
	
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
	if ($("#selectTipoControlRiesgoCrediticioEmpresa").val() == 0) {
		alert("Debe seleccionar una Empresa.");
		return false;
	}
	
	if ($("#selectTipoControlRiesgoCrediticio").val() == 0) {
		alert("Debe seleccionar un tipo de control de riesgo.");
		return false;
	}
	
	var cantidadRegistros = $("#inputTipoControlRiesgoCrediticioTamanoSubconjunto").val();
	
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	metadataConsulta.tamanoSubconjunto = cantidadRegistros;
	
	var URL = null;
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoREST/controlarRiesgoCrediticio";
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfacePrepagoREST/controlarRiesgoCrediticio";
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
	
	if (URL != null) {
		if (confirm("Se controlará el riesgo crediticio para " + cantidadRegistros + " registros.")) {
			$.ajax({
				url: URL,
				method: "POST",
				contentType: 'application/json',
				data: JSON.stringify({
					"metadataConsulta": grid.filtroDinamico.calcularMetadataConsulta(),
					"empresaId": $("#selectTipoControlRiesgoCrediticioEmpresa").val(),
					"tipoControlRiesgoCrediticioId": $("#selectTipoControlRiesgoCrediticio").val()
				})
			}).then(function(data) {
//				if (data != null) {
					alert("Operación exitosa.");
					
					inputCancelarSeleccionTipoControlRiesgoCrediticioOnClick();
					
					reloadData();
//				}
			});
		}
	}
}

function inputControlarRiesgoCrediticioSubconjuntoOnClick(event) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
	}).then(function(data) {
		fillSelect("selectTipoControlRiesgoCrediticioEmpresa", data, "id", "nombre");
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/TipoControlRiesgoCrediticioREST/list"
	}).then(function(data) {
		fillSelect("selectTipoControlRiesgoCrediticio", data, "id", "descripcion");
	});
	
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	$("#inputTipoControlRiesgoCrediticioTamanoSubconjunto").val(metadataConsulta.tamanoSubconjunto);
	
	showPopUp(document.getElementById("divIFrameSeleccionTipoControlRiesgoCrediticio"));
}

function inputListaNegraOnClick(event) {
	var URL = null;
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfaceContratoREST/agregarAListaNegra";
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfacePrepagoREST/agregarAListaNegra";
	} else if ($("#selectTipoRegistro").val() == "sinDatos") {
		URL = "/LogisticaWEB/RESTFacade/ACMInterfaceMidREST/agregarAListaNegraSinDatos";
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
	
	if (URL != null) {
		if (confirm("Se agregarán " + grid.getCount() + " registros a la lista negra.")) {
			$.ajax({
				url: URL,
				method: "POST",
				contentType: 'application/json',
				data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
			}).then(function(data) {
				if (data != null) {
					alert("Operación exitosa.");
					
					reloadData();
				}
			});
		}
	}
}

function inputSubirArchivoOnClick(event, element) {
	showPopUp(document.getElementById("divIFrameImportacionArchivo"));
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameImportacionArchivo"));
	
	reloadData();
}

function inputAceptarSubirArchivoOnClick(event, element) {
	var formData = new FormData(document.getElementById("formSubirArchivo"));
	
	$.ajax({
		url: '/LogisticaWEB/Upload', 
		type: 'POST',
		data: formData,
		processData: false,
		contentType: false
	}).then(function(data) {
		if (confirm(data.message.replace(new RegExp("\\|", "g"), "\n"))) {
			$.ajax({
				url: '/LogisticaWEB/RESTFacade/ACMInterfaceContratoREST/procesarArchivo', 
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					"nombre": data.fileName
				})
			}).then(function(data) {
				if (data != null) {
					alert(data.mensaje.replace(new RegExp("\\|", "g"), "\n"));
				}
				
				reloadData();
			});
		}
	}, function(data) {
		alert(data);
	});
}