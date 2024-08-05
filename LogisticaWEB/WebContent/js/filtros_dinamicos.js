var ordenSufijos = [ "NOO", "ASC", "DES" ];

function FiltroDinamico(grid, campos, reloadListener) {
	this.grid = grid;
	this.tamanoMuestra = 50;
	this.tamanoSubconjunto = 1;
	this.campos = campos;
	this.filtros = 0;
	this.ordenes = {};
	this.reloadListener = reloadListener;
	this.preventReload = false;
	
	/**
	 * Construye el espacio para filtros dinámicos asociados a la tabla grid.
	 */
	this.rebuild = _rebuild,
	
	/**
	 * Listener del evento "change" del input de selección del tamaño de la muestra.
	 */
	this.tamanoMuestraOnChange = function(eventObject) {
		return _tamanoMuestraOnChange.bind(this)(eventObject);
	},
	
	/**
	 * Listener del evento "change" del input de selección del tamaño del subconjunto.
	 */
	this.tamanoSubconjuntoOnChange = function(eventObject) {
		return _tamanoSubconjuntoOnChange.bind(this)(eventObject);
	},
	
	/**
	 * Listener del evento "click" del input que abre la ventana de selección de columnas.
	 */
	this.seleccionarColumnasOnClick = function(eventObject) {
		return _seleccionarColumnasOnClick.bind(this)(eventObject);
	},
	
	/**
	 * Listener del evento "click" del input que confirma la selección de columnas.
	 */
	this.actualizarColumnasOnClick = function(eventObject) {
		return _actualizarColumnasOnClick.bind(this)(eventObject);
	},
	
	/**
	 * Listener del evento "click" del input que elimina los filtros definidos.
	 */
	this.limpiarFiltrosOnClick = function(eventObject) {
		return _limpiarFiltrosOnClick.bind(this)(eventObject);
	},
	
	this.agregarFiltroManual = function(filtro, fijo) {
		return _agregarFiltroManual.bind(this)(filtro, fijo);
	},
	
	this.agregarFiltrosManuales = function(filtros, fijo) {
		return _agregarFiltrosManuales.bind(this)(filtros, fijo);
	},
	
	/**
	 * Listener del evento "click" del input que agrega un filtro sobre los datos de la tabla grid.
	 */
	this.agregarFiltroOnClick = function(eventObject) {
		return _agregarFiltro.bind(this)(false);
	},
	
	/**
	 * Listener del evento "click" del input que elimina un filtro definido.
	 */
	this.quitarFiltroOnClick = function(eventObject) {
		return _quitarFiltro.bind(this)(eventObject);
	},
	
	/**
	 * Listener del evento "change" del select que contiene la lista de campos para definir un filtro.
	 */
	this.campoOnChange = function(eventObject) {
		return _campoOnChange.bind(this)(eventObject);
	},
	
	/**
	 * Listener del evento "change" del select que contiene la lista de condiciones para definir un filtro.
	 */
	this.condicionOnChange = function(eventObject) {
		return _condicionOnChange.bind(this)(eventObject);
	},
	
	/**
	 * Listener del evento "change" de los controles que contienen los valores para definir un filtro.
	 */
	this.valorOnChange = function(eventObject) {
		return _valorOnChange.bind(this)(eventObject);
	},
	
	/**
	 * Listener del evento "change" del select que contiene los valores múltiples para definir un filtro de tipo INCLUIDO EN. 
	 */
	this.valorMultipleOnChange = function(eventObject) {
		return _valorMultipleOnChange.bind(this)(eventObject);
	},
	
	/**
	 * Listener del evento "click" de los div que contienen los valores múltiples para definir un filtro de tipo INCLUIDO EN.
	 */
	this.valorMultipleOnClick = function(eventObject) {
		return _valorMultipleOnClick.bind(this)(eventObject);
	},
	
	/**
	 * Función que calcula las condiciones que representan el filtro definido en pantalla.
	 */
	this.calcularCondiciones = _calcularCondiciones,
	
	/**
	 * Listener del evento "click" de los encabezados de las columnas de la tabla grid.
	 * Generan ordenaciones de los datos en función de la columna seleccionada.
	 * 
	 * Nota: los elementos de los encabezados tienen el formato: "divTableHeaderCell divTableHeaderCellXXX"
	 * donde XXX denota el tipo de ordenación (NOO, ASC, DES).
	 * Siempre se mantiene el prefijo divTableHeaderCell.
	 */
	this.agregarOrdenOnClick = function(eventObject) {
		return _agregarOrden.bind(this)(eventObject);
	},
	
	this.agregarOrdenManual = function(orden) {
		return _agregarOrdenManual.bind(this)(orden);
	},
	
	/**
	 * Función que calcula las ordenaciones que representan el filtro definido en pantalla.
	 */
	this.calcularOrdenaciones = _calcularOrdenaciones,
	
	/**
	 * Función que calcula la entidad que representa el filtro definido en pantalla.
	 */
	this.calcularMetadataConsulta = _calcularMetadataConsulta,
	
	/**
	 * Listener del evento "click" de los div que permiten mostrar el menú de acciones sobre una columna.
	 */
	this.showMenu = _showMenu,
	
	/**
	 * Listener del evento "click" del div que muestra/oculta los filtros.
	 */
	this.mostrarOcultarFiltrosOnClick = function(eventObject) {
		return _mostrarOcultarFiltros.bind(this)(eventObject);
	}
}

