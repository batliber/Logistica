package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
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
			Query query = entityManager.createQuery(
				"SELECT p"
				+ " FROM Producto p"
				+ " ORDER BY p.descripcion"
			);
			
			for (Object object : query.getResultList()) {
				result.add((Producto) object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Producto getById(Long id) {
		Producto result = null;
		
		try {
			result = entityManager.find(Producto.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(Producto producto) {
		try {
			entityManager.persist(producto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(Producto producto) {
		try {
			Producto managedProducto = entityManager.find(Producto.class, producto.getId());
			
			Date date = GregorianCalendar.getInstance().getTime();
			
			managedProducto.setFechaBaja(date);
			
			managedProducto.setFact(producto.getFact());
			managedProducto.setTerm(producto.getTerm());
			managedProducto.setUact(producto.getUact());
			
			entityManager.merge(managedProducto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Producto producto) {
		try {
			Producto productoManaged = entityManager.find(Producto.class, producto.getId());
			
			productoManaged.setDescripcion(producto.getDescripcion());
			productoManaged.setEmpresaService(producto.getEmpresaService());
			productoManaged.setMarca(producto.getMarca());
			
			productoManaged.setFact(producto.getFact());
			productoManaged.setFechaBaja(producto.getFechaBaja());
			productoManaged.setTerm(producto.getTerm());
			productoManaged.setUact(producto.getUact());
			
			entityManager.merge(productoManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}