var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_DISTRIBUCION = 7;

var grid = null;

$(document).ready(function() {
	$("#divButtonAgregarProducto").hide();
	$("#divButtonNuevoStockMovimiento").hide();
	
	grid = new Grid(
		document.getElementById("divTableStockMovimientos"),
		{
			tdEmpresaNombre: { campo: "empresa.nombre", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_STRING},
			tdProductoDescripcion: { campo: "producto.descripcion", descripcion: "Producto", abreviacion: "Producto", tipo: __TIPO_CAMPO_STRING},
			tdCantidad: { campo: "cantidad", descripcion: "Cantidad", abreviacion: "Cantidad", tipo: __TIPO_CAMPO_NUMERICO }
		},
		reloadData,
		trStockMovimientoOnClick
	);
	
	grid.rebuild();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_DISTRIBUCION) {
						mode = __FORM_MODE_ADMIN;
						
						$("#divButtonAgregarProducto").show();
						$("#divButtonNuevoStockMovimiento").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleTripleSize");
						
						break;
					}
				}
			}, async: false
		}
	);
	
	reloadData();
	
	$("#divIFrameStockMovimiento").draggable();
	$("#divIFrameProducto").draggable();
});

function reloadData() {
	StockMovimientoDWR.listStockActual(
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
				
				for (var i=0; i<ordered.length; i++) {
					registros.registrosMuestra[registros.registrosMuestra.length] = {
						cantidad: ordered[i].cantidad,
						empresa: {
							id: ordered[i].empresa.id,
							nombre: ordered[i].empresa.nombre,
						},
						producto: {
							id: ordered[i].producto.id,
							descripcion: ordered[i].producto.descripcion,
						}
					};
				}
				
				grid.reload(registros);
			}, async: false
		}
	);
}

function trStockMovimientoOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	document.getElementById("iFrameStockMovimientoHistorico").src = "./stock_movimiento_historico.jsp?m=" + mode + "&eid=" + $(target).attr("eid") + "&pid=" + $(target).attr("pid");
	showPopUp(document.getElementById("divIFrameStockMovimientoHistorico"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNuevoStockMovimientoOnClick(event, element) {
	document.getElementById("iFrameStockMovimiento").src = "./stock_movimiento_edit.jsp";
	showPopUp(document.getElementById("divIFrameStockMovimiento"));
}

function inputAgregarProductoOnClick(event, element) {
	document.getElementById("iFrameProducto").src = "./producto_edit.jsp";
	showPopUp(document.getElementById("divIFrameProducto"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}