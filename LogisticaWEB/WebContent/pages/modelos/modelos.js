var __ROL_ADMINISTRADOR = 1;
var __ROL_MAESTROS_RIVERGREEN = 20;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonNew").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_MAESTROS_RIVERGREEN) {
				mode = __FORM_MODE_ADMIN;
				
				$("#divButtonNew").show();
				
				grid = new Grid(
					document.getElementById("divTableModelos"),
					{
						tdModeloMarca: { campo: "marca.nombre", clave: "marca.id", descripcion: "Marca", abreviacion: "Marca", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMarcas, clave: "id", valor: "nombre" }, ancho: 150 },
						tdModeloDescripcion: { campo: "descripcion", descripcion: "Descripción", abreviacion: "Descripción", tipo: __TIPO_CAMPO_STRING, ancho: 250 },
						tdModeloEmpresaService: { campo: "empresaService.nombre", clave: "empresaService.id", descripcion: "Service", abreviacion: "Service", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresaServices, clave: "id", valor: "nombre" }, ancho: 200 },
						tdModeloFechaBaja: { campo: "fechaBaja", descripcion: "Eliminado", abreviacion: "Eliminado", tipo: __TIPO_CAMPO_FECHA_HORA }
					}, 
					true,
					reloadData,
					trModeloOnClick
				);
				
				grid.rebuild();
				
				grid.filtroDinamico.agregarFiltroManual(
					{
						campo: "fechaBaja",
						operador: "nl",
						valores: []
					},
					false
				);
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				break;
			}
		}
		
		reloadData();
		
		$("#divIFrameModelo").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ModeloREST/listContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ModeloREST/countContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) { 
    	grid.setCount(data);
    });
}

function trModeloOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameModelo").src = "./modelo_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameModelo"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFrameModelo").src = "./modelo_edit.jsp";
	showPopUp(document.getElementById("divIFrameModelo"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}