package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.IRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.RiesgoCrediticioBean;
import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioAntel;
import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioBCU;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.EstadoRiesgoCrediticio;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.RiesgoCrediticio;
import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticio;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.web.entities.ImportacionArchivoRiesgoCrediticioTO;
import uy.com.amensg.logistica.web.entities.RegistrarAnalisisRiesgoManualTO;
import uy.com.amensg.logistica.web.entities.ResultadoExportacionArchivoTO;
import uy.com.amensg.logistica.web.entities.ResultadoImportacionArchivoTO;

@Path("/RiesgoCrediticioREST")
public class RiesgoCrediticioREST {

	@POST
	@Path("/listContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupBean();
				
				result = iRiesgoCrediticioBean.list(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					RiesgoCrediticio riesgoCrediticio = (RiesgoCrediticio) object;
					
					riesgoCrediticio.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
					riesgoCrediticio.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupBean();
				
				result = iRiesgoCrediticioBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/procesarArchivo")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoImportacionArchivoTO procesarArchivo(
		ImportacionArchivoRiesgoCrediticioTO importacionArchivoRiesgoCrediticioTO, 
		@Context HttpServletRequest request
	) {
		ResultadoImportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupBean();
				
				result = new ResultadoImportacionArchivoTO();
				result.setMensaje(
					iRiesgoCrediticioBean.procesarArchivoEmpresa(
						importacionArchivoRiesgoCrediticioTO.getNombre(),
						importacionArchivoRiesgoCrediticioTO.getEmpresaId(),
						importacionArchivoRiesgoCrediticioTO.getTipoControlRiesgoCrediticioId(),
						usuarioId
					)
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/registrarAnalisisAprobadoManual")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void registrarAnalisisAprobadoManual(
		RegistrarAnalisisRiesgoManualTO registrarAnalisisRiesgoManualTO, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long)httpSession.getAttribute("sesion");
				
				Date date = GregorianCalendar.getInstance().getTime();
				
				IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupBean();
				
				RiesgoCrediticio riesgoCrediticio = new RiesgoCrediticio();
				riesgoCrediticio.setDocumento(registrarAnalisisRiesgoManualTO.getDocumento());
				riesgoCrediticio.setFechaImportacion(date);
				
				CalificacionRiesgoCrediticioAntel calificacionRiesgoCrediticioAntel = new CalificacionRiesgoCrediticioAntel();
				calificacionRiesgoCrediticioAntel.setId(
					Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioAntel.OK"))
				);
				
				riesgoCrediticio.setCalificacionRiesgoCrediticioAntel(calificacionRiesgoCrediticioAntel);
				
				CalificacionRiesgoCrediticioBCU calificacionRiesgoCrediticioBCU = new CalificacionRiesgoCrediticioBCU();
				calificacionRiesgoCrediticioBCU.setId(
					Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDATOS"))
				);
				
				riesgoCrediticio.setCalificacionRiesgoCrediticioBCU(calificacionRiesgoCrediticioBCU);
				
				Empresa empresa = new Empresa();
//				Se define que el registro se cargue a nombre de Gazaler S.A.
//				empresa.setId(empresaId);
				empresa.setId(
					Long.parseLong(Configuration.getInstance().getProperty("empresa.GazalerSA"))
				);
				
				riesgoCrediticio.setEmpresa(empresa);
				
				EstadoRiesgoCrediticio estadoRiesgoCrediticio = new EstadoRiesgoCrediticio();
				estadoRiesgoCrediticio.setId(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.ParaProcesar"))
				);
				
				riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticio);
				
				TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio = new TipoControlRiesgoCrediticio();
				tipoControlRiesgoCrediticio.setId(
					Long.parseLong(Configuration.getInstance().getProperty("tipoControlRiesgoCrediticio.ACMYBCU"))
				);
				
				riesgoCrediticio.setTipoControlRiesgoCrediticio(tipoControlRiesgoCrediticio);
				
				riesgoCrediticio.setFact(date);
				riesgoCrediticio.setTerm(Long.valueOf(1));
				riesgoCrediticio.setUact(usuarioId);
				
				iRiesgoCrediticioBean.save(riesgoCrediticio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/registrarAnalisisRechazadoManual")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void registrarAnalisisRechazadoManual(
		RegistrarAnalisisRiesgoManualTO registrarAnalisisRiesgoManualTO, @Context HttpServletRequest request
	) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long)httpSession.getAttribute("sesion");
				
				Date date = GregorianCalendar.getInstance().getTime();
				
				IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupBean();
				
				RiesgoCrediticio riesgoCrediticio = new RiesgoCrediticio();
				riesgoCrediticio.setDocumento(registrarAnalisisRiesgoManualTO.getDocumento());
				riesgoCrediticio.setFechaImportacion(date);
				
				CalificacionRiesgoCrediticioAntel calificacionRiesgoCrediticioAntel = new CalificacionRiesgoCrediticioAntel();
				calificacionRiesgoCrediticioAntel.setId(
					Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioAntel.TieneDeuda"))
				);
				
				riesgoCrediticio.setCalificacionRiesgoCrediticioAntel(calificacionRiesgoCrediticioAntel);
				
				CalificacionRiesgoCrediticioBCU calificacionRiesgoCrediticioBCU = new CalificacionRiesgoCrediticioBCU();
				calificacionRiesgoCrediticioBCU.setId(
					Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.5"))
				);
				
				riesgoCrediticio.setCalificacionRiesgoCrediticioBCU(calificacionRiesgoCrediticioBCU);
				
				Empresa empresa = new Empresa();
//				Se define que el registro se cargue a nombre de Gazaler S.A.
//				empresa.setId(empresaId);
				empresa.setId(
					Long.parseLong(Configuration.getInstance().getProperty("empresa.GazalerSA"))
				);
				
				riesgoCrediticio.setEmpresa(empresa);
				
				EstadoRiesgoCrediticio estadoRiesgoCrediticio = new EstadoRiesgoCrediticio();
				estadoRiesgoCrediticio.setId(
					Long.parseLong(Configuration.getInstance().getProperty("estadoRiesgoCrediticio.ParaProcesar"))
				);
				
				riesgoCrediticio.setEstadoRiesgoCrediticio(estadoRiesgoCrediticio);
				
				TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio = new TipoControlRiesgoCrediticio();
				tipoControlRiesgoCrediticio.setId(
					Long.parseLong(Configuration.getInstance().getProperty("tipoControlRiesgoCrediticio.ACMYBCU"))
				);
				
				riesgoCrediticio.setTipoControlRiesgoCrediticio(tipoControlRiesgoCrediticio);
				
				riesgoCrediticio.setFact(date);
				riesgoCrediticio.setTerm(Long.valueOf(1));
				riesgoCrediticio.setUact(usuarioId);
				
				iRiesgoCrediticioBean.save(riesgoCrediticio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/exportarAExcel")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoExportacionArchivoTO exportarAExcel(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		ResultadoExportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupBean();
				
				String nombreArchivo = 
					iRiesgoCrediticioBean.exportarAExcel(metadataConsulta, usuarioId);
				
				if (nombreArchivo != null) {
					result = new ResultadoExportacionArchivoTO();
					result.setNombreArchivo(nombreArchivo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IRiesgoCrediticioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IRiesgoCrediticioBean.class.getName();
		String beanName = RiesgoCrediticioBean.class.getSimpleName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IRiesgoCrediticioBean) context.lookup(lookupName);
	}
}