package uy.com.amensg.logistica.bean;

import java.util.Collection;

import jakarta.ejb.Remote;

import uy.com.amensg.logistica.entities.StockTipoMovimiento;

@Remote
public interface IStockTipoMovimientoBean {

	public Collection<StockTipoMovimiento> list();

	public StockTipoMovimiento getById(Long id);
}