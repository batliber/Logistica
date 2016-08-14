var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_ACTIVACION = 9;

var filtroDinamico = new FiltroDinamico(
	{
		tdContratoMid: { campo: "mid", descripcion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
		tdContratoFinContrato: { campo: "fechaFinContrato", descripcion: "Fin de contrato", tipo: __TIPO_CAMPO_FECHA },
		tdContratoNuevoPlan: { campo: "nuevoPlan", descripcion: "Plan", tipo: __TIPO_CAMPO_STRING },
		tdContratoDocumento: { campo: "documento", descripcion: "Documento", tipo: __TIPO_CAMPO_STRING },
		tdContratoNumeroContrato: { campo: "numeroContrato", descripcion: "Nro. contrato", tipo: __TIPO_CAMPO_NUMERICO },
		tdContratoNombre: { campo: "nombre", descripcion: "Nombre", tipo: __TIPO_CAMPO_STRING },
		tdContratoDireccion: { campo: "direccion", descripcion: "Dirección", tipo: __TIPO_CAMPO_STRING },
		tdContratoLocalidad: { campo: "localidad", descripcion: "Localidad", tipo: __TIPO_CAMPO_STRING },
		tdContratoEquipo: { campo: "equipo", descripcion: "Equipo", tipo: __TIPO_CAMPO_STRING },
		tdActivador: { campo: "usuario.nombre", descripcion: "Activador", tipo: __TIPO_CAMPO_STRING },
		tdEmpresa: { campo: "empresa.nombre", descripcion: "Empresa", tipo: __TIPO_CAMPO_STRING },
		tdActivarEn: { campo: "fechaActivarEn", descripcion: "Activar", tipo: __TIPO_CAMPO_STRING },
		tdEstado: { campo: "estado.nombre", descripcion: "Estado", tipo: __TIPO_CAMPO_STRING }
	}, reloadData
);

var grid = null;

$(document).ready(function() {
	$("#divButtonAsignar").hide();
	$("#divButtonExportarAExcel").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if ((data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_ACTIVACION)
						|| (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR)) {
						$("#divButtonAsignar").show();
						$("#divButtonExportarAExcel").show();
						
						grid = new Grid(
							document.getElementById("divTableContratos"),
							{
								tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
								tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
								tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" } },
								tdContratoFinContrato: { campo: "fechaFinContrato", descripcion: "Fin de contrato", abreviacion: "Fin", tipo: __TIPO_CAMPO_FECHA },
								tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
								tdContratoNuevoPlan: { campo: "nuevoPlan.descripcion", clave: "nuevoPlan.id", descripcion: "Nuevo plan", abreviacion: "Nuevo plan", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPlanes, clave: "id", valor: "descripcion" }, ancho: 80 },
								tdContratoEquipo: { campo: "producto.descripcion", clave: "producto.id", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listProductos, clave: "id", valor: "descripcion" }, ancho: 90 },
								tdContratoNumeroSerie: { campo: "numeroSerie", descripcion: "Número de serie", abreviacion: "Serie", tipo: __TIPO_CAMPO_STRING },
								tdFechaActivarEn: { campo: "fechaActivarEn", descripcion: "Activar en", abreviacion: "Act. en", tipo: __TIPO_CAMPO_FECHA },
								tdFechaEnvioAntel: { campo: "fechaEnvioAntel", descripcion: "Fecha de envío a ANTEL", abreviacion: "E. ANTEL", tipo: __TIPO_CAMPO_FECHA },
								tdFechaDevolucionDistribuidor: { campo: "fechaDevolucionDistribuidor", descripcion: "Devuelto por distribuidor", abreviacion: "Distribuído", tipo: __TIPO_CAMPO_FECHA },
								tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING },
								tdUsuario: { campo: "usuario.nombre", clave: "usuario.id", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listUsuarios, clave: "id", valor: "nombre" }, ancho: 90 },
								tdActivador: { campo: "activador.nombre", clave: "activador.id", descripcion: "Activador", abreviacion: "Activador", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listActivadores, clave: "id", valor: "nombre" }, ancho: 90 },
								tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 90 },
							}, 
							reloadData,
							trContratoOnClick
						);
						
						grid.rebuild();
						
						grid.filtroDinamico.agregarFiltro(null, null);
						$("#selectCampo1").val("estado.nombre");
						grid.filtroDinamico.campoOnChange(null, null, 1, true);
						
						$("#selectCondicion1").val("keq");
						grid.filtroDinamico.condicionOnChange(null, null, 1, true, "estado.nombre");
						
						$("#inputValor1").val("10");
						grid.filtroDinamico.valorOnChange(null, null, 1, true);
						
						grid.filtroDinamico.agregarFiltro(null, null);
						$("#selectCampo2").val("usuario.nombre");
						grid.filtroDinamico.campoOnChange(null, null, 2, true);
						
						$("#selectCondicion2").val("nl");
						grid.filtroDinamico.condicionOnChange(null, null, 2, true, "usuario.nombre");
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleTripleSize");
						
						break;
					}
				}
				
				if (grid == null) {
					grid = new Grid(
						document.getElementById("divTableContratos"),
						{
							tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
							tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
							tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" } },
							tdContratoFinContrato: { campo: "fechaFinContrato", descripcion: "Fin de contrato", abreviacion: "Fin", tipo: __TIPO_CAMPO_FECHA },
							tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
							tdContratoLocalidad: { campo: "localidad", descripcion: "Localidad", abreviacion: "Localidad", tipo: __TIPO_CAMPO_STRING },
							tdContratoNuevoPlan: { campo: "nuevoPlan.descripcion", clave: "nuevoPlan.id", descripcion: "Nuevo plan", abreviacion: "Nuevo plan", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPlanes, clave: "id", valor: "descripcion" }, ancho: 80 },
							tdContratoEquipo: { campo: "producto.descripcion", clave: "producto.id", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listProductos, clave: "id", valor: "descripcion" }, ancho: 90 },
							tdContratoTelefonoContacto: { campo: "telefonoContacto", descripcion: "Teléfono contacto", abreviacion: "Teléfono", tipo: __TIPO_CAMPO_STRING },
							tdContratoNumeroSerie: { campo: "numeroSerie", descripcion: "Número de serie", abreviacion: "Serie", tipo: __TIPO_CAMPO_STRING },
							tdFechaDevolucionDistribuidor: { campo: "fechaDevolucionDistribuidor", descripcion: "Devuelto por distribuidor", abreviacion: "Distribuído", tipo: __TIPO_CAMPO_FECHA },
							tdFechaActivarEn: { campo: "fechaActivarEn", descripcion: "Activar en", abreviacion: "Act. en", tipo: __TIPO_CAMPO_FECHA },
							tdFechaEnvioAntel: { campo: "fechaEnvioAntel", descripcion: "Fecha de envío a ANTEL", abreviacion: "E. ANTEL", tipo: __TIPO_CAMPO_FECHA },
							tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING },
							tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 90 },
						}, 
						reloadData,
						trContratoOnClick
					);
					
					grid.rebuild();
				}
			}, async: false
		}
	);
	
	reloadData();
	
	$("#divIFrameContrato").draggable();
	$("#divIFrameSeleccionActivador").draggable();
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

