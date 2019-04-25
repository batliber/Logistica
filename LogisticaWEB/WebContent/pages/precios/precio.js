var __ROL_ADMINISTRADOR = 1;
var __ROL_MAESTROS_RIVERGREEN = 20;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonNuevoPrecio").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_MAESTROS_RIVERGREEN) {
						mode = __FORM_MODE_ADMIN;
						
						$("#divButtonNuevoPrecio").show();
						
						grid = new Grid(
							document.getElementById("divTablePrecios"),
							{
								tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 200 },
								tdTipoProducto: { campo: "tipoProducto.descripcion", clave: "tipoProducto.id", descripcion: "Tipo de producto", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoProductos, clave: "id", valor: "descripcion" } },
								tdMarca: { campo: "marca.nombre", clave: "marca.id", descripcion: "Marca", abreviacion: "Marca", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMarcas, clave: "id", valor: "nombre" }, ancho: 80 },
								tdModelo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Modelo", abreviacion: "Modelo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 200 },
								tdMoneda: { campo: "moneda.simbolo", clave: "moneda.id", descripcion: "Moneda", abreviacion: "Moneda", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMonedas, clave: "id", valor: "nombre" }, ancho: 80 },
								tdCuotas: { campo: "cuotas", descripcion: "Cuotas", abreviacion: "Cuotas", tipo: __TIPO_CAMPO_NUMERICO },
								tdPrecio: { campo: "precio", descripcion: "Precio", abreviacion: "Precio", tipo: __TIPO_CAMPO_NUMERICO }
							},
							true,
							reloadData,
							trPrecioOnClick
						);
						
						grid.rebuild();
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						break;
					}
				}
			}, async: false
		}
	);
	
	reloadData();
	
	$("#divIFramePrecio").draggable();
}

function listEmpresas() {
	var result = [];
	
	UsuarioRolEmpresaDWR.listEmpresasByContext(
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

function listTipoProductos() {
	var result = [];
	
	TipoProductoDWR.list(
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
	grid.setStatus(grid.__STATUS_LOADING);
	
	PrecioDWR.listPreciosActualesContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	PrecioDWR.countPreciosActualesContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
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