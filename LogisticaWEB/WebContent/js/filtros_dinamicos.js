var ordenSufijos = [ "NOO", "ASC", "DES" ];

function FiltroDinamico(campos, reloadListener) {
	this.tamanoMuestra = 50;
	this.tamanoSubconjunto = 50;
	this.campos = campos;
	this.filtros = 0;
	this.ordenes = {};
	this.reloadListener = reloadListener;
}

FiltroDinamico.prototype.tamanoMuestraOnChange = function(event, element) {
	this.tamanoMuestra = $("#inputTamanoMuestra").val();
	
	this.reloadListener();
};

FiltroDinamico.prototype.tamanoSubconjuntoOnChange = function(event, element) {
	this.tamanoSubconjunto = $("#inputTamanoSubconjunto").val();
	
	this.reloadListener();
};

FiltroDinamico.prototype.agregarFiltro = function(event, element) {
	this.filtros++;
	
	var html = 
		"<div id='divFiltro" + this.filtros + "' class='divFiltro'>"
		+ "<div id='divQuitarFiltro" + this.filtros + "' class='divQuitarFiltro'>"
			+ "<input type='submit' id='inputQuitarFiltro" + this.filtros + "' value='Quitar'"
				+ " onclick='javascript:grid.filtroDinamico.quitarFiltro(event, this, " + this.filtros + ")'/>"
		+ "</div>"
		+ "<div class='divFormLabel'>Campo:</div>";
	
	html += 
		"<div id='divCampo" + this.filtros + "' style='float: left;'>"
			+ "<select id='selectCampo" + this.filtros + "' onchange='javascript:grid.filtroDinamico.campoOnChange(event, this, " + this.filtros + ")'>"
				+ "<option value=''>Seleccione...</option>";
	
	for (var campo in this.campos) {
		if (!this.campos[campo].oculto) {
			html +=
				"<option value='" + this.campos[campo].campo + "'>" + this.campos[campo].descripcion + "</option>";
		}
	}
				
	html += 
			"</select>"
		+ "</div>"
		+ "<div class='divFormLabel'>Condici&oacute;n:</div>"
			+ "<div id='divCondicion" + this.filtros + "' class='divCondicion'>&nbsp;</div>"
		+ "</div>";
	
	$("#divFiltros").append(html);
};

FiltroDinamico.prototype.campoOnChange = function(event, element, index) {
	var divCondicionValoresAnterior = $("#divCondicionValores" + index);
	
	if (divCondicionValoresAnterior.length > 0) {
		divCondicionValoresAnterior.remove();
	}
	
	var divCondicion = $("#divCondicion" + index);
	
	divCondicion.empty();
	
	var html = 
		"<select id='selectCondicion" + index + "' onchange='javascript:grid.filtroDinamico.condicionOnChange(event, this, " + index + ")'>"
			+ "<option value=''>Seleccione...</option>";
	
	var selectedFieldValue = $("#selectCampo" + index).val();
	
	var tipoCampo = -1;
	for (var campo in this.campos) {
		if (this.campos[campo].campo == selectedFieldValue) {
			tipoCampo = this.campos[campo].tipo;
		}
	}
	
	if (tipoCampo == __TIPO_CAMPO_NUMERICO) {
		html += 
			"<option value='eq'>Es igual a</option>"
			+ "<option value='btw'>Entre</option>"
			+ "<option value='gt'>Mayor que</option>"
			+ "<option value='lt'>Menor que</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else if (tipoCampo == __TIPO_CAMPO_STRING) {
		html += 
			"<option value='like'>Contiene</option>"
			+ "<option value='nlike'>No contiene</option>"
			+ "<option value='eq'>Es igual a</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else if (tipoCampo == __TIPO_CAMPO_MULTIPLE) {
		html += 
			+ "<option value='eq'>Es igual a</option>"
			+ "<option value='in'>Incluido en</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else if (tipoCampo == __TIPO_CAMPO_FECHA) {
		html += 
			"<option value='eq'>Es igual a</option>"
			+ "<option value='btw'>Entre</option>"
			+ "<option value='gt'>Mayor que</option>"
			+ "<option value='lt'>Menor que</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else {
		html += 
			"<option value='eq'>Es igual a</option>";
	}
	
	html +=
		+ "</select>";
	
	divCondicion.append(html);
	
	this.reloadListener();
};

