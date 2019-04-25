package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.TipoTasaInteresEfectivaAnual;

@Stateless
public class TipoTasaInteresEfectivaAnualBean implements ITipoTasaInteresEfectivaAnualBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<TipoTasaInteresEfectivaAnual> list() {
		Collection<TipoTasaInteresEfectivaAnual> result = new LinkedList<TipoTasaInteresEfectivaAnual>();
		
		try {
			TypedQuery<TipoTasaInteresEfectivaAnual> query = entityManager.createQuery(
				"SELECT t FROM TipoTasaInteresEfectivaAnual t",
				TipoTasaInteresEfectivaAnual.class
			);
			
			for (TipoTasaInteresEfectivaAnual tipoTasaInteresEfectivaAnual : query.getResultList()) {
				result.add(tipoTasaInteresEfectivaAnual);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public TipoTasaInteresEfectivaAnual getById(Long id) {
		TipoTasaInteresEfectivaAnual result = null;
		
		try {
			result = entityManager.find(TipoTasaInteresEfectivaAnual.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}