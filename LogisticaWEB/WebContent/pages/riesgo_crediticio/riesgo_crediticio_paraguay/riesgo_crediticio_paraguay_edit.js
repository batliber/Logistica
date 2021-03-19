$(document).ready(init)

function init() {
	refinarForm();
	
	initEstadoRiesgoCrediticio()
		.then(function(data) {
			if (id != null) {
				$.ajax({
					url: "/LogisticaWEB/RESTFacade/RiesgoCrediticioParaguayREST/getById/" + id
				}).then(function(data) {
					populateField("empresa", data, "empresa.id", "empresa.nombre", "eid", "empresa.id");
					populateField("documento", data, "documento", "documento");
					populateField("fechaNacimiento", data, "fechaNacimiento", "fechaNacimiento", null, null, formatRawDate);
					populateField("fechaNacimientoMostrar", data, "fechaNacimiento", "fechaNacimiento", null, null, formatShortDate);
					populateField("situacionRiesgoCrediticioParaguay", data, "situacionRiesgoCrediticioParaguay.id", "situacionRiesgoCrediticioParaguay.descripcion", "srcpid", "situacionRiesgoCrediticioParaguay.id");
					populateField("estadoRiesgoCrediticio", data, "estadoRiesgoCrediticio.id", "estadoRiesgoCrediticio.nombre", "ercid", "estadoRiesgoCrediticio.id");
					populateField("fechaVigenciaDesde", data, "fechaVigenciaDesde", "fechaVigenciaDesde", null, null, formatLongDate);
					
					populateField("condicion", data, "condicion", "condicion");
					populateField("motivo", data, "motivo", "motivo");
					populateField("iPSDocumento", data, "ipsDocumento", "ipsDocumento");
					populateField("iPSNombres", data, "ipsNombres", "ipsNombres");
					populateField("iPSApellidos", data, "ipsApellidos", "ipsApellidos");
					populateField("iPSFnac", data, "ipsFnac", "ipsFnac");
					populateField("iPSSexo", data, "ipsSexo", "ipsSexo");
					populateField("iPSTipoAseg", data, "ipsTipoAseg", "ipsTipoAseg");
					populateField("iPSEmpleador", data, "ipsEmpleador", "ipsEmpleador");
					populateField("iPSEstado", data, "ipsEstado", "ipsEstado");
					populateField("iPSMesesAporte", data, "ipsMesesAporte", "ipsMesesAporte");
					populateField("iPSNuPatronal", data, "ipsNuPatronal", "ipsNuPatronal");
					populateField("iPSUPA", data, "ipsUPA", "ipsUPA");
					populateField("sETDocumento", data, "setDocumento", "setDocumento");
					populateField("sETDV", data, "setDV", "setDV");
					populateField("sETNombreCompleto", data, "setNombreCompleto", "setNombreCompleto");
					populateField("sETEstado", data, "setEstado", "setEstado");
					populateField("sETSituacion", data, "setSituacion", "setSituacion");
					populateField("sFPEntidad", data, "sfpEntidad", "sfpEntidad");
					populateField("sFPCedula", data, "sfpCedula", "sfpCedula");
					populateField("sFPNombres", data, "sfpNombres", "sfpNombres");
					populateField("sFPApellidos", data, "sfpApellidos", "sfpApellidos");
					populateField("sFPPresupuesto", data, "sfpPresupuesto", "sfpPresupuesto");
					populateField("sFPFnac", data, "sfpFnac", "sfpFnac");
					
					populateField("respuestaExterna", data, "respuestaExterna", "respuestaExterna");
				});
			}
		});
}

