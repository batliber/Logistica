function Grid(element, campos, showFilters, reloadListener, trOnClickListener, tdOpenDetailListener, rowHeight) {
	this.__ANCHO_BORDE = 1;
	this.__ALTO_BORDE = 1;
	this.__ANCHO_SCROLL_VERTICAL = 20;
	this.__ANCHO_CAMPO_NUMERICO = 55 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_DECIMAL = 55 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_STRING = 110 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_FECHA = 65 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_FECHA_HORA = 85 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_FECHA_MES_ANO = 65 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_RELACION = 110 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_MULTIPLE = 110 + this.__ANCHO_BORDE;
<<<<<<< HEAD
	this.__ANCHO_CAMPO_BOOLEAN = 55 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_DETAIL = 15 + this.__ANCHO_BORDE;
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
	this.__ANCHO_ETIQUETA_CANTIDAD_REGISTROS = 120;
	
	this.__ALTO_FILA = 18;
	this.__FILAS = 16;
	
	this.__STATUS_LOADING = 0;
	this.__STATUS_LOADED = 1;
	
	this.element = element;
	this.campos = campos;
	this.showFilters = showFilters;
	this.reloadListener = reloadListener;
	this.trOnClickListener = trOnClickListener;
	this.tdOpenDetailListener = tdOpenDetailListener;
	this.rowHeight = rowHeight;
	this.status = this.__STATUS_LOADED;
	this.filtroDinamico = new FiltroDinamico(this, this.campos, this.reloadListener);
}

Grid.prototype.setCampos = function(campos) {
	this.campos = campos;
	this.filtroDinamico = new FiltroDinamico(this.campos, this.reloadListener);
	
	this.rebuild();
	this.reload();
};

