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
				
				$(".inputDia").val(fecha.getDate());
				$(".inputMes").val(meses[fecha.getMonth()]);
				$(".inputAno").val(fecha.getFullYear());
				$(".inputDepartamento").val(data.barrio.departamento.nombre);
				$(".inputDuracionContrato").val(data.nuevoPlan.duracion + " a�os");
				$(".inputNombre").val(data.nombre.toUpperCase() + " " + data.apellido.toUpperCase());
				
				$(".inputClienteNombre").val(data.nombre.toUpperCase());
				$(".inputClienteApellido").val(data.apellido.toUpperCase());
				
				if (data.tipoDocumento != null) {
					$(".divTipoDocumento" + data.tipoDocumento.id).text("X");
				}
				
				for (var i=0; i<data.documento.length; i++) {
					$(".divColumnaDigit" + (i + 1)).text(data.documento[i]);
				}
				
				$(".inputClienteSexo").val(data.sexo != null ? data.sexo.descripcion : "");
				$(".inputClienteFechaNacimiento").val(data.fechaNacimiento != null ? formatShortDate(data.fechaNacimiento) : "");
				$(".inputClienteTipoCliente").val("1");
				$(".inputClienteActividad").val("5");
				$(".inputClienteTelefonoContacto").val(data.telefonoContacto);
				
				$(".inputMID").val(data.mid);
				$(".inputNumeroChip").val(data.numeroChip);
				$(".inputMarca").val(data.producto.marca.nombre);
				$(".inputNumeroSerie").val(data.numeroSerie);
				$(".inputModelo").val(data.producto.descripcion);
				$(".inputCodigoBloqueo").val(data.numeroBloqueo);
				
				$(".inputDireccionCalle").val(data.direccionFacturaCalle != null ? data.direccionFacturaCalle.toUpperCase() : "");
				$(".inputDireccionNumero").val(data.direccionFacturaNumero);
				$(".inputDireccionBis").val(data.direccionFacturaBis ? "S�" : "");
				$(".inputDireccionApto").val(data.direccionFacturaApto);
				$(".inputDireccionBlock").val(data.direccionFacturaBlock);
				$(".inputDireccionManzana").val(data.direccionFacturaManzana);
				$(".inputDireccionSolar").val(data.direccionFacturaSolar);
				$(".inputDireccionLocalidad").val(data.direccionFacturaLocalidad != null ? data.direccionFacturaLocalidad.toUpperCase() : "");
				$(".inputDireccionDepartamento").val(data.direccionFacturaDepartamento != null ? data.direccionFacturaDepartamento.nombre : "");
				$(".inputDireccionCodigoPostal").val(data.direccionFacturaCodigoPostal);
				$(".inputDireccionObservaciones").val(data.direccionFacturaObservaciones != null ? data.direccionFacturaObservaciones.toUpperCase() : "");
				
				$(".inputDireccionFacturaCalle").val("IDEM");
//				$(".inputDireccionFacturaCalle").val(data.direccionFacturaCalle);
//				$(".inputDireccionFacturaNumero").val(data.direccionFacturaNumero);
//				$(".inputDireccionFacturaBis").val(data.direccionFacturaBis ? "S�" : "");
//				$(".inputDireccionFacturaApto").val(data.direccionFacturaApto);
//				$(".inputDireccionFacturaBlock").val(data.direccionFacturaBlock);
//				$(".inputDireccionFacturaManzana").val(data.direccionFacturaManzana);
//				$(".inputDireccionFacturaSolar").val(data.direccionFacturaSolar);
//				$(".inputDireccionFacturaLocalidad").val(data.direccionFacturaLocalidad);
//				$(".inputDireccionFacturaDepartamento").val(data.direccionFacturaDepartamento != null ? data.direccionFacturaDepartamento.nombre : "");
//				$(".inputDireccionFacturaCodigoPostal").val(data.direccionFacturaCodigoPostal);
//				$(".inputDireccionFacturaObservaciones").val(data.direccionFacturaObservaciones);
				
//				$(".inputUsuarioNombre").val(data.nombre);
//				$(".inputUsuarioApellido").val(data.nombre);
//				$(".inputUsuarioDocumento").val(data.documento);
//				$(".inputUsuarioEmail").val(data.email);
				
				$(".inputPlan").val(data.nuevoPlan.descripcion);
				$(".inputPromotor").val("1");
				$(".inputAyudaEconomica").val("S�");
//				$(".inputCantidadMinutos").val("Cant. minutos");
//				$(".inputDesde").val("Desde");
//				$(".inputHasta").val("Hasta");
				
