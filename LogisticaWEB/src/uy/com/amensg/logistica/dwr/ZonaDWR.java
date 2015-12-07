package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.IZonaBean;
import uy.com.amensg.logistica.bean.ZonaBean;
import uy.com.amensg.logistica.entities.Zona;
import uy.com.amensg.logistica.entities.ZonaTO;

@RemoteProxy
public class ZonaDWR {

	private IZonaBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = ZonaBean.class.getSimpleName();
		String remoteInterfaceName = IZonaBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
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
	
	public static ZonaTO transform(Zona zona) {
		ZonaTO zonaTO = new ZonaTO();
		
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
		
		zona.setNombre(zonaTO.getNombre());
		
		zona.setDepartamento(DepartamentoDWR.transform(zonaTO.getDepartamento()));
		
		zona.setFact(zonaTO.getFact());
		zona.setId(zonaTO.getId());
		zona.setTerm(zonaTO.getTerm());
		zona.setUact(zonaTO.getUact());
		
		return zona;
	}
}