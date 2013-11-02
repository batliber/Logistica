package uy.com.amensg.logistica.robot;

public interface IConnectionStrategy {

	public String getSiguienteMidSinProcesar();
	
	public void actualizarDatosMidContrato(
		String direccion,
		String documentoTipo,
		String documento,
		String fechaFinContrato,
		String localidad,
		String codigoPostal,
		String mid,
		String nombre,
		String tipoContratoCodigo,
		String tipoContratoDescripcion
	);
	
	public void actualizarDatosMidPrepago(
		String mesAno,
		String mid,
		String montoMesActual,
		String montoMesAnterior1,
		String montoMesAnterior2
	);

	public void actualizarDatosMidListaVacia(
		String mid
	);
}