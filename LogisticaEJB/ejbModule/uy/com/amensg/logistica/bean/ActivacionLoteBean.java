package uy.com.amensg.logistica.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.ActivacionLote;

@Stateless
public class ActivacionLoteBean implements IActivacionLoteBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public ActivacionLote save(ActivacionLote activacionLote) {
		ActivacionLote result = null;
		
		try {
			TypedQuery<Long> query = 
				entityManager.createQuery(
					"SELECT MAX(a.numero)"
					+ " FROM ActivacionLote a",
					Long.class
				);
			
			Long maxNumero = Long.valueOf(1);
			
			List<Long> resultList = query.getResultList();
			if (resultList.size() > 0 && resultList.get(0) != null) {
				maxNumero = resultList.get(0) + 1;
			}
			
			activacionLote.setNumero(maxNumero);
			
			activacionLote.setFcre(activacionLote.getFact());
			activacionLote.setUcre(activacionLote.getUact());
			
			entityManager.persist(activacionLote);
			
			result = activacionLote;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}