package uy.com.amensg.logistica.robot;

public class LogisticaProxy {

	private IConnectionStrategy iConnectionStrategy = new ConnectionStrategyWebService();
	
	public void getSiguienteMidSinProcesar() {
		System.out.println(iConnectionStrategy.getSiguienteMidSinProcesar());
	}
	
	public void getSiguienteNumeroContratoSinProcesar() {
		System.out.println(iConnectionStrategy.getSiguienteNumeroContratoSinProcesar());
	}
	
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
	) {
		iConnectionStrategy.actualizarDatosMidContrato(
			direccion, 
			documentoTipo,
			documento, 
			fechaFinContrato, 
			localidad,
			codigoPostal,
			mid, 
			nombre, 
			tipoContratoCodigo,
			tipoContratoDescripcion,
			agente,
			equipo,
			numeroCliente,
			numeroContrato,
			estadoContrato
		);
	}
	
	public void actualizarDatosMidPrepago(
		String mesAno,
		String mid,
		String montoMesActual,
		String montoMesAnterior1,
		String montoMesAnterior2,
		String fechaActivacionKit,
		String agente
	) {
		iConnectionStrategy.actualizarDatosMidPrepago(
			mesAno, 
			mid, 
			montoMesActual, 
			montoMesAnterior1, 
			montoMesAnterior2,
			fechaActivacionKit,
			agente
		);
	}
	
	public void actualizarDatosMidListaVacia(String mid) {
		iConnectionStrategy.actualizarDatosMidListaVacia(
			mid
		);
	}
	
	public void actualizarDatosNumeroContratoListaVacia(String numeroContrato) {
		iConnectionStrategy.actualizarDatosNumeroContratoListaVacia(
			numeroContrato
		);
	}
	
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
		String email) {
		iConnectionStrategy.actualizarDatosPersona(
			 idCliente,
			 mid,
			 pais,
			 tipoDocumento,
			 documento,
			 apellido,
			 nombre,
			 razonSocial,
			 tipoCliente,
			 actividad,
			 fechaNacimiento,
			 sexo,
			 direccionCalle, 
			 direccionNumero,
			 direccionBis,
			 direccionApartamento,
			 direccionEsquina,
			 direccionBlock,
			 direccionManzana,
			 direccionSolar,
			 direccionObservaciones,
			 direccionLocalidad,
			 direccionCodigoPostal,
			 direccionCompleta,
			 distribuidor,
			 telefonosFijo,
			 telefonosAviso,
			 telefonosFax,
			 email
		);
	}
	
	public static void main(String[] args) {
		if (args[0].equals("getSiguienteMidSinProcesar")) {
			new LogisticaProxy().getSiguienteMidSinProcesar();
		} else if (args[0].equals("getSiguienteNumeroContratoSinProcesar")) {
			new LogisticaProxy().getSiguienteNumeroContratoSinProcesar();
		} else if (args[0].equals("actualizarDatosMid")) {
			if (args[1].equals("contrato")) {
				new LogisticaProxy().actualizarDatosMidContrato(
					args[2],
					args[3],
					args[4],
					args[5],
					args[6],
					args[7],
					args[8],
					args[9],
					args[10],
					args[11],
					args[12],
					args[13],
					args[14],
					args[15],
					args[16]
				);
			} else if (args[1].equals("prepago")) {
				new LogisticaProxy().actualizarDatosMidPrepago(
					args[2],
					args[3],
					args[4],
					args[5],
					args[6],
					args[7],
					args[8]
				);
			} else if (args[1].equals("listaVacia")) {
				new LogisticaProxy().actualizarDatosMidListaVacia(
					args[2]
				);
			}
		} else if (args[0].equals("actualizarDatosNumeroContrato")) {
			if (args[1].equals("listaVacia")) {
				new LogisticaProxy().actualizarDatosNumeroContratoListaVacia(
					args[2]
				);
			}
		} else if (args[0].equals("actualizarDatosPersona")) {
			new LogisticaProxy().actualizarDatosPersona(
				args[1],
				args[2],
				args[3],
				args[4],
				args[5],
				args[6],
				args[7],
				args[8],
				args[9],
				args[10],
				args[11],
				args[12],
				args[13],
				args[14],
				args[15],
				args[16],
				args[17],
				args[18],
				args[19],
				args[20],
				args[21],
				args[22],
				args[23],
				args[24],
				args[25],
				args[26],
				args[27],
				args[28],
				args[29]
			);
		}
	}
}