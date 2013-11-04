package uy.com.amensg.logistica.robot;

public class LogisticaProxy {

	private IConnectionStrategy iConnectionStrategy = new ConnectionStrategyDirect();
	
	public void getSiguienteMidSinProcesar() {
		System.out.println(iConnectionStrategy.getSiguienteMidSinProcesar());
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
		String equipo
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
			equipo
		);
	}
	
	public void actualizarDatosMidPrepago(
		String mesAno,
		String mid,
		String montoMesActual,
		String montoMesAnterior1,
		String montoMesAnterior2
	) {
		iConnectionStrategy.actualizarDatosMidPrepago(
			mesAno, 
			mid, 
			montoMesActual, 
			montoMesAnterior1, 
			montoMesAnterior2
		);
	}
	
	public void actualizarDatosMidListaVacia(String mid) {
		iConnectionStrategy.actualizarDatosMidListaVacia(
			mid
		);
	}
	
	public static void main(String[] args) {
		if (args[0].equals("getSiguienteMidSinProcesar")) {
			new LogisticaProxy().getSiguienteMidSinProcesar();
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
					args[13]
				);
			} else if (args[1].equals("prepago")) {
				new LogisticaProxy().actualizarDatosMidPrepago(
					args[2],
					args[3],
					args[4],
					args[5],
					args[6]
				);
			} else if (args[1].equals("listaVacia")) {
				new LogisticaProxy().actualizarDatosMidListaVacia(
					args[2]
				);
			}
		}
	}
}