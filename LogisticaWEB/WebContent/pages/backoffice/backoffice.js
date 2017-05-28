var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_BACK_OFFICE = 5;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonAsignar").hide();
	$("#divButtonExportarAExcel").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if ((data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_BACK_OFFICE)
						|| (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR)) {
						$("#divButtonAsignar").show();
						$("#divButtonExportarAExcel").show();
						
						grid = new Grid(
							document.getElementById("divTableContratos"),
							{
								tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
								tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
								tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 80 },
								tdFechaVenta: { campo: "fechaVenta", descripcion: "Fecha de venta", abreviacion: "Vendido", tipo: __TIPO_CAMPO_FECHA },
								tdContratoFechaBackoffice: { campo: "fechaBackoffice", descripcion: "Fecha de armado", abreviacion: "Armado", tipo: __TIPO_CAMPO_FECHA },
								tdFechaEnvioAntel: { campo: "fechaEnvioAntel", descripcion: "Fecha de envío a ANTEL", abreviacion: "E. ANTEL", tipo: __TIPO_CAMPO_FECHA },
								tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING, ancho: 80 },
								tdContratoNuevoPlan: { campo: "nuevoPlan.descripcion", clave: "nuevoPlan.id", descripcion: "Nuevo plan", abreviacion: "Nuevo plan", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPlanes, clave: "id", valor: "descripcion" }, ancho: 80 },
<<<<<<< HEAD
								tdContratoEquipo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 80 },
								tdContratoFormaPago: { campo: "formaPago.descripcion", clave: "formaPago.id", descripcion: "Forma de pago", abreviacion: "Forma pago", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listFormaPagos, clave: "id", valor: "descripcion" }, ancho: 90 },
=======
								tdContratoEquipo: { campo: "producto.descripcion", clave: "producto.id", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listProductos, clave: "id", valor: "descripcion" }, ancho: 80 },
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
								tdContratoDepartamento: { campo: "zona.departamento.nombre", clave: "zona.departamento.id", descripcion: "Departamento", abreviacion: "Depto.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre"}, ancho: 80 },
								tdContratoBarrio: { campo: "barrio.nombre", clave: "barrio.id", descripcion: "Barrio", abreviacion: "Barrio", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBarrios, clave: "id", valor: "nombre" }, ancho: 80 },
								tdContratoZona: { campo: "zona.nombre", clave: "zona.id", descripcion: "Zona", abreviacion: "Zona", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listZonas, clave: "id", valor: "nombre"}, ancho: 80 },
<<<<<<< HEAD
								tdContratoNumeroSerie: { campo: "numeroSerie", descripcion: "Número de serie", abreviacion: "Serie", tipo: __TIPO_CAMPO_STRING, ancho: 80 },
=======
								tdContratoNumeroSerie: { campo: "numeroSerie", descripcion: "N�mero de serie", abreviacion: "Serie", tipo: __TIPO_CAMPO_STRING, ancho: 80 },
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
								tdVendedor: { campo: "vendedor.nombre", clave: "vendedor.id", descripcion: "Vendedor", abreviacion: "Vendedor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listVendedores, clave: "id", valor: "nombre" }, ancho: 80 },
								tdBackoffice: { campo: "backoffice.nombre", clave: "backoffice.id", descripcion: "Backoffice", abreviacion: "Backoffice", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBackoffices, clave: "id", valor: "nombre" }, ancho: 80 },
								tdUsuario: { campo: "usuario.nombre", clave: "usuario.id", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listUsuarios, clave: "id", valor: "nombre" }, ancho: 80 },
								tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 80 },
								tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 80 }
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
						
						$("#inputValor1").val("4");
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
<<<<<<< HEAD
							tdContratoEquipo: { campo: "modelo.descripcion", clave: "modelo.id", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listModelos, clave: "id", valor: "descripcion" }, ancho: 90 },
=======
							tdContratoEquipo: { campo: "producto.descripcion", clave: "producto.id", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listProductos, clave: "id", valor: "descripcion" }, ancho: 90 },
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
							tdContratoDepartamento: { campo: "zona.departamento.nombre", clave: "zona.departamento.id", descripcion: "Departamento", abreviacion: "Depto.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre"} },
							tdContratoZona: { campo: "zona.nombre", clave: "zona.id", descripcion: "Zona", abreviacion: "Zona", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listZonas, clave: "id", valor: "nombre"} },
							tdContratoFechaEntrega: { campo: "fechaEntrega", descripcion: "Fecha de entrega", abreviacion: "F. entrega", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
							tdContratoNumeroSerie: { campo: "numeroSerie", descripcion: "Número de serie", abreviacion: "Serie", tipo: __TIPO_CAMPO_STRING },
							tdVendedor: { campo: "vendedor.nombre", clave: "vendedor.id", descripcion: "Vendedor", abreviacion: "Vendedor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listVendedores, clave: "id", valor: "nombre" }, ancho: 90 },
							tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 90 },
							tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING }
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
	$("#divIFrameSeleccionBackoffice").draggable();
}

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

function listBackoffices() {
	var result = [];
	
	UsuarioRolEmpresaDWR.listBackofficesByContext(
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
	if (estadoId == __ESTADO_VENDIDO) {
		formMode = __FORM_MODE_BACKOFFICE;
	}
	
	document.getElementById("iFrameContrato").src = "/LogisticaWEB/pages/contrato/contrato.jsp?m=" + formMode + "&cid=" + $(target).attr("id");
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
					$("#selectBackoffice > option").remove();
					
					$("#selectBackoffice").append("<option value='0'>Seleccione...</option>");
					
					UsuarioRolEmpresaDWR.listBackofficesByContext(
						{
							callback: function(data) {
								var html = "";
								
								for (var i=0; i<data.length; i++) {
									html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
								}
								
								$("#selectBackoffice").append(html);
							}, async: false
						}
					);
					
					showPopUp(document.getElementById("divIFrameSeleccionBackoffice"));
				}
			}, async: false
		}
	);
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameSeleccionBackoffice"));
	
	$("#selectBackoffice").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputAceptarOnClick(event, element) {
	if ($("#selectBackoffice").val() != "0") {
		var backoffice = {
			id: $("#selectBackoffice").val()
		};
		
		metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
		metadataConsulta.tamanoSubconjunto = 
			Math.min(
				$("#inputTamanoSubconjunto").val(),
				$("#divCantidadRegistrosValue").text()
			);
		
		if (confirm("Se asignar�n " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			ContratoDWR.asignarBackoffice(
				backoffice,
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
		alert("Debe seleccionar un backoffice.");
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