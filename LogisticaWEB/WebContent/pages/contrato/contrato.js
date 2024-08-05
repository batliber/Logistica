var __EMPRESA_ID_ATENCION_CLIENTES = 75559430;

var __SEMANAS_DISPONIBILIDAD_FECHA_ENTREGA = 4;
var __FORMA_PAGO_TARJETA_ID = 3;
var __FORMA_PAGO_NUESTRO_CREDITO_ID = 4;
var __FORMA_PAGO_NUESTRO_CREDITO_CUOTAS_DEFAULT = 12;
var __ELEGIBILIDAD_FINANCIACION_RECHAZAR = 0;
var __ELEGIBILIDAD_FINANCIACION_REALIZAR_CLEARING = 1;
var __ELEGIBILIDAD_FINANCIACION_NO_REALIZAR_CLEARING = 2;
var __ELEGIBILIDAD_SIN_ANALISIS = -1;
var __TIPO_ARCHIVO_ADJUNTO_REMITO_RIVERGREEN = 2;
var __TIPO_ARCHIVO_ADJUNTO_EFACTURA_ANTEL = 5;

var gridArchivosAdjuntos = null;
var gridContratosRelacionados = null;

$(document).ready(init);

function init() {
	initTabbedPanel();
	initTabArchivosAdjuntos();
	initTabContratosRelacionados();
	
	refinarFormRemote();
	
	$("#selectFechaEntrega").append("<option value='0'>Seleccione...</option>");
	$("#selectFormaPago").append("<option value='0'>Seleccione...</option>");
	
	initEmpresas()
		.then(function(data) {
			initNuevoPlan()
				.then(function(data) {
					initMotivoCambioPlan()
						.then(function(data) {
							initMoneda()
								.then(function(data) {
									initModalidadVenta()
										.then(function(data) {
											initAtencionClienteTipoContacto()
												.then(function(data) {
													initAtencionClienteConcepto()
														.then(function(data) {
															initTarjetaCredito()
																.then(function(data) {
																	initTipoDocumento()
																		.then(function(data) {
																			initSexo()
																				.then(function(data) {
																					initDepartamento()
																						.then(function(data) {
																							initBarrio()
																								.then(function(data) {
																									initZona()
																										.then(function(data) {
																											initTurno()
																												.then(function(data) {
																													initRepuestaTecnicaComercial()
																														.then(function(data) {
																															initTipoProducto()
																																.then(function(data) {
																																	if (id != null) {
																																		reloadContrato();
																																	}
																																});
																														});
																												});
																										});
																								});
																						});
																				});
																		});
																});
														});
												});
										});
								});
						});
				});
		});
}

function initTabbedPanel() {
	$("#divTab2").hide();
	$("#divTab3").hide();
	$("#divTab4").hide();
	
	$(".divTabHeader > div").click(function(eventObject) {
		var element = $(eventObject.currentTarget);
		var index = element.attr("id").substring(element.attr("id").length - 1);
		
		if (element.attr("class") != "divTabTitleFiller") {
			var tabs = $(".divTabbedPanel > .divTab");
			for (var i=0; i<tabs.length; i++) {
				if ((i + 1) == index) {
					$("#divTab" + (i + 1)).show();
					$("#divTabTitle" + (i + 1)).attr("class", "divTabTitleSelected");
				} else {
					$("#divTab" + (i + 1)).hide();
					$("#divTabTitle" + (i + 1)).attr("class", "divTabTitle");
				}
			}
		}
	});
}

function initTabArchivosAdjuntos() {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/TipoArchivoAdjuntoREST/list"
	}).then(function(data) {
		fillSelect(
			"selectTipoArchivoAdjunto", data, "id", "descripcion"
		);
		
		gridArchivosAdjuntos = new Grid(
			document.getElementById("divTableArchivosAdjuntos"),
			{
				tdTipo: { campo: "tipoArchivoAdjunto.descripcion", descripcion: "Tipo", abreviacion: "Tipo", tipo: __TIPO_CAMPO_STRING, ancho: 150 },
				tdURL: { campo: "urlLink", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 350 },
				tdFechaSubida: { campo: "fechaSubida", descripcion: "Fecha", abreviacion: "Fecha", tipo: __TIPO_CAMPO_FECHA_HORA } 
			}, 
			false,
			reloadArchivosAdjuntosData,
			trArchivosAdjuntosOnClick,
			null,
			6
		);
		
		gridArchivosAdjuntos.rebuild();
		
		var html = "";
		
		html +=
			"<div class='divGalleryContent'>&nbsp;</div>";
		
		$("#divResultadoEntregaDistribucionDocumentos").html(html);
	});
}

function reloadArchivosAdjuntosData(id, contrato) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoArchivoAdjuntoREST/listByContratoId/" + id
	}).then(function(archivoAdjuntos) {
		var data = {
			cantidadRegistros: 0,
			registrosMuestra: []
		};
		
		for (var i=0; i<archivoAdjuntos.length; i++) {
			if (mode == __FORM_MODE_ANTEL) {
				if (archivoAdjuntos[i].tipoArchivoAdjunto != null
					&& archivoAdjuntos[i].tipoArchivoAdjunto.id == __TIPO_ARCHIVO_ADJUNTO_REMITO_RIVERGREEN) {
					var url =
						"<a href=\"/LogisticaWEB/Download?fn=" + archivoAdjuntos[i].url + "&f=s\">" 
							+ archivoAdjuntos[i].url 
						+ "</a>";
					
					data.registrosMuestra[data.cantidadRegistros] = archivoAdjuntos[i];
					data.registrosMuestra[data.cantidadRegistros].urlLink = url;
					data.cantidadRegistros++;
				}
			} else {
				if (archivoAdjuntos[i].tipoArchivoAdjunto != null
					&& archivoAdjuntos[i].tipoArchivoAdjunto.id == __TIPO_ARCHIVO_ADJUNTO_EFACTURA_ANTEL) {
					var url =
						"<a href=" + archivoAdjuntos[i].url + ">" 
							+ archivoAdjuntos[i].url
						+ "</a>";
					
					data.registrosMuestra[data.cantidadRegistros] = archivoAdjuntos[i];
					data.registrosMuestra[data.cantidadRegistros].urlLink = url;
					data.cantidadRegistros++;
				} else {	
					var url =
						"<a href=\"/LogisticaWEB/Download?fn=" + archivoAdjuntos[i].url + "&f=s\">" 
							+ archivoAdjuntos[i].url 
						+ "</a>";
					
					data.registrosMuestra[data.cantidadRegistros] = archivoAdjuntos[i];
					data.registrosMuestra[data.cantidadRegistros].urlLink = url;
					data.cantidadRegistros++;
				}
			}
		}
		
		var html = "";
		var imgGalleryClass = "imgGalleryActive";
		for (var i=0; i<data.registrosMuestra.length; i++) {
			var registroMuestra = data.registrosMuestra[i];
			
			if (i > 0) {
				imgGalleryClass = "imgGalleryInactive";
			}
			
			if (registroMuestra.tipoArchivoAdjunto.id != __TIPO_ARCHIVO_ADJUNTO_EFACTURA_ANTEL) {
				html += "<img class='" + imgGalleryClass + "'"
					+ " aaid='" + data.registrosMuestra[i].id + "'"
					+ " src='/LogisticaWEB/Stream?fn=" + data.registrosMuestra[i].url + "'/>";
			}
		}
		
		$(".divGalleryContent").html(html != "" ? html : "&nbsp;");
		
		gridArchivosAdjuntos.reload(data);
	});
}

function reloadDireccionesData(id, contrato) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoDireccionREST/listByContratoId/" + id
	}).then(function(direcciones) {
		for (var i=0; i<direcciones.length; i++) {
			var direccion = direcciones[i];
			if (direccion.tipoDireccion.id == 1) {
				$("#divTab1").attr("did", direccion.id);
			} else if (direccion.tipoDireccion.id == 2) {
				$("#divTab2").attr("did", direccion.id);
			}
		}
	});
}

function trArchivosAdjuntosOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var archivoAdjuntoId = $(target).attr("id");
	
	var img = $("img[aaid='" + archivoAdjuntoId + "']");

	$(".imgGalleryActive").attr("class", "imgGalleryInactive");
	$(img).attr("class", "imgGalleryActive");
}

function inputAgregarAdjuntoOnClick(event, element) {
	var inputFile = $("#inputAdjunto").val();
	var tipoArchivoAdjuntoId = $("#selectTipoArchivoAdjunto").val();
	
	if (tipoArchivoAdjuntoId == 0) {
		alert("Debe seleccionar un tipo de adjunto.");
		
		return;
	}
	
	if (inputFile == null || inputFile == "") {
		alert("Debe seleccionar un archivo.");
		
		return;
	}
	
	var formData = new FormData(document.getElementById("formArchivo"));
	formData.append("caller", "contrato_archivo_adjunto");
	formData.append("inputId", id);
	formData.append("tipoArchivoAdjuntoId", tipoArchivoAdjuntoId);
	
	$.ajax({
		url: '/LogisticaWEB/Upload', 
		type: 'POST',
		data: formData,
		processData: false,
		contentType: false
	}).then(function(data) {
		alert(data.message.replace(new RegExp("\\|", "g"), "\n"));
		$("#selectTipoArchivoAdjunto").val(0);
		$("#inputAdjunto").val(null);
		
		reloadArchivosAdjuntosData(id);
	}, function(data) {
		alert(data.message);
		$("#selectTipoArchivoAdjunto").val(0);
		$("#inputAdjunto").val(null);
		
		reloadArchivosAdjuntosData(id);
	});
}

function initTabContratosRelacionados() {
	gridContratosRelacionados = new Grid(
		document.getElementById("divTableContratosRelacionados"),
		{
			tdContratoRelacionadoNumeroTramite: { campo: "contratoRelacionado.numeroTramite", descripcion: "Número de trámite", abreviacion: "Nro. trámite", tipo: __TIPO_CAMPO_NUMERICO, ancho: 150 },
			tdContratoRelacionadoEstado: { campo: "contratoRelacionado.estado.nombre", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_STRING, ancho: 150 },
			tdContratoRelacionadoFcre: { campo: "fcre", descripcion: "Relacionado", abreviacion: "Relacionado", tipo: __TIPO_CAMPO_FECHA_HORA } 
		}, 
		false,
		reloadContratosRelacionadosData,
		trContratosRelacionadosOnClick,
		null,
		6
	);
	
	gridContratosRelacionados.rebuild();
}

function reloadContratosRelacionadosData(id) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoRelacionREST/listByContratoId/" + id
	}).then(function(contratosRelacionados) {
		var data = {
			cantidadRegistros: 0,
			registrosMuestra: []
		};
		
		for (var i=0; i<contratosRelacionados.length; i++) {
			if (mode == __FORM_MODE_ANTEL) {
			
			} else {
				if (contratosRelacionados[i].contratoRelacionado.id == id) {
					contratosRelacionados[i].contratoRelacionado = contratosRelacionados[i].contrato;
				}
				
				data.registrosMuestra[data.cantidadRegistros] = contratosRelacionados[i];
				data.cantidadRegistros++;
			}
		}
		
		gridContratosRelacionados.reload(data);
	});
}

function trContratosRelacionadosOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var contratoRelacionId = $(target).attr("id");
}

function inputAgregarContratoRelacionadoOnClick(event, element) {
	var inputNumeroTramite = $("#inputNumeroTramiteRelacionado").val();
	
	if (inputNumeroTramite == null || inputNumeroTramite == "") {
		alert("Debe seleccionar un número de trámite.");
		
		return;
	}
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/getByNumeroTramite/" + inputNumeroTramite
	}).then(function(data) {
		var contratoRelacion = {
			contrato: {
				id: id
			},
			contratoRelacionado: {
				id: data.id
			}
		};
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoRelacionREST/add",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(contratoRelacion)
		}).then(function(data) {
			if (data != null) {
				alert("Operacion exitosa.");
				
				$("#inputNumeroTramiteRelacionado").val(null);
				
				reloadContratosRelacionadosData();
			}
		});
	});
}

