$(document).ready(init);

function init() {
	refinarForm();
	
	$("#divEliminarPlan").hide();
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/TipoPlanREST/list"
    }).then(function(data) { 
		fillSelect(
			"selectPlanTipoPlan", 
			data,
			"id", 
			"descripcion"
		);
    }).then(function(data) { 
		if (id != null) {
			$.ajax({
		        url: "/LogisticaWEB/RESTFacade/PlanREST/getById/" + id
		    }).then(function(data) { 
				if (data.tipoPlan != null) {
					$("#selectPlanTipoPlan").val(data.tipoPlan.id);
				}
				
				$("#inputPlanDescripcion").val(data.descripcion);
				$("#inputPlanAbreviacion").val(data.abreviacion);
				$("#inputPlanConsumoMinimo").val(data.consumoMinimo);
				$("#inputPlanDuracion").val(data.duracion);
				$("#inputPlanPrecioMinutoDestinosAntelHorarioNormal").val(data.precioMinutoDestinosAntelHorarioNormal);
				$("#inputPlanPrecioMinutoDestinosAntelHorarioReducido").val(data.precioMinutoDestinosAntelHorarioReducido);
				$("#inputPlanRendimientoMinutosMensualDestinosAntelHorarioNormal").val(data.rendimientoMinutosMensualDestinosAntelHorarioNormal);
				$("#inputPlanRendimientoMinutosMensualDestinosAntelHorarioReducido").val(data.rendimientoMinutosMensualDestinosAntelHorarioReducido);
				$("#inputPlanPrecioMinutoNumerosAmigos").val(data.precioMinutoNumerosAmigos);
				$("#inputPlanPrecioMinutoOtrasOperadoras").val(data.precioMinutoOtrasOperadoras);
				$("#inputPlanRendimientoMinutosMensualOtrasOperadoras").val(data.rendimientoMinutosMensualOtrasOperadoras);
				$("#inputPlanPrecioSms").val(data.precioSms);
				$("#inputPlanMontoNavegacionCelular").val(data.montoNavegacionCelular);
				$("#inputPlanPrecioConsumoFueraBono").val(data.precioConsumoFueraBono);
				$("#inputPlanTopeFacturacionMensualTraficoDatos").val(data.topeFacturacionMensualTraficoDatos);
				$("#inputPlanDestinosGratis").val(data.destinosGratis);
				$("#inputPlanMinutosGratisMesCelularesAntel").val(data.minutosGratisMesCelularesAntel);
				$("#inputPlanCantidadCelularesAntelMinutosGratis").val(data.cantidadCelularesAntelMinutosGratis);
				$("#inputPlanSmsGratisMesCelularesAntel").val(data.smsGratisMesCelularesAntel);
				$("#inputPlanCantidadCelularesAntelSmsGratis").val(data.cantidadCelularesAntelSmsGratis);
				$("#inputPlanMinutosGratisMesFijosAntel").val(data.minutosGratisMesFijosAntel);
				$("#inputPlanCantidadFijosAntelMinutosGratis").val(data.cantidadFijosAntelMinutosGratis);
				$("#inputPlanPiePagina").val(data.piePagina);
				$("#inputPlanBeneficioIncluidoEnLlamadas").prop("checked", data.beneficioIncluidoEnLlamadas);
				
				if (mode == __FORM_MODE_ADMIN) {
					$("#divEliminarPlan").show();
					$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
				}
			});
		}
    });
}

function refinarForm() {
	hideField("planPrecioMinutoDestinosAntelHorarioReducido");
	hideField("planRendimientoMinutosMensualDestinosAntelHorarioReducido");
	hideField("planPiePagina");
	
	if (mode == __FORM_MODE_ADMIN) {
		
	} else if (mode == __FORM_MODE_USER) {
		
	}
	
	$("#divLabelPlanDescripcion").addClass("requiredFormLabelExtraExtended");
	$("#divLabelPlanAbreviacion").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanConsumoMinimo").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanDuracion").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanPrecioMinutoDestinosAntelHorarioNormal").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanPrecioMinutoDestinosAntelHorarioReducido").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanRendimientoMinutosMensualDestinosAntelHorarioNormal").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanRendimientoMinutosMensualDestinosAntelHorarioReducido").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanPrecioMinutoNumerosAmigos").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanPrecioMinutoOtrasOperadoras").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanRendimientoMinutosMensualOtrasOperadoras").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanPrecioSms").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanMontoNavegacionCelular").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanPrecioConsumoFueraBono").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanTopeFacturacionMensualTraficoDatos").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanDestinosGratis").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanMinutosGratisMesCelularesAntel").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanCantidadCelularesAntelMinutosGratis").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanSmsGratisMesCelularesAntel").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanCantidadCelularesAntelSmsGratis").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanMinutosGratisMesFijosAntel").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanCantidadFijosAntelMinutosGratis").addClass("requiredFormLabelExtraExtended");
}

