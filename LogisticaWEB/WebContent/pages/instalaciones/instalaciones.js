var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_DISTRIBUCION = 5;
var __ROL_BACK_OFFICE_FIBRA_OPTICA = 77975403;
var __ROL_LECTURA_ANTEL = 0;

//var __EMPRESA_ID_ANTEL_FO = 7237252;
var __EMPRESA_ID_ANTEL_FO = 72043061;

var rolActual = -1;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonAgregarMid").hide();
	$("#divButtonAsignar").hide();
	$("#divButtonNotificar").hide();
	$("#divButtonNotificarEntrega").hide();
	$("#divButtonNotificarAPIStock").hide();
	$("#divButtonExportarAExcel").hide();
	$("#divButtonExportarReporteTiempos").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if ((data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_DISTRIBUCION)
				|| (data.usuarioRolEmpresas[i].rol.id == __ROL_BACK_OFFICE_FIBRA_OPTICA)
				|| (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR)) {
				rolActual = __ROL_BACK_OFFICE_FIBRA_OPTICA;
				
				$("#divButtonAgregarMid").show();
				$("#divButtonAsignar").show();
				$("#divButtonNotificar").show();
				$("#divButtonNotificarEntrega").show();
				$("#divButtonNotificarAPIStock").show();
				$("#divButtonExportarAExcel").show();
				$("#divButtonExportarReporteTiempos").show();
				
				grid = new Grid(
					document.getElementById("divTableContratos"),
					{
						tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
						tdContratoAntelNroTrn: { campo: "antelNroTrn", descripcion: "Id Ticket", abreviacion: "Id Ticket", tipo: __TIPO_CAMPO_STRING },
						tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
						tdContratoTelefonoContacto: { campo: "telefonoContacto", descripcion: "Número de servicio", abreviacion: "Nro. servicio", tipo: __TIPO_CAMPO_STRING },
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 80 },
						tdFechaVenta: { campo: "fechaVenta", descripcion: "Fecha de venta", abreviacion: "Vendido", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdContratoFechaBackoffice: { campo: "fechaBackoffice", descripcion: "Fecha de armado", abreviacion: "Armado", tipo: __TIPO_CAMPO_FECHA },
						tdFechaEnvioAntel: { campo: "fechaEnvioAntel", descripcion: "Fecha de envío a ANTEL", abreviacion: "E. ANTEL", tipo: __TIPO_CAMPO_FECHA, oculto: true },
						tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING, ancho: 80 },
						tdContratoDepartamento: { campo: "zona.departamento.nombre", clave: "zona.departamento.id", descripcion: "Departamento", abreviacion: "Depto.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre"}, ancho: 80 },
						tdContratoBarrio: { campo: "barrio.nombre", clave: "barrio.id", descripcion: "Barrio", abreviacion: "Barrio", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBarrios, clave: "id", valor: "nombre" }, ancho: 80 },
						tdContratoZona: { campo: "zona.nombre", clave: "zona.id", descripcion: "Zona", abreviacion: "Zona", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listZonas, clave: "id", valor: "nombre"}, ancho: 55 },
						tdContratoNumeroSerie: { campo: "numeroSerie", descripcion: "Número de serie", abreviacion: "Serie", tipo: __TIPO_CAMPO_STRING, ancho: 80 },
						tdFechaEnvioANucleo: { campo: "fechaEnvioANucleo", descripcion: "Fecha de notificación a ANTEL", abreviacion: "Notif. ANTEL", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdFechaEnvioAIZI: { campo: "fechaEnvioAIZI", descripcion: "Fecha de notificación a IZI", abreviacion: "Notif. IZI", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdFechaEnvioAGLA: { campo: "fechaEnvioAGLA", descripcion: "Fecha de notificación a GLA", abreviacion: "Notif. GLA", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdResultadoEntregaDistribucion: { campo: "resultadoEntregaDistribucion.descripcion", clave: "resultadoEntregaDistribucion.id", descripcion: "Resultado entrega", abreviacion: "Entrega", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listResultadoEntregaDistribuciones, clave: "id", valor: "descripcion" } },
						tdVendedor: { campo: "vendedor.nombre", clave: "vendedor.id", descripcion: "Vendedor", abreviacion: "Vendedor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listVendedores, clave: "id", valor: "nombre" }, ancho: 80 },
						tdBackoffice: { campo: "backoffice.nombre", clave: "backoffice.id", descripcion: "Backoffice", abreviacion: "Backoffice", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBackoffices, clave: "id", valor: "nombre" }, ancho: 80 },
						tdUsuario: { campo: "usuario.nombre", clave: "usuario.id", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listUsuarios, clave: "id", valor: "nombre" }, ancho: 80 },
						tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 80 },
						tdFcre: { campo: "fcre", descripcion: "Creado", abreviacion: "Creado", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdFact: { campo: "fact", descripcion: "Modificado", abreviacion: "Modif.", tipo: __TIPO_CAMPO_FECHA_HORA, oculto: true },
						tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 80, oculto: true }
					}, 
					true,
					reloadData,
					trContratoOnClick
				);
				
				grid.rebuild();
				
				grid.filtroDinamico.preventReload = true;
				grid.filtroDinamico.agregarFiltrosManuales(
					[
						{
							campo: "antelNroTrn",
							operador: "nnl",
							valores: []
						}, 
						{
							campo: "empresa.nombre",
							operador: "keq",
							valores: [__EMPRESA_ID_ANTEL_FO]
						}
					], 
					false
				).then(function (data) {
					grid.filtroDinamico.agregarOrdenManual(
						{
							campo: "tdContratoNumeroTramite",
							ascendente: false
						}
					).then(function(data) {
						grid.filtroDinamico.preventReload = false
							
						reloadData();
					});
				});
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleOctupleSize");
				
				break;
			}
		}
		
		if (grid == null) {
			rolActual = __ROL_LECTURA_ANTEL;
			
			$("#divButtonExportarAExcel").show();
			
			grid = new Grid(
				document.getElementById("divTableContratos"),
				{
					tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
					tdContratoAntelNroTrn: { campo: "antelNroTrn", descripcion: "Id Ticket", abreviacion: "Id Ticket", tipo: __TIPO_CAMPO_STRING },
					tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
					tdContratoTelefonoContacto: { campo: "telefonoContacto", descripcion: "Número de servicio", abreviacion: "Nro. servicio", tipo: __TIPO_CAMPO_STRING },
					tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 80 },
					tdFechaVenta: { campo: "fechaVenta", descripcion: "Fecha de venta", abreviacion: "Vendido", tipo: __TIPO_CAMPO_FECHA_HORA },
					tdContratoFechaBackoffice: { campo: "fechaBackoffice", descripcion: "Fecha de armado", abreviacion: "Armado", tipo: __TIPO_CAMPO_FECHA },
					tdFechaEnvioAntel: { campo: "fechaEnvioAntel", descripcion: "Fecha de envío a ANTEL", abreviacion: "E. ANTEL", tipo: __TIPO_CAMPO_FECHA, oculto: true },
					tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING, ancho: 80 },
					tdContratoDepartamento: { campo: "zona.departamento.nombre", clave: "zona.departamento.id", descripcion: "Departamento", abreviacion: "Depto.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre"}, ancho: 80 },
					tdContratoBarrio: { campo: "barrio.nombre", clave: "barrio.id", descripcion: "Barrio", abreviacion: "Barrio", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBarrios, clave: "id", valor: "nombre" }, ancho: 80 },
					tdContratoZona: { campo: "zona.nombre", clave: "zona.id", descripcion: "Zona", abreviacion: "Zona", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listZonas, clave: "id", valor: "nombre"}, ancho: 55 },
					tdContratoNumeroSerie: { campo: "numeroSerie", descripcion: "Número de serie", abreviacion: "Serie", tipo: __TIPO_CAMPO_STRING, ancho: 80 },
					tdFechaEnvioANucleo: { campo: "fechaEnvioANucleo", descripcion: "Fecha de notificación a ANTEL", abreviacion: "Notif. ANTEL", tipo: __TIPO_CAMPO_FECHA_HORA },
					tdFechaEnvioAIZI: { campo: "fechaEnvioAIZI", descripcion: "Fecha de notificación a IZI", abreviacion: "Notif. IZI", tipo: __TIPO_CAMPO_FECHA_HORA },
					tdFechaEnvioAGLA: { campo: "fechaEnvioAGLA", descripcion: "Fecha de notificación a GLA", abreviacion: "Notif. GLA", tipo: __TIPO_CAMPO_FECHA_HORA },
					tdResultadoEntregaDistribucion: { campo: "resultadoEntregaDistribucion.descripcion", clave: "resultadoEntregaDistribucion.id", descripcion: "Resultado entrega", abreviacion: "Entrega", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listResultadoEntregaDistribuciones, clave: "id", valor: "descripcion" } },
					tdVendedor: { campo: "vendedor.nombre", clave: "vendedor.id", descripcion: "Vendedor", abreviacion: "Vendedor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listVendedores, clave: "id", valor: "nombre" }, ancho: 80 },
					tdBackoffice: { campo: "backoffice.nombre", clave: "backoffice.id", descripcion: "Backoffice", abreviacion: "Backoffice", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBackoffices, clave: "id", valor: "nombre" }, ancho: 80 },
					tdUsuario: { campo: "usuario.nombre", clave: "usuario.id", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listUsuarios, clave: "id", valor: "nombre" }, ancho: 80 },
					tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 80 },
					tdFcre: { campo: "fcre", descripcion: "Creado", abreviacion: "Creado", tipo: __TIPO_CAMPO_FECHA_HORA },
					tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 80, oculto: true }
				}, 
				true,
				reloadData,
				trContratoOnClick
			);
			
			grid.rebuild();
			
			grid.filtroDinamico.preventReload = true;
			grid.filtroDinamico.agregarFiltrosManuales(
				[
					{
						campo: "antelNroTrn",
						operador: "nnl",
						valores: []
					}, 
					{
						campo: "empresa.nombre",
						operador: "keq",
						valores: [__EMPRESA_ID_ANTEL_FO]
					}
				], 
				false
			).then(function (data) {
				grid.filtroDinamico.agregarOrdenManual(
					{
						campo: "tdContratoNumeroTramite",
						ascendente: false
					}
				).then(function(data) {
					grid.filtroDinamico.preventReload = false
						
					reloadData();
				});
			});
			
			$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
		}
		
		$("#divIFrameContrato").draggable();
		$("#divIFrameSeleccionBackoffice").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/listContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/countContextAware",
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

function trContratoOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var estadoId = $(target).children("[campo='tdEstado']").attr("clave");
	
	var formMode = __FORM_MODE_READ_INSTALACION_FIBRA;
	if (rolActual != __ROL_LECTURA_ANTEL
		&& (estadoId == __ESTADO_LLAMAR
		|| estadoId == __ESTADO_VENDIDO
		|| estadoId == __ESTADO_INSTALADO)) {
		formMode = __FORM_MODE_INSTALACION_FIBRA;
	}
	
	document.getElementById("iFrameContrato").src = 
		"/LogisticaWEB/pages/contrato/contrato.jsp?m=" + formMode + "&cid=" + $(target).attr("id");
	
	showPopUp(document.getElementById("divIFrameContrato"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#selectBackoffice").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameContrato"));
}

function inputAsignarOnClick() {
	metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
		metadataConsulta.tamanoSubconjunto = grid.getCount();
	}
	
	if (metadataConsulta.tamanoSubconjunto <= __MAXIMA_CANTIDAD_REGISTROS_ASIGNACION) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/chequearAsignacion",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
		}).then(function(dataRelaciones) {
			if (dataRelaciones != null
				&& (dataRelaciones.ok
					|| confirm("Atención: dentro de la selección hay trámites relacionados sin asignar.")
				)
			) {
				$.ajax({
					url: "/LogisticaWEB/RESTFacade/ContratoREST/chequearAsignacion",
					method: "POST",
					contentType: 'application/json',
					data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
				}).then(function(data) {
					if (data != null 
						&& (data.ok 
							|| confirm("Atención: se modificarán trámites que ya se encuentran asignados.")
						)
					) {
						listDistribuidores()
							.then(function(data) {
								fillSelect(
									"selectDistribuidor",
									data,
									"id",
									"nombre"
								);
							
								showPopUp(document.getElementById("divIFrameSeleccionDistribuidor"));
							});
					}
				});
			}
		});
	} else {
		alert("No se puede completar la operación. La asignación modificará más de 300 registros.");
	}
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameSeleccionDistribuidor"));
	
	$("#selectDistribuidor").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputAceptarOnClick(event, element) {
	if ($("#selectDistribuidor").val() == "0") {
		alert("Debe seleccionar un distribuidor.");
		
		return;
	}
	
	var distribuidor = {
		id: $("#selectDistribuidor").val()
	};
	
	metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
		metadataConsulta.tamanoSubconjunto = grid.getCount();
	}
	
	if (confirm("Se asignarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/asignarDistribuidor",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify({
				"usuario": distribuidor,
				"metadataConsulta": grid.filtroDinamico.calcularMetadataConsulta()
			})
		}).then(function(data) {
			alert("Operación exitosa.");
			
			reloadData();
		});
	}
}

