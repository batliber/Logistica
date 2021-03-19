var __RECARGA_FORMA_PAGO_TRANSFERENCIA_ELECTRONICA = 1;

$(document).ready(init);

function init() {
	$("#divTitle").append("Solicitar saldo");
	
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
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaFormaPagoREST/list"
	}).then(function(data) {
		fillSelect(
			"selectRecargaFormaPago",
			data,
			"id", 
			"descripcion"
		);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaBancoREST/list"
	}).then(function(data) {
		fillSelect(
			"selectRecargaBanco",
			data,
			"id", 
			"nombre"
		);
	});
	
	fillSelect(
		"selectRecargaBanco",
		[],
		"id", 
		"nombre"
	);
}

function selectRecargaBancoOnChange(event, element) {
	var recargaBancoId = $(element).val();
	if (recargaBancoId != 0) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/EmpresaRecargaBancoCuentaREST/listByRecargaBancoId/" + recargaBancoId
		}).then(function(data) {
			fillSelect(
				"selectEmpresaRecargaBancoCuenta",
				data,
				"id", 
				"numero"
			);
		});
	} else {
		fillSelect(
			"selectRecargaBanco",
			[],
			"id", 
			"nombre"
		);
	}
}

function inputAgregarArchivoOnClick(event, element) {
	var divArchivos = $(".divArchivos");
	var inputCount = $('input[type="file"]').length;
	
	divArchivos.append(
		"<div class='divFormLabel'>Nombre:</div><div class='divFormValue'>"
			+ "<form method='POST' action='/LogisticaWEB/Upload' id='formArchivo" + inputCount + "'>"
				+ "<input type='file' id='inputArchivo" + inputCount + "' name='inputArchivo" + inputCount + "'/>"
			+ "</form>"
		+ "</div>"
	);
}

function inputSubmitOnClick(event) {
	var recargaSolicitudAcreditacionSaldo = { 
		observaciones: $("#textareaObservaciones").val(),
		moneda: {
			id: 1
		}
	};
	
	var recargaEmpresaId = $("#selectRecargaEmpresa").val();
	if (recargaEmpresaId > 0) {
		recargaSolicitudAcreditacionSaldo.recargaEmpresa = {
			id: recargaEmpresaId
		};
	} else {
		alert("Seleccione una Empresa.");
		return;
	}
	
	var monto = $("#inputMonto").val();
	if (monto != null && monto != "") {
		recargaSolicitudAcreditacionSaldo.monto = monto;
	} else {
		alert("Ingrese un monto.");
		return;
	}
	
	var recargaFormaPagoId = $("#selectRecargaFormaPago").val();
	if (recargaFormaPagoId > 0) {
		recargaSolicitudAcreditacionSaldo.recargaFormaPago = {
			id: recargaFormaPagoId
		};
		
		if (recargaFormaPagoId == __RECARGA_FORMA_PAGO_TRANSFERENCIA_ELECTRONICA) {
			var recargaBancoId = $("#selectRecargaBanco").val();
			if (recargaBancoId == 0) {
				alert("Seleccione un Banco destino.");
				
				return;
			} else {
				recargaSolicitudAcreditacionSaldo.recargaBanco = {
					id: recargaBancoId
				};
				
				var empresaRecargaBancoCuentaId = $("#selectEmpresaRecargaBancoCuenta").val()
				if (empresaRecargaBancoCuentaId == 0) {
					alert("Seleccione una cuenta.");
				
					return;
				} else {
					recargaSolicitudAcreditacionSaldo.empresaRecargaBancoCuenta = {
						id: empresaRecargaBancoCuentaId
					};
				}
			}
		}
	} else {
		alert("Debe seleccionar una Forma de pago.");
		return;
	}
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaSolicitudAcreditacionSaldoREST/solicitar",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(recargaSolicitudAcreditacionSaldo)
	}).then(function(data) {
		if (data != null) {
			var inputs = $('input[type="file"]');
			
			if (inputs.length > 0) {
				var xmlHTTPRequest = new XMLHttpRequest();
				for (var i=0; i<inputs.length; i++) {
					xmlHTTPRequest = new XMLHttpRequest();
					xmlHTTPRequest.open(
						"POST",
						"/LogisticaWEB/Upload",
						false
					);
					
					formData = new FormData(document.getElementById("formArchivo" + i));
					formData.append("caller", "recarga");
					formData.append("id", data.id);
					
					xmlHTTPRequest.send(formData);
					
					if (xmlHTTPRequest.status != 200) {
						alert(xmlHTTPRequest.responseText);
						return;
					}
				}
				
				if (xmlHTTPRequest.status == 200) {
					alert(JSON.parse(xmlHTTPRequest.responseText).message);
					
					window.location = "/LogisticaWEB/pages/mobile/mrecargas/msaldo/msaldo.jsp";
				} else {
					alert(xmlHTTPRequest.responseText);
				}
			} else {
				alert("Operación exitosa");
				
				window.location = "/LogisticaWEB/pages/mobile/mrecargas/msaldo/msaldo.jsp";
			}
		}
	});
}