package uy.com.amensg.logistica.bean;

import java.util.Collection;
import java.util.LinkedList;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import uy.com.amensg.logistica.entities.EstadoProcesoImportacion;

@Stateless
public class EstadoProcesoImportacionBean implements IEstadoProcesoImportacionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogistica")
	private EntityManager entityManager;
	
	public Collection<EstadoProcesoImportacion> list() {
		Collection<EstadoProcesoImportacion> result = new LinkedList<EstadoProcesoImportacion>();
		
		try {
			TypedQuery<EstadoProcesoImportacion> query = entityManager.createQuery(
				"SELECT e"
				+ " FROM EstadoProcesoImportacion e"
				+ " ORDER BY e.nombre", 
				EstadoProcesoImportacion.class
			);
			
			for (EstadoProcesoImportacion estadoProcesoImportacion : query.getResultList()) {
				result.add(estadoProcesoImportacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public EstadoProcesoImportacion getById(Long id) {
		EstadoProcesoImportacion result = null;
		
		try {
			result = entityManager.find(EstadoProcesoImportacion.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}