Grid.prototype.rebuild = function() {
	var html = "";
	
	if (this.showFilters) {
		html += 
			"<div id='divFiltros'>"
				+ "<div class='divFormLabelExtended'>Tama&ntilde;o de muestra:</div>"
				+ "<div id='divTamanoMuestra'>"
					+ "<input type='text' id='inputTamanoMuestra' value='50' onchange='javascript:grid.filtroDinamico.tamanoMuestraOnChange(event)'/>"
				+ "</div>"
				+ "<div class='divFormLabelExtended'>Tama&ntilde;o subconjunto:</div>"
				+ "<div id='divTamanoSubconjunto'>"
					+ "<input type='text' id='inputTamanoSubconjunto' value='50' onchange='javascript:grid.filtroDinamico.tamanoSubconjuntoOnChange(event)'/>"
				+ "</div>"
				+ "<div id='divAgregarFiltroContainer'>"
					+ "<div class='divFormLabelExtended'>Agregar filtro:</div>"
					+ "<div id='divAgregarFiltro'>"
						+ "<input type='submit' value='' id='inputAgregarFiltro' onclick='javascript:grid.filtroDinamico.agregarFiltro(event, this)'/>"
					+ "</div>"
					+ "<div class='divFormLabelExtended'>Limpiar filtros:</div>"
					+ "<div id='divLimpiarFiltros'>"
						+ "<input type='submit' value='' id='inputLimpiarFiltros' onclick='javascript:grid.filtroDinamico.limpiarFiltros(event, this)'/>"
					+ "</div>"
					+ "<div class='divFormLabelExtended'>Seleccionar columnas:</div>"
					+ "<div id='divSeleccionarColumnas'>"
						+ "<input type='submit' value='' id='inputSeleccionarColumnas' onclick='javascript:grid.filtroDinamico.seleccionarColumnas(event, this)'/>"
						+ "<div id='divIFrameSeleccionColumnas'>"
							+ "<div class='divTitleBar'>"
								+ "<div class='divTitleBarText' style='float:left;'>Columnas</div>"
								+ "<div class='divTitleBarCloseButton' onclick='javascript:closePopUp(event, this.parentNode.parentNode)'>&nbsp;</div>"
							+ "</div>"
							+ "<div id='divSeleccionColumnas'>"
								+ "<div class='divPopupWindow'>"
									+ "<div id='divSeleccionColumnasLista'>&nbsp;</div>"
									+ "<div id='divSeleccionColumnasBotonera'>"
										+ "<input type='submit' id='inputSeleccionColumnasAceptar' value=''"
											+ " onclick='javascript:grid.filtroDinamico.actualizarColumnas(event, this)'/>"
									+ "</div>"
								+ "</div>"
							+ "</div>"
						+ "</div>"
					+ "</div>"
				+ "</div>"
			+ "</div>";
	}
	
	html +=
		"<div id='table' class='divTable'>"
			+ "<div class='divTableHeader'>"
				+ "<div class='divTableHeaderRow'>";
	
		
	var width = this.__ANCHO_SCROLL_VERTICAL;
	for (var campo in this.filtroDinamico.campos) {
		if (this.filtroDinamico.campos[campo].ancho == null) {
			switch (this.filtroDinamico.campos[campo].tipo) {
				case __TIPO_CAMPO_NUMERICO:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_NUMERICO;
					
					break;
				case __TIPO_CAMPO_DECIMAL:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_DECIMAL;
					
					break;
				case __TIPO_CAMPO_DECIMAL:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_DECIMAL;
					
					break;
				case __TIPO_CAMPO_STRING:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_STRING;
					
					break;
				case __TIPO_CAMPO_FECHA:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_FECHA;
					
					break;
				case __TIPO_CAMPO_FECHA_HORA:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_FECHA_HORA;
					
					break;
				case __TIPO_CAMPO_FECHA_MES_ANO:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_FECHA_MES_ANO;
					
					break;
<<<<<<< HEAD
				case __TIPO_CAMPO_BOOLEAN:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_BOOLEAN;
					
					break;
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
				case __TIPO_CAMPO_RELACION:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_RELACION;
					
					break;
				case __TIPO_CAMPO_MULTIPLE:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_MULTIPLE;
					
					break;
				case __TIPO_CAMPO_MULTIPLE:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_MULTIPLE;
					
					break;
				case __TIPO_CAMPO_DETAIL:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_DETAIL;
					
					break;
			}
		} else {
			width += this.filtroDinamico.campos[campo].oculto ? 0 : this.filtroDinamico.campos[campo].ancho + this.__ANCHO_BORDE;
		}
		
		var height = this.__ALTO_FILA * (this.rowHeight != null ? this.rowHeight : this.__FILAS) - 2 * this.__ALTO_BORDE;
		
		html +=
					"<div id='" + campo + "'"
						+ " class='divTableHeaderCell" + this.filtroDinamico.campos[campo].tipo + "NOO'";
		
		if (this.filtroDinamico.campos[campo].oculto) {
			html += 
						" style='display: none;'";
		} else if (this.filtroDinamico.campos[campo].ancho != null) {
			html +=
						" style='width: " + this.filtroDinamico.campos[campo].ancho + "px;'";
		}
		
		if (this.filtroDinamico.campos[campo].tipo == __TIPO_CAMPO_DETAIL) {
			html +=
						">"
						+ "<div class='divTableHeaderCellContent'>"
							+ "<div class='divTableHeaderCellText'>&nbsp;</div>"
						+ "</div>"
					+ "</div>";
		} else {
			html +=
						" onclick='javascript:grid.filtroDinamico.agregarOrden(event, this)'>"
						+ "<div class='divTableHeaderCellContent'>"
							+ "<div class='divTableHeaderCellText'"
								+ "title='" + this.filtroDinamico.campos[campo].descripcion + "'"
							+ ">"
								+ (this.filtroDinamico.campos[campo].abreviacion != null ? 
									this.filtroDinamico.campos[campo].abreviacion
									: this.filtroDinamico.campos[campo].descripcion)
							+ "</div>"
//							+ "<div class='divTableHeaderCellMenu'" 
//								+ " onclick='javascript:grid.filtroDinamico.showMenu(event, this)'>&nbsp;</div>"
						+ "</div>"
					+ "</div>";
		}
	}
	
	html += 
				"</div>"
			+ "</div>"
//			+ "<div class='divTableHeaderMenu'>"
//				+ "<div>Orden ascendente</div>"
//				+ "<div>Orden descendente</div>"
//				+ "<div>"
//					+ "<input type='button' value='Aceptar'/>"
//					+ "<input type='button' value='Cancelar'/>"
//				+ "</div>"
//			+ "</div>"
			+ "<div class='divTableBodyCover'><img class='imgTableBodyCoverLoading' src='/LogisticaWEB/images/loading.gif'/></div>"
			+ "<div class='divTableBody'>"
				+ "<div class='divTableBodyRow'>";
	
	for (var campo in this.filtroDinamico.campos) {
		html +=
					"<div class='divTableBodyCell" + this.filtroDinamico.campos[campo].tipo + "' campo='" + campo + "'";
		
		if (this.filtroDinamico.campos[campo].oculto) {
			html += 
						" style='display: none;'";
		} else if (this.filtroDinamico.campos[campo].ancho != null) {
			html +=
						" style='width: " + ((this.filtroDinamico.campos[campo].ancho - 2) * this.__ANCHO_BORDE) + "px;'";
		}
		
		html +=
					">"
						+ "&nbsp;"
					+ "</div>";
	}
	
	html +=
				"</div>"
			+ "</div>"
			+ "<div class='divTableFooter'>"
				+ "<div class='divTableFooterRow'>"
					+ "<div class='divTableFooterCell'><div id='divCantidadRegistrosLabel'>Registros:</div></div>"
					+ "<div class='divTableFooterCell'><div id='divCantidadRegistrosValue'>&nbsp;</div></div>"
				+ "</div>"
			+ "</div>"
		+ "</div>";

	this.element.innerHTML = html;
	
	var pesosElement = $(this.element);
	
	// ancho(table) = this.__ANCHO_SCROLL_VERTICAL + (ANCHO_COLUMNAS)
	pesosElement.css("width", width + 1);
	
	this.table = pesosElement.children(".divTable");
	this.table.css("width", width - 1);
	this.table.css("height", height);
	
	// ancho(tbody) = ancho(table) - 3 * this.__ANCHO_BORDER;
	this.table.children(".divTableBody").css("width", width - 2 * this.__ANCHO_BORDE);
	this.table.children(".divTableBody").css("height", height - 2 * this.__ALTO_FILA - this.__ALTO_BORDE);
	this.table.children(".divTableBodyCover").css("width", width - this.__ANCHO_BORDE - this.__ANCHO_SCROLL_VERTICAL);
	this.table.children(".divTableBodyCover").css("height", height - 2 * this.__ALTO_FILA);
	
	// ancho(cantidadRegistrosValor) = ancho(table) - this.__ANCHO_SCROLL_VERTICAL - this.__ANCHO_ETIQUETA_CANTIDAD_REGISTROS - 3 * this.__ANCHO_BORDER
	this.table.find(".divTableFooter > .divTableFooterRow > .divTableFooterCell > #divCantidadRegistrosValue").css(
		"width", width - 2 - this.__ANCHO_SCROLL_VERTICAL - this.__ANCHO_ETIQUETA_CANTIDAD_REGISTROS - 2 * this.__ANCHO_BORDE
	);
};

