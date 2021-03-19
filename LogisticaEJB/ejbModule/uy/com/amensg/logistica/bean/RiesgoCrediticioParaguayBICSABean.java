package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.RiesgoCrediticioParaguayBICSA;

@Stateless
public class RiesgoCrediticioParaguayBICSABean implements IRiesgoCrediticioParaguayBICSABean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;

	public Collection<RiesgoCrediticioParaguayBICSA> listByRiesgoCrediticioParaguayId(Long riesgoCrediticioParaguayId) {
		Collection<RiesgoCrediticioParaguayBICSA> result = new LinkedList<RiesgoCrediticioParaguayBICSA>();
		
		try {
			TypedQuery<RiesgoCrediticioParaguayBICSA> query = 
				entityManager.createQuery(
					"SELECT rcpb"
					+ " FROM RiesgoCrediticioParaguayBICSA rcpb"
					+ " WHERE rcpb.riesgoCrediticioParaguay.id = :riesgoCrediticioParaguayId",
					RiesgoCrediticioParaguayBICSA.class
				);
			query.setParameter("riesgoCrediticioParaguayId", riesgoCrediticioParaguayId);
			
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
 	public RiesgoCrediticioParaguayBICSA save(RiesgoCrediticioParaguayBICSA riesgoCrediticioParaguayBICSA) {
		RiesgoCrediticioParaguayBICSA result = null;
		
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			riesgoCrediticioParaguayBICSA.setFact(date);
			
			if (riesgoCrediticioParaguayBICSA.getId() != null) {
				result = riesgoCrediticioParaguayBICSA = entityManager.merge(riesgoCrediticioParaguayBICSA);
			} else {
				riesgoCrediticioParaguayBICSA.setFcre(date);
				riesgoCrediticioParaguayBICSA.setUcre(riesgoCrediticioParaguayBICSA.getUact());
				
				entityManager.persist(riesgoCrediticioParaguayBICSA);
				
				result = riesgoCrediticioParaguayBICSA;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}