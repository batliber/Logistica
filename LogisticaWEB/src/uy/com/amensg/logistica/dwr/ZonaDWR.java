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
	
	public void add(ZonaTO zonaTO) {
		try {
			IZonaBean iZonaBean = lookupBean();
			
			iZonaBean.save(transform(zonaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		ZonaTO zonaTO = new ZonaTO();
		
		zonaTO.setDetalle(zona.getDetalle());
		zonaTO.setNombre(zona.getNombre());
		
		zonaTO.setDepartamento(DepartamentoDWR.transform(zona.getDepartamento()));
		
		zonaTO.setFact(zona.getFact());
		zonaTO.setId(zona.getId());
		zonaTO.setTerm(zona.getTerm());
		zonaTO.setUact(zona.getUact());
		
		return zonaTO;
	}
	
	public static Zona transform(ZonaTO zonaTO) {
		Zona zona = new Zona();
		
		zona.setDetalle(zonaTO.getDetalle());
		zona.setNombre(zonaTO.getNombre());
		
		zona.setDepartamento(DepartamentoDWR.transform(zonaTO.getDepartamento()));
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		zona.setFact(date);
		zona.setId(zonaTO.getId());
		zona.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		zona.setUact(usuarioId);
		
		return zona;
	}
}