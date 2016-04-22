package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Estado;

@Stateless
public class EstadoBean implements IEstadoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Estado> list() {
		Collection<Estado> result = new LinkedList<Estado>();
		
		try {
			TypedQuery<Estado> query = entityManager.createQuery(
				"SELECT e "
				+ " FROM Estado e"
				+ " ORDER BY e.nombre", 
				Estado.class
			);
			
			for (Estado estado : query.getResultList()) {
				result.add(estado);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
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