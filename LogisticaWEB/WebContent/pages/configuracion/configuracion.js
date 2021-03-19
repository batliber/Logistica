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
				$("#divButtonNew").show();
				
				grid = new Grid(
					document.getElementById("divConfiguracion"),
					{
						tdClave: { campo: "clave", descripcion: "Clave", abreviacion: "Clave", tipo: __TIPO_CAMPO_STRING, ancho: 300 },
						tdValor: { campo: "valor", descripcion: "Valor", abreviacion: "Valor", tipo: __TIPO_CAMPO_STRING, ancho: 600 },
						tdFact: { campo: "fact", descripcion: "Modificado", abreviacion: "Modificado", tipo: __TIPO_CAMPO_FECHA_HORA }
					}, 
					true,
					reloadData,
					trConfiguracionOnClick
				);
				
				grid.rebuild();
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				
				break;
			}
		}
    	
    	reloadData();
		
		$("#divIFrameConfiguracion").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ConfigurationREST/listContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ConfigurationREST/countContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) { 
    	grid.setCount(data);
    });
}

function trConfiguracionOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameConfiguracion").src = 
		"./configuracion_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	
	showPopUp(document.getElementById("divIFrameConfiguracion"));
}

function inputActualizarOnClick(event, element) {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameConfiguracion").src = "./configuracion_edit.jsp";
	
	showPopUp(document.getElementById("divIFrameConfiguracion"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}