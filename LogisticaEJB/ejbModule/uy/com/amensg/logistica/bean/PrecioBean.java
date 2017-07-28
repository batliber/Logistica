package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Marca;
import uy.com.amensg.logistica.entities.Modelo;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.Precio;
import uy.com.amensg.logistica.entities.TipoProducto;

@Stateless
public class PrecioBean implements IPrecioBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Precio> listPreciosActuales() {
		Collection<Precio> result = new LinkedList<Precio>();
		
		try {
			TypedQuery<Precio> query = entityManager.createQuery(
				"SELECT p"
				+ " FROM Precio p"
				+ " WHERE p.fechaHasta IS NULL",
				Precio.class
			);
			
			for (Precio precio : query.getResultList()) {
				result.add(precio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Collection<Precio> listPreciosActualesByEmpresaId(Long empresaId) {
		Collection<Precio> result = new LinkedList<Precio>();
		
		try {
			TypedQuery<Precio> query = entityManager.createQuery(
				"SELECT p"
				+ " FROM Precio p"
				+ " WHERE p.fechaHasta IS NULL"
				+ " AND p.empresa.id = :empresaId",
				Precio.class
			);
			query.setParameter("empresaId", empresaId);
			
			for (Precio precio : query.getResultList()) {
				result.add(precio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Precio getById(Long id) {
		Precio result = null;
		
		try {
			result = entityManager.find(Precio.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Precio getActualByEmpresaMarcaModeloMoneda(Empresa empresa, Marca marca, Modelo modelo, Moneda moneda) {
		Precio result = null;
		
		try {
			TypedQuery<Precio> query = entityManager.createQuery(
				"SELECT p"
				+ " FROM Precio p"
				+ " WHERE p.fechaHasta IS NULL"
				+ " AND p.empresa.id = :empresaId"
				+ " AND p.marca.id = :marcaId"
				+ " AND p.modelo.id = :modeloId"
				+ " AND p.moneda.id = :monedaId",
				Precio.class
			);
			query.setParameter("empresaId", empresa.getId());
			query.setParameter("marcaId", marca.getId());
			query.setParameter("modeloId", modelo.getId());
			query.setParameter("monedaId", moneda.getId());
			
			List<Precio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Precio getActualByEmpresaTipoProductoMarcaModeloMoneda(
		Empresa empresa, 
		TipoProducto tipoProducto, 
		Marca marca, 
		Modelo modelo, 
		Moneda moneda
	) {
		Precio result = null;
		
		try {
			TypedQuery<Precio> query = entityManager.createQuery(
				"SELECT p"
				+ " FROM Precio p"
				+ " WHERE p.fechaHasta IS NULL"
				+ " AND p.empresa.id = :empresaId"
				+ " AND p.tipoProducto.id = :tipoProductoId"
				+ " AND p.marca.id = :marcaId"
				+ " AND p.modelo.id = :modeloId"
				+ " AND p.moneda.id = :monedaId",
				Precio.class
			);
			query.setParameter("empresaId", empresa.getId());
			query.setParameter("tipoProductoId", tipoProducto.getId());
			query.setParameter("marcaId", marca.getId());
			query.setParameter("modeloId", modelo.getId());
			query.setParameter("monedaId", moneda.getId());
			
			List<Precio> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(Precio precio) {
		try {
			entityManager.persist(precio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Precio precio) {
		try {
			Precio precioManaged = entityManager.find(Precio.class, precio.getId());
			
			precioManaged.setFechaHasta(GregorianCalendar.getInstance().getTime());
			
			entityManager.merge(precioManaged);
			
			precio.setId(null);
			
			entityManager.persist(precio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}