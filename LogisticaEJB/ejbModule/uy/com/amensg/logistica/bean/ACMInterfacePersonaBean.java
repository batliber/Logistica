package uy.com.amensg.logistica.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import uy.com.amensg.logistica.entities.ACMInterfacePersona;

@Stateless
public class ACMInterfacePersonaBean implements IACMInterfacePersonaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public ACMInterfacePersona getById(Long id) {
		ACMInterfacePersona result = null;
		
		try {
			result = entityManager.find(ACMInterfacePersona.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}