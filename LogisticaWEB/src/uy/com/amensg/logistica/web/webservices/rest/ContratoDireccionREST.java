package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import uy.com.amensg.logistica.bean.ContratoDireccionBean;
import uy.com.amensg.logistica.bean.IContratoDireccionBean;
import uy.com.amensg.logistica.entities.ContratoDireccion;

@Path("/ContratoDireccionREST")
public class ContratoDireccionREST {

	@GET
	@Path("/listByContratoId/{id}")
	@Produces("application/json")
	public Collection<ContratoDireccion> listByContratoId(@PathParam("id") Long id) {
		Collection<ContratoDireccion> result = new LinkedList<ContratoDireccion>();
		
		try {
			IContratoDireccionBean iContratoDireccionBean = lookupBean();
			
			result = iContratoDireccionBean.listByContratoId(id);
			
			for (ContratoDireccion contratoDireccion : result) {
				contratoDireccion.setContrato(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IContratoDireccionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoDireccionBean.class.getSimpleName();
		String remoteInterfaceName = IContratoDireccionBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		Context context = new InitialContext();
		
		return (IContratoDireccionBean) context.lookup(lookupName);
	}
}