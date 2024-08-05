var __ROL_ADMINISTRADOR = 1;

var grid = null;

$(document).ready(init);
		
function init() {
	$("#divButtonNew").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR) {
				mode = __FORM_MODE_ADMIN;
				
				$("#divButtonNew").show();
				
				grid = new Grid(
					document.getElementById("divTableModalidadesVenta"),
					{
						tdDescripcion: { campo: "descripcion", descripcion: "Descripción", abreviacion: "Descripción", tipo: __TIPO_CAMPO_STRING, ancho: 200 } 
					}, 
					false,
					reloadData,
					trModalidadVentaOnClick
				);
				
				grid.rebuild();
					
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				
				break;
			}
		}
		
		reloadData();
		
		$("#divIFrameModalidadVenta").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ModalidadVentaREST/listContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ModalidadVentaREST/countContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.setCount(data);
	});
}

function trModalidadVentaOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameModalidadVenta").src = 
		"./modalidad_venta_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	
	showPopUp(document.getElementById("divIFrameModalidadVenta"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameModalidadVenta").src = "./modalidad_venta_edit.jsp";
	
	showPopUp(document.getElementById("divIFrameModalidadVenta"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}