function refinarFormRemote() {
	$(".divButtonBar .divButton").hide();
	$(".divButtonTitleBar div").hide();
	$(".divFormLabelExtended").hide();
	$(".divFormValue").hide();
	
	hideFields(["unidadIndexada", "tasaInteresEfectivaAnual"]);
	$("#divRealizarClearing").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/EntidadVistaREST/getById/" + mode,
		method: "GET",
		contentType: 'application/json',
	}).then(function(data) {
		if (data != null) {
			var visibles = 0;
			for (var i=0; i<data.entidadVistaAcciones.length; i++) {
				var entidadVistaAccion = data.entidadVistaAcciones[i];
				
				if (entidadVistaAccion.visible) {
					var accion = entidadVistaAccion.entidadAccion;
					
					$("#divInput" + accion.nombre).show();
					
					visibles++;
				}
			}
			
			var sizes = [
				"Single", "Double", "Triple", "Fourfold", "Quintuple", "Sextuple", "Septuple", 
				"Octuple", "Ninetuple"
			];
			
			$("#divButtonTitleTripleSize").attr("id", "divButtonTitle" + sizes[visibles - 1] + "Size");
			
			var hidden = [];
			var visibles = [];
			var readOnly = [];
			var required = [];
			for (var i=0; i<data.entidadVistaCampos.length; i++) {
				var entidadVistaCampo = data.entidadVistaCampos[i];
				
				if (!entidadVistaCampo.visible) {
					hidden[hidden.length] = entidadVistaCampo.nombre;
				} else {
					visibles[visibles.length] = entidadVistaCampo.nombre;
				}
				
				if (!entidadVistaCampo.editable) {
					readOnly[readOnly.length] = "div" 
						+ entidadVistaCampo.nombre.substring(0, 1).toUpperCase()
						+ entidadVistaCampo.nombre.substring(1);
				}
				
				if (entidadVistaCampo.obligatorio) {
					required[required.length] = "divLabel" 
						+ entidadVistaCampo.nombre.substring(0, 1).toUpperCase()
						+ entidadVistaCampo.nombre.substring(1);
				}
			}
			
			hideFields(hidden);
			showFields(visibles);
			makeFieldsReadOnly(readOnly);
			setRequiredFields(required);
			
			$("#inputNumeroFacturaRiverGreen").prop("disabled", true);
			
			if (mode == __FORM_MODE_READ) {
				$("#divObservaciones").css("width", "65%");
			} else if (mode == __FORM_MODE_NEW) {
				$("#divButtonTitleFourfoldSize").hide();
				$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
			} else if (mode == __FORM_MODE_NEW_ANTEL) {
				makeFieldsReadOnly([
					"divAgregarAdjunto"
				]);
				
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").hide();
				$("#divButtonTitleTripleSize").attr("id", "divButtonTitleDoubleSize");
			} else if (mode == __FORM_MODE_READ_ANTEL) {
				$("#divObservaciones").css("width", "65%");
				
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").hide();
				$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
			} else if (mode == __FORM_MODE_SUPERVISOR_CALL_CENTER) {
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleSextupleSize");
				$("#divButtonTitleTripleSize").attr("id", "divButtonTitleDoubleSize");
			} else if (mode == __FORM_MODE_VENTA) {
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleSextupleSize");
				$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
			} else if (mode == __FORM_MODE_BACKOFFICE) {
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleQuintupleSize");
			} else if (mode == __FORM_MODE_RECOORDINACION) {
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleQuintupleSize");
				$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
			} else if (mode == __FORM_MODE_DISTRIBUCION) {
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleTripleSize").attr("id", "divButtonTitleDoubleSize");
			} else if (mode == __FORM_MODE_REDISTRIBUCION) {
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
			} else if (mode == __FORM_MODE_ACTIVACION) {
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleSextupleSize");
			} else if (mode == __FORM_MODE_SUPERVISOR_ACTIVACION) {
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleNinetupleSize");
			} else if (mode == __FORM_MODE_ANTEL) {
			} else if (mode == __FORM_MODE_RIESGO_CREDITICIO) {
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleSingleSize");
				$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
			} else if (mode == __FORM_MODE_INSTALACION_FIBRA) {
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleTripleSize");
			} else if (mode == __FORM_MODE_NEW_INSTALACION_FIBRA) {
				makeFieldsReadOnly([
					"divAgregarAdjunto"
				]);
				
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").hide();
				$("#divButtonTitleTripleSize").attr("id", "divButtonTitleDoubleSize");
			} else if (mode == __FORM_MODE_READ_INSTALACION_FIBRA) {
				$("#divObservaciones").css("width", "65%");
				
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").hide();
				$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
			} else if (mode == __FORM_MODE_NEW_ATENCION_CLIENTE) {
				$("#divTabTitle1").hide();
				$("#divTabTitle1").attr("class", "divTabTitle");
				$("#divTab1").hide();
				$("#divTabTitle2").hide();
				$("#divTab3").show();
				$("#divTabTitle3").attr("class", "divTabTitleSelected");
				$("#divTabTitle4").hide();
				
				var data = {
					empresa: { 
						id: __EMPRESA_ID_ATENCION_CLIENTES,
						nombre: "Atencion a Clientes" 
					}
				};
				populateField("empresa", data, "empresa.id", "empresa.nombre", "eid", "empresa.id");
				
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").hide();
				$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
			} else if (mode == __FORM_MODE_ATENCION_CLIENTE_OPERADOR) {
				$("#divTabTitle1").hide();
				$("#divTabTitle1").attr("class", "divTabTitle");
				$("#divTab1").hide();
				$("#divTabTitle2").hide();
				$("#divTab3").show();
				$("#divTabTitle3").attr("class", "divTabTitleSelected");
				$("#divTabTitle4").hide();
				
				$("#divLabelAntelNroTrn").text("Ticket asignado");
				$("#divLabelFechaActivarEn").text("Fecha ingreso ANTEL");
				
				$("#divLabelAntelNroTrn").detach().insertAfter($("#divAtencionClienteRespuestaTecnicaComercial"));
				$("#divAntelNroTrn").detach().insertAfter($("#divLabelAntelNroTrn"));
				$("#divLabelFechaActivarEn").detach().insertAfter($("#divAntelNroTrn"));
				$("#divFechaActivarEn").detach().insertAfter($("#divLabelFechaActivarEn"));
				$("#divLabelAtencionClienteCantidadVeces").detach().insertAfter($("#divFechaActivarEn"));
				$("#divAtencionClienteCantidadVeces").detach().insertAfter($("#divLabelAtencionClienteCantidadVeces"));
				
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleQuintupleSize");
			} else if (mode == __FORM_MODE_ATENCION_CLIENTE_SUPERVISOR) {
				$("#divTabTitle1").hide();
				$("#divTabTitle1").attr("class", "divTabTitle");
				$("#divTab1").hide();
				$("#divTabTitle2").hide();
				$("#divTab3").show();
				$("#divTabTitle3").attr("class", "divTabTitleSelected");
				$("#divTabTitle4").hide();
				
				$("#divLabelAntelNroTrn").text("Ticket asignado");
				$("#divLabelFechaActivarEn").text("Fecha ingreso ANTEL");
				
				$("#divLabelAntelNroTrn").detach().insertAfter($("#divAtencionClienteRespuestaTecnicaComercial"));
				$("#divAntelNroTrn").detach().insertAfter($("#divLabelAntelNroTrn"));
				$("#divLabelFechaActivarEn").detach().insertAfter($("#divAntelNroTrn"));
				$("#divFechaActivarEn").detach().insertAfter($("#divLabelFechaActivarEn"));
				$("#divLabelAtencionClienteCantidadVeces").detach().insertAfter($("#divFechaActivarEn"));
				$("#divAtencionClienteCantidadVeces").detach().insertAfter($("#divLabelAtencionClienteCantidadVeces"));
				
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleQuintupleSize");
			} else if (mode == __FORM_MODE_READ_ATENCION_CLIENTE) {
				$("#divTabTitle1").hide();
				$("#divTabTitle1").attr("class", "divTabTitle");
				$("#divTab1").hide();
				$("#divTabTitle2").hide();
				$("#divTab3").show();
				$("#divTabTitle3").attr("class", "divTabTitleSelected");
				$("#divTabTitle4").hide();
				
				$("#divLabelAntelNroTrn").text("Ticket asignado");
				$("#divLabelFechaActivarEn").text("Fecha ingreso ANTEL");
				
				$("#divLabelAntelNroTrn").detach().insertAfter($("#divAtencionClienteRespuestaTecnicaComercial"));
				$("#divAntelNroTrn").detach().insertAfter($("#divLabelAntelNroTrn"));
				$("#divLabelFechaActivarEn").detach().insertAfter($("#divAntelNroTrn"));
				$("#divFechaActivarEn").detach().insertAfter($("#divLabelFechaActivarEn"));
				$("#divLabelAtencionClienteCantidadVeces").detach().insertAfter($("#divFechaActivarEn"));
				$("#divAtencionClienteCantidadVeces").detach().insertAfter($("#divLabelAtencionClienteCantidadVeces"));
				
				$(".divButtonBarSeparator").show();
				$(".divButtonTitleBar > div").show();
				$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleSingleSize");
			} else if (mode == __FORM_MODE_CONSULTA) {
				$("#divObservaciones").css("width", "65%");
			}
		}
	});
}

function makeFieldsReadOnly(fieldIds) {
	for (var i=0; i<fieldIds.length; i++) {
		$("#" + fieldIds[i]).html("&nbsp;");
	}
}

function setRequiredFields(fieldIds) {
	for (var i=0; i<fieldIds.length; i++) {
		$("#" + fieldIds[i]).addClass("requiredFormLabel");
	}
}

function initEmpresas() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
	}).then(function(data) {
		fillSelect(
			"selectEmpresa", 
			data,
			"id", 
			"nombre"
		);
	});
}

function initNuevoPlan() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/PlanREST/listVigentesMinimal"
	}).then(function(data) {
		if ($("#selectNuevoPlan").length > 0) {
			fillSelect(
				"selectNuevoPlan", 
				data,
				"id", 
				"descripcion"
			);
		}
	});
}

function initMotivoCambioPlan() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/MotivoCambioPlanREST/list"
	}).then(function(data) {
		if ($("#selectMotivoCambioPlan").length > 0) {
			fillSelect(
				"selectMotivoCambioPlan", 
				data,
				"id", 
				"descripcion"
			);
		}
	});
}

function initMoneda() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/MonedaREST/list"
	}).then(function(data) {
		if ($("#selectMoneda").length > 0) {
			fillSelect(
				"selectMoneda", 
				data,
				"id", 
				"simbolo"
			);
		}
	});
}

function initModalidadVenta() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/ModalidadVentaREST/list"
	}).then(function(data) {
		if ($("#selectModalidadVenta").length > 0) {
			fillSelect(
				"selectModalidadVenta", 
				data,
				"id", 
				"descripcion"
			);
		}
	});
}

function initAtencionClienteTipoContacto() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/AtencionClienteTipoContactoREST/list"
	}).then(function(data) {
		if ($("#selectAtencionClienteTipoContacto").length > 0) {
			fillSelect(
				"selectAtencionClienteTipoContacto", 
				data,
				"id", 
				"descripcion"
			);
		}
	});
}

function initAtencionClienteConcepto() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/AtencionClienteConceptoREST/list"
	}).then(function(data) {
		if ($("#selectAtencionClienteConcepto").length > 0) {
			fillSelect(
				"selectAtencionClienteConcepto", 
				data,
				"id", 
				"descripcion"
			);
		}
	});
}

function initTarjetaCredito() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/TarjetaCreditoREST/list"
	}).then(function(data) {
		if ($("#selectTarjetaCredito").length > 0) {
			fillSelect(
				"selectTarjetaCredito", 
				data,
				"id", 
				"nombre"
			);
		
			if ($("#selectCuotas").length > 0) {
				fillSelect(
					"selectCuotas", 
					[
						{ id: 1, valor: 1 },
						{ id: 2, valor: 2 },
						{ id: 3, valor: 3 },
						{ id: 4, valor: 4 },
						{ id: 5, valor: 5 },
						{ id: 6, valor: 6 },
						{ id: 8, valor: 8 },
						{ id: 10, valor: 10 },
						{ id: 12, valor: 12 },
						{ id: 15, valor: 15 },
						{ id: 18, valor: 18 },
						{ id: 21, valor: 21 },
						{ id: 24, valor: 24 }, 
					], 
					"id", 
					"valor"
				);
			}
		
			$("#inputPTF").prop("disabled", true);
			$("#inputValorCuota").prop("disabled", true);
		}
	});
}

