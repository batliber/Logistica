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