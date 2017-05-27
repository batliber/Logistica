package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.TasaInteresEfectivaAnual;

@Stateless
public class TasaInteresEfectivaAnualBean implements ITasaInteresEfectivaAnualBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<TasaInteresEfectivaAnual> listVigentes() {
		Collection<TasaInteresEfectivaAnual> result = new LinkedList<TasaInteresEfectivaAnual>();
		
		try {
			TypedQuery<TasaInteresEfectivaAnual> query = 
				entityManager.createQuery(
					"SELECT t"
					+ " FROM TasaInteresEfectivaAnual t"
					+ " WHERE t.fechaVigenciaHasta IS NULL",
					TasaInteresEfectivaAnual.class
				);
			
			Collection<TasaInteresEfectivaAnual> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}