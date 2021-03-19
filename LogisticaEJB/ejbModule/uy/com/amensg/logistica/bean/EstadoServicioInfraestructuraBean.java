package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.EstadoServicioInfraestructura;

@Stateless
public class EstadoServicioInfraestructuraBean implements IEstadoServicioInfraestructuraBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<EstadoServicioInfraestructura> list() {
		Collection<EstadoServicioInfraestructura> result = new LinkedList<EstadoServicioInfraestructura>();
		
		try {
			TypedQuery<EstadoServicioInfraestructura> query = entityManager.createQuery(
				"SELECT e "
				+ " FROM EstadoServicioInfraestructura e"
				+ " ORDER BY e.nombre", 
				EstadoServicioInfraestructura.class
			);
			
			for (EstadoServicioInfraestructura estadoServicioInfraestructura : query.getResultList()) {
				result.add(estadoServicioInfraestructura);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public EstadoServicioInfraestructura getById(Long id) {
		EstadoServicioInfraestructura result = null;
		
		try {
			result = entityManager.find(EstadoServicioInfraestructura.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}