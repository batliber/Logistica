package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.TipoActivacion;

@Stateless
public class TipoActivacionBean implements ITipoActivacionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<TipoActivacion> list() {
		Collection<TipoActivacion> result = new LinkedList<TipoActivacion>();
		
		try {
			TypedQuery<TipoActivacion> query = entityManager.createQuery(
				"SELECT t "
				+ " FROM TipoActivacion t"
				+ " ORDER BY t.descripcion", 
				TipoActivacion.class
			);
			
			for (TipoActivacion tipoActivacion : query.getResultList()) {
				result.add(tipoActivacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public TipoActivacion getById(Long id) {
		TipoActivacion result = null;
		
		try {
			result = entityManager.find(TipoActivacion.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}