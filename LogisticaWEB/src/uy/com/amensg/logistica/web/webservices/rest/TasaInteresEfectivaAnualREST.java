package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.ITasaInteresEfectivaAnualBean;
import uy.com.amensg.logistica.bean.TasaInteresEfectivaAnualBean;
import uy.com.amensg.logistica.entities.TasaInteresEfectivaAnual;

@Path("/TasaInteresEfectivaAnualREST")
public class TasaInteresEfectivaAnualREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<TasaInteresEfectivaAnual> list(@Context HttpServletRequest request) {
		Collection<TasaInteresEfectivaAnual> result = new LinkedList<TasaInteresEfectivaAnual>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITasaInteresEfectivaAnualBean iTasaInteresEfectivaAnualBean = lookupBean();
				
				result = iTasaInteresEfectivaAnualBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listVigentes")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<TasaInteresEfectivaAnual> listVigentes(@Context HttpServletRequest request) {
		Collection<TasaInteresEfectivaAnual> result = new LinkedList<TasaInteresEfectivaAnual>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITasaInteresEfectivaAnualBean iTasaInteresEfectivaAnualBean = lookupBean();
				
				result = iTasaInteresEfectivaAnualBean.listVigentes();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public TasaInteresEfectivaAnual getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		TasaInteresEfectivaAnual result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITasaInteresEfectivaAnualBean iTasaInteresEfectivaAnualBean = lookupBean();
				
				result = iTasaInteresEfectivaAnualBean.getById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public TasaInteresEfectivaAnual add(
		TasaInteresEfectivaAnual tasaInteresEfectivaAnual, @Context HttpServletRequest request
	) {
		TasaInteresEfectivaAnual result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITasaInteresEfectivaAnualBean iTasaInteresEfectivaAnualBean = lookupBean();
			
				result = iTasaInteresEfectivaAnualBean.save(tasaInteresEfectivaAnual);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(TasaInteresEfectivaAnual tasaInteresEfectivaAnual, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITasaInteresEfectivaAnualBean iTasaInteresEfectivaAnualBean = lookupBean();
				
				iTasaInteresEfectivaAnualBean.remove(tasaInteresEfectivaAnual);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ITasaInteresEfectivaAnualBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TasaInteresEfectivaAnualBean.class.getSimpleName();
		String remoteInterfaceName = ITasaInteresEfectivaAnualBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (ITasaInteresEfectivaAnualBean) context.lookup(lookupName);
	}
}