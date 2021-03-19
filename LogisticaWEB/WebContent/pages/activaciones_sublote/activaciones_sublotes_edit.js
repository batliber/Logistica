var __ROL_ADMINISTRADOR = 1;
var __ROL_ENCARGADO_ACTIVACIONES = 12;
var __ROL_SUPERVISOR_DISTRIBUCION_CHIPS = 12456792;

var chips = {
	cantidadRegistros: 0,
	registrosMuestra: []
};

var grid = null;

$(document).ready(init);

function init() {
	refinarForm();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listDistribuidoresByContextMinimal"
    }).then(function(data) {
    	fillSelect(
    		"selectDistribuidor", 
    		data, 
    		"id", 
    		"nombre"
    	);
    }).then(function(data) {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/DepartamentoREST/list"
	    }).then(function(data) {
	    	fillSelect(
	    		"selectPuntoVentaDepartamento", 
	    		data, 
	    		"id", 
	    		"nombre"
	    	);
	    }).then(function(data) {
			$.ajax({
		        url: "/LogisticaWEB/RESTFacade/BarrioREST/listMinimal"
		    }).then(function(data) {
		    	fillSelect(
		    		"selectPuntoVentaBarrio", 
		    		data, 
		    		"id", 
		    		"nombre"
		    	);
		    }).then(function(data) {
				/*
				$.ajax({
			        url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/listMinimal"
			    }).then(function(data) { 
			    	fillSelect(
			    		"selectPuntoVenta", 
			    		data, 
			    		"id", 
			    		"nombre"
			    	);
			    });
				*/
				
				grid = new Grid(
					document.getElementById("divTableActivaciones"),
					{
						tdMID: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO, ancho: 90 },
						tdChip: { campo: "chip", descripcion: "Chip", abreviacion: "Chip", tipo: __TIPO_CAMPO_STRING, ancho: 150 },
						tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 150 },
						tdEstado: { campo: "estadoActivacion.nombre", clave: "estadoActivacion.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoActivaciones, clave: "id", valor: "nombre" }, ancho: 150 },
						tdTipo: { campo: "tipoActivacion.descripcion", clave: "tipoActivacion.id", descripcion: "Tipo", abreviacion: "Tipo", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listTipoActivaciones, clave: "id", valor: "descripcion" }, ancho: 150 },
					},
					false,
					reloadGrid,
					trActivacionOnClick,
					null,
					13
				);
				
				grid.rebuild();
				
				if (id != null) {
					$.ajax({
				        url: "/LogisticaWEB/RESTFacade/ActivacionSubloteREST/getById/" + id
				    }).then(function(data) {
						$("#divNumero").text(data.numero);
						$("#inputDescripcion").val(data.descripcion);
			
						if (data.fechaAsignacionDistribuidor != null) {
							$("#divFechaAsignacionDistribuidor").text(formatLongDate(data.fechaAsignacionDistribuidor));
							$("#divFechaAsignacionDistribuidor").attr("d", data.fechaAsignacionDistribuidor);
						}
						
						if (data.fechaAsignacionPuntoVenta != null) {
							$("#divFechaAsignacionPuntoVenta").text(formatLongDate(data.fechaAsignacionPuntoVenta));
							$("#divFechaAsignacionPuntoVenta").attr("d", data.fechaAsignacionPuntoVenta);
						}
						
						if (data.empresa != null) {
							$("#divEmpresa").text(data.empresa.nombre);
							$("#divEmpresa").attr("eid", data.empresa.id);
						}
						
						if (data.distribuidor != null) {
							fillSelect(
								"selectDistribuidor", 
								[data.distribuidor], 
								"id", 
								"nombre", 
								null, null, null, null, 
								true
							);
							
							$("#selectDistribuidor").val(data.distribuidor.id);
						}
						
						if (data.puntoVenta != null) {
							if (data.puntoVenta.departamento != null) {
								$("#selectPuntoVentaDepartamento").val(data.puntoVenta.departamento.id);
							}
							if (data.puntoVenta.barrio != null) {
								fillSelect(
									"selectPuntoVentaBarrio", 
									[data.puntoVenta.barrio], 
									"id", 
									"nombre", 
									null, null, null, null, 
									true
								);
								
								$("#selectPuntoVentaBarrio").val(data.puntoVenta.barrio.id);
							}
							
							fillSelect(
								"selectPuntoVenta", 
								[data.puntoVenta], 
								"id", 
								"nombre", 
								null, null, null, null, 
								true
							);
							
							$("#selectPuntoVenta").val(data.puntoVenta.id);
						}
						
						chips.cantidadRegistros = data.activaciones.length;
						for (var i=0; i<data.activaciones.length; i++) {
							chips.registrosMuestra[chips.registrosMuestra.length] = data.activaciones[i];
						}
						
						reloadGrid();
						
						$("#divButtonImprimir").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				    });
				}
		    });
	    });
    });
}

