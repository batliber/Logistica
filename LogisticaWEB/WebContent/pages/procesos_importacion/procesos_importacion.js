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
					document.getElementById("divTableProcesosImportacion"),
					{
						tdNombreArchivo: { campo: "nombreArchivo", descripcion: "Archivo", abreviacion: "Archivo", tipo: __TIPO_CAMPO_STRING, ancho: 300 },
						tdFechaInicio: { campo: "fechaInicio", descripcion: "Fecha de inicio", abreviacion: "Inicio", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdFechaFin: { campo: "fechaFin", descripcion: "Fecha de fin", abreviacion: "Fin", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 375 },
						tdEstado: { campo: "estadoProcesoImportacion.nombre", clave: "estadoProcesoImportacion.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadosProcesoImportacion, clave: "id", valor: "nombre"} },
						tdTipo: { campo: "tipoProcesoImportacion.descripcion", clave: "tipoProcesoImportacion.id", descripcion: "Tipo", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTiposProcesoImportacion, clave: "id", valor: "descripcion"}, ancho: 120 },
						tdUsuario: { campo: "usuario.nombre", clave: "usuario.id", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listUsuarios, clave: "id", valor: "nombre"} }
					},
					true,
					reloadData,
					trProcesoImportacionOnClick
				);
				
				grid.rebuild();
				
				break;
			}
		}
		
		$("#divIFrameProcesoImportacion").draggable();
		
		reloadData();
	});
}

function reloadData() {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ProcesoImportacionREST/listContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ProcesoImportacionREST/countContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) { 
    	grid.setCount(data);
    });
}

function trProcesoImportacionOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameProcesoImportacion").src = "./proceso_importacion_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameProcesoImportacion"));
}

function inputActualizarOnClick() {
	reloadData();
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}