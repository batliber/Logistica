package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.EstadoANTEL;

@Stateless
public class EstadoANTELBean implements IEstadoANTELBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<EstadoANTEL> list() {
		Collection<EstadoANTEL> result = new LinkedList<EstadoANTEL>();
		
		try {
			TypedQuery<EstadoANTEL> query = entityManager.createQuery(
				"SELECT e "
				+ " FROM EstadoANTEL e"
				+ " ORDER BY e.nombre", 
				EstadoANTEL.class
			);
			
			for (EstadoANTEL estadoANTEL : query.getResultList()) {
				result.add(estadoANTEL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public EstadoANTEL getById(Long id) {
		EstadoANTEL result = null;
		
		try {
			result = entityManager.find(EstadoANTEL.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}