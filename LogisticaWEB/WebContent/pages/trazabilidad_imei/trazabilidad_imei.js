var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_DISTRIBUCION = 7;
var __ROL_DEMO = 21;

var grid = null;

$(document).ready(init);

function init() {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
    }).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_DISTRIBUCION
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
				mode = __FORM_MODE_ADMIN;
				
				grid = new Grid(
					document.getElementById("divTableStockMovimientos"),
					{
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 200 },
						tdMarca: { campo: "marca.nombre", clave: "marca.id", descripcion: "Marca", abreviacion: "Marca", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMarcas, clave: "id", valor: "nombre" }, ancho: 80 },
						tdModelo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Modelo", abreviacion: "Modelo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 200 },
						tdStockTipoMovimiento: { campo: "stockTipoMovimiento.descripcion", clave: "stockTipoMovimiento.id", descripcion: "Tipo de movimiento", abreviacion: "Tipo mov.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listStockTipoMovimientos, clave: "id", valor: "descripcion" }, ancho: 200 },
						tdFecha: { campo: "fecha", descripcion: "Fecha", abreviacion: "Fecha", tipo: __TIPO_CAMPO_FECHA_HORA }
					},
					false,
					reloadData,
					trStockMovimientoOnClick
				);
				
				grid.rebuild();
				
				break;
			}
		}
		
		reloadData();
	});
}

function reloadData() {
	var imei = $("#inputIMEI").val();
	
	if (imei == null || imei == "") {
		return;
	}
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/StockMovimientoREST/listByIMEI/" + $("#inputIMEI").val()
    }).then(function(data) {
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
				fecha: ordered[i].fecha,
				empresa: ordered[i].empresa,
				marca: ordered[i].marca,
				modelo: ordered[i].modelo,
				producto: ordered[i].producto,
				stockTipoMovimiento: ordered[i].stockTipoMovimiento
			};
		}
		
		grid.reload(registros);
	});
}

function inputIMEIOnChange(event, element) {
	if ($("#inputIMEI").val() != null && $("#inputIMEI").val() != "") {
		reloadData();
	}
}

function trStockMovimientoOnClick() {
	
}

function inputActualizarOnClick() {
	reloadData();
}