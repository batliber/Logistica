var __ROL_ADMINISTRADOR = 1;
var __ROL_ENCARGADO_ANALISIS_FINANCIERO = 13;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonSubirArchivo").hide();
	$("#divButtonExportarAExcel").hide();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
    }).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_ENCARGADO_ANALISIS_FINANCIERO) {
				$("#divButtonSubirArchivo").show();
				$("#divButtonExportarAExcel").show();
				
				grid = new Grid(
					document.getElementById("divTableAnalisisRiesgo"),
					{
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 150 },
						tdDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
						tdFechaImportacion: { campo: "fechaImportacion", descripcion: "Fecha de importación", abreviacion: "Importado", tipo: __TIPO_CAMPO_FECHA, ancho: 80 },
						tdTipoControlRiesgoCrediticio: { campo: "tipoControlRiesgoCrediticio.descripcion", clave: "tipoControlRiesgoCrediticio.id", descripcion: "Tipo", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoControlRiesgoCrediticios, clave: "id", valor: "descripcion" }, ancho: 100 },
						tdCalificacionRiesgoCrediticioAntel: { campo: "calificacionRiesgoCrediticioAntel.descripcion", clave: "calificacionRiesgoCrediticioAntel.id", descripcion: "Calificación ANTEL", abreviacion: "Calif. ANTEL", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listCalificacionRiesgoCrediticioAntel, clave: "id", valor: "descripcion" }, ancho: 100 },
						tdCalificacionRiesgoCrediticioBCU: { campo: "calificacionRiesgoCrediticioBCU.descripcion", clave: "calificacionRiesgoCrediticioBCU.id", descripcion: "Calificación BCU", abreviacion: "Calif. BCU", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listCalificacionRiesgoCrediticioBCU, clave: "id", valor: "descripcion" }, ancho: 100 },
						tdEstadoRiesgoCrediticio: { campo: "estadoRiesgoCrediticio.nombre", clave: "estadoRiesgoCrediticio.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoRiesgoCrediticios, clave: "id", valor: "nombre" }, ancho: 180 },
						tdFechaVigenciaDesde: { campo: "fechaVigenciaDesde", descripcion: "Vigencia", abreviacion: "Vigencia", tipo: __TIPO_CAMPO_FECHA_HORA },
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
					tdDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING }
				}, 
				true,
				reloadData,
				trAnalisisRiesgoOnClick
			);
			
			grid.rebuild();
		}
		
		reloadData();

		$("#divIFrameImportacionArchivo").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/RiesgoCrediticioREST/listContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) { 
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/RiesgoCrediticioREST/countContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) { 
    	grid.setCount(data);
    });
}

function inputActualizarOnClick(event, element) {
	reloadData();
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}

function trAnalisisRiesgoOnClick(eventObject) {
	return false;
}

function inputSubirArchivoOnClick(event, element) {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
    }).then(function(data) {
    	fillSelect(
    		"selectEmpresa",
    		data,
    		"id",
    		"nombre"
    	);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/TipoControlRiesgoCrediticioREST/list"
    }).then(function(data) {
    	fillSelect(
    		"selectTipoControlRiesgoCrediticio",
    		data,
    		"id",
    		"descripcion"
    	);
    });
	
	showPopUp(document.getElementById("divIFrameImportacionArchivo"));
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameImportacionArchivo"));
	
	$("#selectEmpresa").val("0");
	$("#inputArchivo").val("");
	
	reloadData();
}

function inputAceptarSubirArchivoOnClick(event, element) {
	if ($("#selectEmpresa").val() == "0") {
		alert("Debe seleccionar una empresa.");
		
		return;
	} else if ($("#selectTipoControlRiesgoCrediticio").val == "0") {
		alert("Debe seleccionar un tipo de control de riesgo.");
		
		return;
	}
	
	var formData = new FormData(document.getElementById("formSubirArchivo"));
	
	$.ajax({
		url: '/LogisticaWEB/Upload', 
		type: 'POST',
		data: formData,
		processData: false,
		contentType: false
	}).then(function(data) {
		if (confirm(data.message.replace(new RegExp("\\|", "g"), "\n"))) {
			$.ajax({
				url: '/LogisticaWEB/RESTFacade/RiesgoCrediticioREST/procesarArchivo', 
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					"nombre": data.fileName,
					"empresaId": data.empresaId,
					"tipoControlRiesgoCrediticioId": data.tipoControlRiesgoCrediticioId
				})
			}).then(function(data) {
				if (data != null) {
					alert(data.mensaje.replace(new RegExp("\\|", "g"), "\n"));
				}
				
				reloadData();
			});
		}
	}, function(data) {
		alert(data);
	});
}

function inputExportarAExcelOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/RiesgoCrediticioREST/exportarAExcel",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(metadataConsulta)
	    }).then(function(data) {
	    	if (data != null) {
	    		document.getElementById("formExportarAExcel").action = 
	    			"/LogisticaWEB/Download?fn=" + data.nombreArchivo;
	    		
	    		document.getElementById("formExportarAExcel").submit();
	    	}
	    });
	}
}