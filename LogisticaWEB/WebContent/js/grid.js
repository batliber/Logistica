function Grid(element, campos, reloadListener, trOnClickListener) {
	this.__ANCHO_BORDE = 1;
	this.__ANCHO_SCROLL_VERTICAL = 20;
	this.__ANCHO_CAMPO_NUMERICO = 55 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_DECIMAL = 55 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_STRING = 110 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_FECHA = 65 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_FECHA_HORA = 85 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_FECHA_MES_ANO = 65 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_RELACION = 110 + this.__ANCHO_BORDE;
	this.__ANCHO_CAMPO_MULTIPLE = 110 + this.__ANCHO_BORDE;
	this.__ANCHO_ETIQUETA_CANTIDAD_REGISTROS = 120;
	
	this.__STATUS_LOADING = 0;
	this.__STATUS_LOADED = 1;
	
	this.element = element;
	this.campos = campos;
	this.reloadListener = reloadListener;
	this.trOnClickListener = trOnClickListener;
	this.status = this.__STATUS_LOADED;
	this.filtroDinamico = new FiltroDinamico(this.campos, this.reloadListener);
}

Grid.prototype.setCampos = function(campos) {
	this.campos = campos;
	this.filtroDinamico = new FiltroDinamico(this.campos, this.reloadListener);
	
	this.rebuild();
	this.reload();
};

Grid.prototype.rebuild = function() {
	var html =
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
				case __TIPO_CAMPO_RELACION:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_RELACION;
					
					break;
				case __TIPO_CAMPO_MULTIPLE:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : this.__ANCHO_CAMPO_MULTIPLE;
					
					break;
			}
		} else {
			width += this.filtroDinamico.campos[campo].ancho + this.__ANCHO_BORDE;
		}
		
		html +=
					"<div id='" + campo + "'"
						+ " class='divTableHeaderCell" + this.filtroDinamico.campos[campo].tipo + "NOO'"
						+ (this.filtroDinamico.campos[campo].ancho != null ? " style='width: " + this.filtroDinamico.campos[campo].ancho + "px;'" : "")
						+ (this.filtroDinamico.campos[campo].oculto ? " style='display: none;'" : "")
						+ " onclick='javascript:grid.filtroDinamico.agregarOrden(event, this)'>"
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
					"<div class='divTableBodyCell" + this.filtroDinamico.campos[campo].tipo + "'"
						+ " campo='" + campo + "'"
						+ (this.filtroDinamico.campos[campo].ancho != null ? " style='width: " + ((this.filtroDinamico.campos[campo].ancho - 2) * this.__ANCHO_BORDE)  + "px;'" : "")
						+ (this.filtroDinamico.campos[campo].oculto ? " style='display: none;'" : "")
						+ ">"
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
	
	// ancho(tbody) = ancho(table) - 3 * this.__ANCHO_BORDER;
	this.table.children(".divTableBody").css("width", width - 2 * this.__ANCHO_BORDE);
	this.table.children(".divTableBodyCover").css("width", width - this.__ANCHO_BORDE - this.__ANCHO_SCROLL_VERTICAL);
	
	// ancho(cantidadRegistrosValor) = ancho(table) - this.__ANCHO_SCROLL_VERTICAL - this.__ANCHO_ETIQUETA_CANTIDAD_REGISTROS - 3 * this.__ANCHO_BORDER
	this.table.find(".divTableFooter > .divTableFooterRow > .divTableFooterCell > #divCantidadRegistrosValue").css("width", width - 2 - this.__ANCHO_SCROLL_VERTICAL - this.__ANCHO_ETIQUETA_CANTIDAD_REGISTROS - 2 * this.__ANCHO_BORDE);
};

Grid.prototype.reload = function(data) {
	this.table.find(".divTableBody:last > .divTableBodyRow").remove();
	
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
				case __TIPO_CAMPO_RELACION:
					formattedValue = value != null && value != "" ? value : "&nbsp;";
					
					break;
				case __TIPO_CAMPO_MULTIPLE:
					formattedValue = value != null && value != "" ? value : "&nbsp;";
					
					break;
			}
		    
		    html += 
		    	"<div class='divTableBodyCell" + this.filtroDinamico.campos[campo].tipo + "'"
		    		+ " campo='" + campo + "'"
		    		+ (this.filtroDinamico.campos[campo].ancho != null ? 
		    			" style='width: " + ((this.filtroDinamico.campos[campo].ancho - 2) * this.__ANCHO_BORDE) + "px;'" 
		    			: "")
		    		+ (this.filtroDinamico.campos[campo].oculto ? 
		    			" style='display: none;'" 
		    			: "")
					+ " title='" + formattedValue + "'"
					+ (key != null ? 
						" clave='" + key + "'" 
						: "") 
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