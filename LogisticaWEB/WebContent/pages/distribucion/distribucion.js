var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_DISTRIBUCION = 7;
var __ROL_COORDINADOR_DISTRIBUCION = 11;
var __ROL_DISTRIBUIDOR = 8;

var rol = __ROL_DISTRIBUIDOR;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonAsignar").hide();
	$("#divButtonExportarAExcel").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if ((data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_DISTRIBUCION)
						|| (data.usuarioRolEmpresas[i].rol.id == __ROL_COORDINADOR_DISTRIBUCION)
						|| (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR)) {
						$("#divButtonAsignar").show();
						$("#divButtonExportarAExcel").show();
						
						grid = new Grid(
							document.getElementById("divTableContratos"),
							{
								tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
								tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
								tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 80 },
								tdContratoEquipo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 80 },
								tdContratoPrecio: { campo: "precio", descripcion: "Precio", abreviacion: "Precio", tipo: __TIPO_CAMPO_NUMERICO, ancho: 50 },
								tdContratoCostoEnvio: { campo: "costoEnvio", descripcion: "Costo de envío", abreviacion: "C. env.", tipo: __TIPO_CAMPO_DECIMAL },
								tdContratoFechaEntrega: { campo: "fechaEntrega", descripcion: "Fecha de entrega", abreviacion: "Entrega", tipo: __TIPO_CAMPO_FECHA },
								tdContratoDepartamento: { campo: "zona.departamento.nombre", clave: "zona.departamento.id", descripcion: "Departamento", abreviacion: "Depto.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre"}, ancho: 80 },
								tdContratoBarrio: { campo: "barrio.nombre", clave: "barrio.id", descripcion: "Barrio", abreviacion: "Barrio", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBarrios, clave: "id", valor: "nombre" }, ancho: 80 },
								tdContratoZona: { campo: "zona.nombre", clave: "zona.id", descripcion: "Zona", abreviacion: "Zona", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listZonas, clave: "id", valor: "nombre"}, ancho: 55 },
								tdContratoNumeroSerie: { campo: "numeroSerie", descripcion: "Número de serie", abreviacion: "Serie", tipo: __TIPO_CAMPO_STRING, ancho: 95 },
								tdUsuario: { campo: "usuario.nombre", clave: "usuario.id", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listUsuarios, clave: "id", valor: "nombre" }, ancho: 80 },
								tdFechaBackoffice: { campo: "fechaBackoffice", descripcion: "Fecha de armado", abreviacion: "Armado", tipo: __TIPO_CAMPO_FECHA },
								tdFechaEntregaDistribuidor: { campo: "fechaEntregaDistribuidor", descripcion: "Entregado", abreviacion: "Entregado", tipo: __TIPO_CAMPO_FECHA },
								tdFechaDevolucionDistribuidor: { campo: "fechaDevolucionDistribuidor", descripcion: "Devuelto", abreviacion: "Devuelto", tipo: __TIPO_CAMPO_FECHA },
								tdResultadoEntregaDistribucion: { campo: "resultadoEntregaDistribucion.descripcion", clave: "resultadoEntregaDistribucion.id", descripcion: "Resultado entrega", abreviacion: "Entrega", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listResultadoEntregaDistribuciones, clave: "id", valor: "descripcion" } },
								tdFechaCoordinacion: { campo: "fechaCoordinacion", descripcion: "Fecha de coordinación", abreviacion: "Coordinado", tipo: __TIPO_CAMPO_FECHA },
								tdFechaEnvioAntel: { campo: "fechaEnvioAntel", descripcion: "Fecha de envío a ANTEL", abreviacion: "E. ANTEL", tipo: __TIPO_CAMPO_FECHA },
								tdDistribuidor: { campo: "distribuidor.nombre", clave: "distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidores, clave: "id", valor: "nombre" }, ancho: 80 },
								tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 80 },
							}, 
							true,
							reloadData,
							trContratoOnClick
						);
						
						grid.rebuild();
						
						grid.filtroDinamico.agregarFiltrosManuales([
							{
								campo: "estado.nombre",
								operador: "keq",
								valores: ["8"]
							},
							{
								campo: "usuario.nombre",
								operador: "nl",
								valores: []
							},
							{
								campo: "fechaEntregaDistribuidor",
								operador: "nl",
								valores: []
							}
						]);
						
						rol = data.usuarioRolEmpresas[i].rol.id;
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleTripleSize");
						
						break;
					}
				}
				
				if (grid == null) {
					$("#divButtonExportarAExcel").show();
					
					grid = new Grid(
						document.getElementById("divTableContratos"),
						{
							tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
							tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" } },
							tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
							tdContratoCostoEnvio: { campo: "costoEnvio", descripcion: "Costo de envío", abreviacion: "C. env.", tipo: __TIPO_CAMPO_DECIMAL },
							tdContratoDepartamento: { campo: "zona.departamento.nombre", clave: "zona.departamento.id", descripcion: "Departamento", abreviacion: "Depto.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre"} },
							tdContratoBarrio: { campo: "barrio.nombre", clave: "barrio.id", descripcion: "Barrio", abreviacion: "Barrio", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBarrios, clave: "id", valor: "nombre" }, ancho: 80 },
							tdFechaBackoffice: { campo: "fechaBackoffice", descripcion: "Fecha de armado", abreviacion: "Armado", tipo: __TIPO_CAMPO_FECHA },
							tdFechaEntregaDistribuidor: { campo: "fechaEntregaDistribuidor", descripcion: "Entregado", abreviacion: "Entregado", tipo: __TIPO_CAMPO_FECHA },
							tdFechaDevolucionDistribuidor: { campo: "fechaDevolucionDistribuidor", descripcion: "Devuelto", abreviacion: "Devuelto", tipo: __TIPO_CAMPO_FECHA },
							tdResultadoEntregaDistribucion: { campo: "resultadoEntregaDistribucion.descripcion", clave: "resultadoEntregaDistribucion.id", descripcion: "Resultado entrega", abreviacion: "Entrega", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listResultadoEntregaDistribuciones, clave: "id", valor: "descripcion" } },
							tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 90 },
						}, 
						true,
						reloadData,
						trContratoOnClick
					);
					
					grid.rebuild();
					
					reloadData();
				}
			}, async: false
		}
	);
	
	$("#divIFrameContrato").draggable();
	$("#divIFrameSeleccionDistribuidor").draggable();
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

