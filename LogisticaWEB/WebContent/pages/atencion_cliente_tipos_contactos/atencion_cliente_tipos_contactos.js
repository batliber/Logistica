var __ROL_ADMINISTRADOR = 1;
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
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_ATENCION_CLIENTES) {
				mode = __FORM_MODE_ADMIN;
				
				$("#divButtonNew").show();
				
				grid = new Grid(
					document.getElementById("divTableAtencionClienteTiposContactos"),
					{
						tdAtencionClienteTipoContactoDescripcion: { campo: "descripcion", descripcion: "Descripción", abreviacion: "Descripción", tipo: __TIPO_CAMPO_STRING, ancho: 200 } 
					}, 
					false,
					reloadData,
					trAtencionClienteTipoContactoOnClick
				);
				
				grid.rebuild();
					
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				
				break;
			}
		}
		
		reloadData();
		
		$("#divIFrameAtencionClienteTipoContacto").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/AtencionClienteTipoContactoREST/listContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/AtencionClienteTipoContactoREST/countContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.setCount(data);
	});
}

function trAtencionClienteTipoContactoOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameAtencionClienteTipoContacto").src = 
		"./atencion_cliente_tipo_contacto_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	
	showPopUp(document.getElementById("divIFrameAtencionClienteTipoContacto"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameAtencionClienteTipoContacto").src = "./atencion_cliente_tipo_contacto_edit.jsp";
	
	showPopUp(document.getElementById("divIFrameAtencionClienteTipoContacto"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}