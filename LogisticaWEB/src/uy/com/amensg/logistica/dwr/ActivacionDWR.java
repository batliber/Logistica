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

import uy.com.amensg.logistica.bean.ActivacionBean;
import uy.com.amensg.logistica.bean.IActivacionBean;
import uy.com.amensg.logistica.entities.Activacion;
import uy.com.amensg.logistica.entities.ActivacionLote;
import uy.com.amensg.logistica.entities.ActivacionSublote;
import uy.com.amensg.logistica.entities.ActivacionTO;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EstadoActivacion;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;
import uy.com.amensg.logistica.entities.TipoActivacion;

@RemoteProxy
public class ActivacionDWR {

	private IActivacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ActivacionBean.class.getSimpleName();
		String remoteInterfaceName = IActivacionBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (IActivacionBean) context.lookup(lookupName);
	}
	
	public MetadataConsultaResultadoTO listContextAware(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionBean iActivacionBean = lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iActivacionBean.list(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						),
						usuarioId
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object activacion : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(ActivacionDWR.transform((Activacion) activacion));
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
				
				IActivacionBean iActivacionBean = lookupBean();
				
				result = 
					iActivacionBean.count(
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
	
	public String procesarArchivoEmpresa(String fileName, Long empresaId, Long tipoActivacionId) {
		String result = null;
		
		try {
			IActivacionBean iActivacionBean = lookupBean();
			
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			result = iActivacionBean.procesarArchivoEmpresa(fileName, empresaId, tipoActivacionId, usuarioId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public ActivacionTO getById(Long id) {
		ActivacionTO result = null;
		
		try {
			IActivacionBean iActivacionBean = lookupBean();
			
			Activacion activacion = iActivacionBean.getById(id);
			if (activacion != null) {
				result = transform(activacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ActivacionTO getLastByChip(String chip) {
		ActivacionTO result = null;
		
		try {
			IActivacionBean iActivacionBean = lookupBean();
			
			Activacion activacion = iActivacionBean.getLastByChip(chip);
			if (activacion != null) {
				result = transform(activacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void update(ActivacionTO activacionTO) {
		try {
			IActivacionBean iActivacionBean = lookupBean();
			
			iActivacionBean.update(transform(activacionTO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String exportarAExcel(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionBean iActivacionBean = lookupBean();
				
				result = iActivacionBean.exportarAExcel(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ActivacionTO transform(Activacion activacion) {
		ActivacionTO activacionTO = new ActivacionTO();
		
		activacionTO.setChip(activacion.getChip());
		activacionTO.setFechaActivacion(activacion.getFechaActivacion());
		activacionTO.setFechaVencimiento(activacion.getFechaVencimiento());
		activacionTO.setFechaImportacion(activacion.getFechaImportacion());
		activacionTO.setMid(activacion.getMid());
		
		if (activacion.getEmpresa() != null) {
			activacionTO.setEmpresa(EmpresaDWR.transform(activacion.getEmpresa()));
		}
		
		if (activacion.getTipoActivacion() != null) {
			activacionTO.setTipoActivacion(TipoActivacionDWR.transform(activacion.getTipoActivacion()));
		}
		
		if (activacion.getEstadoActivacion() != null) {
			activacionTO.setEstadoActivacion(EstadoActivacionDWR.transform(activacion.getEstadoActivacion()));
		}
		
		if (activacion.getActivacionLote() != null) {
			activacionTO.setActivacionLote(ActivacionLoteDWR.transform(activacion.getActivacionLote()));
		}
		
		if (activacion.getActivacionSublote() != null) {
			activacionTO.setActivacionSublote(ActivacionSubloteDWR.transform(activacion.getActivacionSublote(), false));
		}
		
		activacionTO.setFact(activacion.getFact());
		activacionTO.setId(activacion.getId());
		activacionTO.setTerm(activacion.getTerm());
		activacionTO.setUact(activacion.getUact());
		
		return activacionTO;
	}

	public static Activacion transform(ActivacionTO activacionTO) {
		Activacion result = new Activacion();
		
		result.setChip(activacionTO.getChip());
		result.setFechaActivacion(activacionTO.getFechaActivacion());
		result.setFechaImportacion(activacionTO.getFechaImportacion());
		result.setFechaVencimiento(activacionTO.getFechaVencimiento());
		result.setMid(activacionTO.getMid());
		
		if (activacionTO.getActivacionLote() != null) {
			ActivacionLote activacionLote = new ActivacionLote();
			activacionLote.setId(activacionTO.getActivacionLote().getId());
			
			result.setActivacionLote(activacionLote);
		}
		
		if (activacionTO.getActivacionSublote() != null) {
			ActivacionSublote activacionSublote = new ActivacionSublote();
			activacionSublote.setId(activacionTO.getActivacionSublote().getId());
		}
		
		if (activacionTO.getEmpresa() != null) {
			Empresa empresa = new Empresa();
			empresa.setId(activacionTO.getEmpresa().getId());
			
			result.setEmpresa(empresa);
		}
		
		if (activacionTO.getEstadoActivacion() != null) {
			EstadoActivacion estadoActivacion = new EstadoActivacion();
			estadoActivacion.setId(activacionTO.getEstadoActivacion().getId());
			
			result.setEstadoActivacion(estadoActivacion);
		}
		
		if (activacionTO.getTipoActivacion() != null) {
			TipoActivacion tipoActivacion = new TipoActivacion();
			tipoActivacion.setId(activacionTO.getTipoActivacion().getId());
			
			result.setTipoActivacion(tipoActivacion);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFact(date);
		result.setId(activacionTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);

		return result;
	}
}