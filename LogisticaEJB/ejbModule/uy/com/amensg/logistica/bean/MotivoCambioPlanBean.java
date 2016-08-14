package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.MotivoCambioPlan;

@Stateless
public class MotivoCambioPlanBean implements IMotivoCambioPlanBean {
	
	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<MotivoCambioPlan> list() {
		Collection<MotivoCambioPlan> result = new LinkedList<MotivoCambioPlan>();
		
		try {
			TypedQuery<MotivoCambioPlan> query = entityManager.createQuery(
				"SELECT m FROM MotivoCambioPlan m",
				MotivoCambioPlan.class
			);
			
			for (MotivoCambioPlan motivoCambioPlan : query.getResultList()) {
				result.add(motivoCambioPlan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}