function initTipoDocumento() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/TipoDocumentoREST/list"
	}).then(function(data) {
		if ($("#selectTipoDocumento").length > 0) {
			fillSelect(
				"selectTipoDocumento", 
				data,
				"id", 
				"descripcion"
			);
		}
	});
}

function initSexo() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/SexoREST/list"
	}).then(function(data) {
		if ($("#selectSexo").length > 0) {
			fillSelect(
				"selectSexo",
				data,
				"id", 
				"descripcion"
			);
		}
	});
}

function initDepartamento() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/DepartamentoREST/list"
	}).then(function(data) {
		if ($("#selectDireccionFacturaDepartamento").length > 0) {
			fillSelect(
				"selectDireccionFacturaDepartamento",
				data,
				"id", 
				"nombre"
			);
		}
			
		if ($("#selectDireccionEntregaDepartamento").length > 0) {
			fillSelect(
				"selectDireccionEntregaDepartamento",
				data,
				"id", 
				"nombre"
			);
		}
		
		if ($("#selectDepartamento").length > 0) {
			fillSelect(
				"selectDepartamento",
				data,
				"id", 
				"nombre"
			);
		}
	});
}

function initBarrio() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/BarrioREST/listMinimal"
	}).then(function(data) {
		if ($("#selectBarrio").length > 0) {
			fillSelect(
				"selectBarrio",
				data,
				"id", 
				"nombre"
			);
		}
	});
}

function initZona() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/ZonaREST/listMinimal"
	}).then(function(data) {
		if ($("#selectZona").length > 0) {
			fillSelect(
				"selectZona",
				data,
				"id", 
				"nombre"
			);
		}
	});
}

function initTurno() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/TurnoREST/list"
	}).then(function(data) {
		if ($("#selectTurno").length > 0) {
			fillSelect(
				"selectTurno",
				data,
				"id", 
				"nombre"
			);
		}
	});
}

function initRepuestaTecnicaComercial() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/AtencionClienteRespuestaTecnicaComercialREST/list"
	}).then(function(data) {
		if ($("#selectAtencionClienteRespuestaTecnicaComercial").length > 0) {
			fillSelect(
				"selectAtencionClienteRespuestaTecnicaComercial",
				data,
				"id", 
				"descripcion"
			);
		}
	});
}

function initDisponibilidadEntrega(empresaId, zonaId, turnoId) {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/DisponibilidadEntregaEmpresaZonaTurnoREST/listByEmpresaZonaTurno?"
			+ "eid=" + empresaId
			+ "&zid=" + zonaId
			+ "&tid=" + turnoId
	}).then(function(data) {
		var availability = [0, 0, 0, 0, 0, 0, 0];
		
		for (var i=0; i<data.length; i++) {
			availability[data[i].dia - 1] = data[i].cantidad;
		}
		
		var options = [];
		
		var date = new Date();
		for (var j=0; j<availability.length * __SEMANAS_DISPONIBILIDAD_FECHA_ENTREGA; j++) {
			if (availability[date.getDay()] > 0) {
				options[options.length] = {
					fecha: formatShortDate(date),
					descripcion: formatShortDate(date) + " - " + availability[date.getDay()]
				};
			}
			
			date.setDate(date.getDate() + 1);
		}
		
		fillSelect("selectFechaEntrega", options, "fecha", "descripcion");
	});
}

function initTipoProducto() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/TipoProductoREST/list"
	}).then(function(data) {
		if ($("#selectTipoProducto").length > 0) {
			fillSelect(
				"selectTipoProducto",
				data,
				"id", 
				"descripcion"
			);
		}
	});
}

function initStock(empresaId) {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/StockMovimientoREST/listStockByEmpresaId/" + empresaId
	}).then(function(dataStock) {
		var equipos = [];
		
		for (var i=0; i<dataStock.length; i++) {
			if (dataStock[i].cantidad > 0 && dataStock[i].fechaBaja == null) {
				equipos[equipos.length] = {
					id: dataStock[i].modelo.id,
					descripcion: dataStock[i].marca.nombre + " " + dataStock[i].modelo.descripcion + " (" + dataStock[i].cantidad + ")",
					marca: dataStock[i].marca,
					modelo: dataStock[i].modelo,
					tipoProducto: dataStock[i].tipoProducto
				};
			}
		}
		
		var ordered = equipos.sort(function(a, b) {
			var result = 0;
			
			var aDescripcion = a.descripcion;
			var bDescripcion = b.descripcion;
			
			if (aDescripcion < bDescripcion) {
				result = -1;
			} else if (aDescripcion > bDescripcion) {
				result = 1;
			}
			
			return result;
		});
		
		fillSelect("selectEquipo", ordered, "id", "descripcion", "maid", "marca.id");
	});
}

function initFormasPago(empresaId, documento) {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/EmpresaREST/listFormasPagoById/" + empresaId
	}).then(function(formasPago) {
		fillSelect("selectFormaPago", formasPago, "id", "descripcion");
	});
}

function revisarFormasPago(empresaId, documento) {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/FinanciacionREST/analizarElegibilidadFinanaciacion",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
			"empresaId": empresaId,
			"documento": documento
		})
	}).then(function(data) {
		if (data != null) {
			if (data.elegibilidad == __ELEGIBILIDAD_FINANCIACION_REALIZAR_CLEARING) {
				$("#selectFormaPago > option[value='" + __FORMA_PAGO_NUESTRO_CREDITO_ID + "']").attr("defid", data.elegibilidad)
			} else if (data.elegibilidad == __ELEGIBILIDAD_FINANCIACION_RECHAZAR) {
				// No se presenta la opción de Nuestro crédito.
				$("#selectFormaPago > option[value='" + __FORMA_PAGO_NUESTRO_CREDITO_ID + "']").remove()
			}
		}
	});
}

