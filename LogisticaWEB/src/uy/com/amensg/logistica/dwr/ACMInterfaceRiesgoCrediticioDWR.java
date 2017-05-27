package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfaceRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.IACMInterfaceRiesgoCrediticioBean;
import uy.com.amensg.logistica.entities.ACMInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.ACMInterfaceRiesgoCrediticioTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class ACMInterfaceRiesgoCrediticioDWR {

	private IACMInterfaceRiesgoCrediticioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IACMInterfaceRiesgoCrediticioBean.class.getName();
		String beanName = ACMInterfaceRiesgoCrediticioBean.class.getSimpleName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfaceRiesgoCrediticioBean) context.lookup(lookupName);
	}

	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IACMInterfaceRiesgoCrediticioBean iACMInterfaceRiesgoCrediticioBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iACMInterfaceRiesgoCrediticioBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object acmInterfaceRiesgoCrediticio : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(transform((ACMInterfaceRiesgoCrediticio) acmInterfaceRiesgoCrediticio));
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
				
				IACMInterfaceRiesgoCrediticioBean iACMInterfaceRiesgoCrediticioBean = lookupBean();
				
				result = 
					iACMInterfaceRiesgoCrediticioBean.count(
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
	
	public static ACMInterfaceRiesgoCrediticioTO transform(ACMInterfaceRiesgoCrediticio acmInterfaceRiesgoCrediticio) {
		ACMInterfaceRiesgoCrediticioTO result = new ACMInterfaceRiesgoCrediticioTO();
		
		result.setDocumento(acmInterfaceRiesgoCrediticio.getDocumento());
		result.setFechaAnalisis(acmInterfaceRiesgoCrediticio.getFechaAnalisis());
		result.setFechaCelular(acmInterfaceRiesgoCrediticio.getFechaCelular());
		result.setDeudaCelular(acmInterfaceRiesgoCrediticio.getDeudaCelular());
		result.setRiesgoCrediticioCelular(acmInterfaceRiesgoCrediticio.getRiesgoCrediticioCelular());
		result.setContratosCelular(acmInterfaceRiesgoCrediticio.getContratosCelular());
		result.setContratosSolaFirmaCelular(acmInterfaceRiesgoCrediticio.getContratosSolaFirmaCelular());
		result.setContratosGarantiaCelular(acmInterfaceRiesgoCrediticio.getContratosGarantiaCelular());
		result.setSaldoAyudaEconomicaCelular(acmInterfaceRiesgoCrediticio.getSaldoAyudaEconomicaCelular());
		result.setNumeroClienteFijo(acmInterfaceRiesgoCrediticio.getNumeroClienteFijo());
		result.setNombreClienteFijo(acmInterfaceRiesgoCrediticio.getNombreClienteFijo());
		result.setEstadoDeudaClienteFijo(acmInterfaceRiesgoCrediticio.getEstadoDeudaClienteFijo());
		result.setNumeroClienteMovil(acmInterfaceRiesgoCrediticio.getNumeroClienteMovil());
		
		if (acmInterfaceRiesgoCrediticio.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(acmInterfaceRiesgoCrediticio.getEmpresa()));
		}
		
		result.setFact(acmInterfaceRiesgoCrediticio.getFact());
		result.setId(acmInterfaceRiesgoCrediticio.getId());
		result.setTerm(acmInterfaceRiesgoCrediticio.getTerm());
		result.setUact(acmInterfaceRiesgoCrediticio.getUact());
		
		return result;
	}
}