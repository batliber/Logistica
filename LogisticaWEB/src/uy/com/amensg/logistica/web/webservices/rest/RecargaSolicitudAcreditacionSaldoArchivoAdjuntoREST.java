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

import uy.com.amensg.logistica.bean.IRecargaSolicitudAcreditacionSaldoArchivoAdjuntoBean;
import uy.com.amensg.logistica.bean.RecargaSolicitudAcreditacionSaldoArchivoAdjuntoBean;
import uy.com.amensg.logistica.entities.RecargaSolicitudAcreditacionSaldoArchivoAdjunto;

@Path("/RecargaSolicitudAcreditacionSaldoArchivoAdjuntoREST")
public class RecargaSolicitudAcreditacionSaldoArchivoAdjuntoREST {

	@GET
	@Path("/listByRecargaSolicitudAcreditacionSaldoId/{id}")
	@Produces("application/json")
	public Collection<RecargaSolicitudAcreditacionSaldoArchivoAdjunto> 
		listByRecargaSolicitudAcreditacionSaldoId(@PathParam("id") Long id) {
		Collection<RecargaSolicitudAcreditacionSaldoArchivoAdjunto> result = 
			new LinkedList<RecargaSolicitudAcreditacionSaldoArchivoAdjunto>();
		
		try {
			IRecargaSolicitudAcreditacionSaldoArchivoAdjuntoBean iRecargaSolicitudAcreditacionSaldoArchivoAdjuntoBean = 
				lookupBean();
			
			result = 
				iRecargaSolicitudAcreditacionSaldoArchivoAdjuntoBean.listByRecargaSolicitudAcreditacionSaldoId(id);
			
			for (RecargaSolicitudAcreditacionSaldoArchivoAdjunto recargaSolicitudAcreditacionSaldoArchivoAdjunto : result) {
				recargaSolicitudAcreditacionSaldoArchivoAdjunto.setRecargaSolicitudAcreditacionSaldo(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IRecargaSolicitudAcreditacionSaldoArchivoAdjuntoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RecargaSolicitudAcreditacionSaldoArchivoAdjuntoBean.class.getSimpleName();
		String remoteInterfaceName = IRecargaSolicitudAcreditacionSaldoArchivoAdjuntoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		Context context = new InitialContext();
		
		return (IRecargaSolicitudAcreditacionSaldoArchivoAdjuntoBean) context.lookup(lookupName);
	}
}