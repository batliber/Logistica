package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
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
	
	public Collection<TasaInteresEfectivaAnual> list() {
		Collection<TasaInteresEfectivaAnual> result = new LinkedList<TasaInteresEfectivaAnual>();
		
		try {
			TypedQuery<TasaInteresEfectivaAnual> query = 
				entityManager.createQuery(
					"SELECT t"
					+ " FROM TasaInteresEfectivaAnual t"
					+ " ORDER BY t.fechaVigenciaHasta DESC",
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

	public TasaInteresEfectivaAnual getById(Long id) {
		TasaInteresEfectivaAnual result = null;
		
		try {
			result = entityManager.find(TasaInteresEfectivaAnual.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(TasaInteresEfectivaAnual tasaInteresEfectivaAnual) {
		try {
			entityManager.persist(tasaInteresEfectivaAnual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(TasaInteresEfectivaAnual tasaInteresEfectivaAnual) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			TasaInteresEfectivaAnual managedTasaInteresEfectivaAnual = entityManager.find(TasaInteresEfectivaAnual.class, tasaInteresEfectivaAnual.getId());
			
			managedTasaInteresEfectivaAnual.setFechaVigenciaHasta(date);
			
			managedTasaInteresEfectivaAnual.setFact(tasaInteresEfectivaAnual.getFact());
			managedTasaInteresEfectivaAnual.setTerm(tasaInteresEfectivaAnual.getTerm());
			managedTasaInteresEfectivaAnual.setUact(tasaInteresEfectivaAnual.getUact());
			
			entityManager.merge(managedTasaInteresEfectivaAnual);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}