package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioAntel;

@Stateless
public class CalificacionRiesgoCrediticioAntelBean implements ICalificacionRiesgoCrediticioAntelBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<CalificacionRiesgoCrediticioAntel> list() {
		Collection<CalificacionRiesgoCrediticioAntel> result = new LinkedList<CalificacionRiesgoCrediticioAntel>();
		
		try {
			TypedQuery<CalificacionRiesgoCrediticioAntel> query = entityManager.createQuery(
				"SELECT c "
				+ " FROM CalificacionRiesgoCrediticioAntel c"
				+ " ORDER BY c.descripcion", 
				CalificacionRiesgoCrediticioAntel.class
			);
			
			for (CalificacionRiesgoCrediticioAntel calificacionRiesgoCrediticioAntel : query.getResultList()) {
				result.add(calificacionRiesgoCrediticioAntel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public CalificacionRiesgoCrediticioAntel getById(Long id) {
		CalificacionRiesgoCrediticioAntel result = null;
		
		try {
			result = entityManager.find(CalificacionRiesgoCrediticioAntel.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}