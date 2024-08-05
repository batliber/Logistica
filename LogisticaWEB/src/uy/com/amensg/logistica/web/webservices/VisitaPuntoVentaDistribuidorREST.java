package uy.com.amensg.logistica.web.webservices;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import uy.com.amensg.logistica.bean.EstadoVisitaPuntoVentaDistribuidorBean;
import uy.com.amensg.logistica.bean.IEstadoVisitaPuntoVentaDistribuidorBean;
import uy.com.amensg.logistica.bean.IUsuarioBean;
import uy.com.amensg.logistica.bean.IVisitaPuntoVentaDistribuidorBean;
import uy.com.amensg.logistica.bean.UsuarioBean;
import uy.com.amensg.logistica.bean.VisitaPuntoVentaDistribuidorBean;
import uy.com.amensg.logistica.entities.EstadoVisitaPuntoVentaDistribuidor;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.VisitaPuntoVentaDistribuidor;
import uy.com.amensg.logistica.util.Configuration;
import uy.com.amensg.logistica.web.entities.VisitaPuntoVentaDistribuidorAppsheetTO;

@Path("/VisitaPuntoVentaDistribuidorREST")
public class VisitaPuntoVentaDistribuidorREST {

	@GET
	@Path("/visitar")
	public void visitar(@Context UriInfo uriInfo) {
		try {
			String email = null;
			String id = null;
			
			MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
			
			if (queryParams.size() >= 2) {
				email = queryParams.get("email").get(0);
				id = queryParams.get("id").get(0);
				
				IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				IUsuarioBean iUsuarioBean = lookupUsuarioBean();
				
				Usuario managedUsuario = iUsuarioBean.getByEmailMinimal(email);
				
				IEstadoVisitaPuntoVentaDistribuidorBean iEstadoVisitaPuntoVentaDistribuidorBean = 
					lookupEstadoVisitaPuntoVentaDistribuidorBean();
				
				EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor =
					iEstadoVisitaPuntoVentaDistribuidorBean.getById(
						Long.parseLong(
							Configuration.getInstance().getProperty(
								"estadoVisitaPuntoVentaDistribuidor.Visitado"
							)
						)
					);
				
				VisitaPuntoVentaDistribuidor managedVisita = 
					iVisitaPuntoVentaDistribuidorBean.getById(Long.parseLong(id));
				
				managedVisita.setEstadoVisitaPuntoVentaDistribuidor(estadoVisitaPuntoVentaDistribuidor);
				managedVisita.setFechaVisita(hoy);
				
				managedVisita.setFact(hoy);
				managedVisita.setTerm(Long.valueOf(1));
				managedVisita.setUact(managedUsuario.getId());
				
				iVisitaPuntoVentaDistribuidorBean.update(managedVisita);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/visitarPOST")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void visitarPOST(
		VisitaPuntoVentaDistribuidorAppsheetTO visitaPuntoVentaDistribuidorAppsheetTO, 
		@Context HttpServletRequest request
	) {
		try	{
			System.out.println(
				"id: " + visitaPuntoVentaDistribuidorAppsheetTO.getId() + "\n"
				+ "observaciones: " + visitaPuntoVentaDistribuidorAppsheetTO.getObservaciones()
			);
			
			IVisitaPuntoVentaDistribuidorBean iVisitaPuntoVentaDistribuidorBean = lookupBean();
			
			VisitaPuntoVentaDistribuidor managedVisita = 
				iVisitaPuntoVentaDistribuidorBean.getById(visitaPuntoVentaDistribuidorAppsheetTO.getId());
			
			managedVisita.setFechaVisita(managedVisita.getFact());
			
			iVisitaPuntoVentaDistribuidorBean.update(managedVisita);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IVisitaPuntoVentaDistribuidorBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = VisitaPuntoVentaDistribuidorBean.class.getSimpleName();
		String remoteInterfaceName = IVisitaPuntoVentaDistribuidorBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IVisitaPuntoVentaDistribuidorBean) context.lookup(lookupName);
	}

	private IUsuarioBean lookupUsuarioBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = UsuarioBean.class.getSimpleName();
		String remoteInterfaceName = IUsuarioBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IUsuarioBean) context.lookup(lookupName);
	}

	private IEstadoVisitaPuntoVentaDistribuidorBean lookupEstadoVisitaPuntoVentaDistribuidorBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoVisitaPuntoVentaDistribuidorBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoVisitaPuntoVentaDistribuidorBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEstadoVisitaPuntoVentaDistribuidorBean) context.lookup(lookupName);
	}
}