package uy.com.amensg.logistica.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import uy.com.amensg.logistica.entities.Estado;

@Stateless
public class EstadoBean implements IEstadoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Estado getById(Long id) {
		Estado result = null;
		
		try {
			result = entityManager.find(Estado.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}