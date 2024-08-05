package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
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