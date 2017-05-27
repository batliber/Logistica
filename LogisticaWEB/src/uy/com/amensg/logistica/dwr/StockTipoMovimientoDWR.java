package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
		StockTipoMovimientoTO stockTipoMovimientoTO = new StockTipoMovimientoTO();
		
		stockTipoMovimientoTO.setDescripcion(stockTipoMovimiento.getDescripcion());
		stockTipoMovimientoTO.setSigno(stockTipoMovimiento.getSigno());
		
		stockTipoMovimientoTO.setFact(stockTipoMovimiento.getFact());
		stockTipoMovimientoTO.setId(stockTipoMovimiento.getId());
		stockTipoMovimientoTO.setUact(stockTipoMovimiento.getUact());
		stockTipoMovimientoTO.setTerm(stockTipoMovimiento.getTerm());
		
		return stockTipoMovimientoTO;
	}
	
	public static StockTipoMovimiento transform(StockTipoMovimientoTO stockTipoMovimientoTO) {
		StockTipoMovimiento stockTipoMovimiento = new StockTipoMovimiento();
		
		stockTipoMovimiento.setDescripcion(stockTipoMovimientoTO.getDescripcion());
		stockTipoMovimiento.setSigno(stockTipoMovimientoTO.getSigno());
		
		stockTipoMovimiento.setFact(stockTipoMovimientoTO.getFact());
		stockTipoMovimiento.setId(stockTipoMovimientoTO.getId());
		stockTipoMovimiento.setUact(stockTipoMovimientoTO.getUact());
		stockTipoMovimiento.setTerm(stockTipoMovimientoTO.getTerm());
		
		return stockTipoMovimiento;
	}
}