function reloadContrato() {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/getById/" + id
	}).then(function(data) {
		// Fechas del workflow
		// -------------------
		
		populateField("fechaVenta", data, "fechaVenta", "fechaVenta", "fv", "fechaVenta", formatLongDate);
		populateField("fechaVentaMostrar", data, "fechaVenta", "fechaVenta", null, null, formatLongDate);
		populateField("fechaBackoffice", data, "fechaBackoffice", "fechaBackoffice", null, null, formatRawDate);
		populateField("fechaEntregaDistribuidor", data, "fechaEntregaDistribuidor", "fechaEntregaDistribuidor", null, null, formatRawDate);
		populateField("fechaDevolucionDistribuidor", data, "fechaDevolucionDistribuidor", "fechaDevolucionDistribuidor", null, null, formatRawDate);
		populateField("fechaEnvioAntel", data, "fechaEnvioAntel", "fechaEnvioAntel", null, null, formatRawDate);
		populateField("fechaActivacion", data, "fechaActivacion", "fechaActivacion", null, null, formatRawDate);
		populateField("fechaCoordinacion", data, "fechaCoordinacion", "fechaCoordinacion", null, null, formatRawDate);
		populateField("fechaRechazo", data, "fechaRechazo", "fechaRechazo", null, null, formatRawDate);
		populateField("resultadoEntregaDistribucionFecha", data, "resultadoEntregaDistribucionFecha", "resultadoEntregaDistribucionFecha", null, null, formatRawDate);
		populateField("fechaPickUp", data, "fechaPickUp", "fechaPickUp", null, null, formatRawDate);
		populateField("fechaAtencionClienteOperador", data, "fechaAtencionClienteOperador", "fechaAtencionClienteOperador", null, null, formatRawDate);
		populateField("fechaAtencionClienteGestionador", data, "fechaAtencionClienteGestionador", "fechaAtencionClienteGestionador", null, null, formatRawDate);
		populateField("fechaAtencionClienteDevuelto", data, "fechaAtencionClienteDevuelto", "fechaAtencionClienteDevuelto", null, null, formatRawDate);
		populateField("fechaAtencionClienteCierre", data, "fechaAtencionClienteCierre", "fechaAtencionClienteCierre", null, null, formatRawDate);
		populateField("fcre", data, "fcre", "fcre", null, null, formatLongDate);
	
		// -------------------
		
		// Usuarios del workflow
		// ---------------------
		
		populateField("rol", data, "rol.id", "rol.nombre", "rid", "rol.id");
		populateField("usuario", data, "usuario.id", "usuario.nombre", "uid", "usuario.id");
		populateField("vendedor", data, "vendedor.id", "vendedor.nombre", "vid", "vendedor.id");
		populateField("backoffice", data, "backoffice.id", "backoffice.nombre", "bid", "backoffice.id");
		populateField("distribuidor", data, "distribuidor.id", "distribuidor.nombre", "did", "distribuidor.id");
		populateField("activador", data, "activador.id", "activador.nombre", "aid", "activador.id");
		populateField("coordinador", data, "coordinador.id", "coordinador.nombre", "coid", "coordinador.id");
		populateField("atencionClienteOperador", data, "atencionClienteOperador.id", "atencionClienteOperador.nombre", "acoid", "atencionClienteOperador.id");
		populateField("atencionClienteGestionador", data, "atencionClienteGestionador.id", "atencionClienteGestionador.nombre", "acgid", "atencionClienteGestionador.id");
		
		// ---------------------
		
		populateField("random", data, "random", "random");
		populateField("empresa", data, "empresa.id", "empresa.nombre", "eid", "empresa.id");
		populateField("numeroTramite", data, "numeroTramite", "numeroTramite");
		populateField("mid", data, "mid", "mid");
		populateField("estado", data, "estado.id", "estado.nombre", "eid", "estado.id");
		if (data.estado != null && data.estado.id == __ESTADO_FALTA_DOCUMENTACION) {
			$("#divInputRedistribuir").hide();
			$("#divInputTelelink").hide();
			$("#divInputRenovo").hide();
			$("#divInputReActivar").hide();
			$("#divInputNoRecoordina").hide();
			
			$("#divInputRedistribuir").show();
			$("#divInputEquiposPagos").show();
			$("#divInputEquipoDevuelto").show();
			$("#divInputReActivar").show();
			$("#divInputNoRecuperado").show();
		}
		populateField("localidad", data, "localidad", "localidad");
		populateField("codigoPostal", data, "codigoPostal", "codigoPostal");
		populateField("fechaFinContrato", data, "fechaFinContrato", "fechaFinContrato", null, null, formatShortDate);
		populateField("numeroContrato", data, "numeroContrato", "numeroContrato");
		populateField("tipoContratoDescripcion", data, "tipoContratoDescripcion", "tipoContratoDescripcion");
		populateField("nuevoPlanString", data, "nuevoPlanString", "nuevoPlanString");
		
		if (data.nuevoPlan != null) {
			if ($("#selectNuevoPlan").length > 0) {
				if ($("#selectNuevoPlan > option[value='" + data.nuevoPlan.id + "']").length == 0) {
					$("#selectNuevoPlan> option:first").after(
						"<option value='" + data.nuevoPlan.id + "'>" 
							+ data.nuevoPlan.descripcion
						+ "</option>"
					);
				}
			}
		}
		populateField("nuevoPlan", data, "nuevoPlan.id", "nuevoPlan.descripcion");
		populateField("modalidadVenta", data, "modalidadVenta.id", "modalidadVenta.descripcion");
		
		populateField("atencionClienteTipoContacto", data, "atencionClienteTipoContacto.id", "atencionClienteTipoContacto.descripcion");
		populateField("atencionClienteConcepto", data, "atencionClienteConcepto.id", "atencionClienteConcepto.descripcion");
		populateField("atencionClienteRespuestaTecnicaComercial", data, "atencionClienteRespuestaTecnicaComercial.id", "atencionClienteRespuestaTecnicaComercial.descripcion");
		populateField("atencionClienteCantidadVeces", data, "atencionClienteCantidadVeces", "atencionClienteCantidadVeces");
		
		populateField("motivoCambioPlan", data, "motivoCambioPlan.id", "motivoCambioPlan.descripcion");
		populateField("tipoProducto", data, "tipoProducto.id", "tipoProducto.descripcion");
		
		populateField("valorCuota", data, "valorCuota", "valorCuota", null, null, function(val) { return formatDecimal(val, 2); });
		populateField("numeroVale", data, "numeroVale", "numeroVale");
		populateField("intereses", data, "intereses", "intereses");
		populateField("gastosAdministrativos", data, "gastosAdministrativos", "gastosAdministrativos");
		populateField("gastosConcesion", data, "gastosConcesion", "gastosConcesion");
		populateField("gastosAdministrativosTotales", data, "gastosAdministrativosTotales", "gastosAdministrativosTotales");
		populateField("unidadIndexada", data, "valorUnidadIndexada", "valorUnidadIndexada");
		populateField("tasaInteresEfectivaAnual", data, "valorTasaInteresEfectivaAnual", "valorTasaInteresEfectivaAnual");
		populateField("numeroFactura", data, "numeroFactura", "numeroFactura");
		populateField("numeroFacturaRiverGreen", data, "numeroFacturaRiverGreen", "numeroFacturaRiverGreen");
		populateField("numeroSerie", data, "numeroSerie", "numeroSerie", "pid", "producto.id");
		populateField("numeroChip", data, "numeroChip", "numeroChip");
		populateField("numeroBloqueo", data, "numeroBloqueo", "numeroBloqueo");
		
		populateField("tipoDocumento", data, "tipoDocumento.id", "tipoDocumento.descripcion");
		populateField("documento", data, "documento", "documento");
		populateField("nombre", data, "nombre", "nombre");
		populateField("apellido", data, "apellido", "apellido");
		populateField("fechaNacimiento", data, "fechaNacimiento", "fechaNacimiento", null, null, formatShortDate);
		populateField("sexo", data, "sexo.id", "sexo.descripcion", "sid", "sexo.id");
		populateField("telefonoContacto", data, "telefonoContacto", "telefonoContacto");
		populateField("email", data, "email", "email");
		
		populateField("incluirChip", data, "incluirChip", "incluirChip");
		populateField("costoEnvio", data, "costoEnvio", "costoEnvio");
		
		populateField("precio", data, "precio", "precio");
		populateField("moneda", data, "moneda.id", "moneda.simbolo", "mid", "moneda.id");
		
		populateField("tipoTasaInteresEfectivaAnual", data, "tipoTasaInteresEfectivaAnual.id", "tipoTasaInteresEfectivaAnual.descripcion", "tid", "tipoTasaInteresEfectivaAnual.id");
		
		if (data.tipoTasaInteresEfectivaAnual == null) {
			$("#divTipoTasaInteresEfectivaAnual").attr("tid", 1);
		}
		
		populateField("tarjetaCredito", data, "tarjetaCredito.id", "tarjetaCredito.nombre", "tid", "tarjetaCredito.id");
		populateField("cuotas", data, "cuotas", "cuotas");
		
		if (data.cuotas != null) {
			showField("cuotas");
		}
		
		populateField("fechaActivarEn", data, "fechaActivarEn", "fechaActivarEn", null, null, formatShortDate);
		populateField("observaciones", data, "observaciones", "observaciones");
		
		populateField("direccionFacturaCalle", data, "direccionFacturaCalle", "direccionFacturaCalle");
		populateField("direccionFacturaNumero", data, "direccionFacturaNumero", "direccionFacturaNumero");
		populateField("direccionFacturaBis", data, "direccionFacturaBis", "direccionFacturaBis");
		populateField("direccionFacturaBlock", data, "direccionFacturaBlock", "direccionFacturaBlock");
		populateField("direccionFacturaApto", data, "direccionFacturaApto", "direccionFacturaApto");
		populateField("direccionFacturaSolar", data, "direccionFacturaSolar", "direccionFacturaSolar");
		populateField("direccionFacturaManzana", data, "direccionFacturaManzana", "direccionFacturaManzana");
		populateField("direccionFacturaCodigoPostal", data, "direccionFacturaCodigoPostal", "direccionFacturaCodigoPostal");
		populateField("direccionFacturaLocalidad", data, "direccionFacturaLocalidad", "direccionFacturaLocalidad");
		populateField("direccionFacturaObservaciones", data, "direccionFacturaObservaciones", "direccionFacturaObservaciones");
		
		populateField("direccionEntregaCalle", data, "direccionEntregaCalle", "direccionEntregaCalle");
		populateField("direccionEntregaNumero", data, "direccionEntregaNumero", "direccionEntregaNumero");
		populateField("direccionEntregaBis", data, "direccionEntregaBis", "direccionEntregaBis");
		populateField("direccionEntregaBlock", data, "direccionEntregaBlock", "direccionEntregaBlock");
		populateField("direccionEntregaApto", data, "direccionEntregaApto", "direccionEntregaApto");
		populateField("direccionEntregaSolar", data, "direccionEntregaSolar", "direccionEntregaSolar");
		populateField("direccionEntregaManzana", data, "direccionEntregaManzana", "direccionEntregaManzana");
		populateField("direccionEntregaCodigoPostal", data, "direccionEntregaCodigoPostal", "direccionEntregaCodigoPostal");
		populateField("direccionEntregaLocalidad", data, "direccionEntregaLocalidad", "direccionEntregaLocalidad");
		populateField("direccionEntregaObservaciones", data, "direccionEntregaObservaciones", "direccionEntregaObservaciones");
		
		populateField("direccionFacturaDepartamento", data, "direccionFacturaDepartamento.id", "direccionFacturaDepartamento.nombre", "did", "direccionFacturaDepartamento.id");
		populateField("direccionEntregaDepartamento", data, "direccionEntregaDepartamento.id", "direccionEntregaDepartamento.nombre", "did", "direccionEntregaDepartamento.id");
		
		populateField("resultadoEntregaDistribucion", data, "resultadoEntregaDistribucion.id", "resultadoEntregaDistribucion.descripcion", "redid", "resultadoEntregaDistribucion.id");
		populateField("resultadoEntregaDistribucionLatitud", data, "resultadoEntregaDistribucionLatitud");
		populateField("resultadoEntregaDistribucionLongitud", data, "resultadoEntregaDistribucionLongitud");
		populateField("resultadoEntregaDistribucionPrecision", data, "resultadoEntregaDistribucionPrecision");
		populateField("resultadoEntregaDistribucionObservaciones", data, "resultadoEntregaDistribucionObservaciones", "resultadoEntregaDistribucionObservaciones");
		
		populateField("antelNroTrn", data, "antelNroTrn", "antelNroTrn");
		populateField("antelFormaPago", data, "antelFormaPago", "antelFormaPago");
		populateField("antelNroServicioCuenta", data, "antelNroServicioCuenta", "antelNroServicioCuenta");
		populateField("antelImporte", data, "antelImporte", "antelImporte");
		
		populateField("fechaEnvioANucleo", data, "fechaEnvioANucleo", "fechaEnvioANucleo", null, null, formatLongDate);
		populateField("fechaEnvioAIZI", data, "fechaEnvioAIZI", "fechaEnvioAIZI", null, null, formatLongDate);
		populateField("fechaEnvioAGLA", data, "fechaEnvioAGLA", "fechaEnvioAGLA", null, null, formatLongDate);
		
		if (data.departamento != null) {
			if ($("#selectDepartamento").length > 0) {
				if ($("#selectDepartamento > option[value='" + data.departamento.id + "']").length == 0) {
					$("#selectDepartamento > option:first").after(
						"<option value='" + data.departamento.id + "'>" 
							+ data.departamento.nombre
						+ "</option>"
					);
				}
			}
		}
		populateField("departamento", data, "zona.departamento.id", "zona.departamento.nombre");
		
		if (data.barrio != null) {
			if ($("#selectBarrio").length > 0) {
				if ($("#selectBarrio > option[value='" + data.barrio.id + "']").length == 0) {
					$("#selectBarrio > option:first").after(
						"<option value='" + data.barrio.id + "'>" 
							+ data.barrio.nombre
						+ "</option>"
					);
				}
			}
		}
		populateField("barrio", data, "barrio.id", "barrio.nombre");
		
		if (data.zona != null) {
			if ($("#selectZona").length > 0) {
				if ($("#selectZona > option[value='" + data.zona.id + "']").length == 0) {
					$("#selectZona > option:first").after(
						"<option value='" + data.zona.id + "'>" 
							+ data.zona.nombre
						+ "</option>"
					);
				}
			}
		}
		
		populateField("zona", data, "zona.id", "zona.nombre");
		
		if (data.turno != null) {
			if ($("#selectTurno").length > 0) {
				if ($("#selectTurno > option[value='" + data.turno.id + "']").length == 0) {
					$("#selectTurno > option:first").after(
						"<option value='" + data.turno.id + "'>" 
							+ data.turno.nombre
						+ "</option>"
					);
				}
			}
		}
		
		populateField("turno", data, "turno.id", "turno.nombre");
		
		if (data.empresa != null) {
			initStock(data.empresa.id)
				.then(function(dataStock) {
					if ($("#selectEquipo").length > 0) {
						if (data.modelo != null) {
							if ($("#selectEquipo > option[value='" + data.modelo.id + "']").length == 0) {
								$("#selectEquipo > option:first").after(
									"<option value='" + data.modelo.id + "' maid='" + data.marca.id + "'>" 
										+ data.marca.nombre + " - " + data.modelo.descripcion
									+ "</option>"
								);
							}
						}
					}
					
					populateField("equipo", data, "modelo.id", "modelo.descripcion");
					
					initFormasPago(data.empresa.id, data.documento)
						.then(function(dataFormaPago) {
							revisarFormasPago(data.empresa.id, data.documento)
								.then(function (dataRevisionFormasPago) {
									if (data.formaPago != null) {
										if ($("#selectFormaPago > option[value='" + data.formaPago.id + "']").length == 0) {
											$("#selectFormaPago > option:first").after(
												"<option value='" + data.formaPago.id + "'>" 
													+ data.formaPago.descripcion
												+ "</option>"
											);
										}
										
										if (data.formaPago.id == __FORMA_PAGO_TARJETA_ID) {
											showField("tarjetaCredito");
										}
										
										if (data.formaPago.id == __FORMA_PAGO_NUESTRO_CREDITO_ID) {
											if ($("#selectFormaPago").length > 0) {
												showField("valorCuota");
												$("#divInputImprimirPagare").show();
												$("#divInputImprimirRemito").show();
												// $("#divLabelFechaActivarEn").addClass("requiredFormLabel");
												$("#divButtonTitleTripleSize").attr("id", "divButtonTitleQuintupleSize");
											}
										}
									} else {
										$("#selectFormaPago").val(__FORMA_PAGO_NUESTRO_CREDITO_ID);
										if ($("#selectFormaPago").val() == __FORMA_PAGO_NUESTRO_CREDITO_ID) {
											showField("cuotas");
											showField("valorCuota");
											$("#selectCuotas").val(12);
											reloadDatosFinanciacion();
										}
									}
									
									populateField("formaPago", data, "formaPago.id", "formaPago.descripcion", "fid", "formaPago.id");
									
									if (data.zona != null && data.turno != null) {
										initDisponibilidadEntrega(data.empresa.id, data.zona.id, data.turno.id)
											.then(function(dataDisponibilidadEntrega) {
												if (data.fechaEntrega != null) {
													if ($("#selectFechaEntrega").length > 0) {
														if ($("#selectFechaEntrega > option[value='" + formatShortDate(data.fechaEntrega) + "']").length == 0) {
															$("#selectFechaEntrega > option:first").after(
																"<option value='" + formatShortDate(data.fechaEntrega) + "'>" 
																	+ formatShortDate(data.fechaEntrega)
																+ "</option>"
															);
														}
														
														$("#selectFechaEntrega").val(formatShortDate(data.fechaEntrega));
													} else {
														$("#divFechaEntrega").html(formatShortDate(data.fechaEntrega));
													}
												}
												
												reloadArchivosAdjuntosData(data.id);
												reloadContratosRelacionadosData(data.id);
												reloadDireccionesData(data.id);
											});
									} else {
										reloadArchivosAdjuntosData(data.id);
										reloadContratosRelacionadosData(data.id);
										reloadDireccionesData(data.id);
									}
								});
					});
				});
		}
	});
}

function reloadFormasPago(empresaId, documento) {
	if (empresaId != null && empresaId != "") {
		return $.ajax({
			url: "/LogisticaWEB/RESTFacade/EmpresaREST/listFormasPagoById/" + empresaId
		}).then(callbackReloadFormasPago);
	} else {
		return null;
	}
}

