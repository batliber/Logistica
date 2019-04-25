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
								tdInstitucionFinancieras: { 
									campo: "bcuInterfaceRiesgoCrediticioInstitucionFinancieras",
									detail: {
										tdInstitucionFinanciera: { campo: "institucionFinanciera", descripcion: "Institución financiera", abreviacion: "Institución", tipo: __TIPO_CAMPO_STRING, ancho: 300 },
										tdCalificacion: { campo: "calificacion", descripcion: "Calificación", abreviacion: "Calificación", tipo: __TIPO_CAMPO_STRING, ancho: 100 },
//										private Double vigente;
//										private Double vigenteNoAutoliquidable;
//										private Double previsionesTotales;
//										private Double contingencias;
//										tdFact: { campo: "fact", descripcion: "Controlado", abreviacion: "Controlado", tipo: __TIPO_CAMPO_FECHA_HORA },
//										private Long term;
									},
									tipo: __TIPO_CAMPO_DETAIL
								},
								tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 150 },
								tdDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
								tdPeriodo: { campo: "periodo", descripcion: "Período", abreviación: "Período", tipo:__TIPO_CAMPO_STRING, ancho: 60 },
//								private String nombreCompleto;
//								private String actividad;
								tdVigente: { campo: "vigente", descripcion: "Crédito vigente", abreviacion: "Créd. vig.", tipo: __TIPO_CAMPO_DECIMAL, ancho: 70 },
								tdVigenteNoAutoliquidable: { campo: "vigenteNoAutoliquidable", descripcion: "Cédito vigente no auto-liquidable", abreviacion: "Créd. vig. no auto-liq.", tipo: __TIPO_CAMPO_DECIMAL, ancho: 130 },
								tdGarantiasComputables: { campo: "garantiasComputables", descripcion: "Garantías computables", abreviacion: "Garant. comput.", tipo: __TIPO_CAMPO_DECIMAL, ancho: 105 },
								tdGarantiasNoComputables: { campo: "garantiasNoComputables", descripcion: "Garantías no computables", abreviacion: "Garant. no comput.", tipo: __TIPO_CAMPO_DECIMAL, ancho: 120 },
								tdCastigadoPorAtraso: { campo: "castigadoPorAtraso", descripcion: "Castigado por atraso", abreviacion: "Castig. atraso", tipo: __TIPO_CAMPO_BOOLEAN, ancho: 90 },
								tdCastigadoPorQuitasYDesistimiento: { campo: "castigadoPorQuitasYDesistimiento", descripcion: "Castigado quitas y desistimiento", abreviacion: "Castig. quit. des.", tipo: __TIPO_CAMPO_BOOLEAN, ancho: 105 },
								tdPrevisionesTotales: { campo: "previsionesTotales", descripcion: "Previsiones totales", abreviacion: "Prevs. tot.", tipo: __TIPO_CAMPO_DECIMAL, ancho: 70 },
								tdContingencias: { campo: "contingencias", descripcion: "Contingencias", abreviacion: "Conting.", tipo: __TIPO_CAMPO_DECIMAL, ancho: 65 },
								tdOtorgantesGarantias: { campo: "vigente", descripcion: "Otorgantes garantias", abreviacion: "Otorg. garant.", tipo: __TIPO_CAMPO_DECIMAL, ancho: 90 },
								tdFact: { campo: "fact", descripcion: "Obtenido", abreviacion: "Obtenido", tipo: __TIPO_CAMPO_FECHA_HORA },
							}, 
							true,
							reloadData,
							trAnalisisRiesgoOnClick,
							tdAnalisisRiesgoOpenDetail
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

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	BCUInterfaceRiesgoCrediticioDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	BCUInterfaceRiesgoCrediticioDWR.countContextAware(
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

function tdAnalisisRiesgoOpenDetail(divRow) {
	var id = $(divRow).attr("id");
	
	var detail = [];
	
	BCUInterfaceRiesgoCrediticioInstitucionFinancieraDWR.listByBCUInterfaceRiesgoCrediticioId(
		id,
		{
			callback: function(data) {
				detail = data;
			}, async: false
		}
	);
	
	return detail;
}

function inputExportarAExcelOnClick(event, element) {
	BCUInterfaceRiesgoCrediticioDWR.exportarAExcel(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
				document.getElementById("formExportarAExcel").submit();
			}, async: false
		}
	);
}
