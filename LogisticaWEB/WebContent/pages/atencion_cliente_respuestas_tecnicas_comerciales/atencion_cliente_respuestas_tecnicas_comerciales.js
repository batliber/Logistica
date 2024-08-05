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
					document.getElementById("divTableAtencionClienteRespuestasTecnicasComerciales"),
					{
						tdAtencionClienteRespuestaTecnicaComercialDescripcion: { campo: "descripcion", descripcion: "Descripción", abreviacion: "Descripción", tipo: __TIPO_CAMPO_STRING, ancho: 200 } 
					}, 
					false,
					reloadData,
					trAtencionClienteRespuestaTecnicaComercialOnClick
				);
				
				grid.rebuild();
					
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				
				break;
			}
		}
		
		reloadData();
		
		$("#divIFrameAtencionClienteRespuestaTecnicaComercial").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/AtencionClienteRespuestaTecnicaComercialREST/listContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/AtencionClienteRespuestaTecnicaComercialREST/countContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.setCount(data);
	});
}

function trAtencionClienteRespuestaTecnicaComercialOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameAtencionClienteRespuestaTecnicaComercial").src = 
		"./atencion_cliente_respuesta_tecnica_comercial_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	
	showPopUp(document.getElementById("divIFrameAtencionClienteRespuestaTecnicaComercial"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameAtencionClienteRespuestaTecnicaComercial").src = "./atencion_cliente_respuesta_tecnica_comercial_edit.jsp";
	
	showPopUp(document.getElementById("divIFrameAtencionClienteRespuestaTecnicaComercial"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}