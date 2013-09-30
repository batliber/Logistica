package uy.com.amensg.logistica.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;

@Stateless
public class ACMInterfaceBean implements IACMInterfaceBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public ACMInterfaceContrato getNextContratoSinProcesar() {
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
	
	public ACMInterfacePrepago getNextPrepagoSinProcesar() {
		ACMInterfacePrepago result = null;
		
		try {
			TypedQuery<ACMInterfacePrepago> query = entityManager.createQuery(
				"SELECT a FROM ACMInterfacePrepago a"
				+ " WHERE a.procesoId = null"
				, ACMInterfacePrepago.class);
			query.setMaxResults(1);
			
			result =  query.getResultList().get(0);
			
			result.setProcesoId(new Long(1));
			
			entityManager.merge(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void update(ACMInterfaceContrato acmInterfaceContrato) {
		try {
			entityManager.merge(acmInterfaceContrato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(ACMInterfacePrepago acmInterfacePrepago) {
		try {
			entityManager.merge(acmInterfacePrepago);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}