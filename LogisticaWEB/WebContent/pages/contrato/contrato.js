var __SEMANAS_DISPONIBILIDAD_FECHA_ENTREGA = 4;
var __FORMA_PAGO_TARJETA_ID = 3;
var __FORMA_PAGO_NUESTRO_CREDITO_ID = 4;
var __FORMA_PAGO_NUESTRO_CREDITO_CUOTAS_DEFAULT = 12;
var __ELEGIBILIDAD_FINANCIACION_RECHAZAR = 0;
var __ELEGIBILIDAD_FINANCIACION_REALIZAR_CLEARING = 1;
var __ELEGIBILIDAD_FINANCIACION_NO_REALIZAR_CLEARING = 2;
var __ELEGIBILIDAD_SIN_ANALISIS = -1;
var __TIPO_ARCHIVO_ADJUNTO_REMITO_RIVERGREEN = 2;

var gridArchivosAdjuntos = null;
var gridContratosRelacionados = null;

$(document).ready(init);

function init() {
	initTabbedPanel();
	initTabArchivosAdjuntos();
	initTabContratosRelacionados();
	
	refinarForm();
	
	if ($("#selectEmpresa").length > 0) {
		UsuarioRolEmpresaDWR.listEmpresasByContext(
			{
				callback: function(data) {
					fillSelect("selectEmpresa", data, "id", "nombre");
				}, async: false
			}
		);
	}
	
	if ($("#selectNuevoPlan").length > 0) {
		PlanDWR.listVigentes(
			{
				callback: function(data) {
					fillSelect("selectNuevoPlan", data, "id", "descripcion");
				}, async: false
			}
		);
	}
	
	if ($("#selectMotivoCambioPlan").length > 0) {
		MotivoCambioPlanDWR.list(
			{
				callback: function(data) {
					fillSelect("selectMotivoCambioPlan", data, "id", "descripcion");
				}, async: false
			}
		);
	}
	
	if ($("#selectMoneda").length > 0) {
		MonedaDWR.list(
			{
				callback: function(data) {
					fillSelect("selectMoneda", data, "id", "simbolo");
				}, async: false
			}
		);
	}
	
	if ($("#selectModalidadVenta").length > 0) {
		ModalidadVentaDWR.list(
			{
				callback: function(data) {
					fillSelect("selectModalidadVenta", data, "id", "descripcion");
				}, async: false
			}
		);
	}
	
	if ($("#selectTarjetaCredito").length > 0) {
		TarjetaCreditoDWR.list(
			{
				callback: function(data) {
					fillSelect("selectTarjetaCredito", data, "id", "nombre");
				}, async: false
			}
		);
	}
	
	if ($("#selectCuotas").length > 0) {
		fillSelect(
			"selectCuotas", 
			[ { id: 1, valor: 1 }, 
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
	
	if ($("#selectTipoDocumento").length > 0) {
		TipoDocumentoDWR.list(
			{
				callback: function(data) {
					fillSelect("selectTipoDocumento", data, "id", "descripcion");
				}, async: false
			}
		);
	}
	
	if ($("#selectSexo").length > 0) {
		SexoDWR.list(
			{
				callback: function(data) {
					fillSelect("selectSexo", data, "id", "descripcion");
				}, async: false
			}
		);
	}
	
	if ($("#selectDireccionFacturaDepartamento").length > 0) {
		DepartamentoDWR.list(
			{
				callback: function(data) {
					fillSelect("selectDireccionFacturaDepartamento", data, "id", "nombre");
				}, async: false
			}
		);
	}
	
	if ($("#selectDireccionEntregaDepartamento").length > 0) {
		DepartamentoDWR.list(
			{
				callback: function(data) {
					fillSelect("selectDireccionEntregaDepartamento", data, "id", "nombre");
				}, async: false
			}
		);
	}
	
	if ($("#selectDepartamento").length > 0) {
		DepartamentoDWR.list(
			{
				callback: function(data) {
					fillSelect("selectDepartamento", data, "id", "nombre");
				}, async: false
			}
		);
	}
	
	if ($("#selectTipoProducto").length > 0) {
		TipoProductoDWR.list(
			{
				callback: function(data) {
					fillSelect("selectTipoProducto", data, "id", "descripcion");
				}, async: false
			}
		);
	}
	
	$("#selectFechaEntrega").append("<option value='0'>Seleccione...</option>");
	$("#selectEquipo").append("<option value='0'>Seleccione...</option>");
	$("#selectBarrio").append("<option value='0'>Seleccione...</option>");
	$("#selectZona").append("<option value='0'>Seleccione...</option>");
	$("#selectTurno").append("<option value='0'>Seleccione...</option>");
	$("#selectFormaPago").append("<option value='0'>Seleccione...</option>");
	
	if (id != null) {
		ContratoDWR.getById(
			id,
			{
				callback: function(data) {
					// Fechas del workflow
					// -------------------
					
					populateField("fechaVenta", data, "fechaVenta", "fechaVenta", null, null, formatRawDate);
					populateField("fechaVentaMostrar", data, "fechaVenta", "fechaVenta", null, null, formatLongDate);
					populateField("fechaBackoffice", data, "fechaBackoffice", "fechaBackoffice", null, null, formatRawDate);
					populateField("fechaEntregaDistribuidor", data, "fechaEntregaDistribuidor", "fechaEntregaDistribuidor", null, null, formatRawDate);
					populateField("fechaDevolucionDistribuidor", data, "fechaDevolucionDistribuidor", "fechaDevolucionDistribuidor", null, null, formatRawDate);
					populateField("fechaEnvioAntel", data, "fechaEnvioAntel", "fechaEnvioAntel", null, null, formatRawDate);
					populateField("fechaActivacion", data, "fechaActivacion", "fechaActivacion", null, null, formatRawDate);
					populateField("fechaCoordinacion", data, "fechaCoordinacion", "fechaCoordinacion", null, null, formatRawDate);
					populateField("fechaRechazo", data, "fechaRechazo", "fechaRechazo", null, null, formatRawDate);

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
					populateField("fechaVencimiento", data, "fechaFinContrato", "fechaFinContrato", null, null, formatShortDate);
					populateField("numeroContrato", data, "numeroContrato", "numeroContrato");
					populateField("plan", data, "tipoContratoDescripcion", "tipoContratoDescripcion");
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
					
					populateField("motivoCambioPlan", data, "motivoCambioPlan.id", "motivoCambioPlan.descripcion");
					populateField("tipoProducto", data, "tipoProducto.id", "tipoProducto.descripcion");
					
					if (data.empresa != null) {
						if ($("#selectEquipo").length > 0) {
							StockMovimientoDWR.listStockByEmpresaId(
								data.empresa.id,
								{
									callback: function(dataStock) {
										var equipos = [];
										
										var found = false;
										for (var i=0; i<dataStock.length; i++) {
											if (dataStock[i].cantidad > 0 && dataStock[i].fechaBaja == null) {
												equipos[equipos.length] = {
													id: dataStock[i].modelo.id,
													descripcion: dataStock[i].marca.nombre + " " + dataStock[i].modelo.descripcion + " (" + dataStock[i].cantidad + ")",
													marca: dataStock[i].marca,
													modelo: dataStock[i].modelo,
													tipoProducto: dataStock[i].tipoProducto
												};
												
												found = 
													found || (data.marca != null && data.modelo != null && dataStock[i].marca.id == data.marca.id && dataStock[i].modelo.id == data.modelo.id);
											}
										}
										
										if (!found && data.marca != null && data.modelo != null) {
											equipos[equipos.length] = {
												id: data.modelo.id,
												descripcion: data.marca.nombre + " " + data.modelo.descripcion,
												marca: data.marca,
												modelo: data.modelo,
												tipoProducto: data.tipoProducto
											};
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
										
										populateField("equipo", data, "modelo.id", "modelo.descripcion");
									}, async: false
								}
							);
						} else {
							populateField("equipo", data, "modelo.id", "modelo.descripcion");
						}
						
						reloadFormasPago(data.empresa.id, data.documento);
					}
					populateField("incluirChip", data, "incluirChip", "incluirChip");
					populateField("costoEnvio", data, "costoEnvio", "costoEnvio");
					
					populateField("precio", data, "precio", "precio");
					populateField("moneda", data, "moneda.id", "moneda.simbolo", "mid", "moneda.id");
					if (data.formaPago != null) {
						var options = $("#selectFormaPago option");
						var found = false;
						for (var i=0; i<options.length; i++) {
							if ($(options[i]).attr("value") == data.formaPago.id) {
								found = true;
								break;
							}
						}
						
						if (!found) {
							$("#selectFormaPago").append(
								"<option value=" + data.formaPago.id + ">" + data.formaPago.descripcion + "</option>"
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
//								$("#divLabelFechaActivarEn").addClass("requiredFormLabel");
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
					if (data.tipoTasaInteresEfectivaAnual != null) {
						populateField("tipoTasaInteresEfectivaAnual", data, "tipoTasaInteresEfectivaAnual.id", "tipoTasaInteresEfectivaAnual.descripcion", "tid", "tipoTasaInteresEfectivaAnual.id");
					} else {
						$("#divTipoTasaInteresEfectivaAnual").attr("tid", 1);
					}
					populateField("tarjetaCredito", data, "tarjetaCredito.id", "tarjetaCredito.nombre", "tid", "tarjetaCredito.id");
					populateField("cuotas", data, "cuotas", "cuotas");
					if (data.cuotas != null) {
						showField("cuotas");
					}
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
					
					if ($("#selectTurno").length > 0) {
						selectTurnoOnChange();
					}
					
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
					
					populateField("direccionFacturaDepartamento", data, "direccionFacturaDepartamento.id", "direccionFacturaDepartamento.nombre");
					populateField("direccionEntregaDepartamento", data, "direccionEntregaDepartamento.id", "direccionEntregaDepartamento.nombre");
					
					populateField("resultadoEntregaDistribucion", data, "resultadoEntregaDistribucion.descripcion", "redid", "resultadoEntregaDistribucion.id");
					populateField("resultadoEntregaDistribucionLatitud", data, "resultadoEntregaDistribucionLatitud");
					populateField("resultadoEntregaDistribucionLongitud", data, "resultadoEntregaDistribucionLongitud");
					populateField("resultadoEntregaDistribucionPrecision", data, "resultadoEntregaDistribucionPrecision");
					populateField("resultadoEntregaDistribucionObservaciones", data, "resultadoEntregaDistribucionObservaciones", "resultadoEntregaDistribucionObservaciones");
					
					populateField("antelNroTrn", data, "antelNroTrn", "antelNroTrn");
					populateField("antelFormaPago", data, "antelFormaPago", "antelFormaPago");
					populateField("antelNroServicioCuenta", data, "antelNroServicioCuenta", "antelNroServicioCuenta");
					populateField("antelImporte", data, "antelImporte", "antelImporte");
					
					
					populateField("fechaEnvioANucleo", data, "fechaEnvioANucleo", "fechaEnvioANucleo", null, null, formatLongDate);
					
					reloadArchivosAdjuntosData(data);
					reloadContratosRelacionadosData(data);
				}, async: false
			}
		);
	}
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
	TipoArchivoAdjuntoDWR.list(
		{
			callback: function(data) {
				fillSelect("selectTipoArchivoAdjunto", data, "id", "descripcion");
			}, async: false
		}
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
}

function reloadArchivosAdjuntosData(contrato) {
	var data = {
		cantidadRegistros: 0,
		registrosMuestra: []
	};
			
	if (contrato != null) {
		for (var i=0; i<contrato.archivosAdjuntos.length; i++) {
			if (mode == __FORM_MODE_ANTEL) {
				if (contrato.archivosAdjuntos[i].tipoArchivoAdjunto != null
				&& contrato.archivosAdjuntos[i].tipoArchivoAdjunto.id == __TIPO_ARCHIVO_ADJUNTO_REMITO_RIVERGREEN) {
					var url =
						"<a href=\"/LogisticaWEB/Download?fn=" + contrato.archivosAdjuntos[i].url + "&f=s\">" 
							+ contrato.archivosAdjuntos[i].url 
						+ "</a>";
					
					data.registrosMuestra[data.cantidadRegistros] = contrato.archivosAdjuntos[i];
					data.registrosMuestra[data.cantidadRegistros].urlLink = url;
					data.cantidadRegistros++;
				}
			} else {
				var url =
					"<a href=\"/LogisticaWEB/Download?fn=" + contrato.archivosAdjuntos[i].url + "&f=s\">" 
						+ contrato.archivosAdjuntos[i].url 
					+ "</a>";
				
				data.registrosMuestra[data.cantidadRegistros] = contrato.archivosAdjuntos[i];
				data.registrosMuestra[data.cantidadRegistros].urlLink = url;
				data.cantidadRegistros++;
			}
		}
	} else {
		ContratoArchivoAdjuntoDWR.listByContratoId(
			id,
			{
				callback: function(archivoAdjuntos) {
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
							var url =
								"<a href=\"/LogisticaWEB/Download?fn=" + archivoAdjuntos[i].url + "&f=s\">" 
									+ archivoAdjuntos[i].url 
								+ "</a>";
							
							data.registrosMuestra[data.cantidadRegistros] = archivoAdjuntos[i];
							data.registrosMuestra[data.cantidadRegistros].urlLink = url;
							data.cantidadRegistros++;
						}
					}
				}, async: false
			}
		);
	}
	
	var html = "";
	var imgGalleryClass = "imgGalleryActive";
	for (var i=0; i<data.registrosMuestra.length; i++) {
		if (i > 0) {
			imgGalleryClass = "imgGalleryInactive";
		}
		
		html += "<img class='" + imgGalleryClass + "'"
			+ " aaid='" + data.registrosMuestra[i].id + "'"
			+ " src='/LogisticaWEB/Stream?fn=" + data.registrosMuestra[i].url + "'/>";
	}
	
	$(".divGalleryContent").html(html != "" ? html : "&nbsp;");
	
	gridArchivosAdjuntos.reload(data);
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
	
	if (inputFile != null && inputFile != "") {
		var xmlHTTPRequest = new XMLHttpRequest();
		xmlHTTPRequest.open(
			"POST",
			"/LogisticaWEB/Upload",
			false
		);
		
		var formData = new FormData(document.getElementById("formArchivo"));
		formData.append("caller", "contrato_archivo_adjunto");
		formData.append("inputId", id);
		formData.append("tipoArchivoAdjuntoId", tipoArchivoAdjuntoId);
		
		xmlHTTPRequest.send(formData);
		
		if (xmlHTTPRequest.status != 200) {
			alert(xmlHTTPRequest.responseText);
			return;
		} else {
			alert(JSON.parse(xmlHTTPRequest.responseText).message);
			$("#selectTipoArchivoAdjunto").val(0);
			$("#inputAdjunto").val(null);
			
			reloadArchivosAdjuntosData();
		}
	} else {
		alert("Debe seleccionar un archivo.");
	}
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

function reloadContratosRelacionadosData(contrato) {
	var data = {
		cantidadRegistros: 0,
		registrosMuestra: []
	};
			
//	if (contrato != null) {
//		
//	} else {
		ContratoRelacionDWR.listByContratoId(
			id,
			{
				callback: function(contratosRelacionados) {
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
				}, async: false
			}
		);
//	}
	
	gridContratosRelacionados.reload(data);
}

function trContratosRelacionadosOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var contratoRelacionId = $(target).attr("id");
}

function inputAgregarContratoRelacionadoOnClick(event, element) {
	var inputNumeroTramite = $("#inputNumeroTramiteRelacionado").val();
	
	if (inputNumeroTramite != null && inputNumeroTramite != "") {
		ContratoDWR.getByNumeroTramite(
			inputNumeroTramite,
			{
				callback: function(data) {
					var contratoRelacion = {
						contrato: {
							id: id
						},
						contratoRelacionado: {
							id: data.id
						}
					};
					
					ContratoRelacionDWR.add(
						contratoRelacion,
						{
							callback: function(dataResultado) {
								alert("Operacion exitosa.");
								
								$("#inputNumeroTramiteRelacionado").val(null);
								
								reloadContratosRelacionadosData();
							}, async: false
						}
					);
				}, async: false
			}
		);
	} else {
		alert("Debe seleccionar un número de trámite.");
	}
}

function refinarForm() {
	hideField("empresa");
	hideField("rol");
	hideField("usuario");
	hideField("vendedor");
	hideField("backoffice");
	hideField("distribuidor");
	hideField("activador");
	hideField("coordinador");
	hideField("fechaVenta");
	hideField("fechaVentaMostrar");
	hideField("fechaBackoffice");
	hideField("fechaEntregaDistribuidor");
	hideField("fechaDevolucionDistribuidor");
	hideField("fechaEnvioAntel");
	hideField("fechaActivacion");
	hideField("fechaCoordinacion");
	hideField("fechaEnvioANucleo");
	hideField("nuevoPlanString");
	hideField("resultadoEntregaDistribucion");
	hideField("resultadoEntregaDistribucionObservaciones");
	hideField("resultadoEntregaDistribucionLatitud");
	hideField("resultadoEntregaDistribucionLongitud");
	hideField("resultadoEntregaDistribucionPrecision");
	hideField("tipoTasaInteresEfectivaAnual");
	hideField("tarjetaCredito");
	hideField("cuotas");
	hideField("valorCuota");
	hideField("numeroVale");
	hideField("intereses");
	hideField("gastosAdministrativos");
	hideField("gastosConcesion");
	hideField("gastosAdministrativosTotales");
	hideField("unidadIndexada");
	hideField("tasaInteresEfectivaAnual");
	hideField("antelNroTrn");
	hideField("antelFormaPago");
	hideField("antelNroServicioCuenta");
	hideField("antelImporte");
	hideField("incluirChip");
	hideField("random");
	
	$("#divRealizarClearing").hide();
	$("#inputNumeroFacturaRiverGreen").prop("disabled", true);
	
	$(".divButtonBar > div").hide();
	$(".divButtonTitleBar > div").hide();
	
	if (mode == __FORM_MODE_READ) {
		$("#divEmpresa").html("&nbsp;");
		$("#divMid").html("&nbsp;");
		$("#divPlan").html("&nbsp;");
		$("#divNumeroContrato").html("&nbsp;");
		$("#divNuevoPlan").html("&nbsp;");
		$("#divMotivoCambioPlan").html("&nbsp;");
		$("#divModalidadVenta").html("&nbsp;");
		$("#divTipoProducto").html("&nbsp;");
		$("#divEquipo").html("&nbsp;");
		$("#divNumeroSerie").html("&nbsp;");
		$("#divNumeroChip").html("&nbsp;");
		$("#divNumeroBloqueo").html("&nbsp;");
		$("#divTipoDocumento").html("&nbsp;");
		$("#divDocumento").html("&nbsp;");
		$("#divNombre").html("&nbsp;");
		$("#divApellido").html("&nbsp;");
		$("#divFechaNacimiento").html("&nbsp;");
		$("#divSexo").html("&nbsp;");
		$("#divNumeroFactura").html("&nbsp;");
		$("#divNumeroFacturaRiverGreen").html("&nbsp;");
		$("#divAgregarAdjunto").html("&nbsp");
		$("#divTipoArchivoAdjunto").html("&nbsp");
		
		$("#divDireccionFactura").html("&nbsp;");
		$("#divDireccionFacturaCalle").html("&nbsp;");
		$("#divDireccionFacturaNumero").html("&nbsp;");
		$("#divDireccionFacturaBis").html("&nbsp;");
		$("#divDireccionFacturaBlock").html("&nbsp;");
		$("#divDireccionFacturaApto").html("&nbsp;");
		$("#divDireccionFacturaSolar").html("&nbsp;");
		$("#divDireccionFacturaManzana").html("&nbsp;");
		$("#divDireccionFacturaCodigoPostal").html("&nbsp;");
		$("#divDireccionFacturaLocalidad").html("&nbsp;");
		$("#divDireccionFacturaObservaciones").html("&nbsp;");
		$("#divDireccionFacturaDepartamento").html("&nbsp;");
		
		$("#divDireccionEntrega").html("&nbsp;");
		$("#divDireccionEntregaCalle").html("&nbsp;");
		$("#divDireccionEntregaNumero").html("&nbsp;");
		$("#divDireccionEntregaBis").html("&nbsp;");
		$("#divDireccionEntregaBlock").html("&nbsp;");
		$("#divDireccionEntregaApto").html("&nbsp;");
		$("#divDireccionEntregaSolar").html("&nbsp;");
		$("#divDireccionEntregaManzana").html("&nbsp;");
		$("#divDireccionEntregaCodigoPostal").html("&nbsp;");
		$("#divDireccionEntregaLocalidad").html("&nbsp;");
		$("#divDireccionEntregaObservaciones").html("&nbsp;");
		$("#divDireccionEntregaDepartamento").html("&nbsp;");
		
		$("#divTelefonoContacto").html("&nbsp;");
		$("#divEmail").html("&nbsp;");
		$("#divPrecio").html("&nbsp;");
		$("#divValorCuota").html("&nbsp;");
		$("#divMoneda").html("&nbsp;");
		$("#divFormaPago").html("&nbsp;");
		$("#divTarjetaCredito").html("&nbsp;");
		$("#divCuotas").html("&nbsp;");
		$("#divDepartamento").html("&nbsp;");
		$("#divBarrio").html("&nbsp;");
		$("#divZona").html("&nbsp;");
		$("#divTurno").html("&nbsp;");
		$("#divFechaEntrega").html("&nbsp;");
		$("#divFechaActivarEn").html("&nbsp;");
		$("#divObservaciones").html("&nbsp;");
		$("#divCostoEnvio").html("&nbsp;");
		
		$("#divObservaciones").css("width", "65%");
	} else if (mode == __FORM_MODE_NEW) {
		showField("empresa");
		
		$("#divNumeroSerie").html("&nbsp;");
		hideField("numeroSerie");
		
		$("#divNumeroChip").html("&nbsp;");
		hideField("numeroChip");
		
		$("#divNumeroBloqueo").html("&nbsp;");
		hideField("numeroBloqueo");
		
		$("#divNumeroFactura").html("&nbsp;");
		hideField("numeroFactura");
		
		$("#divNumeroFacturaRiverGreen").html("&nbsp;");
		hideField("numeroFacturaRiverGreen");
		
		$("#divFechaAtivarEn").html("&nbsp;");
		hideField("fechaActivarEn");
		
		$("#divAgregarAdjunto").html("&nbsp");
		
		$("#divLabelEmpresa").addClass("requiredFormLabel");
		$("#divLabelMid").addClass("requiredFormLabel");
		
		$("#divInputGuardar").show();
		
		$("#divButtonTitleFourfoldSize").hide();
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
	} else if (mode == __FORM_MODE_SUPERVISOR_CALL_CENTER) {
		$("#divEmpresa").html("&nbsp;");
		$("#divMid").html("&nbsp;");
		
		showField("incluirChip");
		
		$("#divNumeroSerie").html("&nbsp;");
		hideField("numeroSerie");
		
		$("#divNumeroChip").html("&nbsp;");
		hideField("numeroChip");
		
		$("#divNumeroBloqueo").html("&nbsp;");
		hideField("numeroBloqueo");
		
		$("#divNumeroFactura").html("&nbsp;");
		hideField("numeroFactura");
		
		$("#divNumeroFacturaRiverGreen").html("&nbsp;");
		hideField("numeroFacturaRiverGreen");
		
		$("#divFechaAtivarEn").html("&nbsp;");
		hideField("fechaActivarEn");
		
		$("#divLabelModalidadVenta").addClass("requiredFormLabel");
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelTipoProducto").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
		$("#divLabelFormaPago").addClass("requiredFormLabel");
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
		$("#divLabelApellido").addClass("requiredFormLabel");
		$("#divLabelFechaNacimiento").addClass("requiredFormLabel");
		$("#divLabelSexo").addClass("requiredFormLabel");
		$("#divLabelDireccionFactura").addClass("requiredFormLabel");
		$("#divLabelDireccionEntrega").addClass("requiredFormLabel");
		$("#divLabelTelefonoContacto").addClass("requiredFormLabel");
		$("#divLabelEmail").addClass("requiredFormLabel");
		$("#divLabelDepartamento").addClass("requiredFormLabel");
		$("#divLabelBarrio").addClass("requiredFormLabel");
		$("#divLabelZona").addClass("requiredFormLabel");
		$("#divLabelTurno").addClass("requiredFormLabel");
		$("#divLabelFechaEntrega").addClass("requiredFormLabel");
		$("#divLabelCostoEnvio").addClass("requiredFormLabel");
		
		$("#divInputAgendar").show();
		$("#divInputPosponer").show();
		$("#divInputRechazar").show();
		$("#divInputTelelink").show();
		$("#divInputRenovo").show();

		$("#divInputEstadoRiesgoCrediticio").show();
		$("#divInputGuardar").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleQuintupleSize");
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleDoubleSize");
	} else if (mode == __FORM_MODE_VENTA) {
		$("#divEmpresa").html("&nbsp;");
		$("#divMid").html("&nbsp;");
		
		showField("incluirChip");
		
		$("#divNumeroSerie").html("&nbsp;");
		hideField("numeroSerie");
		
		$("#divNumeroChip").html("&nbsp;");
		hideField("numeroChip");
		
		$("#divNumeroBloqueo").html("&nbsp;");
		hideField("numeroBloqueo");
		
		$("#divNumeroFactura").html("&nbsp;");
		hideField("numeroFactura");
		
		$("#divNumeroFacturaRiverGreen").html("&nbsp;");
		hideField("numeroFacturaRiverGreen");
		
		$("#divFechaAtivarEn").html("&nbsp;");
		hideField("fechaActivarEn");
		
		$("#divLabelModalidadVenta").addClass("requiredFormLabel");
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelTipoProducto").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
		$("#divLabelFormaPago").addClass("requiredFormLabel");
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
		$("#divLabelApellido").addClass("requiredFormLabel");
		$("#divLabelFechaNacimiento").addClass("requiredFormLabel");
		$("#divLabelSexo").addClass("requiredFormLabel");
		$("#divLabelDireccionFactura").addClass("requiredFormLabel");
		$("#divLabelDireccionEntrega").addClass("requiredFormLabel");
		$("#divLabelTelefonoContacto").addClass("requiredFormLabel");
		$("#divLabelEmail").addClass("requiredFormLabel");
		$("#divLabelDepartamento").addClass("requiredFormLabel");
		$("#divLabelBarrio").addClass("requiredFormLabel");
		$("#divLabelZona").addClass("requiredFormLabel");
		$("#divLabelTurno").addClass("requiredFormLabel");
		$("#divLabelFechaEntrega").addClass("requiredFormLabel");
		$("#divLabelCostoEnvio").addClass("requiredFormLabel");
		
		$("#divInputAgendar").show();
		$("#divInputPosponer").show();
		$("#divInputRechazar").show();
		$("#divInputTelelink").show();
		$("#divInputRenovo").show();

		$("#divInputGuardar").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleQuintupleSize");
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
	} else if (mode == __FORM_MODE_BACKOFFICE) {
		$("#divEmpresa").html("&nbsp;");
		showField("empresa");
		showField("incluirChip");
		
		$("#divMid").html("&nbsp;");
		
//		$("#divFechaAtivarEn").html("&nbsp;");
//		hideField("fechaActivarEn");
		
		$("#divLabelModalidadVenta").addClass("requiredFormLabel");
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelTipoProducto").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
		$("#divLabelFormaPago").addClass("requiredFormLabel");
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
		$("#divLabelApellido").addClass("requiredFormLabel");
		$("#divLabelFechaNacimiento").addClass("requiredFormLabel");
		$("#divLabelSexo").addClass("requiredFormLabel");
		$("#divLabelDireccionFactura").addClass("requiredFormLabel");
		$("#divLabelDireccionEntrega").addClass("requiredFormLabel");
		$("#divLabelTelefonoContacto").addClass("requiredFormLabel");
		$("#divLabelEmail").addClass("requiredFormLabel");
		$("#divLabelDepartamento").addClass("requiredFormLabel");
		$("#divLabelBarrio").addClass("requiredFormLabel");
		$("#divLabelZona").addClass("requiredFormLabel");
		$("#divLabelTurno").addClass("requiredFormLabel");
		$("#divLabelFechaEntrega").addClass("requiredFormLabel");
		$("#divLabelCostoEnvio").addClass("requiredFormLabel");
		
		$("#divLabelNumeroFactura").addClass("requiredFormLabel");
		$("#divLabelNumeroSerie").addClass("requiredFormLabel");
		$("#divLabelNumeroChip").addClass("requiredFormLabel");
		
		$("#divInputDistribuir").show();
		$("#divInputRechazar").show();
		$("#divInputTelelink").show();
		$("#divInputRenovo").show();
		$("#divInputReagendar").show();
		
		$("#divInputImprimirKit").show();
		$("#divInputImprimirContrato").show();
		$("#divInputGuardar").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleQuintupleSize");
	} else if (mode == __FORM_MODE_RECOORDINACION) {
		$("#divEmpresa").html("&nbsp;");
		showField("empresa");
		showField("incluirChip");
		
		$("#divMid").html("&nbsp;");
		
		$("#divFechaAtivarEn").html("&nbsp;");
		hideField("fechaActivarEn");
		
		$("#divLabelModalidadVenta").addClass("requiredFormLabel");
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelTipoProducto").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
		$("#divLabelFormaPago").addClass("requiredFormLabel");
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
		$("#divLabelApellido").addClass("requiredFormLabel");
		$("#divLabelFechaNacimiento").addClass("requiredFormLabel");
		$("#divLabelSexo").addClass("requiredFormLabel");
		$("#divLabelDireccionFactura").addClass("requiredFormLabel");
		$("#divLabelDireccionEntrega").addClass("requiredFormLabel");
		$("#divLabelTelefonoContacto").addClass("requiredFormLabel");
		$("#divLabelEmail").addClass("requiredFormLabel");
		$("#divLabelDepartamento").addClass("requiredFormLabel");
		$("#divLabelBarrio").addClass("requiredFormLabel");
		$("#divLabelZona").addClass("requiredFormLabel");
		$("#divLabelTurno").addClass("requiredFormLabel");
		$("#divLabelFechaEntrega").addClass("requiredFormLabel");
		$("#divLabelCostoEnvio").addClass("requiredFormLabel");
		
		$("#divLabelNumeroFactura").addClass("requiredFormLabel");
		$("#divLabelNumeroSerie").addClass("requiredFormLabel");
		$("#divLabelNumeroChip").addClass("requiredFormLabel");
		
		$("#divInputRedistribuir").show();
		$("#divInputTelelink").show();
		$("#divInputRenovo").show();
		$("#divInputReActivar").show();
		$("#divInputNoRecoordina").show();
		
		$("#divInputGuardar").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleQuintupleSize");
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
	} else if (mode == __FORM_MODE_DISTRIBUCION) {
		showField("resultadoEntregaDistribucionObservaciones");
		showField("incluirChip");
		
		$("#divEmpresa").html("&nbsp;");
		showField("empresa");
		
		$("#divMid").html("&nbsp;");
		
		$("#divLocalidad").html("&nbsp;");
		hideField("localidad");
		
		$("#divCodigoPostal").html("&nbsp;");
		hideField("codigoPostal");
		
		$("#divFechaVencimiento").html("&nbsp;");
		hideField("fechaVencimiento");
		
		$("#divPlan").html("&nbsp;");
		hideField("plan");
		
		$("#divFechaAtivarEn").html("&nbsp;");
		hideField("fechaActivarEn");
		
		$("#divLabelModalidadVenta").addClass("requiredFormLabel");
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelTipoProducto").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
		$("#divLabelFormaPago").addClass("requiredFormLabel");
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
		$("#divLabelApellido").addClass("requiredFormLabel");
		$("#divLabelFechaNacimiento").addClass("requiredFormLabel");
		$("#divLabelDireccionFactura").addClass("requiredFormLabel");
		$("#divLabelDireccionEntrega").addClass("requiredFormLabel");
		$("#divLabelTelefonoContacto").addClass("requiredFormLabel");
		$("#divLabelEmail").addClass("requiredFormLabel");
		$("#divLabelDepartamento").addClass("requiredFormLabel");
		$("#divLabelBarrio").addClass("requiredFormLabel");
		$("#divLabelZona").addClass("requiredFormLabel");
		$("#divLabelTurno").addClass("requiredFormLabel");
		$("#divLabelFechaEntrega").addClass("requiredFormLabel");
		$("#divLabelCostoEnvio").addClass("requiredFormLabel");
		
		$("#divLabelNumeroFactura").addClass("requiredFormLabel");
		$("#divLabelNumeroSerie").addClass("requiredFormLabel");
		
		$("#divInputActivar").show();
		$("#divInputNoFirma").show();
		$("#divInputRecoordinar").show();
		$("#divInputGestionDistribucion").show();
		
		$("#divInputImprimirAdjuntos").show();
		$("#divInputGuardar").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleDoubleSize");
		
		$(".divPopupWindow > .divLayoutColumn").css("height", "425");
	} else if (mode == __FORM_MODE_REDISTRIBUCION) {
		$("#divLabelResultadoEntregaDistribucionObservaciones").show();
		$("#divResultadoEntregaDistribucionObservaciones").show();
		
		$("#divEmpresa").html("&nbsp;");
		showField("empresa");
		showField("incluirChip");
		
		$("#divMid").html("&nbsp;");
		
		$("#divLocalidad").html("&nbsp;");
		hideField("localidad");
		
		$("#divCodigoPostal").html("&nbsp;");
		hideField("codigoPostal");
		
		$("#divFechaVencimiento").html("&nbsp;");
		hideField("fechaVencimiento");
		
		$("#divPlan").html("&nbsp;");
		hideField("plan");
		
		$("#divFechaAtivarEn").html("&nbsp;");
		hideField("fechaActivarEn");
		
		$("#divLabelModalidadVenta").addClass("requiredFormLabel");
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelTipoProducto").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
		$("#divLabelFormaPago").addClass("requiredFormLabel");
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
		$("#divLabelApellido").addClass("requiredFormLabel");
		$("#divLabelFechaNacimiento").addClass("requiredFormLabel");
		$("#divLabelDireccionFactura").addClass("requiredFormLabel");
		$("#divLabelDireccionEntrega").addClass("requiredFormLabel");
		$("#divLabelTelefonoContacto").addClass("requiredFormLabel");
		$("#divLabelEmail").addClass("requiredFormLabel");
		$("#divLabelDepartamento").addClass("requiredFormLabel");
		$("#divLabelBarrio").addClass("requiredFormLabel");
		$("#divLabelZona").addClass("requiredFormLabel");
		$("#divLabelTurno").addClass("requiredFormLabel");
		$("#divLabelFechaEntrega").addClass("requiredFormLabel");
		$("#divLabelCostoEnvio").addClass("requiredFormLabel");
		
		$("#divLabelNumeroFactura").addClass("requiredFormLabel");
		$("#divLabelNumeroSerie").addClass("requiredFormLabel");
		
		$("#divInputReActivar").show();
		$("#divInputNoFirma").show();
		$("#divInputRecoordinar").show();
		$("#divInputGestionDistribucion").show();
		
		$("#divInputGuardar").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
	} else if (mode == __FORM_MODE_ACTIVACION) {
		$("#divEmpresa").html("&nbsp;");
		showField("empresa");
		
		$("#divMid").html("&nbsp;");
		
		$("#divLabelModalidadVenta").addClass("requiredFormLabel");
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelTipoProducto").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
		$("#divLabelFormaPago").addClass("requiredFormLabel");
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
		$("#divLabelApellido").addClass("requiredFormLabel");
		$("#divLabelFechaNacimiento").addClass("requiredFormLabel");
		$("#divLabelDireccionFactura").addClass("requiredFormLabel");
		$("#divLabelDireccionEntrega").addClass("requiredFormLabel");
		$("#divLabelTelefonoContacto").addClass("requiredFormLabel");
		$("#divLabelEmail").addClass("requiredFormLabel");
		$("#divLabelDepartamento").addClass("requiredFormLabel");
		$("#divLabelBarrio").addClass("requiredFormLabel");
		$("#divLabelZona").addClass("requiredFormLabel");
		$("#divLabelTurno").addClass("requiredFormLabel");
		$("#divLabelFechaEntrega").addClass("requiredFormLabel");
		$("#divLabelNumeroFactura").addClass("requiredFormLabel");
		$("#divLabelNumeroSerie").addClass("requiredFormLabel");
		$("#divLabelCostoEnvio").addClass("requiredFormLabel");
		
		$("#divInputEnviarAAntel").show();
		$("#divInputAgendarActivacion").show();
		$("#divInputTerminar").show();
		$("#divInputFaltaDocumentacion").show();
		$("#divInputFacturaImpaga").show();
		$("#divInputCanceladoPorCliente").show();
		
		$("#divInputImprimirContrato").show();
		$("#divInputGuardar").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleSextupleSize");
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleDoubleSize");
	} else if (mode == __FORM_MODE_SUPERVISOR_ACTIVACION) {
		$("#divEmpresa").html("&nbsp;");
		showField("empresa");
		
		$("#divMid").html("&nbsp;");
		
		$("#divLabelModalidadVenta").addClass("requiredFormLabel");
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelTipoProducto").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
		$("#divLabelFormaPago").addClass("requiredFormLabel");
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
		$("#divLabelApellido").addClass("requiredFormLabel");
		$("#divLabelFechaNacimiento").addClass("requiredFormLabel");
		$("#divLabelDireccionFactura").addClass("requiredFormLabel");
		$("#divLabelDireccionEntrega").addClass("requiredFormLabel");
		$("#divLabelTelefonoContacto").addClass("requiredFormLabel");
		$("#divLabelEmail").addClass("requiredFormLabel");
		$("#divLabelDepartamento").addClass("requiredFormLabel");
		$("#divLabelBarrio").addClass("requiredFormLabel");
		$("#divLabelZona").addClass("requiredFormLabel");
		$("#divLabelTurno").addClass("requiredFormLabel");
		$("#divLabelFechaEntrega").addClass("requiredFormLabel");
		$("#divLabelNumeroFactura").addClass("requiredFormLabel");
		$("#divLabelNumeroSerie").addClass("requiredFormLabel");
		$("#divLabelCostoEnvio").addClass("requiredFormLabel");
		
		$("#divInputEnviarAAntel").show();
		$("#divInputAgendarActivacion").show();
		$("#divInputTerminar").show();
		$("#divInputFaltaDocumentacion").show();
		$("#divInputCerrar").show();
		$("#divInputGestionInterna").show();
		$("#divInputEquipoPerdido").show();
		$("#divInputFacturaImpaga").show();
		$("#divInputCanceladoPorCliente").show();
		
		$("#divInputImprimirContrato").show();
		$("#divInputGuardar").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleNinetupleSize");
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleDoubleSize");
	} else if (mode == __FORM_MODE_ANTEL) {
		hideField("localidad");
		hideField("codigoPostal");
		hideField("fechaVencimiento");
		showField("antelNroTrn");
		showField("antelFormaPago");
		showField("antelNroServicioCuenta");
		showField("antelImporte");
		showField("fechaVentaMostrar");
		$("#divEmpresa").html("&nbsp;");
		$("#divMid").html("&nbsp;");
		hideField("mid");
		$("#divPlan").html("&nbsp;");
		hideField("plan");
		$("#divNumeroContrato").html("&nbsp;");
		hideField("numeroContrato");
		$("#divModalidadVenta").html("&nbsp;");
		hideField("modalidadVenta");
		$("#divNuevoPlan").html("&nbsp;");
		hideField("nuevoPlan");
		$("#divMotivoCambioPlan").html("&nbsp;");
		hideField("motivoCambioPlan");
		$("#divTipoProducto").html("&nbsp;");
		hideField("tipoProducto");
		$("#divEquipo").html("&nbsp;");
		hideField("equipo");
		$("#divNumeroSerie").html("&nbsp;");
		hideField("numeroSerie");
		$("#divNumeroChip").html("&nbsp;");
		hideField("numeroChip");
		$("#divNumeroBloqueo").html("&nbsp;");
		hideField("numeroBloqueo");
		$("#divTipoDocumento").html("&nbsp;");
		hideField("tipoDocumento");
		$("#divDocumento").html("&nbsp;");
		$("#divNombre").html("&nbsp;");
		$("#divApellido").html("&nbsp;");
		$("#divFechaNacimiento").html("&nbsp;");
		hideField("fechaNacimiento");
		$("#divSexo").html("&nbsp;");
		hideField("sexo");
		$("#divNumeroFactura").html("&nbsp;");
		$("#divNumeroFacturaRiverGreen").html("&nbsp;");
		hideField("numeroFacturaRiverGreen");
		$("#divAgregarAdjunto").html("&nbsp");
		$("#divTipoArchivoAdjunto").html("&nbsp");
		hideField("costoEnvio");
		
		$("#divDireccionFactura").html("&nbsp;");
		$("#divDireccionFacturaCalle").html("&nbsp;");
		$("#divDireccionFacturaNumero").html("&nbsp;");
		$("#divDireccionFacturaBis").html("&nbsp;");
		$("#divDireccionFacturaBlock").html("&nbsp;");
		$("#divDireccionFacturaApto").html("&nbsp;");
		$("#divDireccionFacturaSolar").html("&nbsp;");
		$("#divDireccionFacturaManzana").html("&nbsp;");
		$("#divDireccionFacturaCodigoPostal").html("&nbsp;");
		$("#divDireccionFacturaLocalidad").html("&nbsp;");
		$("#divDireccionFacturaObservaciones").html("&nbsp;");
		$("#divDireccionFacturaDepartamento").html("&nbsp;");
		
		$("#divDireccionEntrega").html("&nbsp;");
		$("#divDireccionEntregaCalle").html("&nbsp;");
		$("#divDireccionEntregaNumero").html("&nbsp;");
		$("#divDireccionEntregaBis").html("&nbsp;");
		$("#divDireccionEntregaBlock").html("&nbsp;");
		$("#divDireccionEntregaApto").html("&nbsp;");
		$("#divDireccionEntregaSolar").html("&nbsp;");
		$("#divDireccionEntregaManzana").html("&nbsp;");
		$("#divDireccionEntregaCodigoPostal").html("&nbsp;");
		$("#divDireccionEntregaLocalidad").html("&nbsp;");
		$("#divDireccionEntregaObservaciones").html("&nbsp;");
		$("#divDireccionEntregaDepartamento").html("&nbsp;");
		
		$("#divTelefonoContacto").html("&nbsp;");
		$("#divEmail").html("&nbsp;");
		hideField("email");
		$("#divPrecio").html("&nbsp;");
		hideField("precio");
		$("#divValorCuota").html("&nbsp;");
		$("#divMoneda").html("&nbsp;");
		$("#divFormaPago").html("&nbsp;");
		hideField("formaPago");
		$("#divTarjetaCredito").html("&nbsp;");
		hideField("tarjetaCredito");
		$("#divCuotas").html("&nbsp;");
		$("#divDepartamento").html("&nbsp;");
		hideField("departamento");
		$("#divBarrio").html("&nbsp;");
		hideField("barrio");
		$("#divZona").html("&nbsp;");
		hideField("zona");
		$("#divTurno").html("&nbsp;");
		hideField("turno");
		$("#divFechaEntrega").html("&nbsp;");
		hideField("fechaEntrega");
		$("#divFechaActivarEn").html("&nbsp;");
		hideField("fechaActivarEn");
		$("#divObservaciones").html("&nbsp;");
	} else if (mode == __FORM_MODE_RIESGO_CREDITICIO) {
		showField("fechaEnvioANucleo");
		hideField("localidad");
		hideField("codigoPostal");
		hideField("fechaVencimiento");
		hideField("antelNroTrn");
		hideField("antelNroServicioCuenta");
		hideField("antelImporte");
		hideField("fechaVentaMostrar");
		$("#divEmpresa").html("&nbsp;");
		$("#divMid").html("&nbsp;");
		$("#divPlan").html("&nbsp;");
		hideField("plan");
		$("#divNumeroContrato").html("&nbsp;");
		$("#divModalidadVenta").html("&nbsp;");
		hideField("modalidadVenta");
		$("#divNuevoPlan").html("&nbsp;");
		$("#divMotivoCambioPlan").html("&nbsp;");
		hideField("motivoCambioPlan");
		$("#divTipoProducto").html("&nbsp;");
		$("#divEquipo").html("&nbsp;");
		$("#divNumeroSerie").html("&nbsp;");
		hideField("numeroSerie");
		$("#divNumeroChip").html("&nbsp;");
		hideField("numeroChip");
		$("#divNumeroBloqueo").html("&nbsp;");
		hideField("numeroBloqueo");
		$("#divTipoDocumento").html("&nbsp;");
		$("#divDocumento").html("&nbsp;");
		$("#divNombre").html("&nbsp;");
		$("#divApellido").html("&nbsp;");
		$("#divFechaNacimiento").html("&nbsp;");
		hideField("fechaNacimiento");
		$("#divSexo").html("&nbsp;");
		hideField("sexo");
		$("#divNumeroFactura").html("&nbsp;");
		hideField("numeroFactura");
		$("#divNumeroFacturaRiverGreen").html("&nbsp;");
		hideField("numeroFacturaRiverGreen");
		$("#divAgregarAdjunto").html("&nbsp");
		$("#divTipoArchivoAdjunto").html("&nbsp");
		hideField("costoEnvio");
		
		$("#divDireccionFactura").html("&nbsp;");
		$("#divDireccionFacturaCalle").html("&nbsp;");
		$("#divDireccionFacturaNumero").html("&nbsp;");
		$("#divDireccionFacturaBis").html("&nbsp;");
		$("#divDireccionFacturaBlock").html("&nbsp;");
		$("#divDireccionFacturaApto").html("&nbsp;");
		$("#divDireccionFacturaSolar").html("&nbsp;");
		$("#divDireccionFacturaManzana").html("&nbsp;");
		$("#divDireccionFacturaCodigoPostal").html("&nbsp;");
		$("#divDireccionFacturaLocalidad").html("&nbsp;");
		$("#divDireccionFacturaObservaciones").html("&nbsp;");
		$("#divDireccionFacturaDepartamento").html("&nbsp;");
		
		$("#divDireccionEntrega").html("&nbsp;");
		$("#divDireccionEntregaCalle").html("&nbsp;");
		$("#divDireccionEntregaNumero").html("&nbsp;");
		$("#divDireccionEntregaBis").html("&nbsp;");
		$("#divDireccionEntregaBlock").html("&nbsp;");
		$("#divDireccionEntregaApto").html("&nbsp;");
		$("#divDireccionEntregaSolar").html("&nbsp;");
		$("#divDireccionEntregaManzana").html("&nbsp;");
		$("#divDireccionEntregaCodigoPostal").html("&nbsp;");
		$("#divDireccionEntregaLocalidad").html("&nbsp;");
		$("#divDireccionEntregaObservaciones").html("&nbsp;");
		$("#divDireccionEntregaDepartamento").html("&nbsp;");
		
		$("#divTelefonoContacto").html("&nbsp;");
		$("#divEmail").html("&nbsp;");
		hideField("email");
		$("#divPrecio").html("&nbsp;");
		$("#divValorCuota").html("&nbsp;");
		$("#divMoneda").html("&nbsp;");
		$("#divFormaPago").html("&nbsp;");
		$("#divTarjetaCredito").html("&nbsp;");
		hideField("tarjetaCredito");
		$("#divCuotas").html("&nbsp;");
		$("#divDepartamento").html("&nbsp;");
		hideField("departamento");
		$("#divBarrio").html("&nbsp;");
		hideField("barrio");
		$("#divZona").html("&nbsp;");
		hideField("zona");
		$("#divTurno").html("&nbsp;");
		hideField("turno");
		$("#divFechaEntrega").html("&nbsp;");
		hideField("fechaEntrega");
		$("#divFechaActivarEn").html("&nbsp;");
		hideField("fechaActivarEn");
		$("#divObservaciones").html("&nbsp;");
		
		$("#divInputEnviadoANucleo").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleSingleSize");
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
	}
}

function reloadFormasPago(empresaId, documento) {
	var empresa = {
		id: empresaId
	};
	
	var options = [];
	
	if (documento != null && documento != "" && empresaId != null && empresaId != "") {
		EmpresaDWR.listFormasPagoById(
			empresaId,
			{
				callback: function(formasPago) {
					if (formasPago != null) {
						for (var i=0; i<formasPago.length; i++) {
							if (formasPago[i].id == __FORMA_PAGO_NUESTRO_CREDITO_ID) {
								FinanciacionDWR.analizarElegibilidadFinanaciacion(
									empresa,
									documento,
									{
										callback: function(data) {
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
										}, async: false
									}
								);
							} else {
								options[options.length] = formasPago[i];
							}
						}
					}
				}, async: false
			}
		);
	}
	
	fillSelect("selectFormaPago", options, "id", "descripcion", "defid", "datosElegibilidadFinanciacion.elegibilidad");
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
		BarrioDWR.listByDepartamentoId(
			$("#selectDepartamento").val(),
			{
				callback: function(data) {
					fillSelect("selectBarrio", data, "id", "nombre", "zid", "zona.id");
					
					selectBarrioOnChange();
				}, async: false
			}
		);
	}
}

function selectBarrioOnChange() {
	$("#selectZona > option:gt(0)").remove();
	$("#selectTurno > option:gt(0)").remove();
	$("#selectFechaEntrega > option:gt(0)").remove();
	
	if ($("#selectBarrio").val() != "0") {
		ZonaDWR.getById(
			$("#selectBarrio > option:selected").attr("zid"),
			{
				callback: function(data) {
					fillSelect("selectZona", [data], "id", "nombre");
					
					$("#selectZona").val(data.id);
					
					selectZonaOnChange();
				}, async: false
			}
		);
	}
}

function selectZonaOnChange() {
	$("#selectTurno > option:gt(0)").remove();
	$("#selectFechaEntrega > option:gt(0)").remove();
	
	if ($("#selectZona").val() != "0") {
		TurnoDWR.list(
			{
				callback: function(data) {
					fillSelect("selectTurno", data, "id", "nombre");
					
					selectTurnoOnChange();
				}, async: false
			}
		);
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
		DisponibilidadEntregaEmpresaZonaTurnoDWR.listByEmpresaZonaTurno(
			{
				id: $("#selectEmpresa").length > 0 ? $("#selectEmpresa").val() : $("#divEmpresa").attr("eid")
			},
			{
				id: $("#selectZona").val(),
				departamento: {
					id: $("#selectDepartamento").val()
				}
			},
			{
				id: $("#selectTurno").val()
			},
			{
				callback: function(data) {
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
				}, async: false
			}
		);
	}
}

function selectFechaEntregaOnChange() {
	return true;
}

function reloadPrecio() {
	$("#inputPrecio").val(null);
	
	if (($("#selectTipoProducto").val() != 0) && 
		($("#selectEquipo").val() != 0) && 
		($("#selectMoneda").val() != 0)) {
		PrecioDWR.getActualByEmpresaTipoProductoMarcaModeloMonedaCuotas(
			{
				id: $("#selectEmpresa").length > 0 ? $("#selectEmpresa").val() : $("#divEmpresa").attr("eid")
			},
			{
				id: $("#selectTipoProducto").length > 0 ? $("#selectTipoProducto").val() : $("#divTipoProducto").attr("tpid")
			},
			{
				id: $("#selectEquipo").length > 0 ? $("#selectEquipo option:selected").attr("maid") : $("#divEquipo").attr("maid")
			},
			{
				id: $("#selectEquipo").length > 0 ? $("#selectEquipo").val() : $("#divEquipo").attr("moid")
			},
			{
				id: $("#selectMoneda").length > 0 ? $("#selectMoneda").val() : $("#divMoneda").attr("mid")
			},
			(
				($("#selectCuotas").length > 0 && $("#selectCuotas").val() > 0) ? $("#selectCuotas").val() : 1
			),
			{
				callback: function(data) {
					if (data != null) {
						$("#inputPrecio").val(data.precio);
					} else {
						$("#inputPrecio").val(null);
					}
				}, async: false
			}
		);
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
	
	if (($("#selectEquipo").val() != 0) 
		&& ($("#selectMoneda").val() != 0) 
		&& ($("#inputPrecio").val() != "")
		&& ($("#selectCuotas").val() != 0)) {
		FinanciacionDWR.calcularFinanciacion(
			{
				id: $("#selectMoneda").length > 0 ? $("#selectMoneda").val() : $("#divMoneda").attr("mid")
			},
			{
				id: $("#divTipoTasaInteresEfectivaAnual").attr("tid")
			},
			$("#inputPrecio").val(),
			$("#selectCuotas").val(),
			{
				callback: function(data) {
					if (data != null) {
						$("#inputValorCuota").val(formatDecimal(data.montoCuota, 2));
						$("#divIntereses").html(data.intereses);
						$("#divGastosAdministrativos").html(data.gastosAdministrativos);
						$("#divGastosConcesion").html(data.gastosConcesion);
						$("#divGastosAdministrativosTotales").html(data.gastosAdministrativosTotales);
						$("#divUnidadIndexada").html(data.valorUnidadIndexada);
						$("#divTasaInteresEfectivaAnual").html(data.valorTasaInteresEfectivaAnual);
					}
				}, async: false
			}
		);
	}
}

function selectTipoProductoOnChange() {
	reloadPrecio();
	reloadDatosFinanciacion();
	reloadStock();
}

function reloadStock() {
	StockMovimientoDWR.listStockByEmpresaTipoProducto(
		{
			id: $("#selectEmpresa").length > 0 ? $("#selectEmpresa").val() : $("#divEmpresa").attr("eid")
		},
		{
			id: $("#selectTipoProducto").length > 0 ? $("#selectTipoProducto").val() : $("#divTipoProducto").attr("tpid")
		},
		{
			callback: function(data) {
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
			}, async: false
		}
	);
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
		ProductoDWR.getByIMEI(
			val,
			{
				callback: function(data) {
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
				}, async: false
			}
		);
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
		fechaVenta: $("#divFechaVenta").text().trim() != "" ? new Date(parseInt($("#divFechaVenta").text().trim())) : null,
		fechaBackoffice: $("#divFechaBackoffice").text().trim() != "" ? new Date(parseInt($("#divFechaBackoffice").text().trim())) : null,
		fechaEntregaDistribuidor: $("#divFechaEntregaDistribuidor").text().trim() != "" ? new Date(parseInt($("#divFechaEntregaDistribuidor").text().trim())) : null,
		fechaDevolucionDistribuidor: $("#divFechaDevolucionDistribuidor").text().trim() != "" ? new Date(parseInt($("#divFechaDevolucionDistribuidor").text().trim())) : null,
		fechaEnvioAntel: $("#divFechaEnvioAntel").text().trim() != "" ? new Date(parseInt($("#divFechaEnvioAntel").text().trim())) : null,
		fechaActivacion: $("#divFechaActivacion").text().trim() != "" ? new Date(parseInt($("#divFechaActivacion").text().trim())) : null,
		fechaCoordinacion: $("#divFechaCoordinacion").text().trim() != "" ? new Date(parseInt($("#divFechaCoordinacion").text().trim())) : null,
		// -------------------
		
		tipoContratoDescripcion: $("#divPlan").text().trim() != "" ? $("#divPlan").text().trim() : null,
		nuevoPlanString: $("#divNuevoPlanString").text().trim() != "" ? $("#divNuevoPlanString").text().trim() : null,
		fechaFinContrato: $("#divFechaVencimiento").text().trim() != "" ? parseShortDate($("#divFechaVencimiento").text().trim()) : null,
		tipoContratoDescripcion: $("#inputPlan").length > 0 ? 
			($("#inputPlan").val().trim() != "" ? $("#inputPlan").val().trim() : null) : 
				$("#divPlan").text().trim(),
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
				$("#divCostoEnvio").text().trim(),
		numeroChip: $("#inputNumeroChip").length > 0 ? 
			($("#inputNumeroChip").val().trim() != "" ? $("#inputNumeroChip").val().trim() : null) :
				$("#divNumeroChip").text().trim(),
		numeroBloqueo: $("#inputNumeroBloqueo").length > 0 ? 
			($("#inputNumeroBloqueo").val().trim() != "" ? $("#inputNumeroBloqueo").val().trim() : null) :
				$("#divNumeroBloqueo").text().trim(),
		localidad: $("#divLocalidad").text().trim() != "" ? $("#divLocalidad").text().trim() : null,
		precio: $("#inputPrecio").length > 0 ? 
			($("#inputPrecio").val().trim() != "" ? $("#inputPrecio").val().trim() : null) :
				$("#divPrecio").text().trim(),
		valorCuota: $("#inputValorCuota").length > 0 ? 
			($("#inputValorCuota").val().trim() != "" ? $("#inputValorCuota").val().trim() : null) :
				$("#divValorCuota").text().trim(),
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
			($("#inputDireccionEntregaNumero").val().trim() != "" ? $("#inputDireccionEntregaNumero").val().trim() : null) :
				$("#divDireccionEntregaNumero").text().trim(),
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
			($("#inputDireccionEntregaManzana").val().trim() != "" ? $("#inputDireccionEntregaManzana").val().trim() : null) :
				$("#divDireccionEntregaManzana").text().trim(),
		direccionEntregaCodigoPostal: $("#inputDireccionEntregaCodigoPostal").length > 0 ?
			($("#inputDireccionEntregaCodigoPostal").val().trim() != "" ? $("#inputDireccionEntregaCodigoPostal").val().trim() : null) :
				$("#divDireccionEntregaCodigoPostal").text().trim(),
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
			($("#inputDireccionFacturaNumero").val().trim() != "" ? $("#inputDireccionFacturaNumero").val().trim() : null) :
				$("#divDireccionFacturaNumero").text().trim(),
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
			($("#inputDireccionFacturaManzana").val().trim() != "" ? $("#inputDireccionFacturaManzana").val().trim() : null) :
				$("#divDireccionFacturaManzana").text().trim(),
		direccionFacturaCodigoPostal: $("#inputDireccionFacturaCodigoPostal").length > 0 ?
			($("#inputDireccionFacturaCodigoPostal").val().trim() != "" ? $("#inputDireccionFacturaCodigoPostal").val().trim() : null) :
				$("#divDireccionFacturaCodigoPostal").text().trim(),
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
		antelNroTrn: $("#divAntelNroTrn").text().trim() != "" ? $("#divAntelNroTrn").text().trim() : null,
		antelFormaPago: $("#divAntelFormaPago").text().trim() != "" ? $("#divAntelFormaPago").text().trim() : null,
		antelNroServicioCuenta: $("#divAntelNroServicioCuenta").text().trim() != "" ? $("#divAntelNroServicioCuenta").text().trim() : null,
		antelImporte: $("#divAntelImporte").text().trim() != "" ? $("#divAntelImporte").text().trim() : null,

		fechaEnvioANucleo: $("#divFechaEnvioANucleo").text().trim() != "" ? parseLongDate($("#divFechaEnvioANucleo").text().trim()) : null,
	};
	
	if ($("#selectDireccionEntregaDepartamento").val() != "0") {
		contrato.direccionEntregaDepartamento = {
			id: $("#selectDireccionEntregaDepartamento").val()
		};
	}
	
	if ($("#selectDireccionFacturaDepartamento").val() != "0") {
		contrato.direccionFacturaDepartamento = {
			id: $("#selectDireccionFacturaDepartamento").val()
		};
	}
	
	if ($("#selectTipoDocumento").val() != "0") {
		contrato.tipoDocumento = {
			id: $("#selectTipoDocumento").val()
		};
	}
	
	if ($("#selectSexo").val() != "0") {
		contrato.sexo = {
			id: $("#selectSexo").val()
		};
	}
	
	if ($("#selectTipoProducto").val() != "0") {
		contrato.tipoProducto = {
			id: $("#selectTipoProducto").val()
		};
	}
	
	if ($("#selectEquipo").val() != "0") {
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
	
	if ($("#selectNuevoPlan").val() != "0") {
		contrato.nuevoPlan = {
			id: $("#selectNuevoPlan").val()
		};
	}
	
	if ($("#selectMotivoCambioPlan").val() != "0") {
		contrato.motivoCambioPlan = {
			id: $("#selectMotivoCambioPlan").val()
		};
	}
	
	if ($("#selectMoneda").val() != "0") {
		contrato.moneda = {
			id: $("#selectMoneda").val()
		};
	}
	
	if ($("#selectFormaPago").val() != "0") {
		contrato.formaPago = {
			id: $("#selectFormaPago").val()
		};
	}
	
	if ($("#selectModalidadVenta").val() != "0") {
		contrato.modalidadVenta = {
			id: $("#selectModalidadVenta").val()
		};
	}
	
	if ($("#selectTarjetaCredito").val() != "0") {
		contrato.tarjetaCredito = {
			id: $("#selectTarjetaCredito").val()
		};
	}
	
	if ($("#selectCuotas").val() != "0") {
		contrato.cuotas = $("#selectCuotas").val();
	}
	
	if ($("#divResultadoEntregaDistribucion").attr("redid") != null && $("#divResultadoEntregaDistribucion").attr("redid") != "") {
		contrato.resultadoEntregaDistribucion = {
			id: $("#divResultadoEntregaDistribucion").attr("redid")
		};
	}
	
	if ($("#selectDepartamento").val() != "0" && $("#selectBarrio").val() != "0" && $("#selectZona").val() != "0") {
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
	
	if ($("#selectTurno").val() != "0") {
		contrato.turno = {
			id: $("#selectTurno").val()
		};
	}
	
	if ($("#selectFechaEntrega").val() != "0") {
		contrato.fechaEntrega = parseShortDate($("#selectFechaEntrega").val());
	}
	
	if ($("#divEmpresa").attr("eid") != null && $("#divEmpresa").attr("eid") != "") {
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
	
	var archivosAdjuntos = [];
	var imgs = $(".divGalleryContent > img");
	for (var i=0; i<imgs.length; i++) {
		archivosAdjuntos[archivosAdjuntos.length] = {
			id: $(imgs[i]).attr("id")
		};
	}
	contrato.archivosAdjuntos = archivosAdjuntos;
	
	return contrato;
}

function inputImprimirKitOnClick() {
	var contrato = collectContratoData();
	
	window.open("/LogisticaWEB/pages/contrato/contrato_print.jsp?m=" + __FORM_MODE_PRINT + "&cid=" + contrato.id);
}

function inputImprimirContratoOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	window.open("/LogisticaWEB/pages/contrato/contrato_preimpreso_print.jsp?cid=" + contrato.id);
	
	if (contrato.formaPago != null && contrato.formaPago.id == __FORMA_PAGO_NUESTRO_CREDITO_ID) {
		window.open("/LogisticaWEB/pages/contrato/contrato_preimpreso_pagare_print.jsp?cid=" + contrato.id);
	}
}

function inputImprimirPagareOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	window.open("/LogisticaWEB/pages/contrato/contrato_preimpreso_pagare_print.jsp?cid=" + contrato.id);
}

function inputImprimirRemitoOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	window.open("/LogisticaWEB/pages/contrato/contrato_preimpreso_remito_print.jsp?cid=" + contrato.id);
}

function inputImprimirAdjuntosOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	window.open("/LogisticaWEB/pages/contrato/contrato_adjunto_print.jsp?cid=" + contrato.id);
}

function inputImprimirContratoOnClick() {
	if (!checkRequiredFields()) {
		alert("Informaci�n incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	window.open("/LogisticaWEB/pages/contrato/contrato_preimpreso_print.jsp?cid=" + contrato.id);
}

function inputEstadoRiesgoCrediticioOnClick() {
	var empresaId = $("#divEmpresa").attr("eid");
	var documento = $("#inputDocumento").val();
	
	if (documento != null && documento != "") {
		documento = documento.trim();
		
		if (empresaId != null && empresaId != "") {
			FinanciacionDWR.analizarElegibilidadFinanaciacion(
				{
					id: empresaId,
				},
				documento,
				{
					callback: function(data) {
						if (data != null) {
							if (data.elegibilidad == __ELEGIBILIDAD_SIN_ANALISIS) {
								if (confirm("No hay información de análisis de riesgo crediticio.\n"
										+ "¿Desea registrarlo manualmente?")) {
									if (confirm("¿El control realizado permite habilitar la financiación con Nuestro crédito?\n"
										+ "(Aceptar = Sí / Cancelar = No)")) {
										RiesgoCrediticioDWR.registrarAnalisisAprobadoManual(
											empresaId,
											documento,
											{
												callback: function(data) {
													alert("Operación exitosa.");
													
													reloadFormasPago(empresaId, documento);
												}, async: false
											}
										);
									} else {
										RiesgoCrediticioDWR.registrarAnalisisRechazadoManual(
											empresaId,
											documento,
											{
												callback: function(data) {
													alert("Operación exitosa.");
													
													reloadFormasPago(empresaId, documento);
												}, async: false
											}
										);
									}
								} else {
									// No hacer nada.
								}
							} else if (data.elegibilidad == __ELEGIBILIDAD_FINANCIACION_NO_REALIZAR_CLEARING) {
								alert("Se ha aprobado la financiación con Nuestro Crédito.");
							} else if (data.elegibilidad == __ELEGIBILIDAD_FINANCIACION_REALIZAR_CLEARING) {
								alert("Se debe realizar análisis de Clearing para la financiación con Nuestro Crédito.");
							} else if (data.elegibilidad == __ELEGIBILIDAD_FINANCIACION_RECHAZAR) {
								alert("Se ha rechazado la financiación con Nuestro Crédito.");
							}
						}
					}, async: false
				}
			);
		} else {
			alert("Los datos de la empresa no se han cargado correctamente.");
		}
	} else {
		alert("No se ha ingresado el documento.");
	}
}

function inputGuardarOnClick() {
	var contrato = collectContratoData();
	
	if (contrato.id != null) {
		ContratoDWR.update(
			contrato,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		ContratoDWR.addAsignacionManual(
			$("#selectEmpresa").val(),
			contrato,
			{
				callback: function(data) {
					alert(data);
				}, async: false
			}
		);
	}
}

function inputAgendarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	FinanciacionDWR.validarFinanciacion(
		contrato.moneda,
		contrato.formaPago,
		contrato.precio,
		{
			callback: function(dataFinanciacion) {
				if (dataFinanciacion != null) {
					alert(dataFinanciacion);
				} else {
					ContratoDWR.validarVenta(
						contrato,
						{
							callback: function(dataValidacion) {
								if (dataValidacion != null && dataValidacion) {
									ContratoDWR.agendar(
										contrato,
										{
											callback: function(dataVenta) {
												alert("Operación exitosa");
												
												window.parent.closeDialog();
											}, async: false
										}
									);
								} else {
									alert("No se puede realizar la operación.");
									
									window.parent.closeDialog();
								}
							}, async: false
						}
					);
				}
			}, async: false
		}
	);
}

function inputRechazarOnClick() {
	ContratoDWR.rechazar(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputPosponerOnClick() {
	ContratoDWR.posponer(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputDistribuirOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	FinanciacionDWR.validarFinanciacion(
		contrato.moneda,
		contrato.formaPago,
		contrato.precio,
		{
			callback: function(data) {
				if (data != null) {
					alert(data);
				} else {
					ContratoRelacionDWR.listByContratoId(
						contrato.id,
						{
							callback: function(dataContratoRelacion) {
								if (dataContratoRelacion.length > 0) {
									if (confirm("Atención: existen trámites relaciados. ¿Desea continuar?")) {
										ContratoDWR.distribuir(
											contrato,
											{
												callback: function(dataDistribuir) {
													alert("Operación exitosa");
													
													window.parent.closeDialog();
												}, async: false
											}
										);
									}
								} else {
									ContratoDWR.distribuir(
										contrato,
										{
											callback: function(dataDistribuir) {
												alert("Operación exitosa");
												
												window.parent.closeDialog();
											}, async: false
										}
									);
								}
							}, async: false
						}
					);
				}
			}, async: false
		}
	);
}

function inputRedistribuirOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	ContratoDWR.redistribuir(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputTelelinkOnClick() {
	ContratoDWR.telelink(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputRenovoOnClick() {
	ContratoDWR.renovo(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputReagendarOnClick() {
	ContratoDWR.reagendar(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputActivarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	FinanciacionDWR.validarFinanciacion(
		contrato.moneda,
		contrato.formaPago,
		contrato.precio,
		{
			callback: function(data) {
				if (data != null) {
					alert(data);
				} else {
					ContratoDWR.activar(
						contrato,
						{
							callback: function(data) {
								alert("Operación exitosa");
								
								window.parent.closeDialog();
							}, async: false
						}
					);
				}
			}, async: false
		}
	);
}

function inputNoFirmaOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	ContratoDWR.noFirma(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputRecoordinarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	ContratoDWR.recoordinar(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputAgendarActivacionOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	if ($("#inputFechaActivarEn").val() == "") {
		$("#divLabelFechaActivarEn").css("color", "red");
		$("#divLabelFechaActivarEn").css("font-weight", "bold");
		alert("Debe ingresar una fecha de activación.");
		
		return;
	}
	
	var contrato = collectContratoData();
	
	FinanciacionDWR.validarFinanciacion(
		contrato.moneda,
		contrato.formaPago,
		contrato.precio,
		{
			callback: function(data) {
				if (data != null) {
					alert(data);
				} else {
					ContratoDWR.agendarActivacion(
						contrato,
						{
							callback: function(data) {
								alert("Operación exitosa");
								
								window.parent.closeDialog();
							}, async: false
						}
					);
				}
			}, async: false
		}
	);
}

function inputEnviarAAntelOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	FinanciacionDWR.validarFinanciacion(
		contrato.moneda,
		contrato.formaPago,
		contrato.precio,
		{
			callback: function(data) {
				if (data != null) {
					alert(data);
				} else {
					ContratoDWR.enviarAAntel(
						contrato,
						{
							callback: function(data) {
								alert("Operación exitosa");
								
								window.parent.closeDialog();
							}, async: false
						}
					);
				}
			}, async: false
		}
	);
}

function inputTerminarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	FinanciacionDWR.validarFinanciacion(
		contrato.moneda,
		contrato.formaPago,
		contrato.precio,
		{
			callback: function(data) {
				if (data != null) {
					alert(data);
				} else {
					ContratoDWR.terminar(
						contrato,
						{
							callback: function(data) {
								alert("Operación exitosa");
								
								window.parent.closeDialog();
							}, async: false
						}
					);
				}
			}, async: false
		}
	);
}

function inputFaltaDocumentacionOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	ContratoDWR.faltaDocumentacion(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputReActivarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	ContratoDWR.reActivar(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputNoRecoordinaOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	ContratoDWR.noRecoordina(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputCerrarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	ContratoDWR.cerrar(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputGestionInternaOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	ContratoDWR.gestionInterna(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputGestionDistribucionOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	ContratoDWR.gestionDistribucion(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputEquipoPerdidoOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	ContratoDWR.equipoPerdido(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputFacturaImpagaOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	ContratoDWR.facturaImpaga(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputEnviadoANucleoOnClick() {
	var contrato = collectContratoData();
	
	ContratoDWR.enviadoANucleo(
		contrato,
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputCanceladoPorClienteOnClick() {
	var contrato = collectContratoData();
	
	ContratoDWR.canceladoPorCliente(
		contrato,
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputEquiposPagosOnClick() {
	var contrato = collectContratoData();
	
	ContratoDWR.equiposPagos(
		contrato,
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputEquipoDevueltoOnClick() {
	var contrato = collectContratoData();
	
	ContratoDWR.equipoDevuelto(
		contrato,
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputNoRecuperadoOnClick() {
	var contrato = collectContratoData();
	
	ContratoDWR.noRecuperado(
		contrato,
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}