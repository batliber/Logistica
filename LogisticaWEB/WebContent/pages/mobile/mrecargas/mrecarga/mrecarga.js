var __RECARGA_EMPRESA_ANTEL = 1;
var __RECARGA_EMPRESA_CLARO = 2;
var __RECARGA_EMPRESA_MOVISTAR = 3;

var __RECARGA_ESTADO_PENDIENTE = 1;
var __RECARGA_ESTADO_APROBADO = 2;
var __RECARGA_ESTADO_SALDO_INSUFICIENTE = 4;
var __RECARGA_ESTADO_APROBADO_CON_SALDO_BAJO = 5;

var __MAX_MILLISECONDS = 15 * 1000;

var id = null;

$(document).ready(init);

function init() {
	$("#divTitle").append("Recarga");
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaEmpresaREST/list"
	}).then(function(data) {
		fillSelect(
			"selectRecargaEmpresa",
			data,
			"id", 
			"nombre"
		);
	});
	
	$("#selectRecargaEmpresa").attr("disabled", "true");
}

function inputMIDOnChange(event, element) {
	var val = $(element).val();
	
	if (val == null || val == "") {
		$("#selectRecargaEmpresa").val(0);
	} else if (
		val.substring(0, 3) == "091"
		|| val.substring(0, 3) == "092"
		|| val.substring(0, 3) == "098"
		|| val.substring(0, 3) == "099"
	) {
		$("#selectRecargaEmpresa").val(__RECARGA_EMPRESA_ANTEL);
	} else if (
		val.substring(0, 3) == "096"
		|| val.substring(0, 3) == "097"
	) {
		$("#selectRecargaEmpresa").val(__RECARGA_EMPRESA_CLARO);
	} else if (
		val.substring(0, 3) == "093"
		|| val.substring(0, 3) == "094"
		|| val.substring(0, 3) == "095"
	) {
		$("#selectRecargaEmpresa").val(__RECARGA_EMPRESA_MOVISTAR);
	}  else {
		$("#selectRecargaEmpresa").val(0);
	} 
}

function inputSubmitOnClick(event, element) {
	var recarga = {
		moneda: {
			id: 1
		}
	};
	
	var mid = $("#inputMID").val();
	if (mid != null && mid != "") {
		recarga.mid = mid
		recarga.recargaEmpresa = {
			id: $("#selectRecargaEmpresa").val()
		};
	} else {
		alert("Debe ingresar un número.");
		return;
	}
	
	var monto = $("#inputMonto").val();
	if (monto != null && monto != "") {
		recarga.monto = monto;
	} else {
		alert("Debe ingresar un monto.");
		return;
	}
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaREST/recargar",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(recarga)
	}).then(function(data) {
		if (data != null) {
			id = data.id;
			
			if (data.recargaEstado.id == __RECARGA_ESTADO_SALDO_INSUFICIENTE) {
				$("#divResultado").text("Saldo insuficiente.");
			} else {
				$("#divResultado").text("Recarga pendiente");
				
				setTimeout("pollAprobacion(" + data.id + ", 0)", 1000);
			}
		} else {
			alert("No se pudo completar la operación");
		}
	});
}

function pollAprobacion(id, invocaciones) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaREST/getById/" + id
	}).then(function(data) {
		var now = new Date();
		if (data != null) {
			if ((now - data.fechaSolicitud) > __MAX_MILLISECONDS) {
				$("#divResultado").text("Tiempo de espera excedido.");
				
				$.ajax({
					url: "/LogisticaWEB/RESTFacade/RecargaREST/timeout",
					method: "POST",
					contentType: 'application/json',
					data: JSON.stringify(data)
				}).then(function(odata) {
					
				});
			} else if (data.recargaEstado.id == __RECARGA_ESTADO_PENDIENTE) {
				var status = "Recarga pendiente";
				for (var i=0; i<invocaciones; i++) {
					status += ".";
				}
				
				$("#divResultado").text(status);
				setTimeout("pollAprobacion(" + data.id + ", " + (invocaciones + 1) + ")", 1000);
			} else if (data != null && data.recargaEstado.id == __RECARGA_ESTADO_APROBADO) {
				$("#divResultado").text("Recarga aprobada.");
			} else if (data != null && data.recargaEstado.id == __RECARGA_ESTADO_APROBADO_CON_SALDO_BAJO) {
				$("#divResultado").text("Recarga aprobada. Se recomienda realizar solicitud de saldo.");
			}
		}
	});
}

function inputComprobarNuevamenteOnClick(event, element) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaREST/getById/" + id
	}).then(function(data) {
		
	});
}