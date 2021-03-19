$(document).ready(init);

function init() {
	refinarForm();
	
	$("#divEliminarCuenta").hide();
	
	initEmpresa()
		.then(initRecargaBanco)
		.then(initMoneda)
		.then(function(odata) {
			if (id != null) {
				$.ajax({
			        url: "/LogisticaWEB/RESTFacade/EmpresaRecargaBancoCuentaREST/getById/" + id
			    }).then(function(data) {
			    	populateField("empresa", data, "empresa.id", "empresa.nombre", "eid", "empresa.id");
			    	populateField("recargaBanco", data, "recargaBanco.id", "recargaBanco.nombre", "rbid", "recargaBanco.id");
			    	populateField("moneda", data, "moneda.id", "moneda.nombre", "mid", "moneda.id");
			    	populateField("numero", data, "numero", "numero");
			    	
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarCuenta").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
					}
				});
			}
		});
}

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function initEmpresa() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/UsuarioRolEmpresaREST/listEmpresasByContext"
	}).then(function(data) {
		if ($("#selectEmpresa").length > 0) {
			fillSelect(
				"selectEmpresa",
				data,
				"id", 
				"nombre"
			);
		}
	});
}

function initRecargaBanco() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaBancoREST/list"
	}).then(function(data) {
		if ($("#selectRecargaBanco").length > 0) {
			fillSelect(
				"selectRecargaBanco",
				data,
				"id", 
				"nombre"
			);
		}
	});
}

function initMoneda() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/MonedaREST/list"
	}).then(function(data) {
		if ($("#selectMoneda").length > 0) {
			fillSelect(
				"selectMoneda",
				data,
				"id", 
				"nombre"
			);
		}
	});
}

function inputGuardarOnClick(event) {
	var numero = $("#inputNumero").val();
	if (numero == null || numero == "") {
		alert("Debe ingresar un número.");
		
		return;
	};
	
	var empresaId = $("#selectEmpresa").val();
	if (empresaId == 0) {
		alert("Seleccione una empresa.");
		
		return;
	};
	
	var recargaBancoId = $("#selectRecargaBanco").val();
	if (recargaBancoId == 0) {
		alert("Seleccione un Banco.");
		
		return;
	};
	
	var monedaId = $("#selectMoneda").val();
	if (monedaId == 0) {
		alert("Seleccione una moneda.");
		
		return;
	};
	
	var cuenta = {
		numero: $("#inputNumero").val(),
		empresa: {
			id: empresaId
		},
		recargaBanco: {
			id: recargaBancoId
		},
		moneda: {
			id: monedaId
		}
	};
	
	if (id != null) {
		cuenta.id = id;
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/EmpresaRecargaBancoCuentaREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(cuenta)
	    }).then(function(data) {
	    	alert("Operación exitosa");
	    });
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/EmpresaRecargaBancoCuentaREST/add",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(cuenta)
	    }).then(function(data) {
	    	if (data != null) {
				alert("Operación exitosa");
	    	
				$("#inputEliminarCuenta").prop("disabled", false);
	    	} else {
	    		alert("Error en la operación");
	    	}
	    });
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará la cuenta")) {
		var cuenta = {
			id: id
		};
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/EmpresaRecargaBancoCuentaREST/remove",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(cuenta)
	    }).then(function(data) {
	    	alert("Operación exitosa");
	    });
	}
}