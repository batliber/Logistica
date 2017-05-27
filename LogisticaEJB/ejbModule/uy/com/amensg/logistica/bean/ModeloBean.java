package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Modelo;

@Stateless
public class ModeloBean implements IModeloBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Modelo> list() {
		Collection<Modelo> result = new LinkedList<Modelo>();
		
		try {
			TypedQuery<Modelo> query = entityManager.createQuery(
				"SELECT m"
				+ " FROM Modelo m"
				+ " ORDER BY m.descripcion",
				Modelo.class
			);
			
			for (Modelo modelo : query.getResultList()) {
				result.add(modelo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Collection<Modelo> listByMarcaId(Long marcaId) {
		Collection<Modelo> result = new LinkedList<Modelo>();
		
		try {
			TypedQuery<Modelo> query = entityManager.createQuery(
				"SELECT m"
				+ " FROM Modelo m"
				+ " WHERE m.marca.id = :marcaId"
				+ " ORDER BY m.descripcion",
				Modelo.class
			);
			query.setParameter("marcaId", marcaId);
			
			for (Modelo modelo : query.getResultList()) {
				result.add(modelo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Modelo getById(Long id) {
		Modelo result = null;
		
		try {
			result = entityManager.find(Modelo.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(Modelo modelo) {
		try {
			entityManager.persist(modelo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(Modelo modelo) {
		try {
			Modelo managedModelo = entityManager.find(Modelo.class, modelo.getId());
			
			Date date = GregorianCalendar.getInstance().getTime();
			
			managedModelo.setFechaBaja(date);
			
			managedModelo.setFact(modelo.getFact());
			managedModelo.setTerm(modelo.getTerm());
			managedModelo.setUact(modelo.getUact());
			
			entityManager.merge(managedModelo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Modelo modelo) {
		try {
			Modelo modeloManaged = entityManager.find(Modelo.class, modelo.getId());
			
			modeloManaged.setDescripcion(modelo.getDescripcion());
			modeloManaged.setEmpresaService(modelo.getEmpresaService());
			modeloManaged.setMarca(modelo.getMarca());
			
			modeloManaged.setFact(modelo.getFact());
			modeloManaged.setFechaBaja(modelo.getFechaBaja());
			modeloManaged.setTerm(modelo.getTerm());
			modeloManaged.setUact(modelo.getUact());
			
			entityManager.merge(modeloManaged);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}