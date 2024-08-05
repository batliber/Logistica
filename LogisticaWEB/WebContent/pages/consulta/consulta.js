var __ROL_ADMINISTRADOR = 1;
var __ROL_CONSULTA = 23;

var grid = null;
var formularioContrato = null;

$(document).ready(init);

function init() {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_CONSULTA) {
				grid = new Grid(
					document.getElementById("divTableContratos"),
					{
						tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO, ancho: 75 },
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" } },
						tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
						tdContratoDocumento: { campo: "documento", descripcion: "Documento", abreviacion: "Documento", tipo: __TIPO_CAMPO_STRING },
						tdContratoNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING },
						tdContratoApellido: { campo: "apellido", descripcion: "Apellido", abreviacion: "Apellido", tipo: __TIPO_CAMPO_STRING },
						tdContratoNumeroContrato: { campo: "numeroContrato", descripcion: "Número de Contrato", abreviacion: "Nro. Contrato", tipo: __TIPO_CAMPO_STRING },
						tdVendedor: { campo: "vendedor.nombre", clave: "vendedor.id", descripcion: "Vendedor", abreviacion: "Vendedor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listVendedores, clave: "id", valor: "nombre" }, ancho: 90 },
						tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 90 },
						tdFcre: { campo: "fcre", descripcion: "Creado", abreviacion: "Creado", tipo: __TIPO_CAMPO_FECHA_HORA }
					}, 
					true,
					reloadData,
					trContratoOnClick
				);
				
				grid.rebuild();
				
				break;
			}
		}

		reloadData();

//		formularioContrato = new FormularioContrato(document.getElementById("divFormularioContrato"));

		$("#divIFrameContrato").draggable();
		$("#divIFrameHistoricoContrato").draggable();
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
	
	//document.getElementById("iFrameHistorico").src = "/LogisticaWEB/pages/monitoreo/historico.jsp?cid=" + $(target).attr("id");
	//showPopUp(document.getElementById("divIFrameHistoricoContrato"));
	document.getElementById("iFrameContrato").src = "/LogisticaWEB/pages/contrato/contrato.jsp?m=" + __FORM_MODE_CONSULTA + "&cid=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameContrato"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameContrato"));
}