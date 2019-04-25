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
	
	FormaPagoDWR.list(
		{
			callback: function(data) {
				var html = 
					"<option value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html +=
						"<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectFormaPago").html(html);
			}, async: false
		}
	);
	
	UsuarioDWR.list(
		{
			callback: function(data) {
				var html = 
					"<option value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html +=
						"<option value='" + data[i].id + "'>" + data[i].nombre + "</option>";
				}
				
				$("#selectUsuario").html(html);
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
			FormaPagoDWR.getById(
				formaPagoId,
				{
					callback: function(data) {
						formaPagos.cantidadRegistros = formaPagos.cantidadRegistros + 1;
						formaPagos.registrosMuestra[formaPagos.registrosMuestra.length] = data;
						
						reloadFormaPagos();
					}, async: false
				}
			);
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
			UsuarioDWR.getById(
				usuarioId,
				{
					callback: function(data) {
						empresaUsuarioContratos.cantidadRegistros = empresaUsuarioContratos.cantidadRegistros + 1;
						empresaUsuarioContratos.registrosMuestra[empresaUsuarioContratos.registrosMuestra.length] = data;
						
						reloadEmpresaUsuarioContratos();
					}, async: false
				}
			);
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
		
		EmpresaDWR.update(
			empresa,
			{
				callback: function(data) {
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
				}, async: false
			}
		);
	} else {
		EmpresaDWR.add(
			empresa,
			{
				callback: function(data) {
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
				}, async: false
			}
		);
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