function FormularioDinamico(element, mode, campos, layoutData) {
	this.element = element;
	this.mode = mode;
	this.campos = campos;
	this.layoutData = layoutData;
}

FormularioDinamico.prototype.rebuild = function() {
	var html = "";
	
	var fila = 0;
	for (var campo in this.campos) {
		if (fila % 15 == 0) {
			if (fila >= 0) {
				html += 
					"</div>";
			}
			html += 
				"<div class='divFormColumn'/>";
		}
		
		html += 
			"<div class='divCampo'>"
				+ "<div class='divFormLabelExtended'>" + this.campos[campo].etiqueta + ":</div>";
		
		switch (this.campos[campo].mode) {
			case __FORM_FIELD_MODE_EDIT:
				switch (this.campos[campo].tipo) {
					case __TIPO_CAMPO_FORMULARIO_NUMERICO:
						html += "<div class='divFormValue'><input type='text' campoid='" + this.campos[campo].campo + "'/></div>";
						
						break;
					case __TIPO_CAMPO_FORMULARIO_STRING:
						html += "<div class='divFormValue'><input type='text' campoid='" + this.campos[campo].campo + "'/></div>";
						
						break;
					case __TIPO_CAMPO_FORMULARIO_TEXT:
						html += "<div class='divFormValue'><textarea campoid='" + this.campos[campo].campo + "'></textarea></div>";
						
						break;
					case __TIPO_CAMPO_FORMULARIO_FECHA:
						html += "<div class='divFormValue'><input type='text' campoid='" + this.campos[campo].campo + "'/></div>";
						
						break;
					case __TIPO_CAMPO_FORMULARIO_SELECCION:
						html += 
							"<div class='divFormValue'>"
								+ "<select campoid='" + this.campos[campo].campo + "'>"
									+ "<option value='0'>Seleccione...</option>";
						
						var opciones = this.campos[campo].listOptions();
						for (var i=0; i<opciones.length; i++) {
							html += 
								"<option value='" + opciones[i].valor + "'>" + opciones[i].etiqueta + "</option>";
						}
						
						html +=
								"</select>"
							+ "</div>";
						
						break;
				}
				
				break;
			case __FORM_FIELD_MODE_READ:
			default:
				html += 
					"<div class='divFormValue'><div type='text' campoid='" + this.campos[campo].campo + "'>&nbsp;</div></div>";
				
				break;
		}
		
		
		html += "</div>";
		
		fila++;
	}
	
	this.element.innerHTML = html;
	
	for (var campo in this.campos) {
		if (this.campos[campo].onChange != null) {
			switch (this.campos[campo].tipo) {
				case __TIPO_CAMPO_FORMULARIO_NUMERICO:
					$("input[campoid='" + this.campos[campo].campo + "']").change(this.campos[campo].onChange);
					
					break;
				case __TIPO_CAMPO_FORMULARIO_STRING:
					$("input[campoid='" + this.campos[campo].campo + "']").change(this.campos[campo].onChange);
					
					break;
				case __TIPO_CAMPO_FORMULARIO_TEXT:
					$("textarea[campoid='" + this.campos[campo].campo + "']").change(this.campos[campo].onChange);
					
					break;
				case __TIPO_CAMPO_FORMULARIO_FECHA:
					$("input[campoid='" + this.campos[campo].campo + "']").change(this.campos[campo].onChange);
					
					break;
				case __TIPO_CAMPO_FORMULARIO_SELECCION:
					$("select[campoid='" + this.campos[campo].campo + "']").change(this.campos[campo].onChange);
					
					break;
			}
		}
	}
};

FormularioDinamico.prototype.clear = function() {
	for (var campo in this.campos) {
		switch (this.campos[campo].mode) {
			case __FORM_FIELD_MODE_EDIT:
				switch (this.campos[campo].tipo) {
					case __TIPO_CAMPO_FORMULARIO_NUMERICO:
						$("input[campoid='" + this.campos[campo].campo + "']").val(null);
						
						break;
					case __TIPO_CAMPO_FORMULARIO_STRING:
						$("input[campoid='" + this.campos[campo].campo + "']").val(null);
						
						break;
					case __TIPO_CAMPO_FORMULARIO_TEXT:
						$("textarea[campoid='" + this.campos[campo].campo + "']").val(null);
						
						break;
					case __TIPO_CAMPO_FORMULARIO_FECHA:
						$("input[campoid='" + this.campos[campo].campo + "']").val(null);
						
						break;
					case __TIPO_CAMPO_FORMULARIO_SELECCION:
						$("select[campoid='" + this.campos[campo].campo + "']").val(0);
						
						break;
				}
				
				break;
			case __FORM_FIELD_MODE_READ:
			default:
				$("div[campoid='" + this.campos[campo].campo + "']").html("&nbsp;");
			
				break;
		}
	}
};

FormularioDinamico.prototype.setOptions = function(campoId, options) {
	for (var campo in this.campos) {
		if (this.campos[campo].campo == campoId) {
			$("select[campoid='" + this.campos[campo].campo + "'] > option:gt(0)").remove();
			
			for (var i=0; i<options.length; i++) {
				html += "<option value='" + options[i].valor + "'>" + options[i].etiqueta + "</option>";
			}
			
			$("select[campoid='" + this.campos[campo].campo + "'])").append(html);
			
			break;
		}
	}
};

FormularioDinamico.prototype.reload = function(object) {
	this.clear();
	
	for (var campo in this.campos) {
		var value = null;
		var formattedValue = null;
		
		switch (this.campos[campo].mode) {
			case __FORM_FIELD_MODE_EDIT:
				try {
					value = eval("object." + this.campos[campo].campo);
				} catch (e) {
					value = null;
				}
				
				switch (this.campos[campo].tipo) {
					case __TIPO_CAMPO_FORMULARIO_NUMERICO:
						formattedValue = value;
						
						$("input[campoid='" + this.campos[campo].campo + "']").val(formattedValue);
						
						break;
					case __TIPO_CAMPO_FORMULARIO_STRING:
						formattedValue = value;
						
						$("input[campoid='" + this.campos[campo].campo + "']").val(formattedValue);
						
						break;
					case __TIPO_CAMPO_FORMULARIO_TEXT:
						formattedValue = value;
						
						$("textarea[campoid='" + this.campos[campo].campo + "']").val(formattedValue);
						
						break;
					case __TIPO_CAMPO_FORMULARIO_FECHA:
						if (value != null) {
							formattedValue = formatShortDate(value);
						}
						
						$("input[campoid='" + this.campos[campo].campo + "']").val(formattedValue);
						
						break;
					case __TIPO_CAMPO_FORMULARIO_SELECCION:
						formattedValue = value;
						
						$("select[campoid='" + this.campos[campo].campo + "']").val(formattedValue);
						
						break;
				}
				
				break;
			case __FORM_FIELD_MODE_READ:
			default:
				try {
					value = eval("object." + this.campos[campo].campoAlternativo);
				} catch (e) {
					value = null;
				}
				
				switch (this.campos[campo].tipo) {
					case __TIPO_CAMPO_FORMULARIO_FECHA:
						if (value != null) {
							formattedValue = formatShortDate(value);
						}
						
						break;
					case __TIPO_CAMPO_FORMULARIO_NUMERICO:
					case __TIPO_CAMPO_FORMULARIO_STRING:
					case __TIPO_CAMPO_FORMULARIO_TEXT:
					case __TIPO_CAMPO_FORMULARIO_SELECCION:
					default:
						formattedValue = value;
						
						break;
				}
			
				$("div[campoid='" + this.campos[campo].campo + "']").html(formattedValue);
				
				break;
		}
	}
};