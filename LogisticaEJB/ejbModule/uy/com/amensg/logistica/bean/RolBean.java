package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Rol;

@Stateless
public class RolBean implements IRolBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<Rol> list() {
		Collection<Rol> result = new LinkedList<Rol>();
		
		try {
			TypedQuery<Rol> query = 
				entityManager.createQuery(
					"SELECT r FROM Rol r"
					+ " ORDER BY r.id",
					Rol.class
				);
			
			for (Rol rol : query.getResultList()) {
				result.add(rol);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Rol getById(Long id) {
		Rol result = null;
		
		try {
			result = entityManager.find(Rol.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}