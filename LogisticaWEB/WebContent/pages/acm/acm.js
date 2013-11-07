var filtros = 0;
var ordenaciones = 0;

var ordenSufijos = ["NOO", "ASC", "DES"];

var ordenPrepago = {
	tdPrepagoMid: 0,
	tdPrepagoMesAno: 0,
	tdPrepagoMontoMesActual: 0,
	tdPrepagoMontoMesAnterior1: 0,
	tdPrepagoMontoMesAnterior2: 0,
	tdPrepagoMontoPromedio: 0,
	tdPrepagoFechaExportacion: 0,
	tdPrepagoFact: 0
};

var ordenContrato = {
	tdContratoMid: 0,
	tdContratoFinContrato: 0,
	tdContratoTipoContratoDescripcion: 0,
	tdContratoDocumento: 0,
	tdContratoNombre: 0,
	tdContratoDireccion: 0,
	tdContratoCodigoPostal: 0,
	tdContratoLocalidad: 0,
	tdContratoFechaExportacion: 0,
	tdContratoFact: 0
};

var campos = {
	tdPrepagoMid: "mid",
	tdPrepagoMesAno: "mesAno",
	tdPrepagoMontoMesActual: "montoMesActual",
	tdPrepagoMontoMesAnterior1: "montoMesAnterior1",
	tdPrepagoMontoMesAnterior2: "montoMesAnterior2",
	tdPrepagoMontoPromedio: "montoPromedio",
	tdPrepagoFechaExportacion: "fechaExportacion",
	tdPrepagoFact: "fact",
	tdContratoMid: "mid",
	tdContratoFinContrato: "fechaFinContrato",
	tdContratoTipoContratoDescripcion: "tipoContratoDescripcion",
	tdContratoDocumento: "documento",
	tdContratoNombre: "nombre",
	tdContratoDireccion: "direccion",
	tdContratoCodigoPostal: "codigoPostal",
	tdContratoLocalidad: "localidad",
	tdContratoFechaExportacion: "fechaExportacion",
	tdContratoFact: "fact"
};

var metadataOrdenacionesContrato = [];
var metadataOrdenacionesPrepago = [];

$(document).ready(function() {
	reloadData();
});

function selectTipoRegistroOnChange() {
	$("#divContratos").hide();
	$("#divPrepagos").hide();
	
	var divFiltros = $(".divFiltro");
	for (var i=0; i<divFiltros.length; i++) {
		divFiltros[i].remove();
	}
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		$("#divContratos").show();
	} else {
		$("#divPrepagos").show();
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
									+ registroMuestra.tipoContratoDescripcion 
								+ "</div></td>"
								+ "<td class='tdContratoDocumento'><div class='divContratoDocumento' title='"
									+ registroMuestra.documentoTipo + "'>" 
									+ registroMuestra.documento 
								+ "</div></td>"
								+ "<td class='tdContratoNombre'><div class='divContratoNombre'>" 
									+ registroMuestra.nombre 
								+ "</div></td>"
								+ "<td class='tdContratoDireccion'><div class='divContratoDireccion'>" 
									+ registroMuestra.direccion 
								+ "</div></td>"
								+ "<td class='tdContratoCodigoPostal'><div class='divContratoCodigoPostal'>" 
									+ registroMuestra.codigoPostal 
								+ "</div></td>"
								+ "<td class='tdContratoLocalidad'><div class='divContratoLocalidad'>" 
									+ registroMuestra.localidad 
								+ "</div></td>"
								+ "<td class='tdContratoFechaExportacion'><div class='divContratoFechaExportacion'>" 
									+ (registroMuestra.fechaExportacion != null ?
										formatLongDate(registroMuestra.fechaExportacion) : "&nbsp;")
								+ "</div></td>"
							+ "</tr>"
						);
					}
					
					$("#divContratoCantidadRegistros").text(data.cantidadRegistros);
				}, async: false
			}
		);
	} else {
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
									+ formatMonthYearDate(registroMuestra.mesAno) 
								+ "</div></td>"
								+ "<td class='tdPrepagoMontoMesActual'><div class='divPrepagoMontoMesActual'>" 
									+ registroMuestra.montoMesActual 
								+ "</div></td>"
								+ "<td class='tdPrepagoMontoMesAnterior1'><div class='divPrepagoMontoMesAnterior1'>" 
									+ registroMuestra.montoMesAnterior1 
								+ "</div></td>"
								+ "<td class='tdPrepagoMontoMesAnterior2'><div class='divPrepagoMontoMesAnterior2'>" 
									+ registroMuestra.montoMesAnterior2 
								+ "</div></td>"
								+ "<td class='tdPrepagoMontoPromedio'><div class='divPrepagoMontoPromedio'>" 
									+ registroMuestra.montoPromedio 
								+ "</div></td>"
								+ "<td class='tdPrepagoFechaExportacion'><div class='divPrepagoFechaExportacion'>" 
									+ (registroMuestra.fechaExportacion != null ?
										formatLongDate(registroMuestra.fechaExportacion) : "&nbsp;")
								+ "</div></td>"
							+ "</tr>"
						);
					}
					
					$("#divPrepagoCantidadRegistros").text(data.cantidadRegistros);
				}, async: false
			}
		);
	}
}

