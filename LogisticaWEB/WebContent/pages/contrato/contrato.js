$(document).ready(function() {
	refinarForm();
	
	$("#selectEmpresa").append("<option value='0'>Seleccione...</option>");
	
	if ($("#selectEmpresa").length > 0) {
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
	}
	
	$("#selectEquipo").append("<option value='0'>Seleccione...</option>");
	
	if ($("#selectEquipo").length > 0) {
		StockMovimientoDWR.listStockByEmpresaId(
			1,
			{
				callback: function(data) {
					var html = "";
					
					for (var i=0; i<data.length; i++) {
						html += "<option value='" + data[i].producto.id + "'>" 
								+ data[i].producto.descripcion + " (" + data[i].cantidad + ")"
							+ "</option>";
					}
					
					$("#selectEquipo").append(html);
				}, async: false
			}
		);
	}
	
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
					$("#divFechaVenta").html(
						data.fechaVenta != null ? formatShortDate(data.fechaVenta) : "&nbsp;"
					);
					$("#divLocalidad").html(
						data.localidad != null ? data.localidad : "&nbsp;"
					);
					$("#divCodigoPostal").html(
						data.codigoPostal != null ? data.codigoPostal : "&nbsp;"
					);
					$("#divFechaVencimiento").html(
						data.fechaFinContrato != null ? formatShortDate(data.fechaFinContrato) : "&nbsp;"
					);
					$("#divNumeroContrato").html(
						data.numeroContrato != null ? data.numeroContrato : "&nbsp;"
					);
					$("#inputPlan").length > 0 ? 
						$("#inputPlan").val(data.tipoContratoDescripcion) : 
						(data.tipoContratoDescripcion != null ? 
							$("#divPlan").html(data.tipoContratoDescripcion) : 
							$("#divPlan").html("&nbsp;"));
					$("#inputNuevoPlan").length > 0 ? 
						$("#inputNuevoPlan").val(data.nuevoPlan) : 
						(data.nuevoPlan != null ? 
							$("#divNuevoPlan").html(data.nuevoPlan) : 
							$("#divNuevoPlan").html("&nbsp;"));
					$("#selectEquipo").length > 0 ? 
						(data.producto != null ? 
							$("#selectEquipo").val(data.producto.id) : 
							$("#selectEquipo").val(0)) :
						(data.producto != null ? 
							$("#divEquipo").html(data.producto.descripcion) : 
							$("#divEquipo").html("&nbsp;"));
					$("#inputNumeroSerie").length > 0 ? 
						$("#inputNumeroSerie").val(data.numeroSerie) : 
						(data.numeroSerie != null ? 
							$("#divNumeroSerie").html(data.numeroSerie) : 
							$("#divNumeroSerie").html("&nbsp;"));
					$("#inputDocumento").length > 0 ? 
						$("#inputDocumento").val(data.documento) : 
						(data.documento != null ? 
							$("#divDocumento").html(data.documento) : 
							$("#divDocumento").html("&nbsp;"));
					$("#inputNombre").length > 0 ? 
						$("#inputNombre").val(data.nombre) : 
						(data.nombre != null ? 
							$("#divNombre").html(data.nombre) : 
							$("#divNombre").html("&nbsp;"));
					$("#inputNumeroFactura").length > 0 ? 
						$("#inputNumeroFactura").val(data.numeroFactura) : 
						(data.numeroFactura != null ? 
							$("#divNumeroFactura").html(data.numeroFactura) : 
							$("#divNumeroFactura").html("&nbsp;"));
					$("#inputDireccionFactura").length > 0 ? 
						$("#inputDireccionFactura").val(data.direccionFactura) : 
						(data.direccionFactura != null ? 
							$("#divDireccionFactura").html(data.direccionFactura) : 
							$("#divDireccionFactura").html("&nbsp;"));
					$("#inputDireccionEntrega").length > 0 ? 
						$("#inputDireccionEntrega").val(data.direccionEntrega) : 
						(data.direccionEntrega != null ? 
							$("#divDireccionEntrega").html(data.direccionEntrega) : 
							$("#divDireccionEntrega").html("&nbsp;"));
					$("#inputTelefonoContacto").length > 0 ? 
						$("#inputTelefonoContacto").val(data.telefonoContacto) : 
						(data.telefonoContrato != null ? 
							$("#divTelefonoContacto").html(data.telefonoContacto) : 
							$("#divTelefonoContacto").html("&nbsp;"));
					$("#inputEmail").length > 0 ? 
						$("#inputEmail").val(data.email) : 
						(data.email != null ? 
							$("#divEmail").html(data.email) : 
							$("#divEmail").html("&nbsp;"));
					$("#inputPrecio").length > 0 ? 
						$("#inputPrecio").val(data.precio) : 
						(data.precio != null ? 
							$("#divPrecio").html(data.precio) : 
							$("#divPrecio").html("&nbsp;"));
					if (data.zona != null) {
						if ($("#selectDepartamento").length > 0) {
							$("#selectDepartamento").val(data.zona.departamento.id);
							
							$("#selectZona").val(data.zona.id);
						} else {
							$("#divDepartamento").html(data.zona.departamento.nombre);
							$("#divZona").html(data.zona.nombre);
						}
					}
					if (data.turno != null) {
						if ($("#selectTurno").length > 0) {
							$("#selectTurno").val(data.turno.id);
						} else {
							$("#divTurno").html(data.turno.nombre);
						}
					}
					if (data.fechaEntrega != null) {
						if ($("#selectFechaEntrega").length > 0) {
							$("#selectFechaEntrega > option:first").after(
								"<option value='" + formatShortDate(data.fechaEntrega) + "'>" 
									+ formatShortDate(data.fechaEntrega) 
								+ "</option>"
							);
							$("#selectFechaEntrega").val(formatShortDate(data.fechaEntrega));
						} else {
							$("#divFechaEntrega").html(formatShortDate(data.fechaEntrega));
						}
					}
					$("#inputFechaActivarEn").length > 0 ? 
						(data.fechaActivarEn != null ?
							$("#inputFechaActivarEn").val(formatShortDate(data.fechaActivarEn)) :
							$("#inputFechaActivarEn").val("")) : 
						(data.fechaActivarEn != null ? 
							$("#divFechaActivarEn").html(formatShortDate(data.fechaActivarEn)) : 
							$("#divFechaActivarEn").html("&nbsp;"));
					$("#textareaObservaciones").length > 0 ? 
						$("#textareaObservaciones").val(data.observaciones) : 
							$("#divObservaciones").html(data.observaciones);
					
					if ($("#inputNuevoPlan").length > 0) {
						$("#inputNuevoPlan").focus();
					}
					
					if (data.empresa != null) {
						$("#divEmpresa").attr("eid", data.empresa.id);
					}
					
					if (data.rol != null) {
						$("#divRol").attr("rid", data.rol.id);
					}
					
					if (data.usuario != null) {
						$("#divUsuario").attr("uid", data.usuario.id);
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
		$("#divInputTerminar").hide();
		$("#divInputAgendarActivacion").hide();
		$("#divInputFaltaDocumentacion").hide();
		
		$("#divEmpresa").html("&nbsp;");
		$("#divLabelEmpresa").hide();
		$("#divEmpresa").hide();
		
		$("#divPlan").html("&nbsp;");
		$("#divNuevoPlan").html("&nbsp;");
		$("#divEquipo").html("&nbsp;");
		$("#divNumeroSerie").html("&nbsp;");
		$("#divDocumento").html("&nbsp;");
		$("#divNombre").html("&nbsp;");
		$("#divNumeroFactura").html("&nbsp;");
		$("#divDireccionFactura").html("&nbsp;");
		$("#divDireccionEntrega").html("&nbsp;");
		$("#divTelefonoContacto").html("&nbsp;");
		$("#divEmail").html("&nbsp;");
		$("#divPrecio").html("&nbsp;");
		$("#divDepartamento").html("&nbsp;");
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
		$("#divInputTerminar").hide();
		
		$("#divLabelEmpresa").addClass("requiredFormLabel");
		$("#divLabelMid").addClass("requiredFormLabel");
		
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonSingleSize");
	} else if (mode == __FORM_MODE_VENTA) {
		$("#divInputDistribuir").hide();
		$("#divInputRedistribuir").hide();
		$("#divInputReagendar").hide();
		$("#divInputActivar").hide();
		$("#divInputNoFirma").hide();
		$("#divInputRecoordinar").hide();
		$("#divInputAgendarActivacion").hide();
		$("#divInputTerminar").hide();
		$("#divInputFaltaDocumentacion").hide();
		
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
		
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
		$("#divLabelDireccionFactura").addClass("requiredFormLabel");
		$("#divLabelDireccionEntrega").addClass("requiredFormLabel");
		$("#divLabelTelefonoContacto").addClass("requiredFormLabel");
		$("#divLabelEmail").addClass("requiredFormLabel");
		$("#divLabelDepartamento").addClass("requiredFormLabel");
		$("#divLabelZona").addClass("requiredFormLabel");
		$("#divLabelTurno").addClass("requiredFormLabel");
		$("#divLabelFechaEntrega").addClass("requiredFormLabel");
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleQuintupleSize");
	} else if (mode == __FORM_MODE_BACKOFFICE) {
		$("#divInputAgendar").hide();
		$("#divInputRechazar").hide();
		$("#divInputPosponer").hide();
		$("#divInputRedistribuir").hide();
		$("#divInputActivar").hide();
		$("#divInputNoFirma").hide();
		$("#divInputRecoordinar").hide();
		$("#divInputAgendarActivacion").hide();
		$("#divInputTerminar").hide();
		$("#divInputFaltaDocumentacion").hide();
		
		$("#divEmpresa").html("&nbsp;");
		$("#divLabelEmpresa").hide();
		$("#divEmpresa").hide();
		
		$("#divFechaAtivarEn").html("&nbsp;");
		$("#divLabelFechaActivarEn").hide();
		$("#divFechaActivarEn").hide();
		
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
		$("#divLabelDireccionFactura").addClass("requiredFormLabel");
		$("#divLabelDireccionEntrega").addClass("requiredFormLabel");
		$("#divLabelTelefonoContacto").addClass("requiredFormLabel");
		$("#divLabelEmail").addClass("requiredFormLabel");
		$("#divLabelDepartamento").addClass("requiredFormLabel");
		$("#divLabelZona").addClass("requiredFormLabel");
		$("#divLabelTurno").addClass("requiredFormLabel");
		$("#divLabelFechaEntrega").addClass("requiredFormLabel");
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		
		$("#divLabelNumeroFactura").addClass("requiredFormLabel");
		$("#divLabelNumeroSerie").addClass("requiredFormLabel");
		
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleFourfoldSize");
	} else if (mode == __FORM_MODE_RECOORDINACION) {
		$("#divInputAgendar").hide();
		$("#divInputRechazar").hide();
		$("#divInputPosponer").hide();
		$("#divInputDistribuir").hide();
		$("#divInputActivar").hide();
		$("#divInputNoFirma").hide();
		$("#divInputRecoordinar").hide();
		$("#divInputAgendarActivacion").hide();
		$("#divInputTerminar").hide();
		$("#divInputFaltaDocumentacion").hide();
		
		$("#divEmpresa").html("&nbsp;");
		$("#divLabelEmpresa").hide();
		$("#divEmpresa").hide();
		
		$("#divFechaAtivarEn").html("&nbsp;");
		$("#divLabelFechaActivarEn").hide();
		$("#divFechaActivarEn").hide();
		
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
		$("#divLabelDireccionFactura").addClass("requiredFormLabel");
		$("#divLabelDireccionEntrega").addClass("requiredFormLabel");
		$("#divLabelTelefonoContacto").addClass("requiredFormLabel");
		$("#divLabelEmail").addClass("requiredFormLabel");
		$("#divLabelDepartamento").addClass("requiredFormLabel");
		$("#divLabelZona").addClass("requiredFormLabel");
		$("#divLabelTurno").addClass("requiredFormLabel");
		$("#divLabelFechaEntrega").addClass("requiredFormLabel");
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		
		$("#divLabelNumeroFactura").addClass("requiredFormLabel");
		$("#divLabelNumeroSerie").addClass("requiredFormLabel");
		
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleFourfoldSize");
	} else if (mode == __FORM_MODE_DISTRIBUCION) {
		$("#divInputAgendar").hide();
		$("#divInputRechazar").hide();
		$("#divInputPosponer").hide();
		$("#divInputDistribuir").hide();
		$("#divInputRedistribuir").hide();
		$("#divInputTelelink").hide();
		$("#divInputRenovo").hide();
		$("#divInputAgendarActivacion").hide();
		$("#divInputTerminar").hide();
		$("#divInputFaltaDocumentacion").hide();
		
		$("#divEmpresa").html("&nbsp;");
		$("#divLabelEmpresa").hide();
		$("#divEmpresa").hide();
		
		$("#divFechaAtivarEn").html("&nbsp;");
		$("#divLabelFechaActivarEn").hide();
		$("#divFechaActivarEn").hide();
		
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
		$("#divLabelDireccionFactura").addClass("requiredFormLabel");
		$("#divLabelDireccionEntrega").addClass("requiredFormLabel");
		$("#divLabelTelefonoContacto").addClass("requiredFormLabel");
		$("#divLabelEmail").addClass("requiredFormLabel");
		$("#divLabelDepartamento").addClass("requiredFormLabel");
		$("#divLabelZona").addClass("requiredFormLabel");
		$("#divLabelTurno").addClass("requiredFormLabel");
		$("#divLabelFechaEntrega").addClass("requiredFormLabel");
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		
		$("#divLabelNumeroFactura").addClass("requiredFormLabel");
		$("#divLabelNumeroSerie").addClass("requiredFormLabel");
		
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleFourfoldSize");
	} else if (mode == __FORM_MODE_ACTIVACION) {
		$("#divInputAgendar").hide();
		$("#divInputRechazar").hide();
		$("#divInputPosponer").hide();
		$("#divInputDistribuir").hide();
		$("#divInputRedistribuir").hide();
		$("#divInputTelelink").hide();
		$("#divInputRenovo").hide();
		$("#divInputActivar").hide();
		$("#divInputNoFirma").hide();
		$("#divInputRecoordinar").hide();
		
		$("#divEmpresa").html("&nbsp;");
		$("#divLabelEmpresa").hide();
		$("#divEmpresa").hide();
		
		$("#divLabelDocumento").addClass("requiredFormLabel");
		$("#divLabelNombre").addClass("requiredFormLabel");
		$("#divLabelDireccionFactura").addClass("requiredFormLabel");
		$("#divLabelDireccionEntrega").addClass("requiredFormLabel");
		$("#divLabelTelefonoContacto").addClass("requiredFormLabel");
		$("#divLabelEmail").addClass("requiredFormLabel");
		$("#divLabelDepartamento").addClass("requiredFormLabel");
		$("#divLabelZona").addClass("requiredFormLabel");
		$("#divLabelTurno").addClass("requiredFormLabel");
		$("#divLabelFechaEntrega").addClass("requiredFormLabel");
		$("#divLabelNuevoPlan").addClass("requiredFormLabel");
		
		$("#divLabelNumeroFactura").addClass("requiredFormLabel");
		$("#divLabelNumeroSerie").addClass("requiredFormLabel");
		
		$("#divButtonTitleFourfoldSize").attr("id", "divButtonTitleFourfoldSize");
	}
}

function selectDepartamentoOnChange() {
	$("#selectZona > option:gt(0)").remove();
	$("#selectTurno > option:gt(0)").remove();
	$("#selectFechaEntrega > option:gt(0)").remove();
	
	ZonaDWR.listByDepartamentoId(
		$("#selectDepartamento").val(),
		{
			callback: function(data) {
				$("#selectZona > option:gt(0)").remove();
				
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectZona").append(html);
				
				selectZonaOnChange();
			}, async: false
		}
	);
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
			id: 1
		},
		{
			id: $("#selectZona").val(),
			departamento: {
				id: $("#selectDepartamento").val(),
				nombre: $("#selectDepartamento").text()
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
				for (var j=0; j<availability.length; j++) {
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
		fechaVenta: $("#divFechaVenta").text().trim() != "" ? parseShortDate($("#divFechaVenta").text().trim()) : null,
		tipoContratoDescripcion: $("#divPlan").text().trim() != "" ? $("#divPlan").text().trim() : null,
		fechaFinContrato: $("#divFechaVencimiento").text().trim() != "" ? parseShortDate($("#divFechaVencimiento").text().trim()) : null,
		tipoContratoDescripcion: $("#inputPlan").length > 0 ? 
			($("#inputPlan").val().trim() != "" ? $("#inputPlan").val().trim() : null) : 
				$("#divPlan").text().trim(),
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
//			$("#selectBackoffice").val().trim();
		observaciones: $("#textareaObservaciones").length > 0 ?
			($("#textareaObservaciones").val().trim() != "" ? $("#textareaObservaciones").val().trim() : null) :
				$("#divObservaciones").text().trim()
	};
	
	if ($("#selectEquipo").val() != "0") {
		contrato.producto = {
			id: $("#selectEquipo").val()
		};
	}
	
	if ($("#selectDepartamento").val() != "0" && $("#selectZona").val() != "0") {
		contrato.zona = {
			id: $("#selectZona").val(),
			departamento: {
				id: $("#selectDepartamento").val()
			}
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
		ContratoRoutingHistoryDWR.addAsignacionManual(
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
	
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.agendar(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}

function inputRechazarOnClick() {
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.rechazar(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}

function inputPosponerOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.posponer(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}

function inputDistribuirOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.distribuir(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}

function inputRedistribuirOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.redistribuir(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}

function inputTelelinkOnClick() {
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.telelink(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}

function inputRenovoOnClick() {
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.renovo(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}

function inputReagendarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.reagendar(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}

function inputActivarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.activar(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}

function inputNoFirmaOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.noFirma(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}

function inputRecoordinarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.recoordinar(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}

function inputAgendarActivacionOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.agendarActivacion(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}

function inputTerminarOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.terminar(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}

function inputFaltaDocumentacionOnClick() {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var contratoRoutingHistory = {
		id: crhid,
		contrato: collectContratoData(),
		fecha: new Date()
	};
	
	ContratoRoutingHistoryDWR.faltaDocumentacion(
		contratoRoutingHistory,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}