package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.TipoControl;

@Stateless
public class TipoControlBean implements ITipoControlBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<TipoControl> list() {
		Collection<TipoControl> result = new LinkedList<TipoControl>();
		
		try {
			TypedQuery<TipoControl> query = entityManager.createQuery(
				"SELECT t "
				+ " FROM TipoControl t"
				+ " ORDER BY t.descripcion", 
				TipoControl.class
			);
			
			for (TipoControl tipoControl : query.getResultList()) {
				result.add(tipoControl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public TipoControl getById(Long id) {
		TipoControl result = null;
		
		try {
			result = entityManager.find(TipoControl.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}