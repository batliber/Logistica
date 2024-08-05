var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_DISTRIBUCION = 7;
var __ROL_MAESTROS_RIVERGREEN = 20;
var __ROL_SUPERVISOR_ATENCION_CLIENTES = 75559429;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonNew").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_DISTRIBUCION
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_MAESTROS_RIVERGREEN
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_ATENCION_CLIENTES) {
				mode = __FORM_MODE_ADMIN;
				$("#divButtonNew").show();
				
				grid = new Grid(
					document.getElementById("divTableZonas"),
					{
						tdZonaNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 200 },
						tdZonaDetalle: { campo: "detalle", descripcion: "Detalle", abreviacion: "Detalle", tipo: __TIPO_CAMPO_STRING, ancho: 200 },
						tdZonaDepartamento: { campo: "departamento.nombre", clave: "departamento.id", descripcion: "Departamento", abreviacion: "Departamento", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre" } } 
					}, 
					true,
					reloadData,
					trZonaOnClick
				);
				
				grid.rebuild();
					
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				
				break;
			}
		}
		
		reloadData();
		
		$("#divIFrameZona").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ZonaREST/listContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ZonaREST/countContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) { 
    	grid.setCount(data);
    });
}

function trZonaOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_NEW;
	
	document.getElementById("iFrameZona").src = "./zona_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameZona"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameZona").src = "./zona_edit.jsp";
	showPopUp(document.getElementById("divIFrameZona"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}