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
import javax.ws.rs.core.MediaType;

import uy.com.amensg.logistica.bean.IRecargaMovimientoBean;
import uy.com.amensg.logistica.bean.RecargaMovimientoBean;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.RecargaMovimiento;

@Path("/RecargaMovimientoREST")
public class RecargaMovimientoREST {

	@GET
	@Path("/listSaldoByPuntoVentaId/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<RecargaMovimiento> listSaldoByPuntoVentaId(
		@PathParam("id") Long id, @Context HttpServletRequest request
	) {
		Collection<RecargaMovimiento> result = new LinkedList<RecargaMovimiento>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IRecargaMovimientoBean iRecargaMovimientoBean = lookupBean();
			
				PuntoVenta puntoVenta = new PuntoVenta();
				puntoVenta.setId(id);
				
				result = iRecargaMovimientoBean.listSaldoByPuntoVenta(puntoVenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IRecargaMovimientoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RecargaMovimientoBean.class.getSimpleName();
		String remoteInterfaceName = IRecargaMovimientoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IRecargaMovimientoBean) context.lookup(lookupName);
	}
}