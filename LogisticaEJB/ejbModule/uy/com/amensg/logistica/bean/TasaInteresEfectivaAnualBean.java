package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.TasaInteresEfectivaAnual;
import uy.com.amensg.logistica.entities.TipoTasaInteresEfectivaAnual;

@Stateless
public class TasaInteresEfectivaAnualBean implements ITasaInteresEfectivaAnualBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
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
					+ " WHERE t.fechaVigenciaHasta IS NULL"
					+ " ORDER BY t.tipoTasaInteresEfectivaAnual.descripcion,"
						+ " t.cuotasDesde,"
						+ " t.cuotasHasta,"
						+ " t.montoDesde,"
						+ " t.montoHasta",
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
	
	public Collection<TasaInteresEfectivaAnual> listVigentesByTipo(TipoTasaInteresEfectivaAnual tipoTasaInteresEfectivaAnual) {
		Collection<TasaInteresEfectivaAnual> result = new LinkedList<TasaInteresEfectivaAnual>();
		
		try {
			TypedQuery<TasaInteresEfectivaAnual> query = 
				entityManager.createQuery(
					"SELECT t"
					+ " FROM TasaInteresEfectivaAnual t"
					+ " WHERE t.fechaVigenciaHasta IS NULL"
					+ " AND t.tipoTasaInteresEfectivaAnual.id = :tipoTasaInteresEfectivaAnualId",
					TasaInteresEfectivaAnual.class
				);
			query.setParameter("tipoTasaInteresEfectivaAnualId", tipoTasaInteresEfectivaAnual.getId());
			
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
	
	public TasaInteresEfectivaAnual save(TasaInteresEfectivaAnual tasaInteresEfectivaAnual) {
		TasaInteresEfectivaAnual result = null;
		
		try {
			tasaInteresEfectivaAnual.setFcre(tasaInteresEfectivaAnual.getFact());
			tasaInteresEfectivaAnual.setUcre(tasaInteresEfectivaAnual.getUact());
			
			entityManager.persist(tasaInteresEfectivaAnual);
			
			result = tasaInteresEfectivaAnual;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
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