Grid.prototype.reload = function(data) {
	this.table.find(".divTableBody:last > div").remove();
	
	for (var i=0; i<data.registrosMuestra.length; i++) {
		var registroMuestra = data.registrosMuestra[i];
		
		var html = 
			"<div class='divTableBodyRow' id='" + registroMuestra.id + "'"
				+ (registroMuestra.contrato != null ? " cid='" + registroMuestra.contrato.id + "'" : "")
				+ ">";
		
		for (var campo in this.filtroDinamico.campos) {
			var key = null;
			var value = null;
			var formattedValue = null;
			
			try {
				value = eval("registroMuestra." + this.filtroDinamico.campos[campo].campo);
				
				if (this.filtroDinamico.campos[campo].tipo == __TIPO_CAMPO_RELACION) {
					key = eval("registroMuestra." + this.filtroDinamico.campos[campo].clave);
				}
			} catch(e) {
				key = null;
		        value = null;
		    }
			
		    switch (this.filtroDinamico.campos[campo].tipo) {
				case __TIPO_CAMPO_NUMERICO:
					formattedValue = value != null && value !== "" ? value : "&nbsp;";
					
					break;
				case __TIPO_CAMPO_DECIMAL:
<<<<<<< HEAD
					var decimales = 2;
					if (this.filtroDinamico.campos[campo].decimales != null) {
						decimales = this.filtroDinamico.campos[campo].decimales;
					} 
					
					formattedValue = value != null && value !== "" ? formatDecimal(value, decimales) : "&nbsp;";
=======
					formattedValue = value != null && value !== "" ? formatDecimal(value, 2) : "&nbsp;";
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
					
					break;
				case __TIPO_CAMPO_STRING:
					formattedValue = value != null && value != "" ? value : "&nbsp;";
					
					break;
				case __TIPO_CAMPO_FECHA:
					formattedValue = value != null && value != "" ? formatShortDate(value) : "&nbsp;";
					
					break;
				case __TIPO_CAMPO_FECHA_HORA:
					formattedValue = value != null && value != "" ? formatLongDate(value) : "&nbsp;";
					
					break;
				case __TIPO_CAMPO_FECHA_MES_ANO:
					formattedValue = value != null && value != "" ? formatMonthYearDate(value) : "&nbsp;";
					
					break;
<<<<<<< HEAD
				case __TIPO_CAMPO_BOOLEAN:
					formattedValue = value != null && value !== "" ? formatBoolean(value) : "&nbsp;";
					
					break;
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
				case __TIPO_CAMPO_RELACION:
					formattedValue = value != null && value != "" ? value : "&nbsp;";
					
					break;
				case __TIPO_CAMPO_MULTIPLE:
					formattedValue = value != null && value != "" ? value : "&nbsp;";
					
					break;
				case __TIPO_CAMPO_MULTIPLE:
					formattedValue = value != null && value != "" ? value : "&nbsp;";
					
					break;
				case __TIPO_CAMPO_DETAIL:
					formattedValue = "&nbsp;";
					
					break;
			}
		    
		    html +=
				"<div class='divTableBodyCell" + this.filtroDinamico.campos[campo].tipo + "' campo='" + campo + "'";
	
			if (this.filtroDinamico.campos[campo].oculto) {
				html += 
					" style='display: none;'";
			} else if (this.filtroDinamico.campos[campo].ancho != null) {
				html +=
					" style='width: " + ((this.filtroDinamico.campos[campo].ancho - 2) * this.__ANCHO_BORDE) + "px;'";
			}
			
			if (this.filtroDinamico.campos[campo].tipo == __TIPO_CAMPO_DETAIL) {
				html += 
					" onclick='javascript:grid.openDetail(event, this)'"
			}
			
			html +=
					" title='" + formattedValue + "'"
					+ (key != null ? " clave='" + key + "'" : "") 
				+ ">" 
					+ formattedValue
				+ "</div>";
		}
		
		html +=
			"</div>";
		
		this.table.children(".divTableBody:last").append(html);
	}
	
	if (data.cantidadRegistros != null) {
		this.setCount(data.cantidadRegistros);
	}
	
	this.table.find(".divTableBody:last > .divTableBodyRow:odd").css("background-color", "#F8F8F8");
	this.table.find(".divTableBody:last > .divTableBodyRow:odd").hover(
		function() {
			$(this).css("background-color", "orange");
		},
		function() {
			$(this).css("background-color", "#F8F8F8");
		}
	);
	this.table.find(".divTableBody:last > .divTableBodyRow:even").css("background-color", "#FFFFFF");
	this.table.find(".divTableBody:last > .divTableBodyRow:even").hover(
		function() {
			$(this).css("background-color", "orange");
		},
		function() {
			$(this).css("background-color", "#FFFFFF");
		}
	);
	this.table.find(".divTableBody:last > .divTableBodyRow").click(this.trOnClickListener);
	
	this.setStatus(this.__STATUS_LOADED);
};