function listDepartamentos() {
	var result = [];
	
	DepartamentoDWR.list(
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

function listBarrios() {
	var result = [];
	
	BarrioDWR.list(
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
	if (estadoId == __ESTADO_DISTRIBUIR) {
		formMode = __FORM_MODE_DISTRIBUCION;
	} else if (estadoId == __ESTADO_REDISTRIBUIR) {
		formMode = __FORM_MODE_REDISTRIBUCION;
	} else if (estadoId == __ESTADO_RECOORDINAR || estadoId == __ESTADO_FALTA_DOCUMENTACION) {
		formMode = __FORM_MODE_RECOORDINACION;
	}
	
	if (rol == __ROL_DISTRIBUIDOR) {
		formMode = __FORM_MODE_READ;
	}
	
	if (rol == __ROL_DISTRIBUIDOR) {
		formMode = __FORM_MODE_READ;
	}
	
	document.getElementById("iFrameContrato").src = "/LogisticaWEB/pages/contrato/contrato.jsp?m=" + formMode + "&cid=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameContrato"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#selectDistribuidor").val("0");
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
		ContratoDWR.chequearRelacionesAsignacion(
			metadataConsulta,
			{
				callback: function(dataRelaciones) {
					if (dataRelaciones || confirm("Atención: dentro de la selección hay contratos relacionados sin asignar.")) {
						ContratoDWR.chequearAsignacion(
							metadataConsulta,
							{
								callback: function(data) {
									if (data || confirm("Atención: se modificarán registros que ya se encuentran asignados.")) {
										$("#selectDistribuidor > option").remove();
										
										$("#selectDistribuidor").append("<option value='0'>Seleccione...</option>");
										
										UsuarioRolEmpresaDWR.listDistribuidoresByContext(
											{
												callback: function(data) {
													var html = "";
													
													for (var i=0; i<data.length; i++) {
														html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
													}
													
													$("#selectDistribuidor").append(html);
												}, async: false
											}
										);
						
										showPopUp(document.getElementById("divIFrameSeleccionDistribuidor"));
									}
								}, async: false
							}
						);
					}
				}, async: false
			}
		);
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
	if ($("#selectDistribuidor").val() != "0") {
		var distribuidor = {
			id: $("#selectDistribuidor").val()
		};
		
		metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
		if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
			metadataConsulta.tamanoSubconjunto = grid.getCount();
		}
		
		if (confirm("Se asignarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			ContratoDWR.asignarDistribuidor(
				distribuidor,
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
		alert("Debe seleccionar un distribuidor.");
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