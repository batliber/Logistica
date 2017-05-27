package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.EstadoControl;

@Stateless
public class EstadoControlBean implements IEstadoControlBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<EstadoControl> list() {
		Collection<EstadoControl> result = new LinkedList<EstadoControl>();
		
		try {
			TypedQuery<EstadoControl> query = entityManager.createQuery(
				"SELECT e "
				+ " FROM EstadoControl e"
				+ " ORDER BY e.nombre", 
				EstadoControl.class
			);
			
			for (EstadoControl estadoControl : query.getResultList()) {
				result.add(estadoControl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public EstadoControl getById(Long id) {
		EstadoControl result = null;
		
		try {
			result = entityManager.find(EstadoControl.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}