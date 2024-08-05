package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import uy.com.amensg.logistica.bean.ACMInterfaceContratoTipoDocumentoBean;
import uy.com.amensg.logistica.bean.IACMInterfaceContratoTipoDocumentoBean;
import uy.com.amensg.logistica.entities.ACMInterfaceContratoTipoDocumento;

@Path("/ACMInterfaceContratoTipoDocumentoREST")
public class ACMInterfaceContratoTipoDocumentoREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<ACMInterfaceContratoTipoDocumento> list(@Context HttpServletRequest request) {
		Collection<ACMInterfaceContratoTipoDocumento> result = new LinkedList<ACMInterfaceContratoTipoDocumento>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfaceContratoTipoDocumentoBean iACMInterfaceContratoTipoDocumentoBean = lookupBean();
			
				result = iACMInterfaceContratoTipoDocumentoBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IACMInterfaceContratoTipoDocumentoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IACMInterfaceContratoTipoDocumentoBean.class.getName();
		String beanName = ACMInterfaceContratoTipoDocumentoBean.class.getSimpleName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IACMInterfaceContratoTipoDocumentoBean) context.lookup(lookupName);
	}
}