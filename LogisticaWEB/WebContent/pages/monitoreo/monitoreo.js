var grid = null;
var formularioContrato = null;

$(document).ready(function() {
	grid = new Grid(
		document.getElementById("divTableContratos"),
		{
			tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO, ancho: 75 },
			tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" } },
//			tdEmpresa: { campo: "empresa.nombre", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_STRING, ancho: 75 },
			tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
			tdContratoFinContrato: { campo: "fechaFinContrato", abreviacion: "Fin", descripcion: "Fin de contrato", tipo: __TIPO_CAMPO_FECHA },
			tdContratoTipoContratoDescripcion: { campo: "tipoContratoDescripcion", abreviacion: "Plan", descripcion: "Plan actual", tipo: __TIPO_CAMPO_STRING, ancho: 80 },
			tdFechaVenta: { campo: "fechaVenta", descripcion: "Fecha de venta", abreviacion: "Vendido", tipo: __TIPO_CAMPO_FECHA },
			tdContratoLocalidad: { campo: "localidad", descripcion: "Localidad", abreviacion: "Localidad", tipo: __TIPO_CAMPO_STRING, ancho: 90 },
			tdContratoEquipo: { campo: "producto.descripcion", clave: "producto.id", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listProductos, clave: "id", valor: "descripcion" }, ancho: 90 },
//			tdContratoEquipo: { campo: "producto.descripcion", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_STRING, ancho: 90 },
			tdContratoObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING },
			tdVendedor: { campo: "vendedor.nombre", clave: "vendedor.id", descripcion: "Vendedor", abreviacion: "Vendedor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listVendedores, clave: "id", valor: "nombre" }, ancho: 90 },
//			tdVendedor: { campo: "vendedor.nombre", descripcion: "Vendedor", abreviacion: "Vendedor", tipo: __TIPO_CAMPO_STRING, ancho: 100 },
			tdBackoffice: { campo: "backoffice.nombre", clave: "backoffice.id", descripcion: "Backoffice", abreviacion: "Backoffice", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBackoffices, clave: "id", valor: "nombre" }, ancho: 80 },
//			tdBackoffice: { campo: "backoffice.nombre", descripcion: "Backoffice", abreviacion: "Backoffice", tipo: __TIPO_CAMPO_STRING, ancho: 100 },
			tdDistribuidor: { campo: "distribuidor.nombre", clave: "distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidores, clave: "id", valor: "nombre" }, ancho: 80 },
//			tdDistribuidor: { campo: "distribuidor.nombre", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_STRING, ancho: 100 },
			tdActivador: { campo: "activador.nombre", clave: "activador.id", descripcion: "Activador", abreviacion: "Activador", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listActivadores, clave: "id", valor: "nombre" }, ancho: 90 },
//			tdActivador: { campo: "activador.nombre", descripcion: "Activador", abreviacion: "Activador", tipo: __TIPO_CAMPO_STRING, ancho: 100 },
			tdFechaEnvioAntel: { campo: "fechaEnvioAntel", descripcion: "Fecha de envío a ANTEL", abreviacion: "E. ANTEL", tipo: __TIPO_CAMPO_FECHA },
			tdUsuario: { campo: "usuario.nombre", clave: "usuario.id", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listUsuarios, clave: "id", valor: "nombre" }, ancho: 90 },
//			tdUsuario: { campo: "usuario.nombre", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_STRING, ancho: 100 },
			tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 90 },
//			tdEstado: { campo: "estado.nombre", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_STRING, ancho: 90 },
//			tdEstadoId: { campo: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_NUMERICO, oculto: true }
		}, 
		reloadData,
		trContratoOnClick
	);
	
	grid.rebuild();
	
	reloadData();
	
	formularioContrato = new FormularioContrato(document.getElementById("divFormularioContrato"));
	$("#divIFrameContrato").draggable();
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
	
	document.getElementById("iFrameHistorico").src = "/LogisticaWEB/pages/monitoreo/historico.jsp?cid=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameHistoricoContrato"));
	
//	var cid = $(target).attr("cid");
//	ContratoDWR.getById(
//		cid,
//		{
//			callback: function(data) {
//				formularioContrato.setContrato(data);
//			}, async: false
//		}
//	);
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameContrato"));
}

function inputSubirArchivoOnClick() {
	$("#selectEmpresa > option").remove();
	
	$("#selectEmpresa").append("<option value='0'>Seleccione...</option>");
	
	EmpresaDWR.list(
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

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameImportacionArchivo"));
	
	$("#selectEmpresa").val("0");
	$("#inputArchivo").val("");
	
	reloadData();
}

function inputAceptarOnClick(event, element) {
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