package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.IRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.RiesgoCrediticioBean;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.RiesgoCrediticio;
import uy.com.amensg.logistica.entities.RiesgoCrediticioTO;

@RemoteProxy
public class RiesgoCrediticioDWR {

	private IRiesgoCrediticioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IRiesgoCrediticioBean.class.getName();
		String beanName = RiesgoCrediticioBean.class.getSimpleName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IRiesgoCrediticioBean) context.lookup(lookupName);
	}

	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iRiesgoCrediticioBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object riesgoCrediticio : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(transform((RiesgoCrediticio) riesgoCrediticio));
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
				
				IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupBean();
				
				result = 
					iRiesgoCrediticioBean.count(
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
	
	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long tipoControlRiesgoCrediticioId) {
		String result = null;
		
		try {
			IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupBean();
			
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			result = iRiesgoCrediticioBean.procesarArchivoEmpresa(fileName, empresaId, tipoControlRiesgoCrediticioId, usuarioId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public static RiesgoCrediticioTO transform(RiesgoCrediticio riesgoCrediticio) {
		RiesgoCrediticioTO result = new RiesgoCrediticioTO();

		result.setDocumento(riesgoCrediticio.getDocumento());
		result.setFechaVigenciaDesde(riesgoCrediticio.getFechaVigenciaDesde());
		
		if (riesgoCrediticio.getCalificacionRiesgoCrediticioAntel() != null) {
			result.setCalificacionRiesgoCrediticioAntel(
				CalificacionRiesgoCrediticioAntelDWR.transform(riesgoCrediticio.getCalificacionRiesgoCrediticioAntel())
			);
		}
		
		if (riesgoCrediticio.getCalificacionRiesgoCrediticioBCU() != null) {
			result.setCalificacionRiesgoCrediticioBCU(
				CalificacionRiesgoCrediticioBCUDWR.transform(riesgoCrediticio.getCalificacionRiesgoCrediticioBCU())
			);
		}
		
		if (riesgoCrediticio.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(riesgoCrediticio.getEmpresa()));
		}
		
		if (riesgoCrediticio.getEstadoRiesgoCrediticio() != null) {
			result.setEstadoRiesgoCrediticio(EstadoRiesgoCrediticioDWR.transform(riesgoCrediticio.getEstadoRiesgoCrediticio()));
		}
		
		if (riesgoCrediticio.getTipoControlRiesgoCrediticio() != null) {
			result.setTipoControlRiesgoCrediticio(TipoControlRiesgoCrediticioDWR.transform(riesgoCrediticio.getTipoControlRiesgoCrediticio()));
		}
		
		result.setFact(riesgoCrediticio.getFact());
		result.setId(riesgoCrediticio.getId());
		result.setTerm(riesgoCrediticio.getTerm());
		result.setUact(riesgoCrediticio.getUact());
		
		return result;
	}
}