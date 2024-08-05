function inputImprimirKitOnClick() {
	var contrato = collectContratoData();
	
	window.open(
		"/LogisticaWEB/pages/contrato/contrato_print.jsp?m=" + __FORM_MODE_PRINT + "&cid=" + contrato.id
	);
}

function inputImprimirKitANTELOnClick() {
	var contrato = collectContratoData();
	
	window.open(
		"/LogisticaWEB/pages/contrato/contrato_kit_antel_print.jsp?m=" + __FORM_MODE_PRINT + "&cid=" + contrato.id
	);
}

function inputImprimirQRInstalacionFibraOnClick(event) {
	var contrato = collectContratoData();
	
	window.open(
		"/LogisticaWEB/pages/contrato/contrato_qr_instalacion_fibra_print.jsp?m=" + __FORM_MODE_PRINT + "&cid=" + contrato.id
	);
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

function inputEstadoRiesgoCrediticioOnClick() {
	var empresaId = $("#divEmpresa").attr("eid");
	var documento = $("#inputDocumento").val();
	
	if (documento != null && documento != "") {
		documento = documento.trim();
		
		if (empresaId != null && empresaId != "") {
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
					if (data.elegibilidad == __ELEGIBILIDAD_SIN_ANALISIS) {
						if (confirm("No hay información de análisis de riesgo crediticio.\n"
								+ "¿Desea registrarlo manualmente?")) {
							if (confirm(
								"¿El control realizado permite habilitar la financiación con Nuestro crédito?\n"
								+ "(Aceptar = Sí / Cancelar = No)")
							) {
								$.ajax({
									url: "/LogisticaWEB/RESTFacade/RiesgoCrediticioREST/registrarAnalisisAprobadoManual",
									method: "POST",
									contentType: 'application/json',
									data: JSON.stringify({
										"empresaId": empresaId,
										"documento": documento
									})
								}).then(function(data) {
									alert("Operación exitosa.");
									
									reloadFormasPago(empresaId, documento);
								});
							} else {
								$.ajax({
									url: "/LogisticaWEB/RESTFacade/RiesgoCrediticioREST/registrarAnalisisRechazadoManual",
									method: "POST",
									contentType: 'application/json',
									data: JSON.stringify({
										"empresaId": empresaId,
										"documento": documento
									})
								}).then(function(data) {
									alert("Operación exitosa.");
									
									reloadFormasPago(empresaId, documento);
								});
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
			});
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
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/update",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(contrato)
		}).then(function(data) {
			alert("Operación exitosa");
		});
	} else {
		if(!checkRequiredFields()) {
			alert("Ingrese los campos requeridos.");
			return false;
		}
		
		if (mode == __FORM_MODE_NEW_INSTALACION_FIBRA) {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/ContratoREST/addAsignacionManualFibraOptica",
				method: "POST",
				contentType: 'application/json',
				data: JSON.stringify(contrato)
			}).then(function(data) {
				if (data != null) {
					alert(data.resultado);
					
					if (data.id != null && data.id != "") {
						id = data.id
						mode = __FORM_MODE_READ_INSTALACION_FIBRA;
						
						refinarFormRemote();
						reloadContrato();
					}
				} else {
					alert("No se pudo completar la operación.");
				}
			});
		} else if (mode == __FORM_MODE_NEW_ATENCION_CLIENTE) {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/ContratoREST/addAsignacionManualAtencionCliente",
				method: "POST",
				contentType: 'application/json',
				data: JSON.stringify(contrato)
			}).then(function(data) {
				if (data != null) {
					alert(data.resultado);
					
					if (data.id != null && data.id != "") {
						id = data.id
						mode = __FORM_MODE_READ_ATENCION_CLIENTE;
						
						refinarFormRemote();
						reloadContrato();
					}
				} else {
					alert("No se pudo completar la operación.");
				}
			});
		} else {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/ContratoREST/addAsignacionManual",
				method: "POST",
				contentType: 'application/json',
				data: JSON.stringify(contrato)
			}).then(function(data) {
				if (data != null) {
					alert(data.resultado);
					
					if (data.id != null && data.id != "") {
						id = data.id
						if (mode == __FORM_MODE_NEW_ANTEL) {
							mode = __FORM_MODE_READ_ANTEL;
						}
						refinarFormRemote();
						reloadContrato();
					}
				} else {
					alert("No se pudo completar la operación.");
				}
			});
		}
	}
}

function realizarAccionContrato(url, contrato) {
	$.ajax({
		url: url,
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(contrato)
	}).then(callbackRealizarAccionContrato);
}

function callbackRealizarAccionContrato(data) {
	alert("Operación exitosa");
	
	window.parent.closeDialog();
}

function inputAgendarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	if (contrato.moneda == null) {
		contrato.moneda = {
			id: 1
		};
	}
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/FinanciacionREST/validarFinanciacion",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
			"monedaId": contrato.moneda.id,
			"formaPagoId": contrato.formaPago.id,
			"monto": contrato.precio
		})
	}).then(function(dataFinanciacion) {
		if (dataFinanciacion != null) {
			alert(dataFinanciacion.mensaje);
		} else {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/ContratoREST/validarVenta",
				method: "POST",
				contentType: 'application/json',
				data: JSON.stringify(contrato)
			}).then(function(dataValidacion) {
				if (dataValidacion != null && dataValidacion.ok) {
					realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/agendar", contrato);
				} else {
					alert("No se puede realizar la operación.");
					
					window.parent.closeDialog();
				}
			});
		}
	});
}

