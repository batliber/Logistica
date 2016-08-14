$(document).ready(function() {
	refinarForm();
	
	$("#divEliminarEmpresa").hide();
	
	if (id != null) {
		EmpresaDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputEmpresaNombre").val(data.nombre);
					$("#inputEmpresaCodigoPromotor").val(data.codigoPromotor);
					$("#inputEmpresaNombreContrato").val(data.nombreContrato);
					$("#inputEmpresaNombreSucursal").val(data.nombreSucursal);
					$("#inputEmpresaId").val(data.id);
					if (data.logoURL != null) {
						$("#imgEmpresaLogo").attr("src", "/LogisticaWEB/Stream?fn=" + data.logoURL);
					}
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarEmpresa").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
					}
				}, async: false
			}
		);
	}
});

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function inputGuardarOnClick(event) {
	var xmlHTTPRequest = new XMLHttpRequest();
	xmlHTTPRequest.open(
		"POST",
		"/LogisticaWEB/Upload",
		false
	);
	
	var formData = new FormData(document.getElementById("formEmpresa"));
	
	xmlHTTPRequest.send(formData);
	
	if (xmlHTTPRequest.status == 200) {
		var response = JSON.parse(xmlHTTPRequest.responseText);
		
		alert(response.message);
		
		$("#imgEmpresaLogo").attr("src", "/LogisticaWEB/Stream?fn=" + response.fileName);
	} else {
		alert(xmlHTTPRequest.responseText);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará la Empresa")) {
		var empresa = {
			id: id
		};
		
		EmpresaDWR.remove(
			empresa,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}