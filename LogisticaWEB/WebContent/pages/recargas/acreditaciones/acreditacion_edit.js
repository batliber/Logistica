var gridArchivosAdjuntos = null;

$(document).ready(init)

function init() {
	$("#inputAcreditar").hide();
	$("#divInputPreAprobar").hide();
	$("#divInputDenegar").hide();
	$("#divInputCredito").hide();
	
	
	refinarForm();
	
	initTableArchivosAdjuntos();
	
	initRecargaEmpresa()
		.then(initRecargaFormaPago)
		.then(initRecargaBanco)
		.then(function(odata) {
			if (id != null) {
				$.ajax({
					url: "/LogisticaWEB/RESTFacade/RecargaSolicitudAcreditacionSaldoREST/getById/" + id
				}).then(function(data) {
					populateField("recargaEmpresa", data, "recargaEmpresa.id", "recargaEmpresa.nombre", "reid", "recargaEmpresa.id");
					populateField("recargaEstadoAcreditacionSaldo", data, "recargaEstadoAcreditacionSaldo.id", "recargaEstadoAcreditacionSaldo.nombre", "reasid", "recargaEstadoAcreditacionSaldo.id");
					populateField("puntoVenta", data, "puntoVenta.id", "puntoVenta.nombre", "pvid", "puntoVenta.id");
					populateField("fechaSolicitud", data, "fechaSolicitud", "fechaSolicitud", null, null, formatShortDate);
					populateField("fechaPreprobacion", data, "fechaPreAprobacion", "fechaPreAprobacion", null, null, formatShortDate);
					populateField("fechaAcreditacion", data, "fechaAcreditacion", "fechaAcreditacion", null, null, formatShortDate);
					populateField("fechaDenegacion", data, "fechaDenegacion", "fechaDenegacion", null, null, formatShortDate);
					populateField("fechaCredito", data, "fechaCredito", "fechaCredito", null, null, formatShortDate);
					populateField("fechaEliminacion", data, "fechaEliminacion", "fechaEliminacion", null, null, formatShortDate);
					populateField("monto", data, "monto", "monto");
					populateField("observaciones", data, "observaciones", "observaciones");
					populateField("distribuidor", data, "distribuidor", "distribuidor");
					populateField("recargaFormaPago", data, "recargaFormaPago.id", "recargaFormaPago.descripcion", "rfpid", "recargaFormaPago.id");
					populateField("recargaBanco", data, "recargaBanco.id", "recargaBanco.nombre", "rbid", "recargaBanco.id");
					populateField("cajero", data, "cajero", "cajero");
					populateField("cuentas", data, "empresaRecargaBancoCuenta.id", "empresaRecargaBancoCuenta.numero", "erbcid", "empresaRecargaBancoCuenta.id");
					
					if (data.recargaEstadoAcreditacionSaldo.id == 1) {
						$("#inputAcreditar").show();
						$("#divInputPreAprobar").show();
						$("#divInputDenegar").show();
						$("#divInputCredito").show();
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleFourfoldSize");
					} else if (data.recargaEstadoAcreditacionSaldo.id == 2) {
						$("#divInputPreAprobar").show();
						$("#divInputDenegar").show();
						$("#divInputCredito").show();
						
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleTripleSize");
					} else if (data.recargaEstadoAcreditacionSaldo.id == 3) {
						$("#inputAcreditar").show();
						
					} else if (data.recargaEstadoAcreditacionSaldo.id == 4) {
					
					} else if (data.recargaEstadoAcreditacionSaldo.id == 5) {
						$("#inputAcreditar").show();
					} else {
						
					}
					
					reloadArchivosAdjuntosData(data.id);
				});
			}
		});
}

function initTableArchivosAdjuntos() {
	gridArchivosAdjuntos = new Grid(
		document.getElementById("divTableArchivosAdjuntos"),
		{
			//tdTipo: { campo: "tipoArchivoAdjunto.descripcion", descripcion: "Tipo", abreviacion: "Tipo", tipo: __TIPO_CAMPO_STRING, ancho: 150 },
			tdURL: { campo: "urlLink", descripcion: "Nombre", abreviacion: "Nombre", tipo: __TIPO_CAMPO_STRING, ancho: 350 },
			tdFechaSubida: { campo: "fechaSubida", descripcion: "Fecha", abreviacion: "Fecha", tipo: __TIPO_CAMPO_FECHA_HORA } 
		}, 
		false,
		reloadArchivosAdjuntosData,
		trArchivosAdjuntosOnClick,
		null,
		6
	);
	
	gridArchivosAdjuntos.rebuild();
}

