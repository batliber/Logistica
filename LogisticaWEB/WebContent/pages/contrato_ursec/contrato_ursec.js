var __ROL_ADMINISTRADOR = 1;
var __ROL_DEMO = 21;

var grid = null;
		
$(document).ready(init);

function init() {
	$("#divButtonSubirArchivo").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
				$("#divButtonSubirArchivo").show();
				
				grid = new Grid(
					document.getElementById("divTableURSEC"),
					{
						tdMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
						tdFcre: { campo: "fcre", abreviacion: "Creado", descripcion: "Creado", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdFact: { campo: "fact", abreviacion: "Modificado", descripcion: "Modificado", tipo: __TIPO_CAMPO_FECHA_HORA }
					}, 
					true,
					reloadData,
					trURSECOnClick
				);
				
				grid.rebuild();
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				break;
			}
		}
		
		if (grid == null) {
			grid = new Grid(
				document.getElementById("divTableURSEC"),
				{
					tdMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
					tdFcre: { campo: "fcre", abreviacion: "Creado", descripcion: "Creado", tipo: __TIPO_CAMPO_FECHA_HORA },
					tdFact: { campo: "fact", abreviacion: "Modificado", descripcion: "Modificado", tipo: __TIPO_CAMPO_FECHA_HORA }
				}, 
				true,
				reloadData,
				trURSECOnClick
			);
			
			grid.rebuild();
		}
		
		reloadData();

		$("#divIFrameImportacionArchivo").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoURSECREST/listContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoURSECREST/countContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.setCount(data);
	});
}

function inputActualizarOnClick(event, element) {
	reloadData();
}

function trURSECOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
//	document.getElementById("iFrameControl").src = "/LogisticaWEB/pages/controles/control.jsp?m=" + formMode + "&aid=" + $(target).attr("id");
//	showPopUp(document.getElementById("divIFrameControl"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameURSEC"));
}

function inputSubirArchivoOnClick(event, element) {
	showPopUp(document.getElementById("divIFrameImportacionArchivo"));
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameImportacionArchivo"));
	
	$("#inputArchivo").val("");
	
	reloadData();
}

function inputAceptarSubirArchivoOnClick(event, element) {
	var formData = new FormData(document.getElementById("formSubirArchivo"));
	
	$.ajax({
		url: '/LogisticaWEB/Upload', 
		type: 'POST',
		data: formData,
		processData: false,
		contentType: false
	}).then(function(data) {
		if (confirm(data.message.replace(new RegExp("\\|", "g"), "\n"))) {
			$.ajax({
				url: '/LogisticaWEB/RESTFacade/ContratoURSECREST/procesarArchivoURSEC', 
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					"nombre": data.fileName,
				})
			}).then(function(data) {
				if (data != null) {
					alert(data.mensaje.replace(new RegExp("\\|", "g"), "\n"));
				}
				
				reloadData();
			});
		}
	}, function(data) {
		alert(data);
	});
}