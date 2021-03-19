package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.SituacionRiesgoCrediticioParaguay;

@Stateless
public class SituacionRiesgoCrediticioParaguayBean implements ISituacionRiesgoCrediticioParaguayBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<SituacionRiesgoCrediticioParaguay> list() {
		Collection<SituacionRiesgoCrediticioParaguay> result = new LinkedList<SituacionRiesgoCrediticioParaguay>();
		
		try {
			TypedQuery<SituacionRiesgoCrediticioParaguay> query = 
				entityManager.createQuery(
					"SELECT s"
					+ " FROM SituacionRiesgoCrediticioParaguay s"
					+ " ORDER BY s.descripcion",
					SituacionRiesgoCrediticioParaguay.class
				);
			
			for (SituacionRiesgoCrediticioParaguay situacionRiesgoCrediticioParaguay : query.getResultList()) {
				result.add(situacionRiesgoCrediticioParaguay);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public SituacionRiesgoCrediticioParaguay getById(Long id) {
		SituacionRiesgoCrediticioParaguay result = null;
		
		try {
			result = entityManager.find(SituacionRiesgoCrediticioParaguay.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}