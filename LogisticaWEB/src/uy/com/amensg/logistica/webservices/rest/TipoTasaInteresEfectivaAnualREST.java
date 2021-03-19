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

import uy.com.amensg.logistica.bean.ITipoTasaInteresEfectivaAnualBean;
import uy.com.amensg.logistica.bean.TipoTasaInteresEfectivaAnualBean;
import uy.com.amensg.logistica.entities.TipoTasaInteresEfectivaAnual;

@Path("/TipoTasaInteresEfectivaAnualREST")
public class TipoTasaInteresEfectivaAnualREST {
	
	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<TipoTasaInteresEfectivaAnual> list(@Context HttpServletRequest request) {
		Collection<TipoTasaInteresEfectivaAnual> result = new LinkedList<TipoTasaInteresEfectivaAnual>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITipoTasaInteresEfectivaAnualBean iTipoTasaInteresEfectivaAnualBean = lookupBean();
				
				result = iTipoTasaInteresEfectivaAnualBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ITipoTasaInteresEfectivaAnualBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoTasaInteresEfectivaAnualBean.class.getSimpleName();
		String remoteInterfaceName = ITipoTasaInteresEfectivaAnualBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (ITipoTasaInteresEfectivaAnualBean) context.lookup(lookupName);
	}
}