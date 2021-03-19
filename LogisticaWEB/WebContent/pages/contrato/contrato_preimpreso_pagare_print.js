var __VALOR_IVA = 0.22;
var __OFFSET_DIAS_FECHA_VENCIMIENTO_PRIMERA_CUOTA = 30;

var meses = [
	"Enero",
	"Febrero",
	"Marzo",
	"Abril",
	"Mayo",
	"Junio",
	"Julio",
	"Agosto",
	"Setiembre",
	"Octubre",
	"Noviembre",
	"Diciembre"
];
var usuario = null;

$(document).ready(init);

function init() {
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/SeguridadREST/getActiveUserData",   
	}).then(function(data) {
		usuario = data;
	
		$.ajax({
			url: "/LogisticaWEB/RESTFacade/ContratoREST/getById/" + id
		}).then(function(data) { 
			var fecha = new Date();
			
			
			$(".inputValeNumero").val(data.numeroVale);
			$(".inputCapitalPrestado").val(formatDecimal(data.precio, 2));
			
			var interesesSinIVA = data.intereses / (1 + __VALOR_IVA);
			$(".inputInteresesCompensatorios").val(formatDecimal(interesesSinIVA, 2));
			
			var gastosAdministrativosSinIVA = data.gastosAdministrativosTotales / (1 + __VALOR_IVA);
			$(".inputGastosAdministracion").val(formatDecimal(gastosAdministrativosSinIVA, 2));
			
			var iva = (interesesSinIVA + gastosAdministrativosSinIVA) * __VALOR_IVA;
			$(".inputIVA").val(formatDecimal(iva, 2));
			
			var total = data.precio + interesesSinIVA + gastosAdministrativosSinIVA + iva;
			$(".inputTotal").val(formatDecimal(total, 2));
			
			$(".inputDepartamento").val(
				data.direccionEntregaDepartamento != null ? data.direccionEntregaDepartamento.nombre : ""
			);
			
			// Se establece que la fecha del pagaré es la fecha de activación.
			var fechaActivarEn = data.fechaActivarEn;
			if (fechaActivarEn != null) {
				$(".inputDia").val(new Date(fechaActivarEn).getDate());
				$(".inputMes").val(meses[new Date(fechaActivarEn).getMonth()]);
				$(".inputAno").val(new Date(fechaActivarEn).getFullYear());
				
//				var fechaVencimientoPrimeraCuota = 
//				new Date(fechaActivarEn.getTime() + (__OFFSET_DIAS_FECHA_VENCIMIENTO_PRIMERA_CUOTA * 24 * 60 * 60 * 1000));
				
				var fechaVencimientoPrimeraCuota = 
					new Date(
						new Date(fechaActivarEn).getFullYear(), 
						new Date(fechaActivarEn).getMonth() + 1, 
						new Date(fechaActivarEn).getDate(), 0, 0, 0, 0
					);
				$(".inputFechaVencimientoPrimeraCuota").val(formatShortDate(fechaVencimientoPrimeraCuota));
			}
			
			$(".inputAgente").val(data.empresa.nombreContrato);
			$(".inputCuotas").val(data.cuotas);
			$(".inputValorCuota").val(formatDecimal(data.valorCuota, 2));
			
			$(".inputFormaPagoCuota").val(data.tipoProducto.formaPagoCuota.descripcion);
			
			$(".inputPorcentajeMora").val(formatDecimal(((data.valorTasaInteresEfectivaAnual / 1.55) * 1.8 * 100), 2));
			
			$(".inputDomicilioAcreedor").val(data.empresa.direccion);
//			$(".inputDomicilioAcreedor").val("Av. Luis Alberto de Herrera 1248 - Torre B - Piso 18");
			
			$(".inputTasaEfectivaAnual").val(formatDecimal(data.valorTasaInteresEfectivaAnual * 100, 2));
			
			var diasPorMes = 30;
			var diasPorAno = 360;
			var tasaEfectivaMensual = Math.pow(1 + data.valorTasaInteresEfectivaAnual, diasPorMes / diasPorAno) - 1;
			
			$(".inputTasaEfectivaMensual").val(formatDecimal(tasaEfectivaMensual * 100, 2));
			
			var gastosAdministrativosUIPorCuota =
				gastosAdministrativosSinIVA / data.valorUnidadIndexada / data.cuotas;
			$(".inputGastosAdministrativosUIPorCuota").val(formatDecimal(gastosAdministrativosUIPorCuota, 2));
			
			$(".inputCI").val(null);
			$(".inputDomicilio").val(null);
			$(".inputLocalidadDepartamento").val(null);
			$(".inputFirma").val(null);
			$(".inputAclaracion").val(null);
			
			$(".inputCI").hide();
			$(".inputDomicilio").hide();
			$(".inputLocalidadDepartamento").hide();
			$(".inputFirma").hide();
			$(".inputAclaracion").hide();
	    });
	});
}

function inputImprimirOnClick() {
	$("input[type='text']").css("background-color", "white");
	$("textarea").css("background-color", "white");
	$(".divA4Sheet").css("border", "none");
	$(".divPrintingButtonBar").hide();
	
	window.print();
}

function inputCancelarOnClick() {
	window.close();
}