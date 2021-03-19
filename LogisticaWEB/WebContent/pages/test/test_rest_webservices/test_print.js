$(document).ready(init);

function init() {
	fillSelect(
		"selectMethod", 
		[{ id: "GET", value: "GET" }, { id: "POST", value: "POST" }],
		"id", 
		"value"
	);
	
	fillSelect(
		"selectParametersTemplate", 
		[{ id: "plain", value: "Plain" }, { id: "mc", value: "Metadata consulta" }],
		"id", 
		"value"
	);
	
	$("#selectParametersTemplate").val("plain");
}

function selectParametersTemplateOnChange(event, element) {
	var pesosElement = $(element);
	if (pesosElement.val() == "plain") {
		$("#textareaParameters").val("");
	} else {
		$("#textareaParameters").val(
			"{\"metadataCondiciones\":[],\"metadataOrdenaciones\":[],\"tamanoMuestra\":50,\"tamanoSubconjunto\":1}"
		);
	}
}

function inputSubmitOnClick(event, element) {
	if ($("#selectMethod").val() == "0") {
		alert("Seleccione un Method");
		return false;
	}
	
	$("#textareaResponse").val(null);
	
	$.ajax({
		url: $("#inputURL").val(),
		method: $("#selectMethod").val(),
		contentType: "application/json",
		data: $("#textareaParameters").val()
	}).then(function(data) {
		$("#textareaResponse").val(JSON.stringify(data));
	});
}