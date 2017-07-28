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
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_ENCARGADO_ANALISIS_FINANCIERO) {
						$("#divButtonExportarAExcel").show();
						
						grid = new Grid(
							document.getElementById("divTableAnalisisRiesgo"),
							{
								tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 150 },
								tdDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
								tdFechaCelular: { campo: "fechaCelular", descripcion: "Antigüedad celular", abreviacion: "Antig. cel.", tipo: __TIPO_CAMPO_FECHA },
								tdDeudaCelular: { campo: "deudaCelular", descripcion: "Deuda celular", abreviacion: "Deuda cel.", tipo: __TIPO_CAMPO_BOOLEAN, ancho: 75 },
								tdRiesgoCrediticioCelular: { campo: "riesgoCrediticioCelular", descripcion: "Riesgo celular", abreviacion: "Riesgo cel.", tipo: __TIPO_CAMPO_BOOLEAN, ancho: 75 },
//								private Long contratosCelular;
//								private Long contratosSolaFirmaCelular;
//								private Long contratosGarantiaCelular;
//								private Double saldoAyudaEconomicaCelular;
//								private Long numeroClienteFijo;
//								private String nombreClienteFijo;
								tdEstadoDeudaClienteFijo: { campo: "estadoDeudaClienteFijo", descripcion: "Estado deuda telefonía fija", abreviacion: "Estado deuda fijo", tipo: __TIPO_CAMPO_STRING },
								tdFact: { campo: "fact", descripcion: "Obtenido", abreviacion: "Obtenido", tipo: __TIPO_CAMPO_FECHA_HORA },
//								private Long numeroClienteMovil;
							}, 
							true,
							reloadData,
							trAnalisisRiesgoOnClick
						);
						
						grid.rebuild();
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
						break;
					}
				}
				
				if (grid == null) {
					grid = new Grid(
						document.getElementById("divTableAnalisisRiesgo"),
						{
							tdDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_NUMERICO }
						}, 
						true,
						reloadData,
						trAnalisisRiesgoOnClick
					);
					
					grid.rebuild();
				}
			}, async: false
		}
	);
	
	reloadData();

	$("#divIFrameImportacionArchivo").draggable();
}

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

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	ACMInterfaceRiesgoCrediticioDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				var dataWithId = {
					cantidadRegistros: data.cantidadRegistros,
					registrosMuestra: []
				};
				
				for (var i=0; i<data.cantidadRegistros; i++) {
					dataWithId.registrosMuestra[i] = data.registrosMuestra[i];
					dataWithId.registrosMuestra[i].id = data.registrosMuestra[i].documento;
				}
				
				grid.reload(data);
			}
		}
	);
	
	ACMInterfaceRiesgoCrediticioDWR.countContextAware(
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

function trAnalisisRiesgoOnClick(eventObject) {
	
}

function inputExportarAExcelOnClick(event, element) {
	ACMInterfaceRiesgoCrediticioDWR.exportarAExcel(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
				document.getElementById("formExportarAExcel").submit();
			}, async: false
		}
	);
}