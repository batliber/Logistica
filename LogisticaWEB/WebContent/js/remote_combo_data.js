function listACMInterfaceEstados() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/ACMInterfaceEstadoREST/list"
	});
}

function listTipoControlRiesgoCrediticios() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/TipoControlRiesgoCrediticioREST/list"
	});
}

function listEstadoRiesgoCrediticios() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/EstadoRiesgoCrediticioREST/list"
	});
}

function listCalificacionRiesgoCrediticioAntel() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/CalificacionRiesgoCrediticioAntelREST/list"
	});
}

function listCalificacionRiesgoCrediticioBCU() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/CalificacionRiesgoCrediticioBCUREST/list"
	});
}

function listSituacionRiesgoCrediticioParaguays() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/SituacionRiesgoCrediticioParaguayREST/list"
	});
}

function listEmpresas() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
	});
}

function listMarcas() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/MarcaREST/list"
	});
}

function listEmpresaServices() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/EmpresaServiceREST/list"
	});
}

function listModelos() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/ModeloREST/list"
	});
}

function listTipoPlanes() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/TipoPlanREST/list"
	});
}

function listPlanes() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/PlanREST/listMinimal"
	});
}

function listMotivoCambioPlanes() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/MotivoCambioPlanREST/list"
	});
}

function listMonedas() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/MonedaREST/list"
	});
}

function listFormaPagos() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/FormaPagoREST/list"
	});
}

function listTipoTasaInteresEfectivaAnuales() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/TipoTasaInteresEfectivaAnualREST/list"
	});
}

function listTipoProductos() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/TipoProductoREST/list"
	});
}

function listProductos() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/ProductoREST/listMinimal"
	});
}

function listStockTipoMovimientos() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/StockTipoMovimientoREST/list"
	});
}

function listDepartamentos() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/DepartamentoREST/list"
	});
}

function listBarrios() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/BarrioREST/listMinimal"
	});
}

function listZonas() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/ZonaREST/listMinimal"
	});
}

function listEstadoPuntoVentas() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/EstadoPuntoVentaREST/list"
	});
}

function listEstadoVisitaPuntoVentaDistribuidores() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/EstadoVisitaPuntoVentaDistribuidorREST/list"
	});
}

function listResultadoEntregaDistribuciones() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/ResultadoEntregaDistribucionREST/list"
	});
}

function listPuntoVentas() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/listMinimal"
	});
}

function listVendedores() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listVendedoresByContextMinimal"
	});
}

function listBackoffices() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listBackofficesByContextMinimal"
	});
}

function listDistribuidores() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listDistribuidoresByContextMinimal"
	});
}

function listDistribuidoresChips() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listDistribuidoresChipsByContextMinimal"
	});
}

function listActivadores() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listActivadoresByContextMinimal"
	});
}

function listUsuarios() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioREST/listMinimal"
	});
}

function listEstados() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/EstadoREST/list"
	});
}

function listEstadoANTELs() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/EstadoANTELREST/list"
	});
}

function listEstadoActivaciones() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/EstadoActivacionREST/list"
	});
}

function listTipoActivaciones() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/TipoActivacionREST/list"
	});
}

function listEstadoControles() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/EstadoControlREST/list"
	});
}

function listTipoControles() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/TipoControlREST/list"
	});
}

function listMenus() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/MenuREST/list"
	});
}

function listEstadosProcesoImportacion() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/EstadoProcesoImportacionREST/list"
	});
}

function listTiposProcesoImportacion() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/TipoProcesoImportacionREST/list"
	});
}

function listSeguridadTipoEventos() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadTipoEventoREST/list"
	});
}

function listRecargaEstadoAcreditacionSaldos() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaEstadoSolicitudSaldoREST/list"
	});
}

function listRecargaEmpresas() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaEmpresaREST/list"
	});
}

function listRecargaFormaPagos() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaFormaPagoREST/list"
	});
}

function listRecargaBancos() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaBancoREST/list"
	});
}

function listEmpresaRecargaBancoCuentas() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/EmpresaRecargaBancoCuentaREST/list"
	});
}

function listRecargaEstados() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaEstadoREST/list"
	});
}