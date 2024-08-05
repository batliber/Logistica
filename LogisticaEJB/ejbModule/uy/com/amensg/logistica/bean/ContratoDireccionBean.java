package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ContratoDireccion;

@Stateless
public class ContratoDireccionBean implements IContratoDireccionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<ContratoDireccion> listByContratoId(Long id) {
		Collection<ContratoDireccion> result = new LinkedList<ContratoDireccion>();
		
		try {
			TypedQuery<ContratoDireccion> query =
				entityManager.createQuery(
					"SELECT cd"
					+ " FROM ContratoDireccion cd"
					+ " WHERE cd.contrato.id = :contratoId",
					ContratoDireccion.class
				);
			query.setParameter("contratoId", id);
			
			Collection<ContratoDireccion> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}