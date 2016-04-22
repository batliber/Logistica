function formatMonthYearDate(date) {
	var parts = [
 	    "0" + date.getDate(), 
 	    "0" + (date.getMonth() + 1), 
 	    date.getFullYear(),
 	    "0" + date.getHours(),
 	    "0" + date.getMinutes()
 	];
 	
 	return parts[1].substring(parts[1].length - 2) + "/" + parts[2];
}

function formatShortDate(date) {
	return formatParts(date, 3);
}

function formatLongDate(date) {
	return formatParts(date, 5);
}

function formatParts(date, size) {
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
}

function formatDecimal(number, decimals) {
	if (number != null) {
		return (new Number(number)).toFixed(decimals);
	} else {
		return "";
	}
}

function parseShortDate(string) {
	var parts = string.split("/");
	
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

function parseLongDate(string) {
	var parts = string.split(" ");
	var dateParts = parts[0].split("/");
	var hourParts = parts[1].split(":");
	
	return new Date(2000 + parseInt(dateParts[2]), dateParts[1] - 1, dateParts[0], hourParts[0], hourParts[1], hourParts.length > 2 ? hourParts[2] : 0, 0);
}