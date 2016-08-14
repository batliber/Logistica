function inputImprimirOnClick() {
	$("input[type='text']").css("background-color", "white");
	$("textarea").css("background-color", "white");
	$(".divA4Sheet").css("border", "none");
	$(".divPrintingButtonBar").hide();
	
	window.print();
}

function inputCancelarOnClick() {
	window.close();
}