var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_DISTRIBUCION = 7;
var __ROL_COORDINADOR_DISTRIBUCION = 11;

var grid = null;

$(document).ready(function() {
	$("#divButtonAsignar").hide();
	$("#divButtonExportarAExcel").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if ((data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_DISTRIBUCION)
						|| (data.usuarioRolEmpresas[i].rol.id == __ROL_COORDINADOR_DISTRIBUCION)
						|| (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR)) {
						$("#divButtonAsignar").show();
						$("#divButtonExportarAExcel").show();
						
						grid = new Grid(
							document.getElementById("divTableContratos"),
							{
								tdContratoNumeroTramite: { campo: "contrato.numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
								tdContratoMid: { campo: "contrato.mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
								tdEmpresa: { campo: "contrato.empresa.nombre", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_STRING },
								tdCodigoPosal: { campo: "contrato.codigoPostal", descripcion: "Código postal", abreviacion: "C.P.", tipo: __TIPO_CAMPO_NUMERICO },
								tdContratoEquipo: { campo: "contrato.producto.descripcion", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_STRING },
								tdContratoPrecio: { campo: "contrato.precio", descripcion: "Precio", abreviacion: "Precio", tipo: __TIPO_CAMPO_NUMERICO },
								tdContratoFechaEntrega: { campo: "contrato.fechaEntrega", descripcion: "Fecha de entrega", abreviacion: "Entrega", tipo: __TIPO_CAMPO_FECHA },
								tdContratoDireccionEntrega: { campo: "contrato.direccionEntrega", descripcion: "Dirección entrega", abreviacion: "Dir. entrega", tipo: __TIPO_CAMPO_STRING },
								tdContratoZona: { campo: "contrato.zona.descripcion", descripcion: "Zona", abreviacion: "Zona", tipo: __TIPO_CAMPO_STRING },
								tdContratoNumeroSerie: { campo: "contrato.numeroSerie", descripcion: "Número de serie", abreviacion: "Serie", tipo: __TIPO_CAMPO_STRING },
								tdDistribuidor: { campo: "contrato.usuario.nombre", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_STRING },
								tdFechaEntregaDistribuidor: { campo: "contrato.fechaEntregaDistribuidor", descripcion: "Entregado", abreviacion: "Entregado", tipo: __TIPO_CAMPO_FECHA },
								tdFechaDevolucionDistribuidor: { campo: "contrato.fechaDevolucionDistribuidor", descripcion: "Devuelto", abreviacion: "Devuelto", tipo: __TIPO_CAMPO_FECHA },
								tdActivador: { campo: "contrato.activador.nombre", descripcion: "Activador", abreviacion: "Activador", tipo: __TIPO_CAMPO_STRING },
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
	if (estadoId == __ESTADO_DISTRIBUIR) {
		formMode = __FORM_MODE_DISTRIBUCION;
	} else if (estadoId == __ESTADO_RECOORDINAR || estadoId == __ESTADO_FALTA_DOCUMENTACION) {
		formMode = __FORM_MODE_RECOORDINACION;
	}
	
	document.getElementById("iFrameContrato").src = "/LogisticaWEB/pages/contrato/contrato.jsp?m=" + formMode + "&cid=" + $(target).attr("cid") + "&crhid=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameContrato"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#selectDistribuidor").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputAsignarOnClick() {
	$("#selectDistribuidor > option").remove();
	
	$("#selectDistribuidor").append("<option value='0'>Seleccione...</option>");
	
	UsuarioRolEmpresaDWR.listDistribuidoresByContext(
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

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameSeleccionDistribuidor"));
	
	$("#selectDistribuidor").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputAceptarOnClick(event, element) {
	if ($("#selectDistribuidor").val() != "0") {
		var distribuidor = {
			id: $("#selectDistribuidor").val()
		};
		
		metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
		metadataConsulta.tamanoSubconjunto = 
			Math.min(
				$("#inputTamanoSubconjunto").val(),
				$("#divCantidadRegistros").text()
			);
		
		if (confirm("Se asignarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			ContratoRoutingHistoryDWR.asignarDistribuidor(
				distribuidor,
				metadataConsulta,
				{
					callback: function(data) {
						reloadData();
					}, async: false
				}
			);
		}
	} else {
		alert("Debe seleccionar un distribuidor.");
	}
}

function inputExportarAExcelOnClick(event, element) {
	ContratoRoutingHistoryDWR.exportarAExcel(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
				document.getElementById("formExportarAExcel").submit();
			}, async: false
		}
	);
}