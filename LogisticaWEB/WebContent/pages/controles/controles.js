var __ROL_ADMINISTRADOR = 1;

var grid = null;
		
$(document).ready(function() {
	$("#divButtonAsignar").hide();
	$("#divButtonSubirArchivo").hide();
	$("#divButtonAgregarMid").hide();
	$("#divButtonExportarAExcel").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR){
//						$("#divButtonAsignar").show();
						$("#divButtonSubirArchivo").show();
						$("#divButtonAgregarMid").show();
						$("#divButtonExportarAExcel").show();
						
						grid = new Grid(
							document.getElementById("divTableControles"),
							{
								tdMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
								tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 200 },
								tdFechaControl: { campo: "fechaControl", abreviacion: "F. Control", descripcion: "Fecha de control", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
								tdFechaActivacion: { campo: "fechaActivacion", abreviacion: "F. Activación", descripcion: "Fecha de activación", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
								tdFechaImportacion: { campo: "fechaImportacion", abreviacion: "F. Importación", descripcion: "Fecha de importación", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
								tdCargaInicial: { campo: "cargaInicial", descripcion: "Carga inicial", abreviacion: "Carga ini.", tipo: __TIPO_CAMPO_NUMERICO, ancho: 90 },
								tdMontoCargar: { campo: "montoCargar", descripcion: "Monto a cargar", abreviacion: "Monto cargar", tipo: __TIPO_CAMPO_NUMERICO, ancho: 90 },
								tdEstadoControl: { campo: "estadoControl.nombre", clave: "estadoControl.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoControles, clave: "id", valor: "nombre" }, ancho: 200 },
								tdTipoControl: { campo: "tipoControl.descripcion", clave: "tipoControl.id", descripcion: "Tipo", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoControles, clave: "id", valor: "descripcion" }, ancho: 200 }
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
});

function listEmpresas() {
	var result = [];
	
	EmpresaDWR.list(
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

function listProductos() {
	var result = [];
	
	ProductoDWR.list(
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
	var estadoId = $(target).children("[campo='tdEstado']").attr("clave");
	
	var formMode = __FORM_MODE_READ;
	if (estadoId == __ESTADO_LLAMAR
		|| estadoId == __ESTADO_RELLAMAR
		|| estadoId == __ESTADO_RECHAZADO
		|| estadoId == __ESTADO_VENDIDO
		|| estadoId == __ESTADO_REAGENDAR) {
		formMode = __FORM_MODE_VENTA;
	} else if (estadoId == __ESTADO_RECOORDINAR
		|| estadoId == __ESTADO_FALTA_DOCUMENTACION) {
		formMode = __FORM_MODE_RECOORDINACION;
	}
	
	document.getElementById("iFrameControl").src = "/LogisticaWEB/pages/controles/control.jsp?m=" + formMode + "&aid=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameControl"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#selectVendedor").val("0");
	$("#selectEmpresa").val("0");
	$("#textareaObservaciones").val("");
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

function inputAsignarOnClick() {
	metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	metadataConsulta.tamanoSubconjunto = 
		Math.min(
			$("#inputTamanoSubconjunto").val(),
			$("#divCantidadRegistrosValue").text()
		);
	
	ContratoDWR.chequearAsignacion(
		metadataConsulta,
		{
			callback: function(data) {
				if (data || confirm("Atenci�n: se modificar�n registros que ya se encuentran asignados.")) {
					$("#selectVendedor > option").remove();
					
					$("#selectVendedor").append("<option value='0'>Seleccione...</option>");
					
					UsuarioRolEmpresaDWR.listVendedoresByContext(
						{
							callback: function(data) {
								var html = "";
								
								for (var i=0; i<data.length; i++) {
									html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
								}
								
								$("#selectVendedor").append(html);
							}, async: false
						}
					);
					
					showPopUp(document.getElementById("divIFrameSeleccionVendedor"));
				}
			}, async: false
		}
	);
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameSeleccionVendedor"));
	
	$("#selectVendedor").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputAceptarOnClick(event, element) {
	if ($("#selectVendedor").val() != "0") {
		var vendedor = {
			id: $("#selectVendedor").val()
		};
		
		metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
		metadataConsulta.tamanoSubconjunto = 
			Math.min(
				$("#inputTamanoSubconjunto").val(),
				$("#divCantidadRegistrosValue").text()
			);
		
		if (confirm("Se asignarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			ContratoDWR.asignarVentas(
				vendedor,
				metadataConsulta,
				{
					callback: function(data) {
						alert("Operaci�n exitosa.");
						
						reloadData();
					}, async: false
				}
			);
		}
	} else {
		alert("Debe seleccionar un vendedor.");
	}
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

function inputAgregarMidOnClick(event, element) {
	document.getElementById("iFrameControl").src = "/LogisticaWEB/pages/controles/controles.jsp?m=" + __FORM_MODE_NEW;
	showPopUp(document.getElementById("divIFrameControl"));
}

function inputExportarAExcelOnClick(event, element) {
	ControlesDWR.exportarAExcel(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
				document.getElementById("formExportarAExcel").submit();
			}, async: false
		}
	);
}