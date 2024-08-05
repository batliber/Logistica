package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.UnidadIndexada;

@Stateless
public class UnidadIndexadaBean implements IUnidadIndexadaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;

	public Collection<UnidadIndexada> list() {
		Collection<UnidadIndexada> result = new LinkedList<UnidadIndexada>();
		
		try {
			TypedQuery<UnidadIndexada> query = 
				entityManager.createQuery(
					"SELECT u"
					+ " FROM UnidadIndexada u"
					+ " ORDER BY u.fechaVigenciaHasta DESC",
					UnidadIndexada.class
				);
			
			Collection<UnidadIndexada> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public UnidadIndexada getById(Long id) {
		UnidadIndexada result = null;
		
		try {
			result = entityManager.find(UnidadIndexada.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public UnidadIndexada getVigente() {
		UnidadIndexada result = null;

		try {
			TypedQuery<UnidadIndexada> query = 
				entityManager.createQuery(
					"SELECT u"
					+ " FROM UnidadIndexada u"
					+ " WHERE u.fechaVigenciaHasta IS NULL", 
					UnidadIndexada.class
				);
			
			List<UnidadIndexada> resultList = query.getResultList();
			if (resultList.size() > 0) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public UnidadIndexada save(UnidadIndexada unidadIndexada) {
		UnidadIndexada result = null;
		
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			UnidadIndexada unidadIndexadaVigente = this.getVigente();
			if (unidadIndexadaVigente != null) {
				unidadIndexadaVigente.setFechaVigenciaHasta(date);
				
				unidadIndexadaVigente.setFact(date);
				unidadIndexadaVigente.setTerm(Long.valueOf(1));
				unidadIndexadaVigente.setUact(unidadIndexada.getUact());
				
				entityManager.merge(unidadIndexadaVigente);
			}
			
			unidadIndexada.setFcre(unidadIndexada.getFact());
			unidadIndexada.setUcre(unidadIndexada.getUact());
			
			entityManager.persist(unidadIndexada);
			
			return unidadIndexada;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void remove(UnidadIndexada unidadIndexada) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			UnidadIndexada managedUnidadIndexada = entityManager.find(UnidadIndexada.class, unidadIndexada.getId());
			
			managedUnidadIndexada.setFechaVigenciaHasta(date);
			
			managedUnidadIndexada.setFact(unidadIndexada.getFact());
			managedUnidadIndexada.setTerm(unidadIndexada.getTerm());
			managedUnidadIndexada.setUact(unidadIndexada.getUact());
			
			entityManager.merge(managedUnidadIndexada);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}