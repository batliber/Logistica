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

import uy.com.amensg.logistica.bean.IZonaBean;
import uy.com.amensg.logistica.bean.ZonaBean;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.Zona;
import uy.com.amensg.logistica.entities.ZonaTO;

@RemoteProxy
public class ZonaDWR {

	private IZonaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ZonaBean.class.getSimpleName();
		String remoteInterfaceName = IZonaBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IZonaBean) context.lookup(lookupName);
	}
	
	public Collection<ZonaTO> list() {
		Collection<ZonaTO> result = new LinkedList<ZonaTO>();
		
		try {
			IZonaBean iZonaBean = lookupBean();
			
			for (Zona zona : iZonaBean.list()) {
				result.add(transform(zona));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<ZonaTO> listByDepartamentoId(Long departamentoId) {
		Collection<ZonaTO> result = new LinkedList<ZonaTO>();
		
		try {
			IZonaBean iZonaBean = lookupBean();
			
			for (Zona zona : iZonaBean.listByDepartamentoId(departamentoId)) {
				result.add(transform(zona));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IZonaBean iZonaBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iZonaBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object zona : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(ZonaDWR.transform((Zona) zona));
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
				
				IZonaBean iZonaBean = lookupBean();
				
				result = 
					iZonaBean.count(
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
	
	public ZonaTO getById(Long id) {
		ZonaTO result = null;
		
		try {
			IZonaBean iZonaBean = lookupBean();
			
			result = transform(iZonaBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ZonaTO add(ZonaTO zonaTO) {
		ZonaTO result = null;
		
		try {
			IZonaBean iZonaBean = lookupBean();
			
			result = transform(iZonaBean.save(transform(zonaTO)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove(ZonaTO zonaTO) {
		try {
			IZonaBean iZonaBean = lookupBean();
			
			iZonaBean.remove(transform(zonaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(ZonaTO zonaTO) {
		try {
			IZonaBean iZonaBean = lookupBean();
			
			iZonaBean.update(transform(zonaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ZonaTO transform(Zona zona) {
		ZonaTO result = new ZonaTO();
		
		result.setDetalle(zona.getDetalle());
		result.setNombre(zona.getNombre());
		
		result.setDepartamento(DepartamentoDWR.transform(zona.getDepartamento()));
		
		result.setFcre(zona.getFcre());
		result.setFact(zona.getFact());
		result.setId(zona.getId());
		result.setTerm(zona.getTerm());
		result.setUact(zona.getUact());
		result.setUcre(zona.getUcre());
		
		return result;
	}
	
	public static Zona transform(ZonaTO zonaTO) {
		Zona result = new Zona();
		
		result.setDetalle(zonaTO.getDetalle());
		result.setNombre(zonaTO.getNombre());
		
		result.setDepartamento(DepartamentoDWR.transform(zonaTO.getDepartamento()));
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(zonaTO.getFcre());
		result.setFact(date);
		result.setId(zonaTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(zonaTO.getUcre());
		
		return result;
	}
}