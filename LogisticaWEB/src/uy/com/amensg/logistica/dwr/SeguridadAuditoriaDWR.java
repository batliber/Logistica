package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ISeguridadAuditoriaBean;
import uy.com.amensg.logistica.bean.SeguridadAuditoriaBean;
import uy.com.amensg.logistica.entities.SeguridadAuditoria;
import uy.com.amensg.logistica.entities.SeguridadAuditoriaTO;

@RemoteProxy
public class SeguridadAuditoriaDWR {

	private ISeguridadAuditoriaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = SeguridadAuditoriaBean.class.getSimpleName();
		String remoteInterfaceName = ISeguridadAuditoriaBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ISeguridadAuditoriaBean) context.lookup(lookupName);
	}
	
	public Collection<SeguridadAuditoriaTO> list(Date fechaDesde, Date fechaHasta) {
		Collection<SeguridadAuditoriaTO> result = new LinkedList<SeguridadAuditoriaTO>();
		
		try {
			ISeguridadAuditoriaBean iSeguridadAuditoriaBean = lookupBean();
			
			for (SeguridadAuditoria seguridadAuditoria : iSeguridadAuditoriaBean.list(fechaDesde, fechaHasta)) {
				result.add(transform(seguridadAuditoria));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static SeguridadAuditoriaTO transform(SeguridadAuditoria seguridadAuditoria) {
		SeguridadAuditoriaTO result = new SeguridadAuditoriaTO();
		
		result.setFecha(seguridadAuditoria.getFecha());
		
		if (seguridadAuditoria.getSeguridadTipoEvento() != null) {
			result.setSeguridadTipoEvento(SeguridadTipoEventoDWR.transform(seguridadAuditoria.getSeguridadTipoEvento()));
		}
		
		if (seguridadAuditoria.getUsuario() != null) {
			result.setUsuario(UsuarioDWR.transform(seguridadAuditoria.getUsuario(), false));
		}
		
		result.setFcre(seguridadAuditoria.getFcre());
		result.setFact(seguridadAuditoria.getFact());
		result.setId(seguridadAuditoria.getId());
		result.setTerm(seguridadAuditoria.getTerm());
		result.setUact(seguridadAuditoria.getUact());
		result.setUcre(seguridadAuditoria.getUcre());
		
		return result;
	}
}