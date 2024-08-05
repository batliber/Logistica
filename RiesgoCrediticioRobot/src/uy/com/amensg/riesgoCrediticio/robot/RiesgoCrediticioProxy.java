package uy.com.amensg.riesgoCrediticio.robot;

public class RiesgoCrediticioProxy {

	private static int __location_parameter_index = 0;
	private static int __method_parameter_index = 1;
	
	private IConnectionStrategy iConnectionStrategy = new ConnectionStrategyWebService();
	
	public void getSiguienteDocumentoParaControlar(String wsdlFileName) {
		System.out.println(iConnectionStrategy.getSiguienteDocumentoParaControlar(wsdlFileName));
	}
	
	public void getSiguienteDocumentoParaControlarRiesgoOnLine(String wsdlFileName) {
		System.out.println(iConnectionStrategy.getSiguienteDocumentoParaControlarRiesgoOnLine(wsdlFileName));
	}
	
	public void actualizarDatosRiesgoCrediticioACM(
		String wsdlFileName,
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
			wsdlFileName,
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
		String wsdlFileName,
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
			wsdlFileName,
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
		String wsdlFileName,
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
			wsdlFileName,
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
		String wsdlFileName = "";
		if (args[__location_parameter_index].equals("amensg")) {
			wsdlFileName = "RiesgoCrediticioWebService-amensg.wsdl";
		} else if (args[__location_parameter_index].equals("cargamas")) {
			wsdlFileName = "RiesgoCrediticioWebService-cargamas.wsdl";
		} else if (args[__location_parameter_index].equals("uruentregas")) {
			wsdlFileName = "RiesgoCrediticioWebService-uruentregas.wsdl";
		}
		
		if (args[__method_parameter_index].equals("getSiguienteDocumentoParaControlar")) {
			new RiesgoCrediticioProxy().getSiguienteDocumentoParaControlar(wsdlFileName);
		} else if (args[__method_parameter_index].equals("getSiguienteDocumentoParaControlarRiesgoOnLine")) {
			new RiesgoCrediticioProxy().getSiguienteDocumentoParaControlarRiesgoOnLine(wsdlFileName);
		} else if (args[__method_parameter_index].equals("actualizarDatosRiesgoCrediticioACM")) {
			new RiesgoCrediticioProxy().actualizarDatosRiesgoCrediticioACM(
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
				args[15]
			);
		} else if (args[__method_parameter_index].equals("actualizarDatosRiesgoCrediticioBCU")) {
			new RiesgoCrediticioProxy().actualizarDatosRiesgoCrediticioBCU(
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
				args[17]
			);
		} else if (args[__method_parameter_index].equals("actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera")) {
			new RiesgoCrediticioProxy().actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
				wsdlFileName,
				args[2],
				args[3],
				args[4],
				args[5],
				args[6],
				args[7],
				args[8],
				args[9],
				args[10]
			);
		}
	}
}