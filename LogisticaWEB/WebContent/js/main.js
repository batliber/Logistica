var links = {
	acm: "/LogisticaWEB/pages/acm/acm.jsp",
	procesos: "/LogisticaWEB/pages/procesos/procesos.jsp"
};

$(document).ready(function() {
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				$("#divActiveUser").text(data);
			}, async: false
		}
	);
});

function menuItemOnClick(event, element) {
	window.frames[0].location = links[element.id];
	
	var active = $(".activeMenuBarItem");
	active.removeClass("activeMenuBarItem");
	active.addClass("inactiveMenuBarItem");
	
	element.parentNode.parentNode.setAttribute("class", "activeMenuBarItem");
}

function divLogoutOnClick() {
	SeguridadDWR.logout(
		{
			callback: function(data) {
				window.location = "/LogisticaWEB";
			}, async: false
		}
	);
}