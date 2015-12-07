$(document).ready(function() {
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				var html = 
					"<div class='inactiveMenuBarItem'>"
						+ "<div id='divLogo'><img id='imgLogo' src='/LogisticaWEB/images/logo-vos-bw.png'></img></div>"
					+ "</div>";
				
				for (var i=0; i<data.menus.length; i++) {
					html += 
						"<div class='" + (i == 0 ? "activeMenuBarItem" : "inactiveMenuBarItem") + "'>"
							+ "<div>"
								+ "<a href='#' onclick='javascript:menuItemOnClick(event, this)'"
									+ " id='" + data.menus[i].id + "'"
									+ " url='" + data.menus[i].url + "'>" + data.menus[i].titulo + "</a>"
							+ "</div>"
						+ "</div>";
					if (i == 0) {
						$("#iFrameActivePage").attr("src", data.menus[i].url);
					}
				}
				
				html += 
					"<div class='divUserInfo'>"
						+ "<div class='divLogout' style='float: right;' onclick='javascript:divLogoutOnClick(event, this)'>&nbsp;</div>"
						+ "<div id='divActiveUser' style='float: right;'>" + data.nombre + "</div>"
						+ "<div style='float: right;'>Usuario:</div>"
					+ "</div>";
				
				$(".divMenuBar").append(html);
				
				$(".divUserInfo").width($(".divUserInfo").width() - (80 * data.menus.length));
			}, async: false
		}
	);
});

function menuItemOnClick(event, element) {
	window.frames[0].location = element.getAttribute("url");
	
	var active = $(".activeMenuBarItem");
	active.removeClass("activeMenuBarItem");
	active.addClass("inactiveMenuBarItem");
	
	element.parentNode.parentNode.setAttribute("class", "activeMenuBarItem");
}

function divLogoutOnClick() {
	SeguridadDWR.logout(
		{
			callback: function(data) {
				window.location = "/LogisticaWEB/";
			}, async: false
		}
	);
}