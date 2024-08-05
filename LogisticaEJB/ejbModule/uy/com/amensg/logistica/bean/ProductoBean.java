package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Producto;
import uy.com.amensg.logistica.util.QueryBuilder;

@Stateless
public class ProductoBean implements IProductoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
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
	
	public Collection<Producto> listMinimal() {
		Collection<Producto> result = new LinkedList<Producto>();
		
		try {
			TypedQuery<Object[]> query = 
				entityManager.createQuery(
					"SELECT p.id, p.descripcion"
					+ " FROM Producto p"
					+ " WHERE p.fechaBaja IS NULL"
					+ " ORDER BY p.descripcion ASC", 
					Object[].class
				);
			
			for (Object[] producto : query.getResultList()) {
				Producto productoMinimal = new Producto();
				productoMinimal.setId((Long)producto[0]);
				productoMinimal.setDescripcion((String)producto[1]);
				
				result.add(productoMinimal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public MetadataConsultaResultado list(MetadataConsulta metadataConsulta, Long usuarioId) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			return new QueryBuilder<Producto>().list(entityManager, metadataConsulta, new Producto());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long count(MetadataConsulta metadataConsulta, Long usuarioId) {
		Long result = null;
		
		try {
			result = new QueryBuilder<Producto>().count(entityManager, metadataConsulta, new Producto());
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
	
	public Producto save(Producto producto) {
		Producto result = null;
		
		try {
			producto.setFcre(producto.getFact());
			producto.setUcre(producto.getUact());
			
			entityManager.persist(producto);
			
			result = producto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
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