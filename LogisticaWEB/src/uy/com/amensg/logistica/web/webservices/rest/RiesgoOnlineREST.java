package uy.com.amensg.logistica.web.webservices.rest;

import java.util.LinkedList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import uy.com.amensg.logistica.bean.IRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.RiesgoCrediticioBean;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.webservices.external.tablero.BCUBCUItem;

@Path("/RiesgoOnlineREST")
public class RiesgoOnlineREST {

	@GET
	@Path("/listPendientes")
	@Produces("application/json")
	public List<BCUBCUItem> listPendientes() {
		List<BCUBCUItem> result = new LinkedList<BCUBCUItem>();
		
		try {
			IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupRiesgoCrediticioBean();
			
			result = iRiesgoCrediticioBean.listPendientesRiesgoOnLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/reprocesar")
	@Produces("application/json")
	public void reprocesar(@Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuario = new Usuario();
				usuario.setId(usuarioId);
				
				IRiesgoCrediticioBean iRiesgoCrediticioBean = lookupRiesgoCrediticioBean();
				
				for (BCUBCUItem bcuBCUItem : listPendientes()) {
					iRiesgoCrediticioBean.controlarRiesgoBCUOnline(bcuBCUItem.getBCUDOC());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IRiesgoCrediticioBean lookupRiesgoCrediticioBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RiesgoCrediticioBean.class.getSimpleName();
		String remoteInterfaceName = IRiesgoCrediticioBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
				
		return (IRiesgoCrediticioBean) context.lookup(lookupName);
	}
}