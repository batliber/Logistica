$(document).ready(function() {
	refinarForm();
	
	EmpresaDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectEmpresa").append(html);
			}, async: false
		}
	);
	
	ProductoDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectProducto").append(html);
			}, async: false
		}
	);
	
	StockTipoMovimientoDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "' s='" + data[i].signo + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectStockTipoMovimiento").append(html);
			}, async: false
		}
	);
});

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function inputGuardarOnClick(event) {
	var stockMovimiento = {
		cantidad: $("#inputCantidad").val()
	};
	
	if ($("#selectEmpresa").length > 0 && $("#selectEmpresa").val() != 0) {
		stockMovimiento.empresa = {
			id: $("#selectEmpresa").length > 0 ? $("#selectEmpresa").val() : $("#divEmpresa").attr("eid")
		};
	} else {
		alert("Debe seleccionar una empresa.");
		
		return;
	}
	
	if ($("#selectProducto").length > 0 && $("#selectProducto").val() != 0) {
		stockMovimiento.producto = {
			id: $("#selectProducto").length > 0 ? $("#selectProducto").val() : $("#divProducto").attr("pid")
		};
	} else {
		alert("Debe seleccionar un producto.");
		
		return;
	}
	
	if ($("#selectStockTipoMovimiento").length > 0 && $("#selectStockTipoMovimiento").val() != 0) {
		stockMovimiento.stockTipoMovimiento = {
			id: $("#selectStockTipoMovimiento").length > 0 ? $("#selectStockTipoMovimiento").val() : $("#divStockTipoMovimiento").attr("stmid"),
			signo: $("#selectStockTipoMovimiento").length > 0 ? $("#selectStockTipoMovimiento > option:selected").attr("s") : $("#divStockTipoMovimiento").attr("s")
		};
	} else {
		alert("Debe seleccionar un tipo de movimiento.");
		
		return;
	}
	
	StockMovimientoDWR.add(
		stockMovimiento,
		{
			callback: function(data) {
				alert("Operación exitosa");
			}, async: false
		}
	);
}