package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.BarrioBean;
import uy.com.amensg.logistica.bean.IBarrioBean;
import uy.com.amensg.logistica.entities.Barrio;
import uy.com.amensg.logistica.entities.BarrioTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.MinimalTO;

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
	
	public Collection<MinimalTO> listMinimal() {
		Collection<MinimalTO> result = new LinkedList<MinimalTO>();
		
		try {
			IBarrioBean iBarrioBean = lookupBean();
			
			for (Barrio barrio : iBarrioBean.listMinimal()) {
				MinimalTO minimalTO = new MinimalTO();
				
				minimalTO.setId(barrio.getId());
				minimalTO.setNombre(barrio.getNombre());
				
				result.add(minimalTO);
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
	
	public Collection<MinimalTO> listMinimalByDepartamentoId(Long departamentoId) {
		Collection<MinimalTO> result = new LinkedList<MinimalTO>();
		
		try {
			IBarrioBean iBarrioBean = lookupBean();
			
			for (Barrio barrio : iBarrioBean.listMinimalByDepartamentoId(departamentoId)) {
				MinimalTO minimalTO = new MinimalTO();
				
				minimalTO.setId(barrio.getId());
				minimalTO.setNombre(barrio.getNombre());
				
				result.add(minimalTO);
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
				
				IBarrioBean iBarrioBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iBarrioBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object barrio : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(BarrioDWR.transform((Barrio) barrio));
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
				
				IBarrioBean iBarrioBean = lookupBean();
				
				result = 
					iBarrioBean.count(
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
		BarrioTO result = new BarrioTO();
		
		result.setNombre(barrio.getNombre());
		
		result.setDepartamento(DepartamentoDWR.transform(barrio.getDepartamento()));
		result.setZona(ZonaDWR.transform(barrio.getZona()));
		
		result.setFcre(barrio.getFcre());
		result.setFact(barrio.getFact());
		result.setId(barrio.getId());
		result.setTerm(barrio.getTerm());
		result.setUact(barrio.getUact());
		result.setUcre(barrio.getUcre());
		
		return result;
	}
	
	public static Barrio transform(BarrioTO barrioTO) {
		Barrio result = new Barrio();
		
		result.setNombre(barrioTO.getNombre());
		
		result.setDepartamento(DepartamentoDWR.transform(barrioTO.getDepartamento()));
		result.setZona(ZonaDWR.transform(barrioTO.getZona()));
		
		result.setFcre(barrioTO.getFcre());
		result.setFact(barrioTO.getFact());
		result.setId(barrioTO.getId());
		result.setTerm(barrioTO.getTerm());
		result.setUact(barrioTO.getUact());
		result.setUcre(barrioTO.getUcre());
		
		return result;
	}
}