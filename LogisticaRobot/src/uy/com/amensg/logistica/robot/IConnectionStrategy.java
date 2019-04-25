package uy.com.amensg.logistica.robot;

public interface IConnectionStrategy {

	public String getSiguienteMidSinProcesar();
	
	public String getSiguienteNumeroContratoSinProcesar();
	
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
		String tipoContratoDescripcion,
		String agente,
		String equipo,
		String numeroCliente,
		String numeroContrato,
		String estadoContrato
	);
	
	public void actualizarDatosMidPrepago(
		String mesAno,
		String mid,
		String montoMesActual,
		String montoMesAnterior1,
		String montoMesAnterior2,
		String fechaActivacionKit,
		String agente
	);

	public void actualizarDatosMidListaVacia(
		String mid
	);
	
	public void actualizarDatosNumeroContratoListaVacia(
		String numeroContrato
	);

	public void actualizarDatosPersona(
		String idCliente,
		String mid,
		String pais,
		String tipoDocumento,
		String documento,
		String apellido,
		String nombre,
		String razonSocial,
		String tipoCliente,
		String actividad,
		String fechaNacimiento,
		String sexo,
		String direccionCalle, 
		String direccionNumero,
		String direccionBis,
		String direccionApartamento,
		String direccionEsquina,
		String direccionBlock,
		String direccionManzana,
		String direccionSolar,
		String direccionObservaciones,
		String direccionLocalidad,
		String direccionCodigoPostal,
		String direccionCompleta,
		String distribuidor,
		String telefonosFijo,
		String telefonosAviso,
		String telefonosFax,
		String email
	);
}