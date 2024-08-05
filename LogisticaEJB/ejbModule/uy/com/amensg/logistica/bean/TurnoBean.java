package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Turno;

@Stateless
public class TurnoBean implements ITurnoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<Turno> list() {
		Collection<Turno> result = new LinkedList<Turno>();
		
		try {
			TypedQuery<Turno> query = entityManager.createQuery(
				"SELECT t"
				+ " FROM Turno t"
				+ " ORDER BY t.nombre", 
				Turno.class
			);
			
			for (Turno turno : query.getResultList()) {
				result.add(turno);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}