function checkRequiredFields() {
	var result = true;
	
	var requiredLabelsDIVs = $(".requiredFormLabelExtraExtended");
	for (var i=0; i < requiredLabelsDIVs.length; i++) {
		var requiredLabelDIV = $(requiredLabelsDIVs[i]);
		var requiredLabelDIVId = $(requiredLabelDIV).attr("id");
		var valueElementId = requiredLabelDIVId.replace("Label", "");
		var valueElement = $("#" + valueElementId).children()[0];
		var valueElementSelected = $(valueElement);
		var valueElementTagName = valueElement.tagName;
		
		if (valueElementTagName == "INPUT") {
			if (valueElementSelected.val() == null || valueElementSelected.val() == "") {
				requiredLabelDIV.css("color", "red");
				
				result = result && false;
			} else {
				requiredLabelDIV.css("color", "black");
			}
		} else if (valueElementTagName == "SELECT") {
			if (valueElementSelected.val() == 0) {
				requiredLabelDIV.css("color", "red");
				
				result = result && false;
			} else {
				requiredLabelDIV.css("color", "black");
			}
		}
	}
	
	return result;
}

function inputGuardarOnClick(event) {
	if (!checkRequiredFields()) {
		alert("Información incompleta.");
		return;
	}
	
	var plan = {
		descripcion: $("#inputPlanDescripcion").val(),
		abreviacion: $("#inputPlanAbreviacion").val(),
		consumoMinimo: $("#inputPlanConsumoMinimo").val(),
		duracion: $("#inputPlanDuracion").val(),
		precioMinutoDestinosAntelHorarioNormal: $("#inputPlanPrecioMinutoDestinosAntelHorarioNormal").val(),
		precioMinutoDestinosAntelHorarioReducido: $("#inputPlanPrecioMinutoDestinosAntelHorarioReducido").val(),
		rendimientoMinutosMensualDestinosAntelHorarioNormal: $("#inputPlanRendimientoMinutosMensualDestinosAntelHorarioNormal").val(),
		rendimientoMinutosMensualDestinosAntelHorarioReducido: $("#inputPlanRendimientoMinutosMensualDestinosAntelHorarioReducido").val(),
		precioMinutoNumerosAmigos: $("#inputPlanPrecioMinutoNumerosAmigos").val(),
		precioMinutoOtrasOperadoras: $("#inputPlanPrecioMinutoOtrasOperadoras").val(),
		rendimientoMinutosMensualOtrasOperadoras: $("#inputPlanRendimientoMinutosMensualOtrasOperadoras").val(),
		precioSms: $("#inputPlanPrecioSms").val(),
		montoNavegacionCelular: $("#inputPlanMontoNavegacionCelular").val(),
		precioConsumoFueraBono: $("#inputPlanPrecioConsumoFueraBono").val(),
		topeFacturacionMensualTraficoDatos: $("#inputPlanTopeFacturacionMensualTraficoDatos").val(),
		destinosGratis: $("#inputPlanDestinosGratis").val(),
		minutosGratisMesCelularesAntel: $("#inputPlanMinutosGratisMesCelularesAntel").val(),
		cantidadCelularesAntelMinutosGratis: $("#inputPlanCantidadCelularesAntelMinutosGratis").val(),
		smsGratisMesCelularesAntel: $("#inputPlanSmsGratisMesCelularesAntel").val(),
		cantidadCelularesAntelSmsGratis: $("#inputPlanCantidadCelularesAntelSmsGratis").val(),
		minutosGratisMesFijosAntel: $("#inputPlanMinutosGratisMesFijosAntel").val(),
		cantidadFijosAntelMinutosGratis: $("#inputPlanCantidadFijosAntelMinutosGratis").val(),
		piePagina: $("#inputPlanPiePagina").val(),
		beneficioIncluidoEnLlamadas: $("#inputPlanBeneficioIncluidoEnLlamadas").prop("checked")
	};
	
	if ($("#selectPlanTipoPlan").val() != 0) {
		plan.tipoPlan = {
			id: $("#selectPlanTipoPlan").val()
		};
	}
	
	if (id != null) {
		plan.id = id;
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/PlanREST/update",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(plan)
	    }).then(function(data) {
			alert("Operación exitosa");
		});
	} else {
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/PlanREST/add",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(plan)
	    }).then(function(data) {
	    	if (data != null) {
	    		alert("Operación exitosa");
					
	    		("#inputEliminarPlan").prop("disabled", false);
	    	} else {
	    		alert("Error en la operación");
	    	}
		});
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Plan")) {
		var plan = {
			id: id
		};
		
		$.ajax({
	        url: "/LogisticaWEB/RESTFacade/PlanREST/remove",
	        method: "POST",
	        contentType: 'application/json',
	        data: JSON.stringify(plan)
	    }).then(function(data) {
			alert("Operación exitosa");
		});
	}
}