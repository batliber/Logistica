$(document).ready(init);

function init() {
	refinarForm();
	
	$("#divEliminarEmpresa").hide();
	
	FormaPagoDWR.list(
		{
			callback: function(data) {
				$("#tableFormasPago > tbody:last > tr").remove();
				
				html = "";
				
				for (var i=0; i<data.length; i++) {
					html += 
						"<tr>"
							+ "<td><div class='divFormaPagoDescripcion'>" + data[i].descripcion + "</div></td>"
							+ "<td><div><input type='checkbox' id='" + data[i].id + "' name='inputFormaPago" + data[i].id + "' value='" + data[i].id + "'/></div></td>"
						+ "</tr>";
				}
				
				$("#tableFormasPago > tbody:last").append(html);
				
				$("#tableFormasPago > tbody:last > tr:odd").css("background-color", "#F8F8F8");
				$("#tableFormasPago > tbody:last > tr:odd").hover(
					function() {
						$(this).css("background-color", "orange");
					},
					function() {
						$(this).css("background-color", "#F8F8F8");
					}
				);
				$("#tableFormasPago > tbody:last > tr:even").css("background-color", "#FFFFFF");
				$("#tableFormasPago > tbody:last > tr:even").hover(
					function() {
						$(this).css("background-color", "orange");
					},
					function() {
						$(this).css("background-color", "#FFFFFF");
					}
				);
			}, async: false
		}
	);
	
	if (id != null) {
		EmpresaDWR.getById(
			id,
			{
				callback: function(data) {
					$("#inputEmpresaNombre").val(data.nombre);
					$("#inputEmpresaCodigoPromotor").val(data.codigoPromotor);
					$("#inputEmpresaNombreContrato").val(data.nombreContrato);
					$("#inputEmpresaNombreSucursal").val(data.nombreSucursal);
					$("#inputEmpresaDireccion").val(data.direccion);
					$("#inputEmpresaId").val(data.id);
					
					if (data.formaPagos != null) {
						for (var i=0; i<data.formaPagos.length; i++) {
							$("#tableFormasPago > tbody > tr > td > div > input[id=" + data.formaPagos[i].id + "]").prop("checked", true);
						}
					}
					
					if (data.logoURL != null) {
						$("#imgEmpresaLogo").attr("src", "/LogisticaWEB/Stream?fn=" + data.logoURL);
					}
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#tableFormasPago > tbody > tr > td > div > input").prop("disabled", false);
						
						$("#divEliminarEmpresa").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
					} else {
						$("#tableFormasPago > tbody > tr > td > div > input").prop("disabled", true);
					}
				}, async: false
			}
		);
	}
}

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