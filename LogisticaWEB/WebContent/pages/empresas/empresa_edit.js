$(document).ready(init);

var gridFormaPagos = null;
var gridEmpresaUsuarioContratos = null;

var formaPagos = {
	cantidadRegistros: 0,
	registrosMuestra: []
};

var empresaUsuarioContratos = {
	cantidadRegistros: 0,
	registrosMuestra: []
};

var updateLogo = false;

function init() {
	refinarForm();
	
	$("#divEliminarEmpresa").hide();
	
	gridFormaPagos = new Grid(
		document.getElementById("divTableFormaPagos"),
		{
			tdDescripcion: { campo: "descripcion", descripcion: "Forma de pago", abreviacion: "Forma pago", tipo: __TIPO_CAMPO_STRING, ancho: 166 } 
		}, 
		false,
		reloadFormaPagos,
		trFormaPagosOnClick,
		null,
		8
	);
	
	gridFormaPagos.rebuild();
	
	gridEmpresaUsuarioContratos = new Grid(
		document.getElementById("divTableEmpresaUsuarioContratos"),
		{
			tdNombre: { campo: "nombre", descripcion: "Usuario", abreviacion: "Usuario", tipo: __TIPO_CAMPO_STRING, ancho: 169 } 
		}, 
		false,
		reloadEmpresaUsuarioContratos,
		trEmpresaUsuarioContratosOnClick,
		null,
		8
	);
	
	gridEmpresaUsuarioContratos.rebuild();
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/FormaPagoREST/list"
	}).then(function(data) {
		fillSelect(
			"selectFormaPago", 
			data,
			"id", 
			"descripcion"
		);
	}).then(function(data) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/UsuarioREST/listMinimal"
		}).then(function(data) { 
			fillSelect(
				"selectUsuario", 
				data,
				"id", 
				"nombre"
			);
		});
	}).then(function(data) {
		if (id != null) {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/EmpresaREST/getById/" + id
			}).then(function(data) {
				$("#inputEmpresaNombre").val(data.nombre);
				$("#inputEmpresaCodigoPromotor").val(data.codigoPromotor);
				$("#inputEmpresaNombreContrato").val(data.nombreContrato);
				$("#inputEmpresaNombreSucursal").val(data.nombreSucursal);
				$("#inputEmpresaDireccion").val(data.direccion);
				$("#inputEmpresaOmitirControlVendidos").prop("checked", data.omitirControlVendidos);
				$("#inputEmpresaId").val(data.id);
				
				if (data.formaPagos != null) {
					formaPagos.cantidadRegistros = data.formaPagos.length;
					
					for (var i=0; i<data.formaPagos.length; i++) {
						formaPagos.registrosMuestra[formaPagos.registrosMuestra.length] = data.formaPagos[i];
					}
				}
				
				if (data.empresaUsuarioContratos != null) {
					empresaUsuarioContratos.cantidadRegistros = data.empresaUsuarioContratos.length;
					
					for (var i=0; i<data.empresaUsuarioContratos.length; i++) {
						empresaUsuarioContratos.registrosMuestra[empresaUsuarioContratos.registrosMuestra.length] = data.empresaUsuarioContratos[i];
					}
				}
				
				if (data.logoURL != null) {
					$("#imgEmpresaLogo").attr("src", "/LogisticaWEB/Stream?fn=" + data.logoURL);
					$("#imgEmpresaLogo").attr("fn", data.logoURL);
				}
				
				reloadFormaPagos();
				reloadEmpresaUsuarioContratos();
				
				if (mode == __FORM_MODE_ADMIN) {
					$("#divEliminarEmpresa").show();
					$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				} else {
					
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

function reloadFormaPagos() {
	gridFormaPagos.reload(formaPagos);
}

function reloadEmpresaUsuarioContratos() {
	gridEmpresaUsuarioContratos.reload(empresaUsuarioContratos);
}

function trFormaPagosOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var id = $(target).attr("id");
	
	var i=0;
	for (i=0; i<formaPagos.registrosMuestra.length; i++) {
		if (formaPagos.registrosMuestra[i].id == id) {
			break;
		}
	}
	
	formaPagos.cantidadRegistros = formaPagos.cantidadRegistros - 1;
	formaPagos.registrosMuestra.splice(i, 1);
	
	reloadFormaPagos();
}

function trEmpresaUsuarioContratosOnClick(eventObject) {
	var target = eventObject.currentTarget;
	var id = $(target).attr("id");
	
	var i=0;
	for (i=0; i<empresaUsuarioContratos.registrosMuestra.length; i++) {
		if (empresaUsuarioContratos.registrosMuestra[i].id == id) {
			break;
		}
	}
	
	empresaUsuarioContratos.cantidadRegistros = empresaUsuarioContratos.cantidadRegistros - 1;
	empresaUsuarioContratos.registrosMuestra.splice(i, 1);
	
	reloadEmpresaUsuarioContratos();
}

function inputAgregarFormaPagoOnClick(event, element) {
	var formaPagoId = $("#selectFormaPago").val();
	if (formaPagoId != 0) {
		var found = false;
		for (i=0; i<formaPagos.registrosMuestra.length; i++) {
			if (formaPagos.registrosMuestra[i].id == formaPagoId) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/FormaPagoREST/getById/" + formaPagoId
			}).then(function(data) {
				formaPagos.cantidadRegistros = formaPagos.cantidadRegistros + 1;
				formaPagos.registrosMuestra[formaPagos.registrosMuestra.length] = data;
				
				reloadFormaPagos();
			});
		}
	} else {
		alert("Debe seleccionar una forma de pago.");
	}
}

function inputAgregarUsuarioOnClick(event, element) {
	var usuarioId = $("#selectUsuario").val();
	if (usuarioId != 0) {
		var found = false;
		for (i=0; i<empresaUsuarioContratos.registrosMuestra.length; i++) {
			if (empresaUsuarioContratos.registrosMuestra[i].id == usuarioId) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			$.ajax({
				url: "/LogisticaWEB/RESTFacade/UsuarioREST/getByIdMinimal/" + usuarioId
			}).then(function(data) {
				empresaUsuarioContratos.cantidadRegistros = empresaUsuarioContratos.cantidadRegistros + 1;
				empresaUsuarioContratos.registrosMuestra[empresaUsuarioContratos.registrosMuestra.length] = data;
				
				reloadEmpresaUsuarioContratos();
			});
		}
	} else {
		alert("Debe seleccionar un usuario.");
	}
}

function inputEmpresaLogoOnChange(event, element) {
	updateLogo = true;
}

function inputGuardarOnClick(event) {
	var empresa = {
		nombre: $("#inputEmpresaNombre").val(),
		codigoPromotor: $("#inputEmpresaCodigoPromotor").val(),
		nombreContrato: $("#inputEmpresaNombreContrato").val(),
		nombreSucursal: $("#inputEmpresaNombreSucursal").val(),
		direccion: $("#inputEmpresaDireccion").val(),
		omitirControlVendidos: $("#inputEmpresaOmitirControlVendidos").prop("checked"),
		logoURL: $("#imgEmpresaLogo").attr("fn"),
		formaPagos: [],
		empresaUsuarioContratos: []
	};
	
	if (empresa.nombre == null || empresa.nombre == "") {
		alert("El nombre no puede estar vacío.");
		return false;
	}
	
	if (empresaUsuarioContratos.registrosMuestra.length == 0) {
		alert("Debe seleccionar al menos un usuario que figurará en la impresión de contratos.");
		return false;
	}
	
	for (var i=0; i<formaPagos.registrosMuestra.length; i++) {
		empresa.formaPagos[empresa.formaPagos.length] = {
			id: formaPagos.registrosMuestra[i].id
		};
	}
	
	for (var i=0; i<empresaUsuarioContratos.registrosMuestra.length; i++) {
		empresa.empresaUsuarioContratos[empresa.empresaUsuarioContratos.length] = {
			id: empresaUsuarioContratos.registrosMuestra[i].id
		};
	}
	
	if (id != null) {
		empresa.id = id;
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/EmpresaREST/update",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(empresa)
		}).then(function(data) {
			if (updateLogo) {
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
					
					$("#imgEmpresaLogo").attr("src", "/LogisticaWEB/Stream?fn=" + response.fileName);
				} else {
					alert(xmlHTTPRequest.responseText);
				}
			}
			
			alert("Operación exitosa");
		});
	} else {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/EmpresaREST/add",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(empresa)
		}).then(function(data) {
			if(data != null) {
				if (updateLogo) {
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
						
						$("#imgEmpresaLogo").attr("src", "/LogisticaWEB/Stream?fn=" + response.fileName);
					} else {
						alert(xmlHTTPRequest.responseText);
					}
				}
				
				alert("Operación exitosa");
				
				$("#divEliminarEmpresa").show();
			} else {
				alert("Error en la operación");
			}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará la Empresa")) {
		var empresa = {
			id: id
		};
		
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/EmpresaREST/remove",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(empresa)
		}).then(function(data) {
			alert("Operación exitosa");
		});
	}
}