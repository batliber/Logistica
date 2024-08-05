package uy.com.amensg.logistica.robot;

public class LogisticaProxy {

	private static int __location_parameter_index = 0;
	private static int __method_parameter_index = 1;
	private static int __registry_type_parameter_index = 2;
	
	private IConnectionStrategy iConnectionStrategy = new ConnectionStrategyWebService();
	
	public void getSiguienteMidSinProcesar(String wsdlFileName) {
		System.out.println(iConnectionStrategy.getSiguienteMidSinProcesar(wsdlFileName));
	}
	
	public void getSiguienteNumeroContratoSinProcesar(String wsdlFileName) {
		System.out.println(iConnectionStrategy.getSiguienteNumeroContratoSinProcesar(wsdlFileName));
	}
	
	public void actualizarDatosMidContrato(
		String wsdlFileName,
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
			wsdlFileName, 
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
		String wsdlFileName,
		String mesAno,
		String mid,
		String montoMesActual,
		String montoMesAnterior1,
		String montoMesAnterior2,
		String fechaActivacionKit,
		String agente
	) {
		iConnectionStrategy.actualizarDatosMidPrepago(
			wsdlFileName, 
			mesAno, 
			mid, 
			montoMesActual, 
			montoMesAnterior1, 
			montoMesAnterior2,
			fechaActivacionKit,
			agente
		);
	}
	
	public void actualizarDatosMidListaVacia(String wsdlFileName, String mid) {
		iConnectionStrategy.actualizarDatosMidListaVacia(
			wsdlFileName, mid
		);
	}
	
	public void actualizarDatosNumeroContratoListaVacia(String wsdlFileName, String numeroContrato) {
		iConnectionStrategy.actualizarDatosNumeroContratoListaVacia(
			wsdlFileName, numeroContrato
		);
	}
	
	public void actualizarDatosMidListaNegra(String wsdlFileName, String mid) {
		iConnectionStrategy.actualizarDatosMidListaNegra(
			wsdlFileName, mid
		);
	}
	
	public void actualizarDatosMidNegociando(String wsdlFileName, String mid) {
		iConnectionStrategy.actualizarDatosMidNegociando(
			wsdlFileName, mid
		);
	}
	
	public void actualizarDatosMidNoLlamar(String wsdlFileName, String mid) {
		iConnectionStrategy.actualizarDatosMidNoLlamar(
			wsdlFileName, mid
		);
	}
	
	public void actualizarDatosPersona(
		String wsdlFileName,
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
			wsdlFileName, 
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
		String wsdlFileName = "";
		if (args[__location_parameter_index].equals("amensg")) {
			wsdlFileName = "LogisticaWebService-amensg.wsdl";
		} else if (args[__location_parameter_index].equals("cargamas")) {
			wsdlFileName = "LogisticaWebService-cargamas.wsdl";
		} else if (args[__location_parameter_index].equals("uruentregas")) {
			wsdlFileName = "LogisticaWebService-uruentregas.wsdl";
		} else if (args[__location_parameter_index].equals("phonehouse")) {
			wsdlFileName = "LogisticaPHWebService-phonehouse.wsdl";
		} else if (args[__location_parameter_index].equals("phonehousedev")) {
			wsdlFileName = "LogisticaPHWebService-amensg.wsdl";
		} else if (args[__location_parameter_index].equals("agentesdeventas")) {
			wsdlFileName = "LogisticaWebService-agentesdeventas.wsdl";
		}
		
		if (args[__method_parameter_index].equals("getSiguienteMidSinProcesar")) {
			new LogisticaProxy().getSiguienteMidSinProcesar(wsdlFileName);
		} else if (args[__method_parameter_index].equals("getSiguienteNumeroContratoSinProcesar")) {
			new LogisticaProxy().getSiguienteNumeroContratoSinProcesar(wsdlFileName);
		} else if (args[__method_parameter_index].equals("actualizarDatosMid")) {
			if (args[__registry_type_parameter_index].equals("contrato")) {
				new LogisticaProxy().actualizarDatosMidContrato(
					wsdlFileName,
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
					args[17]
				);
			} else if (args[__registry_type_parameter_index].equals("prepago")) {
				new LogisticaProxy().actualizarDatosMidPrepago(
					wsdlFileName,
					args[3],
					args[4],
					args[5],
					args[6],
					args[7],
					args[8],
					args[9]
				);
			} else if (args[__registry_type_parameter_index].equals("listaVacia")) {
				new LogisticaProxy().actualizarDatosMidListaVacia(
					wsdlFileName, args[3]
				);
			} else if (args[__registry_type_parameter_index].equals("listaNegra")) {
				new LogisticaProxy().actualizarDatosMidListaNegra(
					wsdlFileName, args[3]
				);
			} else if (args[__registry_type_parameter_index].equals("negociando")) {
				new LogisticaProxy().actualizarDatosMidNegociando(
					wsdlFileName, args[3]
				);
			} else if (args[__registry_type_parameter_index].equals("noLlamar")) {
				new LogisticaProxy().actualizarDatosMidNoLlamar(
					wsdlFileName, args[3]
				);
			}
		} else if (args[__method_parameter_index].equals("actualizarDatosNumeroContrato")) {
			if (args[1].equals("listaVacia")) {
				new LogisticaProxy().actualizarDatosNumeroContratoListaVacia(
					wsdlFileName, args[3]
				);
			}
		} else if (args[__method_parameter_index].equals("actualizarDatosPersona")) {
			new LogisticaProxy().actualizarDatosPersona(
				wsdlFileName,
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
				args[29],
				args[30]
			);
		}
	}
}