function _rebuild(width) {
	html = 
		"<div class='divFiltrosHandle'>"
			+ "<div class='divFiltrosHandleLeft' style='width: " + ((width - 100) / 2) + "px;'>"
				+ "<div class='divFiltrosHandleLeftTop'>&nbsp;</div>"
				+ "<div class='divFiltrosHandleLeftBottom'>&nbsp;</div>"
			+ "</div>"
			+ "<div class='divFiltrosHandleCenter'>"
				+ "<div class='divFiltrosHandleCenterTop'>"
					+ "<div class='divFiltrosHandleCenterTopLine'>&nbsp;</div>"
					+ "<div class='divFiltrosHandleCenterTopLine'>&nbsp;</div>"
					+ "<div class='divFiltrosHandleCenterTopLine'>&nbsp;</div>"
				+ "</div>"
				+ "<div class='divFiltrosHandleCenterBottom'>&nbsp;</div>"
			+ "</div>"
			+ "<div class='divFiltrosHandleRight' style='width: " + ((width - 100) / 2) + "px;'>"
				+ "<div class='divFiltrosHandleRightTop'>&nbsp;</div>"
				+ "<div class='divFiltrosHandleRightBottom'>&nbsp;</div>"
			+ "</div>"
		+ "</div>"
		+ "<div class='divFiltros'>"
			+ "<div class='divLabelTamanoMuestra'>Muestra:</div>"
			+ "<div class='divTamanoMuestra'>"
				+ "<input type='text' class='inputTamanoMuestra' value='" + this.tamanoMuestra + "'/>"
			+ "</div>"
			+ "<div class='divLabelTamanoSubconjunto'>Subconj.:</div>"
			+ "<div class='divTamanoSubconjunto'>"
				+ "<input type='text' class='inputTamanoSubconjunto' value='" + this.tamanoSubconjunto + "'/>"
			+ "</div>"
			+ "<div class='divAgregarFiltroContainer'>"
				+ "<div class='divLabelFiltros'>Filtros:</div>"
				+ "<div class='divAgregarFiltro'>"
					+ "<input type='submit' value='' class='inputAgregarFiltro'/>"
				+ "</div>"
//				+ "<div class='divFormLabelExtended'>Limpiar filtros:</div>"
				+ "<div class='divLimpiarFiltros'>"
					+ "<input type='submit' value='' class='inputLimpiarFiltros'/>"
				+ "</div>"
//				+ "<div class='divFormLabelExtended'>Seleccionar columnas:</div>"
				+ "<div class='divSeleccionarColumnas'>"
					+ "<input type='submit' value='' class='inputSeleccionarColumnas'/>"
					+ "<div class='divIFrameSeleccionColumnas'>"
						+ "<div class='divTitleBar'>"
							+ "<div class='divTitleBarText'>Columnas</div>"
							+ "<div class='divTitleBarCloseButton' onclick='javascript:closePopUp(event, this.parentNode.parentNode)'>&nbsp;</div>"
						+ "</div>"
						+ "<div class='divSeleccionColumnas'>"
							+ "<div class='divPopupWindow'>"
								+ "<div class='divSeleccionColumnasLista'>&nbsp;</div>"
								+ "<div class='divSeleccionColumnasBotonera'>"
									+ "<input type='submit' class='inputSeleccionColumnasAceptar' value=''/>"
								+ "</div>"
							+ "</div>"
						+ "</div>"
					+ "</div>"
				+ "</div>"
			+ "</div>"
		+ "</div>";
		
	$(this.grid.element).html(html);
	
	var divFiltros = $(this.grid.element).children(".divFiltros");
	var divFiltrosHandle = $(this.grid.element).children(".divFiltrosHandle");
	
	divFiltros.css("width", width - 6);
	
	divFiltros.find(".inputTamanoMuestra").change(this.tamanoMuestraOnChange.bind(this));
	divFiltros.find(".inputTamanoSubconjunto").change(this.tamanoSubconjuntoOnChange.bind(this));
	divFiltros.find(".inputSeleccionarColumnas").click(this.seleccionarColumnasOnClick.bind(this));
	divFiltros.find(".inputSeleccionColumnasAceptar").click(this.actualizarColumnasOnClick.bind(this));
	divFiltros.find(".inputAgregarFiltro").click(this.agregarFiltroOnClick.bind(this));
	divFiltros.find(".inputLimpiarFiltros").click(this.limpiarFiltrosOnClick.bind(this));
	divFiltrosHandle.find(".divFiltrosHandleCenterTop").click(this.mostrarOcultarFiltrosOnClick.bind(this));
	divFiltrosHandle.find(".divFiltrosHandleCenterTopLine").click(this.mostrarOcultarFiltrosOnClick.bind(this));
	divFiltrosHandle.find(".divFiltrosHandleCenterBottom").click(this.mostrarOcultarFiltrosOnClick.bind(this));
}

