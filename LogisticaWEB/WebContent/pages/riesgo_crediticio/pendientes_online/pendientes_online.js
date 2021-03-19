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
					document.getElementById("divTablePendientesOnline"),
					{
						tdDocumento: { campo: "BCUDOC", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
						tdEstado: { campo: "BCUCALI", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_STRING },
						tdFecha: { campo: "BCUFECH", descripcion: "Fecha", abreviacion: "Fecha", tipo: __TIPO_CAMPO_STRING }
					}, 
					true,
					reloadData,
					trPendientesOnlineOnClick
				);
				
				grid.rebuild();
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				break;
			}
		}
		
		if (grid == null) {
			grid = new Grid(
				document.getElementById("divTablePendientesOnline"),
				{
					tdDocumento: { campo: "bcudoc", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING }
				}, 
				true,
				reloadData,
				trPendientesOnlineOnClick
			);
			
			grid.rebuild();
		}
		
		reloadData();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/RiesgoOnlineREST/listPendientes"
    }).then(function(data) { 
    	grid.reload({
    		cantidadRegistros: 0,
    		registrosMuestra: data
    	}); 
    });
}

function inputActualizarOnClick(event, element) {
	reloadData();
}

function trPendientesOnlineOnClick(eventObject) {
	return false;
}

function inputReprocesarOnClick(event, element) {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/RiesgoOnlineREST/reprocesar"
    }).then(function(data) { 
    	alert("Operación exitosa."); 
    });
}