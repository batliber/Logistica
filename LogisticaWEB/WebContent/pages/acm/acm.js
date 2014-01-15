var accionesHabilitadas = false;

var estados = [
	"No procesado", 
	"En proceso", 
	"Procesado", 
	"Lista vacia", 
	"Para procesar", 
	"Prioritario", 
	"Lista negra"
];

var filtros = 0;
var ordenaciones = 0;

var ordenSufijos = [ "NOO", "ASC", "DES" ];

var ordenes = {};

var campos = {
	tdPrepagoMid: "mid",
	tdPrepagoMesAno: "mesAno",
	tdPrepagoMontoMesActual: "montoMesActual",
	tdPrepagoMontoMesAnterior1: "montoMesAnterior1",
	tdPrepagoMontoMesAnterior2: "montoMesAnterior2",
	tdPrepagoMontoPromedio: "montoPromedio",
	tdPrepagoFechaExportacion: "fechaExportacion",
	tdPrepagoFechaActivacionKit: "fechaActivacionKit",
	tdPrepagoFact: "fact",
	tdContratoMid: "mid",
	tdContratoFinContrato: "fechaFinContrato",
	tdContratoTipoContratoDescripcion: "tipoContratoDescripcion",
	tdContratoDocumento: "documento",
	tdContratoNumeroCliente: "numeroCliente",
	tdContratoNumeroContrato: "numeroContrato",
	tdContratoNombre: "nombre",
	tdContratoDireccion: "direccion",
	tdContratoCodigoPostal: "codigoPostal",
	tdContratoLocalidad: "localidad",
	tdContratoEquipo: "equipo",
	tdContratoAgente: "agente",
	tdContratoFechaExportacion: "fechaExportacion",
	tdContratoFact: "fact",
	tdListaNegraMid: "mid",
	tdListaNegraObservaciones: "observaciones",
	tdListaNegraFact: "fact",
	tdSinDatosMid: "mid",
	tdSinDatosEstado: "estado",
	tdSinDatosFact: "fact"
};

$(document).ready(function() {
	$("#inputExportarAExcel").prop("disabled", true);
	$("#inputExportarSubconjunto").prop("disabled", true);
	$("#inputDeshacerAsignacion").prop("disabled", true);
	$("#inputReprocesar").prop("disabled", true);
	$("#inputReprocesarSubconjunto").prop("disabled", true);
	$("#inputListaNegra").prop("disabled", true);
	
	reloadData();
});

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
			$("#inputDeshacerAsignacion").prop("disabled", true);
			$("#inputReprocesar").prop("disabled", true);
			$("#inputReprocesarSubconjunto").prop("disabled", true);
			$("#inputListaNegra").prop("disabled", true);
			
			$("#inputHabilitarAcciones").val("Habilitar acciones");
		} else {
			$("#inputExportarAExcel").prop("disabled", false);
			$("#inputExportarSubconjunto").prop("disabled", false);
			$("#inputDeshacerAsignacion").prop("disabled", false);
			$("#inputReprocesar").prop("disabled", false);
			$("#inputReprocesarSubconjunto").prop("disabled", false);
			$("#inputListaNegra").prop("disabled", false);
			
			$("#inputHabilitarAcciones").val("Deshabilitar acciones");
		}
		
		accionesHabilitadas = !accionesHabilitadas;
	} else {
		accionesHabilitadas = habilitar;
		
		$("#inputExportarAExcel").prop("disabled", !habilitar);
		$("#inputExportarSubconjunto").prop("disabled", !habilitar);
		$("#inputDeshacerAsignacion").prop("disabled", !habilitar);
		$("#inputReprocesar").prop("disabled", !habilitar);
		$("#inputReprocesarSubconjunto").prop("disabled", !habilitar);
		$("#inputListaNegra").prop("disabled", !habilitar);
		
		$("#inputHabilitarAcciones").val(habilitar ? "Deshabilitar acciones" : "Habilitar acciones" );
	}
}

function selectTipoRegistroOnChange() {
	$("#divContratos").hide();
	$("#divPrepagos").hide();
	$("#divListaNegra").hide();
	$("#divSinDatos").hide();
	
	habilitarAcciones(false, true);
	
	var divFiltros = $(".divFiltro");
	for (var i=0; i<divFiltros.length; i++) {
		divFiltros[i].remove();
	}
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		$("#divContratos").show();
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		$("#divPrepagos").show();
	} else if ($("#selectTipoRegistro").val() == "listaNegra") {
		$("#divListaNegra").show();
	} else {
		$("#divSinDatos").show();
	}
	
	reloadData();
}

