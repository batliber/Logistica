package uy.com.amensg.logistica.bean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import uy.com.amensg.logistica.entities.EntidadVista;

@Stateless
public class EntidadVistaBean implements IEntidadVistaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public EntidadVista getById(Long id) {
		EntidadVista result = null;
		
		try {
			result = entityManager.find(EntidadVista.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}