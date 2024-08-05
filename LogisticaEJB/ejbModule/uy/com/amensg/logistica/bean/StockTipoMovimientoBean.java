package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.StockTipoMovimiento;

@Stateless
public class StockTipoMovimientoBean implements IStockTipoMovimientoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<StockTipoMovimiento> list() {
		Collection<StockTipoMovimiento> result = new LinkedList<StockTipoMovimiento>();
		
		try {
			TypedQuery<StockTipoMovimiento> query = entityManager.createQuery("SELECT stm FROM StockTipoMovimiento stm", StockTipoMovimiento.class);
			
			for (StockTipoMovimiento stockTipoMovimiento : query.getResultList()) {
				result.add(stockTipoMovimiento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public StockTipoMovimiento getById(Long id) {
		StockTipoMovimiento result = null;

		try {
			result = entityManager.find(StockTipoMovimiento.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}