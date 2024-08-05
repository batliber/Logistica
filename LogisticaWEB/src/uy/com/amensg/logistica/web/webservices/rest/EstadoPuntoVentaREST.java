package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import uy.com.amensg.logistica.bean.EstadoPuntoVentaBean;
import uy.com.amensg.logistica.bean.IEstadoPuntoVentaBean;
import uy.com.amensg.logistica.entities.EstadoPuntoVenta;

@Path("/EstadoPuntoVentaREST")
public class EstadoPuntoVentaREST {
	
	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<EstadoPuntoVenta> list() {
		Collection<EstadoPuntoVenta> result = new LinkedList<EstadoPuntoVenta>();
		
		try {
			IEstadoPuntoVentaBean iEstadoPuntoVentaBean = lookupBean();
			
			result = iEstadoPuntoVentaBean.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IEstadoPuntoVentaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoPuntoVentaBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoPuntoVentaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEstadoPuntoVentaBean) context.lookup(lookupName);
	}
}