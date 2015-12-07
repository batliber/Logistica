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
			TypedQuery<Zona> query = entityManager.createQuery("SELECT z FROM Zona z", Zona.class);
			
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
				+ " WHERE z.departamento.id = :departamentoId", 
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
}