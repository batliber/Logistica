var gridTotales = null;
var gridSolicitudesSaldo = null;

$(document).ready(init);

function init() {
	$("#divTitle").append("Saldo");
	
	gridTotales = new Grid(
		document.getElementById("divTableTotales"),
		{
			tdEmpresa: { campo: "recargaEmpresa.nombre", clave: "recargaEmpresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" } },
			tdSaldo: { campo: "monto", descripcion: "Monto", abreviacion: "Monto", tipo: __TIPO_CAMPO_DECIMAL, ancho: 90 }
		}, 
		false,
		reloadData,
		trTotalesOnClick,
		null,
		5,
		35
	);
	
	gridTotales.rebuild();
	
	gridSolicitudesSaldo = new Grid(
		document.getElementById("divTableSolicitudesSaldo"),
		{
			tdFechaSolicitud: { campo: "fechaSolicitud", descripcion: "Fecha de solicitud", abreviacion: "Solicitud", tipo: __TIPO_CAMPO_FECHA, ancho: 80 },
			tdMonto: { campo: "monto", descripcion: "Monto", abreviacion: "Monto", tipo: __TIPO_CAMPO_DECIMAL, ancho: 80 },
			tdEstadoAcreditacionSaldo: { campo: "recargaEstadoAcreditacionSaldo.nombre", clave: "recargaEstadoAcreditacionSaldo.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listRecargaEstadoAcreditacionSaldos, clave: "id", valor: "nombre" }, ancho: 80 },
			tdEmpresa: { campo: "recargaEmpresa.nombre", clave: "recargaEmpresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listRecargaEmpresas, clave: "id", valor: "nombre" }, ancho: 75 }
		}, 
		true,
		reloadData,
		trSolicitudesSaldoOnClick,
		null,
		8,
		35
	);
	
	gridSolicitudesSaldo.rebuild();
	
	gridSolicitudesSaldo.filtroDinamico.preventReload = true;
	gridSolicitudesSaldo.filtroDinamico.agregarOrdenManual(
		{
			campo: "tdFechaSolicitud",
			ascendente: false
		}
	).then(function(data) {
		gridSolicitudesSaldo.filtroDinamico.preventReload = false
			
		reloadData();
	});
}

function reloadData() {
	gridTotales.setStatus(gridTotales.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaPuntoVentaUsuarioREST/getPuntoVentaByContext"
	}).then(function(data) {
		if (data != null) {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/RecargaMovimientoREST/listSaldoByPuntoVentaId/" + data.id,
				method: "GET",
				contentType: 'application/json'
			}).then(function(data) {
				gridTotales.reload(
					{ registrosMuestra: data, cantidadRegistros: data.length }
				);
			});
		}
	});
	
	gridSolicitudesSaldo.setStatus(gridSolicitudesSaldo.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaSolicitudAcreditacionSaldoREST/listContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(gridSolicitudesSaldo.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		gridSolicitudesSaldo.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaSolicitudAcreditacionSaldoREST/countContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(gridSolicitudesSaldo.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		gridSolicitudesSaldo.setCount(data);
	});
}

function trTotalesOnClick(eventObject) {
	
}

function trSolicitudesSaldoOnClick() {
	
}

function inputSolicitudSaldoOnClick(event, element) {
	window.location = "/LogisticaWEB/pages/mobile/mrecargas/msolicitud_saldo/msolicitud_saldo.jsp";
}