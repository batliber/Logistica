package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ControlBean;
import uy.com.amensg.logistica.bean.IControlBean;
import uy.com.amensg.logistica.entities.Control;
import uy.com.amensg.logistica.entities.ControlTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

@RemoteProxy
public class ControlDWR {

	private IControlBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ControlBean.class.getSimpleName();
		String remoteInterfaceName = IControlBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IControlBean) context.lookup(lookupName);
	}
	
	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IControlBean iControlBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iControlBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object control : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(ControlDWR.transform((Control) control));
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
				
				IControlBean iControlBean = lookupBean();
				
				result = 
					iControlBean.count(
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
	
	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long tipoControlId) {
		String result = null;
		
		try {
			IControlBean iControlBean = lookupBean();
			
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			result = iControlBean.procesarArchivoEmpresa(fileName, empresaId, tipoControlId, usuarioId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ControlTO transform(Control control) {
		ControlTO controlTO = new ControlTO();
		
		controlTO.setCargaInicial(control.getCargaInicial());
		controlTO.setFechaActivacion(control.getFechaActivacion());
		controlTO.setFechaControl(control.getFechaControl());
		controlTO.setFechaImportacion(control.getFechaImportacion());
		controlTO.setMesControl(control.getMesControl());
		controlTO.setMid(control.getMid());
		controlTO.setMontoCargar(control.getMontoCargar());
		
		if (control.getEmpresa() != null) {
			controlTO.setEmpresa(EmpresaDWR.transform(control.getEmpresa()));
		}
		
		if (control.getTipoControl() != null) {
			controlTO.setTipoControl(TipoControlDWR.transform(control.getTipoControl()));
		}
		
		if (control.getEstadoControl() != null) {
			controlTO.setEstadoControl(EstadoControlDWR.transform(control.getEstadoControl()));
		}
		
		controlTO.setFact(control.getFact());
		controlTO.setId(control.getId());
		controlTO.setTerm(control.getTerm());
		controlTO.setUact(control.getUact());
		
		return controlTO;
	}
}