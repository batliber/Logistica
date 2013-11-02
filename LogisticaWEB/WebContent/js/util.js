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
	    "0" + date.getFullYear(),
	    "0" + date.getHours(),
	    "0" + date.getMinutes()
	];
	
	for (var i=0; i<size; i++) {
		parts[i] = parts[i].substring(parts[i].length - 2);
	}
	
	return parts[0] + "/" + parts[1] + "/" + parts[2] + (size > 3 ? " " + parts[3] + ":" + parts[4] : "");
}