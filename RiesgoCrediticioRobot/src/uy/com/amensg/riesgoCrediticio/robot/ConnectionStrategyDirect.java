package uy.com.amensg.riesgoCrediticio.robot;

import java.util.Collection;
import java.util.LinkedList;

public class ConnectionStrategyDirect implements IConnectionStrategy {

	public String getSiguienteDocumentoParaControlar() {
		String result = "";
		
		// TODO Implementar.
		
		return result;
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
		String numeroClienteMovil
	) {
		try  {
			DataProcessingTemplateMethod dataProcessingTemplateMethod = new ACMInterfaceRiesgoCrediticio();
			
			Collection<String> data = new LinkedList<String>();
			data.add(empresaId);
			data.add(documento);
			data.add(fechaCelular);
			data.add(deudaCelular);
			data.add(riesgoCrediticioCelular);
			data.add(contratosCelular);
			data.add(contratosSolaFirmaCelular);
			data.add(contratosGarantiaCelular);
			data.add(saldoAyudaEconomicaCelular);
			data.add(numeroClienteFijo);
			data.add(nombreClienteFijo);
			data.add(estadoDeudaClienteFijo);
			data.add(numeroClienteMovil);
			
			dataProcessingTemplateMethod.doProcessing(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosRiesgoCrediticioBCU(
		String empresaId,
		String documento,
		String periodo,
		String nombreCompleto,
		String actividad,
		String vigente,
		String vigenteNoAutoLiquidable,
		String garantiasComputables,
		String garantiasNoComputables,
		String castigadoPorAtraso,
		String castigadoPorQuitasYDesistimiento,
		String previsionesTotales,
		String contingencias,
		String otorgantesGarantias,
		String sinDatos
	) {
		try  {
			DataProcessingTemplateMethod dataProcessingTemplateMethod = new BCUInterfaceRiesgoCrediticio();
			
			Collection<String> data = new LinkedList<String>();
			data.add(empresaId);
			data.add(documento);
			data.add(periodo);
			data.add(nombreCompleto);
			data.add(actividad);
			data.add(vigente);
			data.add(vigenteNoAutoLiquidable);
			data.add(garantiasComputables);
			data.add(garantiasNoComputables);
			data.add(castigadoPorAtraso);
			data.add(castigadoPorQuitasYDesistimiento);
			data.add(previsionesTotales);
			data.add(contingencias);
			data.add(otorgantesGarantias);
			
//			TODO agregar parámetro "sinDatos"
			
			dataProcessingTemplateMethod.doProcessing(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
		String empresaId,
		String documento,
		String institucionFinanciera,
		String calificacion,
		String vigente,
		String vigenteNoAutoLiquidable,
		String previsionesTotales,
		String contingencias
	) {
		try  {
			DataProcessingTemplateMethod dataProcessingTemplateMethod = new BCUInterfaceRiesgoCrediticioInstitucionFinanciera();
			
			Collection<String> data = new LinkedList<String>();
			data.add(empresaId);
			data.add(documento);
			data.add(institucionFinanciera);
			data.add(calificacion);
			data.add(vigente);
			data.add(vigenteNoAutoLiquidable);
			data.add(previsionesTotales);
			data.add(contingencias);
			
			dataProcessingTemplateMethod.doProcessing(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}