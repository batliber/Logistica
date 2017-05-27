package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.EstadoRiesgoCrediticio;

@Stateless
public class EstadoRiesgoCrediticioBean implements IEstadoRiesgoCrediticioBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<EstadoRiesgoCrediticio> list() {
		Collection<EstadoRiesgoCrediticio> result = new LinkedList<EstadoRiesgoCrediticio>();
		
		try {
			TypedQuery<EstadoRiesgoCrediticio> query = entityManager.createQuery(
				"SELECT e "
				+ " FROM EstadoRiesgoCrediticio e"
				+ " ORDER BY e.nombre", 
				EstadoRiesgoCrediticio.class
			);
			
			for (EstadoRiesgoCrediticio estadoRiesgoCrediticio : query.getResultList()) {
				result.add(estadoRiesgoCrediticio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public EstadoRiesgoCrediticio getById(Long id) {
		EstadoRiesgoCrediticio result = null;
		
		try {
			result = entityManager.find(EstadoRiesgoCrediticio.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}