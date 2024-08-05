package uy.com.amensg.logistica.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
@Remote(IBaseBean.class)
public class BaseBean implements IBaseBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;

	public Collection<Serializable> list(Class type) {
		Collection<Serializable> result = new LinkedList<Serializable>();
		
		try {
			TypedQuery<Serializable> query = 
				entityManager.createQuery(
					"SELECT s FROM " + type.getSimpleName() + " s",
					Serializable.class
				);
			
			result = query.getResultList(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Serializable getById(Long id, Class type) {
		Serializable result = null;
		
		try {
			result = (Serializable) entityManager.find(type, id); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}