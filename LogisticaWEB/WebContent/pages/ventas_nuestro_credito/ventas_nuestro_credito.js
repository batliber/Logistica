var __ROL_ADMINISTRADOR = 1;
var __ROL_ENCARGADO_ANALISIS_FINANCIERO = 13;
var __ROL_VENTAS_NUESTRO_CREDITO = 16;
var __ROL_DEMO = 21;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonExportarAExcel").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_ENCARGADO_ANALISIS_FINANCIERO
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_VENTAS_NUESTRO_CREDITO
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
						$("#divButtonExportarAExcel").show();
						
						grid = new Grid(
							document.getElementById("divTableContratos"),
							{
								tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
								tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 90 },
								tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING, ancho: 90 },
								tdFechaVenta: { campo: "fechaVenta", descripcion: "Fecha de venta", abreviacion: "Vendido", tipo: __TIPO_CAMPO_FECHA },
								tdNumeroVale: { campo: "numeroVale", descripcion: "Número de vale", abreviacion: "Nro. vale", tipo: __TIPO_CAMPO_NUMERICO },
								tdValorTasaInteresEfectivaAnual: { campo: "valorTasaInteresEfectivaAnual", descripcion: "TEA", abreviacion: "TEA", tipo: __TIPO_CAMPO_DECIMAL },
								tdValorUnidadIndexada: { campo: "valorUnidadIndexada", descripcion: "UI", abreviacion: "UI", tipo: __TIPO_CAMPO_DECIMAL },
								tdValorTasaInteresEfectivaAnual: { campo: "valorTasaInteresEfectivaAnual", descripcion: "TEA", abreviacion: "TEA", tipo: __TIPO_CAMPO_DECIMAL },
								tdPrecio: { campo: "precio", descripcion: "Precio", abreviacion: "Precio", tipo: __TIPO_CAMPO_DECIMAL },
								tdCuotas: { campo: "cuotas", descripcion: "Cuotas", abreviacion: "Cuotas", tipo: __TIPO_CAMPO_NUMERICO },
								tdValorCuota: { campo: "valorCuota", descripcion: "Valor cuota", abreviacion: "Valor cuota", tipo: __TIPO_CAMPO_DECIMAL, ancho: 90 },
								tdIntereses: { campo: "intereses", descripcion: "Intereses", abreviacion: "Intereses", tipo: __TIPO_CAMPO_DECIMAL, ancho: 90 },
								tdTipoProducto: { campo: "tipoProducto.descripcion", clave: "tipoProducto.id", descripcion: "Tipo de producto", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoProductos, clave: "id", valor: "descripcion" }, ancho: 80 },
								tdMarca: { campo: "marca.nombre", clave: "marca.id", descripcion: "Marca", abreviacion: "Marca", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMarcas, clave: "id", valor: "nombre" } },
								tdModelo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Modelo", abreviacion: "Modelo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" } },
								tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" } },
								tdFechaEnvioANucleo: { campo: "fechaEnvioANucleo", descripcion: "Fecha envío a Núcleo", abreviacion: "Env. Núcleo", tipo: __TIPO_CAMPO_FECHA_HORA },
							}, 
							true,
							reloadData,
							trContratoOnClick
						);
						
						grid.rebuild();
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						break;
					}
				}
				
				if (grid == null) {
					grid = new Grid(
						document.getElementById("divTableContratos"),
						{
							tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO }
						}, 
						true,
						reloadData,
						trContratoOnClick
					);
					
					grid.rebuild();
				}
			}, async: false
		}
	);
	
	reloadData();
	
	$("#divIFrameContrato").draggable();
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

function listEstados() {
	var result = [];
	
	EstadoDWR.list(
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
	
	ContratoDWR.listNuestroCreditoContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	ContratoDWR.countNuestroCreditoContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
}

function inputActualizarOnClick(event, element) {
	reloadData();
}

function trContratoOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_RIESGO_CREDITICIO;
		
	document.getElementById("iFrameContrato").src = "/LogisticaWEB/pages/contrato/contrato.jsp?m=" + formMode + "&cid=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameContrato"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameContrato"));
}

function inputExportarAExcelOnClick(event, element) {
	ContratoDWR.exportarAExcelVentasNuestroCredito(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
				document.getElementById("formExportarAExcel").submit();
			}, async: false
		}
	);
}