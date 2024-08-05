var __ROL_ADMINISTRADOR = 1;
var __ROL_ENCARGADO_ACTIVACIONES = 12;
var __ROL_DEMO = 21;

var grid = null;
var map = null;

$(document).ready(init);

function init() {
	$("#divButtonNew").hide();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
	}).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_ENCARGADO_ACTIVACIONES
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
				mode = __FORM_MODE_ADMIN;
				$("#divButtonNew").show();
				
				grid = new Grid(
					document.getElementById("divTablePuntosVenta"),
					{
						tdId: { campo: "id", descripcion: "Id", abreviacion: "Id", tipo: __TIPO_CAMPO_NUMERICO },
						tdNombre: { campo: "nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 200 },
						tdTelefono: { campo: "telefono", descripcion: "Teléfono", abreviacion: "Tel.", tipo: __TIPO_CAMPO_STRING, ancho: 100, oculto: true },
						tdContacto: { campo: "contacto", descripcion: "Contacto", abreviacion: "Contacto", tipo: __TIPO_CAMPO_STRING, ancho: 150, oculto: true },
						tdDepartamento: { campo: "departamento.nombre", clave: "departamento.id", descripcion: "Departamento", abreviacion: "Depto.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre"} },
						tdBarrio: { campo: "barrio.nombre", clave: "barrio.id", descripcion: "Barrio", abreviacion: "Barrio", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBarrios, clave: "id", valor: "nombre"} },
						tdEstado: { campo: "estadoPuntoVenta.nombre", clave: "estadoPuntoVenta.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoPuntoVentas, clave: "id", valor: "nombre"}, ancho: 100 },
						tdFechaBaja: { campo: "fechaBaja", descripcion: "Eliminado", abreviacion: "Eliminado", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdDistribuidor: { campo: "distribuidor.nombre", clave: "distribuidor.id", descripcion: "Distribuidor chips", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidoresChips, clave: "id", valor: "nombre"} },
						tdFechaAsignacion: { campo: "fechaAsignacionDistribuidor", descripcion: "Asignado distribuidor", abreviacion: "F. asign. distr.", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdFechaVisita: { campo: "fechaVisitaDistribuidor", descripcion: "Visitado distribuidor", abreviacion: "F. visit. distr.", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdEstadoVisita: { campo: "estadoVisitaPuntoVentaDistribuidor.nombre", clave: "estadoVisitaPuntoVentaDistribuidor.id", descripcion: "Estado última visita", abreviacion: "Estado últ. visita", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoVisitaPuntoVentaDistribuidores, clave: "id", valor: "nombre"}, ancho: 100 },
						tdFechaUltimoCambioEstadoVisita: { campo: "fechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor", descripcion: "Fecha último cambio estado visita", abreviacion: "F. últ. camb. est. visit.", tipo: __TIPO_CAMPO_FECHA_HORA, ancho: 120 },
						tdFechaVencimientoChipMasViejo: { campo: "fechaVencimientoChipMasViejo", descripcion: "Fecha vencimiento chip más antiguo", abreviacion: "F. venc. chip más viejo.", tipo: __TIPO_CAMPO_FECHA_HORA, ancho: 120 },
						tdCreador: { campo: "creador.nombre", clave: "creador.id", descripcion: "Creado por", abreviacion: "Creado por", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidoresChips, clave: "id", valor: "nombre"} },
						tdFcre: { campo: "fcre", descripcion: "Creado", abreviacion: "Creado", tipo: __TIPO_CAMPO_FECHA_HORA }
					},
					true,
					reloadData,
					trPuntoVentaOnClick
				);
				
				grid.rebuild();
				
				grid.filtroDinamico.agregarFiltroManual(
					{
						campo: "fechaBaja",
						operador: "nl",
						valores: []
					},
					false
				);
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				break;
			}
		}
		
		$("#divIFramePuntoVenta").draggable();
		
		reloadData();
	});
}

