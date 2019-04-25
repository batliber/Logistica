var __ROL_ADMINISTRADOR = 1;
var __ROL_ENCARGADO_ACTIVACIONES = 12;
var __ROL_SUPERVISOR_DISTRIBUCION_CHIPS = 18;
var __ROL_DEMO = 21;

var grid = null;
		
$(document).ready(init)

function init() {
	$("#divButtonAsignarVisitas").hide();
	$("#divButtonRecalcularPorcentajes").hide();
	$("#divButtonNuevo").hide();
	
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
					if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_DISTRIBUCION_CHIPS
						|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
						$("#divButtonNuevo").show();
						$("#divButtonAsignarVisitas").show();
						$("#divButtonRecalcularPorcentajes").show();
						
						grid = new Grid(
							document.getElementById("divTableActivacionesSublotes"),
							{
								tdNumero: { campo: "numero", descripcion: "Número", abreviacion: "Número", tipo: __TIPO_CAMPO_NUMERICO },
								tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 200 },
								tdDescripcion: { campo: "descripcion", abreviacion: "Descripción", descripcion: "Descripción", tipo: __TIPO_CAMPO_STRING, ancho: 250 },
								tdDistribuidor: { campo: "distribuidor.nombre", clave: "distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidores, clave: "id", valor: "nombre" }, ancho: 200 },
								tdFechaAsignacionDistribuidor: { campo: "fechaAsignacionDistribuidor", abreviacion: "F. Asign. Distr.", descripcion: "Fecha de asign. Distribuidor", tipo: __TIPO_CAMPO_FECHA_HORA },
								tdPuntoVenta: { campo: "puntoVenta.nombre", clave: "puntoVenta.id", descripcion: "Punto de venta", abreviacion: "Pto. venta", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPuntoVentas, clave: "id", valor: "nombre" }, ancho: 200 },
								tdFechaAsignacionPuntoVenta: { campo: "fechaAsignacionPuntoVenta", abreviacion: "F. Asign. P.V.", descripcion: "Fecha de asign. Pto. venta", tipo: __TIPO_CAMPO_FECHA_HORA },
								tdPorcentajeActivacion: { campo: "porcentajeActivacion", descripcion: "Porcentaje activación", abreviacion: "% act.", tipo: __TIPO_CAMPO_PORCENTAJE, decimales: 1 },
							}, 
							true,
							reloadData,
							trActivacionSubloteOnClick
						);
						
						grid.rebuild();
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleFourfoldSize");
						break;
					}
				}
				
				if (grid == null) {
					for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
						if (data.usuarioRolEmpresas[i].rol.id == __ROL_ENCARGADO_ACTIVACIONES) {
							$("#divButtonNuevo").show();
							
							grid = new Grid(
								document.getElementById("divTableActivacionesSublotes"),
								{
									tdNumero: { campo: "numero", descripcion: "Número", abreviacion: "Número", tipo: __TIPO_CAMPO_NUMERICO },
									tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 200 },
									tdDescripcion: { campo: "descripcion", abreviacion: "Descripción", descripcion: "Descripción", tipo: __TIPO_CAMPO_STRING, ancho: 250 },
									tdDistribuidor: { campo: "distribuidor.nombre", clave: "distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidores, clave: "id", valor: "nombre" }, ancho: 200 },
									tdFechaAsignacionDistribuidor: { campo: "fechaAsignacionDistribuidor", abreviacion: "F. Asign. Distr.", descripcion: "Fecha de asign. Distribuidor", tipo: __TIPO_CAMPO_FECHA_HORA }
								}, 
								true,
								reloadData,
								trActivacionSubloteOnClick
							);
							
							grid.rebuild();
							
							$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
							
							break;
						}
					}
				}
				
				if (grid == null) {
					grid = new Grid(
						document.getElementById("divTableActivacionesSublotes"),
						{
							tdMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
						}, 
						true,
						reloadData,
						trActivacionSubloteOnClick
					);
					
					grid.rebuild();
				}
			}, async: false
		}
	);
	
	reloadData();

	$("#divIFrameActivacionSublote").draggable();
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

function listPuntoVentas() {
	var result = [];
	
	PuntoVentaDWR.list(
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
	
	ActivacionSubloteDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	ActivacionSubloteDWR.countContextAware(
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

function trActivacionSubloteOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameActivacionSublote").src = "/LogisticaWEB/pages/activaciones_sublote/activaciones_sublotes_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFrameActivacionSublote"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#selectDistribuidor").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function closeDialog() {
	divCloseOnClick(null, document.getElementById("divCloseIFrameActivacionSublote"));
}

function inputNuevoOnClick() {
	document.getElementById("iFrameActivacionSublote").src = "./activaciones_sublotes_edit.jsp";
	showPopUp(document.getElementById("divIFrameActivacionSublote"));
}

function inputAsignarVisitasOnClick() {
	metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
		metadataConsulta.tamanoSubconjunto = grid.getCount();
	}
	
	$("#selectDistribuidor > option").remove();
	
	$("#selectDistribuidor").append("<option value='0'>Seleccione...</option>");
	
	UsuarioRolEmpresaDWR.listDistribuidoresChipsByContext(
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
		
		var observaciones = $("#textareaObservaciones").val();
		
		metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
		if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
			metadataConsulta.tamanoSubconjunto = grid.getCount();
		}
		
		if (confirm("Se asignarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
			VisitaPuntoVentaDistribuidorDWR.crearVisitasPorSubLotes(
				distribuidor,
				observaciones,
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

function inputRecalcularPorcentajesOnClick() {
	LiquidacionDWR.calcularPorcentajeActivacionSubLotes(
		{
			callback: function(data) {
				alert("Operación exitosa");
				
				reloadData();
			}, async: false
		}
	);
}
