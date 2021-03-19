function divMenuOnClick(event, element) {
	if ($("#divMenuContent").css("display") == "none") {
		$("#divMenuContent").show();
	} else {
		$("#divMenuContent").hide();
	}
}

function divLogoutOnClick(event, element) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/logout",   
	}).then(function(data) {
		window.location = "/LogisticaWEB/pages/mobile/mlogin/mlogin.jsp";
	});
}