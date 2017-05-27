package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioBCU;

@Stateless
public class CalificacionRiesgoCrediticioBCUBean implements ICalificacionRiesgoCrediticioBCUBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<CalificacionRiesgoCrediticioBCU> list() {
		Collection<CalificacionRiesgoCrediticioBCU> result = new LinkedList<CalificacionRiesgoCrediticioBCU>();
		
		try {
			TypedQuery<CalificacionRiesgoCrediticioBCU> query = entityManager.createQuery(
				"SELECT c "
				+ " FROM CalificacionRiesgoCrediticioBCU c"
				+ " ORDER BY c.descripcion", 
				CalificacionRiesgoCrediticioBCU.class
			);
			
			for (CalificacionRiesgoCrediticioBCU calificacionRiesgoCrediticioBCU : query.getResultList()) {
				result.add(calificacionRiesgoCrediticioBCU);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public CalificacionRiesgoCrediticioBCU getById(Long id) {
		CalificacionRiesgoCrediticioBCU result = null;
		
		try {
			result = entityManager.find(CalificacionRiesgoCrediticioBCU.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public CalificacionRiesgoCrediticioBCU getByCalificacion(String calificacion) {
		CalificacionRiesgoCrediticioBCU result = null;
		
		try {
			TypedQuery<CalificacionRiesgoCrediticioBCU> query =
				entityManager.createQuery(
					"SELECT c"
					+ " FROM CalificacionRiesgoCrediticioBCU c"
					+ " WHERE c.descripcion = :calificacion",
					CalificacionRiesgoCrediticioBCU.class
				);
			query.setParameter("calificacion", calificacion);
			
			Collection<CalificacionRiesgoCrediticioBCU> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.iterator().next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}