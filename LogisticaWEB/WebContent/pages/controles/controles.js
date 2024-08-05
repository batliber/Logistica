var __ROL_ADMINISTRADOR = 1;
var __ROL_DEMO = 21;

var grid = null;
		
$(document).ready(init);

function init() {
	$("#divButtonAsignar").hide();
	$("#divButtonSubirArchivo").hide();
	$("#divButtonAgregarMid").hide();
	$("#divButtonExportarAExcel").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
				$("#divButtonSubirArchivo").show();
				$("#divButtonExportarAExcel").show();
				
				grid = new Grid(
					document.getElementById("divTableControles"),
					{
						tdMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
						tdChip: { campo: "chip", abreviacion: "Chip", descripcion: "Chip", tipo: __TIPO_CAMPO_STRING, ancho: 120, oculto: true },
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 100 },
						tdFechaImportacion: { campo: "fechaImportacion", abreviacion: "Importado", descripcion: "Fecha de importación", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdFechaActivacion: { campo: "fechaActivacion", abreviacion: "Activado", descripcion: "Fecha de activación", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdFechaControl: { campo: "fechaControl", abreviacion: "Controlado", descripcion: "Fecha de control", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdFechaVencimiento: { campo: "fechaVencimiento", abreviacion: "Vence", descripcion: "Fecha de vencimiento", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdCargaInicial: { campo: "cargaInicial", descripcion: "Carga inicial", abreviacion: "Carga ini.", tipo: __TIPO_CAMPO_NUMERICO, ancho: 75 },
						tdMontoCargar: { campo: "montoCargar", descripcion: "Monto a cargar", abreviacion: "Monto car.", tipo: __TIPO_CAMPO_NUMERICO, ancho: 80 },
						tdMontoTotal: { campo: "montoTotal", descripcion: "Monto total", abreviacion: "Monto tot.", tipo: __TIPO_CAMPO_NUMERICO, ancho: 80 },
						tdTipoControl: { campo: "tipoControl.descripcion", clave: "tipoControl.id", descripcion: "Tipo de control", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoControles, clave: "id", valor: "descripcion" } },
						tdEstadoControl: { campo: "estadoControl.nombre", clave: "estadoControl.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoControles, clave: "id", valor: "nombre" } },
						tdDistribuidor: { campo: "distribuidor.nombre", clave: "distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidores, clave: "id", valor: "nombre" } },
						tdFechaAsignacionDistribuidor: { campo: "fechaAsignacionDistribuidor", abreviacion: "F. Asign. Distr.", descripcion: "Fecha de asign. Distribuidor", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdPuntoVenta: { campo: "puntoVenta.nombre", clave: "puntoVenta.id", descripcion: "Punto de venta", abreviacion: "Pto. venta", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPuntoVentas, clave: "id", valor: "nombre" }, ancho: 100 },
						tdFechaAsignacionPuntoVenta: { campo: "fechaAsignacionPuntoVenta", abreviacion: "F. Asign. P.V.", descripcion: "Fecha de asign. Pto. venta", tipo: __TIPO_CAMPO_FECHA_HORA },
					}, 
					true,
					reloadData,
					trControlOnClick
				);
				
				grid.rebuild();
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleTripleSize");
				break;
			}
		}
		
		if (grid == null) {
			grid = new Grid(
				document.getElementById("divTableControles"),
				{
					tdMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
				}, 
				true,
				reloadData,
				trControlOnClick
			);
			
			grid.rebuild();
		}
		
		reloadData();

		$("#divIFrameControl").draggable();
		$("#divIFrameImportacionArchivo").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ControlREST/listContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ControlREST/countContextAware",
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

function trControlOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
//	document.getElementById("iFrameControl").src = "/LogisticaWEB/pages/controles/control.jsp?m=" + formMode + "&aid=" + $(target).attr("id");
//	showPopUp(document.getElementById("divIFrameControl"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#selectEmpresa").val("0");
	$("#selectTipoControl").val("0");
	$("#inputArchivo").val("");
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameControl"));
}

function inputSubirArchivoOnClick(event, element) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
	}).then(function(data) {
		fillSelect("selectEmpresa", data, "id", "nombre");
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/TipoControlREST/list"
	}).then(function(data) {
		fillSelect("selectTipoControl", data, "id", "descripcion" );
	});
	
	showPopUp(document.getElementById("divIFrameImportacionArchivo"));
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameImportacionArchivo"));
	
	$("#selectEmpresa").val("0");
	$("#selectTipoControl").val("0");
	$("#inputArchivo").val("");
	
	reloadData();
}

function inputAceptarSubirArchivoOnClick(event, element) {
	if ($("#selectEmpresa").val() == "0") {
		alert("Debe seleccionar una empresa.");
		
		return;
	} else if ($("#selectTipoControl").val == "0") {
		alert("Debe seleccionar un tipo de control.");
		
		return;
	}
	
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
				url: '/LogisticaWEB/RESTFacade/ControlREST/procesarArchivo', 
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					"nombre": data.fileName,
					"empresaId": data.empresaId,
					"tipoControlId": data.tipoControlId
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

function inputExportarAExcelOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ControlREST/exportarAExcel",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(metadataConsulta)
		}).then(function(data) {
			if (data != null) {
				document.getElementById("formExportarAExcel").action = 
					"/LogisticaWEB/Download?fn=" + data.nombreArchivo;
				
				document.getElementById("formExportarAExcel").submit();
			}
		});
	}
}