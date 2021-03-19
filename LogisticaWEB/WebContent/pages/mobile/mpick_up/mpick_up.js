var grid = null;

$(document).ready(init);

function init() {
	$("#divTitle").append("Pick-up");
	
	grid = new Grid(
		document.getElementById("divTableContratos"),
		{
			tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
			tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 80 },
			tdEstado: { campo: "estado.nombre", clave: "estado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoANTELs, clave: "id", valor: "nombre" }, ancho: 100 },
			tdFechaPickUp: { campo: "fechaPickUp", descripcion: "Fecha de Pick-up", abreviacion: "Pick-Up", tipo: __TIPO_CAMPO_FECHA_HORA }
		}, 
		true,
		reloadData,
		trContratoOnClick,
		null,
		5,
		35
	);
	
	grid.rebuild();
	
	grid.filtroDinamico.preventReload = true;
	grid.filtroDinamico.agregarFiltrosManuales(
		[
			{
				campo: "empresa.nombre",
				operador: "keq",
				valores: [63371826]
			}, {
				campo: "estado.nombre",
				operador: "keq",
				valores: ["1"]
			}, {
				campo: "fechaPickUp",
				operador: "nl",
				valores: []
			}
		], 
		false
	).then(function (data) {
		grid.filtroDinamico.preventReload = false
		
		reloadData();
	});
	
	if (numeroTramite != null) {
		$("#inputNumeroTramite").val(numeroTramite);
		
		inputNumeroTramiteOnChange();
	} else if (id != null) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/getById/" + id
		}).then(function(data) {
			if (data != null) {
				$("#inputNumeroTramite").val(data.numeroTramite);
				$("#aMID").attr("href", "tel:0" + data.mid);
				$("#aMID").text(data.mid);
				if (data.resultadoEntregaDistribucionObservaciones) {
					$("#textareaObservaciones").text(data.resultadoEntregaDistribucionObservaciones);
				}
			}
				
			$("#inputNumeroTramite").focus();
		});
	}
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoANTELREST/listContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoANTELREST/countContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.setCount(data);
	});
}

function trContratoOnClick(eventObject) {
	
}

function inputNumeroTramiteOnChange(event, element) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/getByNumeroTramite/" + $("#inputNumeroTramite").val()
	}).then(function(data) { 
		if (data != null) {
			if (data.fechaPickUp != null) {
				alert("Ya se realiz\u00f3 Pick-up para el tr\u00e1mite ingresado.");
				inputLimpiarOnClick();
			} else {
				id = data.id;
				$("#aMID").attr("href", "tel:0" + data.mid);
				$("#aMID").text(data.mid);
			}
		} else {
			alert("No se encuentra el tr\u00e1mite solicitado.");
			inputLimpiarOnClick();
		}
	});
}

function inputLimpiarOnClick(event, element) {
	$("#aMID").html("&nbsp;");
	$("#aMID").prop("href", "#");
	
	$("#textareaObservaciones").val("");
	
	$("#inputNumeroTramite").val(null);
	$("#inputNumeroTramite").focus();
}

function inputSubmitOnClick(event, element) {
	var numeroTramite = $("#inputNumeroTramite").val();
	
	if (numeroTramite.trim() == "") {
		alert("Debe ingresar un número de trámite");
		return false;
	}
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/pickUp",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
			contratoId: id,
			observaciones: $("#textareaObservaciones").text().trim()
		})
	}).then(function(data) {
		alert("Operación exitosa.");
		
		inputLimpiarOnClick();
	});
}