function _tamanoMuestraOnChange(eventObject) {
	this.tamanoMuestra = $(eventObject.target).val();
	
	if (!this.preventReload) {
		this.reloadListener();
	}
}

function _tamanoSubconjuntoOnChange(eventObject) {
	this.tamanoSubconjunto = $(eventObject.target).val();
	
	if (!this.preventReload) {
		this.reloadListener();
	}
}

function _seleccionarColumnasOnClick() {
	$(this.grid.element).find(".divSeleccionColumna").remove();
	
	var html = "";
	var i=0;
	for (var campo in this.campos) {
		html += 
			"<div id='divSeleccionColumna" + i + "' class='divSeleccionColumna'>" 
				+ "<div class='divSeleccionColumnasCampo'>" + this.campos[campo].descripcion + "</div>"
				+ "<div class='divSeleccionColumnasCampoCheck'>"
					+ "<input type='checkbox' class='checkboxSeleccionCampos'"
						+ " cid='" + campo + "'" 
							+ (this.campos[campo] == null || !this.campos[campo].oculto ? 
								" checked='checked'" 
								: "") 
					+ "'/>"
				+ "</div>"
			+ "</div>";
		i++;
	}
	
	$(this.grid.element).find(".divSeleccionColumnasLista").html(html);
	$(this.grid.element).find(".divIFrameSeleccionColumnas").show();
	$(this.grid.element).find(".divIFrameSeleccionColumnas").draggable();
}

function _actualizarColumnasOnClick(eventObject) {
	var checkboxes = $(this.grid.element).find(".checkboxSeleccionCampos");
	
	for (var i=0; i < checkboxes.length; i++) {
		this.campos[$(checkboxes[i]).attr("cid")].oculto = !$(checkboxes[i]).prop("checked");
	}
	
	var condiciones = this.calcularCondiciones();
	
	this.grid.rebuild();
	
	if (condiciones.length > 0) {
		var functionThen = function(eventObject) {
			this.preventReload = false;
			
			if (!this.preventReload) {
				this.reloadListener();
			}
			
			closePopUp(eventObject, eventObject.target.parentNode.parentNode.parentNode.parentNode);
		};
		
		this.agregarFiltrosManuales(condiciones, condiciones[0].fijo)
			.then(functionThen.bind(this, eventObject));
	} else {
		this.reloadListener();
	}
}

function _limpiarFiltrosOnClick(eventObject) {
	var divFiltros = $(this.grid.element).children(".divFiltros").find(".divFiltro");
	
	for (var i=0; i<divFiltros.length; i++) {
		var divFiltro = $(divFiltros[i]);
		var selectCampo = divFiltro.find(".selectCampo");
		
		if (!selectCampo.prop("disabled")) {
			divFiltro.remove();
		}
	}
	
	if (!this.preventReload) {
		this.reloadListener();
	}
}