function refinarForm() {
	hideField("fechaNacimiento");
	
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		$("#divEmpresa").html("&nbsp");
		$("#divDocumento").html("&nbsp");
		$("#divFechaNacimiento").html("&nbsp");
		$("#divFechaNacimientoMostrar").html("&nbsp");
		$("#divSituacionRiesgoCrediticioParaguay").html("&nbsp");
		$("#divEstadoRiesgoCrediticio").html("&nbsp");
		$("#divFechaVigenciaDesde").html("&nbsp");
		$("#divCondicion").html("&nbsp");
		$("#divMotivo").html("&nbsp");
		$("#divIPSDocumento").html("&nbsp");
		$("#divIPSNombres").html("&nbsp");
		$("#divIPSApellidos").html("&nbsp");
		$("#divIPSFnac").html("&nbsp");
		$("#divIPSSexo").html("&nbsp");
		$("#divIPSTipoAseg").html("&nbsp");
		$("#divIPSEmpleador").html("&nbsp");
		$("#divIPSEstado").html("&nbsp");
		$("#divIPSMesesAporte").html("&nbsp");
		$("#divIPSNuPatronal").html("&nbsp");
		$("#divIPSUPA").html("&nbsp");
		$("#divSETDocumento").html("&nbsp");
		$("#divSETDV").html("&nbsp");
		$("#divSETNombreCompleto").html("&nbsp");
		$("#divSETEstado").html("&nbsp");
		$("#divSETSituacion").html("&nbsp");
		$("#divSFPEntidad").html("&nbsp");
		$("#divSFPCedula").html("&nbsp");
		$("#divSFPNombres").html("&nbsp");
		$("#divSFPApellidos").html("&nbsp");
		$("#divSFPPresupuesto").html("&nbsp");
		$("#divSFPFnac").html("&nbsp");
		$("#divRespuestaExterna").html("&nbsp");
	}
}

function initEstadoRiesgoCrediticio() {
	return $.ajax({
		url: "/LogisticaWEB/RESTFacade/EstadoRiesgoCrediticioREST/list"
	}).then(function(data) {
		if ($("#selectEstadoRiesgoCrediticio").length > 0) {
			fillSelect(
				"selectEstadoRiesgoCrediticio",
				data,
				"id", 
				"nombre"
			);
		}
	});
}

