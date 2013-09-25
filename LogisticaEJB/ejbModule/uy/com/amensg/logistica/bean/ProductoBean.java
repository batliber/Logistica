package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import uy.com.amensg.logistica.entities.Producto;

@Stateless
public class ProductoBean implements IProductoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Producto> list() {
		Collection<Producto> result = new LinkedList<Producto>();
		
		try {
			Query query = entityManager.createQuery("SELECT p FROM Producto p");
			
			for (Object object : query.getResultList()) {
				result.add((Producto) object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void save(Producto producto) {
		try {
			Producto managedProducto = entityManager.merge(producto);
			managedProducto.setDescripcion(producto.getDescripcion());
			managedProducto.setFact(producto.getFact());
			managedProducto.setTerm(producto.getTerm());
			managedProducto.setUact(producto.getUact());
			
			entityManager.persist(managedProducto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(Producto producto) {
		try {
			Producto managedProducto = entityManager.merge(producto);
			
			entityManager.remove(managedProducto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Producto producto) {
		try {
			this.save(producto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}