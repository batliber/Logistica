package uy.com.amensg.logistica.webservices.rest;

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

import uy.com.amensg.logistica.bean.ACMInterfaceNumeroContratoBean;
import uy.com.amensg.logistica.bean.IACMInterfaceNumeroContratoBean;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.ReprocesarTO;

@Path("/ACMInterfaceNumeroContratoREST")
public class ACMInterfaceNumeroContratoREST {

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
				IACMInterfaceNumeroContratoBean iACMInterfaceNumeroContratoBean = lookupBean();
				
				result = iACMInterfaceNumeroContratoBean.list(metadataConsulta);
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
				IACMInterfaceNumeroContratoBean iACMInterfaceNumeroContratoBean = lookupBean();
				
				result = iACMInterfaceNumeroContratoBean.count(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/reprocesar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void reprocesar(ReprocesarTO reprocesarTO, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfaceNumeroContratoBean iACMInterfaceNumeroContratoBean = lookupBean();
				
				iACMInterfaceNumeroContratoBean.reprocesar(
					reprocesarTO.getMetadataConsulta(),
					reprocesarTO.getObservaciones()
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IACMInterfaceNumeroContratoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IACMInterfaceNumeroContratoBean.class.getName();
		String beanName = ACMInterfaceNumeroContratoBean.class.getSimpleName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IACMInterfaceNumeroContratoBean) context.lookup(lookupName);
	}
}