package uy.com.amensg.logistica.web.webservices.rest;

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
import uy.com.amensg.logistica.bean.ContratoURSECBean;
import uy.com.amensg.logistica.bean.IContratoURSECBean;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.web.entities.ImportacionArchivoURSECTO;
import uy.com.amensg.logistica.web.entities.ResultadoImportacionArchivoTO;

@Path("/ContratoURSECREST")
public class ContratoURSECREST {

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
				
				IContratoURSECBean iContratoURSECBean = lookupBean();
				
				result = iContratoURSECBean.list(metadataConsulta, usuarioId);
				
//				for (Object object : result.getRegistrosMuestra()) {
//					ContratoURSEC contratoURSEC = (ContratoURSEC) object;
//				}
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
				
				IContratoURSECBean iContratoURSECBean = lookupBean();
				
				result = iContratoURSECBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/procesarArchivoURSEC")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResultadoImportacionArchivoTO procesarArchivoURSEC(
		ImportacionArchivoURSECTO importacionArchivoURSECTO, 
		@Context HttpServletRequest request
	) {
		ResultadoImportacionArchivoTO result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IContratoURSECBean iContratoURSECBean = lookupBean();
				
				result = new ResultadoImportacionArchivoTO();
				result.setMensaje(
					iContratoURSECBean.procesarArchivoURSEC(
						importacionArchivoURSECTO.getNombre(),
						importacionArchivoURSECTO.getObservaciones(),
						usuarioId
					)
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IContratoURSECBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoURSECBean.class.getSimpleName();
		String remoteInterfaceName = IContratoURSECBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
				
		return (IContratoURSECBean) context.lookup(lookupName);
	}
}