//				$(".inputFiadorSolidarioNombre").val("Fiador solidario");
//				$(".inputFiadorSolidarioNumeroCuenta").val("Nro. cuenta");
//				$(".inputFiadorSolidarioDocumento").val("Documento");
//				$(".inputFiadorSolidarioTelefonoContacto").val("Tel");
//				$(".inputFiadorSolidarioDireccion").val("Dir.");
//				$(".inputFiadorSolidarioDepositoGarantia").val("Monto");
				
				$(".textareaObservaciones").val(
					"Se informa que la fecha de conexi�n ser� el pr�ximo "
				);
				
				$(".inputAgenteVentaNombre").val(data.empresa.nombreContrato);
				$(".inputAgenteVentaCodigo").val(data.empresa.codigoPromotor);
				$(".inputAgenteVentaSucursal").val(data.empresa.nombreSucursal);
				
				$(".inputVendedorNombre").val(data.vendedor != null ? data.vendedor.nombre.toUpperCase() : "");
				$(".inputVendedorDocumento").val(data.vendedor != null ? data.vendedor.documento : "");
				
				$(".inputControlCuentaCliente").val(formatShortDate(fecha));
				$(".inputControlCuentaBackoffice").val(
					data.usuario != null ? 
						data.usuario.nombre : 
						(usuario != null ? usuario.nombre.toUpperCase() : "")
				);
				
				$(".inputEspecificacionesNumeroContrato").val(data.numeroContrato);
				$(".inputEspecificacionesPlanComercial").val(data.nuevoPlan.abreviacion);
				$(".inputEspecificacionesConsumoMinimo").val(data.nuevoPlan.consumoMinimo);
				$(".inputEspecificacionesDuraccionContractual").val(data.nuevoPlan.duracion);
				$(".inputEspecificacionesPrecioMinutoHorarioNormal").val(data.nuevoPlan.precioMinutoDestinosAntelHorarioNormal);
				$(".inputEspecificacionesPrecioMinutoHorarioReducido").val(data.nuevoPlan.precioMinutoDestinosAntelHorarioReducido);
				$(".inputEspecificacionesRendimientoMinutosAntelHorarioNormal").val(data.nuevoPlan.rendimientoMinutosMensualDestinosAntelHorarioNormal);
				$(".inputEspecificacionesRendimientoMinutosAntelHorarioReducido").val(data.nuevoPlan.rendimientoMinutosMensualDestinosAntelHorarioReducido);
				$(".inputEspecificacionesPrecioMinutoOtrasOperadoras").val(data.nuevoPlan.precioMinutoOtrasOperadoras);
				$(".inputEspecificacionesRendimientoMinutosOtrasOperadoras").val(data.nuevoPlan.rendimientoMinutosMensualOtrasOperadoras);
				$(".inputEspecificacionesPrecioSMS").val(data.nuevoPlan.precioSms);
				$(".inputEspecificacionesIncluyeParaNavegacionCelular").val(data.nuevoPlan.montoNavegacionCelular);
				$(".inputEspecificacionesConsumoFueraBono").val(data.nuevoPlan.precioConsumoFueraBono);
				$(".inputEspecificacionesTopeFacturacionMensual").val(data.nuevoPlan.topeFacturacionMensualTraficoDatos);
				$(".inputEspecificacionesDestinosGratis").val(data.nuevoPlan.destinosGratis);
				$(".inputEspecificacionesMinutosGratisMovil").val(data.nuevoPlan.minutosGratisMesCelularesAntel);
				$(".inputEspecificacionesMinutosGratisCantidadCelulares").val(data.nuevoPlan.cantidadCelularesAntelMinutosGratis);
				$(".inputEspecificacionesSMSGratisMovil").val(data.nuevoPlan.smsGratisMesCelularesAntel);
				$(".inputEspecificacionesSMSGratisCantidadCelulares").val(data.nuevoPlan.cantidadCelularesAntelSmsGratis);
				$(".inputEspecificacionesMinutosGratisFijo").val(data.nuevoPlan.minutosGratisMesFijosAntel);
				$(".inputEspecificacionesMinutosGratisCantidadFijos").val(data.nuevoPlan.cantidadFijosAntelMinutosGratis);
				
				$(".inputEspecificacionesDepartamento").val(data.barrio.departamento.nombre);
				$(".inputEspecificacionesDia").val(fecha.getDate());
				$(".inputEspecificacionesMes").val(meses[fecha.getMonth()]);
				$(".inputEspecificacionesAno").val(fecha.getFullYear());
				
				$(".imgGarantiaLogo").attr("src", "/LogisticaWEB/Stream?fn=" + data.empresa.logoURL);
				$(".inputGarantiaEmpresa").val(data.empresa.nombreContrato);
				$(".inputGarantiaService").val(data.producto.empresaService != null ? data.producto.empresaService.nombre : "");
				$(".inputGarantiaProductoMarca").val(data.producto.marca.nombre);
				$(".inputGarantiaProductoModelo").val(data.producto.descripcion);
				$(".inputGarantiaNumeroSerie").val(data.numeroSerie);
				$(".inputGarantiaNombreApellido").val(data.nombre + ", " + data.apellido);
				$(".inputGarantiaMID").val(data.mid);
				$(".inputGarantiaDatosService").val(
					(data.producto.empresaService != null ? data.producto.empresaService.nombre : "")
					+ " DIR: " + (data.producto.empresaService != null ? data.producto.empresaService.direccion : "") 
					+ " TEL: " + (data.producto.empresaService != null ? data.producto.empresaService.telefono : "")
					+ " GARANTIA " + data.producto.marca.nombre
				);
				
				if (data.motivoCambioPlan != null) {
					$(".inputCambioPlanFecha").val(formatShortDate(fecha));
					$(".inputCambioPlanClienteNombre").val(data.nombre.toUpperCase() + " " + data.apellido.toUpperCase());
					$(".inputCambioPlanClienteDocumento").val(data.documento);
					$(".inputCambioPlanNumeroContrato").val(data.numeroContrato);
					$(".inputCambioPlanMid").val(data.mid);
					$(".inputCambioPlanPlan").val(data.tipoContratoDescripcion);
					$(".inputCambioPlanNuevoPlan").val(data.nuevoPlan.descripcion);
					$(".inputCambioPlanMotivosCambioPlan").val(data.motivoCambioPlan.descripcion);
//					$(".inputCambioPlanFechaVigencia").val(formatShortDate(fecha));
					$(".inputCambioPlanPrecioChip").val("");
					$(".inputCambioPlanBackofficeNombre").val(
						data.usuario != null ? data.usuario.nombre.toUpperCase() : ""
					);
				}
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