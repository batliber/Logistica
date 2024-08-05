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
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.ITipoDocumentoBean;
import uy.com.amensg.logistica.bean.TipoDocumentoBean;
import uy.com.amensg.logistica.entities.TipoDocumento;

@Path("/TipoDocumentoREST")
public class TipoDocumentoREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<TipoDocumento> list(@Context HttpServletRequest request) {
		Collection<TipoDocumento> result = new LinkedList<TipoDocumento>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITipoDocumentoBean iTipoDocumentoBean = lookupBean();
				
				result = iTipoDocumentoBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ITipoDocumentoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoDocumentoBean.class.getSimpleName();
		String remoteInterfaceName = ITipoDocumentoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (ITipoDocumentoBean) context.lookup(lookupName);
	}
}