function initMap() {
	var divMap = document.getElementById("divMapaPuntosVenta");
	map = new google.maps.Map(
		divMap, {
			center: {lat: 0, lng: 0},
			zoom: 8
		}
	);
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/listContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		initMap();
		
		var markerBounds = new google.maps.LatLngBounds();
		
		for (var i=0; i<data.registrosMuestra.length; i++) {
			if (data.registrosMuestra[i].latitud != null && data.registrosMuestra[i].longitud != null) {
				var latlng = {
					lat: data.registrosMuestra[i].latitud, lng: data.registrosMuestra[i].longitud
				};
				
				var marker = new google.maps.Marker({
					position: latlng,
					map: map,
					title: data.registrosMuestra[i].nombre
				});
				
				markerBounds.extend(latlng);
			}
		}
		
		map.fitBounds(markerBounds);
		
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/countContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) { 
		grid.setCount(data);
	});
}

function trPuntoVentaOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var formMode = __FORM_MODE_ADMIN;
	
	document.getElementById("iFramePuntoVenta").src = 
		"./punto_venta_edit.jsp?m=" + formMode + "&id=" + $(target).attr("id");
	showPopUp(document.getElementById("divIFramePuntoVenta"));
}

function inputActualizarOnClick() {
	reloadData();
}

function inputNewOnClick(event, element) {
	document.getElementById("iFramePuntoVenta").src = "./punto_venta_edit.jsp";
	showPopUp(document.getElementById("divIFramePuntoVenta"));
}

function inputAsignarVisitasOnClick() {
	metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
		metadataConsulta.tamanoSubconjunto = grid.getCount();
	}
	
	$("#inputVisitasPermanentes").attr("checked", false);
	
	listDistribuidoresChips()
		.then(function(data) {
			fillSelect(
				"selectDistribuidor",
				data,
				"id",
				"nombre"
			);
			
			showPopUp(document.getElementById("divIFrameSeleccionDistribuidor"));
		});
}

function inputVisitasPermanentesOnClick() {
	metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	if (metadataConsulta.tamanoSubconjunto > grid.getCount()) {
		metadataConsulta.tamanoSubconjunto = grid.getCount();
	}
	
	$("#inputVisitasPermanentes").attr("checked", true);
	
	listDistribuidoresChips()
		.then(function(data) {
			fillSelect(
				"selectDistribuidor",
				data,
				"id",
				"nombre"
			);
			
			showPopUp(document.getElementById("divIFrameSeleccionDistribuidor"));
		});
}

function inputCancelarOnClick(event, element) {
	closePopUp(event, document.getElementById("divIFrameSeleccionDistribuidor"));
	
	$("#inputVisitasPermanentes").attr("checked", false);
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
		if ($("#inputVisitasPermanentes").attr("checked")) {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/crearVisitasPermanentes",
				method: "POST",
				contentType: 'application/json',
				data: JSON.stringify({
					"distribuidor": distribuidor,
					"observaciones": observaciones,
					"metadataConsulta": metadataConsulta
				})
			}).then(function(data) { 
				alert("Operación exitosa.");
				
				reloadData();
			});
		} else {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/crearVisitas",
				method: "POST",
				contentType: 'application/json',
				data: JSON.stringify({
					"distribuidor": distribuidor,
					"observaciones": observaciones,
					"metadataConsulta": metadataConsulta
				})
			}).then(function(data) { 
				alert("Operación exitosa.");
				
				reloadData();
			});
		}
	}
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	reloadData();
}

function inputExportarAExcelOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/exportarAExcel",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(metadataConsulta)
		}).then(function(data) {
			if (data != null) {
				document.getElementById("formExportarAExcel").action = 
					"/LogisticaWEB/Download?fn=" + data.nombreArchivo;
				
				document.getElementById("formExportarAExcel").submit();
			}
		});
	}
}