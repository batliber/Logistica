package uy.com.amensg.logistica.webservices;

import java.text.SimpleDateFormat;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uy.com.amensg.logistica.bean.IRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.RiesgoCrediticioBean;
import uy.com.amensg.logistica.entities.ACMInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticioInstitucionFinanciera;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.RiesgoCrediticio;

@WebService
public class RiesgoCrediticioWebService {

	@WebMethod
	public String getSiguienteDocumentoParaControlar() {
		String result = "";
		
		try {
			IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupRiesgoCrediticioBean();
			
			RiesgoCrediticio riesgoCrediticio = iRiesgoCrediticioBean.getSiguienteDocumentoParaControlar();
			
			if (riesgoCrediticio != null) {
				result = riesgoCrediticio.getDocumento() 
					+ " " + riesgoCrediticio.getEmpresa().getId() 
					+ " " + riesgoCrediticio.getTipoControlRiesgoCrediticio().getId() 
					+ " " + riesgoCrediticio.getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@WebMethod
	public String getSiguienteDocumentoParaControlarRiesgoOnLine() {
		String result = "";
		
		try {
			IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupRiesgoCrediticioBean();
			
			RiesgoCrediticio riesgoCrediticio = iRiesgoCrediticioBean.getSiguienteDocumentoParaControlarRiesgoOnLine();
			
			if (riesgoCrediticio != null) {
				result = riesgoCrediticio.getDocumento() 
					+ " " + riesgoCrediticio.getEmpresa().getId() 
					+ " " + riesgoCrediticio.getTipoControlRiesgoCrediticio().getId() 
					+ " " + riesgoCrediticio.getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@WebMethod
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
		String numeroClienteMovil
	) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			ACMInterfaceRiesgoCrediticio acmInterfaceRiesgoCrediticio = new ACMInterfaceRiesgoCrediticio();
			
			Empresa empresa = new Empresa();
			try {
				empresa.setId(Long.parseLong(empresaId));
			} catch (Exception e) {
				e.printStackTrace();
				empresa = null;
			}
			acmInterfaceRiesgoCrediticio.setEmpresa(empresa);
			
			acmInterfaceRiesgoCrediticio.setDocumento(documento);
			
			try {
				acmInterfaceRiesgoCrediticio.setFechaCelular(format.parse(fechaCelular));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				acmInterfaceRiesgoCrediticio.setDeudaCelular(deudaCelular);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				acmInterfaceRiesgoCrediticio.setRiesgoCrediticioCelular(riesgoCrediticioCelular);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				acmInterfaceRiesgoCrediticio.setContratosCelular(Long.parseLong(contratosCelular));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				acmInterfaceRiesgoCrediticio.setContratosSolaFirmaCelular(Long.parseLong("0" + contratosSolaFirmaCelular));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				acmInterfaceRiesgoCrediticio.setContratosGarantiaCelular(Long.parseLong("0" + contratosGarantiaCelular));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				acmInterfaceRiesgoCrediticio.setSaldoAyudaEconomicaCelular(Double.valueOf("0" + saldoAyudaEconomicaCelular.replace(",", ".")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				acmInterfaceRiesgoCrediticio.setNumeroClienteFijo(Long.parseLong(numeroClienteFijo));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			acmInterfaceRiesgoCrediticio.setNombreClienteFijo(nombreClienteFijo);
			acmInterfaceRiesgoCrediticio.setEstadoDeudaClienteFijo(
				estadoDeudaClienteFijo != null && !estadoDeudaClienteFijo.trim().equals("") ? estadoDeudaClienteFijo : null
			);
			
			try {
				acmInterfaceRiesgoCrediticio.setNumeroClienteMovil(Long.parseLong(numeroClienteMovil));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupRiesgoCrediticioBean();
			
			iRiesgoCrediticioBean.actualizarDatosRiesgoCrediticioACM(
				Long.parseLong(riesgoCrediticioId),
				acmInterfaceRiesgoCrediticio
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@WebMethod
	public void actualizarDatosRiesgoCrediticioBCU(
		String riesgoCrediticioId,
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
		try {
			String etiquetaSi = "SI";
			
			BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticio = new BCUInterfaceRiesgoCrediticio();
			
			Empresa empresa = new Empresa();
			try {
				empresa.setId(Long.parseLong(empresaId));
			} catch (Exception e) {
				e.printStackTrace();
				empresa = null;
			}
			bcuInterfaceRiesgoCrediticio.setEmpresa(empresa);
			
			bcuInterfaceRiesgoCrediticio.setDocumento(documento);
			bcuInterfaceRiesgoCrediticio.setPeriodo(periodo);
			bcuInterfaceRiesgoCrediticio.setNombreCompleto(nombreCompleto);
			bcuInterfaceRiesgoCrediticio.setActividad(actividad);
			
			try {
				bcuInterfaceRiesgoCrediticio.setVigente(Double.valueOf("0" + vigente.replace(",", ".")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				bcuInterfaceRiesgoCrediticio.setVigenteNoAutoliquidable(Double.valueOf("0" + vigenteNoAutoLiquidable.replace(",", ".")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				bcuInterfaceRiesgoCrediticio.setGarantiasComputables(Double.valueOf("0" + garantiasComputables.replace(",", ".")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				bcuInterfaceRiesgoCrediticio.setGarantiasNoComputables(Double.valueOf("0" + garantiasNoComputables.replace(",", ".")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				bcuInterfaceRiesgoCrediticio.setCastigadoPorAtraso(castigadoPorAtraso.equals(etiquetaSi));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				bcuInterfaceRiesgoCrediticio.setCastigadoPorQuitasYDesistimiento(castigadoPorQuitasYDesistimiento.equals(etiquetaSi));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				bcuInterfaceRiesgoCrediticio.setPrevisionesTotales(Double.valueOf("0" + previsionesTotales.replace(",", ".")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				bcuInterfaceRiesgoCrediticio.setContingencias(Double.valueOf("0" + contingencias.replace(",", ".")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				bcuInterfaceRiesgoCrediticio.setOtorgantesGarantias(Double.valueOf("0" + otorgantesGarantias.replace(",", ".")));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupRiesgoCrediticioBean();
			
			if (sinDatos != null && sinDatos.toLowerCase().equals(etiquetaSi.toLowerCase())) {
				iRiesgoCrediticioBean.actualizarDatosRiesgoCrediticioBCUSinDatos(
					Long.parseLong(riesgoCrediticioId),
					bcuInterfaceRiesgoCrediticio
				);
			} else {
				iRiesgoCrediticioBean.actualizarDatosRiesgoCrediticioBCU(
					Long.parseLong(riesgoCrediticioId),
					bcuInterfaceRiesgoCrediticio
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
		String riesgoCrediticioId,
		String empresaId,
		String documento,
		String institucionFinanciera,
		String calificacion,
		String vigente,
		String vigenteNoAutoLiquidable,
		String previsionesTotales,
		String contingencias
	) {
		try {
			BCUInterfaceRiesgoCrediticioInstitucionFinanciera bcuInterfaceRiesgoCrediticioInstitucionFinanciera = 
				new BCUInterfaceRiesgoCrediticioInstitucionFinanciera();
			
			Empresa empresa = new Empresa();
			try {
				empresa.setId(Long.parseLong(empresaId));
			} catch (Exception e) {
				e.printStackTrace();
				empresa = null;
			}
			bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setEmpresa(empresa);
			
			BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticio = new BCUInterfaceRiesgoCrediticio();
			bcuInterfaceRiesgoCrediticio.setEmpresa(empresa);
			bcuInterfaceRiesgoCrediticio.setDocumento(documento);
			
			bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setBcuInterfaceRiesgoCrediticio(
				bcuInterfaceRiesgoCrediticio
			);
			bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setDocumento(documento);
			bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setInstitucionFinanciera(institucionFinanciera);
			bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setCalificacion(calificacion);
			
			try {
				bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setVigente(
					Double.valueOf("0" + vigente.replace(",", "."))
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setVigenteNoAutoliquidable(
					Double.valueOf("0" + vigenteNoAutoLiquidable.replace(",", "."))
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setPrevisionesTotales(
					Double.valueOf("0" + previsionesTotales.replace(",", "."))
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setContingencias(
					Double.valueOf("0" + contingencias.replace(",",  "."))
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupRiesgoCrediticioBean();
			
			iRiesgoCrediticioBean.actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
				Long.parseLong(riesgoCrediticioId),
				bcuInterfaceRiesgoCrediticioInstitucionFinanciera
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IRiesgoCrediticioBean lookupRiesgoCrediticioBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RiesgoCrediticioBean.class.getSimpleName();
		String remoteInterfaceName = IRiesgoCrediticioBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (IRiesgoCrediticioBean) context.lookup(lookupName);
	}
}