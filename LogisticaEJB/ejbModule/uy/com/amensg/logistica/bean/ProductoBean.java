package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Producto;

@Stateless
public class ProductoBean implements IProductoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Producto> list() {
		Collection<Producto> result = new LinkedList<Producto>();
		
		try {
			TypedQuery<Producto> query = entityManager.createQuery(
				"SELECT p"
				+ " FROM Producto p"
				+ " WHERE p.fechaBaja IS NULL"
				+ " ORDER BY p.descripcion",
				Producto.class
			);
			
			for (Producto producto : query.getResultList()) {
				result.add(producto);
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
	
	public Producto getByIMEI(String imei) {
		Producto result = null;
		
		try {
			TypedQuery<Producto> query = 
				entityManager.createQuery(
					"SELECT p"
					+ " FROM Producto p"
					+ " WHERE p.fechaBaja IS NULL"
					+ " AND p.imei = :imei",
					Producto.class
				);
			query.setParameter("imei", imei);
			
			List<Producto> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Boolean existeIMEI(String imei) {
		Boolean result = false;
		
		try {
			TypedQuery<Producto> query = entityManager.createQuery(
				"SELECT p"
				+ " FROM Producto p"
				+ " WHERE fechaBaja IS NULL"
				+ " AND p.imei = :imei",
				Producto.class
			);
			query.setParameter("imei", imei);
			
			result = query.getResultList().size() > 0;
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