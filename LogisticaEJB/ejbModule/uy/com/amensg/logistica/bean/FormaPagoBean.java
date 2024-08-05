package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.FormaPago;

@Stateless
public class FormaPagoBean implements IFormaPagoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<FormaPago> list() {
		Collection<FormaPago> result = new LinkedList<FormaPago>();
		
		try {
			TypedQuery<FormaPago> query = entityManager.createQuery(
				"SELECT f "
				+ " FROM FormaPago f"
				+ " ORDER BY f.orden", 
				FormaPago.class
			);
			
			for (FormaPago formaPago : query.getResultList()) {
				result.add(formaPago);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public FormaPago getById(Long id) {
		FormaPago result = null;
		
		try {
			result = entityManager.find(FormaPago.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}