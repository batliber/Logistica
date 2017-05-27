var map = null;
var marker = null;

$(document).ready(function() {
	
});

function initMap() {
	var divMap = document.getElementById("divMap");
	
	var center = {lat: -34.9017, lng: -56.1634};
	
	map = new google.maps.Map(
		divMap, {
			center: center,
			zoom: 8
    	}
	);
	
	marker = new google.maps.Marker({
		position: center,
		map: map
	});
	
	map.addListener('center_changed', function() {
		var coords = map.getCenter();
		
		marker.setPosition(coords);
	});
	
	map.addListener('zoom_changed', function() {
		var coords = map.getCenter();
		
		marker.setPosition(coords);
	});
}