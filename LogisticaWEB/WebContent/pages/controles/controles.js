var __ROL_ADMINISTRADOR = 1;
var __ROL_DEMO = 21;

var grid = null;
		
$(document).ready(init);

function init() {
	$("#divButtonAsignar").hide();
	$("#divButtonSubirArchivo").hide();
	$("#divButtonAgregarMid").hide();
	$("#divButtonExportarAExcel").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
//						$("#divButtonAsignar").show();
						$("#divButtonSubirArchivo").show();
						$("#divButtonAgregarMid").show();
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
			}, async: false
		}
	);
	
	reloadData();

	$("#divIFrameControl").draggable();
	$("#divIFrameSeleccionVendedor").draggable();
	$("#divIFrameImportacionArchivo").draggable();
}

function listEmpresas() {
	var result = [];
	
	UsuarioRolEmpresaDWR.listEmpresasByContext(
		{
			callback: function(data) {
				if (data != null) {
					result = data;
				}
			}, async: false
		}
	);
	
	return result;
}

function listEstadoControles() {
	var result = [];
	
	EstadoControlDWR.list(
		{
			callback: function(data) {
				if (data != null) {
					result = data;
				}
			}, async: false
		}
	);
	
	return result;
}

function listTipoControles() {
	var result = [];
	
	TipoControlDWR.list(
		{
			callback: function(data) {
				if (data != null) {
					result = data;
				}
			}, async: false
		}
	);
	
	return result;
}

function listDistribuidores() {
	var result = [];
	
	UsuarioRolEmpresaDWR.listDistribuidoresByContext(
		{
			callback: function(data) {
				if (data != null) {
					result = data;
				}
			}, async: false
		}
	);
	
	return result;
}

function listPuntoVentas() {
	var result = [];
	
	PuntoVentaDWR.list(
		{
			callback: function(data) {
				if (data != null) {
					result = data;
				}
			}, async: false
		}
	);
	
	return result;
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	ControlDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	ControlDWR.countContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
}

function inputActualizarOnClick(event, element) {
	reloadData();
}

function trControlOnClick(eventObject) {
	var target = eventObject.currentTarget;
//	var estadoId = $(target).children("[campo='tdEstado']").attr("clave");
//	
//	var formMode = __FORM_MODE_READ;
//	if (estadoId == __ESTADO_LLAMAR
//		|| estadoId == __ESTADO_RELLAMAR
//		|| estadoId == __ESTADO_RECHAZADO
//		|| estadoId == __ESTADO_VENDIDO
//		|| estadoId == __ESTADO_REAGENDAR) {
//		formMode = __FORM_MODE_VENTA;
//	} else if (estadoId == __ESTADO_RECOORDINAR
//		|| estadoId == __ESTADO_FALTA_DOCUMENTACION) {
//		formMode = __FORM_MODE_RECOORDINACION;
//	}
	
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
	$("#selectEmpresa > option").remove();
	$("#selectTipoControl > option").remove();
	
	$("#selectEmpresa").append("<option value='0'>Seleccione...</option>");
	$("#selectTipoControl").append("<option value='0'>Seleccione...</option>");
	
	UsuarioRolEmpresaDWR.listEmpresasByContext(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectEmpresa").append(html);
			}, async: false
		}
	);
	
	TipoControlDWR.list(
		{
			callback: function(data) {
				var html = "";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectTipoControl").append(html);
			}, async: false
		}
	);
	
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
	} else if ($("#selectTipoControl").val == "0") {
		alert("Debe seleccionar un tipo de control.");
	} else {
		var xmlHTTPRequest = new XMLHttpRequest();
		xmlHTTPRequest.open(
			"POST",
			"/LogisticaWEB/Upload",
			false
		);
		
		var formData = new FormData(document.getElementById("formSubirArchivo"));
		
		xmlHTTPRequest.send(formData);
		
		if (xmlHTTPRequest.status == 200) {
			var response = JSON.parse(xmlHTTPRequest.responseText);
			
			if (confirm(response.message.replace(new RegExp("\\|", "g"), "\n"))) {
				ControlDWR.procesarArchivoEmpresa(
					response.fileName,
					response.empresaId,
					response.tipoControlId,
					{
						callback: function(data) {
							if (data != null) {
								alert(data.replace(new RegExp("\\|", "g"), "\n"));
							}
							
							reloadData();
						}, async: false
					}
				);
			}
		} else {
			alert(xmlHTTPRequest.responseText);
		}
	}
}

function inputExportarAExcelOnClick(event, element) {
	ControlDWR.exportarAExcel(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
				document.getElementById("formExportarAExcel").submit();
			}, async: false
		}
	);
}