function _agregarFiltroManual(filtro, fijo) {
	var divFiltros = $(this.grid.element).children(".divFiltros");
	
	var functionThen = function(divFiltro) {
		var selectCampo = divFiltro.find(".selectCampo");
		selectCampo.val(filtro.campo);
		
		if (fijo != null && fijo) {
			selectCampo.prop("disabled", "disabled");
		}
		
		var functionThen = function(divCondicion) {
			var selectCondicion = divCondicion.find(".selectCondicion");
			
			selectCondicion.val(filtro.operador);
			
			if (fijo != null && fijo) {
				selectCondicion.prop("disabled", "disabled");
			}
			
			var functionThen = function(divCondicionValores) {
				if (filtro.valores.length > 0) {
					var selectValor = divCondicionValores.find(".selectValor");
					selectValor.val(filtro.valores[0]);
					
					if (fijo != null && fijo) {
						selectValor.prop("disabled", "disabled");
					}
				}
			};
			
			return _condicionOnChange.bind(this)({ target: selectCondicion })
				.then(functionThen.bind(this));
		};
		
		return _campoOnChange.bind(this)({ target: selectCampo })
			.then(functionThen.bind(this));
	};
		
	return _agregarFiltro.bind(this)(fijo)
		.then(functionThen.bind(this));
}

function _agregarFiltrosManuales(filtros, fijo) {
	var promises = [];
	
	for (var i=0; i<filtros.length; i++) {
		var filtro = filtros[i];
		
		promises[promises.length] = _agregarFiltroManual.bind(this)(filtro, fijo);
	}
	
	return Promise.all(promises);
}

function _agregarFiltro(fijo) {
	var promiseFunction = function(resolve, reject) {
		this.filtros++;
		
		var html = 
			"<div id='divFiltro" + this.filtros + "' class='divFiltro'>"
				+ "<div class='divQuitarFiltro'>"
					+ "<input type='submit' value='' ";
		
		if (fijo != null && fijo) {
			html += 
						"class='inputQuitarFiltroDisabled' disabled='disabled'";
		} else {
			html += 
						"class='inputQuitarFiltro'";
		}
		
		html +=  
						"/>"
				+ "</div>"
				+ "<div class='divLabelCampo'>Campo:</div>"
				+ "<div class='divCampo'>"
					+ "<select class='selectCampo' id='selectCampo" + this.filtros + "'";
		if (fijo != null && fijo) {
			html += 
						" disabled='disabled'"
					+ ">";
		} else {
			html += 
					">";
		}
		
		html += 
						"<option value=''>Seleccione...</option>";
		
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
				+ "<div class='divLabelCondicion'>Condici&oacute;n:</div>"
				+ "<div class='divCondicion'>&nbsp;</div>"
			+ "</div>";
		
		var divFiltro = 
			$(this.grid.element)
				.children(".divFiltros")
				.append(html)
				.children(".divFiltro:last");
		
		divFiltro.find(".inputQuitarFiltro").click(this.quitarFiltroOnClick.bind(this));
		divFiltro.find(".selectCampo").change(this.campoOnChange.bind(this));
		
		resolve(divFiltro);
	}
	
	return new Promise(promiseFunction.bind(this));
}

function _quitarFiltro(eventObject) {
	var promiseFunction = function(resolve, reject) {
		$(eventObject.target).parent().parent().remove();
		
		if (!this.preventReload) {
			this.reloadListener();
		}
	};
	
	return new Promise(promiseFunction.bind(this));
}

function _campoOnChange(eventObject) {
	var promiseFunction = function(resolve, reject) {
		var selectCampo = $(eventObject.target);
		var divCampo = selectCampo.parent();
		var divCondicion = divCampo.siblings(".divCondicion");
		var divCondicionValoresAnterior = divCondicion.siblings(".divCondicionValores");
		
		if (divCondicionValoresAnterior.length > 0) {
			divCondicionValoresAnterior.remove();
		}
		
		divCondicion.empty();
		
		var selectedFieldValue = selectCampo.val();
		
		var campo = null;
		var tipoCampo = -1;
		for (campo in this.campos) {
			if (this.campos[campo].campo == selectedFieldValue) {
				tipoCampo = this.campos[campo].tipo;
				break;
			}
		}
		
		var html = 
			"<select class='selectCondicion' id='selectCondicion " + this.filtros + "'>"
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
		} else if (tipoCampo == __TIPO_CAMPO_PORCENTAJE) {
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
				+ "<option value='like'>Contiene</option>"
				+ "<option value='nlike'>No contiene</option>"
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
				+ "<option value='kneq'>No es igual a</option>"
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
		
		divCondicion
			.append(html)
			.find(".selectCondicion")
			.change(this.condicionOnChange.bind(this));
		
		resolve(divCondicion);
	};
	
	return new Promise(promiseFunction.bind(this));
}

