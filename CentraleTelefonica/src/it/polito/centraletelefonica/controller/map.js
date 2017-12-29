/*MAP AND CENTERS*/
var map = "";
var crocetta = "";
var lingotto = "";
var urb = "";
var lucento = "";

/* Response for travel time */
var res = "";

function initMap() {

//	urb = {
//		lat : 45.0702376,
//		lng : 7.684295600000041
//	};
//
//	crocetta = {
//		lat : 45.0692365,
//		lng : 7.674955599999976
//	};
//
//	lingotto = {
//		lat : 45.0158213,
//		lng : 7.627964499999962
//	};
//
//	lucento = {
//		lat : 45.1031432,
//		lng : 7.663839199999984
//	};
//
//	var opCentersImg = {
//		url : "https://www.florence-forestnews.com/wp-content/uploads/2017/08/blue_tower.png",
//		scaledSize : new google.maps.Size(30, 30), // scaled size
//		origin : new google.maps.Point(0, 0), // origin
//		anchor : new google.maps.Point(0, 0)
//	};
	
	center = {
			lat:45.0695848,
			lng:7.7045186
	}

	map = new google.maps.Map(document.getElementById('map'), {
		zoom : 12,
		center : center
	});

//	var marker = new google.maps.Marker({
//		position : urb,
//		map : map,
//		icon : opCentersImg
//	});
//
//	var marker1 = new google.maps.Marker({
//		position : crocetta,
//		map : map,
//		icon : opCentersImg
//	});
//
//	var marker2 = new google.maps.Marker({
//		position : lingotto,
//		map : map,
//		icon : opCentersImg
//	});
//
//	var marker3 = new google.maps.Marker({
//		position : lucento,
//		map : map,
//		icon : opCentersImg
//	});

}

function createRoute(latStart, lngStart, latEnd, lngEnd, color) {

	var directionsService = new google.maps.DirectionsService();
	var directionsDisplay = new google.maps.DirectionsRenderer();

	directionsDisplay.setOptions({
		polylineOptions : {
			strokeColor : color
		}
	});

	var start = new google.maps.LatLng(latStart, lngStart);
	var end = new google.maps.LatLng(latEnd, lngEnd);
	var bounds = new google.maps.LatLngBounds();
	bounds.extend(start);
	bounds.extend(end);
	map.fitBounds(bounds);

	var request = {
		origin : start,
		destination : end,
		travelMode : google.maps.TravelMode.DRIVING
	};

	directionsService.route(request, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
			directionsDisplay.setMap(map);
		} else {
			alert("Directions Request from " + start.toUrlValue(6) + " to "
					+ end.toUrlValue(6) + " failed: " + status);
		}
	});

}

function addCentrale(latitude, longitude) {

	var opImg = {
		url : 'https://www.florence-forestnews.com/wp-content/uploads/2017/08/blue_tower.png',
		scaledSize : new google.maps.Size(25, 25),
		origin : new google.maps.Point(0, 0),
		anchor : new google.maps.Point(0, 0)
	};

	var pos = {
		lat : latitude,
		lng : longitude
	};

	var ma = new google.maps.Marker({
		position : pos,
		map : map,
		icon : opImg
	});

}

function addMarker(latitude, longitude) {

	var opImg = {
		url : 'http://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/building-icon.png',
		scaledSize : new google.maps.Size(25, 25),
		origin : new google.maps.Point(0, 0),
		anchor : new google.maps.Point(0, 0)
	};

	var pos = {
		lat : latitude,
		lng : longitude
	};

	var ma = new google.maps.Marker({
		position : pos,
		map : map,
		icon : opImg
	});

}

function calculateDistanceTime(latStart, lngStart, latEnd, lngEnd) {

	var origin = new google.maps.LatLng(latStart, lngStart);
	var destination = new google.maps.LatLng(latEnd, lngEnd);
	var service = new google.maps.DistanceMatrixService();

	function calc(fn) {
		res += "Entro in calc\n";
		service.getDistanceMatrix({
			origins : [ origin ],
			destinations : [ destination ],
			travelMode : 'DRIVING',
			drivingOptions : {
				departureTime : new Date(Date.now() + 0), // for the time
				// N
				// milliseconds from
				// now.
				trafficModel : 'optimistic'
			},
			avoidHighways : Boolean,
			avoidTolls : Boolean,
		}, function(results, status) {
			res += "Entro nella funzione callback\n";
			if (status == 'OK') {
				res += "Status OK\n";
				fn(results + status);
			}
			fn(status);
		});

		res += "Eseguito calc()\n";
	}

	calc(function(resu) {
		res = resu;
	});

	var t0 = new Date().getTime();

	while ((t0 + 2000) > new Date().getTime()) {

	}

}

function getRes() {
	return res;
}
