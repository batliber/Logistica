package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

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
import uy.com.amensg.logistica.entities.EstadoPuntoVenta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.PuntoVentaTO;
import uy.com.amensg.logistica.util.Configuration;

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
	
	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
//				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iPuntoVentaBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						)
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object puntoVenta : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(transform((PuntoVenta) puntoVenta));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
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
//				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IPuntoVentaBean iPuntoVentaBean = lookupBean();
				
				result = 
					iPuntoVentaBean.count(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						)
					);
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
	
	public Collection<PuntoVentaTO> listByDepartamentoIdLocationAware(Long departamentoId, Double latitud, Double longitud) {
		Collection<PuntoVentaTO> result = new LinkedList<PuntoVentaTO>();
		
		try {
			IPuntoVentaBean iPuntoVentaBean = lookupBean();
			
			Departamento departamento = new Departamento();
			departamento.setId(departamentoId);
			
			for (PuntoVenta puntoVenta : iPuntoVentaBean.listByDepartamento(departamento)) {
				result.add(transform(puntoVenta));
			}
			
			List<PuntoVentaTO> toOrder = new LinkedList<PuntoVentaTO>();
			toOrder.addAll(result);
			
			Collections.sort(toOrder, new ComparatorLocation(latitud, longitud));
			
			result = toOrder;
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
	
 	public Collection<PuntoVentaTO> listByBarrioIdLocationAware(Long barrioId, Double latitud, Double longitud) {
		Collection<PuntoVentaTO> result = new LinkedList<PuntoVentaTO>();
		
		try {
			IPuntoVentaBean iPuntoVentaBean = lookupBean();
			
			Barrio barrio = new Barrio();
			barrio.setId(barrioId);
			
			for (PuntoVenta puntoVenta : iPuntoVentaBean.listByBarrio(barrio)) {
				result.add(transform(puntoVenta));
			}
			
			List<PuntoVentaTO> toOrder = new LinkedList<PuntoVentaTO>();
			toOrder.addAll(result);
			
			Collections.sort(toOrder, new ComparatorLocation(latitud, longitud));
			
			result = toOrder;
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
	
	public void addMobile(PuntoVentaTO puntoVentaTO) {
		try {
			IPuntoVentaBean iPuntoVentaBean = lookupBean();
			
			PuntoVenta puntoVenta = transform(puntoVentaTO);
			
			EstadoPuntoVenta estadoPuntoVenta = new EstadoPuntoVenta();
			estadoPuntoVenta.setId(
				new Long(Configuration.getInstance().getProperty("estadoPuntoVenta.Pendiente"))
			);
			
			puntoVenta.setEstadoPuntoVenta(estadoPuntoVenta);
			
			iPuntoVentaBean.save(puntoVenta);
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
		
		if (puntoVenta.getEstadoPuntoVenta() != null) {
			result.setEstadoPuntoVenta(EstadoPuntoVentaDWR.transform(puntoVenta.getEstadoPuntoVenta()));
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
		
		if (puntoVentaTO.getEstadoPuntoVenta() != null) {
			EstadoPuntoVenta estadoPuntoVenta = new EstadoPuntoVenta();
			estadoPuntoVenta.setId(puntoVentaTO.getEstadoPuntoVenta().getId());
			
			result.setEstadoPuntoVenta(estadoPuntoVenta);
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

class ComparatorLocation implements Comparator<PuntoVentaTO> {
	
	private final Double latitudFinal;
	private final Double longitudFinal;
	
	public ComparatorLocation(Double latitud, Double longitud) {
		this.latitudFinal = latitud;
		this.longitudFinal = longitud;
	}
	
	public int compare(PuntoVentaTO o1, PuntoVentaTO o2) {
		if (latitudFinal == null || longitudFinal == null) {
			// Si la ubicación con la cual comparar es null, son iguales.
			return 0;
		}
		
		if (o1.getLatitud() == null || o1.getLongitud() == null) {
			// Si la ubicación del punto de venta o1 es null
			if (o2.getLatitud() == null || o2.getLongitud() == null) {
				// Si la ubicación del punto de venta o2 es null, entonces son iguales.
				return 0;
			} else {
				// Si la ubicación del punto de venta o2 no es null, entonces la distancia al punto de venta o2 es menor.
				return 1;
			}
		} else if (o2.getLatitud() == null || o2.getLongitud() == null) {
			// Si la ubicación del punto de venta o1 no es null y la del punto de venta o2 es null, entnoces la distancia al punto de venta o1 es menor.
			return -1;
		} else {
			// Si todas las ubicaciones están definidas, entonces las comparamos:
			Double distanceO1 = 
				3959 
				* Math.acos(Math.cos(Math.toRadians(o1.getLatitud())) 
				* Math.cos(Math.toRadians(latitudFinal)) 
				* Math.cos(Math.toRadians(longitudFinal) - Math.toRadians(o1.getLongitud())) 
				+ Math.sin(Math.toRadians(o1.getLatitud())) * Math.sin(Math.toRadians(latitudFinal)));
			
			Double distanceO2 = 
				3959 
				* Math.acos(Math.cos(Math.toRadians(o2.getLatitud())) 
				* Math.cos(Math.toRadians(latitudFinal)) 
				* Math.cos(Math.toRadians(longitudFinal) - Math.toRadians(o2.getLongitud())) 
				+ Math.sin(Math.toRadians(o2.getLatitud())) * Math.sin(Math.toRadians(latitudFinal)));
			
			return distanceO1.compareTo(distanceO2);
		}
	}				
}