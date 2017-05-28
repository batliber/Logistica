var ordenSufijos = [ "NOO", "ASC", "DES" ];

function FiltroDinamico(grid, campos, reloadListener) {
	this.grid = grid;
	this.tamanoMuestra = 50;
	this.tamanoSubconjunto = 1;
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

FiltroDinamico.prototype.seleccionarColumnas = function(event, element) {
	$("#divSeleccionColumnasLista .divSeleccionColumna").remove();
	
	var html = "";
	var i=0;
	for (var campo in this.campos) {
		html += 
			"<div id='divSeleccionColumna" + i + "' class='divSeleccionColumna'>" 
				+ "<div style='float: left;width: 88%;'>" + this.campos[campo].descripcion + "</div>"
				+ "<div style='float: left;width: 10%;'>"
					+ "<input type='checkbox' class='checkboxSeleccionCampos' cid='" + campo + "'" 
						+ (this.campos[campo] == null || !this.campos[campo].oculto ? " checked='checked'" : "") 
					+ "'/>"
				+ "</div>"
			+ "</div>";
		i++;
	}
	
	$("#divSeleccionColumnasLista").html(html);
	
	$("#divIFrameSeleccionColumnas").show();
	$("#divIFrameSeleccionColumnas").draggable();
}

FiltroDinamico.prototype.actualizarColumnas = function(event, element) {
	var checkboxes = $(".checkboxSeleccionCampos");
	for (var i=0; i < checkboxes.length; i++) {
		this.campos[$(checkboxes[i]).attr("cid")].oculto = !$(checkboxes[i]).prop("checked");
	}
	
	this.grid.rebuild();
	this.reloadListener();
	
	closePopUp(event, element.parentNode.parentNode.parentNode.parentNode);
}

FiltroDinamico.prototype.agregarFiltro = function(event, element) {
	this.filtros++;
	
	var html = 
		"<div id='divFiltro" + this.filtros + "' class='divFiltro'>"
		+ "<div id='divQuitarFiltro" + this.filtros + "' class='divQuitarFiltro'>"
			+ "<input type='submit' id='inputQuitarFiltro" + this.filtros + "' value=''"
				+ " onclick='javascript:grid.filtroDinamico.quitarFiltro(event, this, " + this.filtros + ")'/>"
		+ "</div>"
		+ "<div class='divCampo'>Campo:</div>";
	
	html += 
		"<div id='divCampo" + this.filtros + "' style='float: left;'>"
			+ "<select id='selectCampo" + this.filtros + "' onchange='javascript:grid.filtroDinamico.campoOnChange(event, this, " + this.filtros + ")'>"
				+ "<option value=''>Seleccione...</option>";
	
	for (var campo in this.campos) {
		if (!this.campos[campo].oculto && this.campos[campo].tipo != __TIPO_CAMPO_DETAIL) {
			html +=
				"<option value='" + this.campos[campo].campo + "'"
					+ (this.campos[campo].clave != null ? " key='" + this.campos[campo].clave + "'" : "") + ">" 
					+ this.campos[campo].descripcion 
				+ "</option>";
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

FiltroDinamico.prototype.campoOnChange = function(event, element, index, preventReload) {
	var divCondicionValoresAnterior = $("#divCondicionValores" + index);
	
	if (divCondicionValoresAnterior.length > 0) {
		divCondicionValoresAnterior.remove();
	}
	
	var divCondicion = $("#divCondicion" + index);
	
	divCondicion.empty();
	
	var selectedFieldValue = $("#selectCampo" + index).val();
	
	var campo = null;
	var tipoCampo = -1;
	for (campo in this.campos) {
		if (this.campos[campo].campo == selectedFieldValue) {
			tipoCampo = this.campos[campo].tipo;
			break;
		}
	}
	
	var html = 
		"<select id='selectCondicion" + index + "' onchange='javascript:grid.filtroDinamico.condicionOnChange(event, this, " + index + ", false, \"" + selectedFieldValue + "\")'>"
			+ "<option value=''>Seleccione...</option>";
	
	if (tipoCampo == __TIPO_CAMPO_NUMERICO) {
		html += 
			"<option value='eq'>Es igual a</option>"
			+ "<option value='btw'>Entre</option>"
			+ "<option value='gt'>Mayor que</option>"
			+ "<option value='lt'>Menor que</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else if (tipoCampo == __TIPO_CAMPO_DECIMAL) {
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
			"<option value='eq'>Es igual a</option>"
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
	} else if (tipoCampo == __TIPO_CAMPO_FECHA_MES_ANO) {
		html += 
			"<option value='eq'>Es igual a</option>"
			+ "<option value='btw'>Entre</option>"
			+ "<option value='gt'>Mayor que</option>"
			+ "<option value='lt'>Menor que</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else if (tipoCampo == __TIPO_CAMPO_FECHA_HORA) {
		html += 
			"<option value='eq'>Es igual a</option>"
			+ "<option value='btw'>Entre</option>"
			+ "<option value='gt'>Mayor que</option>"
			+ "<option value='lt'>Menor que</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else if (tipoCampo == __TIPO_CAMPO_FECHA_MES_ANO) {
		html += 
			"<option value='eq'>Es igual a</option>"
			+ "<option value='btw'>Entre</option>"
			+ "<option value='gt'>Mayor que</option>"
			+ "<option value='lt'>Menor que</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else if (tipoCampo == __TIPO_CAMPO_BOOLEAN) {
		html += 
			"<option value='eq'>Es igual a</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else if (tipoCampo == __TIPO_CAMPO_RELACION) {
		html += 
			"<option value='keq'>Es igual a</option>"
			+ "<option value='like'>Contiene</option>"
			+ "<option value='nlike'>No contiene</option>"
			+ "<option value='nl'>Vac&iacute;o</option>"
			+ "<option value='nnl'>No vac&iacute;o</option>";
	} else {
		html += 
			"<option value='eq'>Es igual a</option>";
	}
	
	html +=
		"</select>";
	
	divCondicion.append(html);
	
	if (!preventReload) {
		this.reloadListener();
	}
};

FiltroDinamico.prototype.condicionOnChange = function(event, element, index, preventReload, selectedFieldValue) {
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
					+ "<input type='text' id='inputValorMin" + index + "' onchange='javascript:grid.filtroDinamico.valorOnChange(event, this, " + index + ", " + false + ")'/>"
				+ "</div>"
				+ "<div class='divFormLabel' style='width: 50px;'>y:</div>"
				+ "<div id='divValorMax" + index + "' style='float: left;'>"
					+ "<input type='text' id='inputValorMax" + index + "' onchange='javascript:grid.filtroDinamico.valorOnChange(event, this, " + index + ", " + false + ")'/>"
				+ "</div>";
			break;
		case "nl":
		case "nnl":
			if (!preventReload) {
				this.reloadListener();
			}
			
			break;
		case "in":
			$("#divFiltro" + index).css("height", "78px");
			
			var campo = null;
			for (campo in this.campos) {
				if (this.campos[campo].campo == selectedFieldValue) {
					break;
				}
			}
			
			html += 
				"<div class='divFormLabel'>Valor:</div>"
				+ "<div id='divValor" + index + "' style='float: left;'>"
					+ "<select id='inputValor" + index 
						+ "' onchange='javascript:grid.filtroDinamico.valorMultipleOnChange(event, this, " + index + ", false)'>"
						+ "<option value=''>Seleccione...</option>"
						+ "<option value='Todos'>Todos</option>"
						+ "<option value='Ninguno'>Ninguno</option>";
			
			var values = this.campos[campo].dataSource.funcion();
			for (var i=0; i<values.length; i++) {
				var keyValue = null;
				var value = null;
				
				try {
					keyValue = eval("values[i]." + this.campos[campo].dataSource.clave);
					value = eval("values[i]." + this.campos[campo].dataSource.valor);
				} catch (e) {
					keyValue = null;
					value = null;
				}
				
				if (keyValue != null) {
					html += 
						"<option value='" + keyValue + "'>" + value + "</option>";
				}
			}
			
			html +=
					"</select>"
				+ "</div>";
			
			html += 
				"<div id='divValores" + index + "' class='divValoresMultiples' style='float: left;'>";
				
			for (var i=0; i<values.length; i++) {
				keyValue = null;
				value = null;
				
				try {
					keyValue = eval("values[i]." + this.campos[campo].dataSource.clave);
					value = eval("values[i]." + this.campos[campo].dataSource.valor);
				} catch (e) {
					keyValue = null;
					value = null;
				}
				
				html += 
					"<div class='divValorMultiple' onclick='javascript:grid.filtroDinamico.valorMultipleOnClick(event, this, " + index + ", false)'>"
						+ value
					+ "</div>";
			}
			
			html +=
				"</div>";
			
			break;
		case "keq":
			var campo = null;
			for (campo in this.campos) {
				if (this.campos[campo].campo == selectedFieldValue) {
					break;
				}
			}
			
			html += 
				"<div class='divFormLabel'>Valor:</div>"
				+ "<div id='divValor" + index + "' style='float: left;'>"
					+ "<select id='inputValor" + index + "' onchange='javascript:grid.filtroDinamico.valorOnChange(event, this, " + index + ", " + false + ")'>"
						+ "<option value=''>Seleccione...</option>";
			
			var values = this.campos[campo].dataSource.funcion();
			for (var i=0; i<values.length; i++) {
				var keyValue = null;
				var value = null;
				
				try {
					keyValue = eval("values[i]." + this.campos[campo].dataSource.clave);
					value = eval("values[i]." + this.campos[campo].dataSource.valor);
				} catch (e) {
					keyValue = null;
					value = null;
				}
				
				if (keyValue != null) {
					html += 
						"<option value='" + keyValue + "'>" + value + "</option>";
				}
			}
			
			html +=
					"</select>"
				+ "</div>";
			
			break;
		case "gt":
		case "lt":
		case "like":
		case "nlike":
		case "eq":
		default:
			var campo = null;
			for (campo in this.campos) {
				if (this.campos[campo].campo == selectedFieldValue) {
					break;
				}
			}
		
			if ($("#selectCampo" + index).val() == "documentoTipo") {
				html += 
					"<div class='divFormLabel'>Valor:</div>"
					+ "<div id='divValor" + index + "' style='float: left;'>"
						+ "<select id='inputValor" + index + "' onchange='javascript:grid.filtroDinamico.valorOnChange(event, this, " + index + ", " + false + ")'>"
							+ "<option value=''>Seleccione...</option>"
							+ "<option value='1'>Persona</option>"
							+ "<option value='2'>Empresa</option>"
							+ "<option value='3'>Estatal</option>"
							+ "<option value='4'>Otros</option>"
						+ "</select>"
					+ "</div>";
			} else if (this.campos[campo].tipo == __TIPO_CAMPO_BOOLEAN) {
				html += 
					"<div class='divFormLabel'>Valor:</div>"
					+ "<div id='divValor" + index + "' style='float: left;'>"
						+ "<select id='inputValor" + index + "' onchange='javascript:grid.filtroDinamico.valorOnChange(event, this, " + index + ", " + false + ")'>"
							+ "<option value=''>Seleccione...</option>"
							+ "<option value='true'>SI</option>"
							+ "<option value='false'>NO</option>"
						+ "</select>"
					+ "</div>";
			} else {
				html += 
					"<div class='divFormLabel'>Valor:</div>"
					+ "<div id='divValor" + index + "' style='float: left;'>"
						+ "<input type='text' id='inputValor" + index + "' onchange='javascript:grid.filtroDinamico.valorOnChange(event, this, " + index + ", " + false + ")'/>"
					+ "</div>";
			}

			break;
	}
	
	html +=
		"</div>";
	
	$("#divFiltro" + index).append(html);
};

FiltroDinamico.prototype.valorOnChange = function(event, element, index, preventReload) {
	if (!preventReload) {
		this.reloadListener();
	}
};

FiltroDinamico.prototype.valorMultipleOnChange = function(event, element, index, preventReload) {
	if ($("#inputValor" + index).val() == "Todos") {
		var options = $("#inputValor" + index + " option");
		
		for (var i=0; i<options.length; i++) {
			if (i > 2) {
				$("#divValores" + index).append(
					"<div class='divValorMultiple' onclick='javascript:grid.filtroDinamico.valorMultipleOnClick(event, this, " + index + ", false)'>"
						+ $(options[i]).val()
					+ "</div>"
				);
			}
		}
	} else if ($("#inputValor" + index).val() == "Ninguno") {
		$("#divValores" + index + " div").remove();
	} else {
		$("#divValores" + index).append(
			"<div class='divValorMultiple' onclick='javascript:grid.filtroDinamico.valorMultipleOnClick(event, this, " + index + ", false)'>"
				+ $("#inputValor" + index).val()
			+ "</div>"
		);
	}
	
	if (!preventReload) {
		this.reloadListener();
	}
};

FiltroDinamico.prototype.valorMultipleOnClick = function(event, element, index, preventReload) {
	$(element).remove();
	
	if (!preventReload) {
		this.reloadListener();
	}
};

FiltroDinamico.prototype.quitarFiltro = function(event, element, index) {
	$("#divFiltro" + index).remove();
	
	this.reloadListener();
};

FiltroDinamico.prototype.limpiarFiltros = function(event, element) {
	$(".divFiltro").remove();
	
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
				operador: $("#selectCondicion" + i).val()
			};
			
			var filtroValido = false;
			switch ($("#selectCondicion" + i).val()) {
				case "btw":
					metadataCondicion.campo = $("#selectCampo" + i).val();
					
					var valorMin = $("#inputValorMin" + i).val();
					var valorMax = $("#inputValorMax" + i).val();
					
					metadataCondicion.valores = [valorMin, valorMax];
					
					filtroValido = 
						metadataCondicion.valores[0] != "" 
							&& metadataCondicion.valores[1] != "";
					
					break;
				case "nl":
				case "nnl":
					metadataCondicion.campo = $("#selectCampo" + i).val();
					
					metadataCondicion.valores = [];
					
					filtroValido = true;
					
					break;
				case "in":
					metadataCondicion.campo = $("#selectCampo" + i).val();
					
					var valoresMultiples = $(("#divFiltro" + i) + " .divValorMultiple");
					
					metadataCondicion.valores = [];
					for (var j=0; j<valoresMultiples.length; j++) {
						metadataCondicion.valores[metadataCondicion.valores.length] = 
							valoresMultiples[j].innerHTML;
					}
					
					filtroValido = true;
					
					break;
				case "keq":
					metadataCondicion.campo = 
						$("#selectCampo" + i + " option:selected").attr("key");
					
					metadataCondicion.valores = [$("#inputValor" + i).val()];

					filtroValido = metadataCondicion.valores[0] != "";
					
					break;
				case "gt":
				case "lt":
				case "like":
				case "nlike":
				case "eq": 
				default:
					metadataCondicion.campo = $("#selectCampo" + i).val();
				
					metadataCondicion.valores = [$("#inputValor" + i).val()];

					filtroValido = metadataCondicion.valores[0] != "";
				
					break;
			}
			
			if (filtroValido) {
				if (metadataCondicion.valores != null) {
					for (var k = 0; k < metadataCondicion.valores.length; k++) {
						if (typeof(metadataCondicion.valores[k]) == "string"
							&& metadataCondicion.valores[k].indexOf("/") > 0) {
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

FiltroDinamico.prototype.showMenu = function(event, element) {
	var divMenu = $(".divTableHeaderMenu");
	divMenu.show();
	divMenu.offset({ top: divMenu.offset().top, left: $(element).position().left });
};