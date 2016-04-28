var __SEMANAS_DISPONIBILIDAD_FECHA_ENTREGA = 4;

$(document).ready(function() {
	refinarForm();
	
	$("#selectEmpresa").append("<option value='0'>Seleccione...</option>");
	
	if ($("#selectEmpresa").length > 0) {
		UsuarioRolEmpresaDWR.listEmpresasByContext(
			{
				callback: function(data) {
					var html = "";
					
					var ids = {};
					
					for (var i=0; i<data.length; i++) {
						if (ids[data[i].id] == null || !ids[data[i].id]) {
							html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
							
							ids[data[i].id] = true;
						}
					}
					
					$("#selectEmpresa").append(html);
				}, async: false
			}
		);
	}
	
	$("#selectEquipo").append("<option value='0'>Seleccione...</option>");
	
	if ($("#selectDepartamento").length > 0) {
		$("#selectDepartamento").append("<option value='0'>Seleccione...</option>");
		
		DepartamentoDWR.list(
			{
				callback: function(data) {
					var html = "";
					
					for (var i=0; i<data.length; i++) {
						html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
					}
					
					$("#selectDepartamento").append(html);
				}, async: false
			}
		);
	}
	
	if ($("#selectBarrio").length > 0) {
		$("#selectBarrio").append("<option value='0'>Seleccione...</option>");
		
		BarrioDWR.list(
			{
				callback: function(data) {
					var html = "";
					
					for (var i=0; i<data.length; i++) {
						html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
					}
					
					$("#selectBarrio").append(html);
				}, async: false
			}
		);
	}
	
	if ($("#selectZona").length > 0) {
		$("#selectZona").append("<option value='0'>Seleccione...</option>");
		
		ZonaDWR.list(
			{
				callback: function(data) {
					var html = "";
					
					for (var i=0; i<data.length; i++) {
						html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
					}
					
					$("#selectZona").append(html);
				}, async: false
			}
		);
	}
	
	if ($("#selectTurno").length > 0) {
		$("#selectTurno").append("<option value='0'>Seleccione...</option>");
		
		TurnoDWR.list(
			{
				callback: function(data) {
					var html = "";
					
					for (var i=0; i<data.length; i++) {
						html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
					}
					
					$("#selectTurno").append(html);
				}, async: false
			}
		);
	}
	
	if ($("#selectFechaEntrega").length > 0) {
		$("#selectFechaEntrega").append("<option value='0'>Seleccione...</option>");
	}
	
	if (id != null) {
		ContratoDWR.getById(
			id,
			{
				callback: function(data) {
					$("#divNumeroTramite").text(data.numeroTramite);
					$("#divMid").text(data.mid);
					$("#divEstado").text(data.estado.nombre);
					$("#divEstado").attr("eid", data.estado.id);
					
					// Fechas del workflow
					// -------------------
					$("#divFechaVenta").html(
						data.fechaVenta != null && data.fechaVenta != "" ? data.fechaVenta.getTime() : "&nbsp;"
					);
					$("#divFechaBackoffice").html(
						data.fechaBackoffice != null && data.fechaBackoffice != "" ? data.fechaBackoffice.getTime() : "&nbsp;"
					);
					$("#divFechaEntregaDistribuidor").html(
						data.fechaEntregaDistribuidor != null && data.fechaEntregaDistribuidor != "" ? data.fechaEntregaDistribuidor.getTime() : "&nbsp;"
					);
					$("#divFechaDevolucionDistribuidor").html(
						data.fechaDevolucionDistribuidor != null && data.fechaDevolucionDistribuidor != "" ? data.fechaDevolucionDistribuidor.getTime() : "&nbsp;"
					);
					$("#divFechaEnvioAntel").html(
						data.fechaEnvioAntel != null && data.fechaEnvioAntel != "" ? data.fechaEnvioAntel.getTime() : "&nbsp;"
					);
					$("#divFechaActivacion").html(
						data.fechaActivacion != null && data.fechaActivacion != "" ? data.fechaActivacion.getTime() : "&nbsp;"
					);
					$("#divFechaCoordinacion").html(
						data.fechaCoordinacion != null && data.fechaCoordinacion != "" ? data.fechaCoordinacion.getTime() : "&nbsp;"
					);
					// -------------------
					
					$("#divLocalidad").html(
						data.localidad != null && data.localidad != "" ? data.localidad : "&nbsp;"
					);
					$("#divCodigoPostal").html(
						data.codigoPostal != null && data.codigoPostal != "" ? data.codigoPostal : "&nbsp;"
					);
					$("#divFechaVencimiento").html(
						data.fechaFinContrato != null && data.fechaFinContrato != "" ? formatShortDate(data.fechaFinContrato) : "&nbsp;"
					);
					$("#inputNumeroContrato").length > 0 ?
						$("#inputNumeroContrato").val(data.numeroContrato) :
						(data.numeroContrato != null && data.numeroContrato != "" ?
							$("#divNumeroContrato").html(data.numeroContrato) :
							$("#divNumeroContrato").html("&nbsp;"));
					$("#inputPlan").length > 0 ? 
						$("#inputPlan").val(data.tipoContratoDescripcion) : 
						(data.tipoContratoDescripcion != null && data.tipoContratoDescripcion != "" ? 
							$("#divPlan").html(data.tipoContratoDescripcion) : 
							$("#divPlan").html("&nbsp;"));
					$("#inputNuevoPlan").length > 0 ? 
						$("#inputNuevoPlan").val(data.nuevoPlan) : 
						(data.nuevoPlan != null && data.nuevoPlan != "" ? 
							$("#divNuevoPlan").html(data.nuevoPlan) : 
							$("#divNuevoPlan").html("&nbsp;"));
					
					if (data.empresa != null) {
						$("#divEmpresa").attr("eid", data.empresa.id);
						$("#divEmpresa").text(data.empresa.nombre);
						
						$("#selectEquipo > option").remove();
						
						$("#selectEquipo").append("<option value='0'>Seleccione...</option>");

						if ($("#selectEquipo").length > 0) {
							StockMovimientoDWR.listStockByEmpresaId(
								data.empresa.id,
								{
									callback: function(dataStock) {
										var html = "";
										
										for (var i=0; i<dataStock.length; i++) {
											if (dataStock[i].cantidad > 0) {
												html += "<option value='" + dataStock[i].producto.id + "'>" 
														+ dataStock[i].producto.descripcion + " (" + dataStock[i].cantidad + ")"
													+ "</option>";
											}
										}
										
										$("#selectEquipo").append(html);
									}, async: false
								}
							);
						}
					}
					
					if ($("#selectEquipo").length > 0) {
						if (data.producto != null && data.producto != "") {
							if ($("#selectEquipo > option[value='" + data.producto.id + "']").length == 0) {
								var html = 
									"<option value='" + data.producto.id + "'>"
										+ data.producto.descripcion
									+ "</option>";
								
								$("#selectEquipo").append(html);
							}
							
							$("#selectEquipo").val(data.producto.id);
						} else {
							$("#selectEquipo").val(0);
						}
					} else {
						(data.producto != null && data.producto != "" ? 
							$("#divEquipo").html(data.producto.descripcion) : 
							$("#divEquipo").html("&nbsp;"));
					}

					$("#inputNumeroSerie").length > 0 ? 
						$("#inputNumeroSerie").val(data.numeroSerie) : 
						(data.numeroSerie != null && data.numeroSerie != "" ? 
							$("#divNumeroSerie").html(data.numeroSerie) : 
							$("#divNumeroSerie").html("&nbsp;"));
					$("#inputDocumento").length > 0 ? 
						$("#inputDocumento").val(data.documento) : 
						(data.documento != null && data.documento != "" ? 
							$("#divDocumento").html(data.documento) : 
							$("#divDocumento").html("&nbsp;"));
					$("#inputNombre").length > 0 ? 
						$("#inputNombre").val(data.nombre) : 
						(data.nombre != null && data.nombre != "" ? 
							$("#divNombre").html(data.nombre) : 
							$("#divNombre").html("&nbsp;"));
					$("#inputFechaNacimiento").length > 0 ? 
							(data.fechaNacimiento != null && data.fechaNacimiento != "" ?
								$("#inputFechaNacimiento").val(formatShortDate(data.fechaNacimiento)) :
								$("#inputFechaNacimiento").val("")) : 
							(data.fechaNacimiento != null && data.fechaNacimiento != "" ? 
								$("#divFechaNacimiento").html(formatShortDate(data.fechaNacimiento)) : 
								$("#divFechaNacimiento").html("&nbsp;"));
					$("#inputNumeroFactura").length > 0 ? 
						$("#inputNumeroFactura").val(data.numeroFactura) : 
						(data.numeroFactura != null && data.numeroFactura != "" ? 
							$("#divNumeroFactura").html(data.numeroFactura) : 
							$("#divNumeroFactura").html("&nbsp;"));
					$("#inputDireccionFactura").length > 0 ? 
						$("#inputDireccionFactura").val(data.direccionFactura) : 
						(data.direccionFactura != null && data.direccionFactura != "" ? 
							$("#divDireccionFactura").html(data.direccionFactura) : 
							$("#divDireccionFactura").html("&nbsp;"));
					$("#inputDireccionEntrega").length > 0 ? 
						$("#inputDireccionEntrega").val(data.direccionEntrega) : 
						(data.direccionEntrega != null && data.direccionEntrega != "" ? 
							$("#divDireccionEntrega").html(data.direccionEntrega) : 
							$("#divDireccionEntrega").html("&nbsp;"));
					$("#inputTelefonoContacto").length > 0 ? 
						$("#inputTelefonoContacto").val(data.telefonoContacto) : 
						(data.telefonoContacto != null && data.telefonoContacto != "" ? 
							$("#divTelefonoContacto").html(data.telefonoContacto) : 
							$("#divTelefonoContacto").html("&nbsp;"));
					$("#inputEmail").length > 0 ? 
						$("#inputEmail").val(data.email) : 
						(data.email != null && data.email != "" ? 
							$("#divEmail").html(data.email) : 
							$("#divEmail").html("&nbsp;"));
					$("#inputPrecio").length > 0 ? 
						$("#inputPrecio").val(data.precio) : 
						(data.precio != null && data.precio != "" ? 
							$("#divPrecio").html(data.precio) : 
							$("#divPrecio").html("&nbsp;"));
					if (data.zona != null) {
						if ($("#selectDepartamento").length > 0) {
							$("#selectDepartamento").val(data.zona.departamento.id);
							$("#selectBarrio").val(data.barrio.id);
							$("#selectZona").val(data.zona.id);
						} else {
							$("#divDepartamento").html(data.zona.departamento.nombre);
							$("#divBarrio").html(data.barrio.nombre);
							$("#divZona").html(data.zona.nombre);
						}
					}
					if (data.turno != null) {
						if ($("#selectTurno").length > 0) {
							$("#selectTurno").val(data.turno.id);
							
							selectTurnoOnChange();
						} else {
							$("#divTurno").html(data.turno.nombre);
						}
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
					$("#inputFechaActivarEn").length > 0 ? 
						(data.fechaActivarEn != null && data.fechaActivarEn != "" ?
							$("#inputFechaActivarEn").val(formatShortDate(data.fechaActivarEn)) :
							$("#inputFechaActivarEn").val("")) : 
						(data.fechaActivarEn != null && data.fechaActivarEn != "" ? 
							$("#divFechaActivarEn").html(formatShortDate(data.fechaActivarEn)) : 
							$("#divFechaActivarEn").html("&nbsp;"));
					$("#textareaObservaciones").length > 0 ? 
						$("#textareaObservaciones").val(data.observaciones) : 
							$("#divObservaciones").html(data.observaciones);
					$("#textareaResultadoEntregaDistribucionObservaciones").length > 0 ? 
						$("#textareaResultadoEntregaDistribucionObservaciones").val(data.resultadoEntregaDistribucionObservaciones) : 
							$("#divResultadoEntregaDistribucionObservaciones").html(data.resultadoEntregaDistribucionObservaciones);
					if (data.resultadoEntregaDistribucionURLAnverso != null && data.resultadoEntregaDistribucionURLAnverso != "") {
						$("#imgResultadoEntregaDistribucionURLAnverso").attr("src", "/LogisticaWEB/Stream?fn=" + data.resultadoEntregaDistribucionURLAnverso);
					}
					if (data.resultadoEntregaDistribucionURLReverso != null && data.resultadoEntregaDistribucionURLReverso!= "") {
						$("#imgResultadoEntregaDistribucionURLReverso").attr("src", "/LogisticaWEB/Stream?fn=" + data.resultadoEntregaDistribucionURLReverso);
					}
					
					if ($("#inputNuevoPlan").length > 0) {
						$("#inputNuevoPlan").focus();
					}
					
					if (data.rol != null) {
						$("#divRol").attr("rid", data.rol.id);
					}
					
					if (data.usuario != null) {
						$("#divUsuario").attr("uid", data.usuario.id);
					}
					
					if (data.vendedor != null) {
						$("#divVendedor").attr("vid", data.vendedor.id);
					}
					
					if (data.backoffice != null) {
						$("#divBackoffice").attr("bid", data.backoffice.id);
					}
					
					if (data.distribuidor != null) {
						$("#divDistribuidor").attr("did", data.distribuidor.id);
					}
					
					if (data.activador != null) {
						$("#divActivador").attr("aid", data.activador.id);
					}
					
					if (data.coordinador != null) {
						$("#divCoordinador").attr("coid", data.coordinador.id);
					}
					
					if (data.resultadoEntregaDistribucion != null) {
						$("#divResultadoEntregaDistribucion").text(data.resultadoEntregaDistribucion.descripcion);
						$("#divResultadoEntregaDistribucion").attr("redid", data.resultadoEntregaDistribucion.id);
					}
					
					if (data.resultadoEntregaDistribucionLatitud != null) {
						$("#divResultadoEntregaDistribucionLatitud").text(data.resultadoEntregaDistribucionLatitud);
					}
					
					if (data.resultadoEntregaDistribucionLongitud != null) {
						$("#divResultadoEntregaDistribucionLongitud").text(data.resultadoEntregaDistribucionLongitud);
					}
					
					if (data.resultadoEntregaDistribucionPrecision != null) {
						$("#divResultadoEntregaDistribucionPrecision").text(data.resultadoEntregaDistribucionPrecision);
					}
				}, async: false
			}
		);
	}
});

function refinarForm() {
	$("#divLabelRol").hide();
	$("#divRol").hide();
	$("#divLabelUsuario").hide();
	$("#divUsuario").hide();
	$("#divLabelVendedor").hide();
	$("#divVendedor").hide();
	$("#divLabelBackoffice").hide();
	$("#divBackoffice").hide();
	$("#divLabelDistribuidor").hide();
	$("#divDistribuidor").hide();
	$("#divLabelActivador").hide();
	$("#divActivador").hide();
	$("#divLabelCoordinador").hide();
	$("#divCoordinador").hide();
	$("#divLabelFechaVenta").hide();
	$("#divFechaVenta").hide();
	$("#divLabelFechaBackoffice").hide();
	$("#divFechaBackoffice").hide();
	$("#divLabelFechaEntregaDistribuidor").hide();
	$("#divFechaEntregaDistribuidor").hide();
	$("#divLabelFechaDevolucionDistribuidor").hide();
	$("#divFechaDevolucionDistribuidor").hide();
	$("#divLabelFechaEnvioAntel").hide();
	$("#divFechaEnvioAntel").hide();
	$("#divLabelFechaActivacion").hide();
	$("#divFechaActivacion").hide();
	$("#divLabelFechaCoordinacion").hide();
	$("#divFechaCoordinacion").hide();
	$("#divLabelResultadoEntregaDistribucion").hide();
	$("#divResultadoEntregaDistribucion").hide();
	$("#divLabelResultadoEntregaDistribucionObservaciones").hide();
	$("#divResultadoEntregaDistribucionObservaciones").hide();
	$("#divLabelResultadoEntregaDistribucionURLAnverso").hide();
	$("#divResultadoEntregaDistribucionURLAnverso").hide();
	$("#divLabelResultadoEntregaDistribucionURLReverso").hide();
	$("#divResultadoEntregaDistribucionURLReverso").hide();
	$("#divLabelResultadoEntregaDistribucionLatitud").hide();
	$("#divResultadoEntregaDistribucionLatitud").hide();
	$("#divLabelResultadoEntregaDistribucionLongitud").hide();
	$("#divResultadoEntregaDistribucionLongitud").hide();
	$("#divLabelResultadoEntregaDistribucionPrecision").hide();
	$("#divResultadoEntregaDistribucionPrecision").hide();
	
	if (mode == __FORM_MODE_READ) {
		$("#divInputAgendar").hide();
		$("#divInputRechazar").hide();
		$("#divInputPosponer").hide();
		$("#divInputDistribuir").hide();
		$("#divInputRedistribuir").hide();
		$("#divInputTelelink").hide();
		$("#divInputRenovo").hide();
		$("#divInputReagendar").hide();
		$("#divInputActivar").hide();
		$("#divInputNoFirma").hide();
		$("#divInputRecoordinar").hide();
		$("#divInputEnviarAAntel").hide();
		$("#divInputTerminar").hide();
		$("#divInputAgendarActivacion").hide();
		$("#divInputFaltaDocumentacion").hide();
		$("#divInputReActivar").hide();
		$("#divInputNoRecoordina").hide();
		
		$("#divEmpresa").html("&nbsp;");
		$("#divLabelEmpresa").hide();
		$("#divEmpresa").hide();
		
		$("#divPlan").html("&nbsp;");
		$("#divNumeroContrato").html("&nbsp;");
		$("#divNuevoPlan").html("&nbsp;");
		$("#divEquipo").html("&nbsp;");
		$("#divNumeroSerie").html("&nbsp;");
		$("#divDocumento").html("&nbsp;");
		$("#divNombre").html("&nbsp;");
		$("#divFechaNacimiento").html("&nbsp;");
		$("#divNumeroFactura").html("&nbsp;");
		$("#divDireccionFactura").html("&nbsp;");
		$("#divDireccionEntrega").html("&nbsp;");
		$("#divTelefonoContacto").html("&nbsp;");
		$("#divEmail").html("&nbsp;");
		$("#divPrecio").html("&nbsp;");
		$("#divDepartamento").html("&nbsp;");
		$("#divBarrio").html("&nbsp;");
		$("#divZona").html("&nbsp;");
		$("#divTurno").html("&nbsp;");
		$("#divFechaEntrega").html("&nbsp;");
		$("#divFechaActivarEn").html("&nbsp;");
		$("#divObservaciones").html("&nbsp;");
		
		$(".divButtonBar > div").hide();
		$(".divButtonTitleBar > div").hide();
	} else if (mode == __FORM_MODE_NEW) {
		$("#divInputAgendar").hide();
		$("#divInputRechazar").hide();
		$("#divInputPosponer").hide();
		$("#divInputDistribuir").hide();
		$("#divInputRedistribuir").hide();
		$("#divInputTelelink").hide();
		$("#divInputRenovo").hide();
		$("#divInputReagendar").hide();
		$("#divInputActivar").hide();
		$("#divInputNoFirma").hide();
		$("#divInputRecoordinar").hide();
		$("#divInputAgendarActivacion").hide();
		$("#divInputEnviarAAntel").hide();
		$("#divInputTerminar").hide();
		$("#divInputFaltaDocumentacion").hide();
		$("#divInputImprimir").hide();
		$("#divInputReActivar").hide();
		$("#divInputNoRecoordina").hide();
		
		$("#divNumeroSerie").html("&nbsp;");
		$("#divLabelNumeroSerie").hide();
		$("#divNumeroSerie").hide();
		
		$("#divNumeroFactura").html("&nbsp;");
		$("#divLabelNumeroFactura").hide();
		$("#divNumeroFactura").hide();
		
		$("#divFechaAtivarEn").html("&nbsp;");
		$("#divLabelFechaActivarEn").hide();
		$("#divFechaActivarEn").hide();
		
		$("#divLabelEmpresa").addClass("requiredFormLabel");
		$("#divLabelMid").addClass("requiredFormLabel");
		
		$("#divButtonTitleFourfoldSize").hide();
		$("#divButtonTitleDoubleSize").attr("id", "divButtonTitleSingleSize");
	} else if (mode == __FORM_MODE_VENTA) {
		$("#divInputDistribuir").hide();
		$("#divInputRedistribuir").hide();
		$("#divInputReagendar").hide();
		$("#divInputActivar").hide();
		$("#divInputNoFirma").hide();
		$("#divInputRecoordinar").hide();
		$("#divInputAgendarActivacion").hide();
		$("#divInputEnviarAAntel").hide();
		$("#divInputTerminar").hide();
		$("#divInputFaltaDocumentacion").hide();
		$("#divInputReActivar").hide();
		$("#divInputNoRecoordina").hide();
		
		$("#divEmpresa").html("&nbsp;");
		$("#divLabelEmpresa").hide();
		$("#divEmpresa").hide();
		
		$("#divNumeroSerie").html("&nbsp;");
		$("#divLabelNumeroSerie").hide();
		$("#divNumeroSerie").hide();
		
		$("#divNumeroFactura").html("&nbsp;");
		$("#divLabelNumeroFactura").hide();
		$("#divNumeroFactura").hide();
		
		$("#divFechaAtivarEn").html("&nbsp;");
		$("#divLabelFechaActivarEn").hide();
		$("#divFechaActivarEn").hide();
		
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
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
		
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleQuintupleSize");
	} else if (mode == __FORM_MODE_BACKOFFICE) {
		$("#divInputAgendar").hide();
		$("#divInputPosponer").hide();
		$("#divInputRedistribuir").hide();
		$("#divInputActivar").hide();
		$("#divInputNoFirma").hide();
		$("#divInputRecoordinar").hide();
		$("#divInputAgendarActivacion").hide();
		$("#divInputEnviarAAntel").hide();
		$("#divInputTerminar").hide();
		$("#divInputFaltaDocumentacion").hide();
		$("#divInputReActivar").hide();
		$("#divInputNoRecoordina").hide();
		
		$("#divEmpresa").html("&nbsp;");
		$("#divLabelEmpresa").hide();
		$("#divEmpresa").hide();
		
		$("#divFechaAtivarEn").html("&nbsp;");
		$("#divLabelFechaActivarEn").hide();
		$("#divFechaActivarEn").hide();
		
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
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
		
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleQuintupleSize");
	} else if (mode == __FORM_MODE_RECOORDINACION) {
		$("#divInputAgendar").hide();
		$("#divInputRechazar").hide();
		$("#divInputPosponer").hide();
		$("#divInputDistribuir").hide();
		$("#divInputActivar").hide();
		$("#divInputNoFirma").hide();
		$("#divInputReagendar").hide();
		$("#divInputRecoordinar").hide();
		$("#divInputAgendarActivacion").hide();
		$("#divInputEnviarAAntel").hide();
		$("#divInputTerminar").hide();
		$("#divInputFaltaDocumentacion").hide();
		
//		$("#divInputRedistribuir").hide();
//		$("#divInputTelelink").hide();
//		$("#divInputRenovo").hide();
//		$("#divInputReActivar").hide();
//		$("#divInputNoRecoordina").hide();
		
		$("#divEmpresa").html("&nbsp;");
		$("#divLabelEmpresa").hide();
		$("#divEmpresa").hide();
		
		$("#divFechaAtivarEn").html("&nbsp;");
		$("#divLabelFechaActivarEn").hide();
		$("#divFechaActivarEn").hide();
		
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
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
		
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleQuintupleSize");
	} else if (mode == __FORM_MODE_DISTRIBUCION) {
		$("#divInputAgendar").hide();
		$("#divInputRechazar").hide();
		$("#divInputPosponer").hide();
		$("#divInputDistribuir").hide();
		$("#divInputRedistribuir").hide();
		$("#divInputTelelink").hide();
		$("#divInputRenovo").hide();
		$("#divInputReagendar").hide();
		$("#divInputAgendarActivacion").hide();
		$("#divInputEnviarAAntel").hide();
		$("#divInputTerminar").hide();
		$("#divInputFaltaDocumentacion").hide();
		$("#divInputReActivar").hide();
		$("#divInputNoRecoordina").hide();
		
		$("#divLabelResultadoEntregaDistribucionURLAnverso").show();
		$("#divResultadoEntregaDistribucionURLAnverso").show();
		$("#divLabelResultadoEntregaDistribucionURLReverso").show();
		$("#divResultadoEntregaDistribucionURLReverso").show();
		$("#divLabelResultadoEntregaDistribucionObservaciones").show();
		$("#divResultadoEntregaDistribucionObservaciones").show();
		
		$("#divEmpresa").html("&nbsp;");
		
		$("#divLocalidad").html("&nbsp;");
		$("#divLabelLocalidad").hide();
		$("#divLocalidad").hide();
		
		$("#divCodigoPostal").html("&nbsp;");
		$("#divLabelCodigoPostal").hide();
		$("#divCodigoPostal").hide();
		
		$("#divFechaVencimiento").html("&nbsp;");
		$("#divLabelFechaVencimiento").hide();
		$("#divFechaVencimiento").hide();
		
		$("#divPlan").html("&nbsp;");
		$("#divLabelPlan").hide();
		$("#divPlan").hide();
		
		$("#divFechaAtivarEn").html("&nbsp;");
		$("#divLabelFechaActivarEn").hide();
		$("#divFechaActivarEn").hide();
		
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
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
		
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleTripleSize");
	} else if (mode == __FORM_MODE_ACTIVACION) {
		$("#divInputAgendar").hide();
		$("#divInputRechazar").hide();
		$("#divInputPosponer").hide();
		$("#divInputDistribuir").hide();
		$("#divInputRedistribuir").hide();
		$("#divInputTelelink").hide();
		$("#divInputRenovo").hide();
		$("#divInputReagendar").hide();
		$("#divInputActivar").hide();
		$("#divInputNoFirma").hide();
		$("#divInputRecoordinar").hide();
		$("#divInputReActivar").hide();
		$("#divInputNoRecoordina").hide();
		
		$("#divEmpresa").html("&nbsp;");
		$("#divLabelEmpresa").hide();
		$("#divEmpresa").hide();
		
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		$("#divLabelEquipo").addClass("requiredFormLabel");
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
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
		
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleFourfoldSize");
	}
}

function selectDepartamentoOnChange() {
	$("#selectBarrio > option:gt(0)").remove();
	$("#selectZona > option:gt(0)").remove();
	$("#selectTurno > option:gt(0)").remove();
	$("#selectFechaEntrega > option:gt(0)").remove();
	
	BarrioDWR.listByDepartamentoId(
		$("#selectDepartamento").val(),
		{
			callback: function(data) {
				$("#selectBarrio > option:gt(0)").remove();
				
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "' zid='" + data[i].zona.id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectBarrio").append(html);
				
				selectBarrioOnChange();
			}, async: false
		}
	);
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
					$("#selectZona > option:gt(0)").remove();
					
					var html = "<option value='" + data.id + "'>" + data.nombre + "</option>";
					
					$("#selectZona").append(html);
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
	
	TurnoDWR.list(
		{
			callback: function(data) {
				$("#selectTurno > option:gt(0)").remove();
				
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectTurno").append(html);
				
				selectTurnoOnChange();
			}, async: false
		}
	);
}

function selectTurnoOnChange() {
	$("#selectFechaEntrega > option:gt(0)").remove();
	
	DisponibilidadEntregaEmpresaZonaTurnoDWR.listByEmpresaZonaTurno(
		{
			id: $("#divEmpresa").attr("eid")
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
				
				$("#selectFechaEntrega > option:gt(0)").remove();
				
				var html = "";
				
				var date = new Date();
				for (var j=0; j<availability.length * __SEMANAS_DISPONIBILIDAD_FECHA_ENTREGA; j++) {
					if (availability[date.getDay()] > 0) {
						html += 
							"<option value='" + formatShortDate(date) + "'>" 
								+ formatShortDate(date) + " - " + availability[date.getDay()]
							+ "</option>";
					}
					
					date.setDate(date.getDate() + 1);
				}
				
				$("#selectFechaEntrega").append(html);
				
				selectFechaEntregaOnChange();
			}, async: false
		}
	);
}

