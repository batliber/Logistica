$(document).ready(init);

function init() {
	refinarForm();
	
	$("#divEliminarPlan").hide();
	
	TipoPlanDWR.list(
		{
			callback: function(data) {
				$("#selectPlanTipoPlan option").remove();
				
				var html =
					"<option id='0' value='0'>Seleccione...</option>";
				
				for (var i=0; i<data.length; i++) {
					html += "<option value='" + data[i].id + "'>" + data[i].descripcion + "</option>";
				}
				
				$("#selectPlanTipoPlan").append(html);
			}, async: false
		}
	);
	
	if (id != null) {
		PlanDWR.getById(
			id,
			{
				callback: function(data) {
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
					
					if (mode == __FORM_MODE_ADMIN) {
						$("#divEliminarPlan").show();
						$("#divButtonTitleSingleSize").attr("id", "divButtonTitleDoubleSize");
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
	
	$("#divLabelPlanDescripcion").addClass("requiredFormLabelExtraExtended");
	$("#divLabelPlanAbreviacion").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanConsumoMinimo").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanDuracion").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanPrecioMinutoDestinosAntelHorarioNormal").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanPrecioMinutoDestinosAntelHorarioReducido").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanRendimientoMinutosMensualDestinosAntelHorarioNormal").addClass("requiredFormLabelExtraExtended");
//	$("#divLabelPlanRendimientoMinutosMensualDestinosAntelHorarioReducido").addClass("requiredFormLabelExtraExtended");
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
		cantidadFijosAntelMinutosGratis: $("#inputPlanCantidadFijosAntelMinutosGratis").val()
	};
	
	if ($("#selectPlanTipoPlan").val() != 0) {
		plan.tipoPlan = {
			id: $("#selectPlanTipoPlan").val()
		};
	}
	
	if (id != null) {
		plan.id = id;
		
		PlanDWR.update(
			plan,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	} else {
		PlanDWR.add(
			plan,
			{
				callback: function(data) {
					alert("Operación exitosa");
					
					$("#inputEliminarPlan").prop("disabled", false);
				}, async: false
			}
		);
	}
}

function inputEliminarOnClick(event) {
	if ((id != null) && confirm("Se eliminará el Plan")) {
		var plan = {
			id: id
		};
		
		PlanDWR.remove(
			plan,
			{
				callback: function(data) {
					alert("Operación exitosa");
				}, async: false
			}
		);
	}
}