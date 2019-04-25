function formatMonthYearDate(date) {
	if (date != null) {
		var parts = [
	 	    "0" + date.getDate(), 
	 	    "0" + (date.getMonth() + 1), 
	 	    date.getFullYear(),
	 	    "0" + date.getHours(),
	 	    "0" + date.getMinutes()
	 	];
	 	
	 	return parts[1].substring(parts[1].length - 2) + "/" + parts[2];
	} else {
		return "";
	}
}

function formatShortDate(date) {
	return formatParts(date, 3);
}

function formatLongDate(date) {
	return formatParts(date, 5);
}

function formatRawDate(date) {
	if (date != null) {
		return date.getTime();
	} else {
		return "";
	}
}

function formatParts(date, size) {
	if (date != null) {
		var parts = [
		    "0" + date.getDate(), 
		    "0" + (date.getMonth() + 1), 
		    "" + date.getFullYear(),
		    "0" + date.getHours(),
		    "0" + date.getMinutes()
		];
		
		for (var i=0; i<size; i++) {
			if ((i != 2) || (size > 3)) {
				parts[i] = parts[i].substring(parts[i].length - 2);
			}
		}
		
		return parts[0] + "/" + parts[1] + "/" + parts[2] + (size > 3 ? " " + parts[3] + ":" + parts[4] : "");
	} else {
		return null;
	}
}

function formatDecimal(number, decimals) {
	if (number != null) {
		return (new Number(number)).toFixed(decimals);
	} else {
		return "";
	}
}

function formatBoolean(value) {
	if (value != null) {
		return (value ? "SI" : "NO");
	} else {
		return "";
	}
}

function parseShortDate(string) {
	if (string != null && string != "")	{
		var parts = string.split("/");
		
		return new Date(parts[2], parts[1] - 1, parts[0]);
	} else {
		return null;
	}
}

function parseLongDate(string) {
	if (string != null && string != "") {
		var parts = string.split(" ");
		var dateParts = parts[0].split("/");
		var hourParts = parts[1].split(":");
		
		return new Date(2000 + parseInt(dateParts[2]), dateParts[1] - 1, dateParts[0], hourParts[0], hourParts[1], hourParts.length > 2 ? hourParts[2] : 0, 0);
	} else {
		return null;
	}
}

/**
 * Carga las opciones del select con id selectId las opciones contenidas en data.
 * Toma el valor de la opción del atributo valueField y su respectiva descripción del atributo descriptionField de data.
 * Admite la carga de dos atributos extra que se cargan en el tag correspondiente con etiquetas extraAttributeName y 
 * extraAttribute2Name y valores extraAttributeValueField y extraAttribute2ValueField.
 */
function fillSelect(
	selectId, 
	data, 
	valueField, 
	descriptionField, 
	extraAttributeName, 
	extraAttributeValueField, 
	extraAttribute2Name, 
	extraAttribute2ValueField
) {
	$("#" + selectId + " > option").remove();
	
	var html = "<option value='0'>Seleccione...</option>";
	
	var values = {};
	
	for (var i=0; i<data.length; i++) {
		var value = null;
		var description = null;
		var extraValue = null;
		var extraValue2 = null;
		try {
			value = eval("data[" + i + "]." + valueField);
			description = eval("data[" + i + "]." + descriptionField);
		} catch (e) {
			value = null;
			description = null;
		}
		
		try {
			if (extraAttributeName != null) {
				extraValue = eval("data[" + i + "]." + extraAttributeValueField);
			}
		} catch (e) {
			extraValue = null;
		}
		
		try {
			if (extraAttribute2Name != null) {
				extraValue2 = eval("data[" + i + "]." + extraAttribute2ValueField)
			}
		} catch (e) {
			extraValue2 = null;
		}
		
		if (values[value] == null || !values[value]) {
			html += 
				"<option value='" + value + "'"
					+ (extraAttributeName != null ? " " + extraAttributeName + "='" + extraValue + "'" : "")
					+ (extraAttribute2Name != null ? " " + extraAttribute2Name + "='" + extraValue2 + "'" : "")
				+ ">" 
					+ description 
				+ "</option>";
			
			values[value] = true;
		}
	}
	
	$("#" + selectId).append(html);
}

/**
 * Carga en el control con id elementId, el dato data.field.
 * En caso de que el elemento con id elementId sea un DIV se carga en su texto el valor data.alternativeField.
 * Si se recibe un string en el parámetro extraAttributeName, se carga en su value el valor data.extraAttributeValueField.
 * Si se recibe una función en el parámetro formatter, se ejecuta antes de colocar el valor en el control.
 */
function populateField(
	elementId, 
	data, 
	field, 
	alternativeField, 
	extraAttributeName, 
	extraAttributeValueField, 
	formatter
) {
	var elementSuffix = elementId.substring(0, 1).toUpperCase() + elementId.substring(1, elementId.length);
	
	var value = null;
	var alternativeValue = null;
	var extraValue = null;
	try {
		value = eval("data." + field);
		alternativeValue = eval("data." + alternativeField);
		extraValue = eval("data." + extraAttributeValueField);
	} catch (e) {
		
	}
	
	if ($("#input" + elementSuffix).length > 0) {
		if ($("#input" + elementSuffix).attr("type") == "checkbox") {
			$("#input" + elementSuffix).prop("checked", value != null && value);
		} else {
			$("#input" + elementSuffix).val(formatter != null ? formatter(value) : value);
		}
		
		if (extraAttributeName != null) {
			$("#input" + elementSuffix).attr(extraAttributeName, extraValue);
		}
	} else if ($("#select" + elementSuffix).length > 0) {
		if (value != null) {
			$("#select" + elementSuffix).val(value);
		}
		if (extraAttributeName != null) {
			$("#select" + elementSuffix).attr(extraAttributeName, extraValue);
		}
	} else if ($("#textarea" + elementSuffix).length > 0) {
		$("#textarea" + elementSuffix).val(value);
		
		if (extraAttributeName != null) {
			$("#textarea" + elementSuffix).attr(extraAttributeName, extraValue);
		}
	} else if (alternativeValue != null && alternativeValue != "") {
		$("#div" + elementSuffix).html(formatter != null ? formatter(alternativeValue) : alternativeValue);
		
		if (extraAttributeName != null) {
			$("#div" + elementSuffix).attr(extraAttributeName, extraValue);
		}
	} else {
		$("#div" + elementSuffix).html("&nbsp;");
	}
}

/**
 * Oculta el control compuesto por DIV de etiqueta y DIV de valor.
 * Asume que los ids respectivos son de la forma divLabel + elementId y div + elementId. 
 */
function hideField(elementId) {
	var elementSuffix = elementId.substring(0, 1).toUpperCase() + elementId.substring(1, elementId.length);
	
	$("#divLabel" + elementSuffix).hide();
	$("#div" + elementSuffix).hide();
}

/**
 * Muestra el control compuesto por DIV de etiqueta y DIV de valor.
 * Asume que los ids respectivos son de la forma divLabel + elementId y div + elementId. 
 */
function showField(elementId) {
	var elementSuffix = elementId.substring(0, 1).toUpperCase() + elementId.substring(1, elementId.length);
	
	$("#divLabel" + elementSuffix).show();
	$("#div" + elementSuffix).show();
}