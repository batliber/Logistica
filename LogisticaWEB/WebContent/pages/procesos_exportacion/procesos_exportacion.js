var __ROL_ADMINISTRADOR = 1;

var grid = null;
var map = null;

$(document).ready(init);

function init() {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
    }).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR) {
				mode = __FORM_MODE_ADMIN;
				
				grid = new Grid(
					document.getElementById("divTableProcesosExportacion"),
					{
						tdNombreArchivo: { campo: "nombreArchivo", descripcion: "Archivo", abreviacion: "Archivo", tipo: __TIPO_CAMPO_STRING, ancho: 150 },
						tdFechaInicio: { campo: "fechaInicio", descripcion: "Fecha de inicio", abreviacion: "Inicio", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdFechaFin: { campo: "fechaFin", descripcion: "Fecha de fin", abreviacion: "Fin", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 375 },
						//tdEstado: { campo: "estadoProcesoExportacion.nombre", clave: "estadoProcesoImportacion.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadosProcesoImportacion, clave: "id", valor: "nombre"} },
						//tdTipo: { campo: "tipoProcesoExportacion.descripcion", clave: "tipoProcesoImportacion.id", descripcion: "Tipo", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTiposProcesoImportacion, clave: "id", valor: "descripcion"}, ancho: 120 },
						tdUsuario: { campo: "usuario.nombre", clave: "usuario.id", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listUsuarios, clave: "id", valor: "nombre"} }
					},
					true,
					reloadData,
					trProcesoExportacionOnClick
				);
				
				grid.rebuild();
				
				break;
			}
		}
		
		$("#divIFrameProcesoExportacion").draggable();
		
		reloadData();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ProcesoExportacionREST/listContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ProcesoExportacionREST/countContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) { 
    	grid.setCount(data);
    });
}

function trProcesoExportacionOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameProcesoExportacion").src = "./proceso_exportacion_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameProcesoExportacion"));
}

function inputActualizarOnClick() {
	reloadData();
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}