Grid.prototype.setCount = function(data) {
	$("#divCantidadRegistrosValue").text(data != null ? data : 0);
};

Grid.prototype.getCount = function() {
	return $("#divCantidadRegistrosValue").text();
};

Grid.prototype.setStatus = function(status) {
	this.status = status;
	
	if (this.status == this.__STATUS_LOADING) {
		this.table.children(".divTableBodyCover").show();
	} else if (this.status == this.__STATUS_LOADED) {
		this.table.children(".divTableBodyCover").hide();
	}
};

Grid.prototype.openDetail = function(event, element) {
	var divRow = $($(element).parent()[0]);
	var divCell = $(element);
	
	var divCellClass = divCell.attr("class");
	
	if (divCellClass == "divTableBodyCell10") {
		divCell.attr("class", "divTableBodyCell10Open");
		
		var campoParent = divCell.attr("campo");
		var camposDetail = this.filtroDinamico.campos[campoParent].detail;
		var data = this.tdOpenDetailListener(divRow);
		
		var html =
					"<div class='divTableHeaderDetailRow' rid='" + divRow.attr("id") + "'>";
		
		var width = this.__ANCHO_SCROLL_VERTICAL;
		for (var campoHeader in camposDetail) {
			if (camposDetail[campoHeader].ancho == null) {
				switch (camposDetail[campoHeader].tipo) {
					case __TIPO_CAMPO_NUMERICO:
						width += camposDetail[campoHeader].oculto ? 0 : this.__ANCHO_CAMPO_NUMERICO;
						
						break;
					case __TIPO_CAMPO_DECIMAL:
						width += camposDetail[campoHeader].oculto ? 0 : this.__ANCHO_CAMPO_DECIMAL;
						
						break;
					case __TIPO_CAMPO_STRING:
						width += camposDetail[campoHeader].oculto ? 0 : this.__ANCHO_CAMPO_STRING;
						
						break;
					case __TIPO_CAMPO_FECHA:
						width += camposDetail[campoHeader].oculto ? 0 : this.__ANCHO_CAMPO_FECHA;
						
						break;
					case __TIPO_CAMPO_FECHA_HORA:
						width += camposDetail[campoHeader].oculto ? 0 : this.__ANCHO_CAMPO_FECHA_HORA;
						
						break;
					case __TIPO_CAMPO_FECHA_MES_ANO:
						width += camposDetail[campoHeader].oculto ? 0 : this.__ANCHO_CAMPO_FECHA_MES_ANO;
						
						break;
					case __TIPO_CAMPO_BOOLEAN:
						width += camposDetail[campoHeader].oculto ? 0 : this.__ANCHO_CAMPO_BOOLEAN;
						
						break;
				}
			} else {
				width += camposDetail[campoHeader].oculto ? 0 : camposDetail[campoHeader].ancho + this.__ANCHO_BORDE;
			}
			
			html +=
						"<div id='" + campoHeader + "'"
							+ " class='divTableHeaderCell" + camposDetail[campoHeader].tipo + "NOO'";
			
			if (camposDetail[campoHeader].oculto) {
				html += 
							" style='display: none;'";
			} else if (camposDetail[campoHeader].ancho != null) {
				html +=
							" style='width: " + camposDetail[campoHeader].ancho + "px;'";
			}
			
			html +=
						">"
							+ "<div class='divTableHeaderCellText'"
								+ "title='" + camposDetail[campoHeader].descripcion + "'"
							+ ">"
								+ (camposDetail[campoHeader].abreviacion != null ? 
									camposDetail[campoHeader].abreviacion
									: camposDetail[campoHeader].descripcion)
							+ "</div>"
						+ "</div>"
		}
		
		html += 
				"</div>";
		
		for (var i=0; i<data.length; i++) {
			var registro = data[i];
			
			html += 
				"<div class='divTableBodyDetailRow' rid='" + divRow.attr("id") + "'>";
			
			for (var campoBody in camposDetail) {
				var value = null;
				var formattedValue = null;
				
				try {
					value = eval("registro." + camposDetail[campoBody].campo);
				} catch(e) {
					value = null;
			    }
				
			    switch (camposDetail[campoBody].tipo) {
					case __TIPO_CAMPO_NUMERICO:
						formattedValue = value != null && value !== "" ? value : "&nbsp;";
						
						break;
					case __TIPO_CAMPO_DECIMAL:
						formattedValue = value != null && value !== "" ? formatDecimal(value, 2) : "&nbsp;";
						
						break;
					case __TIPO_CAMPO_STRING:
						formattedValue = value != null && value != "" ? value : "&nbsp;";
						
						break;
					case __TIPO_CAMPO_FECHA:
						formattedValue = value != null && value != "" ? formatShortDate(value) : "&nbsp;";
						
						break;
					case __TIPO_CAMPO_FECHA_HORA:
						formattedValue = value != null && value != "" ? formatLongDate(value) : "&nbsp;";
						
						break;
					case __TIPO_CAMPO_FECHA_MES_ANO:
						formattedValue = value != null && value != "" ? formatMonthYearDate(value) : "&nbsp;";
						
						break;
					case __TIPO_CAMPO_BOOLEAN:
						formattedValue = value != null && value !== "" ? formatBoolean(value) : "&nbsp;";
						
						break;
				}
			    
			    html +=
					"<div class='divTableBodyCell" + camposDetail[campoBody].tipo + "' campo='" + campoBody + "'";
		
				if (camposDetail[campoBody].oculto) {
					html += 
						" style='display: none;'";
				} else if (camposDetail[campoBody].ancho != null) {
					html +=
						" style='width: " + ((camposDetail[campoBody].ancho - 2) * this.__ANCHO_BORDE) + "px;'";
				}
				
				html +=
						" title='" + formattedValue + "'"
					+ ">"
						+ formattedValue
					+ "</div>";
			}
			
			html +=
				"</div>";
		}
		
		$(html).insertAfter(divRow);
	} else {
		divCell.attr("class", "divTableBodyCell10");
		$('.divTableHeaderDetailRow[rid="' + divRow.attr("id") + '"]').remove();
		$('.divTableBodyDetailRow[rid="' + divRow.attr("id") + '"]').remove();
	}
}