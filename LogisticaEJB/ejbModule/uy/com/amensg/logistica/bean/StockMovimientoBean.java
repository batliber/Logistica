package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Producto;
import uy.com.amensg.logistica.entities.StockMovimiento;

@Stateless
public class StockMovimientoBean implements IStockMovimientoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<StockMovimiento> listStockActual() {
		Collection<StockMovimiento> result = new LinkedList<StockMovimiento>();
		
		try {
			Query query = entityManager.createQuery(
				"SELECT s.empresa.id, s.producto.id, SUM(s.cantidad) AS cantidad"
				+ " FROM StockMovimiento s"
				+ " GROUP BY s.empresa.id, s.producto.id"
			);
			
			for (Object object : query.getResultList()) {
				Object[] values = (Object[]) object;
				
				StockMovimiento stockMovimiento = new StockMovimiento();
				
				stockMovimiento.setCantidad((Long) values[2]);
				
				Empresa empresa = entityManager.find(Empresa.class, (Long) values[0]);
				
				stockMovimiento.setEmpresa(empresa);
				
				Producto producto = entityManager.find(Producto.class, (Long) values[1]);
				
				stockMovimiento.setProducto(producto);
				
				result.add(stockMovimiento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<StockMovimiento> listStockByEmpresaId(Long id) {
		Collection<StockMovimiento> result = new LinkedList<StockMovimiento>();
		
		try {
			Query query = entityManager.createQuery(
				"SELECT s.producto.id, SUM(s.cantidad) AS cantidad"
				+ " FROM StockMovimiento s"
				+ " WHERE s.empresa.id = :empresaId"
				+ " AND s.producto.fechaBaja IS NULL"
				+ " GROUP BY s.producto.id"
			);
			query.setParameter("empresaId", id);
			
			for (Object object : query.getResultList()) {
				Object[] values = (Object[]) object;
				
				StockMovimiento stockMovimiento = new StockMovimiento();
				
				stockMovimiento.setCantidad((Long) values[1]);
				
				Producto producto = entityManager.find(Producto.class, (Long) values[0]);
				
				stockMovimiento.setProducto(producto);
				
				result.add(stockMovimiento);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void save(StockMovimiento stockMovimiento) {
		try {
			entityManager.persist(stockMovimiento);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}