package uy.com.amensg.logistica.web.webservices.rest;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import uy.com.amensg.logistica.bean.ACMInterfacePersonaBean;
import uy.com.amensg.logistica.bean.IACMInterfacePersonaBean;
import uy.com.amensg.logistica.entities.ACMInterfacePersona;

@Path("/ACMInterfacePersonaREST")
public class ACMInterfacePersonaREST {

	@GET
	@Path("/getById/{id}")
	@Produces("application/json")
	public ACMInterfacePersona getById(@PathParam("id") Long id) {
		ACMInterfacePersona result = null;
		
		try {
			IACMInterfacePersonaBean iACMInterfacePersonaBean = lookupBean();
			
			result = iACMInterfacePersonaBean.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IACMInterfacePersonaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfacePersonaBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfacePersonaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		Context context = new InitialContext();
		
		return (IACMInterfacePersonaBean) context.lookup(lookupName);
	}
}