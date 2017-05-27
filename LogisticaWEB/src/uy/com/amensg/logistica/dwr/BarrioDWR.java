package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.BarrioBean;
import uy.com.amensg.logistica.bean.IBarrioBean;
import uy.com.amensg.logistica.entities.Barrio;
import uy.com.amensg.logistica.entities.BarrioTO;

@RemoteProxy
public class BarrioDWR {

	private IBarrioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = BarrioBean.class.getSimpleName();
		String remoteInterfaceName = IBarrioBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IBarrioBean) context.lookup(lookupName);
	}
	
	public Collection<BarrioTO> list() {
		Collection<BarrioTO> result = new LinkedList<BarrioTO>();
		
		try {
			IBarrioBean iBarrioBean = lookupBean();
			
			for (Barrio barrio : iBarrioBean.list()) {
				result.add(transform(barrio));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<BarrioTO> listByDepartamentoId(Long departamentoId) {
		Collection<BarrioTO> result = new LinkedList<BarrioTO>();
		
		try {
			IBarrioBean iBarrioBean = lookupBean();
			
			for (Barrio barrio : iBarrioBean.listByDepartamentoId(departamentoId)) {
				result.add(transform(barrio));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public BarrioTO getById(Long id) {
		BarrioTO result = null;
		
		try {
			IBarrioBean iBarrioBean = lookupBean();
			
			result = transform(iBarrioBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(BarrioTO barrioTO) {
		try {
			IBarrioBean iBarrioBean = lookupBean();
			
			iBarrioBean.save(transform(barrioTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(BarrioTO barrioTO) {
		try {
			IBarrioBean iBarrioBean = lookupBean();
			
			iBarrioBean.remove(transform(barrioTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(BarrioTO barrioTO) {
		try {
			IBarrioBean iBarrioBean = lookupBean();
			
			iBarrioBean.update(transform(barrioTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BarrioTO transform(Barrio barrio) {
		BarrioTO barrioTO = new BarrioTO();
		
		barrioTO.setNombre(barrio.getNombre());
		
		barrioTO.setDepartamento(DepartamentoDWR.transform(barrio.getDepartamento()));
		barrioTO.setZona(ZonaDWR.transform(barrio.getZona()));
		
		barrioTO.setFact(barrio.getFact());
		barrioTO.setId(barrio.getId());
		barrioTO.setTerm(barrio.getTerm());
		barrioTO.setUact(barrio.getUact());
		
		return barrioTO;
	}
	
	public static Barrio transform(BarrioTO barrioTO) {
		Barrio barrio = new Barrio();
		
		barrio.setNombre(barrioTO.getNombre());
		
		barrio.setDepartamento(DepartamentoDWR.transform(barrioTO.getDepartamento()));
		barrio.setZona(ZonaDWR.transform(barrioTO.getZona()));
		
		barrio.setFact(barrioTO.getFact());
		barrio.setId(barrioTO.getId());
		barrio.setTerm(barrioTO.getTerm());
		barrio.setUact(barrioTO.getUact());
		
		return barrio;
	}
}