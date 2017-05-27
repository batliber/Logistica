var __ROL_ADMINISTRADOR = 1;

var grid = null;

$(document).ready(function() {
	$("#divButtonNuevoPrecio").hide();
	
	grid = new Grid(
		document.getElementById("divTablePrecios"),
		{
			tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 200 },
			tdMarca: { campo: "marca.nombre", clave: "marca.id", descripcion: "Marca", abreviacion: "Marca", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMarcas, clave: "id", valor: "nombre" }, ancho: 80 },
			tdModelo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Modelo", abreviacion: "Modelo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 200 },
			tdMoneda: { campo: "moneda.simbolo", clave: "moneda.id", descripcion: "Moneda", abreviacion: "Moneda", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMonedas, clave: "id", valor: "nombre" }, ancho: 80 },
			tdPrecio: { campo: "precio", descripcion: "Precio", abreviacion: "Precio", tipo: __TIPO_CAMPO_NUMERICO }
		},
		false,
		reloadData,
		trPrecioOnClick
	);
	
	grid.rebuild();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR) {
						mode = __FORM_MODE_ADMIN;
						
						$("#divButtonNuevoPrecio").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						
						break;
					}
				}
			}, async: false
		}
	);
	
	reloadData();
	
	$("#divIFramePrecio").draggable();
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

function listMonedas() {
	var result = [];
	
	MonedaDWR.list(
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
	PrecioDWR.listPreciosActuales(
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
						id: ordered[i].id,
						precio: ordered[i].precio,
						empresa: {
							id: ordered[i].empresa.id,
							nombre: ordered[i].empresa.nombre,
						},
						marca: {
							id: ordered[i].marca.id,
							nombre: ordered[i].marca.nombre
						},
						modelo: {
							id: ordered[i].modelo.id,
							descripcion: ordered[i].modelo.descripcion
						},
						moneda: {
							id: ordered[i].moneda.id,
							nombre: ordered[i].moneda.nombre,
							simbolo: ordered[i].moneda.simbolo
						}
					};
				}
					
				grid.reload(registros);
			}, async: false
		}
	)
}

function trPrecioOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	document.getElementById("iFramePrecio").src = "./precio_edit.jsp?m=" + mode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFramePrecio"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNuevoPrecioOnClick() {
	document.getElementById("iFramePrecio").src = "./precio_edit.jsp";
	showPopUp(document.getElementById("divIFramePrecio"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}