function listPlanes() {
	var result = [];
	
	PlanDWR.list(
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

function listActivadores() {
	var result = [];
	
	UsuarioRolEmpresaDWR.listActivadoresByContext(
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

function listUsuarios() {
	var result = [];
	
	UsuarioDWR.list(
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

function listEstados() {
	var result = [];
	
	EstadoDWR.list(
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
	
	ContratoDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	ContratoDWR.countContextAware(
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

function trContratoOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var estadoId = $(target).children("[campo='tdEstado']").attr("clave");
	
	var formMode = __FORM_MODE_READ;
	if (estadoId == __ESTADO_ACTIVAR 
		|| estadoId == __ESTADO_CONTROL_ANTEL
		|| estadoId == __ESTADO_ACT_DOC_VENTA) {
		formMode = __FORM_MODE_ACTIVACION;
	}
	
	document.getElementById("iFrameContrato").src = "/LogisticaWEB/pages/contrato/contrato.jsp?m=" + formMode + "&cid=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameContrato"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#selectActivador").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameContrato"));
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
				if (data || confirm("Atención: se modificarán registros que ya se encuentran asignados.")) {
					$("#selectActivador > option").remove();
					
					$("#selectActivador").append("<option value='0'>Seleccione...</option>");
					
					UsuarioRolEmpresaDWR.listActivadoresByContext(
						{
							callback: function(data) {
								var html = "";
								
								for (var i=0; i<data.length; i++) {
									html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
								}
								
								$("#selectActivador").append(html);
							}, async: false
						}
					);
					
					showPopUp(document.getElementById("divIFrameSeleccionActivador"));
				}
			}, async: false
		}
	);
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameSeleccionActivador"));
	
	$("#selectActivador").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputAceptarOnClick(event, element) {
	if ($("#selectActivador").val() != "0") {
		var activador = {
			id: $("#selectActivador").val()
		};
		
		metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
		metadataConsulta.tamanoSubconjunto = 
			Math.min(
				$("#inputTamanoSubconjunto").val(),
				$("#divCantidadRegistrosValue").text()
			);
		
		if (confirm("Se asignarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			ContratoDWR.asignarActivador(
				activador,
				metadataConsulta,
				{
					callback: function(data) {
						alert("Operación exitosa.");
						
						reloadData();
					}, async: false
				}
			);
		}
	} else {
		alert("Debe seleccionar un activador.");
	}
}

function inputExportarAExcelOnClick(event, element) {
	ContratoDWR.exportarAExcel(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				document.getElementById("formExportarAExcel").action = "/LogisticaWEB/Download?fn=" + data;
				document.getElementById("formExportarAExcel").submit();
			}, async: false
		}
	);
}