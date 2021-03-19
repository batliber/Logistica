var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_DISTRIBUCION = 7;

var imeis = {
	cantidadRegistros: 0,
	registrosMuestra: []
};

var grid = null;

$(document).ready(init);

function init() {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
    }).then(function(data) {
    	fillSelect(
    		"selectEmpresaDestino",
    		data,
    		"id",
    		"nombre"
    	);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
    }).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_DISTRIBUCION) {
				mode = __FORM_MODE_ADMIN;
				
				grid = new Grid(
					document.getElementById("divTableIMEIs"),
					{
						tdIMEI: { campo: "imei", descripcion: "IMEI", abreviacion: "IMEI", tipo: __TIPO_CAMPO_STRING, ancho: 150 },
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 150 },
						tdMarca: { campo: "marca.nombre", clave: "marca.id", descripcion: "Marca", abreviacion: "Marca", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMarcas, clave: "id", valor: "nombre" }, ancho: 150 },
						tdModelo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Modelo", abreviacion: "Modelo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 150 },
						tdTipoProducto: { campo: "tipoProducto.descripcion", clave: "tipoProducto.id", descripcion: "Tipo", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoProductos, clave: "id", valor: "descripcion" }, ancho: 150 }
					},
					false,
					reloadGrid,
					trIMEIOnClick
				);
				
				grid.rebuild();
				
				break;
			}
		}
	});
}

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
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
		registros.registrosMuestra[registros.registrosMuestra.length] = {
			imei: ordered[i].imei,
			empresa: ordered[i].empresa,
			marca: ordered[i].marca,
			modelo: ordered[i].modelo,
			tipoProducto: ordered[i].tipoProducto
		};
	}
	
	grid.reload(registros);
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

function inputIMEIOnChange(event, element) {
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
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/StockMovimientoREST/getLastByIMEI/" + val
	    }).then(function(data) {
			if (data != null) {
				imeis.cantidadRegistros = imeis.cantidadRegistros + 1;
				imeis.registrosMuestra[imeis.registrosMuestra.length] = {
					imei: val,
					marca: data.marca,
					modelo: data.modelo,
					empresa: data.empresa,
					producto: data.producto,
					tipoProducto: data.tipoProducto
				};
				
				reloadGrid();
			} else {
				alert("No se encuentra el IMEI ingresado.")
			}
			
			$("#inputIMEI").val(null);
		});
	}
}

function inputGuardarOnClick(event) {
	var empresaDestinoId = $("#selectEmpresaDestino").val();
	if (empresaDestinoId == 0) {
		alert("Debe seleccionar una empresa.");
		return;
	}
	
	if (imeis.cantidadRegistros == 0) {
		alert("Debe ingresar al menos un IMEI.");
		return;
	}
	
	var stockMovimientos = [];
	for (var i=0; i<imeis.registrosMuestra.length;i++) {
		stockMovimientos[stockMovimientos.length] = {
			empresa: imeis.registrosMuestra[i].empresa,
			marca: imeis.registrosMuestra[i].marca,
			modelo: imeis.registrosMuestra[i].modelo,
			producto: imeis.registrosMuestra[i].producto,
			tipoProducto: imeis.registrosMuestra[i].tipoProducto,
			cantidad: 1
		}
	}
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/StockMovimientoREST/transferir",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify({
        	"empresaDestinoId": empresaDestinoId,
        	"stockMovimientos": stockMovimientos
		})
    }).then(function(data) {
    	alert("Operación exitosa");
		
		$("#selectEmpresaDestino").val(0);
		imeis = {
			cantidadRegistros: 0,
			registrosMuestra: []
		};
		
		reloadGrid();
    });
}