function _condicionOnChange(eventObject) {
	var promiseFunction = function(resolve, reject) {
		var selectCondicion = $(eventObject.target);
		var divCondicion = selectCondicion.parent();
		var divFiltro = divCondicion.parent();
		var divCampo = divCondicion.siblings(".divCampo");
		var selectCampo = divCampo.children(".selectCampo");
		var selectedFieldValue = selectCampo.val();
		var divCondicionValoresAnterior = divCondicion.siblings(".divCondicionValores");
		
		if (divCondicionValoresAnterior.length > 0) {
			divCondicionValoresAnterior.remove();
		}
		
		var html = "<div class='divCondicionValores' id='divCondicionValores" + this.filtros + "'>";
		
		switch (selectCondicion.val()) {
			case "btw":
				html +=
					"<div class='divLabelCondicionValor'>Valor:</div>"
					+ "<div class='divValorMin'>"
						+ "<input type='text' class='inputValorMin'/>"
					+ "</div>"
					+ "<div class='divLabelCondicionValor'>y:</div>"
					+ "<div class='divValorMax'>"
						+ "<input type='text' class='inputValorMax'/>"
					+ "</div>";
				break;
			case "nl":
			case "nnl":
				if (!this.preventReload) {
					this.reloadListener();
				}
				
				break;
			case "in":
				var campo = null;
				for (campo in this.campos) {
					if (this.campos[campo].campo == selectedFieldValue) {
						break;
					}
				}
				
				var callbackDataSource = function(values) {
					divFiltro.css("height", "78px");
					
					html += 
						"<div class='divLabelCondicionValor'>Valor:</div>"
						+ "<div class='divValor'>"
							+ "<select class='selectValoresMultiples'>"
								+ "<option value=''>Seleccione...</option>"
								+ "<option value='Todos'>Todos</option>"
								+ "<option value='Ninguno'>Ninguno</option>";
					
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
						+ "</div>"
						+ "<div class='divValoresMultiples'>";
						
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
									"<div class='divValorMultiple'>"
										+ value
									+ "</div>";
					}
					
					html +=
								"</div>"
						+ "</div>";
					
					divFiltro
						.append(html)
						.children()
						.last()
						.find(".selectValoresMultiples")
						.change(this.valorMultipleOnChange.bind(this))
					
					divFiltro
						.children()
						.last()
						.find(".divValorMultiple")
						.click(this.valorMultipleOnClick.bind(this));
				};
				
				this.campos[campo].dataSource.funcion()
					.then(callbackDataSource.bind(this));
				
				break;
			case "keq":
			case "kneq":
				var campo = null;
				for (campo in this.campos) {
					if (this.campos[campo].campo == selectedFieldValue) {
						break;
					}
				}
				
				var funcionThen = this.campos[campo].dataSource.funcion().then;
				
				if (typeof funcionThen == 'function') {
					var callbackDataSource = function(values) {
						html += 
							"<div class='divLabelCondicionValor'>Valor:</div>"
							+ "<div class='divValor'>"
								+ "<select class='selectValor'>"
									+ "<option value=''>Seleccione...</option>";
						
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
						
						var divCondicionValor =
							divFiltro
								.append(html)
								.children();
						
						
						divCondicionValor
							.find(".selectValor")
							.change(this.valorOnChange.bind(this))
						
						resolve(divCondicionValor);
					};
					
					this.campos[campo].dataSource.funcion()
						.then(callbackDataSource.bind(this));
				} else {
					html += 
						"<div class='divLabelCondicionValor'>Valor:</div>"
						+ "<div class='divValor'>"
							+ "<select class='selectValor'>"
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
				}
				
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
			
				if (selectedFieldValue == "documentoTipo") {
					html += 
						"<div class='divLabelCondicionValor'>Valor:</div>"
						+ "<div class='divValor'>"
							+ "<select class='selectValor'>"
								+ "<option value=''>Seleccione...</option>"
								+ "<option value='1'>Persona</option>"
								+ "<option value='2'>Empresa</option>"
								+ "<option value='3'>Estatal</option>"
								+ "<option value='4'>Otros</option>"
							+ "</select>"
						+ "</div>";
				} else if (this.campos[campo].tipo == __TIPO_CAMPO_BOOLEAN) {
					html += 
						"<div class='divLabelCondicionValor'>Valor:</div>"
						+ "<div class='divValor'>"
							+ "<select class='selectValor'>"
								+ "<option value=''>Seleccione...</option>"
								+ "<option value='true'>SI</option>"
								+ "<option value='false'>NO</option>"
							+ "</select>"
						+ "</div>";
				} else {
					html += 
						"<div class='divLabelCondicionValor'>Valor:</div>"
						+ "<div class='divValor'>"
							+ "<input type='text' class='inputValor'/>"
						+ "</div>";
				}
	
				break;
		}
		
		if (selectCondicion.val() != "in" && typeof funcionThen != 'function') {
			html +=
				"</div>";
		
			divCondicionValores = 
				divFiltro
					.append(html)
					.children()
			
			divCondicionValores.find(".inputValor").change(this.valorOnChange.bind(this));
			divCondicionValores.find(".inputValor").change(this.valorOnChange.bind(this));
			divCondicionValores.find(".selectValor").change(this.valorOnChange.bind(this));
			divCondicionValores.find(".inputValorMin").change(this.valorOnChange.bind(this));
			divCondicionValores.find(".inputValorMax").change(this.valorOnChange.bind(this));
			
			resolve(divCondicionValores);
		}
		
		if (typeof funcionThen == 'function') {
			return this.campos[campo].dataSource.funcion;
		}
	};
	
	return new Promise(promiseFunction.bind(this));
}

