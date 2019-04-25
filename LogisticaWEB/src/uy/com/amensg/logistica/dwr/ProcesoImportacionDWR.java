package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ProcesoImportacionBean;
import uy.com.amensg.logistica.bean.IProcesoImportacionBean;
import uy.com.amensg.logistica.entities.ProcesoImportacion;
import uy.com.amensg.logistica.entities.ProcesoImportacionTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class ProcesoImportacionDWR {

	private IProcesoImportacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ProcesoImportacionBean.class.getSimpleName();
		String remoteInterfaceName = IProcesoImportacionBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (IProcesoImportacionBean) context.lookup(lookupName);
	}
	
	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IProcesoImportacionBean iProcesoImportacionBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iProcesoImportacionBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object procesoImportacion : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(ProcesoImportacionDWR.transform((ProcesoImportacion) procesoImportacion));
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
				
				IProcesoImportacionBean iProcesoImportacionBean = lookupBean();
				
				result = 
					iProcesoImportacionBean.count(
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
	
	public static ProcesoImportacionTO transform(ProcesoImportacion procesoImportacion) {
		ProcesoImportacionTO result = new ProcesoImportacionTO();
		
		result.setFechaFin(procesoImportacion.getFechaFin());
		result.setFechaInicio(procesoImportacion.getFechaInicio());
		result.setNombreArchivo(procesoImportacion.getNombreArchivo());
		result.setObservaciones(procesoImportacion.getObservaciones());
		
		if (procesoImportacion.getEstadoProcesoImportacion() != null) {
			result.setEstadoProcesoImportacion(EstadoProcesoImportacionDWR.transform(procesoImportacion.getEstadoProcesoImportacion()));
		}
		
		if (procesoImportacion.getTipoProcesoImportacion() != null) {
			result.setTipoProcesoImportacion(TipoProcesoImportacionDWR.transform(procesoImportacion.getTipoProcesoImportacion()));
		}
		
		if (procesoImportacion.getUsuario() != null) {
			result.setUsuario(UsuarioDWR.transform(procesoImportacion.getUsuario(), false));
		}
		
		result.setFcre(procesoImportacion.getFcre());
		result.setFact(procesoImportacion.getFact());
		result.setId(procesoImportacion.getId());
		result.setTerm(procesoImportacion.getTerm());
		result.setUact(procesoImportacion.getUact());
		result.setUcre(procesoImportacion.getUcre());
		
		return result;
	}
}