function collectRiesgoCrediticioParaguayData() {
	var riesgoCrediticioParaguay = {
		id: id,
		empresa: {
			id: $("#divEmpresa").attr("eid"),
		},
		situacionRiesgoCrediticioParaguay: {
			id: $("#divSituacionRiesgoCrediticioParaguay").attr("srcpid"),
		},
		fechaNacimiento: $("#divFechaNacimiento").text().trim() != "" ? 
			parseInt($("#divFechaNacimiento").text().trim()) 
			: null,
		documento: $("#inputDocumento").length > 0 ?
			($("#inputDocumento").val().trim() != "" ? $("#inputDocumento").val().trim() : null) :
			$("#divDocumento").text().trim(),
		fechaVigenciaDesde: new Date().getTime(),
		
		condicion: $("#inputCondicion").length > 0 ?
			($("#inputCondicion").val().trim() != "" ? $("#inputCondicion").val().trim() : null) :
			$("#divCondicion").text().trim(),
		motivo: $("#inputMotivo").length > 0 ?
			($("#inputMotivo").val().trim() != "" ? $("#inputMotivo").val().trim() : null) :
			$("#divMotivo").text().trim(),
		ipsDocumento: $("#inputIPSDocumento").length > 0 ?
			($("#inputIPSDocumento").val().trim() != "" ? $("#inputIPSDocumento").val().trim() : null) :
			$("#divIPSDocumento").text().trim(),
		ipsNombres: $("#inputIPSNombres").length > 0 ?
			($("#inputIPSNombres").val().trim() != "" ? $("#inputIPSNombres").val().trim() : null) :
			$("#divIPSNombres").text().trim(),
		ipsApellidos: $("#inputIPSApellidos").length > 0 ?
			($("#inputIPSApellidos").val().trim() != "" ? $("#inputIPSApellidos").val().trim() : null) :
			$("#divIPSApellidos").text().trim(),
		ipsFnac: $("#inputIPSFnac").length > 0 ?
			($("#inputIPSFnac").val().trim() != "" ? $("#inputIPSFnac").val().trim() : null) :
			$("#divIPSFnac").text().trim(),
		ipsSexo: $("#inputIPSSexo").length > 0 ?
			($("#inputIPSSexo").val().trim() != "" ? $("#inputIPSSexo").val().trim() : null) :
			$("#divIPSSexo").text().trim(),
		ipsTipoAseg: $("#inputIPSTipoAseg").length > 0 ?
			($("#inputIPSTipoAseg").val().trim() != "" ? $("#inputIPSTipoAseg").val().trim() : null) :
			$("#divIPSTipoAseg").text().trim(),
		ipsEmpleador: $("#inputIPSEmpleador").length > 0 ?
			($("#inputIPSEmpleador").val().trim() != "" ? $("#inputIPSEmpleador").val().trim() : null) :
			$("#divIPSEmpleador").text().trim(),
		ipsEstado: $("#inputIPSEstado").length > 0 ?
			($("#inputIPSEstado").val().trim() != "" ? $("#inputIPSEstado").val().trim() : null) :
			$("#divIPSEstado").text().trim(),
		ipsMesesAporte: $("#inputIPSMesesAporte").length > 0 ?
			($("#inputIPSMesesAporte").val().trim() != "" ? $("#inputIPSMesesAporte").val().trim() : null) :
			$("#divIPSMesesAporte").text().trim(),
		ipsNuPatronal: $("#inputIPSNuPatronal").length > 0 ?
			($("#inputIPSNuPatronal").val().trim() != "" ? $("#inputIPSNuPatronal").val().trim() : null) :
			$("#divIPSNuPatronal").text().trim(),
		ipsUPA: $("#inputIPSUPA").length > 0 ?
			($("#inputIPSUPA").val().trim() != "" ? $("#inputIPSUPA").val().trim() : null) :
			$("#divIPSUPA").text().trim(),
		setDocumento: $("#inputSETDocumento").length > 0 ?
			($("#inputSETDocumento").val().trim() != "" ? $("#inputSETDocumento").val().trim() : null) :
			$("#divSETDocumento").text().trim(),
		setDV: $("#inputSETDV").length > 0 ?
			($("#inputSETDV").val().trim() != "" ? $("#inputSETDV").val().trim() : null) :
			$("#divSETDV").text().trim(),
		setNombreCompleto: $("#inputSETNombreCompleto").length > 0 ?
			($("#inputSETNombreCompleto").val().trim() != "" ? $("#inputSETNombreCompleto").val().trim() : null) :
			$("#divSETNombreCompleto").text().trim(),
		setEstado: $("#inputSETEstado").length > 0 ?
			($("#inputSETEstado").val().trim() != "" ? $("#inputSETEstado").val().trim() : null) :
			$("#divSETEstado").text().trim(),
		setSituacion: $("#inputSETSituacion").length > 0 ?
			($("#inputSETSituacion").val().trim() != "" ? $("#inputSETSituacion").val().trim() : null) :
			$("#divSETSituacion").text().trim(),
		sfpEntidad: $("#inputSFPEntidad").length > 0 ?
			($("#inputSFPEntidad").val().trim() != "" ? $("#inputSFPEntidad").val().trim() : null) :
			$("#divSFPEntidad").text().trim(),
		sfpCedula: $("#inputSFPCedula").length > 0 ?
			($("#inputSFPCedula").val().trim() != "" ? $("#inputSFPCedula").val().trim() : null) :
			$("#divSFPCedula").text().trim(),
		sfpNombres: $("#inputSFPNombres").length > 0 ?
			($("#inputSFPNombres").val().trim() != "" ? $("#inputSFPNombres").val().trim() : null) :
			$("#divSFPNombres").text().trim(),
		sfpApellidos: $("#inputSFPApellidos").length > 0 ?
			($("#inputSFPApellidos").val().trim() != "" ? $("#inputSFPApellidos").val().trim() : null) :
			$("#divSFPApellidos").text().trim(),
		sfpPresupuesto: $("#inputSFPPresupuesto").length > 0 ?
			($("#inputSFPPresupuesto").val().trim() != "" ? $("#inputSFPPresupuesto").val().trim() : null) :
			$("#divSFPPresupuesto").text().trim(),
		sfpFnac: $("#inputSFPFnac").length > 0 ?
			($("#inputSFPFnac").val().trim() != "" ? $("#inputSFPFnac").val().trim() : null) :
			$("#divSFPFnac").text().trim(),
		sfpFnac: $("#inputRespuestaExterna").length > 0 ?
			($("#inputRespuestaExterna").val().trim() != "" ? $("#inputRespuestaExterna").val().trim() : null) :
			$("#divRespuestaExterna").text().trim()
	}
	
	if ($("#selectEstadoRiesgoCrediticio").val() != "0") {
		riesgoCrediticioParaguay.estadoRiesgoCrediticio = {
			id: $("#selectEstadoRiesgoCrediticio").val()
		};
	}
	
	return riesgoCrediticioParaguay;
}

function inputGuardarOnClick(event) {
	var riesgoCrediticioParaguay = collectRiesgoCrediticioParaguayData();
	
	if (riesgoCrediticioParaguay.id != null) {
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/RiesgoCrediticioParaguayREST/actualizarDatosRiesgoCrediticioParaguay",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(riesgoCrediticioParaguay)
		}).then(function(data) {
			alert("Operación exitosa");
		});
	}
}