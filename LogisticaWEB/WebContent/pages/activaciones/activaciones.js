var __ROL_ADMINISTRADOR = 1;
var __ROL_ENCARGADO_ACTIVACIONES = 12;
var __ROL_ENCARGADO_ACTIVACIONES_SIN_DISTRIBUCION = 15;
var __ROL_SUPERVISOR_DISTRIBUCION_CHIPS = 18;
var __ROL_DEMO = 21;

var grid = null;
		
$(document).ready(init);

function init() {
	$("#divButtonAsignar").hide();
	$("#divButtonSubirArchivo").hide();
	$("#divButtonAgregarMid").hide();
	$("#divButtonExportarAExcel").hide();
	$("#divButtonExportarAExcelSupervisorDistribucionChips").hide();
	$("#divButtonExportarAExcelEncargadoActivaciones").hide();
	$("#divButtonExportarAExcelEncargadoActivacionesSinDistribucion").hide();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
    }).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_DISTRIBUCION_CHIPS
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
//				$("#divButtonAsignar").show();
				$("#divButtonSubirArchivo").show();
				$("#divButtonAgregarMid").show();
				$("#divButtonExportarAExcelSupervisorDistribucionChips").show();
				
				grid = new Grid(
					document.getElementById("divTableActivaciones"),
					{
						tdMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 150 },
						tdChip: { campo: "chip", abreviacion: "Chip", descripcion: "Chip", tipo: __TIPO_CAMPO_STRING, ancho: 120 },
						tdFechaActivacion: { campo: "fechaActivacion", abreviacion: "F. Activación", descripcion: "Fecha de activación", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
						tdFechaImportacion: { campo: "fechaImportacion", abreviacion: "F. Importación", descripcion: "Fecha de importación", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
						tdFechaVencimiento: { campo: "fechaVencimiento", abreviacion: "F. Vencimiento", descripcion: "Fecha de vencimiento", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
						tdEstadoActivacion: { campo: "estadoActivacion.nombre", clave: "estadoActivacion.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoActivaciones, clave: "id", valor: "nombre" }, ancho: 125 },
						tdActivacionLote: { campo: "activacionLote.numero", abreviacion: "Lote", descripcion: "Lote", tipo: __TIPO_CAMPO_NUMERICO },
						tdActivacionSublote: { campo: "activacionSublote.numero", abreviacion: "Sublote", descripcion: "Sublote", tipo: __TIPO_CAMPO_NUMERICO },
						tdDistribuidor: { campo: "activacionSublote.distribuidor.nombre", clave: "activacionSublote.distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidores, clave: "id", valor: "nombre" }, ancho: 125 },
						tdFechaAsignacionDistribuidor: { campo: "activacionSublote.fechaAsignacionDistribuidor", abreviacion: "F. Asign. Distr.", descripcion: "Fecha asign. Distribuidor", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
						tdPuntoVenta: { campo: "activacionSublote.puntoVenta.nombre", clave: "activacionSublote.puntoVenta.id", descripcion: "Punto de venta", abreviacion: "Punto de venta", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPuntoVentas, clave: "id", valor: "nombre" }, ancho: 125 },
						tdPuntoVentaDepartamento: { campo: "activacionSublote.puntoVenta.departamento.nombre", clave: "activacionSublote.puntoVenta.departamento.id", descripcion: "Departamento", abreviacion: "Departamento", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre" } },
						tdFechaAsignacionPuntoVenta: { campo: "activacionSublote.fechaAsignacionPuntoVenta", abreviacion: "F. Asign. P.V.", descripcion: "Fecha asign. Pto. venta", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
						tdFechaLiquidacion: { campo: "liquidacion.fechaLiquidacion", abreviacion: "F. Liq.", descripcion: "Fecha de liquidación", tipo: __TIPO_CAMPO_FECHA_HORA },
					}, 
					true,
					reloadData,
					trActivacionOnClick
				);
				
				grid.rebuild();
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleTripleSize");
				break;
			}
		}
		
		if (grid == null) {
			for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
				if (data.usuarioRolEmpresas[i].rol.id == __ROL_ENCARGADO_ACTIVACIONES) {
//					$("#divButtonAsignar").show();
					$("#divButtonSubirArchivo").show();
					$("#divButtonAgregarMid").show();
					$("#divButtonExportarAExcelEncargadoActivaciones").show();
					
					grid = new Grid(
						document.getElementById("divTableActivaciones"),
						{
							tdMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
							tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 150 },
							tdChip: { campo: "chip", abreviacion: "Chip", descripcion: "Chip", tipo: __TIPO_CAMPO_STRING, ancho: 120 },
							tdFechaActivacion: { campo: "fechaActivacion", abreviacion: "F. Activación", descripcion: "Fecha de activación", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
							tdFechaImportacion: { campo: "fechaImportacion", abreviacion: "F. Importación", descripcion: "Fecha de importación", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
							tdEstadoActivacion: { campo: "estadoActivacion.nombre", clave: "estadoActivacion.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoActivaciones, clave: "id", valor: "nombre" }, ancho: 125 },
							tdActivacionLote: { campo: "activacionLote.numero", abreviacion: "Lote", descripcion: "Lote", tipo: __TIPO_CAMPO_NUMERICO },
							tdActivacionSublote: { campo: "activacionSublote.numero", abreviacion: "Sublote", descripcion: "Sublote", tipo: __TIPO_CAMPO_NUMERICO },
							tdDistribuidor: { campo: "activacionSublote.distribuidor.nombre", clave: "activacionSublote.distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidores, clave: "id", valor: "nombre" }, ancho: 125 },
							tdFechaAsignacionDistribuidor: { campo: "activacionSublote.fechaAsignacionDistribuidor", abreviacion: "F. Asign. Distr.", descripcion: "Fecha asign. Distribuidor", tipo: __TIPO_CAMPO_FECHA, ancho: 90 }
						}, 
						true,
						reloadData,
						trActivacionOnClick
					);
					
					grid.rebuild();
					
					$("#divButtonTitleSingleSize").attr("id", "divButtonTitleTripleSize");
					break;
				}
			}
		}
		
		if (grid == null) {
			for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
				if (data.usuarioRolEmpresas[i].rol.id == __ROL_ENCARGADO_ACTIVACIONES_SIN_DISTRIBUCION) {
					$("#divButtonSubirArchivo").show();
					$("#divButtonAgregarMid").show();
					$("#divButtonExportarAExcelEncargadoActivacionesSinDistribucion").show();
					
					grid = new Grid(
						document.getElementById("divTableActivaciones"),
						{
							tdMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
							tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 150 },
							tdChip: { campo: "chip", abreviacion: "Chip", descripcion: "Chip", tipo: __TIPO_CAMPO_STRING, ancho: 120 },
							tdFechaActivacion: { campo: "fechaActivacion", abreviacion: "F. Activación", descripcion: "Fecha de activación", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
							tdFechaImportacion: { campo: "fechaImportacion", abreviacion: "F. Importación", descripcion: "Fecha de importación", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
							tdEstadoActivacion: { campo: "estadoActivacion.nombre", clave: "estadoActivacion.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoActivaciones, clave: "id", valor: "nombre" }, ancho: 125 }
						}, 
						true,
						reloadData,
						trActivacionOnClick
					);
					
					grid.rebuild();
					
					$("#divButtonTitleSingleSize").attr("id", "divButtonTitleTripleSize");
					break;
				}
			}
		}
		
		if (grid == null) {
			grid = new Grid(
				document.getElementById("divTableActivaciones"),
				{
					tdMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
				}, 
				true,
				reloadData,
				trActivacionOnClick
			);
			
			grid.rebuild();
		}
		
		reloadData();

		$("#divIFrameActivacion").draggable();
		$("#divIFrameSeleccionVendedor").draggable();
		$("#divIFrameImportacionArchivo").draggable();
    });
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ActivacionREST/listContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ActivacionREST/countContextAware",
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

function trActivacionOnClick(eventObject) {
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
	
	document.getElementById("iFrameActivacion").src = 
		"/LogisticaWEB/pages/activaciones/activaciones_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	
	showPopUp(document.getElementById("divIFrameActivacion"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#selectEmpresa").val("0");
	$("#selectTipoActivacion").val("0");
	$("#inputArchivo").val("");
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameActivacion"));
}

function inputSubirArchivoOnClick(event, element) {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
    }).then(function(data) {
    	fillSelect(
    		"selectEmpresa",
    		data,
    		"id",
    		"nombre"
    	);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/TipoActivacionREST/list"
    }).then(function(data) {
    	fillSelect(
    		"selectTipoActivacion",
    		data,
    		"id",
    		"descripcion"
    	);
    });
	
	showPopUp(document.getElementById("divIFrameImportacionArchivo"));
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameImportacionArchivo"));
	
	$("#selectEmpresa").val("0");
	$("#selectTipoActivacion").val("0");
	$("#inputArchivo").val("");
	
	reloadData();
}

function inputAceptarSubirArchivoOnClick(event, element) {
	if ($("#selectEmpresa").val() == "0") {
		alert("Debe seleccionar una empresa.");
		
		return;
	}
	
	if ($("#selectTipoActivacion").val == "0") {
		alert("Debe seleccionar un tipo de activación.");
		
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
				url: '/LogisticaWEB/RESTFacade/ActivacionREST/procesarArchivo', 
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					"nombre": data.fileName,
					"empresaId": data.empresaId,
					"tipoActivacionId": data.tipoActivacionId
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
			url: "/LogisticaWEB/RESTFacade/ActivacionREST/exportarAExcel",
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

function inputExportarAExcelSupervisorDistribucionChipsOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ActivacionREST/exportarAExcelSupervisorDistribucionChips",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(metadataConsulta)
	    }).then(function(data) {
	    	if (data != null) {
	    		document.getElementById("formExportarAExcelSupervisorDistribucionChips").action = 
	    			"/LogisticaWEB/Download?fn=" + data.nombreArchivo;
	    		
	    		document.getElementById("formExportarAExcelSupervisorDistribucionChips").submit();
	    	}
	    });
	}
}

function inputExportarAExcelEncargadoActivacionesOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ActivacionREST/exportarAExcelEncargadoActivaciones",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(metadataConsulta)
	    }).then(function(data) {
	    	if (data != null) {
	    		document.getElementById("formExportarAExcelEncargadoActivaciones").action = 
	    			"/LogisticaWEB/Download?fn=" + data.nombreArchivo;
	    		
	    		document.getElementById("formExportarAExcelEncargadoActivaciones").submit();
	    	}
	    });
	}
}

function inputExportarAExcelEncargadoActivacionesSinDistribucionOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ActivacionREST/exportarAExcelEncargadoActivacionesSinDistribucion",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(metadataConsulta)
	    }).then(function(data) {
	    	if (data != null) {
	    		document.getElementById("formExportarAExcelEncargadoActivacionesSinDistribucion").action = 
	    			"/LogisticaWEB/Download?fn=" + data.nombreArchivo;
	    		
	    		document.getElementById("formExportarAExcelEncargadoActivacionesSinDistribucion").submit();
	    	}
	    });
	}
}