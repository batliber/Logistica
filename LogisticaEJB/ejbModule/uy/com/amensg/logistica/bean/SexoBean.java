package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Sexo;

@Stateless
public class SexoBean implements ISexoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<Sexo> list() {
		Collection<Sexo> result = new LinkedList<Sexo>();
		
		try {
			TypedQuery<Sexo> query = entityManager.createQuery(
				"SELECT s FROM Sexo s",
				Sexo.class
			);
			
			for (Sexo sexo : query.getResultList()) {
				result.add(sexo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}