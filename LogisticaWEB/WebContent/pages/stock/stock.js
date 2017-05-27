var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_DISTRIBUCION = 7;

var grid = null;

$(document).ready(function() {
	$("#divButtonAgregarPorIMEI").hide();
	$("#divButtonNuevoStockMovimiento").hide();
	
	grid = new Grid(
		document.getElementById("divTableStockMovimientos"),
		{
			tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 200 },
			tdMarca: { campo: "marca.nombre", clave: "marca.id", descripcion: "Marca", abreviacion: "Marca", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMarcas, clave: "id", valor: "nombre" }, ancho: 80 },
			tdModelo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Modelo", abreviacion: "Modelo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 200 },
			tdCantidad: { campo: "cantidad", descripcion: "Cantidad", abreviacion: "Cantidad", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 }
		},
		false,
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
						
						$("#divButtonNuevoStockMovimiento").show();
						$("#divButtonAgregarPorIMEI").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						
						break;
					}
				}
			}, async: false
		}
	);
	
	reloadData();
	
	$("#divIFrameStockMovimiento").draggable();
	$("#divIFrameIMEI").draggable();
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

function listMarcas() {
	var result = [];
	
	MarcaDWR.list(
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

function listModelos() {
	var result = [];
	
	ModeloDWR.list(
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
//					if ($("#inputMostrarFechaBaja").prop("checked")
//						|| ordered[i].producto.fechaBaja == null) {
						registros.registrosMuestra[registros.registrosMuestra.length] = {
							cantidad: ordered[i].cantidad,
							empresa: ordered[i].empresa,
							marca: ordered[i].marca,
							modelo: ordered[i].modelo,
							producto: ordered[i].producto
						};
//					}
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
//	var productoId = $(target).children("[campo='tdProducto']").attr("clave");
	
//	document.getElementById("iFrameStockMovimientoHistorico").src = "./stock_movimiento_historico.jsp?m=" + mode + "&eid=" + $(target).attr("eid") + "&pid=" + $(target).attr("pid");
//	showPopUp(document.getElementById("divIFrameStockMovimientoHistorico"));
	
//	document.getElementById("iFrameProducto").src = "./producto_edit.jsp?m=" + mode + "&id=" + productoId;
//	showPopUp(document.getElementById("divIFrameProducto"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNuevoStockMovimientoOnClick(event, element) {
	document.getElementById("iFrameStockMovimiento").src = "./stock_movimiento_edit.jsp";
	showPopUp(document.getElementById("divIFrameStockMovimiento"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}