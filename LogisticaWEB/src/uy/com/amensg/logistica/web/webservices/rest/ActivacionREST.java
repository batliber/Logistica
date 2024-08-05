package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.ActivacionBean;
import uy.com.amensg.logistica.bean.IActivacionBean;
import uy.com.amensg.logistica.entities.Activacion;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;
import uy.com.amensg.logistica.web.entities.ImportacionArchivoActivacionTO;
import uy.com.amensg.logistica.web.entities.ResultadoExportacionArchivoTO;
import uy.com.amensg.logistica.web.entities.ResultadoImportacionArchivoTO;

@Path("/ActivacionREST")
public class ActivacionREST {

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
				
				IActivacionBean iActivacionBean = lookupBean();
				
				result = iActivacionBean.list(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					Activacion activacion = (Activacion) object;
					
					prepareJSON(activacion);
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
				
				IActivacionBean iActivacionBean = lookupBean();
				
				result = iActivacionBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Activacion getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		Activacion result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			IActivacionBean iActivacionBean = lookupBean();
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				result = prepareJSON(iActivacionBean.getById(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getLastByChip/{chip}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Activacion getLastByChip(@PathParam("chip") String chip, @Context HttpServletRequest request) {
		Activacion result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			IActivacionBean iActivacionBean = lookupBean();
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				result = prepareJSON(iActivacionBean.getLastByChip(chip));
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
		ImportacionArchivoActivacionTO importacionArchivoActivacionTO, 
		@Context HttpServletRequest request
	) {
		ResultadoImportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionBean iActivacionBean = lookupBean();
				
				result = new ResultadoImportacionArchivoTO();
				result.setMensaje(
					iActivacionBean.procesarArchivoEmpresa(
						importacionArchivoActivacionTO.getNombre(),
						importacionArchivoActivacionTO.getEmpresaId(),
						importacionArchivoActivacionTO.getTipoActivacionId(),
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
	@Path("/update")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void update(Activacion activacion, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionBean iActivacionBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				activacion.setFact(hoy);
				activacion.setTerm(Long.valueOf(1));
				activacion.setUact(loggedUsuarioId);
				
				iActivacionBean.update(activacion);
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
				
				IActivacionBean iActivacionBean = lookupBean();
				
				String nombreArchivo = 
					iActivacionBean.exportarAExcel(metadataConsulta, usuarioId);
				
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
	
	@POST
	@Path("/exportarAExcelSupervisorDistribucionChips")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoExportacionArchivoTO exportarAExcelSupervisorDistribucionChips(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		ResultadoExportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionBean iActivacionBean = lookupBean();
				
				String nombreArchivo = 
					iActivacionBean.exportarAExcelSupervisorDistribucionChips(metadataConsulta, usuarioId);
				
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
	
	@POST
	@Path("/exportarAExcelEncargadoActivaciones")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoExportacionArchivoTO exportarAExcelEncargadoActivaciones(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		ResultadoExportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionBean iActivacionBean = lookupBean();
				
				String nombreArchivo = 
					iActivacionBean.exportarAExcelEncargadoActivaciones(metadataConsulta, usuarioId);
				
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
	
	@POST
	@Path("/exportarAExcelEncargadoActivacionesSinDistribucion")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoExportacionArchivoTO exportarAExcelEncargadoActivacionesSinDistribucion(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		ResultadoExportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IActivacionBean iActivacionBean = lookupBean();
				
				String nombreArchivo = 
					iActivacionBean.exportarAExcelEncargadoActivacionesSinDistribucion(metadataConsulta, usuarioId);
				
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
	
	private Activacion prepareJSON(Activacion activacion) {
		Activacion result = activacion;
		
		if (activacion.getActivacionLote() != null) {
			result.getActivacionLote().getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
			result.getActivacionLote().getEmpresa().setFormaPagos(new HashSet<FormaPago>());
		}
		
		if (activacion.getActivacionSublote() != null) {
			activacion.getActivacionSublote().setActivaciones(new HashSet<Activacion>());
			
			if (activacion.getActivacionSublote().getDistribuidor() != null) {
				result.getActivacionSublote().getDistribuidor()
					.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
			}
			
			result.getActivacionSublote().getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
			result.getActivacionSublote().getEmpresa().setFormaPagos(new HashSet<FormaPago>());
			
			if (activacion.getActivacionSublote().getPuntoVenta() != null) {
				if (activacion.getActivacionSublote().getPuntoVenta().getDistribuidor() != null) {
					result.getActivacionSublote().getPuntoVenta().getDistribuidor()
						.setUsuarioRolEmpresas(new HashSet<UsuarioRolEmpresa>());
				}
				
				if (activacion.getActivacionSublote().getPuntoVenta().getCreador() != null) {
					result.getActivacionSublote().getPuntoVenta().getCreador()
						.setUsuarioRolEmpresas(new HashSet<UsuarioRolEmpresa>());
				}
				
				if (activacion.getActivacionSublote().getPuntoVenta().getRecargaPuntoVentaCota() != null) {
					result.getActivacionSublote().getPuntoVenta().getRecargaPuntoVentaCota().setPuntoVenta(null);
				}
			}
		}
		
		result.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
		result.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
		
		if (activacion.getLiquidacion() != null) {
			result.getLiquidacion().getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
			result.getLiquidacion().getEmpresa().setFormaPagos(new HashSet<FormaPago>());
		}
		
		return result;
	}
	
 	private IActivacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ActivacionBean.class.getSimpleName();
		String remoteInterfaceName = IActivacionBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IActivacionBean) context.lookup(lookupName);
	}
}