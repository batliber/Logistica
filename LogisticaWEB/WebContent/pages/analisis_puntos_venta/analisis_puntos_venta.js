var __ROL_ADMINISTRADOR = 1;
var __ROL_DEMO = 21;

var grid = null;

$(document).ready(init);

function init() {
	$("#divButtonAsignarVisitas").hide();
	$("#divButtonVisitasPermanentes").hide();
	$("#divButtonRecalcularPorcentajes").hide();
	$("#divButtonExportarAExcel").hide();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
    }).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ADMINISTRADOR
				|| data.usuarioRolEmpresas[i].rol.id == __ROL_DEMO) {
				$("#divButtonAsignarVisitas").show();
				$("#divButtonVisitasPermanentes").show();
				$("#divButtonRecalcularPorcentajes").show();
				$("#divButtonExportarAExcel").show();
				
				grid = new Grid(
					document.getElementById("divTablePuntosVenta"),
					{
						tdNombre: { campo: "puntoVenta.nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 200 },
						tdDepartamento: { campo: "puntoVenta.departamento.nombre", clave: "puntoVenta.departamento.id", descripcion: "Departamento", abreviacion: "Depto.", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDepartamentos, clave: "id", valor: "nombre"} },
						tdBarrio: { campo: "puntoVenta.barrio.nombre", clave: "puntoVenta.barrio.id", descripcion: "Barrio", abreviacion: "Barrio", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBarrios, clave: "id", valor: "nombre"} },
						tdEstado: { campo: "puntoVenta.estadoPuntoVenta.nombre", clave: "puntoVenta.estadoPuntoVenta.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoPuntoVentas, clave: "id", valor: "nombre"}, ancho: 70 },
						tdPorcentajeActivacion: { campo: "porcentajeActivacion", descripcion: "Porcentaje activación", abreviacion: "% act.", tipo: __TIPO_CAMPO_PORCENTAJE, decimales: 1 },
						tdFechaCalculoPorcentajeActivacion: { campo: "fechaCalculo", descripcion: "Fecha cálculo % activación", abreviacion: "F. calc. % act.", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdFechaLiquidacion: { campo: "fechaLiquidacion", descripcion: "Fecha de liquidación", abreviacion: "F. liq.", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdDistribuidor: { campo: "puntoVenta.distribuidor.nombre", clave: "puntoVenta.distribuidor.id", descripcion: "Distribuidor", abreviacion: "Distribuidor", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listDistribuidoresChips, clave: "id", valor: "nombre" }, ancho: 90 },
						tdFechaAsignacion: { campo: "puntoVenta.fechaAsignacionDistribuidor", descripcion: "Asignado distribuidor", abreviacion: "F. asign. distr.", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdFechaVisita: { campo: "puntoVenta.fechaVisitaDistribuidor", descripcion: "Visitado distribuidor", abreviacion: "F. visit. distr.", tipo: __TIPO_CAMPO_FECHA_HORA },
						tdEstadoVisita: { campo: "puntoVenta.estadoVisitaPuntoVentaDistribuidor.nombre", clave: "puntoVenta.estadoVisitaPuntoVentaDistribuidor.id", descripcion: "Estado última visita", abreviacion: "Estado últ. visita", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoVisitaPuntoVentaDistribuidores, clave: "id", valor: "nombre"}, ancho: 100 },
						tdFechaUltimoCambioEstadoVisita: { campo: "puntoVenta.fechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor", descripcion: "Fecha último cambio estado visita", abreviacion: "F. últ. camb. est. visit.", tipo: __TIPO_CAMPO_FECHA_HORA, ancho: 120 }
					}, 
					true,
					reloadData,
					trPuntoVentaOnClick
				);
				
				grid.rebuild();
				
				$("#divButtonTitleSingleSize").attr("id", "divButtonTitleQuintupleSize");
				break;
			}
		}
		
		if (grid == null) {
			grid = new Grid(
				document.getElementById("divTablePuntosVenta"),
				{
					tdNombre: { campo: "puntoVenta.nombre", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 200 }
				}, 
				true,
				reloadData,
				trPuntoVentaOnClick
			);
			
			grid.rebuild();
		}
		
		reloadData();
		
		$("#divIFramePuntoVenta").draggable();
		$("#divIFrameSeleccionDistribuidor").draggable();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/CalculoPorcentajeActivacionPuntoVentaREST/listContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/CalculoPorcentajeActivacionPuntoVentaREST/countContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.setCount(data);
    });
}

function trPuntoVentaOnClick() {
	
}

function inputActualizarOnClick() {
	reloadData();
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

	showPopUp(document.getElementById("divIFrameSeleccionDistribuidor"));
}

function divCloseOnClick(event, element) {
	closePopUp(event, element.parentNode.parentNode);
	
	$("#inputVisitasPermanentes").attr("checked", false);
	$("#selectDistribuidor").val("0");
	$("#textareaObservaciones").val("");
	
	reloadData();
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
		        url: "/LogisticaWEB/RESTFacade/VisitaPuntoVentaDistribuidorREST/crearVisitasPermanentes",
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
		        url: "/LogisticaWEB/RESTFacade/VisitaPuntoVentaDistribuidorREST/crearVisitas",
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

function inputRecalcularPorcentajesOnClick() {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/LiquidacionREST/calcularPorcentajeActivacionPuntoVentas",   
    }).then(function(data) {
		alert("Operación exitosa");
		
		reloadData();
	});
}

function inputExportarAExcelOnClick(event, element) {
	var metadataConsulta = grid.filtroDinamico.calcularMetadataConsulta();
	
	if (confirm("Se exportarán " + metadataConsulta.tamanoSubconjunto + " registros.")) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/CalculoPorcentajeActivacionPuntoVentaREST/exportarAExcel",
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