$(document).ready(init);

function init() {
	hideBackground();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
    }).then(function(data) {
    	if (data != null) {
	    	var html = 
	    		"<div class='divMenuIcons'>";
//	    	
//	    	var currentURL = window.location.href;
//	    	var comparator = 
//	    		currentURL.substring(
//	    			currentURL.indexOf("/LogisticaWEB/"), 
//	    			currentURL.lenght
//	    		);
//	    	
//	    	var first = true;
//	    	var active = false;
//	    	var activeParentItemId = 0;
	    	for (var i=0; i<data.menus.length; i++) {
	    		var menu = data.menus[i];
	    		
	    		if (!menu.url.includes("mobile")) {
//		    		active = menu.url == comparator;
//		    		if (active) {
//		    			activeParentItemId = menu.padre;
//		    		}
//		    	
		    		if (menu.padre != null) {
		    			html +=
			    			"<div class='divMenuItemIcon'>"
			    				+ "<div class='divMenuItemIconImage'>"
			    					+ "<a href='" + menu.url + "'"
										+ " id='" + menu.id + "'"
										+ " url='" + menu.url + "'"
									+ ">" 
										+ "<img class='imgMenuItemIcon'"
											+ " src='/LogisticaWEB/Stream?fn=diamantech.png'"
										+ "></img>"
									+ "</a>" 
								+ "</div>"
								+ "<div class='divMenuItemIconText'>"
			    					+ menu.titulo
			    				+ "</div>"
			    			+ "</div>"
		    		} else {
//		    			html +=
//		    				"<div id='" + menu.id + "' class='closedMenuBarParentItem'>"
//		    					+ "<div>"
//		    						+ "<div class='divMenuParent'>"
//										+ menu.titulo
//									+ "</div>"
//								+ "</div>"
//							+ "</div>";
		    		}
	    		}
	    		
	    		first = false;
	    	}
	    	
	    	html +=
	    		"</div>"
	    	
	    	$(".divBody")
	    		.append(html);
	    	
//	    	$(".divMenuBar")
//	    		.find(".p" + activeParentItemId)
//	    		.show();
//	    	
//	    	$(".divMenuBar")
//	    		.find("#" + activeParentItemId)
//	    		.removeClass("closedMenuBarParentItem")
//	    		.addClass("openMenuBarParentItem");
    	}
    });
}

function hideBackground() {
	$("#divModalBackground").hide();
}

function showBackground() {
	$("#divModalBackground").show();
}