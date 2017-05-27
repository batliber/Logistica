var __ROL_ADMINISTRADOR = 1;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonSubirArchivo").hide();
	$("#divButtonExportarAExcel").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR) {
						$("#divButtonSubirArchivo").show();
						$("#divButtonExportarAExcel").show();
						
						grid = new Grid(
							document.getElementById("divTableAnalisisRiesgo"),
							{
								tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 150 },
								tdDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
								tdTipoControlRiesgoCrediticio: { campo: "tipoControlRiesgoCrediticio.descripcion", clave: "tipoControlRiesgoCrediticio.id", descripcion: "Tipo", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoControlRiesgoCrediticios, clave: "id", valor: "descripcion" }, ancho: 100 },
								tdCalificacionRiesgoCrediticioAntel: { campo: "calificacionRiesgoCrediticioAntel.descripcion", clave: "calificacionRiesgoCrediticioAntel.id", descripcion: "Calificación ANTEL", abreviacion: "Calif. ANTEL", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listCalificacionRiesgoCrediticioAntel, clave: "id", valor: "descripcion" }, ancho: 100 },
								tdCalificacionRiesgoCrediticioBCU: { campo: "calificacionRiesgoCrediticioBCU.descripcion", clave: "calificacionRiesgoCrediticioBCU.id", descripcion: "Calificación BCU", abreviacion: "Calif. BCU", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listCalificacionRiesgoCrediticioBCU, clave: "id", valor: "descripcion" }, ancho: 100 },
								tdEstadoRiesgoCrediticio: { campo: "estadoRiesgoCrediticio.nombre", clave: "estadoRiesgoCrediticio.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoRiesgoCrediticios, clave: "id", valor: "nombre" }, ancho: 100 },
								tdFechaVigenciaDesde: { campo: "fechaVigenciaDesde", descripcion: "Vigencia", abreviacion: "Vigencia", tipo: __TIPO_CAMPO_FECHA },
								tdFact: { campo: "fact", descripcion: "Controlado", abreviacion: "Controlado", tipo: __TIPO_CAMPO_FECHA_HORA },
							}, 
							true,
							reloadData,
							trAnalisisRiesgoOnClick
						);
						
						grid.rebuild();
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleTripleSize");
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

function listTipoControlRiesgoCrediticios() {
	var result = [];
	
	TipoControlRiesgoCrediticioDWR.list(
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

function listEstadoRiesgoCrediticios() {
	var result = [];
	
	EstadoRiesgoCrediticioDWR.list(
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

function listCalificacionRiesgoCrediticioAntel() {
	var result = [];
	
	CalificacionRiesgoCrediticioAntelDWR.list(
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

function listCalificacionRiesgoCrediticioBCU() {
	var result = [];
	
	CalificacionRiesgoCrediticioBCUDWR.list(
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
	
	RiesgoCrediticioDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	RiesgoCrediticioDWR.countContextAware(
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

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}

function trAnalisisRiesgoOnClick(eventObject) {
	
}

function inputSubirArchivoOnClick(event, element) {
	$("#selectEmpresa > option").remove();
	$("#selectTipoControlRiesgoCrediticio > option").remove();
	
	$("#selectEmpresa").append("<option value='0'>Seleccione...</option>");
	$("#selectTipoControlRiesgoCrediticio").append("<option value='0'>Seleccione...</option>");
	
	UsuarioRolEmpresaDWR.listEmpresasByContext(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectEmpresa").append(html);
			}, async: false
		}
	);
	
	TipoControlRiesgoCrediticioDWR.list(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectTipoControlRiesgoCrediticio").append(html);
			}, async: false
		}
	);
	
	showPopUp(document.getElementById("divIFrameImportacionArchivo"));
}

function inputAceptarSubirArchivoOnClick(event, element) {
	if ($("#selectEmpresa").val() == "0") {
		alert("Debe seleccionar una empresa.");
	} else if ($("#selectTipoControlRiesgoCrediticio").val == "0") {
		alert("Debe seleccionar un tipo de control de riesgo.");
	} else {
		var xmlHTTPRequest = new XMLHttpRequest();
		xmlHTTPRequest.open(
			"POST",
			"/LogisticaWEB/Upload",
			false
		);
		
		var formData = new FormData(document.getElementById("formSubirArchivo"));
		
		xmlHTTPRequest.send(formData);
		
		if (xmlHTTPRequest.status == 200) {
			var response = JSON.parse(xmlHTTPRequest.responseText);
			
			if (confirm(response.message.replace(new RegExp("\\|", "g"), "\n"))) {
				RiesgoCrediticioDWR.procesarArchivoEmpresa(
					response.fileName,
					response.empresaId,
					response.tipoControlRiesgoCrediticioId,
					{
						callback: function(data) {
							if (data != null) {
								alert(data.replace(new RegExp("\\|", "g"), "\n"));
							}
							
							reloadData();
						}, async: false
					}
				);
			}
		} else {
			alert(xmlHTTPRequest.responseText);
		}
	}
}