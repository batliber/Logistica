package uy.com.amensg.riesgoCrediticio.robot;

public class RiesgoCrediticioProxy {

	private IConnectionStrategy iConnectionStrategy = new ConnectionStrategyWebService();
	
	public void getSiguienteDocumentoParaControlar() {
		System.out.println(iConnectionStrategy.getSiguienteDocumentoParaControlar());
	}
	
	public void getSiguienteDocumentoParaControlarRiesgoOnLine() {
		System.out.println(iConnectionStrategy.getSiguienteDocumentoParaControlarRiesgoOnLine());
	}
	
	public void actualizarDatosRiesgoCrediticioACM(
		String riesgoCrediticioId,
		String empresaId,
		String documento,
		String fechaCelular,
		String deudaCelular,
		String riesgoCrediticioCelular,
		String contratosCelular,
		String contratosSolaFirmaCelular,
		String contratosGarantiaCelular,
		String saldoAyudaEconomicaCelular,
		String numeroClienteFijo,
		String nombreClienteFijo,
		String estadoDeudaClienteFijo,
		String numeroClienteMovil) {
		iConnectionStrategy.actualizarDatosRiesgoCrediticioACM(
			riesgoCrediticioId,
			empresaId,
			documento,
			fechaCelular,
			deudaCelular,
			riesgoCrediticioCelular,
			contratosCelular,
			contratosSolaFirmaCelular,
			contratosGarantiaCelular,
			saldoAyudaEconomicaCelular,
			numeroClienteFijo,
			nombreClienteFijo,
			estadoDeudaClienteFijo,
			numeroClienteMovil
		);
	}
	
	public void actualizarDatosRiesgoCrediticioBCU(
		String riesgoCrediticioId,
		String empresaId,
		String documento,
		String periodo,
		String nombreCompleto,
		String actividad,
		String vigente,
		String vigenteNoAutoliquidable,
		String garantiasComputables,
		String garantiasNoComputables,
		String castigadoPorAtraso,
		String castigadoPorQuitasYDesistimiento,
		String previsionesTotales,
		String contingencias,
		String otorgantesGarantias,
		String sinDatos
	) {
		iConnectionStrategy.actualizarDatosRiesgoCrediticioBCU(
			riesgoCrediticioId,
			empresaId,
			documento,
			periodo,
			nombreCompleto,
			actividad,
			vigente,
			vigenteNoAutoliquidable,
			garantiasComputables,
			garantiasNoComputables,
			castigadoPorAtraso,
			castigadoPorQuitasYDesistimiento,
			previsionesTotales,
			contingencias,
			otorgantesGarantias,
			sinDatos
		);
	}
	
	public void actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
		String riesgoCrediticioId,
		String empresaId,
		String documento,
		String institucionFinanciera,
		String calificacion,
		String vigente,
		String vigenteNoAutoliquidable,
		String previsionesTotales,
		String contingencias
	) {
		iConnectionStrategy.actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
			riesgoCrediticioId,
			empresaId,
			documento,
			institucionFinanciera,
			calificacion,
			vigente,
			vigenteNoAutoliquidable,
			previsionesTotales,
			contingencias
		);
	}
	
	public static void main(String[] args) {
		if (args[0].equals("getSiguienteDocumentoParaControlar")) {
			new RiesgoCrediticioProxy().getSiguienteDocumentoParaControlar();
		} else if (args[0].equals("getSiguienteDocumentoParaControlarRiesgoOnLine")) {
			new RiesgoCrediticioProxy().getSiguienteDocumentoParaControlarRiesgoOnLine();
		} else if (args[0].equals("actualizarDatosRiesgoCrediticioACM")) {
			new RiesgoCrediticioProxy().actualizarDatosRiesgoCrediticioACM(
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
				args[14]
			);
		} else if (args[0].equals("actualizarDatosRiesgoCrediticioBCU")) {
			new RiesgoCrediticioProxy().actualizarDatosRiesgoCrediticioBCU(
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
				args[16]
			);
		} else if (args[0].equals("actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera")) {
			new RiesgoCrediticioProxy().actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
				args[1],
				args[2],
				args[3],
				args[4],
				args[5],
				args[6],
				args[7],
				args[8],
				args[9]
			);
		}
	}
}