var __ROL_ADMINISTRADOR = 1;
var __ROL_ENCARGADO_ANALISIS_FINANCIERO = 13;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonExportarAExcel").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_ENCARGADO_ANALISIS_FINANCIERO){
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
								tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 90 },
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

function trContratoOnClick() {
	
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