function callbackReloadFormasPago(formasPago) {
	var empresaId = 
		$("#selectEmpresa").length > 0 ? $("#selectEmpresa").val() : $("#divEmpresa").attr("eid");
			 
	var documento = 
		$("#inputDocumento").length > 0 ?
			($("#inputDocumento").val().trim() != "" ? $("#inputDocumento").val().trim() : null) :
			$("#divDocumento").text().trim();
		
	var options = [];
	
	if (formasPago != null) {
		var nuestroCredito = false;
		
		for (var i=0; i<formasPago.length; i++) {
			if (formasPago[i].id == __FORMA_PAGO_NUESTRO_CREDITO_ID) {
				nuestroCredito = true;
			} else {
				options[options.length] = formasPago[i];
			}
		}
		
		// Si "Nuestro cédito" está habilitado como forma de pago para la empresa 
		if (nuestroCredito) {
			if (documento != null && documento != "") {
				$.ajax({
					url: "/LogisticaWEB/RESTFacade/FinanciacionREST/analizarElegibilidadFinanaciacion",
					method: "POST",
					contentType: 'application/json',
					data: JSON.stringify({
						"empresaId": empresaId,
						"documento": documento
					})
				}).then(function(data) {
					if (data != null) {
						if (data.elegibilidad == __ELEGIBILIDAD_FINANCIACION_NO_REALIZAR_CLEARING) {
							options[options.length] = formasPago[i];
						} else if (data.elegibilidad == __ELEGIBILIDAD_FINANCIACION_REALIZAR_CLEARING) {
							formasPago[i].datosElegibilidadFinanciacion = data;
							
							options[options.length] = formasPago[i];
						} else if (data.elegibilidad == __ELEGIBILIDAD_FINANCIACION_RECHAZAR) {
							// No se presenta la opción de Nuestro crédito.
						}
					}
				});
			}
			
			fillSelect(
				"selectFormaPago", 
				options, 
				"id", 
				"descripcion", 
				"defid", 
				"datosElegibilidadFinanciacion.elegibilidad"
			);
		} else {
			fillSelect(
				"selectFormaPago", 
				options, 
				"id", 
				"descripcion", 
				"defid", 
				"datosElegibilidadFinanciacion.elegibilidad"
			);
		}
	}
}

function selectEmpresaOnChange(event, element) {
	$("#selectFormaPago").val(0);
	
	var empresaId = $("#selectEmpresa").val();
	reloadFormasPago(empresaId);
}

function inputDocumentoOnChange(event, element) {
	$("#selectFormaPago").val(0);
	selectFormaPagoOnChange();
	
	var empresaId = $("#divEmpresa").attr("eid");
	var documento = $("#inputDocumento").val();
	
	if (documento != null && documento != "") {
		documento = documento.trim();
		
		if (empresaId != null && empresaId != "") {
			reloadFormasPago(empresaId, documento);
		}
	} else {
		reloadPrecio();
		reloadDatosFinanciacion();
	}
}

function selectDepartamentoOnChange() {
	$("#selectBarrio > option:gt(0)").remove();
	$("#selectZona > option:gt(0)").remove();
	$("#selectTurno > option:gt(0)").remove();
	$("#selectFechaEntrega > option:gt(0)").remove();
	
	if ($("#selectDepartamento").val() != "0") {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/BarrioREST/listByDepartamentoId/" + $("#selectDepartamento").val()
		}).then(function(data) {
			fillSelect("selectBarrio", data, "id", "nombre", "zid", "zona.id");

			selectBarrioOnChange();
		});
	}
}

function selectBarrioOnChange() {
	$("#selectZona > option:gt(0)").remove();
	$("#selectTurno > option:gt(0)").remove();
	$("#selectFechaEntrega > option:gt(0)").remove();
	
	if ($("#selectBarrio").val() != "0") {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ZonaREST/getById/" + $("#selectBarrio > option:selected").attr("zid")
		}).then(function(data) {
			fillSelect("selectZona", [data], "id", "nombre");
			
			$("#selectZona").val(data.id);
			
			selectZonaOnChange();
		});
	}
}

function selectZonaOnChange() {
	$("#selectTurno > option:gt(0)").remove();
	$("#selectFechaEntrega > option:gt(0)").remove();
	
	if ($("#selectZona").val() != "0") {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/TurnoREST/list/"
		}).then(function(data) {
			fillSelect("selectTurno", data, "id", "nombre");
			selectTurnoOnChange();
		});
	}
}

function selectTurnoOnChange() {
	$("#selectFechaEntrega > option:gt(0)").remove();
	
	if ($("#selectTurno").val() != "0" &&
		$("#selectZona").val() != "0" &&
		$("#selectDepartamento").val() != "0" && (
			($("#selectEmpresa").length > 0 && $("#selectEmpresa").val() != "0") ||
			($("#divEmpresa").attr("eid") != null && $("#divEmpresa").attr("eid") != "")
		)
	) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/DisponibilidadEntregaEmpresaZonaTurnoREST/listByEmpresaZonaTurno?"
				+ "eid=" + ($("#selectEmpresa").length > 0 ? $("#selectEmpresa").val() : $("#divEmpresa").attr("eid"))
				+ "&zid=" + $("#selectZona").val()
				+ "&tid=" + $("#selectTurno").val()
		}).then(function(data) {
			var availability = [0, 0, 0, 0, 0, 0, 0];
			
			for (var i=0; i<data.length; i++) {
				availability[data[i].dia - 1] = data[i].cantidad;
			}
			
			var options = [];
			
			var date = new Date();
			for (var j=0; j<availability.length * __SEMANAS_DISPONIBILIDAD_FECHA_ENTREGA; j++) {
				if (availability[date.getDay()] > 0) {
					options[options.length] = {
						fecha: formatShortDate(date),
						descripcion: formatShortDate(date) + " - " + availability[date.getDay()]
					};
				}
				
				date.setDate(date.getDate() + 1);
			}
			
			fillSelect("selectFechaEntrega", options, "fecha", "descripcion");
			
			selectFechaEntregaOnChange();
		});
	}
}

function selectFechaEntregaOnChange() {
	return true;
}

function reloadPrecio() {
	$("#inputPrecio").val(null);
	
	if ($("#selectTipoProducto").val() != 0
		&& $("#selectEquipo").val() != 0
		&& $("#selectMoneda").val() != 0) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/PrecioREST/getActualByEmpresaTipoProductoMarcaModeloMonedaCuotas",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify({
				"empresaId": 
					$("#selectEmpresa").length > 0 ? $("#selectEmpresa").val() : $("#divEmpresa").attr("eid"),
				"tipoProductoId": 
					$("#selectTipoProducto").length > 0 ? 
						$("#selectTipoProducto").val() 
						: $("#divTipoProducto").attr("tpid"),
				"marcaId": 
					$("#selectEquipo").length > 0 ? 
						$("#selectEquipo option:selected").attr("maid") 
						: $("#divEquipo").attr("maid"),
				"modeloId":
					$("#selectEquipo").length > 0 ? 
						$("#selectEquipo").val() 
						: $("#divEquipo").attr("moid"),
				"monedaId": 
					$("#selectMoneda").length > 0 ? 
						$("#selectMoneda").val() 
						: $("#divMoneda").attr("mid"),
				"cuotas": 
					($("#selectCuotas").length > 0 && $("#selectCuotas").val() > 0) ? 
						$("#selectCuotas").val() 
						: 1
			})
		}).then(function(data) {
			if (data != null) {
				$("#inputPrecio").val(data.precio);
			} else {
				$("#inputPrecio").val(null);
			}
		});
	}
}

function reloadDatosFinanciacion() {
//	$("#inputPTF").val(null);
	$("#inputValorCuota").val(null);
	$("#divIntereses").html(null);
	$("#divGastosAdministrativos").html(null);
	$("#divGastosConcesion").html(null);
	$("#divGastosAdministrativosTotales").html(null);
	$("#divUnidadIndexada").html(null);
	$("#divTasaInteresEfectivaAnual").html(null);
	
	if ($("#selectEquipo").val() != 0 
		&& $("#selectMoneda").val() != 0 
		&& $("#inputPrecio").val() != ""
		&& $("#selectCuotas").val() != 0) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/FinanciacionREST/calcularFinanciacion",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify({
				"monedaId": ($("#selectMoneda").length > 0 ? $("#selectMoneda").val() : $("#divMoneda").attr("mid")),
				"tipoTasaInteresEfectivaAnualId": $("#divTipoTasaInteresEfectivaAnual").attr("tid"),
				"monto": $("#inputPrecio").val(),
				"cuotas": $("#selectCuotas").val()
			})
		}).then(function(data) {
			if (data != null) {
				$("#inputValorCuota").val(formatDecimal(data.montoCuota, 2));
				$("#divIntereses").html(data.intereses);
				$("#divGastosAdministrativos").html(data.gastosAdministrativos);
				$("#divGastosConcesion").html(data.gastosConcesion);
				$("#divGastosAdministrativosTotales").html(data.gastosAdministrativosTotales);
				$("#divUnidadIndexada").html(data.valorUnidadIndexada);
				$("#divTasaInteresEfectivaAnual").html(data.valorTasaInteresEfectivaAnual);
			}
		});
	}
}

function selectTipoProductoOnChange() {
	reloadPrecio();
	reloadDatosFinanciacion();
	reloadStock();
}

function reloadStock() {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/StockMovimientoREST/listStockByEmpresaTipoProducto?"
			+ "eid=" + ($("#selectEmpresa").length > 0 ? $("#selectEmpresa").val() : $("#divEmpresa").attr("eid"))
			+ "&tid=" + ($("#selectTipoProducto").length > 0 ? $("#selectTipoProducto").val() : $("#divTipoProducto").attr("tpid"))
	}).then(function(data) {
		var equipos = [];
		
		for (var i=0; i<data.length; i++) {
			if (data[i].cantidad > 0 && data[i].fechaBaja == null) {
				equipos[equipos.length] = {
					id: data[i].modelo.id,
					descripcion: data[i].marca.nombre + " " + data[i].modelo.descripcion + " (" + data[i].cantidad + ")",
					marca: data[i].marca,
					modelo: data[i].modelo,
					tipoProducto: data[i].tipoProducto
				};
			}
		}
		
		var ordered = equipos.sort(function(a, b) {
			var result = 0;
			
			var aDescripcion = a.descripcion;
			var bDescripcion = b.descripcion;
			
			if (aDescripcion < bDescripcion) {
				result = -1;
			} else if (aDescripcion > bDescripcion) {
				result = 1;
			}
			
			return result;
		});
		
		fillSelect("selectEquipo", ordered, "id", "descripcion", "maid", "marca.id");
	});
}

function selectEquipoOnChange() {
	reloadPrecio();
	reloadDatosFinanciacion();
}

function selectMonedaOnChange() {
	reloadPrecio();
	reloadDatosFinanciacion();
}

function inputPrecioOnChange() {
	reloadDatosFinanciacion();
}

function selectFormaPagoOnChange() {
	$("#selectTarjetaCredito").val(0);
	$("#selectCuotas").val(0);
	$("#divRealizarClearing").hide();
	
	if ($("#selectFormaPago").val() == __FORMA_PAGO_TARJETA_ID) {
		showField("tarjetaCredito");
		showField("cuotas");
		hideField("valorCuota");
		
		$("#divLabelPrecio").addClass("requiredFormLabel");
	} else if ($("#selectFormaPago").val() == __FORMA_PAGO_NUESTRO_CREDITO_ID) {
		hideField("tarjetaCredito");
		
		$("#selectCuotas").val(__FORMA_PAGO_NUESTRO_CREDITO_CUOTAS_DEFAULT);
		
		var datosElegibilidadFinanciacionId = $("#selectFormaPago > option:selected").attr("defid");
		if (datosElegibilidadFinanciacionId != null && datosElegibilidadFinanciacionId != "") {
			if (datosElegibilidadFinanciacionId == __ELEGIBILIDAD_FINANCIACION_REALIZAR_CLEARING) {
//				alert("Debe realizarse comprobación en Clearing de Informes");
				$("#divRealizarClearing").show();
			}
		}
		
		showField("cuotas");
		showField("valorCuota");
		
		$("#divLabelPrecio").addClass("requiredFormLabel");
	} else {
		hideField("tarjetaCredito");
		hideField("cuotas");
		hideField("valorCuota");
		
		$("#divLabelPrecio").removeClass("requiredFormLabel");
	}
	
	reloadDatosFinanciacion();
}

