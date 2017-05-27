package uy.com.amensg.logistica.bean;

import java.util.Collection;

import javax.ejb.Remote;

import uy.com.amensg.logistica.entities.StockMovimiento;

@Remote
public interface IStockMovimientoBean {

	public Collection<StockMovimiento> listStockActual();
	
	public Collection<StockMovimiento> listStockByEmpresaId(Long id);
	
	public Collection<StockMovimiento> listByIMEI(String imei);
	
	public StockMovimiento getLastByIMEI(String imei);
	
	public void save(StockMovimiento stockMovimiento);
	
	public void save(Collection<StockMovimiento> stockMovimientos);
	
	public void transferir(Collection<StockMovimiento> stockMovimientos, Long empresaDestinoId);
}