function calcularMetadataConsulta() {
	var metadataConsulta = {
		tamanoMuestra: $("#inputTamanoMuestra").val()
	};
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		metadataConsulta.metadataOrdenaciones = metadataOrdenacionesContrato;
		metadataConsulta.metadataCondiciones = calcularCondiciones();
	} else {
		metadataConsulta.metadataOrdenaciones = metadataOrdenacionesPrepago;
		metadataConsulta.metadataCondiciones = calcularCondiciones();
	}
	
	return metadataConsulta;
}

function calcularCondiciones() {
	var result = [];
	
	for (var i=1; i<=filtros; i++) {
		if ($("#divFiltro" + i).length > 0) {
			var metadataCondicion = {
				campo: $("#selectCampo" + i).val(),
				operador: $("#selectCondicion" + i).val()
			};
			
			var filtroValido = false;
			switch ($("#selectCondicion" + i).val()) {
				case "btw":
					metadataCondicion.valores = [$("#inputValorMin" + i).val(), $("#inputValorMax" + i).val()];
					
					filtroValido = metadataCondicion.valores[0] != "" && metadataCondicion.valores[1] != "";
					
					break;
				case "gt":
				case "lt":
				case "like":
				case "eq": 
				case "in":
				default:
					metadataCondicion.valores = [$("#inputValor" + i).val()];

					filtroValido = metadataCondicion.valores[0] != "";
				
					break;
			}
			
			if (filtroValido) {
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
		+ "<div class='divFormLabel'>Campo:</div>";
	
	if ($("#selectTipoRegistro").val() == "contrato") {
		html += 
			"<div id='divCampoContrato" + filtros + "' style='float: left;'>"
				+ "<select id='selectCampo" + filtros + "' onchange='javascript:selectCampoOnChange(event, this, " + filtros + ")'>"
					+ "<option>Seleccione...</option>"
					+ "<option value='mid'>MID</option>"
					+ "<option value='fechaFinContrato'>Fin de contrato</option>"
					+ "<option value='tipoContratoDescripcion'>Tipo de contrato</option>"
					+ "<option value='documentoTipo'>Tipo de documento</option>"
					+ "<option value='documento'>Documento</option>"
					+ "<option value='nombre'>Nombre</option>"
					+ "<option value='direccion'>Direcci&oacute;n</option>"
					+ "<option value='codigoPostal'>Código postal</option>"
					+ "<option value='localidad'>Localidad</option>"
					+ "<option value='fechaExportacion'>Asignado</option>"
				+ "</select>"
			+ "</div>";
	} else {
		html += 
			"<div id='divCampoPrepago" + filtros + "' style='float: left;'>"
				+ "<select id='selectCampo" + filtros + "' onchange='javascript:selectCampoOnChange(event, this, " + filtros + ")'>"
					+ "<option>Seleccione...</option>"
					+ "<option value='mid'>MID</option>"
					+ "<option value='mesAno'>Mes/A&ntilde;o</option>"
					+ "<option value='montoMesActual'>Monto mes actual</option>"
					+ "<option value='montoMesAnterior1'>Monto mes ant. 1</option>"
					+ "<option value='montoMesAnterior2'>Monto mes ant. 2</option>"
					+ "<option value='montoPromedio'>Monto promedio</option>"
					+ "<option value='fechaExportacion'>Asignado</option>"
				+ "</select>"
			+ "</div>";
	}
	
	html +=
			"<div class='divFormLabel'>Condici&oacute;n:</div>"
			+ "<div id='divCondicion" + filtros + "' style='float: left;'></div>"
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
		"<select id='selectCondicion" + filtros + "' onchange='javascript:selectCondicionOnChange(event, this, " + filtros + ")'>"
			+ "<option>Seleccione...</option>";
	
	if (($("#selectCampo" + index).val() == "mid") 
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
			+ "<option value='in'>Est&aacute; incluido en</option>";
	} else if (
		($("#selectCampo" + index).val() == "tipoContratoDescripcion")
		|| ($("#selectCampo" + index).val() == "documento")
		|| ($("#selectCampo" + index).val() == "nombre")
		|| ($("#selectCampo" + index).val() == "direccion")
		|| ($("#selectCampo" + index).val() == "localidad")) {
		html += 
			"<option value='like'>Contiene</option>"
			+ "<option value='eq'>Es igual a</option>";
	} else if (
		($("#selectCampo" + index).val() == "fechaExportacion")
		|| ($("#selectCampo" + index).val() == "fechaFinContrato")) {
		html += 
			"<option value='eq'>Es igual a</option>"
			+ "<option value='btw'>Entre</option>"
			+ "<option value='gt'>Mayor que</option>"
			+ "<option value='lt'>Menor que</option>";
	} else if ($("#selectCampo" + index).val() == "documentoTipo") {
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
	
	var html = "<div id='divCondicionValores" + index + "'>";
	
	switch ($("#selectCondicion" + index).val()) {
		case "btw":
			html +=
				"<div class='divFormLabel'>Valor:</div>"
				+ "<div id='divValorMin" + index + "' style='float: left'>"
					+ "<input id='inputValorMin" + index + "' onchange='javascript:inputValorOnChange(event, this)'/>"
				+ "</div>"
				+ "<div class='divFormLabel'>y:</div>"
				+ "<div id='divValorMax" + index + "' style='float: left'>"
					+ "<input id='inputValorMax" + index + "' onchange='javascript:inputValorOnChange(event, this)'/>"
				+ "</div>";
			break;
		case "gt":
		case "lt":
		case "like":
		case "eq": 
		case "in":
		default:
			if ($("#selectCampo" + index).val() == "documentoTipo") {
				html += 
					"<div class='divFormLabel'>Valor:</div>"
					+ "<div id='divValor" + index + "' style='float: left;'>"
						+ "<select id='inputValor" + index + "' onchange='javascript:inputValorOnChange(event, this)'>"
							+ "<option>Seleccione...</option>"
							+ "<option value='1'>Persona</option>"
							+ "<option value='2'>Empresa</option>"
							+ "<option value='3'>Estatal</option>"
						+ "</select>"
					+ "</div>";				
			} else {
				html += 
					"<div class='divFormLabel'>Valor:</div>"
					+ "<div id='divValor" + index + "' style='float: left;'>"
						+ "<input id='inputValor" + index + "' onchange='javascript:inputValorOnChange(event, this)'/>"
					+ "</div>";
			}
			break;
	}
	
	html +=
		"<div id='divQuitarFiltro" + index + "'>"
			+ "<input type='submit' id='inputQuitarFiltro" + index + "' value='Quitar'"
				+ " onclick='javascript:inputQuitarFiltroOnClick(event, this, " + index + ")'/>"
		+ "</div>"
		+ "<div>";
	
	$("#divFiltro" + index).append(html);
}

function inputValorOnChange(event, element) {
	reloadData();
}

function inputQuitarFiltroOnClick(event, element, index) {
	$("#divFiltro" + index).remove();
	
	reloadData();
}

function tableTheadTdOnClick(event, element) {
	var className = element.getAttribute("class");
	className = className.substring(0, className.length - 3);
	
	if (className.split("Contrato").length > 1) {
		ordenContrato[className] = (ordenContrato[className] + 1) % ordenSufijos.length;
		
		element.setAttribute("class", className + ordenSufijos[ordenContrato[className]]);
		
		var index = -1;
		for (var i=0; i<metadataOrdenacionesContrato.length; i++) {
			if (metadataOrdenacionesContrato[i].campo == campos[className]) {
				index = i;
				break;
			}
		}
		
		if (index > -1) {
			if (metadataOrdenacionesContrato[index].ascendente) {
				metadataOrdenacionesContrato[index].ascendente = false;
			} else {
				metadataOrdenacionesContrato.splice(index, 1);
			}
		} else {
			metadataOrdenacionesContrato[metadataOrdenacionesContrato.length] = {
				campo: campos[className],
				ascendente: true
			};
		}
	} else {
		ordenPrepago[className] = (ordenPrepago[className] + 1) % ordenSufijos.length;
		
		element.setAttribute("class", className + ordenSufijos[ordenPrepago[className]]);
		
		var index = -1;
		for (var i=0; i<metadataOrdenacionesPrepago.length; i++) {
			if (metadataOrdenacionesPrepago[i].campo == campos[className]) {
				index = i;
				break;
			}
		}
		
		if (index > -1) {
			if (metadataOrdenacionesPrepago[index].ascendente) {
				metadataOrdenacionesPrepago[index].ascendente = false;
			} else {
				metadataOrdenacionesPrepago.splice(index, 1);
			}
		} else {
			metadataOrdenacionesPrepago[metadataOrdenacionesPrepago.length] = {
				campo: campos[className],
				ascendente: true
			};
		}
	}
	
	reloadData();
}

function inputExportarAExcelOnClick(event) {
	if ($("#selectTipoRegistro").val() == "contrato") {
		ACMInterfaceContratoDWR.exportarAExcel(
			calcularMetadataConsulta(),
			{
				callback: function(data) {
					alert("Archivo generado: " + data);
					
					reloadData();
				}
			}
		);
	} else {
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
}

function inputReprocesarOnClick(event) {
	if ($("#selectTipoRegistro").val() == "contrato") {
		ACMInterfaceContratoDWR.reprocesar(
			calcularMetadataConsulta(),
			{
				callback: function(data) {
					
				}
			}
		);
	} else {
		ACMInterfacePrepagoDWR.reprocesar(
			calcularMetadataConsulta(),
			{
				callback: function(data) {
					
				}
			}
		);
	}
}