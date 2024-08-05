package uy.com.amensg.logistica.bean;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ACMInterfacePersona;

@Stateless
public class ACMInterfacePersonaBean implements IACMInterfacePersonaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
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
	
	public ACMInterfacePersona getByDocumento(String documento) {
		ACMInterfacePersona result = null;
		
		try {
			TypedQuery<ACMInterfacePersona> query = 
				entityManager.createQuery(
					"SELECT p"
					+ " FROM ACMInterfacePersona p"
					+ " WHERE p.documento = :documento",
					ACMInterfacePersona.class
				);
			query.setParameter("documento", documento);
			
			List<ACMInterfacePersona> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public ACMInterfacePersona getByIdCliente(String idCliente) {
		ACMInterfacePersona result = null;
		
		try {
			TypedQuery<ACMInterfacePersona> query = 
				entityManager.createQuery(
					"SELECT p"
					+ " FROM ACMInterfacePersona p"
					+ " WHERE p.idCliente = :idCliente",
					ACMInterfacePersona.class
				);
			query.setParameter("idCliente", idCliente);
			
			List<ACMInterfacePersona> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}