var __ROL_ADMINISTRADOR = 1;
var __ROL_GERENTE_DE_EMPRESA = 1;
var __ROL_DEMO = 21;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonSubirArchivo").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_GERENTE_DE_EMPRESA
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
				$("#divButtonSubirArchivo").show();
				
				grid = new Grid(
					document.getElementById("divTableLiquidaciones"),
					{
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" } },
						tdPlan: { campo: "plan", descripcion: "Plan", abreviacion: "Plan", tipo: __TIPO_CAMPO_STRING },
						tdMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
						tdFechaLiquidacion: { campo: "fechaLiquidacion", descripcion: "Fecha de liquidación", abreviacion: "Fecha", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdNombreConcepto: { campo: "nombreConcepto", descripcion: "Nombre de concepto", abreviacion: "Concepto", tipo: __TIPO_CAMPO_STRING },
						tdMoneda: { campo: "moneda.simbolo", clave: "moneda.id", descripcion: "Moneda", abreviacion: "Moneda", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listMonedas, clave: "id", valor: "nombre" } },
						tdImporte: { campo: "importe", descripcion: "Importe", abreviacion: "Importe", tipo: __TIPO_CAMPO_DECIMAL }
					}, 
					true,
					reloadData,
					trLiquidacionOnClick
				);
				
				grid.rebuild();
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
			
				break;
			}
		}
		
		reloadData();
		
		$("#divIFrameLiquidacion").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/LiquidacionREST/listContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/LiquidacionREST/countContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.setCount(data);
    });
}

function trLiquidacionOnClick(eventObject) {
	
}

function inputActualizarOnClick() {
	reloadData();
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
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
				url: '/LogisticaWEB/RESTFacade/LiquidacionREST/procesarArchivo', 
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					"nombre": data.fileName
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