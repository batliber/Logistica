package uy.com.amensg.logistica.webservices.rest;

import java.util.HashSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import uy.com.amensg.logistica.bean.ControlBean;
import uy.com.amensg.logistica.bean.IControlBean;
import uy.com.amensg.logistica.entities.Control;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.ImportacionArchivoControlTO;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ResultadoExportacionArchivoTO;
import uy.com.amensg.logistica.entities.ResultadoImportacionArchivoTO;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;

@Path("/ControlREST")
public class ControlREST {

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
				
				IControlBean iControlBean = lookupBean();
				
				result = iControlBean.list(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					Control control = (Control) object;
					
					control.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
					control.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					
					if (control.getDistribuidor() != null) {
						control.getDistribuidor().setUsuarioRolEmpresas(new HashSet<UsuarioRolEmpresa>());
					}
					
					if (control.getPuntoVenta() != null && control.getPuntoVenta().getDistribuidor() != null) {
						control.getPuntoVenta().getDistribuidor().setUsuarioRolEmpresas(new HashSet<UsuarioRolEmpresa>());
					}
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
				
				IControlBean iControlBean = lookupBean();
				
				result = iControlBean.count(metadataConsulta, usuarioId);
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
		ImportacionArchivoControlTO importacionArchivoControlTO, 
		@Context HttpServletRequest request
	) {
		ResultadoImportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IControlBean iControlBean = lookupBean();
				
				result = new ResultadoImportacionArchivoTO();
				result.setMensaje(
					iControlBean.procesarArchivoEmpresa(
						importacionArchivoControlTO.getNombre(),
						importacionArchivoControlTO.getEmpresaId(),
						importacionArchivoControlTO.getTipoControlId(),
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
				
				IControlBean iControlBean = lookupBean();
				
				String nombreArchivo = 
					iControlBean.exportarAExcel(metadataConsulta, usuarioId);
				
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
	
	private IControlBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IControlBean.class.getName();
		String beanName = ControlBean.class.getSimpleName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IControlBean) context.lookup(lookupName);
	}
}