function inputAgregarMidOnClick(event, element) {
	document.getElementById("iFrameContrato").src = "/LogisticaWEB/pages/contrato/contrato.jsp?m=" + __FORM_MODE_NEW_INSTALACION_FIBRA;
	showPopUp(document.getElementById("divIFrameContrato"));
}

function inputNotificarOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se notificarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/notificarInstalacion",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(metadataConsulta)
		}).then(function(data) {
			if (data != null) {
				alert("Se notificaron " + data.resultadoNotificarInstalaciones.length + " trámites.");
			}
		});
	}
}

function inputNotificarEntregaOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se notificarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/notificarIZI",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(metadataConsulta)
		}).then(function(data) {
			if (data != null) {
				alert("Se notificaron " + data.resultadoNotificarIZIs.length + " trámites.");
			}
		});
	}
}

function inputNotificarAPIStockOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se notificarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/notificarAPIStock",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(metadataConsulta)
		}).then(function(data) {
			if (data != null) {
				alert("Se notificaron " + data.resultadoNotificarAPIStocks.length + " trámites.");
			}
		});
	}
}

function inputExportarAExcelOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/exportarAExcelFibraOptica",
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

function inputExportarReporteTiemposOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/exportarReporteTiemposFibraOptica",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(metadataConsulta)
		}).then(function(data) {
			if (data != null) {
				document.getElementById("formExportarReporteTiempos").action = 
					"/LogisticaWEB/Download?fn=" + data.nombreArchivo;
				
				document.getElementById("formExportarReporteTiempos").submit();
			}
		});
	}
}