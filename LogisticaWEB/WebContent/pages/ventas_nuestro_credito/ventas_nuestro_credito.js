var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_FINANCIERA = 11;

var grid = null;

$(document).ready(init);

function init() {
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if ((data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_FINANCIERA)
						|| (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR)){
						
						grid = new Grid(
							document.getElementById("divTableContratos"),
							{
								tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
								tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 90 },
								tdFechaVenta: { campo: "fechaVenta", descripcion: "Fecha de venta", abreviacion: "Vendido", tipo: __TIPO_CAMPO_FECHA },
								tdValorTasaInteresEfectivaAnual: { campo: "valorTasaInteresEfectivaAnual", descripcion: "TEA", abreviacion: "TEA", tipo: __TIPO_CAMPO_DECIMAL },
								tdValorUnidadIndexada: { campo: "valorUnidadIndexada", descripcion: "UI", abreviacion: "UI", tipo: __TIPO_CAMPO_DECIMAL },
								tdValorTasaInteresEfectivaAnual: { campo: "valorTasaInteresEfectivaAnual", descripcion: "TEA", abreviacion: "TEA", tipo: __TIPO_CAMPO_DECIMAL },
								tdPrecio: { campo: "precio", descripcion: "Precio", abreviacion: "Precio", tipo: __TIPO_CAMPO_DECIMAL },
								tdCuotas: { campo: "cuotas", descripcion: "Cuotas", abreviacion: "Cuotas", tipo: __TIPO_CAMPO_NUMERICO },
								tdValorCuota: { campo: "valorCuota", descripcion: "Valor cuota", abreviacion: "Valor cuota", tipo: __TIPO_CAMPO_DECIMAL, ancho: 90 },
								tdIntereses: { campo: "intereses", descripcion: "Intereses", abreviacion: "Intereses", tipo: __TIPO_CAMPO_DECIMAL, ancho: 90 },
								tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstados, clave: "id", valor: "nombre" }, ancho: 90 },
							}, 
							true,
							reloadData,
							trContratoOnClick
						);
						
						grid.rebuild();
						
						break;
					}
				}
				
				if (grid == null) {
					grid = new Grid(
						document.getElementById("divTableContratos"),
						{
							tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO }
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
	
	ContratoDWR.listNuestroCreditoContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	ContratoDWR.countNuestroCreditoContextAware(
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

function trContratoOnClick() {
	
}