package uy.com.amensg.riesgoCrediticio.robot;

public interface IConnectionStrategy {

	public String getSiguienteDocumentoParaControlar(String wsdlFileName);
	
	public String getSiguienteDocumentoParaControlarRiesgoOnLine(String wsdlFileName);

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
		String numeroClienteMovil
	);
	
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
	);

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
	);
}