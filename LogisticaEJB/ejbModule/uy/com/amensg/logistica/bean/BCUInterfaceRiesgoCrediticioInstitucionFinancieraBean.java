package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.BCUInterfaceRiesgoCrediticioInstitucionFinanciera;

@Stateless
public class BCUInterfaceRiesgoCrediticioInstitucionFinancieraBean implements IBCUInterfaceRiesgoCrediticioInstitucionFinancieraBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<BCUInterfaceRiesgoCrediticioInstitucionFinanciera> listByBCUInterfaceRiesgoCrediticioId(Long id) {
		Collection<BCUInterfaceRiesgoCrediticioInstitucionFinanciera> result = new LinkedList<BCUInterfaceRiesgoCrediticioInstitucionFinanciera>();
		
		try {
			TypedQuery<BCUInterfaceRiesgoCrediticioInstitucionFinanciera> query = 
				entityManager.createQuery(
					"SELECT b"
					+ " FROM BCUInterfaceRiesgoCrediticioInstitucionFinanciera b"
					+ " WHERE b.bcuInterfaceRiesgoCrediticio.id = :bcuInterfaceRiesgoCrediticioId",
					BCUInterfaceRiesgoCrediticioInstitucionFinanciera.class
				);
			query.setParameter("bcuInterfaceRiesgoCrediticioId", id);
			
			Collection<BCUInterfaceRiesgoCrediticioInstitucionFinanciera> resultList = query.getResultList();
			for (BCUInterfaceRiesgoCrediticioInstitucionFinanciera bcuInterfaceRiesgoCrediticioInstitucionFinanciera : resultList) {
				result.add(bcuInterfaceRiesgoCrediticioInstitucionFinanciera);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void save(BCUInterfaceRiesgoCrediticioInstitucionFinanciera bcuInterfaceRiesgoCrediticioInstitucionFinanciera) {
		try {
			Date date = GregorianCalendar.getInstance().getTime();
			
			bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setFcre(date);
			bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setFact(date);
			bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setTerm(Long.valueOf(1));
			bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setUact(Long.valueOf(1));
			bcuInterfaceRiesgoCrediticioInstitucionFinanciera.setUcre(Long.valueOf(1));
			
			entityManager.persist(bcuInterfaceRiesgoCrediticioInstitucionFinanciera);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}