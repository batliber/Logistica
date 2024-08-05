package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import uy.com.amensg.logistica.bean.ISituacionRiesgoCrediticioParaguayBean;
import uy.com.amensg.logistica.bean.SituacionRiesgoCrediticioParaguayBean;
import uy.com.amensg.logistica.entities.SituacionRiesgoCrediticioParaguay;

@Path("/SituacionRiesgoCrediticioParaguayREST")
public class SituacionRiesgoCrediticioParaguayREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<SituacionRiesgoCrediticioParaguay> list() {
		Collection<SituacionRiesgoCrediticioParaguay> result = new LinkedList<SituacionRiesgoCrediticioParaguay>();
		
		try {
			ISituacionRiesgoCrediticioParaguayBean iSituacionRiesgoCrediticioParaguayBean = lookupBean();
			
			result = iSituacionRiesgoCrediticioParaguayBean.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ISituacionRiesgoCrediticioParaguayBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = SituacionRiesgoCrediticioParaguayBean.class.getSimpleName();
		String remoteInterfaceName = ISituacionRiesgoCrediticioParaguayBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		Context context = new InitialContext();
		
		return (ISituacionRiesgoCrediticioParaguayBean) context.lookup(lookupName);
	}
}