function inputRechazarOnClick() {
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/rechazar", collectContratoData());
}

function inputPosponerOnClick() {
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/posponer", collectContratoData());
}

function inputDistribuirOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/FinanciacionREST/validarFinanciacion",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
			"monedaId": contrato.moneda.id,
			"formaPagoId": contrato.formaPago.id,
			"monto": contrato.precio
		})
	}).then(function(dataFinanciacion) {
		if (dataFinanciacion != null) {
			alert(dataFinanciacion.mensaje);
		} else {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/ContratoRelacionREST/listByContratoId/" + contrato.id
			}).then(function(contratosRelacionados) {
				if (contratosRelacionados.length > 0) {
					if (confirm("Atención: existen trámites relaciados. ¿Desea continuar?")) {
						realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/distribuir", contrato);
					}
				} else {
					realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/distribuir", contrato);
				}
			});
		}
	});
}

function inputRedistribuirOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/redistribuir", collectContratoData());
}

function inputTelelinkOnClick() {
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/telelink", collectContratoData());
}

function inputRenovoOnClick() {
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/renovo", collectContratoData());
}

function inputReagendarOnClick() {
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/reagendar", collectContratoData());
}

function inputActivarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/FinanciacionREST/validarFinanciacion",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
			"monedaId": contrato.moneda.id,
			"formaPagoId": contrato.formaPago.id,
			"monto": contrato.precio
		})
	}).then(function(dataFinanciacion) {
		if (dataFinanciacion != null) {
			alert(dataFinanciacion.mensaje);
		} else {
			realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/activar", contrato);
		}
	});
}

function inputNoFirmaOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/noFirma", collectContratoData());
}

function inputRecoordinarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/recoordinar", collectContratoData());
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
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/FinanciacionREST/validarFinanciacion",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
			"monedaId": contrato.moneda.id,
			"formaPagoId": contrato.formaPago.id,
			"monto": contrato.precio
		})
	}).then(function(dataFinanciacion) {
		if (dataFinanciacion != null) {
			alert(dataFinanciacion.mensaje);
		} else {
			realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/agendarActivacion", contrato);
		}
	});
}

function inputEnviarAAntelOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/FinanciacionREST/validarFinanciacion",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
			"monedaId": contrato.moneda.id,
			"formaPagoId": contrato.formaPago.id,
			"monto": contrato.precio
		})
	}).then(function(dataFinanciacion) {
		if (dataFinanciacion != null) {
			alert(dataFinanciacion.mensaje);
		} else {
			realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/enviarAAntel", contrato);
		}
	});
}

function inputTerminarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contrato = collectContratoData();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/FinanciacionREST/validarFinanciacion",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
			"monedaId": contrato.moneda.id,
			"formaPagoId": contrato.formaPago.id,
			"monto": contrato.precio
		})
	}).then(function(dataFinanciacion) {
		if (dataFinanciacion != null) {
			alert(dataFinanciacion.mensaje);
		} else {
			realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/terminar", contrato);
		}
	});
}

function inputFaltaDocumentacionOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/faltaDocumentacion", collectContratoData());
}

function inputReActivarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/reActivar", collectContratoData());
}

function inputNoRecoordinaOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/noRecoordina", collectContratoData());
}

function inputCerrarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/cerrar", collectContratoData());
}

function inputGestionInternaOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/gestionInterna", collectContratoData());
}

function inputGestionDistribucionOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/gestionDistribucion", collectContratoData());
}

function inputEquipoPerdidoOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/equipoPerdido", collectContratoData());
}

function inputFacturaImpagaOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/facturaImpaga", collectContratoData());
}

function inputEnviadoANucleoOnClick() {
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/enviadoANucleo", collectContratoData());
}

function inputCanceladoPorClienteOnClick() {
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/canceladoPorCliente", collectContratoData());
}

function inputEquiposPagosOnClick() {
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/equiposPagos", collectContratoData());
}

function inputEquipoDevueltoOnClick() {
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/equipoDevuelto", collectContratoData());
}

function inputNoRecuperadoOnClick() {
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/noRecuperado", collectContratoData());
}

function inputNoLlamarOnClick() {
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/noLlamar", collectContratoData());
}

function inputModemDevueltoOnClick() {
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/modemDevuelto", collectContratoData());
}

function inputAtencionClienteRellamarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/atencionClienteRellamar", collectContratoData());
}

function inputAtencionClienteSolucionadoOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/atencionClienteSolucionado", collectContratoData());
}

function inputAtencionClienteANTELOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/atencionClienteANTEL", collectContratoData());
}

function inputAtencionClienteComercialOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/atencionClienteComercial", collectContratoData());
}

function inputAtencionClienteCambioClienteOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/atencionClienteCambioCliente", collectContratoData());
}

function inputAtencionClienteSupervisionOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/atencionClienteSupervision", collectContratoData());
}

function inputAtencionClienteCerradoOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/atencionClienteCerrado", collectContratoData());
}

function inputAtencionClienteCambioProveedorOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/atencionClienteCambioProveedor", collectContratoData());
}

function inputAtencionClienteRechazarOnClick() {
	realizarAccionContrato("/LogisticaWEB/RESTFacade/ContratoREST/atencionClienteRechazar", collectContratoData());
}