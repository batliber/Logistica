package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.EstadoActivacion;

@Stateless
public class EstadoActivacionBean implements IEstadoActivacionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<EstadoActivacion> list() {
		Collection<EstadoActivacion> result = new LinkedList<EstadoActivacion>();
		
		try {
			TypedQuery<EstadoActivacion> query = entityManager.createQuery(
				"SELECT e "
				+ " FROM EstadoActivacion e"
				+ " ORDER BY e.nombre", 
				EstadoActivacion.class
			);
			
			for (EstadoActivacion estadoActivacion : query.getResultList()) {
				result.add(estadoActivacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public EstadoActivacion getById(Long id) {
		EstadoActivacion result = null;
		
		try {
			result = entityManager.find(EstadoActivacion.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}