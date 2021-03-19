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
					document.getElementById("divTableTasasInteresEfectivaAnual"),
					{
						tdTipoTasaInteresEfectivaAnual: { campo: "tipoTasaInteresEfectivaAnual.descripcion", clave: "tipoTasaInteresEfectivaAnual.id", descripcion: "Tipo", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoTasaInteresEfectivaAnuales, clave: "id", valor: "descripcion" } },
						tdCuotasDesde: { campo: "cuotasDesde", descripcion: "Cuotas desde", abreviacion: "Cuotas desde", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
						tdCuotasHasta: { campo: "cuotasHasta", descripcion: "Cuotas hasta", abreviacion: "Cuotas hasta", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
						tdMontoDesde: { campo: "montoDesde", descripcion: "UIs desde", abreviacion: "UIs desde", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
						tdMontoHasta: { campo: "montoHasta", descripcion: "UIs hasta", abreviacion: "UIs hasta", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
						tdValor: { campo: "valor", descripcion: "Valor", abreviacion: "Valor", tipo: __TIPO_CAMPO_DECIMAL, decimales: 2, ancho: 100 },
						tdFechaVigenciaHasta: { campo: "fechaVigenciaHasta", descripcion: "Vigente hasta", abreviacion: "Vigente hasta", tipo: __TIPO_CAMPO_FECHA_HORA, ancho: 100 }
					}, 
					false,
					reloadData,
					trTasaInteresEfectivaAnualOnClick
				);
				
				grid.rebuild();
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				break;
			}
		}
		
		reloadData();
		
		$("#divIFrameTasaInteresEfectivaAnual").draggable();
    });
}

function reloadData() {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/TasaInteresEfectivaAnualREST/listVigentes"
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

function trTasaInteresEfectivaAnualOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameTasaInteresEfectivaAnual").src = "./tasa_interes_efectiva_anual_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameTasaInteresEfectivaAnual"));
}

function inputActualizarOnClick(event) {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameTasaInteresEfectivaAnual").src = "./tasa_interes_efectiva_anual_edit.jsp";
	showPopUp(document.getElementById("divIFrameTasaInteresEfectivaAnual"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}