function selectFechaEntregaOnChange() {
	return true;
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
		fechaFinContrato: $("#divFechaVencimiento").text().trim() != "" ? parseShortDate($("#divFechaVencimiento").text().trim()) : null,
		tipoContratoDescripcion: $("#inputPlan").length > 0 ? 
			($("#inputPlan").val().trim() != "" ? $("#inputPlan").val().trim() : null) : 
				$("#divPlan").text().trim(),
		numeroContrato: $("#inputNumeroContrato").length > 0 ? 
			($("#inputNumeroContrato").val().trim() != "" ? $("#inputNumeroContrato").val().trim() : null) : 
				$("#divNumeroContrato").text().trim(),
		nuevoPlan: $("#inputNuevoPlan").length > 0 ? 
			($("#inputNuevoPlan").val().trim() != "" ? $("#inputNuevoPlan").val().trim() : null) : 
				$("#divNuevoPlan").text().trim(),
		numeroSerie: $("#inputNumeroSerie").length > 0 ? 
			($("#inputNumeroSerie").val().trim() != "" ? $("#inputNumeroSerie").val().trim() : null) :
				$("#divNumeroSerie").text().trim(),
		localidad: $("#divLocalidad").text().trim() != "" ? $("#divLocalidad").text().trim() : null,
		precio: $("#inputPrecio").length > 0 ? 
			($("#inputPrecio").val().trim() != "" ? $("#inputPrecio").val().trim() : null) :
				$("#divPrecio").text().trim(),
		numeroFactura: $("#inputNumeroFactura").length > 0 ?
			($("#inputNumeroFactura").val().trim() != "" ? $("#inputNumeroFactura").val().trim() : null) :
				$("#divNumeroFactura").text().trim(),
		documento: $("#inputDocumento").length > 0 ?
			($("#inputDocumento").val().trim() != "" ? $("#inputDocumento").val().trim() : null) :
				$("#divDocumento").text().trim(),
		nombre: $("#inputNombre").length > 0 ?
			($("#inputNombre").val().trim() != "" ? $("#inputNombre").val().trim() : null) :
				$("#divNombre").text().trim(),
		fechaNacimiento: $("#inputFechaNacimiento").length > 0 ?
			($("#inputFechaNacimiento").val().trim() != "" ? parseShortDate($("#inputFechaNacimiento").val().trim()) : null) :
				parseShortDate($("#divFechaNacimiento").text().trim()),
		direccionFactura: $("#inputDireccionFactura").length > 0 ?
			($("#inputDireccionFactura").val().trim() != "" ? $("#inputDireccionFactura").val().trim() : null) :
				$("#divDireccionFactura").text().trim(),
		direccionEntrega: $("#inputDireccionEntrega").length > 0 ?
			($("#inputDireccionEntrega").val().trim() != "" ? $("#inputDireccionEntrega").val().trim() : null) :
				$("#divDireccionEntrega").text().trim(),
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
		resultadoEntregaDistribucionURLAnverso: $("#divResultadoEntregaDistribucionURLAnverso").text().trim() != "" ? $("#divResultadoEntregaDistribucionURLAnverso").text().trim() : null,
		resultadoEntregaDistribucionURLReverso: $("#divResultadoEntregaDistribucionURLReverso").text().trim() != "" ? $("#divResultadoEntregaDistribucionURLReverso").text().trim() : null,
		resultadoEntregaDistribucionLatitud: $("#divResultadoEntregaDistribucionLatitud").text().trim() != "" ? $("#divResultadoEntregaDistribucionLatitud").text().trim() : null,
		resultadoEntregaDistribucionLongitud: $("#divResultadoEntregaDistribucionLongitud").text().trim() != "" ? $("#divResultadoEntregaDistribucionLongitud").text().trim() : null,
		resultadoEntregaDistribucionPrecision: $("#divResultadoEntregaDistribucionPrecision").text().trim() != "" ? $("#divResultadoEntregaDistribucionPrecision").text().trim() : null
	};
	
	if ($("#selectEquipo").val() != "0") {
		contrato.producto = {
			id: $("#selectEquipo").val()
		};
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
	
	return contrato;
}

function inputImprimirOnClick() {
	var contrato = collectContratoData();
	
	window.open("/LogisticaWEB/pages/contrato/contrato_print.jsp?m=" + __FORM_MODE_PRINT + "&cid=" + contrato.id);
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