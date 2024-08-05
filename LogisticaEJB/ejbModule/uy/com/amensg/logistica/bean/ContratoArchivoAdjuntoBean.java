package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ContratoArchivoAdjunto;

@Stateless
public class ContratoArchivoAdjuntoBean implements IContratoArchivoAdjuntoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<ContratoArchivoAdjunto> listByContratoId(Long id) {
		Collection<ContratoArchivoAdjunto> result = new LinkedList<ContratoArchivoAdjunto>();
		
		try {
			TypedQuery<ContratoArchivoAdjunto> query =
				entityManager.createQuery(
					"SELECT ca"
					+ " FROM ContratoArchivoAdjunto ca"
					+ " WHERE ca.contrato.id = :contratoId",
					ContratoArchivoAdjunto.class
				);
			query.setParameter("contratoId", id);
			
			Collection<ContratoArchivoAdjunto> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}