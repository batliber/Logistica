package uy.com.amensg.riesgoCrediticio.robot;

public class RiesgoCrediticioProxy {

	private IConnectionStrategy iConnectionStrategy = new ConnectionStrategyWebService();
	
	public void getSiguienteDocumentoParaControlar() {
		System.out.println(iConnectionStrategy.getSiguienteDocumentoParaControlar());
	}
	
	public void actualizarDatosRiesgoCrediticioACM(
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
		String otorgantesGarantias
	) {
		iConnectionStrategy.actualizarDatosRiesgoCrediticioBCU(
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
			otorgantesGarantias
		);
	}
	
	public void actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
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
				args[13]
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
				args[14]
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
				args[8]
			);
		}
	}
}