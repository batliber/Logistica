package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.StockMovimiento;

@Remote
public interface IStockMovimientoBean {

	public Collection<StockMovimiento> listStockActual();
	
	public Collection<StockMovimiento> listStockByEmpresaId(Long id);
	
	public void save(StockMovimiento stockMovimiento);
}