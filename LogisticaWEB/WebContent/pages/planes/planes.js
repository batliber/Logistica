var __ROL_ADMINISTRADOR = 1;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonNew").hide();
	
	grid = new Grid(
		document.getElementById("divTablePlanes"),
		{
			tdPlanTipoPlan: { campo: "tipoPlan.descripcion", clave: "tipoPlan.id", descripcion: "Tipo de plan", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoPlanes, clave: "id", valor: "descripcion" }},
			tdPlanDescripcion: { campo: "descripcion", descripcion: "Descripción", abreviacion: "Descripción", tipo: __TIPO_CAMPO_STRING, ancho: 250 }
		}, 
		false,
		reloadData,
		trPlanOnClick
	);
	
	grid.rebuild();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR) {
						mode = __FORM_MODE_ADMIN;
						
						$("#divButtonNew").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						
						break;
					}
				}
			}, async: false
		}
	);
	
	reloadData();
	
	$("#divIFramePlan").draggable();
}

function listTipoPlanes() {
	var result = [];
	
	TipoPlanDWR.list(
		{
			callback: function(data) {
				if (data != null) {
					result = data;
				}
			}, async: false
		}
	);
	
	return result;
}

function reloadData() {
	PlanDWR.list(
		{
			callback: function(data) {
				var registros = {
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
					if ($("#inputMostrarFechaBaja").prop("checked")
						|| ordered[i].fechaBaja == null) {
						registros.registrosMuestra[registros.registrosMuestra.length] = ordered[i];
					}
				}
				
				registros.cantidadRegistros = registros.registrosMuestra.length;
				
				grid.reload(registros);
			}, async: false
		}
	);
}

function inputMostrarFechaBajaOnClick(event, element) {
	reloadData();
}

function trPlanOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFramePlan").src = "./plan_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFramePlan"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFramePlan").src = "./plan_edit.jsp";
	showPopUp(document.getElementById("divIFramePlan"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}