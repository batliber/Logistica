package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Zona;

@Stateless
public class ZonaBean implements IZonaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Zona> list() {
		Collection<Zona> result = new LinkedList<Zona>();
		
		try {
			TypedQuery<Zona> query = 
				entityManager.createQuery(
					"SELECT z"
					+ " FROM Zona z"
					+ " ORDER BY z.nombre", 
					Zona.class
				);
			
			for (Zona zona : query.getResultList()) {
				result.add(zona);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Collection<Zona> listByDepartamentoId(Long departamentoId) {
		Collection<Zona> result = new LinkedList<Zona>();
		
		try {
			TypedQuery<Zona> query = entityManager.createQuery(
				"SELECT z"
				+ " FROM Zona z"
				+ " WHERE z.departamento.id = :departamentoId"
				+ " ORDER BY z.nombre", 
				Zona.class
			);
			query.setParameter("departamentoId", departamentoId);
			
			for (Zona zona : query.getResultList()) {
				result.add(zona);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Zona getById(Long id) {
		Zona result = null;
		
		try {
			result = entityManager.find(Zona.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void save(Zona zona) {
		try {
			entityManager.persist(zona);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(Zona zona) {
		try {
			Zona managedZona = entityManager.find(Zona.class, zona.getId());
			
//			managedZona.setFechaBaja(date);
			
			managedZona.setFact(zona.getFact());
			managedZona.setTerm(zona.getTerm());
			managedZona.setUact(zona.getUact());
			
			entityManager.merge(managedZona);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Zona zona) {
		try {
			Zona managedZona = entityManager.find(Zona.class, zona.getId());
			
			managedZona.setNombre(zona.getNombre());
			managedZona.setDetalle(zona.getDetalle());
			
			managedZona.setDepartamento(zona.getDepartamento());
			
			managedZona.setFact(zona.getFact());
			managedZona.setTerm(zona.getTerm());
			managedZona.setUact(zona.getUact());
			
			entityManager.merge(managedZona);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}