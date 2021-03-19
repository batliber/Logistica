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

import uy.com.amensg.logistica.bean.ACMInterfaceRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.IACMInterfaceRiesgoCrediticioBean;
import uy.com.amensg.logistica.entities.ACMInterfaceRiesgoCrediticio;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ResultadoExportacionArchivoTO;
import uy.com.amensg.logistica.entities.Usuario;

@Path("/ACMInterfaceRiesgoCrediticioREST")
public class ACMInterfaceRiesgoCrediticioREST {

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
				
				IACMInterfaceRiesgoCrediticioBean iACMInterfaceRiesgoCrediticioBean = lookupBean();
				
				result = iACMInterfaceRiesgoCrediticioBean.list(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					ACMInterfaceRiesgoCrediticio acmInterfaceRiesgoCrediticio = (ACMInterfaceRiesgoCrediticio) object;
					
					acmInterfaceRiesgoCrediticio.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
					acmInterfaceRiesgoCrediticio.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
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
				
				IACMInterfaceRiesgoCrediticioBean iACMInterfaceRiesgoCrediticioBean = lookupBean();
				
				result = iACMInterfaceRiesgoCrediticioBean.count(metadataConsulta, usuarioId);
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
				
				IACMInterfaceRiesgoCrediticioBean iACMInterfaceRiesgoCrediticioBean = lookupBean();
				
				String nombreArchivo = 
					iACMInterfaceRiesgoCrediticioBean.exportarAExcel(metadataConsulta, usuarioId);
				
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
	
	private IACMInterfaceRiesgoCrediticioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IACMInterfaceRiesgoCrediticioBean.class.getName();
		String beanName = ACMInterfaceRiesgoCrediticioBean.class.getSimpleName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IACMInterfaceRiesgoCrediticioBean) context.lookup(lookupName);
	}
}