function refinarForm() {
	$("#divButtonImprimir").hide();
	
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_NEW) {
		
	}
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
    }).then(function(data) {
		for (var i=0; i<data.usuarioRolEmpresas.length; i++) {
			if (data.usuarioRolEmpresas[i].rol.id == __ROL_ENCARGADO_ACTIVACIONES) {
				hideField("puntoVentaDepartamento");
				hideField("puntoVentaBarrio");
				hideField("puntoVenta");
				hideField("fechaAsignacionPuntoVenta");
				
				break;
			}
		}
	});
}

function selectPuntoVentaDepartamentoOnChange(event, element) {
	fillSelect("selectPuntoVentaBarrio", [], "id", "nombre");
	fillSelect("selectPuntoVenta", [], "id", "nombre");
	
	if ($("#selectPuntoVentaDepartamento").val() != "0") {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/BarrioREST/listMinimalByDepartamentoId/"
	        	+ $("#selectPuntoVentaDepartamento").val()
	    }).then(function(data) { 
	    	fillSelect(
	    		"selectPuntoVentaBarrio", 
	    		data, 
	    		"id", 
	    		"nombre"
	    	);
	    	
	    	selectPuntoVentaBarrioOnChange();
	    });
	}
}

function selectPuntoVentaBarrioOnChange(event, element) {
	fillSelect("selectPuntoVenta", [], "id", "nombre");
	
	if ($("#selectPuntoVentaBarrio").val() != "0") {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/listMinimalByBarrioId/"
	        	+ $("#selectPuntoVentaBarrio").val()
	    }).then(function(data) {
	    	fillSelect(
	    		"selectPuntoVenta", 
	    		data, 
	    		"id", 
	    		"nombre"
	    	);
	    });
	} else if ($("#selectPuntoVentaDepartamento").val() != "0") {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/listMinimalByDepartamentoId/"
	        	+ $("#selectPuntoVentaDepartamento").val()
	    }).then(function(data) {
	    	fillSelect(
	    		"selectPuntoVenta", 
	    		data, 
	    		"id", 
	    		"nombre"
	    	);
	    });
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/PuntoVentaREST/listMinimal"
	    }).then(function(data) {
	    	fillSelect(
	    		"selectPuntoVenta",
	    		data, 
	    		"id", 
	    		"nombre"
	    	);
	    });
	}
}

function reloadGrid() {
	var ordered = chips.registrosMuestra;
	
	var ordenaciones = grid.filtroDinamico.calcularOrdenaciones();
	if (ordenaciones.length > 0 && ordered != null) {
		ordered = ordered.sort(function(a, b) {
			var result = 0;
			
			for (var i=0; i<ordenaciones.length; i++) {
				var aValue = null;
				try {
					aValue = eval("a." + ordenaciones[i].campo);
			    } catch(e) {
			        aValue = null;
			    }
			    
			    var bValue = null;
			    try {
					bValue = eval("b." + ordenaciones[i].campo);
			    } catch(e) {
			        bValue = null;
			    }
				
				if (aValue < bValue) {
					result = -1 * (ordenaciones[i].ascendente ? 1 : -1);
					
					break;
				} else if (aValue > bValue) {
					result = 1 * (ordenaciones[i].ascendente ? 1 : -1);
					
					break;
				}
			}
			
			return result;
		});
	}
	
	var registros = {
		cantidadRegistros: chips.cantidadRegistros,
		registrosMuestra: []
	};
	for (var i=0; i<ordered.length; i++) {
		registros.registrosMuestra[registros.registrosMuestra.length] = ordered[i];
	}
	
	grid.reload(registros);
}

function trActivacionOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var imei = $(target).children("[campo='tdChip']").html();
	
	var i=0;
	for (i=0; i<chips.registrosMuestra.length; i++) {
		if (chips.registrosMuestra[i].chip == imei) {
			break;
		}
	}
	
	chips.cantidadRegistros = chips.cantidadRegistros - 1;
	chips.registrosMuestra.splice(i, 1);
	
	grid.reload(chips);
	$("#inputCantidad").val(chips.cantidadRegistros);
}

function inputChipOnChange(event, element) {
	var val = $("#inputChip").val();
	if (val != null && val.trim() != "") {
		val = val.trim();
		
		for (var i=0; i<chips.registrosMuestra.length; i++) {
			if (chips.registrosMuestra[i].chip == val) {
				alert("El Chip ya fue ingresado.");
				
				$("#inputChip").val(null);
				
				return;
			}
		}
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ActivacionREST/getLastByChip/" + val
	    }).then(function(data) {
			if (data != null) {
				if (chips.cantidadRegistros > 0 
					&& chips.registrosMuestra[0].empresa.id != data.empresa.id
				) {
					alert("El chip ingresado fue activado por otra Empresa");
				} else {
					$("#divEmpresa").text(data.empresa.nombre);
					$("#divEmpresa").attr("eid", data.empresa.id);
					
					chips.cantidadRegistros = chips.cantidadRegistros + 1;
					chips.registrosMuestra[chips.registrosMuestra.length] = {
						id: data.id,
						mid: data.mid,
						chip: data.chip,
						empresa: data.empresa,
						tipoActivacion: data.tipoActivacion,
						estadoActivacion: data.estadoActivacion
					};
					
					reloadGrid();
				}
			} else {
				alert("No se encuentra el Chip ingresado.")
			}
			
			$("#inputChip").val(null);
		});
	}
}

function inputGuardarOnClick(event) {
	var empresaId = $("#divEmpresa").attr("eid");
	if (empresaId == 0) {
		alert("Debe seleccionar una empresa.");
		return;
	}
	
	var activacionSublote = {
		descripcion: $("#inputDescripcion").val(),
		empresa: {
			id: empresaId
		}
	};
	
	var distribuidorId = $("#selectDistribuidor").val();
	if (distribuidorId > 0) {
		activacionSublote.distribuidor = {
			id: distribuidorId
		};
	}
//	else {
//		alert("Debe seleccionar un distribuidor.");
//		return;
//	}
	
	var puntoVentaId = $("#selectPuntoVenta").val();
	if (puntoVentaId > 0) {
		activacionSublote.puntoVenta = {
			id: puntoVentaId
		};
	}
//	else {
//		alert("Debe seleccionar un Punto de venta.");
//		return;
//	}
	
	if (chips.cantidadRegistros == 0) {
		alert("Debe ingresar al menos un Chip.");
		return;
	}
	
	activacionSublote.activaciones = [];
	for (var i=0; i<chips.registrosMuestra.length;i++) {
		activacionSublote.activaciones[activacionSublote.activaciones.length] = {
			id: chips.registrosMuestra[i].id
		}
	}
	
	if (id != null) {
		activacionSublote.id = id;
		activacionSublote.numero = $("#divNumero").text();
		
		var fechaAsignacionDistribuidor = $("#divFechaAsignacionDistribuidor").attr("d");
		if (fechaAsignacionDistribuidor != null && fechaAsignacionDistribuidor != "") {
			activacionSublote.fechaAsignacionDistribuidor = fechaAsignacionDistribuidor;
		}
		
		var fechaAsignacionPuntoVenta = $("#divFechaAsignacionPuntoVenta").attr("d");
		if (fechaAsignacionPuntoVenta != null && fechaAsignacionPuntoVenta != "") {
			activacionSublote.fechaAsignacionPuntoVenta = fechaAsignacionPuntoVenta;
		}
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ActivacionSubloteREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(activacionSublote)
	    }).then(function(data) {
	    	alert("Operación exitosa");
	    	
	    	window.parent.closeDialog();
	    });
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/ActivacionSubloteREST/add",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(activacionSublote)
	    }).then(function(data) {
	    	if (data != null) {
				alert("Operación exitosa");
	    	
				alert("Se ha creado el sub-lote nro: " + data.numero);
				
				window.parent.closeDialog();
	    	} else {
	    		alert("Error en la operación");
	    	}
	    });
	}
}

function inputImprimirOnClick(event) {
	window.open("/LogisticaWEB/pages/activaciones_sublote/activaciones_sublotes_print.jsp?sid=" + id);
}