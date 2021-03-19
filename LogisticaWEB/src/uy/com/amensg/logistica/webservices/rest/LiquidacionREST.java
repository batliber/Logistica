package uy.com.amensg.logistica.webservices.rest;

import java.util.HashSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import uy.com.amensg.logistica.bean.ILiquidacionBean;
import uy.com.amensg.logistica.bean.LiquidacionBean;
import uy.com.amensg.logistica.entities.ArchivoTO;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.Liquidacion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ResultadoImportacionArchivoTO;
import uy.com.amensg.logistica.entities.Usuario;

@Path("/LiquidacionREST")
public class LiquidacionREST {

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
				
				ILiquidacionBean iLiquidacionBean = lookupBean();
				
				result = iLiquidacionBean.list(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					Liquidacion liquidacion = (Liquidacion) object;
					
					liquidacion.getEmpresa().setEmpresaUsuarioContratos(new HashSet<Usuario>());
					liquidacion.getEmpresa().setFormaPagos(new HashSet<FormaPago>());
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
				
				ILiquidacionBean iLiquidacionBean = lookupBean();
				
				result = iLiquidacionBean.count(metadataConsulta, usuarioId);
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
		ArchivoTO archivoTO, @Context HttpServletRequest request
	) {
		ResultadoImportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				ILiquidacionBean iLiquidacionBean = lookupBean();
				
				result = new ResultadoImportacionArchivoTO();
				result.setMensaje(iLiquidacionBean.procesarArchivo(archivoTO.getNombre(), usuarioId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/calcularPorcentajeActivacionSubLotes")
	@Produces({ MediaType.APPLICATION_JSON })
	public void calcularPorcentajeActivacionSubLotes(@Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				ILiquidacionBean iLiquidacionBean = lookupBean();
				
				iLiquidacionBean.calcularPorcentajeActivacionSubLotes(usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/calcularPorcentajeActivacionPuntoVentas")
	@Produces({ MediaType.APPLICATION_JSON })
	public void calcularPorcentajeActivacionPuntoVentas(@Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				ILiquidacionBean iLiquidacionBean = lookupBean();
				
				iLiquidacionBean.calcularPorcentajeActivacionPuntoVentas(usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ILiquidacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = LiquidacionBean.class.getSimpleName();
		String remoteInterfaceName = ILiquidacionBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (ILiquidacionBean) context.lookup(lookupName);
	}
}