function divMenuOnClick(event, element) {
	if ($("#divMenuContent").css("display") == "none") {
		$("#divMenuContent").show();
	} else {
		$("#divMenuContent").hide();
	}
}

function divLogoutOnClick(event, element) {
	SeguridadDWR.logout(
		{
			callback: function(data) {
				window.location = "/LogisticaWEB/pages/mobile/mlogin.jsp";
			}, async: false
		}
	);
}