function _valorOnChange(eventObject) {
	var promiseFunction = function(resolve, reject) {
		if (!this.preventReload) {
			this.reloadListener();
		}
		
		resolve("Ok");
	};
	
	return new Promise(promiseFunction.bind(this));
}

function _valorMultipleOnChange(eventObject) {
	var promiseFunction = function(resolve, reject) {
		var selectValoresMultiples = $(eventObject.target);
		var divValor = selectValoresMultiples.parent();
		var divValoresMultiples = divValor.next();
		
		if (selectValoresMultiples.val() == "Todos") {
			var options = selectValoresMultiples.children("option");
			
			for (var i=0; i<options.length; i++) {
				if (i > 2) {
					divValoresMultiples.append(
						"<div class='divValorMultiple'>"
							+ $(options[i]).val()
						+ "</div>"
					);
				}
			}
		} else if (selectValoresMultiples.val() == "Ninguno") {
			divValoresMultiples.find(".divValorMultiple").remove();
		} else {
			divValoresMultiples.append(
				"<div class='divValorMultiple'>"
					+ selectValoresMultiples.val()
				+ "</div>"
			);
		}
		
		if (!this.preventReload) {
			this.reloadListener();
		}
		
		divValoresMultiples.find(".divValorMultiple").click(this.valorMultipleOnClick.bind(this));
		
		resolve("Ok");
	};
	
	return new Promise(promiseFunction.bind(this));
}

function _valorMultipleOnClick(eventObject) {
	var promiseFunction = function(resolve, reject) {
		$(eventObject.target).remove();
		
		if (!this.preventReload) {
			this.reloadListener();
		}
		
		resolve("Ok");
	};
	
	return new Promise(promiseFunction.bind(this));
}

