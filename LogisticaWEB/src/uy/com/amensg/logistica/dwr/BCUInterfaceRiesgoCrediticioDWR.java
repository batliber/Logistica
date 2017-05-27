package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.BCUInterfaceRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.IBCUInterfaceRiesgoCrediticioBean;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticioInstitucionFinanciera;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticioTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class BCUInterfaceRiesgoCrediticioDWR {

	private IBCUInterfaceRiesgoCrediticioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IBCUInterfaceRiesgoCrediticioBean.class.getName();
		String beanName = BCUInterfaceRiesgoCrediticioBean.class.getSimpleName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IBCUInterfaceRiesgoCrediticioBean) context.lookup(lookupName);
	}

	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IBCUInterfaceRiesgoCrediticioBean iBCUInterfaceRiesgoCrediticioBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iBCUInterfaceRiesgoCrediticioBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object bcuInterfaceRiesgoCrediticio : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(transform((BCUInterfaceRiesgoCrediticio) bcuInterfaceRiesgoCrediticio));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
				result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long countContextAware(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IBCUInterfaceRiesgoCrediticioBean iBCUInterfaceRiesgoCrediticioBean = lookupBean();
				
				result = 
					iBCUInterfaceRiesgoCrediticioBean.count(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static BCUInterfaceRiesgoCrediticioTO transform(BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticio) {
		BCUInterfaceRiesgoCrediticioTO result = new BCUInterfaceRiesgoCrediticioTO();
		
		result.setDocumento(bcuInterfaceRiesgoCrediticio.getDocumento());
		result.setFechaAnalisis(bcuInterfaceRiesgoCrediticio.getFechaAnalisis());
		result.setPeriodo(bcuInterfaceRiesgoCrediticio.getPeriodo());
		result.setNombreCompleto(bcuInterfaceRiesgoCrediticio.getNombreCompleto());
		result.setActividad(bcuInterfaceRiesgoCrediticio.getActividad());
		result.setVigente(bcuInterfaceRiesgoCrediticio.getVigente());
		result.setVigenteNoAutoliquidable(bcuInterfaceRiesgoCrediticio.getVigenteNoAutoliquidable());
		result.setGarantiasComputables(bcuInterfaceRiesgoCrediticio.getGarantiasComputables());
		result.setGarantiasNoComputables(bcuInterfaceRiesgoCrediticio.getGarantiasNoComputables());
		result.setCastigadoPorAtraso(bcuInterfaceRiesgoCrediticio.getCastigadoPorAtraso());
		result.setCastigadoPorQuitasYDesistimiento(bcuInterfaceRiesgoCrediticio.getCastigadoPorQuitasYDesistimiento());
		result.setPrevisionesTotales(bcuInterfaceRiesgoCrediticio.getPrevisionesTotales());
		result.setContingencias(bcuInterfaceRiesgoCrediticio.getContingencias());
		result.setOtorgantesGarantias(bcuInterfaceRiesgoCrediticio.getOtorgantesGarantias());
		
		if (bcuInterfaceRiesgoCrediticio.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(bcuInterfaceRiesgoCrediticio.getEmpresa()));
		}
		
		Collection<BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO> bcuInterfaceRiesgoCrediticioInstitucionFinancieras = 
			new LinkedList<BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO>();
		if (bcuInterfaceRiesgoCrediticio.getBcuInterfaceRiesgoCrediticioInstitucionFinancieras() != null) {
			for (BCUInterfaceRiesgoCrediticioInstitucionFinanciera bcuInterfaceRiesgoCrediticioInstitucionFinanciera : 
				bcuInterfaceRiesgoCrediticio.getBcuInterfaceRiesgoCrediticioInstitucionFinancieras()) {
				bcuInterfaceRiesgoCrediticioInstitucionFinancieras.add(
					BCUInterfaceRiesgoCrediticioInstitucionFinancieraDWR.transform(bcuInterfaceRiesgoCrediticioInstitucionFinanciera)
				);
			}
		}
		result.setBcuInterfaceRiesgoCrediticioInstitucionFinancieras(bcuInterfaceRiesgoCrediticioInstitucionFinancieras);
		
		result.setFact(bcuInterfaceRiesgoCrediticio.getFact());
		result.setId(bcuInterfaceRiesgoCrediticio.getId());
		result.setTerm(bcuInterfaceRiesgoCrediticio.getTerm());
		result.setUact(bcuInterfaceRiesgoCrediticio.getUact());
		
		return result;
	}
}