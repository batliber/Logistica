package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticio;

@Stateless
public class TipoControlRiesgoCrediticioBean implements ITipoControlRiesgoCrediticioBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<TipoControlRiesgoCrediticio> list() {
		Collection<TipoControlRiesgoCrediticio> result = new LinkedList<TipoControlRiesgoCrediticio>();
		
		try {
			TypedQuery<TipoControlRiesgoCrediticio> query = entityManager.createQuery(
				"SELECT t "
				+ " FROM TipoControlRiesgoCrediticio t"
				+ " ORDER BY t.descripcion", 
				TipoControlRiesgoCrediticio.class
			);
			
			for (TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio : query.getResultList()) {
				result.add(tipoControlRiesgoCrediticio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public TipoControlRiesgoCrediticio getById(Long id) {
		TipoControlRiesgoCrediticio result = null;
		
		try {
			result = entityManager.find(TipoControlRiesgoCrediticio.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}