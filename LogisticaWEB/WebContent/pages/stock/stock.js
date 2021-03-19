var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_DISTRIBUCION = 7;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonAgregarPorIMEI").hide();
	$("#divButtonNuevoStockMovimiento").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_DISTRIBUCION) {
				mode = __FORM_MODE_ADMIN;
				
				grid = new Grid(
					document.getElementById("divTableStockMovimientos"),
					{
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 200 },
						tdMarca: { campo: "marca.nombre", clave: "marca.id", descripcion: "Marca", abreviacion: "Marca", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMarcas, clave: "id", valor: "nombre" }, ancho: 80 },
						tdModelo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Modelo", abreviacion: "Modelo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 200 },
						tdTipoProducto: { campo: "tipoProducto.descripcion", clave: "tipoProducto.id", descripcion: "Tipo de producto", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoProductos, clave: "id", valor: "descripcion" } },
						tdCantidad: { campo: "cantidad", descripcion: "Cantidad", abreviacion: "Cantidad", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 }
					},
					true,
					reloadData,
					trStockMovimientoOnClick
				);
				
				grid.rebuild();
				
				$("#divButtonNuevoStockMovimiento").show();
				$("#divButtonAgregarPorIMEI").show();
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				
				break;
			}
		}
		
		reloadData();
		
		$("#divIFrameStockMovimiento").draggable();
		$("#divIFrameIMEI").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/StockMovimientoREST/listStockActualContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/StockMovimientoREST/countStockActualContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.setCount(data);
	});
}

function trStockMovimientoOnClick(eventObject) {
	var target = eventObject.currentTarget;
//	var productoId = $(target).children("[campo='tdProducto']").attr("clave");
	
//	document.getElementById("iFrameStockMovimientoHistorico").src = "./stock_movimiento_historico.jsp?m=" + mode + "&eid=" + $(target).attr("eid") + "&pid=" + $(target).attr("pid");
//	showPopUp(document.getElementById("divIFrameStockMovimientoHistorico"));
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