var grid = null;

$(document).ready(function() {
	grid = new Grid(
		document.getElementById("divTableContratos"),
		{
			tdContratoNumeroTramite: { campo: "contrato.numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO },
			tdContratoMid: { campo: "contrato.mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
			tdContratoFinContrato: { campo: "contrato.fechaFinContrato", abreviacion: "Fin", descripcion: "Fin de contrato", tipo: __TIPO_CAMPO_FECHA },
			tdContratoTipoContratoDescripcion: { campo: "contrato.tipoContratoDescripcion", abreviacion: "Plan", descripcion: "Plan actual", tipo: __TIPO_CAMPO_STRING },
			tdFechaVenta: { campo: "contrato.fechaVenta", descripcion: "Fecha de venta", abreviacion: "Vendido", tipo: __TIPO_CAMPO_FECHA },
			tdContratoLocalidad: { campo: "contrato.localidad", descripcion: "Localidad", abreviacion: "Localidad", tipo: __TIPO_CAMPO_STRING },
			tdContratoEquipo: { campo: "contrato.equipo", descripcion: "Equipo", abreviacion: "Equipo", tipo: __TIPO_CAMPO_STRING },
			tdContratoObservaciones: { campo: "contrato.observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING },
			tdVendedor: { campo: "contrato.vendedor.nombre", descripcion: "Vendedor", abreviacion: "Vendedor", tipo: __TIPO_CAMPO_STRING },
			tdBackoffice: { campo: "contrato.backoffice.nombre", descripcion: "Backoffice", abreviacion: "Backoffice", tipo: __TIPO_CAMPO_STRING },
			tdDistribuidor: { campo: "contrato.distribuidor.nombre", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_STRING },
			tdActivador: { campo: "contrato.activador.nombre", descripcion: "Activador", abreviacion: "Activador", tipo: __TIPO_CAMPO_STRING },
			tdEstado: { campo: "contrato.estado.nombre", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_STRING },
			tdEstadoId: { campo: "contrato.estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_NUMERICO, oculto: true }
		}, 
		reloadData,
		trContratoOnClick
	);
	
	grid.rebuild();
	
	reloadData();
	
	if (message != null) {
		if (confirm(message.replace(new RegExp("\\|", "g"), "\n"))) {
			ContratoRoutingHistoryDWR.procesarArchivoEmpresa(
				fileName,
				empresaId,
				{
					callback: function(data) {
						reloadData();
					}, async: false
				}
			);
		}
	}
});

function reloadData() {
	ContratoRoutingHistoryDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}, async: false
		}
	);
}

function inputActualizarOnClick(event, element) {
	reloadData();
}

function trContratoOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	document.getElementById("iFrameHistorico").src = "/LogisticaWEB/pages/monitoreo/historico.jsp?cid=" + $(target).attr("cid") + "&crhid=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameHistoricoContrato"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
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
		$("#formSubirArchivo").submit();
	} else {
		alert("Debe seleccionar una empresa.");
	}
}

function inputAgregarMidOnClick(event, element) {
	document.getElementById("iFrameContrato").src = "../contrato/contrato.jsp?m=" + __FORM_MODE_NEW;
	showPopUp(document.getElementById("divIFrameContrato"));
}