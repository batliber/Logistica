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

$(document).ready(function() {
	ContratoDWR.getById(
		id,
		{
			callback: function(data) {
				$("#spanDia").text(data.fechaVenta.getDate());
				$("#spanMes").text(meses[data.fechaVenta.getMonth()]);
				$("#spanAno").text(data.fechaVenta.getFullYear());
				$("#spanDepartamento").text(data.barrio.departamento.nombre);
				$("#spanDuracionContrato").text("N años");
				$("#spanNombre").text(data.nombre);
				$("#spanClienteNombre").text(data.nombre);
				$("#spanClienteApellido").text(data.nombre);
				
				for (var i=0; i<data.documento.length; i++) {
					$($(".divColumnaDigit")[i]).text(data.documento[i]);
				}
				
				$("#spanMID").text(data.mid);
				$("#spanNumeroChip").text("Chip");
				$("#spanMarca").text(data.producto.descripcion);
				$("#spanNumeroSerie").text(data.numeroSerie);
				$("#spanModelo").text(data.producto.descripcion);
				$("#spanCodigoBloqueo").text("Cod. Bloqueo");
				$("#spanDireccionCalle").text(data.direccionEntrega);
				$("#spanDireccionNumero").text(data.direccionEntrega);
				$("#spanDireccionBis").text(data.direccionEntrega);
				$("#spanDireccionApto").text(data.direccionEntrega);
				$("#spanDireccionBlock").text(data.direccionEntrega);
				$("#spanDireccionManzana").text(data.direccionEntrega);
				$("#spanDireccionSolar").text(data.direccionEntrega);
				$("#spanDireccionLocalidad").text(data.barrio.departamento.nombre);
				$("#spanDireccionDepartamento").text(data.barrio.departamento.nombre);
				$("#spanDireccionCodigoPostal").text(data.codigoPostal);
				$("#spanDireccionObservaciones").text("Obs.");
				$("#spanDireccionFacturaCalle").text(data.direccionFactura);
				$("#spanDireccionFacturaNumero").text(data.direccionFactura);
				$("#spanDireccionFacturaBis").text(data.direccionFactura);
				$("#spanDireccionFacturaApto").text(data.direccionFactura);
				$("#spanDireccionFacturaBlock").text(data.direccionFactura);
				$("#spanDireccionFacturaManzana").text(data.direccionFactura);
				$("#spanDireccionFacturaSolar").text(data.direccionFactura);
				$("#spanDireccionFacturaLocalidad").text(data.barrio.departamento.nombre);
				$("#spanDireccionFacturaDepartamento").text(data.barrio.departamento.nombre);
				$("#spanDireccionFacturaCodigoPostal").text(data.codigoPostal);
				$("#spanDireccionFacturaObservaciones").text("Obs.");
				$("#spanUsuarioNombre").text(data.nombre);
				$("#spanUsuarioApellido").text(data.nombre);
				$("#spanUsuarioDocumento").text(data.documento);
				$("#spanUsuarioEmail").text(data.email);
				$("#spanPlan").text(data.nuevoPlan);
				$("#spanPromotor").text(data.empresa.nombre);
				$("#spanAyudaEconomica").text("Sí");
				$("#spanCantidadMinutos").text("Cant. minutos");
				$("#spanDesde").text("Desde");
				$("#spanHasta").text("Hasta");
				$("#spanFiadorSolidarioNombre").text("Fiador solidario");
				$("#spanFiadorSolidarioNumeroCuenta").text("Nro. cuenta");
				$("#spanFiadorSolidarioDocumento").text("Documento");
				$("#spanFiadorSolidarioTelefonoContacto").text("Tel");
				$("#spanFiadorSolidarioDireccion").text("Dir.");
				$("#spanFiadorSolidarioDepositoGarantia").text("Monto");
				$("#spanObservaciones").text(data.observaciones);
				$("#spanAgenteVentaNombre").text(data.empresa.nombre);
				$("#spanAgenteVentaCodigo").text(data.empresa.id);
				$("#spanAgenteVentaSucursal").text("Sucursal");
				$("#spanVendedorNombre").text(data.vendedor.nombre);
				$("#spanVendedorDocumento").text("Documento");
				$("#spanControlCuentaCliente").text(formatShortDate(data.fechaVenta));
			}, async: false
		}
	);
});