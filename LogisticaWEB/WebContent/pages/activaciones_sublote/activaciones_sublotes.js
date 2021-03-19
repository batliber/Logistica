var __ROL_ADMINISTRADOR = 1;
var __ROL_ENCARGADO_ACTIVACIONES = 12;
var __ROL_SUPERVISOR_DISTRIBUCION_CHIPS = 18;
var __ROL_DEMO = 21;

var grid = null;

$(document).ready(init)

function init() {
	$("#divButtonNuevo").hide();
	$("#divButtonRecalcularPorcentajes").hide();
	$("#divButtonRecalcularFechasVencimientoChipMasViejo").hide();
	$("#divButtonAsignarVisitas").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
				$("#divButtonNuevo").show();
				$("#divButtonRecalcularPorcentajes").show();
				$("#divButtonRecalcularFechasVencimientoChipMasViejo").show();
				$("#divButtonAsignarVisitas").show();
				
				grid = new Grid(
					document.getElementById("divTableActivacionesSublotes"),
					{
						tdNumero: { campo: "numero", descripcion: "Número", abreviacion: "Número", tipo: __TIPO_CAMPO_NUMERICO, ancho: 70 },
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 200 },
						tdDescripcion: { campo: "descripcion", abreviacion: "Descripción", descripcion: "Descripción", tipo: __TIPO_CAMPO_STRING, ancho: 250 },
						tdDistribuidor: { campo: "distribuidor.nombre", clave: "distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidores, clave: "id", valor: "nombre" }, ancho: 200 },
						tdFechaAsignacionDistribuidor: { campo: "fechaAsignacionDistribuidor", abreviacion: "F. Asign. Dis.", descripcion: "Fecha de asign. Distribuidor", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdFechaVencimientoChipMasViejo: { campo: "fechaVencimientoChipMasViejo", abreviacion: "F. Venc. Chip. más viejo", descripcion: "Fecha de vencimiento de chip más viejo", tipo: __TIPO_CAMPO_FECHA, ancho: 100 },
						tdPuntoVenta: { campo: "puntoVenta.nombre", clave: "puntoVenta.id", descripcion: "Punto de venta", abreviacion: "Pto. venta", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPuntoVentas, clave: "id", valor: "nombre" }, ancho: 200 },
						tdFechaAsignacionPuntoVenta: { campo: "fechaAsignacionPuntoVenta", abreviacion: "F. Asign. P.V.", descripcion: "Fecha de asign. Pto. venta", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdPorcentajeActivacion: { campo: "porcentajeActivacion", descripcion: "Porcentaje activación", abreviacion: "% act.", tipo: __TIPO_CAMPO_PORCENTAJE, decimales: 1 },
					}, 
					true,
					reloadData,
					trActivacionSubloteOnClick
				);
				
				grid.rebuild();
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleQuintupleSize");
				break;
			}
		}
		
		if (grid == null) {
			for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
				if (data.usuarioRolEmpresas[i].rol.id == __ROL_ENCARGADO_ACTIVACIONES
					|| data.usuarioRolEmpresas[i].rol.id == __ROL_SUPERVISOR_DISTRIBUCION_CHIPS) {
					$("#divButtonNuevo").show();
					
					grid = new Grid(
						document.getElementById("divTableActivacionesSublotes"),
						{
							tdNumero: { campo: "numero", descripcion: "Número", abreviacion: "Número", tipo: __TIPO_CAMPO_NUMERICO },
							tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 200 },
							tdDescripcion: { campo: "descripcion", abreviacion: "Descripción", descripcion: "Descripción", tipo: __TIPO_CAMPO_STRING, ancho: 250 },
							tdDistribuidor: { campo: "distribuidor.nombre", clave: "distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidores, clave: "id", valor: "nombre" }, ancho: 200 },
							tdFechaAsignacionDistribuidor: { campo: "fechaAsignacionDistribuidor", abreviacion: "F. Asign. Distr.", descripcion: "Fecha de asign. Distribuidor", tipo: __TIPO_CAMPO_FECHA_HORA },
							tdFechaVencimiento: { campo: "fechaVencimiento", abreviacion: "F. Venc.", descripcion: "Fecha de vencimiento", tipo: __TIPO_CAMPO_FECHA_HORA }
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
		
		reloadData();

		$("#divIFrameActivacionSublote").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ActivacionSubloteREST/listContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ActivacionSubloteREST/countContextAware",
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

function trActivacionSubloteOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFrameActivacionSublote").src = 
		"/LogisticaWEB/pages/activaciones_sublote/activaciones_sublotes_edit.jsp?m=" 
		+ formMode + "&id=" + $(target).attr("id");
	
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
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listDistribuidoresChipsByContextMinimal"
	}).then(function(data) {
		fillSelect(
			"selectDistribuidor",
			data,
			"id",
			"nombre"
		);
	});
	
	showPopUp(document.getElementById("divIFrameSeleccionDistribuidor"));
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameSeleccionDistribuidor"));
	
	$("#selectDistribuidor").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
}

function inputAceptarOnClick(event, element) {
	if ($("#selectDistribuidor").val() == "0") {
		alert("Debe seleccionar un distribuidor.");
		
		return;
	}
		
	var distribuidor = {
		id: $("#selectDistribuidor").val()
	};
	
	var observaciones = $("#textareaObservaciones").val();
	
	metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
		metadataConsulta.tamanoSubconjunto = grid.getCount();
	}
	
	if (confirm("Se asignarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/VisitaPuntoVentaDistribuidorREST/crearVisitasPorSubLotes",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify({
				"distribuidorId": distribuidor.id,
				"observaciones": observaciones,
				"metadataConsulta": metadataConsulta
			})
		}).then(function(data) {
			alert("Operación exitosa.");
			
			reloadData();
		});
	}
}

function inputRecalcularPorcentajesOnClick() {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/LiquidacionREST/calcularPorcentajeActivacionSubLotes",
	}).then(function(data) {
		alert("Operación exitosa");
		
		reloadData();
	});
}

function inputRecalcularFechasVencimientoChipMasViejoOnClick(event, element) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ActivacionSubloteREST/calcularFechasVencimientoChipMasViejo",
	}).then(function(data) {
		alert("Operación exitosa");
		
		reloadData();
	});
}