function selectTarjetaCreditoOnChange() {
	$("#selectCuotas").val(0);
	
	reloadDatosFinanciacion();
}

function selectCuotasOnChange() {
	reloadDatosFinanciacion();
}

function inputFacturaRiverGreenOnClick() {
	if ($("#inputFacturaRiverGreen").prop("checked")) {
		$("#inputNumeroFacturaRiverGreen").prop("disabled", false);
	} else {
		$("#inputNumeroFacturaRiverGreen").prop("disabled", true);
		$("#inputNumeroFacturaRiverGreen").val(null);
	}
}

function inputNumeroSerieOnChange(event, element) {
	var val = $("#inputNumeroSerie").val();
	
	if (val != null && val.trim() != "") {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ProductoREST/getByIMEI/" + val,
		}).then(function(data) {
			if (data != null) {
				var modeloId = $("#selectEquipo").attr("moid");
				
				if (modeloId != null && modeloId != "" && modeloId != data.modelo.id) {
					alert("El IMEI ingresado no es de la marca y modelo del Equipo seleccionado.");
				} else {
					$("#inputNumeroSerie").attr("pid", data.id);
				}
			} else {
				alert("El IMEI ingresado no existe.")
			}
		});
	}
}

function checkRequiredFields() {
	var result = true;
	
	var requiredLabelsDIVs = $(".requiredFormLabel");
	for (var i=0; i < requiredLabelsDIVs.length; i++) {
		var requiredLabelDIV = $(requiredLabelsDIVs[i]);
		var requiredLabelDIVId = $(requiredLabelDIV).attr("id");
		var valueElementId = requiredLabelDIVId.replace("Label", "");
		var valueElement = $("#" + valueElementId).children()[0];
		var valueElementSelected = $(valueElement);
		var valueElementTagName = valueElement.tagName;
		
		if (valueElementTagName == "INPUT") {
			if (valueElementSelected.val() == null || valueElementSelected.val().trim() == "") {
				requiredLabelDIV.css("color", "red");
				
				result = result && false;
			} else {
				requiredLabelDIV.css("color", "black");
			}
		} else if (valueElementTagName == "SELECT") {
			if (valueElementSelected.val() == 0) {
				requiredLabelDIV.css("color", "red");
				
				result = result && false;
			} else {
				requiredLabelDIV.css("color", "black");
			}
		}
	}
	
	return result;
}

