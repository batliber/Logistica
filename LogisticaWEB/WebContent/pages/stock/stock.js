var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_DISTRIBUCION = 7;

var grid = null;

$(document).ready(function() {
	$("#divButtonAgregarProducto").hide();
	$("#divButtonAgregarEmpresaService").hide();
	$("#divButtonNuevoStockMovimiento").hide();
	
	grid = new Grid(
		document.getElementById("divTableStockMovimientos"),
		{
			tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 80 },
			tdProducto: { campo: "producto.descripcion", clave: "producto.id", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listProductos, clave: "id", valor: "descripcion" }, ancho: 80 },
			tdProductoFechaBaja: { campo: "producto.fechaBaja", descripcion: "Fecha baja", abreviacion: "Fecha baja", tipo: __TIPO_CAMPO_FECHA, ancho: 80 },
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
						$("#divButtonAgregarEmpresaService").show();
						$("#divButtonNuevoStockMovimiento").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleFourfoldSize");
						
						break;
					}
				}
			}, async: false
		}
	);
	
	reloadData();
	
	$("#divIFrameStockMovimiento").draggable();
	$("#divIFrameProducto").draggable();
	$("#divIFrameEmpresaService").draggable();
});

function listEmpresas() {
	var result = [];
	
	EmpresaDWR.list(
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

function listProductos() {
	var result = [];
	
	ProductoDWR.list(
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
					if ($("#inputMostrarFechaBaja").prop("checked")
						|| ordered[i].producto.fechaBaja == null) {
						registros.registrosMuestra[registros.registrosMuestra.length] = {
							cantidad: ordered[i].cantidad,
							empresa: {
								id: ordered[i].empresa.id,
								nombre: ordered[i].empresa.nombre,
							},
							producto: {
								id: ordered[i].producto.id,
								descripcion: ordered[i].producto.descripcion,
								fechaBaja: ordered[i].producto.fechaBaja
							}
						};
					}
				}
				
				grid.reload(registros);
			}, async: false
		}
	);
}

function inputMostrarFechaBajaOnClick(event, element) {
	reloadData();
}

function trStockMovimientoOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var productoId = $(target).children("[campo='tdProducto']").attr("clave");
	
//	document.getElementById("iFrameStockMovimientoHistorico").src = "./stock_movimiento_historico.jsp?m=" + mode + "&eid=" + $(target).attr("eid") + "&pid=" + $(target).attr("pid");
//	showPopUp(document.getElementById("divIFrameStockMovimientoHistorico"));
	
	document.getElementById("iFrameProducto").src = "./producto_edit.jsp?m=" + mode + "&id=" + productoId;
	showPopUp(document.getElementById("divIFrameProducto"));
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

function inputAgregarEmpresaServiceOnClick(event, element) {
	document.getElementById("iFrameEmpresaService").src = "./empresa_service_edit.jsp";
	showPopUp(document.getElementById("divIFrameEmpresaService"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}