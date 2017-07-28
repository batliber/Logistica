var __ROL_ADMINISTRADOR = 1;
var __ROL_SUPERVISOR_DISTRIBUCION = 7;

var chips = {
	cantidadRegistros: 0,
	registrosMuestra: []
};

var grid = null;

$(document).ready(init);

function init() {
	refinarForm();
	
	UsuarioRolEmpresaDWR.listDistribuidoresByContext(
		{
			callback: function(data) {
				if (data != null) {
					var html =
						"<option id='0' value='0'>Seleccione...</option>";
					
					for (var i=0; i<data.length; i++) {
						html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
					}
					
					$("#selectDistribuidor").append(html);
				}
			}, async: false
		}
	);
	
	DepartamentoDWR.list(
		{
			callback: function(data) {
				if (data != null) {
					var html =
						"<option id='0' value='0'>Seleccione...</option>";
					
					for (var i=0; i<data.length; i++) {
						html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
					}
					
					$("#selectPuntoVentaDepartamento").append(html);
				}
			}, async: false
		}
	);
	
	BarrioDWR.list(
		{
			callback: function(data) {
				if (data != null) {
					var html =
						"<option id='0' value='0'>Seleccione...</option>";
					
					for (var i=0; i<data.length; i++) {
						html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
					}
					
					$("#selectPuntoVentaBarrio").append(html);
				}
			}, async: false
		}
	);
	
	PuntoVentaDWR.list(
		{
			callback: function(data) {
				if (data != null) {
					var html =
						"<option id='0' value='0'>Seleccione...</option>";
					
					for (var i=0; i<data.length; i++) {
						html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
					}
					
					$("#selectPuntoVenta").append(html);
				}
			}, async: false
		}
	);
	
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
		17
	);
	
	grid.rebuild();
	
	if (id != null) {
		ActivacionSubloteDWR.getById(
			id,
			{
				callback: function(data) {
					$("#divNumero").text(data.numero);

					if (data.fechaAsignacionDistribuidor != null) {
						$("#divFechaAsignacionDistribuidor").text(formatLongDate(data.fechaAsignacionDistribuidor));
						$("#divFechaAsignacionDistribuidor").attr("d", data.fechaAsignacionDistribuidor.getTime());
					}
					
					if (data.fechaAsignacionPuntoVenta != null) {
						$("#divFechaAsignacionPuntoVenta").text(formatLongDate(data.fechaAsignacionPuntoVenta));
						$("#divFechaAsignacionPuntoVenta").attr("d", data.fechaAsignacionPuntoVenta.getTime());
					}
					
					if (data.empresa != null) {
						$("#divEmpresa").text(data.empresa.nombre);
						$("#divEmpresa").attr("eid", data.empresa.id);
					}
					
					if (data.distribuidor != null) {
						$("#selectDistribuidor").val(data.distribuidor.id);
					}
					
					if (data.puntoVenta != null) {
						if (data.puntoVenta.departamento != null) {
							$("#selectPuntoVentaDepartamento").val(data.puntoVenta.departamento.id);
						}
						if (data.puntoVenta.barrio != null) {
							$("#selectPuntoVentaBarrio").val(data.puntoVenta.barrio.id);
							
							selectPuntoVentaBarrioOnChange();
						}
						
						$("#selectPuntoVenta").val(data.puntoVenta.id);
					}
					
					chips.cantidadRegistros = data.activaciones.length;
					for (var i=0; i<data.activaciones.length; i++) {
						chips.registrosMuestra[chips.registrosMuestra.length] = data.activaciones[i];
					}
					
					reloadGrid();
				}, async: false
			}
		);
	}
}

function refinarForm() {
	$("#divButtonImprimir").hide();
	
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_NEW) {
		
	}
	
	$("#divButtonImprimir").show();
	
	$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
}

function listEmpresas() {
	EmpresaDWR.list(
		{
			callback: function(data) {
				return data;
			}, async: false
		}
	);
}

function listEstadoActivaciones() {
	EstadoActivacionDWR.list(
		{
			callback: function(data) {
				return data;
			}, async: false
		}
	);
}

function listTipoActivaciones() {
	TipoActivacionDWR.list(
		{
			callback: function(data) {
				return data;
			}, async: false
		}
	);
}

function selectPuntoVentaDepartamentoOnChange(event, element) {
	$("#selectPuntoVentaBarrio > option:gt(0)").remove();
	$("#selectPuntoVenta > option:gt(0)").remove();
	
	if ($("#selectPuntoVentaDepartamento").val() != "0") {
		BarrioDWR.listByDepartamentoId(
			$("#selectPuntoVentaDepartamento").val(),
			{
				callback: function(data) {
					if (data != null) {
						var html = "";
						
						for (var i=0; i<data.length; i++) {
							html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
						}
						
						$("#selectPuntoVentaBarrio").append(html);
					}
					
					selectPuntoVentaBarrioOnChange();
				}, async: false
			}
		);
	}
}

function selectPuntoVentaBarrioOnChange(event, element) {
	$("#selectPuntoVenta > option:gt(0)").remove();
	
	if ($("#selectPuntoVentaBarrio").val() != "0") {
		PuntoVentaDWR.listByBarrioId(
			$("#selectPuntoVentaBarrio > option:selected").val(),
			{
				callback: function(data) {
					var html = "";
					
					for (var i=0; i<data.length; i++) {
						html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
					}
					
					$("#selectPuntoVenta").append(html);
				}, async: false
			}
		);
	} else if ($("#selectPuntoVentaDepartamento").val() != "0") {
		PuntoVentaDWR.listByDepartamentoId(
			$("#selectPuntoVentaDepartamento > option:selected").val(),
			{
				callback: function(data) {
					var html = "";
					
					for (var i=0; i<data.length; i++) {
						html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
					}
					
					$("#selectPuntoVenta").append(html);
				}, async: false
			}
		);
	} else {
		PuntoVentaDWR.list(
			{
				callback: function(data) {
					var html = "";
					
					for (var i=0; i<data.length; i++) {
						html += "<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
					}
					
					$("#selectPuntoVenta").append(html);
				}, async: false
			}
		);
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
		registros.registrosMuestra[registros.registrosMuestra.length] = {
			id: ordered[i].id,
			mid: ordered[i].mid,
			chip: ordered[i].chip,
			empresa: ordered[i].empresa,
			estadoActivacion: ordered[i].estadoActivacion,
			tipoActivacion: ordered[i].tipoActivacion
		};
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
		
		ActivacionDWR.getLastByChip(
			val,
			{
				callback: function(data) {
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
				}, async: false
			}
		);
	}
}

function inputGuardarOnClick(event) {
	var empresaId = $("#divEmpresa").attr("eid");
	if (empresaId == 0) {
		alert("Debe seleccionar una empresa.");
		return;
	}
	
	var activacionSublote = {
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
		
		ActivacionSubloteDWR.update(
			activacionSublote,
			{
				callback: function(data) {
					alert("Operación exitosa.");
					
					window.parent.closeDialog();
				}, async: false
			}
		);
	} else {
		ActivacionSubloteDWR.add(
			activacionSublote,
			{
				callback: function(data) {
					if (data == null) {
						alert("No se ha podido completar la operación.");
						return;
					}
					
					alert("Se ha creado el sub-lote nro: " + data);
					
					window.parent.closeDialog();
				}, async: false
			}
		);
	}
}

function inputImprimirOnClick(event) {
	window.open("/LogisticaWEB/pages/activaciones_sublote/activaciones_sublotes_print.jsp?sid=" + id);
}