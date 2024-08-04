package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import uy.com.amensg.logistica.bean.ContratoArchivoAdjuntoBean;
import uy.com.amensg.logistica.bean.IContratoArchivoAdjuntoBean;
import uy.com.amensg.logistica.entities.ContratoArchivoAdjunto;

@Path("/ContratoArchivoAdjuntoREST")
public class ContratoArchivoAdjuntoREST {

	@GET
	@Path("/listByContratoId/{id}")
	@Produces("application/json")
	public Collection<ContratoArchivoAdjunto> listByContratoId(@PathParam("id") Long id) {
		Collection<ContratoArchivoAdjunto> result = new LinkedList<ContratoArchivoAdjunto>();
		
		try {
			IContratoArchivoAdjuntoBean iContratoArchivoAdjuntoBean = lookupBean();
			
			result = iContratoArchivoAdjuntoBean.listByContratoId(id);
			
			for (ContratoArchivoAdjunto contratoArchivoAdjunto : result) {
				contratoArchivoAdjunto.setContrato(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IContratoArchivoAdjuntoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoArchivoAdjuntoBean.class.getSimpleName();
		String remoteInterfaceName = IContratoArchivoAdjuntoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		Context context = new InitialContext();
		
		return (IContratoArchivoAdjuntoBean) context.lookup(lookupName);
	}
}