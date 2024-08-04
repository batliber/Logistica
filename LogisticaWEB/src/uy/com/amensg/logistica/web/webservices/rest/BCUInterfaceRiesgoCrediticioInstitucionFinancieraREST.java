package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import uy.com.amensg.logistica.bean.BCUInterfaceRiesgoCrediticioInstitucionFinancieraBean;
import uy.com.amensg.logistica.bean.IBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean;
import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticioInstitucionFinanciera;

@Path("/BCUInterfaceRiesgoCrediticioInstitucionFinancieraREST")
public class BCUInterfaceRiesgoCrediticioInstitucionFinancieraREST {

	@GET
	@Path("/listByBCUInterfaceRiesgoCrediticioId/{bcuInterfaceRiesgoCrediticioId}")
	@Produces("application/json")
	public Collection<BCUInterfaceRiesgoCrediticioInstitucionFinanciera> listByBCUInterfaceRiesgoCrediticioId(
		@PathParam("bcuInterfaceRiesgoCrediticioId") Long bcuInterfaceRiesgoCrediticioId, 
		@Context HttpServletRequest request
	) {
		Collection<BCUInterfaceRiesgoCrediticioInstitucionFinanciera> result = 
			new LinkedList<BCUInterfaceRiesgoCrediticioInstitucionFinanciera>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean 
					iBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean = lookupBean();
			
				result = 
					iBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean
						.listByBCUInterfaceRiesgoCrediticioId(bcuInterfaceRiesgoCrediticioId);
			
				for (BCUInterfaceRiesgoCrediticioInstitucionFinanciera 
					bcuInterfaceRiesgoCrediticioInstitucionFinanciera : result) {
					bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setBcuInterfaceRiesgoCrediticio(null);
					bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setEmpresa(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean.class.getName();
		String beanName = BCUInterfaceRiesgoCrediticioInstitucionFinancieraBean.class.getSimpleName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean) context.lookup(lookupName);
	}
}