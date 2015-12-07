var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_ACTIVACION = 9;

var filtroDinamico = new FiltroDinamico(
	{
		tdContratoMid: { campo: "contrato.mid", descripcion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
		tdContratoFinContrato: { campo: "contrato.fechaFinContrato", descripcion: "Fin de contrato", tipo: __TIPO_CAMPO_FECHA },
		tdContratoNuevoPlan: { campo: "contrato.nuevoPlan", descripcion: "Plan", tipo: __TIPO_CAMPO_STRING },
		tdContratoDocumento: { campo: "contrato.documento", descripcion: "Documento", tipo: __TIPO_CAMPO_STRING },
		tdContratoNumeroContrato: { campo: "contrato.numeroContrato", descripcion: "Nro. contrato", tipo: __TIPO_CAMPO_NUMERICO },
		tdContratoNombre: { campo: "contrato.nombre", descripcion: "Nombre", tipo: __TIPO_CAMPO_STRING },
		tdContratoDireccion: { campo: "contrato.direccion", descripcion: "Dirección", tipo: __TIPO_CAMPO_STRING },
		tdContratoLocalidad: { campo: "contrato.localidad", descripcion: "Localidad", tipo: __TIPO_CAMPO_STRING },
		tdContratoEquipo: { campo: "contrato.equipo", descripcion: "Equipo", tipo: __TIPO_CAMPO_STRING },
		tdActivador: { campo: "contrato.usuario.nombre", descripcion: "Activador", tipo: __TIPO_CAMPO_STRING },
		tdEmpresa: { campo: "contrato.empresa.nombre", descripcion: "Empresa", tipo: __TIPO_CAMPO_STRING },
		tdActivarEn: { campo: "contrato.fechaActivarEn", descripcion: "Activar", tipo: __TIPO_CAMPO_STRING },
		tdEstado: { campo: "contrato.estado.nombre", descripcion: "Estado", tipo: __TIPO_CAMPO_STRING }
	}, reloadData
);

var grid = null;

$(document).ready(function() {
	$("#divButtonAsignar").hide();
	$("#divButtonExportarAExcel").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if ((data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_ACTIVACION)
						|| (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR)) {
						$("#divButtonAsignar").show();
						$("#divButtonExportarAExcel").show();
						
						grid = new Grid(
							document.getElementById("divTableContratos"),
							{
								tdContratoNumeroTramite: { campo: "contrato.numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
								tdContratoMid: { campo: "contrato.mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
								tdEmpresa: { campo: "contrato.empresa.nombre", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_STRING },
								tdContratoFinContrato: { campo: "contrato.fechaFinContrato", descripcion: "Fin de contrato", abreviacion: "Fin", tipo: __TIPO_CAMPO_FECHA },
								tdContratoDocumento: { campo: "contrato.documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
								tdContratoNuevoPlan: { campo: "contrato.nuevoPlan", descripcion: "Nuevo plan", abreviacion: "Nuevo plan", tipo: __TIPO_CAMPO_STRING },
								tdContratoEquipo: { campo: "contrato.producto.descripcion", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_STRING },
								tdContratoTelefonoContacto: { campo: "contrato.telefonoContacto", descripcion: "Teléfono contacto", abreviacion: "Teléfono", tipo: __TIPO_CAMPO_STRING },
								tdContratoNumeroSerie: { campo: "contrato.numeroSerie", descripcion: "Número de serie", abreviacion: "Serie", tipo: __TIPO_CAMPO_STRING },
								tdContratoObservaciones: { campo: "contrato.observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING },
								tdActivador: { campo: "contrato.usuario.nombre", descripcion: "Activador", abreviacion: "Activador", tipo: __TIPO_CAMPO_STRING },
								tdEstado: { campo: "contrato.estado.nombre", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_STRING },
								tdEstadoId: { campo: "contrato.estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_NUMERICO, oculto: true }
							}, 
							reloadData,
							trContratoOnClick
						);
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleTripleSize");
						
						break;
					}
				}
				
				if (grid == null) {
					grid = new Grid(
						document.getElementById("divTableContratos"),
						{
							tdContratoNumeroTramite: { campo: "contrato.numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
							tdContratoMid: { campo: "contrato.mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
							tdEmpresa: { campo: "contrato.empresa.nombre", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_STRING },
							tdContratoFinContrato: { campo: "contrato.fechaFinContrato", descripcion: "Fin de contrato", abreviacion: "Fin", tipo: __TIPO_CAMPO_FECHA },
							tdContratoDocumento: { campo: "contrato.documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
							tdContratoLocalidad: { campo: "contrato.localidad", descripcion: "Localidad", abreviacion: "Localidad", tipo: __TIPO_CAMPO_STRING },
							tdContratoNuevoPlan: { campo: "contrato.nuevoPlan", descripcion: "Nuevo plan", abreviacion: "Nuevo plan", tipo: __TIPO_CAMPO_STRING },
							tdContratoEquipo: { campo: "contrato.producto.descripcion", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_STRING },
							tdContratoTelefonoContacto: { campo: "contrato.telefonoContacto", descripcion: "Teléfono contacto", abreviacion: "Teléfono", tipo: __TIPO_CAMPO_STRING },
							tdContratoNumeroSerie: { campo: "contrato.numeroSerie", descripcion: "Número de serie", abreviacion: "Serie", tipo: __TIPO_CAMPO_STRING },
							tdContratoObservaciones: { campo: "contrato.observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING },
							tdEstado: { campo: "contrato.estado.nombre", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_STRING },
							tdEstadoId: { campo: "contrato.estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_NUMERICO, oculto: true }
						}, 
						reloadData,
						trContratoOnClick
					);
				}
				
				grid.rebuild();
			}, async: false
		}
	);
	
	reloadData();
});

function reloadData() {
	ContratoRoutingHistoryDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}, async: false
		}
	);
}

function inputActualizarOnClick(event, element) {
	reloadData();
}

function trContratoOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var estadoId = $(target).find(".tdEstadoId > div").text();
	
	var formMode = __FORM_MODE_READ;
	if (estadoId == __ESTADO_ACTIVAR) {
		formMode = __FORM_MODE_ACTIVACION;
	}
	
	document.getElementById("iFrameContrato").src = "/LogisticaWEB/pages/contrato/contrato.jsp?m=" + formMode + "&cid=" + $(target).attr("cid") + "&crhid=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameContrato"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#selectActivador").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputAsignarOnClick() {
	$("#selectActivador > option").remove();
	
	$("#selectActivador").append("<option value='0'>Seleccione...</option>");
	
	UsuarioRolEmpresaDWR.listActivadoresByContext(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectActivador").append(html);
			}, async: false
		}
	);
	
	showPopUp(document.getElementById("divIFrameSeleccionActivador"));
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameSeleccionActivador"));
	
	$("#selectActivador").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputAceptarOnClick(event, element) {
	if ($("#selectActivador").val() != "0") {
		var activador = {
			id: $("#selectActivador").val()
		};
		
		metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
		metadataConsulta.tamanoSubconjunto = 
			Math.min(
				$("#inputTamanoSubconjunto").val(),
				$("#divCantidadRegistros").text()
			);
		
		if (confirm("Se asignarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			ContratoRoutingHistoryDWR.asignarActivador(
				activador,
				metadataConsulta,
				{
					callback: function(data) {
						reloadData();
					}, async: false
				}
			);
		}
	} else {
		alert("Debe seleccionar un activador.");
	}
}

function inputExportarAExcelOnClick(event, element) {
	ContratoRoutingHistoryDWR.exportarAExcel(
		filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
				document.getElementById("formExportarAExcel").submit();
			}, async: false
		}
	);
}