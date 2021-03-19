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

import uy.com.amensg.logistica.bean.FileManagerBean;
import uy.com.amensg.logistica.bean.IFileManagerBean;
import uy.com.amensg.logistica.entities.Archivo;

@Path("/FileManagerREST")
public class FileManagerREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<Archivo> list(@Context HttpServletRequest request) {
		Collection<Archivo> result = new LinkedList<Archivo>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IFileManagerBean iFileManagerBean = lookupBean();
				
				result = iFileManagerBean.listArchivos();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IFileManagerBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = FileManagerBean.class.getSimpleName();
		String remoteInterfaceName = IFileManagerBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IFileManagerBean) context.lookup(lookupName);
	}
}