FiltroDinamico.prototype.condicionOnChange = function(event, element, index) {
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
					+ "<input type='text' id='inputValorMin" + index + "' onchange='javascript:grid.filtroDinamico.valorOnChange(event, this)'/>"
				+ "</div>"
				+ "<div class='divFormLabel' style='width: 50px;'>y:</div>"
				+ "<div id='divValorMax" + index + "' style='float: left;'>"
					+ "<input type='text' id='inputValorMax" + index + "' onchange='javascript:grid.filtroDinamico.valorOnChange(event, this)'/>"
				+ "</div>";
			break;
		case "nl":
		case "nnl":
			this.reloadListener();
			
			break;
		case "in":
			if ($("#selectCampo" + index).val() == "contrato.tipoContratoDescripcion") {
				this.reloadListener();
				
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
						+ "<select id='inputValor" + index + "' onchange='javascript:grid.filtroDinamico.valorOnChange(event, this)'>"
							+ "<option value=''>Seleccione...</option>"
							+ "<option value='1'>Persona</option>"
							+ "<option value='2'>Empresa</option>"
							+ "<option value='3'>Estatal</option>"
							+ "<option value='4'>Otros</option>"
						+ "</select>"
					+ "</div>";				
			} else {
				html += 
					"<div class='divFormLabel'>Valor:</div>"
					+ "<div id='divValor" + index + "' style='float: left;'>"
						+ "<input type='text' id='inputValor" + index + "' onchange='javascript:grid.filtroDinamico.valorOnChange(event, this)'/>"
					+ "</div>";
			}

			break;
	}
	
	html +=
		"</div>";
	
	$("#divFiltro" + index).append(html);
};

FiltroDinamico.prototype.valorOnChange = function(event, element, index) {
	this.reloadListener();
};

FiltroDinamico.prototype.quitarFiltro = function(event, element, index) {
	$("#divFiltro" + index).remove();
	
	this.reloadListener();
};

FiltroDinamico.prototype.calcularCondiciones = function() {
	var result = [];
	
	for (var i=1; i<=this.filtros; i++) {
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
					for (var k = 0; k < metadataCondicion.valores.length; k++) {
						if (metadataCondicion.valores[k].indexOf("/") > 0) {
							metadataCondicion.valores[k] = 
								(metadataCondicion.valores[k] + " 00:00")
									.substring(0, 16);
						}
					}
				}
				
				result[result.length] = metadataCondicion;
			}
		}
	}
	
	return result;
};

FiltroDinamico.prototype.agregarOrden = function(event, element) {
	var className = element.getAttribute("class");
	var id = element.id;
	className = className.substring(0, className.length - 3);
	
	if (this.ordenes[id] != null) {
		this.ordenes[id] = (this.ordenes[id] + 1) % ordenSufijos.length;
	} else {
		this.ordenes[id] = 1;
	}
	
	element.setAttribute("class", className + ordenSufijos[this.ordenes[id]]);
	
	if (this.ordenes[id] == 0) {
		this.ordenes[id] = null;
	}
	
	this.reloadListener();
};

FiltroDinamico.prototype.calcularOrdenaciones = function calcularOrdenaciones() {
	var result = [];
	
	for (var campo in this.ordenes) {
		if (this.ordenes[campo] > 0) {
			result[result.length] = {
				campo: this.campos[campo].campo,
				ascendente: (this.ordenes[campo] == 1)
			};
		}
	}
	
	return result;
};

FiltroDinamico.prototype.calcularMetadataConsulta = function() {
	var metadataConsulta = {
		tamanoMuestra: this.tamanoMuestra
	};
	
	if (this.tamanoSubconjunto > 0) {
		metadataConsulta.tamanoSubconjunto = this.tamanoSubconjunto;
	}
	
	metadataConsulta.metadataOrdenaciones = this.calcularOrdenaciones();
	metadataConsulta.metadataCondiciones = this.calcularCondiciones();
	
	return metadataConsulta;
};