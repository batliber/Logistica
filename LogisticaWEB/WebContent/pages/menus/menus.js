var __ROL_ADMINISTRADOR = 1;

var grid = null;

$(document).ready(init);
		
function init() {
	$("#divButtonNew").hide();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
    }).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR) {
				mode = __FORM_MODE_ADMIN;
				
				$("#divButtonNew").show();
				
				grid = new Grid(
					document.getElementById("divTableMenus"),
					{
						tdMenuTitulo: { campo: "titulo", descripcion: "Título", abreviacion: "Título", tipo: __TIPO_CAMPO_STRING, ancho: 200 },
						tdMenuURL: { campo: "url", descripcion: "URL", abreviacion: "URL", tipo: __TIPO_CAMPO_STRING, ancho: 450 },
						tdMenuOrden: { campo: "orden", descripcion: "Orden", abreviacion: "Orden", tipo: __TIPO_CAMPO_NUMERICO },
						tdMenuPadre: { campo: "padre.titulo", clave: "padre.id", descripcion: "Padre", abreviacion: "Padre", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMenus, clave: "id", valor: "titulo" } }
					}, 
					false,
					reloadData,
					trMenuOnClick
				);
				
				grid.rebuild();
					
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				
				break;
			}
		}
		
		reloadData();
		
		$("#divIFrameMenu").draggable();
	});
}

function reloadData() {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/MenuREST/list"
	}).then(function (data) {
		var registros = {
			cantidadRegistros: data.length,
			registrosMuestra: []
		};
		
		var map = {}; 
		for (var i=0; i<data.length; i++) {
			map[data[i].id] = data[i];
		}
		
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
			ordered[i].padre = map[ordered[i].padre];
			registros.registrosMuestra[registros.registrosMuestra.length] = ordered[i];
		}
		
		grid.reload(registros);
	});
}

function trMenuOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameMenu").src = "./menu_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameMenu"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameMenu").src = "./menu_edit.jsp";
	showPopUp(document.getElementById("divIFrameMenu"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}