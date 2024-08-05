package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.EstadoVisitaPuntoVentaDistribuidor;

@Stateless
public class EstadoVisitaPuntoVentaDistribuidorBean implements IEstadoVisitaPuntoVentaDistribuidorBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<EstadoVisitaPuntoVentaDistribuidor> list() {
		Collection<EstadoVisitaPuntoVentaDistribuidor> result = 
			new LinkedList<EstadoVisitaPuntoVentaDistribuidor>();
		
		try {
			TypedQuery<EstadoVisitaPuntoVentaDistribuidor> query = 
				entityManager.createQuery(
					"SELECT e "
					+ " FROM EstadoVisitaPuntoVentaDistribuidor e"
					+ " ORDER BY e.nombre", 
					EstadoVisitaPuntoVentaDistribuidor.class
				);
			
			for (EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor : query.getResultList()) {
				result.add(estadoVisitaPuntoVentaDistribuidor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public EstadoVisitaPuntoVentaDistribuidor getById(Long id) {
		EstadoVisitaPuntoVentaDistribuidor result = null;
		
		try {
			result = entityManager.find(EstadoVisitaPuntoVentaDistribuidor.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}