var __ROL_ADMINISTRADOR = 1;

var grid = null;

$(document).ready(init);

function init() {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
    }).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR) {
				mode = __FORM_MODE_ADMIN;
				
				grid = new Grid(
					document.getElementById("divTableArchivos"),
					{
						tdNombre: { campo: "nombre", descripcion: "Archivo", abreviacion: "Archivo", tipo: __TIPO_CAMPO_STRING, ancho: 400 }
					}, 
					false,
					reloadData,
					trArchivoOnClick
				);
				
				grid.rebuild();
				
				break;
			}
		}
		
		reloadData();
    });
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/FileManagerREST/list"
    }).then(function(data) {
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
		
		for (var i=0; i<ordered.length; i++) {
			registros.registrosMuestra[registros.registrosMuestra.length] = {
				nombre: 
					"<a href=\"/LogisticaWEB/Download?fn=" + ordered[i].nombre + "\">" 
						+ ordered[i].nombre 
					+ "</a>"
			};
		}
		
		grid.reload(registros);
	});
}

function trArchivoOnClick() {
	
}

function inputActualizarOnClick(event) {
	reloadData();
}