function reloadArchivosAdjuntosData(id, recargaSolicitudAcreditacionSaldo) {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaSolicitudAcreditacionSaldoArchivoAdjuntoREST/listByRecargaSolicitudAcreditacionSaldoId/" + id
	}).then(function(archivoAdjuntos) {
		var data = {
			cantidadRegistros: 0,
			registrosMuestra: []
		};
		
		for (var i=0; i<archivoAdjuntos.length; i++) {
			var url =
				"<a href=\"/LogisticaWEB/Download?fn=" + archivoAdjuntos[i].url + "&f=s\">" 
					+ archivoAdjuntos[i].url
				+ "</a>";
			
			data.registrosMuestra[data.cantidadRegistros] = archivoAdjuntos[i];
			data.registrosMuestra[data.cantidadRegistros].urlLink = url;
			data.cantidadRegistros++;
		}
		
		var html = "";
		var imgGalleryClass = "imgGalleryActive";
		for (var i=0; i<data.registrosMuestra.length; i++) {
			if (i > 0) {
				imgGalleryClass = "imgGalleryInactive";
			}
			
			html += "<img class='" + imgGalleryClass + "'"
				+ " aaid='" + data.registrosMuestra[i].id + "'"
				+ " src='/LogisticaWEB/Stream?fn=" + data.registrosMuestra[i].url + "'/>";
		}
		
		$(".divGalleryContent").html(html != "" ? html : "&nbsp;");
		
		gridArchivosAdjuntos.reload(data);
	});
}

function trArchivosAdjuntosOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	var archivoAdjuntoId = $(target).attr("id");
	
	var img = $("img[aaid='" + archivoAdjuntoId + "']");

	$(".imgGalleryActive").attr("class", "imgGalleryInactive");
	$(img).attr("class", "imgGalleryActive");
}

function refinarForm() {
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
}

function initRecargaEmpresa() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaEmpresaREST/list"
	}).then(function(data) {
		if ($("#selectRecargaEmpresa").length > 0) {
			fillSelect(
				"selectRecargaEmpresa",
				data,
				"id", 
				"nombre"
			);
		}
	});
}

function initRecargaFormaPago() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaFormaPagoREST/list"
	}).then(function(data) {
		if ($("#selectRecargaFormaPago").length > 0) {
			fillSelect(
				"selectRecargaFormaPago",
				data,
				"id", 
				"descripcion"
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

function collectRecargaSolicitudAcreditacionSaldoData() {
	var result = {
		id: id
	};
	
	return result;
}

function inputPreAprobarOnClick(event) {
	var recargaSolicitudAcreditacionSaldo = collectRecargaSolicitudAcreditacionSaldoData();
	
	if (recargaSolicitudAcreditacionSaldo.id != null) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/RecargaSolicitudAcreditacionSaldoREST/preaprobar",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(recargaSolicitudAcreditacionSaldo)
		}).then(function(data) {
			alert("Operación exitosa");
			
			window.parent.closeDialog();
		});
	}
}

function inputAcreditarOnClick(event) {
	var recargaSolicitudAcreditacionSaldo = collectRecargaSolicitudAcreditacionSaldoData();
	
	if (recargaSolicitudAcreditacionSaldo.id != null) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/RecargaSolicitudAcreditacionSaldoREST/aprobar",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(recargaSolicitudAcreditacionSaldo)
		}).then(function(data) {
			alert("Operación exitosa");
			
			window.parent.closeDialog();
		});
	}
}

function inputDenegarOnClick(event) {
	var recargaSolicitudAcreditacionSaldo = collectRecargaSolicitudAcreditacionSaldoData();
	
	if (recargaSolicitudAcreditacionSaldo.id != null) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/RecargaSolicitudAcreditacionSaldoREST/denegar",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(recargaSolicitudAcreditacionSaldo)
		}).then(function(data) {
			alert("Operación exitosa");
			
			window.parent.closeDialog();
		});
	}
}

function inputCreditoOnClick(event) {
	var recargaSolicitudAcreditacionSaldo = collectRecargaSolicitudAcreditacionSaldoData();
	
	if (recargaSolicitudAcreditacionSaldo.id != null) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/RecargaSolicitudAcreditacionSaldoREST/credito",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(recargaSolicitudAcreditacionSaldo)
		}).then(function(data) {
			alert("Operación exitosa");
			
			window.parent.closeDialog();
		});
	}
}