package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.EstadoPuntoVenta;

@Stateless
public class EstadoPuntoVentaBean implements IEstadoPuntoVentaBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
	private EntityManager entityManager;
	
	public Collection<EstadoPuntoVenta> list() {
		Collection<EstadoPuntoVenta> result = new LinkedList<EstadoPuntoVenta>();
		
		try {
			TypedQuery<EstadoPuntoVenta> query = entityManager.createQuery(
				"SELECT e"
				+ " FROM EstadoPuntoVenta e"
				+ " ORDER BY e.nombre", 
				EstadoPuntoVenta.class
			);
			
			for (EstadoPuntoVenta estadoPuntoVenta : query.getResultList()) {
				result.add(estadoPuntoVenta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public EstadoPuntoVenta getById(Long id) {
		EstadoPuntoVenta result = null;
		
		try {
			result = entityManager.find(EstadoPuntoVenta.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}