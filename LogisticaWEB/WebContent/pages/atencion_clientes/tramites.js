var __EMPRESA_ID_ATENCION_CLIENTES = 75559430;

var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_ATENCION_CLIENTES = 75559429;
var __ROL_OPERADOR_ATENCION_CLIENTES = 75559428;
var __ROL_LECTURA = 0;

var rolActual = -1;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonAgregarMid").hide();
	$("#divButtonAsignarAOperador").hide();
	$("#divButtonExportarAExcel").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if ((data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_ATENCION_CLIENTES)
				|| (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR)) {
				rolActual = __ROL_SUPERVISOR_ATENCION_CLIENTES;
				
				$("#divButtonAgregarMid").show();
				$("#divButtonAsignarAOperador").show();
				$("#divButtonExportarAExcel").show();
				
				grid = new Grid(
					document.getElementById("divTableTramites"),
					{
						
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 80 },
						tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
						tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
						tdFcre: { campo: "fcre", descripcion: "Fecha de ingreso", abreviacion: "F. ingreso", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdContratoConcepto: { campo: "atencionClienteConcepto.descripcion", clave: "atencionClienteConcepto.id", descripcion: "Concepto", abreviacion: "Concepto", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listAtencionClienteConceptos, clave: "id", valor: "descripcion" }, ancho: 80 },
						tdContratoTipoProducto: { campo: "tipoProducto.descripcion", clave: "tipoProducto.id", descripcion: "Tipo", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoProductos, clave: "id", valor: "descripcion" }, ancho: 80 },
						tdContratoNombre: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
						tdContratoNumeroContrato: { campo: "numeroContrato", descripcion: "Número de contrato", abreviacion: "Num. contrato", tipo: __TIPO_CAMPO_STRING },
						tdContratoTipoContacto: { campo: "atencionClienteTipoContacto.descripcion", clave: "atencionClienteTipoContacto.id", descripcion: "Tipo de contacto", abreviacion: "Tipo contacto", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listAtencionClienteTipoContactos, clave: "id", valor: "descripcion" }, ancho: 80 },
						tdContratoTipoProducto: { campo: "tipoProducto.descripcion", clave: "tipoProducto.id", descripcion: "Tipo de producto", abreviacion: "Tipo prod.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoProductos, clave: "id", valor: "descripcion" }, ancho: 80 },
						tdContratoPlan: { campo: "nuevoPlan.descripcion", clave: "nuevoPlan.id", descripcion: "Plan", abreviacion: "Plan", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPlanes, clave: "id", valor: "descripcion" }, ancho: 80 },
						fechaAtencionClienteCierre: { campo: "fechaAtencionClienteCierre", descripcion: "Fecha de cierre", abreviacion: "F. Cierre", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdContratoTelefonoContacto: { campo: "telefonoContacto", descripcion: "Teléfono de contacto", abreviacion: "Tel. de contacto", tipo: __TIPO_CAMPO_STRING },
						tdContratoFechaOperador: { campo: "fechaAtencionClienteOperador", descripcion: "Fecha de operador", abreviacion: "F. Operador", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdContratoFechaActivarEn: { campo: "fechaActivarEn", descripcion: "Fecha de ingreso a ANTEL", abreviacion: "F. ANTEL", tipo: __TIPO_CAMPO_FECHA },
						tdOperador: { campo: "atencionClienteOperador.nombre", clave: "atencionClienteOperador.id", descripcion: "Operador", abreviacion: "Operador", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listAtencionClienteOperadores, clave: "id", valor: "nombre" }, ancho: 80 },
						tdUsuario: { campo: "usuario.nombre", clave: "usuario.id", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listUsuarios, clave: "id", valor: "nombre" }, ancho: 80 },
						tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadosAtencionClientes, clave: "id", valor: "nombre" }, ancho: 80 },
						tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 80 },
						tdFact: { campo: "fact", descripcion: "Modificado", abreviacion: "Modif.", tipo: __TIPO_CAMPO_FECHA_HORA, oculto: true },
						
					}, 
					true,
					reloadData,
					trTramiteOnClick
				);
				
				grid.rebuild();
				
				grid.filtroDinamico.agregarFiltrosManuales(
					[
						{
							campo: "empresa.nombre",
							operador: "keq",
							valores: [__EMPRESA_ID_ATENCION_CLIENTES]
						}
					], 
					true
				).then(function (data) {
					grid.filtroDinamico.agregarFiltrosManuales(
						[
							{
								campo: "estado.nombre",
								operador: "kneq",
								valores: [__ESTADO_ATENCION_CLIENTE_SOLUCIONADO]
							},
							{
								campo: "estado.nombre",
								operador: "kneq",
								valores: [__ESTADO_ATENCION_CLIENTE_CAMBIO_CLIENTE]
							},
							{
								campo: "estado.nombre",
								operador: "kneq",
								valores: [__ESTADO_RECHAZADO]
							}
						], 
						false
					).then(function (data) {
						grid.filtroDinamico.preventReload = false
					
						reloadData();
					});
				});
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleFourfoldSize");
				
				break;
			}
		}
		
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_OPERADOR_ATENCION_CLIENTES) {
				rolActual = __ROL_OPERADOR_ATENCION_CLIENTES;
				
				$("#divButtonAgregarMid").show();
				
				grid = new Grid(
					document.getElementById("divTableTramites"),
					{
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 80 },
						tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
						tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
						tdContratoNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING },
						tdFcre: { campo: "fcre", descripcion: "Fecha de ingreso", abreviacion: "F. ingreso", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdContratoFechaCierre: { campo: "fechaAtencionClienteCierre", descripcion: "Fecha de cierre", abreviacion: "F. cierre", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdContratoConcepto: { campo: "atencionClienteConcepto.descripcion", clave: "atencionClienteConcepto.id", descripcion: "Concepto", abreviacion: "Concepto", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listAtencionClienteConceptos, clave: "id", valor: "descripcion" }, ancho: 80 },
						tdContratoTipoProducto: { campo: "tipoProducto.descripcion", clave: "tipoProducto.id", descripcion: "Tipo", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoProductos, clave: "id", valor: "descripcion" }, ancho: 80 },
						tdContratoPlan: { campo: "nuevoPlan.descripcion", clave: "nuevoPlan.id", descripcion: "Plan", abreviacion: "Plan", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPlanes, clave: "id", valor: "descripcion" }, ancho: 80 },
						tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadosAtencionClientes, clave: "id", valor: "nombre" }, ancho: 80 },
						tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 80 },
						tdFact: { campo: "fact", descripcion: "Modificado", abreviacion: "Modif.", tipo: __TIPO_CAMPO_FECHA_HORA, oculto: true }
					}, 
					true,
					reloadData,
					trTramiteOnClick
				);
				
				grid.rebuild();
				
				grid.filtroDinamico.preventReload = true;
				grid.filtroDinamico.agregarFiltrosManuales(
					[
						{
							campo: "empresa.nombre",
							operador: "keq",
							valores: [__EMPRESA_ID_ATENCION_CLIENTES]
						}
					], 
					true
				).then(function (data) {
					grid.filtroDinamico.agregarFiltrosManuales(
						[
							{
								campo: "estado.nombre",
								operador: "keq",
								valores: [__ESTADO_LLAMAR]
							}
						], 
						false
					).then(function (data) {
						grid.filtroDinamico.preventReload = false
					
						reloadData();
					});
				});
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				
				break;
			}
		}
		
		if (grid == null) {
			rolActual = __ROL_LECTURA;
			
			grid = new Grid(
				document.getElementById("divTableTramites"),
				{
					tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 80 },
					tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
				}, 
				true,
				reloadData,
				trTramiteOnClick
			);
			
			grid.rebuild();
			
			grid.filtroDinamico.preventReload = true;
			grid.filtroDinamico.agregarFiltrosManuales(
				[
					{
						campo: "empresa.nombre",
						operador: "keq",
						valores: [__EMPRESA_ID_ATENCION_CLIENTES]
					}
				], 
				true
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
		}
		
		$("#divIFrameTramite").draggable();
		$("#divIFrameSeleccionOperador").draggable();
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

function trTramiteOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var estadoId = $(target).children("[campo='tdEstado']").attr("clave");
	
	var formMode = __FORM_MODE_READ_ATENCION_CLIENTE;
	if (rolActual == __ROL_OPERADOR_ATENCION_CLIENTES
		&& ((estadoId == __ESTADO_LLAMAR)
			|| (estadoId == __ESTADO_ATENCION_CLIENTE_RELLAMAR)
			|| (estadoId == __ESTADO_ATENCION_CLIENTE_SOLUCIONADO)
			|| (estadoId == __ESTADO_ATENCION_CLIENTE_ANTEL)
			|| (estadoId == __ESTADO_ATENCION_CLIENTE_COMERCIAL)
			|| (estadoId == __ESTADO_ATENCION_CLIENTE_CAMBIO_CLIENTE)
			|| (estadoId == __ESTADO_ATENCION_CLIENTE_SUPERVISION))
	) {
		formMode = __FORM_MODE_ATENCION_CLIENTE_OPERADOR;
	} else if (rolActual == __ROL_SUPERVISOR_ATENCION_CLIENTES
		&& ((estadoId == __ESTADO_LLAMAR)
			|| (estadoId == __ESTADO_ATENCION_CLIENTE_RELLAMAR)
			|| (estadoId == __ESTADO_ATENCION_CLIENTE_SOLUCIONADO)
			|| (estadoId == __ESTADO_ATENCION_CLIENTE_ANTEL)
			|| (estadoId == __ESTADO_ATENCION_CLIENTE_COMERCIAL)
			|| (estadoId == __ESTADO_ATENCION_CLIENTE_CAMBIO_CLIENTE)
			|| (estadoId == __ESTADO_ATENCION_CLIENTE_SUPERVISION))
	) {
		formMode = __FORM_MODE_ATENCION_CLIENTE_SUPERVISOR;
	}	
	
	document.getElementById("iFrameTramite").src = 
		"/LogisticaWEB/pages/contrato/contrato.jsp?m=" + formMode + "&cid=" + $(target).attr("id");
		
	$("#divIFrameTramite").css("height", "601px");
	$("#iFrameTramite").css("height", "580px");
	
	showPopUp(document.getElementById("divIFrameTramite"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#selectAtencionClienteOperador").val("0");
	$("#textareaAtencionClienteOperadorObservaciones").val("");
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameTramite"));
}

function inputAsignarAOperadorOnClick() {
	metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
		metadataConsulta.tamanoSubconjunto = grid.getCount();
	}
	
	if (metadataConsulta.tamanoSubconjunto <= __MAXIMA_CANTIDAD_REGISTROS_ASIGNACION) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/chequearRelacionesAsignacion",
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
						listAtencionClienteOperadores()
							.then(function(data) {
								fillSelect(
									"selectAtencionClienteOperador",
									data,
									"id",
									"nombre"
								);
							
								showPopUp(document.getElementById("divIFrameSeleccionAtencionClienteOperador"));
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
	closePopUp(event, document.getElementById("divIFrameSeleccionAtencionClienteOperador"));
	
	$("#selectAtencionClienteOperador").val("0");
	$("#textareaAtencionClienteOperadorObservaciones").val("");
	
	reloadData();
}

function inputAceptarAtencionClienteOperadorOnClick(event, element) {
	if ($("#selectAtencionClienteOperador").val() == "0") {
		alert("Debe seleccionar un operador.");
		
		return;
	}
	
	var operador = {
		id: $("#selectAtencionClienteOperador").val()
	};
	
	metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
		metadataConsulta.tamanoSubconjunto = grid.getCount();
	}
	
	if (confirm("Se asignarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/asignarAtencionClienteOperador",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify({
				"usuario": operador,
				"metadataConsulta": grid.filtroDinamico.calcularMetadataConsulta()
			})
		}).then(function(data) {
			alert("Operación exitosa.");
			
			reloadData();
		});
	}
}

function inputAgregarMidOnClick(event, element) {
	document.getElementById("iFrameTramite").src = "/LogisticaWEB/pages/contrato/contrato.jsp?m=" + __FORM_MODE_NEW_ATENCION_CLIENTE
	
	$("#divIFrameTramite").css("height", "521px");
	$("#iFrameTramite").css("height", "500px");
	
	showPopUp(document.getElementById("divIFrameTramite"));
}

function inputExportarAExcelOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/exportarAExcelAtencionCliente",
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