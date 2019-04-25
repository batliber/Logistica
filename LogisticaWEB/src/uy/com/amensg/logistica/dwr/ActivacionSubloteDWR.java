package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ActivacionSubloteBean;
import uy.com.amensg.logistica.bean.IActivacionSubloteBean;
import uy.com.amensg.logistica.entities.Activacion;
import uy.com.amensg.logistica.entities.ActivacionSublote;
import uy.com.amensg.logistica.entities.ActivacionSubloteTO;
import uy.com.amensg.logistica.entities.ActivacionTO;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.PuntoVentaTO;
import uy.com.amensg.logistica.entities.Usuario;

@RemoteProxy
public class ActivacionSubloteDWR {

	private IActivacionSubloteBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ActivacionSubloteBean.class.getSimpleName();
		String remoteInterfaceName = IActivacionSubloteBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IActivacionSubloteBean) context.lookup(lookupName);
	}
	
	public Collection<ActivacionSubloteTO> list() {
		Collection<ActivacionSubloteTO> result = new LinkedList<ActivacionSubloteTO>();
		
		try {
			IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
			
			for (ActivacionSublote activacionSublote : iActivacionSubloteBean.list()) {
				result.add(transform(activacionSublote, false));
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
				
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iActivacionSubloteBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						)
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object activacionSublote : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(ActivacionSubloteDWR.transform((ActivacionSublote) activacionSublote, false));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
				result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MetadataConsultaResultadoTO listMisSublotesContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iActivacionSubloteBean.listMisSublotes(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object activacionSublote : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(ActivacionSubloteDWR.transform((ActivacionSublote) activacionSublote, false));
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
//				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				result = 
					iActivacionSubloteBean.count(
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
	
	public Long countMisSublotesContextAware(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
				
				result = 
					iActivacionSubloteBean.countMisSublotes(
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
	
	public ActivacionSubloteTO getById(Long id) {
		ActivacionSubloteTO result = null;
		
		try {
			IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
			
			ActivacionSublote activacionSublote = iActivacionSubloteBean.getById(id, true);
			if (activacionSublote != null) {
				result = transform(activacionSublote, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ActivacionSubloteTO getByNumero(Long numero) {
		ActivacionSubloteTO result = null;
		
		try {
			IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
			
			ActivacionSublote activacionSublote = iActivacionSubloteBean.getByNumero(numero, false);
			if (activacionSublote != null) {
				result = transform(activacionSublote, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ActivacionSubloteTO getByNumeroContextAware(Long numero) {
		ActivacionSubloteTO result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
			
				ActivacionSublote activacionSublote = iActivacionSubloteBean.getByNumeroUsuario(numero, usuarioId, false);
				if (activacionSublote != null) {
					result = transform(activacionSublote, false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long add(ActivacionSubloteTO activacionSubloteTO) {
		Long result = null;
		
		try {
			IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
			
			result = iActivacionSubloteBean.save(transform(activacionSubloteTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void update(ActivacionSubloteTO activacionSubloteTO) {
		try {
			IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
			
			iActivacionSubloteBean.update(transform(activacionSubloteTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void asignarAPuntoVentaContextAware(ActivacionSubloteTO activacionSubloteTO, PuntoVentaTO puntoVentaTO) {
		try {
			IActivacionSubloteBean iActivacionSubloteBean = lookupBean();
			
			iActivacionSubloteBean.asignarAPuntoVenta(
				transform(activacionSubloteTO), 
				PuntoVentaDWR.transform(puntoVentaTO)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ActivacionSubloteTO transform(ActivacionSublote activacionSublote, boolean transformCollections) {
		ActivacionSubloteTO result = new ActivacionSubloteTO();
		
		result.setDescripcion(activacionSublote.getDescripcion());
		result.setFechaAsignacionDistribuidor(activacionSublote.getFechaAsignacionDistribuidor());
		result.setFechaAsignacionPuntoVenta(activacionSublote.getFechaAsignacionPuntoVenta());
		result.setNumero(activacionSublote.getNumero());
		result.setPorcentajeActivacion(activacionSublote.getPorcentajeActivacion());
		
		if (activacionSublote.getDistribuidor() != null) {
			result.setDistribuidor(UsuarioDWR.transform(activacionSublote.getDistribuidor(), false));
		}
		
		if (activacionSublote.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(activacionSublote.getEmpresa(), false));
		}
		
		if (activacionSublote.getPuntoVenta() != null) {
			result.setPuntoVenta(PuntoVentaDWR.transform(activacionSublote.getPuntoVenta()));
		}
		
		if (transformCollections) {
			Collection<ActivacionTO> activaciones = new LinkedList<ActivacionTO>();
			if (activacionSublote.getActivaciones() != null) {
				for (Activacion activacion : activacionSublote.getActivaciones()) {
					activaciones.add(ActivacionDWR.transform(activacion));
				}
			}
			result.setActivaciones(activaciones);
		}
		
		result.setFcre(activacionSublote.getFcre());
		result.setFact(activacionSublote.getFact());
		result.setId(activacionSublote.getId());
		result.setTerm(activacionSublote.getTerm());
		result.setUcre(activacionSublote.getUcre());
		
		return result;
	}

	public static ActivacionSublote transform(ActivacionSubloteTO activacionSubloteTO) {
		ActivacionSublote result = new ActivacionSublote();
		
		result.setDescripcion(activacionSubloteTO.getDescripcion());
		result.setFechaAsignacionDistribuidor(activacionSubloteTO.getFechaAsignacionDistribuidor());
		result.setFechaAsignacionPuntoVenta(activacionSubloteTO.getFechaAsignacionPuntoVenta());
		result.setNumero(activacionSubloteTO.getNumero());
		result.setPorcentajeActivacion(activacionSubloteTO.getPorcentajeActivacion());
		
		if (activacionSubloteTO.getDistribuidor() != null) {
			Usuario distribuidor = new Usuario();
			distribuidor.setId(activacionSubloteTO.getDistribuidor().getId());
			
			result.setDistribuidor(distribuidor);
		}
		
		if (activacionSubloteTO.getEmpresa() != null) {
			Empresa empresa = new Empresa();
			empresa.setId(activacionSubloteTO.getEmpresa().getId());
			
			result.setEmpresa(empresa);
		}
		
		if (activacionSubloteTO.getPuntoVenta() != null) {
			PuntoVenta puntoVenta = new PuntoVenta();
			puntoVenta.setId(activacionSubloteTO.getPuntoVenta().getId());
			
			result.setPuntoVenta(puntoVenta);
		}
		
		Set<Activacion> activaciones = null;
		if (activacionSubloteTO.getActivaciones() != null) {
			activaciones = new HashSet<Activacion>();
			
			for (ActivacionTO activacionTO : activacionSubloteTO.getActivaciones()) {
				Activacion activacion = new Activacion();
				activacion.setId(activacionTO.getId());
				activacion.setActivacionSublote(result);
				
				activaciones.add(activacion);
			}
		}
		
		result.setActivaciones(activaciones);
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(activacionSubloteTO.getFcre());
		result.setFact(date);
		result.setId(activacionSubloteTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(activacionSubloteTO.getUcre());
		
		return result;
	}
}