function collectContratoData() {
	var contrato = {
		id: id,
		random: $("#divRandom").text().trim() != "" ? $("#divRandom").text().trim() : null,
		numeroTramite: $("#divNumeroTramite").text().trim() != "" ? $("#divNumeroTramite").text().trim() : null,
		mid: $("#inputMid").length > 0 ? $("#inputMid").val().trim() : $("#divMid").text().trim(),
		estado: {
			id: $("#divEstado").attr("eid"),
		},
		
		// Fechas del workflow
		// -------------------
		fechaVenta: $("#divFechaVenta").attr("fv") != null && $("#divFechaVenta").attr("fv").trim() != "" ? parseInt($("#divFechaVenta").attr("fv").trim()) : null,
		fechaBackoffice: $("#divFechaBackoffice").text().trim() != "" ? parseInt($("#divFechaBackoffice").text().trim()) : null,
		fechaEntregaDistribuidor: $("#divFechaEntregaDistribuidor").text().trim() != "" ? parseInt($("#divFechaEntregaDistribuidor").text().trim()) : null,
		fechaDevolucionDistribuidor: $("#divFechaDevolucionDistribuidor").text().trim() != "" ? parseInt($("#divFechaDevolucionDistribuidor").text().trim()) : null,
		fechaEnvioAntel: $("#divFechaEnvioAntel").text().trim() != "" ? parseInt($("#divFechaEnvioAntel").text().trim()) : null,
		fechaActivacion: $("#divFechaActivacion").text().trim() != "" ? parseInt($("#divFechaActivacion").text().trim()) : null,
		fechaCoordinacion: $("#divFechaCoordinacion").text().trim() != "" ? parseInt($("#divFechaCoordinacion").text().trim()) : null,
		fechaPickUp: $("#divFechaPickUp").text().trim() != "" ? parseInt($("#divFechaPickUp").text().trim()) : null,
		resultadoEntregaDistribucionFecha: $("#divResultadoEntregaDistribucionFecha").text().trim() != "" ? parseInt($("#divResultadoEntregaDistribucionFecha").text().trim()) : null,
		fechaAtencionClienteOperador: $("#divFechaAtencionClienteOperador").text().trim() != "" ? parseInt($("#divFechaAtencionClienteOperador").text().trim()) : null,
		fechaAtencionClienteGestionador: $("#divFechaAtencionClienteGestionador").text().trim() != "" ? parseInt($("#divFechaAtencionClienteGestionador").text().trim()) : null,
		fechaAtencionClienteDevuelto: $("#divFechaAtencionClienteDevuelto").text().trim() != "" ? parseInt($("#divFechaAtencionClienteDevuelto").text().trim()) : null,
		fechaAtencionClienteCierre: $("#divFechaAtencionClienteCierre").text().trim() != "" ? parseInt($("#divFechaAtencionClienteCierre").text().trim()) : null,
		// -------------------
		
		nuevoPlanString: $("#divNuevoPlanString").text().trim() != "" ? $("#divNuevoPlanString").text().trim() : null,
		fechaFinContrato: $("#divFechaFinContrato").text().trim() != "" ? parseShortDate($("#divFechaFinContrato").text().trim()) : null,
		tipoContratoDescripcion: $("#inputTipoContratoDescripcion").length > 0 ? 
			($("#inputTipoContratoDescripcion").val().trim() != "" ? $("#inputTipoContratoDescripcion").val().trim() : null) : 
				$("#divTipoContratoDescripcion").text().trim(),
		numeroContrato: $("#inputNumeroContrato").length > 0 ? 
			($("#inputNumeroContrato").val().trim() != "" ? $("#inputNumeroContrato").val().trim() : null) : 
				$("#divNumeroContrato").text().trim(),
		numeroSerie: $("#inputNumeroSerie").length > 0 ? 
			($("#inputNumeroSerie").val().trim() != "" ? $("#inputNumeroSerie").val().trim() : null) :
			($("#divNumeroSerie").text().trim() != "" ? $("#divNumeroSerie").text().trim() : null),
		incluirChip: $("#inputIncluirChip").length > 0 ?
			$("#inputIncluirChip").prop("checked") : 
				$("#divIncluirChip").text().trim(),
		costoEnvio: $("#inputCostoEnvio").length > 0 ?
			($("#inputCostoEnvio").val().trim() != "" ? $("#inputCostoEnvio").val().trim() : null) :
				$("#divCostoEnvio").text().trim() != "" ? $("#divCostoEnvio").text().trim() : null,
		numeroChip: $("#inputNumeroChip").length > 0 ? 
			($("#inputNumeroChip").val().trim() != "" ? $("#inputNumeroChip").val().trim() : null) :
				$("#divNumeroChip").text().trim(),
		numeroBloqueo: $("#inputNumeroBloqueo").length > 0 ? 
			($("#inputNumeroBloqueo").val().trim() != "" ? $("#inputNumeroBloqueo").val().trim() : null) :
				$("#divNumeroBloqueo").text().trim(),
		localidad: $("#divLocalidad").text().trim() != "" ? $("#divLocalidad").text().trim() : null,
		precio: $("#inputPrecio").length > 0 ? 
			($("#inputPrecio").val().trim() != "" ? $("#inputPrecio").val().trim() : null) :
				$("#divPrecio").text().trim() != "" ? $("#divPrecio").text().trim() : null,
		valorCuota: $("#inputValorCuota").length > 0 ? 
			($("#inputValorCuota").val().trim() != "" ? $("#inputValorCuota").val().trim() : null) :
				$("#divValorCuota").text().trim() != "" ? $("#divValorCuota").text().trim() : null,
		numeroVale: $("#divNumeroVale").text().trim() != "" ? $("#divNumeroVale").text().trim() : null,
		intereses: $("#divIntereses").text().trim() != "" ? $("#divIntereses").text().trim() : null,
		gastosAdministrativos: $("#divGastosAdministrativos").text().trim() != "" ? $("#divGastosAdministrativos").text().trim() : null,
		gastosConcesion: $("#divGastosConcesion").text().trim() != "" ? $("#divGastosConcesion").text().trim() : null,
		gastosAdministrativosTotales: $("#divGastosAdministrativosTotales").text().trim() != "" ? $("#divGastosAdministrativosTotales").text().trim() : null,
		valorUnidadIndexada: $("#divUnidadIndexada").text().trim() != "" ? $("#divUnidadIndexada").text().trim() : null,
		valorTasaInteresEfectivaAnual: $("#divTasaInteresEfectivaAnual").text().trim() != "" ? $("#divTasaInteresEfectivaAnual").text().trim() : null,
		numeroFactura: $("#inputNumeroFactura").length > 0 ?
			($("#inputNumeroFactura").val().trim() != "" ? $("#inputNumeroFactura").val().trim() : null) :
				$("#divNumeroFactura").text().trim(),
		numeroFacturaRiverGreen: $("#inputNumeroFacturaRiverGreen").length > 0 ?
			($("#inputNumeroFacturaRiverGreen").val().trim() != "" ? $("#inputNumeroFacturaRiverGreen").val().trim() : null) :
				$("#divNumeroFacturaRiverGreen").text().trim(),
		documento: $("#inputDocumento").length > 0 ?
			($("#inputDocumento").val().trim() != "" ? $("#inputDocumento").val().trim() : null) :
				$("#divDocumento").text().trim(),
		nombre: $("#inputNombre").length > 0 ?
			($("#inputNombre").val().trim() != "" ? $("#inputNombre").val().trim() : null) :
				$("#divNombre").text().trim(),
		apellido: $("#inputApellido").length > 0 ?
			($("#inputApellido").val().trim() != "" ? $("#inputApellido").val().trim() : null) :
				$("#divApellido").text().trim(),
		fechaNacimiento: $("#inputFechaNacimiento").length > 0 ?
			($("#inputFechaNacimiento").val().trim() != "" ? parseShortDate($("#inputFechaNacimiento").val().trim()) : null) :
				($("#divFechaNacimiento").text().trim() != "" ? parseShortDate($("#divFechaNacimiento").text().trim()) : null),
		atencionClienteCantidadVeces: $("#inputAtencionClienteCantidadVeces").length > 0 ? 
			($("#inputAtencionClienteCantidadVeces").val().trim() != "" ? $("#inputAtencionClienteCantidadVeces").val().trim() : null) :
				$("#divAtencionClienteCantidadVeces").text().trim() != "" ? $("#divAtencionClienteCantidadVeces").text().trim() : null,
				
		direccionFactura: $("#inputDireccionFactura").length > 0 ?
			($("#inputDireccionFactura").val().trim() != "" ? $("#inputDireccionFactura").val().trim() : null) :
				$("#divDireccionFactura").text().trim(),
		direccionEntrega: $("#inputDireccionEntrega").length > 0 ?
			($("#inputDireccionEntrega").val().trim() != "" ? $("#inputDireccionEntrega").val().trim() : null) :
				$("#divDireccionEntrega").text().trim(),
							
		direccionEntregaCalle: $("#inputDireccionEntregaCalle").length > 0 ?
			($("#inputDireccionEntregaCalle").val().trim() != "" ? $("#inputDireccionEntregaCalle").val().trim() : null) :
			$("#divDireccionEntregaCalle").text().trim(),
		direccionEntregaNumero: $("#inputDireccionEntregaNumero").length > 0 ?
			($("#inputDireccionEntregaNumero").val().trim() != "" ? $("#inputDireccionEntregaNumero").val().trim() : null)
			: ($("#divDireccionEntregaNumero").text().trim() != "" ? $("#divDireccionEntregaNumero").text().trim() : null),
		direccionEntregaBis: $("#inputDireccionEntregaBis").length > 0 ?
			$("#inputDireccionEntregaBis").prop("checked") : 
			$("#divDireccionEntregaBis").text().trim(),
		direccionEntregaBlock: $("#inputDireccionEntregaBlock").length > 0 ?
			($("#inputDireccionEntregaBlock").val().trim() != "" ? $("#inputDireccionEntregaBlock").val().trim() : null) :
			$("#divDireccionEntregaBlock").text().trim(),
		direccionEntregaApto: $("#inputDireccionEntregaApto").length > 0 ?
			($("#inputDireccionEntregaApto").val().trim() != "" ? $("#inputDireccionEntregaApto").val().trim() : null) :
			$("#divDireccionEntregaApto").text().trim(),
		direccionEntregaSolar: $("#inputDireccionEntregaSolar").length > 0 ?
			($("#inputDireccionEntregaSolar").val().trim() != "" ? $("#inputDireccionEntregaSolar").val().trim() : null) :
			$("#divDireccionEntregaSolar").text().trim(),
		direccionEntregaManzana: $("#inputDireccionEntregaManzana").length > 0 ?
			($("#inputDireccionEntregaManzana").val().trim() != "" ? $("#inputDireccionEntregaManzana").val().trim() : null)
			: ($("#divDireccionEntregaManzana").text().trim() != "" ? $("#divDireccionEntregaManzana").text().trim() : null),
		direccionEntregaCodigoPostal: $("#inputDireccionEntregaCodigoPostal").length > 0 ?
			($("#inputDireccionEntregaCodigoPostal").val().trim() != "" ? $("#inputDireccionEntregaCodigoPostal").val().trim() : null)
			: ($("#divDireccionEntregaCodigoPostal").text().trim() != "" ? $("#divDireccionEntregaCodigoPostal").text().trim() : null),
		direccionEntregaLocalidad: $("#inputDireccionEntregaLocalidad").length > 0 ?
			($("#inputDireccionEntregaLocalidad").val().trim() != "" ? $("#inputDireccionEntregaLocalidad").val().trim() : null) :
			$("#divDireccionEntregaLocalidad").text().trim(),
		direccionEntregaObservaciones: $("#inputDireccionEntregaObservaciones").length > 0 ?
			($("#inputDireccionEntregaObservaciones").val().trim() != "" ? $("#inputDireccionEntregaObservaciones").val().trim() : null) :
			$("#divDireccionEntregaObservaciones").text().trim(),
		
		direccionFacturaCalle: $("#inputDireccionFacturaCalle").length > 0 ?
			($("#inputDireccionFacturaCalle").val().trim() != "" ? $("#inputDireccionFacturaCalle").val().trim() : null) :
			$("#divDireccionFacturaCalle").text().trim(),
		direccionFacturaNumero: $("#inputDireccionFacturaNumero").length > 0 ?
			($("#inputDireccionFacturaNumero").val().trim() != "" ? $("#inputDireccionFacturaNumero").val().trim() : null)
			: ($("#divDireccionFacturaNumero").text().trim() != "" ? $("#divDireccionFacturaNumero").text().trim() : null),
		direccionFacturaBis: $("#inputDireccionFacturaBis").length > 0 ?
			$("#inputDireccionFacturaBis").prop("checked") : $("#divDireccionFacturaBis").text().trim(),
		direccionFacturaBlock: $("#inputDireccionFacturaBlock").length > 0 ?
			($("#inputDireccionFacturaBlock").val().trim() != "" ? $("#inputDireccionFacturaBlock").val().trim() : null) :
			$("#divDireccionFacturaBlock").text().trim(),
		direccionFacturaApto: $("#inputDireccionFacturaApto").length > 0 ?
			($("#inputDireccionFacturaApto").val().trim() != "" ? $("#inputDireccionFacturaApto").val().trim() : null) :
			$("#divDireccionFacturaApto").text().trim(),
		direccionFacturaSolar: $("#inputDireccionFacturaSolar").length > 0 ?
			($("#inputDireccionFacturaSolar").val().trim() != "" ? $("#inputDireccionFacturaSolar").val().trim() : null) :
			$("#divDireccionFacturaSolar").text().trim(),
		direccionFacturaManzana: $("#inputDireccionFacturaManzana").length > 0 ?
			($("#inputDireccionFacturaManzana").val().trim() != "" ? $("#inputDireccionFacturaManzana").val().trim() : null)
			: ($("#divDireccionFacturaManzana").text().trim() != "" ? $("#divDireccionFacturaManzana").text().trim() : null),
		direccionFacturaCodigoPostal: $("#inputDireccionFacturaCodigoPostal").length > 0 ?
			($("#inputDireccionFacturaCodigoPostal").val().trim() != "" ? $("#inputDireccionFacturaCodigoPostal").val().trim() : null)
			: ($("#divDireccionFacturaCodigoPostal").text().trim() != "" ? $("#divDireccionFacturaCodigoPostal").text().trim() : null),
		direccionFacturaLocalidad: $("#inputDireccionFacturaLocalidad").length > 0 ?
			($("#inputDireccionFacturaLocalidad").val().trim() != "" ? $("#inputDireccionFacturaLocalidad").val().trim() : null) :
			$("#divDireccionFacturaLocalidad").text().trim(),
		direccionFacturaObservaciones: $("#inputDireccionFacturaObservaciones").length > 0 ?
			($("#inputDireccionFacturaObservaciones").val().trim() != "" ? $("#inputDireccionFacturaObservaciones").val().trim() : null) :
			$("#divDireccionFacturaObservaciones").text().trim(),
				
		telefonoContacto: $("#inputTelefonoContacto").length > 0 ?
			($("#inputTelefonoContacto").val().trim() != "" ? $("#inputTelefonoContacto").val().trim() : null) :
				$("#divTelefonoContacto").text().trim(),
		email: $("#inputEmail").length > 0 ?
			($("#inputEmail").val().trim() != "" ? $("#inputEmail").val().trim() : null) :
				$("#divEmail").text().trim(),
		fechaActivarEn: $("#inputFechaActivarEn").length > 0 ?
			($("#inputFechaActivarEn").val().trim() != "" ? parseShortDate($("#inputFechaActivarEn").val().trim()) : null) :
				($("#divFechaActivarEn").text().trim() != "" ? parseShortDate($("#divFechaActivarEn").text().trim()) : null),
		observaciones: $("#textareaObservaciones").length > 0 ?
			($("#textareaObservaciones").val().trim() != "" ? $("#textareaObservaciones").val().trim() : null) :
				$("#divObservaciones").text().trim(),
		resultadoEntregaDistribucionObservaciones: $("#textareaResultadoEntregaDistribucionObservaciones").length > 0 ?
			($("#textareaResultadoEntregaDistribucionObservaciones").val().trim() != "" ? $("#textareaResultadoEntregaDistribucionObservaciones").val().trim() : null) :
				$("#divResultadoEntregaDistribucionObservaciones").text().trim(),
		resultadoEntregaDistribucionLatitud: $("#divResultadoEntregaDistribucionLatitud").text().trim() != "" ? $("#divResultadoEntregaDistribucionLatitud").text().trim() : null,
		resultadoEntregaDistribucionLongitud: $("#divResultadoEntregaDistribucionLongitud").text().trim() != "" ? $("#divResultadoEntregaDistribucionLongitud").text().trim() : null,
		resultadoEntregaDistribucionPrecision: $("#divResultadoEntregaDistribucionPrecision").text().trim() != "" ? $("#divResultadoEntregaDistribucionPrecision").text().trim() : null,
		antelNroTrn: $("#inputAntelNroTrn").length > 0 ?
			($("#inputAntelNroTrn").val().trim() != "" ? $("#inputAntelNroTrn").val().trim() : null) :
			$("#divAntelNroTrn").text().trim(),
		antelFormaPago: $("#divAntelFormaPago").text().trim() != "" ? $("#divAntelFormaPago").text().trim() : null,
		antelNroServicioCuenta: $("#divAntelNroServicioCuenta").text().trim() != "" ? $("#divAntelNroServicioCuenta").text().trim() : null,
		antelImporte: $("#divAntelImporte").text().trim() != "" ? $("#divAntelImporte").text().trim() : null,

		fechaEnvioANucleo: $("#divFechaEnvioANucleo").text().trim() != "" ? parseLongDate($("#divFechaEnvioANucleo").text().trim()) : null,
		fechaEnvioAIZI: $("#divFechaEnvioAIZI").text().trim() != "" ? parseLongDate($("#divFechaEnvioAIZI").text().trim()) : null,
		fechaEnvioAGLA: $("#divFechaEnvioAGLA").text().trim() != "" ? parseLongDate($("#divFechaEnvioAGLA").text().trim()) : null
	};
	
	if (($("#selectDireccionEntregaDepartamento").length > 0) 
		&& ($("#selectDireccionEntregaDepartamento").val() != "0")) {
		contrato.direccionEntregaDepartamento = {
			id: $("#selectDireccionEntregaDepartamento").val()
		};
	} else if ($("#divDireccionEntregaDepartamento").attr("did") != null 
		&& $("#divDireccionEntregaDepartamento").attr("did") != "") {
		contrato.direccionEntregaDepartamento = {
			id: $("#divDireccionEntregaDepartamento").attr("did")
		};
	}
	
	if (($("#selectDireccionFacturaDepartamento").length > 0) 
		&& ($("#selectDireccionFacturaDepartamento").val() != "0")) {
		contrato.direccionFacturaDepartamento = {
			id: $("#selectDireccionFacturaDepartamento").val()
		};
	} else if ($("#divDireccionFacturaDepartamento").attr("did") != null 
		&& $("#divDireccionFacturaDepartamento").attr("did") != "") {
		contrato.direccionFacturaDepartamento = {
			id: $("#divDireccionFacturaDepartamento").attr("did")
		};
	}
	
	if ($("#selectTipoDocumento").val() != null && $("#selectTipoDocumento").val() != "0") {
		contrato.tipoDocumento = {
			id: $("#selectTipoDocumento").val()
		};
	}
	
	if ($("#selectSexo").val() != null && $("#selectSexo").val() != "0") {
		contrato.sexo = {
			id: $("#selectSexo").val()
		};
	}
	
	if ($("#selectTipoProducto").val() != null && $("#selectTipoProducto").val() != "0") {
		contrato.tipoProducto = {
			id: $("#selectTipoProducto").val()
		};
	}
	
	if ($("#selectEquipo").val() != null && $("#selectEquipo").val() != "" && $("#selectEquipo").val() != "0") {
		contrato.marca = {
			id: $("#selectEquipo").length > 0 ? $("#selectEquipo option:selected").attr("maid") : $("#divEquipo").attr("maid")
		};
		
		contrato.modelo = {
			id: $("#selectEquipo").length > 0 ? $("#selectEquipo").val() : $("#divEquipo").attr("moid")
		};
	}
	
	if ($("#inputNumeroSerie").attr("pid") != null && $("#inputNumeroSerie").attr("pid") != "") {
		contrato.producto = {
			id: $("#inputNumeroSerie").attr("pid")
		};
	}
	
	if ($("#selectNuevoPlan").val() != null && $("#selectNuevoPlan").val() != "0") {
		contrato.nuevoPlan = {
			id: $("#selectNuevoPlan").val()
		};
	}
	
	if ($("#selectMotivoCambioPlan").val() != null && $("#selectMotivoCambioPlan").val() != "0") {
		contrato.motivoCambioPlan = {
			id: $("#selectMotivoCambioPlan").val()
		};
	}
	
	if ($("#selectMoneda").val() != null && $("#selectMoneda").val() != "0") {
		contrato.moneda = {
			id: $("#selectMoneda").val()
		};
	}
	
	if ($("#selectFormaPago").val() != null && $("#selectFormaPago").val() != "" && $("#selectFormaPago").val() != "0") {
		contrato.formaPago = {
			id: $("#selectFormaPago").val()
		};
	}
	
	if ($("#selectModalidadVenta").val() != null && $("#selectModalidadVenta").val() != "0") {
		contrato.modalidadVenta = {
			id: $("#selectModalidadVenta").val()
		};
	}
	
	if ($("#selectAtencionClienteTipoContacto").val() != null && $("#selectAtencionClienteTipoContacto").val() != "0") {
		contrato.atencionClienteTipoContacto = {
			id: $("#selectAtencionClienteTipoContacto").val()
		};
	}
	
	if ($("#selectAtencionClienteConcepto").val() != null && $("#selectAtencionClienteConcepto").val() != "0") {
		contrato.atencionClienteConcepto = {
			id: $("#selectAtencionClienteConcepto").val()
		};
	}
	
	if ($("#selectAtencionClienteRespuestaTecnicaComercial").val() != null && $("#selectAtencionClienteRespuestaTecnicaComercial").val() != "0") {
		contrato.atencionClienteRespuestaTecnicaComercial = {
			id: $("#selectAtencionClienteRespuestaTecnicaComercial").val()
		};
	}
	
	if ($("#selectTarjetaCredito").val() != null && $("#selectTarjetaCredito").val() != "0") {
		contrato.tarjetaCredito = {
			id: $("#selectTarjetaCredito").val()
		};
	}
	
	if ($("#selectCuotas").val() != null && $("#selectCuotas").val() != "0") {
		contrato.cuotas = $("#selectCuotas").val();
	}
	
	if ($("#divResultadoEntregaDistribucion").attr("redid") != null && $("#divResultadoEntregaDistribucion").attr("redid") != "") {
		contrato.resultadoEntregaDistribucion = {
			id: $("#divResultadoEntregaDistribucion").attr("redid")
		};
	}
	
	if ($("#selectDepartamento").val() != null && $("#selectDepartamento").val() != "0" 
		&& $("#selectBarrio").val() != null && $("#selectBarrio").val() != "0" 
		&& $("#selectZona").val() != null && $("#selectZona").val() != "0") {
		contrato.zona = {
			id: $("#selectZona").val(),
			departamento: {
				id: $("#selectDepartamento").val()
			}
		};
		contrato.barrio = {
			id: $("#selectBarrio").val(),
			departamento: {
				id: $("#selectDepartamento").val()
			},
			zona: contrato.zona
		};
	}
	
	if ($("#selectTurno").val() != null && $("#selectTurno").val() != "0") {
		contrato.turno = {
			id: $("#selectTurno").val()
		};
	}
	
	if ($("#selectFechaEntrega").val() != null && $("#selectFechaEntrega").val() != "0") {
		contrato.fechaEntrega = parseShortDate($("#selectFechaEntrega").val());
	}
	
	if ($("#selectEmpresa").length > 0) {
		contrato.empresa = {
			id: $("#selectEmpresa").val()
		};
	} else if ($("#divEmpresa").attr("eid") != null && $("#divEmpresa").attr("eid") != "") {
		contrato.empresa = {
			id: $("#divEmpresa").attr("eid")
		};
	}
	
	if ($("#divTipoTasaInteresEfectivaAnual").attr("tid") != null && $("#divTipoTasaInteresEfectivaAnual").attr("tid") != "") {
		contrato.tipoTasaInteresEfectivaAnual = {
			id: $("#divTipoTasaInteresEfectivaAnual").attr("tid")
		};
	}
	
	if ($("#divRol").attr("rid") != null && $("#divRol").attr("rid") != "") {
		contrato.rol = {
			id: $("#divRol").attr("rid")
		};
	}
	
	if ($("#divUsuario").attr("uid") != null && $("#divUsuario").attr("uid") != "") {
		contrato.usuario = {
			id: $("#divUsuario").attr("uid")
		};
	}
	
	if ($("#divVendedor").attr("vid") != null && $("#divVendedor").attr("vid") != "") {
		contrato.vendedor = {
			id: $("#divVendedor").attr("vid")
		};
	}
	
	if ($("#divBackoffice").attr("bid") != null && $("#divBackoffice").attr("bid") != "") {
		contrato.backoffice = {
			id: $("#divBackoffice").attr("bid")
		};
	}
	
	if ($("#divDistribuidor").attr("did") != null && $("#divDistribuidor").attr("did") != "") {
		contrato.distribuidor = {
			id: $("#divDistribuidor").attr("did")
		};
	}
	
	if ($("#divActivador").attr("aid") != null && $("#divActivador").attr("aid") != "") {
		contrato.activador = {
			id: $("#divActivador").attr("aid")
		};
	}
	
	if ($("#divCoordinador").attr("coid") != null && $("#divCoordinador").attr("coid") != "") {
		contrato.coordinador = {
			id: $("#divCoordinador").attr("coid")
		};
	}
	
	if ($("#divAtencionClienteOperador").attr("acoid") != null && $("#divAtencionClienteOperador").attr("acoid") != "") {
		contrato.atencionClienteOperador = {
			id: $("#divAtencionClienteOperador").attr("acoid")
		};
	}
	
	if ($("#divAtencionClienteGestionador").attr("acgid") != null && $("#divAtencionClienteGestionador").attr("acgid") != "") {
		contrato.atencionClienteGestionador = {
			id: $("#divAtencionClienteGestionador").attr("acgid")
		};
	}
	
	var archivosAdjuntos = [];
	var imgs = $(".divGalleryContent > img");
	for (var i=0; i<imgs.length; i++) {
		archivosAdjuntos[archivosAdjuntos.length] = {
			id: $(imgs[i]).attr("aaid")
		};
	}
	contrato.archivosAdjuntos = archivosAdjuntos;
	
	var direcciones = [];
	direcciones[direcciones.length] = {
		id: $("#divTab1").attr("did"),
		tipoDireccion: {
			id: 1
		}, 
		calle: $("#inputDireccionEntregaCalle").length > 0 ?
			($("#inputDireccionEntregaCalle").val().trim() != "" ? $("#inputDireccionEntregaCalle").val().trim() : null) :
			$("#divDireccionEntregaCalle").text().trim(),
		numero: $("#inputDireccionEntregaNumero").length > 0 ?
			($("#inputDireccionEntregaNumero").val().trim() != "" ? $("#inputDireccionEntregaNumero").val().trim() : null)
			: ($("#divDireccionEntregaNumero").text().trim() != "" ? $("#divDireccionEntregaNumero").text().trim() : null),
		bis: $("#inputDireccionEntregaBis").length > 0 ?
			$("#inputDireccionEntregaBis").prop("checked") : 
			$("#divDireccionEntregaBis").text().trim(),
		block: $("#inputDireccionEntregaBlock").length > 0 ?
			($("#inputDireccionEntregaBlock").val().trim() != "" ? $("#inputDireccionEntregaBlock").val().trim() : null) :
			$("#divDireccionEntregaBlock").text().trim(),
		apto: $("#inputDireccionEntregaApto").length > 0 ?
			($("#inputDireccionEntregaApto").val().trim() != "" ? $("#inputDireccionEntregaApto").val().trim() : null) :
			$("#divDireccionEntregaApto").text().trim(),
		solar: $("#inputDireccionEntregaSolar").length > 0 ?
			($("#inputDireccionEntregaSolar").val().trim() != "" ? $("#inputDireccionEntregaSolar").val().trim() : null) :
			$("#divDireccionEntregaSolar").text().trim(),
		manzana: $("#inputDireccionEntregaManzana").length > 0 ?
			($("#inputDireccionEntregaManzana").val().trim() != "" ? $("#inputDireccionEntregaManzana").val().trim() : null) 
			: ($("#divDireccionEntregaManzana").text().trim() != "" ? $("#divDireccionEntregaManzana").text().trim() : null),
		codigoPostal: $("#inputDireccionEntregaCodigoPostal").length > 0 ?
			($("#inputDireccionEntregaCodigoPostal").val().trim() != "" ? $("#inputDireccionEntregaCodigoPostal").val().trim() : null)
			: ($("#divDireccionEntregaCodigoPostal").text().trim() != "" ? $("#divDireccionEntregaCodigoPostal").text().trim() : null),
		localidad: $("#inputDireccionEntregaLocalidad").length > 0 ?
			($("#inputDireccionEntregaLocalidad").val().trim() != "" ? $("#inputDireccionEntregaLocalidad").val().trim() : null) :
			$("#divDireccionEntregaLocalidad").text().trim(),
		observaciones: $("#inputDireccionEntregaObservaciones").length > 0 ?
			($("#inputDireccionEntregaObservaciones").val().trim() != "" ? $("#inputDireccionEntregaObservaciones").val().trim() : null) :
			$("#divDireccionEntregaObservaciones").text().trim()
	};
	
	if (($("#selectDireccionEntregaDepartamento").length > 0) && ($("#selectDireccionEntregaDepartamento").val() != "0")) {
		direcciones[direcciones.length - 1].departamento = {
			id: $("#selectDireccionEntregaDepartamento").val()
		};
	} else if ($("#divDireccionEntregaDepartamento").attr("did") != null && $("#divDireccionEntregaDepartamento").attr("did") != "") {
		direcciones[direcciones.length - 1].departamento = {
			id: $("#divDireccionEntregaDepartamento").attr("did")
		};
	}
	
	direcciones[direcciones.length] = {
		id: $("#divTab2").attr("did"),
		tipoDireccion: {
			id: 2
		},
		calle: $("#inputDireccionFacturaCalle").length > 0 ?
			($("#inputDireccionFacturaCalle").val().trim() != "" ? $("#inputDireccionFacturaCalle").val().trim() : null) :
			$("#divDireccionFacturaCalle").text().trim(),
		numero: $("#inputDireccionFacturaNumero").length > 0 ?
			($("#inputDireccionFacturaNumero").val().trim() != "" ? $("#inputDireccionFacturaNumero").val().trim() : null)
			: ($("#divDireccionFacturaNumero").text().trim() != "" ? $("#divDireccionFacturaNumero").text().trim() : null),
		bis: $("#inputDireccionFacturaBis").length > 0 ?
			$("#inputDireccionFacturaBis").prop("checked") : 
			$("#divDireccionFacturaBis").text().trim(),
		block: $("#inputDireccionFacturaBlock").length > 0 ?
			($("#inputDireccionFacturaBlock").val().trim() != "" ? $("#inputDireccionFacturaBlock").val().trim() : null) :
			$("#divDireccionFacturaBlock").text().trim(),
		apto: $("#inputDireccionFacturaApto").length > 0 ?
			($("#inputDireccionFacturaApto").val().trim() != "" ? $("#inputDireccionFacturaApto").val().trim() : null) :
			$("#divDireccionFacturaApto").text().trim(),
		solar: $("#inputDireccionFacturaSolar").length > 0 ?
			($("#inputDireccionFacturaSolar").val().trim() != "" ? $("#inputDireccionFacturaSolar").val().trim() : null) :
			$("#divDireccionFacturaSolar").text().trim(),
		manzana: $("#inputDireccionFacturaManzana").length > 0 ?
			($("#inputDireccionFacturaManzana").val().trim() != "" ? $("#inputDireccionFacturaManzana").val().trim() : null)
			: ($("#divDireccionFacturaManzana").text().trim() != "" ? $("#divDireccionFacturaManzana").text().trim() : null),
		codigoPostal: $("#inputDireccionFacturaCodigoPostal").length > 0 ?
			($("#inputDireccionFacturaCodigoPostal").val().trim() != "" ? $("#inputDireccionFacturaCodigoPostal").val().trim() : null)
			: ($("#divDireccionFacturaCodigoPostal").text().trim() != "" ? $("#divDireccionFacturaCodigoPostal").text().trim() : null),
		localidad: $("#inputDireccionFacturaLocalidad").length > 0 ?
			($("#inputDireccionFacturaLocalidad").val().trim() != "" ? $("#inputDireccionFacturaLocalidad").val().trim() : null) :
			$("#divDireccionFacturaLocalidad").text().trim(),
		observaciones: $("#inputDireccionFacturaObservaciones").length > 0 ?
			($("#inputDireccionFacturaObservaciones").val().trim() != "" ? $("#inputDireccionFacturaObservaciones").val().trim() : null) :
			$("#divDireccionFacturaObservaciones").text().trim()
	};
	
	if (($("#selectDireccionFacturaDepartamento").length > 0) && ($("#selectDireccionFacturaDepartamento").val() != "0")) {
		direcciones[direcciones.length - 1].departamento = {
			id: $("#selectDireccionFacturaDepartamento").val()
		};
	} else if ($("#divDireccionFacturaDepartamento").attr("did") != null && $("#divDireccionFacturaDepartamento").attr("did") != "") {
		direcciones[direcciones.length - 1].departamento = {
			id: $("#divDireccionFacturaDepartamento").attr("did")
		};
	}
	
	contrato.direcciones = direcciones;
	
	return contrato;
}