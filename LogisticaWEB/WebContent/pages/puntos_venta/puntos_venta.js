var __ROL_ADMINISTRADOR = 1;
var __ROL_ENCARGADO_ACTIVACIONES = 12;

var grid = null;
var map = null;

$(document).ready(function() {
	$("#divButtonNew").hide();
	
	grid = new Grid(
		document.getElementById("divTablePuntosVenta"),
		{
			tdNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 200 },
			tdTelefono: { campo: "telefono", descripcion: "Teléfono", abreviacion: "Tel.", tipo: __TIPO_CAMPO_STRING, ancho: 150 },
			tdContacto: { campo: "contacto", descripcion: "Contacto", abreviacion: "Contacto", tipo: __TIPO_CAMPO_STRING, ancho: 150 },
			tdDepartamento: { campo: "departamento.nombre", descripcion: "Departamento", abreviacion: "Departamento", tipo: __TIPO_CAMPO_STRING, ancho: 100 },
			tdBarrio: { campo: "barrio.nombre", descripcion: "Barrio", abreviacion: "Barrio", tipo: __TIPO_CAMPO_STRING, ancho: 250 }
		},
		false,
		reloadData,
		trPuntoVentaOnClick
	);
	
	grid.rebuild();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_ENCARGADO_ACTIVACIONES) {
						mode = __FORM_MODE_ADMIN;
						
						$("#divButtonNew").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						
						break;
					}
				}
			}, async: false
		}
	);
	
	$("#divIFramePuntoVenta").draggable();
	
	reloadData();
});

function initMap() {
	var divMap = document.getElementById("divMapaPuntosVenta");
	map = new google.maps.Map(
		divMap, {
			center: {lat: 0, lng: 0},
			zoom: 8
    	}
	);
}

function reloadData() {
	PuntoVentaDWR.list(
		{
			callback: function(data) {
				var registros = {
					cantidadRegistros: data.length,
					registrosMuestra: []
				};
				
				var ordered = data;
				
				var ordenaciones = grid.filtroDinamico.calcularOrdenaciones();
				if (ordenaciones.length > 0 && data != null) {
					ordered = data.sort(function(a, b) {
						var result = 0;
						
						for (var i=0; i<ordenaciones.length; i++) {
							var aValue = null;
							try {
								aValue = eval("a." + ordenaciones[i].campo);
						    } catch(e) {
						        aValue = null;
						    }
						    
						    var bValue = null;
						    try {
								bValue = eval("b." + ordenaciones[i].campo);
						    } catch(e) {
						        bValue = null;
						    }
							
							if (aValue < bValue) {
								result = -1 * (ordenaciones[i].ascendente ? 1 : -1);
								
								break;
							} else if (aValue > bValue) {
								result = 1 * (ordenaciones[i].ascendente ? 1 : -1);
								
								break;
							}
						}
						
						return result;
					});
				}
				
				initMap();
				
				var markerBounds = new google.maps.LatLngBounds();
				
				for (var i=0; i<ordered.length; i++) {
					registros.registrosMuestra[registros.registrosMuestra.length] = {
						id: ordered[i].id,
						nombre: ordered[i].nombre,
						direccion: ordered[i].direccion,
						telefono: ordered[i].telefono,
						contacto: ordered[i].contacto,
						departamento: ordered[i].departamento,
						barrio: ordered[i].barrio,
						uact: ordered[i].uact,
						fact: ordered[i].fact,
						term: ordered[i].term
					};
					
					if (ordered[i].latitud != null && ordered[i].longitud != null) {
						var latlng = {
							lat: ordered[i].latitud, lng: ordered[i].longitud
						};
						
						var marker = new google.maps.Marker({
							position: latlng,
							map: map,
							title: ordered[i].nombre
						});
						
						markerBounds.extend(latlng);
					}
				}
				
				map.fitBounds(markerBounds);
				
				grid.reload(registros);
			}, async: false
		}
	);
}

function trPuntoVentaOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFramePuntoVenta").src = "./punto_venta_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFramePuntoVenta"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFramePuntoVenta").src = "./punto_venta_edit.jsp";
	showPopUp(document.getElementById("divIFramePuntoVenta"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}