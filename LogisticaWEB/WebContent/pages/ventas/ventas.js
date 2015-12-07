var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_CALL_CENTER = 3;

var grid = null;
		
$(document).ready(function() {
	$("#divButtonAsignar").hide();
	$("#divButtonSubirArchivo").hide();
	$("#divButtonAgregarMid").hide();
	$("#divButtonExportarAExcel").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if ((data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_CALL_CENTER)
						|| (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR)){
						$("#divButtonAsignar").show();
						$("#divButtonSubirArchivo").show();
						$("#divButtonAgregarMid").show();
						$("#divButtonExportarAExcel").show();
						
						grid = new Grid(
							document.getElementById("divTableContratos"),
							{
								tdContratoMid: { campo: "contrato.mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
								tdContratoFinContrato: { campo: "contrato.fechaFinContrato", abreviacion: "Fin", descripcion: "Fin de contrato", tipo: __TIPO_CAMPO_FECHA },
								tdContratoTipoContratoDescripcion: { campo: "contrato.tipoContratoDescripcion", abreviacion: "Plan", descripcion: "Plan actual", tipo: __TIPO_CAMPO_STRING },
								tdContratoDocumento: { campo: "contrato.documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
								tdFechaVenta: { campo: "contrato.fechaVenta", descripcion: "Fecha de venta", abreviacion: "Vendido", tipo: __TIPO_CAMPO_FECHA },
								tdContratoLocalidad: { campo: "contrato.localidad", descripcion: "Localidad", abreviacion: "Localidad", tipo: __TIPO_CAMPO_STRING },
								tdContratoEquipo: { campo: "contrato.producto.descripcion", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_STRING },
								tdContratoObservaciones: { campo: "contrato.observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING },
								tdContratoTelefonoContacto: { campo: "contrato.telefonoContacto", descripcion: "Teléfono contacto", abreviacion: "Teléfono", tipo: __TIPO_CAMPO_STRING },
								tdContratoEmail: { campo: "contrato.email", descripcion: "Email", abreviacion: "Email", tipo: __TIPO_CAMPO_STRING },
								tdVendedor: { campo: "contrato.usuario.nombre", descripcion: "Vendedor", abreviacion: "Vendedor", tipo: __TIPO_CAMPO_STRING },
								tdEstado: { campo: "contrato.estado.nombre", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_STRING },
								tdEstadoId: { campo: "contrato.estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_NUMERICO, oculto: true }
							}, 
							reloadData,
							trContratoOnClick
						);
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleQuintupleSize");
						break;
					}
				}
				
				if (grid == null) {
					grid = new Grid(
						document.getElementById("divTableContratos"),
						{
							tdContratoMid: { campo: "contrato.mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
							tdContratoFinContrato: { campo: "contrato.fechaFinContrato", abreviacion: "Fin", descripcion: "Fin de contrato", tipo: __TIPO_CAMPO_FECHA },
							tdContratoTipoContratoDescripcion: { campo: "contrato.tipoContratoDescripcion", abreviacion: "Plan", descripcion: "Plan actual", tipo: __TIPO_CAMPO_STRING },
							tdContratoNuevoPlan: { campo: "contrato.nuevoPlan", descripcion: "Nuevo plan", abreviacion: "Nuevo plan", tipo: __TIPO_CAMPO_STRING },
							tdContratoDocumento: { campo: "contrato.documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
							tdContratoNombre: { campo: "contrato.nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING },
							tdContratoDireccionFactura: { campo: "contrato.direccionFactura", descripcion: "Dirección factura", abreviacion: "Dirección", tipo: __TIPO_CAMPO_STRING },
							tdContratoLocalidad: { campo: "contrato.localidad", descripcion: "Localidad", abreviacion: "Localidad", tipo: __TIPO_CAMPO_STRING },
							tdContratoZona: { campo: "contrato.zona.descripcion", descripcion: "Zona", abreviacion: "Zona", tipo: __TIPO_CAMPO_STRING },
							tdContratoEquipo: { campo: "contrato.producto.descripcion", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_STRING },
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

	if (message != null) {
		if (confirm(message.replace(new RegExp("\\|", "g"), "\n"))) {
			ContratoRoutingHistoryDWR.procesarArchivoEmpresa(
				fileName,
				empresaId,
				{
					callback: function(data) {
						reloadData();
					}, async: false
				}
			);
		}
	}
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
	if (estadoId == __ESTADO_LLAMAR
		|| estadoId == __ESTADO_RELLAMAR
		|| estadoId == __ESTADO_RECHAZADO
		|| estadoId == __ESTADO_VENDIDO
		|| estadoId == __ESTADO_REAGENDAR) {
		formMode = __FORM_MODE_VENTA;
	}
	
	document.getElementById("iFrameContrato").src = "/LogisticaWEB/pages/contrato/contrato.jsp?m=" + formMode + "&cid=" + $(target).attr("cid") + "&crhid=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameContrato"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#selectVendedor").val("0");
	$("#selectEmpresa").val("0");
	$("#textareaObservaciones").val("");
	$("#inputArchivo").val("");
	
	reloadData();
}

function inputSubirArchivoOnClick(event, element) {
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
	
	showPopUp(document.getElementById("divIFrameImportacionArchivo"));
}

function inputAsignarOnClick() {
	$("#selectVendedor > option").remove();
	
	$("#selectVendedor").append("<option value='0'>Seleccione...</option>");
	
	UsuarioRolEmpresaDWR.listVendedoresByContext(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectVendedor").append(html);
			}, async: false
		}
	);
	
	showPopUp(document.getElementById("divIFrameSeleccionVendedor"));
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameSeleccionVendedor"));
	
	$("#selectVendedor").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputAceptarOnClick(event, element) {
	if ($("#selectVendedor").val() != "0") {
		var vendedor = {
			id: $("#selectVendedor").val()
		};
		
		metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
		metadataConsulta.tamanoSubconjunto = 
			Math.min(
				$("#inputTamanoSubconjunto").val(),
				$("#divCantidadRegistros").text()
			);
		
		if (confirm("Se asignarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			ContratoRoutingHistoryDWR.asignarVentas(
				vendedor,
				metadataConsulta,
				{
					callback: function(data) {
						reloadData();
					}, async: false
				}
			);
		}
	} else {
		alert("Debe seleccionar un vendedor.");
	}
}

function inputAceptarSubirArchivoOnClick(event, element) {
	if ($("#selectEmpresa").val() != "0") {
		$("#formSubirArchivo").submit();
	} else {
		alert("Debe seleccionar una empresa.");
	}
}

function inputAgregarMidOnClick(event, element) {
	document.getElementById("iFrameContrato").src = "/LogisticaWEB/pages/contrato/contrato.jsp?m=" + __FORM_MODE_NEW;
	showPopUp(document.getElementById("divIFrameContrato"));
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