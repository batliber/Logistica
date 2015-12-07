function Grid(element, campos, reloadListener, trOnClickListener) {
	this.element = element;
	this.campos = campos;
	this.reloadListener = reloadListener;
	this.trOnClickListener = trOnClickListener;
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
		"<table id='table' border='0' cellspacing='0' cellpadding='0'>"
			+ "<thead>"
				+ "<tr>";
	
	var width = 21;
	for (var campo in this.filtroDinamico.campos) {
		if (this.filtroDinamico.campos[campo].ancho == null) {
			switch (this.filtroDinamico.campos[campo].tipo) {
				case __TIPO_CAMPO_NUMERICO:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : 55 + 1;
					
					break;
				case __TIPO_CAMPO_STRING:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : 110 + 1;
					
					break;
				case __TIPO_CAMPO_FECHA:
					width += this.filtroDinamico.campos[campo].oculto ? 0 : 65 + 1;
					
					break;
			}
		} else {
			width += this.filtroDinamico.campos[campo].ancho;
		}
		
		html += 
					"<td id='" + campo + "'"
						+ " class='td" + this.filtroDinamico.campos[campo].tipo + "NOO'"
						+ (this.filtroDinamico.campos[campo].ancho != null ? " style='width: " + this.filtroDinamico.campos[campo].ancho + "px;'" : "")
						+ (this.filtroDinamico.campos[campo].oculto ? " style='display: none;'" : "")
						+ " onclick='javascript:grid.filtroDinamico.agregarOrden(event, this)'>" 
						+ this.filtroDinamico.campos[campo].abreviacion 
					+ "</td>";
	}
	
	html += 
				"</tr>"
			+ "</thead>"
			+ "<tbody>"
				+ "<tr>";
	
	for (var campo in this.filtroDinamico.campos) {
		if (!this.filtroDinamico.campos[campo].oculto) {
			html += 
					"<td>&nbsp;</td>";
		}
	}
	
	html +=
				"</tr>"
			+ "</tbody>"
			+ "<tfoot>"
				+ "<tr>"
					+ "<td class='tdCantidadRegistrosLabel'><div>Cantidad de registros:</div></td>"
					+ "<td id='tdCantidadRegistrosValor'><div id='divCantidadRegistros'>&nbsp;</div></td>"
				+ "</tr>"
			+ "</tfoot>"
		+ "</table>";

	this.element.innerHTML = html;
	
	var pesosElement = $(this.element);
	
	pesosElement.css("width", width);
	
	this.table = pesosElement.children("table");
	this.table.css("width", width);
	this.table.children("tbody").css("width", width - 3);
	this.table.find("tfoot > tr > #tdCantidadRegistrosValor").css("width", width - 17 - 120 - 6 );
};

Grid.prototype.reload = function(data) {
	this.table.find("tbody:last > tr").remove();
	
	for (var i=0; i<data.registrosMuestra.length; i++) {
		var registroMuestra = data.registrosMuestra[i];
		
		var html = 
			"<tr id='" + registroMuestra.id + "'" 
				+ (registroMuestra.contrato != null ?  " cid='" + registroMuestra.contrato.id + "'" : "")
				+ ">";
//				+ " onclick='javascript:grid.trOnClickListener(event, this)'>";
		
		for (var campo in this.filtroDinamico.campos) {
			var value = null;
			var formattedValue = null;
			
			try {
				value = eval("registroMuestra." + this.filtroDinamico.campos[campo].campo);
		    } catch(e) {
		        value = null;
		    }
			
			switch (this.filtroDinamico.campos[campo].tipo) {
				case __TIPO_CAMPO_NUMERICO:
					formattedValue = value;
					
					break;
				case __TIPO_CAMPO_STRING:
					formattedValue = value;
					
					break;
				case __TIPO_CAMPO_FECHA:
					if (value != null) {
						formattedValue = formatShortDate(value);
					}
					
					break;
			}
			
			html += 
				"<td class='" + campo + "'" 
					+ (this.filtroDinamico.campos[campo].oculto ? " style='display: none;'" : "") + ">"
					+ "<div class='div" + this.filtroDinamico.campos[campo].tipo + "'"
						+ (this.filtroDinamico.campos[campo].ancho != null ? " style='width: " + (this.filtroDinamico.campos[campo].ancho - 3) + "px;'" : "")
						+ " title='" + (formattedValue != null ? formattedValue : "") + "'>" 
						+ (formattedValue != null ? formattedValue : "&nbsp;")
					+ "</div>"
				+ "</td>";
		}
		
		html += 
			+ "</tr>";
		
		this.table.children("tbody:last").append(html);
	}
	
	$("#divCantidadRegistros").text(data.cantidadRegistros);
	
	this.table.find("tbody:last > tr:odd").css("background-color", "#F8F8F8");
	this.table.find("tbody:last > tr:odd").hover(
		function() {
			$(this).css("background-color", "orange");
		},
		function() {
			$(this).css("background-color", "#F8F8F8");
		}
	);
	this.table.find("tbody:last > tr:even").css("background-color", "#FFFFFF");
	this.table.find("tbody:last > tr:even").hover(
		function() {
			$(this).css("background-color", "orange");
		},
		function() {
			$(this).css("background-color", "#FFFFFF");
		}
	);
	this.table.find("tbody:last > tr").click(this.trOnClickListener);
};