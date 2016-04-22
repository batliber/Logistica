function divLogoutOnClick() {
	SeguridadDWR.logout(
		{
			callback: function(data) {
				window.location = "/LogisticaWEB/";
			}, async: false
		}
	);
}