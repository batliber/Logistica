var imeis = {
	cantidadRegistros: 0,
	registrosMuestra: []
};

$(document).ready(init);

function init() {
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
	
	MarcaDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectMarca").append(html);
			}, async: false
		}
	);
	
	TipoProductoDWR.list(
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectTipoProducto").append(html);
			}, async: false
		}
	);
	
	grid = new Grid(
		document.getElementById("divTableIMEIs"),
		{
			tdIMEI: { campo: "imei", descripcion: "IMEI", abreviacion: "IMEI", tipo: __TIPO_CAMPO_STRING, ancho: 150 },
			tdMarca: { campo: "marca.nombre", clave: "marca.id", descripcion: "Marca", abreviacion: "Marca", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMarcas, clave: "id", valor: "nombre" }, ancho: 150 },
			tdModelo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Modelo", abreviacion: "Modelo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 150 },
		},
		false,
		reloadGrid,
		trIMEIOnClick,
		null,
		16
	);
	
	grid.rebuild();
	
	$("#selectModelo").append("<option id='0' value='0'>Seleccione...</option>");
	$("#inputCantidad").prop("disabled", true);
	$("#inputIMEI").prop("disabled", true);
}

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function listMarcas() {
	MarcaDWR.list(
		{
			callback: function(data) {
				return data;
			}, async: false
		}
	);
}

function listModelos() {
	ModeloDWR.listVigentes(
		{
			callback: function(data) {
				return data;
			}, async: false
		}
	);
}

function reloadGrid() {
	var ordered = imeis.registrosMuestra;
	
	var ordenaciones = grid.filtroDinamico.calcularOrdenaciones();
	if (ordenaciones.length > 0 && ordered != null) {
		ordered = ordered.sort(function(a, b) {
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
	
	var registros = {
		cantidadRegistros: imeis.cantidadRegistros,
		registrosMuestra: []
	};
	for (var i=0; i<ordered.length; i++) {
		registros.registrosMuestra[registros.registrosMuestra.length] = ordered[i];
	}
	
	grid.reload(registros);
}

function selectStockTipoMovimientoOnChange() {
	$("#selectMarca").val(0);
	$("#selectModelo").val(0);
	$("#selectTipoProducto").val(0);
	$("#inputCantidad").val(0);
	$("#inputIMEI").val(null);
	
	imeis.cantidadRegistros = 0;
	imeis.registrosMuestra = [];
	
	grid.reload(imeis);
	
	if ($("#selectStockTipoMovimiento").val() == 3) {
		$("#inputCantidad").prop("disabled", true);
		$("#inputIMEI").prop("disabled", false);
	} else {
		$("#inputCantidad").prop("disabled", false);
		$("#inputIMEI").prop("disabled", true);
	}
}

function selectMarcaOnChange() {
	$("#selectModelo > option").remove();
	
	ModeloDWR.listVigentesByMarcaId(
		$("#selectMarca").val(), 
		{
			callback: function(data) {
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectModelo").append(html);
			}, async: false
		}
	);
}

function inputIMEIOnChange() {
	var val = $("#inputIMEI").val();
	if (val != null && val.trim() != "") {
		val = val.trim();
		
		for (var i=0; i<imeis.registrosMuestra.length; i++) {
			if (imeis.registrosMuestra[i].imei == val) {
				alert("El IMEI ya fue ingresado.");
				
				$("#inputIMEI").val(null);
				
				return;
			}
		}
		
		ProductoDWR.existeIMEI(
			val,
			{
				callback: function(data) {
					if (data != null && !data) {
						imeis.cantidadRegistros = imeis.cantidadRegistros + 1;
						imeis.registrosMuestra[imeis.registrosMuestra.length] = {
							imei: val,
							marca: {
								id: $("#selectMarca").val(),
								nombre: $("#selectMarca > option:selected").text()
							},
							modelo: {
								id: $("#selectModelo").val(),
								descripcion: $("#selectModelo > option:selected").text()
							}
						};
						
						reloadGrid();
						$("#inputCantidad").val(imeis.cantidadRegistros);
					} else {
						alert("El IMEI ya fue ingresado.")
					}
					
					$("#inputIMEI").val(null);
				}, async: false
			}
		);
	}
}

function trIMEIOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var imei = $(target).children("[campo='tdIMEI']").html();
	
	var i=0;
	for (i=0; i<imeis.registrosMuestra.length; i++) {
		if (imeis.registrosMuestra[i].imei == imei) {
			break;
		}
	}
	
	imeis.cantidadRegistros = imeis.cantidadRegistros - 1;
	imeis.registrosMuestra.splice(i, 1);
	
	grid.reload(imeis);
	$("#inputCantidad").val(imeis.cantidadRegistros);
}

function inputGuardarOnClick(event) {
	var stockTipoMovimiento = {};
	if ($("#selectStockTipoMovimiento").length > 0 && $("#selectStockTipoMovimiento").val() != 0) {
		stockTipoMovimiento.id = $("#selectStockTipoMovimiento").val();
		stockTipoMovimiento.signo = $("#selectStockTipoMovimiento").length > 0 ? $("#selectStockTipoMovimiento > option:selected").attr("s") : $("#divStockTipoMovimiento").attr("s");
	} else {
		alert("Debe seleccionar un tipo de movimiento.");
		
		return;
	}
		
	var empresa = {};
	if ($("#selectEmpresa").length > 0 && $("#selectEmpresa").val() != 0) {
		empresa.id = $("#selectEmpresa").length > 0 ? $("#selectEmpresa").val() : $("#divEmpresa").attr("eid");
	} else {
		alert("Debe seleccionar una empresa.");
		
		return;
	}
	
	var marca = {};
	if ($("#selectMarca").length > 0 && $("#selectMarca").val() != 0) {
		marca.id = $("#selectMarca").length > 0 ? $("#selectMarca").val() : $("#divMarca").attr("mid");
	} else {
		alert("Debe seleccionar una marca.");
		
		return;
	}
	
	var modelo = {};
	if ($("#selectModelo").length > 0 && $("#selectModelo").val() != 0) {
		modelo.id = $("#selectModelo").length > 0 ? $("#selectModelo").val() : $("#divModelo").attr("mid");
	} else {
		alert("Debe seleccionar un modelo.");
		
		return;
	}
	
	var tipoProducto = {};
	if ($("#selectTipoProducto").length > 0 && $("#selectTipoProducto").val() != 0) {
		tipoProducto.id = $("#selectTipoProducto").length > 0 ? $("#selectTipoProducto").val() : $("#divTipoProducto").attr("tpid");
	} else {
		alert("Debe seleccionar un tipo de producto.");
		
		return;
	}
	
	if ($("#selectStockTipoMovimiento").val() == 3) {
		var stockMovimientos = [];
		
		for (i=0; i<imeis.registrosMuestra.length; i++) {
			stockMovimientos[stockMovimientos.length] = {
				stockTipoMovimiento: stockTipoMovimiento,
				empresa: empresa,
				cantidad: 1,
				producto: {
					imei: imeis.registrosMuestra[i].imei,
					marca: marca,
					modelo: modelo
				},
				marca: marca,
				modelo: modelo,
				tipoProducto: tipoProducto
			}
		}
		
		StockMovimientoDWR.add(
			stockMovimientos,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		var stockMovimiento = {
			stockTipoMovimiento: stockTipoMovimiento,
			empresa: empresa,
			cantidad: $("#inputCantidad").val(),
			marca: marca,
			modelo: modelo,
			tipoProducto: tipoProducto
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
}