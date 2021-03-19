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
					document.getElementById("divTableUnidadesIndexadas"),
					{
						tdFechaVigenciaHasta: { campo: "fechaVigenciaHasta", descripcion: "Vigente hasta", abreviacion: "Vigente hasta", tipo: __TIPO_CAMPO_FECHA_HORA, ancho: 100 },
						tdValor: { campo: "valor", descripcion: "Valor", abreviacion: "Valor", tipo: __TIPO_CAMPO_DECIMAL, decimales: 4, ancho: 100 }
					}, 
					false,
					reloadData,
					trUnidadIndexadaOnClick
				);
					
				grid.rebuild();
					
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				
				break;
			}
		}
		
		reloadData();
		
		$("#divIFrameUnidadIndexada").draggable();
	});
}

function reloadData() {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/UnidadIndexadaREST/list"
	}).then(function (data) {
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
			registros.registrosMuestra[registros.registrosMuestra.length] = ordered[i];
		}
		
		grid.reload(registros);
	});
}

function trUnidadIndexadaOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameUnidadIndexada").src = "./unidad_indexada_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameUnidadIndexada"));
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameUnidadIndexada").src = "./unidad_indexada_edit.jsp";
	showPopUp(document.getElementById("divIFrameUnidadIndexada"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}