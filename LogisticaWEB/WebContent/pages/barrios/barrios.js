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
				
				grid = new Grid(
					document.getElementById("divTableBarrios"),
					{
						tdBarrioNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 350 },
						tdBarrioZona: { campo: "zona.nombre", clave: "zona.id", descripcion: "Zona", abreviacion: "Zona", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listZonas, clave: "id", valor: "nombre" }, ancho: 250 },
						tdBarrioDepartamento: { campo: "departamento.nombre", clave: "departamento.id", descripcion: "Departamento", abreviacion: "Departamento", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre" }, ancho: 150 }
					}, 
					true,
					reloadData,
					trBarrioOnClick
				);
				
				grid.rebuild();
				
				$("#divButtonNew").show();
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				
				break;
			}
		}
		
		reloadData();
		
		$("#divIFrameBarrio").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/BarrioREST/listContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/BarrioREST/countContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) { 
    	grid.setCount(data);
    });
}

function trBarrioOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_NEW;
	
	document.getElementById("iFrameBarrio").src = "./barrio_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameBarrio"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameBarrio").src = "./barrio_edit.jsp";
	showPopUp(document.getElementById("divIFrameBarrio"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}