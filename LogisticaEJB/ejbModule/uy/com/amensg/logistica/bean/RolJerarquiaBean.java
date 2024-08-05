package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.RolJerarquia;

@Stateless
public class RolJerarquiaBean implements IRolJerarquiaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<RolJerarquia> list() {
		Collection<RolJerarquia> result = new LinkedList<RolJerarquia>();
		
		try {
			TypedQuery<RolJerarquia> query = 
				entityManager.createQuery(
					"SELECT rj"
					+ " FROM RolJerarquia rj"
					, RolJerarquia.class
				);
			
			for (RolJerarquia rolJerarquia : query.getResultList()) {
				result.add(rolJerarquia);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}