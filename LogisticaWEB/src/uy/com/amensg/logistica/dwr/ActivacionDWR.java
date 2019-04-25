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
import uy.com.amensg.logistica.entities.Liquidacion;
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
	
	public String exportarAExcelSupervisorDistribucionChips(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionBean iActivacionBean = lookupBean();
				
				result = iActivacionBean.exportarAExcelSupervisorDistribucionChips(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String exportarAExcelEncargadoActivaciones(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionBean iActivacionBean = lookupBean();
				
				result = iActivacionBean.exportarAExcelEncargadoActivaciones(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String exportarAExcelEncargadoActivacionesSinDistribucion(MetadataConsultaTO metadataConsultaTO) {
		String result = "";
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionBean iActivacionBean = lookupBean();
				
				result = iActivacionBean.exportarAExcelEncargadoActivacionesSinDistribucion(MetadataConsultaDWR.transform(metadataConsultaTO), usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ActivacionTO transform(Activacion activacion) {
		ActivacionTO result = new ActivacionTO();
		
		result.setChip(activacion.getChip());
		result.setFechaActivacion(activacion.getFechaActivacion());
		result.setFechaVencimiento(activacion.getFechaVencimiento());
		result.setFechaImportacion(activacion.getFechaImportacion());
		result.setMid(activacion.getMid());
		
		if (activacion.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(activacion.getEmpresa(), false));
		}
		
		if (activacion.getTipoActivacion() != null) {
			result.setTipoActivacion(TipoActivacionDWR.transform(activacion.getTipoActivacion()));
		}
		
		if (activacion.getEstadoActivacion() != null) {
			result.setEstadoActivacion(EstadoActivacionDWR.transform(activacion.getEstadoActivacion()));
		}
		
		if (activacion.getActivacionLote() != null) {
			result.setActivacionLote(ActivacionLoteDWR.transform(activacion.getActivacionLote()));
		}
		
		if (activacion.getActivacionSublote() != null) {
			result.setActivacionSublote(ActivacionSubloteDWR.transform(activacion.getActivacionSublote(), false));
		}
		
		if (activacion.getLiquidacion() != null) {
			result.setLiquidacion(LiquidacionDWR.transform(activacion.getLiquidacion()));
		}
		
		result.setFcre(activacion.getFcre());
		result.setFact(activacion.getFact());
		result.setId(activacion.getId());
		result.setTerm(activacion.getTerm());
		result.setUcre(activacion.getUcre());
		
		return result;
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
		
		if (activacionTO.getLiquidacion() != null) {
			Liquidacion liquidacion = new Liquidacion();
			liquidacion.setId(activacionTO.getLiquidacion().getId());
			
			result.setLiquidacion(liquidacion);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(activacionTO.getFcre());
		result.setFact(date);
		result.setId(activacionTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(result.getUcre());

		return result;
	}
}