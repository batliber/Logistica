package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.TipoPlan;

@Stateless
public class TipoPlanBean implements ITipoPlanBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<TipoPlan> list() {
		Collection<TipoPlan> result = new LinkedList<TipoPlan>();
		
		try {
			TypedQuery<TipoPlan> query = entityManager.createQuery(
				"SELECT t FROM TipoPlan t",
				TipoPlan.class
			);
			
			for (TipoPlan tipoPlan : query.getResultList()) {
				result.add(tipoPlan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public TipoPlan getById(Long id) {
		TipoPlan result = null;
		
		try {
			result = entityManager.find(TipoPlan.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}