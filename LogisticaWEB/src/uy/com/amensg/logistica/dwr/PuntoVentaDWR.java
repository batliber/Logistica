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

import uy.com.amensg.logistica.bean.IPuntoVentaBean;
import uy.com.amensg.logistica.bean.PuntoVentaBean;
import uy.com.amensg.logistica.entities.Barrio;
import uy.com.amensg.logistica.entities.Departamento;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.PuntoVentaTO;

@RemoteProxy
public class PuntoVentaDWR {

	private IPuntoVentaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = PuntoVentaBean.class.getSimpleName();
		String remoteInterfaceName = IPuntoVentaBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IPuntoVentaBean) context.lookup(lookupName);
	}
	
	public Collection<PuntoVentaTO> list() {
		Collection<PuntoVentaTO> result = new LinkedList<PuntoVentaTO>();
		
		try {
			IPuntoVentaBean iPuntoVentaBean = lookupBean();
			
			for (PuntoVenta puntoVenta : iPuntoVentaBean.list()) {
				result.add(transform(puntoVenta));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<PuntoVentaTO> listByDepartamentoId(Long departamentoId) {
		Collection<PuntoVentaTO> result = new LinkedList<PuntoVentaTO>();
		
		try {
			IPuntoVentaBean iPuntoVentaBean = lookupBean();
			
			Departamento departamento = new Departamento();
			departamento.setId(departamentoId);
			
			for (PuntoVenta puntoVenta : iPuntoVentaBean.listByDepartamento(departamento)) {
				result.add(transform(puntoVenta));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<PuntoVentaTO> listByBarrioId(Long barrioId) {
		Collection<PuntoVentaTO> result = new LinkedList<PuntoVentaTO>();
		
		try {
			IPuntoVentaBean iPuntoVentaBean = lookupBean();
			
			Barrio barrio = new Barrio();
			barrio.setId(barrioId);
			
			for (PuntoVenta puntoVenta : iPuntoVentaBean.listByBarrio(barrio)) {
				result.add(transform(puntoVenta));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public PuntoVentaTO getById(Long id) {
		PuntoVentaTO result = null;
		
		try {
			IPuntoVentaBean iPuntoVentaBean = lookupBean();
			
			PuntoVenta puntoVenta = iPuntoVentaBean.getById(id);
			if (puntoVenta != null) {
				result = transform(puntoVenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void add(PuntoVentaTO puntoVentaTO) {
		try {
			IPuntoVentaBean iPuntoVentaBean = lookupBean();
			
			iPuntoVentaBean.save(transform(puntoVentaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(PuntoVentaTO puntoVentaTO) {
		try {
			IPuntoVentaBean iPuntoVentaBean = lookupBean();
			
			iPuntoVentaBean.remove(transform(puntoVentaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(PuntoVentaTO puntoVentaTO) {
		try {
			IPuntoVentaBean iPuntoVentaBean = lookupBean();
			
			iPuntoVentaBean.update(transform(puntoVentaTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static PuntoVentaTO transform(PuntoVenta puntoVenta) {
		PuntoVentaTO result = new PuntoVentaTO();
		result.setContacto(puntoVenta.getContacto());
		result.setDireccion(puntoVenta.getDireccion());
		result.setDocumento(puntoVenta.getDocumento());
		result.setFechaBaja(puntoVenta.getFechaBaja());
		result.setLatitud(puntoVenta.getLatitud());
		result.setLongitud(puntoVenta.getLongitud());
		result.setNombre(puntoVenta.getNombre());
		result.setPrecision(puntoVenta.getPrecision());
		result.setTelefono(puntoVenta.getTelefono());
		
		if (puntoVenta.getBarrio() != null) {
			result.setBarrio(BarrioDWR.transform(puntoVenta.getBarrio()));
		}
		
		if (puntoVenta.getDepartamento() != null) {
			result.setDepartamento(DepartamentoDWR.transform(puntoVenta.getDepartamento()));
		}
		
		result.setFact(puntoVenta.getFact());
		result.setId(puntoVenta.getId());
		result.setTerm(puntoVenta.getTerm());
		result.setUact(puntoVenta.getUact());
		
		return result;
	}
	
	public static PuntoVenta transform(PuntoVentaTO puntoVentaTO) {
		PuntoVenta result = new PuntoVenta();
		result.setContacto(puntoVentaTO.getContacto());
		result.setDireccion(puntoVentaTO.getDireccion());
		result.setDocumento(puntoVentaTO.getDocumento());
		result.setFechaBaja(puntoVentaTO.getFechaBaja());
		result.setLatitud(puntoVentaTO.getLatitud());
		result.setLongitud(puntoVentaTO.getLongitud());
		result.setNombre(puntoVentaTO.getNombre());
		result.setPrecision(puntoVentaTO.getPrecision());
		result.setTelefono(puntoVentaTO.getTelefono());
		
		if (puntoVentaTO.getBarrio() != null) {
			Barrio barrio = new Barrio();
			barrio.setId(puntoVentaTO.getBarrio().getId());
			
			result.setBarrio(barrio);
		}
		
		if (puntoVentaTO.getDepartamento() != null) {
			Departamento departamento = new Departamento();
			departamento.setId(puntoVentaTO.getDepartamento().getId());
			
			result.setDepartamento(departamento);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFact(date);
		result.setId(puntoVentaTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		
		return result;
	}
}