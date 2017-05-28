var __SEMANAS_DISPONIBILIDAD_FECHA_ENTREGA = 4;
var __FORMA_PAGO_TARJETA_ID = 3;
var __FORMA_PAGO_NUESTRO_CREDITO_ID = 4;
var __FORMA_PAGO_NUESTRO_CREDITO_CUOTAS_DEFAULT = 12;
var __ELEGIBILIDAD_FINANCIACION_RECHAZAR = 0;
var __ELEGIBILIDAD_FINANCIACION_REALIZAR_CLEARING = 1;
var __ELEGIBILIDAD_FINANCIACION_NO_REALIZAR_CLEARING = 2;

<<<<<<< HEAD
var gridArchivosAdjuntos = null;

$(document).ready(init);

function init() {
	$("#divTab2").hide();
	$("#divTab3").hide();
=======
$(document).ready(function() {
	$("#divTab2").hide();
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
	
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
	
<<<<<<< HEAD
	initTabArchivosAdjuntos();
=======
	buildGallery();
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
	
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
<<<<<<< HEAD
		PlanDWR.listVigentes(
=======
		PlanDWR.list(
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
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
	
<<<<<<< HEAD
	if ($("#selectMoneda").length > 0) {
		MonedaDWR.list(
			{
				callback: function(data) {
					fillSelect("selectMoneda", data, "id", "simbolo");
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
	
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
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
	
	if ($("#selectBarrio").length > 0) {
		BarrioDWR.list(
			{
				callback: function(data) {
					fillSelect("selectBarrio", data, "id", "nombre");
				}, async: false
			}
		);
	}
	
	if ($("#selectZona").length > 0) {
		ZonaDWR.list(
			{
				callback: function(data) {
					fillSelect("selectZona", data, "id", "nombre");
				}, async: false
			}
		);
	}
	
	if ($("#selectTurno").length > 0) {
		TurnoDWR.list(
			{
				callback: function(data) {
					fillSelect("selectTurno", data, "id", "nombre");
				}, async: false
			}
		);
	}
	
	$("#selectFechaEntrega").append("<option value='0'>Seleccione...</option>");
	$("#selectEquipo").append("<option value='0'>Seleccione...</option>");
	
	if (id != null) {
		ContratoDWR.getById(
			id,
			{
				callback: function(data) {
					// Fechas del workflow
					// -------------------
					
					populateField("fechaVenta", data, "fechaVenta", "fechaVenta", null, null, formatRawDate);
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
					
					populateField("empresa", data, "empresa.id", "empresa.nombre", "eid", "empresa.id");
					populateField("numeroTramite", data, "numeroTramite", "numeroTramite");
					populateField("mid", data, "mid", "mid");
					populateField("estado", data, "estado.id", "estado.nombre", "eid", "estado.id");
					populateField("localidad", data, "localidad", "localidad");
					populateField("codigoPostal", data, "codigoPostal", "codigoPostal");
					populateField("fechaVencimiento", data, "fechaFinContrato", "fechaFinContrato", null, null, formatShortDate);
					populateField("numeroContrato", data, "numeroContrato", "numeroContrato");
					populateField("plan", data, "tipoContratoDescripcion", "tipoContratoDescripcion");
					populateField("nuevoPlanString", data, "nuevoPlanString", "nuevoPlanString");
					populateField("nuevoPlan", data, "nuevoPlan.id", "nuevoPlan.descripcion");
					populateField("motivoCambioPlan", data, "motivoCambioPlan.id", "motivoCambioPlan.descripcion");
					
					if (data.empresa != null) {
						if ($("#selectEquipo").length > 0) {
							StockMovimientoDWR.listStockByEmpresaId(
								data.empresa.id,
								{
									callback: function(dataStock) {
										var equipos = [];
										
										var found = false;
										for (var i=0; i<dataStock.length; i++) {
<<<<<<< HEAD
											if (dataStock[i].cantidad > 0 && dataStock[i].fechaBaja == null) {
												equipos[equipos.length] = {
													id: dataStock[i].modelo.id,
													descripcion: dataStock[i].marca.nombre + " " + dataStock[i].modelo.descripcion + " (" + dataStock[i].cantidad + ")",
													marca: dataStock[i].marca,
													modelo: dataStock[i].modelo
												};
												
												found = 
													found || (data.marca != null && data.modelo != null && dataStock[i].marca.id == data.marca.id && dataStock[i].modelo.id == data.modelo.id);
=======
											if (dataStock[i].cantidad > 0) {
												equipos[equipos.length] = {
													id: dataStock[i].producto.id,
													descripcion: dataStock[i].producto.descripcion + " (" + dataStock[i].cantidad + ")" 
												};
												
												found = 
													found || (data.producto != null && data.producto != "" && dataStock[i].producto.id == data.producto.id);
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
											}
										}
										
<<<<<<< HEAD
										if (!found && data.marca != null && data.modelo != null) {
											equipos[equipos.length] = {
												id: data.modelo.id,
												descripcion: data.marca.nombre + " " + data.modelo.descripcion,
												marca: data.marca,
												modelo: data.modelo
											};
										}
										
										fillSelect("selectEquipo", equipos, "id", "descripcion", "maid", "marca.id", "moid", "modelo.id");
										
										populateField("equipo", data, "modelo.id", "modelo.descripcion", "moid", "modelo.id");
=======
										if (!found && data.producto != null && data.producto != "") {
											equipos[equipos.length] = {
												id: data.producto.id,
												descripcion: data.producto.descripcion
											};
										}
										
										fillSelect("selectEquipo", equipos, "id", "descripcion");
										
										populateField("equipo", data, "producto.id", "producto.descripcion", "pid", "producto.id");
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
									}, async: false
								}
							);
						} else {
<<<<<<< HEAD
							populateField("equipo", data, "modelo.id", "modelo.descripcion", "moid", "modelo.id");
=======
							populateField("equipo", data, "producto.id", "producto.descripcion", "pid", "producto.id");
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
						}
						
						reloadFormasPago(data.empresa.id, data.documento);
					}
					
					populateField("precio", data, "precio", "precio");
<<<<<<< HEAD
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
							$("#divCuotas").css("width", "60%");
							$("#selectCuotas").css("width", "53.5%");
						}
						
						if (data.formaPago.id == __FORMA_PAGO_NUESTRO_CREDITO_ID) {
							showField("valorCuota");
							$("#divInputImprimirPagare").show();
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
					populateField("tarjetaCredito", data, "tarjetaCredito.id", "tarjetaCredito.nombre", "tid", "tarjetaCredito.id");
					populateField("cuotas", data, "cuotas", "cuotas");
					if (data.cuotas != null) {
						showField("cuotas");
					}
					populateField("valorCuota", data, "valorCuota", "valorCuota");
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
=======
					populateField("numeroFactura", data, "numeroFactura", "numeroFactura");
					populateField("numeroFacturaRiverGreen", data, "numeroFacturaRiverGreen", "numeroFacturaRiverGreen");
					populateField("numeroSerie", data, "numeroSerie", "numeroSerie");
					populateField("numeroChip", data, "numeroChip", "numeroChip");
					populateField("numeroBloqueo", data, "numeroBloqueo", "numeroBloqueo");
					
					populateField("tipoDocumento", data, "tipoDocumento.id", "tipoDocumento.descripcion");
					populateField("documento", data, "documento", "documento");
					populateField("nombre", data, "nombre", "nombre");
					populateField("apellido", data, "apellido", "apellido");
					populateField("fechaNacimiento", data, "fechaNacimiento", "fechaNacimiento", null, null, formatShortDate);
					populateField("sexo", data, "sexo.id", "sexo.descripcion");
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
					populateField("telefonoContacto", data, "telefonoContacto", "telefonoContacto");
					populateField("email", data, "email", "email");
					
					populateField("departamento", data, "zona.departamento.id", "zona.departamento.nombre");
					populateField("barrio", data, "barrio.id", "barrio.nombre");
					populateField("zona", data, "zona.id", "zona.nombre");
					populateField("turno", data, "turno.id", "turno.nombre");
					
					if ($("#selectTurno").length) {
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
					
<<<<<<< HEAD
					reloadArchivosAdjuntosData(data);
=======
					var galleryContent = $(".divGalleryContent > img");
					if (data.resultadoEntregaDistribucionURLAnverso != null && data.resultadoEntregaDistribucionURLAnverso != "") {
						$(galleryContent[0]).attr("src", "/LogisticaWEB/Stream?fn=" + data.resultadoEntregaDistribucionURLAnverso);
					}
					if (data.resultadoEntregaDistribucionURLReverso != null && data.resultadoEntregaDistribucionURLReverso!= "") {
						$(galleryContent[1]).attr("src", "/LogisticaWEB/Stream?fn=" + data.resultadoEntregaDistribucionURLReverso);
					}
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
				}, async: false
			}
		);
	}
}

function initTabArchivosAdjuntos() {
	gridArchivosAdjuntos = new Grid(
		document.getElementById("divTableArchivosAdjuntos"),
		{
			tdURL: { campo: "url", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 350 },
			tdFechaSubida: { campo: "fechaSubida", descripcion: "Fecha", abreviacion: "Fecha", tipo: __TIPO_CAMPO_FECHA_HORA } 
		}, 
		false,
		reloadArchivosAdjuntosData,
		trArchivosAdjuntosOnClick,
		null,
		6
	);
	
	gridArchivosAdjuntos.rebuild();
	
	buildGallery();
}

function reloadArchivosAdjuntosData(contrato) {
	var data = {};
			
	if (contrato != null) {
		data.cantidadRegistros = contrato.archivosAdjuntos.length;
		data.registrosMuestra = contrato.archivosAdjuntos;
	} else {
		ContratoArchivoAdjuntoDWR.listByContratoId(
			id,
			{
				callback: function(archivoAdjuntos) {
					data.cantidadRegistros = archivoAdjuntos.length;
					data.registrosMuestra = archivoAdjuntos;
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

function buildGallery() {
	var html = "";
	
	html +=
		"<div class='divGalleryContent'>&nbsp;</div>";
//		"<div class='divGalleryLeft'><</div>"
//		+ "<div class='divGalleryContent'>&nbsp;</div>"
//		+ "<div class='divGalleryRight'>></div>";
	
	$("#divResultadoEntregaDistribucionDocumentos").html(html);
	
//	$(".divGalleryLeft").click(function() {
//		var activeItem = $($(".divGalleryContent > .imgGalleryActive")[0]);
//		
//		if (activeItem.prev() != null && activeItem.prev().length > 0) {
//			activeItem.attr("class", "imgGalleryInactive");
//			activeItem.prev().attr("class", "imgGalleryActive");
//		}
//	});
//	
//	$(".divGalleryRight").click(function() {
//		var activeItem = $($(".divGalleryContent > .imgGalleryActive")[0]);
//		
//		if (activeItem.next() != null && activeItem.next().length > 0) {
//			activeItem.attr("class", "imgGalleryInactive");
//			activeItem.next().attr("class", "imgGalleryActive");
//		}
//	});

	return false;
}

function inputAgregarAdjuntoOnClick(event, element) {
	var inputFile = $("#inputAdjunto").val();
	
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
		
		xmlHTTPRequest.send(formData);
		
		if (xmlHTTPRequest.status != 200) {
			alert(xmlHTTPRequest.responseText);
			return;
		} else {
			alert(JSON.parse(xmlHTTPRequest.responseText).message);
			$("#inputAdjunto").val(null);
			reloadArchivosAdjuntosData();
		}
	} else {
		alert("Debe seleccionar un archivo.");
	}
}

function buildGallery() {
	var html = "";
	
	html +=
		"<div class='divGalleryLeft'><</div>"
		+ "<div class='divGalleryContent'>"
			+ "<img class='imgGalleryActive' src='#'/>"
			+ "<img class='imgGalleryInactive' src='#'/>"
		+ "</div>"
		+ "<div class='divGalleryRight'>></div>";
	
	$("#divResultadoEntregaDistribucionDocumentos").append(html);
	
	$(".divGalleryLeft").click(function() {
		var activeItem = $($(".divGalleryContent > .imgGalleryActive")[0]);
		var inactiveItem = $($(".divGalleryContent > .imgGalleryInactive")[0]);
		
		activeItem.attr("class", "imgGalleryInactive");
		inactiveItem.attr("class", "imgGalleryActive");
	});
	
	$(".divGalleryRight").click(function() {
		var activeItem = $($(".divGalleryContent > .imgGalleryActive")[0]);
		var inactiveItem = $($(".divGalleryContent > .imgGalleryInactive")[0]);
		
		activeItem.attr("class", "imgGalleryInactive");
		inactiveItem.attr("class", "imgGalleryActive");
	});
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
	hideField("fechaBackoffice");
	hideField("fechaEntregaDistribuidor");
	hideField("fechaDevolucionDistribuidor");
	hideField("fechaEnvioAntel");
	hideField("fechaActivacion");
	hideField("fechaCoordinacion");
	hideField("resultadoEntregaDistribucion");
	hideField("resultadoEntregaDistribucionObservaciones");
<<<<<<< HEAD
	hideField("resultadoEntregaDistribucionLatitud");
	hideField("resultadoEntregaDistribucionLongitud");
	hideField("resultadoEntregaDistribucionPrecision");
	hideField("tarjetaCredito");
	hideField("cuotas");
	hideField("valorCuota");
	hideField("intereses");
	hideField("gastosAdministrativos");
	hideField("gastosConcesion");
	hideField("gastosAdministrativosTotales");
	hideField("unidadIndexada");
	hideField("tasaInteresEfectivaAnual");
	
	$("#divRealizarClearing").hide();
	$("#inputNumeroFacturaRiverGreen").prop("disabled", true);
=======
	hideField("resultadoEntregaDistribucionDocumentos");
	hideField("resultadoEntregaDistribucionURLAnverso");
	hideField("resultadoEntregaDistribucionURLReverso");
	hideField("resultadoEntregaDistribucionLatitud");
	hideField("resultadoEntregaDistribucionLongitud");
	hideField("resultadoEntregaDistribucionPrecision");
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
	
	$(".divButtonBar > div").hide();
	$(".divButtonTitleBar > div").hide();
	
	if (mode == __FORM_MODE_READ) {
		$("#divEmpresa").html("&nbsp;");
		$("#divMid").html("&nbsp;");
		$("#divPlan").html("&nbsp;");
		$("#divNumeroContrato").html("&nbsp;");
		$("#divNuevoPlan").html("&nbsp;");
		$("#divMotivoCambioPlan").html("&nbsp;");
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
<<<<<<< HEAD
		$("#divAgregarAdjunto").html("&nbsp");
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
		
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
<<<<<<< HEAD
		
		$("#divAgregarAdjunto").html("&nbsp");
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
		
		$("#divLabelEmpresa").addClass("requiredFormLabel");
		$("#divLabelMid").addClass("requiredFormLabel");
		
		$("#divInputGuardar").show();
		
		$("#divButtonTitleFourfoldSize").hide();
		$("#divButtonTitleDoubleSize").attr("id", "divButtonTitleSingleSize");
	} else if (mode == __FORM_MODE_VENTA) {
		$("#divEmpresa").html("&nbsp;");
		$("#divMid").html("&nbsp;");
		
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
		
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
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
<<<<<<< HEAD
=======
		
		$("#divMid").html("&nbsp;");
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
		
<<<<<<< HEAD
		$("#divMid").html("&nbsp;");
		
//		$("#divFechaAtivarEn").html("&nbsp;");
//		hideField("fechaActivarEn");
=======
		$("#divFechaAtivarEn").html("&nbsp;");
		hideField("fechaActivarEn");
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
		
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
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
		
		$("#divMid").html("&nbsp;");
		
		$("#divFechaAtivarEn").html("&nbsp;");
		hideField("fechaActivarEn");
		
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
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
<<<<<<< HEAD
=======
		showField("resultadoEntregaDistribucionDocumentos");
		showField("resultadoEntregaDistribucionURLAnverso");
		showField("resultadoEntregaDistribucionURLReverso");
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
		showField("resultadoEntregaDistribucionObservaciones");
		
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
		
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
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
		
		$("#divInputActivar").show();
		$("#divInputNoFirma").show();
		$("#divInputRecoordinar").show();
<<<<<<< HEAD
		$("#divInputGestionDistribucion").show();
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
		
		$("#divInputImprimirAdjuntos").show();
		$("#divInputGuardar").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleDoubleSize");
<<<<<<< HEAD
		
		$(".divPopupWindow > .divLayoutColumn").css("height", "410");
	} else if (mode == __FORM_MODE_REDISTRIBUCION) {
=======
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleTripleSize");
	} else if (mode == __FORM_MODE_REDISTRIBUCION) {
		$("#divLabelResultadoEntregaDistribucionDocumentos").show();
		$("#divResultadoEntregaDistribucionDocumentos").show();
		$("#divLabelResultadoEntregaDistribucionURLAnverso").show();
		$("#divResultadoEntregaDistribucionURLAnverso").show();
		$("#divLabelResultadoEntregaDistribucionURLReverso").show();
		$("#divResultadoEntregaDistribucionURLReverso").show();
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
		$("#divLabelResultadoEntregaDistribucionObservaciones").show();
		$("#divResultadoEntregaDistribucionObservaciones").show();
		
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
		
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
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
		
		$("#divInputReActivar").show();
		$("#divInputNoFirma").show();
		$("#divInputRecoordinar").show();
<<<<<<< HEAD
		$("#divInputGestionDistribucion").show();
		
		$("#divInputGuardar").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
=======
		
		$("#divInputGuardar").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleSingleSize");
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleTripleSize");
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
	} else if (mode == __FORM_MODE_ACTIVACION) {
		$("#divEmpresa").html("&nbsp;");
		showField("empresa");
		
		$("#divMid").html("&nbsp;");
		
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
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
		
		$("#divInputEnviarAAntel").show();
		$("#divInputAgendarActivacion").show();
		$("#divInputTerminar").show();
		$("#divInputFaltaDocumentacion").show();
		
		$("#divInputImprimirContrato").show();
		$("#divInputGuardar").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleDoubleSize");
<<<<<<< HEAD
	} else if (mode == __FORM_MODE_SUPERVISOR_ACTIVACION) {
		$("#divEmpresa").html("&nbsp;");
		showField("empresa");
		
		$("#divMid").html("&nbsp;");
		
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
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
		
		$("#divInputEnviarAAntel").show();
		$("#divInputAgendarActivacion").show();
		$("#divInputTerminar").show();
		$("#divInputFaltaDocumentacion").show();
		$("#divInputCerrar").show();
		$("#divInputGestionInterna").show();
		$("#divInputEquipoPerdido").show();
		
		$("#divInputImprimirContrato").show();
		$("#divInputGuardar").show();
		
		$(".divButtonBarSeparator").show();
		$(".divButtonTitleBar > div").show();
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleSeptupleSize");
		$("#divButtonTitleTripleSize").attr("id", "divButtonTitleDoubleSize");
	}
}

function fillSelect(
	selectId, data, 
	valueField, descriptionField, 
	extraAttributeName, extraAttributeValueField, 
	extraAttribute2Name, extraAttribute2ValueField
) {
	$("#" + selectId + " > option").remove();
	
	var html = "<option value='0'>Seleccione...</option>";
	
	var values = {};
	
	for (var i=0; i<data.length; i++) {
		var value = null;
		var description = null;
		var extraValue = null;
		var extraValue2 = null;
		try {
			value = eval("data[" + i + "]." + valueField);
			description = eval("data[" + i + "]." + descriptionField);
		} catch (e) {
			value = null;
			description = null;
		}
		
		try {
			if (extraAttributeName != null) {
				extraValue = eval("data[" + i + "]." + extraAttributeValueField);
			}
			if (extraAttribute2Name != null) {
				extraValue2 = eval("data[" + i + "]." + extraAttribute2ValueField)
			}
		} catch (e) {
			extraValue = null;
			extraValue2 = null;
		}
		
		if (values[value] == null || !values[value]) {
			html += 
				"<option value='" + value + "'"
					+ (extraAttributeName != null ? " " + extraAttributeName + "='" + extraValue + "'" : "")
					+ (extraAttribute2Name != null ? " " + extraAttribute2Name + "='" + extraValue2 + "'" : "")
				+ ">" 
					+ description 
				+ "</option>";
			
			values[value] = true;
		}
	}
	
	$("#" + selectId).append(html);
}

/**
 * Carga en el control con id elementId, el dato data.field.
 * En caso de que el elemento con id elementId sea un DIV se carga en su texto el valor data.alternativeField.
 * Si se recibe un string en el parámetro extraAttributeName, se carga en su value el valor data.extraAttributeValueField.
 * Si se recibe una función en el parámetro formatter, se ejecuta antes de colocar el valor en el control.
 */
function populateField(elementId, data, field, alternativeField, extraAttributeName, extraAttributeValueField, formatter) {
	var elementSuffix = elementId.substring(0, 1).toUpperCase() + elementId.substring(1, field.length);
	
	var value = null;
	var alternativeValue = null;
	var extraValue = null;
	try {
		value = eval("data." + field);
		alternativeValue = eval("data." + alternativeField);
		extraValue = eval("data." + extraAttributeValueField);
	} catch (e) {
		
	}
	
	if ($("#input" + elementSuffix).length > 0) {
		$("#input" + elementSuffix).val(formatter != null ? formatter(value) : value);
		
		if (extraAttributeName != null) {
			$("#input" + elementSuffix).attr(extraAttributeName, extraValue);
		}
	} else if ($("#select" + elementSuffix).length > 0) {
		if (value != null) {
			$("#select" + elementSuffix).val(value);
		}
		if (extraAttributeName != null) {
			$("#select" + elementSuffix).attr(extraAttributeName, extraValue);
		}
	} else if ($("#textarea" + elementSuffix).length > 0) {
		$("#textarea" + elementSuffix).val(value);
		
		if (extraAttributeName != null) {
			$("#textarea" + elementSuffix).attr(extraAttributeName, extraValue);
		}
	} else if (alternativeValue != null && alternativeValue != "") {
		$("#div" + elementSuffix).html(formatter != null ? formatter(alternativeValue) : alternativeValue);
		
		if (extraAttributeName != null) {
			$("#div" + elementSuffix).attr(extraAttributeName, extraValue);
		}
	} else {
		$("#div" + elementSuffix).html("&nbsp;");
	}
}

function hideField(elementId) {
	var elementSuffix = elementId.substring(0, 1).toUpperCase() + elementId.substring(1, elementId.length);
	
	$("#divLabel" + elementSuffix).hide();
	$("#div" + elementSuffix).hide();
}

function showField(elementId) {
	var elementSuffix = elementId.substring(0, 1).toUpperCase() + elementId.substring(1, elementId.length);
	
	$("#divLabel" + elementSuffix).show();
	$("#div" + elementSuffix).show();
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
		if (empresaId != null && empresaId != "") {
			reloadFormasPago(empresaId, documento);
		}
	} else {
		reloadPrecio();
		reloadDatosFinanciacion();
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
	}
}

function fillSelect(selectId, data, valueField, descriptionField, extraAttributeName, extraAttributeValueField) {
	$("#" + selectId + " > option").remove();
	
	var html = "<option value='0'>Seleccione...</option>";
	
	var values = {};
	
	for (var i=0; i<data.length; i++) {
		var value = null;
		var description = null;
		var extraValue = null;
		try {
			value = eval("data[" + i + "]." + valueField);
			description = eval("data[" + i + "]." + descriptionField);
			if (extraAttributeName != null) {
				extraValue = eval("data[" + i + "]." + extraAttributeValueField);
			}
		} catch (e) {
			value = null;
			description = null;
			extraValue = null;
		}
		
		if (values[value] == null || !values[value]) {
			html += 
				"<option value='" + value + "'"
					+ (extraAttributeName != null ? " " + extraAttributeName + "='" + extraValue + "'" : "") 
				+ ">" 
					+ description 
				+ "</option>";
			
			values[value] = true;
		}
	}
	
	$("#" + selectId).append(html);
}

function populateField(elementId, data, field, alternativeField, extraAttributeName, extraAttributeValueField, formatter) {
	var elementSuffix = elementId.substring(0, 1).toUpperCase() + elementId.substring(1, field.length);
	
	var value = null;
	var alternativeValue = null;
	var extraValue = null;
	try {
		value = eval("data." + field);
		alternativeValue = eval("data." + alternativeField);
		extraValue = eval("data." + extraAttributeValueField);
	} catch (e) {
		value = null;
		alternativeValue = null;
		extraValue = null;
	}
	
	if ($("#input" + elementSuffix).length > 0) {
		$("#input" + elementSuffix).val(formatter != null ? formatter(value) : value);
		
		if (extraAttributeName != null) {
			$("#input" + elementSuffix).attr(extraAttributeName, extraValue);
		}
	} else if ($("#select" + elementSuffix).length > 0) {
		if (value != null) {
			$("#select" + elementSuffix).val(value);
		}
		if (extraAttributeName != null) {
			$("#select" + elementSuffix).attr(extraAttributeName, extraValue);
		}
	} else if ($("#textarea" + elementSuffix).length > 0) {
		$("#textarea" + elementSuffix).val(value);
		
		if (extraAttributeName != null) {
			$("#textarea" + elementSuffix).attr(extraAttributeName, extraValue);
		}
	} else if (alternativeValue != null && alternativeValue != "") {
		$("#div" + elementSuffix).html(formatter != null ? formatter(alternativeValue) : alternativeValue);
		
		if (extraAttributeName != null) {
			$("#div" + elementSuffix).attr(extraAttributeName, extraValue);
		}
	} else {
		$("#div" + elementSuffix).html("&nbsp;");
	}
}

function hideField(elementId) {
	var elementSuffix = elementId.substring(0, 1).toUpperCase() + elementId.substring(1, elementId.length);
	
	$("#divLabel" + elementSuffix).hide();
	$("#div" + elementSuffix).hide();
}

function showField(elementId) {
	var elementSuffix = elementId.substring(0, 1).toUpperCase() + elementId.substring(1, elementId.length);
	
	$("#divLabel" + elementSuffix).show();
	$("#div" + elementSuffix).show();
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
	
	if (($("#selectEquipo").val() != 0) && ($("#selectMoneda").val() != 0)) {
		PrecioDWR.getActualByEmpresaMarcaModeloMoneda(
			{
				id: $("#selectEmpresa").length > 0 ? $("#selectEmpresa").val() : $("#divEmpresa").attr("eid")
			},
			{
				id: $("#selectEquipo").length > 0 ? $("#selectEquipo option:selected").attr("maid") : $("#divEquipo").attr("maid")
			},
			{
				id: $("#selectEquipo").length > 0 ? $("#selectEquipo option:selected").attr("moid") : $("#divEquipo").attr("moid")
			},
			{
				id: $("#selectMoneda").length > 0 ? $("#selectMoneda").val() : $("#divMoneda").attr("mid")
			},
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
	} else if ($("#selectFormaPago").val() == __FORMA_PAGO_NUESTRO_CREDITO_ID) {
		hideField("selectTarjetaCredito");
		
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
	} else {
		hideField("tarjetaCredito");
		hideField("cuotas");
		hideField("valorCuota");
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
			if (valueElementSelected.val() == null || valueElementSelected.val() == "") {
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
<<<<<<< HEAD
			($("#divNumeroSerie").text().trim() != "" ? $("#divNumeroSerie").text().trim() : null),
=======
				$("#divNumeroSerie").text().trim(),
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
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
				parseShortDate($("#divFechaNacimiento").text().trim()),
		
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
			$("#inputDireccionEntregaBis").prop("checked") : $("#divDireccionEntregaBis").text().trim(),
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
				parseShortDate($("#divFechaActivarEn").text().trim()),
		observaciones: $("#textareaObservaciones").length > 0 ?
			($("#textareaObservaciones").val().trim() != "" ? $("#textareaObservaciones").val().trim() : null) :
				$("#divObservaciones").text().trim(),
		resultadoEntregaDistribucionObservaciones: $("#textareaResultadoEntregaDistribucionObservaciones").length > 0 ?
			($("#textareaResultadoEntregaDistribucionObservaciones").val().trim() != "" ? $("#textareaResultadoEntregaDistribucionObservaciones").val().trim() : null) :
				$("#divResultadoEntregaDistribucionObservaciones").text().trim(),
//		resultadoEntregaDistribucionURLAnverso: $("#divResultadoEntregaDistribucionURLAnverso").text().trim() != "" ? $("#divResultadoEntregaDistribucionURLAnverso").text().trim() : null,
//		resultadoEntregaDistribucionURLReverso: $("#divResultadoEntregaDistribucionURLReverso").text().trim() != "" ? $("#divResultadoEntregaDistribucionURLReverso").text().trim() : null,
		resultadoEntregaDistribucionLatitud: $("#divResultadoEntregaDistribucionLatitud").text().trim() != "" ? $("#divResultadoEntregaDistribucionLatitud").text().trim() : null,
		resultadoEntregaDistribucionLongitud: $("#divResultadoEntregaDistribucionLongitud").text().trim() != "" ? $("#divResultadoEntregaDistribucionLongitud").text().trim() : null,
		resultadoEntregaDistribucionPrecision: $("#divResultadoEntregaDistribucionPrecision").text().trim() != "" ? $("#divResultadoEntregaDistribucionPrecision").text().trim() : null
	};
	
	if ($("#selectDireccionEntregaDepartamento").val() != "0") {
		contrato.direccionEntregaDepartamento = {
			id: $("#selectDireccionEntregaDepartamento").val()
<<<<<<< HEAD
=======
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
	
	if ($("#selectEquipo").val() != "0") {
		contrato.producto = {
			id: $("#selectEquipo").val()
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
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
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
	
	if ($("#selectEquipo").val() != "0") {
		contrato.marca = {
			id: $("#selectEquipo").length > 0 ? $("#selectEquipo option:selected").attr("maid") : $("#divEquipo").attr("maid")
		};
		
		contrato.modelo = {
			id: $("#selectEquipo").length > 0 ? $("#selectEquipo option:selected").attr("moid") : $("#divEquipo").attr("moid")
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

function inputImprimirAdjuntosOnClick() {
	if (!checkRequiredFields()) {
		alert("Informaci�n incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	window.open("/LogisticaWEB/pages/contrato/contrato_adjunto_print.jsp?cid=" + contrato.id);
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
	
	ContratoDWR.agendar(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
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
	
	ContratoDWR.distribuir(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
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
	
	ContratoDWR.activar(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
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
	
	ContratoDWR.agendarActivacion(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputEnviarAAntelOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	ContratoDWR.enviarAAntel(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
			}, async: false
		}
	);
}

function inputTerminarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	ContratoDWR.terminar(
		collectContratoData(),
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				window.parent.closeDialog();
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