package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.IStockMovimientoBean;
import uy.com.amensg.logistica.bean.StockMovimientoBean;
import uy.com.amensg.logistica.entities.StockMovimiento;
import uy.com.amensg.logistica.entities.StockMovimientoTO;

@RemoteProxy
public class StockMovimientoDWR {

	private IStockMovimientoBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = StockMovimientoBean.class.getSimpleName();
		String remoteInterfaceName = IStockMovimientoBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IStockMovimientoBean) context.lookup(lookupName);
	}
	
	public Collection<StockMovimientoTO> listStockByEmpresaId(Long id) {
		Collection<StockMovimientoTO> result = new LinkedList<StockMovimientoTO>();
		
		try {
			IStockMovimientoBean iStockMovimientoBean = lookupBean();
			
			for (StockMovimiento stockMovimiento : iStockMovimientoBean.listStockByEmpresaId(id)) {
				result.add(transform(stockMovimiento));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static StockMovimientoTO transform(StockMovimiento stockMovimiento) {
		StockMovimientoTO result = new StockMovimientoTO();
		
		result.setCantidad(stockMovimiento.getCantidad());
		result.setFecha(stockMovimiento.getFecha());
		
		if (stockMovimiento.getEmpresa() != null) {
			result.setEmpresa(EmpresaDWR.transform(stockMovimiento.getEmpresa()));
		}
		
		if (stockMovimiento.getProducto() != null) {
			result.setProducto(ProductoDWR.transform(stockMovimiento.getProducto()));
		}
		
		result.setFact(stockMovimiento.getFact());
		result.setId(stockMovimiento.getId());
		result.setTerm(stockMovimiento.getTerm());
		result.setUact(stockMovimiento.getUact());
		
		return result;
	}
}