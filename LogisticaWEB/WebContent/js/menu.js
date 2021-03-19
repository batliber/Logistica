$(document).ready(init);

function init() {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
	}).then(function(data) {
		if (data != null) {
			var html = "";
			
			var currentURL = window.location.href;
			var comparator = 
				currentURL.substring(
					currentURL.indexOf("/LogisticaWEB/"), 
					currentURL.lenght
				);
			
			var first = true;
			var active = false;
			var activeParentItemId = 0;
			for (var i=0; i<data.menus.length; i++) {
				var menu = data.menus[i];
				
				if (!menu.url.includes("mobile")) {
					active = menu.url == comparator;
					if (active) {
						activeParentItemId = menu.padre;
					}
					
					if (menu.padre != null) {
						html +=
							"<div class='" + (active ? "activeMenuBarItem" : "inactiveMenuBarItem") + " p" + menu.padre + "'"
								+ " style='display:none'"
							+ ">"
								+ "<div>"
									+ "<a href='" + menu.url + "'"
										+ " id='" + menu.id + "'"
										+ " url='" + menu.url + "'"
										+ " class='aMenuLeaf'"
									+ ">"
										+ menu.titulo
									+ "</a>"
								+ "</div>"
							+ "</div>";
					} else {
						html +=
							"<div id='" + menu.id + "' class='closedMenuBarParentItem'>"
								+ "<div>"
									+ "<div class='divMenuParent'>"
										+ menu.titulo
									+ "</div>"
								+ "</div>"
							+ "</div>";
					}
				}
				
				first = false;
			}
			
			$(".divMenuBar")
				.append(html)
				.children(".closedMenuBarParentItem")
				.click(divMenuParentOnClick);
			
			$(".divMenuBar")
				.find(".p" + activeParentItemId)
				.show();
			
			$(".divMenuBar")
				.find("#" + activeParentItemId)
				.removeClass("closedMenuBarParentItem")
				.addClass("openMenuBarParentItem");
		}
	});
}

function divLogoutOnClick() {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/logout",   
	}).then(function(data) {
		window.location = "/LogisticaWEB/";
	});
}

function divMenuParentOnClick(eventObject) {
	var menuItemDIV = $(eventObject.target).parent().parent();
	
	if (menuItemDIV.attr("class") == "openMenuBarParentItem") {
		menuItemDIV.removeClass("openMenuBarParentItem");
		menuItemDIV.addClass("closedMenuBarParentItem");
		
		$(".divMenuBar")
			.find(".p" + menuItemDIV.attr("id"))
			.hide();
	} else {
		menuItemDIV.removeClass("closedMenuBarParentItem");
		menuItemDIV.addClass("openMenuBarParentItem");
		
		$(".divMenuBar")
			.find(".p" + menuItemDIV.attr("id"))
			.show();
	}
}