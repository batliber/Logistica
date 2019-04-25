package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.Moneda;

@Stateless
public class MonedaBean implements IMonedaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;

	public Collection<Moneda> list() {
		Collection<Moneda> result = new LinkedList<Moneda>();
		
		try {
			TypedQuery<Moneda> query = entityManager.createQuery(
				"SELECT m FROM Moneda m",
				Moneda.class
			);
			
			for (Moneda moneda : query.getResultList()) {
				result.add(moneda);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Moneda getById(Long id) {
		Moneda result = null;
		
		try {
			result = entityManager.find(Moneda.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Moneda getBySimbolo(String simbolo) {
		Moneda result = null;
		
		try {
			TypedQuery<Moneda> query = 
				entityManager.createQuery(
					"SELECT m"
					+ " FROM Moneda m"
					+ " WHERE m.simbolo = :simbolo",
					Moneda.class
				);
			query.setParameter("simbolo", simbolo);
			
			List<Moneda> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}