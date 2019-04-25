package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ContratoRelacionBean;
import uy.com.amensg.logistica.bean.IContratoRelacionBean;
import uy.com.amensg.logistica.entities.Contrato;
import uy.com.amensg.logistica.entities.ContratoRelacion;
import uy.com.amensg.logistica.entities.ContratoRelacionTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class ContratoRelacionDWR {

	private IContratoRelacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoRelacionBean.class.getSimpleName();
		String remoteInterfaceName = IContratoRelacionBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IContratoRelacionBean) context.lookup(lookupName);
	}
	
	public Collection<ContratoRelacionTO> listByContratoId(Long id) {
		Collection<ContratoRelacionTO> result = new LinkedList<ContratoRelacionTO>();
		
		try {
			IContratoRelacionBean iContratoRelacionBean = lookupBean();
			
			for (ContratoRelacion contratoRelacion : iContratoRelacionBean.listByContratoId(id)) {
				result.add(transform(contratoRelacion));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean chequearAsignacion(MetadataConsultaTO metadataConsultaTO) {
		boolean result = false;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				IContratoRelacionBean iContratoRelacionBean = lookupBean();
			
				result = 
					iContratoRelacionBean.chequearAsignacion(
						MetadataConsultaDWR.transform(metadataConsultaTO), 
						usuarioId
					);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(ContratoRelacionTO contratoRelacionTO) {
		try {
			IContratoRelacionBean iContratoRelacionBean = lookupBean();
			
			iContratoRelacionBean.save(transform(contratoRelacionTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ContratoRelacionTO transform(ContratoRelacion contratoRelacion) {
		ContratoRelacionTO result = new ContratoRelacionTO();
		
		if (contratoRelacion.getContrato() != null) {
			result.setContrato(ContratoDWR.transform(contratoRelacion.getContrato(), false));
		}
		
		if (contratoRelacion.getContratoRelacionado() != null) {
			result.setContratoRelacionado(ContratoDWR.transform(contratoRelacion.getContratoRelacionado(), false));
		}
		
		result.setFact(contratoRelacion.getFact());
		result.setFcre(contratoRelacion.getFcre());
		result.setId(contratoRelacion.getId());
		result.setTerm(contratoRelacion.getId());
		result.setUact(contratoRelacion.getUact());
		result.setUcre(contratoRelacion.getUcre());
		
		return result;
	}

	public static ContratoRelacion transform(ContratoRelacionTO contratoRelacionTO) {
		ContratoRelacion result = new ContratoRelacion();
		
		if (contratoRelacionTO.getContrato() != null) {
			Contrato contrato = new Contrato();
			contrato.setId(contratoRelacionTO.getContrato().getId());
			
			result.setContrato(contrato);
		}
		
		if (contratoRelacionTO.getContratoRelacionado() != null) {
			Contrato contratoRelacionado = new Contrato();
			contratoRelacionado.setId(contratoRelacionTO.getContratoRelacionado().getId());
			
			result.setContratoRelacionado(contratoRelacionado);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(contratoRelacionTO.getFcre());
		result.setFact(date);
		result.setId(contratoRelacionTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(contratoRelacionTO.getUcre());
		
		return result;
	}
}