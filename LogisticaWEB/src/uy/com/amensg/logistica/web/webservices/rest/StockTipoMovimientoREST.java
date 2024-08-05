package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.IStockTipoMovimientoBean;
import uy.com.amensg.logistica.bean.StockTipoMovimientoBean;
import uy.com.amensg.logistica.entities.StockTipoMovimiento;

@Path("/StockTipoMovimientoREST")
public class StockTipoMovimientoREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<StockTipoMovimiento> list(@Context HttpServletRequest request) {
		Collection<StockTipoMovimiento> result = new LinkedList<StockTipoMovimiento>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IStockTipoMovimientoBean iStockTipoMovimientoBean = lookupBean();
				
				result = iStockTipoMovimientoBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IStockTipoMovimientoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = StockTipoMovimientoBean.class.getSimpleName();
		String remoteInterfaceName = IStockTipoMovimientoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IStockTipoMovimientoBean) context.lookup(lookupName);
	}
}