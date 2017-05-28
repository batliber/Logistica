var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_CALL_CENTER = 3;
var __ROL_COORDINADOR_DISTRIBUCION = 11;

var grid = null;
		
$(document).ready(init);
		
function init() {
	$("#divButtonAsignar").hide();
	$("#divButtonSubirArchivo").hide();
	$("#divButtonAgregarMid").hide();
	$("#divButtonExportarAExcel").hide();
	$("#divButtonExportarAExcelNucleo").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if ((data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_CALL_CENTER)
						|| (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR)){
						$("#divButtonAsignar").show();
						$("#divButtonSubirArchivo").show();
						$("#divButtonAgregarMid").show();
						$("#divButtonExportarAExcel").show();
						$("#divButtonExportarAExcelNucleo").show();
						
						grid = new Grid(
							document.getElementById("divTableContratos"),
							{
								tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
								tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 90 },
								tdContratoFinContrato: { campo: "fechaFinContrato", abreviacion: "Fin", descripcion: "Fin de contrato", tipo: __TIPO_CAMPO_FECHA },
								tdContratoTipoContratoDescripcion: { campo: "tipoContratoDescripcion", abreviacion: "Plan", descripcion: "Plan actual", tipo: __TIPO_CAMPO_MULTIPLE, dataSource: { funcion: listTipoContratos, clave: "tipoContratoDescripcion", valor: "tipoContratoDescripcion" }, ancho: 75 },
								tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING, ancho: 90 },
								tdFechaVenta: { campo: "fechaVenta", descripcion: "Fecha de venta", abreviacion: "Vendido", tipo: __TIPO_CAMPO_FECHA },
								tdContratoLocalidad: { campo: "localidad", descripcion: "Localidad", abreviacion: "Localidad", tipo: __TIPO_CAMPO_STRING, ancho: 90 },
								tdContratoEquipo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 90 },
								tdContratoFormaPago: { campo: "formaPago.descripcion", clave: "formaPago.id", descripcion: "Forma de pago", abreviacion: "Forma pago", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listFormaPagos, clave: "id", valor: "descripcion" }, ancho: 90 },
								tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 90 },
								tdVendedor: { campo: "vendedor.nombre", clave: "vendedor.id", descripcion: "Vendedor", abreviacion: "Vendedor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listVendedores, clave: "id", valor: "nombre" }, ancho: 90 },
								tdDistribuidor: { campo: "distribuidor.nombre", clave: "distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidores, clave: "id", valor: "nombre" }, ancho: 90 },
								tdFechaEntregaDistribuidor: { campo: "fechaEntregaDistribuidor", descripcion: "Entregado", abreviacion: "Entregado", tipo: __TIPO_CAMPO_FECHA },
								tdFechaDevolucionDistribuidor: { campo: "fechaDevolucionDistribuidor", descripcion: "Devuelto", abreviacion: "Devuelto", tipo: __TIPO_CAMPO_FECHA },
								tdResultadoEntregaDistribucion: { campo: "resultadoEntregaDistribucion.descripcion", clave: "resultadoEntregaDistribucion.id", descripcion: "Resultado entrega", abreviacion: "Entrega", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listResultadoEntregaDistribuciones, clave: "id", valor: "descripcion" } },
								tdFechaEnvioAntel: { campo: "fechaEnvioAntel", descripcion: "Fecha de env�o a ANTEL", abreviacion: "E. ANTEL", tipo: __TIPO_CAMPO_FECHA },
								tdUsuario: { campo: "usuario.nombre", clave: "usuario.id", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listUsuarios, clave: "id", valor: "nombre" }, ancho: 90 },
								tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 90 },
							}, 
							true,
							reloadData,
							trContratoOnClick
						);
						
						grid.rebuild();
						
						grid.filtroDinamico.agregarFiltro(null, null);
						$("#selectCampo1").val("estado.nombre");
						grid.filtroDinamico.campoOnChange(null, null, 1, true);
						
						$("#selectCondicion1").val("keq");
						grid.filtroDinamico.condicionOnChange(null, null, 1, true, "estado.nombre");
						
						$("#inputValor1").val("1");
						grid.filtroDinamico.valorOnChange(null, null, 1, true);
						
						grid.filtroDinamico.agregarFiltro(null, null);
						$("#selectCampo2").val("usuario.nombre");
						grid.filtroDinamico.campoOnChange(null, null, 2, true);
						
						$("#selectCondicion2").val("nl");
						grid.filtroDinamico.condicionOnChange(null, null, 2, true, "usuario.nombre");
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleSextupleSize");
						break;
					}
				}
				
				if (grid == null) {
					grid = new Grid(
						document.getElementById("divTableContratos"),
						{
							tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
							tdContratoFinContrato: { campo: "fechaFinContrato", abreviacion: "Fin", descripcion: "Fin de contrato", tipo: __TIPO_CAMPO_FECHA },
							tdContratoTipoContratoDescripcion: { campo: "tipoContratoDescripcion", abreviacion: "Plan", descripcion: "Plan actual", tipo: __TIPO_CAMPO_STRING },
							tdContratoNuevoPlan: { campo: "nuevoPlan", descripcion: "Nuevo plan", abreviacion: "Nuevo plan", tipo: __TIPO_CAMPO_STRING },
							tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
							tdContratoNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING },
							tdContratoDireccionFactura: { campo: "direccionFactura", descripcion: "Direcci�n factura", abreviacion: "Direcci�n", tipo: __TIPO_CAMPO_STRING },
							tdContratoLocalidad: { campo: "localidad", descripcion: "Localidad", abreviacion: "Localidad", tipo: __TIPO_CAMPO_STRING },
							tdContratoZona: { campo: "zona.nombre", clave: "zona.id", descripcion: "Zona", abreviacion: "Zona", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listZonas, clave: "id", valor: "nombre"} },
							tdContratoEquipo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 90 },
							tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING },
							tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 90 },
						}, 
						true,
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

