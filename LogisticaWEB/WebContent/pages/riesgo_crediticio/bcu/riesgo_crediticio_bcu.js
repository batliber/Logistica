var __ROL_ADMINISTRADOR = 1;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonExportarAExcel").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR) {
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
//								private String periodo;
//								private String nombreCompleto;
//								private String actividad;
								tdVigente: { campo: "vigente", descripcion: "Crédito vigente", abreviacion: "Créd. vigente", tipo: __TIPO_CAMPO_DECIMAL, ancho: 90 },
//								private Double vigenteNoAutoliquidable;
//								private Double garantiasComputables;
//								private Double garantiasNoComputables;
								tdCastigadoPorAtraso: { campo: "castigadoPorAtraso", descripcion: "Castigado por atraso", abreviacion: "Castigado atraso", tipo: __TIPO_CAMPO_BOOLEAN, ancho: 75 },
								tdCastigadoPorQuitasYDesistimiento: { campo: "castigadoPorQuitasYDesistimiento", descripcion: "Castigado quitas y desistimiento", abreviacion: "Castigado quitas y des.", tipo: __TIPO_CAMPO_BOOLEAN, ancho: 75 },
//								private Double previsionesTotales;
//								private Double contingencias;
//								private Double otorgantesGarantias;
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