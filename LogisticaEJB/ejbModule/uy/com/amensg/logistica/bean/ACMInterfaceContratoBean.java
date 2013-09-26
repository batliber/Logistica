package uy.com.amensg.logistica.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ACMInterfaceContrato;

@Stateless
public class ACMInterfaceContratoBean implements IACMInterfaceContratoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public ACMInterfaceContrato getSiguienteSinProcesar() {
		ACMInterfaceContrato result = null;
		
		try {
			TypedQuery<ACMInterfaceContrato> query = entityManager.createQuery(
				"SELECT a FROM ACMInterfaceContrato a"
				+ " WHERE a.procesoId = null"
				, ACMInterfaceContrato.class);
			query.setMaxResults(1);
			
			result =  query.getResultList().get(0);
			
			result.setProcesoId(new Long(1));
			
			entityManager.merge(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}