function listModelos() {
	var result = [];
	
	ModeloDWR.list(
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

function listFormaPagos() {
	var result = [];
	
	FormaPagoDWR.list(
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

function listVendedores() {
	var result = [];
	
	UsuarioRolEmpresaDWR.listVendedoresByContext(
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

function listZonas() {
	var result = [];
	
	ZonaDWR.list(
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

function listResultadoEntregaDistribuciones() {
	var result = [];
	
	ResultadoEntregaDistribucionDWR.list(
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

function listTipoContratos() {
	var result = [];
	
	ContratoDWR.listTipoContratos(
		grid.filtroDinamico.calcularMetadataConsulta(),
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
	if (estadoId == __ESTADO_LLAMAR
		|| estadoId == __ESTADO_RELLAMAR
		|| estadoId == __ESTADO_RECHAZADO
		|| estadoId == __ESTADO_REAGENDAR) {
		formMode = __FORM_MODE_VENTA;
	} else if (estadoId == __ESTADO_RECOORDINAR
		|| estadoId == __ESTADO_FALTA_DOCUMENTACION) {
		formMode = __FORM_MODE_RECOORDINACION;
	}
	
	document.getElementById("iFrameContrato").src = "/LogisticaWEB/pages/contrato/contrato.jsp?m=" + formMode + "&cid=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameContrato"));
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
	divCloseOnClick(null, document.getElementById("divCloseIFrameContrato"));
}

function inputSubirArchivoOnClick(event, element) {
	$("#selectEmpresa > option").remove();
	
	$("#selectEmpresa").append("<option value='0'>Seleccione...</option>");
	
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
		
		if (confirm("Se asignar�n " + metadataConsulta.tamanoSubconjunto + " registros.")) {
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
	if ($("#selectEmpresa").val() != "0") {
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
				ContratoDWR.procesarArchivoEmpresa(
					response.fileName,
					response.empresaId,
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
	} else {
		alert("Debe seleccionar una empresa.");
	}
}

function inputAgregarMidOnClick(event, element) {
	document.getElementById("iFrameContrato").src = "/LogisticaWEB/pages/contrato/contrato.jsp?m=" + __FORM_MODE_NEW;
	showPopUp(document.getElementById("divIFrameContrato"));
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

function inputExportarAExcelNucleoOnClick(event, element) {
	ContratoDWR.exportarAExcelNucleo(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				document.getElementById("formExportarAExcelNucleo").action = "/LogisticaWEB/Download?fn=" + data;
				document.getElementById("formExportarAExcelNucleo").submit();
			}, async: false
		}
	);
}