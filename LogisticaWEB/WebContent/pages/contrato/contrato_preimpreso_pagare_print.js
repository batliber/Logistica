var __VALOR_IVA = 1.22;
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

$(document).ready(function() {
	SeguridadDWR.getActiveUserData(
		{
			callback: function(data) {
				usuario = data;
			}, async: false
		}
	);
	
	ContratoDWR.getById(
		id,
		{
			callback: function(data) {
				var fecha = new Date();
				
				$(".inputValeNumero").val(null);
				$(".inputCapitalPrestado").val(formatDecimal(data.precio, 0));
				$(".inputInteresesCompensatorios").val(formatDecimal(data.intereses, 0));
				$(".inputGastosAdministracion").val(formatDecimal(data.gastosAdministrativosTotales, 0));
				$(".inputIVA").val(formatDecimal(data.precio * __VALOR_IVA, 0));
				$(".inputTotal").val(formatDecimal((data.precio + data.intereses + data.gastosAdministrativosTotales), 0));
				
				$(".inputDepartamento").val(data.direccionFacturaLocalidad != null ? data.direccionFacturaLocalidad : "");
//				$(".inputDia").val(fecha.getDate());
//				$(".inputMes").val(meses[fecha.getMonth()]);
//				$(".inputAno").val(fecha.getFullYear());
				
				// Se establece que la fecha del pagaré es la fecha de activación.
				var fechaActivarEn = data.fechaActivarEn;
				if (fechaActivarEn != null) {
					$(".inputDia").val(fechaActivarEn.getDate());
					$(".inputMes").val(meses[fechaActivarEn.getMonth()]);
					$(".inputAno").val(fechaActivarEn.getFullYear());
				}
				
				$(".inputCuotas").val(data.cuotas);
				$(".inputValorCuota").val(formatDecimal(data.valorCuota, 2));
				$(".inputFechaVencimientoPrimeraCuota").val(null);
				$(".inputFormaPago").val(null);
				
				$(".inputPorcentajeMora").val(null);
				
				$(".inputDomicilioAcreedor").val(null);
				
				$(".inputTasaEfectivaAnual").val(formatDecimal(data.valorTasaInteresEfectivaAnual * 100, 0));
				$(".inputTasaEfectivaMensual").val(formatDecimal(data.valorTasaInteresEfectivaAnual * 100 / 12, 0));
				$(".inputGastosAdministrativosUIPorCuota").val(formatDecimal(data.gastosAdministrativosTotales / data.valorUnidadIndexada / data.cuotas, 0));
				
				$(".inputCedulaIdentidad").val(null);
				$(".inputDomicilio").val(null);
				$(".inputLocalidadDepartamento").val(null);
				$(".inputFirma").val(null);
				$(".inputFirma").val(null);
				$(".inputAclaracion").val(null);
			}, async: false
		}
	);
});

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