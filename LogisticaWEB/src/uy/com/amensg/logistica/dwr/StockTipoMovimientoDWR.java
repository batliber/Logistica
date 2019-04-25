package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.IStockTipoMovimientoBean;
import uy.com.amensg.logistica.bean.StockTipoMovimientoBean;
import uy.com.amensg.logistica.entities.StockTipoMovimiento;
import uy.com.amensg.logistica.entities.StockTipoMovimientoTO;

@RemoteProxy
public class StockTipoMovimientoDWR {

	private IStockTipoMovimientoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = StockTipoMovimientoBean.class.getSimpleName();
		String remoteInterfaceName = IStockTipoMovimientoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IStockTipoMovimientoBean) context.lookup(lookupName);
	}
	
	public Collection<StockTipoMovimientoTO> list() {
		Collection<StockTipoMovimientoTO> result = new LinkedList<StockTipoMovimientoTO>();
		
		try {
			IStockTipoMovimientoBean iStockTipoMovimientoBean = lookupBean();
			
			for (StockTipoMovimiento stockTipoMovimiento : iStockTipoMovimientoBean.list()) {
				result.add(transform(stockTipoMovimiento));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static StockTipoMovimientoTO transform(StockTipoMovimiento stockTipoMovimiento) {
		StockTipoMovimientoTO result = new StockTipoMovimientoTO();
		
		result.setDescripcion(stockTipoMovimiento.getDescripcion());
		result.setSigno(stockTipoMovimiento.getSigno());
		
		result.setFcre(stockTipoMovimiento.getFcre());
		result.setFact(stockTipoMovimiento.getFact());
		result.setId(stockTipoMovimiento.getId());
		result.setTerm(stockTipoMovimiento.getTerm());
		result.setUact(stockTipoMovimiento.getUact());
		result.setUcre(stockTipoMovimiento.getUcre());
		
		return result;
	}
	
	public static StockTipoMovimiento transform(StockTipoMovimientoTO stockTipoMovimientoTO) {
		StockTipoMovimiento result = new StockTipoMovimiento();
		
		result.setDescripcion(stockTipoMovimientoTO.getDescripcion());
		result.setSigno(stockTipoMovimientoTO.getSigno());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(stockTipoMovimientoTO.getFcre());
		result.setFact(date);
		result.setId(stockTipoMovimientoTO.getId());
		result.setTerm(stockTipoMovimientoTO.getTerm());
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(stockTipoMovimientoTO.getUcre());
		
		return result;
	}
}