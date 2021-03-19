package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

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