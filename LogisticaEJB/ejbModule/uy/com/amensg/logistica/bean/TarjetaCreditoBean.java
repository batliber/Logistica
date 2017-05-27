package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.TarjetaCredito;

@Stateless
public class TarjetaCreditoBean implements ITarjetaCreditoBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<TarjetaCredito> list() {
		Collection<TarjetaCredito> result = new LinkedList<TarjetaCredito>();
		
		try {
			TypedQuery<TarjetaCredito> query = entityManager.createQuery(
				"SELECT t "
				+ " FROM TarjetaCredito t"
				+ " ORDER BY t.nombre", 
				TarjetaCredito.class
			);
			
			for (TarjetaCredito TarjetaCredito : query.getResultList()) {
				result.add(TarjetaCredito);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public TarjetaCredito getById(Long id) {
		TarjetaCredito result = null;
		
		try {
			result = entityManager.find(TarjetaCredito.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}