function inputTamanoMuestraOnChange(event) {
	reloadData();
}

function reloadData() {
	if ($("#selectTipoRegistro").val() == "contrato") {
		ACMInterfaceContratoDWR.list(
			calcularMetadataConsulta(),
			{
				callback: function(data) {
					$("#tableContratos > tbody:last > tr").remove();
					
					for (var i=0; i<data.registrosMuestra.length; i++) {
						var registroMuestra = data.registrosMuestra[i];
						
						$("#tableContratos > tbody:last").append(
							"<tr id='" + registroMuestra.mid + "'>"
								+ "<td class='tdContratoMid'><div class='divContratoMid'>" 
									+ registroMuestra.mid 
								+ "</div></td>"
								+ "<td class='tdContratoFinContrato'><div class='divContratoFinContrato'>" 
									+ (registroMuestra.fechaFinContrato != null ? 
										formatShortDate(registroMuestra.fechaFinContrato) : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdContratoTipoContratoDescripcion'><div class='divContratoTipoContratoDescripcion' title='" 
									+ registroMuestra.tipoContratoCodigo + "'>" 
									+ (registroMuestra.tipoContratoDescripcion != null ? 
										registroMuestra.tipoContratoDescripcion : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdContratoDocumento'><div class='divContratoDocumento' title='"
									+ registroMuestra.documentoTipo + "'>" 
									+ (registroMuestra.documento != null ?
										registroMuestra.documento : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdContratoNumeroCliente'><div class='divContratoNumeroCliente' title='" 
									+ (registroMuestra.numeroCliente != null ?
										registroMuestra.numeroCliente : "&nbsp;") + "'>" 
									+ (registroMuestra.numeroCliente != null ?
										registroMuestra.numeroCliente : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdContratoNumeroContrato'><div class='divContratoNumeroContrato' title='" 
									+ (registroMuestra.numeroContrato != null ?
										registroMuestra.numeroContrato : "&nbsp;") + "'>" 
									+ (registroMuestra.numeroContrato != null ?
										registroMuestra.numeroContrato : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdContratoNombre'><div class='divContratoNombre' title='" 
									+ (registroMuestra.nombre != null ?
										registroMuestra.nombre : "&nbsp;") + "'>" 
									+ (registroMuestra.nombre != null ?
										registroMuestra.nombre : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdContratoDireccion'><div class='divContratoDireccion' title='" 
									+ (registroMuestra.direccion != null ?
										registroMuestra.direccion : "&nbsp;") + "'>"
									+ (registroMuestra.direccion != null ?
										registroMuestra.direccion : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdContratoCodigoPostal'><div class='divContratoCodigoPostal'>" 
									+ (registroMuestra.codigoPostal != null ?
										registroMuestra.codigoPostal : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdContratoLocalidad'><div class='divContratoLocalidad' title='" 
									+ (registroMuestra.localidad != null ?
										registroMuestra.localidad : "&nbsp;") + "'>" 
									+ (registroMuestra.localidad != null ?
										registroMuestra.localidad : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdContratoEquipo'><div class='divContratoEquipo' title='" 
									+ (registroMuestra.equipo != null ?
											registroMuestra.equipo : "&nbsp;") + "'>" 
									+ (registroMuestra.equipo != null ?
										registroMuestra.equipo : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdContratoAgente'><div class='divContratoAgente' title='"
									+ (registroMuestra.agente != null ?
										registroMuestra.agente : "&nbsp;") + "'>" 
									+ (registroMuestra.agente != null ?
										registroMuestra.agente : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdContratoFechaExportacion'><div class='divContratoFechaExportacion'>" 
									+ (registroMuestra.fechaExportacion != null ?
										formatLongDate(registroMuestra.fechaExportacion) : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdContratoFact'><div class='divContratoFact'>" 
									+ formatLongDate(registroMuestra.fact)
								+ "</div></td>"
							+ "</tr>"
						);
					}
					
					$("#divContratoCantidadRegistros").text(data.cantidadRegistros);
					
					$("#inputHabilitarAcciones").prop("disabled", false);
				}, async: false
			}
		);
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		ACMInterfacePrepagoDWR.list(
			calcularMetadataConsulta(),
			{
				callback: function(data) {
					$("#tablePrepagos > tbody:last > tr").remove();
					
					for (var i=0; i<data.registrosMuestra.length; i++) {
						var registroMuestra = data.registrosMuestra[i];
						
						$("#tablePrepagos > tbody:last").append(
							"<tr id='" + registroMuestra.mid + "'>"
								+ "<td class='tdPrepagoMid'><div class='divPrepagoMid'>" 
									+ registroMuestra.mid 
								+ "</div></td>"
								+ "<td class='tdPrepagoMesAno'><div class='divPrepagoMesAno'>" 
									+ (registroMuestra.mesAno != null ?
										formatMonthYearDate(registroMuestra.mesAno) : "&nbsp;") 
								+ "</div></td>"
								+ "<td class='tdPrepagoMontoMesActual'><div class='divPrepagoMontoMesActual'>" 
									+ formatDecimal(registroMuestra.montoMesActual, 2) 
								+ "</div></td>"
								+ "<td class='tdPrepagoMontoMesAnterior1'><div class='divPrepagoMontoMesAnterior1'>" 
									+ formatDecimal(registroMuestra.montoMesAnterior1, 2) 
								+ "</div></td>"
								+ "<td class='tdPrepagoMontoMesAnterior2'><div class='divPrepagoMontoMesAnterior2'>" 
									+ formatDecimal(registroMuestra.montoMesAnterior2, 2)
								+ "</div></td>"
								+ "<td class='tdPrepagoMontoPromedio'><div class='divPrepagoMontoPromedio'>" 
									+ formatDecimal(registroMuestra.montoPromedio, 2)
								+ "</div></td>"
								+ "<td class='tdPrepagoFechaExportacion'><div class='divPrepagoFechaExportacion'>" 
									+ (registroMuestra.fechaExportacion != null ?
										formatLongDate(registroMuestra.fechaExportacion) : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdPrepagoFechaActivacionKit'><div class='divPrepagoFechaActivacionKit'>" 
									+ (registroMuestra.fechaActivacionKit != null ?
										formatShortDate(registroMuestra.fechaActivacionKit) : "&nbsp;")
								+ "</div></td>"
								+ "<td class='tdPrepagoFact'><div class='divPrepagoFact'>" 
									+ formatLongDate(registroMuestra.fact)
								+ "</div></td>"
							+ "</tr>"
						);
					}
					
					$("#divPrepagoCantidadRegistros").text(data.cantidadRegistros);
					
					$("#inputHabilitarAcciones").prop("disabled", false);
				}, async: false
			}
		);
	} else if ($("#selectTipoRegistro").val() == "listaNegra") {
		ACMInterfaceListaNegraDWR.list(
			calcularMetadataConsulta(),
			{
				callback: function(data) {
					$("#tableListaNegra > tbody:last > tr").remove();
					
					for (var i=0; i<data.registrosMuestra.length; i++) {
						var registroMuestra = data.registrosMuestra[i];
						
						$("#tableListaNegra > tbody:last").append(
							"<tr id='" + registroMuestra.mid + "'>"
								+ "<td class='tdListaNegraMid'><div class='divListaNegraMid'>" 
									+ registroMuestra.mid 
								+ "</div></td>"
								+ "<td class='tdListaNegraObservaciones'><div class='divListaNegraObservaciones'>" 
									+ (registroMuestra.observaciones != null ?
										registroMuestra.observaciones : "&nbsp;") 
								+ "</div></td>"
								+ "<td class='tdListaNegraFact'><div class='divListaNegraFact'>" 
									+ formatLongDate(registroMuestra.fact)
								+ "</div></td>"
							+ "</tr>"
						);
					}
					
					$("#divListaNegraCantidadRegistros").text(data.cantidadRegistros);
					
					$("#inputHabilitarAcciones").prop("disabled", true);
				}, async: false
			}
		);
	} else {
		ACMInterfaceMidDWR.listSinDatos(
			calcularMetadataConsulta(),
			{
				callback: function(data) {
					$("#tableSinDatos > tbody:last > tr").remove();
					
					for (var i=0; i<data.registrosMuestra.length; i++) {
						var registroMuestra = data.registrosMuestra[i];
						
						$("#tableSinDatos > tbody:last").append(
							"<tr id='" + registroMuestra.mid + "'>"
								+ "<td class='tdSinDatosMid'><div class='divSinDatosMid'>" 
									+ registroMuestra.mid 
								+ "</div></td>"
								+ "<td class='tdSinDatosEstado'><div class='divSinDatosEstado'>" 
									+ estados[registroMuestra.estado]
								+ "</div></td>"
								+ "<td class='tdSinDatosFact'><div class='divSinDatosFact'>"
									+ (registroMuestra.fact != null ?  
										formatLongDate(registroMuestra.fact)
										: "&nbsp;")
								+ "</div></td>"
							+ "</tr>"
						);
					}
					
					$("#divSinDatosCantidadRegistros").text(data.cantidadRegistros);
					
					$("#inputHabilitarAcciones").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function calcularMetadataConsulta() {
	var metadataConsulta = {
		tamanoMuestra: $("#inputTamanoMuestra").val(),
	};
	
	metadataConsulta.metadataOrdenaciones = calcularOrdenaciones();
	metadataConsulta.metadataCondiciones = calcularCondiciones();
	
	return metadataConsulta;
}

function calcularOrdenaciones() {
	var result = [];
	
	for (var campo in ordenes) {
		if ((campo.toLowerCase().indexOf($("#selectTipoRegistro").val().toLowerCase()) > 0)
			&& (ordenes[campo] > 0)) {
			result[result.length] = {
				campo: campos[campo],
				ascendente: (ordenes[campo] == 1)
			};
		}
	}
	
	return result;
}

function calcularCondiciones() {
	var result = [];
	
	for (var i=1; i<=filtros; i++) {
		if (($("#divFiltro" + i).length > 0) 
			&& ($("#selectCampo" + i).val() != "")
			&& ($("#selectCondicion" + i).length > 0)
			&& ($("#selectCondicion" + i).val() != "")) {
			var metadataCondicion = {
				campo: $("#selectCampo" + i).val(),
				operador: $("#selectCondicion" + i).val()
			};
			
			var filtroValido = false;
			switch ($("#selectCondicion" + i).val()) {
				case "btw":
					var valorMin = $("#inputValorMin" + i).val();
					var valorMax = $("#inputValorMax" + i).val();
					
					metadataCondicion.valores = [valorMin, valorMax];
					
					filtroValido = 
						metadataCondicion.valores[0] != "" 
							&& metadataCondicion.valores[1] != "";
					
					break;
				case "nl":
				case "nnl":
					metadataCondicion.valores = [];
					
					filtroValido = true;
					
					break;
				case "in":
					var valoresMultiples = $(("#divFiltro" + i) + " .divValorMultiple");
					
					metadataCondicion.valores = [];
					for (var j=0; j<valoresMultiples.length; j++) {
						metadataCondicion.valores[metadataCondicion.valores.length] = 
							valoresMultiples[j].innerHTML;
					}
					
					filtroValido = true;
					
					break;
				case "gt":
				case "lt":
				case "like":
				case "nlike":
				case "eq": 
				default:
					metadataCondicion.valores = [$("#inputValor" + i).val()];

					filtroValido = metadataCondicion.valores[0] != "";
				
					break;
			}
			
			if (filtroValido) {
				if (metadataCondicion.valores != null) {
					for (var j = 0; j < metadataCondicion.valores.length; j++) {
						if (metadataCondicion.valores[j].indexOf("/") > 0) {
							metadataCondicion.valores[j] = 
								(metadataCondicion.valores[j] + " 00:00")
									.substring(0, 16);
						}
					}
				}
				
				result[result.length] = metadataCondicion;
			}
		}
	}
	
	return result;
}

function inputAgregarFiltroOnClick(event) {
	filtros++;
	
	var html = 
		"<div id='divFiltro" + filtros + "' class='divFiltro'>"
		+ "<div id='divQuitarFiltro" + filtros + "' class='divQuitarFiltro'>"
			+ "<input type='submit' id='inputQuitarFiltro" + filtros + "' value='Quitar'"
				+ " onclick='javascript:inputQuitarFiltroOnClick(event, this, " + filtros + ")'/>"
		+ "</div>"
		+ "<div class='divFormLabel'>Campo:</div>";
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		html += 
			"<div id='divCampoContrato" + filtros + "' style='float: left;'>"
				+ "<select id='selectCampo" + filtros + "' onchange='javascript:selectCampoOnChange(event, this, " + filtros + ")'>"
					+ "<option value=''>Seleccione...</option>"
					+ "<option value='mid'>MID</option>"
					+ "<option value='fechaFinContrato'>Fin de contrato</option>"
					+ "<option value='tipoContratoDescripcion'>Tipo de contrato</option>"
					+ "<option value='documentoTipo'>Tipo de documento</option>"
					+ "<option value='documento'>Documento</option>"
					+ "<option value='numeroCliente'>N&uacute;mero de cliente</option>"
					+ "<option value='numeroContrato'>N&uacute;mero de contrato</option>"
					+ "<option value='nombre'>Nombre</option>"
					+ "<option value='direccion'>Direcci&oacute;n</option>"
					+ "<option value='codigoPostal'>Código postal</option>"
					+ "<option value='localidad'>Localidad</option>"
					+ "<option value='equipo'>Equipo</option>"
					+ "<option value='agente'>Agente</option>"
					+ "<option value='fechaExportacion'>Asignado</option>"
					+ "<option value='fact'>Obtenido</option>"
				+ "</select>"
			+ "</div>";
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		html += 
			"<div id='divCampoPrepago" + filtros + "' style='float: left;'>"
				+ "<select id='selectCampo" + filtros + "' onchange='javascript:selectCampoOnChange(event, this, " + filtros + ")'>"
					+ "<option value=''>Seleccione...</option>"
					+ "<option value='mid'>MID</option>"
					+ "<option value='mesAno'>Mes/A&ntilde;o</option>"
					+ "<option value='montoMesActual'>Monto mes actual</option>"
					+ "<option value='montoMesAnterior1'>Monto mes ant. 1</option>"
					+ "<option value='montoMesAnterior2'>Monto mes ant. 2</option>"
					+ "<option value='montoPromedio'>Monto promedio</option>"
					+ "<option value='fechaExportacion'>Asignado</option>"
					+ "<option value='fechaActivacionKit'>Activaci&oacute;n</option>"
					+ "<option value='fact'>Obtenido</option>"
				+ "</select>"
			+ "</div>";
	} else if ($("#selectTipoRegistro").val() == "listaNegra") {
		html += 
			"<div id='divCampoListaNegra" + filtros + "' style='float: left;'>"
				+ "<select id='selectCampo" + filtros + "' onchange='javascript:selectCampoOnChange(event, this, " + filtros + ")'>"
					+ "<option value=''>Seleccione...</option>"
					+ "<option value='mid'>MID</option>"
					+ "<option value='observaciones'>Observaciones</option>"
					+ "<option value='fact'>Ingresado</option>"
				+ "</select>"
			+ "</div>";
	} else {
		html += 
			"<div id='divCampoSinDatos" + filtros + "' style='float: left;'>"
				+ "<select id='selectCampo" + filtros + "' onchange='javascript:selectCampoOnChange(event, this, " + filtros + ")'>"
					+ "<option value=''>Seleccione...</option>"
					+ "<option value='mid'>MID</option>"
					+ "<option value='estado'>Estado</option>"
					+ "<option value='fact'>Modificado</option>"
				+ "</select>"
			+ "</div>";
	}
	
	html +=
			"<div class='divFormLabel'>Condici&oacute;n:</div>"
			+ "<div id='divCondicion" + filtros + "' class='divCondicion'>&nbsp;</div>"
		+ "</div>";
	
	$("#divFiltros").append(html);
}

function selectCampoOnChange(event, element, index) {
	var divCondicionValoresAnterior = $("#divCondicionValores" + index);
	
	if (divCondicionValoresAnterior.length > 0) {
		divCondicionValoresAnterior.remove();
	}
	
	var divCondicion = $("#divCondicion" + index);
	
	divCondicion.empty();
	
	var html = 
		"<select id='selectCondicion" + index + "' onchange='javascript:selectCondicionOnChange(event, this, " + index + ")'>"
			+ "<option value=''>Seleccione...</option>";
	
	if (($("#selectCampo" + index).val() == "mid") 
		|| ($("#selectCampo" + index).val() == "numeroCliente")
		|| ($("#selectCampo" + index).val() == "numeroContrato")	
		|| ($("#selectCampo" + index).val() == "codigoPostal")
		|| ($("#selectCampo" + index).val() == "mesAno")
		|| ($("#selectCampo" + index).val() == "montoMesActual")
		|| ($("#selectCampo" + index).val() == "montoMesAnterior1")
		|| ($("#selectCampo" + index).val() == "montoMesAnterior2")
		|| ($("#selectCampo" + index).val() == "montoPromedio")) {
		html += 
			"<option value='eq'>Es igual a</option>"
			+ "<option value='btw'>Entre</option>"
			+ "<option value='gt'>Mayor que</option>"
			+ "<option value='lt'>Menor que</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else if (
		($("#selectCampo" + index).val() == "documento")
		|| ($("#selectCampo" + index).val() == "nombre")
		|| ($("#selectCampo" + index).val() == "direccion")
		|| ($("#selectCampo" + index).val() == "localidad")
		|| ($("#selectCampo" + index).val() == "equipo")
		|| ($("#selectCampo" + index).val() == "agente")
		|| ($("#selectCampo" + index).val() == "observaciones")) {
		html += 
			"<option value='like'>Contiene</option>"
			+ "<option value='nlike'>No contiene</option>"
			+ "<option value='eq'>Es igual a</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else if (
		($("#selectCampo" + index).val() == "tipoContratoDescripcion")) {
		html += 
			+ "<option value='eq'>Es igual a</option>"
			+ "<option value='in'>Incluido en</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else if (
		($("#selectCampo" + index).val() == "fechaExportacion")
		|| ($("#selectCampo" + index).val() == "fechaFinContrato")
		|| ($("#selectCampo" + index).val() == "fechaActivacionKit")
		|| ($("#selectCampo" + index).val() == "fact")) {
		html += 
			"<option value='eq'>Es igual a</option>"
			+ "<option value='btw'>Entre</option>"
			+ "<option value='gt'>Mayor que</option>"
			+ "<option value='lt'>Menor que</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else if (
		($("#selectCampo" + index).val() == "documentoTipo")
		|| ($("#selectCampo" + index).val() == "estado")) {
		html += 
			"<option value='eq'>Es igual a</option>";
	}
	
	html +=
		+ "</select>";
	
	divCondicion.append(html);
}

function selectCondicionOnChange(event, element, index) {
	var divCondicionValoresAnterior = $("#divCondicionValores" + index);
	
	if (divCondicionValoresAnterior.length > 0) {
		divCondicionValoresAnterior.remove();
	}
	
	var html = "<div id='divCondicionValores" + index + "' class='divCondicionValores'>";
	
	switch ($("#selectCondicion" + index).val()) {
		case "btw":
			html +=
				"<div class='divFormLabel'>Valor:</div>"
				+ "<div id='divValorMin" + index + "' style='float: left'>"
					+ "<input type='text' id='inputValorMin" + index + "' onchange='javascript:inputValorOnChange(event, this)'/>"
				+ "</div>"
				+ "<div class='divFormLabel' style='width: 50px;'>y:</div>"
				+ "<div id='divValorMax" + index + "' style='float: left;'>"
					+ "<input type='text' id='inputValorMax" + index + "' onchange='javascript:inputValorOnChange(event, this)'/>"
				+ "</div>";
			break;
		case "nl":
		case "nnl":
			reloadData();
			
			break;
		case "in":
			if ($("#selectCampo" + index).val() == "tipoContratoDescripcion") {
				ACMInterfaceContratoDWR.listTipoContratos(
					calcularMetadataConsulta(),
					{
						callback: function(data) {
							var html = "<div id='divCondicionValores" + index + "' class='divCondicionValoresMultiples'>";
							
							html += 
								"<div class='divFormLabel'>Valor:</div>"
								+ "<div id='divValor" + index + "' style='float: left;'>"
									+ "<select id='inputValor" + index 
										+ "' onchange='javascript:inputValorMultipleOnChange(event, this, " + index + ")'>"
										+ "<option value=''>Seleccione...</option>"
										+ "<option value='Todos'>Todos</option>"
										+ "<option value='Ninguno'>Ninguno</option>";
							
							for (var i=0; i<data.length; i++) {
								html += 
										"<option value='" + data[i].tipoContratoDescripcion + "'>"
											+ data[i].tipoContratoDescripcion 
										+ "</option>";
							}
							
							html += 
										"</select>"
								+ "</div>";	
							
							html += 
								"<div id='divValores" + index + "' class='divValoresMultiples' style='float: left;'>";
								
							for (var i=0; i<data.length; i++) {
								html += 
									"<div class='divValorMultiple' onclick='javascript:inputValorMultipleOnClick(event, this)'>"
										+ data[i].tipoContratoDescripcion
									+ "</div>";
							}
							
							html +=
								"</div>";
							
							$("#divFiltro" + index).append(html);
						}, async: false
					}
				);
				
				reloadData();
				
				return;
			}
			
			break;
		case "gt":
		case "lt":
		case "like":
		case "nlike":
		case "eq":
		default:
			if ($("#selectCampo" + index).val() == "documentoTipo") {
				html += 
					"<div class='divFormLabel'>Valor:</div>"
					+ "<div id='divValor" + index + "' style='float: left;'>"
						+ "<select id='inputValor" + index + "' onchange='javascript:inputValorOnChange(event, this)'>"
							+ "<option value=''>Seleccione...</option>"
							+ "<option value='1'>Persona</option>"
							+ "<option value='2'>Empresa</option>"
							+ "<option value='3'>Estatal</option>"
							+ "<option value='4'>Otros</option>"
						+ "</select>"
					+ "</div>";				
			} else if ($("#selectCampo" + index).val() == "estado") {
				html += 
					"<div class='divFormLabel'>Valor:</div>"
					+ "<div id='divValor" + index + "' style='float: left;'>"
						+ "<select id='inputValor" + index + "' onchange='javascript:inputValorOnChange(event, this)'>"
							+ "<option value=''>Seleccione...</option>"
							+ "<option value='0'>No procesado</option>"
							+ "<option value='1'>En proceso</option>"
							// + "<option value='2'>Procesado</option>"
							+ "<option value='3'>Lista vac&iacute;a</option>"
							+ "<option value='4'>Para procesar</option>"
							+ "<option value='5'>Para procesar prioritario</option>"
							+ "<option value='6'>Lista negra</option>"
						+ "</select>"
					+ "</div>";				
			} else {
				html += 
					"<div class='divFormLabel'>Valor:</div>"
					+ "<div id='divValor" + index + "' style='float: left;'>"
						+ "<input type='text' id='inputValor" + index + "' onchange='javascript:inputValorOnChange(event, this)'/>"
					+ "</div>";
			}
			break;
	}
	
	html +=
		"</div>";
	
	$("#divFiltro" + index).append(html);
}

function inputValorOnChange(event, element) {
	reloadData();
}

function inputValorMultipleOnChange(event, element, index) {
	if ($("#inputValor" + index).val() == "Todos") {
		var options = $("#inputValor" + index + " option");
		
		for (var i=0; i < options.length; i++) {
			if (i > 2) {
				$("#divValores" + index).append(
					"<div class='divValorMultiple' onclick='javascript:inputValorMultipleOnClick(event, this)'>"
						+ $(options[i]).val()
					+ "</div>"
				);
			}
		}
	} else if ($("#inputValor" + index).val() == "Ninguno") {
		$("#divValores" + index + " div").remove();
	} else {
		$("#divValores" + index).append(
			"<div class='divValorMultiple' onclick='javascript:inputValorMultipleOnClick(event, this)'>"
				+ $("#inputValor" + index).val()
			+ "</div>"
		);
	}
	
	reloadData();
}

function inputValorMultipleOnClick(event, element) {
	$(element).remove();
	
	reloadData();
}

function inputQuitarFiltroOnClick(event, element, index) {
	$("#divFiltro" + index).remove();
	
	reloadData();
}

function tableTheadTdOnClick(event, element) {
	var className = element.getAttribute("class");
	className = className.substring(0, className.length - 3);
	
	if (ordenes[className] != null) {
		ordenes[className] = (ordenes[className] + 1) % ordenSufijos.length;
	} else {
		ordenes[className] = 1;
	}
	
	element.setAttribute("class", className + ordenSufijos[ordenes[className]]);
	
	if (ordenes[className] == 0) {
		ordenes[className] = null;
	}
	
	reloadData();
}

function inputExportarAExcelOnClick(event) {
	if ($("#selectTipoRegistro").val() == "contrato") {
		if (confirm("Se exportarán " + $("#divContratoCantidadRegistros").text() + " registros.")) {
			ACMInterfaceContratoDWR.exportarAExcel(
				calcularMetadataConsulta(),
				{
					callback: function(data) {
						alert("Archivo generado: " + data);
						
						reloadData();
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		if (confirm("Se exportarán " + $("#divPrepagoCantidadRegistros").text()+ " registros.")) {
			ACMInterfacePrepagoDWR.exportarAExcel(
				calcularMetadataConsulta(),
				{
					callback: function(data) {
						alert("Archivo generado: " + data);
						
						reloadData();
					}
				}
			);
		}
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
}

function inputExportarSubconjuntoOnClick(event) {
	var metadataConsulta = calcularMetadataConsulta();
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		metadataConsulta.tamanoSubconjunto = 
			Math.min(
				$("#inputTamanoSubconjunto").val(),
				$("#divContratoCantidadRegistros").text()
			);
		
		if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			ACMInterfaceContratoDWR.exportarAExcel(
				metadataConsulta,
				{
					callback: function(data) {
						alert("Archivo generado: " + data);
						
						reloadData();
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		metadataConsulta.tamanoSubconjunto = 
			Math.min(
				$("#inputTamanoSubconjunto").val(),
				$("#divPrepagoCantidadRegistros").text()
			);
		
		if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			ACMInterfacePrepagoDWR.exportarAExcel(
				metadataConsulta,
				{
					callback: function(data) {
						alert("Archivo generado: " + data);
						
						reloadData();
					}
				}
			);
		}
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
}

function inputDeshacerAsignacionOnClick(event) {
	var metadataConsulta = calcularMetadataConsulta();
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		if (confirm("Se anulará la última asignación.")) {
			ACMInterfaceContratoDWR.deshacerAsignacion(
				metadataConsulta,
				{
					callback: function(data) {
						reloadData();
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		if (confirm("Se anulará la última asignación.")) {
			ACMInterfacePrepagoDWR.deshacerAsignacion(
				metadataConsulta,
				{
					callback: function(data) {
						reloadData();
					}
				}
			);
		}
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
}

function inputReprocesarOnClick(event) {
	var observaciones = null;
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		observaciones = prompt("Se reprocesarán " + $("#divContratoCantidadRegistros").text() + " registros.");
		
		if (observaciones != null) {
			ACMInterfaceContratoDWR.reprocesar(
				calcularMetadataConsulta(),
				observaciones,
				{
					callback: function(data) {
						
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		observaciones = prompt("Se reprocesarán " + $("#divPrepagoCantidadRegistros").text() + " registros.");
		
		if (observaciones != null) {
			ACMInterfacePrepagoDWR.reprocesar(
				calcularMetadataConsulta(),
				observaciones,
				{
					callback: function(data) {
						
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "sinDatos") {
		observaciones = prompt("Se reprocesarán " + $("#divSinDatosCantidadRegistros").text() + " registros.");
		
		if (observaciones != null) {
			ACMInterfaceMidDWR.reprocesarSinDatos(
				calcularMetadataConsulta(),
				observaciones,
				{
					callback: function(data) {
						reloadData();
					}
				}
			);
		}
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
}

function inputReprocesarSubconjuntoOnClick(event) {
	var metadataConsulta = calcularMetadataConsulta();
	var observaciones = null;
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		metadataConsulta.tamanoSubconjunto = 
			Math.min(
				$("#inputTamanoSubconjunto").val(),
				$("#divContratoCantidadRegistros").text()
			);
		
		observaciones = prompt("Se reprocesarán " + metadataConsulta.tamanoSubconjunto + " registros.");
		
		if (observaciones != null) {
			ACMInterfaceContratoDWR.reprocesar(
				metadataConsulta,
				observaciones,
				{
					callback: function(data) {
						
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		metadataConsulta.tamanoSubconjunto = 
			Math.min(
				$("#inputTamanoSubconjunto").val(),
				$("#divPrepagoCantidadRegistros").text()
			);
		
		observaciones = prompt("Se reprocesarán " + metadataConsulta.tamanoSubconjunto + " registros.");
		
		if (observaciones != null) {
			ACMInterfacePrepagoDWR.reprocesar(
				metadataConsulta,
				observaciones,
				{
					callback: function(data) {
						
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "sinDatos") {
		metadataConsulta.tamanoSubconjunto = 
			Math.min(
				$("#inputTamanoSubconjunto").val(),
				$("#divSinDatosCantidadRegistros").text()
			);
		
		observaciones = prompt("Se reprocesarán " + metadataConsulta.tamanoSubconjunto + " registros.");
		
		if (observaciones != null) {
			ACMInterfaceMidDWR.reprocesarSinDatos(
				metadataConsulta,
				observaciones,
				{
					callback: function(data) {
						reloadData();
					}
				}
			);
		}
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
}

function inputListaNegraOnClick(event) {
	var metadataConsulta = calcularMetadataConsulta();
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		if (confirm("Se agregarán " + $("#divContratoCantidadRegistros").text() + " registros a la lista negra.")) {
			ACMInterfaceContratoDWR.agregarAListaNegra(
				metadataConsulta,
				{
					callback: function(data) {
						reloadData();
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "prepago") {
		if (confirm("Se agregarán " + $("#divPrepagoCantidadRegistros").text()+ " registros a la lista negra.")) {
			ACMInterfacePrepagoDWR.agregarAListaNegra(
				metadataConsulta,
				{
					callback: function(data) {
						reloadData();
					}
				}
			);
		}
	} else if ($("#selectTipoRegistro").val() == "sinDatos") {
		if (confirm("Se agregarán " + $("#divSinDatosCantidadRegistros").text()+ " registros a la lista negra.")) {
			ACMInterfaceMidDWR.agregarAListaNegraSinDatos(
				metadataConsulta,
				{
					callback: function(data) {
						reloadData();
					}
				}
			);
		}
	} else {
		alert("Funcionalidad no habilitada para el tipo de registro.");
	}
}