function _calcularCondiciones() {
	var result = [];
	
	var filtros = $(this.grid.element).find(".divFiltro");
	for (var i=0; i<filtros.length; i++) {
		var divFiltro = $(filtros[i]);
		var selectCampo = divFiltro.find(".selectCampo");
		var valCampo = selectCampo.val();
		var selectCondicion = divFiltro.find(".selectCondicion");
		var valCondicion = selectCondicion.val();
		var fijo = divFiltro.find(".selectCampo").prop("disabled");
		
		if (valCampo != "" && valCondicion != "") {
			var metadataCondicion = {
				"operador": valCondicion,
				"fijo": fijo
			};
			
			var filtroValido = false;
			switch (valCondicion) {
				case "btw":
					metadataCondicion.campo = valCampo;
					
					var valorMin = divFiltro.find(".inputValorMin").val();
					var valorMax = divFiltro.find(".inputValorMax").val();
					
					metadataCondicion.valores = [valorMin, valorMax];
					
					filtroValido = 
						metadataCondicion.valores[0] != "" 
							&& metadataCondicion.valores[1] != "";
					
					break;
				case "nl":
				case "nnl":
					metadataCondicion.campo = valCampo;
					
					metadataCondicion.valores = [];
					
					filtroValido = true;
					
					break;
				case "in":
					metadataCondicion.campo = valCampo;
					
					var valoresMultiples = divFiltro.find(".divValorMultiple");
					
					metadataCondicion.valores = [];
					for (var j=0; j<valoresMultiples.length; j++) {
						metadataCondicion.valores[metadataCondicion.valores.length] = 
							valoresMultiples[j].innerHTML;
					}
					
					filtroValido = true;
					
					break;
				case "keq":
				case "kneq":
					metadataCondicion.campo = selectCampo.find("option:selected").attr("key");
					
					metadataCondicion.valores = [divFiltro.find(".selectValor").val()];
					filtroValido = 
						metadataCondicion.valores[0] != null
						&& metadataCondicion.valores[0] != "";
					
					break;
				case "gt":
				case "lt":
				case "like":
				case "nlike":
				case "eq": 
				default:
					metadataCondicion.campo = valCampo;
				
					var elementValor = divFiltro.find(".inputValor");
					if (elementValor.length > 0) {
						metadataCondicion.valores = [elementValor.val()];
					} else {
						elementValor = divFiltro.find(".selectValor");
						
						metadataCondicion.valores = [elementValor.val()];
					}
					
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
}

function _agregarOrdenManual(orden) {
	var promiseFunction = function(resolve, reject) {
		var campo = orden.campo;
		var ascendente = orden.ascendente;
		
		this.ordenes[campo] = ascendente ? 1 : 2;
		
		var element = $("#" + orden.campo).closest(".divTableHeaderCell");
		var className = element.attr("class");
		className = className.substring(0, className.length - 3);
		
		element.attr("class", className + ordenSufijos[this.ordenes[campo]]);
		
		if (!this.preventReload) {
			this.reloadListener();
		}
		
		resolve();
	};
	
	return new Promise(promiseFunction.bind(this));
}

function _agregarOrden(eventObject) {
	var element = $(eventObject.target).closest(".divTableHeaderCell");
	var className = element.attr("class");
	
	var id = element.attr("id");
	className = className.substring(0, className.length - 3);
	
	if (this.ordenes[id] != null) {
		this.ordenes[id] = (this.ordenes[id] + 1) % ordenSufijos.length;
	} else {
		this.ordenes[id] = 1;
	}
	
	element.attr("class", className + ordenSufijos[this.ordenes[id]]);
	
	if (this.ordenes[id] == 0) {
		this.ordenes[id] = null;
	}
	
	if (!this.preventReload) {
		this.reloadListener();
	}
}

function _calcularOrdenaciones() {
	var result = [];
	
	for (var campo in this.ordenes) {
		if (this.ordenes[campo] > 0) {
			result[result.length] = {
				"campo": this.campos[campo].campo,
				"ascendente": (this.ordenes[campo] == 1)
			};
		}
	}
	
	return result;
}

function _calcularMetadataConsulta() {
	var metadataConsulta = {
		"tamanoMuestra": this.tamanoMuestra,
		"tamanoSubconjunto": this.tamanoSubconjunto,
		"metadataOrdenaciones": this.calcularOrdenaciones(),
		"metadataCondiciones": this.calcularCondiciones()
	};
	
	return metadataConsulta;
}

function _showMenu(event, element) {
	var divMenu = $(".divTableHeaderMenu");
	divMenu.show();
	divMenu.offset({ top: divMenu.offset().top, left: $(element).position().left });
}

function _mostrarOcultarFiltros(eventObject) {
	var promiseFunction = function(resolve, reject) {
		var target = $(eventObject.target);
		
		if (target.hasClass("divFiltrosHandleCenterTopLine")) {
			target = target.parent();
		}
		
		var divFiltros = $(target.parent().parent().siblings(".divFiltros")[0]);
		var divFiltrosHandle = target.parent().parent();
		
		if (divFiltros.is(":visible")) {
			divFiltros.hide();
		} else {
			divFiltros.show();
		}
	};
	
	return new Promise(promiseFunction.bind(this));
}